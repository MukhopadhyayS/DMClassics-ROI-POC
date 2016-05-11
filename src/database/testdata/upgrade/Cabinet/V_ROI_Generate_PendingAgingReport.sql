USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_PendingAgingReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_PendingAgingReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_PendingAgingReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_PendingAgingReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_Generate_PendingAgingReport]
@requestorTypes xml,
@facilityNames xml,
@fromDate datetime,
@toDate datetime,
@currentDate datetime

AS
--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))
Declare @Facilities table (Data varchar(max))
--Fill the table with requestor types
Insert @ReqTypes (Data)
SELECT
	ReqTypeTable.ReqTypes.value('@value','VARCHAR(max)')
FROM
	@requestorTypes.nodes('/ListContent/requestor-type') AS ReqTypeTable(ReqTypes)
--Fill the table with requestor types
Insert @Facilities (Data)
SELECT
	FacilityTable.Facility.value('@value','VARCHAR(max)')
FROM
	@facilityNames.nodes('/ListContent/facility') AS FacilityTable(Facility)
-- data delimiter is @
-- entry delimiter is ;

select * from

(
(select
	   cast(pat.aa. value('(patient/@facility)[1]', 'VARCHAR(max)') as varchar(8000)) as fac,
	   cast(RequestMainXML.query('data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	   cast(RequestMainXML.query('data(request/requestor/name)') as varchar(8000) ) requestor_name,
	   cast(roi_requestmain_seq as int) as request_id,

	   agingRange = case
    					when  datediff (day,modified_dt, @currentDate) between 0 and 5 then '0-5 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 6 and 10 then '6-10 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 11 and 15 then '11-15 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 16 and 20 then '16-20 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 21 and 25 then '21-25 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 26 and 30 then '26-30 days pending'
    					when  datediff (day,modified_dt, @currentDate) > 30 then '30+ days pending'
    					else  'days Pending'
    					END

FROM ROI_requestmain req
      CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)

WHERE
		pat.aa.value( '@source', 'VARCHAR(max)') = 'hpf'
		and RequestMainXML.value('request[1]/status[1]', 'varchar(max)') = 'Pended'
		and pat.aa.value( '(patient/@facility)[1]', 'VARCHAR(max)') IN (select * from @Facilities)
	    and (created_dt between @fromDate and @toDate)
	    and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))

)
UNION
(select
		cast(pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') as varchar(8000)) as facility,
		cast(RequestMainXML.query( 'data(request/requestor/type-name)') as varchar(8000)) requestor_type ,
	    cast(RequestMainXML.query( 'data(request/requestor/name)') as varchar(8000)) requestor_name,
	    cast(roi_requestmain_seq as int) as request_id,

	   agingRange = case
    					when  datediff (day,modified_dt, @currentDate) between 0 and 5 then '0-5 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 6 and 10 then '6-10 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 11 and 15 then '11-15 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 16 and 20 then '16-20 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 21 and 25 then '21-25 days pending'
    					when  datediff (day,modified_dt, @currentDate) between 26 and 30 then '26-30 days pending'
    					when  datediff (day,modified_dt, @currentDate) > 30 then '30+ days pending'
    					else  'days Pending'
    					END

 from roi_requestmain req
 CROSS APPLY RequestMainXML.nodes('request/item') pat(aa)

WHERE pat.aa.value( '@source', 'VARCHAR(max)') = 'roi'
	 and RequestMainXML.value('request[1]/status[1]', 'varchar(max)') = 'Pended'
	 and pat.aa.value('(patient/facility/text())[1]', 'VARCHAR(max)') IN (select * from @Facilities)
	 and (created_dt between @fromDate and @toDate)
	 and (RequestMainXML.value('request[1]/requestor[1]/type-name[1]', 'varchar(max)') in (select * from @ReqTypes))

)
)  res

PIVOT
( count([request_id]) for [agingRange] in
   (
      [0-5 days pending] ,[6-10 days pending] ,[11-15 days pending] ,
      [16-20 days pending] ,[21-25 days pending],[26-30 days pending],[30+ days pending]
    )

) Pvt
--GROUP BY fac, requestor_type, requestor_Id, requestor_name
GO
GRANT EXECUTE ON [dbo].[ROI_Generate_PendingAgingReport] TO [IMNET]
GO
