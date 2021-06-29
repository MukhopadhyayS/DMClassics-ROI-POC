USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_RequestStatusSummaryReport]    Script Date: 11/11/2008  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_RequestStatusSummaryReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_RequestStatusSummaryReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_RequestStatusSummaryReport]    Script Date: 04/20/2009 20:39:59 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ROI_Generate_RequestStatusSummaryReport]
@requestorTypes xml,
@requestStatus xml,
@fromDate datetime,
@toDate datetime
AS

--Create a memory table to hold requestor types
Declare @ReqTypes table (Types varchar(max))
Declare @ReqStatuses table (Statuses varchar(max))

--Fill the table with requestor types
Insert @ReqTypes (Types)
SELECT
	TempTable.Column1.value('@value','VARCHAR(max)')
FROM
	@requestorTypes.nodes('/ListContent/requestor-type') AS TempTable(Column1)

Insert @ReqStatuses (Statuses)
SELECT
	TempTable.Column1.value('@value','VARCHAR(max)')
FROM
	@requestStatus.nodes('/ListContent/request-status') AS TempTable(Column1)

SELECT cast(RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type,
       cast(RequestMainXML.query('data(request/requestor/name)') as varchar(8000)) requestor_name,
       roi_requestmain_seq request_id, cast(RequestMainXML.query('data(request/status)') as varchar(8000)) request_status

FROM ROI_RequestMain req
WHERE RequestMainXML.value('request[1]/status[1]', 'varchar(max)') IN (select * from @ReqStatuses)
AND created_dt between @fromDate and @toDate
AND RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') IN (select * from @ReqTypes)
ORDER BY requestor_type, requestor_name
GO
GRANT EXECUTE ON [dbo].[ROI_Generate_RequestStatusSummaryReport] TO [IMNET]
GO
