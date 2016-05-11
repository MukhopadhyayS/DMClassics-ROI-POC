USE [HIS]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Trending_CodingDaysProductivity]') AND type in (N'P'))
DROP PROCEDURE [dbo].[MPF_Trending_CodingDaysProductivity]
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
*/

/*
 	Procedure Name: [MPF_Trending_CodingDaysProductivity]
 	Description:   

 	NOTES: 
	Revision History:
	Name						Date			Changes
	---------					----------		-------------
 	Srinivasan Ramaswamy		11/28/2012		Created
 	Iryna Politykina			02/28/2013		Add LOS_Undefined column to select, 
 												Add condition if admitted date is NULL
	Iryna Politykina			03/14/2013		Add a default value for User Full name if the user full name is NULL				
	Iryna Politykina		    03/18/2013		set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty					
	Bhanu Vanteru				03/19/2013		CR 378,081: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
	Latonia Howard				04/18/2013		Fix per CR # 379227 :Patient type, service code etc. must be identified based on code set related to facility
	Iryna Politykina			05/02/2013		Fix per CR # 375729; HPFA - The MPF_Trending_CodingDaysProductivity subset has many columns which are not needed
	MD Meersma				10/08/2013	Change localhost to use @@servername
*/

/*
	UC5938 - View Trending Coding Days Productivity User Measure

	===========================================================================================================================
	Columns						DATABASE	TABLE/VIEW				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	Encounter					HIS	 		Encounters 				Encounter Id						Encounter
 	FACILITY_NAME				CABINET		Facility_File			Facility Name						FacilityName
	Queue						CABINET		AssignmentAuditEvent	AssignmentAuditEvent				WorkflowQueueName
	UserInstanceID				CABINET		AssignmentAuditEvent	Coder								Coder
	assignmentEventDate			CABINET		AssignmentAuditEvent	Completed Date						CompletedDate
	CALCULATED FIELD												assignmentEventDate - 
																	Discharged Date 					DischargeToCompletedDays
	CALCULATED FIELD												aae.assignmentEventDate(assignment
																	'Create'/'Reassign to' date) - 
																	aae.assignmentEventDate(assignment
																	'Complete'							AssignedToCompletedDays
																	
	CALCULATED FIELD			CABINET		ENCOUNTERS				DISCHARGED date - ADMITTED date		LOS
	==============================================================================================================================
*/

CREATE PROCEDURE 
	[dbo].[MPF_Trending_CodingDaysProductivity]
	@HBI_TargetDir varchar(4000),  		-- file_path column from WebData.dbo.wt_definition_info (HBI Server)
	@TrendDate smalldatetime      		-- if null add rows to Trend Table using max(date) in Trend Table
										-- if not null use this date to remove from Trend table and repopulate
AS

BEGIN TRY

	-- 1.1 Declaration
	DECLARE 
		@xp_cmdshell TINYINT, 
		@Success INT, 
		@sqlcmd VARCHAR(2000), 
		@ABS_Target_Dir VARCHAR(255), 
		@TrendRetentionDays INT, 
		@TrendRetentionDate SMALLDATETIME

	-- 1.2 Delete entries that fall prior to retention date
	SET @TrendRetentionDays = 
		COALESCE(
			(
				SELECT 
					MPF2HBI_Parm_VALUE 
				FROM 
					[dbo].[MPF2HBI_Parameters] with (nolock)
				WHERE
					[MPF2HBI_Parm_IDENT] = -12 AND 
					[MPF2HBI_Parm_NAME] = 'MPFA.TrendingRetentionDays'
			),
			0
		)
	SET @TrendRetentionDate = (
		SELECT 
			CONVERT(
				SMALLDATETIME, 
				CONVERT(
					char(10), 
					(GETDATE()) - (@TrendRetentionDays), 
					101
				)
			)
	)

	DELETE 
		Trending_CodingDaysProductivity 
	WHERE
		[CompletedDate] < @TrendRetentionDate

	-- 1.3 Delete from trend table using optional parameter or max(date)
	IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
		SET @TrendDate = (
			SELECT CONVERT(smalldatetime,
				CONVERT(char(10), 
				(SELECT MAX([CompletedDate]) FROM Trending_CodingDaysProductivity with (nolock)) + 1, 101)))

	IF @TrendDate IS NULL or @TrendDate < @TrendRetentionDate -- no parameter and no max(date)
		SET @TrendDate = @TrendRetentionDate

	DELETE Trending_CodingDaysProductivity where [CompletedDate] >= @TrendDate

	-- 1.4 Show advanced options enable if disabled		
	IF EXISTS(
		SELECT 
			value_in_use 
		FROM 
			master.sys.configurations 
		WHERE
			name = 'show advanced options' AND 
			value_in_use = 0
	)
	
	BEGIN
		EXEC sp_configure 'show advanced options', 1
		reconfigure WITH override
	END

	-- 1.5 xp_cmdshell enable if disabled
	SELECT @xp_cmdshell = 1
	
	IF EXISTS(
		SELECT 
			value_in_use 
		FROM
			master.sys.configurations 
		WHERE 
			name = 'xp_cmdshell' AND
			value_in_use = 0)
			
	BEGIN
		SELECT @xp_cmdshell = 0
		EXEC sp_configure 'xp_cmdshell', 1
		reconfigure with override
	END

	-- 1.6 Delete all the previous TXT files incase we do not generate all of them

	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_CodingDaysProductivity.txt'
	EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

	-- 1.7 index_time can sometimes be zero
	SET arithabort OFF
	SET ansi_warnings OFF

	-- 1.8 Populate Trend Table
	INSERT INTO
		Trending_CodingDaysProductivity 
		( 
			[Encounter], 
			[FacilityName], 
			[WorkflowQueueName], 
			[Coder],
			[CompletedDate],
			[DischargeToCompletedDays],
			[AssignedToCompletedDays],
			[LOS]
		)    
		
	SELECT
		e.ENCOUNTER as Encounter, 
		COALESCE(NULLIF((SELECT TOP 1 
			FACILITY_NAME 
		FROM 
			cabinet.dbo.FACILITY_FILE 
		WITH
			(NOLOCK) 
		WHERE 
			FACILITY_CODE = e.FACILITY ), ''), COALESCE(NULLIF(e.FACILITY, ''), 'NO FACILITY')) as FacilityName,
		aae.Queue as WorkflowQueueName,
		COALESCE(u.FullName, 'User ID: ' + CAST(aae.UserInstanceId  as VARCHAR))  as Coder,
		aae.assignmentEventDate as CompletedDate,
		DATEDIFF( dd, e.Discharged, aae.assignmentEventDate ) AS DischargeToCompletedDays,
		DATEDIFF( 
			dd,
			(SELECT TOP 1  
				dd.AssignmentEventDate 
			FROM 
				cabinet.dbo.AssignmentAuditEvent dd WITH (NOLOCK)
			WHERE
				dd.ASSIGNMENT_ID = aae.ASSIGNMENT_ID AND (dd.WorkflowEventName = 'Create' or  dd.WorkflowEventName= 'Reassign To')
			),
			aae.assignmentEventDate ) AS AssignedToCompletedDays,
		CASE  WHEN E.ADMITTED IS NULL THEN 0
			ELSE
			(CASE 
				WHEN DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)= 0 THEN 1
				ELSE DATEDIFF(DAY, E.ADMITTED, E.DISCHARGED)
			END)
		END  AS LOS
	FROM 
		cabinet.dbo.AssignmentAuditEvent aae WITH (NOLOCK) 
	JOIN 
		dbo.ENCOUNTERS e WITH (NOLOCK) ON
		e.ENCOUNTER = aae.ENCOUNTER AND
		e.FACILITY = aae.FACILITY
	LEFT JOIN 
		cabinet.dbo.USERS u WITH (NOLOCK) ON 
		aae.UserInstanceId = u.UserInstanceId	
	WHERE
		aae.WorkflowEventName='Complete' AND
		aae.assignmentEventDate >= @TrendDate AND
		aae.assignmentEventDate < DateAdd(day, datediff(day,0, GETDATE()), 0) AND
		aae.QueueTypeName='Coding' AND
		e.DISCHARGED IS NOT NULL

	
	SET  @sqlcmd = ' bcp [his].[dbo].[Trending_CodingDaysProductivity] out ' + 
		rtrim(@HBI_TargetDir) + 
		'\MPF_Trending_CodingDaysProductivity.txt -c -T -S' + @@SERVERNAME

	EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
	
	select @sqlcmd

	IF @Success = 1
		RAISERROR ('bcp of Trending_CodingDaysProductivity was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	WHILE @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has date 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_CodingDaysProductivity.txt'
	EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

	DECLARE @ErrorMessage NVARCHAR(2048), @ErrorSeverity INT, @ErrorState INT

	SELECT 
        @ErrorMessage = ERROR_MESSAGE(),
        @ErrorSeverity = ERROR_SEVERITY(),
        @ErrorState = ERROR_STATE()

	INSERT 
		cabinet.dbo.SYSERRORS_GLOBAL 
		(GlobalID, Occurred, ERROR_TYPE, ERROR_DESC, [ERROR_NUMBER], [ERROR_SEVERITY], [ERROR_STATE], [ERROR_PROCEDURE], [ERROR_LINE])
		VALUES (-43, getdate(), 'SQL', ERROR_MESSAGE(), ERROR_NUMBER(), ERROR_SEVERITY(), ERROR_STATE(), ERROR_PROCEDURE(), ERROR_LINE())

	RAISERROR (@ErrorMessage, @ErrorSeverity, @ErrorState)

END CATCH
GO

grant exec on [MPF_Trending_CodingDaysProductivity] to [IMNET]
go