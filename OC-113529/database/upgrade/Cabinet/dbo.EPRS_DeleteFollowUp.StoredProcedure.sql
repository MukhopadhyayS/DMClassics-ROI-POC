USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EPRS_DeleteFollowUp]') AND type in (N'P'))
DROP PROCEDURE [dbo].[EPRS_DeleteFollowUp]
GO

/* 9/14/01 M.A. - modified to delete cabient_Xref row if applicable  */
/* added @duid parameter to help identifying the cabinet_Xref row */
/* 10/19/2010 - MD Meersma - Performance Enhancements */
/* 01/26/2013 - NTT Data - Followup audit event added */
/*	MD Meersma	09/25/2013	DE1460 Deleting a deficiency leaves orphan rows in PendingSignJob and PendingSignDeficiency */

CREATE PROCEDURE [dbo].[EPRS_DeleteFollowUp]
   @UserInstanceId integer
AS

declare @Type integer
declare @DefStatus integer
declare @Facility char(10)
declare @WF_Link integer
declare @ARC_Stop char(10)
declare @Encounter char(20)
declare @DefType integer
declare @DocType integer
declare @Link char(50)
declare @AgingDate datetime
declare @Priority char(1)
declare @Action varchar(260)
declare @Name char(40)
declare @Created datetime
declare @Queue char(50)
declare @LastCreated datetime
declare @Duid char(64)
declare @Id integer
declare @CheckoutBy varchar(50)
declare @CheckoutTime datetime
declare @Reassigned integer

select @Created = GetDate()

begin transaction del_followup
	
select @Type = def_type, @DefStatus = status_id, @Duid = Duid,
@Facility = facility, @Encounter = folder, @DefType = def_type, @DocType = doctype_id,
@AgingDate = agingdate,@CheckoutBy=CHECKOUTBY,@CheckoutTime=CHECKOUTTIME,@Reassigned=REASSIGNED, @Priority = priority, @Name = pt_name, @LastCreated = created,
@Action = ACTION, @Queue = QUEUE, @WF_Link = wf_link from follow_up 
WHERE id IN
	(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
	WHERE adi_work.userinstanceid = @UserInstanceID)

/* Added to Insert WorkflowEventName, FollowUPEventDate,UserInstanceId and  ComponentName into the FOLLOW_UPAuditEvent table 
   along with the follow_up table parameters */
 insert into FOLLOW_UPAuditEvent ( facility, folder, type, def_type, doctype_id, wf_link, queue, action, agingdate, priority, status_id, pt_name, id,REASSIGNED,CHECKOUTBY,CHECKOUTTIME, created, arc_stop, duid,WorkflowEventName, FollowUPEventDate,UserInstanceId, ComponentName )
 select facility,folder,type,def_type,doctype_id,wf_link, queue, action, agingdate, priority, status_id, pt_name, id,REASSIGNED,CHECKOUTBY,CHECKOUTTIME, created, arc_stop, duid,'Delete', GetDate(), 1, 'sp_EPRS_ApplyAdiChanges'
FROM FOLLOW_UP WHERE id IN
	(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
	WHERE adi_work.userinstanceid = @UserInstanceID)	


	-- DE1460 BEGIN
-- Remove any PendingSignDeficiency and PendingSignJob based on the pages being moved

	SELECT DISTINCT PendingSignJobID INTO #EPRS_DeleteFollowUp FROM PendingSignDeficiency where FollowUPID in
		(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
		WHERE adi_work.userinstanceid = @UserInstanceID)

	DELETE FROM PendingSignDeficiency where FollowUPID  in
		(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
		WHERE adi_work.userinstanceid = @UserInstanceID)

	DELETE FROM PendingSignJob where PendingSignJobID in (select PendingSignJobID FROM #EPRS_DeleteFollowUp)
		and not exists (SELECT 1 FROM PendingSignDeficiency where PendingSignDeficiency.PendingSignJobID = PendingSignJob.PendingSignJobID)
		
	-- DE1460 END

--Remove any follow up items based on the pages being moved
DELETE FROM follow_up 
WHERE id IN
	(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
	WHERE adi_work.userinstanceid = @UserInstanceID)
if @@error <> 0 goto delete_err


--Remove any signatures 
DELETE FROM signatures 
WHERE id IN
	(SELECT follow_up.id FROM adi_work 
		JOIN cabinet ON cabinet.imnet = adi_work.imnet
		JOIN FOLLOW_UP ON FOLLOW_UP.DUID = cabinet.DUID
	WHERE adi_work.userinstanceid = @UserInstanceID)
if @@error <> 0 goto delete_err

-- clean out any entries because of duid change
DELETE FROM cabinet_Xref --remove records from this table
FROM cabinet_Xref --the source table for the join
LEFT OUTER JOIN follow_up --the join table
ON cabinet_Xref.duid = follow_up.duid --all records from xref and only the matching from follow_up
WHERE follow_up.duid IS NULL --show only records in xref that do not exists in follow_up
	AND cabinet_Xref.duid IN --list of duids based on the imnets from adi
		(SELECT cabinet.duid FROM cabinet JOIN adi_work on cabinet.imnet = adi_work.imnet
		WHERE adi_work.userinstanceid = @UserInstanceID)
if @@error <> 0 goto delete_err

/*
declare @FollowupID integer,
	@Duid	    char(64)

begin transaction del_followup

declare follow_delete_cursor cursor for 
select distinct f.id, c.duid from follow_up f, 
                 adi_work a, 
                 cabinet c 
where c.imnet = a.imnet and 
      c.duid = f.duid and 
      a.userinstanceid = @UserInstanceID
      
open follow_delete_cursor
fetch next from follow_delete_cursor into @FollowupId, @Duid
        while (@@Fetch_Status <> -1)
        begin
           if (@@Fetch_Status <> -2)
--              begin transaction del_followup
                 begin
		     delete from follow_up where id = @FollowupId
                 if @@error <> 0 goto delete_err
                 delete from signatures where id = @FollowupId
	           if @@error <> 0 goto delete_err 
		 delete from cabinet_Xref 
  			from cabinet_Xref left outer join follow_up 
  			on cabinet_Xref.duid = follow_up.duid
  			where follow_up.duid is null and
			cabinet_Xref.duid = @Duid
		if @@error <> 0 goto delete_err
                 end
--              commit transaction del_followup
           fetch next from follow_delete_cursor into @FollowupId, @Duid
        end
close follow_delete_cursor
deallocate follow_delete_cursor*/
commit transaction del_followup
return 0

delete_err:
rollback transaction del_followup
return -1   /*error deleting follow_up and signatures */
GO

GRANT EXECUTE ON [dbo].[EPRS_DeleteFollowUp] TO [IMNET] AS [dbo]
GO