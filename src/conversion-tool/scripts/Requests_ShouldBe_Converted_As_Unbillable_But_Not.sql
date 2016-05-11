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
* SQL script Name: ....
* Description: The script lists the ROI_RequestMain_seq for the classic requests that were not converted into 'Un-billable' requests,
*			   but should. 
*
* NOTES: 
* Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	I.Politykina			06/24/2013		Created; CR #381949
****************************************************************
*/

SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;


;WITH xmlRequests AS (SELECT * FROM  cabinet.dbo.ROI_RequestMain main WITH (NOLOCK)
WHERE NOT(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)') = 'MIGRATION'
AND main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)') =  'MIGRATION' collate SQL_Latin1_General_CP1_CI_AS ))

SELECT unbillables.ROI_RequestMain_Seq FROM (
SELECT requests.ROI_RequestMain_Seq, 
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.DocumentChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalDocumentChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.FeeChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalFeeChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.shippingChargeRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalshippingChargeRelease,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.debitAdjustmentsRelease.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totaldebitAdjustmentsRelease,

SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.DocumentChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalDocumentChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.FeeChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalFeeChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.shippingChargeInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totalshippingChargeInvoice,
SUM(COALESCE(CAST(REPLACE(REPLACE(REPLACE(REPLACE(requests.debitAdjustmentsInvoice.value('(result)[1]','varchar(20)'), '$', ''),')',''),'(', ''), ',', '') AS float), 0)) as totaldebitAdjustmentsInvoice

FROM

(SELECT d.ROI_RequestMain_Seq, 

c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "DocumentCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as DocumentChargeRelease,

c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "FeeCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as FeeChargeRelease,

c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "shipping-charge"]
where ($item/attribute/@name = "shipping-charge" ) 
return <result> {data($item/attribute[@name = "shipping-charge"]/@value)} </result>
') as shippingChargeRelease,

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
c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "DocumentCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as DocumentChargeInvoice,

c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "FeeCharge"]/release-item
where ($item/attribute/@name = "amount" ) 
return <result> {data($item/attribute[@name = "amount"]/@value)} </result>
') as FeeChargeInvoice,

c.RequestDeliveryChargesXML.query('
for $item in /release-item[@type="billing-doc"]/release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "shipping-charge"]
where ($item/attribute/@name = "shipping-charge" ) 
return <result> {data($item/attribute[@name = "shipping-charge"]/@value)} </result>
') as shippingChargeInvoice,


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
as requests GROUP BY requests.ROI_RequestMain_Seq) as unbillables 

JOIN  xmlRequests on xmlRequests.ROI_RequestMain_Seq = unbillables.ROI_RequestMain_Seq
WHERE totalDocumentChargeRelease = 0 AND totalFeeChargeRelease = 0 AND  totalshippingChargeRelease = 0 AND totaldebitAdjustmentsRelease = 0 
AND totalDocumentChargeInvoice = 0 AND totalFeeChargeInvoice = 0 AND totalshippingChargeInvoice = 0 AND totaldebitAdjustmentsInvoice = 0
AND unbillables.ROI_RequestMain_Seq NOT IN (
SELECT  ROI_RequestMain_Seq from cabinet.dbo.ROI_RequestCore core  WITH (NOLOCK)
JOIN  xmlRequests ON xmlRequests.ROI_RequestMain_Seq = core.ROI_RequestCore_Seq WHERE
core.Unbillable = 1
)

