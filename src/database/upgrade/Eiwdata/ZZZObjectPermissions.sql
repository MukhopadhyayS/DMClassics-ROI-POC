-- This must be the LAST SQL script to execute so that it applies all the original permissions

USE [eiwdata]
GO

select 'Grant Object Permissions'

-- This script will apply all of the existing permissions in the database using ObjectPermissions 
-- which was populated at the beginning of the upgrade before any objects were modified

IF OBJECT_ID('ObjectPermissions') IS NOT NULL
BEGIN

DECLARE @mysql varchar(max), @objectname varchar(max)
DECLARE Object_Cursor cursor for select mysql, objectname from ObjectPermissions

OPEN Object_Cursor
fetch next from Object_Cursor into @mysql, @objectname

while @@FETCH_STATUS=0
begin
	if object_id(@objectname) is not null
	exec (@mysql)
	fetch next from Object_Cursor into @mysql, @objectname
end

CLOSE Object_Cursor
DEALLOCATE Object_Cursor
END
GO