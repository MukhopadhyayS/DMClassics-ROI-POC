USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Scheduled_LatestDeficiencyDelinquencyRate]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Scheduled_LatestDeficiencyDelinquencyRate]
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
* Procedure Name: [MPF_SCHEDULED_LatestDeficiencyDelinquencyRate]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Latonia Howard			10/09/2012	Created
*	MD Meersma				11/30/2012	Code Review
*   Latonia Howard			12/20/2012	Updated to address requirement change:  AMD should use previous 12 full calendar month.
*                                       if one full calendar month is not available, AMD is not calculated.  If 12 full calendar
*                                       months are not available AMD is calculated with the # of available full calendar months
*	Joseph Chen				01/08/2013  Updated to use DefType_File table instead of DEF_TYPE table because DEF_TYPE table is obsolete
*   Iryna Politykina        02/28/2013  Add a condition if admitted date is NULL and removed admitted date column 
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to 'NO FACILITY' if facilitycode is empty
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5814 - View Latest Deficiency Delinquency Rate Measure

	Retrieve delinquency rate (how many discharged encounters are currently delinquent, divided by the average number of monthly discharges) 
	for the patient types or patient type and service combinations
	
	Retrieve all encounters with deficiencies for configured facilities 
	
	===========================================================================================================================
	Columns						DATABASE	TABLE				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
 	FACILITY_NAME				CABINET		Facility_File		Facility Name						FacilityName
 	MRN							HIS			Encounters			MRN									MRN
	Encounter					HIS	 		Encounters 			Encounter Id						Encounter
  	TOTAL_CHARGES				HIS			Encounters			Charges								Total_Charges
 	ADMITTED					HIS			Encounters			Admitted Date (Formatted)			AdmittedDateText
 	DISCHARGED					HIS			Encounters			Discharged Date						DischargedDate
 	DISCHARGED					HIS			Encounters			Discharged Date (Formatted)			DischargedDateText
	NAME						HIS			PATIENTS			Patient Name						PatientName
 	PTTYPE_NAME					CABINET		PTTYPE_FILE			Patient Type						PatientType
	UNIT_NAME					HIS			UNIT_FILE			Unit Name							MedUnitName
	DefType_File_Name			CABINET		DefType_File		Deficiency Type						DeficiencyType
	Status_name					CABINET		STATUS				Deficiency State					DeficiencyState
	Tag							CABINET		DOCUMENT_NAMES		Document Type						DocumentType
 	FULLNAME 					CABINET		USERS				Physician Name						Physician
 	Service						CABINET		User_Facility		Physician Service					PhysicianService
 	Speciality					CABINET		User_Facility		Physician Speciality				PhysicianSpeciality
	NAME						HIS			PHYSICIANS			Attending Physician					AttendingPhysician
	CALCULATED FIELD			HIS			ENCOUNTERS			IF DischargedDate=AdmitDate Then 1 	LengthOfStay
																ELSE 
																(Discharged Date - Admit Date) + 1
 	CALCULATED FIELD											AMD (Average Monthly Discharges)	AverageMonthlyDischarges
																Facility's Overall AMD as 
																configured by patienttype/service
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Scheduled_LatestDeficiencyDelinquencyRate]
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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_LatestDeficiencyDelinquencyRate.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES') IS NOT NULL
DROP TABLE ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES

IF OBJECT_ID('tempdb..##HBI_SUBSET_SCHEDULED_DDRM') IS NOT NULL
DROP TABLE ##HBI_SUBSET_SCHEDULED_DDRM

--HBI_SUBSET_SCHEDULED_DDRM_FACILITIES is used to iterate through each facility configured for DDRM
CREATE TABLE ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES (
FacilityCode varchar(10)
)

CREATE TABLE ##HBI_SUBSET_SCHEDULED_DDRM (
FacilityName varchar(25),
MRN varchar(20),
Encounter varchar(20),
Total_Charges decimal(9,2) ,
AdmittedDateText varchar(100),
DischargedDate smalldatetime,
DischargedDateText varchar(100),
PatientName varchar(40),
PatientType varchar(25),
MedicalUnitName varchar(25),
DeficiencyType varchar(20),
DeficiencyState varchar(50),
DocumentType varchar(30),
Physician varchar(30),
PhysicianService varchar(25),
PhysicianSpeciality varchar(25),
AttendingPhysician varchar(30),
LengthOfStay int,
AverageMonthlyDischarges int,
AverageMonthlyDischargesFacility int
)

DECLARE @facilityCode VARCHAR(10)
DECLARE @rowCount int

--retrieve list of facilites configured for DDRM
INSERT INTO ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES
SELECT MPF2HBI_Parm_FACILITY FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_UsePT_TypeAndService'

SELECT @rowCount=COUNT(FacilityCode) FROM ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES
WHILE @rowCount > 0
BEGIN
	SELECT TOP 1 @facilityCode = FacilityCode FROM ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES
	--@tmpUsePatientTypeAndService is used to determine if facility is configured to use Patient type ONLY as a criteria or Patient Type and Service as a criteria
	DECLARE @tmpUsePatientTypeAndService INT
	SET @tmpUsePatientTypeAndService=(SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_UsePT_TypeAndService' AND MPF2HBI_Parm_FACILITY=@facilityCode)

	--FIND Average Monthly Discharges for Current Facility

	DECLARE @countOfFacilityDischarges FLOAT
	DECLARE @AverageMonthlyDischarges INT
	DECLARE @totalAMDMonths INT
	DECLARE @facilityMinDate as smalldatetime
	DECLARE @firstDayOfCurrentMonth as smalldatetime
	DECLARE @startAMDDate as smalldatetime
	
	SET @firstDayOfCurrentMonth=(select DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0))
	SET @facilityMinDate = (select MIN(DISCHARGED) from his.dbo.ENCOUNTERS WITH (NOLOCK) WHERE FACILITY = @facilityCode)
	SET @totalAMDMonths=(select DATEDIFF(month, @facilityMinDate, @firstDayOfCurrentMonth-1))
	
	--Max range is 12 months
	IF @totalAMDMonths > 12 SET @totalAMDMonths = 12
	
	IF @totalAMDMonths > 0 
	BEGIN
		SET @startAMDDate = (select  DATEADD(MONTH, -@totalAMDMonths, DATEADD(MONTH, datediff(MONTH, 0,GETDATE()), 0))) --first day of #of months previous current month
		
		SET @countOfFacilityDischarges=	( SELECT COUNT(ENCOUNTER) FROM dbo.ENCOUNTERS WITH(NOLOCK) WHERE 			
										 ( CASE @tmpUsePatientTypeAndService
											  WHEN 1 THEN	
												CASE WHEN RTRIM(PT_TYPE) + ',' + RTRIM(SERVICE)  IN  (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY=@facilityCode) THEN 1 END
											  WHEN 0 THEN
												CASE WHEN PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY=@facilityCode) THEN 1 END
											  END = 1
											 )		
										AND facility=@facilityCode
										AND DISCHARGED IS NOT NULL
										AND DISCHARGED >= @startAMDDate and DISCHARGED < @firstDayOfCurrentMonth)
				
		SET @AverageMonthlyDischarges=CAST(ROUND(@countOfFacilityDischarges/@totalAMDMonths,0) as int)
		
		--if AMD is 0 due to rounding set as 1
		IF @AverageMonthlyDischarges < 1 SET @AverageMonthlyDischarges=1

	END
	ELSE
		Set @AverageMonthlyDischarges=NULL
	
	
	INSERT INTO ##HBI_SUBSET_SCHEDULED_DDRM
	SELECT
		COALESCE(NULLIF((SELECT TOP 1 FACILITY_NAME FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=f.FACILITY), ''), COALESCE(NULLIF(f.FACILITY, ''), 'NO FACILITY')) as FacilityName,
		e.MRN, 
		e.ENCOUNTER as Encounter, 
		e.TOTAL_CHARGES as Charges, 
		COALESCE(his.dbo.fnHBIDateTime(e.ADMITTED),'Undefined')  AS AdmittedDateText, 
		e.DISCHARGED AS DischargedDate, 
		his.dbo.fnHBIDateTime(e.DISCHARGED) AS DischargedDateText,
		p.NAME AS PatientName,	
		COALESCE(NULLIF((SELECT TOP 1 PTTYPE_NAME  FROM cabinet.dbo.PTTYPE_FILE WITH (NOLOCK) WHERE PTTYPE_CODE=e.PT_TYPE AND PTTYPE_ACTIVE = 'Y' AND SET_ID=(SELECT TOP 1 PTTYPE_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=e.FACILITY)), ''), 'Undefined') AS PatientTypeName,
		COALESCE((SELECT TOP 1 UNIT_NAME FROM dbo.UNIT_FILE WITH (NOLOCK) WHERE UNIT_CODE =  
		(SELECT TOP 1 UNIT FROM dbo.LOCATOR WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER and FACILITY=e.FACILITY ORDER BY occurred DESC )
		 AND SET_ID=(SELECT TOP 1 UNIT_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=f.FACILITY)), 'Undefined') AS MedicalUnitName, 
		(SELECT TOP 1 DefType_File_Name FROM cabinet.dbo.DEFTYPE_FILE WITH (NOLOCK) WHERE Def_Type=f.DEF_TYPE) AS DeficiencyType,
		(SELECT TOP 1 Status_Name FROM cabinet.dbo.STATUS WITH (NOLOCK) WHERE Status_Id=f.STATUS_ID) AS DeficiencyState,
		(SELECT TOP 1 Tag FROM cabinet.dbo.DOCUMENT_NAMES WITH (NOLOCK) WHERE 
			SET_ID=(SELECT TOP 1 DOCUMENT_SET FROM cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE=f.FACILITY)
			AND DOCTYPE_ID=f.DOCTYPE_ID) AS DocumentType,
		COALESCE(NULLIF((SELECT TOP 1 fullname FROM cabinet.dbo.USERS WITH (NOLOCK) WHERE UserInstanceId = (SELECT TOP 1 UserInstanceId FROM cabinet.dbo.WQ_DEFINITION WITH (NOLOCK) WHERE QUEUE=f.queue)), ''),'Undefined') AS Physician,
		COALESCE(NULLIF(uf.service, ''), 'Undefined') AS PhysicanService,
		COALESCE(NULLIF(uf.SPECIALTY, ''), 'Undefined') AS PhysicanSpeciality,
		COALESCE((SELECT TOP 1 name FROM dbo.PHYSICIANS WITH (NOLOCK) WHERE FOLDER=e.ENCOUNTER AND FACILITY=e.FACILITY AND TYPE = 'Attending' ORDER BY NAME), 'Undefined') AS AttendingPhysician,		
		CASE 
			WHEN e.ADMITTED IS NULL THEN 0
			ELSE
  			(CASE 
  				WHEN DATEDIFF(DAY, e.ADMITTED, e.DISCHARGED) = 0 THEN 1	
				ELSE DATEDIFF(DAY, e.ADMITTED, e.DISCHARGED)
			END)
		END AS LengthOfStay,
		@AverageMonthlyDischarges AS AverageMonthlyDischarges,
		/* Required field to support HBI calculations at scorecard level */
		AverageMonthlyDischargesFacility = CASE WHEN ROW_NUMBER() over (partition by f.facility order by f.facility ) = 1
										   THEN @AverageMonthlyDischarges
										   ELSE 0
			                               END
	
	FROM cabinet.dbo.FOLLOW_UP f WITH (NOLOCK)  
	JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON 
		f.FOLDER=e.ENCOUNTER 
		AND f.FACILITY=e.facility
		AND f.STATUS_ID IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDRAgingStatus')
		AND DISCHARGED IS NOT NULL
		AND f.FACILITY=@facilityCode
		AND ( CASE @tmpUsePatientTypeAndService
			  WHEN 1 THEN	 
				CASE WHEN RTRIM(e.PT_TYPE) + ',' + RTRIM(e.SERVICE)  IN  (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME=	'MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY=f.FACILITY) THEN 1 END
			  WHEN 0 THEN
				CASE WHEN PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY=@facilityCode)  THEN 1 END
			  END = 1
				)		
	JOIN dbo.PATIENTS p WITH (NOLOCK) ON
		e.MRN=p.MRN 
		AND e.FACILITY = p.FACILITY
	JOIN cabinet.dbo.USER_FACILITY uf WITH (NOLOCK) ON
		uf.USER_ID=(SELECT userinstanceid FROM cabinet.dbo.wq_definition WITH (NOLOCK) WHERE QUEUE=f.queue)
		AND uf.FACILITY=f.FACILITY
	order by f.facility, p.name
	
	--delete facility FROM temp table to get next facility
	delete FROM ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES WHERE FacilityCode = @facilityCode
	SELECT @rowCount =  COUNT(FacilityCode) FROM ##HBI_SUBSET_SCHEDULED_DDRM_FACILITIES
END

SET  @sqlcmd = ' bcp ##HBI_SUBSET_SCHEDULED_DDRM out ' + rtrim(@HBI_TargetDir) + '\MPF_Scheduled_LatestDeficiencyDelinquencyRate.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_SCHEDULED_DDRM was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Scheduled_LatestDeficiencyDelinquencyRate.txt'
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

GRANT exec on [dbo].[MPF_SCHEDULED_LatestDeficiencyDelinquencyRate] to [IMNET]
go