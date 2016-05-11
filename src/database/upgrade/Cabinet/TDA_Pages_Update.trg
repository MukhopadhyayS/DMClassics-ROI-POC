USE [cabinet]
GO

IF EXISTS (SELECT * 
			FROM SYS.TRIGGERS 
            WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[TDA_Pages_Update]'))
                     
DROP TRIGGER [DBO].[TDA_Pages_Update]

GO

CREATE Trigger [DBO].[TDA_Pages_Update]
ON [dbo].[Pages]
AFTER UPDATE
AS

/***************************************************************************************************************
* Trigger			: PagesOnUpdates
* Creation Date		: 12/20/2012
* Copyright			: COPYRIGHT MCKESSON 2008
*					: ALL RIGHTS RESERVED
*
* 					: The copyright to the computer program(s) herein
* 					: is the property of McKesson. The program(s)
* 					: may be used and/or copied only with the written
* 					: permission of McKesson or in accordance with
* 					: the terms and conditions stipulated in the     
* 					: agreement/contract under which the program(s)
* 					: have been supplied.
*					
* Written by		: Thomas Hart 
*									
* Purpose			: Create UPDATE Trigger for Delete Action on dbo.Pages
*				
* Database			: Cabinet
*									
* Requirements		: None
*									
* Data Modifications: Inserts row(s) into JOBQUEUE table upon 'Deleted' Flag change in Pages table.
*									
* Updates			: 
* Name				Date		Purpose
* ---------			-------		------------- 
*		
* Thomas Hart		12/20/2012	Created UPDATE Trigger to INSERT data into JOBQUEUE table per Nawneet Jha
* Thomas Hart		12/31/2012	Modified Trigger to be on FTS_Index_Work_Queue table per Roshini Manickam
* Thomas Hart		01/02/2013	Modified Trigger to pull FACILITY for SubDomain from Documents table for IMNET per Roshini Manickam
* Thomas Hart		01/11/2013	Modified name of column IndexWorkStatus to IndexWorkStatusID
* Thomas Hart		01/22/2013	Added rtrim to IMNET and FACILITY fields for Roshini
* Thomas Hart		01/28/2013	Added SYSPARMS_GLOBAL and Pages.IndexDate conditons
* Thomas Hart		02/06/2013	Added Comment for Trigger Action to script and new condition for archived = 'N'
* Thomas Hart		09/18/2013	Modified IF EXISTS and addded PRIORITY to INSERT statement
* R Manickham		09/19/2013	Modified IndexWorkStatusID from '0' to '2'		
*	
***************************************************************************************************************/
BEGIN

SET NOCOUNT ON

IF UPDATE (DELETED)

/*Trigger only fires when a documents deleted flag has been updated to 'Y', its archived flag is N or null and FTS_Indexing is TRUE*/

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
		
	SELECT rtrim(i.IMNET),
			'DELETE',
			GETDATE(),
			NULL,
			2,
			'HIS',
			rtrim(do.FACILITY),
			0,
			50
			
	FROM INSERTED i
	JOIN DELETED d
		ON i.IMNET = d.IMNET
	JOIN PAGES p
		ON i.IMNET = p.IMNET
			AND p.FTS_IndexDate IS NOT NULL
	JOIN VERSIONS v
		ON v.VERSION_ID = p.VERSION_ID
	JOIN DOCUMENTS do 
        ON do.DOC_ID = v.DOC_ID 
        
	WHERE i.DELETED = 'Y'
		and (i.ARCHIVED = 'N' or i.ARCHIVED is null)
		and (d.DELETED = 'N' or d.DELETED is null)
	END
END

SET NOCOUNT OFF