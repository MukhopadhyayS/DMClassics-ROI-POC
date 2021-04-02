Three additions or changes exist for the Cabinet database in 13.5  CP1.  The files are:
1.	Create_WFE_ROI_ProcessAutomatedRequest.sql
2.	Create_ROI_UpdateImnetInRequestMain.sql
3.	DAL_Archive.sql
The purpose of the files are to:
1.	Trim trailing spaces from the Imnet when creating an automated request
2.	Replace an Imnet in the RequestMain XML
3.	Update the ROI Request with archived images
To update the database run the files in SSMS in the numbered order shown above.
