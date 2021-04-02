Letter Template Conversion Tool

Overview
The purpose of the ROI Letter Template Conversion Tool is to convert letter templates from releases 13.5.2/15.0/15.1/15.1.1/15.1.2/15.1.3/16.0/16.1 that are stored as .rtf format 
into .docx format in the ROI application available in OneContent 16.2. 
*	The ROI Letter Template Conversion Tool is available in the Support folder on the ROI Release CD. 
*	It is required that the work station that runs the tool can access the database server.  
*	The workstation must also have Microsoft Word installed.

Conversion Process
1.	Copy <ROI Release CD>\Support\Letter_Template_Conversion folder to C:\imnet\Letter_Template_Conversion folder (referred to as <Letter_Template_Conversion> in the instructions below).
	NOTE: Do NOT run multiple conversion tools at the same time against the same database.
2.	Open a DOS command prompt as Administrator.
3.	At the DOS command prompt, enter the following command to navigate to ROI Letter Template Conversion Tool directory:  cd <Letter_Template_Conversion>
4.	At the DOS command prompt, enter the following command (not case sensitive):  docx-conversion-tool.bat
5.	The ROI Letter Template Conversion Tool starts and prompts you to enter the database server name, SQL Server user (preferred username is "sa") and the password for that user.
6.	After validating the server name, database user, and password, the conversion tool attempts to initialize the database connection and then starts.  Press 'Y' to start the conversion.
7.	The ROI Letter Template Conversion Tool constantly updates the screen with the progress of conversion. 
	If there are any errors reported, the ROI Letter Template Conversion Tool logs these errors in the <Letter_Template_Conversion>\conversion.log.   
	Search this log for any exceptions. 
	
Copies of all the converted files are located in the <Letter_Template_Conversion>\template\docx folder.  
After reviewing this files, they can be deleted.  The original files are stored in the database.


