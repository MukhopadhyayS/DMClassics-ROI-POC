SET QUOTED_IDENTIFIER ON
GO

/* BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!
*
* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.
*
END-COPYRIGHT-COMMENT  Do not remove or modify this line! */

/***************************************************************************************************************
* Procedure             : ROI_MigrationEvents.sql
* Creation Date         : 05/26/2009
*					
* Written by            : RC	
*									
* Purpose               : Move data from Classic ROI to ROI13.5
*		
* Database              : CABINET 
*									
* Input Parameters      : NONE 
*	Name		Type		Description
* 	---------	-------		----------------
*
* Output Parameters     : NONE 
*   Name		Type        Description
*   ---------	----------- ----------------
*									
* Return Status         : <none>		
*									
* Usage                 : ROI_MigrationEvents.sql 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[AA_ROI_ClassicReleaseDetail]
*                       : [Cabinet].[dbo].[ROI_RequestMain]
*                       : [Cabinet].[dbo].[ROI_RequestEvent]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        05/26/2009  Created   
* MD Meersma	10/25/2010	Set Modified_DT to Created_DT as this is what is shown for the date of the migrated request
*
***************************************************************************************************************************************/
USE CABINET
GO
Print 'Starting event migration  at ' + cast(getdate() as varchar(20));

SET NOCOUNT ON
GO
--place the data in the table
INSERT Roi_RequestEvent
           (
           [Created_Dt]
           ,[Created_By_Seq]
           ,[ROI_RequestMain_Seq]
           ,[Name]
           ,[Description]
           ,[Modified_Dt]
           ,[Modified_By_Seq]
           )
    (
    SELECT 
        Convert(char(10),AA_ROI_ClassicReleaseDetail.ReqRel_ReleaseDate,101)   
        , AA_ROI_ClassicReleaseDetail.ReqRel_RequestId * -1
        , ROI_RequestMain.ROI_RequestMain_Seq
        , 'Documents Released'
        , 'patient count:1 document count:' + Cast(COUNT(DISTINCT AA_ROI_ClassicReleaseDetail.Documents_DocId) AS varchar(10)) + ' output method:[print/fax]'
        , Convert(char(10),AA_ROI_ClassicReleaseDetail.ReqRel_ReleaseDate,101)   
        , AA_ROI_ClassicReleaseDetail.ReqRel_RequestId * -1

    FROM         
        AA_ROI_ClassicReleaseDetail 
    INNER JOIN ROI_RequestMain 
        ON AA_ROI_ClassicReleaseDetail.ReqRel_RequestId = ROI_RequestMain.Created_By_Seq * - 1
   GROUP BY 
          AA_ROI_ClassicReleaseDetail.ReqRel_RequestId
        , ROI_RequestMain.ROI_RequestMain_Seq
        , AA_ROI_ClassicReleaseDetail.ReqRel_ReleaseDate
        , AA_ROI_ClassicReleaseDetail.ReqRel_OutputType
    )
Print 'Completed event migration  at ' + cast(getdate() as varchar(20));
