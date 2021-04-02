USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Wsp_UpdateAssignmentCount]') AND type in (N'P'))
DROP PROCEDURE [dbo].[Wsp_UpdateAssignmentCount]
GO

/***************************************************************************************************************
* Stored Procedure	: Wsp_UpdateAssignmentCount
* Creation Date		: 07/18/2008
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
* Written by		: RC	
*									
* Purpose			: Update the count of assignments in every work queue
*				
* Database			:CABINET
*									
* Input Parameters	: None 
*									
* Output Parameters	: None
*									
* Return Status		: None		
*									
* Called By			: various
*									
* Calls				: NA
*			
* Data Modifications: updates the column assignment_count in WQ_DEFINITION
*									
* Updates			: 
*	Name				Date		Purpose
*	---------			-------		------------- 						
*	RC					07/18/2008	Created new stored procedure.						
*	MD Meersma			07/01/2013	LOCK Hints, Add on UPDATE where clause wq_definition.assignment_count != NumberInQueue, PRIMARY KEY
*	MD Meersma			09/30/2013	Null assignment_count update to 0
***************************************************************************************************************************************/
Create PROCEDURE [dbo].[Wsp_UpdateAssignmentCount]
AS
SET Nocount ON
	BEGIN TRANSACTION Tran_Wsp_UpdateAssignmentCount

		Declare @CountAssignmentsInQueue table
			(	
				QueueName varchar(50) PRIMARY KEY,
				NumberInQueue int
			)
		INSERT INTO @CountAssignmentsInQueue 
			SELECT     
				wq_definition.queue
				, count(assignments.userid) AS [NumberInQueue]
			FROM         
				wq_definition WITH (UPDLOCK, ROWLOCK, SERIALIZABLE)
			LEFT OUTER JOIN 
				assignments ON wq_definition.queue = assignments.userid
			GROUP BY 
				wq_definition.queue, assignments.userid

		UPDATE 
			wq_definition 
		SET 
			assignment_count= NumberInQueue
		FROM 
			@CountAssignmentsInQueue
		WHERE 
			QueueName=wq_definition.queue and (wq_definition.assignment_count != NumberInQueue or wq_definition.assignment_count is null)

	COMMIT TRANSACTION Tran_Wsp_UpdateAssignmentCount

RETURN 0
GO

GRANT EXECUTE ON [dbo].[Wsp_UpdateAssignmentCount] TO [IMNET] AS [dbo]
GO
