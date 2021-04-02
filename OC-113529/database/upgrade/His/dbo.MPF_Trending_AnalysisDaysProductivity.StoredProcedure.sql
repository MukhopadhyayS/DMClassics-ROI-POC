USE [HIS]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_AnalysisDaysProductivity]') AND type in (N'P'))
DROP PROCEDURE [dbo].[MPF_Trending_AnalysisDaysProductivity]
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
* Procedure Name: [MPF_Trending_AnalysisDaysProductivity]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Bhanu Vanteru			12/10/2012	Created
*   Iryna Politykina		03/14/2013  Add a default value for User Full name if the user full name is NULL	
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty								 										
*	Bhanu Vanteru			03/19/2013	CR 378,081: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5940 - View Trending Analysis Days Productivity Measure
	UC5941 - View Trending Analysis Days by User Productivity Measure

	Utilization of MPF Analysis Days Productivity
	
	===========================================================================================================================
	Columns						DATABASE	TABLE/VIEW				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	ENCOUNTER					HIS			ENCOUNTERS				Encounter number					Encounter
	FACILITY_NAME				CABINET		FACILITY_FILE			Facility Name						FacilityName
	FullName					CABINET		USERS					User Full Name						FullName
	DATE						CABINET		AssignmentAuditEvent	date and time when					DATE
																	analysis event completed
	Queue						CABINET		AssignmentAuditEvent	Workflow Quque name					Queue
	CALCULATED FIELD			HIS			ENCOUNTERS				discharged-admitted as				LengthOfStay
																	length of stay.
																	if LOS=0; then LOS=1;
	CALCULATED FIELD			HIS			ENCOUNTERS				aae.assignmentauditevent[completed]	DaysToCompleteFromDischarge
																	- discharged
	CALCULATED FIELD			CABINET		AssignmentAuditEvent	completed - (Create/ReassignTo)		DaysToCompleteFromAnalysisReady
	==============================================================================================================================
*/

CREATE PROCEDURE [dbo].[MPF_Trending_AnalysisDaysProductivity]
@HBI_TargetDir varchar(4000),  -- file_path column from WebData.dbo.wt_definition_info (HBI Server)
@TrendDate smalldatetime      -- if null add rows to Trend Table using max(date) in Trend Table
                                          -- if not null use this date to remove from Trend table and repopulate
as

begin try

declare @xp_cmdshell tinyint, @Success int, @sqlcmd varchar(2000), @ABS_Target_Dir varchar(255), @TrendRetentionDays int, @TrendRetentionDate smalldatetime

-- Enforce Trending Retention

SET @TrendRetentionDays = COALESCE((SELECT MPF2HBI_Parm_VALUE FROM [dbo].[MPF2HBI_Parameters] where [MPF2HBI_Parm_IDENT] = -12 and [MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'), 0)
SET @TrendRetentionDate = (select CONVERT(smalldatetime,CONVERT(char(10), (GETDATE()) - (@TrendRetentionDays), 101)))

DELETE Trending_AnalysisDaysProductivity where [date]<@TrendRetentionDate


-- Delete from trend table using optional parameter or max(date)

IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
SET @TrendDate = (select CONVERT(smalldatetime,CONVERT(char(10), (select max([date]) from Trending_AnalysisDaysProductivity) + 1, 101)))

IF @TrendDate is null or @TrendDate < @TrendRetentionDate -- no parameter and no max(date) use TrendRetentionDate
SET @TrendDate = @TrendRetentionDate

DELETE Trending_AnalysisDaysProductivity where [date] >= @TrendDate


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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_AnalysisDaysProductivity.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT


-- index_time can sometimes be zero
set arithabort off
set ansi_warnings off

-- Populate Trending Table

INSERT Trending_AnalysisDaysProductivity
(
      [FACILITY_NAME],
	  [ENCOUNTER],
      [FullName],
      [date],
	  [Queue],
	  [LengthOfStay],
	  [DaysToCompleteFromDischarge],
	  [DaysToCompleteFromAnalysisReady]
)
select
	COALESCE(NULLIF((FF.FACILITY_NAME), ''), COALESCE(NULLIF(c.FACILITY, ''), 'NO FACILITY')),
	ENC.ENCOUNTER,
	COALESCE(u.FullName, 'User ID: ' + CAST(c.UserInstanceId  as VARCHAR)) as FullName,
	c.assignmentEventDate as date,
	c.Queue,
	CASE  WHEN ENC.ADMITTED IS NULL THEN 0
		  ELSE
  			(CASE 
  				WHEN DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)= 0 THEN 1
				ELSE DATEDIFF(DAY, ENC.ADMITTED, ENC.DISCHARGED)
			END)
	 END AS LengthOfStay ,
	datediff(dd,ENC.DISCHARGED,c.AssignmentEventDate) AS DaysToCompleteFromDischarge,
	datediff(dd,
		COALESCE(
			(select max(AssignmentEventDate) from cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK)where ASSIGNMENT_ID=c.ASSIGNMENT_ID and WorkflowEventName='ReassignTo'), 
			(select max(AssignmentEventDate) from cabinet.dbo.AssignmentAuditEvent WITH (NOLOCK) where ASSIGNMENT_ID=c.ASSIGNMENT_ID and WorkflowEventName='Create'))
			,c.AssignmentEventDate) AS DaysToCompleteFromAnalysisReady
from cabinet.dbo.AssignmentAuditEvent c with (nolock)
left outer join cabinet.dbo.USERS u with (nolock) on c.UserInstanceId = u.UserInstanceId
join his.dbo.ENCOUNTERS ENC with (nolock) on ENC.ENCOUNTER=c.ENCOUNTER and ENC.FACILITY=c.FACILITY
join cabinet.dbo.FACILITY_FILE ff with (nolock) on c.FACILITY=ff.FACILITY_CODE
where c.QueueTypeName='Analysis' and c.WorkflowEventName='Complete' and ENC.DISCHARGED is not null
and c.assignmentEventDate >= @TrendDate AND
c.assignmentEventDate < DateAdd(day, datediff(day,0, GETDATE()), 0)

SET  @sqlcmd = ' bcp [his].[dbo].[Trending_AnalysisDaysProductivity] out ' + rtrim(@HBI_TargetDir) + '\MPF_Trending_AnalysisDaysProductivity.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT

IF @Success = 1
      RAISERROR ('bcp of Trending_AnalysisDaysProductivity was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

      while @@TRANCOUNT > 0
      ROLLBACK TRANSACTION 

      -- Delete all the previous TXT files as an error has occurred 

      SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_AnalysisDaysProductivity.txt'
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

GRANT exec on [dbo].[MPF_Trending_AnalysisDaysProductivity] to [IMNET]
go