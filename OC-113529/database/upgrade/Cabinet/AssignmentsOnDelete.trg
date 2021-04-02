USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.triggers WHERE object_id = OBJECT_ID(N'[dbo].[AssignmentsOnDelete]'))
DISABLE TRIGGER [dbo].[AssignmentsOnDelete] on [dbo].[ASSIGNMENTS]
GO
