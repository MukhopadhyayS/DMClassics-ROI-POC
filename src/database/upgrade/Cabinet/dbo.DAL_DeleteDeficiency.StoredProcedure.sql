USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DAL_DeleteDeficiency]') AND type in (N'P'))
DROP PROCEDURE [dbo].[DAL_DeleteDeficiency]
GO

/*
/*************************************************************************************************\
*  	Copyright ¬ 2003 McKesson Corporation and/or one of its subsidiaries. 
*		All rights reserved. 
*
* 	The copyright to the computer program(s) herein
* 	is the property of MCKESSON. The program(s)
* 	may be used and/or copied only with the written
* 	permission of MCKESSON or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
**************************************************************************************************
* Script Name: DAL_DeleteDeficiency.SQL
* Script Type: SQL (For purpose of CREATE, DROP, ALTER, GRANT to stored procedure.)
* Function		: ..
*
* Database		: <none>
*			
* Input Parameters	: Name			Type		Description
* 			---------		-------		----------------
*			
*			
*		
*
* Output Parameters	: <none>
*									
* Return Status		: <none>		
*									
* Usage			: <none>
*									
* Local Variables	: Name			Type		Description
* 			---------		-------		----------------
*			:
*									
* Called By 		: <none>
* (views,rpts,		: 
*  procedrues,apps)	:
*								
* Calls-Dependence	: <none>
* (views,procedrues)	:			
*			: 
*								
* Data Modifications	: <none>			
*				
*	
*
* 
* Revision History:
*	Name			Date		Changes/Version&SPnn&Build(BuildRequestCQ#)/ClearQuest#
*	---------		-------		------------------------------------------------
*	Unknown			Unknown		Created.
*	Judy Jiang		09/14/06	Updated:HPF11.0/ST#172,178/: Add condition "IF @signatureName is not null and @signatureName <>''" , which reduces deleting deficiency from 30 seconds to 1 seconds.
*   NTT DATA		01/25/2013  insert into the FOLLOW_UPAuditEvent table with delete event
*	MD Meersma		09/25/2013	DE1460 Deleting a deficiency leaves orphan rows in PendingSignJob and PendingSignDeficiency
*************************************************************************************************/
*/

CREATE  PROCEDURE [dbo].[DAL_DeleteDeficiency] 
(@duid varchar(64))
AS
BEGIN
	DECLARE @defId		integer
	DECLARE @count		integer
	DECLARE @action		varchar(260)
	DECLARE @created	smalldatetime
	DECLARE @facility	char(10)
	DECLARE @encounter  char(20)
	DECLARE @defAction	varchar(60)
	DECLARE @workFlowLink char(1)
	DECLARE @queue varchar(50)
	DECLARE @priority char(1) 
	DECLARE @docTypeId integer
	DECLARE @agingDate varchar(25)
	DECLARE @folder		varchar(20)
	DECLARE @patientName	varchar(40)
	DECLARE @signatureName	varchar(20)
	DECLARE @followUpCursor CURSOR 

	SET @followUpCursor = CURSOR FORWARD_ONLY FOR  
		    SELECT FU.ID, FU.ACTION, FU.CREATED, FU.FACILITY, FU.FOLDER, FU.PT_NAME AS isLockedBy FROM FOLLOW_UP_view FU LEFT OUTER JOIN USERS U ON (FU.CHECKOUTBY = U.USERINSTANCEID)
		    WHERE DUID = @duid AND (DEF_TYPE=1 OR DEF_TYPE=2) AND (FU.CHECKOUTBY IS NULL OR U.USERINSTANCEID IS NULL)

	OPEN @followUpCursor 
        FETCH NEXT FROM @followUpCursor INTO @defId, @action, @created, @facility, @folder, @patientName 

	WHILE @@FETCH_STATUS = 0
        BEGIN
        	SELECT @signatureName = NAME FROM SIGNATURES_view WITH (NOLOCK) WHERE ID=@defId 
		DELETE FROM SIGNATURES_view WHERE ID=@defId 
		

		IF @signatureName is not null and @signatureName <>''	-- added on 9/14/06 by Judy Jiang to improve performance
		Begin

		SELECT @count = COUNT(DISTINCT ID) FROM SIGNATURES_view WITH (NOLOCK) WHERE NAME=@signatureName AND ID IN  
		    (SELECT ID FROM FOLLOW_UP_view WHERE DEF_TYPE = 1) 
		IF @count > 0  
		BEGIN 
		        UPDATE SIGN_CONTROL_view SET SIGN_COUNT=@count WHERE SIGN=@signatureName 
		END 
		ELSE 
		BEGIN 
		        DELETE FROM SIGN_CONTROL_view WHERE SIGN=@signatureName 
		END 
		End -- -- added on 9/14/06 
		
		-- select values from FOLLOW_UP_view
		SELECT @facility = FACILITY, @encounter = FOLDER, @defAction = ACTION, @duid = DUID, @workFlowLink = WF_LINK, @queue = QUEUE, @priority = PRIORITY,@docTypeId = DOCTYPE_ID, @agingDate = AGINGDATE, @defId = ID, @patientName =  PT_NAME  
		FROM FOLLOW_UP_view WHERE ID=@defId		

		-- DE1460 BEGIN

		SELECT DISTINCT PendingSignJobID INTO #DAL_DeleteDeficiency FROM PendingSignDeficiency where FollowUPID = @defid

		DELETE FROM PendingSignDeficiency where FollowUPID = @defid
		DELETE FROM PendingSignJob where PendingSignJobID in (select PendingSignJobID FROM #DAL_DeleteDeficiency)
			and not exists (SELECT 1 FROM PendingSignDeficiency where PendingSignDeficiency.PendingSignJobID = PendingSignJob.PendingSignJobID)
		
		-- DE1460 END

		DELETE FROM FOLLOW_UP_view WHERE ID=@defId 
		INSERT INTO WORK_FLOW_view (ENCOUNTER, FACILITY, DESCRIPTION, PURPOSE, ASSIGNED, COMPLETED, RESULT, USERID, MRN, NAME, DEF_STATUS, DOC_SUBTITLE) 
		        VALUES(@folder, @facility, SUBSTRING(@action, 1, 50) , SUBSTRING(@action, 1, 60), @created, GETDATE(), 'document deleted','USER', NULL, @patientName, NULL, NULL) 
				
		-- insert into the FOLLOW_UPAuditEvent table
		INSERT INTO FOLLOW_UPAuditEvent (TYPE, FACILITY, FOLDER, ACTION, DUID, CREATED, WF_LINK, QUEUE, ARC_STOP, PRIORITY, DOCTYPE_ID, STATUS_ID, AGINGDATE, DEF_TYPE, ID, PT_NAME, WorkflowEventName, FollowUPEventDate, UserInstanceId, ComponentName) 
		VALUES ('1', @facility, @encounter, @defAction, @duid, GETDATE(), @workFlowLink, @queue, 'Y', @priority, @docTypeId, 2, @agingDate, 1, @defId, @patientName, 'Delete', getDate(), 1, 'IU')
			
		
		FETCH NEXT FROM @followUpCursor INTO @defId, @action, @created, @facility, @folder, @patientName 
	END 
        CLOSE @followUpCursor 
	DEALLOCATE @followUpCursor
END
GO

GRANT EXECUTE ON [dbo].[DAL_DeleteDeficiency] TO [DAL] AS [dbo]
GO
