
rem -------------------------------------------------------------------------
rem Clearing build files
rem -------------------------------------------------------------------------

rem Set the directoty location where the build files exist

set dirLocation=\\192.168.6.126\test1

rem Set NAnt home directory path. if directory not exist, NAnt script wont execute

set NANT_HOME=C:\3rdParty\NAnt\nant

set PATH=%NANT_HOME%\bin;%PATH%

nant -buildfile:Purge-build-files.xml clear-bulid-files -D:dirLocation=%dirLocation%

exit