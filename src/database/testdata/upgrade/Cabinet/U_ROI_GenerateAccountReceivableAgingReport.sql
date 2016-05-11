USE [cabinet]
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_GenerateAccountReceivableAgingReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_GenerateAccountReceivableAgingReport]


USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_GenerateAccountReceivableAgingReport]    Script Date: 05/16/2009 15:24:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ROI_GenerateAccountReceivableAgingReport]
@requestorTypes xml,
@facilityNames xml,
@fromDate datetime,
@toDate datetime,
@toDaysDate datetime
AS

--Create a memory table to hold requestor types
Declare @ReqTypes table (Data varchar(max))
Declare @Facilities table (Data varchar(max))

Declare @facility varchar(max), @requestor_type varchar(max), @requestor_name varchar(max), @requestor_id varchar(max), @agingRange varchar(max), @fac varchar(max), @request_id int,@adjustment_total varchar(max),@payment_total varchar(max),@total_cost varchar(max),@balance varchar(max)
Declare @TempResults table (Facility varchar(max), RequestorType varchar(max), RequestorName varchar(max),RequestorId varchar(max), PendingAge varchar(max), TotalCost varchar(max), Payment varchar(max), Adjustment varchar(max), Balance varchar(max), RequestId int)
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

DECLARE results CURSOR FOR

            SELECT
			  cast(requestdeliverychargesxml.query('for $items in release-item/release-item
													where $items/@type[.="requestItem"]
													return
        											  for $values in $items/release-item
														return
														  for $attributes in $values/attribute
													      where $attributes/@name[.="facility"]
															return concat(string($attributes/@value),"@")')
															as varchar(8000))facility,

              cast(requestdeliverychargesxml.query('for $items in release-item/release-item
									                where $items/@type[.="requestorInfo"]
													  return
												        for $attributes in $items/attribute
														where $attributes/@name[.="requestortypename"]
														  return string($attributes/@value)') as varchar(8000)) requestor_type,

			  cast(requestdeliverychargesxml.query('for $items in release-item/release-item
													where $items/@type[.="requestorInfo"]
													  return
														for $attributes in $items/attribute
														where $attributes/@name[.="requestorname"]
														  return string($attributes/@value)') as varchar(8000)) requestor_name,

			  cast(requestdeliverychargesxml.query('for $items in release-item/release-item
													where $items/@type[.="requestorInfo"]
													  return
														for $attributes in $items/attribute
														where $attributes/@name[.="requestorid"]
														  return string($attributes/@value)') as varchar(8000)) requestorType_id,

			  delivery.roi_requestMain_seq request_id,

			  cast(requestdeliverychargesxml.query('for $items in release-item/release-item
													where $items/@type[.="billing-info"]
													return
														for $values in $items/release-item
														where $values/@type[.="charges"]
														return
															if (count($values/release-item/@type[.="request-transaction"]) > 0) then
																for $transactions in $values/release-item
																where $transactions/@type[.="request-transaction"]
																return
																	for $attributes in $transactions/attribute
																	where $attributes/@name[.="adjustment-total"]
																	return string($attributes/@value)
															else
																string("$0")') as varchar(8000)) adjustment_total,

			cast(requestdeliverychargesxml.query('for $items in release-item/release-item
												  where $items/@type[.="billing-info"]
												  return
													for $values in $items/release-item
													where $values/@type[.="charges"]
													return
														if (count($values/release-item/@type[.="request-transaction"]) > 0) then
															for $transactions in $values/release-item
															where $transactions/@type[.="request-transaction"]
															return
																for $attributes in $transactions/attribute
																where $attributes/@name[.="payment-total"]
																return string($attributes/@value)
															else
																string("$0")') as varchar(8000)) payment_total,


            cast(requestdeliverychargesxml.query('for $items in release-item/release-item
                                                                where $items/@type[.="billing-info"]
                                                                return
                                                                for $charges in $items/release-item
                                                                where $charges/@type[.="charges"]
                                                                return
                                                                for $releaseInfo in $charges/release-item
                                                                where $releaseInfo/@type[.="release-info"]
                                                                return
                                                                for $attributes in $releaseInfo/attribute
                                                                where $attributes/@name[.="total-request-cost"]
                                                              return string($attributes/@value)'
                                                                ) as varchar(8000)) total_cost,

	           cast(requestdeliverychargesxml.query('for $items in release-item/release-item
                                                                where $items/@type[.="billing-info"]
                                                                return
                                                                for $charges in $items/release-item
                                                                where $charges/@type[.="charges"]
                                                                return
                                                                for $releaseInfo in $charges/release-item
                                                                where $releaseInfo/@type[.="release-info"]
                                                                return
                                                                for $attributes in $releaseInfo/attribute
                                                                where $attributes/@name[.="balance-due"]
                                                              return string($attributes/@value)'
                                                                ) as varchar(8000)) balance,

            agingRange = case
            								  when  datediff (day,charges.created_dt, @toDaysDate) between 0 and 30 then '0'
            								  when  datediff (day,charges.created_dt, @toDaysDate) between 30 and 60 then '30+'
            								  when  datediff (day,charges.created_dt, @toDaysDate) between 60 and 90 then '60+'
            								  when  datediff (day,charges.created_dt, @toDaysDate) between 90 and 120 then '90+'
            								  when  datediff (day,charges.created_dt, @toDaysDate) > 120 then '120+'
            								 else  'days Pending'
            								END

	        FROM ROI_REQUESTDELIVERYCHARGES charges, ROI_REQUESTDELIVERY delivery,
			(select max(roi_requestdelivery_seq) as delId, max(modified_dt) as maxDt, roi_requestmain_seq as reId from roi_requestdelivery group by roi_requestmain_seq) as res1,
		    (select roi_requestdelivery_seq as delId, MAX(modified_dt) as maxdt from roi_requestdeliverycharges rdc where rdc.isInvoiced = 1 group by roi_requestdelivery_seq) as res2

			WHERE res1.delId = charges.roi_requestdelivery_seq
			AND res1.reId = delivery.roi_Requestmain_seq
        	AND res2.maxDt = charges.modified_dt
			AND  (res1.maxDt between @fromDate and @toDate)
			AND	  charges.isInvoiced = 1
			AND (cast(requestdeliverychargesxml.query('for $items in release-item/attribute
														     where $items/@name[.="type"]
													         return string($items/@value)')
															 as varchar(8000))='Invoice')
            AND cast(requestdeliverychargesxml.query('for $items in release-item/release-item
									  where $items/@type[.="requestorInfo"]
									   return
										 for $attributes in $items/attribute
										 where $attributes/@name[.="requestortypename"]
									   return string($attributes/@value)') as varchar(8000)) IN (select * from @ReqTypes)

OPEN results
FETCH NEXT FROM results
INTO @facility, @requestor_type, @requestor_name, @requestor_id, @request_id,@adjustment_total,@payment_total,@total_cost,@balance,@agingRange

WHILE @@FETCH_STATUS = 0
BEGIN
    DECLARE facilities CURSOR FOR
            SELECT * FROM cabinet.dbo.splitUtil(substring(@facility, 0, len(@facility)),'@')
    OPEN facilities
    FETCH NEXT FROM facilities
    INTO @fac

    WHILE @@FETCH_STATUS = 0
        BEGIN
            INSERT INTO @TempResults values(@fac, @requestor_type, @requestor_name,@requestor_id,@agingRange,@total_cost,@payment_total,@adjustment_total,@balance,@request_id)
    FETCH NEXT FROM facilities
    INTO @fac
        END
        CLOSE facilities
        DEALLOCATE facilities

FETCH NEXT FROM results
INTO @facility, @requestor_type, @requestor_name, @requestor_id, @request_id,@adjustment_total,@payment_total,@total_cost,@balance,@agingRange
END
CLOSE results
DEALLOCATE results

SELECT cast(facility as varchar(8000)) as facility,
	   cast(requestortype as varchar(8000)) as requestorType,
       cast(requestorname as varchar(8000)) as requestorname,
       cast(requestorId as varchar(8000)) as requestorId,
	   cast(pendingage as varchar(8000)) pendingage,
       cast(TotalCost as varchar(8000)) totalcost,
	   cast(payment as varchar(8000)) payment,
	   cast(adjustment as varchar(8000)) adjustment,
       cast(balance as varchar(8000)) balance,
       cast(requestid as varchar(8000)) requestid
FROM @TempResults
WHERE facility IN (select * from @Facilities)
GROUP BY facility, requestorId,requestortype,requestorname,pendingage,TotalCost,payment,adjustment,balance,requestid
ORDER BY facility asc
GO
GRANT EXECUTE ON [dbo].[ROI_GenerateAccountReceivableAgingReport] TO [IMNET]
GO
