@echo off

if /I /?==%~1 goto usage

set RELEASE=17_1_0_481
echo Installing ROI %RELEASE% into OneContent

rem find the zip file
set ZIP_FILE=ROI_%RELEASE%.zip
CALL :FULLPATH ".\%ZIP_FILE%" ZIP_FILE_PATH

if exist %ZIP_FILE_PATH% goto oneContentInstallPath
    echo Could not find zip file in current directory.
    set "ZIP_FILE_PATH=S:\%ZIP_FILE%"
	set /p ZIP_FILE_PATH="Enter Zip Location [%ZIP_FILE_PATH%] >"

rem validate the entered zip file path
CALL :FULLPATH %ZIP_FILE_PATH% ZIP_FILE_PATH
if exist "%ZIP_FILE_PATH%" goto oneContentInstallPath
    echo Error: could not locate zip file at %ZIP_FILE_PATH%
    goto end


:oneContentInstallPath
echo Using Zip File at %ZIP_FILE_PATH%
set "ONE_CONTENT_INSTALL_PATH=D:\OneContent"
set /p ONE_CONTENT_INSTALL_PATH="Enter OneContent Installation path [%ONE_CONTENT_INSTALL_PATH%] >"
if exist %ONE_CONTENT_INSTALL_PATH% goto installFound
  echo Install folder %ONE_CONTENT_INSTALL_PATH% not found...
  goto end

:installFound
SC query MPFServer | FIND "STATE" | FIND "RUNNING"
if ERRORLEVEL 1 goto serverStopped
echo OneContent is running, stopping the service.
sc stop MPFServer
echo Waiting 30 seconds for server to stop...
ping -n 31 127.0.0.1>nul

:serverStopped

set INSTALL_DRIVE=%ONE_CONTENT_INSTALL_PATH:~0,2%
%INSTALL_DRIVE%


cd %ONE_CONTENT_INSTALL_PATH%
echo Install Path: %ONE_CONTENT_INSTALL_PATH%
echo ZIP File Path: %ZIP_FILE_PATH%
if EXIST %ONE_CONTENT_INSTALL_PATH%\MPFServer\mpfconfig\mpfroi.properties ren %ONE_CONTENT_INSTALL_PATH%\MPFServer\mpfconfig\mpfroi.properties ocroi.properties
if NOT EXIST %ONE_CONTENT_INSTALL_PATH%\MPFServer\mpfconfig\ocroi.properties goto unpackroi
   echo Found previous ROI install. Copying over ROI configuration files. This may take a few minutes...
if EXIST %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\mpf-roi.config.bat ren %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\mpf-roi.config.bat oc-roi.config.bat
   copy /Y %ONE_CONTENT_INSTALL_PATH%\MPFServer\mpfconfig\ocroi.properties MPFServer\mpfconfig\ocroi.properties.bak

   findstr /c:"WINDSS" %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\oc-roi.config.bat
   if %errorlevel% neq 1 copy /Y %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\oc-roi.config.bat MPFServer\webapps\ROOT\oc-roi.config.bat.bak
   
   rmdir /s /q %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient
   rmdir /s /q %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\roi
   goto unpackroiandupdateclient

:unpackroi
echo Unzipping new code to %ONE_CONTENT_INSTALL_PATH%.  This may take a few minutes...
%ONE_CONTENT_INSTALL_PATH%\jdk\64\bin\jar xf "%ZIP_FILE_PATH%"
echo You MUST edit the %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\oc-roi.config.bat file AND execute %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\updatesetup.bat
goto end

:unpackroiandupdateclient
echo Unzipping new code to %ONE_CONTENT_INSTALL_PATH%.  This may take a few minutes...
%ONE_CONTENT_INSTALL_PATH%\jdk\64\bin\jar xf "%ZIP_FILE_PATH%"
move /Y %ONE_CONTENT_INSTALL_PATH%\MPFServer\mpfconfig\ocroi.properties.bak MPFServer\mpfconfig\ocroi.properties
if exist %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\oc-roi.config.bat.bak move/Y %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\oc-roi.config.bat.bak MPFServer\webapps\ROOT\ROIClient\oc-roi.config.bat
goto checkrunupdatesetup

:checkrunupdatesetup
echo You MUST execute %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\updatesetup.bat
set "ASK_RUN_UPDATE_SETUP=Y"
set "RUN_UPDATE_SETUP=N"
set /p ASK_RUN_UPDATE_SETUP="Do you want to execute the ROIClient updatesetup.bat now? [%ASK_RUN_UPDATE_SETUP%] >"
if %ASK_RUN_UPDATE_SETUP%==y set "RUN_UPDATE_SETUP=Y"
if %ASK_RUN_UPDATE_SETUP%==Y set "RUN_UPDATE_SETUP=Y"
if %RUN_UPDATE_SETUP%==Y goto runupdatesetup
  goto end
  
:runupdatesetup
echo Executing %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\updatesetup.bat
echo Close window when done
cd %ONE_CONTENT_INSTALL_PATH%\MPFServer\webapps\ROOT\ROIClient\
updatesetup.bat

:usage
echo Installs ROI %RELEASE% into an existing OneContent server
echo.
echo install-ROI.bat
echo No command line arguments required.  Just answer the prompts. 
echo Default response values are enclosed in [].

:end
PAUSE
GOTO :EOF

:PARENT
SET %2=%~p1
GOTO :EOF

:FILENAME
SET %2=%~nx1
GOTO :EOF

:FULLPATH
SET %2=%~f1
GOTO :EOF