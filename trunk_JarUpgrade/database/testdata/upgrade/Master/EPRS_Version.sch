/*************************************************************************************************\
*  	Copyright  2003 McKesson Corporation and/or one of its subsidiaries. 
*		All rights reserved. 
*
* 	The copyright to the computer program(s) herein
* 	is the property of MCKESSON. The program(s)
* 	may be used and/or copied only with the written
* 	permission of MCKESSON or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
**************************************************************************************************
* Script Name: EPRS_version
* Script Type: SCH (For purpose of CREATE, DROP, ALTER, GRANT view; 
*				   INSERT, UPDATE, DELETE data on table.)
*
* Description: Insert a row for each pair of database and DB_VERSION if it doesn't exist. Else update the row.
*		The databases are AUDIT, CABINET, EIWDATA, HIS, AND WFE.
*
* Database: 	Master
* Tables Used:	EPRS_Version
*
* NOTES: 
* Revision History:
*	Name			Date		Changes/Version&Build/CQ#
*	---------		-------		----------------------------
*	
*	Joseph Chen				created	
\*************************************************************************************************/
Declare @@product_DB_NAME Varchar(25),
	@@product_db_version Varchar(50)	
/* @@product_db_version is reading 'MainRelease.MinorRelease.ServicePack.BuildNumber'.*/

SELECT @@product_DB_NAME ='HPF'
SELECT @@product_db_version = '13.5.0.0'
IF NOT EXISTS ( SELECT * FROM EPRS_Version WHERE (DB_NAME = @@product_DB_NAME )     AND 
						 (DB_VERSION = @@product_db_version)  )
	insert into EPRS_Version values(@@product_DB_NAME , @@product_db_version,GETDATE())
ELSE
	update EPRS_Version set DB_UPDATE_DATE = GETDATE() 
		where (DB_NAME = @@product_DB_NAME ) AND (DB_VERSION = @@product_db_version)

GO


