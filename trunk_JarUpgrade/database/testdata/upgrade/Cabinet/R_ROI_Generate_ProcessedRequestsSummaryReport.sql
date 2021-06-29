USE [cabinet]
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_ProcessedRequestsSummaryReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_ProcessedRequestsSummaryReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_ProcessedRequestsSummaryReport]    Script Date: 06/12/2009 14:14:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_ProcessedRequestsSummaryReport]
@requestorTypes xml,
@fromDate datetime,
@toDate datetime
AS
--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))

--Fill the table with requestor types
Insert @ReqTypes (Data)
SELECT
	ReqTypeTable.ReqTypes.value('@value','VARCHAR(max)')
FROM
	@requestorTypes.nodes('/ListContent/requestor-type') AS ReqTypeTable(ReqTypes)
-- data delimiter is @
-- entry delimiter is ;
select * from

(

(select cast(pat.aa. value( '(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as fac,
	    cast(RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    requestmainxml.value('request[1]/status[1]', 'varchar(max)') requestStatus,
	    roi_requestMain_seq request_id

FROM ROI_requestmain req
      CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
WHERE
	    pat.aa.value( '@source', 'VARCHAR(max)') = 'hpf'  and
		requestmainxml.value('request[1]/status[1]', 'varchar(max)') not in ('Logged')
		and (created_dt between @fromDate and @toDate)
		and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)
UNION
(select cast(pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as facility,
		cast(RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
		requestmainxml.value('request[1]/status[1]', 'varchar(max)') requestStatus,
	    cast(roi_requestmain_seq as int) as request_id

 from roi_requestmain req
	 CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)
WHERE
	  pat.aa.value( '@source', 'VARCHAR(max)') = 'roi' and
	  requestmainxml.value('request[1]/status[1]', 'varchar(max)') not in ('Logged')
	  and (created_dt between @fromDate and @toDate)
	  and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))
)
)  res

PIVOT

( count([request_id]) for [requestStatus] in
   (
      [Completed],[Canceled],[Denied],[Prebilled],[Pended]
   )

) Pvt_ROISearchLOV

SET ANSI_NULLS OFF

GO
GRANT EXECUTE ON [dbo].[ROI_Generate_ProcessedRequestsSummaryReport] TO [IMNET]
GO
