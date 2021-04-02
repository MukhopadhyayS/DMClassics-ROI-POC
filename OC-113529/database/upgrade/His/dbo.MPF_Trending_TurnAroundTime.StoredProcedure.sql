USE [HIS]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_TurnAroundTime]') AND type in (N'P'))
DROP PROCEDURE [dbo].[MPF_Trending_TurnAroundTime]
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
* Procedure Name: [MPF_Trending_TurnAroundTime]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Latonia Howard			11/29/2012	Created
*   Bhanu Vanteru           03/19/2013  CR 378,081: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
*	Iryna Politykina		05/01/2013	Put  'With (NOLOCK)' in select statement(s)
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/

/*
*	UC5923 - View Trending Availablity Turn Around Time Measure
*
*	Historical turnaround time for encounters waiting to be scanned
*	
*   ===========================================================================================================================================================================================================				
*	COLUMN(s)								DATABASE	TABLE					DESCRIPTION							NAME USED IN SCRIPT						COMMENTS
*	===========================================================================================================================================================================================================
* 	FacilityName							CABINET		Facility_File			Facility Name						Facility_Name
*	Encounter								HIS	 		Encounters 				Encounter Number					Encounter_Number
*	DocumentationSatisfied					CABINET		AssignmentAuditEvent	EncounterEventDate					DocumentationSatisfied
*	DocumentationSatisfiedDate				CABINET		AssignmentAuditEvent	EncounterEventDate					DocumentationSatisfiedDate
*	HoursTurnAround							HIS			Encounters				Turn Around Time in hours			Formula: DATEDIFF(HOUR, ENC.DISCHARGED,EA.EncounterEventDate)	
*	===========================================================================================================================================================================================================
*/

CREATE PROCEDURE [dbo].[MPF_Trending_TurnAroundTime]
@HBI_TargetDir varchar(4000),  -- file_path column from WebData.dbo.wt_definition_info (HBI Server)
@TrendingDate smalldatetime      -- if null add rows to Trending Table using max(date) in Trending Table
                                          -- if not null use this date to remove from Trending table and repopulate
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255), @TrendingRetentionDays int, @TrendingRetentionDate smalldatetime

-- Enforce Trending Retention

SET @TrendingRetentionDays = COALESCE((SELECT MPF2HBI_Parm_VALUE FROM [dbo].[MPF2HBI_Parameters] where [MPF2HBI_Parm_IDENT] = -12 and [MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'), 0)
SET @TrendingRetentionDate = (select CONVERT(smalldatetime,CONVERT(char(10), (GETDATE()) - (@TrendingRetentionDays), 101)))

DELETE Trending_TurnAroundTime where [DocumentationSatisfied]<@TrendingRetentionDate


-- Delete from Trending table using optional parameter or max(date)

IF @TrendingDate is null -- if null add rows to Trending Table using max(date) in Trending Table
SET @TrendingDate = (select CONVERT(smalldatetime,CONVERT(char(10), (select max([DocumentationSatisfied]) from Trending_TurnAroundTime) + 1, 101)))

IF @TrendingDate is null or @TrendingDate < @TrendingRetentionDate -- no parameter and no max(date) use TrendingRetentionDate
SET @TrendingDate = @TrendingRetentionDate

DELETE Trending_TurnAroundTime where [DocumentationSatisfied] >= @TrendingDate


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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_TurnAroundTime.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT


-- index_time can sometimes be zero
set arithabort off
set ansi_warnings off

-- Populate Trending Table

INSERT Trending_TurnAroundTime
(
      [FacilityName],
      [Encounter],
      [DocumentationSatisfied],
      [DocumentationSatisfiedDate],
      [HoursTurnAround]
)
SELECT DISTINCT
COALESCE(NULLIF((select facility_name from [cabinet].[dbo].[facility_file] WITH (NOLOCK) where facility_code = EA.FACILITY), ''),
								 COALESCE(NULLIF( EA.FACILITY, ''), 'NO FACILITY')) as FacilityName,
ENC.ENCOUNTER as Encounter,
min(EA.EncounterEventDate) as DocumentationSatisfied,
CONVERT(VARCHAR(10), min(EA.EncounterEventDate), 101) as DocumentationSatisfiedDate,
DATEDIFF(HOUR, ENC.DISCHARGED, min(EA.EncounterEventDate)) as HoursTurnAround
FROM ENCOUNTERS ENC WITH (NOLOCK)
JOIN cabinet.dbo.EncounterAuditEvent EA WITH (NOLOCK) ON ENC.ENCOUNTER = EA.ENCOUNTER AND EA.FACILITY = ENC.FACILITY
WHERE
EA.EncounterEventName='DocumentationSatisfied'
AND (EA.EncounterEventDate >= @TrendingDate)
AND EA.EncounterEventDate < DateAdd(day, datediff(day,0, GETDATE()), 0)
AND ENC.DISCHARGED  IS NOT NULL
group by EA.FACILITY, ENC.MRN, ENC.ENCOUNTER, ENC.DISCHARGED

SET  @sqlcmd = ' bcp [his].[dbo].[Trending_TurnAroundTime] out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_TurnAroundTime.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT

IF @Success = 1
      RAISERROR ('bcp of Trending_TurnAroundTime was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

      while @@TRANCOUNT > 0
      ROLLBACK TRANSACTION 

      -- Delete all the previous TXT files as an error has occurred 

      SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_TurnAroundTime.txt'
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

GRANT exec on [dbo].[MPF_Trending_TurnAroundTime] to [IMNET]
go