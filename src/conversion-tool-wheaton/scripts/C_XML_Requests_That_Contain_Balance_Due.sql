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
* NOTES: 
* Revision History:
*	Name					Date		Changes
*	---------				-------		-------------
* 	Bhanu Vanteru			02/19/2013	Created
*	Bhanu Vanteru			03/22/2013	CR 378,808: Removed Modified_By as a check to identify c-xml request
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;

select
	MAX(Modified_Dt) as 'Last Modified date', COUNT(ROI_RequestMain_Seq) as 'Total Number of Requests'
from cabinet.dbo.ROI_RequestMain rm
cross apply RequestMainXML.nodes('/request') as p(p)
where p.value('(/request/requestor/type-name)[1]','varchar(max)') = 'Migration'
and p.value('(/request/request-reason)[1]','varchar(max)') = 'Migration'
