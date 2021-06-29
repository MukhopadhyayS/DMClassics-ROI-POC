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
* SQL script Name: Request_That_Contain_Refunds
* Description:   
*	The scrip checks if requests have debit adjustments and a positive balance due.
*	The list of columns in resultset: Date Created, Last Updated, Requestor Name and Type, Request ID, Request Status, Total Request Cost, Balance Due
*
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	I.Politykina			01/31/2013	Created
*	Bhanu Vanteru			03/08/2013	CR377838
*	Bhanu Vanteru			03/22/2013	CR 378,808: Removed Modified_By as a check to identify c-xml request
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;

SELECT 
	m.Created_dt as 'Date Created',
	m.Modified_Dt as 'Last Updated',
	m.RequestMainXml.value('(/request/requestor/name)[1]', 'varchar(max)') as 'Requestor Name',
	m.RequestMainXml.value('(/request/requestor/type-name)[1]', 'varchar(max)') as 'Requestor Type',
	m.ROI_RequestMain_Seq as 'Request ID',
	m.RequestMainXml.value('(/request/status)[1]', 'varchar(max)') as 'Request Status',
	ISNULL(RequestDeliveryChargesXML.value('(/release-item/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name = "total-request-cost"]/@value)[1]', 'varchar(max)'),'') as 'Total Request Cost',
	ISNULL(RequestDeliveryChargesXML.value('(/release-item/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name = "balance-due"]/@value)[1]', 'varchar(max)'),'') as 'Balance Due',
	'OLD ROI' = CASE 
					WHEN 
					m.RequestMainXML.value('(/request/requestor/type-name)[1]','varchar(max)') = 'Migration' 
					AND m.RequestMainXML.value('(/request/request-reason)[1]','varchar(max)') = 'Migration'
					THEN 'Y' ELSE 'N' END
FROM Cabinet.dbo.ROI_RequestMain m
JOIN Cabinet.dbo.ROI_RequestDelivery d ON  m.ROI_RequestMain_Seq = d.ROI_RequestMain_Seq
JOIN Cabinet.dbo.ROI_RequestDeliveryCharges c ON d.ROI_RequestDelivery_Seq = c.ROI_RequestDelivery_Seq AND c.isInvoiced = 0 
AND RequestDeliveryChargesXML.query('
for $item in /release-item/release-item[@type="charges"]/release-item[@type = "request-transaction"]/release-item
where ($item/attribute/@name = "transaction-type" and $item/attribute/@value = "Adjustment") 
return <result> {data($item/attribute[@name = "is-debit"]/@value)} </result>
').exist('/result[ text()[1] = "True"]') = 1
AND c.Modified_Dt = (SELECT TOP 1 Modified_Dt from Cabinet.dbo.ROI_RequestDeliveryCharges WHERE ROI_RequestDelivery_Seq IN (SELECT ROI_RequestDelivery_Seq 
FROM Cabinet.dbo.ROI_RequestDelivery WHERE ROI_RequestMain_Seq = m.ROI_RequestMain_Seq ) ORDER By Modified_Dt DESC )
WHERE CAST(REPLACE( REPLACE(REPLACE((RequestDeliveryChargesXML.value('(/release-item/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name = "balance-due"]/@value)[1]', 'varchar(max)')), '$', ''), ')', ''), '(', '-' ) as decimal(18,2)) > 0