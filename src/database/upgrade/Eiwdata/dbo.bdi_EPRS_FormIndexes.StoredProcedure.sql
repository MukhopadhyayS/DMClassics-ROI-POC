USE [eiwdata]
GO

/****************************************************************
*  	COPYRIGHT MCKESSON 2007
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of McKesson. The program(s)
* 	may be used and/or copied only with the written
* 	permission of McKesson or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
*****************************************************************
* Procedure Name:bdi_EPRSFormIndexes
* Description:   
*
*		Name			Type		Description
*		---------		-------		----------------
* Params In:	-- HPF 13.0 ansi code
* Params Out: 
* Database: EIWDATA
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
* 	Ray Firth				created


* This is for installing BDI
*************************************************************************************************************************************/

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[bdi_EPRSFormIndexes]') AND type in (N'P'))
drop procedure [dbo].[bdi_EPRSFormIndexes]
GO

CREATE PROCEDURE [dbo].[bdi_EPRSFormIndexes]
AS
SELECT m.frmind_docset_order, i.index_name
FROM EIWT_FORM_INDEX_MAP m INNER JOIN
            EIWT_INDEX i ON m.frmind_index_id = i.index_id
WHERE m.frmind_form_id = 1
ORDER BY m.frmind_docset_order
GO

GRANT EXECUTE ON [dbo].[bdi_EPRSFormIndexes] TO [EPR]
GO
GRANT EXECUTE ON [dbo].[bdi_EPRSFormIndexes] TO [EIW_Admin                     ] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[bdi_EPRSFormIndexes] TO [EIW_AdminGroup                ] AS [dbo]
GO
