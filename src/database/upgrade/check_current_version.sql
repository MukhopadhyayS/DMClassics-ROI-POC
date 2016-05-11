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
Rem * Batch File Name: check_current_version.sql
Rem * Description:     	This script will check current HPF version.
Rem *			It also determine which serice pack to upgrade.
Rem *
Rem *		 Name			Type		Description
Rem *		 ---------		-------		----------------
Rem * 
Rem * 
Rem * 
Rem * 
Rem *    
Rem * 
Rem * Database Used:	master
Rem * 
Rem * NOTES: 
Rem * Revision History:
Rem *	Name		Date		ChangeType/Version/Track#
Rem *	---------	-------		--------------------------
Rem * 	
Rem * 	Joseph Chen	7/23/2009
Rem *	MD Meersma	09/11/2009	SQL 05/08 Upgrade changes
REM *	MD Meersma	09/11/2013	Remove SQL Server Version and Edition parameters
REM *	MD Meersma	09/16/2013	Remove creation of BAT file and include RAISERROR statements
Rem *************************************************************/

set nocount on

declare @current_major_release char(5), --- example 13.00
@next_major_release char(5),--- example '15.0'
@SQLversion varchar(100),
@Override char(1),
@LogFileName varchar(255)

select @SQLversion=@@version

select @current_major_release='16.0.'
select @next_major_release='16.1.'

select @Override = '$(OVERRIDE)'
select @LogFileName = '$(LOGFILENAME)'

--select '$(OVERRIDE)'
--select '$(LOGFILENAME)'


if not exists (select * from sysobjects where id = object_id('dbo.EPRS_VERSION') and sysstat & 0xf = 3)
begin
	--select 'Upgrade_Error: EPRS_VERSION table is missing from the master database!>>' + @LogFileName
	RAISERROR ('Upgrade_Error: EPRS_VERSION table is missing from the master database!', 24, 127) WITH LOG
end

else

IF upper(@Override) = 'N'
BEGIN
	IF NOT exists (select * from master.dbo.eprs_version where DB_NAME = 'HPF' and db_version like @current_major_release+'%') --- check to see if HPF has upgrade version of the release
	BEGIN
		--select 'Upgrade_Error: Your current MPF system is NOT on 16.0!>>' + @LogFileName
		RAISERROR ('Upgrade_Error: Your current MPF system is NOT on 16.0!', 24, 127) WITH LOG
	END
END
go
