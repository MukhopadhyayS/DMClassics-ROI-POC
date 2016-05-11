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
* Stored Procedure      : ROI_MigrationAdminTables.sql
* Creation Date         : 03/30/2009
*					
* Written by            : RC	
*									
* Purpose               : Move data from Classic ROI to ROI13.5
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        Description
*   ---------	----------- ----------------
*									
* Return Status         : <none>		
*									
* Usage                 : ROI_MigrateAdminTables 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[ROI_BillingTemplate]
*                       : [Cabinet].[dbo].[ROI_FeeType]
*                       : [Cabinet].[dbo].[ROI_BillingTemplateToFeeType]
*                       : [Cabinet].[dbo].[ROI_BillingTier]
*                       : [Cabinet].[dbo].[ROI_BillingTierDetail]
*                       : [Cabinet].[dbo].[ROI_Billing_Type]
*                       : [Cabinet].[dbo].[ROI_Billing_Type_Tier]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* 	Name      	Date        	Purpose
* 	---------	-----------	----------------
* 	RC        	03/30/2009	Created new stored procedure.  
* 	RC			06/26/2009	Added DISTINCT to select for fee type and changed created_by to -1
*	MD Meersma	07/09/2009	The DELETE statement conflicted with the REFERENCE constraint(s) 
*	MD Meersma	07/10/2009	Violation of UNIQUE KEY constraint 'IXU_ROI_BillingTemplate_NameOrganization_Seq'
*
***************************************************************************************************************************************/
SET NOCOUNT ON
Print 'Starting ROI admin table migration at '+ Cast(getdate() as varchar(20))
--Start here

--Remove any data from previous conversion
Delete from [Cabinet].[dbo].ROI_BillingTemplateToFeeType where [Created_By_Seq] < 0
Delete from [Cabinet].[dbo].ROI_BillingTemplateToFeeType where ROI_BillingTemplate_Seq in (select ROI_BillingTemplate_Seq from ROI_BillingTemplate where Created_By_Seq < 0)
Delete from [Cabinet].[dbo].ROI_BillingTemplateToFeeType where ROI_FeeType_Seq in (select ROI_FeeType_Seq from ROI_FeeType where Created_By_Seq < 0)
Delete from [Cabinet].[dbo].ROI_FeeType where [Created_By_Seq] < 0
Delete from [Cabinet].[dbo].ROI_RequestorTypeToBillingTemplate where [Created_By_Seq] < 0
Delete from [Cabinet].[dbo].ROI_RequestorTypeToBillingTemplate where ROI_BillingTemplate_Seq in (select ROI_BillingTemplate_Seq from ROI_BillingTemplate where Created_By_Seq < 0)
Delete from [Cabinet].[dbo].ROI_BillingTemplate where [Created_By_Seq] < 0

Delete from [Cabinet].[dbo].ROI_RequestorTypeToBillingTier where [Created_By_Seq] < 0
Delete from [Cabinet].[dbo].ROI_RequestorTypeToBillingTier where ROI_BillingTier_Seq in (select ROI_BillingTier_Seq from ROI_BillingTier where Created_By_Seq < 0)
Delete from [Cabinet].[dbo].ROI_MediaType where [Created_By_Seq] < 0
Delete from [Cabinet].[dbo].ROI_BillingTierDetail where [Created_By_Seq]< 0
Delete from [Cabinet].[dbo].ROI_BillingTierDetail where ROI_BillingTier_Seq in (select ROI_BillingTier_Seq from ROI_BillingTier where Created_By_Seq < 0)
Delete from [Cabinet].[dbo].ROI_BillingTier where [Created_By_Seq] < 0


--Fill the BillingTemplate table with the names from the ROI_Billing_Type
--Insert the data

INSERT INTO 
	[cabinet].[dbo].[ROI_BillingTemplate]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[Description]
		,[Active]
	)
	(
		SELECT 
			[BillingType_ID] * -1
			, 0
			, Set_id 
			,[BillingType_Name]
			,[BillingType_Type] 
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		WHERE NOT EXISTS (SELECT 1 FROM [cabinet].[dbo].[ROI_BillingTemplate]
			WHERE Organization_Seq = Set_id and Name = BillingType_Name)
	)


--Show the data
Print ' '
Print 'Select * from [ROI_BillingTemplate] WHERE [Created_By_Seq] < 0'
Select * from [ROI_BillingTemplate] WHERE [Created_By_Seq] < 0
Print ' '
Print 'Select * from [ROI_Billing_Type] '
Select * from [ROI_Billing_Type] 


--Insert the data
INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			-1 -- billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'Minimum' as [name]
			,[Minimum] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [Minimum]
		HAVING
			[Minimum] > 0
	)
INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'Research' as [name]
			,[research] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [research]
		HAVING
			[research] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT 
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'Preparation' as [name]
			,[Preparation] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [Preparation]
		HAVING
			[Preparation] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'Certification' as [name]
			,[Certification] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [Certification]
		HAVING
			[Certification] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'HardCopiesPP' as [name]
			,[HardCopiesPP] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [HardCopiesPP]
		HAVING
			[HardCopiesPP] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'MicroFilmPP' as [name]
			,[MicroFilmPP] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [MicroFilmPP]
		HAVING
			[MicroFilmPP] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'Postage' as [name]
			,[Postage] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [Postage]
		HAVING
			[Postage] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'TaxRate' as [name]
			,[TaxRate] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [TaxRate]
		HAVING
			[TaxRate] > 0
	)

INSERT INTO 
	[cabinet].[dbo].[ROI_FeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[ChargeAmount]
		,[Active]
	)
	(
		SELECT DISTINCT
			 -1 --billingtype_id * -1 as [created_by_seq]
			, 0
			,Set_ID as [org_seq]
			,'RemainderFee' as [name]
			,[RemainderFee] as  [charge amount]
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END 
			AS [Active]
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		GROUP BY 
			BillingType_Name, Set_Id, BillingType_Id,BillType_Active, [RemainderFee]
		HAVING
			[RemainderFee] > 0
	)


--Show the data
Print ' '
Print 'Select * from [ROI_FeeType] WHERE [Created_By_Seq] < 0'
Select * from [ROI_FeeType] WHERE [Created_By_Seq] < 0
Print ' '
Print 'Select * from [ROI_Billing_Type] '
Select * from [ROI_Billing_Type]


--Insert the data
INSERT INTO 
	[cabinet].[dbo].[ROI_BillingTemplateToFeeType]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[ROI_BillingTemplate_Seq]
		,[ROI_FeeType_Seq]
	)
	(
	SELECT 
		ROI_BillingTemplate.Created_By_Seq
		, 0
		,ROI_BillingTemplate_Seq
		,ROI_FeeType_Seq
	FROM 
		ROI_BillingTemplate
		INNER JOIN ROI_FeeType 
			ON ROI_BillingTemplate.Created_By_Seq = ROI_FeeType.Created_By_Seq
	)

Print ' '
Print 'Select * from [ROI_BillingTemplateToFeeType] WHERE [Created_By_Seq] < 0'
Select * from [ROI_BillingTemplateToFeeType] WHERE [Created_By_Seq] < 0
Print ' '

--output of Billing template join
Print ' '
Print 'Billing Template data'

SELECT     
	ROI_BillingTemplate.Name AS [Template Name]
	, ROI_BillingTemplate.Organization_Seq AS Set_ID
	, ROI_BillingTemplate.Active AS [Template Active]
	, ROI_FeeType.Name AS [Fee Name]
	, ROI_FeeType.ChargeAmount AS [Fee Charge]
FROM         
	ROI_BillingTemplate 
		INNER JOIN ROI_BillingTemplateToFeeType 
			ON ROI_BillingTemplate.ROI_BillingTemplate_Seq = ROI_BillingTemplateToFeeType.ROI_BillingTemplate_Seq 
		INNER JOIN ROI_FeeType 
			ON ROI_BillingTemplateToFeeType.ROI_FeeType_Seq = ROI_FeeType.ROI_FeeType_Seq
WHERE 
	ROI_BillingTemplate.[Created_By_Seq] < 0
ORDER BY 
	Set_ID
	, [Template Name]
	, [Fee Name]


--Insert the data
INSERT INTO 
	[cabinet].[dbo].[ROI_BillingTier]
	(
		[Created_By_Seq]
		,[Modified_By_Seq]
		,[Organization_Seq]
		,[Name]
		,[Description]
		,[Active]
		,[FlatCharge]
		,[DefaultPageCharge]
		,[ROI_MediaType_Seq]
	)
	(
		SELECT 
			[BillingType_ID ] * -1
			, 0
			, Set_id 
			,[BillingType_Name]
			,[BillingType_Type] 
			,CASE UPPER(BillType_Active) --use case statement to translate the Y/N to True/False
				WHEN  'Y' 
					THEN 1 
					ELSE 2
				END
			,FlatFee
			,PerPage
			, -1 
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type]
		WHERE NOT EXISTS (SELECT 1 FROM [cabinet].[dbo].[ROI_BillingTier]
			WHERE Organization_Seq = Set_id and Name = BillingType_Name)
	)

--Show the data
Print ' '
Print 'Select * from [ROI_BillingTier] WHERE [Created_By_Seq] < 0'
Select * from [ROI_BillingTier] WHERE [Created_By_Seq] < 0
Print ' '
Print 'Select * from [ROI_Billing_Type] '
Select * from [ROI_Billing_Type]



--Insert the data
INSERT INTO 
	[cabinet].[dbo].[ROI_BillingTierDetail]
	(
		[ROI_BillingTier_Seq]
		,[Created_By_Seq]
		,[Modified_By_Seq]
		,[Page]
		,[PageCharge]
	)
	(
		SELECT 
			[ROI_BillingTier_Seq] 
			,[BillingType_ID ] * -1
			, 0
			,[LowerBound]
			,[ChargeAmount] 
		FROM 
			[cabinet].[dbo].[ROI_Billing_Type_Tier]
			INNER JOIN ROI_BillingTier
				ON [cabinet].[dbo].[ROI_Billing_Type_Tier].[BillingType_ID ]
					= [cabinet].[dbo].[ROI_BillingTier].[Created_By_Seq] * -1
	)

--Show the data
Print ' '
Print 'Select * from [[ROI_BillingTierDetail]] WHERE [Created_By_Seq] < 0'
Select * from [ROI_BillingTierDetail] WHERE [Created_By_Seq] < 0
Print ' '
Print 'Completed migration of classic ROI admin tables at '+ Cast(getdate() as varchar(20))

