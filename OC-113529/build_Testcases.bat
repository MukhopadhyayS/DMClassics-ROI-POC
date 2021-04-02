cls
rd /s /q database\.svn
IF Exist database\.svn goto error
call build/env.bat

del build\log\mail*.log
del build\log\testbuild*.log

set LOGDIR=build\log
if not exist "log" mkdir %LOGDIR%

set LOGFILE=testbuild%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log
set MAILLOGFILE=mail%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log

cls

call ant -buildfile build/svn_build.xml build-debug-mode -logfile %LOGDIR%\%LOGFILE%
call ant -buildfile build/svn_build.xml send-email-notification -Dlog.file=%LOGFILE% -logfile %LOGDIR%\%MAILLOGFILE%
goto end

:error
echo Build Process can not refresh source folders!
echo Close all the files/folders opened from ROI source folder in build machine.
:end

REM exit /b 1