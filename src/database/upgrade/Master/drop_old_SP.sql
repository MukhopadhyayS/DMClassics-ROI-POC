USE [master]
GO

/****** Object:  StoredProcedure [dbo].[sp_addalias2]    Script Date: 08/31/2009 10:09:08 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_addalias2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_addalias2]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_addgroup2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_addgroup2]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_changegroup2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_changegroup2]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_dropgroup2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_dropgroup2]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_addlogin2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_addlogin2]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_adduser2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_adduser2]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_droplogin2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_droplogin2]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_dropuser2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_dropuser2]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[sp_dropalias2]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[sp_dropalias2]
GO