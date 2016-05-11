USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_IndexingProductivity]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_IndexingProductivity]
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
*	Procedure Name: [MPF_Daily_IndexingProductivity]
*	Description:	UC5929 - View Daily Indexing Productivity Measure
*	
*	NOTES: 
*   ========================================================================================================================================================================			
*	COLUMN(s)								DATABASE	TABLE					DESCRIPTION							NAME USED IN SCRIPT						COMMENTS
*	========================================================================================================================================================================
* 	Facility_Name							CABINET		Facility_File			Facility Name						FacilityName
*	FullName								CABINET		USERS					User full name						UserFullName
*	pgs_to_be_reviewed						HIS			user_productivity		Indexing Pages						IndexingPages
*	index_time								HIS			user_productivity		Indexed Time						IndexedTime
*	========================================================================================================================================================================
*
*	Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
*	Iryna Politykina		11/05/2012		Created for HPFA 16.0 UC5929
*	MD Meersma				11/30/2012		Code Review
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*	MD Meersma				10/08/2013	Change localhost to use @@servername
*************************************************************************************************************************************/

CREATE PROCEDURE [dbo].MPF_Daily_IndexingProductivity
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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_IndexingProductivity.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##MPF_DAILY_INDEXING_PROD_SUBSET') is not null
DROP TABLE ##MPF_DAILY_INDEXING_PROD_SUBSET

SELECT 
COALESCE(NULLIF((facilityFile.FACILITY_NAME), ''), COALESCE(NULLIF(productivity.FACILITY_CODE, ''), 'NO FACILITY')) as FacilityName,
COALESCE(users.FullName, 'DCS/QCI user: ' + productivity.user_name)  as UserFullName,
productivity.pgs_to_be_reviewed as IndexingPages,
productivity.index_time as IndexedTime
INTO ##MPF_DAILY_INDEXING_PROD_SUBSET
FROM cabinet.dbo.user_productivity productivity WITH (NOLOCK)
LEFT JOIN cabinet.dbo.USERS users WITH (NOLOCK) on productivity.user_name = users.name
LEFT JOIN cabinet.dbo.FACILITY_FILE facilityFile WITH (NOLOCK) ON facilityFile.FACILITY_CODE = productivity.FACILITY_CODE
WHERE productivity.date >= DATEADD(day,datediff(day,1,getdate()),0) AND productivity.date < DATEADD(day,datediff(day,0,getdate()),0) 


SET  @sqlcmd = ' bcp ##MPF_DAILY_INDEXING_PROD_SUBSET out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_IndexingProductivity.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT


IF @Success = 1
	RAISERROR ('bcp of ##MPF_DAILY_INDEXING_PROD_SUBSET was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_IndexingProductivity.txt'
	EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT
END CATCH
GO

GRANT EXECUTE ON [dbo].[MPF_Daily_IndexingProductivity] TO [IMNET] AS [dbo]
GO