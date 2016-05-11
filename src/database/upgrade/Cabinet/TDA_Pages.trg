USE [CABINET]
GO

IF EXISTS (SELECT * 
			FROM SYS.TRIGGERS 
            WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[TDA_Pages]'))
                     
DROP TRIGGER [DBO].TDA_Pages

GO

CREATE Trigger [dbo].[TDA_Pages]
ON [dbo].[Pages]
AFTER DELETE
AS

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
* Trigger:    TDA_Pages
* Creation Date:     02/06/2009 
*      
* Written by: RC
*                                                             
* Purpose:    This trigger will remove any entries in the cabinet database 
*                    containing referances to imnets which are deleted
*                    If the last page of a version is deleted the version is deleted from the versions table
* Database:   CABINET
* Table:      Pages
*                                                             
* Data Modifications: may delete records from assignments, pages_corrected,signatures and versions
*                                                             
* Updates: 
*      	Name          	Date          	Purpose
*     	---------       -------		-------------        
*      	Joseph Chen	05/12/2011    	ADI outbound fix. CR348,178 15.0 HotFix version 
*      	Thomas Hart     12/18/2012    	Added INSERT to JOBQUEUE table on Physical Deletion of Data   
*      	Thomas Hart     12/31/2012    	Modified Trigger to be on FTS_Index_Work_Queue table per Roshini Manickam
*      	Thomas Hart     01/02/2013    	Modified Trigger to pull FACILITY for SubDomain from Documents table for IMNET per Roshini Manickam     
*      	Joseph Chen     01/10/2013    	Remove Insert Audit.dbo.Audit_trail
*      	Thomas Hart     01/11/2013    	Modified name of column IndexWordStatus to IndexWorkStatusID
*      	Thomas Hart     01/22/2013    	Added rtrim to IMNET and FACILITY fields for Roshini   
*      	Thomas Hart     01/28/2013    	Added SYSPARMS_GLOBAL and Pages.IndexDate conditons
*      	Thomas Hart	02/06/2013	Added Comment for Trigger Action to script
*      	Thomas Hart	09/18/2013	Modified IF EXISTS and addded PRIORITY to INSERT statement
*	R Manickham	09/19/2013	Modified IndexWorkStatusID from '0' to '2'		
**************************************************************************************************************/
SET NOCOUNT ON

BEGIN TRANSACTION TDA_PagesT01

DELETE FROM Assignments WHERE Ref_Id in (Select Imnet from Deleted)
IF @@ERROR <> 0 GOTO ErrorTDA_PagesT01

DELETE FROM signatures WHERE Imnet in (Select Imnet from Deleted)
IF @@ERROR <> 0 GOTO ErrorTDA_PagesT01

/*Trigger only fires when a document is physically 'DELETED' from the PAGES table, a deleted flag = 'N' and FTS_Indexing is TRUE*/


IF EXISTS (SELECT * FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused'))
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
              
              SELECT rtrim(d.IMNET),
                     'DELETE',
                     GETDATE(),
                     NULL,
                     2,
                     'HIS',
                     rtrim(do.FACILITY),
                     0,
                     50
                     
              FROM DELETED d
              JOIN VERSIONS v
                     ON v.VERSION_ID = d.VERSION_ID
              JOIN DOCUMENTS do 
                     ON do.DOC_ID = v.DOC_ID
         
              WHERE d.DELETED = 'N'
                     AND d.FTS_IndexDate IS NOT NULL
       END
       
IF @@ERROR <> 0 GOTO ErrorTDA_PagesT01

COMMIT TRANSACTION TDA_PagesT01

SET NOCOUNT OFF

RETURN

ErrorTDA_PagesT01:
ROLLBACK TRANSACTION TDA_PagesT01

SET NOCOUNT OFF
RETURN
GO
