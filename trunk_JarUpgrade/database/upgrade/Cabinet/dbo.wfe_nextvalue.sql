USE [cabinet]
GO

/****************************************************************
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
*****************************************************************
* Procedure Name: DAL_ARCHIVE
* Description: 
*
*		Name			Type		Description
*		---------			-------		----------------
* Params In:	
*	            @reason varchar(128)
	            @oldImnet varchar(22)
	            @newImnet varchar(22)
	            
* Params Out: @output varchar(64)

* Database: CABINET
* 
* NOTES: 
* Revision History:
*	Name			Date		Changes
*	---------		-------		-------------
* 	
*	MD Meersma		10/17/2013	Sync CalctableID with wfe_nextvalue
\*****************************************************************************************************/

update wfe_nextvalue
set NextID = (select max(CalcTable_ID) from CALCTABLE)
where Name = 'calctable'
go
