Title ROI Migration 
Color 1e
prompt McKesson ROI Migration $F
cls
@Echo off

ECHO *************************************************************
ECHO
ECHO Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
ECHO Use of this software and related documentation is governed by a license agreement.
ECHO This material contains confidential, proprietary and trade secret information of
ECHO McKesson Information Solutions and is protected under United States
ECHO and international copyright and other intellectual property laws.
ECHO Use, disclosure, reproduction, modification, distribution, or storage
ECHO in a retrieval system in any form or by any means is prohibited without the
ECHO prior express written permission of McKesson Information Solutions.
ECHO
ECHO *************************************************************

echo    * Batch File Name: ROI_Migration.cmd 
echo    * Description:     This cmd File will migrate pre HPF13.X ROI to HPF13.X
echo    *			
echo    *
echo    *		 Order			Type		Description
echo    *		 ---------		-------		----------------
echo    * Params In:
echo    *		  1 			String		server name to upgrade.
echo    *		  2 			String		user
echo    *		  3 			String		password
echo    *		
echo    *    
echo    * 
echo    * Database Used:	Cabinet
echo    * Script File called:	various
echo    * NOTES: 
echo    * Revision History:
echo    *	Name		Date		ChangeType/Version/Track#
echo    *	---------	-------		--------------------------
echo    * 	
echo    * 	RC		04/02/2009	created
echo    * 	
echo    * 	RC		06/10/2009	added user	
echo    *************************************************************

if EXIST ROI_Migration.log attrib -r ROI_Migration.log

echo Starting ROI_Migration.cmd from %ServerName% - %date% - %time%  
echo Starting ROI_Migration.cmd from %ServerName% - %date% - %time%  >>ROI_Migration.log

:ServerName
if "%1"=="" goto ServerNameDefault
echo Setting Server Name to %1 
echo Setting Server Name to %1 >>ROI_Migration.log
set ServerName=%1
goto Username

:ServerNameDefault
echo Server Name not specified - Job Aborted 
echo Server Name not specified - Job Aborted >>ROI_Migration.log
goto Finished

:Username
if "%2"=="" goto usernameDefault
echo Setting username to user supplied username 
echo Setting username to user supplied username >>ROI_Migration.log
set Username=%2
goto Password

:usernameDefault
echo username not specified - Job Aborted 
echo username not specified - Job Aborted >>ROI_Migration.log
goto Finished

:Password
if "%3"=="" goto PasswordDefault
echo Setting password to user supplied password 
echo Setting password to user supplied password >>ROI_Migration.log
set Password=%3
goto SystemCheck

:PasswordDefault
echo Password not specified - Job Aborted 
echo Password not specified - Job Aborted >>ROI_Migration.log
goto Finished

:SystemCheck
REM Checking if the cabinet is the correct version
echo Checking for the ROI_Main table 
echo Checking for the ROI_Main table >>ROI_Migration.log
sqlcmd -S %1 -d Cabinet -U %2 -P %3 -Q "EXIT(SET NOCOUNT ON IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[CABINET].[dbo].[ROI_RequestMain]') AND type in (N'U')) BEGIN  select 10 END ELSE BEGIN SELECT -10 END)"
REM echo %errorlevel%
GOTO SystemCheck%errorlevel% 

:SystemCheck10
echo The target system has a ROI_Main table
echo Checking for final migration 
echo Checking for final migration >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 -Q "EXIT(SET NOCOUNT ON IF NOT EXISTS (Select * from master.dbo.EPRS_Version where DB_NAME = 'ROI_Migration' and DB_VERSION = 'Final')BEGIN  select 20 END ELSE BEGIN SELECT -20 END)"
GOTO SystemCheck%errorlevel% 
GOTO Finished

:SystemCheck-10
echo The target system does not have a ROI_Main table 
echo The target system does not have a ROI_Main table >>ROI_Migration.log
GOTO Finished

:SystemCheck-20
echo The final migration has been performed 
echo The final migration has been performed >>ROI_Migration.log
PAUSE
GOTO Finished

:SystemCheck20
echo The system reports the final migration has NOT been performed.
echo The system reports the final migration has NOt been performed. >>ROI_Migration.log

echo Creating the ROI_MigrationLOV table if required.
echo Creating the ROI_MigrationLOV table if required. >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationLOV.sql" -m0  >>ROI_Migration.log

echo ------------------------------------------------------ 
echo ------------------------------------------------------ >>ROI_Migration.log
echo Your database log file resides in %cd%
echo Your database log file resides in %cd%  >>ROI_Migration.log
echo Your computer date and time is
echo Your computer date and time is              >>ROI_Migration.log
echo %date% - %time%
echo %date% - %time%              >>ROI_Migration.log

echo The sql computer date and time is
echo The sql computer date and time is              >>ROI_Migration.log
sqlcmd -U %2 -d master -P %3 -S %1 -h -1 -Q "set nocount on select 'Run Date - ', convert(varchar(30),getdate()) "  
sqlcmd -U %2 -d master -P %3 -S %1 -h -1 -Q "set nocount on select 'Run Date - ', convert(varchar(30),getdate()) "  >>ROI_Migration.log
echo ------------------------------------------------------ 
echo ------------------------------------------------------ >>ROI_Migration.log
echo The system has passed readiness checks. Press CTRL-C to stop now or
Pause

:Menu
cls
echo.
echo The system has passed readiness checks.
echo.
echo Select an item by number to proceed
echo.
echo 1 Migrate Requests
echo 2 Prepare Requesters for clean-up
echo 3 Import Requesters
echo 4 Migrate Requesters
echo 5 Finalize migration
echo 6 Remove all previously migrated data
echo 7 Quit
echo 8 Clear the log file
echo 9 View the log file
Choice /C 123456789
echo UserChoice%errorlevel%... >>ROI_Migration.log
GOTO UserChoice%errorlevel%

:UserChoice9
GOTO ShowLog


:UserChoice8
GOTO RemoveLog

:Userchoice7
GOTO Finished

:UserChoice6
GOTO RemoveMigratedData

:UserChoice5
GOTO FinalizeMigration

:UserChoice4
GOTO MigrateRequester

:Userchoice3
GOTO ImportRequesters

:Userchoice2
GOTO PrepareRequesters

:UserChoice1
GOTO MigrateRequest


REM place the code for below
:RemoveLog
if EXIST ROI_Migration.log del ROI_Migration.log
GOTO MENU

:ShowLog
CALL ROI_Migration.log
GOTO MENU

:RemoveMigratedData
echo Generating record count for ROI_Migration
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

echo Removing any previously migrated classic request data
echo Removing any previously migrated classic request data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_RemoveMigratedClassicRequestData.sql" -m0  >>ROI_Migration.log

echo Removing any previously migrated classic requester data
echo Removing any previously migrated classic requester data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_RemoveMigratedClassicRequesterData.sql" -m0  >>ROI_Migration.log

echo Generating record count for ROI_Migration 
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

REM echo Removing migrated data
REM echo Removing migrated data... >>ROI_Migration.log
REM sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRemoveMigratedData.sql" -m0  >>ROI_Migration.log
CALL ROI_Migration.log
GOTO MENU

:PrepareRequesters
REM Prepare Requesters for clean-up
echo Copying data from the Requester table to RequesterEdited
echo Copying data from the Requester table to RequesterEdited >>ROI_Migration.log 
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationPrepareRequesterForExport.sql" -m0  >>ROI_Migration.log
sqlcmd -S %1 -d Cabinet -U %2 -P %3 -Q "EXIT(Select COUNT(*) from ROI_RequesterEdited)"
echo Rows transfered = %errorlevel%
pause
GOTO Menu


:FinalizeMigration
echo You have selected to finalize the migration. Finalizing the migration
echo signals a complete and correct migration has been performed! No migration
echo will be performed during this run and future migrations will be disabled. 
echo Are you sure? 
echo Press Ctrl-C to stop or
Pause
sqlcmd /S %1 /U %2 /P %3 /d cabinet -i "ROI_MigrationFinalization.sql" -m0 >>ROI_Migration.log
DIR *.sql
REM DEL *.sql /F /Q   >>ROI_Migration.log
echo Final Migration 
GOTO Finished


:MigrateRequester

echo Removing any previously migrated classic requester data
echo Removing any previously migrated classic requester data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_RemoveMigratedClassicRequesterData.sql" -m0  >>ROI_Migration.log

echo Generating record count for ROI_Migration 
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

REM not needed for requester
REM echo Creating required views and tables for migration
REM echo Creating required views and tables for migration... >>ROI_Migration.log
REM sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationCreateViewsTables.sql" -m0  >>ROI_Migration.log

REM administration tables are out of scope for migration
REM echo Migrating the administration tables
REM echo Migrating the administration tables... >>ROI_Migration.log
REM sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationAdminTables.sql" -m0  >>ROI_Migration.log

echo Migrating the ROI Requesters 
echo Migrating the ROI requesters... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRequester.sql" -m0  >>ROI_Migration.log

echo Linking migrated and classic requester data
echo Linking migrated and classic requester data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_LinkingMigratedAndClassicRequesterData.sql" -m0  >>ROI_Migration.log

echo Generating record count for ROI_Migration 
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

CALL ROI_Migration.log
GOTO Menu


:ImportRequesters
echo Importing the edited ROI Requesters 
echo Importing the edited ROI Requester... >>ROI_Migration.log
sqlcmd /S %1 /U %2 /P %3 /d CABINET /i "ROI_MigrationPrepareRequesterForImport.sql" -m0  >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationImportRequesterEdited.sql" -m0  >>ROI_Migration.log
CALL ROI_Migration.log
goto Menu




:MigrateRequest
echo The migration of requests is ready to begin. 
echo The migration of requests is ready to begin. >>ROI_Migration.log 
echo Running the SQL scripts
echo Running the SQL scripts... >>ROI_Migration.log

echo Removing any previously migrated classic request data
echo Removing any previously migrated classic request data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_RemoveMigratedClassicRequestData.sql" -m0  >>ROI_Migration.log

echo Generating record count for ROI_Migration 
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

echo Creating required views and tables for migration
echo Creating required views and tables for migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationCreateViewsTables.sql" -m0  >>ROI_Migration.log

echo Migrating the ROI requests 
echo Migrating the ROI requests... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRequestData.sql" -m0  >>ROI_Migration.log

echo Migrating the ROI delivery 
echo Migrating the ROI delivery... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationDeliveryData.sql" -m0  >>ROI_Migration.log

echo Migrating the ROI Invoice and Payments 
echo Migrating the ROI Invoice and Payments... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationInvoiceData.sql" -m0  >>ROI_Migration.log

echo Creating the ROI Events 
echo Creating the ROI Events... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationEvents.sql" -m0  >>ROI_Migration.log

echo Creating the ROI Search LOV 
echo Creating the ROI Search LOV... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationSearchLOV.sql" -m0  >>ROI_Migration.log

echo Linking migrated and classic request data
echo Linking migrated and classic request data... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_LinkingMigratedAndClassicRequestData.sql" -m0  >>ROI_Migration.log

echo Generating record count for ROI_Migration 
echo Generating record count for ROI_Migration... >>ROI_Migration.log
sqlcmd /S %1 /d CABINET /U %2 /P %3 /i "ROI_MigrationRecordCount.sql" -m0  >>ROI_Migration.log

echo ROI Migration Requests completed - %date% - %time% 
echo ROI Migration Requests completed - %date% - %time%  >>ROI_Migration.log
echo.
echo.
Call ROI_Migration.log
goto Menu

REM Finshed MUST the the last item
:Finished
CLS
Title cmd
Prompt $p$g
color 07
Start ROI_Migration.Log
