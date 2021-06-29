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
Rem * Command File Name: db_upgrade.cmd  
Rem * Description: Command file to execute all upgrade scripts for a given database
Rem *		   Assumes that a subdirectory exists for each database name, one level
Rem *		   below the current dir
Rem *		   Run this from the top-level upgrade script directory
Rem *		   still needs some parameter validation/defaults
Rem *		   and error handling
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
rem *		  %1 			String		server name
rem *		  %2 			String		database name
rem *		  %3			String		user name (must be sa)
rem *		  %4			String		user pwd
Rem *		
Rem * Script File called: 
Rem * NOTES: 
Rem * Revision History:
Rem *	Name			Date		Changes/Version&Build/ClearQuest#
Rem *	---------		----------	----------------------
Rem *	M. Alahyar		01/06/04	created
REM *	Joseph Chen		04/27/04	Remove -r from command file
REM *	Judy Jiang		06/08/04	Updated/HPF ANSI Multi-facility 06.02.00/:
REM *						Add *.TB2.
REM *	Judy Jiang		06/10/04	Updated/HPF ANSI Multi-facility 06.02.00/:Add *.TB3.
REM *   Joseph Chen		08/10/05	Change log location to c:/HPF11.0_Upgrade.log
REM *	MD Meersma		09/11/09	SQL 05/08 Changes 
REM *   Joseph Chen		07/2012		new upgrade 
REM *	MD Meersma		10/24/2012	Restructure to match HPF 16 DBINIT
REM *	MD Meersma		09/11/2013	Remove SQL Server Version and Edition parameters
Rem *************************************************************


chdir %2

echo. 
echo. >> ..\%LogFileName%

echo Applying %2 database scripts
echo Applying %2 database scripts >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

echo SQL Logins
echo SQL Logins >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.login) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.alterlogin) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Schemas
echo Schemas >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.schema) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Users
echo Users >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.user) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Roles
echo Roles >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.role) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Tables
echo Tables >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.tbl) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb2) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb3) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb4) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb5) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb6) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb7) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb8) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.tb9) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Views
echo Views >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.vw) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal
setlocal enabledelayedexpansion
for %%f in (*.vw2) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo Triggers
echo Triggers >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.trg) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo SQL scripts
echo SQL scripts >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.sql) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal


echo. 
echo. >> ..\%LogFileName%

echo SQL Jobs
echo SQL Jobs >> ..\%LogFileName%

echo. 
echo. >> ..\%LogFileName%

setlocal enabledelayedexpansion
for %%f in (*.job) do (
echo %%f
sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >> ..\%LogFileName%)
endlocal

chdir ..