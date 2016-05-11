USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DAL_Archive]') AND type in (N'P'))
DROP PROCEDURE [dbo].[DAL_Archive]
GO

/****************************************************************
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
*****************************************************************
* Procedure Name: DAL_ARCHIVE
* Description: 
*
*		Name			Type		Description
*		---------			-------		----------------
* Params In:	
*	            @reason varchar(128)
	            @oldImnet varchar(22)
	            @newImnet varchar(22)
	            
* Params Out: @output varchar(64)

* Database: CABINET
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
* 	
*        Jeff Chen            07/29/2005      CQ #20812
*        Maxim Kantor         08/04/2005      CQ #20812  added Update for ROI_Req_Release_Page table
*        Maxim Kantor         10/02/2005      CQ #20812  promoted 6.2.2 to 11.0
*	     Judy Jiang	          05/17/2006      Updated/HPF11.0: add the new column from Pages_views.ContentCount.
*        RC                   12/15/2009      CR#324,728 update imnet id in tngROI
*		 I.Politykina		  09/12/2012      Update per MPF 16.0 
*        Roshini Manickam	  09/23/2012      Adding the new Archived images to the FTS queue for indexing.
\*****************************************************************************************************/

CREATE PROCEDURE [dbo].[DAL_Archive](@reason varchar(128), @oldImnet varchar(22), @newImnet varchar(22), @output varchar(64) OUTPUT)
AS
BEGIN 
	INSERT INTO CAB_DEL_ITEMS_view (IMNET_ID, DUID, DATE_DELETED, REASON)
	SELECT P.IMNET, D.DUID, GETDATE(), @reason FROM Documents_View D JOIN Versions_View V ON (D.DOC_ID = V.DOC_ID) JOIN
		Pages_View P ON (V.Version_ID = P.Version_ID)
		WHERE P.IMNET = @oldImnet AND P.DELETED='N' 
	IF @@error <> 0
		GOTO err_insert_cab_del_items
	
	UPDATE Pages_View SET DELETED='Y' WHERE IMNET = @oldImnet
	If @@error <> 0
		GOTO err_update_page

	UPDATE ROI_Request_Page SET Imnet = @newImnet WHERE Imnet = @oldImnet
        If @@error <> 0
		GOTO err_update_ROI_Request_Page

	UPDATE ROI_Req_Release_Page SET Imnet = @newImnet WHERE Imnet = @oldImnet
        If @@error <> 0
		GOTO err_update_ROI_Req_Release_Page
	
    --added 12/15/2009
    DECLARE @RC int
    EXECUTE @RC = [cabinet].[dbo].[ROI_UpdateImnetInRequestMain]  @OldImnet, @NewImnet
    IF @RC <> 0
    GOTO err_update_RequestMain
    --end added 12/15/2009
	
	-- added 1/15/2013 - For 16.0 ROI
	DECLARE @RCore int
    EXECUTE @RCore = [cabinet].[dbo].[ROI_UpdateImnetInRequestCore]  @OldImnet, @NewImnet
    IF @RCore <> 0
    GOTO err_update_RequestCore
	-- end added 1/15/2013
	

	INSERT INTO Pages_view (imnet, version_id, imagedate, page, filename, archived, deleted,contentcount) 
	SELECT @newImnet, P.Version_ID, P.imagedate, P.page, P.filename, 'Y', 'N' ,P.contentcount
	FROM Pages_View P WHERE P.imnet = @oldImnet
	-- added 9/23/2013 - FOR FTS
	IF EXISTS (SELECT 1 FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused')) 
    BEGIN
      INSERT INTO FTS_IndexWorkQueue 
                                  (ContentID, 
                                  IndexAction, 
                                  IndexWorkInsertedTime, 
                                  IndexWorkPickupTime, 
                                  IndexWorkStatusID,
                                  Domain, 
                                  SubDomain, 
                                  RetryCount,
                                  Priority)
              
              SELECT rtrim(@newImnet),
                     'ADD',
                     GETDATE(),
                     NULL,
                     2,
                     'HIS',
                     rtrim(DO.FACILITY),
                     0,
                     50                     
              FROM Pages_View P
              JOIN VERSIONS V
                     ON V.VERSION_ID = P.VERSION_ID
              JOIN DOCUMENTS DO 
                     ON DO.DOC_ID = V.DOC_ID         
              WHERE P.imnet = @oldImnet
    END
    --end added 9/23/2013 
    
	IF @@error <> 0
		GOTO err_insert_page
		
	SELECT @output = 'OK'
	RETURN

	err_insert_cab_del_items:
		SELECT @output = 'Could not insert into CAB_DEL_ITEMS table.'
		RETURN

	err_update_page:
		SELECT @output = 'Could not update Pages_View.'
		RETURN

	err_insert_page:
		SELECT @output = 'Could not insert into Pages_View.'
		RETURN

    err_update_ROI_Request_Page:
		SELECT @output = 'Could not update ROI_Request_Page table.'
		RETURN

    err_update_ROI_Req_Release_Page:
		SELECT @output = 'Could not update ROI_Req_Release_Page table.'
		RETURN
		
	err_update_RequestMain:
	    SELECT @output = 'Could not update ROI_RequestMain.'
	    RETURN
		
	err_update_RequestCore:	
		SELECT @output = 'Could not update ROI_RequestMain.'
	    RETURN
	
	err_update_ROI_Pages:
		SELECT @output = 'Could not update ROI_Pages.'
	    RETURN

END
GO
GRANT EXECUTE ON [dbo].[DAL_Archive] TO [DAL] AS [dbo]
GO
