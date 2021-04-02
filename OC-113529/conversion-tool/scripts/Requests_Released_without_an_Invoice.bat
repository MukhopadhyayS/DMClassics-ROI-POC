@echo off 
rem #
rem #    Copyright (C) 2007 McKesson Corporation and/or one of its
rem #    subsidiaries. All Rights Reserved.
rem #
rem #    Use of this material is governed by a license agreement.
rem #    This material contains confidential, proprietary and trade
rem #    secret information of McKesson Information Solutions and is
rem #    protected under United States and international copyright and
rem #    other intellectual property laws. Use, disclosure,
rem #    reproduction, modification, distribution, or storage in a
rem #    retrieval system in any form or by any means is prohibited
rem #    without the prior express written permission of McKesson
rem #    Information Solutions.
rem # 

ECHO  ------------------------------------------
ECHO  --  ROI migration tool script start     --
ECHO  ------------------------------------------

ECHO Please Enter Database Server Name:
SET /p serverName=

IF "%serverName%"=="" GOTO NOSERVERNAME

ECHO Please Enter Database User Name:
SET /p userLoginName=

IF "%userLoginName%"=="" GOTO NOUSERNAME

ECHO Please Enter Database Password:
SET /p userPassword=

IF "%userPassword%"=="" GOTO NOPASSWORD

ECHO Processing...
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i Requests_Released_Without_an_Invoice.sql -o Requests_Released_Without_an_Invoice.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
GOTO SUCCESS

:NOSERVERNAME 
ECHO No server name was entered. Please try again.
GOTO FAILED

:NOUSERNAME 
ECHO No user name was entered. Please try again.
GOTO FAILED

:NOPASSWORD 
ECHO No password was entered. Please try again.
GOTO FAILED

:FAILED
ECHO  ------------------------------------------
ECHO  --  ROI migration tool script  failed   --
ECHO  ------------------------------------------
GOTO END

:SUCCESS
ECHO  ------------------------------------------
ECHO  -- ROI migration tool script succeed    --
ECHO  ------------------------------------------

:END
