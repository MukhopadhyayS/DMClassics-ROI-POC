USE [his]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MPF_Daily_FaxingUtilization]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[MPF_Daily_FaxingUtilization]
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
* Procedure Name: [MPF_Daily_FaxingUtilization]
* Description:   
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Bhanu Vanteru			11/07/2012	Created
*	MD Meersma				11/30/2012	Code Review
*   Latonia Howard          03/06/2013	set Encounter to  'Global' if MRN is null, set User Department as 'Undefined' if  null, added "Undefined" and "ALL Documents" conditions for DocumentType 
*   Iryna Politykina		03/15/2013  set Facility Name to facilityCode , if facilityname is null or empty,set to ''no facility' if facilitycode is empty
*	Srinivasan Ramaswamy	04/19/2013 	CR#378,080 - MPFA: Issue in Daily utilization measures when document type contains " - "; Fix --> if audit remark contain '-' use the ExtractDocumentType function
*	MD Meersma				10/08/2013	Change localhost to use @@servername
****************************************************************
*/
/*
	UC5952 - View Daily Faxing Utilization Measure

	Utilization of MPF Faxing
	
	===========================================================================================================================
	Columns						DATABASE	TABLE/VIEW				DESCRIPTION							NAME_USED_IN_SCRIPT		
	===========================================================================================================================
	ENCOUNTER					AUDIT		AUDIT_TRAIL				Encounter							Encounter
	MRN							AUDIT		AUDIT_TRAIL				MRN									MRN
	Name						HIS			PATIENTS				Patient Name						PatientName
	Occurred					AUDIT		AUDIT_TRAIL				Datetime when Faxing				Occurred
																	event occurred
	FullName					CABINET		Users					User Name							Username
	Description					CABINET		Users					User Description					UserDescription
	DEPT_NAME					CABINET		DEPT_FILE				User Department						UserDepartment
	FACILITY_NAME				CABINET		Facility_File			Facility Name						FacilityName
	REMARK						AUDIT		AUDIT_TRAIL				If remark like 'HPFW%'				DocumentType
																	then doc_type = Cast(SUBSTRING(AUDIT_TRAIL.remark, 7,7000) as varchar(7168))
																	else doc_type = (LEFT(AUDIT_TRAIL.remark, CHARINDEX(' - ', AUDIT_TRAIL.remark)) as varchar(7168))
	USER_ID						CABINET		User_Security			Total number of users in a			TotalUsersWithAccess
																	facility who have access to 
																	fax documents
	==============================================================================================================================
*/


CREATE PROCEDURE [dbo].[MPF_Daily_FaxingUtilization]
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

SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_FaxingUtilization.txt'
EXEC @Success =  master..xp_cmdshell @sqlcmd, NO_OUTPUT

IF OBJECT_ID('tempdb..##HBI_SUBSET_DAILY_FAXINGUTILIZATION') IS NOT NULL
DROP TABLE ##HBI_SUBSET_DAILY_FAXINGUTILIZATION

SELECT
    COALESCE(NULLIF(aud_trail.ENCOUNTER,''),(CASE WHEN aud_trail.MRN is NULL THEN 'Undefined' ELSE 'GLOBAL' END)) as [Encounter],
	COALESCE(NULLIF(aud_trail.MRN,''),'Undefined') as [MRN],
	COALESCE(NULLIF((select name from [his].[dbo].[patients] with (nolock) where mrn = aud_trail.mrn and FACILITY = aud_trail.FACILITY), ''), 'Undefined') as [PatientName],
	aud_trail.occurred AS [Occurred],
	COALESCE(NULLIF(users.FullName,''),'Undefined') as [Username],
	COALESCE(NULLIF(users.Description,''),'Undefined') as [UserDescription],
	COALESCE( (select dept_name from [cabinet].[dbo].[dept_file] with (nolock) where dept_code = users.DEPT_CODE) , 'Undefined' ) as [UserDepartment],
	COALESCE(NULLIF((select facility_name from [cabinet].[dbo].[facility_file] with (nolock) where facility_code = aud_trail.facility), ''),
				 COALESCE(NULLIF(aud_trail.facility, ''), 'NO FACILITY')) as [FacilityName],
	
	[DocumentType] = 
		CASE
			WHEN aud_trail.remark like 'HPFW:%'
				THEN CAST(SUBSTRING(aud_trail.remark, 7, 7000) as VARCHAR(7168))
			WHEN aud_trail.remark like 'View%:%'
				THEN 'ALL Documents'				
			WHEN CHARINDEX(' - ',aud_trail.remark) > 0
				THEN His.dbo.fnExtractDocumentType(aud_trail.remark, aud_trail.FACILITY)
			ELSE 'Undefined'
		END,
	[TotalUsersWithAccess] = 
		CASE
			WHEN ROW_NUMBER() over (partition by Facility order by occurred) = 1
				THEN (select count(distinct(us.user_id)) 
					from [cabinet].[dbo].[User_Security] us  with (nolock)
					join [cabinet].[dbo].[USERS] u with (nolock) on us.USER_ID = u.UserInstanceId and u.AccountDisabled = 0
					where SECURITY_ID=3100 and FACILITY = aud_trail.FACILITY)
			ELSE 0
		END
into ##HBI_SUBSET_DAILY_FAXINGUTILIZATION
from [audit].[dbo].[AUDIT_TRAIL] aud_trail with (nolock)
left outer join [cabinet].[dbo].[USERS] users with (nolock)
on aud_trail.UserInstanceId = users.UserInstanceId
where 
aud_trail.OCCURRED >= DATEADD(day,datediff(day,1,getdate()),0) and OCCURRED < DATEADD(day,datediff(day,0,getdate()),0) and
aud_trail.ACTION = 'F'

SET  @sqlcmd = ' bcp ##HBI_SUBSET_DAILY_FAXINGUTILIZATION out ' + rtrim(@HBI_TargetDir) + '\MPF_Daily_FaxingUtilization.txt -c -T -S' + @@SERVERNAME

EXEC @Success =  master..xp_cmdshell @sqlcmd --, NO_OUTPUT

IF @Success = 1
	RAISERROR ('bcp of ##HBI_SUBSET_DAILY_FAXINGUTILIZATION was not successful', 10, 1)

END TRY

BEGIN CATCH  -- There was an error

	while @@TRANCOUNT > 0
	ROLLBACK TRANSACTION 

	-- Delete all the previous TXT files as an error has occurred 
	SET  @sqlcmd = 'del ' + @HBI_TargetDir + '\MPF_Daily_FaxingUtilization.txt'
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

GRANT exec on [dbo].[MPF_Daily_FaxingUtilization] to [IMNET]
go