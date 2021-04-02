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
* SQL script Name: Requests_Released_without_an_Invoice
* Description:   
* The script checks which requests meet this condition (request is released without a invoice); 
* List fields to display: Date Created, Last Updated, Requestor Name and Type, Request ID, Request Status, Total Request Cost, Balance Due
*
*
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	I.Politykina			01/31/2013	Created
*	Bhanu Vanteru			03/08/2013	CR377838
*	Bhanu Vanteru			03/14/2013	CR378093
*	Bhanu Vanteru			03/22/2013	CR 378,808: Removed Modified_By as a check to identify c-xml request
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;

select
	rm.Created_dt as 'Date Created',
	rm.Modified_Dt as 'Last Updated',
	rm.RequestMainXml.value('(/request/requestor/name)[1]', 'varchar(max)') as 'Requestor Name',
	rm.RequestMainXml.value('(/request/requestor/type-name)[1]', 'varchar(max)') as 'Requestor Type',
	rm.ROI_RequestMain_Seq as 'Request ID',
	rm.RequestMainXml.value('(/request/status)[1]', 'varchar(max)') as 'Request Status',
	ISNULL(rdc.RequestDeliveryChargesXML.value('(/release-item/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name = "total-request-cost"]/@value)[1]', 'varchar(max)'),'') as  'Total Request Cost',
	ISNULL(REPLACE(REPLACE(rdc.RequestDeliveryChargesXML.value('(/release-item/release-item[@type="charges"]/release-item[@type = "release-info"]/attribute[@name = "balance-due"]/@value)[1]', 'varchar(max)'), '(','-'),')', ''),'') as 'Balance Due',
	'OLD ROI' = CASE 
					WHEN 
					rm.RequestMainXML.value('(/request/requestor/type-name)[1]','varchar(max)') = 'Migration' 
					AND rm.RequestMainXML.value('(/request/request-reason)[1]','varchar(max)') = 'Migration'
					THEN 'Y' ELSE 'N' END,
	tmp.NumOfReleases as 'Number of releases',
	tmp.NumOfInvoices as 'Number of invoices'
from cabinet.dbo.ROI_RequestMain rm
join
(
	select m.ROI_RequestMain_Seq, count(distinct(d.ROI_RequestDelivery_Seq)) as NumOfReleases, sum(isnull(cast(c.isInvoiced as int),0)) as NumOfInvoices
	from Cabinet.dbo.ROI_RequestMain m
	JOIN Cabinet.dbo.ROI_RequestDelivery d ON  m.ROI_RequestMain_Seq = d.ROI_RequestMain_Seq
	cross apply RequestDeliveryXML.nodes('/release') as p(p)
	LEFT JOIN Cabinet.dbo.ROI_RequestDeliveryCharges c ON d.ROI_RequestDelivery_Seq = c.ROI_RequestDelivery_Seq
	where (p.value('@is-released','varchar(10)')='True' or p.value('@is-released','varchar(10)')='true')
	group by m.ROI_RequestMain_Seq
	having count(distinct(d.ROI_RequestDelivery_Seq)) > sum(isnull(cast(c.isInvoiced as int),0)) 
) tmp
on rm.ROI_RequestMain_Seq=tmp.ROI_RequestMain_Seq
join cabinet.dbo.ROI_RequestDelivery rd on rm.ROI_RequestMain_Seq = rd.ROI_RequestMain_Seq
left join cabinet.dbo.ROI_RequestDeliveryCharges rdc on rd.ROI_RequestDelivery_Seq = rdc.ROI_RequestDelivery_Seq and rdc.isInvoiced=0
and rdc.ROI_RequestDeliveryCharges_Seq = (select top 1 ROI_RequestDeliveryCharges_Seq from cabinet.dbo.ROI_RequestDeliveryCharges WHERE ROI_RequestDelivery_Seq IN (SELECT ROI_RequestDelivery_Seq FROM Cabinet.dbo.ROI_RequestDelivery WHERE ROI_RequestMain_Seq = rm.ROI_RequestMain_Seq ) and isInvoiced=0 ORDER By ROI_RequestDeliveryCharges_Seq DESC)
where (rdc.ROI_RequestDeliveryCharges_Seq is not null or not exists (select 1 from cabinet.dbo.ROI_RequestDeliveryCharges where ROI_RequestDelivery_Seq = rd.ROI_RequestDelivery_Seq))
order by rm.ROI_RequestMain_Seq
