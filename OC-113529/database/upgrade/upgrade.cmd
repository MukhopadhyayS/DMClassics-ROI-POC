@Echo off

REM
REM BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
REM
REM Copyright © 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
REM Use of this software and related documentation is governed by a license agreement.
REM This material contains confidential, proprietary and trade secret information of
REM McKesson Information Solutions and is protected under United States
REM and international copyright and other intellectual property laws.
REM Use, disclosure, reproduction, modification, distribution, or storage
REM in a retrieval system in any form or by any means is prohibited without the
REM prior express written permission of McKesson Information Solutions.
REM
REM END-COPYRIGHT-COMMENT  Do not remove or modify this line!
REM

Rem *************************************************************
Rem * Batch File Name: Update.cmd 
Rem * Description:     	This Batch File will apply HPF upgrade
Rem *			
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
Rem *		  %1 			String		server name to upgrade.
Rem *		  %2 			String		sa password
Rem *		  
Rem * 
Rem * Script File called: upgrade_steps.cmd
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	Joseph Chen	
Rem *	MD Meersma	09/14/2009	15.0 Upgrade changes
Rem * 	MD Meersma	09/11/2013	16.1 Upgrade changes
Rem * 	MD Meersma	10/29/2013	16.2 Upgrade changes
Rem *************************************************************

rem Set some local variables with the parameter data

set LogFileName=MPF16.2_Upgrade.log

echo Starting Update.cmd file from %1 - %date% - %time%  >> %LogFileName%

:ServerName
if "%1"=="" goto ServerNameDefault
echo Setting Server Name to %1 >>%LogFileName%
set ServerName=%1
goto Password

:ServerNameDefault
echo Server Name not specified - Job Aborted >>%LogFileName%
goto Done

:Password
if "%2"=="" goto PasswordDefault
echo Setting password to user supplied password >>%LogFileName%
set Password=%2
goto upgradestatus

:upgradestatus
if "%3"=="" goto job
if "%3"=="y" goto manualupgrade
if "%3"=="Y" goto manualupgrade

echo Your third parameter is invalid >>%LogFileName%
goto Done

:PasswordDefault
echo Password not specified - Job Aborted >>%LogFileName%
goto Done


:job
sqlcmd -S %1 -U sa -P %2 -h-1 -w110 -i check_current_version.sql -v OVERRIDE = N LOGFILENAME = %LogFileName% -o check_current_version.sol

IF not ERRORLEVEL 1 GOTO Upgrade
type check_current_version.sol  >>%LogFileName%
type check_current_version.sol
goto Done

:manualupgrade
sqlcmd -S %1 -U sa -P %2 -h-1 -w110 -i check_current_version.sql -v OVERRIDE = Y LOGFILENAME = %LogFileName% -o check_current_version.sol 

IF not ERRORLEVEL 1 GOTO Upgrade
type check_current_version.sol  >>%LogFileName%
type check_current_version.sol
goto Done

:Upgrade
echo Running the SQL scripts... >>%LogFileName%

if exist master\nul call db_upgrade %ServerName% master sa %Password%
if exist audit\nul call db_upgrade %ServerName% audit sa %Password%
if exist cabinet\nul call db_upgrade %ServerName% cabinet sa %Password%
if exist eiwdata\nul call db_upgrade %ServerName% eiwdata sa %Password%
if exist his\nul call db_upgrade %ServerName% his sa %Password%
if exist MCK_HPF\nul call db_upgrade %ServerName% MCK_HPF sa %Password%
if exist wfe\nul call db_upgrade %ServerName% wfe sa %Password%

:Done
echo Database Upgrade complete check the log file for errors >>%LogFileName%
echo Database Upgrade complete check the log file for errors
echo Recommended to search for the string ', level' in the log file to find errors

Rem End of upgrade.cmd

