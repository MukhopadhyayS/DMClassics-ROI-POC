USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_HospitalDeficiencyAging]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Trending_HospitalDeficiencyAging]
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
* 	Iryna Politykina		12/01/2012	Created
*	Joseph Chen				01/08/2013  Updated to use DefType_File table instead of DEF_TYPE table because DEF_TYPE table is obsolete
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*	Bhanu Vanteru			03/19/2013	CR 378,081: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
*	Joseph Chen				03/19/2013	Remove print statement
*	Iryna Politykina		05/01/2013	Inserted WITH (NoLock) in select statement(s)
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/

/*
	UC5924 -  View Trending Hospital Deficiency Aging Measure

	===========================================================================================================================
	Columns						DATABASE	TABLE						DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
 	FACILITY_NAME				CABINET		Facility_File				Facility Name						FacilityName
 	CREATED						CABINT		FOLLOW_UP					Deficiency Date						CreatedDate
 	CALCULATED FIELD 													Formated Deficiency Date			CreatedDateText
 	Service						CABINET		User_Facility				Physician Service					Service
 	Speciality					CABINET		User_Facility				Physician Speciality				Speciality
 	fullname                    CABINET     USERS						Physician Full name					Physician
 	CALCULATED FIELD													AMD (Average Monthly Discharges)	AverageMonthlyDischarges
																		Facility's Overall AMD as 
																		configured by patienttype/service
	DefType_File_Name			CABINET		DefType_File				Deficiency Type						DeficiencyType
	MPF2HBI_Parm_FACILITY		CABINET		MPF2HBI_Parameters			Facilities Code which are tracking	FacilityCode
																		delinquent deficiencies
	CALCULATED FIELD													AMD (Average Monthly Discharges)    facilityLevelAMD
																		Facility's Overall AMD as 
																		configured by patienttype/service	
	Encounter					HIS	 		Encounters 					Encounter Id						DelinquentEncounter					
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Trending_HospitalDeficiencyAging]
@HBI_TargetDir varchar(4000),  -- file_path column FROM WebData.dbo.wt_definition_info (HBI Server)
@TrendDate smalldatetime      -- if null add rows to Trend Table using max(date) in Trend Table
                                          -- if not null use this date to remove from Trend table and repopulate
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255),  @TrendRetentionDays int, @TrendRetentionDate smalldatetime

-- Enforce Trending Retention

DECLARE @currentDate smalldatetime
SET @currentDate = GETDATE()

SET @TrendRetentionDays = COALESCE((SELECT MPF2HBI_Parm_VALUE FROM [dbo].[MPF2HBI_Parameters] where [MPF2HBI_Parm_IDENT] = -12 and [MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'), 0)
SET @TrendRetentionDate = (select CONVERT(smalldatetime, CONVERT(char(10), (@currentDate) - (@TrendRetentionDays), 101)))

DELETE Trending_HospitalDeficiencyAging where CreatedDate < @TrendRetentionDate

-- Delete from trend table using optional parameter or max(date)

IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
SET @TrendDate = (select CONVERT(smalldatetime, CONVERT(char(10), (select max([CreatedDate]) from Trending_HospitalDeficiencyAging) + 1, 101)))

IF @TrendDate IS NULL or @TrendDate < @TrendRetentionDate -- no parameter and no max(date) use TrendRetentionDate
SET @TrendDate = @TrendRetentionDate


DELETE Trending_HospitalDeficiencyAging where CreatedDate >= @TrendDate


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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_HospitalDeficiencyAging.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_TRENDING_HDA_FACILITIES') IS NOT NULL
DROP TABLE ##HBI_SUBSET_TRENDING_HDA_FACILITIES


--##HBI_SUBSET_TRENDING_HDA_FACILITIES is used to iterate through each facility configured for HDA
CREATE TABLE ##HBI_SUBSET_TRENDING_HDA_FACILITIES (
FacilityCode varchar(10),
Value varchar(10)
)


DECLARE @facilityCode VARCHAR(10)
DECLARE @rowCount int
DECLARE @startDate smalldatetime
DECLARE @endDate smalldatetime



DECLARE @startAMDCalculationDate smalldatetime
DECLARE @endAMDCalculationDate smalldatetime


/*the last date of the  previous completed month()*/
SET @endAMDCalculationDate = DATEADD(MONTH, DATEDIFF(MONTH, 0, @currentDate), -1) 

--PRINT '@endAMDCalculationDate: ' + convert(varchar(10),@endAMDCalculationDate, 101)

--retrieve list of facilites configured for DDRM
INSERT INTO ##HBI_SUBSET_TRENDING_HDA_FACILITIES 
SELECT MPF2HBI_Parm_FACILITY, MPF2HBI_Parm_VALUE 
FROM dbo.MPF2HBI_Parameters WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_UsePT_TypeAndService'

SELECT @rowCount = COUNT(FacilityCode) FROM ##HBI_SUBSET_TRENDING_HDA_FACILITIES
WHILE @rowCount > 0
BEGIN

	SELECT TOP 1 @facilityCode = FacilityCode FROM ##HBI_SUBSET_TRENDING_HDA_FACILITIES

	/*find out what is a minimum discharged date, 
	and difference between last date of the previous month, to minimum discharged date in full months*/
	DECLARE @mothNumber int
	SELECT @mothNumber = DATEDIFF(MONTH, MIN(discharged), @endAMDCalculationDate) from his.dbo.Encounters WITH (NOLOCK) WHERE FACILITY = @facilityCode
	IF @mothNumber > 12 SET @mothNumber = 12
	
	--PRINT 'facility: ' + @facilityCode +  '; @mothNumber:' + convert(varchar(10),@mothNumber)

	/*the first date of the ast date of the  previous completed month()*/
	IF @mothNumber > 0 SET @startAMDCalculationDate = DATEADD(MONTH, -@mothNumber, DATEADD(MONTH, datediff(MONTH, 0, @endAMDCalculationDate), 0))

	--PRINT '@@startAMDCalculationDate: ' + convert(varchar(10),@startAMDCalculationDate, 101)
	
	DECLARE @AverageMonthlyDischarges float
	IF @mothNumber > 0
	
	BEGIN
		DECLARE @tmpUsePatientTypeAndService int
		--@tmpUsePatientTypeAndService is used to determine if facility is configured to use Patient type ONLY as a criteria or Patient Type and Service as a criteria
		SET @tmpUsePatientTypeAndService = (SELECT Value FROM ##HBI_SUBSET_TRENDING_HDA_FACILITIES WHERE FacilityCode = @facilityCode)
		SET @AverageMonthlyDischarges =   ROUND(CASE @tmpUsePatientTypeAndService
											WHEN 1 THEN (SELECT (COUNT(ENCOUNTER)/@mothNumber) FROM dbo.ENCOUNTERS WITH(NOLOCK) WHERE LTRIM(RTRIM(PT_TYPE)) + ',' + LTRIM(RTRIM(SERVICE)) 
														 IN  (SELECT LTRIM(RTRIM(MPF2HBI_Parm_VALUE)) FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY = @facilityCode) AND FACILITY = @facilityCode AND DISCHARGED is not NULL AND DISCHARGED BETWEEN  @startAMDCalculationDate AND @endAMDCalculationDate)			
											WHEN 0 THEN ( SELECT (COUNT(ENCOUNTER)/@mothNumber) FROM dbo.ENCOUNTERS WITH(NOLOCK) WHERE PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME = 'MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY = @facilityCode) AND FACILITY = @facilityCode AND DISCHARGED is not NULL AND DISCHARGED BETWEEN  @startAMDCalculationDate AND @endAMDCalculationDate)
										  END, 0)
										  
		END
	ELSE SET @AverageMonthlyDischarges = NULL
	
	--PRINT '@AverageMonthlyDischarges:'  + convert(varchar(10),@AverageMonthlyDischarges)
									  
	IF @AverageMonthlyDischarges < 1 SET @AverageMonthlyDischarges = 1
	
	INSERT INTO Trending_HospitalDeficiencyAging
	SELECT
		COALESCE(NULLIF((Select Facility_Name from cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE =  @facilityCode), ''), COALESCE(NULLIF( @facilityCode, ''), 'NO FACILITY')) as FacilityName,
		f.CREATED AS CreatedDate, 
		CONVERT(VARCHAR(10), f.created, 101) as CreatedDateText,
		COALESCE(NULLIF(uf.service, ''), 'Undefined') AS [Service],
		COALESCE(NULLIF(uf.SPECIALTY, ''), 'Undefined') AS Speciality,
		COALESCE(NULLIF((SELECT TOP 1 fullname FROM cabinet.dbo.USERS WITH (NOLOCK) WHERE UserInstanceId = (SELECT TOP 1 UserInstanceId FROM cabinet.dbo.WQ_DEFINITION WITH (NOLOCK) WHERE QUEUE = f.queue)), ''),'Undefined') AS Physician,
		@AverageMonthlyDischarges as AverageMonthlyDischarges,
		deficiencyType.DefType_File_Name as DeficiencyType,
		@facilityCode as FacilityCode,
		FacilityLevelAMD = 
		CASE
			WHEN ROW_NUMBER() over (partition by f.Facility order by created) = 1
				THEN @AverageMonthlyDischarges
			ELSE 0
		END,
		e.Encounter as DelinquentEncounter
	FROM cabinet.dbo.FOLLOW_UPAuditEvent f WITH (NOLOCK)  
	JOIN cabinet.dbo.DefType_File deficiencyType WITH (NOLOCK) ON  deficiencyType.Def_Type= f.DEF_TYPE
	JOIN cabinet.dbo.USER_FACILITY uf WITH (NOLOCK) ON	uf.USER_ID = (SELECT userinstanceid FROM cabinet.dbo.wq_definition WITH (NOLOCK) WHERE QUEUE = f.queue) AND uf.FACILITY = f.FACILITY
	
	JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON 	f.FOLDER = e.ENCOUNTER AND e.FACILITY=f.FACILITY AND e.DISCHARGED IS NOT NULL 
		 AND ( CASE @tmpUsePatientTypeAndService
			  WHEN 1 THEN	 
				CASE WHEN LTRIM(RTRIM(e.PT_TYPE)) + ',' + LTRIM(RTRIM(e.SERVICE))  IN  (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME = 'MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY = f.FACILITY) THEN 1 END
			  WHEN 0 THEN
				CASE WHEN e.PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY=f.FACILITY)  THEN 1 END
			  END = 1)
	WHERE f.FACILITY = @facilityCode AND 
	f.STATUS_ID IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDRAgingStatus')	
	AND f.CREATED >= @TrendDate
	AND f.CREATED < DateAdd(day, datediff(day,0, GETDATE()), 0)
	
	/*update AMD for the facility in the existing */
	UPDATE Trending_HospitalDeficiencyAging SET AverageMonthlyDischarges = @AverageMonthlyDischarges, FacilityLevelAMD = 0  WHERE FacilityCode = @facilityCode
	UPDATE TOP (1) Trending_HospitalDeficiencyAging SET FacilityLevelAMD = @AverageMonthlyDischarges WHERE FacilityCode = @facilityCode
    
	--delete facility FROM temp table to get next facility
	delete FROM ##HBI_SUBSET_TRENDING_HDA_FACILITIES WHERE FacilityCode = @facilityCode
	SELECT @rowCount =  COUNT(FacilityCode) FROM ##HBI_SUBSET_TRENDING_HDA_FACILITIES
END


SET  @sqlcmd = ' bcp [his].[dbo].Trending_HospitalDeficiencyAging out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_HospitalDeficiencyAging.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of Trending_HospitalDeficiencyAging was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_HospitalDeficiencyAging.txt'
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

GRANT exec on [dbo].[MPF_Trending_HospitalDeficiencyAging] to [IMNET]
go
