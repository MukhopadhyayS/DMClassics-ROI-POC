USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.triggers WHERE object_id = OBJECT_ID(N'[dbo].[AssignmentsOnInsert]'))
DROP TRIGGER [dbo].[AssignmentsOnInsert]
GO

CREATE  TRIGGER [dbo].[AssignmentsOnInsert]
ON [dbo].[ASSIGNMENTS]
FOR INSERT
AS
/***************************************************************************************************************
* Trigger			: AssignmentsOnInsert
* Creation Date		: 07/15/2008
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
* Purpose			: Remove single quote or % characters from the purpose column
* Purpose			: update the assignment count in wq_definition
*				
* Database			: Cabinet
*									
* Requirements		: None
*									
*									
* Data Modifications: Removes illegal characters
*									
* Updates			: 
* Name				Date		Purpose
* ---------			-------		------------- 						
* RC 				07/14/2008	Created trigger						
* Joseph Chen		11/21/2008	remove spcial trigger 	
* MD Meersma			08/23/2013	Remove WQ_DEFINITION update
***************************************************************************************************************/
set nocount on     

declare @Activity varchar (50)  --aka the user_id or queue of the assignment
declare @Purpose varchar (260)  --aka the reason in HPF
declare @ASSIGNMENT_ID int		--the assignment counter

--assign the values from the inserted data
select 
	@Activity = UserId
	, @Purpose = PURPOSE
	, @ASSIGNMENT_ID = ASSIGNMENT_ID
from 
	inserted
--check if there are any offending characters
If 
	CHARINDEX('''', @Purpose)>0 --check for single quote
	OR 
	CHARINDEX('%', @Purpose)>0 --check for % sign
	--add character to filter below
	--OR 
	--CHARINDEX(''%'', @Purpose)>0 --check for  sign
BEGIN
	--start the replacement
	Set @Purpose = replace(@Purpose,'''',' ')
	Set @Purpose = replace(@Purpose,'%','pct')
	--add replacement statements here
	--Set @Purpose = replace(@Purpose,'''',''pct'')

	--update the table with the new purpose field
	update 
		assignments 
	set 
		purpose = @Purpose 
	where 
		ASSIGNMENT_ID = @ASSIGNMENT_ID
END

--update the assignment count in the wq_definition table
--begin transaction AssignUpdateTrig   
--	update 
--		wq_definition 
--	set 
--		assignment_count = ISNULL(assignment_count, 0)+1
--	where 
--		queue = @Activity
--commit transaction AssignUpdateTrig

--End of trigger
GO
