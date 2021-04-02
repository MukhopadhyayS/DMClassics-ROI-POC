::search.cmd
::This file would be invoked by providing the options
::
:: Search string         The string to be searched in the file. 
::			 This string should be enclosed by double quotes
::
:: File			 The file to be searched.

echo off
find %1 %2

exit %errorlevel%
