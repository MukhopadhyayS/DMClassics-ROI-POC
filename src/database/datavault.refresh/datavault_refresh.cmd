Rem ****************************************************************
Rem *  	COPYRIGHT MCKESSON 2008
Rem *		ALL RIGHTS RESERVED
Rem *
Rem * 	The copyright to the computer program(s) herein
Rem * 	is the property of McKesson. The program(s)
Rem * 	may be used and/or copied only with the written
Rem * 	permission of McKesson or in accordance with
Rem * 	the terms and conditions stipulated in the     
Rem * 	agreement/contract under which the program(s)
Rem * 	have been supplied.
Rem *************************************************************
Rem * Command File Name: datavault_Refresh.cmd  
Rem * Description: Command file to execute all  datavault scripts for a given database
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
rem *		  %1 			String		server name
rem *		  %2 			String		database name
rem *		  %3			String		user name (should be sa)
rem *		  %4			String		user pwd (can we encrypt this?)
Rem *		
Rem * Database Used:	master, cabinet
Rem * Script File called: *.dv1, then *.dv2, then *.dv3 , then *.dv4.
Rem * NOTES: 
REM *   
Rem *************************************************************


chdir %2

echo **************************************** >>..\%LogFile%
echo ***start updating %2 - .datavault files*** >>..\%LogFile%
echo **************************************** >>..\%LogFile%
rem clean up the tables 
for %%f in (*.dv1) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0  -e   >>..\%LogFile%
rem create DB link in ROI Server
for %%f in (*.dv2) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0  -v refserver=%5 refpwd=%6  -e   >>..\%LogFile%
rem insert the data from reference server to ROI  server 
for %%f in (*.dv3) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0  -v refserver=%5 -e   >>..\%LogFile%
rem drop the DB link in ROI Server
for %%f in (*.dv4) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0  -v refserver=%5 -e   >>..\%LogFile%
chdir ..

