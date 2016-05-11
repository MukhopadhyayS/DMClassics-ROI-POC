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
* SQL script Name: C-XMLRequest
* Description:   
*	DisplayS the following for pre-migration and post migration requests to validate XML requests
*	1.	Total number of XML ROI requests
*	2.	Total number of XML requests released with no invoice 
*	3.	Total number of XML requests where the total request cost is $0.00 or null (unbillable candidates)
*	4.	Total number of unbillable XML requests (post migration )
*	5.	For each request status: Total number of requests
*
*
* NOTES: 
* Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	I.Politykina			05/12/2013		Created
* 	I.Politykina			05/21/2013		Fix for Un-billable Requests 
*	I.Politykina			05/31/2013		Fix per bug # 381304: ROI-Conversion Tool: Post Migration Report
*											Auth-Received/Auth Received Denied Request status are not displayed..
*	I.Politykina			06/05/2013		Fix per bug # 381305: ROI-Conversion Tool: Pre-migrated total Un-billable 
*											Requests count mismatches with the Post Migration Un-billable request count
*	I.Politykina			06/14/2013		Fix per bug # 381305 after fail: ROI-Conversion Tool: Pre-migrated total Un-billable 
*											Requests count mismatches with the Post Migration Un-billable request count
*	I.Politykina			06/19/2013		Fix per bug #381305, exclude from unbillable the requests with total request cost = 0 in release-info, but in
*											invoice they have total request cost <> 0
*	I.Politykina			06/20/2013		Fix per bug #381305, the request will be unbillabale if 'Document Charges', 'Shipping fee' , 'Fee Charges' and
*											'Debit Adjustments' equals zero in billing-info and in billing-doc
*
****************************************************************
*/

SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;
DECLARE @total_b int, @total_a int, 
@xmlRequestsWithZeroCost_b int, @xmlRequestsWithZeroCost_a int, @unknown_b int,
@unknown_a int,@none_b int, @none_a int, @authRecieved_b int,  @authRecieved_a int,
@logged_b int, @logged_a int, @completed_b int, @completed_a int, @denied_b int,
@denied_a int, @canceled_b int, @canceled_a int, @pended_b int, @pended_a int,
@preBilled_b int, @preBilled_a int , @authReceivedDenied_b int , @authReceivedDenied_a int 

-- XML requests, not Migrated ones
;WITH xmlRequests AS (SELECT * FROM  cabinet.dbo.ROI_RequestMain main WITH (NOLOCK)
WHERE NOT(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)') = 'MIGRATION'
AND main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)') =  'MIGRATION' collate SQL_Latin1_General_CP1_CI_AS ))

--total XML requests before and after conversion
SELECT 
@total_b = (SELECT COUNT(*) FROM  xmlRequests) ,  
@total_a = (SELECT COUNT(*) FROM [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK)
JOIN   xmlRequests xml ON c.ROI_RequestCore_Seq = xml.ROI_RequestMain_Seq
),

-- Unbillable candidate requests for XML rquests: total cost = 0 and there is no debit adjustments or the sum of debit adjustments equals 0(from 15.x version). 
@xmlRequestsWithZeroCost_b = (SELECT COUNT( DISTINCT res.ROI_RequestMain_Seq) FROM (



(SELECT unbillables.ROI_RequestMain_Seq FROM (
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

JOIN 	xmlRequests on xmlRequests.ROI_RequestMain_Seq = unbillables.ROI_RequestMain_Seq
WHERE totalDocumentChargeRelease = 0 AND totalFeeChargeRelease = 0 AND  totalshippingChargeRelease = 0 AND totaldebitAdjustmentsRelease = 0 
	AND totalDocumentChargeInvoice = 0 AND totalFeeChargeInvoice = 0 AND totalshippingChargeInvoice = 0 AND totaldebitAdjustmentsInvoice = 0)

) as res 
) ,


-- Total Unbillable candidate requests converted to 16.0
@xmlRequestsWithZeroCost_a = (SELECT COUNT(*) from cabinet.dbo.ROI_RequestCore core  WITH (NOLOCK)
JOIN  xmlRequests ON xmlRequests.ROI_RequestMain_Seq = core.ROI_RequestCore_Seq WHERE
core.Unbillable = 1)
,

--Total XML requests with 'UNKNOWN' status before and after conversion 
@unknown_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'UNKNOWN' collate SQL_Latin1_General_CP1_CI_AS) ,
@unknown_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'UNKNOWN' collate SQL_Latin1_General_CP1_CI_AS),
	
--Total XML requests with 'NONE' status before and after conversion 	
@none_b =(SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'NONE' collate SQL_Latin1_General_CP1_CI_AS) ,
@none_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'NONE' collate SQL_Latin1_General_CP1_CI_AS),

--Total XML requests with 'AUTH-RECEIVED' status before and after conversion 	
@authRecieved_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') like 'AUTH%RECEIVED' collate SQL_Latin1_General_CP1_CI_AS
) ,
@authRecieved_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus like 'AUTH%RECEIVED' collate SQL_Latin1_General_CP1_CI_AS
	) ,

--Total XML requests with 'LOGGED' status before and after conversion 		
@logged_b =(SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'LOGGED' collate SQL_Latin1_General_CP1_CI_AS) ,
@logged_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'LOGGED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'COMPLETED' status before and after conversion 		
@completed_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'COMPLETED' collate SQL_Latin1_General_CP1_CI_AS) ,
@completed_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'COMPLETED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'DENIED' status before and after conversion 		
@denied_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'DENIED' collate SQL_Latin1_General_CP1_CI_AS) ,
@denied_a =(SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN  xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'DENIED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'CANCELED' status before and after conversion 		
@canceled_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'CANCELED' collate SQL_Latin1_General_CP1_CI_AS) ,
@canceled_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
	JOIN  xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'CANCELED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'PENDED' status before and after conversion 		
@pended_b =(SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') = 'PENDED' collate SQL_Latin1_General_CP1_CI_AS) ,
@pended_a =(SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
		JOIN  xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus = 'PENDED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'PRE-BILLED' status before and after conversion 		
@preBilled_b = (SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') LIKE 'PRE%BILLED' collate SQL_Latin1_General_CP1_CI_AS) ,
@preBilled_a =(SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
		JOIN  xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus LIKE 'PRE%BILLED' collate SQL_Latin1_General_CP1_CI_AS) ,

--Total XML requests with 'AUTH RECEIVED DENIED'' status before and after conversion 		
@authReceivedDenied_b =(SELECT COUNT(*) FROM xmlRequests  WHERE RequestMainXML.value('(/request/status)[1]', 'varchar(100)') LIKE 'AUTH%RECEIVED%DENIED' collate SQL_Latin1_General_CP1_CI_AS
) ,
@authReceivedDenied_a = (SELECT COUNT(*) from  [cabinet].[dbo].[ROI_RequestCore] c WITH (NOLOCK) 
		JOIN  xmlRequests xmlreq ON c.ROI_RequestCore_Seq = xmlreq.ROI_RequestMain_Seq  WHERE c.RequestStatus LIKE 'AUTH%RECEIVED%DENIED' collate SQL_Latin1_General_CP1_CI_AS) 

SELECT 'Total Requests (excluding Classic Requests)',CAST(@total_b as VARCHAR)as 'Before Conversion', CAST(@total_a as VARCHAR) as 'After Conversion', 
CASE WHEN @total_b <> @total_a THEN 'Fail'
ELSE 'Pass' END as 'Check'

UNION ALL
SELECT 'Total Un-billable Requests (excluding Classic Requests)'  , CAST(@xmlRequestsWithZeroCost_b as VARCHAR), CAST(@xmlRequestsWithZeroCost_a as VARCHAR), 
CASE WHEN @xmlRequestsWithZeroCost_b <> @xmlRequestsWithZeroCost_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL 
SELECT '','','',''

UNION ALL 
SELECT 'Request Status','Total Requests','Total Requests',''

UNION ALL 
SELECT 'Status `Unknown`', CAST(@unknown_b as VARCHAR), CAST(@unknown_a as VARCHAR), CASE WHEN @unknown_b <> @unknown_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL 
SELECT 'Status `None`', CAST(@none_b as VARCHAR), CAST(@none_a as VARCHAR), CASE WHEN @none_b <> @none_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Auth-Received`', CAST(@authRecieved_b as VARCHAR), CAST(@authRecieved_a as VARCHAR),CASE WHEN @authRecieved_b <> @authRecieved_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL    
SELECT 'Status `Logged`', CAST(@logged_b as VARCHAR), CAST(@logged_a as VARCHAR), CASE WHEN @logged_b <> @logged_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Completed`', CAST(@completed_b as VARCHAR), CAST(@completed_a as VARCHAR), CASE WHEN @completed_b <> @completed_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Denied`', CAST(@denied_b as VARCHAR), CAST(@denied_a as VARCHAR), CASE WHEN @denied_b <> @denied_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Canceled`', CAST(@canceled_b as VARCHAR), CAST(@canceled_a as VARCHAR),CASE WHEN @canceled_b <> @canceled_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Pended`',  CAST(@pended_b as VARCHAR), CAST(@pended_a as VARCHAR), CASE WHEN @pended_b <> @pended_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Pre-Billed`', CAST(@preBilled_b as VARCHAR), CAST(@preBilled_a as VARCHAR), CASE WHEN @preBilled_b <> @preBilled_a THEN 'Fail' ELSE 'Pass' END 

UNION ALL  
SELECT 'Status `Auth Received Denied`' , CAST(@authReceivedDenied_b as VARCHAR), CAST(@authReceivedDenied_a as VARCHAR), CASE WHEN @authReceivedDenied_b <> @authReceivedDenied_a THEN 'Fail' ELSE 'Pass' END 