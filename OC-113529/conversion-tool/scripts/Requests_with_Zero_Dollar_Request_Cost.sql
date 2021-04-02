/*
***************************************************************
*
*  	COPYRIGHT MCKESSON 2013
*		ALL RIGHTS RESERVED
*
* 	The copyright to the computer program(s) herein
* 	is the property of McKesson. The program(s)
* 	may be used and/or copied only with the written
* 	permission of McKesson or in accordance with
* 	the terms and conditions stipulated in the     
* 	agreement/contract under which the program(s)
* 	have been supplied.
*****************************************************************
* SQL script Name: Requests_with_Zero_Dollar_Request_Cost
* Description:
*	Script that lists all requests, which are not C-XML, where the total request cost is $0.00 or null.
*	If a request has multiple releases and at least one release has a total request cost of $0.00, request should display in this script.
*	Fields to display in order - Date Created; Request ID; Requestor Name; Requestor Type; Request Status; Total Request Cost (most recent release), Total Balance Due.
*	Filename should be titled "Requests with Zero Dollar Request Cost".
*
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
*	Bhanu Vanteru			03/14/2013	Created
*	Bhanu Vanteru			03/22/2013	CR 378,808: Removed Modified_By as a check to identify c-xml request
*	Iryna Politykina 		06/17/2013	CR 381,799: ROI-Conversion Tool: 'Requests_with_Zero_Dollar_Request_Cost' pre-migration script shows invalid unbillable requests 
*	I.Politykina			06/19/2013	Fix per bug #381305, exclude from unbillable the requests with total request cost = 0 in release-info, but in
*										invoice they have total request cost <> 0
*	I.Politykina			06/20/2013		Fix per bug #381305, the request will be unbillabale if 'Document Charges', 'Shipping fee' , 'Fee Charges' and
*											'Debit Adjustments' equals zero in billing-info and in billing-doc
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;

;WITH xmlRequests AS (SELECT * FROM  cabinet.dbo.ROI_RequestMain main WITH (NOLOCK)
WHERE NOT(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)') = 'MIGRATION'
AND main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)') =  'MIGRATION' collate SQL_Latin1_General_CP1_CI_AS ))

SELECT xmlRequests.Created_Dt as 'Date Created',
xmlRequests.ROI_RequestMain_Seq as 'Request ID',
COALESCE(xmlRequests.RequestMainXML.value('(/request/requestor/name)[1]','varchar(max)'), '') as 'Requestor Name',
COALESCE(xmlRequests.RequestMainXML.value('(/request/status)[1]','varchar(max)'), '') as 'Request Status',
(totalDocumentChargeRelease + totalFeeChargeRelease+totalshippingChargeRelease + totaldebitAdjustmentsRelease 
	+ totalDocumentChargeInvoice + totalFeeChargeInvoice + totalshippingChargeInvoice + totaldebitAdjustmentsInvoice ) AS 'Total Request Cost' ,
CAST(REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(xmlRequests.RequestMainXML.value('(/request/balance-due)[1]','varchar(max)'),0.00), '$',''), ')', ''), '(', '-'),',','') AS decimal(9,2) ) AS 'Total Balance Due'
FROM xmlRequests
JOIN( 
SELECT requests.ROI_RequestMain_Seq, 
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.DocumentChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalDocumentChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.FeeChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalFeeChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.shippingChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalshippingChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.debitAdjustmentsRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totaldebitAdjustmentsRelease,

SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.DocumentChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalDocumentChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.FeeChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalFeeChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.shippingChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totalshippingChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.debitAdjustmentsInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS decimal(9,2)), 0)) as totaldebitAdjustmentsInvoice

FROM

(SELECT d.ROI_RequestMain_Seq, 

RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "DocumentCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
')  as DocumentChargeRelease,

RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "FeeCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as FeeChargeRelease,

RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "shipping-charge"]
where ($item/attribute/@name = "shipping-charge" ) 
return <result> {data($item/attribute[@name = "shipping-charge"]/@value)} </result>
')  as shippingChargeRelease,


(SELECT RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type" and $item/attribute/@value = "Adjustment") 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
')  from Cabinet.dbo.ROI_RequestDeliveryCharges charges WITH (NOLOCK) WHERE charges.ROI_RequestDeliveryCharges_Seq = c.ROI_RequestDeliveryCharges_Seq
AND charges.RequestDeliveryChargesXML.query('
for $item in /release-item/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type" and $item/attribute/@value = "Adjustment") 
return <result> {data($item/attribute[@name = "is-debit"]/@value)} </result>
').exist('/result[ text()[1] = "True"]') = 1) as debitAdjustmentsRelease,

--Invoice
RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "DocumentCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as DocumentChargeInvoice,

RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "FeeCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as FeeChargeInvoice,

RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "shipping-charge"]
where ($item/attribute/@name = "shipping-charge" ) 
return <result> {data($item/attribute[@name = "shipping-charge"]/@value)} </result>
')  as shippingChargeInvoice,


(SELECT RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type" and $item/attribute/@value = "Adjustment") 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
')  from Cabinet.dbo.ROI_RequestDeliveryCharges charges WITH (NOLOCK) WHERE charges.ROI_RequestDeliveryCharges_Seq = c.ROI_RequestDeliveryCharges_Seq
AND charges.RequestDeliveryChargesXML.query('
for $item in /release-item/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type" and $item/attribute/@value = "Adjustment") 
return <result> {data($item/attribute[@name = "is-debit"]/@value)} </result>
').exist('/result[ text()[1] = "True"]') = 1) as debitAdjustmentsInvoice

FROM  Cabinet.dbo.ROI_RequestDelivery d  WITH (NOLOCK)
JOIN Cabinet.dbo.ROI_RequestDeliveryCharges c  WITH (NOLOCK) ON c.ROI_RequestDelivery_Seq = d.ROI_RequestDelivery_Seq ) 
as requests GROUP BY requests.ROI_RequestMain_Seq) as unbillableRequests ON unbillableRequests.ROI_RequestMain_Seq = xmlRequests.ROI_RequestMain_Seq 
WHERE totalDocumentChargeRelease = 0 AND totalFeeChargeRelease = 0 AND  totalshippingChargeRelease = 0 AND totaldebitAdjustmentsRelease = 0 
	  AND totalDocumentChargeInvoice = 0 AND totalFeeChargeInvoice = 0 AND totalshippingChargeInvoice = 0 AND totaldebitAdjustmentsInvoice = 0
 