USE [eiwdata]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ADM_db_SetUserForSection]') AND type in (N'P'))
DROP PROCEDURE [dbo].[ADM_db_SetUserForSection]
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
* Procedure Name: 
* Description: 
*
*				Name			Type		Description
*				---------		-------		----------------
* Params In:			
*		
* Params Out:	
* Result Set: 	
*
* Returns:		
* Database:		
* Tables Used:	
*
* NOTES: 
* Revision History:
*				Name			Date		Changes/Version&Build/Track#
*				---------		-------		----------------------------
*				
*				Joseph Chen		08/20/2009	Change for SQL 2008
*				NTTDATA			10/04/2013	Modified for CR#378,274
*				NTTDATA			09/16/2013	Modified for CR#380,586
*****************************************************************/

CREATE PROCEDURE [dbo].[ADM_db_SetUserForSection]
(
@Section	varchar(32),
@UserName	varchar(50) OUTPUT,
@MachineName	varchar(255) OUTPUT,
@AdStatus	int 
)
AS

declare @UserID 	int,
	@Time		datetime,
	@SecTime  	datetime,
	@SecUserID	int,
	@SecUserName	varchar(255),
	@SecMachineName varchar(255)
	
select @Time = GETDATE()
select @SecTime = sydb_lock_time 
   from EIWT_SYSADMIN_DB_LOCK
   where sydb_lock_section = @Section
   
--Implemented for AD users    
if @AdStatus = 1
BEGIN	
	if (@SecTime is NULL) OR (DATEDIFF(minute, @SecTime, @Time)  > 2)
	 BEGIN	 
		UPDATE EIWT_SYSADMIN_DB_LOCK
		   SET sydb_lock_userid = NULL, sydb_lock_machine_name = @MachineName, sydb_lock_time = @Time, sydb_lock_adusername = @UserName
			WHERE sydb_lock_section = @Section
	 END
	else
	 BEGIN  
		select @SecUserName = sydb_lock_adusername
		 from EIWT_SYSADMIN_DB_LOCK
		  where sydb_lock_section = @Section
		select @UserName = @SecUserName	
		select @SecMachineName = sydb_lock_machine_name
		 from EIWT_SYSADMIN_DB_LOCK
		  where sydb_lock_section = @Section  

		if @SecMachineName <> @MachineName 
		 BEGIN
			select @MachineName = @SecMachineName
			return -3000
		 END
		else
		 BEGIN
			UPDATE EIWT_SYSADMIN_DB_LOCK
			  SET sydb_lock_time = @Time
			   WHERE sydb_lock_section = @Section
		 END
		
	 END
return 0
END
--Implemented for AD users 
else
 BEGIN 
	if (@SecTime is NULL) OR (DATEDIFF(minute, @SecTime, @Time)  > 2)
	 BEGIN
	  select @UserID = user_id(system_user)
	  if @UserID is NULL
		 return -20		-- Invalid User Name
		
	  UPDATE EIWT_SYSADMIN_DB_LOCK
	   SET sydb_lock_userid = @UserID, sydb_lock_machine_name = @MachineName, sydb_lock_time = @Time, sydb_lock_adusername = NULL
		WHERE sydb_lock_section = @Section

	 END
	else
	BEGIN  
		select @SecUserID = sydb_lock_userid
		 from EIWT_SYSADMIN_DB_LOCK
		  where sydb_lock_section = @Section

		select @SecMachineName = sydb_lock_machine_name
		 from EIWT_SYSADMIN_DB_LOCK
		  where sydb_lock_section = @Section
		
		if @SecUserID <> user_id(system_user) OR @SecMachineName <> @MachineName 
		 BEGIN
			select @UserName = name 
			 from sys.SYSUSERS, EIWT_SYSADMIN_DB_LOCK
			  where uid = sydb_lock_userid
			  and sydb_lock_section = @Section

			select @MachineName = @SecMachineName

			return -3000      	-- Someone else is running this section of sysadmin
		 END
		else
		 BEGIN
			UPDATE EIWT_SYSADMIN_DB_LOCK
			  SET sydb_lock_time = @Time
			   WHERE sydb_lock_section = @Section
		 END

	END
END
return @@ERROR
GO

GRANT EXECUTE ON [dbo].[ADM_db_SetUserForSection] TO [EIW_Admin                     ] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[ADM_db_SetUserForSection] TO [EIW_AdminGroup                ] AS [dbo]
GO