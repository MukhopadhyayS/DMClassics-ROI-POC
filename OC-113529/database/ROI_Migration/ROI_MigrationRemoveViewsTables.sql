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
* Procedure             : ROI_MigrateRemoveViewsTables
* Creation Date         : 03/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Clean up after classic ROI migration
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
* Usage                 : ROI_MigrateRemoveViewsTables = deletes tables and views created for ROI Migration
*																
* Called By             : migration process
*									
* Tables used           : 
*			
* Data Modifications	: none
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        04/02/2009  Created new stored procedure.  
*
***************************************************************************************************************************************/
SET NOCOUNT ON
Use CABINET;
Select 'Starting ROI_MigrationDeleteViewsTables at '+ Cast(getdate() as varchar(20));

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicInvoice_View]'))
DROP VIEW [dbo].[ROI_ClassicInvoice_View]

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestHeader_View]'))
DROP VIEW [dbo].[ROI_ClassicRequestHeader_View]

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicRequestDetail_View]'))
DROP VIEW [dbo].[ROI_ClassicRequestDetail_View]

IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ROI_ClassicReleaseDetail_View]'))
DROP VIEW [dbo].[ROI_ClassicReleaseDetail_View]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AA_ROI_ClassicInvoice]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_ClassicInvoice]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_CLassicRequestHeader]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_CLassicRequestHeader]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_CLassicRequestDetail]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_CLassicRequestDetail]

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]') AND type in (N'U'))
DROP TABLE [Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]

Select 'Completed ROI_MigrationCreateViewsTables at '+ Cast(getdate() as varchar(20));



