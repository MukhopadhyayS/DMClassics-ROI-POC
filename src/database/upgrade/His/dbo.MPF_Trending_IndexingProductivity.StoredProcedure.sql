USE [HIS]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_IndexingProductivity]') AND type in (N'P'))
DROP PROCEDURE [dbo].[MPF_Trending_IndexingProductivity]
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
* Procedure Name: [MPF_Trending_IndexingProductivity]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Bhanu Vanteru			11/19/2012	Created
*	Latonia Howard			03/07/2013	Modified returned date to always have midnight as the time
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*   Bhanu Vanteru             03/19/2013  CR 378,081: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5936 - View Trending Indexing Volume Measure
	UC5937 - View Trending Indexing User Measure

	Utilization of MPF Indexing Productivity
	
	===========================================================================================================================
	Columns						DATABASE	TABLE/VIEW				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	FACILITY_NAME				CABINET		FACILITY_FILE			Facility Name						FacilityName
	FullName					CABINET		USERS					User Full Name						FullName
	DATE						CABINET		USER_PRODUCTIVITY		date and time when					DATE
																	occurred
	PGS_TO_BE_REVIEWED			CABINET		USER_PRODUCTIVITY		Number of pages indexed				PGS_TO_BE_REVIEWED
	INDEX_TIME					CABINET		USER_PRODUCTIVITY		Index time (in minutes)				INDEX_TIME
	==============================================================================================================================
*/

CREATE PROCEDURE [dbo].[MPF_Trending_IndexingProductivity]
@HBI_TargetDir varchar(4000),  -- file_path column from WebData.dbo.wt_definition_info (HBI Server)
@TrendDate smalldatetime      -- if null add rows to Trend Table using max(date) in Trend Table
                                          -- if not null use this date to remove from Trend table and repopulate
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255), @TrendRetentionDays int, @TrendRetentionDate smalldatetime

-- Enforce Trending Retention

SET @TrendRetentionDays = COALESCE((SELECT MPF2HBI_Parm_VALUE FROM [dbo].[MPF2HBI_Parameters] where [MPF2HBI_Parm_IDENT] = -12 and [MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'), 0)
SET @TrendRetentionDate = (select CONVERT(smalldatetime,CONVERT(char(10), (GETDATE()) - (@TrendRetentionDays), 101)))

DELETE Trending_IndexingProductivity where [date]<@TrendRetentionDate


-- Delete from trend table using optional parameter or max(date)

IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
SET @TrendDate = (select CONVERT(smalldatetime,CONVERT(char(10), (select max([date]) from Trending_IndexingProductivity) + 1, 101)))

IF @TrendDate is null or @TrendDate < @TrendRetentionDate -- no parameter and no max(date) use TrendRetentionDate
SET @TrendDate = @TrendRetentionDate

DELETE Trending_IndexingProductivity where [date] >= @TrendDate


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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_IndexingProductivity.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT


-- index_time can sometimes be zero
set arithabort off
set ansi_warnings off

-- Populate Trend Table

INSERT Trending_IndexingProductivity
(
      [FACILITY_NAME],
      [FullName],
      [date],
      [pgs_to_be_reviewed],
      [index_time]
)
select 
COALESCE(NULLIF((FF.FACILITY_NAME), ''), COALESCE(NULLIF(up.facility_code, ''), 'NO FACILITY')) as FacilityName,
COALESCE(U.FullName, 'DCS/QCI user: ' + UP.[user_name]) as [USER_NAME],
DateAdd(day, datediff(day,0, UP.[date]), 0),
UP.pgs_to_be_reviewed,
UP.index_time
from cabinet.dbo.user_productivity UP with (nolock)
left join cabinet.dbo.FACILITY_FILE FF with (nolock) on up.facility_code = FF.FACILITY_CODE
left join cabinet.dbo.USERS U with (nolock) on U.NAME = UP.[user_name]
where [date] >= @TrendDate
AND [date] < DateAdd(day, datediff(day,0, GETDATE()), 0)

SET  @sqlcmd = ' bcp [his].[dbo].[Trending_IndexingProductivity] out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_IndexingProductivity.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT

IF @Success = 1
      RAISERROR ('bcp of Trending_IndexingProductivity was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

      while @@TRANCOUNT > 0
      ROLLBACK TRANSACTION 

      -- Delete all the previous TXT files as an error has occurred 

      SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_IndexingProductivity.txt'
      EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

      -- CEL
      DECLARE @ErrorMessage NVARCHAR(2048), @ErrorSeverity INT, @ErrorState INT

      SELECT 
            @ErrorMessage = ERROR_MESSAGE() ,
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE()

      insert cabinet.dbo.SYSERRORS_GLOBAL (GlobalID, OCCURRED, ERROR_TYPE, ERROR_DESC, [ERROR_NUMBER], [ERROR_SEVERITY], [ERROR_STATE], [ERROR_PROCEDURE], [ERROR_LINE])
      values (-43, getdate(), 'SQL', ERROR_MESSAGE(), ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE())

      RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState)

END CATCH
GO

GRANT exec on [dbo].[MPF_Trending_IndexingProductivity] to [IMNET]
go