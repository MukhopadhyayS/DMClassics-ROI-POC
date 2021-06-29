
@echo on
rem	******************************************************************************************
rem	*
rem	*	UPGRADE_steps.CMD
rem	*	
rem	*	This batch file is run from command to upgrade database to HPF13.5 release.
rem	*
rem	*	Created	 06/29/2005	Joseph Chen 
rem	*		 01/25/2006	Joseph Chen
rem	*				Modified for HPF 11.0  
rem	*		 01/12/2007	Judy Jiang
rem	*				Modified for HPF 13.0  
rem	*		 06/04/2009	RC
rem	*				Modified for HPF 13.5  
rem	******************************************************************************************
rem
rem	This batch file will do the following:	
rem	
rem	This batch file takes the following arguments:
rem		1. The name of the database server
rem		2. The sa password - cannnot be null


if EXIST upgrade.log attrib -r upgrade.log
echo ------------------------------------------------------ >>HPF13.5_Upgrade.log
echo *** Your database log file resides in C:\HPF13.5_Upgrade.log. *** >>HPF13.5_Upgrade.log
echo 
echo Starting Upgrade.cmd file - %date% - %time%              >>HPF13.5_Upgrade.log

sqlcmd -U sa -d master -P %2 -S %1 -Q "select 'Run Date - ', convert(varchar(30),getdate()) "  >>HPF13.5_Upgrade.log

echo ------------------------------------------------------ >>HPF13.5_Upgrade.log
rem Set some local variables with the parameter data

:ServerName
if "%1"=="" goto ServerNameDefault
echo Setting Server Name to %1 >>HPF13.5_Upgrade.log
set ServerName=%1
goto Password

:ServerNameDefault
echo Server Name not specified - Job Aborted >>HPF13.5_Upgrade.log
goto Done

:Password
if "%2"=="" goto PasswordDefault
echo Setting password to user supplied password >>HPF13.5_Upgrade.log
set Password=%2
goto start

:PasswordDefault
echo Password not specified - Job Aborted >>HPF13.5_Upgrade.log
goto done


:Start

dir





echo Running the SQL scripts... >>HPF13.5_Upgrade.log

if exist master\nul call db_upgrade %ServerName% master sa %Password%
if exist wfe\nul call db_upgrade %ServerName% wfe sa %Password%
if exist audit\nul call db_upgrade %ServerName% audit sa %Password% 
if exist eiwdata\nul call db_upgrade %ServerName% eiwdata sa %Password% 

if exist cabinet\nul call db_upgrade %ServerName% cabinet sa %Password% 
if exist his\nul call db_upgrade %ServerName% his sa %Password% 






echo HPF Databases installation completed - %date% - %time%  >>HPF13.5_Upgrade.log

goto Done


:Done