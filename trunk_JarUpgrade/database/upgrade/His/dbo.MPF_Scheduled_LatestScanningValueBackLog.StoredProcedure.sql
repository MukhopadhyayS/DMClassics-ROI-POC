USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Scheduled_LatestScanningValueBacklog]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Scheduled_LatestScanningValueBacklog]
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
*	Procedure Name: [MPF_SCHEDULED_LATEST_SCANNING_VALUE_BACKLOG]
*	Description: UC5812 - View Latest Scanning Value Backlog Measure
*			   Fetches all the discharged but not scanned yet encounters.
*	
*	NOTES: 
*   =======================================================================================================================================================================================
*	COLUMN(s)											DATABASE	TABLE				DESCRIPTION						NAME USED IN SCRIPT				COMMENTS
*	=======================================================================================================================================================================================
*	encounter											HIS	 		Encounters 			Encounter Id					Ecounter					
* 	Facility_Name										CABINET		Facility_File		Facility Name					FacilityName
* 	MRN													HIS			Encounters			MRN								MRN
* 	total_charges										HIS			Encounters			Charges							Charges
*	DISCHARGED											HIS			Encounters			Discharged Date					DischargedDate
*	DISCHARGED											HIS			Encounters			Discharged Date	Text			DischargedDateText				Conversion to mm\dd\yyyy format
*	PTTYPE_NAME											CABINET		PT_Type_File		Patient Type Name				PatientTypeName
*	name												HIS			Physicians			Attending Physician Name		AttendingPhysician
* 	UNIT_NAME											HIS			UNIT_FILE			Med Unit Name					MedicalUnitName
*	Name												HIS			Patient				Patient Name					PatientName		
*	CALCULATED											HIS			Encounters			Length Of stay					LOS								Formula: (DISCHARGED - ADMITTED)
*	========================================================================================================================================================================================
*
*	Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	MD Meersma				08/15/2012		Created
*	Joseph Chen				09/20/2012		Modified for HPFA 16.0 DNFC
*	Iryna Politykina		10/03/2012		Adapted for HPFA 16.0 UC5812
*   Iryna Politykina        02/28/2013		Add a condition if admitted date is NULL
*   Iryna Politykina		03/15/2013		set Facility Name to facilityCode , if facilityname is null or empty,set to 'NO FACILITY' if facilitycode is empty
*   Latonia Howard			04/18/2013		Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	Iryna Politykina		04/22/2013		add condition to check Encounters.abstractComplete is null. CR #379172 
*	MD Meersma				10/08/2013	Change localhost to use @@servername
*************************************************************************************************************************************/

CREATE PROCEDURE [dbo].MPF_Scheduled_LatestScanningValueBacklog
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

--SET  @sqlcmd = 'del ' + @ABS_Target_Dir + '\MPF_SCHEDULED_LATEST_SCANNING_VALUE_BACKLOG.txt'
SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_LatestScanningValueBacklog.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##MPF_SCHEDULED_LSVB_SUBSET') is not null
DROP TABLE ##MPF_SCHEDULED_LSVB_SUBSET

-- Notes that affect the resultset
-- PHYSICIANS can have multiple attending on FOLDER, FACILITY, TYPE = 'ATTENDING'
-- LOCATOR can have multiple rows on FOLDER, FACILITY, UNIT
-- Encounter status is Unavailable

SELECT 
DISTINCT ENC.ENCOUNTER as Encounter,
COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF(ENC.FACILITY, ''), 'NO FACILITY')) AS FacilityName, 
ENC.MRN,
COALESCE(enc.TOTAL_CHARGES, 0.0) AS Charges,
ENC.DISCHARGED AS DischargedDate,
(SELECT dbo.fnHBIDateTime(ENC.DISCHARGED)) AS DischargedDateText,
COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=ENC.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=ENC.FACILITY)), ''), 'Undefined') AS PatientTypeName,
COALESCE((select top 1 name FROM PHYSICIANS WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY and TYPE = 'Attending' ), 'Undefined') as AttendingPhysician, 
COALESCE((SELECT TOP 1 UNIT_NAME FROM UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
(SELECT top 1 UNIT from LOCATOR WITH (NOLOCK) where FOLDER=ENC.ENCOUNTER and FACILITY=ENC.FACILITY ORDER BY occurred DESC ) AND SET_ID = facilityFile.UNIT_SET
), 'Undefined') AS MedicalUnitName, 
(SELECT NAME FROM PATIENTS WITH (NOLOCK) WHERE MRN = ENC.MRN AND facility = ENC.FACILITY) AS PatientName,
LengthOfStay = CASE 
				  WHEN ENC.ADMITTED IS NULL THEN 0
				  ELSE
  					(CASE 
  						WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
						ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
					END)
			   END			   
INTO ##MPF_SCHEDULED_LSVB_SUBSET
FROM ENCOUNTERS ENC with (nolock)
JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON FACILITY_CODE =  ENC.FACILITY
JOIN (SELECT ENCOUNTER, FACILITY, QueueTypeName, USERID FROM cabinet.dbo.ASSIGNMENTS a WITH (NOLOCK)
      join cabinet.dbo.WQ_DEFINITION WITH (NOLOCK) ON a.USERID=cabinet.dbo.WQ_DEFINITION.QUEUE 
      join cabinet.dbo.QueueTypeLov WITH (NOLOCK) ON cabinet.dbo.QueueTypeLov.QueueTypeLovID=cabinet.dbo.WQ_DEFINITION.QueueTypeLovID
      where cabinet.dbo.QueueTypeLov.QueueTypeName='Unavailable'
      and ASSIGNED = (SELECT MAX(assigned) 
      from cabinet.dbo.ASSIGNMENTS a1 with (nolock)
      join cabinet.dbo.WQ_DEFINITION wq1 with (nolock) on a1.USERID=wq1.QUEUE 
      join cabinet.dbo.QueueTypeLov qtv1 with (nolock) on qtv1.QueueTypeLovID=wq1.QueueTypeLovID
      where a1.ENCOUNTER = a.encounter and a1.FACILITY = a.facility and 
      qtv1.QueueTypeName ='Unavailable')
      GROUP BY ENCOUNTER, FACILITY,QueueTypeName, USERID
) AS MostRecentQueue on MostRecentQueue.ENCOUNTER = ENC.ENCOUNTER and MostRecentQueue.FACILITY = ENC.FACILITY
WHERE ENC.DISCHARGED IS NOT NULL AND ENC.AbstractComplete is null 

SET  @sqlcmd = ' bcp ##MPF_SCHEDULED_LSVB_SUBSET out ' + rtrim(@HBI_TargetDir) + '\MPF_Scheduled_LatestScanningValueBacklog.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--select @Success

IF @Success = 1
	RAISERROR ('bcp of ##MPF_SCHEDULED_LSVB_SUBSET was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 

	--SET  @sqlcmd = 'del ' + @ABS_Target_Dir + '\MPF_Scheduled_LatestScanningValueBacklog.txt'
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_LatestScanningValueBacklog.txt'
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

	if exists (select 1 FROM [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName]='HPF2HBI_Parameters.Email_sysmail_profile' and [GlobalParentID] = (select [GlobalID] from  [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName] = 'HPF2HBI_Parameters'))
	select @Email_sysmail_profile=convert(varchar(255), [GlobalVariant])
	FROM [cabinet].[dbo].[SYSPARMS_GLOBAL]
	where [GlobalName]='HPF2HBI_Parameters.Email_sysmail_profile' and [GlobalParentID] = (select [GlobalID] from  [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName] = 'HPF2HBI_Parameters')

	if exists (select 1 FROM [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName]='HPF2HBI_Parameters.Email_sysmail_account' and [GlobalParentID] = (select [GlobalID] from  [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName] = 'HPF2HBI_Parameters'))
	select @Email_sysmail_account=convert(varchar(255), [GlobalVariant])
	FROM [cabinet].[dbo].[SYSPARMS_GLOBAL]
	 where [GlobalName]='HPF2HBI_Parameters.Email_sysmail_account' and [GlobalParentID] = (select [GlobalID] from  [cabinet].[dbo].[SYSPARMS_GLOBAL] where [GlobalName] = 'HPF2HBI_Parameters')

	select top 1 @recipents = email_address from msdb.dbo.sysmail_account where name=@Email_sysmail_account
	select @ErrorMessage = 'Msg ' + convert(nvarchar(8), @ErrorNumber) + ' ' + @ErrorMessage

	if @recipents is not null and @recipents <> ''
	EXECUTE [msdb].[dbo].[sp_send_dbmail]
		@profile_name = @Email_sysmail_profile 
		,@recipients  = @recipents
		,@body        = @ErrorMessage
		,@subject     = @ErrorProcedure
		,@mailitem_id = @mailid OUTPUT
*/		

END CATCH
GO

GRANT EXECUTE ON [dbo].[MPF_Scheduled_LatestScanningValueBacklog] TO [IMNET] AS [dbo]
GO
