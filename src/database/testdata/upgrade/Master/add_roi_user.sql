USE [master]
--create the login if it does not exist
IF NOT EXISTS(SELECT name FROM sys.server_principals WHERE name = 'roi')
BEGIN
    CREATE LOGIN roi WITH PASSWORD = 'roipazz', CHECK_POLICY = OFF, DEFAULT_DATABASE = [cabinet]
END

USE [audit]
GO
--create the user if it does not exist
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'roi')
CREATE USER [roi] FOR LOGIN [roi] WITH DEFAULT_SCHEMA=[roi]
GO
--fix the user is it happened to be orphaned
EXEC sp_change_users_login 'Auto_Fix', 'roi'
GO
--add the user to the imnet group
EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'roi'
GO
--do the next db
USE [cabinet]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'roi')
CREATE USER [roi] FOR LOGIN [roi] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'roi'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'roi'
GO

USE [his]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'roi')
CREATE USER [roi] FOR LOGIN [roi] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'roi'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'roi'
GO

USE [wfe]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'roi')
CREATE USER [roi] FOR LOGIN [roi] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'roi'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'roi'
GO


--now do the new user output
USE [master]

IF NOT EXISTS(SELECT name FROM sys.server_principals WHERE name = 'output')
BEGIN
    CREATE LOGIN [output] WITH PASSWORD = 'outputpazz', CHECK_POLICY = OFF, DEFAULT_DATABASE = [cabinet]
END

USE [audit]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'output')
CREATE USER [output] FOR LOGIN [output] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'output'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'output'
GO

USE [cabinet]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'output')
CREATE USER [output] FOR LOGIN [output] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'output'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'output'
GO

USE [his]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'output')
CREATE USER [output] FOR LOGIN [output] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'output'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'output'
GO

USE [wfe]
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'output')
CREATE USER [output] FOR LOGIN [output] WITH DEFAULT_SCHEMA=[roi]
GO

EXEC sp_change_users_login 'Auto_Fix', 'output'
GO

EXEC sys.sp_addrolemember @rolename=N'IMNET', @membername=N'output'
GO


