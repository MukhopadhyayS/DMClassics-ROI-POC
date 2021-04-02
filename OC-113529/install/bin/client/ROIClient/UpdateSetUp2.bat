rem %1% - tomcat_home, %2% - tomcat port, %3% - protocol

set clientVersion=17.0.1.118
set clientAppFolder=Application Files/McK.EIG.ROI.Client.App_17_0_0_118

rem Read configuration file
call oc-roi.config.bat

echo ROI : %ROI_PROTOCOL%://%ROI_IP%:%ROI_PORT%
echo PORTAL : %PORTAL_PROTOCOL%://%PORTAL_IP%:%PORTAL_PORT%

SET fileName="%clientAppFolder%"\Mck.EIG.ROI.Client.App.ini

echo %fileName%

if Exist %fileName% del %fileName%

rem Update INI File
echo [ROI]>> %fileName%
echo protocol=%ROI_PROTOCOL%>> %fileName%
echo ip=%ROI_IP%>> %fileName%
echo port=%ROI_PORT%>> %fileName%
echo WINDSS_DATABASE=%WINDSS_DATABASE_SERVER%>> %fileName%
echo WINDSS_USER=%WINDSS_DBUSER_NAME%>> %fileName%
echo WINDSS_PSWD=%WINDSS_DB_PSWD%>> %fileName%
echo.>>%fileName%

echo [PORTAL]>> %fileName%
echo protocol=%PORTAL_PROTOCOL%>> %fileName%
echo ip=%PORTAL_IP%>> %fileName%
echo port=%PORTAL_PORT%>> %fileName%
echo.>>%fileName%

copy setup.exe temp.exe

temp.exe -url=%ROI_PROTOCOL%://%ROI_IP%:%ROI_PORT%/ROIClient /dest=setup.exe /hide_progress

mage.exe -New Application -ToFile "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest" -Name McK.EIG.ROI.Client.App -Version %clientVersion% -Processor "x86" -FromDirectory "%clientAppFolder%" -TrustLevel FullTrust

mage.exe -Sign "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest" -CertFile ROI-CertFile.pfx

mage.exe -Update McK.EIG.ROI.Client.App.application -appm "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest" -appc "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest"

mage.exe -Update McK.EIG.ROI.Client.App.application -ProviderUrl %ROI_PROTOCOL%://%ROI_IP%:%ROI_PORT%/ROIClient/McK.EIG.ROI.Client.App.application

mage.exe -Update McK.EIG.ROI.Client.App.application -Version %clientVersion% -AppManifest "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest"

mage.exe -Update McK.EIG.ROI.Client.App.application -mv %clientVersion% -AppManifest "%clientAppFolder%\McK.EIG.ROI.Client.App.exe.manifest"

mage.exe -Update McK.EIG.ROI.Client.App.application -Publisher ROI

mage.exe -Sign McK.EIG.ROI.Client.App.application -CertFile ROI-CertFile.pfx

del temp.exe

cd %CATALINA_HOME%
cd ..


exit
