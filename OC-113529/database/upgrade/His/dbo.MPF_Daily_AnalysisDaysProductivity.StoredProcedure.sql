USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_AnalysisDaysProductivity]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_AnalysisDaysProductivity]
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
* Procedure Name: [MPF_Daily_AnalysisDaysProductivity]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Joseph Chen				11/7/2012	Created
*	MD Meersma				11/30/2012	Code Review
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL,
*										add LOS_Undefined column	
*	Iryna Politykina		03/01/2013 Removed admtted date column
*	Srinivasan Ramaswamy	03/04/2013	Added DocumentationSatisfiedDate to the Query	
*   Iryna Politykina		03/14/2013  Add a default value for User Full name if the user full name is NULL	
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty						
*   Latonia Howard			04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*   Latonia Howard			04/25/2013  Fix per CR # 380234 :Daily Scorecard, Coding and Analysis Productivity will not display - Performance issue
*	Latonia Howard			05/08/2013	Fix per CR # 380,370: Days from Assign is a negative number
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5931 - View Daily Analysis Productivity Measure

	
	
	===========================================================================================================================
	Columns						DATABASE	TABLE					DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
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
	NAME						HIS			PHYSICIANS				Attending Physician					AttendingPhysician
	CALCULATED FIELD			HIS			ENCOUNTERS				IF DischargedDate=AdmitDate Then 1 	LOS
																	ELSE 
																	(Discharged Date - Admit Date) + 1
	DocumentationSatisfiedDate	CABINET		AssignmentAuditEvent	EncounterEventDate					DocumentationSatisfiedDate																	
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Daily_AnalysisDaysProductivity]
@HBI_TargetDir varchar(4000)  -- file_path column FROM WebData.dbo.wt_definition_info (HBI Server)
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255)

-- show advanced options enable if disabled
if exists(SELECT value_in_use FROM master.sys.configurations WHERE name = 'show advanced options' and value_in_use = 0)
begin
	exec sp_configure 'show advanced options', 1
	reconfigure with override
end

-- xp_cmdshell enable if disabled
SELECT @xp_cmdshell = 1
if exists(SELECT value_in_use FROM master.sys.configurations WHERE name = 'xp_cmdshell' and value_in_use = 0)
begin
	SELECT @xp_cmdshell = 0
	exec sp_configure 'xp_cmdshell', 1
	reconfigure with override
end


-- Delete all the previous TXT files incase we do not generate all of them

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_AnalysisDaysProductivity.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_DAILY_ANALYSISDAYSPRODUCTIVITY') IS NOT NULL
DROP TABLE ##HBI_SUBSET_DAILY_ANALYSISDAYSPRODUCTIVITY
IF OBJECT_ID('tempdb..##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_1') IS NOT NULL
DROP TABLE ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_1
IF OBJECT_ID('tempdb..##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_2') IS NOT NULL
DROP TABLE ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_2

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
INTO ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_1
FROM cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK)
WHERE
      assignmentEventDate >= DATEADD(day,datediff(day,1,getdate()),0) AND assignmentEventDate < DATEADD(day,datediff(day,0,getdate()),0)
      AND QueueTypeName='Analysis'
      AND WorkflowEventName='Complete'


SELECT
	daya.*,
	ae.AssignmentEventDate as AssignmentCreationDate
INTO ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_2
FROM ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_1 daya JOIN
cabinet.dbo.AssignmentAuditEvent ae WITH (NOLOCK) ON
daya.ASSIGNMENT_ID = ae.ASSIGNMENT_ID
WHERE ae.WorkflowEventName = 'Create' or  ae.WorkflowEventName= 'Reassign To' or ae.WorkflowEventName= 'ReassignTo'



SELECT
      ENC.FACILITY as FacilityCode, 
      COALESCE(NULLIF((f.FACILITY_NAME), ''), COALESCE(NULLIF(ENC.FACILITY, ''), 'NO FACILITY')) as FacilityName,
      ENC.MRN, 
      ENC.ENCOUNTER as Encounter, 
      COALESCE(NULLIF(
	  (SELECT TOP 1 SERVICE_NAME FROM cabinet.dbo.SERVICE_FILE WHERE SERVICE_CODE=ENC.service AND SERVICE_ACTIVE='Y' AND SET_ID=(SELECT TOP 1 SERVICE_SET from cabinet.dbo.FACILITY_FILE WHERE FACILITY_CODE=ENC.FACILITY)  
	  ),''),'Undefined') as EncounterService,
      COALESCE(u.FullName, 'User ID: ' + CAST(c.UserInstanceId  as VARCHAR)) as Username,
      c.ASSIGNMENT_ID as AssignmentID,
      ENC.TOTAL_CHARGES as Charges, 
      ENC.DISCHARGED AS DischargedDate, 
      his.dbo.fnHBIDateTime(ENC.DISCHARGED) AS DischargedDateText,
      c.Queue as WorkflowQueueName,
      p.NAME AS PatientName,  
	  COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=ENC.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=ENC.FACILITY)), ''), 'Undefined') AS PatientType,
	  COALESCE((SELECT TOP 1 UNIT_NAME FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
      (SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY ORDER BY occurred DESC )
      AND SET_ID=F.UNIT_SET), 'Undefined') AS MedicalUnitName, 
      COALESCE((SELECT TOP 1 UNIT_CODE FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
      (SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY ORDER BY occurred DESC )
      AND SET_ID=F.UNIT_SET), 'Undefined') AS MedicalUnitCode,          
      COALESCE((SELECT TOP 1 name FROM his.dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER=ENC.ENCOUNTER AND FACILITY=ENC.FACILITY AND TYPE = 'Attending' ORDER BY NAME), 'Undefined') AS AttendingPhysician,  
     
      c.AssignmentCreationDate AS AssignmentCreationDate,
	  his.dbo.fnHBIDateTime(c.AssignmentCreationDate) AS AssignmentCreationDateText ,
	  c.AssignmentCreationDate AS AnalysisReadyDate,
	  his.dbo.fnHBIDateTime(c.AssignmentCreationDate) AS AnalysisReadyDateText,
  
      c.AssignmentCompleteDate as AssignmentCompleteDate,
	  his.dbo.fnHBIDateTime(c.AssignmentCompleteDate) AS AssignmentCompleteDateText,
      datediff(dd,ENC.DISCHARGED,c.AssignmentCompleteDate) AS DaysToCompleteFromDischarge, 
      datediff(dd, c.AssignmentCreationDate, c.AssignmentCompleteDate) AS DaysToCompleteFromAnalysisReady,  
      datediff(dd, c.AssignmentCreationDate, c.AssignmentCompleteDate) AS DaysToCompleteFromCreation,  
      CASE  WHEN ENC.ADMITTED IS NULL THEN 0
		  ELSE
  			(CASE 
  				WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
				ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
			END)
	  END as LengthOfStay ,
			LOS_1 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 0 AND 1 THEN 1 ELSE 0 END),
			LOS_2 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) = 2 THEN 1 ELSE 0 END),
			LOS_3 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) = 3 THEN 1 ELSE 0 END),
			LOS_4_7 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 4 AND 7 THEN 1 ELSE 0 END),
			LOS_8_14 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 8 AND 14 THEN 1 ELSE 0 END),
			LOS_15_21 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 15 AND 21 THEN 1 ELSE 0 END),
			LOS_22_28 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 22 AND 28 THEN 1 ELSE 0 END),
			LOS_29_60 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 29 AND 60 THEN 1 ELSE 0 END),
			LOS_61_90 = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) BETWEEN 61 AND 90 THEN 1 ELSE 0 END),
			LOS_91_More = (CASE WHEN (datediff(dd, ENC.admitted, ENC.Discharged)) > 90 THEN 1 ELSE 0 END),
			LOS_Undefined = (CASE WHEN  ENC.admitted IS NULL THEN 1 ELSE 0 END),
		his.dbo.fnHBIDateTime((select top 1 EncounterEventDate from cabinet.dbo.encounterauditevent with (NOLOCK) WHERE encounter=ENC.ENCOUNTER AND facility=ENC.facility AND encountereventname='DocumentationSatisfied')) as DocumentationSatisfiedDateText
INTO ##HBI_SUBSET_DAILY_ANALYSISDAYSPRODUCTIVITY
FROM ##HBI_D_ANALYSISDAYS_YESTERDAY_ASSIGNMENTS_2 c WITH (NOLOCK)
join cabinet.dbo.USERS u WITH (NOLOCK) on 
	c.UserInstanceId = u.UserInstanceId
JOIN his.dbo.ENCOUNTERS ENC WITH (NOLOCK) ON
      ENC.ENCOUNTER = c.ENCOUNTER
      AND ENC.FACILITY = c.FACILITY
JOIN his.dbo.PATIENTS p WITH (NOLOCK) ON
      p.FACILITY =ENC.FACILITY
      AND p.MRN = ENC.MRN
JOIN cabinet.dbo.FACILITY_FILE F WITH (NOLOCK) ON 
      c.FACILITY = F.FACILITY_CODE  
WHERE
      ENC.DISCHARGED is not null      

SET  @sqlcmd = ' bcp ##HBI_SUBSET_DAILY_ANALYSISDAYSPRODUCTIVITY out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_AnalysisDaysProductivity.txt -c -T -S' + @@servername

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_DAILY_ANALYSISDAYSPRODUCTIVITY was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_AnalysisDaysProductivity.txt'
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

Grant exec on [dbo].[MPF_Daily_AnalysisDaysProductivity] to [IMNET]
go