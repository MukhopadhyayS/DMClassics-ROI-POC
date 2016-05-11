USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_ReviewDaysBacklog]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_ReviewDaysBacklog]
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
*	Procedure Name: [MPF_DAILY_ReviewDaysBacklog]
*	Description:	UC5927 - View Daily Review Days Backlog Measure
*					Fetches the number of days encounter is waiting in 'Review'.
*	
*	NOTES: 
*   ===========================================================================================================================================================================================================				
*	COLUMN(s)								DATABASE	TABLE					DESCRIPTION							NAME USED IN SCRIPT						COMMENTS
*	===========================================================================================================================================================================================================
* 	Facility_Name							CABINET		Facility_File			Facility Name						FacilityName
*	Queue									CABINET		AssignmentAuditEvent	AssignmentAuditEvent				WorkflowQueueName
*	AssignmentEventDate						CABINET		AssignmentAuditEvent	assigned Date						AssignDate
*	AssignmentEventDate						CABINET		AssignmentAuditEvent	assigned Date						AssignDateText						Conversion to mm\dd\yyyy format
*	encounter								HIS	 		Encounters 				Encounter Number					Encounter
*	AssignmentEventDate						CABINET		AssignmentAuditEvent	Days in Review						DaysInReview						Formula: (@yesterday - AssignmentEventDate)
*	NAME									CABINET		USERS					User name 							AssigningUser
*	Reason									CABINET		AssignmentAuditEvent	Reason 								AssigningRemarks
*	PTTYPE_NAME								CABINET		PT_Type_File			Patient Type Name					PatientTypeName																											
*	DISCHARGED								HIS			Encounters				Discharged Date						DischargeDate
*	Name									HIS			Patient					Patient Name						PatientName				
* 	MRN										HIS			Encounters				MRN									MRN
*	Charges									HIS			Encounters				Charges per encounter				Charges
*	UNIT_NAME								HIS			UNIT_FILE				Med unit name						MedicalUnitName
*	Name									HIS			PHYSICIANS				Attending Physician					AttendingPhysician
*	ADMITTED,DISCHARGED						HIS			Encounters				Length of stay						LengthOfStay						Formula :DATEDIFF(DAY, ADMITTED, DISCHARGED)
*	===========================================================================================================================================================================================================
*
*	Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	MD Meersma			08/15/2012		Created
*	Joseph Chen			09/20/2012		Modified for HPFA 16.0 DNFC
*	Iryna Politykina	10/10/2012		Adapted for HPFA 16.0 UC5927
*	MD Meersma			11/30/2012		Code Review
*	Latonia Howard		01/02/2013		Validation fix: exclude assignments that were created and completed yesterday
*   Iryna Politykina    02/28/2013		Add a condition if admitted date is NULL
*   Iryna Politykina	03/15/2013		set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*   Iryna Politykina	04/10/2013		Fix per CR # 379165 :MPFA -Daily Backlog highlights (analysis, coding, and review) show today's data
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	MD Meersma				10/08/2013	Change localhost to use @@servername
*************************************************************************************************************************************/

CREATE PROCEDURE [dbo].MPF_Daily_ReviewDaysBacklog
@HBI_TargetDir varchar(4000)  -- file_path column from WebData.dbo.wt_definition_info (HBI Server)
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255)

-- show advanced options enable if disabled
if exists(select value_in_use from master.sys.configurations where name = 'show advanced options' and value_in_use = 0)
begin
	exec sp_configure 'show advanced options', 1
	reconfigure with override
end

-- xp_cmdshell enable if disabled
select @xp_cmdshell = 1
if exists(select value_in_use from master.sys.configurations where name = 'xp_cmdshell' and value_in_use = 0)
begin
	select @xp_cmdshell = 0
	exec sp_configure 'xp_cmdshell', 1
	reconfigure with override
end

--SET @ABS_Target_Dir = (select top 1 HPF2HBI_Parm_Value from HPF2HBI_Parameters where HPF2HBI_Parm_Name = 'ABS_Target_Dir'and HPF2HBI_Parm_Facility = 'E_P_R_S')

-- Delete all the previous TXT files incase we do not generate all of them

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_ReviewDaysBacklog.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##MPF_DAILY_RDB_SUBSET') is not null
DROP TABLE ##MPF_DAILY_RDB_SUBSET

-- Notes that affect the resultset
-- PHYSICIANS can have multiple attending on FOLDER, FACILITY, TYPE = 'ATTENDING'
-- LOCATOR can have multiple rows on FOLDER, FACILITY, UNIT
-- Encounter status is Unavailable

SELECT 
COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF(enc.FACILITY, ''), 'NO FACILITY')) as FacilityName,
assign.UserID as WorkflowQueueName ,
assign.ASSIGNED as AssignDate,
CONVERT(VARCHAR(10),assign.ASSIGNED, 101) AS AssignDateText,
enc.ENCOUNTER as Encounter,
DATEDIFF(day, assign.ASSIGNED, DATEADD(day,datediff(day,1,getdate()),0)) as DaysInReview,
COALESCE((SELECT NAME from cabinet.dbo.USERS  WITH (NOLOCK) WHERE  UserInstanceId = assign.UserInstanceId),  'Undefined') as AssigningUser,
COALESCE(assign.PURPOSE, 'Undefined') as AssigningRemarks,
COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=enc.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=enc.FACILITY)), ''), 'Undefined') AS PatientTypeName,
his.dbo.fnHBIDateTime(ENC.DISCHARGED) AS DischargedDateText,
(SELECT NAME FROM PATIENTS WITH (NOLOCK) WHERE MRN = ENC.MRN AND facility = ENC.FACILITY) AS PatientName,
enc.MRN as MRN,
COALESCE(enc.TOTAL_CHARGES, 0.0) as Charges,
COALESCE((SELECT TOP 1 UNIT_NAME FROM UNIT_FILE with (NOLOCK) WHERE UNIT_CODE = (SELECT top 1 UNIT from LOCATOR WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY ORDER BY occurred DESC ) AND SET_ID = facilityFile.UNIT_SET), 'Undefined') AS MedicalUnitName, 
COALESCE((select top 1 name FROM PHYSICIANS WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY and TYPE = 'Attending' ), 'Undefined') as AttendingPhysician, 
LengthOfStay = CASE  WHEN ENC.ADMITTED IS NULL THEN 0
					ELSE
  						(CASE 
  							WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
							ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
						 END)
			   END 
			   
INTO ##MPF_DAILY_RDB_SUBSET
FROM ENCOUNTERS ENC with (NOLOCK)
JOIN (SELECT DISTINCT def.UserInstanceId, UserID, ASSIGNED,PURPOSE, ENCOUNTER, FACILITY from cabinet.dbo.Assignments assignments WITH (NOLOCK)
JOIN cabinet.dbo.WQ_DEFINITION def WITH (NOLOCK) ON def.QUEUE = assignments.USERID 
JOIN cabinet.dbo.QueueTypeLov qtlov WITH (NOLOCK) ON qtlov.QueueTypeLovID = def.QueueTypeLovID WHERE QueueTypeName='Review' AND assignments.ASSIGNED < DATEADD(day,datediff(day,0,getdate()),0)) as assign ON ENC.ENCOUNTER = assign.ENCOUNTER AND ENC.FACILITY = assign.FACILITY 
JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON FACILITY_CODE =  enc.FACILITY 
WHERE ENC.DISCHARGED  IS NOT NULL

UNION 
 
SELECT DISTINCT
COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF( c.FACILITY , ''), 'NO FACILITY')) as FacilityName,
C.Queue as WorkflowQueueName ,
(Select top 1  dd.AssignmentEventDate FROM cabinet.dbo.AssignmentAuditEvent dd WITH (NOLOCK) WHERE  dd.ASSIGNMENT_ID = c.ASSIGNMENT_ID AND (dd.WorkflowEventName = 'Create' or  dd.WorkflowEventName= 'Reassign To' or dd.WorkflowEventName= 'ReassignTo')) as AssignDate,
CONVERT(VARCHAR(10),(Select top 1  dd.AssignmentEventDate FROM cabinet.dbo.AssignmentAuditEvent dd WITH (NOLOCK) WHERE  dd.ASSIGNMENT_ID = c.ASSIGNMENT_ID AND (dd.WorkflowEventName = 'Create' or  dd.WorkflowEventName= 'Reassign To' or dd.WorkflowEventName= 'ReassignTo')), 101 )AS AssignDateText,
enc.ENCOUNTER as Encounter,
DATEDIFF(day,  (Select top 1  dd.AssignmentEventDate FROM cabinet.dbo.AssignmentAuditEvent dd WITH (NOLOCK) WHERE  dd.ASSIGNMENT_ID = c.ASSIGNMENT_ID AND (dd.WorkflowEventName = 'Create' or  dd.WorkflowEventName= 'Reassign To' or dd.WorkflowEventName= 'ReassignTo')), DATEADD(day,datediff(day,1,getdate()),0)) as DaysInReview ,
COALESCE((SELECT NAME from cabinet.dbo.USERS  WITH (NOLOCK) WHERE  UserInstanceId = c.UserInstanceId), 'Undefined') as AssigningUser,
COALESCE(c.Reason, 'Undefined') as AssigningRemarks,
COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=enc.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=enc.FACILITY)), ''), 'Undefined') AS PatientTypeName,
his.dbo.fnHBIDateTime(ENC.DISCHARGED) AS DischargedDateText,
(SELECT NAME FROM PATIENTS WITH (NOLOCK) WHERE MRN = ENC.MRN AND facility = ENC.FACILITY) AS PatientName,
enc.MRN as MRN,
COALESCE(enc.TOTAL_CHARGES, 0.0)  as Charges,
COALESCE((SELECT TOP 1 UNIT_NAME FROM UNIT_FILE with (NOLOCK) WHERE UNIT_CODE = (SELECT top 1 UNIT from LOCATOR WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY ORDER BY occurred DESC ) AND SET_ID = facilityFile.UNIT_SET), 'Undefined') AS MedicalUnitName, 
COALESCE((select top 1 name FROM PHYSICIANS WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY and TYPE = 'Attending' ), 'Undefined') as AttendingPhysician, 
LengthOfStay = CASE  WHEN ENC.ADMITTED IS NULL THEN 0
					ELSE
  						(CASE 
  							WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
							ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
						 END)
			   END 
FROM ENCOUNTERS ENC with (NOLOCK)
JOIN cabinet.dbo.AssignmentAuditEvent c with (NOLOCK) ON ENC.ENCOUNTER = c.ENCOUNTER AND c.FACILITY = ENC.FACILITY
JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON FACILITY_CODE =  c.FACILITY 
WHERE  c.assignmentEventDate >= DATEADD(day,datediff(day,0,getdate()),0)
AND c.QueueTypeName='Review' AND (c.WorkflowEventName='Complete' OR c.WorkflowEventName='Delete')
AND ENC.DISCHARGED  IS NOT NULL
AND c.assignment_id NOT IN (SELECT assignment_id FROM cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK) 
								WHERE assignmentEventDate >= DATEADD(day,datediff(day,0,getdate()),0)
								AND QueueTypeName='Review'  AND (WorkflowEventName='Create' OR WorkflowEventName='Reassign To' OR WorkflowEventName='ReassignTo')) -- excludes assignments that were created and completed yesterday


SET  @sqlcmd = ' bcp ##MPF_DAILY_RDB_SUBSET out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_ReviewDaysBacklog.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--select @Success

IF @Success = 1
	RAISERROR ('bcp of ##MPF_DAILY_RDB_SUBSET was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_ReviewDaysBacklog.txt'
	EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT
END CATCH
GO

GRANT EXECUTE ON [dbo].[MPF_Daily_ReviewDaysBacklog] TO [IMNET] AS [dbo]
GO