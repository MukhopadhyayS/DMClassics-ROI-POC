USE [cabinet]

--/****** Object:  StoredProcedure [dbo].[ROI_Generate_PostedPaymentsSummaryReport]    Script Date: 05/18/2009 15:05:13 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ROI_Generate_PostedPaymentsSummaryReport]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[ROI_Generate_PostedPaymentsSummaryReport]

USE [cabinet]
GO
/****** Object:  StoredProcedure [dbo].[ROI_Generate_PostedPaymentsSummaryReport]    Script Date: 06/05/2009 17:59:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ROI_Generate_PostedPaymentsSummaryReport]
@requestStatus xml,
@facilityNames xml,
@fromDate datetime,
@toDate datetime
AS

--Create a memory table to hold requestor types
Declare @ReqStatus table (Data varchar(max))
Declare @Facilities table (Data varchar(max))

Declare @oldFacility varchar(max), @NewFacility varchar(max)
Declare @facility varchar(max),@names varchar(max), @requestor_type varchar(max), @requestor_name varchar(max), @requestor_id varchar(max), @rStatus varchar(max), @payMethod varchar(max),@payAmt varchar(max), @request_id int,@amount_paid varchar(max),@total_cost varchar(max), @invoice_no varchar(max), @invoice_date datetime, @postedBy varchar(max),@paymentMethod varchar(max),@payment varchar(max),@fac varchar(max), @inputfacility varchar(max)
Declare @facility1 varchar(max), @requestor_name1 varchar(max), @requestor_id1 varchar(max), @rStatus1 varchar(max), @request_id1 int,@amount_paid1 varchar(max),@total_cost1 varchar(max), @invoice_no1 varchar(max), @invoice_date1 datetime, @postedBy1 varchar(max),@paymentMethod1 varchar(max),@payment1 varchar(max)
Declare @facility2 varchar(max), @requestor_name2 varchar(max), @requestor_id2 varchar(max), @rStatus2 varchar(max), @request_id2 int,@amount_paid2 varchar(max),@total_cost2 varchar(max), @invoice_no2 varchar(max), @invoice_date2 datetime, @postedBy2 varchar(max),@paymentMethod2 varchar(max),@payment2 varchar(max)

Declare @TempResults table (Facility varchar(max), RequestorName varchar(max),RequestorId varchar(max), InvoiceNo varchar(max), InvoiceDate datetime, TotalCost varchar(max), PaymentMethod varchar(max),Payment varchar(max),PaymentTotal varchar(max), PostedBy varchar(max), RequestStatus varchar(max), RequestId int)
Declare @FinalResults table (Facility1 varchar(max), RequestorName1 varchar(max),RequestorId1 varchar(max), InvoiceNo1 varchar(max), InvoiceDate1 datetime, TotalCost1 varchar(max), PaymentMethod1 varchar(max),Payment1 varchar(max),PaymentTotal1 varchar(max), PostedBy1 varchar(max), RequestStatus1 varchar(max), RequestId1 int)
Declare @Results table (Facility2 varchar(max), RequestorName2 varchar(max),RequestorId2 varchar(max), InvoiceNo2 varchar(max), InvoiceDate2 datetime, TotalCost2 varchar(max), PaymentMethod2 varchar(max),Payment2 varchar(max),PaymentTotal2 varchar(max), PostedBy2 varchar(max), RequestStatus2 varchar(max), RequestId2 int)

--Fill the table with Status
Insert @ReqStatus (Data)
SELECT
	ReqStatusTable.ReqStatus.value('@value','VARCHAR(max)')
FROM
	@requestStatus.nodes('/ListContent/request-status') AS ReqStatusTable(ReqStatus)
--Fill the table with requestor types
Insert @Facilities (Data)
SELECT
	FacilityTable.Facility.value('@value','VARCHAR(max)')
FROM
	@facilityNames.nodes('/ListContent/facility') AS FacilityTable(Facility)
-- data delimiter is @
-- entry delimiter is ;

DECLARE results1 CURSOR FOR

			SELECT DISTINCT

                cast(requestdeliverychargesxml.query('for $items in release-item/release-item
                                                    where $items/@type[.="requestItem"]
                                                    return
                                                      for $values in $items/release-item
                                                        return
                                                          for $attributes in $values/attribute
                                                          where $attributes/@name[.="facility"]
                                                            return concat(string($attributes/@value),",")')
                                                            as varchar(8000))facility,


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
                                       return string($attributes/@value)') as varchar(8000)) requestor_id,

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
                                                                for $charges in $items/release-item
                                                                where $charges/@type[.="charges"]
                                                                return
                                                                for $reqTrans in $charges/release-item
                                                                where $reqTrans/@type[.="request-transaction"]
                                                                return
                                                                for $payments in $reqTrans/release-item
                                                                return
                                                                for $attributes in $payments/attribute
                                                                where $attributes/@name[.="transaction-type"]
                                                                and $attributes/@value[.="Payment"]
                                                              return
                                                                for $attributes1 in $payments/attribute
                                                                where $attributes1/@name[.="payment-mode"]
                                                                return concat(string($attributes1/@value),"/")'
                                                                ) as varchar(8000))paymentMethod,

                cast(requestdeliverychargesxml.query('for $items in release-item/release-item
                                                                where $items/@type[.="billing-info"]
                                                                return
                                                                for $charges in $items/release-item
                                                                where $charges/@type[.="charges"]
                                                                return
                                                                if (count($charges/release-item/@type[.="request-transaction"]) > 0) then
                                                                    for $reqTrans in $charges/release-item
                                                                    where $reqTrans/@type[.="request-transaction"]
                                                                    return
                                                                    for $payments in $reqTrans/release-item
                                                                    return
                                                                    for $attributes in $payments/attribute
                                                                    where $attributes/@name[.="transaction-type"]
                                                                    and $attributes/@value[.="Payment"]
                                                                    return
                                                                    for $attributes1 in $payments/attribute
                                                                    where $attributes1/@name[.="amount"]
                                                                    return concat(string($attributes1/@value),"/")
                                                                else
                                                                    concat(string("$0"),"/")'
                                                                 ) as varchar(8000))paymentTotal,

                cast(requestdeliverychargesxml.query('for $items in release-item/release-item
                                                        where $items/@type[.="billing-info"]
                                                        return
                                                        for $charges in $items/release-item
                                                        where $charges/@type[.="charges"]
                                                        return
                                                        if (count($charges/release-item/@type[.="request-transaction"]) > 0) then
                                                            for $reqTrans in $charges/release-item
                                                            where $reqTrans/@type[.="request-transaction"]
                                                            return
                                                              for $attributes in $reqTrans/attribute
                                                              where $attributes/@name[.="payment-total"]
                                                           return string($attributes/@value)
                                                        else
                                                            string("$0")') as varchar(8000))payments,
            usr.fullname postedby,
            requestmainxml.value('request[1]/status[1]', 'varchar(max)') requestStatus,
            charges.roi_requestdeliverycharges_seq invoice_no,
            charges.created_dt invoice_date

            FROM ROI_RequestMain request, ROI_REQUESTDELIVERY delivery, ROI_REQUESTDELIVERYCHARGES charges,users usr,

            (select roi_requestdelivery_seq as delId, MAX(modified_dt) as maxDt from roi_requestdeliverycharges where isinvoiced = 1 group by roi_requestdelivery_seq) as res1,
            (select max(roi_requestdelivery_seq) as delyId, max(modified_dt) as maxDt, roi_requestmain_seq as reId from roi_requestdelivery group by roi_requestmain_seq) as res2

            WHERE request.ROI_RequestMain_Seq = delivery.ROI_RequestMain_Seq
            AND charges.modified_dt = res1.maxDt
            and res2.delyId = res1.delId
            AND res1.delId = charges.roi_requestdelivery_seq
            AND delivery.roi_requestdelivery_seq = charges.roi_requestdelivery_seq
            AND  (delivery.Modified_dt between @fromDate and @toDate)
            AND   charges.isInvoiced = 1
            AND requestmainxml.value('request[1]/status[1]', 'varchar(max)') IN (select * from @ReqStatus)
            AND charges.MODIFIED_BY_SEQ = USR.USERINSTANCEID

            AND (cast(requestdeliverychargesxml.query('for $items in release-item/attribute
                                                             where $items/@name[.="type"]
                                                             return string($items/@value)')

                                                             as varchar(8000))='Invoice')



OPEN results1
FETCH NEXT FROM results1

INTO @facility,@requestor_name,@requestor_id,@request_id,@total_cost,@paymentMethod,@payment,@amount_paid,@postedBy,@rStatus,@invoice_no,@invoice_date

WHILE @@FETCH_STATUS = 0
BEGIN

	INSERT INTO @TempResults values(', ' + @facility,@requestor_name,@requestor_id,@invoice_no,@invoice_date,@total_cost,@paymentMethod,@payment,@amount_paid,@postedBy,@rStatus, @request_id)

FETCH NEXT FROM results1
INTO @facility,@requestor_name,@requestor_id,@request_id,@total_cost,@paymentMethod,@payment,@amount_paid,@postedBy,@rStatus,@invoice_no,@invoice_date
END
CLOSE results1
DEALLOCATE results1

    DECLARE inputfacilities CURSOR FOR
            SELECT * FROM @Facilities
    OPEN inputfacilities
    FETCH NEXT FROM inputfacilities
    INTO @inputfacility

	WHILE @@FETCH_STATUS = 0
	BEGIN

		Declare results CURSOR FOR

			select * from @TempResults where Facility LIKE '%, ' + @inputfacility + ',%'

		OPEN results
		FETCH NEXT FROM results
		INTO @facility1,@requestor_name1,@requestor_id1,@invoice_no1,@invoice_date1,@total_cost1,@paymentMethod1,@payment1,@amount_paid1,@postedBy1,@rStatus1, @request_id1

			WHILE @@FETCH_STATUS = 0
			BEGIN

				if NOT EXISTS(select * from @FinalResults where RequestId1 = @request_id1)
				BEGIN
					INSERT INTO @FinalResults values(@inputfacility + ', ',@requestor_name1,@requestor_id1,@invoice_no1,@invoice_date1,@total_cost1,@paymentMethod1,@payment1,@amount_paid1,@postedBy1,@rStatus1, @request_id1)
				END
				else
					BEGIN
						SET @oldFacility = (SELECT Facility1 from @FinalResults where RequestId1 = @request_id1)
						SET @newFacility = @oldFacility + @inputfacility + ', '
						UPDATE @FinalResults SET Facility1 = @newFacility where RequestId1 = @request_id1
					END

			FETCH NEXT FROM results
			INTO @facility1,@requestor_name1,@requestor_id1,@invoice_no1,@invoice_date1,@total_cost1,@paymentMethod1,@payment1,@amount_paid1,@postedBy1,@rStatus1, @request_id1
			END
			CLOSE results
			DEALLOCATE results

	FETCH NEXT FROM inputfacilities
    INTO @inputfacility
	END
	CLOSE inputfacilities
	DEALLOCATE inputfacilities

	DECLARE details CURSOR FOR

		select * from @FinalResults

	OPEN details
	FETCH NEXT FROM details
	INTO @facility2,@requestor_name2,@requestor_id2,@invoice_no2,@invoice_date2,@total_cost2,@paymentMethod2,@payment2,@amount_paid2,@postedBy2,@rStatus2, @request_id2

	WHILE @@FETCH_STATUS = 0
	BEGIN

		DECLARE paymentMethods CURSOR FOR
            SELECT * FROM cabinet.dbo.splitUtil(substring(@paymentMethod2, 0, len(@paymentMethod2)),'/')
		OPEN paymentMethods
		FETCH NEXT FROM paymentMethods
		INTO @payMethod

		DECLARE payment CURSOR FOR
				SELECT * FROM cabinet.dbo.splitUtil(substring(@payment2, 0, len(@payment2)),'/')
		OPEN payment
		FETCH NEXT FROM payment
		INTO @payAmt

		WHILE @@FETCH_STATUS = 0
		BEGIN
			   INSERT INTO @Results values(substring(@facility2, 0, len(@facility2)),@requestor_name2,@requestor_id2,@invoice_no2,@invoice_date2,@total_cost2,@payMethod,SUBSTRING(@payAmt,1,LEN(@payAmt)),SUBSTRING(@amount_paid2,1,LEN(@amount_paid2)),@postedBy2,@rStatus2, @request_id2)

		FETCH NEXT FROM paymentMethods
		INTO @payMethod

		FETCH NEXT FROM payment
		INTO @payAmt

		END
		CLOSE paymentMethods
		DEALLOCATE paymentMethods
		CLOSE payment
		DEALLOCATE payment

	FETCH NEXT FROM details

	INTO @facility2,@requestor_name2,@requestor_id2,@invoice_no2,@invoice_date2,@total_cost2,@paymentMethod2,@payment2,@amount_paid2,@postedBy2,@rStatus2, @request_id2
	END
	CLOSE details
	DEALLOCATE details

SELECT a.requestid2,
	   cast(a.facility2 as varchar(8000)) as facility2,
	   cast(a.requestorname2 as varchar(8000)) as requestorname2,
	   cast(a.requestorId2 as varchar(8000)) as requestorId2,
	   cast(a.invoiceno2 as varchar(8000)) invoiceNo2,
	   cast(a.invoiceDate2 as datetime) invoiceDate2,
	   cast(a.TotalCost2 as varchar(8000)) totalcost2,
	   cast(a.paymentmethod2 as varchar(8000)) paymentmethod2,
	   cast(a.payment2 as varchar(8000)) payment2,
	   cast(a.PaymentTotal2 as varchar(8000)) PaymentTotal2,
       cast(a.postedBy2 as varchar(8000)) postedBy2,
       cast(a.RequestStatus2 as varchar(8000)) RequestStatus2
FROM @Results as a
where payment2 !='$0'
--GROUP BY requestid2, facility2, requestorId2, requestorname2, invoiceno2, invoiceDate2, TotalCost2, paymentmethod2, payment2,PaymentTotal2, postedBy2,requestStatus2
ORDER BY a.invoiceno2 desc,a.invoiceDate2 desc
GO
GRANT EXECUTE ON [dbo].[ROI_Generate_PostedPaymentsSummaryReport] TO [IMNET]
GO
