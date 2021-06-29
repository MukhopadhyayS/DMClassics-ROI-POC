use master 
go

/*************************************************************************************************\
*  	Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. 
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
* Script Name: sp_dbcmptlevel.sql
*
* Function		: Set database compt level
*
* Database		: master
*
* Revision History:
*	Name			Date		Changes/Version&Build/ClearQuest#
*	---------		-------		--------------------------------------------------
*	MD Meersma		01/22/2010	Set database compt level
*	MD Meersma		07/10/2010	Add EIS (ILE) Database
*	MD Meersma		09/25/2012	SQL Server 2012
\*************************************************************************************************/

-- SQL Server 2005

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel audit, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel cabinet, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel eiwdata, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel HIS, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel wfe, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9'
exec sp_dbcmptlevel master, 90
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '9' and
exists (select 1 from sys.databases where name = 'EIS')
exec sp_dbcmptlevel EIS, 90
go


-- SQL Server 2008

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel audit, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel cabinet, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel eiwdata, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel HIS, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel wfe, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10'
exec sp_dbcmptlevel master, 100
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '10' and
exists (select 1 from sys.databases where name = 'EIS')
exec sp_dbcmptlevel EIS, 100
go


-- SQL Server 2012

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel audit, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel cabinet, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel eiwdata, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel HIS, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel wfe, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11'
exec sp_dbcmptlevel master, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11' and
exists (select 1 from sys.databases where name = 'EIS')
exec sp_dbcmptlevel EIS, 110
go

if substring(CAST(SERVERPROPERTY('productversion') as varchar(255)), 1, PATINDEX('%.%', CAST(SERVERPROPERTY('productversion') as varchar(255))) - 1) = '11' and
exists (select 1 from sys.databases where name = 'MCK_HPF')
exec sp_dbcmptlevel MCK_HPF, 110
go
