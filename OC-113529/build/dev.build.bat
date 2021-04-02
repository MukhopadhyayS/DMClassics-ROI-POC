#
#    Copyright (C) 2007 McKesson Corporation and/or one of its
#    subsidiaries. All Rights Reserved.
#
#    Use of this material is governed by a license agreement.
#    This material contains confidential, proprietary and trade
#    secret information of McKesson Information Solutions and is
#    protected under United States and international copyright and
#    other intellectual property laws. Use, disclosure,
#    reproduction, modification, distribution, or storage in a
#    retrieval system in any form or by any means is prohibited
#    without the prior express written permission of McKesson
#    Information Solutions.
# 

call env.bat

echo off

rem calls ant scripts to update build scripts
call ant -file update.xml -Dbuild.config.file.name=dev.properties

del log\*.log

set LOGDIR=log
if not exist "log" mkdir %LOGDIR%

set LOGFILE=devbuild%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log
set MAILLOGFILE=mail%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log

cls

echo *********************************************
echo **************** ROI Build ******************
echo *********************************************

call ant  -file start.xml -Dbuild.config.file.name=dev.properties -logfile %LOGDIR%\%LOGFILE%

call ant  -file send.email.xml -Dbuild.config.file.name=dev.properties -Dlog.file=%LOGFILE% -logfile %LOGDIR%\%MAILLOGFILE%

echo *********************************************
echo The build log files are located at build/log
echo *********************************************

pause