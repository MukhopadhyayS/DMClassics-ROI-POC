set rowcount 0


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
* Procedure             : ROI_MigrationInvoiceData.sql
* Creation Date         : 03/30/2009
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
* Usage                 : ROI_MigrationInvoiceData.sql 
*																
* Called By             : manual execution
*									
* Tables used           : [Cabinet].[dbo].[AA_ROI_ClassicInvoice]
*			
* Data Modifications	: Creates data in ROI 13.5 tables
*									
* Updates: 	
* Name      Date        Purpose
* ---------	----------- ----------------
* RC        03/30/2009  Created   
* RC		01/06/2010	formatted date
*						added check for null date
*						moved total pages to document charges section
*						added adjustment-payment-total
* MD Meersma	09/22/2010	Changed origional-invoice-id to original-invoice-id   
* MD Meersma	10/27/2010	Set @l_OutputType to None if string is empty
* MD Meersma	10/28/2010	Set Created_Dt, Modified_Dt in ROI_RequestDeliveryCharges to values from ROI_RequestMain
***************************************************************************************************************************************/
SET NOCOUNT ON



--/* this script will create the delivery charges XML for any released requests from the ROI classic system
Declare @l_Debug Bit; 
Set @l_Debug = 'True'
Set @l_Debug = 'False'
declare @l_CounterI int; Set @l_CounterI = 1
declare @l_CounterJ int; Set @l_CounterJ = 1
Declare @l_Payment_Id int
Declare @l_Invoicd_Id int
Declare @l_PaymentDate char(10)
Declare @l_CheckNumber varchar(10)
Declare @l_PaymentAmount decimal(7,2)
Declare @l_TotalPaymentAmount decimal(7,2)
Declare @l_PaymentIsDebit varchar(5)
Declare @l_PostedBy varchar(30)
Declare @l_RequestDeliverySeq int
Declare @l_RequestMainSeq int
Declare @l_RequestId int
Declare @l_InvoiceID int
Declare @l_RequestDeliveryChargesXML xml
Declare @l_varchar1 varchar(max)
Declare @l_PaymentString varchar(max)
Declare @l_HardCopyMicrofilm decimal(7,2)
Declare @l_HardCopyPages int
Declare @l_NumberOfCopies int
Declare @l_MicroFilmPages int
Declare @l_Base decimal(7,2)
Declare @l_HardCopy decimal(7,2)
Declare @l_Microfilm decimal(7,2)
Declare @l_Postage decimal(7,2)
Declare @l_TaxAmount decimal(7,2)
Declare @l_AdjustAmount decimal(7,2)
Declare @l_AdjustAmountIsDebit varchar(5)
Declare @l_AdjustReason varchar(500)
Declare @l_InvoiceDate char(10)
Declare @l_Facility varchar(10)
Declare @l_OutputType varchar(50)
Declare @l_Add_Adjustment_Amt varchar(1)
Declare @l_InvoiceNumber varchar(20)
Declare @l_AdjustTotalCharges decimal(7,2)
Declare @l_AdjustPaymentTotal decimal(7,2)
Declare @l_TotalCharges decimal(7,2)



Declare @l_ROI_RequestDeliveryCharges table
(
rdc int identity(1,1)
,requestdeliveryseq int
,requestmainseq int
,requestid int
,invoiceid int
)
Insert @l_ROI_RequestDeliveryCharges
SELECT  DISTINCT   
ROI_RequestDelivery.ROI_RequestDelivery_Seq
, ROI_RequestMain.ROI_RequestMain_Seq
, ROI_Invoice.Request_ID
, ROI_Invoice.Invoice_ID
FROM         
ROI_RequestDelivery 
INNER JOIN ROI_RequestMain ON ROI_RequestDelivery.ROI_RequestMain_Seq = ROI_RequestMain.ROI_RequestMain_Seq
INNER JOIN ROI_Invoice ON ROI_RequestMain.Created_By_Seq *-1 = ROI_Invoice.Request_ID
WHERE 
ROI_RequestMain.Created_By_Seq < 0
order by ROI_Invoice.Invoice_ID

--
IF @l_Debug = 'True'
Begin
Select '@l_ROI_RequestDeliveryCharges table'
Select * from @l_ROI_RequestDeliveryCharges 
Select 'ROI_Invoice table'
select * from ROI_Invoice
Select 'ROI_Invoice_Payment table'
select * from ROI_Invoice_Payment
END

Declare @l_Payments table
(
pid int identity(1,1)
,invoiceid int
,paymentid int
)

--Start a loop through the invoices
While @l_CounterI <= (Select max(rdc) from @l_ROI_RequestDeliveryCharges)
BEGIN
Select 
@l_RequestDeliverySeq = RequestDeliverySeq
,@l_RequestMainSeq = requestmainseq 
,@l_RequestId = RequestID
,@l_InvoiceID = InvoiceID
FROM @l_ROI_RequestDeliveryCharges 
WHERE rdc = @l_CounterI
--calculate the total of the payments
Select @l_TotalPaymentAmount = SUM(PaymentAmount) FROM ROI_Invoice_Payment WHERE Invoice_Id = @l_InvoiceId

--Retrieve the data from the ROI_Invoice table
Select
@l_InvoiceNumber = InvoiceNumber
,@l_Facility = Facility
,@l_InvoiceDate = ISNULL(convert(char(10), InvoiceDate, 101),'01/01/1900') 
,@l_Base = Base
,@l_HardCopy = HardCopy
,@l_Microfilm = Microfilm
,@l_Postage = Postage
,@l_TaxAmount = TaxAmount
,@l_AdjustAmount = AdjustAmount
,@l_AdjustTotalCharges = AdjustTotalCharges
,@l_AdjustReason = AdjustReason
,@l_OutputType = OutputType
,@l_NumberOfCopies = NumberOfCopies
,@l_HardCopyPages = HardCopyPages
,@l_MicroFilmPages = MicroFilmPages
,@l_Add_Adjustment_Amt = Add_Adjustment_Amt
FROM
	ROI_Invoice
WHERE
	Invoice_ID = @l_InvoiceID

-- @l_OutputType needs to be None for NEW ROI if it is empty
if @l_OutputType = '' set @l_OutputType = 'None'

--Calculate the total charges
SELECT @l_TotalCharges = 
			CASE
			WHEN UPPER(@l_Add_Adjustment_Amt) = 'N'  THEN @l_AdjustAmount  
			ELSE @l_AdjustAmount * -1
		END
	+ @l_AdjustTotalCharges 



--make sure the @l_Payments table is clean
Delete from @l_Payments

--fill the table with the payments for this invoice
Insert @l_Payments
Select Invoice_Id, Payment_Id
FROM ROI_Invoice_Payment
WHERE Invoice_Id = @l_InvoiceID

If @l_Debug = 'True' --display the data
Begin
	Select @l_RequestDeliverySeq AS '@l_RequestDeliverySeq'
	, @l_RequestMainSeq as '@l_RequestMainSeq'
	, @l_RequestId AS '@l_RequestId'
	, @l_InvoiceID AS '@l_InvoiceID'
	, @l_TotalCharges  AS '@l_TotalCharges'
	,'bubba' as 'location'
	Select '@l_Payments'
	Select * FROM @l_Payments
END


--calculate the payment
Select @l_TotalPaymentAmount = ISNULL(SUM(PaymentAmount),0) FROM ROI_Invoice_Payment WHERE Invoice_Id = @l_InvoiceId

--calculate the total of adjust and payment
SELECT @l_AdjustPaymentTotal = 
		CASE
			WHEN UPPER(@l_Add_Adjustment_Amt) = 'N'  THEN @l_AdjustAmount * -1 
			ELSE @l_AdjustAmount
		END
		+ @l_TotalPaymentAmount


--@l_counterJ loops through the invoice payments
Set @l_CounterJ = (Select min(pid) from @l_Payments)
Set @l_PaymentString = ''
While @l_CounterJ <= (Select max(pid) from @l_Payments)
BEGIN ---the loop through the payemnts table
	Select @l_Payment_ID = paymentid from @l_Payments where pid = @l_CounterJ
	-- create the payment data
	Select 
	@l_PaymentAmount = PaymentAmount
	, @l_CheckNumber = CheckNumber
	, @l_PaymentDate = ISNULL(convert(char(10), PaymentDate, 101),'01/01/1900')
	, @l_PostedBy = PostedBy
	, @l_PaymentIsDebit = 
		(CASE
			WHEN PaymentAmount > -1 THEN 'false'
			ELSE 'true'
		END)
	FROM ROI_Invoice_Payment
	WHERE Payment_Id = @l_Payment_ID
	AND Invoice_Id = @l_InvoiceID

	--show the data 
	IF @l_Debug = 'True'
	Begin
		select @l_counterj as 'Counter j', @l_Payment_ID AS 'local payment_id' ,@l_PaymentAmount AS '@lPaymentAmount', @l_CheckNumber AS '@l_CheckNumber'	, @l_PaymentDate AS '@l_PaymentDate', @l_PostedBy AS '@l_PostedBy'	,@l_PaymentIsDebit as '@l_PaymentIsDebit'
		select * from roi_invoice where invoice_id = @l_invoiceid
		select * from roi_invoice_payment where invoice_id = @l_invoiceid

	END
	--insert into string
	Set @l_PaymentString = @l_PaymentString + '<release-item>'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="amount" value="$' 
	+ 
		CASE
			WHEN @l_PaymentAmount > 0 THEN '('+CAST(@l_PaymentAmount AS varchar(10))+')'
			ELSE Cast(ABS(@l_PaymentAmount) AS varchar(10))
		END
	+ '" />' 

--		+ Cast(ABS(@l_PaymentAmount) AS varchar(10))
--		+ '" />' --ROI_Invoice_Payment.PaymentAmount" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="transaction-type" value="Payment" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="payment-mode" value="Migrate" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="is-debit" value="'
		+ @l_PaymentIsDebit 
		+ '" />'
	--True-if-ROI_Invoice_Payment.PaymentAmount-is-less than-0" />' 
	Set @l_PaymentString = @l_PaymentString + '<attribute name="description" value="'+ @l_CheckNumber + '" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="date" value="' + @l_PaymentDate + '" />'
	--+ CONVERT(nvarchar(30), @l_PaymentDate, 126) + '" />'
	--	+  CAST (@l_PaymentDate as varchar(20))+ '" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="PostedBy" value="'+ @l_PostedBy + '" />'
	Set @l_PaymentString = @l_PaymentString + '<attribute name="Payment_Id" value="'+ CAST (@l_Payment_ID as varchar(10)) + '" />'
	Set @l_PaymentString = @l_PaymentString + '</release-item>'
	Set @l_PaymentString = ISNULL(@l_PaymentString, '<release-item />')
	Set @l_CounterJ = @l_CounterJ + 1
END

Set @l_varchar1 = '<release-item type="billing-info">' 
	Set @l_varchar1 = @l_varchar1 + '<release-item type="charges">'  
		Set @l_varchar1 = @l_varchar1 + '<!--Document Charges-->'  
			Set @l_varchar1 = @l_varchar1 + '<release-item type="DocumentCharge">'  
				Set @l_varchar1 = @l_varchar1 + '<attribute name="document-charge-total" value="$0.00" />' 
				Set @l_varchar1 = @l_varchar1 + '<attribute name="original-invoice-id" value="' + CAST(@l_InvoiceID as varchar(20)) + '" />' 
				Set @l_varchar1 = @l_varchar1 + '<release-item>'
					Set @l_varchar1 = @l_varchar1 + '<attribute name="total-pages" value="' + ISNULL(CAST (@l_HardCopyPages + @l_MicroFilmPages as varchar(20)),'0') + '" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="amount" value="$0.00" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="billingtier-name" value="Electronic" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="billingtier-id" value="-1" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="pages" value="' + CAST(1 AS varchar(20)) + '" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="copies" value="' + CAST(@l_NumberOfCopies AS varchar(20)) + '" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="remove-basecharge" value="false" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="release-count" value="1" />' 
					Set @l_varchar1 = @l_varchar1 + '<attribute name="is-electronic" value="true" />' 
				Set @l_varchar1 = @l_varchar1 + '</release-item>'
			Set @l_varchar1 = @l_varchar1 + '</release-item>'
		Set @l_varchar1 = @l_varchar1 + '<!--Fee Charges-->' 
		Set @l_varchar1 = @l_varchar1 + '<release-item type="FeeCharge">'
			Set @l_varchar1 = @l_varchar1 + '<attribute name="billing-type" value="0" />'
			Set @l_varchar1 = @l_varchar1 + '<attribute name="fee-charge-total"  value="' + CAST(@l_TotalCharges AS varchar(10)) + '" />' 
			Set @l_varchar1 = @l_varchar1 + '<release-item>'
				Set @l_varchar1 = @l_varchar1 + '<attribute name="amount" value="' + CAST(@l_TotalCharges AS varchar(10)) + '" />' 
				Set @l_varchar1 = @l_varchar1 + '<attribute name="feetype" value="Custom Fee" />'
				Set @l_varchar1 = @l_varchar1 + '<attribute name="is-custom-fee" value="true " />' 
			Set @l_varchar1 = @l_varchar1 + '</release-item>'
	Set @l_varchar1 = @l_varchar1 + '</release-item>'
	Set @l_varchar1 = @l_varchar1 + '<!--Shipping Charge-->' 
	Set @l_varchar1 = @l_varchar1 + '<release-item type="shipping-charge">' 
	Set @l_varchar1 = @l_varchar1 + '<attribute name="shipping-charge" value="0.00" />' 
	Set @l_varchar1 = @l_varchar1 + '</release-item>'
	Set @l_varchar1 = @l_varchar1 + '<!--Adjustment/Payment charges-->' 
	Set @l_varchar1 = @l_varchar1 + '<release-item type="request-transaction">' 
	Set @l_varchar1 = @l_varchar1 + '<attribute name="adjustment-payment-total" value="' 
		+ 
			CASE
				WHEN @l_AdjustPaymentTotal < 0 THEN '('+CAST(@l_AdjustPaymentTotal * -1 AS varchar(10))+')'
				ELSE Cast(@l_AdjustPaymentTotal AS varchar(10))
			END
		+ '" />' 
	Set @l_varchar1 = @l_varchar1 + '<attribute name="adjustment-total" value="' 
		+ 
			CASE
				WHEN UPPER(@l_Add_Adjustment_Amt) = 'N'  THEN '('+CAST(@l_AdjustAmount AS varchar(10))+')'
				ELSE Cast(@l_AdjustAmount AS varchar(10))
			END
		+ '" />' 
	Set @l_varchar1 = @l_varchar1 + '<attribute name="payment-total" value="' 
		+ 
			CASE
				WHEN @l_TotalPaymentAmount > 0 THEN '('+CAST(@l_TotalPaymentAmount AS varchar(10))+')'
				ELSE Cast(@l_TotalPaymentAmount AS varchar(10))
			END
		+ '" />' 
		Set @l_varchar1 = @l_varchar1 + '<release-item>' 
			Set @l_varchar1 = @l_varchar1 + '<attribute name="amount" value="' 
				+ 
					CASE
						WHEN UPPER(@l_Add_Adjustment_Amt) = 'N'  THEN '('+CAST(@l_AdjustAmount AS varchar(10))+')'
						ELSE Cast(@l_AdjustAmount AS varchar(10))
					END
				+ '" />' 
			Set @l_varchar1 = @l_varchar1 + '<attribute name="transaction-type" value="Adjustment" />'
			Set @l_varchar1 = @l_varchar1 + '<attribute name="payment-mode" value="Migrate" />'
			Set @l_varchar1 = @l_varchar1 + '<attribute name="is-debit" value="' 
				+ 	(CASE
						WHEN @l_AdjustAmount > -1 THEN 'false'
						ELSE 'true'
					END)
				+ '" />' 
			Set @l_varchar1 = @l_varchar1 + '<attribute name="description" value="' +ISNULL(@l_AdjustReason,'unknown') + '" />'
			Set @l_varchar1 = @l_varchar1 + '<attribute name="date" value="'+  ISNULL(@l_InvoiceDate,'01/01/1900')  + '" />'
		Set @l_varchar1 = @l_varchar1 + '</release-item>'
		Set @l_varchar1 = @l_varchar1 + @l_PaymentString
		Set @l_varchar1 = @l_varchar1 + '</release-item>'
		Set @l_varchar1 = @l_varchar1 + '<!--release general info-->' 
		Set @l_varchar1 = @l_varchar1 + '<release-item type="release-info">' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="total-pages" value="' + ISNULL(CAST (@l_HardCopyPages + @l_MicroFilmPages as varchar(20)),'0') + '" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="release-cost" value="' + ISNULL(CAST (@l_Base + @l_Postage + @l_TaxAmount as varchar(20)),'0') + '" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="release-date" value="' +  @l_InvoiceDate + '" />' 
		--added on 01/06/2010
		Set @l_varchar1 = @l_varchar1 + '<attribute name="total-request-cost" value="'+ ISNULL(CAST (@l_Base + @l_Postage + @l_TaxAmount as varchar(20)),'0') + '" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="previously-released-cost" value="$0.00" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="balance-due" value="$0.00" />' 
		--end added on 01/06/2010
		Set @l_varchar1 = @l_varchar1 + '</release-item>'
	----removed on 01/06/2010
	----Set @l_varchar1 = @l_varchar1 + '<!--Release date-->' 
	----Set @l_varchar1 = @l_varchar1 + '<release-item type="ReleaseDate">' 
	----Set @l_varchar1 = @l_varchar1 + '</release-item>'
	----end removed on 01/06/2010
		Set @l_varchar1 = @l_varchar1 + '</release-item>'
	Set @l_varchar1 = @l_varchar1 + '<!--Shipping information-->' 
	Set @l_varchar1 = @l_varchar1 + '<release-item type="shipping-info">' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="output-method" value="' + ISNULL(@l_OutputType,'None') + '" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="will-release-shipped" value="false" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="address-type" value="None" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="address1" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="address2" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="address3" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="city" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="state" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="postalcode" value=" " />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="tracking-number" value=" " />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="shipping-weight" value="0" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="shipping-method" value="" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="shipping-method-id" value="0" />' 
		Set @l_varchar1 = @l_varchar1 + '<attribute name="shipping-url" value="" />' 
	Set @l_varchar1 = @l_varchar1 + '</release-item>'
Set @l_varchar1 = @l_varchar1 + '</release-item>'


Set @l_RequestDeliveryChargesXML = @l_varchar1

IF @l_Debug = 'True'
Begin
	select @l_RequestDeliveryChargesXML AS '@l_RequestDeliveryChargesXML'
END

declare @l_created_dt datetime, @l_modified_dt datetime

select @l_created_dt = Created_Dt, @l_modified_dt = Modified_Dt from ROI_RequestDelivery where ROI_RequestDelivery_Seq = @l_RequestDeliverySeq


INSERT INTO 
	[cabinet].[dbo].[ROI_RequestDeliveryCharges]
	(
		[Created_By_Seq]
		,[ROI_RequestDelivery_Seq]
		,[isReleased]
		,[isInvoiced]
		,[RequestDeliveryChargesXML]
		,[Created_Dt]
		,[Modified_Dt]
	)
VALUES
	(
		@l_InvoiceID * -1
		,@l_RequestDeliverySeq
		,'True'
		,'False'
		,@l_RequestDeliveryChargesXML
		,@l_created_dt
		,@l_modified_dt
	)


SET @l_counterI = @l_CounterI + 1
END
GO
