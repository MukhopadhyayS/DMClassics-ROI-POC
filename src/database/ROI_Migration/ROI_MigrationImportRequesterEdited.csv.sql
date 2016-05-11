/* BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
*
* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.
*
END-COPYRIGHT-COMMENT  Do not remove or modify this line! */

/***************************************************************************************************************
* Procedure             : ROI_MigrationImportRequesterEdited.sql
* Creation Date         : 06/22/2009
*					
* Written by            : RC	
*									
* Purpose               : Import edited Requester data
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        Description
*   ---------	----------- ----------------
*									
* Return Status         : <none>		
*									
* Usage                 : called by batch program
*																
* Called By             : migration process
*									
* Tables used           : cabinet.dbo.ROI_RequesterEdited
*			
* Data Modifications	: loads data into table
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        06/22/2009  Created new stored procedure.  
* MD Meersma	09/30/2010 Use CSV file to import instead of XLS file
*
*1) If your SQL Server is a 64-bit system then replace the ROI_MigrationImportRequesterEdited.sql file with the this version.
*
*2) Before executing Migration Step Menu#3 Import Requesters you must save the XLS file from Excel as a CSV file in the c:\imnet\ROI_Migration folder
*
*3) Execute Menu#3 and continue with the process.
*
***************************************************************************************************************************************/

SET NOCOUNT ON
Use CABINET;
truncate table cabinet.dbo.ROI_RequesterEdited
--Select * from cabinet.dbo.ROI_RequesterEdited
Select 'Starting ROI_MigrationImportRequesterEdited at '+ Cast(getdate() as varchar(20));
SET IDENTITY_INSERT cabinet.dbo.ROI_RequesterEdited ON;

DECLARE @l_vcharSQL varchar(2048)
DECLARE @l_intNumberOfImportFiles int
DECLARE @l_charNumberOfImportFiles char(2)
DECLARE @l_intI int

--the number of import files is limited to 99
--import files of < 1 causes script to return

--update this number for the number of import files
SET @l_intNumberOfImportFiles = 1

If @l_intNumberOfImportFiles < 1 RETURN

While @l_intNumberOfImportFiles > 0
BEGIN
SELECT  @l_charNumberOfImportFiles = REVERSE(SUBSTRING(REVERSE('00'+ CAST(@l_intNumberOfImportFiles as varchar(2))),1,2))


Print 'Importing edited Requesters.  Processing file ROI_RequesterEdited' + @l_charNumberOfImportFiles + '.csv.'

--SET @l_vcharSQL = 'INSERT INTO [cabinet].[dbo].[ROI_RequesterEdited]([Requester_Id],[RequesterName],[Address1],[Address2],[City],[State],[Zip],[Phone],[Fax],[BillingType_Id],[RequesterType_Id],[RecordView],[Set_Id],[DefaultFlag],[UpdatedDate],[UpdateBy],[RequesterActive],[Address3])
--SELECT * FROM OPENROWSET(''Microsoft.Jet.OLEDB.4.0'',''Excel 8.0;Database=C:\Imnet\ROI_Migration\ROI_RequesterEdited' 
--+ @l_charNumberOfImportFiles +'.csv'',''SELECT * FROM [Sheet1$]'')'

SET @l_vcharSQL = 'bulk insert ROI_RequesterEdited
FROM ''C:\imnet\ROI_Migration\ROI_RequesterEdited' + @l_charNumberOfImportFiles + '.csv''
WITH 
(
FIRSTROW=2,
MAXERRORS=10000,
KEEPIDENTITY,
FIELDTERMINATOR = '','',
ROWTERMINATOR = ''\n''
)'

--Select @l_vcharSQL
EXEC(@l_vcharSQL)

select count(*) AS 'The total number of edited Requesters is' from cabinet.dbo.ROI_RequesterEdited

Set @l_intNumberOfImportFiles = @l_intNumberOfImportFiles - 1
END

SET IDENTITY_INSERT cabinet.dbo.ROI_RequesterEdited OFF;


Select 'Completed ROI_MigrationImportRequesterEdited at '+ Cast(getdate() as varchar(20));
GO
