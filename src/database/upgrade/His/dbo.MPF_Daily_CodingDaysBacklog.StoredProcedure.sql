USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_CodingDaysBacklog]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_CodingDaysBacklog]
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
* Procedure Name: [MPF_Daily_CodingDaysBacklog]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Latonia Howard			10/17/2012	Created
*	MD Meersma				11/30/2012	Code Review
*	Latonia Howard			01/02/2013	Validation fix: exclude assignments that were created and completed yesterday
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL,
*										add LOS_Undefined column	
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*   Iryna Politykina		04/08/2013  Fix per Cr 379168 : Daily Backlog coding days LOS incorrect
*   Iryna Politykina		04/10/2013  Fix per CR # 379165 :MPFA -Daily Backlog highlights (analysis, coding, and review) show today's data
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5813 - View Daily Coding Days Backlog Measure

	backlog of encounters waiting for Coding
	
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
	UNIT_CODE					HIS			UNIT_FILE				Unit Code							MedicalUnitCode
	NAME						HIS			PHYSICIANS				Attending Physician					AttendingPhysician
	CALCULATED FIELD			HIS			ENCOUNTERS				IF DischargedDate=AdmitDate Then 1 	LengthOfStay
																	ELSE 
																	(Discharged Date - Admit Date) + 1
	DISCHARGED					HIS			Encounters				(Formatted: mm/dd/yyyy)				DischargedDateShortText																	
	CALCULATED LOS_x ...		HIS			Encounters				LOS Days Bucket for 1, 2, 3 4-7,	LOS_1, LOS_2, LOS_3,LOS_4_7, 
																	8-14, 15-21, 22-28, 29-60, 61-90,	LOS_8_14,LOS_15_21, LOS_22_28,	
																	91 days and more					LOS_29_60, LOS_61_90,LOS_91_Above
	
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Daily_CodingDaysBacklog]
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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_CodingDaysBacklog.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_DAILY_CODINGDAYSBACKLOG') IS NOT NULL
DROP TABLE ##HBI_SUBSET_DAILY_CODINGDAYSBACKLOG

SELECT 
	COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF(E.FACILITY, ''), 'NO FACILITY')) as FacilityName,
	e.MRN, 
	e.ENCOUNTER as Encounter, 
	e.TOTAL_CHARGES as Charges, 
	e.DISCHARGED AS DischargedDate, 
	his.dbo.fnHBIDateTime(e.DISCHARGED) AS DischargedDateText,
	DATEDIFF(dd, e.Discharged,  DATEADD(day,datediff(day,1,getdate()),0)) AS DaysSinceDischarged,
	assign.USERID as WorkflowQueueName,
	patients.NAME AS PatientName,
	COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=e.PT_TYPE AND SET_ID=facilityFile.PTTYPE_SET AND PTTYPE_ACTIVE = 'Y'), ''), 'Undefined') AS PatientTypeName,
	COALESCE((SELECT TOP 1 UNIT_NAME FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
	(SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER and FACILITY=e.FACILITY ORDER BY occurred DESC )
	 AND SET_ID=facilityFile.UNIT_SET), 'Undefined') AS MedicalUnitName, 
	COALESCE((SELECT TOP 1 name FROM dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER AND FACILITY=e.FACILITY AND TYPE = 'Attending' ORDER BY NAME), 'Undefined') AS AttendingPhysician,		
	CASE 
	  WHEN E.ADMITTED IS NULL THEN 0
	  ELSE
		(CASE 
			WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
			ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
		END)
	END AS LengthOfStay,
	CONVERT(VARCHAR(10),e.Discharged, 101 ) as DischargedDateShortText,
	LOS_1 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 0 AND 1 THEN 1 ELSE 0 END),
	LOS_2 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 2 THEN 1 ELSE 0 END),
	LOS_3 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 3 THEN 1 ELSE 0 END),
	LOS_4_7 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 4 AND 7 THEN 1 ELSE 0 END),
	LOS_8_14 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 8 AND 14 THEN 1 ELSE 0 END),
	LOS_15_21 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 15 AND 21 THEN 1 ELSE 0 END),
	LOS_22_28 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 22 AND 28 THEN 1 ELSE 0 END),
	LOS_29_60 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 29 AND 60 THEN 1 ELSE 0 END),
	LOS_61_90 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 61 AND 90 THEN 1 ELSE 0 END),
	LOS_91_Above = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) > 90 THEN 1 ELSE 0 END),
	LOS_Undefined  = (CASE WHEN e.admitted IS NULL  THEN 1 ELSE 0 END)
	
INTO ##HBI_SUBSET_DAILY_CODINGDAYSBACKLOG
FROM ENCOUNTERS E with (NOLOCK)
JOIN (SELECT DISTINCT UserID, ENCOUNTER, FACILITY from cabinet.dbo.Assignments assignments WITH (NOLOCK)
			JOIN cabinet.dbo.WQ_DEFINITION def WITH (NOLOCK) ON def.QUEUE = assignments.USERID 
			JOIN cabinet.dbo.QueueTypeLov qtlov WITH (NOLOCK) ON qtlov.QueueTypeLovID = def.QueueTypeLovID WHERE QueueTypeName='Coding' AND assignments.ASSIGNED < DATEADD(day,datediff(day,0,getdate()),0))
as assign ON E.ENCOUNTER = assign.ENCOUNTER AND E.FACILITY = assign.FACILITY 
JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON FACILITY_CODE =  e.FACILITY 
JOIN PATIENTS patients WITH (NOLOCK) ON patients.MRN = E.MRN AND patients.facility = E.FACILITY
WHERE E.DISCHARGED  IS NOT NULL

UNION 

SELECT DISTINCT  
	COALESCE(NULLIF((F.FACILITY_NAME), ''), COALESCE(NULLIF(E.FACILITY, ''), 'NO FACILITY')) as FacilityName,
	e.MRN, 
	e.ENCOUNTER as Encounter, 
	e.TOTAL_CHARGES as Charges, 
	e.DISCHARGED AS DischargedDate, 
	his.dbo.fnHBIDateTime(e.DISCHARGED) AS DischargedDateText,
	DATEDIFF(dd,e.Discharged,DATEADD(day,datediff(day,1,getdate()),0)) AS DaysSinceDischarged,
	c.Queue as WorkflowQueueName,
	p.NAME AS PatientName,	
	COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=e.PT_TYPE AND SET_ID=F.PTTYPE_SET AND PTTYPE_ACTIVE = 'Y'), ''), 'Undefined') AS PatientTypeName,
	COALESCE((SELECT TOP 1 UNIT_NAME FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
	(SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER and FACILITY=e.FACILITY ORDER BY occurred DESC )
	 AND SET_ID=F.UNIT_SET), 'Undefined') AS MedicalUnitName, 
	COALESCE((SELECT TOP 1 name FROM dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER AND FACILITY=e.FACILITY AND TYPE = 'Attending' ORDER BY NAME), 'Undefined') AS AttendingPhysician,		
	CASE 
	  WHEN E.ADMITTED IS NULL THEN 0
	  ELSE
		(CASE 
			WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
			ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
		END)
	END AS LengthOfStay, 
	CONVERT(VARCHAR(10),e.Discharged, 101 ) as DischargedDateShortText,
	LOS_1 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 0 AND 1 THEN 1 ELSE 0 END),
	LOS_2 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 2 THEN 1 ELSE 0 END),
	LOS_3 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) = 3 THEN 1 ELSE 0 END),
	LOS_4_7 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 4 AND 7 THEN 1 ELSE 0 END),
	LOS_8_14 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 8 AND 14 THEN 1 ELSE 0 END),
	LOS_15_21 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 15 AND 21 THEN 1 ELSE 0 END),
	LOS_22_28 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 22 AND 28 THEN 1 ELSE 0 END),
	LOS_29_60 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 29 AND 60 THEN 1 ELSE 0 END),
	LOS_61_90 = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) BETWEEN 61 AND 90 THEN 1 ELSE 0 END),
	LOS_91_Above = (CASE WHEN (datediff(dd, e.admitted, e.Discharged)) > 90 THEN 1 ELSE 0 END),
	LOS_Undefined  = (CASE WHEN e.admitted IS NULL  THEN 1 ELSE 0 END)
	
	FROM cabinet.dbo.AssignmentAuditEvent c WITH (NOLOCK)
JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON
	e.ENCOUNTER = c.ENCOUNTER
	AND e.FACILITY = c.FACILITY
JOIN dbo.PATIENTS p WITH (NOLOCK) ON
	p.FACILITY = e.FACILITY
	AND p.MRN = e.MRN
JOIN cabinet.dbo.FACILITY_FILE F WITH (NOLOCK) ON 
	C.FACILITY = F.FACILITY_CODE  
WHERE c.assignmentEventDate >= DATEADD(day,datediff(day,0,getdate()),0)
	AND c.QueueTypeName='Coding' AND (c.WorkflowEventName='Complete' Or c.WorkflowEventName='Delete')
	AND e.DISCHARGED  IS NOT NULL
	AND c.assignment_id NOT IN (SELECT assignment_id FROM cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK) 
								WHERE assignmentEventDate >= DATEADD(day,datediff(day,0,getdate()),0) 
								AND QueueTypeName='Coding' AND (WorkflowEventName='Create' OR WorkflowEventName='Reassign To' OR WorkflowEventName='ReassignTo')) -- excludes assignments that were created and completed yesterday

SET  @sqlcmd = ' bcp ##HBI_SUBSET_DAILY_CODINGDAYSBACKLOG out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_CodingDaysBacklog.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_DAILY_CODINGDAYSBACKLOG was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_CodingDaysBacklog.txt'
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

	/*declare @mailid int, @recipents varchar(255), @Email_sysmail_profile varchar(256), @Email_sysmail_account varchar(256)

	if exists (SELECT 1 FROM [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName]='HPF2HBI_Parameters.Email_sysmail_profile' and [GlobalParentID] = (SELECT [GlobalID] FROM  [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName] = 'HPF2HBI_Parameters'))
	SELECT @Email_sysmail_profile=convert(varchar(255), [GlobalVariant])
	FROM [cabinet].[dbo].[SYSPARMS_GLOBAL]
	WHERE [GlobalName]='HPF2HBI_Parameters.Email_sysmail_profile' and [GlobalParentID] = (SELECT [GlobalID] FROM  [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName] = 'HPF2HBI_Parameters')

	if exists (SELECT 1 FROM [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName]='HPF2HBI_Parameters.Email_sysmail_account' and [GlobalParentID] = (SELECT [GlobalID] FROM  [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName] = 'HPF2HBI_Parameters'))
	SELECT @Email_sysmail_account=convert(varchar(255), [GlobalVariant])
	FROM [cabinet].[dbo].[SYSPARMS_GLOBAL]
	 WHERE [GlobalName]='HPF2HBI_Parameters.Email_sysmail_account' and [GlobalParentID] = (SELECT [GlobalID] FROM  [cabinet].[dbo].[SYSPARMS_GLOBAL] WHERE [GlobalName] = 'HPF2HBI_Parameters')

	SELECT top 1 @recipents = email_address FROM msdb.dbo.sysmail_account WHERE name=@Email_sysmail_account
	SELECT @ErrorMessage = 'Msg ' + convert(nvarchar(8), @ErrorNumber) + ' ' + @ErrorMessage

	if @recipents is not null and @recipents <> ''
	EXECUTE [msdb].[dbo].[sp_send_dbmail]
		@profile_name = @Email_sysmail_profile 
		,@recipients  = @recipents
		,@body        = @ErrorMessage
		,@subject     = @ErrorProcedure
		,@mailitem_id = @mailid OUTPUT */

END CATCH
GO

GRANT exec on [dbo].[MPF_Daily_CodingDaysBacklog] to [IMNET]
go