cls
rd /s /q client
IF Exist client goto error
rd /s /q server
IF Exist server goto error
rd /s /q database
IF Exist database goto error
rd /s /q install
IF Exist install goto error
call build/env.bat

del build\log\devbuild*.log

set LOGDIR=build\log
if not exist "log" mkdir %LOGDIR%

set LOGFILE=devbuild%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log
set MAILLOGFILE=mail%DATE:~10%%DATE:~4,2%%DATE:~7,2%.log

cls

call ant -buildfile build/svn_update.xml update-build-scripts -logfile %LOGDIR%\%LOGFILE%
call ant -buildfile build/start.xml set-properties -logfile %LOGDIR%\%LOGFILE%
call ant -buildfile build/svn_build.xml build-release-mode -logfile %LOGDIR%\%LOGFILE%
goto end

:error
echo Build Process can not refresh source folders!
echo Close all the files/folders opened from ROI source folder in build machine.
:end

REM exit /b 1