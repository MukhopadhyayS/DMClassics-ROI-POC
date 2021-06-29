
Purpose:
	The purpose of the ROI Conversion tool is to convert data from 13.5.2/15.0/15.1/15.1.1/15.1.2/15.1.3 ROI application (data stored as xml) to ROI application available in HPF 16.0 (data stored in relational tables). Migrated data includes supplemental patients, requests and requestor letters.

ROI Conversion Tool will be available in the Support folder in ROI Release CD.
It is recommended to run the Pre Conversion Scripts and the Conversion Tool on the database server.
Copy <ROI Release CD>\Support\ROI_Conversion_Tool folder to C:\imnet\ROI_Conversion_Tool folder. Henceforth, this folder will be referred to as <ROI_Conversion_Tool>.

==============================================================
Pre Conversion scripts (to be run before the conversion tool):
==============================================================
	
There are additional scripts built, to get some metrics/data on existing ROI requests (in XML format) before the conversion. 
It is recommended to execute these scripts and correct the data as necessary.
Pre-conversion scripts should be run on the old production db server

	1) Open DOS command prompt as Administrator.
	2) At the DOS command prompt, enter the following command to navigate to ROI Conversion tool directory
		cd <ROI_Conversion_Tool>\common\scripts\
	3) At the DOS command prompt, enter the following commands
		3.1: To get metrics/data for existing XML requests without invoices:
			Requests_Released_without_an_Invoice.bat
		3.2: To get metrics/data on existing XML requests which contain a refund:
			Requests_That_Contain_Refunds.bat
		3.3: To get metrics/data on existing XML requests which have balance due:
			C_XML_Requests_That_Contain_Balance_Due.bat
		3.4: To get metrics/data on existing XML requests which have 0 balance due:
			Requests_with_Zero_Dollar_Request_Cost.bat
		3.5: To get metric/data on  existing XML requests which were invoiced, but not released
			Requests_Invoiced_but_not_Released.bat 
		3.6: To get metrcis/data on canceled and denied requests:
                        Requests_Canceled_And_Denied.bat

	4) These scripts will prompt for database server name, database username and password. The provided database user should have select access to all ROI tables in cabinet database.
	5) They will generate report/CSV file in <ROI_Conversion_Tool>\scripts\ folder.

==============================================================
Conversion process:
==============================================================
NOTE: Do NOT run multiple conversion tools at the same time against the same database.

	1) Open <ROI_Conversion_Tool>\common\config\conversion.properties file in editor and edit the following default properties.
		a) default.invoiceDueDays: The number of days invoice is due after it was created.
			Note: Conversion tool will not start until all these properties are set.
		b) default.database.username: This is by default 'sa'. However this property can be changed provided that database user has necessary privileges.
					Database user specified here should have 
						select, insert, update, delete access to all ROI tables in cabinet database 
						create and drop stored procedure access in cabinet database.
		c) threads.num: This by default is 5. Please do not change this value. The best practice is to keep this number at 5.
	
	
	1. edit billing.location.option properties in the config file. There are 2 options for this properties: simple or advanced.
	   If the option is configured to simple, please goto step 2. otherwise, please go to step 3.
	   
	2. edit
		a) default.facilityCode: This is the default billing location for all requests that will be converted.
		b) default.facilityName: The corresponding facility name.
		
	   Go to step 11
	   
	3. User needs to go to ROI application to configure all the possible billing locations   
		
	4. Open DOS command prompt as Administrator.
	
	5. At the DOS command prompt, enter the following command to navigate to ROI Conversion tool directory
		cd <ROI_Conversion_Tool>\preconversion
		
	6. At the DOS command prompt, enter the following command (not case sensitive)
		roi-preconversion-mapping.bat
		
	7. At this point, pre-conversion will start and prompt to enter the database server name and the password for SQL Server user 'sa'.
	
	8. 2 files will be generated - user-billinglocation-mapping.txt and facility-billinglocation-mapping.txt
	
	9. User needs to edit the 2 files to remove all the extra billing locations from the 2 files.
	
	10. User needs to copy the 2 files to <ROI_Conversion_Tool>\conversion directory.
	
	11. Open DOS command prompt as Administrator.
	
	12. At the DOS command prompt, enter the following command to navigate to ROI Conversion tool directory
		cd <ROI_Conversion_Tool>

	13. At the DOS command prompt, enter the following command (not case sensitive)
		roi-conversion-tool.bat

	14. At this point, roi conversion tool will start and prompt to enter the database server name and the password for SQL Server user 'sa'.

	15 After validating server name and password for 'sa', conversion tool will attempt to initialize database connection and start pre-conversion tasks.

	16. Conversion tool will display list of supplemental patients, requests and requestor letters to convert. Press enter to start conversion.

	17. Conversion tool will constantly update the screen with the progress of conversion.

	18. If there are any errors reported, conversion tool will halt and alert to open log file. Log file will be at <ROI_Conversion_Tool>\conversion.log. Search for any exceptions. Also conversion status along with any error messages will be stored in the following database tables:
		cabinet.dbo.ROI_ConversionStatus_RequestMain
		cabinet.dbo.ROI_ConversionStatus_RequestorLetter
		cabinet.dbo.ROI_ConversionStatus_Supplemental
		After resolving all errors, restart conversion tool. ROI Conversion tool will pick up where it left off.

	19. If no errors are reported, conversion tool will proceed with post-conversion (cleanup) tasks and alert to open log file.
	
	
==============================================================
Post Conversion scripts (to be run after the conversion tool):
==============================================================

These are scripts to validate, to get some metrics/data on existing entities before and after the conversion.
Current list of entities includes: Requestors, Patients, and Requests. 



	1) Open DOS command prompt as Administrator.
	2) At the DOS command prompt, enter the following command to navigate to ROI Conversion tool directory
		cd <ROI_Conversion_Tool>\common\scripts\
	3) At the DOS command prompt, enter the following command:

                migration_validation.bat

	4) These scripts will prompt for database server name, database username and password. The provided database user should have select access to all ROI tables in cabinet database.
	5) They will generate report/CSV files in <ROI_Conversion_Tool>\scripts\ folder:

		
                Requestors_Migration_Validation_Report.txt						for Requestors
		Patients_Migration_Validation_Report.txt						for Patients
		CXMLRequests_Migration_Validation_Report.txt						for Classic Requests
		XMLRequest_Migration_Validation_Report.txt						for Requests(exclude Classic)
		Requests_ShouldBe_Converted_As_Unbillable_But_Not_Report.txt                            for Requests that should be converted as un-billable, but they were converted as billable
		Requests_Should_NOT_Be_Converted_As_Unbillable_Report.txt		                for Requests that were converted as un-billable , but should be converted as 'Undefined':
													requests with broken XML, or just created requests that do not have any billing information.

Note: The script can be run many times while migration tool is running, user can see a progress , but the data is not reliable until the tool will finish the migration.
