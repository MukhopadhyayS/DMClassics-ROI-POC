USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EPRS_UpdateCabinetForADI]') AND type in (N'P'))
DROP PROCEDURE [dbo].[EPRS_UpdateCabinetForADI]
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

/*******************************************************************************************************************
* Stored Procedure	: CABINET..EPRS_UpdateCabinetForADI
*					
* Description		: 
*
* Script Type		: SQL (ALTER  PROCEDURE)
*									
* Input Parameters	: Name			Type		Description
* 			---------		-------		----------------
*			@UserInstanceID 	integer	
*			
*								
* Output Parameters	: <none>
*
* Resultset		: <none>
*									
* Return Status		:  0	- Success
*			: -1 	- Update cabinet failed 
*									
* Usage			: <none>
*									
* Local Variables	: Name			Type		Description
* 			---------		-------		----------------
*			  <none>
*									
* Called By		: HPF (Horizon Patients Folder)
*									
* Calls			: Cabinet.dbo.ADI_WORK
*			: Cabinet.dbo.DOCUMENTS
* 			: Cabinet.dbo.VERSIONS
*			: Cabinet.dbo.PAGES			
*									
* Data Modifications	: Cabinet.dbo.VERSIONS			
*									
* Updates		: Name			Date		Purpose
*			---------		-------		------------- 							
*			...			05/06/99	Created
*			KGill/MDMeersma		05/29/02	Modified to use new Cabinet table structure 
*			MDMeersma/JChen		10/02/02	Modified logic for ADI and new Cabinet Table
*           		Maxim Kantor        	06/28/2006  	Modified logic for ADI outbound
*			Joseph Chen		05/11/2009	Fix HL7 outbound version 0 issue
*			Joseph Chen		10/01/2010	Fix Global document
*			Joseph Chen		06/02/2011	Remove redundant join from outbound_documents to check @filename 
*									15.0.X hotfix version
*			Nawneet Jha		05/15/2013	Modified to support FTS with a PRIORITY of 60 for the default insert
*			Hai Nguyen		07/29/2013	Modified FTS_WorkQueue inserts to include select DISTINCT to fix error
*									with ADI document or page move for multipage document (PDF, HL7)
*			Thomas Hart		08/12/2013	Fixed sytax error in the creation of the stored procedure
*			Nawneet Jha		09/16/2013	Modified to change FTS Priority from 60 to 40
*			Roshini Manickam	09/18/2013  	Modified IF EXISTS and changed IndexWorkStatusID to 2 instead of 0 to INSERT statement
*			Daniel Schmidt	10/7/2013	Correcting FTS_IndexWorkQueue issue that occurs when user ADI's multiple pages into a FTS indexed text document
*******************************************************************************************************************/

CREATE procedure [dbo].[EPRS_UpdateCabinetForADI]
	@UserInstanceID integer,
	@debug int = 0

as
BEGIN TRANSACTION update_cabinet
	declare @encounter varchar(20)
   BEGIN 
	-- If only one duid exists, then it is a delete
	if @debug = 1 select 'check for single duid - then delete'
	-- Adding sourcefacility global variable to use for FTS_IndexWorkQueue
	declare @duid char(64), @targetduid char(64), @sourceduid char(64), @sourcefacility char(64)
	select @duid = '', @targetduid = '', @sourceduid = '', @sourcefacility = ''
	select @duid = duid from adi_work where UserInstanceId = @UserInstanceId group by duid
	if @@rowcount = 1 goto Delete_Cabinet_Page              

	-- Source DUID is always deleted = 'Y' and is always unique
	if @debug = 1 select 'set @sourceduid, err if not found'
	select @sourceduid = duid, @sourcefacility = facility from ADI_WORK where userinstanceid = @UserInstanceID and Deleted = 'Y' group by duid, facility
	if @@rowcount != 1 goto update_err -- either no source, or multiple source duids
	if @sourceduid = null or @sourceduid = '' goto update_err -- always should have a source duid
       
        --MK 06/28/2006 find source filename  
	declare @filename char(12)    
         -- check if a moved/deleted document doesn't have mixed pages
   	SELECT DISTINCT count(c.filename), c.filename FROM Cabinet c
  	INNER JOIN adi_work a ON c.duid = a. duid
        WHERE a.duid =  @sourceduid AND c.deleted = 'N' group by c.filename
   	if @@rowcount = 1 -- document pages have same filename, now lets check if it's HL7 or new COLD
   	--Joseph Chen 15.0 hotfix
   	begin
	   SELECT DISTINCT @filename = c.filename FROM Cabinet c
	   --INNER JOIN Documents d ON c.Duid = d.Duid
	   --INNER JOIN Outbound_Documents od ON d.Doc_Id = od.Doc_Id
	   WHERE c.duid = @sourceduid
	   AND c.deleted = 'N' 
   	end        

	-- Determine if page move or document move
	if @debug = 1 select 'determine if page or document move'
	if not exists 
	(select 1 from cabinet c where c.duid = @sourceduid and c.deleted <> 'Y' and c.imnet not in 
		(select a.imnet from adi_work a where a.deleted = 'Y' and userinstanceid = @userinstanceid))
	begin
		goto Move_Cabinet_Document
	end
	else 
	begin
		goto Move_Cabinet_Page
	end

Move_Cabinet_Document:
	if @debug = 1 select 'Move_Cabinet_Document:'

	-- If there are multiple duids then determine who is the target, source is always deleted = 'Y'
	-- but that is not true with deleted = 'N'.  Target DUID is deleted = 'N', that doesn't have a 'Y' with the same duid
	if @debug = 1 select 'set @targetduid, err if not found'
	select @targetduid = a1.duid from ADI_Work a1 where a1.userinstanceid = @UserInstanceId and a1.Deleted = 'N'  and not exists (select a2.duid from ADI_Work a2 where a2.duid = a1.duid and a2.userinstanceid = @UserInstanceId and a2.deleted = 'Y') group by duid
	if @@rowcount > 1 goto update_err -- can't have multiple target duids

	-- Nawneet Jha: Insert into FTS_IndexWorkQueue to delete the old index and add new index 	
	BEGIN
		IF EXISTS (SELECT 1 FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused')) 
		AND EXISTS (SELECT 1 FROM ADI_WORK A where A.facility = @sourcefacility and A.deleted = 'N' and A.userinstanceid = @UserInstanceID)
		   BEGIN
		   	    Update FTS_IndexWorkQueue 
				Set IndexWorkInsertedTime = GETDATE(), SubDomain = @sourcefacility 
				Where ContentID in (
				       SELECT imnet FROM adi_work a 
				       WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID)      
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT rtrim(p.IMNET), 'UPDATEMETADATA', GETDATE(), NULL, 2, 'HIS', @sourcefacility, 0, 40							 
				FROM PAGES p
			WHERE p.imnet in (
				       SELECT imnet FROM adi_work a, FTS_IndexWorkQueue fts  
				       WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID and fts.ContentID != a.imnet)
				AND p.deleted = 'N'
				AND p.FTS_IndexDate IS NOT NULL
		   END						
		ELSE IF EXISTS (SELECT 1 FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused')) 
			BEGIN
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT DISTINCT rtrim(p.IMNET), 'DELETE', GETDATE(), NULL, 2, 'HIS', a.facility, 0, 40 							 
				FROM PAGES p
				JOIN ADI_WORK a on p.imnet = a.imnet
				WHERE a.deleted = 'Y' and a.userinstanceid = @UserInstanceID
					AND p.deleted = 'N'
					AND p.FTS_IndexDate IS NOT NULL
		
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT DISTINCT rtrim(p.IMNET), 'ADD', GETDATE(), NULL, 2, 'HIS', a.facility, 0, 40 								 
				FROM PAGES p
				JOIN ADI_WORK a on p.imnet = a.imnet
				WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID
					AND p.deleted = 'N'
					AND p.FTS_IndexDate IS NOT NULL
			END	
		END
			
	
	-- If Move non-versioned Source then skip over logic for versioned source
	if @debug = 1 select 'check for non-versioned source'
	if not exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid)
	goto Move_Cabinet_Document_NonVersioned_Source

Move_Cabinet_Document_Versioned_Source:	
	if @debug = 1 select 'Move_Cabinet_Document_Versioned_Source:'

	-- If Source Document with multiple versions, and target document doesn't exist
	if @debug = 1 select 'Check for source multi-version, no target'
	If not exists (select 1 from Documents DT where DT.duid = @targetduid) AND
	exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid) 
	begin
		if @debug = 1 select 'source multi-version, no target logic'
		 -- 1) Create new doc_id
		 -- Non global doc insert
		 If not exists (select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID 
		 and ad.Deleted = 'N' and dn.GLOBAL='Y')
		 begin
                INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
		end
		ELSE
		-- Global doc insert
		-- global to new nonglobal
		if exists (select 1 from dbo.Documents where encounter ='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y')) and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
				select @encounter=''
				
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			@encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
			END
			
			-- nonglobal to new global doc (document move)
		else
		if exists (select 1 from dbo.Documents where encounter !='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y')) and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
				select @encounter=''
				
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			@encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
			END
                --2) Insert into outbound_documents new Doc_Id (Target DUID)
		--select 'insert outbound_documents four'
		if not exists(select 1 from outbound_documents where DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid))
		BEGIN
		INSERT INTO outbound_documents (DOC_ID, UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type)
		SELECT DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid), 
		UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type
		FROM outbound_documents 
		WHERE DOC_ID = (SELECT DOC_ID FROM Documents D WHERE D.duid = @sourceduid)
		END
		
                if @filename = 'HL7' or @filename = 'CLD' -- new COLD
                begin

					
	                -- 3)Insert into Versions new doc_id with an old status

					declare @sourceDoc_ID bigint,@TargetDoc_ID bigint
					select @sourceDoc_ID=DOC_ID FROM cabinet.dbo.Documents WHERE DUID = @sourceduid
					select @TargetDoc_ID=DOC_ID FROM cabinet.dbo.Documents WHERE DUID = @targetduid
					
					if not exists(select 1 from versions where doc_id=@TargetDoc_ID and VersionNumber=0)
					begin
	                INSERT INTO VERSIONS(DOC_ID, VersionNumber, VersionDate, DocumentStatus)
					SELECT  DISTINCT
					DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid),
					0,  GETDATE(),
					DocumentStatus = (SELECT DocumentStatus FROM Versions WHERE VersionNumber =  0 AND 
					DOC_ID = (SELECT DOC_ID FROM Documents WHERE DUID = @sourceduid)) 
					end
                  -- 4) Joseph Chen:  Update versions table with new doc_id 


					 UPDATE Versions
			         set DOC_ID=@TargetDoc_ID 
			         WHERE DOC_ID=@sourceDoc_ID and versionnumber !=0 
					
					
		                
	                --5) Joseph Chen: update pages with new version_id (action - insert)

					Update Pages
					set version_id = (select version_id from versions where doc_id = @TargetDoc_ID and Versionnumber = 0)
					where version_id = (select version_id from versions where doc_id = @SourceDoc_ID and Versionnumber = 0)

					
		end
                else
                begin
			-- Update Versions rows to new Target doc_id in Documents
			UPDATE Versions
			set DOC_ID = (select DOC_ID from Documents DT where DT.DUID = @targetduid)
			FROM Documents DS, Versions V
			WHERE DS.DOC_ID = V.DOC_ID and DS.DUID = @sourceduid
                end  
		
        	if @@error <> 0 goto update_err

		goto update_Cabinet_Commit
	end

	-- If Source Document with multiple versions
	-- and target document exists but only has a current version
	if @debug = 1 select 'check for source multi-version, target only has current version'
	If exists (select 1 from Documents DT, Versions V where DT.DOC_ID = V.DOC_ID and V.VersionNumber = 0 AND DT.DUID = @targetduid) AND
	not exists (select 1 from Documents DT, Versions V where DT.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DT.DUID = @targetduid) AND
	exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid)
	begin
		if @debug = 1 select 'source multi-version, target only has current version logic'
		-- Update Versions rows (VersionNumber > 0) to new Target doc_id in Documents
		UPDATE Versions
		set DOC_ID = (select DOC_ID from Documents DT where DT.DUID = @targetduid)
		FROM Documents DS, Versions V
		WHERE V.VersionNumber != 0 AND DS.DOC_ID = V.DOC_ID and DS.DUID = @sourceduid 
		if @@error <> 0 goto update_err
                ------***06/09/2006 MKantor 
			--select 'Insert outbound_documents two'
			if not exists(select 1 from outbound_documents where DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid))
			begin
        	INSERT INTO outbound_documents (DOC_ID, UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type)
        	SELECT
                DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid), 
	        UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type
		FROM outbound_documents 
        	WHERE DOC_ID = (SELECT DOC_ID FROM Documents d WHERE d.duid = @sourceduid)
			end
        	if @@error <> 0 goto update_err
		-----*************************************************************************
		-- MERGE Source Pages (Current Version) to new Target and
		-- 

		-- Other Source Versioned Pages do not need to be updated as the Version
		-- Row was modified to point to the new target, and the pages rows were
		-- by default repointed at that time

		-- Moving the Page to a different Document, in the PAGES table, by reassigning
		-- the Page Record with the new Document's Version_ID; and also, update the
		-- Page numbers.
		UPDATE  PAGES 
		SET 	page	   = A.page,
			/* Need to change the Version_ID to reference the Document */
			/* where the Page(s) are moving to. 			   */
			version_id = (	SELECT VV.version_id
					FROM   DOCUMENTS DD INNER JOIN Versions VV 
					ON  DD.DOC_ID = VV.DOC_ID 
					WHERE 	DD.duid = A.duid
				 	AND 	VV.VersionNumber = 0 )	
		FROM	Pages P INNER JOIN ADI_WORK  A	ON P.imnet = A.imnet
		WHERE 	A.userinstanceid = @UserInstanceID 
		AND	A.deleted 	 = 'N' 
		AND 	P.deleted 	 = 'N'
		if @@error <> 0 goto update_err
			
		goto Update_Cabinet_Commit
	end	

	-- If Source Document and Target Document both have multiple versions
	if @debug = 1 select 'check for source and target both have multi-versions'
	If exists (select 1 from Documents DT, Versions V where DT.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DT.DUID = @targetduid) AND
	exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid) 
	begin
	if @debug = 1 select 'source and target both have multi-versions logic'
		-- Change Source Versions rows to Target Document
		-- Renumber the VersionNumber based on the max of the Target version
		
		declare @version_id int, @doc_id bigint, @versionnumber int
		declare ReNumberVersion_Cursor cursor for
		select v.version_id, v.doc_id, v.versionnumber from Documents d, Versions v
		where d.doc_id = v.doc_id AND v.VersionNumber != 0 AND d.duid = @sourceduid
		FOR UPDATE OF v.doc_id, v.versionnumber
		
		open ReNumberVersion_Cursor
		fetch next from ReNumberVersion_Cursor into @version_id, @doc_id, @versionnumber
		while @@fetch_status =0
		begin
			update Versions
			set doc_id = (select doc_id from Documents d where d.duid = @targetduid),
			    versionnumber = (select max(VersionNumber) + 1 from Documents d, Versions v where d.doc_id = v.doc_id and d.duid = @targetduid)
			where current of ReNumberVersion_Cursor
			if @@error <> 0 goto update_err
		        ------***06/09/2006 MKantor 
			--select 'Insert outbound_documents three'
			if not exists(select 1 from outbound_documents where DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid))
			begin
			INSERT INTO outbound_documents (DOC_ID, UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type)
        		SELECT
               		DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid), 
	        	UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type
			FROM outbound_documents 
        		WHERE DOC_ID = (SELECT DOC_ID FROM Documents d WHERE d.duid = @sourceduid)
			end
        		if @@error <> 0 goto update_err
			-----*************************************************************************
			fetch next from ReNumberVersion_Cursor into @version_id, @doc_id, @versionnumber
		
		end
		close ReNumberVersion_Cursor
		deallocate ReNumberVersion_Cursor

		-- Moving the Page to a different Document, in the PAGES table, by reassigning
		-- the Page Record with the new Document's Version_ID; and also, update the
		-- Page numbers.
		UPDATE  PAGES 
		SET 	page	   = A.page,
			/* Need to change the Version_ID to reference the Document */
			/* where the Page(s) are moving to. 			   */
			version_id = (	SELECT VV.version_id
					FROM   DOCUMENTS DD INNER JOIN Versions VV 
					ON  DD.DOC_ID = VV.DOC_ID 
					WHERE 	DD.duid = A.duid
				 	AND 	VV.VersionNumber = 0 )	
		FROM	Pages P INNER JOIN ADI_WORK  A	ON P.imnet = A.imnet
		WHERE 	A.userinstanceid = @UserInstanceID 
		AND	A.deleted 	 = 'N' 
		AND 	P.deleted 	 = 'N'
                
		if @@error <> 0 goto update_err

		goto update_Cabinet_Commit
	end

	goto update_err -- Didn't meet any conditions above, something is wrong

Move_Cabinet_Document_NonVersioned_Source:
	if @debug = 1 select 'Move_Cabinet_Document_NonVersioned_Source:'

	-- If Source Document with only current version, and target document doesn't exist
	if @debug = 1 select 'Check for source only current version, no target'
	If not exists (select 1 from Documents DT where DT.duid = @targetduid) AND
	not exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid) 
	begin
		if @debug = 1 select 'source only current version, no target logic'
		--****************************************************
                -- 1) Create new doc_id
                if not exists (select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID 
                and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
                
                INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
		  END
		else
		
		-- global to new nonglobal
		if exists (select 1 from dbo.Documents where encounter ='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y')) and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
				select @encounter=''
				
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			@encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
			END
			
			-- nonglobal to new global doc (document move)
		else
		if exists (select 1 from dbo.Documents where encounter !='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y')) and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
				select @encounter=''
				
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			@encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
			END
                --2) Insert into outbound_documents new Doc_Id (Target DUID)
		--select 'insert outbound_documents four'
		if not exists(select 1 from outbound_documents where DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid))
		BEGIN
		INSERT INTO outbound_documents (DOC_ID, UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type)
		SELECT DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid), 
		UDI_Entity_Identifier, Sending_Application, UDI_namespace, UDI_ID, UDI_ID_Type
		FROM outbound_documents 
		WHERE DOC_ID = (SELECT DOC_ID FROM Documents D WHERE D.duid = @sourceduid)
		END
		
                if @filename = 'HL7' or @filename = 'CLD' -- new COLD
                begin
	                -- 3)Insert into Versions new doc_id with an old status
	                INSERT INTO VERSIONS(DOC_ID, VersionNumber, VersionDate, DocumentStatus)
			SELECT  DISTINCT
			DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid),
			0,  
			GETDATE(),
			DocumentStatus = (SELECT DocumentStatus FROM Versions WHERE VersionNumber =  0 AND DOC_ID = (SELECT DOC_ID FROM Documents WHERE DUID = @sourceduid)) 
		                
	                --4) update pages with new version_id (action - insert)
	        	UPDATE Pages
			SET version_id = (SELECT Version_Id FROM Versions WHERE DOC_ID = (SELECT DOC_ID FROM Documents DT WHERE DT.DUID = @targetduid) )
			WHERE imnet in (SELECT imnet FROM adi_work a WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID)
		end
                else
                begin
			-- Update Versions rows to new Target doc_id in Documents
			UPDATE Versions
			set DOC_ID = (select DOC_ID from Documents DT where DT.DUID = @targetduid)
			FROM Documents DS, Versions V
			WHERE DS.DOC_ID = V.DOC_ID and DS.DUID = @sourceduid
                end  
             
		-----*************************************************************************
		if @@error <> 0 goto update_err
                goto update_Cabinet_Commit
	end

	-- If Source Document with only current version
	-- and target document exists, doesn't matter if target only has current or multi-versions as
	-- the source only has a current version
	if @debug = 1 select 'check for source only current version, target has current version'
	If exists (select 1 from Documents DT, Versions V where DT.DOC_ID = V.DOC_ID and V.VersionNumber = 0 AND DT.DUID = @targetduid) AND
	not exists (select 1 from Documents DS, Versions V where DS.DOC_ID = V.DOC_ID and V.VersionNumber != 0 AND DS.DUID = @sourceduid)
	begin
		if @debug = 1 select 'source only current version, target has current version logic'

		-- MERGE Source Pages (Current Version) to new Target and
		-- 
		-- Other Source Versioned Pages do not need to be updated as the Version
		-- Row was modified to point to the new target, and the pages rows were
		-- by default repointed at that time

		-- Moving the Page to a different Document, in the PAGES table, by reassigning
		-- the Page Record with the new Document's Version_ID; and also, update the
		-- Page numbers.
		UPDATE  PAGES 
		SET 	page	   = A.page,
			/* Need to change the Version_ID to reference the Document */
			/* where the Page(s) are moving to. 			   */
			version_id = (	SELECT VV.version_id
					FROM   DOCUMENTS DD INNER JOIN Versions VV 
					ON  DD.DOC_ID = VV.DOC_ID 
					WHERE 	DD.duid = A.duid
				 	AND 	VV.VersionNumber = 0 )	
		FROM	Pages P INNER JOIN ADI_WORK  A	ON P.imnet = A.imnet
		WHERE 	A.userinstanceid = @UserInstanceID 
		AND	A.deleted 	 = 'N' 
		AND 	P.deleted 	 = 'N'

		if @@error <> 0 goto update_err
			
		goto Update_Cabinet_Commit
	end	

	goto update_err -- Didn't meet any conditions above, something is wrong

Move_Cabinet_Page:
	if @debug = 1 select 'Move_Cabinet_Page'
	-- Needs to check to see if it is necessary to create a new Document Header, 
	-- in the DOCUMENTS table.  This will be required if we were moving the Page(s) 
	-- to a new Document.
	
	-- Nawneet Jha: Insert into FTS_IndexWorkQueue to delete the old index and add new index
	declare @sourceimnet char(64)
	select @sourceimnet = a.imnet from ADI_Work a where A.deleted = 'Y' and a.facility = @sourcefacility 
	if @@rowcount != 1 goto update_err --Inside Move_Cabinet_Page found multiple source imnet
	BEGIN 	
		IF EXISTS (SELECT 1 FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused')) 
		AND EXISTS (SELECT 1 FROM ADI_WORK A where A.facility = @sourcefacility and A.imnet = @sourceimnet and A.deleted = 'N' and A.userinstanceid = @UserInstanceID)
		   BEGIN
			    Update FTS_IndexWorkQueue 
				Set IndexWorkInsertedTime = GETDATE(), SubDomain = @sourcefacility 
				Where ContentID in (
				       SELECT imnet FROM adi_work a 
				       WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID)
				       
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, 
					IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT rtrim(p.IMNET), 'UPDATEMETADATA', GETDATE(), NULL, 2, 'HIS', @sourcefacility, 0, 40							 
				FROM PAGES p
				WHERE p.imnet in (
				       SELECT imnet FROM adi_work a, FTS_IndexWorkQueue fts  
				       WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID and fts.ContentID != a.imnet)
				AND p.deleted = 'N'
				AND p.FTS_IndexDate IS NOT NULL
				
		   END						
		ELSE IF EXISTS (SELECT 1 FROM SYSPARMS_GLOBAL WITH (NOLOCK) WHERE GlobalName = 'FTS_Indexing.Index_Write' AND GlobalVariant in ('true','Paused')) 
			BEGIN
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT DISTINCT rtrim(p.IMNET), 'DELETE', GETDATE(), NULL, 2, 'HIS', a.facility, 0, 40						 
				FROM PAGES p
				JOIN ADI_WORK a on p.imnet = a.imnet
				WHERE a.deleted = 'Y' and a.userinstanceid = @UserInstanceID
					AND p.deleted = 'N'
					AND p.FTS_IndexDate IS NOT NULL
		
				INSERT INTO FTS_IndexWorkQueue (ContentID, IndexAction, IndexWorkInsertedTime, IndexWorkPickupTime, IndexWorkStatusID, Domain, SubDomain, RetryCount, Priority)
				SELECT DISTINCT rtrim(p.IMNET), 'ADD', GETDATE(), NULL, 2, 'HIS', a.facility, 0, 40								 
				FROM PAGES p
				JOIN ADI_WORK a on p.imnet = a.imnet
				WHERE a.deleted = 'N' and a.userinstanceid = @UserInstanceID
					AND p.deleted = 'N'
					AND p.FTS_IndexDate IS NOT NULL
			END	
	END

	If exists (select ad.duid from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid =@UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='N' and ad.duid not in(
	select duid from adi_work where deleted='Y') )
				begin
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
				SELECT 	DISTINCT encounter, mrn, facility, document, subtitle, datetime, duid
				FROM 	ADI_WORK  A WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
					AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
				
				end
	else
	
	-- global to global (pages move)
	if exists (select 1 from dbo.Documents where encounter ='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y'))	and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
				begin
				select @encounter=''
                INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
				SELECT 	DISTINCT @encounter, mrn, facility, document, subtitle, datetime, duid
				FROM 	ADI_WORK  A
				WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
				AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
				
				end	

			-- nonglobal to new global doc (pages move)
		else
		if exists (select 1 from dbo.Documents where encounter !='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y')) and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='Y')
			BEGIN
				select @encounter=''
				
				INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
		SELECT 	DISTINCT 
			@encounter, 
			mrn, 
			facility, 
			document, 
			subtitle, 
			datetime, 
			duid
		FROM 	ADI_WORK  A
		WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
		AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
			END
			else
			
				-- global to noglobal (pages move)
				
			
								if exists (select 1 from dbo.Documents where encounter ='' and DUID in (select DUID from dbo.adi_work where 
				userinstanceid = @UserInstanceID  and Deleted = 'Y'))	and exists 
				(select 1 from dbo.document_names dn join dbo.adi_work ad on dn.TAG=ad.document where ad.userinstanceid = @UserInstanceID and ad.Deleted = 'N' and dn.GLOBAL='n')
				begin
			
                INSERT INTO DOCUMENTS(encounter, mrn, facility, doc_name, subtitle, datetime, duid)
				SELECT 	DISTINCT @encounter, mrn, facility, document, subtitle, datetime, duid
				FROM 	ADI_WORK  A
				WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
				AND	NOT EXISTS ( SELECT * FROM DOCUMENTS WHERE  duid = A.duid )
				
				end	
				
				
			
			
			
	if @@error <> 0 goto update_err

	-- If the Document is new, then the assumption is that we had just created
	-- a new Document and will need to insert a record into the VERSIONS table 
	-- in order to reference the new Document record.
	INSERT INTO VERSIONS(DOC_ID, VersionNumber, VersionDate)
	SELECT  DISTINCT
		D.DOC_ID,
		0,  /* Current Version of the Document */
		GETDATE()
	FROM 	DOCUMENTS  D INNER JOIN ADI_WORK A ON D.duid = A.duid
	WHERE	A.userinstanceid = @UserInstanceID  and a.Deleted = 'N'
	AND	NOT EXISTS ( SELECT * FROM VERSIONS WHERE  DOC_ID = D.DOC_ID )

	if @@error <> 0 goto update_err

Delete_Cabinet_Page:
	if @debug = 1 select 'Delete_Cabinet_Page'
	-- Moving the Page to a different Document, in the PAGES table, by reassigning
	-- the Page Record with the new Document's Version_ID; and also, update the
	-- Page numbers.
	UPDATE  PAGES 
	SET 	page	   = A.page,
		/* Need to change the Version_ID to reference the Document */
		/* where the Page(s) are moving to. 			   */
		version_id = (	SELECT VV.version_id
				FROM   DOCUMENTS DD INNER JOIN Versions VV 
				ON  DD.DOC_ID = VV.DOC_ID 
				WHERE 	DD.duid = A.duid
			 	AND 	VV.VersionNumber = 0 )	
	FROM	Pages P INNER JOIN ADI_WORK  A	ON P.imnet = A.imnet
	WHERE 	A.userinstanceid = @UserInstanceID 
	AND	A.deleted 	 = 'N' 
	AND 	P.deleted 	 = 'N'

	IF @@error <> 0 goto update_err

	-- Setting the moved Page record(s) for deletion, in the PAGES table. 
	-- This is only effective if the Page(s) are only being deleted and 
	-- are not a result of the Page(s) being moved to another Document.

	UPDATE 	PAGES 
	SET 	deleted   = 'Y'
	FROM    DOCUMENTS D 
		   INNER JOIN Versions V ON  D.DOC_ID = V.DOC_ID 
		   INNER JOIN Pages P ON V.Version_ID = P.version_id
		   INNER JOIN ADI_WORK A ON P.imnet = A.imnet
			AND D.duid = A.duid	
      	WHERE 	A.userinstanceid = @UserInstanceID
	AND 	A.deleted   	 = 'Y' 	
	if @@error <> 0 goto update_err

   End

Update_Cabinet_Commit:

if @debug = 1 select 'Update_Cabinet_Commit'
COMMIT TRANSACTION update_cabinet


RETURN 0

update_err:
if @debug = 1 select 'Update_err'
ROLLBACK TRANSACTION update_cabinet

RETURN -1 /* Update cabinet failed */
GO

GRANT EXECUTE ON [dbo].[EPRS_UpdateCabinetForAdi] TO [IMNET] AS [dbo]
GO
