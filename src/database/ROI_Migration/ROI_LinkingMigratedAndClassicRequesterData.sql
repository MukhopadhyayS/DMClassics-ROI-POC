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
* Procedure             : ROI_LinkingMigratedAndClassicRequesterData.sql
* Creation Date         : 08/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Link migrated and classic requester data
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
* Usage                 : ROI_LinkingMigratedAndClassicRequesterData.sql 
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
* Name      Date        Purpose
* ---------	----------- ----------------
*
***************************************************************************************************************************************/

USE CABINET
GO
Print 'Starting ROI_LinkingMigratedAndClassicRequesterData.sql at ' + cast(getdate() as varchar(20));

SET NOCOUNT ON


--place the unique identifier from each table and row with migrated data into the ROI_MigrationLOV table
INSERT ROI_MigrationLOV (TableObjectID, TableRowID)
	select  object_id(N'ROI_RequestorTypeToBillingTier'),ROI_RequestorTypeToBillingTier_Seq
	FROM ROI_RequestorTypeToBillingTier
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_RequestorTypeToBillingTemplate'),ROI_RequestorTypeToBillingTemplate_Seq
	FROM ROI_RequestorTypeToBillingTemplate
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_RequestorToEmailPhone'),ROI_RequestorToEmailPhone_Seq
	FROM ROI_RequestorToEmailPhone
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_Address'),ROI_Address_Seq
	FROM ROI_Address
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_EmailPhone'),ROI_EmailPhone_Seq
	FROM ROI_EmailPhone
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_Requestor'),ROI_Requestor_Seq
	FROM ROI_Requestor
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_RequestorType'),ROI_RequestorType_Seq
	FROM ROI_RequestorType
	WHERE Created_By_Seq < 1
UNION
	select  object_id(N'ROI_RequestorToAddress'),ROI_RequestorToAddress_Seq
	FROM ROI_RequestorToAddress
	WHERE Created_By_Seq < 1

--set all the created by to ADMIN
	UPDATE ROI_RequestorToAddress
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_RequestorTypeToBillingTier
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0
	  
	UPDATE ROI_RequestorTypeToBillingTemplate 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_RequestorToEmailPhone 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_Address 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_EmailPhone 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_Requestor 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0

	UPDATE ROI_RequestorType 
	SET Created_By_Seq = 1
	WHERE Created_By_Seq < 0



--Show the number of rows effected
Select OBJECT_Name(TableObjectID) AS [Table],COUNT(TableRowID) AS [Records Added] FROM ROI_MigrationLOV
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



Print 'Completed ROI_LinkingMigratedAndClassicRequestData at ' + cast(getdate() as varchar(20));
