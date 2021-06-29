if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROI_UpdateImnetInRequestMain]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[ROI_UpdateImnetInRequestMain]
GO

SET QUOTED_IDENTIFIER ON 
GO
SET ANSI_NULLS ON 
GO



/***************************************************************************************************************
* Stored Procedure: ROI_UpdateImnetInRequestMain
* Creation Date		: 11/10/2007
* Copyright		: COPYRIGHT MCKESSON 2007
*			: ALL RIGHTS RESERVED
*
* 			: The copyright to the computer program(s) herein
* 			: is the property of McKessonHBOC. The program(s)
* 			: may be used and/or copied only with the written
* 			: permission of McKessonHBOC or in accordance with
* 			: the terms and conditions stipulated in the     
* 			: agreement/contract under which the program(s)
* 			: have been supplied.
*					
* Written by    : RC
*									
* Purpose		: This stored procedure updates ROI_RequestMain.RequestXML for changes in Imnet id 
*		  			
* Database		:CABINET
*
*									
* Input Parameters:	
*               Name		Type		Description
*       		---------	-------		----------------
*               @oldImnet   varchar(22) the origional imnet
*               @newImnet   varchar(22) the updated imnet
*
* Output Parameters	:  None
*			
*									
* Return Status : 0=failure or 1=success		
*									
* Usage			: ROI_UpdateImnetInRequestMain @OldImnet @NewImnet
*																
* Called By		: DAL_Archive
*									
* Calls			: nothing
*			
*									
* Data Modifications	:ROI_RequestMain.RequestXML			
*									
* Updates		: 
*               Name		Date		Purpose
*               ---------	-------		------------- 						
*               RC          2009/12/15  Created
*				RC          2009/12/19  changed xml query to use "contains" keyword 				
*								 
***************************************************************************************************************/
CREATE PROCEDURE dbo.ROI_UpdateImnetInRequestMain
@OldImnet   varchar(22),
@NewImnet   varchar(22),
@Debug      int  = 0

AS

--stored proc logic
/*
retrieve the enconter from the documents table
inspect the ROI_SearchLOV table to see if the encounter is on a request
If the encounter exists put them in a memory table
update the ROI_RequestMain.RequestXML 
Return

*/
--

SET NOCOUNT ON

BEGIN TRY	--try to update the XML


BEGIN TRANSACTION UIIRMT00  -- Start the outer transaction


DECLARE     @l_OldImnet         varchar(16)
DECLARE     @l_NewImnet         varchar(21)

--declare a memory table to hold the list of requests for the mrn containing the @l_OldImnet
DECLARE     @lt_RequestMain_Seq  table
            ( 
                lt_seq int identity (1,1),
                lt_RM_Seq int
            )

--assign the local variables to the input variables
SELECT @l_OldImnet = @OldImnet, @l_NewImnet = @NewImnet

IF @Debug = 1
BEGIN
    SELECT @OldImnet AS 'Old Imnet', @NewImnet AS 'NewImnet', @l_oldImnet AS 'local old imnet', @l_NewImnet AS 'local new imnet'
END

--Make sure the data passed in is not NULL or zero length
If @l_OldImnet + @l_NewImnet is NULL or Len(@l_OldImnet) = 0 OR Len(@l_NewImnet) = 0
BEGIN
    IF @Debug = 1
    BEGIN
        PRINT 'bad input data'
        SELECT @OldImnet AS 'Old Imnet', @NewImnet AS 'NewImnet'
    END
    COMMIT TRANSACTION UIIRMT00
    RETURN
END
ELSE
BEGIN
    INSERT @lt_RequestMain_Seq  (lt_RM_Seq)
        SELECT --select the RequestMain_Seq having the mrn of the imnet    
            child 
        FROM 
            roi_searchlov 
        WHERE 
            item = 'patient.mrn' 
        AND 
            value =
                (
                    SELECT  --Retrieve the mrn(s) from the documents table
                       Documents.MRN
                    FROM
                        Pages 
                    INNER JOIN
                        Versions ON Pages.version_id = Versions.Version_ID 
                    INNER JOIN
                        Documents ON Versions.DOC_ID = Documents.DOC_ID
                    WHERE
                        imnet = @OldImnet
                )

    IF @Debug = 1
    BEGIN
        SELECT @l_OldImnet AS 'Old Imnet', @l_NewImnet AS 'NewImnet'
        SELECT * FROM @lt_RequestMain_Seq
    END
    
    --if the table does not have any data there is nothing to do
    IF EXISTS (Select 1 FROM @lt_RequestMain_Seq)
    BEGIN  --there are request with the patinet for this imnet
        
        DECLARE     @l_counter00        int
        DECLARE     @l_reqSeq           int

        SELECT @l_counter00 = MAX(lt_seq) FROM @lt_RequestMain_Seq
        WHILE @l_counter00 > 0
        BEGIN
            SELECT @l_reqSeq = lt_RM_Seq FROM  @lt_RequestMain_Seq WHERE lt_seq =  @l_counter00
            IF @Debug = 1
            BEGIN
                SELECT @l_OldImnet AS 'Old Imnet', @l_NewImnet AS 'NewImnet', @l_counter00 AS 'local counter 00', @l_reqSeq AS 'request sequence'
            END
            --authdoc
            UPDATE ROI_RequestMain
            SET RequestMainXML.modify('replace value of (//@Authdoc[contains(., sql:variable("@l_OldImnet"))])[1]  with sql:variable("@l_NewImnet")')
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            AND RequestMainXML.exist('/request/@Authdoc[contains(., sql:variable("@l_OldImnet"))]') = 1
            --global request main
            UPDATE ROI_RequestMain
            SET RequestMainXML.modify('replace value of (//item/patient/global-document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))])[1] with sql:variable("@l_NewImnet")')
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            AND RequestMainXML.exist('//item/patient/global-document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))]') = 1
            --global request delivery
            UPDATE ROI_RequestDelivery
            SET RequestDeliveryXML.modify('replace value of (//item/patient/global-document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))])[1] with sql:variable("@l_NewImnet")')
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            AND RequestDeliveryXML.exist('//item/patient/global-document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))]') = 1
            --non global request main
            UPDATE ROI_RequestMain
            SET RequestMainXML.modify('replace value of (//item/patient/encounter/document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))])[1] [1] with sql:variable("@l_NewImnet")')
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            AND RequestMainXML.exist('//item/patient/encounter/document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))]') = 1
            --non global request delivery
            UPDATE ROI_RequestDelivery
            SET RequestDeliveryXML.modify('replace value of (//item/patient/encounter/document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))])[1] [1] with sql:variable("@l_NewImnet")')
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            AND RequestDeliveryXML.exist('//item/patient/encounter/document/version/page/@imnet-id[contains(., sql:variable("@l_OldImnet"))]') = 1
            --update the record version 
            UPDATE ROI_RequestMain
            SET Record_Version = Record_Version + 1
            WHERE ROI_RequestMain_Seq = @l_reqSeq
            UPDATE ROI_RequestDelivery
            SET Record_Version = Record_Version + 1
            WHERE ROI_RequestMain_Seq =@l_reqSeq
            
            SET @l_counter00 = @l_counter00 - 1
            IF @Debug = 1
            BEGIN
                SELECT @l_OldImnet AS 'Old Imnet', @l_NewImnet AS 'NewImnet', @l_counter00 AS 'local counter 00'
            END
        END
    END
    ELSE
    BEGIN
        IF @Debug = 1
        BEGIN
            SELECT @l_OldImnet AS 'Old Imnet', @l_NewImnet AS 'NewImnet' , 'No data to process'
        END
    END
END

COMMIT TRANSACTION ASCIERL4AST00
END TRY


BEGIN CATCH  -- There was an error
	IF @@TRANCOUNT > 0
	BEGIN
		ROLLBACK TRANSACTION UIIRMT00;  --clean up the open transaction
	END
	
	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;

	SELECT 
		@ErrorMessage = ERROR_MESSAGE()
		,@ErrorSeverity = ERROR_SEVERITY()
		,@ErrorState = ERROR_STATE();

		-- Use RAISERROR inside the CATCH block to return error
		-- information about the original error that caused
		-- execution to jump to the CATCH block.
	RAISERROR 
		(
			@ErrorMessage -- Message text.
			,@ErrorSeverity -- Severity.
			,@ErrorState -- State.
		);
END CATCH;

GO
SET QUOTED_IDENTIFIER OFF 
GO
SET ANSI_NULLS ON 
GO

GRANT  EXECUTE  ON [dbo].[ROI_UpdateImnetInRequestMain]  TO [IMNET]
GO
