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
*						: [Cabinet].[dbo].[ROI_RequesterEdited]
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
* Name      	Date        	Purpose
* ---------	----------- 	----------------
* RC        	05/26/2009  	Created   
* MDMeersma	07/07/2009	Linking the requestor to the requestor main address
*
***************************************************************************************************************************************/
USE CABINET
GO
SET NOCOUNT ON
Print 'Starting requester migration at ' + cast(getdate() as varchar(20));

--Removing data from previous migration
/*
DELETE FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTier] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorToEmailPhone] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorToAddress] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_Address] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_EmailPhone] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_Requestor] WHERE Created_by_Seq < 0

DELETE FROM [cabinet].[dbo].[ROI_RequestorType] WHERE Created_by_Seq < 0
*/
--check the ROI_RequesterEdited table
--if the table does not exist create it and fill with ROI_Requester Data
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_RequesterEdited]') AND type in (N'U'))
BEGIN
	SELECT * INTO ROI_RequesterEdited
	FROM ROI_Requester
		--select * from roi_requesteredited
	GRANT Select, UPDATE, DELETE ON ROI_RequesterEdited to IMNET
END
--fill the table if it is empty
IF  NOT EXISTS (SELECT 1 FROM ROI_RequesterEdited)
BEGIN
	DROP TABLE ROI_RequesterEdited
	SELECT * INTO ROI_RequesterEdited
	FROM ROI_Requester
	GRANT Select, UPDATE, DELETE ON ROI_RequesterEdited to IMNET
END



--insert the distinct requester type and record view from the requesterEdited table into the ROI_Requestortype table
--Use a created by of ROI_Requester.RequesterType_Id *-1 to indicate this data came from a migration

Print 'Migrating the RequestorType.'
INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorType]
	(
		[Created_By_Seq]
		,[RecordView]
		,[Active]
		,[Name]
	)
SELECT DISTINCT 
	ROI_RequesterEdited.RequesterType_Id *-1
	, recordview
	, CASE UPPER(ROI_RequesterEdited.RequesterActive)
		WHEN 'Y' THEN 'True'
		ELSE 'False'
		END
	, RequesterType_Name
FROM
	ROI_RequesterEdited 
INNER JOIN
	ROI_Requester_Type ON ROI_RequesterEdited.RequesterType_Id = ROI_Requester_Type.RequesterType_Id 

/*
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
	ROI_RequestorType 
WHERE 
	ROI_RequestorType.Created_By_Seq < 0

*/
--place the address information in the ROI_Address table
Print 'Migrating the ROI_Address.'
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
	ROI_RequesterEdited
WHERE 
	LEN(Address1) > 0
	AND LEN(City) > 0
	AND LEN(State) > 0
	AND LEN(Zip) > 0

/*
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
INNER JOIN ROI_RequesterEdited 
ON ROI_Address.Created_By_Seq = ROI_RequesterEdited.Requester_Id * - 1 
AND ROI_Address.AddressLine1 = ROI_RequesterEdited.Address1 
AND ROI_Address.AddressLine2 = ROI_RequesterEdited.Address2 
AND ROI_Address.AddressLine3 = ROI_RequesterEdited.Address3 
AND ROI_Address.City = ROI_RequesterEdited.City 
AND ROI_Address.State = ROI_RequesterEdited.State 
AND ROI_Address.PostalCode = ROI_RequesterEdited.Zip
*/

--migrate the requestor information
Print 'Migrating the ROI_Requestor.'
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
	ROI_RequesterEdited.Requester_Id * - 1 
	, ROI_RequestorType.ROI_RequestorType_Seq
	, CASE UPPER(ROI_RequesterEdited.RequesterActive)
		WHEN 'Y' THEN 'True'
		ELSE 'False'
		END
	, 'False'
	, 'False'
	, 'False'
	, 'False'
	, ROI_RequesterEdited.RequesterName
FROM         
	ROI_RequesterEdited 
INNER JOIN
	ROI_RequestorType ON ROI_RequesterEdited.RequesterType_Id = ROI_RequestorType.Created_By_Seq * -1 
AND 
	ROI_RequesterEdited.RecordView = ROI_RequestorType.RecordView

/*
--show the migrated data
Print 'The data entered into ROI_Requestor is:'
SELECT     *
FROM         ROI_Requestor 
WHERE	ROI_Requestor.Created_By_Seq < 0
*/

Print 'Linking the requestor to the requestor main address.'
Declare @l_ROI_AddressType_Seq int
Select 
	@l_ROI_AddressType_Seq = ROI_AddressType_Seq
FROM 
	ROI_AddressType
WHERE
	Name = 'Main'

INSERT INTO 
	[cabinet].[dbo].[ROI_RequestorToAddress]
	([Created_By_Seq]
	,[ROI_Requestor_Seq]
	,[ROI_Address_Seq]
	,[ROI_AddressType_Seq])

SELECT     
	ROI_RequesterEdited.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_Address.ROI_Address_Seq
	, @l_ROI_AddressType_Seq
FROM         
	ROI_RequesterEdited 
		INNER JOIN ROI_Requestor ON ROI_RequesterEdited.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_Address ON ROI_Requestor.Created_By_Seq = ROI_Address.Created_By_Seq

/*
--Show the migrated data
Print 'The migrated data linking the requestor and the address is:'
Select * from ROI_RequestorToAddress WHERE Created_by_Seq < 0
--completed Linking requestor to address
*/

Print 'Linking the requestor to the billing template.'

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
	ROI_RequesterEdited 
	INNER JOIN ROI_RequestorType 
		ON ROI_RequesterEdited.Requester_Id = ROI_RequestorType.Created_By_Seq * - 1 
	INNER JOIN ROI_BillingTemplate
		ON ROI_RequesterEdited.BillingType_Id = ROI_BillingTemplate.Created_By_Seq * - 1

/*
Print 'The migrated data linking requestor type and billing template is:'
SELECT * FROM [cabinet].[dbo].[ROI_RequestorTypeToBillingTemplate]
*/

Print'Moving the requestor phone data.'
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
	[cabinet].[dbo].[ROI_RequesterEdited]
WHERE
	LEN([Phone]) > 0

Print 'Updating the link between requestor and EmailPhone.'
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
	ROI_RequesterEdited.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_EmailPhone.ROI_EmailPhone_Seq
	, @l_ROI_EmailPhoneType_Seq
FROM         
	ROI_RequesterEdited 
		INNER JOIN ROI_Requestor ON ROI_RequesterEdited.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_EmailPhone ON ROI_RequesterEdited.Requester_Id = ROI_EmailPhone.Created_By_Seq * - 1



Print 'Moving the requestor fax data.'

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
	[cabinet].[dbo].[ROI_RequesterEdited]
WHERE
	LEN([Fax]) > 0

Print 'Updating the link between requestor and EmailPhone.'
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
	ROI_RequesterEdited.Requester_Id * -1
	, ROI_Requestor.ROI_Requestor_Seq
	, ROI_EmailPhone.ROI_EmailPhone_Seq
	, @l_ROI_EmailPhoneType_Seq
FROM         
	ROI_RequesterEdited 
		INNER JOIN ROI_Requestor ON ROI_RequesterEdited.Requester_Id = ROI_Requestor.Created_By_Seq * - 1 
		INNER JOIN ROI_EmailPhone ON ROI_RequesterEdited.Requester_Id = ROI_EmailPhone.Modified_By_Seq * - 1


/*
--show the data inserted
Print 'The data Linking the requestor to EmailPhone is:'

SELECT     
	ROI_EmailPhone.Created_By_Seq,ROI_EmailPhone.Modified_By_Seq, ROI_EmailPhone.EmailPhone
FROM
	ROI_EmailPhone 
	INNER JOIN ROI_RequesterEdited 
		ON ROI_EmailPhone.Created_By_Seq = ROI_RequesterEdited.Requester_Id * - 1


--show the migrated data
Print 'The EmailPhone data is:'
SELECT
	*
FROM
	[cabinet].[dbo].[ROI_RequestorToEmailPhone]
WHERE 
	[Created_By_Seq] < 0
*/

--migrating ROI_RequestorTypeToBillingTier
insert into ROI_RequestorTypeToBillingTier
	(
		Created_By_Seq
		,ROI_RequestorType_Seq
		,ROI_BillingTier_Seq
		,HPF
		,HECM
		,CEVA
		,Supplemental
	)
(Select  
		Created_By_Seq
		,ROI_RequestorType_seq
		,-1
		,1
		,0
		,0
		,0
	from ROI_RequestorType Where created_by_seq < 0)

insert into ROI_RequestorTypeToBillingTier
	(
		Created_By_Seq
		,ROI_RequestorType_Seq
		,ROI_BillingTier_Seq
		,HPF
		,HECM
		,CEVA
		,Supplemental
	)
(Select  
		Created_By_Seq
		,ROI_RequestorType_seq
		,-2
		,0
		,0
		,0
		,1
	from ROI_RequestorType Where created_by_seq < 0)


/*
--Show the migrated data
Select * from ROI_RequestorTypeToBillingTier
Where Created_By_Seq < 0
*/





Print 'Completed requester migration at ' + cast(getdate() as varchar(20));
