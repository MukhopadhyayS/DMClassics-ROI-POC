USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_GenerateOutstandingInvoiceBalanceReport]    Script Date: 07/06/2009  ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_GenerateOutstandingInvoiceBalanceReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_GenerateOutstandingInvoiceBalanceReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_GenerateOutstandingInvoiceBalanceReport]    Script Date: 07/06/2009 14:57:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[ROI_GenerateOutstandingInvoiceBalanceReport]
@requestStatus xml,
@facilityNames xml,
@fromDate datetime,
@toDate datetime,
@toDaysDate datetime
AS

--Create a memory table to hold requestor types
Declare @ReqStatus table (Data varchar(max))
Declare @Facilities table (Data varchar(max))

Declare @facility varchar(max),@names varchar(max), @requestor_type varchar(max), @requestor_name varchar(max), @requestorType_id varchar(max), @agingRange varchar(max), @fac varchar(max),@name varchar(max), @request_id int,@amount_paid varchar(max),@total_cost varchar(max),@balance varchar(max), @invoice_no varchar(max), @invoice_date datetime,@vippatients varchar(max),@vippatient varchar(max)
Declare @TempResults table (Facility varchar(max), PatientName varchar(max), IsVip varchar(max), RequestorType varchar(max), RequestorName varchar(max),
RequestorTypeId varchar(max), InvoiceNo varchar(max), InvoiceDate datetime, TotalCost varchar(max), Payment varchar(max), Balance varchar(max),PendingAge varchar(max), RequestId int)
--Fill the table with Status
Insert @ReqStatus (Data)
SELECT
	ReqStatusTable.ReqStatus.value('@value','VARCHAR(max)')
FROM
	@requestStatus.nodes('/ListContent/status') AS ReqStatusTable(ReqStatus)
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
													where $items/@type[.="requestItem"]
													return
        											  for $patientitems in $items/release-item
        										        return
								   					      for $attributes in $patientitems/attribute
													      where $attributes/@name[.="patientname"]
												            return concat(string($attributes/@value),"@")')
        											        as varchar(8000))names,
			  cast(requestdeliverychargesxml.query('for $items in release-item/release-item
													where $items/@type[.="requestItem"]
													return
        											  for $patientitems in $items/release-item
        										        return
								   					      for $attributes in $patientitems/attribute
													      where $attributes/@name[.="vippatient"]
												            return concat(string($attributes/@value),"@")')
        											        as varchar(8000))vippatients,
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
														string("$0")') as varchar(8000)) amountPaid,

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

			datediff (day,charges.created_dt, @toDaysDate) outstanding,
            charges.roi_requestdeliverycharges_seq invoice_no,
			charges.created_dt invoice_date

	        FROM ROI_RequestMain request, ROI_REQUESTDELIVERY delivery, ROI_REQUESTDELIVERYCHARGES charges,
            (select roi_requestdelivery_seq as delId, MAX(modified_dt) as maxDt from roi_requestdeliverycharges group by roi_requestdelivery_seq) as res1
			WHERE request.ROI_RequestMain_Seq = delivery.ROI_RequestMain_Seq
            AND delivery.roi_requestdelivery_seq = charges.roi_requestdelivery_seq
            AND charges.modified_dt = res1.maxDt
			AND res1.delId = charges.roi_requestdelivery_seq
			AND (delivery.modified_dt between @fromDate and @toDate)
			AND	charges.isInvoiced = 1
            AND requestmainxml.value('request[1]/status[1]', 'varchar(max)') IN (select * from @ReqStatus)
			AND (cast(requestdeliverychargesxml.query('for $items in release-item/attribute
														     where $items/@name[.="type"]
													         return string($items/@value)')
															 as varchar(8000))='Invoice')

OPEN results
FETCH NEXT FROM results
INTO @facility, @names, @vippatients, @requestor_type, @requestor_name,@requestorType_id,@request_id,@total_cost,@amount_paid,@balance,@agingRange,@invoice_no,@invoice_date

WHILE @@FETCH_STATUS = 0
BEGIN
    DECLARE facilities CURSOR FOR
            SELECT * FROM cabinet.dbo.splitUtil(substring(@facility, 0, len(@facility)),'@')
    OPEN facilities
    FETCH NEXT FROM facilities
    INTO @fac

    DECLARE names CURSOR FOR
            SELECT * FROM cabinet.dbo.splitUtil(substring(@names, 0, len(@names)),'@')
    OPEN names
    FETCH NEXT FROM names
    INTO @name

    DECLARE vippatients CURSOR FOR
            SELECT * FROM cabinet.dbo.splitUtil(substring(@vippatients, 0, len(@vippatients)),'@')
    OPEN vippatients
    FETCH NEXT FROM vippatients
    INTO @vippatient

    WHILE @@FETCH_STATUS = 0

        BEGIN
            INSERT INTO @TempResults values(@fac,@name,@vippatient,@requestor_type,@requestor_name,@requestorType_id,@invoice_no,@invoice_date,@total_cost,@amount_paid,@balance,@agingRange,@request_id)

	FETCH NEXT FROM facilities
    INTO @fac

    FETCH NEXT FROM names
    INTO @name

    FETCH NEXT FROM vippatients
    INTO @vippatient

    END
    CLOSE facilities
    DEALLOCATE facilities
    CLOSE names
    DEALLOCATE names
    CLOSE vippatients
    DEALLOCATE vippatients

FETCH NEXT FROM results
INTO @facility, @names, @vippatients, @requestor_type, @requestor_name, @requestorType_id, @request_id,@total_cost,@amount_paid,@balance,@agingRange,@invoice_no,@invoice_date
END
CLOSE results
DEALLOCATE results

SELECT requestid, cast(facility as varchar(8000)) as facility, cast(requestortype as varchar(8000)) as requestorType,cast(requestorname as varchar(8000)) as requestorname,cast(requestorTypeId as varchar(8000)) as requestorTypeId,cast(invoiceno as varchar(8000)) invoiceNo, cast(invoiceDate as datetime) invoiceDate,cast(TotalCost as varchar(8000)) totalcost,cast(payment as varchar(8000)) payment,cast(balance as varchar(8000)) balance,cast(pendingage as varchar(8000)) pendingage,cast(PatientName as varchar(8000)) PatientName, cast(IsVip as varchar(8000)) as IsVip
FROM @TempResults
WHERE facility IN (select * from @Facilities)
GROUP BY requestid, facility, requestorTypeId, requestorType, requestorname, invoiceno, invoiceDate, TotalCost, payment, balance, pendingage, PatientName, IsVip
ORDER BY facility asc

GO
GRANT EXECUTE ON [dbo].[ROI_GenerateOutstandingInvoiceBalanceReport] TO [IMNET]
GO