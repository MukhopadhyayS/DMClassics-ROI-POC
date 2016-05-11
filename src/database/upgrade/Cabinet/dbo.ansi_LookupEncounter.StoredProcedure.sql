USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ansi_LookupEncounter]') AND type in (N'P'))
DROP PROCEDURE [dbo].[ansi_LookupEncounter]
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
* Procedure Name:ansi_LookupEncounter
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
* 	Joseph Chen		04/03/2009	 add section to not use wildcard search
*************************************************************************************************************************************/
CREATE PROCEDURE [dbo].[ansi_LookupEncounter] 
	-- Add the parameters for the stored procedure here
	@Mask varchar(38),
    @Search_option bit = 0
AS
BEGIN

-- SET NOCOUNT ON added to prevent extra result sets from
-- interfering with SELECT statements.
SET NOCOUNT ON

if @search_option = 1
  begin
    /* Old way */
	SELECT ENCOUNTER, MRN, FACILITY, ADMITTED,[NAME] FROM ANSI_Lookup_Encounter	
	WHERE ENCOUNTER LIKE '%' + @Mask + '%'
	--raiserror ('You have tried to use wildcard searches.  Please do not do this.', 8,0)
  end
else
  begin
    /* New way */
	SELECT ENCOUNTER, MRN, FACILITY, ADMITTED,[NAME] FROM ANSI_Lookup_Encounter	
	WHERE ENCOUNTER = @Mask
  end

END
GO

GRANT EXECUTE ON [dbo].[ansi_LookupEncounter] TO [IMNET] AS [dbo]
GO
