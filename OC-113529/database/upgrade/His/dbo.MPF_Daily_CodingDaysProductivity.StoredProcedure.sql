USE [his]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_CodingDaysProductivity]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_CodingDaysProductivity]
GO

/*
***************************************************************
*  	COPYRIGHT MCKESSON 2012
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of McKesson. The program(s)
* 	may be used and/or copied only with the written
* 	permission of McKesson or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
*****************************************************************
* Procedure Name: [MPF_Daily_CodingDaysProductivity]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Srinivasan Ramaswamy	11/01/2012	Created
*	MD Meersma				11/30/2012	Code Review
*	Joseph Chen				01/03/2013	Change misspell name ##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL,
*										add LOS_Undefined column	
*	Srinivasan Ramaswamy	03/04/2013	Added AssignmentCreationDate, DocumentationSatisfiedDate to the Query	
*   Iryna Politykina		03/14/2013  Add a default for User Full name if the user full name is NULL	
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*	Iryna Politykina		04/03/2013  Fix per CR# 379171:Beta-Shannon: Daily Coding Productivity Daily since Discharge incorrectly calculated
*   Latonia Howard			04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*   Latonia Howard			04/25/2013  Fix per CR # 380234 :Daily Scorecard, Coding and Analysis Productivity will not display - Performance issue
*	MD Meersma				10/08/2013	Change localhost to use @@servername
**************************************************************************************************************
*/
/*
	UC5930 - View Daily Coding Days Productivity Measure

	-	Yesterday's productivity of encounter coding 
			* how many encounters were coded, 
			* by whom, 
			* within what timeframe from discharge to coding complete
	-	to monitor the staff output and move resources accordingly

	
	===========================================================================================================================
	Columns						DATABASE	TABLE					DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
 	FACILITY					HIS			Encounters				Facility Code						FacilityCode
 	FACILITY_NAME				CABINET		Facility_File			Facility Name						FacilityName
 	MRN							HIS			Encounters				MRN									MRN
	Encounter					HIS	 		Encounters 				Encounter Id						Encounter
  	TOTAL_CHARGES				HIS			Encounters				Charges								Charges
 	DISCHARGED					HIS			Encounters				Discharged Date						DischargedDate
 	DISCHARGED					HIS			Encounters				Discharged Date (Formatted)			DischargedDateText
	CALCULATED FIELD			HIS			ENCOUNTERS				today-e.DISCHARGED				 	DaysSinceDischarged
	Queue						CABINET		AssignmentAuditEvent	AssignmentAuditEvent				WorkflowQueueName
	NAME						HIS			PATIENTS				Patient Name						PatientName
 	PTTYPE_NAME					CABINET		PTTYPE_FILE				Patient Type						PatientType
	UNIT_NAME					HIS			UNIT_FILE				Unit Name							MedicalUnitName
	UNIT_CODE					HIS			UNIT_FILE				Unit Code							MedicalUnitCode
	NAME						HIS			PHYSICIANS				Attending Physician					AttendingPhysician
	CALCULATED FIELD			HIS			ENCOUNTERS				Discharged Date - Admit Date 		LOS
																	=1 if discharged on the admit date itself
	UserInstanceID				CABINET		AssignmentAuditEvent	Coder								Coder
	AssignmentCreationDate		
	DocumentationSatisfiedDate	CABINET		AssignmentAuditEvent	EncounterEventDate					DocumentationSatisfiedDate
	==============================================================================================================================
*/


--  ******************************************************************************************************************
CREATE PROCEDURE [dbo].[MPF_Daily_CodingDaysProductivity]
	@HBI_TargetDir varchar(4000)  -- file_path column FROM WebData.dbo.wt_definition_info (HBI Server)
AS

BEGIN TRY

DECLARE @xp_cmdshell TINYINT, @Success INT, @sqlcmd VARCHAR(2000), @ABS_Target_Dir VARCHAR(255)

-- show advanced options enable if disabled
IF EXISTS( SELECT value_in_use FROM master.sys.configurations WHERE name = 'show advanced options' AND value_in_use = 0)
BEGIN
	EXEC sp_configure 'show advanced options', 1
	reconfigure WITH override
END

-- xp_cmdshell enable if disabled
SELECT @xp_cmdshell = 1
IF EXISTS( SELECT value_in_use FROM master.sys.configurations WHERE name = 'xp_cmdshell' AND value_in_use = 0)
BEGIN
	SELECT @xp_cmdshell = 0
	EXEC sp_configure 'xp_cmdshell', 1
	reconfigure WITH override
END

-- Delete all the previous TXT files incase we do not generate all of them
SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_CodingDaysProductivity.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY') IS NOT NULL
DROP TABLE ##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY
IF OBJECT_ID('tempdb..##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_1') IS NOT NULL
DROP TABLE ##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_1
IF OBJECT_ID('tempdb..##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_2') IS NOT NULL
DROP TABLE ##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_2

-- the main SELECT statement
SELECT
	ASSIGNMENT_ID,
	ENCOUNTER,
	MRN,
	FACILITY,
	QueueTypeName,
	Queue,
	AssignmentEventDate as AssignmentCompleteDate,
	UserInstanceId
INTO ##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_1
FROM cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK)
WHERE
	WorkflowEventName='Complete' AND
	assignmentEventDate >= DATEADD(day,datediff(day,1,getdate()),0) AND assignmentEventDate < DATEADD(day,datediff(day,0,getdate()),0) AND
	QueueTypeName='Coding' 

SELECT
	dcya.*,
	ae.AssignmentEventDate as AssignmentCreationDate
INTO ##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_2
FROM ##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_1 dcya JOIN
cabinet.dbo.AssignmentAuditEvent ae WITH (NOLOCK) ON
dcya.ASSIGNMENT_ID = ae.ASSIGNMENT_ID
WHERE ae.WorkflowEventName = 'Create' or  ae.WorkflowEventName= 'Reassign To' or ae.WorkflowEventName= 'ReassignTo'


SELECT
	e.ENCOUNTER as Encounter, 
	COALESCE(NULLIF((F.FACILITY_NAME), ''), COALESCE(NULLIF(E.FACILITY, ''), 'NO FACILITY')) as FacilityName,
	aae.Queue as WorkflowQueueName,
	COALESCE(u.FullName, 'User ID: ' + CAST(aae.UserInstanceId  as VARCHAR)) as Coder,
	p.NAME AS PatientName,
	e.MRN,
	COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=e.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=e.FACILITY)), ''), 'Undefined') AS PatientType,	
    COALESCE(NULLIF(
	  (SELECT TOP 1 SERVICE_NAME FROM cabinet.dbo.SERVICE_FILE WHERE SERVICE_CODE=e.service AND SERVICE_ACTIVE='Y' AND SET_ID=(SELECT TOP 1 SERVICE_SET from cabinet.dbo.FACILITY_FILE WHERE FACILITY_CODE=e.FACILITY)  
	  ),''),'Undefined') as EncounterService,
	e.DISCHARGED AS DischargedDate, 
	his.dbo.fnHBIDateTime(e.DISCHARGED) AS DischargedDateText,
	/* Modified - SR */
	/* his.dbo.fnHBIDateTime(a.Assigned) AS AssignedDateText, */
	his.dbo.fnHBIDateTime(aae.AssignmentCreationDate) AS AssignedDateText,	
	DATEDIFF( dd, e.Discharged, aae.AssignmentCompleteDate) AS DischargeToCompletedDays,
	DATEDIFF( dd, AssignmentCreationDate, aae.AssignmentCompleteDate) AS AssignedToCompletedDays,
	CASE  WHEN E.ADMITTED IS NULL THEN 0
		  ELSE
  			(CASE 
  				WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
				ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
			END)
	END	as LOS,
	LOS_1 		= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 1 THEN 1 ELSE 0 END),
	LOS_2 		= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 2 THEN 1 ELSE 0 END),
	LOS_3 		= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 3 THEN 1 ELSE 0 END),
	LOS_4_7 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 4 AND 7 THEN 1 ELSE 0 END),
	LOS_8_14 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 8 AND 14 THEN 1 ELSE 0 END),
	LOS_15_21 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 15 AND 21 THEN 1 ELSE 0 END),
	LOS_22_28 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 22 AND 28 THEN 1 ELSE 0 END),
	LOS_29_60 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 29 AND 60 THEN 1 ELSE 0 END),
	LOS_61_90 	= (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 61 AND 90 THEN 1 ELSE 0 END),
	LOS_91_More = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) > 90 THEN 1 ELSE 0 END),
	e.TOTAL_CHARGES as Charges, 
	ISNULL((SELECT TOP 1 name FROM dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER = e.ENCOUNTER AND FACILITY=e.FACILITY AND TYPE = 'Attending' ORDER BY NAME), 'Undefined') AS AttendingPhysician,
	ISNULL((SELECT TOP 1 UNIT_CODE FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE = (SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER = e.ENCOUNTER AND FACILITY=e.FACILITY ORDER BY occurred DESC) AND SET_ID = F.UNIT_SET), 'Undefined') AS MedicalUnitCode,
	ISNULL((SELECT TOP 1 UNIT_NAME FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE = (SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER AND FACILITY=e.FACILITY ORDER BY occurred DESC) AND SET_ID = F.UNIT_SET), 'Undefined') AS MedicalUnitName,	-- DocumentationSatisifiedDateText
	LOS_Undefined = (CASE WHEN  e.admitted IS NULL THEN 1 ELSE 0 END),
	his.dbo.fnHBIDateTime(aae.AssignmentCreationDate) AS AssignmentCreationDateText,
	his.dbo.fnHBIDateTime((select top 1 EncounterEventDate from cabinet.dbo.encounterauditevent with (NOLOCK) WHERE encounter=aae.ENCOUNTER AND facility=aae.facility AND encountereventname='DocumentationSatisfied')) as DocumentationSatisfiedDateText
INTO 
	##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY
FROM 
	##HBI_D_CODINGDAYS_YESTERDAY_ASSIGNMENTS_2 aae
JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON
	e.ENCOUNTER = aae.ENCOUNTER AND
	e.FACILITY = aae.FACILITY
JOIN dbo.PATIENTS p WITH (NOLOCK) ON
	p.FACILITY = e.FACILITY AND
	p.MRN = e.MRN
JOIN cabinet.dbo.FACILITY_FILE f WITH (NOLOCK) ON 
	e.FACILITY = f.FACILITY_CODE 
LEFT OUTER JOIN 
	cabinet.dbo.USERS u WITH (NOLOCK) ON 
	aae.UserInstanceId = u.UserInstanceId	
WHERE
	e.DISCHARGED IS NOT NULL

SET  @sqlcmd = ' bcp ##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_CodingDaysProductivity.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_DAILY_CODINGDAYSPRODUCTIVITY was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_CodingDaysProductivity.txt'
	EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

	DECLARE @ErrorMessage NVARCHAR(2048), @ErrorSeverity INT, @ErrorState INT, @ErrorProcedure nvarchar(252), @ErrorNumber int

	SELECT 
		@ErrorMessage = ERROR_MESSAGE() ,
		@ErrorSeverity = ERROR_SEVERITY(),
		@ErrorState = ERROR_STATE(),
		@ErrorProcedure = ERROR_PROCEDURE(),
		@ErrorNumber = ERROR_NUMBER()

	--if ERROR_NUMBER() <> 50000  -- do not log RAISERRORS caused by the sub stored procedures, they have CEL for this
	insert cabinet.dbo.SYSERRORS_GLOBAL (GlobalID, OCCURRED, ERROR_TYPE, ERROR_DESC, [ERROR_NUMBER], [ERROR_SEVERITY], [ERROR_STATE], [ERROR_PROCEDURE], [ERROR_LINE])
	values (-43, getdate(), 'SQL', ERROR_MESSAGE(), ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE())

	RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState)
	
END CATCH
GO

GRANT EXEC on [dbo].[MPF_Daily_CodingDaysProductivity] to [IMNET]
GO	