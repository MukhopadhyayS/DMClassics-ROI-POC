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
* Procedure             : ROI_LinkingMigratedAndClassicRequestData.sql
* Creation Date         : 08/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Link migrated and classic request data
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        	Description
*   	---------	----------- 	----------------
*									
* Return Status         : <none>		
*									
* Usage                 : ROI_LinkingMigratedAndClassicRequestData.sql 
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
* Name      Date        Purpose
* ---------	----------- ----------------
*
***************************************************************************************************************************************/
USE CABINET
GO
Print 'Starting ROI_LinkingMigratedAndClassicRequestData.sql at ' + cast(getdate() as varchar(20));

SET NOCOUNT ON

Select COUNT(TableRowID) AS [Records found], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
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

--load the cross ref table with the ROI_RequestMain_Seq and the matching classic Request_Id
INSERT ROI_MigrationCrossRef (ROI_RequestMain_Seq, Request_Id)
	--get the data from the migrated requests from ROI_RequestMain
	select  ROI_RequestMain_Seq, Created_By_Seq
	FROM ROI_RequestMain
	WHERE Created_By_Seq < 1

--place the unique identifier from each table and row with migrated data into the ROI_MigrationLOV table
INSERT ROI_MigrationLOV (TableObjectID, TableRowID)
	--get the data from the migrated requests from ROI_RequestMain
	select  object_id(N'ROI_RequestMain'),ROI_RequestMain_Seq
	FROM ROI_RequestMain
	WHERE Created_By_Seq < 1
UNION
	--get the data for the ROI_SearchLOV table
	SELECT OBJECT_ID(N'ROI_SearchLOV'),ROI_SearchLOV_Seq
	FROM ROI_SearchLOV SLOV
	INNER JOIN ROI_RequestMain RM ON SLOV.child = RM.ROI_RequestMain_Seq
	WHERE RM.Created_By_Seq < 0
UNION
	--get the data from the migrated requests from ROI_RequestEvent
	SELECT OBJECT_ID(N'ROI_RequestEvent'),RE.ROI_RequestEvent_Seq
	FROM ROI_RequestEvent RE
	INNER JOIN ROI_RequestMain RM
	ON RE.ROI_RequestMain_Seq= RM.ROI_RequestMain_Seq
	WHERE RM.Created_By_Seq < 0
UNION
	--get the data from the migrated requests from ROI_RequestDelivery
	SELECT OBJECT_ID(N'ROI_RequestDelivery'), ROI_RequestDelivery_Seq
	FROM ROI_RequestDelivery RD
	INNER JOIN ROI_RequestMain RM
	ON RD.ROI_RequestMain_Seq= RM.ROI_RequestMain_Seq
	WHERE RM.Created_By_Seq < 1

--set all the created by to ADMIN
	UPDATE ROI_RequestEvent
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0
	  
	UPDATE ROI_RequestDeliveryCharges 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_RequestDelivery 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_SearchLOV 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_RequestMain 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0



--show the data in the ROI_MigratedLOV table
Select COUNT(TableRowID) AS [Records total], OBJECT_Name(TableObjectID) AS [Table] FROM ROI_MigrationLOV
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


Print 'Completed ROI_LinkingMigratedAndClassicRequestData at ' + cast(getdate() as varchar(20));
