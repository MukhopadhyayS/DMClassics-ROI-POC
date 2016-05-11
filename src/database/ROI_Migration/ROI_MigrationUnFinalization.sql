
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
Rem * Batch File Name:	ROI_MigrationUnFinalization.sql
Rem * Description:		This script will modify files so no further migrations can be performed.
Rem *
Rem * 
Rem * Database Used:	master
Rem * 
Rem * Insert master.dbo.EPRS_Version Values('ROI_Migration','Final',getdate())
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	RC			04/13/2009	created
Rem * 	
Rem *************************************************************/

set nocount on
GO
use cabinet
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_RequestMain]') AND type in (N'U'))
    BEGIN
      select 'echo Migration_Error: Your current HPF is not on the correct version for ROI migration.'
    END
ELSE
    IF EXISTS (Select * from master.dbo.EPRS_Version where DB_NAME = 'ROI_Migration' and DB_VERSION = 'Final')
    BEGIN
        DELETE from master.dbo.EPRS_Version where DB_NAME = 'ROI_Migration' and DB_VERSION = 'Final'
    END
    ELSE
    BEGIN
      select 'The migration is not final'
    END

go

