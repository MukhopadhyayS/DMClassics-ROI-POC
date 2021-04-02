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
*  SQL script Name: Requests_Invoiced_but_not_Released.sql
*  Description: 
*  Displays the following for pre-migration to find requests which were invoiced, but not released
*
*	1.Request ID
*	2.Requestor Name
*	3.Request Status
*	4.Number of Invoices
*	5.Invoice Numbers(comma separated list)
*	6.Total Payments/Adjustments
*
* NOTES: 
* Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	I.Politykina			06/27/2013		Created;CR #382114 
* 	I.Politykina			06/28/2013		Fix per requirement update for the CR
* 	I.Politykina			07/11/2013		Fix per CR #382524:Beta 3 - Shannon: Pre-conversion script needs extra column to flag Old ROI (C-XML) requests
* 	I.Politykina			07/17/2013		Fix per CR #382587:Beta 3 - Shannon - ROI Conversion - Pre-migration script - Requests_Invoiced_but_not_Released_Report - Incorrect Values
* 	I.Politykina			07/23/2013		Fix per CR #382857:ROI-Conversion - Premigration - 'Requests_Invoiced_but_not_Released_Report' - The requests with only prebill should not be displayed in the result
*****************************************************************/

SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;
DECLARE @RequestsWithInvoicesNotReleased table(
		RequestMain_seq int NOT NULL,
		RequestDeliveryCharges_Seq int NOT NULL, 
		RequestDelivery_Seq int NOT NULL
		);

INSERT INTO @RequestsWithInvoicesNotReleased 
SELECT main.ROI_RequestMain_seq,
charges.ROI_RequestDeliveryCharges_Seq,
delivery.ROI_RequestDelivery_Seq
FROM Cabinet.dbo.ROI_RequestMain main WITH (NOLOCK)
JOIN Cabinet.dbo.ROI_RequestDelivery delivery WITH (NOLOCK) ON  main.ROI_RequestMain_Seq = delivery.ROI_RequestMain_Seq
cross apply RequestDeliveryXML.nodes('/release') as p(p)
JOIN Cabinet.dbo.ROI_RequestDeliveryCharges charges WITH (NOLOCK) ON delivery.ROI_RequestDelivery_Seq = charges.ROI_RequestDelivery_Seq
WHERE (p.value('@is-released','varchar(10)')='false' COLLATE SQL_Latin1_General_CP1_CI_AS ) AND charges.isInvoiced = 1 
AND charges.RequestDeliveryChargesXML.value('(/release-item/attribute[@name = "type"]/@value)[1]','varchar(255)') ='Invoice' COLLATE SQL_Latin1_General_CP1_CI_AS



DECLARE @requestsWithAggregatedInvoices table(RequestMain_seq int NOT NULL,	invoices varchar(1000));

;WITH x AS 
(
  SELECT RequestMain_seq,RequestDeliveryCharges_Seq, rn = ROW_NUMBER() OVER
  (PARTITION BY [RequestMain_seq] ORDER BY [RequestMain_seq])
  FROM @RequestsWithInvoicesNotReleased
)
INSERT INTO @requestsWithAggregatedInvoices
SELECT [RequestMain_seq], STUFF( (SELECT ', ' + CONVERT(VARCHAR(12),RequestDeliveryCharges_Seq)    FROM x AS x2 WHERE x2.[RequestMain_seq] = x.[RequestMain_seq]  ORDER BY rn DESC FOR XML PATH('') ), 1, 2, '')
FROM x  GROUP BY [RequestMain_seq];

DECLARE @DeliveriesWithPaymentAndAdjustments table(
		Total decimal (15,2),
		RequestDelivery_Seq int NOT NULL
		);

INSERT INTO @DeliveriesWithPaymentAndAdjustments
SELECT
	SUM(CAST(REPLACE(REPLACE(REPLACE(REPLACE(ISNULL(p.value('(./attribute[@name="amount"]/@value)[1]','varchar(max)'), 0.00), '$',''),')',''), '(', '-'),',','') AS decimal(15,2))),
	ROI_RequestDelivery_Seq
from Cabinet.dbo.ROI_RequestDeliveryCharges cc
cross apply RequestDeliveryChargesXML.nodes('//release-item[@type="billing-info"]/release-item[@type="charges"]/release-item[@type="request-transaction"]/release-item') as p(p)
where ROI_RequestDelivery_Seq IN (SELECT RequestDelivery_Seq FROM @RequestsWithInvoicesNotReleased dd
JOIN Cabinet.dbo.ROI_RequestDeliveryCharges c ON c.ROI_RequestDelivery_Seq =dd.RequestDelivery_Seq )
AND cc.isInvoiced = 0
GROUP BY ROI_RequestDelivery_Seq

SELECT  
main.Created_Dt as 'Date Created',
main.ROI_RequestMain_seq AS 'Request ID',  
main.RequestMainXML.value( '(/request/requestor/name)[1]', 'varchar(255)') as 'Requestor Name',
main.RequestMainXML.value( '(/request/status)[1]', 'varchar(255)') as 'Request Status', 
RTRIM(requestsWithAggregatedInvoices.invoices) as 'Invoice numbers',
COALESCE((SELECT Total FROM @DeliveriesWithPaymentAndAdjustments paa WHERE paa.RequestDelivery_Seq = recentInvoiceWithRequestNumber.Delivery_Seq), 0.00) as 'Total Payments/Adjustments',
CAST((COALESCE(REPLACE(REPLACE(REPLACE(REPLACE(main.RequestMainXML.value( '(/request/balance-due)[1]', 'varchar(20)'),')',''),'(', '-'), ',', '') , '$', '') , '0.00')) as decimal(15,2))AS 'Balance Due',
'OLD ROI' = CASE 
					WHEN 
					main.RequestMainXML.value('(/request/requestor/type-name)[1]','varchar(50)') = 'Migration' COLLATE SQL_Latin1_General_CP1_CI_AS
					AND main.RequestMainXML.value('(/request/request-reason)[1]','varchar(256)') = 'Migration' COLLATE SQL_Latin1_General_CP1_CI_AS
					THEN 'Y' ELSE 'N' END
				
FROM [cabinet].[dbo].[ROI_RequestMain] main WITH (NOLOCK)
JOIN (SELECT RequestMain_seq, RequestDelivery_Seq as Delivery_Seq
FROM @RequestsWithInvoicesNotReleased
GROUP BY RequestMain_seq,RequestDelivery_Seq) as recentInvoiceWithRequestNumber ON recentInvoiceWithRequestNumber.RequestMain_seq = main.ROI_RequestMain_seq
JOIN  @requestsWithAggregatedInvoices requestsWithAggregatedInvoices ON requestsWithAggregatedInvoices.RequestMain_seq = main.ROI_RequestMain_seq
