USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wfe_DeletePacketControlOPRPT]') AND type in (N'P'))
DROP PROCEDURE [dbo].[wfe_DeletePacketControlOPRPT]
GO

/************************************************************************************************************************
* Stored Procedure	: dbo.wfe_DeletePacketControlOPRPT
* Creation Date		: 04/05/2005
* Copyright		: COPYRIGHT MCKESSON 2005
*			: ALL RIGHTS RESERVED
*
* 			: The copyright to the computer program(s) herein
* 			: is the property of McKesson. The program(s)
* 			: may be used and/or copied only with the written
* 			: permission of McKesson or in accordance with
* 			: the terms and conditions stipulated in the     
* 			: agreement/contract under which the program(s)
* 			: have been supplied.	
*				
* Written by		: Don Gatten
*									
* Purpose		: Workflow
*
* Description		: This stored procedure checks the Coding OP Report Packet Controls for the
*			: current encounter being evaluated.  If the current encounter is present
*			: the stored procedure deletes the assignment from the 
*			: Packet Control Queue and executes the update assignment
*			: count stored procedure.
*
*
* Database		: CABINET
*
* HISTORY
*   	NTT DATA		1/25/2013       Added insert into Assignement and Encounter audit event table.
*		MD Meersma		09/30/2013		CR382455 Change subquery in AuditEvent insert statement to use the ENCOUNTERS table as the DELETE statement has already occurred
*										Use @Queue_Description instead of @Queue_Id for subquery to insert into AuditEvent tables
*******************************************************************************************************************/

create PROCEDURE [dbo].[wfe_DeletePacketControlOPRPT]  (

   	@Assignment_Id numeric,
	@Account_Id char(20),
	@Facility_Id char(10),
        @Queue_Id smallint,
        @Queue_Description char(50))
   AS

-- Custom Code Begins
	
	Begin
		
		   delete from cabinet..assignments 
		   where userid = 'HIM: Coding Wait for OP Report'
		   and encounter = @account_ID
		   and facility = @facility_ID

		   delete from cabinet..assignments 
		   where userid = 'HIM: Coding Wait for OP Report Scanned'
		   and encounter = @account_ID
		   and facility = @facility_ID

		   exec cabinet..wsp_updateassignmentcount
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
                  (@Assignment_Id, @account_ID, (select mrn from his.dbo.ENCOUNTERS where encounter = @account_ID and facility = @facility_ID), @facility_ID, (select QueueTypeName from QueueTypeLov 
                  where QueueTypeLovID = (select QueueTypeLovID from wq_definition
                  where queue = @Queue_Description)), @Queue_Id, 'Delete', 
                  GETDATE(), '1', 'sp_wfe_DeletePacketControlOPRPT', (select PURPOSE from ASSIGNMENTS where ASSIGNMENT_ID=@Assignment_Id));
                  
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
                  (@Assignment_Id, @account_ID, (select mrn from his.dbo.ENCOUNTERS where encounter = @account_ID and facility = @facility_ID), @facility_ID, (select QueueTypeName from QueueTypeLov 
                  where QueueTypeLovID = (select QueueTypeLovID from wq_definition
                  where queue = @Queue_Description)), @Queue_Id, 'Delete', 
                  GETDATE(), '1', 'sp_wfe_DeletePacketControlOPRPT', (select PURPOSE from ASSIGNMENTS where ASSIGNMENT_ID=@Assignment_Id));

		   --Changes by for Analytics(Jan22)- Ends
		   select 1,'Stored Procedure Returned True'
		   return
	End
GO

GRANT EXECUTE ON [dbo].[wfe_DeletePacketControlOPRPT] TO [IMNET] AS [dbo]
GO
