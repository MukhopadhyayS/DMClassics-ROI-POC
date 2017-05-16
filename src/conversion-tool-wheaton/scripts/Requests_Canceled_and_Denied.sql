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
*  SQL script Name: Requests_Canceled_and_Denied.sql
*  Description: Canceled and Denied Requests, that have debit or credit adjustments.
*  Create a pre-conversion script that lists all cancelled or denied requests.  Script will contain the following fields:
*
*	Date Created 
*	Request ID 
*	Requestor Name 
*	Request Status 
*	Total Charges
*	Total Payments/Adjustments
*	Balance Due
*	Old ROI
*
* NOTES: 
* Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	I.Politykina			07/19/2013		Created;CR #382488; identify requests that have been cancelled or denied..
********************************************************************************************************************/

SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;


/*Gather all 'canceled' or 'denied' requests which have debit or/and credit adjustments into variable table.
Fields in the table RequestMain_seq(request ID), RequestDeliveryCharges_Seq(billing info for latest delivery), RequestDelivery_Seq (the latest delivery for request).
*/

DECLARE @RequestsWithLatestBillingInfo table
(
	RequestMain_seq int NOT NULL,
	RequestDeliveryCharges_Seq int NOT NULL, 
	RequestDelivery_Seq int NOT NULL
);

;WITH latestRequestDelivery as ( select MAX(ROI_RequestDelivery_Seq) as deliveryId, ROI_RequestMain_seq from Cabinet.dbo.ROI_RequestDelivery WITH (NOLOCK) GROUP BY ROI_RequestMain_seq )

INSERT INTO @RequestsWithLatestBillingInfo 

SELECT main.ROI_RequestMain_seq, MAX (ROI_RequestDeliveryCharges_Seq), c.ROI_RequestDelivery_Seq FROM Cabinet.dbo.ROI_RequestDeliveryCharges c WITH (NOLOCK)
JOIN latestRequestDelivery ON latestRequestDelivery.deliveryId = c.ROI_RequestDelivery_Seq
JOIN Cabinet.dbo.ROI_RequestMain main WITH (NOLOCK) ON latestRequestDelivery.ROI_RequestMain_seq = main.ROI_RequestMain_Seq
WHERE c.isInvoiced = 0 AND c.RequestDeliveryChargesXML.query('
for $item in /release-item/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type") 
return <result> {data($item/attribute[@value = "Adjustment"]/@value)} </result>
').exist('/result[ text()[1] = "Adjustment"]') = 1
AND main.RequestMainXML.value( '(/request/status)[1]', 'varchar(255)') IN ('Canceled', 'Denied')
GROUP BY c.ROI_RequestDelivery_Seq, main.ROI_RequestMain_seq

/*calculate Payments & Adjustments*/
DECLARE @DeliveriesWithPaymentAndAdjustments table
(
	Total decimal (15,2),
	RequestDelivery_Seq int NOT NULL
	
);

INSERT INTO @DeliveriesWithPaymentAndAdjustments
SELECT
	SUM(CAST(REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(p.value('(./attribute[@name="amount"]/@value)[1]','varchar(max)'), 0.00), '$',''),')',''), '(', '-'),',','') AS decimal(15,2))),
	ROI_RequestDelivery_Seq
from Cabinet.dbo.ROI_RequestDeliveryCharges cc
cross apply RequestDeliveryChargesXML.nodes('//release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type="request-transaction"]/release-item') as p(p)
JOIN @RequestsWithLatestBillingInfo info ON info.RequestDeliveryCharges_Seq = cc.ROI_RequestDeliveryCharges_Seq 
GROUP BY ROI_RequestDelivery_Seq

/*Final select */

SELECT main.Created_Dt as 'Date Created',
main.ROI_RequestMain_seq AS 'Request ID',  
main.RequestMainXML.value( '(//request/requestor/name)[1]', 'varchar(255)') as 'Requestor Name',
main.RequestMainXML.value( '(//request/status)[1]', 'varchar(255)') as 'Request Status',
(COALESCE(REPLACE(REPLACE(REPLACE(charges.RequestDeliveryChargesXML.value('(//release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name="total-request-cost"]/@value)[1]', 'varchar(20)'),')',''),'(', '-'), ',', '') , '$0.00')) AS 'Total Charges',
paymentsAndAdj.total as 'Total Payments/Adjustments',
CAST((COALESCE(REPLACE(REPLACE(REPLACE(REPLACE(main.RequestMainXML.value( '(//request/balance-due)[1]', 'varchar(20)'),')',''),'(', '-'), ',', '') , '$', ''), '0.00')) as decimal(15,2))AS 'Balance Due',
'Old ROI' = CASE 
					WHEN 
					main.RequestMainXML.value('(/request/requestor/type-name)[1]','varchar(50)') = 'Migration' COLLATE SQL_Latin1_General_CP1_CI_AS
					AND main.RequestMainXML.value('(/request/request-reason)[1]','varchar(256)') = 'Migration' COLLATE SQL_Latin1_General_CP1_CI_AS
					THEN 'Y' ELSE 'N' END 
from  @RequestsWithLatestBillingInfo info
JOIN Cabinet.dbo.ROI_RequestDeliveryCharges charges  ON info.RequestDeliveryCharges_Seq = charges.ROI_RequestDeliveryCharges_Seq
JOIN @DeliveriesWithPaymentAndAdjustments paymentsAndAdj ON paymentsAndAdj.RequestDelivery_Seq =  charges.ROI_RequestDelivery_Seq
JOIN Cabinet.dbo.ROI_RequestMain main on info.RequestMain_Seq = main.ROI_RequestMain_Seq



