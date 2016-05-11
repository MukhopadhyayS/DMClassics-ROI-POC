SET QUOTED_IDENTIFIER ON
GO

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
* Procedure             : ROI_MigrationAR.sql
* Creation Date         : 05/26/2009
*					
* Written by            : RC	
*									
* Purpose               : Move data from Classic ROI to ROI13.5
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
* Usage                 : ROI_MigrationAR.sql 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[ROI_Requester]
*                       : [Cabinet].[dbo].[ROI_Requestor]
*                       : [Cabinet].[dbo].[ROI_RequestorType]
*                       : [Cabinet].[dbo].[ROI_EmailPhone]
*                       : [Cabinet].[dbo].[ROI_RequestorToEmailPhone]
*                       : [Cabinet].[dbo].[ROI_RequestorToAddress]
*                       : [Cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
*                       : [Cabinet].[dbo].[ROI_Address]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        05/26/2009  Created   
*
***************************************************************************************************************************************/
USE CABINET
GO
SET NOCOUNT ON
Print 'Starting AR migration  at ' + cast(getdate() as varchar(20));

