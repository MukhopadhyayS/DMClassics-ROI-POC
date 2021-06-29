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

ECHO  ------------------------------------------------
ECHO  --  ROI migration vaildation script start     --
ECHO  ------------------------------------------------

ECHO Please Enter Database Server Name:
SET /p serverName=

IF "%serverName%"=="" GOTO NOSERVERNAME

ECHO Please Enter Database User Name:
SET /p userLoginName=

IF "%userLoginName%"=="" GOTO NOUSERNAME

ECHO Please Enter Database Password:
SET /p userPassword=

IF "%userPassword%"=="" GOTO NOPASSWORD

ECHO Processing Requestors validation...
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i RequestsWithRequestors.sql -o Requestors_Migration_Validation_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO Processing Requestors validation finished ...


ECHO Processing Patients validation ...
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i Patients.sql -o Patients_Migration_Validation_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO Processing Patients validation finished...



ECHO Processing Classic Requests validation ...
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i CXMLRequest.sql -o CXMLRequests_Migration_Validation_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO Processing Classic Requests validation  finished...



ECHO Processing Requests(excluding Classic Requests) validation...
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i XMLRequest.sql -o XMLRequest_Migration_Validation_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO Processing Requests(excluding Classic Requests) validation finished...


ECHO Checking if Unbillables were converted correclty 
ECHO Finding requests that suppose to be converted as 'Un-billable', but was not.
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i Requests_ShouldBe_Converted_As_Unbillable_But_Not.sql -o Requests_ShouldBe_Converted_As_Unbillable_But_Not_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO 'Finding requests that suppose to be converted as 'Un-billable', but was not'   finished...


ECHO Checking If Un-billables were converted correclty ...
ECHO Finding requests that were converted as 'Un-billable', but should not be.
sqlcmd -S %serverName% -U %userLoginName% -P %userPassword% -r0 -s"	" -W -i Requests_Converted_As_Unbillable_But_Should_Not.sql -o Requests_Should_NOT_Be_Converted_As_Unbillable_Report.txt -b

if not '%ERRORLEVEL%' == '0' goto FAILED
ECHO 'Finding request that were converted as 'Unbillable', but should not be'  finished...


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
ECHO  ------------------------------------------------
ECHO  --  ROI migration vaildation script  failed   --
ECHO  ------------------------------------------------
GOTO END

:SUCCESS
ECHO  ------------------------------------------------
ECHO  -- ROI migration vaildation script succeed    --
ECHO  ------------------------------------------------

:END
