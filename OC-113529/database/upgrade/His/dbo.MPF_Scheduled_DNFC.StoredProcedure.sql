USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Scheduled_DNFC]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Scheduled_DNFC]
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
* Procedure Name: [HPF2HBI_SUBSET_DNFC]
* Description:   
*
* NOTES: 
* ============================================================================================================
*	COLUMN						DATABASE	TABLE				DESCRIPTION					NAME USED IN SCRIPT
*	============================================================================================================
*	encounter					HIS	 		Encounters 			Encounter Id					encounter			
* 	Facility_Name				CABINET		Facility_File		Facility Name					facilityName
* 	MRN							HIS			Encounters			MRN								MRN
* 	Charges						HIS			Encounters			Charges							Charges
*	DISCHARGED					HIS			Encounters			Discharged Date	Text			dischargedDate_Text
*	PTTYPE_NAME					CABINET		PT_Type_File		Patient Type Name				PatientTypeName
*	name						HIS			Physicians			Attending Physician Name		AttendingPHYSICIAN
* 	UNIT_NAME					HIS			UNIT_FILE			Med Unit Name					MedUnitName
*	Patient Name				HIS			Patient				Patient Name					patientName	
*	DNFCDays					HIS			Encounter			E.Discharged - Today's date		DNFC days					
*	CALCULATED					HIS			Encounters			DISCHARGED - ADMITTED			LengthOfStay
*	============================================================================================================
*  Revision History:
*	Name				Date		Changes
*	---------			-------		-------------
* 	MD Meersma			08/15/2012	Created
*	Joseph Chen			09/20/2012  Modified for HPFA 16.0 DNFC
*	Joseph Chen			12/12/2012	Change parameter name from HPFA to MPFA
*   I.Politykina		02/28/2013  Add a condition if admitted date is NULL, removed admitted date column
*   Iryna Politykina	03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	Iryna Politykina	04/25/2013	Fix per CR# 379172
*	Latonia Howard		06/14/2013	Fix per CR# 381,393 - restrict DNFC data to encounters discharged with the number of days defined by the MPFA.DNFC.InclusionDays parameter 
*	MD Meersma				10/08/2013	Change localhost to use @@servername
*************************************************************************************************************************************/

CREATE PROCEDURE [dbo].[MPF_Scheduled_DNFC]
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


-- Delete all the previous TXT files incase we do not generate all of them

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_DNFC.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_DNFC') is not null
DROP TABLE ##HBI_SUBSET_DNFC

-- Notes that affect the resultset
-- PHYSICIANS can have multiple attending on FOLDER, FACILITY, TYPE = 'ATTENDING'
-- LOCATOR can have multiple rows on FOLDER, FACILITY, UNIT

CREATE TABLE ##HBI_SUBSET_DNFC (
Encounter char(20),
PatientName char(40),
Facility varchar(10),
FacilityName char(25),
MRN char(20),
PatientTypeName char(25),
DischargedDate smalldatetime,
DischargedDateText varchar(20),
AttendingPhysician char(30),
MedicalUnitName char(25),
EncounterService char(25),
PhysicanService varchar(25),
Charges decimal(9,2),
WorkflowState char(50),
WorkFlowQueue char(50),
WorkflowReason char(260),
DNFCDays int,
LengthOfStay int
)

INSERT INTO ##HBI_SUBSET_DNFC 
SELECT 
E.ENCOUNTER as Encounter, 
P.NAME as PatientName,
E.FACILITY as Facility,
COALESCE(NULLIF((F.FACILITY_NAME), ''), COALESCE(NULLIF(E.FACILITY, ''), 'NO FACILITY')) as FacilityName,
E.MRN, 
COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=E.PT_TYPE AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=E.FACILITY)), ''), 'Undefined') AS PatientTypeName,
E.DISCHARGED as DischargedDate, 
his.dbo.fnHBIDateTime(E.DISCHARGED) as DischargedDateText,
COALESCE((SELECT TOP 1 name FROM his.dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER=E.ENCOUNTER and FACILITY=E.FACILITY and TYPE = 'Attending' ORDER BY NAME), 'Undefined') as AttendingPhysician, 
COALESCE((SELECT TOP 1 unit_name FROM his.dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE = (SELECT TOP 1 UNIT from his.dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=E.ENCOUNTER and FACILITY=E.FACILITY ORDER BY occurred DESC) AND SET_ID = F.UNIT_SET), 'Undefined') AS MedicalUnitName,
COALESCE(NULLIF(
         (SELECT TOP 1 SERVICE_NAME FROM cabinet.dbo.SERVICE_FILE WHERE SERVICE_CODE=e.service AND SERVICE_ACTIVE='Y' AND SET_ID=(SELECT TOP 1 SERVICE_SET from cabinet.dbo.FACILITY_FILE WHERE FACILITY_CODE=e.FACILITY)  
         ),''),'Undefined') as EncounterService,
COALESCE(NULLIF(
         (SELECT TOP 1 service FROM cabinet.dbo.user_facility WITH (NOLOCK) WHERE
          physician_code = (SELECT TOP 1 code FROM physicians WITH (NOLOCK) WHERE  folder = e.encounter AND facility = e.facility AND type = 'Attending' ORDER BY name) 
          AND facility = e.facility),''), 'Undefined'
          ) AS PhysicanService,
COALESCE(E.TOTAL_CHARGES, 0) as Charges,
'Pending Coding' as WorkflowState,
'Unassigned' as WorkFlowQueue,
'' as WorkflowReason,
datediff(dd,E.Discharged,getdate()) AS DNFCDays,
LengthOfStay = CASE
                   WHEN E.ADMITTED IS NULL THEN 0
                   ELSE
                      (CASE 
                             WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
                             ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
                      END)
               END
FROM his.dbo.ENCOUNTERS E with (NOLOCK)
JOIN cabinet.dbo.FACILITY_FILE F WITH (NOLOCK) ON E.FACILITY = F.FACILITY_CODE 
JOIN his.dbo.PATIENTS P WITH (NOLOCK) ON E.FACILITY=P.FACILITY and E.MRN=P.MRN
LEFT OUTER join cabinet.dbo.PTTYPE_FILE PT WITH (NOLOCK) ON E.PT_TYPE = PT.PTTYPE_CODE and PT.SET_ID = F.PTTYPE_SET and PT.PTTYPE_ACTIVE = 'Y' 
WHERE 
E.DISCHARGED is NOT NULL 
and E.DISCHARGED >= (DATEADD(Day, -1 * (SELECT MPF2HBI_Parm_VALUE FROM his.dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DNFC.InclusionDays') , DATEDIFF(Day, 0, GetDate())) )
and E.AbstractComplete is null


UPDATE ##HBI_SUBSET_DNFC  SET WorkflowState = MostRecentQueue.QueueTypeName, WorkFlowQueue = MostRecentQueue.USERID
,WorkflowReason = MostRecentQueue.PURPOSE FROM
( SELECT ENCOUNTER, FACILITY, QueueTypeName, USERID,PURPOSE from cabinet.dbo.ASSIGNMENTS a WITH (NOLOCK)
      JOIN cabinet.dbo.WQ_DEFINITION WITH (NOLOCK) ON a.USERID=cabinet.dbo.WQ_DEFINITION.QUEUE 
      JOIN cabinet.dbo.QueueTypeLov WITH (NOLOCK) ON cabinet.dbo.QueueTypeLov.QueueTypeLovID=cabinet.dbo.WQ_DEFINITION.QueueTypeLovID
      WHERE cabinet.dbo.QueueTypeLov.QueueTypeName IN (SELECT DISTINCT MPF2HBI_Parm_VALUE FROM his.dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DNFC.WorkflowState')
      and ASSIGNED = (SELECT MAX(assigned) 
      FROM  cabinet.dbo.ASSIGNMENTS a1 WITH (NOLOCK)
      JOIN cabinet.dbo.WQ_DEFINITION wq1 WITH (NOLOCK) ON a1.USERID=wq1.QUEUE 
      JOIN cabinet.dbo.QueueTypeLov qtv1 WITH (NOLOCK) ON qtv1.QueueTypeLovID=wq1.QueueTypeLovID
      WHERE a1.ENCOUNTER = a.encounter and a1.FACILITY = a.facility AND 
      qtv1.QueueTypeName IN (SELECT DISTINCT MPF2HBI_Parm_VALUE FROM his.dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DNFC.WorkflowState')
         )
      GROUP BY ENCOUNTER, FACILITY,QueueTypeName, USERID,PURPOSE
) AS MostRecentQueue 
WHERE MostRecentQueue.ENCOUNTER = ##HBI_SUBSET_DNFC.ENCOUNTER and MostRecentQueue.FACILITY = ##HBI_SUBSET_DNFC.Facility

UPDATE ##HBI_SUBSET_DNFC SET WorkflowState='Pending Abstract Complete' FROM
(SELECT ae.ENCOUNTER, ae.FACILITY FROM cabinet.dbo.EncounterAuditEvent ae
 JOIN ##HBI_SUBSET_DNFC tmpDNFC ON ae.ENCOUNTER=tmpDNFC.Encounter AND ae.FACILITY = tmpDNFC.Facility 
 WHERE EncounterEventName='Complete' AND QueueTypeName='Coding' AND tmpDNFC.WorkflowState = 'Pending Coding'
) as HaveCoding WHERE  HaveCoding.ENCOUNTER  = ##HBI_SUBSET_DNFC.ENCOUNTER and HaveCoding.FACILITY = ##HBI_SUBSET_DNFC.Facility


SET  @sqlcmd = ' bcp "SELECT Encounter, PatientName, FacilityName, MRN, PatientTypeName, '+
				'DischargedDate, DischargedDateText, AttendingPhysician, MedicalUnitName, '+
				'EncounterService, PhysicanService, Charges, WorkflowState, WorkFlowQueue, '+
				'WorkflowReason, DNFCDays, LengthOfStay  FROM ##HBI_SUBSET_DNFC" queryout ' + rtrim(@HBI_TargetDir) + '\MPF_Scheduled_DNFC.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--select @Success

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_DNFC was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_DNFC.txt'
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

GRANT exec on [dbo].[MPF_Scheduled_DNFC] to [IMNET]
go