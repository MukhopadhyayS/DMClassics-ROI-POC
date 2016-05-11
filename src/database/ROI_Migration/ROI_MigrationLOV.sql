SET QUOTED_IDENTIFIER ON
GO


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


/***************************************************************************************************************
* Procedure             : ROI_MigrationLOV.sql
* Creation Date         : 05/26/2009
*					
* Written by            : RC	
*									
* Purpose               : Create table for linking classic to current ROI migration values
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        	Description
*   	---------	----------- 	----------------
*									
* Return Status         : <none>		
*									
* Usage                 : ROI_MigrationLOV.sql 
*																
* Called By             : ROI migration
*									
* Tables used           : <none>
*			
* Data Modifications	: <none>
*									
* Updates: 	
* Name      	Date        Purpose
* ---------	----------- ----------------
* RC        	05/26/2009  Created   
*
***************************************************************************************************************************************/
Print 'Started ROI_MigrationLOV table creation at ' + cast(getdate() as varchar(20));
USE [cabinet]
GO
/****** Object:  Table [dbo].[ROI_MigrationLOV]    Script Date: 08/26/2009 12:37:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_MigrationLOV]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ROI_MigrationLOV](
	[ROI_MigrationLOV_Seq] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[TableObjectID] [int] NOT NULL,
	[TableRowID] [int] NOT NULL,
 CONSTRAINT [PK_ROI_MigrationLOV_Seq] PRIMARY KEY CLUSTERED 
(
	[ROI_MigrationLOV_Seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
GRANT DELETE ON [dbo].[ROI_MigrationLOV] TO [IMNET]
GO
GRANT INSERT ON [dbo].[ROI_MigrationLOV] TO [IMNET]
GO
GRANT SELECT ON [dbo].[ROI_MigrationLOV] TO [IMNET]
GO
GRANT UPDATE ON [dbo].[ROI_MigrationLOV] TO [IMNET]
GO
Print 'Completed ROI_MigrationLOV table creation at ' + cast(getdate() as varchar(20));
GO
Print 'Started ROI_MigrationCrossRef table creation at ' + cast(getdate() as varchar(20));
USE [cabinet]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
USE [cabinet]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_MigrationCrossRef]') AND type in (N'U'))
DROP TABLE [dbo].[ROI_MigrationCrossRef]
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_MigrationCrossRef]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ROI_MigrationCrossRef](
	[ROI_MigrationCrossRef_Seq] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[ROI_RequestMain_Seq] [int] NOT NULL,
	[Request_Id] [int] NOT NULL,
 CONSTRAINT [PK_ROI_MigrationCrossRef_Seq] PRIMARY KEY CLUSTERED 
(
	[ROI_MigrationCrossRef_Seq] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
GRANT DELETE ON [dbo].[ROI_MigrationCrossRef] TO [IMNET]
GO
GRANT INSERT ON [dbo].[ROI_MigrationCrossRef] TO [IMNET]
GO
GRANT SELECT ON [dbo].[ROI_MigrationCrossRef] TO [IMNET]
GO
GRANT UPDATE ON [dbo].[ROI_MigrationCrossRef] TO [IMNET]
GO
Print 'Completed ROI_MigrationCrossRef table creation at ' + cast(getdate() as varchar(20));

