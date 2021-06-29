
@echo off

set PATH_OLD=%PATH%

set PATH=%~dp0;%PATH%
"%~dpn0.exe" %*

set PATH=%PATH_OLD%
