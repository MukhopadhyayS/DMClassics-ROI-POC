-- turn on deadlock log
use master
go

exec sp_configure 'show advanced option', '1'
reconfigure with override
go
exec sp_configure 'scan for startup procs', 1
reconfigure with override
go

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_MCKTraceFlags]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_MCKTraceFlags]
GO

create procedure sp_MCKTraceFlags
AS

DBCC TRACEON (1222,-1)
DBCC TRACEON(1204,-1)
GO

exec sp_procoption N'sp_MCKTraceFlags', 'startup', 'on'
go
