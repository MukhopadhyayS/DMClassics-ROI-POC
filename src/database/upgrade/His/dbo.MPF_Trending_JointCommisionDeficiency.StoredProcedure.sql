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
* Procedure Name: [MPF_Trending_JointCommissionDeficiency]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Iryna Politykina		11/26/2012	Created
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*   Iryna Politykina		03/25/2013  Fix per CR # 378859: Trending SCorecard not populating data
*	Iryna Politykina		05/01/2013  Put WITH (NOLOCK) in select statement(s)
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/

USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_JointCommissionDeficiency]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Trending_JointCommissionDeficiency]
GO

/*
	UC5816 - View Trending Joint Commission Deficiency Measure

	===========================================================================================================================
	Columns						DATABASE	TABLE				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
 	FACILITY_NAME				CABINET		Facility_File		Facility Name						FacilityName
	CALCULATED FIELD											The last date of a month			lastDateOfAMonth
 	CALCULATED FIELD											AMD (Average Monthly Discharges)	AverageMonthlyDischarges
																Facility's Overall AMD as 
																configured by patienttype/service
	CALCULATED FILED											Rolling Quaters	: (i.e., three		RollingQuater
																months at a time starting with 
																the last full calendar month). 	
 	CALCULATED FILED											AMD , but we insert it only in one	FacilityLevelAMD
 																row for a facility(HBI need)
	Encounter					HIS	 		Encounters 			Encounter Id						DelinquentEncounter
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Trending_JointCommissionDeficiency]
@HBI_TargetDir varchar(4000)  -- file_path column FROM WebData.dbo.wt_definition_info (HBI Server)
/*@TrendDate smalldatetime      -- if null add rows to Trend Table using max(date) in Trend Table minus 1 (may have partial day)
                                          -- if not null use this date to remove from Trend table and repopulate
*/
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255)
DECLARE @currentDate smalldatetime
SET @currentDate = GETDATE()

DELETE Trending_JointCommisionDeficiency 

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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_JointCommissionDeficiency.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT


--@HBI_SUBSET_TRENDING_JCD_FACILITIES is used to iterate through each facility configured for HDA
DECLARE @HBI_SUBSET_TRENDING_JCD_FACILITIES TABLE(
FacilityCode varchar(10),
Value varchar(10)
)


DECLARE @facilityCode VARCHAR(10)
DECLARE @rowCount int
DECLARE @startDate smalldatetime
DECLARE @endDate smalldatetime

DECLARE @mothNumber int

/*the last date of the  previous completed month()*/
SET @endDate = DATEADD(MONTH, DATEDIFF(MONTH, 0, @currentDate), -1) 
--PRINT '@endDate: ' + convert(varchar(10),@endDate, 101)
 

DECLARE @currentMonthFirstDay smalldatetime
DECLARE @minusThreeMonthsFirstDay smalldatetime
DECLARE @minusSixMonthsFirstDay smalldatetime
DECLARE @minusNineMonthsFirstDay smalldatetime
DECLARE @minusTwelveMonthsFirstDay smalldatetime

DECLARE @minusThreeMonthsQuaterName varchar(25)
DECLARE @minusSixMonthsQuaterName varchar(25)
DECLARE @minusNineMonthsQuaterName varchar(25)
DECLARE @minusTwelveMonthsQuaterName varchar(25)

SET @currentMonthFirstDay =  DATEADD(MONTH, datediff(MONTH, 0, @currentDate), 0)
SET @minusThreeMonthsFirstDay =  DATEADD(MONTH, -3, @currentMonthFirstDay)
SET @minusSixMonthsFirstDay =  DATEADD(MONTH, -6, @currentMonthFirstDay)
SET @minusNineMonthsFirstDay =  DATEADD(MONTH, -9, @currentMonthFirstDay)
SET @minusTwelveMonthsFirstDay = DATEADD(MONTH, -12, @currentMonthFirstDay)

SET @minusThreeMonthsQuaterName = CAST(YEAR(@minusThreeMonthsFirstDay)as varchar(4)) + '_' + SUBSTRING(CONVERT(VARCHAR(6),@minusThreeMonthsFirstDay, 101),1,2)  + '_' +SUBSTRING(CONVERT(VARCHAR(6),@minusThreeMonthsFirstDay, 106),3,4) +
+ '_' +SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH, 1,@minusThreeMonthsFirstDay), 106),3,4) + '_' + SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH,2,@minusThreeMonthsFirstDay), 106),3,4)

 
SET @minusSixMonthsQuaterName = CAST(YEAR(@minusSixMonthsFirstDay)as varchar(4)) + '_' + SUBSTRING(CONVERT(VARCHAR(6),@minusSixMonthsFirstDay, 101),1,2)  + '_' +SUBSTRING(CONVERT(VARCHAR(6),@minusSixMonthsFirstDay, 106),3,4) +
+ '_' +SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH, 1,@minusSixMonthsFirstDay), 106),3,4) + '_' + SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH,2,@minusSixMonthsFirstDay), 106),3,4)

SET @minusNineMonthsQuaterName = CAST(YEAR(@minusNineMonthsFirstDay)as varchar(4)) + '_' + SUBSTRING(CONVERT(VARCHAR(6),@minusNineMonthsFirstDay, 101),1,2)  + '_' +SUBSTRING(CONVERT(VARCHAR(6),@minusNineMonthsFirstDay, 106),3,4) +
+ '_' +SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH, 1,@minusNineMonthsFirstDay), 106),3,4) + '_' + SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH,2,@minusNineMonthsFirstDay), 106),3,4)


SET @minusTwelveMonthsQuaterName = CAST(YEAR(@minusTwelveMonthsFirstDay)as varchar(4)) + '_' + SUBSTRING(CONVERT(VARCHAR(6),@minusTwelveMonthsFirstDay, 101),1,2)  + '_' +SUBSTRING(CONVERT(VARCHAR(6),@minusTwelveMonthsFirstDay, 106),3,4) +
+ '_' +SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH, 1,@minusTwelveMonthsFirstDay), 106),3,4) + '_' + SUBSTRING(CONVERT(VARCHAR(6),DATEADD(MONTH,2,@minusTwelveMonthsFirstDay), 106),3,4)


--retrieve list of facilites configured for DDRM
INSERT INTO @HBI_SUBSET_TRENDING_JCD_FACILITIES 
SELECT MPF2HBI_Parm_FACILITY, MPF2HBI_Parm_VALUE 
FROM dbo.MPF2HBI_Parameters WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_UsePT_TypeAndService'

SELECT @rowCount = COUNT(FacilityCode) FROM @HBI_SUBSET_TRENDING_JCD_FACILITIES
WHILE @rowCount > 0
BEGIN
	SELECT TOP 1 @facilityCode = FacilityCode FROM @HBI_SUBSET_TRENDING_JCD_FACILITIES
	/*find out what is a minimum discharged date, 
	and difference between last date of the previous month, to minimum discharged date in full months*/

	SELECT @mothNumber = DATEDIFF(MONTH, MIN(discharged), @endDate) FROM his.dbo.Encounters  WITH (NOLOCK) WHERE FACILITY = @facilityCode
	IF @mothNumber > 12 SET @mothNumber = 12

	/*last day of the  previously month*/
	IF @mothNumber > 0 SET @startDate = DATEADD(MONTH, -@mothNumber, DATEADD(MONTH, datediff(MONTH, 0, @endDate), 0))

	DECLARE @AverageMonthlyDischarges float
	
	IF @mothNumber > 0
		BEGIN
			DECLARE @tmpUsePatientTypeAndService int
			--@tmpUsePatientTypeAndService is used to determine if facility is configured to use Patient type ONLY as a criteria or Patient Type and Service as a criteria
			SET @tmpUsePatientTypeAndService = (SELECT Value FROM @HBI_SUBSET_TRENDING_JCD_FACILITIES WHERE FacilityCode = @facilityCode)
			SET @AverageMonthlyDischarges =   ROUND(CASE @tmpUsePatientTypeAndService
												WHEN 1 THEN (SELECT (COUNT(ENCOUNTER)/@mothNumber) FROM dbo.ENCOUNTERS WITH(NOLOCK) WHERE LTRIM(RTRIM(PT_TYPE)) + ',' + LTRIM(RTRIM(SERVICE)) 
															 IN  (SELECT LTRIM(RTRIM(MPF2HBI_Parm_VALUE)) FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY = @facilityCode) AND FACILITY = @facilityCode AND DISCHARGED is not NULL AND DISCHARGED BETWEEN  @startDate AND @endDate)			
												WHEN 0 THEN ( SELECT (COUNT(ENCOUNTER)/@mothNumber) FROM dbo.ENCOUNTERS WITH(NOLOCK) WHERE PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME = 'MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY = @facilityCode) AND FACILITY = @facilityCode AND DISCHARGED is not NULL AND DISCHARGED BETWEEN  @startDate AND @endDate)
											  END, 0)
		END
		ELSE SET @AverageMonthlyDischarges = NULL
		
	IF @AverageMonthlyDischarges < 1 SET @AverageMonthlyDischarges = 1
		
	/*@MRDR_MRDT Can be either facility or enterprise, depending if all facilities in the enterprise share the same value. */
	DECLARE @MRDR_MRDT int

	/*fetch a parameter for a facility @facilityCode*/
	SET @MRDR_MRDT = (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WHERE MPF2HBI_Parm_FACILITY = @facilityCode AND MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_MRDT')
	
	/*If afcility does not have set parameter , use enterprise  setting*/
	IF @MRDR_MRDT IS NULL SET @MRDR_MRDT = (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WHERE MPF2HBI_Parm_FACILITY = 'E_P_R_S'   AND MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_MRDT')
	
	/*if there is not enterprise setting, use default = 30*/
	IF @MRDR_MRDT IS NULL SET @MRDR_MRDT = 30
	
	DECLARE temp_cursor CURSOR FOR
	
	SELECT f.ID, f.Facility, f.Folder, f.status_id, min(f.created) as delinquentStatusCreated, min(fuae.created) as FinishedDate
	FROM cabinet.dbo.FOLLOW_UPAuditEvent f WITH (NOLOCK)
	JOIN cabinet.dbo.FOLLOW_UPAuditEvent fuae WITH (NOLOCK) ON f.ID = fuae.ID and fuae.STATUS_ID IN (1,7,8,9) AND fuae.created > @minusTwelveMonthsFirstDay
	AND fuae.created < @currentMonthFirstDay
	JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON 	f.FOLDER = e.ENCOUNTER AND e.FACILITY = f.FACILITY AND e.DISCHARGED IS NOT NULL
			 AND (CASE @tmpUsePatientTypeAndService
			  WHEN 1 THEN	 
				CASE WHEN LTRIM(RTRIM(e.PT_TYPE)) + ',' + LTRIM(RTRIM(e.SERVICE))  IN  (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME = 'MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY = f.FACILITY) THEN 1 END
			  WHEN 0 THEN
				CASE WHEN e.PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY=f.FACILITY)  THEN 1 END
			  END = 1)
	WHERE f.FACILITY = @facilityCode AND  f.STATUS_ID IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDRAgingStatus')	
    GROUP BY f.ID, f.facility , f.Folder, f.status_id

UNION ALL
	
	SELECT f.ID, f.Facility, f.Folder, f.status_id, min(f.created) as delinquentStatusCreated, NULL
	FROM cabinet.dbo.FOLLOW_UPAuditEvent f WITH (NOLOCK)
	JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON 	f.FOLDER = e.ENCOUNTER AND e.FACILITY = f.FACILITY AND e.DISCHARGED IS NOT NULL
			 AND (CASE @tmpUsePatientTypeAndService
			  WHEN 1 THEN	 
				CASE WHEN LTRIM(RTRIM(e.PT_TYPE)) + ',' + LTRIM(RTRIM(e.SERVICE))  IN  (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME = 'MPFA.DDRM.MRDR_PT_TypeAndService' AND MPF2HBI_Parm_FACILITY = f.FACILITY) THEN 1 END
			  WHEN 0 THEN
				CASE WHEN e.PT_TYPE IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK)  WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDR_PT_TYPE' AND MPF2HBI_Parm_FACILITY=f.FACILITY)  THEN 1 END
			  END = 1)
	WHERE f.FACILITY = @facilityCode AND f.STATUS_ID IN (SELECT MPF2HBI_Parm_VALUE FROM dbo.MPF2HBI_Parameters WITH (NOLOCK) WHERE MPF2HBI_Parm_NAME='MPFA.DDRM.MRDRAgingStatus')	
	AND NOT EXISTS (SELECT ID FROM cabinet.dbo.FOLLOW_UPAuditEvent WITH (NOLOCK) where ID = f.ID and STATUS_ID IN (1,7,8,9))
	AND f.Created < @currentMonthFirstDay
    GROUP BY f.ID, f.facility , f.Folder, f.status_id
    
	
	DECLARE @facilityAmdCount int
	SET @facilityAmdCount = 0	

	DECLARE @Facility Varchar(10),  @created smalldatetime, @encounter varchar(20), @id int, @status int, @createdDate smalldatetime, @finishedDate smalldateTime
	DECLARE @monthDifference int
	DECLARE @lastDateOfAMonth smalldatetime
	OPEN temp_cursor

	FETCH NEXT FROM temp_cursor INTO  @id ,@Facility, @encounter, @status, @createdDate, @finishedDate

	WHILE @@FETCH_STATUS = 0
	BEGIN
	
	/**	PRINT 'id: ' + convert(varchar(20),@id ) + '@Facility : ' + convert(varchar(20),@Facility  )
		 + '@encounter  : ' + convert(varchar(20),@encounter   )  + '@@status  : ' + convert(varchar(20),@status  ) 
		 
		PRINT '@createdDate  : ' + convert(varchar(20),@createdDate)  +  '@finishedDate  : ' + convert(varchar(20),@finishedDate)   
	**/	
		
		 
		SET @createdDate = COALESCE(@createdDate, @minusTwelveMonthsFirstDay);
		SET @createdDate = @createdDate + @MRDR_MRDT;
		IF @createdDate < @minusTwelveMonthsFirstDay   SET @createdDate = @minusTwelveMonthsFirstDay
		
		SET @finishedDate = COALESCE(@finishedDate, DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,@currentDate),0)) );
		
		
		SET	@monthDifference = DATEDIFF(MONTH, @createdDate, @finishedDate)
		IF @monthDifference > 12 SET  @monthDifference = 12
		
		--PRINT 'Month difference   : ' + convert(varchar(20),@monthDifference)  
		
		DECLARE @j int
		SET @j = 0
		WHILE (@j< @monthDifference)
		
			BEGIN
				SET @lastDateOfAMonth = DATEADD(MINUTE,-1,DATEADD(MONTH, DATEDIFF(MONTH,0,@finishedDate) - @j,0))
				--PRINT '@lastDateOfAMonth : ' +  convert(varchar(20), @lastDateOfAMonth)
				
				INSERT INTO Trending_JointCommisionDeficiency VALUES
				(COALESCE(NULLIF((Select Facility_Name from cabinet.dbo.FACILITY_FILE WITH (NOLOCK) WHERE FACILITY_CODE =  @facilityCode), ''), COALESCE(NULLIF( @facilityCode, ''), 'NO FACILITY')),				
				@lastDateOfAMonth,
				@AverageMonthlyDischarges,
				CASE WHEN @lastDateOfAMonth > @minusThreeMonthsFirstDay AND @lastDateOfAMonth < @currentMonthFirstDay THEN @minusThreeMonthsQuaterName
					 WHEN @lastDateOfAMonth > @minusSixMonthsFirstDay AND @lastDateOfAMonth < @minusThreeMonthsFirstDay THEN @minusSixMonthsQuaterName
					 WHEN @lastDateOfAMonth > @minusNineMonthsFirstDay AND @lastDateOfAMonth < @minusSixMonthsFirstDay THEN @minusNineMonthsQuaterName
					 WHEN @lastDateOfAMonth > @minusTwelveMonthsFirstDay AND @lastDateOfAMonth < @minusNineMonthsFirstDay THEN @minusTwelveMonthsQuaterName
					 ELSE convert(varchar(14),@lastDateOfAMonth)
				END,
				CASE
					WHEN @facilityAmdCount = 0
					THEN @AverageMonthlyDischarges
					ELSE 0
				END,
				@encounter
				)
				SET @facilityAmdCount = 1
				SET @j = @j+1
			END
			FETCH NEXT FROM temp_cursor INTO  @id ,@Facility, @encounter, @status, @createdDate, @finishedDate
	END
	
	CLOSE temp_cursor;   
	DEALLOCATE temp_cursor;
	
	--delete facility FROM temp table to get next facility
	delete FROM @HBI_SUBSET_TRENDING_JCD_FACILITIES WHERE FacilityCode = @facilityCode
	SELECT @rowCount =  COUNT(FacilityCode) FROM @HBI_SUBSET_TRENDING_JCD_FACILITIES
END

SET  @sqlcmd = ' bcp [his].[dbo].Trending_JointCommisionDeficiency out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_JointCommissionDeficiency.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of Trending_JointCommisionDeficiency was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	WHILE @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_JointCommissionDeficiency.txt'
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

GRANT exec on [dbo].[MPF_Trending_JointCommissionDeficiency] to [IMNET]
go
