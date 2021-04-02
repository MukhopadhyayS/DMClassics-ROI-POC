USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[ANSI_Lookup_Claim]'))
DROP VIEW [dbo].[ANSI_Lookup_Claim]
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

/*****************************************************************
* Procedure Name:ANSI_Lookup_Claim
* Description:   
*
*		Name			Type		Description
*		---------		-------		----------------
* Params In:	-- HPF 13.0 ansi code
* Params Out: 
* Database: CABINET
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
* 	Ray Firth		created
* 	Steve Owens		Modified    add [] to name
*************************************************************************************************************************************/

CREATE VIEW [dbo].[ANSI_Lookup_Claim]
AS
SELECT  c.PatAcctNumber, E.ADMITTED, E.MRN, E.FACILITY,
              (SELECT     [NAME]
                FROM          his.dbo.PATIENTS AS p WITH (NOLOCK)
                WHERE      (p.MRN = E.MRN) AND (p.FACILITY = E.FACILITY)) AS [name], 
		c.ClaimNumber
FROM  his.dbo.x_refClaim_table AS c --WITH (INDEX = UIX_x_refClaim NOLOCK) 
	INNER JOIN his.dbo.ENCOUNTERS AS E WITH (NOLOCK) --(INDEX = PK_ENCOUNTERS_ENC_FAC NOLOCK) 
		ON c.PatAcctNumber = E.ENCOUNTER
GO

GRANT DELETE ON [dbo].[ANSI_Lookup_Claim] TO [IMNET] AS [dbo]
GO
GRANT INSERT ON [dbo].[ANSI_Lookup_Claim] TO [IMNET] AS [dbo]
GO
GRANT REFERENCES ON [dbo].[ANSI_Lookup_Claim] TO [IMNET] AS [dbo]
GO
GRANT SELECT ON [dbo].[ANSI_Lookup_Claim] TO [IMNET] AS [dbo]
GO
GRANT UPDATE ON [dbo].[ANSI_Lookup_Claim] TO [IMNET] AS [dbo]
GO

