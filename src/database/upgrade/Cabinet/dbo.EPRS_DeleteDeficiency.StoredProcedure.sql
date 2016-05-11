USE [cabinet]
GO


IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EPRS_DeleteDeficiency]') AND type in (N'P'))
DROP PROCEDURE [dbo].[EPRS_DeleteDeficiency]
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

/*************************************************************************************************************************************
* Stored Procedure	: EPRS_Sign_InsertFollow_upAuditEvent
* Creation Date		: 12/18/2012
*					
* Written by		: NTT Data
*									
* Purpose		: Inserts the audit entry for deficiency in FOLLOW_UPAuditEvent table
*		  			
* Database		: Cabinet
*									
* Called By		: Signature Server
*									
* Calls			: cabinet.dbo.FOLLOW_UPAuditEvent 
*									
* Data Modifications	: <none>			
*									
* Updates		: Name			Date		Purpose
*			---------		-------		------------- 							
*	NTT DATA				12/11/2012	changed the column name from MPFComponentName to ComponentName
*	MD Meersma				09/25/2013	DE1460 Deleting a deficiency leaves orphan rows in PendingSignJob and PendingSignDeficiency
*************************************************************************************************************************************/
CREATE procedure [dbo].[EPRS_DeleteDeficiency]
@Id integer,
@Result integer output,
@Status char(50) output,
@Duid char(64) output
as

declare @Type integer
declare @Who char(50)
declare @DefStatus integer
declare @lnk char(1)
declare @Facility char(10)
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
Declare @LastCreated datetime

select @Created = GetDate()
if @Link = '' Select @lnk = '0' else select @lnk = '1'

begin transaction
   select @Type = def_type, @Who = queue, @DefStatus = status_id, @Duid = Duid,
			@Facility = facility, @Encounter = folder, @DefType = def_type, @DocType = doctype_id,
			@AgingDate = agingdate, @Priority = priority, @Name = pt_name, @LastCreated = created,
			@Action = ACTION, @Queue = QUEUE from follow_up where id = @id

   if @Type = 1
      begin
         delete from signatures where id = @id
         if @@error <> 0 goto delete_err
         if @Who <> '' and @Who is not null and ( @DefStatus > 1 and @DefStatus < 7 )
            begin
               update sign_control set sign_count = sign_count - 1 where sign = @Who
               delete from sign_control where sign = @Who and sign_count < 1
            end
      end

   if @Type = 2
     begin
        delete from signatures where id = @id
        if @@error <> 0 goto delete_err
     end

	-- DE1460 BEGIN

	SELECT DISTINCT PendingSignJobID INTO #EPRS_DeleteDeficiency FROM PendingSignDeficiency where FollowUPID = @id

	DELETE FROM PendingSignDeficiency where FollowUPID = @id
	DELETE FROM PendingSignJob where PendingSignJobID in (select PendingSignJobID FROM #EPRS_DeleteDeficiency)
		and not exists (SELECT 1 FROM PendingSignDeficiency where PendingSignDeficiency.PendingSignJobID = PendingSignJob.PendingSignJobID)
		
	-- DE1460 END

   delete from follow_up where id = @id 


   if @@error <> 0 goto delete_err
    /* Added to Insert WorkflowEventName, FollowUPEventDate,UserInstanceId and  ComponentName into the FOLLOW_UPAuditEvent table 
   along with the follow_up table parameters */
   insert into FOLLOW_UPAuditEvent ( facility, folder, type, def_type, doctype_id, wf_link, queue, action, agingdate, priority, status_id, pt_name, id, created, arc_stop, duid, WorkflowEventName, FollowUPEventDate,UserInstanceId, ComponentName )
                  values ( @Facility, @Encounter, ltrim(str(@DefType)), @DefType, @DocType, @lnk, @Queue, @Action, @AgingDate, @Priority, 2, @Name, @Id, @LastCreated, 'Y', @Duid , 'Delete', @Created, 1, 'WorkStation')
   if @@error <> 0 goto insert_err

/* 8/23/01 M.A. track# 14526 - delete pending signature from eiwt_pending_signatures */
/*  modified to check for remote server EIW,
    if not found, we assume a local eiwdata database and call the proc from there
    else call remote stored proc */

if not exists(select * from master..sysservers where srvname = 'EIW')
begin
    exec eiwdata..AS_DeleteErroredSignatureJob @id
end
else
begin
    exec EIW.eiwdata.dbo.AS_DeleteErroredSignatureJob @id
end

  if @@error <> 0 goto delete_err

commit transaction
select @Result = 0
select @Status = 'OK'

   return

delete_err:

rollback transaction
select @Result = -3
select @Status = 'Delete error'
return
insert_err:
   rollback transaction
   select @Id = -4
   select @Status = 'Insert Failed'
   return

GO
GRANT EXECUTE ON [dbo].[EPRS_DeleteDeficiency] TO [IMNET] AS [dbo]
GO
