/****************************************************************
Rem *  	Copyright © 2009 McKesson Corporation and/or one of its subsidiaries. 
Rem *		All rights reserved. 
Rem *
Rem * 	The copyright to the computer program(s) herein
Rem * 	is the property of McKesson. The program(s)
Rem * 	may be used and/or copied only with the written
Rem * 	permission of McKesson or in accordance with
Rem * 	the terms and conditions stipulated in the     
Rem * 	agreement/contract under which the program(s)
Rem * 	have been supplied.
Rem *************************************************************
Rem * Script File Name:	ROI_MigrationPrepareRequesterForImport.sql
Rem * Description:		This script will create an empty
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

SET NOCOUNT ON
GO

Select * into ROI_RequesterEdited From ROI_Requester WHERE 1 = 2
GO


GRANT Select, UPDATE, DELETE ON ROI_RequesterEdited to IMNET
GO


--Select count(*) AS [Requesters Copied] from ROI_RequesterEdited
