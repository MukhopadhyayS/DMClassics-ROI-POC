USE [master]
GO

exec sp_configure 'show advanced options', 1
go
reconfigure with override
go

exec sp_configure 'max worker threads', 0
go
exec sp_configure 'max degree of parallelism', 1
go
exec sp_configure 'cross db ownership chaining', 0
go
exec sp_configure 'remote query timeout (s)', 0
go
exec sp_configure 'optimize for ad hoc workloads', 1
go
exec sp_configure 'priority boost', 0
go
exec sp_configure 'xp_cmdshell', 1
go

reconfigure with override
go
