
Rem ****************************************************************
Rem *  	Copyright © 2007 McKesson Corporation and/or one of its subsidiaries. 
Rem *		All rights reserved. 
Rem *
Rem * 	The copyright to the computer program(s) herein
Rem * 	is the property of McKessonHBOC. The program(s)
Rem * 	may be used and/or copied only with the written
Rem * 	permission of McKessonHBOC or in accordance with
Rem * 	the terms and conditions stipulated in the     
Rem * 	agreement/contract under which the program(s)
Rem * 	have been supplied.
Rem *************************************************************
Rem * Batch File Name: Update.cmd 
Rem * Description:     	This Batch File will apply upgrade HPF from 13.X to 13.50
Rem *			
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
Rem *		  %1 			String		server name to upgrade.
Rem *		  %2 			String		sa password
Rem *		  
Rem *		
Rem * 
Rem * 
Rem * 
Rem * 
Rem *    
Rem * 
Rem * Database Used:	master, Audit, Cabinet, Eiwdata, His, Wfe.
Rem * Script File called: upgrade_steps.cmd
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	Joseph Chen	
Rem * 	
Rem *************************************************************

rem Set some local variables with the parameter data

echo Starting Update.cmd file from %1 - %date% - %time%  >>HPF13.5_Upgrade.log

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
goto upgradestatus

:upgradestatus
if "%3"=="" goto job
if "%3"=="y" goto manualupgrade
if "%3"=="Y" goto manualupgrade
echo Your third parameter is invalid >>HPF13.5_Upgrade.log
goto Done



:PasswordDefault
echo Password not specified - Job Aborted >>HPF13.5_Upgrade.log
goto Done


:job
sqlcmd -S %1 -U sa -P %2 -h-1 -w110 -i check_current_version.sql -o exec_upgrade.bat 

IF not ERRORLEVEL 1 GOTO autoupgrade
type exec_upgrade.bat  >>HPF13.5_Upgrade.log
goto Done



:autoupgrade
if exist exec_upgrade.bat (call exec_upgrade.bat %1 %2) 
goto Done


:manualupgrade
call upgrade_steps.cmd %1 %2 
goto Done



:Done
Rem End of upgrade.cmd

