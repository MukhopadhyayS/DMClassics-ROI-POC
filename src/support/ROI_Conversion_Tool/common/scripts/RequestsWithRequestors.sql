/**************************************************************
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
* 	SQL script Name: RequestsWithRequestors
* 	Description:   
* 		The script: Display the following for pre-migration and post migration requests to validate requestors:
*		1.	Total number of requestors
*		2.	Compare total number of requests belonging to requestor (pre and post migration) for each requestor
*		3.	IF comparison numbers are not the same 
*		4.	Then display Requestor name, total number of requests (post and pre-migration)
*
* NOTES: 
* Revision History:
*	Name				Date		Changes
*	---------			-------		-------------
* 	I.Politykina		05/12/2013	Created
* 	I.Politykina		05/15/2013	Updated: using variable table instead of temporary table
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;


DECLARE @totalRequestors int, @totalRowsInTempTable int
SELECT @totalRequestors   = COUNT(*) from cabinet.dbo.ROI_Requestor  WITH (NOLOCK)


SELECT 'Total Requestors', CAST(@totalRequestors AS VARCHAR) AS 'Before Conversion', CAST(@totalRequestors AS VARCHAR) AS 'After Conversion',
(CASE WHEN @totalRequestors  = @totalRequestors
THEN 'Pass'
ELSE 'Fail'
END) AS 'Check'

UNION ALL
SELECT '', '', '', '' 

UNION ALL
SELECT 'Requestor Name', 'Total Requests ', 'Total Requests ', '' 

UNION ALL
SELECT RTRIM(LTRIM(requestor.NameFirst + ' '+ COALESCE(requestor.NameMiddle, '' ) + ' '+ requestor.NameLast)) AS RequestorName,
CAST(Request_Main_Before_Conversion.NumberOfRequests AS VARCHAR) as NumberOfRequests_Before ,  
CAST(Request_Core_After_Conversion.NumberOfRequests  AS VARCHAR) as NumberOfRequests_After,
CHECKColumn = (CASE WHEN Request_Main_Before_Conversion.NumberOfRequests <> Request_Core_After_Conversion.NumberOfRequests THEN 'Fail' ELSE 'Pass' END)

FROM [cabinet].[dbo].[ROI_Requestor] requestor  WITH (NOLOCK)
JOIN (SELECT COUNT(id)  AS NumberOfRequests, id FROM (
select RequestMainXml.value('(/request/requestor/@id)[1]', 'varchar(max)') as id FROM [cabinet].[dbo].[ROI_RequestMain] m
JOIN [cabinet].[dbo].[ROI_RequestCore] core ON  core.ROI_RequestCore_Seq = m.ROI_RequestMain_Seq) AS TT
GROUP By id) as Request_Core_After_Conversion  ON Request_Core_After_Conversion.ID = requestor.ROI_Requestor_Seq
JOIN (SELECT COUNT(reqMain.RequestorId) AS NumberOfRequests, reqMain.RequestorId  FROM (
  SELECT RequestMainXml.value('(/request/requestor/@id)[1]', 'varchar(max)') as RequestorId
  FROM [cabinet].[dbo].[ROI_RequestMain]  WITH (NOLOCK)) as reqMain 
GROUP By reqMain.RequestorId
) AS Request_Main_Before_Conversion ON Request_Main_Before_Conversion.RequestorId = requestor.ROI_Requestor_Seq






