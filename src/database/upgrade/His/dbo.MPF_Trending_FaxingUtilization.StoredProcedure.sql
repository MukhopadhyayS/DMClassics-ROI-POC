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
 	Procedure Name: [MPF_Trending_FaxingUtilization]
 	Description:   

 	NOTES: 
	Revision History:
	Name						Date			Changes
	---------					----------		-------------
 	Srinivasan Ramaswamy		11/28/2012		Created
	Srinivasan Ramaswamy		02/01/2013		Replace Total Users with UserinstanceId
	Srinivasan Ramaswamy		03/05/2013		Made Total Users as Active only	
	Bhanu Vanteru				03/05/2013		CR 377,262: MPFA 16.0 Trending Utilization Viewing Scorecard reflects today's date in the result
	Iryna Politykina			03/15/2013		set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
	Bhanu Vanteru				03/22/2013		CR 377,476: Made Total Users with Access as Active only
	Srinivasan Ramaswamy		04/18/2013		CR#-379,970 Total users in trending utilzation measures should be per facility
	Srinivasan Ramaswamy		05/09/2013		While counting users with Fax Access from 'User_Security' table, query now ensures that that user is present in 'User_Facility' table as well
								05/09/2013		Total Users for a facility is picked up from 'User_Facility' table instead of 'User_Security' table
	MD Meersma				10/08/2013	Change localhost to use @@servername
*/

/*
	UC5956 - View Trending Faxing Utilization User Measure

	===========================================================================================================================
	Columns						DATABASE	TABLE/VIEW				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	FACILITY_NAME		CABINET		Facility_File			Facility Name								FacilityName
	Occurred			AUDIT		AUDIT_TRAIL				Datetime when Faxing						date
	UserInstanceId		AUDIT		AUDIT_TRAIL				User who Faxed 								UserInstanceId
	USER_ID				CABINET		User_Security			Total number of users in a	facility		TotalUsersWithAccess
															who have access to print documents
	UserInstanceId		CABINET		Users					Total number of users						TotalUsers																														
	==============================================================================================================================
*/

/*
	STEP 1
	------	
	Create an entry for the number of days that we want to retain trending data
	anything prior to that will be removed when the script is run
*/

	USE [HIS]
	GO

	IF EXISTS ( 
		SELECT * 
		FROM 
			sys.objects 
		WHERE 
			object_id = OBJECT_ID(N'[dbo].[MPF_Trending_FaxingUtilization]') AND 
			type in (N'P')
	)
	DROP PROCEDURE [dbo].[MPF_Trending_FaxingUtilization]
	GO



	CREATE PROCEDURE 
		[dbo].[MPF_Trending_FaxingUtilization]
		@HBI_TargetDir varchar(4000),  		-- file_path column from WebData.dbo.wt_definition_info (HBI Server)
		@TrendDate smalldatetime      		-- if null add rows to Trend Table using max(date) in Trend Table
											-- if not null use this date to remove from Trend table and repopulate
	AS

	BEGIN TRY

		-- 3.1 Declaration
		DECLARE 
			@xp_cmdshell TINYINT, 
			@Success INT, 
			@sqlcmd VARCHAR(2000), 
			@ABS_Target_Dir VARCHAR(255), 
			@TrendRetentionDays INT, 
			@TrendRetentionDate SMALLDATETIME

		-- 3.2 Delete entries that fall prior to retention date
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
			Trending_FaxingUtilization 
		WHERE
			[date] < @TrendRetentionDate

		-- 3.3 Delete from trend table using optional parameter or max(date)
		IF @TrendDate is null -- if null add rows to Trend Table using max(date) in Trend Table
			SET @TrendDate = (
				SELECT CONVERT(smalldatetime,
					CONVERT(char(10), 
					(SELECT MAX([DATE]) FROM Trending_FaxingUtilization with (nolock)) + 1, 101)))

		IF @TrendDate IS NULL or @TrendDate < @TrendRetentionDate -- no parameter and no max(date)
			SET @TrendDate = @TrendRetentionDate

		DELETE Trending_FaxingUtilization where [date] >= @TrendDate

		-- 3.4 Show advanced options enable if disabled		
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

		-- 3.5 xp_cmdshell enable if disabled
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

		-- 3.6 Delete all the previous TXT files incase we do not generate all of them

		SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_FaxingUtilization.txt'
		EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

		-- 3.7 index_time can sometimes be zero
		SET arithabort OFF
		SET ansi_warnings OFF

		-- 3.8 Populate Trend Table
		INSERT INTO
			Trending_FaxingUtilization 
			( 
				[FacilityName], 
				[date], 
				[UserInstanceId], 
				[TotalUsersWithAccess],
				[TotalUsers]
			)    
			
			SELECT			
				[FacilityName] = COALESCE(NULLIF((select facility_name from [cabinet].[dbo].[facility_file] with (nolock) where facility_code = aud_trail.facility), ''),
								 COALESCE(NULLIF(aud_trail.facility, ''), 'NO FACILITY')),
				[Date] = DateAdd(day, datediff(day,0, Occurred), 0),
				[UserInstanceId] = aud_trail.UserInstanceId,
				[TotalUsersWithAccess] = ( 
					SELECT
						COUNT(DISTINCT(us.user_id)) 
					FROM
						[cabinet].[dbo].[User_Security] us WITH (nolock) JOIN
						[cabinet].[dbo].[User_Facility] uf WITH (nolock) ON
						uf.USER_ID = us.USER_ID JOIN
						[cabinet].[dbo].[USERS] u WITH (nolock) ON
						us.USER_ID = u.UserInstanceId AND
						u.AccountDisabled = 0
					WHERE
						SECURITY_ID = 3100 AND
						us.FACILITY = aud_trail.FACILITY
				),
				[TotalUsers] = ( 
					SELECT 
						COUNT(DISTINCT(uf.user_id)) 
					FROM
						[cabinet].[dbo].[User_Facility] uf  WITH (NOLOCK) JOIN 
						[cabinet].[dbo].[USERS] u WITH (NOLOCK) ON
						uf.USER_ID = u.UserInstanceId AND 
						u.AccountDisabled = 0
					WHERE
						FACILITY = aud_trail.FACILITY
				)
			FROM 
				[audit].[dbo].[AUDIT_TRAIL] aud_trail WITH (nolock) JOIN
				[cabinet].[dbo].[USERS] users with (nolock) ON
				aud_trail.UserInstanceId = users.UserInstanceId
			WHERE 
				aud_trail.occurred >= @TrendDate AND
				aud_trail.OCCURRED < DateAdd(day, datediff(day,0, GETDATE()), 0) AND
				aud_trail.ACTION = 'F'	
			GROUP BY
				FACILITY, DateAdd(day, datediff(day,0, Occurred), 0), aud_trail.UserInstanceId
		
		SET  @sqlcmd = ' bcp [his].[dbo].[Trending_FaxingUtilization] out ' + 
			rtrim(@HBI_TargetDir) + 
			'\MPF_Trending_FaxingUtilization.txt -c -T -S' + @@SERVERNAME

		EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT
		
		select @sqlcmd

		IF @Success = 1
			RAISERROR ('bcp of Trending_FaxingUtilization was not successful', 10, 1)

	END TRY

/*
	STEP 4
	------
	Handle error
*/
	
	BEGIN CATCH  -- There was an error

		WHILE @@TRANCOUNT > 0
		ROLLBACK TRANSACTION 

		-- Delete all the previous TXT files as an error has date 
		SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Trending_FaxingUtilization.txt'
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

grant exec on [MPF_Trending_FaxingUtilization] to [IMNET]
go