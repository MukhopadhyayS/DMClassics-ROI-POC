USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_TurnAroundTime]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_TurnAroundTime]
GO

/****************************************************************
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
*	Procedure Name: [MPF_DAILY_TURNAROUNDTIME]
*	Description:	UC5811 - View Daily Availability Turn Around Time Measure
*					Fetches the number of days encounter is waiting in 'Review'.
*	
*	NOTES: 
*   ===========================================================================================================================================================================================================				
*	COLUMN(s)								DATABASE	TABLE					DESCRIPTION							NAME USED IN SCRIPT						COMMENTS
*	===========================================================================================================================================================================================================
* 	FacilityName							CABINET		Facility_File			Facility Name						Facility_Name
*	QueueName 								CABINET		AssignmentAuditEvent	Queue								WorkflowQueue 
*	DocumentationSatisfiedDate				CABINET		AssignmentAuditEvent	EncounterEventDate					DocumentationSatisfiedDate
*	Encounter								HIS	 		Encounters 				Encounter Number					Encounter_Number
*	DischargedDate							HIS			Encounters				Dischagred							Discharged Date
*	DischargedDateText						HIS			Encounters				Discharged Date Formate				Conversion to mm\dd\yyyy format
*	DaysTurnAround							HIS			Encounters				Turn Around Time in days			Formula: DATEDIFF(DAY, ENC.DISCHARGED,EA.EncounterEventDate),
*	HoursTurnAround							HIS			Encounters				Turn Around Time in hours			Formula: DATEDIFF(HOUR, ENC.DISCHARGED,EA.EncounterEventDate)
*	AssigningUser 							CABINET		Users					Asssigment User						Assigment user name
*	PatientTypeName							CABINET		PTTYPE_FILE				Patient Type Name					Patient Type Name																											
*	PatientName								HIS			Patient					Patient Name						patientName				
* 	MRN										HIS			Encounters				MRN									MRN
*	Charges									HIS			Encounters				Charges per encounter				Charges
*	MedicalUNITNAME							HIS			UNIT_FILE				Med unit name						MedUnitName
*	AttendingPhysician						HIS			PHYSICIANS				Attending Physician					AttendingPHYSICIAN
*	Specialty								CABINET		User_Facility			Physician Specialty					Physician Specialty
*	LengthOfStay							HIS			Encounter				Length of stay						LOS				Formula :DATEDIFF(DAY, ADMITTED, DISCHARGED)
*	
*	===========================================================================================================================================================================================================
*
*	Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 
*	Joseph Chen				10/23/2012	Created for HPFA 16.0 Daily Turn Around time
*	MD Meersma				11/30/2012	Code Review
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL in LOS calculations and removed admitted date column.
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty.
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	MD Meersma				10/08/2013	Change localhost to use @@servername
*************************************************************************************************************************************/

CREATE PROCEDURE [dbo].[MPF_Daily_TurnAroundTime]
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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_TurnAroundTime.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##MPF_DAILY_TURNAROUND_SUBSET') is not null
DROP TABLE ##MPF_DAILY_TURNAROUND_SUBSET

-- Notes that affect the resultset
-- PHYSICIANS can have multiple attending on FOLDER, FACILITY, TYPE = 'ATTENDING'
-- LOCATOR can have multiple rows on FOLDER, FACILITY, UNIT
-- Encounter status is Unavailable

SELECT distinct

COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF(EA.FACILITY, ''), 'NO FACILITY')) as FacilityName,
(SELECT top 1 Queue from cabinet.dbo.EncounterAuditEvent CEV WITH (NOLOCK) where CEV.ENCOUNTER=ENC.ENCOUNTER and CEV.FACILITY=ENC.FACILITY and CEV.EncounterEventName='DocumentationSatisfied' ORDER BY CEV.EncounterEventDate DESC) as QueueName,
EA.EncounterEventDate as DocumentationSatisfiedDate,
his.dbo.fnHBIDateTime(EA.EncounterEventDate) as DocumentationSatisfiedDateText,
ENC.ENCOUNTER as Encounter,
ENC.DISCHARGED as DischargedDate, 
his.dbo.fnHBIDateTime(ENC.DISCHARGED) as DischargedDateText,
DATEDIFF(DAY, ENC.DISCHARGED,EA.EncounterEventDate) as DaysTurnAround,
DATEDIFF(HOUR, ENC.DISCHARGED,EA.EncounterEventDate) as HoursTurnAround,
(SELECT NAME from cabinet.dbo.USERS with (nolock) WHERE  UserInstanceId = EA.UserInstanceId) as AssigningUser,
COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=ENC.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=ENC.FACILITY)), ''), 'Undefined') AS PatientTypeName,
(SELECT NAME from his.dbo.PATIENTS with (nolock) WHERE MRN = ENC.MRN AND facility = ENC.FACILITY) AS PatientName,
enc.MRN as MRN,
COALESCE(enc.TOTAL_CHARGES, 0.0)  as Charges,
COALESCE((select top 1 UNIT_NAME from his.dbo.UNIT_FILE with (nolock) where UNIT_CODE = (select top 1 UNIT from his.dbo.LOCATOR with (nolock) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY order by occurred desc) AND SET_ID=facilityFile.UNIT_SET), 'Undefined') as MedicalUnitName,
COALESCE((select top 1 name from PHYSICIANS with (nolock) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY and TYPE = 'Attending' ), 'Undefined') as AttendingPhysician, 
(select top 1 SPECIALTY=case 
when [SPECIALTY]='' then 'Undefined'
when [SPECIALTY] IS NULL then 'Undefined' else [SPECIALTY] end from cabinet.dbo.User_Facility uf with (nolock) where uf.PHYSICIAN_CODE=phy.CODE and uf.FACILITY=ENC.FACILITY ) as Specialty, 
LengthOfStay = CASE  WHEN ENC.ADMITTED IS NULL THEN 0
					 ELSE
  						(CASE 
  							WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
							ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
					     END)
				END 
INTO ##MPF_DAILY_TURNAROUND_SUBSET
FROM ENCOUNTERS ENC WITH (NOLOCK)
JOIN cabinet.dbo.EncounterAuditEvent EA WITH (NOLOCK) ON ENC.ENCOUNTER = EA.ENCOUNTER AND EA.FACILITY = ENC.FACILITY
JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON facilityFile.FACILITY_CODE =EA.FACILITY
JOIN PHYSICIANS phy WITH (NOLOCK) on phy.FOLDER=EA.ENCOUNTER and phy.FACILITY=EA.FACILITY
WHERE
EA.EncounterEventName='DocumentationSatisfied'
AND EA.EncounterEventDate >= DATEADD(day,datediff(day,1,getdate()),0) and EA.EncounterEventDate < DATEADD(day,datediff(day,0,getdate()),0)
AND ENC.DISCHARGED  IS NOT NULL
ORDER BY FacilityName, PatientTypeName, MedicalUnitName


SET  @sqlcmd = ' bcp ##MPF_DAILY_TURNAROUND_SUBSET out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_TurnAroundTime.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--select @Success

IF @Success = 1
	RAISERROR ('bcp of ##MPF_DAILY_TURNAROUND_SUBSET was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_TurnAroundTime.txt'
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

Grant exec on [dbo].[MPF_Daily_TurnAroundTime] to [imnet]
go