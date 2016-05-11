-- This must be the first SQL script to execute so that it collects all the original permissions

USE [eiwdata]
GO

-- drop table if it doesn't have three columns
IF (select count(*) from sys.objects so join sys.columns sc on so.object_id = sc.object_id 
WHERE so.object_id = OBJECT_ID(N'[dbo].[ObjectPermissions]') AND so.type in (N'U')) <> 3 
and OBJECT_ID('ObjectPermissions') IS NOT NULL
DROP TABLE [dbo].[ObjectPermissions]
GO

select 'Capture Object Permissions'

-- This script will read all of the existing permissions in the database and create SQL Statement in ObjectPermissions 
-- that will be used after all the SQL scripts in the upgrade are applied to restore any custom permission settings

IF OBJECT_ID('ObjectPermissions') IS NOT NULL
-- Save current permissions into ObjectPermissions
insert ObjectPermissions
select state_desc + ' ' + permission_name + ' ON [dbo].[' +  object_name(object_id) + '] TO ' + USER_NAME(grantee_principal_id) + ' AS [dbo]' as mysql, getdate() as CreatedDt, '[dbo].[' +  object_name(object_id) + ']' as objectname
from sys.procedures sp JOIN sys.database_permissions dp ON sp.object_id = dp.major_id
order by sp.name
GO

IF OBJECT_ID('ObjectPermissions') IS NULL
-- Save current permissions into ObjectPermissions
select state_desc + ' ' + permission_name + ' ON [dbo].[' +  object_name(object_id) + '] TO ' + USER_NAME(grantee_principal_id) + ' AS [dbo]' as mysql, getdate() as CreatedDt,  '[dbo].[' +  object_name(object_id) + ']' as objectname
into ObjectPermissions
from sys.procedures sp JOIN sys.database_permissions dp ON sp.object_id = dp.major_id
order by sp.name
GO

DELETE [dbo].[ObjectPermissions] where CreatedDT < getdate()-7
GO