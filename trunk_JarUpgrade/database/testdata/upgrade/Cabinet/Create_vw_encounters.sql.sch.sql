USE [cabinet]
GO

/****** Object:  View [dbo].[vw_encounters]    Script Date: 04/15/2009 12:21:58 ******/
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[vw_encounters]'))
DROP VIEW [dbo].[vw_encounters]
GO

USE [cabinet]
GO

/****** Object:  View [dbo].[vw_encounters]    Script Date: 04/15/2009 12:21:58 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[vw_encounters]'))
EXEC dbo.sp_executesql @statement = N'/***************************************************************************************************
*
* Copyright		: COPYRIGHT MCKESSON 2008
*			: ALL RIGHTS RESERVED
*
* 			: The copyright to the computer program(s) herein is the property of
* 			: McKesson. The program(s) may be used and/or copied only with the 
* 			: written permission of McKesson or in accordance with the terms
* 			: and conditions stipulated in the agreement/contract under which the     
* 			: program(s) have been supplied.		
*			
* Written by		: Joseph Chen
*									
* Purpose		: 
*			    	
* Database		:Cabinet
*
*									
* 
*			
*								
* Data Modifications	: <none>			
*								
* Name			Date		Purpose
* ---------		-------		------------- 							
* Joseph Chen				change select statement to not use select * from
* RC            02/24/2005  added VIP column
***************************************************************************************************/
CREATE VIEW [dbo].[vw_encounters]
AS
SELECT     ENCOUNTER, MRN, NO_PUB, ADMITTED, DISCHARGED, FACILITY, CLINIC, PT_TYPE, SERVICE, PAYOR_CODE, FIN_CLASS, PRIM_PLAN, SEC_PLAN, 
                      ADMIT_SOURCE, TOTAL_CHARGES, BALANCE_CHARGES, LOCKOUT, REMARK, DISPOSITION, ENC_OPT_OUT, VIP
FROM         his.dbo.ENCOUNTERS
' 
GO

GRANT DELETE ON [dbo].[vw_encounters] TO [IMNET] AS [dbo]
GO

GRANT INSERT ON [dbo].[vw_encounters] TO [IMNET] AS [dbo]
GO

GRANT SELECT ON [dbo].[vw_encounters] TO [IMNET] AS [dbo]
GO

GRANT UPDATE ON [dbo].[vw_encounters] TO [IMNET] AS [dbo]
GO

