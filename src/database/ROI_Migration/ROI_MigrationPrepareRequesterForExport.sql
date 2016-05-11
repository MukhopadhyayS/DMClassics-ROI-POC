/* BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
*
* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.
*
END-COPYRIGHT-COMMENT  Do not remove or modify this line! */

/*
Rem *************************************************************
Rem * Script File Name:	ROI_MigrationPrepareRequesterForExport.sql
Rem * Description:		This script will copy classic requesters to the
REM *					ROI_RequesterEdited table.
Rem *
Rem * Database Used:	cabinet
Rem * 
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	RC			06/16/2009	created
Rem * 	
Rem *************************************************************/
USE [cabinet]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_RequesterEdited]') AND type in (N'U'))
DROP TABLE [dbo].[ROI_RequesterEdited]
GO
--CREATE TABLE [ROI_RequesterEdited] (
--	[Requester_Id] [int] NOT NULL ,
--	[RequesterName] [varchar] (60) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
--	[Address1] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[Address2] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[City] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[State] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[Zip] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[Phone] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[Fax] [varchar] (20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--	[BillingType_Id] [int] NOT NULL ,
--	[RequesterType_Id] [int] NOT NULL ,
--	[RecordView] [varchar] (40) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
--	[Set_Id] [smallint] NOT NULL ,
--	[DefaultFlag] [char] (1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
--	[UpdatedDate] [smalldatetime] NOT NULL ,
--	[UpdateBy] [varchar] (30) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
--	[RequesterActive] [char] (1) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL ,
--	[Address3] [varchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
--) ON [PRIMARY]
--GO

--Insert ROI_RequesterEdited Select * FROM ROI_Requester
--WHERE 1 = 2
--GO
SET NOCOUNT ON

Select * into ROI_RequesterEdited From ROI_Requester
GO


GRANT Select, UPDATE, DELETE ON ROI_RequesterEdited to IMNET
GO


--Select count(*) AS [Requesters Copied] from ROI_RequesterEdited
