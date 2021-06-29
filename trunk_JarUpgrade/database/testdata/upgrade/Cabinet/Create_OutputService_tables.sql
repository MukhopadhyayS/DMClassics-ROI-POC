/****** Object:  ForeignKey [FK_Output_JobParts_Output_Job]    Script Date: 03/26/2009 11:18:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Output_JobParts_Output_Job]') AND parent_object_id = OBJECT_ID(N'[dbo].[Output_JobParts]'))
ALTER TABLE [dbo].[Output_JobParts] DROP CONSTRAINT [FK_Output_JobParts_Output_Job]
GO
/****** Object:  Table [dbo].[Output_JobParts]    Script Date: 03/26/2009 11:18:14 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Output_JobParts_Output_Job]') AND parent_object_id = OBJECT_ID(N'[dbo].[Output_JobParts]'))
ALTER TABLE [dbo].[Output_JobParts] DROP CONSTRAINT [FK_Output_JobParts_Output_Job]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_JobParts]') AND type in (N'U'))
DROP TABLE [dbo].[Output_JobParts]
GO
/****** Object:  Table [dbo].[Output_Job]    Script Date: 03/26/2009 11:18:14 ******/
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Created_Dt]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Created_Dt]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Created_By_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Created_By_Seq]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Modified_Dt]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Modified_Dt]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Modified_By_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Modified_By_Seq]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Record_Version]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Record_Version]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_Job_osj_Organization_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_Job] DROP CONSTRAINT [DF_Output_Job_osj_Organization_Seq]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_Job]') AND type in (N'U'))
DROP TABLE [dbo].[Output_Job]
GO
/****** Object:  Table [dbo].[Output_ListOfValue]    Script Date: 03/26/2009 11:18:14 ******/
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Created_Dt]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Created_Dt]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Created_By_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Created_By_Seq]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Modified_Dt]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Modified_Dt]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Modified_By_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Modified_By_Seq]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Record_Version]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Record_Version]
END
GO
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[DF_Output_ListOfValue_oslov_Organization_Seq]') AND type = 'D')
BEGIN
ALTER TABLE [dbo].[Output_ListOfValue] DROP CONSTRAINT [DF_Output_ListOfValue_oslov_Organization_Seq]
END
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_ListOfValue]') AND type in (N'U'))
DROP TABLE [dbo].[Output_ListOfValue]
GO
/****** Object:  Table [dbo].[Output_ListOfValue]    Script Date: 03/26/2009 11:18:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_ListOfValue]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Output_ListOfValue](
	[oslov_seq] [int] IDENTITY(1,1) NOT NULL,
	[oslov_Created_Dt] [smalldatetime] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Created_Dt]  DEFAULT (getdate()),
	[oslov_Created_By_Seq] [int] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Created_By_Seq]  DEFAULT ((1)),
	[oslov_Modified_Dt] [smalldatetime] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Modified_Dt]  DEFAULT (getdate()),
	[oslov_Modified_By_Seq] [int] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Modified_By_Seq]  DEFAULT ((1)),
	[oslov_Record_Version] [smallint] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Record_Version]  DEFAULT ((1)),
	[oslov_Organization_Seq] [int] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_Organization_Seq]  DEFAULT ((1)),
	[oslov_parent_seq] [int] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_parent_Seq]  DEFAULT ((0)),
	[oslov_child_Seq] [int] NOT NULL CONSTRAINT [DF_Output_ListOfValue_oslov_child_Seq]  DEFAULT ((0)),
	[oslov_Name] [varchar](50) NOT NULL,
	[oslov_Data] [xml] NOT NULL,
 CONSTRAINT [PK_oslov_seq] PRIMARY KEY CLUSTERED 
(
	[oslov_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Output_Job]    Script Date: 03/26/2009 11:18:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_Job]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Output_Job](
	[osj_seq] [int] IDENTITY(1,1) NOT NULL,
	[osj_Created_Dt] [smalldatetime] NOT NULL CONSTRAINT [DF_Output_Job_osj_Created_Dt]  DEFAULT (getdate()),
	[osj_Created_By_Seq] [int] NOT NULL CONSTRAINT [DF_Output_Job_osj_Created_By_Seq]  DEFAULT ((1)),
	[osj_Modified_Dt] [smalldatetime] NOT NULL CONSTRAINT [DF_Output_Job_osj_Modified_Dt]  DEFAULT (getdate()),
	[osj_Modified_By_Seq] [int] NOT NULL CONSTRAINT [DF_Output_Job_osj_Modified_By_Seq]  DEFAULT ((1)),
	[osj_Record_Version] [smallint] NOT NULL CONSTRAINT [DF_Output_Job_osj_Record_Version]  DEFAULT ((1)),
	[osj_Organization_Seq] [int] NOT NULL CONSTRAINT [DF_Output_Job_osj_Organization_Seq]  DEFAULT ((1)),
	[osj_job_id] [varchar](64) NOT NULL,
	[osj_submitted_by] [varchar](64) NOT NULL,
	[osj_queue_seq] [int] NOT NULL,
	[osj_status_seq] [int] NOT NULL,
	[osj_pages] [int] NOT NULL,
	[osj_output_request] [xml] NOT NULL,
 CONSTRAINT [PK_osj_seq] PRIMARY KEY CLUSTERED 
(
	[osj_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Output_JobParts]    Script Date: 08/20/2009 20:35:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Output_JobParts]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Output_JobParts](
	[osjp_seq] [int] IDENTITY(1,1) NOT NULL,
	[osjp_osj_seq] [int] NOT NULL,
	[osjp_part_seq] [int] NOT NULL,
	[osjp_item_id] [varchar](64) NOT NULL,
	[osjp_status_seq] [int] NOT NULL,
	[osjp_pages] [int] NOT NULL,
 CONSTRAINT [IXPK_osjp_seq] PRIMARY KEY NONCLUSTERED 
(
	[osjp_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO

/****** Object:  Index [IXC_Output_JobParts]    Script Date: 08/20/2009 20:35:21 ******/
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Output_JobParts]') AND name = N'IXC_Output_JobParts')
CREATE CLUSTERED INDEX [IXC_Output_JobParts] ON [dbo].[Output_JobParts] 
(
	[osjp_osj_seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Output_JobParts_Output_Job]') AND parent_object_id = OBJECT_ID(N'[dbo].[Output_JobParts]'))
ALTER TABLE [dbo].[Output_JobParts]  WITH CHECK ADD  CONSTRAINT [FK_Output_JobParts_Output_Job] FOREIGN KEY([osjp_osj_seq])
REFERENCES [dbo].[Output_Job] ([osj_seq])
GO
ALTER TABLE [dbo].[Output_JobParts] CHECK CONSTRAINT [FK_Output_JobParts_Output_Job]
GO
GRANT DELETE ON [dbo].[Output_JobParts] TO [IMNET] AS [dbo]
GO
GRANT INSERT ON [dbo].[Output_JobParts] TO [IMNET] AS [dbo]
GO
GRANT SELECT ON [dbo].[Output_JobParts] TO [IMNET] AS [dbo]
GO
GRANT UPDATE ON [dbo].[Output_JobParts] TO [IMNET] AS [dbo]
GO
GRANT DELETE ON [dbo].[Output_Job] TO [IMNET] AS [dbo]
GO
GRANT INSERT ON [dbo].[Output_Job] TO [IMNET] AS [dbo]
GO
GRANT SELECT ON [dbo].[Output_Job] TO [IMNET] AS [dbo]
GO
GRANT UPDATE ON [dbo].[Output_Job] TO [IMNET] AS [dbo]
GO
GRANT DELETE ON [dbo].[Output_ListOfValue] TO [IMNET] AS [dbo]
GO
GRANT INSERT ON [dbo].[Output_ListOfValue] TO [IMNET] AS [dbo]
GO
GRANT SELECT ON [dbo].[Output_ListOfValue] TO [IMNET] AS [dbo]
GO
GRANT UPDATE ON [dbo].[Output_ListOfValue] TO [IMNET] AS [dbo]
GO

/****** Object:  Index [IXU_oslov_Parent_Name]    Script Date: 04/15/2009 13:37:30 ******/
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Output_ListOfValue]') AND name = N'IXU_oslov_Parent_Name')
DROP INDEX [IXU_oslov_Parent_Name] ON [dbo].[Output_ListOfValue] WITH ( ONLINE = OFF )
GO

/****** Object:  Index [IXU_oslov_Parent_Name]    Script Date: 04/15/2009 13:37:30 ******/
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Output_ListOfValue]') AND name = N'IXU_oslov_Parent_Name')
CREATE UNIQUE NONCLUSTERED INDEX [IXU_oslov_Parent_Name] ON [dbo].[Output_ListOfValue] 
(
     [oslov_parent_seq] ASC,
     [oslov_Name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
