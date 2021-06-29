/* BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
*
* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.
*
END-COPYRIGHT-COMMENT  Do not remove or modify this line! */

/**************************************************************************************************
* Script Name: EPRS_version
* Script Type: SCH (For purpose of CREATE, DROP, ALTER, GRANT view; 
*				   INSERT, UPDATE, DELETE data on table.)
*
* Description: Insert a row for the HPF update
*
* Database: 	Master
* Tables Used:	EPRS_Version
*
* NOTES: 
* Revision History:
*	Name			Date		Changes/Version&Build/CQ#
*	---------		-------		----------------------------
*	MD Meersma		09/16/2009	Insert new row
*	
*	
\*************************************************************************************************/
Declare @@product_DB_NAME Varchar(25),
	@@product_db_version Varchar(50)	
/* @@product_db_version is reading 'MainRelease.MinorRelease.ServicePack.BuildNumber'.*/

SELECT @@product_DB_NAME ='HPF'
SELECT @@product_db_version = '@_Major_@.@_Minor_@.@_ServicePack_@.@_Build_@'

insert into EPRS_Version values(@@product_DB_NAME , @@product_db_version,GETDATE())
GO


