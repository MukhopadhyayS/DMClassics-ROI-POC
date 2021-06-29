
HPF 13.5 database upgrade

1.0	Applying HPF 13.5  Database scripts 

1	Log on to the HPF database server as a system administrator (if you are not currently logged on).

3	Back up all HPF databases (AUDIT, CABINET, EIWDATA, HBF, HIS, and WFE) 

4	Create the following directory in your imnet folder if it does not exits yet:


		c:\imnet\HPF_13.5  

	Create the following directory if it does not exist

		\Database 

5	Insert the Release HPF 13.5  software CD into the CD drive and copy the contents of the upgrade directory:

	to the local hard disk of the database server. (The c:\imnet\HPF_13.5\Database location is REQUIRED!).

6	Double click the cmd.exe shortcut to open a DOS command prompt in the current directory.  Verify the directory is:

		c:\imnet\HPF_13.5\Database

7	At a DOS command prompt, enter this command (not case-sensitive) to run the database upgrade batch file: 

		upgrade.cmd <db_server_name> <sa_password>

8	IMPORTANT! After the upgrade completes

	Open the “HPF13.5_Upgrade.log” file and search the file for any SQL error messages 

		(see section 1.1 Identifying Error Messages in Database Update Logs). 
	
	If any errors exist, you must first resolve them and then perform the database upgrade again. 
	Even though some errors do not prevent you from continuing, 
	McKesson recommends that you resolve all errors described in the log file to avoid possible data corruption or loss.


1.1	Identifying Error Messages in Database Update Logs 

	When database scripts have finished executing, it is important to check the log files for any error messages.  

	This section provides general information on how to search for error messages in the log file.

1	In Windows Explorer, select the highest level directory. 

	This is the location from which you run the database script or the directory you want to search.  

		For example, C:\Imnet\HPF_13.5\Database 

2 	Right-click to display the pop-up menu, and click on Search.  The system displays the Search window.

3 	In the ‘Search for files or folders named:’ field, enter:

    		 *.log

4 	In the ‘Containing text:’ field, enter (repeat for each value below): 

     	a.	, Level

	b.	Login failed for user 'sa'

	c. 	[DBNETLIB]SQL Server does not exist or access denied

5 	Under Search Options, click Advanced Options.  Enable the Search subfolders check box.

6 	Click the Search button.

	This command will return a list of the files containing the search criteria you entered.

	If no files were returned, then the upgrade was successful.   

7 	Open each file and search for the strings that match the following error message pattern:

     		Msg <nnnnn>, Level <n>, State <n>

8 	The string includes the error code. Check each error code and resolve.




