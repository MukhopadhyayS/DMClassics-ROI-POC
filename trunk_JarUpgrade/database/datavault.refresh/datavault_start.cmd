
Rem ****************************************************************
Rem *  	Copyright © 2008 McKesson Corporation and/or one of its subsidiaries. 
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
Rem * Batch File Name: datavault_start.cmd 
Rem * Description:    This Batch File will insert the records from Reference database to ROI Database
Rem *			
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
Rem *		  %1 			String		ROI server name .
Rem *		  %2 			String		ROI sa password
Rem *		  %3 			String		Reference server name .
Rem *		  %4			String		Reference sa password
Rem *		  
Rem *		
Rem * 
Rem * 
Rem * Script File called: datavault_process.cmd
Rem * NOTES: 
Rem *************************************************************

rem Set some local variables with the parameter data

set LogFile=logs\roi.datavault.log

echo Starting datavault_start.cmd file from %1 - %date% - %time% >>%LogFile%

:ServerName
if "%1"=="" goto ServerNameDefault
echo Setting Server Name to %1 >>%LogFile%
set ServerName=%1
goto Password

:ServerNameDefault
echo Reference Server Name not specified - Job Aborted >>%LogFile%
goto Done

:Password
if "%2"=="" goto PasswordDefault
echo Setting password to user supplied password >>%LogFile%
set Password=%2
goto RefServerName

:PasswordDefault
echo Password not specified - Job Aborted >>%LogFile%
goto Done

:RefServerName
if "%3"=="" goto RefServerNameDefault
echo Setting Reference Server Name to %3 >>%LogFile%
set RefServerName=%3
goto RefPassword

:RefServerNameDefault
echo Reference Server Name not specified - Job Aborted >>%LogFile%
goto Done

:RefPassword
if "%4"=="" goto RefPasswordDefault
echo Setting Reference server password to user supplied password >>%LogFile%
set RerServerPassword=%4
goto :exec_datavault


:RefPasswordDefault
echo Reference Password not specified - Job Aborted >>%LogFile%
goto Done


:exec_datavault
call datavault_process.cmd %ServerName% %Password% %RefServerName% %RerServerPassword%
goto Done

:Done
echo Datavault Process - Completed >>%LogFile%


