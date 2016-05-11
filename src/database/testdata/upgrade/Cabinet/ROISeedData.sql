/*************************************************************************************************************************************
*
* Creation Date		:
* Copyright		: COPYRIGHT MCKESSON 2008
*			: ALL RIGHTS RESERVED
*
* 			: The copyright to the computer program(s) herein
* 			: is the property of McKesson. The program(s)
* 			: may be used and/or copied only with the written
* 			: permission of McKesson or in accordance with
* 			: the terms and conditions stipulated in the
* 			: agreement/contract under which the program(s)
* 			: have been supplied.
*
* Written by		: Joseph Chen / RC
*					For ROI
*
* Database		:  Cabinet
*
* Tables		: ROI tables
* Output Parameters	: <none>
*
* Return Status		: <none>
*

*
*
* Data Modifications	: <none>
*
* Updates		:
* 			Name			Date		Purpose
*			---------		-------		-------------
*			Joseph Chen	4/2/2008	ROI_MediaType seed data
*			RC			4/14/08		Updated to include all seed data
*           RC          4/15/08     Corrected order of deletetion of seed data
*			RC			5/02/08		Added seed data for billing tier detail
*			RC			6/4/08		Added example data for letter template files
*           OFS         9/16/08     Added Seed data for SSN Mask (Currently this is added in ROI_Reason which is used as ADMIN LoV Table)
*           OFS         9/30/08     Added Domain types and Domain Sources to ROI_Parameter
*           OFS         10/15/08    Added the 'default' contact type and removed the Domain types and Domain Sources that were added to the  ROI_Parameter
*			OFS			3/27/09     Removed the seed data from ROI_SearchLov for RequestorType To BillingTier association and
*									Added seed data in table ROI_RequestorTypeToBillingTier
*           OFS         3/30/09     Added supplemental column in the ROI_RequestorTypeToBillingTier
*			OFS			6/07/09	    Added seed data for ROI_RequestMain table for Auth-Received request
*
*************************************************************************************************************************************/
/* Tepmplate for seed data
--TableNameHere
set identity_insert TableNameHere on
go
Delete from TableNameHere where TableNameHere_Seq <0


insert into TableNameHere
	(

	)
Values
	(

	)

SET NOCOUNT off

set identity_insert TableNameHere off
go
*/


--Remove old seed data
Delete from ROI_LetterTemplate where ROI_LetterTemplate_Seq <0
Delete from ROI_LetterTemplateFile
Delete from ROI_RequestorTypeToBillingTier where ROI_RequestorTypeToBillingTier_Seq <0
Delete from ROI_RequestorType where ROI_RequestorType_Seq <0
Delete from ROI_BillingTierDetail where ROI_BillingTierDetail_Seq <0
Delete from ROI_BillingTier where ROI_BillingTier_Seq <0
Delete from ROI_MediaType where ROI_MediaType_Seq <0
Delete from ROI_PaymentMethod
Delete from ROI_Weight where ROI_Weight_Seq <0
Delete from ROI_DeliveryMethod
Delete from ROI_LetterTemplate where ROI_LetterTemplate_Seq <0
Delete from ROI_EmailPhoneType where ROI_EmailPhoneType_Seq <0
Delete from ROI_Reason where ROI_Reason_Seq <0
Delete from ROI_ContactType where ROI_ContactType_Seq <0
Delete from ROI_RequestMain where ROI_RequestMain_Seq <0

--make sure identity is off
set identity_insert ROI_BillingTierDetail off
set identity_insert ROI_BillingTier off
set identity_insert ROI_MediaType off
set identity_insert ROI_PaymentMethod off
set identity_insert ROI_Weight off
set identity_insert ROI_RequestorType off
set identity_insert ROI_DeliveryMethod off
set identity_insert ROI_LetterTemplate off
set identity_insert ROI_EmailPhoneType off
set identity_insert ROI_Reason off
set identity_insert ROI_ContactType off
set identity_insert ROI_RequestorTypeToBillingTier off
set identity_insert ROI_RequestMain off

--Remove unwanted security rights
DELETE FROM
	Cabinet.dbo.Security_Rights
WHERE
	Security_ID IN
	(
		4070
		,4075
		,4080
		,4085
		,4090
		,4095
		,4100
		,4105
		,4110
		,4115
		,4120
		,4125
	)


--ROI_EmailPhoneType
set identity_insert ROI_EmailPhoneType on
go

insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-1
	,'Default'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-2
	,'Home'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-3
	,'Work'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-4
	,'Cell'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-5
	,'PrimaryEmail'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-6
	,'OtherEmail'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-7
	,'MainFax'
	)
insert into ROI_EmailPhoneType
	(
	ROI_EmailPhoneType_Seq
	,[Name]
	)
Values
	(
	-8
	,'AltFax'
	)

set identity_insert ROI_EmailPhoneType off
go
SET NOCOUNT off


SET NOCOUNT ON
--ROI_Mediatype
set identity_insert ROI_MediaType on
go

insert into ROI_MediaType
	(
		ROI_MediaType_Seq
		,[Name]
		,[Description]
		,Active
	)
Values
	(
	-1
		,'Electronic'
		,'The Electronic media type represents documents that reside in MPF.'
		,1
	)

insert into ROI_MediaType
	(
		ROI_MediaType_Seq
		,[Name]
		,[Description]
		,Active
	)
Values
	(
		 -2
		,'Non-MPF'
		,'The Non-MPF media type represents documents that do not reside in MPF, or verbal disclosures.'
		,1
	)

set identity_insert ROI_MediaType off
SET NOCOUNT off

go
--ROI_BillingTier
set identity_insert ROI_BillingTier on
go

insert into ROI_BillingTier
	(
		ROI_BillingTier_Seq
		,ROI_Mediatype_Seq
		,Active
		,FlatCharge
		,DefaultPageCharge
		,[Name]
	,Description
	)
Values
	(
		-1
		,-1
		,1
		,0
		,0
		,'Electronic'
		,'The electronic billing tier represents the billing tier structure for documents that reside in MPF.  This billing tier is system-dependent and cannot be renamed, or deleted.'
	)

insert into ROI_BillingTier
	(
		ROI_BillingTier_Seq
		,ROI_Mediatype_Seq
		,Active
		,FlatCharge
		,DefaultPageCharge
		,[Name]
		,Description
	)
Values
	(
		-2
		,-2
		,1
		,0
		,0
		,'Non-MPF'
		,'The non-MPF billing tier represents the billing tier structure for documents that do not reside in MPF, or for verbal disclosures.  This billing tier is system-dependent and cannot be renamed, made inactive, or deleted.'
	)

set identity_insert ROI_BillingTier off
SET NOCOUNT off
GO

--ROI_BillingTierDetail
set identity_insert ROI_BillingTierDetail on
go


INSERT INTO [dbo].[ROI_BillingTierDetail]
           ([ROI_BillingTierDetail_Seq]
		   ,[ROI_BillingTier_Seq]
           ,[Page]
           ,[PageCharge])
     VALUES
           (-1
		   ,-1
           ,1
           ,0)
INSERT INTO [dbo].[ROI_BillingTierDetail]
           ([ROI_BillingTierDetail_Seq]
		   ,[ROI_BillingTier_Seq]
           ,[Page]
           ,[PageCharge])
     VALUES
           (-2
		   ,-2
           ,1
           ,0)
set identity_insert ROI_BillingTierDetail off
SET NOCOUNT off
GO


--ROI_PaymentMethod
set identity_insert ROI_PaymentMethod on
go

insert into ROI_PaymentMethod
	(
		ROI_PaymentMethod_Seq
		,Active
		,Display
		,[Name]
		,Description
	)
Values
	(
		1
		,1
		,1
		,'Credit Card'
		,'Credit Card'
	)

insert into ROI_PaymentMethod
	(
		ROI_PaymentMethod_Seq
		,Active
		,Display
		,[Name]
		,Description
	)
Values
	(
		2
		,1
		,1
		,'Check'
		,'Check'
	)

SET NOCOUNT off

set identity_insert ROI_PaymentMethod off
go


--ROI_Weight
set identity_insert ROI_Weight on
go
insert into ROI_Weight
	(
		ROI_Weight_Seq
		,Active
		,UnitPerMeasure
		,[Name]
		,Unit
		,Measure
	)
Values
	(
-1
,1
,6
,'Paper'
,'Page'
,'Ounce'
	)

SET NOCOUNT off

set identity_insert ROI_Weight off
go


--ROI_RequestorType
set identity_insert ROI_RequestorType on
go

insert into ROI_RequestorType
	(
		ROI_RequestorType_Seq
		,RecordView
		,Active
		,[Name]
	)
Values
	(
		-1
		,'not defined'
		,1
		,'Patient'
	)

SET NOCOUNT off

set identity_insert ROI_RequestorType off
go



--ROI_DeliveryMethod
set identity_insert ROI_DeliveryMethod on
go

insert into ROI_DeliveryMethod
	(
ROI_DeliveryMethod_Seq
,Active
,IsDefault
,[Name]
,Description
,URL
	)
Values
	(
1
,1
,1
,'USPS'
,'United States Postal Service'
,'http://www.usps.com/'
	)

SET NOCOUNT off

set identity_insert ROI_DeliveryMethod off
go


--ROI_LetterTemplateFile
Delete from ROI_LetterTemplateFile
INSERT INTO     ROI_LetterTemplateFile
	(
	[Name]
	,Source)
	SELECT
	'Invoice'
	,BulkColumn FROM Openrowset
	(
     Bulk 'C:\imnet\hpf_13.5\Database\Cabinet\InvoiceLetterTemplate.rtf'
	,SINGLE_BLOB
	) AS blob

INSERT INTO     ROI_LetterTemplateFile
	(
	[Name]
	,Source)
	SELECT
	'Prebill'
	,BulkColumn FROM Openrowset
	(
	Bulk 'C:\imnet\hpf_13.5\Database\Cabinet\PrebillLetterTemplate.rtf'
	,SINGLE_BLOB
	) AS blob

INSERT INTO     ROI_LetterTemplateFile
	(
	[Name]
	,Source)
	SELECT
	'Other'
	,BulkColumn FROM Openrowset
	(
	Bulk 'C:\imnet\hpf_13.5\Database\Cabinet\OtherLetterTemplate.rtf'
	,SINGLE_BLOB
	) AS blob

INSERT INTO     ROI_LetterTemplateFile
	(
	[Name]
	,Source)
	SELECT
	'Cover Letter'
	,BulkColumn FROM Openrowset
	(
	Bulk 'C:\imnet\hpf_13.5\Database\Cabinet\CoverLetterTemplate.rtf'
	,SINGLE_BLOB
	) AS blob




Delete from ROI_LetterTemplate
GO
--ROI_LetterTemplate
set identity_insert ROI_LetterTemplate on
go

insert into ROI_LetterTemplate
	(
ROI_LetterTemplate_Seq
,ROI_LetterTemplateFile_Seq
,Active
,IsDefault
,HasNotes
,[Name]
,Description
,[Type]
	)
Select
-1
,(Select ROI_LetterTemplateFile_Seq FROM ROI_LetterTemplateFile where name = 'Invoice')
,1
,1
,1
,'Invoice'
,'The invoice letter type can be used for associating invoice letter templates.'
,'Invoice'


insert into ROI_LetterTemplate
	(
ROI_LetterTemplate_Seq
,ROI_LetterTemplateFile_Seq
,Active
,IsDefault
,HasNotes
,[Name]
,Description
,[Type]
	)
Select
-2
,(Select ROI_LetterTemplateFile_Seq FROM ROI_LetterTemplateFile where name = 'Prebill')
,1
,1
,1
,'Prebill'
,'The prebill letter type can be used for associating prebill letter templates.'
,'Prebill'


insert into ROI_LetterTemplate
	(
ROI_LetterTemplate_Seq
,ROI_LetterTemplateFile_Seq
,Active
,IsDefault
,HasNotes
,[Name]
,Description
,[Type]
	)
Select
-3
,(Select ROI_LetterTemplateFile_Seq FROM ROI_LetterTemplateFile where name = 'Cover Letter')
,1
,1
,0
,'Cover Letter'
,'The cover letter type can be used for associating cover letter templates.'
,'CoverLetter'


insert into ROI_LetterTemplate
	(
ROI_LetterTemplate_Seq
,ROI_LetterTemplateFile_Seq
,Active
,IsDefault
,HasNotes
,[Name]
,Description
,[Type]
	)
Select
-4
,(Select ROI_LetterTemplateFile_Seq FROM ROI_LetterTemplateFile where name = 'Other')
,1
,0
,1
,'Other'
,'The other letter type can be used for associating status letter templates.Ex:Denial letter, Pend letter,etc.'
,'Other'


SET NOCOUNT off

set identity_insert ROI_LetterTemplate off
go
--Insert the data
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Default'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Home'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Office'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Shipping'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Billing'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Main'
	)
INSERT INTO
	[ROI_AddressType]
	(
		[Name]
	)
Values
	(
		'Alternate'
	)


set identity_insert [ROI_Parameter] on
go
Delete from [ROI_Parameter] where [ROI_Parameter_Seq] <0

INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ItemName]
			)
     VALUES
           (-1
			,'Status Code'
			)

INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-2
           ,-1
           ,'Auth Received'
           ,1
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-3
           ,-1
           ,'Logged'
           ,2
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-4
           ,-1
           ,'Completed'
           ,3
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-5
           ,-1
           ,'Denied'
           ,4
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-6
           ,-1
           ,'Canceled'
           ,5
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-7
           ,-1
           ,'Pended'
           ,6
			)
INSERT INTO [dbo].[ROI_Parameter]
           ([ROI_Parameter_Seq]
           ,[ParentItem_Seq]
           ,[ItemName]
           ,[ItemValue]
			)
     VALUES
           (-8
           ,-1
           ,'Pre-billed'
           ,7
			)

SET NOCOUNT off

set identity_insert [ROI_Parameter] off
go

--ROI_REASON
SET NOCOUNT ON
set identity_insert ROI_REASON on
go
INSERT INTO [dbo].[ROI_REASON]
            (Roi_Reason_Seq,
            Created_Dt,
            Created_By_Seq,
            Modified_Dt,
            Modified_By_Seq,
            Record_Version,
            Organization_Seq,
            Active,
            TPO,
            NonTPO,
            Type,
            Name,
            Description,
            DisplayText,
            Status)
        VALUES
            (-1
            ,getDate()
            ,1
            ,getDate()
            ,1
            ,0
            ,1
            ,1
            ,1
            ,0
            ,'ssnmask'
            ,'ssnmask'
            ,'0'
            ,'1'
            ,0)
--this reason will be used for migrated data
INSERT INTO [dbo].[ROI_REASON]
            (Roi_Reason_Seq,
            Created_Dt,
            Created_By_Seq,
            Modified_Dt,
            Modified_By_Seq,
            Record_Version,
            Organization_Seq,
            Active,
            TPO,
            NonTPO,
            Type,
            Name,
            DisplayText,
            Status)
        VALUES
            (-2
            ,getDate()
            ,1
            ,getDate()
            ,1
            ,0
            ,0
            ,0
            ,0
            ,0
            ,'request'
            ,'Migration'
            ,'Migration'
            ,0)

INSERT INTO [dbo].[ROI_REASON]
            (Roi_Reason_Seq,
            Created_Dt,
            Created_By_Seq,
            Modified_Dt,
            Modified_By_Seq,
            Record_Version,
            Organization_Seq,
            Active,
            TPO,
            NonTPO,
            Type,
            Name,
            DisplayText,
            Status)
        VALUES
            (-3
            ,getDate()
            ,1
            ,getDate()
            ,1
            ,0
            ,0
            ,0
            ,0
            ,0
            ,'request'
            ,'Authorization Request Received'
            ,'Authorization Request Received'
            ,0)

INSERT INTO [dbo].[ROI_REASON]
            (Roi_Reason_Seq,
            Created_Dt,
            Created_By_Seq,
            Modified_Dt,
            Modified_By_Seq,
            Record_Version,
            Organization_Seq,
            Active,
            TPO,
            NonTPO,
            Type,
            Name,
            DisplayText,
            Status)
        VALUES
            (-4
            ,getDate()
            ,1
            ,getDate()
            ,1
            ,0
            ,0
            ,0
            ,0
            ,0
            ,'request'
            ,'Authorization Request Denied'
            ,'Authorization Request Denied'
            ,0)
SET NOCOUNT off
set identity_insert [ROI_REASON] off
go

--ROI_ContactType
SET identity_insert [ROI_CONTACTTYPE] on
go
INSERT INTO [dbo].[ROI_ContactType]
                  (ROI_ContactType_Seq, Name)
                 VALUES
                  (-1,'Default')
SET identity_insert [ROI_CONTACTTYPE] off
go



--ROI_RequestorTypeToBillingTier
set identity_insert [ROI_RequestorTypeToBillingTier] on
go

insert into ROI_RequestorTypeToBillingTier
	(
		ROI_RequestorTypeToBillingTier_Seq
		,ROI_RequestorType_Seq
		,ROI_BillingTier_Seq
		,HPF
		,HECM
		,CEVA
		,Supplemental
	)
Values
	(
		-1
		,-1
		,-1
		,1
		,0
		,0
		,0
	)

SET NOCOUNT off

set identity_insert [ROI_RequestorTypeToBillingTier] off
go


--ROI_RequestorTypeToBillingTier
set identity_insert [ROI_RequestorTypeToBillingTier] on
go

insert into ROI_RequestorTypeToBillingTier
	(
		ROI_RequestorTypeToBillingTier_Seq
		,ROI_RequestorType_Seq
		,ROI_BillingTier_Seq
		,HPF
		,HECM
		,CEVA
		,Supplemental
	)
Values
	(
		-2
		,-1
		,-2
		,0
		,0
		,0
		,1
	)

SET NOCOUNT off

set identity_insert [ROI_RequestorTypeToBillingTier] off
go

