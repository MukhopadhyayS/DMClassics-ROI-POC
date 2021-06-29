USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView]    Script Date: 11/11/2008  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView]    Script Date: 07/07/2009 16:41:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView]
@requestorTypes xml,
@userIds xml,
@disposition varchar(max),
@fromDate datetime,
@toDate datetime,
@fromStatus varchar(max)

AS
--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))
Declare @Ids table (Data varchar(max))

Declare @toStatus varchar(max)

Declare @Actor varchar(max), @RequestorType varchar(max), @RequestorName varchar(max), @TotalCount int, @DispositionCount int, @TAT int

Declare @TempResults table (Actor varchar(max), RequestorType varchar(max), TotalCount int, Disposition int, TAT int)

--Fill the table with requestor types
Insert @ReqTypes (Data)
SELECT
    Table1.col1.value('@value','VARCHAR(max)')
FROM
    @requestorTypes.nodes('/ListContent/requestor-type') AS Table1(col1)
--Fill the table with requestor types
Insert @Ids (Data)
SELECT
    Table1.col1.value('@value','VARCHAR(max)')
FROM
    @userIds.nodes('/ListContent/userId') AS Table1(col1)

--set @toStatus = substring(@disposition, 0, charindex(' ', @disposition))

if (@fromStatus = 'Received')

begin
select cast(y.actor as varchar(8000)) Actor,
       cast(y.requestor_type as varchar(8000)) RequestorType,
       '',--cast(y.requestor_name as varchar(max)) RequestorName,
       cast(count(x.requestId) as int) TotalCount,
       cast(count(y.id) as int) Disposition,
       cast(isnull(cast(sum(y.days)/cabinet.dbo.IsZero((count(y.id)), 1) as int),0) as int) AS TAT
from
(   select roi_requestmain_seq  requestId,
	req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') as reqType
	--req.RequestMainXML.value('request[1]/requestor[1]/name[1]', 'varchar(max)') as reqName
    from roi_requestmain req
    where
    req.requestmainxml.value('(request/receipt-date)[1]','datetime') between @fromDate and @toDate
    AND req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') IN (select * from @ReqTypes)
    AND req.created_by_seq in (SELECT * FROM @Ids)
) x inner join
(   select distinct req.roi_requestmain_seq as id ,
           req.requestmainxml.value('(request/receipt-date)[1]','datetime') as receiptDt,
           datediff(day,req.requestmainxml.value('(request/receipt-date)[1]','datetime'),
           result1.dt) days,
           cast(RequestMainXML.query('data(request/requestor/type-name)') as varchar(max)) requestor_type,
          -- cast(RequestMainXML.query('data(request/requestor/name)') as varchar(max)) requestor_name,
           usr.fullname as actor
    from roi_requestmain req
    inner join users usr
        on req.created_by_seq = usr.userinstanceid,

	(	select max(created_dt) as dt, ROI_RequestMain_Seq as requestId
		from ROI_RequestEvent
		where name like 'Change of Status'
		AND description LIKE ('%to ' + @disposition + '%')
		group by ROI_Requestmain_Seq
	 ) as result1
    where
    req.requestmainxml.value('(request/receipt-date)[1]','datetime') between @fromDate and @toDate
	AND req.roi_requestmain_seq = result1.requestId
    AND req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') IN (SELECT * FROM @ReqTypes)
    AND req.created_by_seq in (SELECT * FROM @Ids)
) y on x.requestId = y.id
group by y.actor,y.requestor_type,x.reqType
order by y.actor,y.requestor_type,x.reqType  desc
end

else

select cast(y.actor as varchar(8000)) Actor,
       cast(y.requestor_type as varchar(8000)) RequestorType,
       '',--cast(y.requestor_name as varchar(max)) RequestorName,
       cast(count(x.requestId) as int) TotalCount,
       cast(count(y.id) as int) Disposition,
       cast(isnull(cast(sum(y.days)/cabinet.dbo.IsZero((count(y.id)), 1) as int),0) as int) AS TAT
from
(   select roi_requestmain_seq  requestId,
	req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') as reqType
	--req.RequestMainXML.value('request[1]/requestor[1]/name[1]', 'varchar(max)') as reqName
    from roi_requestmain req
    where
    req.Created_Dt between @fromDate and @toDate
    AND req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') IN (select * from @ReqTypes)
    AND req.created_by_seq in (SELECT * FROM @Ids)
) x inner join
(   select distinct req.roi_requestmain_seq as id ,
           req.requestmainxml.value('(request/receipt-date)[1]','datetime') as receiptDt,
           datediff(day, req.Created_Dt, result1.dt) days,
           cast(RequestMainXML.query('data(request/requestor/type-name)') as varchar(max)) requestor_type,
           --cast(RequestMainXML.query('data(request/requestor/name)') as varchar(max)) requestor_name,
           usr.fullname as actor
    from roi_requestmain req
    inner join users usr
        on req.created_by_seq = usr.userinstanceid,

	(	select max(created_dt) as dt, ROI_RequestMain_Seq as requestId
		from ROI_RequestEvent
		where name like 'Change of Status'
		AND description LIKE ('%to ' + @disposition + '%')
		group by ROI_Requestmain_Seq
	 ) as result1

    where req.Created_Dt between @fromDate and @toDate
	AND req.roi_requestmain_seq = result1.requestId
    AND req.RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') IN (SELECT * FROM @ReqTypes)
    AND req.created_by_seq in (SELECT * FROM @Ids)

) y on x.requestId = y.id
group by y.actor,y.requestor_type,x.reqType
order by y.actor,y.requestor_type,x.reqType desc


SET ANSI_NULLS OFF
GO
GRANT EXECUTE ON [dbo].[ROI_Generate_TurnAroundTimeReport_SummaryView] TO [IMNET]
GO
