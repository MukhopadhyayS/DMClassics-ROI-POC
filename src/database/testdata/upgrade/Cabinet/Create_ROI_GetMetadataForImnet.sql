--/****** Object:  StoredProcedure [dbo].[WFE_ROI_Notification_Of_Automated_Request]    Script Date: 07/30/2008 15:15:49 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_GetMetadataForImnet]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_GetMetadataForImnet]
/****** Object:  StoredProcedure [dbo].[ROI_GetMetadataForImnet]    Script Date: 07/30/2008 15:15:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/***************************************************************************************************************
* Stored Procedure: [ROI_GetMetadataForImnet]
* Creation Date		: 10/06/2008
* Copyright			: COPYRIGHT MCKESSON 2008
*					: ALL RIGHTS RESERVED
*
* 					: The copyright to the computer program(s) herein
* 					: is the property of McKesson. The program(s)
* 					: may be used and/or copied only with the written
* 					: permission of McKesson or in accordance with
* 					: the terms and conditions stipulated in the     
* 					: agreement/contract under which the program(s)
* 					: have been supplied.
*					
* Written by:	RC	
*									
* Purpose:	Return a list of invalid Imnets and Metadata for valid Imnets
*				
* Database:	CABINET
*									
* Input Parameters	: 
*	Name				Type		Description
* 	---------			-------		----------------
*	@ListOfImnets		xml			source list of Imnets to be validated
*	@ListOfInvalidImnets xml		list of invalid Imnets
*	@Metadata			xml			Metadata for the valid Imnets
*	@Queue_Id			smallint	unique identifier for a queue in Workflow Not used in this script
*	@Queue_Name			char(50))	common name for the queue in Workflow
*									
* Output Parameters	: 
*	Name				Type		Description
* 	---------			-------		----------------
*	@ListOfImnets xml,
*	@ListOfInvalidImnets xml output,
*	@MetaData xml output
*									
* Return Status		: Try catch block will return any error code	
*									
* Called By			: ROI
*									
* Calls				: NA
*			
* Data Modifications: None
*									
* Updates			: 
*	Name				Date		Purpose
*	---------			-------		------------- 						
*			
***************************************************************************************************************************************/
Create PROCEDURE [dbo].[ROI_GetMetadataForImnet]

	@ListOfImnets xml,
	@ListOfInvalidImnets xml output,
	@Metadata xml output

AS

BEGIN TRY	--Start the error handeling

--The code below is used for testing
--DECLARE @RC int
--DECLARE @ListOfImnets xml
--DECLARE @ListOfInvalidImnets xml
--DECLARE @Metadata xml
--
--SET @ListOfImnets = '<ContentList>
--<Imnet ID = "IMAGES1   00917700001         "/>
--<Imnet ID = "IMAGES1   00917800001         "/>
--<Imnet ID = "IMAGES1   00917900001         "/>
--<Imnet ID = "IMAGES1   00918000001         "/>
--<Imnet ID = "IMAGES1   00918100001         "/>
--<Imnet ID = "IMAGES1   00918200001x         "/>
--<Imnet ID = "IMAGES1   00918300001x         "/>
--<Imnet ID = "IMAGES1   00918400001x         "/>
--<Imnet ID = "IMAGES1   07098900001x         "/>
--<Imnet ID = "IMAGES1   07099300001x         "/>
--</ContentList>'
--
--
--
--EXECUTE @RC = [cabinet].[dbo].[ROI_GetMetadataForImnet] 
--   @ListOfImnets
--  ,@ListOfInvalidImnets OUTPUT
--  ,@Metadata OUTPUT
--The code above is used for testing

--Create a memory table to hold  Imnets
Declare @ContentList table
	(
	ContentID varchar(40)
	)
--Fill the table with  Imnets
Insert 
	@ContentList (ContentID)
SELECT
	Table1.Column1.value('@ID','VARCHAR(40)')
FROM
	@ListOfImnets.nodes('/ContentList/Imnet') AS Table1(Column1)
--select * from @ContentList  --uncomment for testing only

--set the output variable to include all invalid Imnets
Set @ListOfInvalidImnets =
(
	Select ContentID as "Imnet" 
	FROM @ContentList
	Where ContentID not in 
	(
		Select Imnet from Cabinet.dbo.Pages
		Where Deleted = 'N'
	)
	FOR XML Path('HPF'), Root('InvalidContent') 
)
--Return the MetaData
Set @MetaData =
(
	Select 
		[his].[dbo].[PATIENTS].[MRN]
		, [his].[dbo].[PATIENTS].[OLD_MRN]
		, [his].[dbo].[PATIENTS].[NAME]
		, [his].[dbo].[PATIENTS].[SSN]
		, [his].[dbo].[PATIENTS].[ADDRESS]
		, [his].[dbo].[PATIENTS].[CITY]
		, [his].[dbo].[PATIENTS].[STATE]
		, [his].[dbo].[PATIENTS].[ZIP]
		, [his].[dbo].[PATIENTS].[HM_PHONE]
		, [his].[dbo].[PATIENTS].[WK_PHONE]
		, [his].[dbo].[PATIENTS].[SEX]
		, [his].[dbo].[PATIENTS].[DOB]
		, [his].[dbo].[PATIENTS].[AGE]
		, [his].[dbo].[PATIENTS].[RACE]
		, [his].[dbo].[PATIENTS].[MARITAL]
		, [his].[dbo].[PATIENTS].[EMPLOYER]
		, [his].[dbo].[PATIENTS].[SMOKER]
		, [his].[dbo].[PATIENTS].[RELIGION]
		, [his].[dbo].[PATIENTS].[CHURCH]
		, [his].[dbo].[PATIENTS].[EMERGENCY_CONTACT]
		, [his].[dbo].[PATIENTS].[BIRTH_PLACE]
		, [his].[dbo].[PATIENTS].[LOCKOUT]
		, [his].[dbo].[PATIENTS].[REMARK]
		, [his].[dbo].[PATIENTS].[FACILITY]
		, [his].[dbo].[PATIENTS].[LNSSN]
		, [his].[dbo].[PATIENTS].[GPI]
		, [his].[dbo].[PATIENTS].[ENTERPRISE_PI]
		, [his].[dbo].[PATIENTS].[ENTERPRISE_AI]
		, [his].[dbo].[PATIENTS].[ADDRESS2]
		, [his].[dbo].[PATIENTS].[ADDRESS3]
		, [his].[dbo].[PATIENTS].[DOMAIN_ID]
		, [his].[dbo].[PATIENTS].[EPN] 
		, [his].[dbo].[ENCOUNTERS].[ENCOUNTER]
		, [his].[dbo].[ENCOUNTERS].[MRN]
		, [his].[dbo].[ENCOUNTERS].[NO_PUB]
		, [his].[dbo].[ENCOUNTERS].[ADMITTED]
		, [his].[dbo].[ENCOUNTERS].[DISCHARGED]
		, [his].[dbo].[ENCOUNTERS].[FACILITY]
		, [his].[dbo].[ENCOUNTERS].[CLINIC]
		, [his].[dbo].[ENCOUNTERS].[PT_TYPE]
		, [his].[dbo].[ENCOUNTERS].[SERVICE]
		, [his].[dbo].[ENCOUNTERS].[PAYOR_CODE]
		, [his].[dbo].[ENCOUNTERS].[FIN_CLASS]
		, [his].[dbo].[ENCOUNTERS].[PRIM_PLAN]
		, [his].[dbo].[ENCOUNTERS].[SEC_PLAN]
		, [his].[dbo].[ENCOUNTERS].[ADMIT_SOURCE]
		, [his].[dbo].[ENCOUNTERS].[TOTAL_CHARGES]
		, [his].[dbo].[ENCOUNTERS].[BALANCE_CHARGES]
		, [his].[dbo].[ENCOUNTERS].[LOCKOUT]
		, [his].[dbo].[ENCOUNTERS].[REMARK]
		, [his].[dbo].[ENCOUNTERS].[DISPOSITION]
		, [his].[dbo].[ENCOUNTERS].[ENC_OPT_OUT]
		, [his].[dbo].[ENCOUNTERS].[VIP]
		, [cabinet].[dbo].[ROI_ContentMetaData].[FACILITY]
		, [cabinet].[dbo].[ROI_ContentMetaData].[MRN]
		, [cabinet].[dbo].[ROI_ContentMetaData].[ENCOUNTER]
		, [cabinet].[dbo].[ROI_ContentMetaData].[DOC_NAME]
		, [cabinet].[dbo].[ROI_ContentMetaData].[ChartOrder]
		, [cabinet].[dbo].[ROI_ContentMetaData].[SUBTITLE]
		, [cabinet].[dbo].[ROI_ContentMetaData].[DATETIME]
		, [cabinet].[dbo].[ROI_ContentMetaData].[DUID]
		, [cabinet].[dbo].[ROI_ContentMetaData].[VersionNumber]
		, [cabinet].[dbo].[ROI_ContentMetaData].[imnet]
		, [cabinet].[dbo].[ROI_ContentMetaData].[imagedate]
		, [cabinet].[dbo].[ROI_ContentMetaData].[page]
		, [cabinet].[dbo].[ROI_ContentMetaData].[filename]
		, [cabinet].[dbo].[ROI_ContentMetaData].[archived]
		, [cabinet].[dbo].[ROI_ContentMetaData].[deleted]
		, [cabinet].[dbo].[ROI_ContentMetaData].[ContentCount]
		, [cabinet].[dbo].[ROI_ContentMetaData].[DeficencyCount]	
	from his.dbo.patients

	Inner join his.dbo.encounters
	ON his.dbo.patients.facility = his.dbo.encounters.facility
		AND his.dbo.patients.mrn = his.dbo.encounters.mrn

	Inner join Cabinet.dbo.ROI_ContentMetaData
	ON his.dbo.encounters.facility = Cabinet.dbo.ROI_ContentMetaData.facility
		AND his.dbo.encounters.encounter = Cabinet.dbo.ROI_ContentMetaData.encounter

	Where Imnet in 
	(
--		Select ContentID as 'Imnet' 
--		from @ContentList
	Select ContentID as "Imnet" 
	FROM @ContentList
	Where ContentID not in 
	(
		Select Imnet from Cabinet.dbo.Pages
		Where Deleted = 'Y'
	)

	)
	FOR XML AUTO, ROOT('MetaData')
)

END TRY
BEGIN CATCH  -- There was an error

	DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;

	SELECT 
		@ErrorMessage = ERROR_MESSAGE()
		,@ErrorSeverity = ERROR_SEVERITY()
		,@ErrorState = ERROR_STATE();

		 --Use RAISERROR inside the CATCH block to return error
		 --information about the original error that caused
		 --execution to jump to the CATCH block.
	RAISERROR 
		(
			@ErrorMessage -- Message text.
			,@ErrorSeverity -- Severity.
			,@ErrorState -- State.
		);
	RETURN
END CATCH;

--Return the data
Select 
	@ListOfImnets
,	@ListOfInvalidImnets
,	@MetaData
--End of sp
 
GO
GRANT EXECUTE ON [dbo].[ROI_GetMetadataForImnet] TO [IMNET]