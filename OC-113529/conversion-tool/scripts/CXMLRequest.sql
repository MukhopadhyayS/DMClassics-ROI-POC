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
* SQL script Name: CXMLRequest.sql
* Description:   
*	Displays the following for pre-migration and post migration requests to validate CXML requests
*	1.	Total number of ROI requests which meet the C-XML condition (reason=migration, requestor_type=migration)
*	2.	Total number of ROI requests which are C-XML, released but not invoiced 
*	3.	Total number of ROI requests which are C-XML but are not released
*
* NOTES: 
* Revision History:
*	Name					Date			Changes
*	---------				-------			-------------
* 	I.Politykina			05/12/2013		Created
* 	I.Politykina			05/15/2013		Updated per changed requirements
*	I.Politykina			05/21/2013		Fixed 'Total Classic Requests which are released' and 'Total Classic Requests which are not released' calculations.
****************************************************************
*/
SET QUOTED_IDENTIFIER ON;
SET NOCOUNT ON;

DECLARE @totalCXMLreq_b int, @totalCXMLreq_a int,
@totalCXMLreq_released_but_not_invoiced_b int, @totalCXMLreq_Not_released_b int,@totalCXMLreq_Not_released_a int,
@totalCXMLreq_Released_b int,@totalCXMLreq_Released_a int

;WITH ConvertedCXMLData AS(SELECT core.* FROM  cabinet.dbo.ROI_RequestCore core WITH (NOLOCK)
JOIN cabinet.dbo.ROI_RequestMain main ON  main.ROI_RequestMain_Seq =  core.ROI_RequestCore_Seq 
)

SELECT 


@totalCXMLreq_b = (SELECT COUNT(*) FROM  cabinet.dbo.ROI_RequestMain main WITH (NOLOCK)
WHERE UPPER(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)')) = 'MIGRATION'
AND UPPER(main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)')) = 'MIGRATION')
 , 
@totalCXMLreq_a = (SELECT COUNT(*) from [cabinet].[dbo].[ROI_RequestCoreRequestor] rcr  WITH (NOLOCK)
JOIN ConvertedCXMLData core ON core.ROI_RequestCore_Seq = rcr.ROI_RequestCore_Seq
where UPPER(rcr.[RequestorTypeName])= 'MIGRATION' AND UPPER(core.ROI_Reason) = 'MIGRATION'),
 

@totalCXMLreq_Not_released_b =  (SELECT COUNT(*) from cabinet.dbo.ROI_RequestMain main  WITH (NOLOCK)
WHERE  NOT  EXISTS (SELECT [ROI_RequestMain_Seq] FROM [cabinet].[dbo].[ROI_RequestDelivery]  WITH (NOLOCK) WHERE main.ROI_RequestMain_Seq = [ROI_RequestMain_Seq]
AND UPPER(RequestDeliveryXML.value('(/release/@is-released)[1]', 'varchar(10)')) = 'TRUE')
AND ( UPPER(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)')) = 'MIGRATION'
AND UPPER(main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)')) = 'MIGRATION')
) ,

@totalCXMLreq_Not_released_a = ( SELECT COUNT(*) FROM ConvertedCXMLData c
JOIN [cabinet].[dbo].[ROI_RequestCoreRequestor] rcr  WITH (NOLOCK) ON rcr.ROI_RequestCore_Seq = c.ROI_RequestCore_Seq
WHERE NOT EXISTS (SELECT  delivery.ROI_RequestCore_Seq FROM cabinet.dbo.ROI_RequestCoreDelivery delivery  WITH (NOLOCK)
WHERE delivery. ROI_RequestCore_Seq =  c.ROI_RequestCore_Seq )
 AND UPPER(c.ROI_Reason) = 'MIGRATION' AND  UPPER(rcr.RequestorTypeName) = 'MIGRATION'
), 

@totalCXMLreq_Released_b =(SELECT COUNT(*) from cabinet.dbo.ROI_RequestMain main  WITH (NOLOCK)
WHERE  EXISTS (SELECT [ROI_RequestMain_Seq] FROM [cabinet].[dbo].[ROI_RequestDelivery]  WITH (NOLOCK) WHERE main.ROI_RequestMain_Seq = [ROI_RequestMain_Seq]
AND UPPER(RequestDeliveryXML.value('(/release/@is-released)[1]', 'varchar(10)')) = 'TRUE')
AND ( UPPER(main.RequestMainXML.value('(/request/request-reason)[1]', 'varchar(256)')) = 'MIGRATION'
AND UPPER(main.RequestMainXML.value('(/request/requestor/type-name)[1]', 'varchar(256)')) = 'MIGRATION'))
,

@totalCXMLreq_Released_a = ( SELECT COUNT(*) FROM ConvertedCXMLData c
JOIN [cabinet].[dbo].[ROI_RequestCoreRequestor] rcr  WITH (NOLOCK) ON rcr.ROI_RequestCore_Seq = c.ROI_RequestCore_Seq
WHERE EXISTS (SELECT  delivery.ROI_RequestCore_Seq FROM cabinet.dbo.ROI_RequestCoreDelivery delivery WITH (NOLOCK)
WHERE delivery. ROI_RequestCore_Seq =  c.ROI_RequestCore_Seq)
 AND UPPER(c.ROI_Reason) = 'MIGRATION' AND  UPPER(rcr.RequestorTypeName) = 'MIGRATION'
)



SELECT 'Total Classic Requests', @totalCXMLreq_b AS 'Before Conversion', @totalCXMLreq_a  AS 'After Conversion',CASE WHEN @totalCXMLreq_b <> @totalCXMLreq_a THEN 'Fail' ELSE 'Pass' END AS 'Check'

UNION ALL
SELECT 'Total Classic Requests which are released', @totalCXMLreq_Released_b, @totalCXMLreq_Released_a,
CASE WHEN @totalCXMLreq_Released_b <> @totalCXMLreq_Released_a THEN 'Fail'
ELSE 'Pass'
END

UNION ALL
SELECT 'Total Classic Requests which are not released', @totalCXMLreq_Not_released_b, @totalCXMLreq_Not_released_a,
CASE WHEN @totalCXMLreq_Not_released_b <> @totalCXMLreq_Not_released_a THEN 'Fail'
ELSE 'Pass'
END

