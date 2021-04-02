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
* Procedure Name: [MPF_Trending_CodingDaysBacklog]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date						Changes
*	---------				-------						-------------
* 	Richard Brightwell		12/12/2012 12:12:12.12 AM 	Created
* 	Richard Brightwell		02/12/2013 12:12:12.12 AM 	CR 376,652
*	Bhanu Vanteru			03/05/2013 04:00 PM			CR 377,784: MPFA 16.0 Trending Coding Days Backlog shows Tomorrow's Date
*	Iryna Politykina		03/18/2013					set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty					
*	Bhanu Vanteru			04/15/2013 04:00 PM 		CR 378,081: MPFA 16.0: Some of the trending scorecards reflects today's data in the result
*   Latonia Howard		04/18/2013  Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/

USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_CodingDaysBacklog]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Trending_CodingDaysBacklog]
GO

/*
	  UC5933 - View Trending Coding Days Backlog Measure

      backlog of encounters waiting for Coding

	===========================================================================================================================
	Columns						DATABASE	TABLE						DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	Assignment_Id				Cabinet		Assignments					Assignment Id Number				ASSIGNMENT_ID
	USERID						Cabinet		Assignments					Queue								USERID
	ASSIGNED					Cabinet		Assignments					Date Assigned						ASSIGNED
	ENCOUNTER					Cabinet		Assignments					ENCOUNTER Id Number					ENCOUNTER
	FACILITY					Cabinet		Assignments					FACILITY Code						FACILITY
	QUEUE						Cabinet		WQ_DEFINITION				Queue								QUEUE
	QueueTypeLovID				Cabinet		QueueTypeLov				Queue Type Id Number				QueueTypeLovID
	QueueTypeName				Cabinet		QueueTypeLov				Queue Type (Coding, etc.)			QueueTypeName
	ASSIGNMENT_ID				Cabinet		AssignmentAuditEvent		Assignment Id Number				ASSIGNMENT_ID
	Queue						Cabinet		AssignmentAuditEvent		Queue								Queue
	AssignmentEventDate			Cabinet		AssignmentAuditEvent		Date Assignment Event Created		AssignmentEventDate
	ENCOUNTER					Cabinet		AssignmentAuditEvent		ENCOUNTER Id Number					ENCOUNTER
	FACILITY					Cabinet		AssignmentAuditEvent		FACILITY Code						FACILITY
	QueueTypeName				Cabinet		AssignmentAuditEvent		Queue Type (Coding, etc.)			QueueTypeName
	WorkflowEventName			Cabinet		AssignmentAuditEvent		e.g. Create, Complete, Reassign		WorkflowEventName
	FACILITY_NAME				Cabinet		FACILITY_FILE				Assignment Id Number				FACILITY_NAME
	Discharged					HIS			ENCOUNTERS					Date Patient checked out			Discharged
	==============================================================================================================================
*/
CREATE PROCEDURE [dbo].[MPF_Trending_CodingDaysBacklog]
@HBI_TargetDir varchar(4000),  -- file_path column FROM WebData.dbo.wt_definition_info (HBI Server)
@TrendDate smalldatetime      -- if null add rows to Trend Table using max(date) in Trend Table
                                          -- if not null use this date to remove from Trend table and repopulate
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255),  @TrendRetentionDays int, @TrendRetentionDate smalldatetime

-- Enforce Trending Retention
DECLARE @currentDate smalldatetime
SET @currentDate = GETDATE()

SET @TrendRetentionDays = COALESCE((SELECT MPF2HBI_Parm_VALUE FROM [dbo].[MPF2HBI_Parameters] with (nolock) where [MPF2HBI_Parm_IDENT] = -12 and [MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'), 0)
SET @TrendRetentionDate = (select CONVERT(smalldatetime, CONVERT(char(10), (@currentDate) - (@TrendRetentionDays), 101)))
--PRINT @TrendDate
--PRINT @TrendRetentionDate

DELETE Trending_CodingDaysBacklog where TrendingDate < @TrendRetentionDate

-- Delete from trend table using optional parameter or max(date)
IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
SET @TrendDate = (select CONVERT(smalldatetime, CONVERT(char(10), (select max([TrendingDate]) from Trending_CodingDaysBacklog with (nolock)) + 1, 101)))
IF(@TrendDate = DATEADD(Day, 0, DATEDIFF(Day, 0, GetDate()))) -- if @TrendDate is today (happens when trending job is run more than once on the same day)
begin
	SET @TrendDate = DATEADD(Day, -1, DATEDIFF(Day, 0, @TrendDate))
end

IF @TrendDate IS NULL or @TrendDate < @TrendRetentionDate -- no parameter and no max(date) use TrendRetentionDate
SET @TrendDate = @TrendRetentionDate

DELETE Trending_CodingDaysBacklog where TrendingDate >= @TrendDate


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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_CodingDaysBacklog.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT


------------------------------------- START NON-TEMPLATE CODE ----------------------------------------------------

-- Get our date range vars set up
DECLARE @todayMidnight Datetime2
SET @todayMidnight = DATEADD(Day, 0, DATEDIFF(Day, 0, GetDate())) 
DECLARE @lastRun Datetime2;
SET @lastRun = DATEADD(Day, 1, @TrendDate)

-- Drop any left over temp tables
;if object_id('TEMPDB..#DateSequence') is not null
  Drop Table #DateSequence

;if object_id('TEMPDB..#AssignmentHistory') is not null
  Drop Table #AssignmentHistory
  
;if object_id('TEMPDB..#EachDaysAssignments') is not null
  Drop Table #EachDaysAssignments
		  	  

--Create a temp table with one row for each date which hasn't yet been run. Dates are in reverse chronological order.
;With DateSequence( AsOfDate ) as
(
    Select @lastRun as Date
        union all
    Select dateadd(day, 1, AsOfDate)
    	from DateSequence
    	where AsOfDate < @todayMidnight
)
select asofdate into #datesequence from DateSequence  ORDER BY asofdate desc option (MaxRecursion 1500)

-- Preload with current assignments in Coding queues.
--USERID = QUEUE NAME, ASSIGNED = DATE ASSIGNED
select 'Assignment' as Source, ASSIGNMENT_ID, USERID, ASSIGNED, ENCOUNTER, FACILITY 
into #EachDaysAssignments
from cabinet.dbo.ASSIGNMENTS WITH (NOLOCK)
			JOIN cabinet.dbo.WQ_DEFINITION def WITH (NOLOCK) ON def.QUEUE = assignments.USERID 
			JOIN cabinet.dbo.QueueTypeLov qtlov WITH (NOLOCK) ON qtlov.QueueTypeLovID = def.QueueTypeLovID 
WHERE QueueTypeName='Coding'

-- Use a cursor to itterate through each date and recreate the assignments that existed on that date at midnight.
;declare DateSequence_cursor CURSOR FORWARD_ONLY for
select asofdate from #datesequence option (MaxRecursion 1500)

DECLARE @AsOfDateVar Datetime2;
open DateSequence_cursor
fetch next from DateSequence_cursor into @AsOfDateVar
WHILE(@@FETCH_STATUS <> -1)
BEGIN
	IF(@@FETCH_STATUS <> -2)
	BEGIN
	
		-- Starting with the last itteration's day's assignments (going backwards in time), add back in assignments completed today (AsOfDate)
		-- because they are missing now but were there at midnight.
		INSERT INTO #EachDaysAssignments
		SELECT	'AuditEvent' as Source, c.ASSIGNMENT_ID,  c.Queue, c.AssignmentEventDate, c.ENCOUNTER as Encounter, c.facility	
		FROM cabinet.dbo.AssignmentAuditEvent c WITH (NOLOCK)
		WHERE c.assignmentEventDate >= DATEADD(day,0,@AsOfDateVar) AND c.assignmentEventDate < DATEADD(day,1,@AsOfDateVar) 
		      AND c.QueueTypeName='Coding' AND (c.WorkflowEventName='Complete' )
		option (MaxRecursion 1500)	
		
		-- Now delete assignments which were created today (AsOfDate) because those weren't there at midnight. 
		DELETE T 
		FROM #EachDaysAssignments T  WITH (NOLOCK)
		INNER JOIN cabinet.dbo.AssignmentAuditEvent AE on AE.ASSIGNMENT_ID = T.ASSIGNMENT_ID
		WHERE AE.assignmentEventDate >= DATEADD(day,0,@AsOfDateVar) AND AE.assignmentEventDate < DATEADD(day,1,@AsOfDateVar) 
		      and AE.WorkflowEventName='Create'
		
		-- We now have a snapshot of what assignments we had at midnight, so write that to our history for the day
		if object_id('TEMPDB..#AssignmentHistory') is not null
		BEGIN
			INSERT INTO #AssignmentHistory
			Select @AsOfDateVar as 'AsOfDate', Source, ASSIGNMENT_ID, USERID,  ENCOUNTER, FACILITY 
			from #EachDaysAssignments  WITH (NOLOCK)
		END
		ELSE
		BEGIN
			Select @AsOfDateVar  as 'AsOfDate', Source, ASSIGNMENT_ID, USERID,  ENCOUNTER, FACILITY 
			INTO #AssignmentHistory
			from #EachDaysAssignments  WITH (NOLOCK)
		END
		
	end

-- Move to the next day (previous day since we are reverse chronological order) 
fetch next from DateSequence_cursor into @AsOfDateVar

END

-- Clean up
close DateSequence_cursor
DEALLOCATE DateSequence_cursor


CREATE CLUSTERED INDEX [IXC_AssignmentHistory_ENC_FAC] ON #AssignmentHistory
(
                [ENCOUNTER] ASC,
                [FACILITY] ASC
)


-- We need this because SQL Server creates a crummy plan
SET FORCEPLAN ON

-- We now have a record of what assignments existed on each day at midnight, so let get the rest of the data and write to the subset.

INSERT INTO
	Trending_CodingDaysBacklog
	( 
		[FacilityName], 
		[TrendingDate], 
		[Encounter],
		[DaysSinceDischarged], 
		[WorkflowQueueName]
	)    

	Select 
		COALESCE(NULLIF((F.FACILITY_NAME), ''), COALESCE(NULLIF(E.FACILITY, ''), 'NO FACILITY')) as FacilityName,
		DATEADD(Day, -1, DATEDIFF(Day, 0, AsOfDate)),
		o.ENCOUNTER as Encounter, 
		DATEDIFF(dd, e.Discharged, o.AsOfDate) AS DaysSinceDischarged,
		o.UserId as WorkflowQueueName
	from #AssignmentHistory o  WITH (NOLOCK)
	JOIN dbo.ENCOUNTERS e WITH (NOLOCK) ON
		e.ENCOUNTER = o.ENCOUNTER
		AND e.FACILITY = o.FACILITY
	JOIN dbo.PATIENTS p WITH (NOLOCK) ON
		p.FACILITY = e.FACILITY
		AND p.MRN = e.MRN
	JOIN cabinet.dbo.FACILITY_FILE F WITH (NOLOCK) ON 
		E.FACILITY = F.FACILITY_CODE  
	WHERE  e.DISCHARGED  IS NOT NULL 
	  AND e.DISCHARGED <= o.AsOfDate

	ORDER BY AsOfDate desc, FacilityName, WorkflowQueueName, Encounter
	
	
------------------------------------- END NON-TEMPLATE CODE ----------------------------------------------------

SET  @sqlcmd = ' bcp [his].[dbo].Trending_CodingDaysBacklog out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_CodingDaysBacklog.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
--SELECT @sqlcmd
--SELECT @Success

IF @Success = 1
	RAISERROR ('bcp of Trending_CodingDaysBacklog was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_CodingDaysBacklog.txt'
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

GRANT exec on [dbo].[MPF_Trending_CodingDaysBacklog] to [IMNET]
go
