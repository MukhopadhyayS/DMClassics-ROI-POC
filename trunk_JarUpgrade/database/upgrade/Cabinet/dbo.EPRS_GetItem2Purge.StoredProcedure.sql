USE [cabinet]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[EPRS_GetItem2Purge]') AND type in (N'P'))
DROP PROCEDURE [dbo].[EPRS_GetItem2Purge]
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

/***************************************************************************************************************
* Stored Procedure: EPRS_GetItem2Purge
* Creation Date		: 06/02/2009
*					
* Written by:	RC	
*									
* Purpose:	Pass two flags back (@RowsFound and @Status) and data to the calling program based on the parameter @DaysToKeep:
*		
*		For any data returned to the calling program the imnet is entered into the Cab_Del_Items_InUse table. 
*		The data entered into the Cab_Del_Items_InUse table is accompanied by the Agent Host Name and Agent ID.
*		Entries in the InUse table prevent multiple agents from recieving the same items to purge
*
* Database:	CABINET
*									
* Input Parameters	: 
*	Name		Type		Description
* 	---------	-------		----------------
*	@DaysToKeep integer		Number of days to keep archived images
*	@Agent		char(15)	The Windows Hostname of the calling program.  Used to 'checkout' the item to the Agent
*	@AgentID	smallint	A number to identify the instance of the program
*	@Debug		smallint	Turns on debugging for interactive testing
*								
* Output Parameters	: 
*	Name		Type		Description
* 	---------	-------		----------------
*	@RowsFound 	integer		If => 1 there is data delete
*					If 0 there is no data to delete
*	@StatusMsg	char(64)	Text message to indicate what work is available in the form 
*					XXXXXXXXX items available to purge of XXXXXXXXX total items
*									
* Return Status		: <none>		
*									
* Usage			: EPRS_GetItem2Purge @DaysToKeep @Agent @AgentID (Debug optional)
*																
* Called By		: Purge
*									
* Calls			: Cabinet_Version, cab_del_items, Cab_Del_Items_InUse
*			
* Data Modifications	:may insert data into the Cab_Del_Items_InUse table
*			:may delete from cab_del_items
*			:may insert into Audit_trail
*									
* Updates: 	Name		Date		Purpose
*		---------	-------		------------- 						
* 		RC			06/02/2009	Created new stored procedure.  This stored procedure replaces the stored procedure						
*						EPRS_GetArchivedPages.  
*		RC/Ben		09/22/2009	Optimized the check for non-deleted pages by moving the logic to within the cab_del_itmes table 
*		RC			10/01/2009	Added missing 'end' to IF statement
*		MD Meersma	03/17/2010	Performance Enhancements
*		MD Meersma	04/13/2010	Remove Index Hint, CAB_DEL_Items != 'Y'
*		MD Meersma	01/18/2013	Include column list when inserting into Audit_Trail
*		MD Meersma	01/23/2013	WITH NOLOCK Pages, Cabinet_Version
*		MD Meersma	08/16/2013	Remove UPPER function on Cabinet.dbo.Pages.Deleted
*		MD Meersma	08/19/2013	Use Global temp table instead of tablockx to control one instance of SP
*		MD Meersma	08/28/2013	Change SELECT TOP 100 Percent to TOP 1000, purge can still control the SET ROWCOUNT if it is less than 1000
***************************************************************************************************************************************/
CREATE      PROCEDURE [dbo].[EPRS_GetItem2Purge]
@DaysToKeep	smallint,
@Agent		char(15),
@AgentID	smallint,
@RowsFound	integer Output,
@StatusMsg	char(64) Output,
@Debug		smallint = 0

AS

BEGIN

DECLARE @I int, @TotalImnet tinyint

SET NOCOUNT ON

--check to see if another copy of the stored procedure is running
IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL  -- first time you get a 1 sec delay to let the other instance of this SP finish
BEGIN
WAITFOR DELAY '00:00:01'
END
 
IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL  -- if the other instance of this SP is still running give a false positive
BEGIN
	SET @RowsFound = 0
	SET @TotalImnet = 0
    SET @StatusMsg =	Cast(@RowsFound as varchar(9)) + ' item(s) available to purge of at least ' + Cast(@TotalImnet as varchar(9))+ ' total item(s)'
	RETURN
END;

--create a global table to let everyone know the sp is running
CREATE TABLE ##EPRS_GetItem2Purge (xxx INT IDENTITY(1,1))

--Start of Query
BEGIN Transaction EPRS_GetItem2PurgeT00

    IF @Debug > 0
    BEGIN
	    SELECT 'Beginning transaction EPRS_GetItem2PurgeT00', getdate()
    END

    --This query will lock the table to ensure only one instance of the sp can run at one time
    --since the query is inside a transactions the lock will be held for the duration of the transaction.
    --SET @TotalImnet = (Select 1 AS [LockingTable] FROM Cabinet.dbo.Cab_Del_Items WITH (tablockx) WHERE 1 = 2)


    --how many imnets are in the table
    -- for performance reasons this is changed to check for existance
    IF EXISTS (select 1 from cabinet.dbo.CAB_DEL_ITEMS)
	BEGIN
		SET @TotalImnet =	1

		--Get the number of available imnets based on the days to keep
		-- for performance reasons this is changed to check for existance
		IF EXISTS(select 1 from CABINET.dbo.CAB_DEL_ITEMS 
		WHERE  
			(
--				DATEADD(day, @DaysToKeep, date_deleted) < GetDate()
				date_deleted < DATEADD(day, @DaysToKeep * -1, getdate())
			) 
		AND Imnet_ID NOT IN 
			(
				SELECT CDII_Imnet FROM CABINET.dbo.Cab_Del_Items_InUse 
			))	
		SET @RowsFound = 1
		ELSE
		SET @RowsFound = 0
	END
	ELSE
	BEGIN
		SET @TotalImnet = 0
		SET @RowsFound  = 0
	END
	
    	
    ----Set the status Msg
    SET @StatusMsg =	Cast(@RowsFound as varchar(9)) + ' item(s) available to purge of at least ' + Cast(@TotalImnet as varchar(9))+ ' total item(s)'

    IF @Debug > 0
	    BEGIN
		    SELECT 'DEBUG----->' AS 'Data Found', @StatusMsg AS 'StatusMsg', @RowsFound AS 'Available Imnet', @TotalImnet AS 'Total Imnet', GETDATE()
	    END

    IF @RowsFound = 0 -- we have nothing to purge
    BEGIN 	
        IF @Debug > 0
        BEGIN
	        SELECT 'There are no items to purge', getdate()
	        SELECT 'Completed Transaction EPRS_GetItem2PurgeT00', getdate()
        END

        COMMIT Transaction EPRS_GetItem2PurgeT00
		IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
			DROP TABLE ##EPRS_GetItem2Purge
		WAITFOR DELAY '00:00:01'
        RETURN
    END

	    --get an id number from the cab_del_items table
    Set @I = (select IDENT_CURRENT( 'cab_del_items_inuse' )) 

	IF @Debug > 0
	BEGIN
			SELECT 'Moving Imnet(s) into the CAB_DEL_ITEMS_INUSE table.', getdate()
	END
		
	    --move the records into the inuse table using the ident_current as the created by sequence	
	    INSERT INTO CABINET.dbo.Cab_Del_Items_InUse
	    (
	            [CDII_Created_Dt] ,[CDII_Created_By_Seq] ,[CDII_Imnet] ,[CDII_Agent] ,[CDII_Agent_Seq]
	    )
	            SELECT TOP 1000 -- Percent 
					Date_Deleted, @I, Imnet_ID,@Agent,@AgentID
	            FROM 
	                CABINET.dbo.CAB_DEL_ITEMS --WITH (INDEX = CDI_DATE)
		    WHERE  
			    (
--				    DATEADD(day, @DaysToKeep, date_deleted) < GetDate()
					date_deleted < DATEADD(day, @DaysToKeep * -1, getdate())
			    ) 
		    AND Imnet_ID NOT IN 
			    (
				    SELECT CDII_Imnet FROM CABINET.dbo.Cab_Del_Items_InUse 
			    )
--		    ORDER BY date_deleted ASC
		
	    IF @@error <> 0
	    BEGIN
		    Rollback Transaction EPRS_GetItem2PurgeT00
		    SET @RowsFound = 0
		    SET @StatusMSg = 'Error in Insert CAB_DEL_ITEMS_INUSE'
			IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
				DROP TABLE ##EPRS_GetItem2Purge
		    RETURN
	    END
    
		    
    	--check to see if any of the imnets are not marked deleted   
		IF EXISTS	(
			    Select 
			        1
			    FROM 
			        CABINET.dbo.Cab_Del_Items_InUse
			    INNER JOIN Cabinet.dbo.Pages WITH (NOLOCK)
			        on Cab_Del_Items_InUse.CDII_Imnet = Cabinet.dbo.Pages.Imnet
			        AND Cabinet.dbo.Pages.Deleted != 'Y'
			    WHERE [CDII_Created_By_Seq] = @I
			)
		BEGIN
			--process any records which are not marked deleted in the pages table
			    IF @Debug > 0
			    BEGIN
				    SELECT 'Checking for any IMNET which is not marked deleted in the pages table.', getdate()
			    END

			    IF @Debug > 0
			    BEGIN
				    SELECT 'This resultset will be inserted into the AUDIT_TRAIL table and removed from CAB_DEL_ITEMS.', getdate()
				    Select
			        'Purge'
			        , 'Purge'
			        , -1
			        , 'PF'
			        , 'The IMNET ID '
			        + RTRIM(Imnet_ID) 
			        + ' listed in CAB_DEL_ITEMS was not marked deleted in the Cabinet.dbo.Pages table.  The data FROM CAB_DEL_ITEMS is '
			        + ' DUID = ' + RTRIM(DUID) +', ' 
			        + 'Date_Deleted = ' + CAST(Date_Deleted as varchar(20)) + ', '
			        + 'Reason = ' + RTRIM(reason)
			        , getdate()
			        , 'E_P_R_S'
			    FROM cabinet.dbo.Cab_Del_Items_INUSE
			    INNER JOIN Cabinet.dbo.Cab_Del_Items
			        on Cab_Del_Items_InUse.CDII_Imnet = Cabinet.dbo.Cab_Del_Items.Imnet_ID
					AND Cab_Del_Items_InUse.CDII_Created_Dt = Cabinet.dbo.Cab_Del_Items.Date_Deleted
			    INNER JOIN Cabinet.dbo.Pages WITH (NOLOCK)
			        on Cab_Del_Items.Imnet_ID = Cabinet.dbo.Pages.Imnet
			        AND Cabinet.dbo.Pages.Deleted != 'Y'
			    WHERE [CDII_Created_By_Seq] = @I
			    END

			    Insert Audit.dbo.Audit_trail
				(ENCOUNTER, MRN, UserInstanceId, [ACTION], REMARK, OCCURRED, FACILITY)
			    Select
			        'Purge'
			        , 'Purge'
			        , -1
			        , 'PF'
			        , 'The IMNET ID '
			        + RTRIM(Imnet_ID) 
			        + ' listed in CAB_DEL_ITEMS was not marked deleted in the Cabinet.dbo.Pages table.  The data FROM CAB_DEL_ITEMS is '
			        + ' DUID = ' + RTRIM(DUID) +', ' 
			        + 'Date_Deleted = ' + CAST(Date_Deleted as varchar(20)) + ', '
			        + 'Reason = ' + RTRIM(reason)
			        , getdate()
			        , 'E_P_R_S'
			    FROM cabinet.dbo.Cab_Del_Items_INUSE
			    INNER JOIN Cabinet.dbo.Cab_Del_Items
			        on Cab_Del_Items_InUse.CDII_Imnet = Cabinet.dbo.Cab_Del_Items.Imnet_ID
					AND Cab_Del_Items_InUse.CDII_Created_Dt = Cabinet.dbo.Cab_Del_Items.Date_Deleted
			    INNER JOIN Cabinet.dbo.Pages WITH (NOLOCK)
			        on Cab_Del_Items.Imnet_ID = Cabinet.dbo.Pages.Imnet
			        AND Cabinet.dbo.Pages.Deleted != 'Y'
			    WHERE [CDII_Created_By_Seq] = @I
				    			
			    IF @@error <> 0
			    BEGIN
				    Rollback Transaction EPRS_GetItem2PurgeT00
				    SET @RowsFound = 0
				    SET @StatusMSg = 'Error in Insert Audit_Trail for any imnet which is not marked deleted in the Pages table'
		            IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
						DROP TABLE ##EPRS_GetItem2Purge
				    RETURN
			    END
		
			--remove non-deleted imnets from cab_del_items

			    IF @Debug > 0
			    BEGIN
				    SELECT 'Removing any IMNET which is not marked deleted in the pages table.', getdate()
			    END

				DELETE cabinet.dbo.CAB_DEL_ITEMS
			    FROM cabinet.dbo.Cab_Del_Items_INUSE
			    INNER JOIN Cabinet.dbo.Cab_Del_Items
			        on Cab_Del_Items_InUse.CDII_Imnet = Cabinet.dbo.Cab_Del_Items.Imnet_ID
					AND Cab_Del_Items_InUse.CDII_Created_Dt = Cabinet.dbo.Cab_Del_Items.Date_Deleted
			    INNER JOIN Cabinet.dbo.Pages WITH (NOLOCK)
			        on Cab_Del_Items.Imnet_ID = Cabinet.dbo.Pages.Imnet
			        AND Cabinet.dbo.Pages.Deleted != 'Y'
			    WHERE [CDII_Created_By_Seq] = @I

			    IF @@error <> 0
			    BEGIN
				    Rollback Transaction EPRS_GetItem2PurgeT00
				    SET @RowsFound = 0
				    SET @StatusMsg = 'Error in Removing any IMNET which is not marked deleted in the pages table.'
				    IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
						DROP TABLE ##EPRS_GetItem2Purge
					RETURN
			    END
            
            -- CAB_DEL_ITEMS_INUSE rows must also be removed --
			
				DELETE FROM cabinet.dbo.Cab_Del_Items_InUse WHERE [CDII_Created_By_Seq] = @I

			    IF @@error <> 0
			    BEGIN
				    Rollback Transaction EPRS_GetItem2PurgeT00
				    SET @RowsFound = 0
				    SET @StatusMsg = 'Error in Remove CAB_DEL_ITEMS_INUSE rows'
					IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
						DROP TABLE ##EPRS_GetItem2Purge
				    RETURN
			    END
            
            SET @RowsFound = 1
            SET @StatusMsg = 'Imnet(s) were found in Cab_Del_Items which were not marked DELETED in the pages table.  Check Audit trail!'

            COMMIT TRANSACTION EPRS_GetItem2PurgeT00
            IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
				DROP TABLE ##EPRS_GetItem2Purge
			RETURN
		 END
	

--Return the list of Imnets to the calling program

IF @Debug > 0
    BEGIN
	SELECT 'This data will be sent to Purge agent ' + Cast(@AgentID AS varchar(6))+ ' at '+ RTRIM(@Agent), getdate()
    END

SELECT     
	    CAB_DEL_ITEMS.Imnet_ID
	    , CAB_DEL_ITEMS.DUID
	    , Encounter as [ENCOUNTER]
	    , Facility as [FACILITY]
	    , MRN as [MRN]
	    , CAB_DEL_ITEMS.Reason
	    , CAB_DEL_ITEMS.Date_Deleted
    FROM         
	    CABINET.dbo.CAB_DEL_ITEMS_INUSE 
		    INNER JOIN CABINET.dbo.Cab_Del_Items ON CAB_DEL_ITEMS.Imnet_ID = Cab_Del_Items_InUse.CDII_Imnet 
		    LEFT OUTER JOIN CABINET.dbo.Cabinet_Version WITH (NOLOCK) ON CAB_DEL_ITEMS_INUSE.CDII_Imnet = Cabinet_Version.Imnet
    WHERE 
	    [CDII_Created_By_Seq] = @I
--	ORDER BY date_deleted ASC
	SET @StatusMsg =	Cast(@RowsFound as varchar(9)) + ' item(s) available to purge of ' + Cast(@TotalImnet as varchar(9))+ ' total item(s)'
	SET @RowsFound = 1

COMMIT Transaction EPRS_GetItem2PurgeT00
IF OBJECT_ID('tempdb..##EPRS_GetItem2Purge') IS NOT NULL
	DROP TABLE ##EPRS_GetItem2Purge

IF @Debug > 0
BEGIN
	SELECT 'Completed Transaction EPRS_GetItem2PurgeT00', getdate()
END

RETURN
END
GO

GRANT EXECUTE ON [dbo].[EPRS_GetItem2Purge] TO [IMNET] AS [dbo]
GO
