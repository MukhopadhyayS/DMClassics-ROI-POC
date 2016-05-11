USE master
GO
--Remove any old messages if they exist
If EXISTS
	(Select 1 from sys.messages where message_id = 60101)
EXEC sp_dropmessage 60101;
If EXISTS
	(Select 1 from sys.messages where message_id = 60102)
EXEC sp_dropmessage 60102;
If EXISTS
	(Select 1 from sys.messages where message_id = 60103)
EXEC sp_dropmessage 60103;
If EXISTS
	(Select 1 from sys.messages where message_id = 60104)
EXEC sp_dropmessage 60104;
If EXISTS
	(Select 1 from sys.messages where message_id = 60105)
EXEC sp_dropmessage 60105;
If EXISTS
	(Select 1 from sys.messages where message_id = 60106)
EXEC sp_dropmessage 60106;
--Add the ROI messages
EXEC sp_addmessage 60101, 16, 
   N'General Failure in procedure: %s, Update Failed.';
GO
EXEC sp_addmessage 60102, 16, 
   N'General Failure in procedure: %s, Delete Failed.';
GO
EXEC sp_addmessage 60103, 16, 
   N'General Failure in procedure: %s, Missing Parameter(s).';
GO
EXEC sp_addmessage 60104, 16, 
   N'No record found in procedure: %s. ';
GO
EXEC sp_addmessage 60105, 16, 
   N'Can not delete parent data: %s.';
GO
EXEC sp_addmessage 60106, 16, 
   N'General Failure in procedure: %s, Code already exits';
GO
