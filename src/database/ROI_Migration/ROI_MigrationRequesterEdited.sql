SET QUOTED_IDENTIFIER ON
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
* Procedure             : ROI_MigrationRequester.sql
* Creation Date         : 05/26/2009
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
* Usage                 : ROI_MigrationRequester.sql 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[ROI_Requester]
*                       : [Cabinet].[dbo].[ROI_Requestor]
*                       : [Cabinet].[dbo].[ROI_RequestorType]
*                       : [Cabinet].[dbo].[ROI_EmailPhone]
*                       : [Cabinet].[dbo].[ROI_RequestorToEmailPhone]
*                       : [Cabinet].[dbo].[ROI_RequestorToAddress]
*                       : [Cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
*                       : [Cabinet].[dbo].[ROI_Address]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        05/26/2009  Created   
*
***************************************************************************************************************************************/
USE CABINET
GO
SET NOCOUNT ON
Print 'Starting requester migration  at ' + cast(getdate() as varchar(20));

--Removing data from previous migration
DELETE FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorToEmailPhone] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorToAddress] WHERE Created_by_Seq < 0

DELETE FROM ROI_Address WHERE [Created_By_Seq]< 0

--DELETE FROM 
--	ROI_Address
--FROM 
--	ROI_Address 
--	INNER JOIN ROI_Requester 
--		ON ROI_Address.Created_By_Seq = ROI_Requester.Requester_Id * - 1 
--		AND ROI_Address.AddressLine1 = ROI_Requester.Address1 
--		AND ROI_Address.AddressLine2 = ROI_Requester.Address2 
--		AND ROI_Address.AddressLine3 = ROI_Requester.Address3 
--		AND ROI_Address.City = ROI_Requester.City 
--		AND ROI_Address.State = ROI_Requester.State 
--		AND ROI_Address.PostalCode = ROI_Requester.Zip

DELETE FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate] WHERE Created_by_Seq < 0

DELETE FROM ROI_EmailPhone WHERE [Created_By_Seq]< 0

--DELETE FROM 
--	ROI_EmailPhone
--FROM
--	ROI_EmailPhone 
--	INNER JOIN ROI_Requester 
--		ON ROI_EmailPhone.Created_By_Seq = ROI_Requester.Requester_Id * - 1

DELETE FROM ROI_Requestor WHERE [Created_By_Seq]< 0

--DELETE FROM 
--	ROI_Requestor
--FROM
--	ROI_Requestor 
--INNER JOIN
--	ROI_Requester 
--		ON ROI_Requestor.Created_By_Seq = ROI_Requester.Requester_Id * - 1

DELETE FROM ROI_RequestorType WHERE [Created_By_Seq]<0

--DELETE FROM 
--	ROI_RequestorType
--FROM 
--	ROI_Requester 
--	INNER JOIN ROI_RequestorType 
--		ON ROI_Requester.Requester_Id = ROI_RequestorType.Created_By_Seq * - 1 
--		AND ROI_Requester.RequesterName = ROI_RequestorType.Name 
--		AND ROI_Requester.RecordView = ROI_RequestorType.RecordView



--insert the requester types in the requestor type table
INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorType]
	(
		[Created_By_Seq]
		,[RecordView]
		,[Active]
		,[Name]
	)

SELECT [Requester_Id] * -1 
	,[RecordView]
	, 'False'
	,[RequesterName]
FROM 
[cabinet].[dbo].[ROI_Requester]
--show the data inserted
Print 'The data inserted into RequestorType is:';
SELECT     
	ROI_RequestorType.ROI_RequestorType_Seq
	, ROI_RequestorType.Created_Dt
	, ROI_RequestorType.Created_By_Seq
	, ROI_RequestorType.RecordView
	, ROI_RequestorType.Active
	, ROI_RequestorType.Name
FROM         
	ROI_Requester 
	INNER JOIN ROI_RequestorType 
		ON ROI_Requester.Requester_Id = ROI_RequestorType.Created_By_Seq * - 1 
		AND ROI_Requester.RequesterName = ROI_RequestorType.Name 
		AND ROI_Requester.RecordView = ROI_RequestorType.RecordView



--place the address information in the ROI_Address table
INSERT INTO 
	[cabinet].[dbo].[ROI_Address]
	(
	[Created_By_Seq]
	,[AddressLine1]
	,[AddressLine2]
	,[AddressLine3]
	,[City]
	,[State]
	,[PostalCode]
	)
SELECT     
	Requester_Id * -1
	, Address1
	, Address2
	, Address3
	, City
	, State
	, Zip
FROM
	ROI_Requester
WHERE 
	LEN(Address1) > 0
	AND LEN(City) > 0
	AND LEN(State) > 0
	AND LEN(Zip) > 0

--show the data inserted
Print 'The data inserted into ROI_Address is:';
SELECT     
ROI_Address.Created_By_Seq
, ROI_Address.AddressLine1
, ROI_Address.AddressLine2
, ROI_Address.AddressLine3
, ROI_Address.City
,  ROI_Address.State
, ROI_Address.PostalCode
FROM         
ROI_Address 
INNER JOIN ROI_Requester 
ON ROI_Address.Created_By_Seq = ROI_Requester.Requester_Id * - 1 
AND ROI_Address.AddressLine1 = ROI_Requester.Address1 
AND ROI_Address.AddressLine2 = ROI_Requester.Address2 
AND ROI_Address.AddressLine3 = ROI_Requester.Address3 
AND ROI_Address.City = ROI_Requester.City 
AND ROI_Address.State = ROI_Requester.State 
AND ROI_Address.PostalCode = ROI_Requester.Zip



--migrate the requester information

INSERT INTO [cabinet].[dbo].[ROI_Requestor]
           (
           [Created_By_Seq]
           ,[ROI_RequestorType_Seq]
           ,[Active]
           ,[Frequent]
           ,[PrePayRequired]
           ,[CertLetterRequired]
           ,[IsPatient]
           ,[NameLast]
           )
SELECT     
	ROI_Requester.Requester_Id * - 1 
	, ROI_RequestorType.ROI_RequestorType_Seq
	, CASE UPPER(ROI_Requester.RequesterActive)
		WHEN 'Y' THEN 'True'
		ELSE 'False'
		END
	, 'False'
	, 'False'
	, 'False'
	, 'False'
	, ROI_Requester.RequesterName
FROM         
	ROI_Requester 
	INNER JOIN ROI_RequestorType 
		ON ROI_Requester.Requester_Id = ROI_RequestorType.Created_By_Seq * - 1


--show the migrated data
SELECT     ROI_Requestor.Created_By_Seq ,ROI_RequestorType_Seq, ROI_Requestor.NameLast,
                      ROI_Requestor.Active
FROM         ROI_Requestor INNER JOIN
                      ROI_Requester ON ROI_Requestor.Created_By_Seq = ROI_Requester.Requester_Id * - 1

----------------------------------------------------------
Print 'linking the requestor to the requestor default address'

Declare @l_ROI_AddressType_Seq int
Select 
	@l_ROI_AddressType_Seq = ROI_AddressType_Seq
FROM 
	ROI_AddressType
WHERE
	Name = 'Default'

INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorToAddress]
	([Created_By_Seq]
	,[ROI_Requestor_Seq]
	,[ROI_Address_Seq]
	,[ROI_AddressType_Seq])

SELECT     
	ROI_Requester.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_Address.ROI_Address_Seq
	, @l_ROI_AddressType_Seq
FROM         
	ROI_Requester 
		INNER JOIN ROI_Requestor ON ROI_Requester.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_Address ON ROI_Requestor.Created_By_Seq = ROI_Address.Created_By_Seq

--Show the migrated data
Print 'The migrated data linking the requestor and the address is'
Select * from ROI_RequestorToAddress WHERE Created_by_Seq < 0

--completed linking requestor to address
--linking the requestor to the billing template

INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
	(
	[Created_By_Seq]
	,[ROI_RequestorType_Seq]
	,[ROI_BillingTemplate_Seq]
	,[IsDefault]
	)
SELECT     
	ROI_RequestorType.Created_By_Seq
	, ROI_RequestorType.ROI_RequestorType_Seq
	, ROI_BillingTemplate.ROI_BillingTemplate_Seq
	, 'False'
FROM         
	ROI_Requester 
	INNER JOIN ROI_RequestorType 
		ON ROI_Requester.Requester_Id = ROI_RequestorType.Created_By_Seq * - 1 
	INNER JOIN ROI_BillingTemplate
		ON ROI_Requester.BillingType_Id = ROI_BillingTemplate.Created_By_Seq * - 1
Print 'The migrated data linking requestor type and billing template is'
SELECT * FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
	



--move the requester phone data 
INSERT INTO 
	[cabinet].[dbo].[ROI_EmailPhone]
	(
	[Created_By_Seq]
	,[EmailPhone]
)
SELECT 
	[Requester_Id] * -1
	,[Phone]
	--,[Fax]
FROM 
	[cabinet].[dbo].[ROI_Requester]
WHERE
	LEN([Phone]) > 0

--update the link between requestor and emailphone
Declare @l_ROI_EmailPhoneType_Seq int
Select @l_ROI_EmailPhoneType_Seq = ROI_EmailPhoneType_Seq
FROM ROI_EmailPhoneType
WHERE UPPER(name) = 'WORK'
--select @l_ROI_EmailPhoneType_Seq

INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorToEmailPhone]
	(
		[Created_By_Seq]
		,[ROI_Requestor_Seq]
		,[ROI_EmailPhone_Seq]
		,[ROI_EmailPhoneType_Seq]
	)
SELECT     
	ROI_Requester.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_EmailPhone.ROI_EmailPhone_Seq
	, @l_ROI_EmailPhoneType_Seq
FROM         
	ROI_Requester 
		INNER JOIN ROI_Requestor ON ROI_Requester.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_EmailPhone ON ROI_Requester.Requester_Id = ROI_EmailPhone.Created_By_Seq * - 1



--move the requester fax data 

INSERT INTO 
	[cabinet].[dbo].[ROI_EmailPhone]
	(
	[Created_By_Seq]
	,[Modified_By_Seq]
	,[EmailPhone]
)
SELECT 
	[Requester_Id] * -1
	,[Requester_Id] * -1
	,[Fax]
FROM 
	[cabinet].[dbo].[ROI_Requester]
WHERE
	LEN([Fax]) > 0

--update the link between requestor and emailphone
Select @l_ROI_EmailPhoneType_Seq = ROI_EmailPhoneType_Seq
FROM ROI_EmailPhoneType
WHERE UPPER(name) = 'MainFax'
--select @l_ROI_EmailPhoneType_Seq


INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorToEmailPhone]
	(
		[Created_By_Seq]
		,[ROI_Requestor_Seq]
		,[ROI_EmailPhone_Seq]
		,[ROI_EmailPhoneType_Seq]
	)
SELECT     
	ROI_Requester.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_EmailPhone.ROI_EmailPhone_Seq
	, @l_ROI_EmailPhoneType_Seq
FROM         
	ROI_Requester 
		INNER JOIN ROI_Requestor ON ROI_Requester.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_EmailPhone ON ROI_Requester.Requester_Id = ROI_EmailPhone.Modified_By_Seq * - 1


--show the data inserted
SELECT     
	ROI_EmailPhone.Created_By_Seq,ROI_EmailPhone.Modified_By_Seq, ROI_EmailPhone.EmailPhone
FROM
	ROI_EmailPhone 
	INNER JOIN ROI_Requester 
		ON ROI_EmailPhone.Created_By_Seq = ROI_Requester.Requester_Id * - 1


--show the migrated data
SELECT
	*
FROM
	[cabinet].[dbo].[ROI_RequestorToEmailPhone]
WHERE 
	[Created_By_Seq] < 0