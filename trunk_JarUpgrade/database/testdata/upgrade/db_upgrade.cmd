Rem ****************************************************************
Rem *  	COPYRIGHT MCKESSON 2007
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
Rem * Command File Name: db_upgrade.cmd  
Rem * Description: Command file to execute all upgrade scripts for a given database
Rem *		   Assumes that a subdirectory exists for each database name, one level
Rem *		   below the current dir
Rem *		   Run this from the top-level upgrade script directory
Rem *		   still needs some parameter validation/defaults
Rem *		   and error handling
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * Params In:
rem *		  %1 			String		server name
rem *		  %2 			String		database name
rem *		  %3			String		user name (should be sa)
rem *		  %4			String		user pwd (can we encrypt this?)
Rem *		
Rem * Database Used:	master, Audit,cabinet,his,HBF.
Rem * Script File called: *.lgn, then *.tbl , then *.sch , then *.sql.
Rem * NOTES: 
Rem * Revision History:
Rem *	Name			Date		Changes/Version&Build/ClearQuest#
Rem *	---------		----------	----------------------
Rem *	M. Alahyar		01/06/04	created
REM *	Joseph Chen		04/27/04	Remove -r from command file
REM *	Judy Jiang		06/08/04	Updated/HPF ANSI Multi-facility 06.02.00/:
Rem *						Add *.TB2.
REM *	Judy Jiang		06/10/04	Updated/HPF ANSI Multi-facility 06.02.00/:Add *.TB3.
REM *   Joseph Chen		08/10/05	Change log location to c:/HPF11.0_Upgrade.log 
REM *   RC			08/10/05	Change log location to ../HPF13.5_Upgrade.log 
REM *   
Rem *************************************************************


chdir %2
rem do login/user changes, then all table changes , then schema ..
rem do login/user changes (login, user.)

echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .log files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.lgn) do sqlcmd /S %1 /d %2 /U %3 /P %4 /i %%f -m0   -e  >>..\HPF13.5_Upgrade.log



rem	NOTE: .tbl scripts should include indexes and constraints
echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .tbl files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.tbl) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e   >>..\HPF13.5_Upgrade.log



rem	NOTE: .tb2 scripts should include indexes and constraints
echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .tb2 files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.tb2) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e   >>..\HPF13.5_Upgrade.log

rem	NOTE: .data scripts should include indexes and constraints
echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .data files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.data) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e   >>..\HPF13.5_Upgrade.log

rem	NOTE: .tb3 scripts should include indexes and constraints
echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .tb3 files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.tb3) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e  >>..\HPF13.5_Upgrade.log



echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .sch files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
rem do other schema changes (views, triggers, etc.)
for %%f in (*.sch) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e   >>..\HPF13.5_Upgrade.log



rem do stored procedures
echo **************************************** >>..\HPF13.5_Upgrade.log
echo ***start updating %2 - .sql files*** >>..\HPF13.5_Upgrade.log
echo **************************************** >>..\HPF13.5_Upgrade.log
for %%f in (*.sql) do sqlcmd -S %1 -d %2 -U %3 -P %4 -i %%f  -m0   -e   >>..\HPF13.5_Upgrade.log

chdir ..