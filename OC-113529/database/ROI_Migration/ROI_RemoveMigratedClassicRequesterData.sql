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
* Procedure             : ROI_RemoveMigratedClassicRequesterData.sql
* Creation Date         : 08/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Remove migrated clissic request data
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
* Usage                 : ROI_RemoveMigratedClassicRequesterData.sql 
*																
* Called By             : ROI_Migration.cmd
*									
* Tables used           : [Cabinet].[dbo].[ROI_RequestorTypeToBillingTier]
*                       : [Cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
*                       : [Cabinet].[dbo].[ROI_RequestorToEmailPhone]
*                       : [Cabinet].[dbo].[ROI_RequestorToAddress]
*                       : [Cabinet].[dbo].[ROI_Address]
*                       : [Cabinet].[dbo].[ROI_EmailPhone]
*                       : [Cabinet].[dbo].[ROI_Requestor]
*                       : [Cabinet].[dbo].[ROI_RequestorType]
*
* Data Modifications	: removes data from above tables
*									
* Updates: 	
* Name			Date        Purpose
* ---------		----------- ----------------
* MD Meersma	08/04/2010	Do not remove rows from ROI_MigrationLOV if any of the previous delete statements fail
***************************************************************************************************************************************/
USE CABINET
GO
Print 'Starting RemoveMigratedClassicRequesterData at ' + cast(getdate() as varchar(20));


--Show the number of rows effected
Select COUNT(TableRowID) AS [Records to be removed], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestorTypeToBillingTier'),
	OBJECT_ID(N'ROI_RequestorTypeToBillingTemplate'),
	OBJECT_ID(N'ROI_RequestorToEmailPhone'),
	OBJECT_ID(N'ROI_RequestorToAddress'),
	OBJECT_ID(N'ROI_Address'),
	OBJECT_ID(N'ROI_EmailPhone'),
	OBJECT_ID(N'ROI_Requestor'),
	OBJECT_ID(N'ROI_RequestorType')
)
GROUP BY 
	OBJECT_Name(TableObjectID)
ORDER BY
	OBJECT_Name(TableObjectID) 



SET NOCOUNT ON

	DELETE ROI_RequestorTypeToBillingTier
	WHERE ROI_RequestorTypeToBillingTier_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestorTypeToBillingTier'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_RequestorTypeToBillingTier at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestorTypeToBillingTemplate
	WHERE ROI_RequestorTypeToBillingTemplate_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestorTypeToBillingTemplate'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_RequestorTypeToBillingTemplate at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestorToEmailPhone
	WHERE ROI_RequestorToEmailPhone_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestorToEmailPhone'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_RequestorToEmailPhone at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestorToAddress
	WHERE ROI_RequestorToAddress_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestorToAddress'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_RequestorToAddress at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_Address
	WHERE ROI_Address_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_Address'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_Address at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_EmailPhone
	WHERE ROI_EmailPhone_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_EmailPhone'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_EmailPhone at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_Requestor
	WHERE ROI_Requestor_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_Requestor'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_Requestor at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestorType
	WHERE ROI_RequestorType_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestorType'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequesterData during ROI_RequestorType at ' + cast(getdate() as varchar(20));
		RETURN
	END

--clean up the ROI_MigrationLOV table
Delete FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestorTypeToBillingTier'),
	OBJECT_ID(N'ROI_RequestorTypeToBillingTemplate'),
	OBJECT_ID(N'ROI_RequestorToEmailPhone'),
	OBJECT_ID(N'ROI_RequestorToAddress'),
	OBJECT_ID(N'ROI_Address'),
	OBJECT_ID(N'ROI_EmailPhone'),
	OBJECT_ID(N'ROI_Requestor'),
	OBJECT_ID(N'ROI_RequestorType')
)
/*--Show the number of rows 
Select COUNT(TableRowID) AS [Rows after removal], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestorTypeToBillingTier'),
	OBJECT_ID(N'ROI_RequestorTypeToBillingTemplate'),
	OBJECT_ID(N'ROI_RequestorToEmailPhone'),
	OBJECT_ID(N'ROI_RequestorToAddress'),
	OBJECT_ID(N'ROI_Address'),
	OBJECT_ID(N'ROI_EmailPhone'),
	OBJECT_ID(N'ROI_Requestor'),
	OBJECT_ID(N'ROI_RequestorType')
)
GROUP BY 
	OBJECT_Name(TableObjectID)
ORDER BY
	OBJECT_Name(TableObjectID) 
*/
Print 'Completed RemoveMigratedClassicRequesterData at ' + cast(getdate() as varchar(20));
GO
