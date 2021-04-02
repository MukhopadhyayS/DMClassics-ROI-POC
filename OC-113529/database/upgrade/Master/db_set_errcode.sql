/*************************************************************************
*  	COPYRIGHT MCKESSON 2002
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of McKessonHBOC. The program(s)
* 	may be used and/or copied only with the written
* 	permission of McKessonHBOC or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
***************************************************************************
* File Name: db_set_errcode
* Script Type: SQL (For purpose of CREATE, DROP, ALTER, GRANT to stored procedure.)
* Description:  Script file db_set_errcode.sql  for PIM4.5.3                         	                                        	 
*   		Create user defined SQL error messages	
*
*		Name			Type		Description
*		---------		-------		----------------
* Params In:	
*				
* Params Out: 
*
* Result Set: 	
*	          									  
* Database: EIWDATA								
*
* NOTES: 
* Revision History:
*	Name			Date		Changes/Version/Track#
*	---------		-------		-------------
*	Judy Jiang		11/02/2000	Created
* 	Judy Jiang		10/31/2001	Updated:  Delete User-defined SQL Error for imnetXP(50012-50020). 
* 	Judy Jiang		04/17/2002	Updated:(#16977) Move The user-defined error messages, like 6xxxx (for ROI), 
*						from UPGRADE/4.5.0/Cabinet/after_install_dbChanges.sch here.					
*	Judy Jiang		05/06/2002	Updated/PIM600/: Copy 'sp_addmessage 50006' from file add2sysmessages.tbl under
*						IE45\upgrade\4.5.2\eiwdata.
*
**********************************************************************************/

USE master 
go
print 'In db_set_errcode.SQL'
go

print 'Add user-defined messages(50001..50006) in Master database.'
go

EXEC sp_addmessage 50001, 16, 'There are some users still associated with form. 
	Please unassociate users from this form first.',null,false,'replace'
EXEC sp_addmessage 50002, 16, 'There are some Batches still associated with this BatchType. 
	Please delete these Batches first.',null,false,'replace'
EXEC sp_addmessage 50003, 16, 'There are some Rules still associated with this MirrorList. 
	Please unassociate these Rules first.',null,false,'replace'
EXEC sp_addmessage 50004, 16, 'There are some Volumes still associated with this MirrorList. 
	Please unassociate these Volumes first.',null,false,'replace'
EXEC sp_addmessage 50005, 16, 'Permission Denied for this user.',null,false,'replace'
EXEC sp_addmessage 50006, 16, 'You can NOT copy group EIW_AdminGroup.',null,false,'replace'


/*
print 'Add User-defined SQL Error for imnetXP'

EXEC sp_addmessage 50012, 16, 'Error executing extended stored procedure: Invalid Parameters.',null,false,'replace'
EXEC sp_addmessage 50013, 16, 'Error executing extended stored procedure: Invalid Parameter Numbers.',null,false,'replace'
EXEC sp_addmessage 50014, 16, 'Error executing extended stored procedure: Invalid Parameter 1.',null,false,'replace'
EXEC sp_addmessage 50015, 16, 'Error executing extended stored procedure: Invalid Parameter 2.',null,false,'replace'
EXEC sp_addmessage 50016, 16, 'Error executing extended stored procedure: Invalid Parameter 3.',null,false,'replace'
EXEC sp_addmessage 50017, 16, 'Error executing extended stored procedure: can not find SQL REGKEY.',null,false,'replace'
EXEC sp_addmessage 50018, 16, 'Error executing extended stored procedure: can not find SQL REGKEY VAL.',null,false,'replace'
EXEC sp_addmessage 50019, 16, 'Error executing extended stored procedure: fail to open databse.',null,false,'replace'
EXEC sp_addmessage 50020, 16, 'Error executing extended stored procedure: fail to create script file.',null,false,'replace'
*/ 
Print 'Add General Messages (55000..55003) in Master database.'

exec sp_addmessage 55000, 16, 'General Message in procedure: No record is found in table %s in procedure %s.',null,false,'replace'
exec sp_addmessage 55001, 16, 'There is %s still associated with this %s. Please %s those first.',null,false,'replace'
exec sp_addmessage 55002, 16, 'General Message in procedure: Invalid %s name: %s.',null,false,'replace'
exec sp_addmessage 55003, 16, 'General Message in procedure: Invalid %s ID: %ld',null,false,'replace'



Print 'Insert user defined messages(60100..60106) in Master database.'

EXEC sp_addmessage @msgnum = 60100, @severity = 16, 
    @msgtext = N'General Failure in procedure: %s, Insert Failed.', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60101, @severity = 16, 
    @msgtext = N'General Failure in procedure: %s, Update Failed.', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60102, @severity = 16, 
    @msgtext = N'General Failure in procedure: %s, Delete Failed.', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60103, @severity = 16, 
    @msgtext = N'General Failure in procedure: %s, Missing Parameter(s).', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60104, @severity = 16, 
    @msgtext = N'No record found in procedure: %s.', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60105, @severity = 16, 
    @msgtext = N'Can not delete parent data: %s.', 
    @lang = 'us_english', @replace = 'replace'
go
EXEC sp_addmessage @msgnum = 60106, @severity = 16, 
    @msgtext = N'General Failure in procedure: %s, Code already exits', 
    @lang = 'us_english', @replace = 'replace'
go

print 'End of db_set_errcode.SQL'
go

