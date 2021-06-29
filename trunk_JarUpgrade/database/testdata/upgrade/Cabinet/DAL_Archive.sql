USE [cabinet]
GO

/****** Object:  StoredProcedure [dbo].[DAL_Archive]    Script Date: 12/16/2009 13:00:16 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DAL_Archive]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[DAL_Archive]
GO

USE [cabinet]
GO

/****** Object:  StoredProcedure [dbo].[DAL_Archive]    Script Date: 12/16/2009 13:00:16 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER OFF
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DAL_Archive]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'/****************************************************************
*  	COPYRIGHT MCKESSON 2006
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of MCKESSON. The program(s)
* 	may be used and/or copied only with the written
* 	permission of MCKESSON or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
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
\*****************************************************************************************************/

CREATE PROCEDURE [dbo].[DAL_Archive](@reason varchar(128), @oldImnet varchar(22), @newImnet varchar(22), @output varchar(64) OUTPUT)
AS
BEGIN 
	INSERT INTO CAB_DEL_ITEMS_view (IMNET_ID, DUID, DATE_DELETED, REASON)
	SELECT P.IMNET, D.DUID, GETDATE(), @reason FROM Documents_View D JOIN Versions_View V ON (D.DOC_ID = V.DOC_ID) JOIN
		Pages_View P ON (V.Version_ID = P.Version_ID)
		WHERE P.IMNET = @oldImnet AND P.DELETED=''N'' 
	IF @@error <> 0
		GOTO err_insert_cab_del_items
	
	UPDATE Pages_View SET DELETED=''Y'' WHERE IMNET = @oldImnet
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

	INSERT INTO Pages_view (imnet, version_id, imagedate, page, filename, archived, deleted,contentcount) 
	SELECT @newImnet, P.Version_ID, P.imagedate, P.page, P.filename, ''Y'', ''N'' ,P.contentcount
	FROM Pages_View P WHERE P.imnet = @oldImnet
	
	IF @@error <> 0
		GOTO err_insert_page
		
	SELECT @output = ''OK''
	RETURN

	err_insert_cab_del_items:
		SELECT @output = ''Could not insert into CAB_DEL_ITEMS table.''
		RETURN

	err_update_page:
		SELECT @output = ''Could not update Pages_View.''
		RETURN

	err_insert_page:
		SELECT @output = ''Could not insert into Pages_View.''
		RETURN

    err_update_ROI_Request_Page:
		SELECT @output = ''Could not update ROI_Request_Page table.''
		RETURN

    err_update_ROI_Req_Release_Page:
		SELECT @output = ''Could not update ROI_Req_Release_Page table.''
		RETURN
		
	err_update_RequestMain:
	    SELECT @output = ''Could not update ROI_RequestMain.''
	    RETURN

END
' 
END
GO

GRANT EXECUTE ON [dbo].[DAL_Archive] TO [DAL] AS [dbo]
GO


