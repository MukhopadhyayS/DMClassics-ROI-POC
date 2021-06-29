USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[WFE_HIS_Deletepatient]') AND type in (N'P'))
DROP PROCEDURE [dbo].[WFE_HIS_Deletepatient]
GO

/****************************************************************
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
* Procedure Name: CABINET..WFE_HIS_DeletePatient
* Description: This procedure deletes all patients record for a specific patient. Only data in 
*		cabinet table will be flagged as deleted, all other tables data will be deleted.
*
*		Name			Type		Description
*		---------		-------		----------------
* Params In:	
*		@Assignment_Id		numeric,
*		@Encounter		char(20),
*		@Facility		char(10),
*		@Queue_Id		smallint,
*		@Queue_Description	Char(50)
* Params Out: NONE
* Result Set: 	
* Returns:	0- ERROR
*		1- SUCCESS 
* Database: CABINET and HIS
* Tables Used:	archive_work, archiving, assignments, cainet, cab_hold, 
*		follow_up, retrieve, signatures
* 		Patients, encounters, diagnoses, locator, physicians, procedures
*		active, census, procedures
*
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
* 	Mariam Alahyar		8/15/2001	Created
*	Mariam Alahyar		12/10/2001	Added work flow related parameters
*						track 15887
*	Mariam Alahyar		01/31/02	Mark Global document for deletion track-16113
*     Judy Jiang		04/23/06 	Updated:HPF11.0: copy  to VSS.
*	MD Meersma		04/24/2007	return statements added after rollback
*	 NTT DATA:    		1/25/2013: 	Added new query for Assignment and Encounter Audit
*	MD Meersma		09/25/2013	DE1460 Deleting a deficiency leaves orphan rows in PendingSignJob and PendingSignDeficiency
*****************************************************************/
CREATE PROCEDURE [dbo].[WFE_HIS_Deletepatient] (
@Assignment_Id		numeric,
@Encounter		char(20),
@Facility		char(10),		
@Queue_Id		smallint,
@Queue_Description	char(50)
)
AS
  set nocount on

  select @Assignment_Id = Nullif (@Assignment_Id,0)
  

  if @Assignment_Id is null
    Begin 
	select 0,'Stored procedure returned false'
    End
    --Changes by for Analytics(Jan22)- Starts
  declare @DefType smallint
  declare @DocType varchar(20)
  declare @lnk char(1)
  declare @Queue char(50)
  declare @Action varchar(260)
  declare @AgingDate smalldatetime
  declare @Priority char(1)
  declare @Name char(40)
  declare @Id int
  declare @LastCreated smalldatetime
  declare @Duid char(64)
  declare @Created smalldatetime
  --Changes by for Analytics(Jan22)- Ends
/* Creating temporary table to store the resultset with all patient's encounters  */
CREATE TABLE #AllEncounters
   (
	mrn		varchar(20),
	facility	varchar(10),
	encounter  	varchar(20)
   )

/* Creating temporary table to store the resultset with all patient's imnetIds  */
CREATE TABLE #AllImnet
   (
	facility	varchar(10),
	ImnetID  	char(30)
   )


declare @mrn varchar(20)
--select @mrn = mrn from assignments 
--	where assignment_Id = @Assignment_Id
select @mrn = mrn from his.dbo.encounters where encounter = @Encounter and facility = @facility
  if @mrn is null
    Begin 
	select 0,'Stored procedure returned false'
	return
    End


INSERT INTO #AllEncounters
SELECT DISTINCT
	mrn, facility, encounter
FROM	HIS.dbo.encounters 
	   where mrn = @mrn and facility = @facility

if exists (select * from cab_hold)
	INSERT INTO #AllImnet
		SELECT facility, Imnet
		FROM	cabinet 
	   	where mrn = @mrn and facility = @facility

Begin Transaction DeleteRequest
/* delete rows from archive_work table		*/
  delete from archive_work
	from archive_work inner join #AllEncounters
		on  archive_work.encounter = #AllEncounters.encounter and
		archive_work.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false' 
		   return
	 	End
/* delete from archiving table			*/
  delete from archiving
	from archiving inner join #AllEncounters
		on  archiving.folder = #AllEncounters.encounter and
		archiving.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false' 
		   return
	 	End

/* delete from assignments table		*/
/* track-16113 modified to delete using mrn and facility rather than joining to #allencounters table. */
  delete from assignments 
	where mrn = @mrn and facility = @facility
	--Changes by for Analytics(Jan22)- Starts
	INSERT INTO [AssignmentAuditEvent]
                 ([Assignment_ID]
                 ,[Encounter]
                 ,[MRN]
                 ,[Facility]
                 ,[QueueTypeName]
                 ,[Queue]
                 ,[WorkflowEventName]
                 ,[AssignmentEventDate]
                 ,[UserInstanceID]
                 ,[ComponentName]
                 ,[Reason])
                 
            VALUES
                  (@Assignment_Id, @Encounter, @mrn, @Facility, (select QueueTypeName from QueueTypeLov 
                  where QueueTypeLovID = (select QueueTypeLovID from wq_definition
                  where queue = @Queue_Description)), @Queue_Id, 'Delete', 
                  GETDATE(), '1', 'sp_WFE_HIS_Deletepatient', (select PURPOSE from ASSIGNMENTS where ASSIGNMENT_ID=@Assignment_Id));
                  
	  INSERT INTO [EncounterAuditEvent]
                 ([Assignment_ID]
                 ,[Encounter]
                 ,[MRN]
                 ,[Facility]
                 ,[QueueTypeName]
                 ,[Queue]
                 ,[EncounterEventName]
                 ,[EncounterEventDate]
                 ,[UserInstanceID]
                 ,[ComponentName]
                 ,[Reason])
            VALUES
                  (@Assignment_Id, @Encounter, @mrn, @Facility, (select QueueTypeName from QueueTypeLov 
                  where QueueTypeLovID = (select QueueTypeLovID from wq_definition
                  where queue = @Queue_Description)), @Queue_Id, 'Delete', 
                  GETDATE(), '1', 'sp_WFE_HIS_Deletepatient', (select PURPOSE from ASSIGNMENTS where ASSIGNMENT_ID=@Assignment_Id));
	--Changes by for Analytics(Jan22)- Ends
				  
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false'	
		   return   
	 	End

/* delete from cabinet table			*/
/* track-16113 modified to delete using mrn and facility rather than joining to #allencounters table.  */
  update cabinet set deleted = 'Y'  
	where mrn = @mrn and facility = @facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'
		   return
	 	End

/* delete from cab_hold table			*/
  delete from cab_hold
	from cab_hold inner join #AllImnet
		on  cab_hold.imnet_Id = #AllImnet.imnetId and
		cab_hold.facility = #AllImnet.facility
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false'
		   return
	 	End

	-- DE1460 BEGIN
/* delete from PendingSignDeficiency and PendingSignJob */
	SELECT DISTINCT PendingSignJobID INTO #WFE_HIS_Deletepatient FROM PendingSignDeficiency where FollowUPID in 
		(select ID from follow_up inner join #AllEncounters
		on  follow_up.folder = #AllEncounters.encounter and
		follow_up.facility = #AllEncounters.facility)

	DELETE FROM PendingSignDeficiency where FollowUPID in 
		(select ID from follow_up inner join #AllEncounters
		on  follow_up.folder = #AllEncounters.encounter and
		follow_up.facility = #AllEncounters.facility)

	DELETE FROM PendingSignJob where PendingSignJobID in (select PendingSignJobID FROM #WFE_HIS_Deletepatient)
		and not exists (SELECT 1 FROM PendingSignDeficiency where PendingSignDeficiency.PendingSignJobID = PendingSignJob.PendingSignJobID)
		
	-- DE1460 END

/* delete from follow_up table 			*/
	select @DefType = DEF_TYPE,@DocType = doctype_id , @lnk = wf_link, @Queue = queue, @Action = action, @AgingDate = agingdate, @Priority = priority, @Name = pt_name, @Id = id, @LastCreated = created, @Duid = duid from FOLLOW_UP where FOLDER=@Encounter and facility=@Facility
  delete from follow_up
	from follow_up inner join #AllEncounters
		on  follow_up.folder = #AllEncounters.encounter and
		follow_up.facility = #AllEncounters.facility
--	select @DefType = DEF_TYPE,@DocType = doctype_id , @lnk = wf_link, @Queue = queue, @Action = action, @AgingDate = agingdate, @Priority = priority, @Name = pt_name, @Id = id, @LastCreated = created, @Duid = duid from FOLLOW_UP where FOLDER=@Encounter and facility=@Facility
	--Changes by for Analytics(Jan22)- Starts
	insert into FOLLOW_UPAuditEvent ( facility, folder, type, def_type, doctype_id, wf_link, queue, action, agingdate, priority, status_id, pt_name, id, created, arc_stop, duid, WorkflowEventName, FollowUPEventDate,UserInstanceId, ComponentName )
                  values ( @Facility, @Encounter, ltrim(str(@DefType)), @DefType, @DocType, @lnk, @Queue, @Action, @AgingDate, @Priority, 2, @Name, @Id, @LastCreated, 'Y', @Duid , 'Delete', GETDATE(), 1, 'sp_WFE_HIS_Deletepatient')	
    --Changes by for Analytics(Jan22)- Ends					
		
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false' 
		   return  
	 	End

/* delete from Retrieve table 			*/
  delete from Retrieve
	from Retrieve inner join #AllEncounters
		on  Retrieve.folder = #AllEncounters.encounter and
		Retrieve.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'   
		   return
	 	End

/* delete from Signatures table 			*/
  delete from Signatures
	from Signatures inner join #AllEncounters
		on  Signatures.folder = #AllEncounters.encounter and
		Signatures.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false'	   
		   return
	 	End

/* delete from His database Patients table 	*/
/* track-16113 modified to delete using mrn and facility rather than joining to #allencounters table.  */
  delete from HIS..Patients
	where HIS..Patients.mrn = @mrn and HIS..Patients.facility = @facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'   
		   return
	 	End

/* delete from His database Encounters table 	*/
/* track-16113 modified to delete using mrn and facility rather than joining to #allencounters table.  */
  delete from HIS..Encounters
	where HIS..Encounters.mrn = @mrn and HIS..Encounters.facility = @facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'  
		   return
	 	End

/* delete from His database Diagnoses table 	*/
  delete from HIS..Diagnoses
	from HIS..Diagnoses inner join #AllEncounters
		on  HIS..Diagnoses.folder = #AllEncounters.encounter and
		HIS..Diagnoses.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'   
		   return
	 	End

/* delete from His database Locator table 	*/
  delete from HIS..Locator
	from HIS..Locator inner join #AllEncounters
		on  HIS..Locator.folder = #AllEncounters.encounter and
		HIS..Locator.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback
		   select 0,'Stored procedure returned false'   
		   return
	 	End

/* delete from His database Physicians table 	*/
  delete from HIS..Physicians
	from HIS..Physicians inner join #AllEncounters
		on  HIS..Physicians.folder = #AllEncounters.encounter and
		HIS..Physicians.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'
		   return
	 	End

/* delete from His database Active table 	*/
  delete from HIS..Active
	from HIS..Active inner join #AllEncounters
		on  HIS..Active.encounter = #AllEncounters.encounter and
		HIS..Active.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'
		   return
	 	End

/* delete from His database Census table 	*/
  delete from HIS..Census
	where HIS..Census.mrn = @mrn and HIS..Census.facility = @facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'
		   return
	 	End

/* delete from His database Procedures table 	*/
  delete from HIS..Procedures
	from HIS..Procedures inner join #AllEncounters
		on  HIS..Procedures.folder = #AllEncounters.encounter and
		HIS..Procedures.facility = #AllEncounters.facility
	if @@error <> 0
		Begin
		   rollback	
		   select 0,'Stored procedure returned false'
		   return
	 	End

Commit Transaction DeleteRequest
drop table #AllEncounters
drop table #AllImnet
select 1,'Stored procedure returned true'
GO

GRANT EXECUTE ON [dbo].[WFE_HIS_Deletepatient] TO [IMNET] AS [dbo]
GO
