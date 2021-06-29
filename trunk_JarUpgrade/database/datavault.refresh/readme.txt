
ROI Datavault Refresh


1	At a DOS command prompt, enter this command (not case-sensitive) to run the datavault refresh batch file: 

		datavault_start.cmd <db_server_name> <sa_password> <ref_db_server_name> <ref_sa_password>


2	IMPORTANT! After the datavault refresh completes

	Open the “roi.datavault.log” file and search the file for any SQL error messages 

		(see section 1.1 Identifying Error Messages in Database Update Logs). 
	
	If any errors exist, you must first resolve them and then perform the atavault refresh again. 
	


3 	Please search the following messages in log file. In the ‘Containing text:’ field, enter (repeat for each value below): 

     	a.	, Level

	b.	Login failed for user 'sa'

	c. 	[DBNETLIB]SQL Server does not exist or access denied

4 	Under Search Options, click Advanced Options.  Enable the Search subfolders check box.

5 	Click the Search button.

	This command will return a list of the files containing the search criteria you entered.

	If no files were returned, then the datavaulr refresh was successful.   

6 	Open each file and search for the strings that match the following error message pattern:

     		Msg <nnnnn>, Level <n>, State <n>

7 	The string includes the error code. Check each error code and resolve.




