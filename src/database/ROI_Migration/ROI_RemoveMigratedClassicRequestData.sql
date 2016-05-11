
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
* Procedure             : ROI_RemoveMigratedClassicRequestData.sql
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
* Usage                 : ROI_RemoveMigratedClassicRequestData.sql 
*																
* Called By             : ROI_Migration.cmd
*									
* Tables used           : [Cabinet].[dbo].[ROI_RequestEvent]
*                       : [Cabinet].[dbo].[ROI_RequestDeliveryCharges]
*                       : [Cabinet].[dbo].[ROI_RequestDelivery]
*                       : [Cabinet].[dbo].[ROI_SearchLOV]
*                       : [Cabinet].[dbo].[ROI_RequestMain]
*                       : [Cabinet].[dbo].[ROI_MigrationLOV]
*
* Data Modifications	: removes data from above tables
*									
* Updates: 	
* Name			Date        Purpose
* ---------		----------- ----------------
* MD Meersma	08/04/2010	Do not remove rows from ROI_MigrationLOV if any of the previous delete statements fail
* MD Meersma	10/27/2010	Remove migrated rows from ROI_RequestEvent
***************************************************************************************************************************************/
USE CABINET
GO
SET NOCOUNT ON
Print 'Starting RemoveMigratedClassicRequestData at ' + cast(getdate() as varchar(20));

--Show the number of rows 
Select COUNT(TableRowID) AS [Records to be removed], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestEvent'),
	OBJECT_ID(N'ROI_RequestDeliveryCharges'),
	OBJECT_ID(N'ROI_RequestDelivery'),
	OBJECT_ID(N'ROI_SearchLOV'),
	OBJECT_ID(N'ROI_RequestMain')
)
GROUP BY 
	OBJECT_Name(TableObjectID)
ORDER BY
	OBJECT_Name(TableObjectID) 


--delete the migrated data in this order
--ROI_RequestEvent  ROI_RequestDeliveryCharges ROI_RequestDelivery ROI_SearchLOV ROI_RequestMain 
	DELETE ROI_RequestEvent
	WHERE ROI_RequestEvent_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestEvent'))
	IF @@ERROR <>0
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_RequestEvent at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestDeliveryCharges
	WHERE ROI_RequestDelivery_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestDelivery'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_RequestDeliveryCharges at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_RequestDelivery
	WHERE ROI_RequestDelivery_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestDelivery'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_RequestDelivery at ' + cast(getdate() as varchar(20));
		RETURN
	END
	DELETE ROI_SearchLOV
	WHERE ROI_SearchLOV_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_SearchLOV'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_SearchLOV at ' + cast(getdate() as varchar(20));
		RETURN
	END

	DELETE ROI_RequestEvent
	WHERE ROI_RequestMain_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestMain'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_RequestEvent at ' + cast(getdate() as varchar(20));
		RETURN
	END

	DELETE ROI_RequestMain
	WHERE ROI_RequestMain_Seq in 
	(Select TableRowID FROM ROI_MigrationLOV WHERE TableObjectID = OBJECT_ID(N'ROI_RequestMain'))
	IF @@ERROR <>0 
	BEGIN
		Print 'Failed RemoveMigratedClassicRequestData during ROI_RequestMain at ' + cast(getdate() as varchar(20));
		RETURN
	END

--clean up the ROI_MigrationLOV table
Delete FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestEvent'),
	OBJECT_ID(N'ROI_RequestDeliveryCharges'),
	OBJECT_ID(N'ROI_RequestDelivery'),
	OBJECT_ID(N'ROI_SearchLOV'),
	OBJECT_ID(N'ROI_RequestMain')
)

--Show the number of rows effected
Select COUNT(TableRowID) AS [Records after removal], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
WHERE TableObjectID IN 
(
	OBJECT_ID(N'ROI_RequestEvent'),
	OBJECT_ID(N'ROI_RequestDeliveryCharges'),
	OBJECT_ID(N'ROI_RequestDelivery'),
	OBJECT_ID(N'ROI_SearchLOV'),
	OBJECT_ID(N'ROI_RequestMain')
)
GROUP BY 
	OBJECT_Name(TableObjectID)
ORDER BY
	OBJECT_Name(TableObjectID) 

Print 'Completed RemoveMigratedClassicRequestData at ' + cast(getdate() as varchar(20));
GO
