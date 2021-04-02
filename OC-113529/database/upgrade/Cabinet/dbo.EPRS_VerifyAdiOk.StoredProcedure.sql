USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EPRS_VerifyAdiOk]') AND type in (N'P'))
DROP PROCEDURE [dbo].[EPRS_VerifyAdiOk]
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

/*****************************************************************
* Procedure Name: EPRS_VerifyAdiOk
* Description: This procedure virifies if a page/document can be moved
*
*		Name			   Type		Description
*		---------		   -------	----------------
* Params In:	
*	        @UserInstanceId            integer
	            
* Params Out: 
		@Verify                    integer
		@Result   		   integer  
		@Status                    char(100) 
* Database: CABINET
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
*   Maxim Kantor	08/11/2004	Build #21027 and #20984    CQ #20994 Updated --Check for Deficiencies
*   Maxim Kantor	08/26/2004	CQ #20994  Check for Deficiencies didn't use @Cnt parameter, so I added it
*	Maxim Kantor	06/21/2006	11.0 outbound need to check if NewCOLD won't be eligible for outbound
*	Maxim Kantor	08/10/2006	CQ #28081 added one more check if a source file is not NEW COLD don't even check other conditions and exit
*   Maxim Kantor	09/29/2006	ST #173,317 and 173,318 added some logic for the targetsource
*   Maxim Kantor	09/28/2007	CR #247,680 and #247,481
*	Joseph Chen		05/27/2010	change for NEW ROI.  HPF 13.5.1.  support both old and new roi CR331105
*	MD Meersma		10/05/2010	Performance Improvements
*	Matt Goddard	2/14/2011	Converted aggregate to bitwise flags
*	Matt Goddard	6/27/2011	Proper fix for CR 345786 
*	Joseph Chen		11/03/2011	Check ROI document status (completed) for 'released'CR# 357,501
*	Joseph Chen		11/22/2011	Add (canceled, denied,auth-received denied) to check ROI doc logic. Request from Product Management
*	Monty Meersma	01/03/2012	(15.1.X version) change check logic to speed up performance 
*	Joseph Chen		03/12/2012	CR 365809, change logic to not display bogus 'The document will not be eligible to outbound.' message
*	MD Meersma		01/22/2013	Rewrite ROI_SearchLOV query for performance CR 376191
*	OFS				12/15/2012  Procedures related to XML Tables are merged
*   Shahm           03/05/2013  Check ROI Request existing for 16.0. 
*	Joseph Chen     05/30/2013	Update ROI warning message per Kuersten's suggestion.  CR 378088
*	Brian Campbel	09/13/2013	Remove RETURN statement
*/

CREATE PROCEDURE [dbo].[EPRS_VerifyAdiOk]
@UserInstanceId integer,
@Verify integer output,
@Result integer output,
@Status char(100) output
as

DECLARE @FLAG_ARCHIVING int;
DECLARE @FLAG_SIGNATURES int;
DECLARE @FLAG_DEFICIENCIES int;
DECLARE @FLAG_ASSIGNMENTS int;
DECLARE @FLAG_ROI_OLD int;
DECLARE @FLAG_ROI_NEW int;
DECLARE @FLAG_NO_OUTBOUND int;
DECLARE @FLAG_ROI_CORE int;

SET @FLAG_ARCHIVING = 1;
SET @FLAG_SIGNATURES = 2;
SET @FLAG_DEFICIENCIES = 4;
SET @FLAG_ASSIGNMENTS = 8;
SET @FLAG_ROI_OLD = 16;
SET @FLAG_ROI_NEW = 32;
SET @FLAG_NO_OUTBOUND = 64;
SET @FLAG_ROI_CORE = 128;

select @Verify = 0

-- Check for Archiving
if exists (select 1 from adi_work aw join ARCHIVING a on aw.facility = a.FACILITY and aw.encounter = a.FOLDER
			where aw.userinstanceid = @UserInstanceId)
   begin
      select @Verify = @FLAG_ARCHIVING, @Result = 0, @Status = 'Archive Request(s) Exist!'
      return
   end

-- Check for Pending Signatures
if exists(select 1 from follow_up f, signatures s, adi_work a
   where s.imnet = a.imnet
     and f.id = s.id
     and f.status_id = 8
     and a.userinstanceid = @UserInstanceID)
   begin
      select @Verify = @FLAG_SIGNATURES, @Result = 0, @Status = 'Pending Signatures Exist!'
      return
   end

-- Check for Deficiencies
--MK 08/2004 CQ #20994
if exists(select 1 from follow_up f, adi_work a, cabinet c, signatures s
where c.imnet = s.imnet and 
      c.duid = a.duid and 
      f.id = s.id and 
      a.userinstanceid = @UserInstanceID)
   begin
      select @Verify = @FLAG_DEFICIENCIES, @Result = 0, @Status = 'One or more deficiencies exist and will be deleted.'
   end

-- Check for Assignments
if exists(select 1 from assignments a, adi_work aw
   where (a.ref_id  = aw.duid or a.ref_id = aw.imnet)
   and aw.deleted = 'Y'
   and aw.userinstanceid = @UserInstanceId)
   begin
      select @Verify = @Verify | @FLAG_ASSIGNMENTS, @Result = 0, @Status = 'One or more assignments exist and will be deleted.'
   end

-- Check for Requests (old ROI)
if exists(select 1 from ROI_Request_Page rqp, adi_work aw where rqp.imnet = aw.imnet and aw.userinstanceid = @UserInstanceID)
   begin
      select @Verify = @Verify | @FLAG_ROI_OLD, @Result = 0, @Status = 'One or more ROI XML requests exists.  Adjusting this page will delete the page from the request.'
   end


-- Check for ROI Request (Truly Releational ROI - 16.0)
if exists(SELECT 1 FROM  [cabinet].[dbo].[ROI_Requestcore] rc, 
		  [cabinet].[dbo].[ROI_RequestCoretoROI_Patients] patients,
		  [cabinet].[dbo].[ROI_Documents] documents,
		  [cabinet].[dbo].[ROI_Versions] versions,
		  [cabinet].[dbo].[ROI_Pages] pages,
		  [cabinet].[dbo].[adi_work] aw
	WHERE rc.ROI_RequestCore_Seq = patients.ROI_RequestCore_Seq 
		AND patients.ROI_Patients_Seq = documents.ROI_Patients_Seq
		AND documents.ROI_Documents_Seq = versions.ROI_Documents_Seq
		AND versions.ROI_Versions_Seq = pages.ROI_Versions_Seq
		-- status (3-Completed, 4 - Denied, 5 - Canceled)
		AND rc.RequestStatus NOT IN('Completed', 'Denied', 'Canceled', 'AuthReceivedDenied')
		ANd pages.deleted = 'N'
		AND pages.imnet = aw.imnet 
		AND aw.userinstanceid = @UserInstanceID)
   begin
      select @Verify = @Verify | @FLAG_ROI_CORE, @Result = 0, @Status = 'Will delete page from requests where status <> Completed, Canceled, Denied, Auth Received Denied.OK?'
   end
   
Else
Begin

--IF OBJECT_ID('#ADI_WORK') is not null
--BEGIN
--DROP TABLE #ADI_WORK
--END
--select distinct w.facility, w.mrn into #ADI_WORK from ADI_WORK w where w.userinstanceid=@UserInstanceId

--IF EXISTS (select w.facility,w.mrn from #ADI_WORK w where w.facility+w.mrn in 
--(select distinct cast(b.value as char(10)) + cast(a.value as char(20)) from ROI_SearchLOV a with(NOLOCK)join 
-- ROI_SearchLOV b with (NOLOCK) on a.child = b.child and a.item = 'patient.mrn' and a.value = w.mrn and b.item = 'patient.facility' and b.value = w.facility  and b.child in
--(select c.child from  ROI_SearchLOV c with (NOLOCK)where c.child= b.child and c.item ='request.status' and c.Value NOT in ('3','4','5','AuthReceivedDenied'))))
--   begin
--      select @Verify = @Verify | @FLAG_ROI_NEW, @Result = 0, @Status = 'One or more ROI requests exists.  Adjusting this page will delete the page from the request.'
--   end

IF EXISTS
	(select 1 from adi_work w where w.userinstanceid = @UserInstanceID and
		EXISTS (select 1 from ROI_SearchLOV a WITH (NOLOCK) where a.Value = w.mrn and a.Item = 'patient.mrn' and
			EXISTS (select 1 from ROI_SearchLOV b WITH (NOLOCK) where b.Child = a.Child and b.Item = 'patient.facility' and
				EXISTS (select 1 from ROI_SearchLOV c WITH (NOLOCK) 
					where c.Child = B.child and c.item ='request.status' and c.Value NOT in ('3','4','5','AuthReceivedDenied')) 
				)
			)
	)		
   begin
      select @Verify = @Verify | @FLAG_ROI_NEW, @Result = 0, @Status = 'Will delete page from requests where status <> Completed, Canceled, Denied, Auth Received Denied.OK?'
   end
End

-- ******************************************************************************************
-- MK 06/28/2006 check if a moved document will not be eligible to outbound
declare @filenameSource char(12), @filenameTarget char(12)
-- 1) Check if a source is a new COLD
   --a) check if a source document doesn't have mixed pages
   SELECT DISTINCT count(c.filename), c.filename FROM Cabinet c (nolock)
   INNER JOIN adi_work a (nolock) ON c.duid = a. duid
   WHERE a.deleted = 'Y' AND a.userinstanceid = @UserInstanceID
   AND c.deleted = 'N' group by c.filename
   if @@rowcount = 1 -- source document pages have same filename, now lets check if it's new COLD
   begin
	   SELECT DISTINCT @filenameSource = c.filename FROM Cabinet c (nolock)
	   INNER JOIN Documents d (nolock) ON c.Duid = d.Duid
	   INNER JOIN Outbound_Documents od (nolock) ON d.Doc_Id = od.Doc_Id
	   WHERE c.duid = (SELECT DISTINCT duid FROM adi_work WHERE deleted = 'Y' AND userinstanceid = @UserInstanceID)
	   AND c.deleted = 'N' AND c.filename = 'CLD'
   end
   else -- we have mixed doc, now lets check for target and if it's a new COLD and not mixed display a message
   begin
        -- check if a target does not have mixed pages
	    SELECT DISTINCT count(c.filename), c.filename FROM Cabinet c (nolock)
	    INNER JOIN adi_work a (nolock) ON c.duid = a. duid
	    WHERE a.deleted = 'N' AND a.userinstanceid = @UserInstanceID
	    AND c.deleted = 'N' group by c.filename
	    if @@rowcount = 1 -- target document pages have same filename, now lets check if it's new COLD    
			begin
   			SELECT DISTINCT @filenameTarget = p.filename 
			FROM dbo.documents d (nolock)
			INNER JOIN dbo.versions v (nolock) on d.doc_id = v.doc_id and v.versionnumber = 0
			INNER JOIN dbo.pages p (nolock) on v.version_id = p.version_id
			INNER JOIN adi_work aw (nolock) on d.facility = aw.facility and d.encounter = aw.encounter and d.mrn = aw.mrn and d.doc_name = aw.document AND d.subtitle = aw.subtitle 
			INNER JOIN outbound_documents od (nolock) on d.doc_id = od.doc_id
			WHERE aw.deleted = 'N' AND aw.userinstanceid = @UserInstanceID AND p.deleted = 'N'

			if (@filenameTarget= 'CLD')
			begin 
				-- 3) Check if we moved the whole Document
			   if
			   (SELECT count (1) FROM Cabinet c (nolock) WHERE c.deleted = 'N' AND c.duid = (SELECT DISTINCT duid FROM adi_work (nolock) WHERE deleted = 'Y' AND userinstanceid = @UserInstanceID))                
			   != (SELECT count (1) FROM adi_work (nolock) WHERE deleted ='Y' and userinstanceid = @UserInstanceId)
			   begin 
				   select @Verify = @Verify | @FLAG_NO_OUTBOUND, @Result = 0, @Status = 'The document will not be eligible to outbound.'
			   end
			end
        end
   end	
if @filenameSource = 'CLD'
begin
-- 2) Check if a target duid is eligible to outbound
	  -- a)check if a target document doesn't have mixed pages
	 SELECT DISTINCT count(c.filename), c.filename FROM Cabinet c (nolock)
	 INNER JOIN adi_work a (nolock) ON c.duid = a. duid
	 WHERE a.deleted = 'N' AND a.userinstanceid = @UserInstanceID
	 AND c.deleted = 'N' group by c.filename
	 if @@rowcount > 1 -- a target document have mixed pages (PAG and CLD) so it's not eligible to outbound and we need to give a message
	 begin
		 -- 3) Check if we moved the whole Document
		 -- JC
		 if
		 (SELECT count (1) FROM Cabinet c (nolock) WHERE c.deleted = 'N' AND c.duid = (SELECT DISTINCT duid FROM adi_work (nolock) WHERE deleted = 'Y' AND userinstanceid = @UserInstanceID))                
		 != (SELECT count (1) FROM adi_work WHERE deleted ='Y' and userinstanceid = @UserInstanceId)
		 begin 
			select @Verify = @Verify | @FLAG_NO_OUTBOUND, @Result = 0, @Status = 'The document will not be eligible to outbound.'
		 end	   
	 end        
	 else
	 begin
	   -- check if target is a new document type and if yes don't provide a message
	   if (select count(d.DUID) FROM documents d (nolock) INNER JOIN adi_work aw (nolock) ON d.duid = aw.duid WHERE aw.deleted = 'N' and aw.userinstanceid = @UserInstanceID) > 0
	   begin
		   -- only one filename exist for a target document and it's merge, so we need to check if it's not HL7 or new COLD
		   SELECT DISTINCT @filenameTarget = p.filename 
		   FROM dbo.documents d (nolock)
		   INNER JOIN dbo.versions v (nolock) on d.doc_id = v.doc_id and v.versionnumber = 0
		   INNER JOIN dbo.pages p (nolock) on v.version_id = p.version_id
		   INNER JOIN adi_work aw (nolock) on d.facility = aw.facility and d.encounter = aw.encounter and d.mrn = aw.mrn and d.doc_name = aw.document AND d.subtitle = aw.subtitle 
		   INNER JOIN outbound_documents od (nolock) on d.doc_id = od.doc_id
		   WHERE aw.deleted = 'N' AND aw.userinstanceid = @UserInstanceID AND p.deleted = 'N'

		   if @filenameSource = 'CLD' AND (@filenameTarget = '' OR @filenameTarget IS NULL) AND
			  (SELECT count(1) FROM adi_work WHERE deleted = 'N' and userinstanceid = @UserInstanceId)  > 0 -- make sure we did not delete the whole document
		   begin
			   -- 3) Check if we moved the whole Document
			   -- JC its ok to outbound
			   if
			   (SELECT count (1) FROM Cabinet c (nolock) WHERE c.deleted = 'N' AND c.duid = (SELECT DISTINCT duid FROM adi_work (nolock) WHERE deleted = 'Y' AND userinstanceid = @UserInstanceID))                
			   != (SELECT count (1) FROM adi_work (nolock) WHERE deleted ='Y' and userinstanceid = @UserInstanceId)
			   begin 
				   select @Verify = @Verify | @FLAG_NO_OUTBOUND, @Result = 0, @Status = 'The document will not be eligible to outbound.'
			   end
		   end
	   end 
	 end
end 
else --check if source is PAG and Target is a New COLD and if so display a message
    begin
		SELECT DISTINCT @filenameSource = p.filename 
		FROM dbo.documents d (nolock)
		INNER JOIN dbo.versions v (nolock) on d.doc_id = v.doc_id and v.versionnumber = 0
		INNER JOIN dbo.pages p (nolock) on v.version_id = p.version_id
		INNER JOIN adi_work aw (nolock) on d.facility = aw.facility and d.encounter = aw.encounter and d.mrn = aw.mrn and d.doc_name = aw.document AND d.subtitle = aw.subtitle 
		INNER JOIN outbound_documents od (nolock) on d.doc_id = od.doc_id
	    WHERE aw.deleted = 'Y' AND aw.userinstanceid = @UserInstanceID AND p.deleted = 'N'
        
		declare @filenameSourceOldCOLD char(12)-- if source is an Old COLD and Target is a New COLD do not display a message
		SELECT DISTINCT @filenameSourceOldCOLD = c.filename FROM Cabinet c
		INNER JOIN adi_work a ON c.duid = a. duid
		WHERE a.deleted = 'Y' AND a.userinstanceid = @UserInstanceID
		AND c.deleted = 'N'
  
		-- no message if move Old COLD to New COLD 
		if (@filenameSource = '' OR @filenameSource IS NULL) AND @filenameSourceOldCOLD <> 'CLD' -- PAG 
		begin -- search for target
            declare @DUID char(64) -- need to find if a target has mixed pages
			SELECT DISTINCT @filenameTarget = p.filename, @DUID = d.duid 
			FROM dbo.documents d (nolock)
			INNER JOIN dbo.versions v (nolock) on d.doc_id = v.doc_id and v.versionnumber = 0
			INNER JOIN dbo.pages p (nolock) on v.version_id = p.version_id
			INNER JOIN adi_work aw (nolock) on d.facility = aw.facility and d.encounter = aw.encounter and d.mrn = aw.mrn and d.doc_name = aw.document AND d.subtitle = aw.subtitle 
			INNER JOIN outbound_documents od (nolock) on d.doc_id = od.doc_id
			WHERE aw.deleted = 'N' AND aw.userinstanceid = @UserInstanceID AND p.deleted = 'N'
		
            if @filenameTarget = 'CLD' and not exists (SELECT  1 FROM  dbo.Documents d (nolock)
													  INNER JOIN dbo.Versions v (nolock) ON d.DOC_ID = v.DOC_ID AND v.VersionNumber = 0 
													  INNER JOIN dbo.Pages p (nolock) ON v.Version_ID = p.version_id
													  INNER JOIN dbo.outbound_documents od (nolock) on d.doc_id=od.doc_id
													  where d.duid = @DUID and v.DocumentStatus is not null and p.filename != 'CLD'
                                                      and p.deleted = 'N')
		    begin 
			   select @Verify = @Verify | @FLAG_NO_OUTBOUND, @Result = 0, @Status = 'The document will not be eligible to outbound.'
		    end				
		end        
    end   
    --********************************************************************************************

-- No dependencies, Adi OK
if @Verify = 0
begin
	select @Verify = 0, @Result = 0, @Status = 'Ok'
end
return

-- error trapping
pending_err:
select @Verify = -1, @Result = -1, @Status = 'Error Checking Pending Signatures!'
return
archive_err:
select @Verify = -1, @Result = -2, @Status = 'Error Checking Archive Table!'
return
followup_err:
select @Verify = -1, @Result = -3, @Status = 'Error Checking Deficiencies!'
return
assignments_err:
select @Verify = -1, @Result = -4, @Status = 'Error Checking Assignments!'
return
RequestPage_err:
select @Verify = -1, @Result = -5, @Status = 'Error Checking Requests!'
return
GO

GRANT EXECUTE ON [dbo].[EPRS_VerifyAdiOk] TO [IMNET] AS [dbo]
GO
