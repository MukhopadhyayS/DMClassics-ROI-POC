/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/


package com.mckesson.eig.roi.requestor.model;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;


/**
 * @author OFS
 * @date Nov 16, 2012
 * @since HPF 16.0 [ROI]; Nov 16, 2012
 *
 */
public class RequestorPayment
extends BaseModel {


    private static final long serialVersionUID = 1L;

    private long _requestCoreDeliveryChargesId;
    private long _paymentId;
    private long _requestId;
    private double _totalAppliedAmount;
    private double _lastAppliedAmount;
    private double _invoicedBaseCharge;
    private double _invoiceBalance;
    private double _invoiceAmountPaid;
    private double _invoicePaymentTotal;
    private double _unAppliedAmount;
    private double _refundAmount;
    private String _facility;
    
    public long getRequestCoreDeliveryChargesId() { return _requestCoreDeliveryChargesId; }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        _requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    
    public long getPaymentId() { return _paymentId; }
    public void setPaymentId(long paymentId) { _paymentId = paymentId; }    
    
    public double getInvoicedBaseCharge() { return _invoicedBaseCharge; }
    public void setInvoicedBaseCharge(double invoicedBaseCharge) { 
        _invoicedBaseCharge = invoicedBaseCharge;
    }
    
    public double getInvoiceBalance() { return _invoiceBalance; }
    public void setInvoiceBalance(double invoiceBalance) { _invoiceBalance = invoiceBalance; }
    
    public double getInvoiceAmountPaid() { return _invoiceAmountPaid; }
    public void setInvoiceAmountPaid(double invoiceAmountPaid) {
        _invoiceAmountPaid = invoiceAmountPaid;
    }
    
    public double getInvoicePaymentTotal() { return _invoicePaymentTotal; }
    public void setInvoicePaymentTotal(double invoicePaymentTotal) {
        _invoicePaymentTotal = invoicePaymentTotal;
    }

    public double getUnAppliedAmount() { return _unAppliedAmount; }
    public void setUnAppliedAmount(double unAppliedAmount) { _unAppliedAmount = unAppliedAmount; }

    public double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(double refundAmount) { _refundAmount = refundAmount; }
    
    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }
    
    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }  
    
    public double getTotalAppliedAmount() { return _totalAppliedAmount; }
    public void setTotalAppliedAmount(double totalAppliedAmount) { 
        _totalAppliedAmount = totalAppliedAmount;
    }
    public double getLastAppliedAmount() { return _lastAppliedAmount; }
    public void setLastAppliedAmount(double lastAppliedAmount) {
        _lastAppliedAmount = lastAppliedAmount;
    }
    
    /**
     * This method create audit comment for Requestor Apply Payment
     * @return audit comment for retrieve event
     */
    public String constructApplyPaymentAuditComment(String paymentMode, long requestorId) {
        return new StringBuffer().append("A  ")
                .append(paymentMode)
                .append(" payment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_lastAppliedAmount))
                .append(" was applied to request id: ")
                .append(_requestId)
                .append(" using invoice ")
                .append(_requestCoreDeliveryChargesId)
                .append(" for requestor id: ")
                .append(requestorId)
                .append(".")
                .toString();
    }

    /**
     * This method create audit comment for Requestor Update Payment
     * @return audit comment for retrieve event
     */
    public String constructUpdatePaymentAuditComment(double oldAppliedAmount, 
            String requestorName, String requestorType) {
        return new StringBuffer().append("A  ")
                .append(ROIConstants.CURRENCY_FORMAT.format(oldAppliedAmount))
                .append(" payment on Request ID")
                .append(_requestId)
                .append("-Invoice")
                .append(_requestCoreDeliveryChargesId)
                .append(" was changed to ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_totalAppliedAmount))
                .append(" for ")
                .append(requestorName)
                .append("-"+requestorType)
                .append(".")
                .toString();
    }
    
    /**
     * This method create event comment for apply payment
     * @return audit comment for retrieve event
     */
    public String constructApplyPaymentEventComment(String paymentType, String originator) {
        return new StringBuffer().append(" A payment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_lastAppliedAmount))
                .append(" "+paymentType)
                .append(" was applied to ")
                .append(_requestCoreDeliveryChargesId)
                .append(" of ")
                .append(_requestId)
                .append(" by ")
                .append(originator.trim())
                .append(".")
                .toString();
    }

    /**
     * This method create event comment for update payment
     * @return audit comment for retrieve event
     */
    public String constructUpdatePaymentEventComment(double originalPayment,
                                        String paymentType, String Originator) {

        return new StringBuffer().append(" A payment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(originalPayment))
                .append(" was changed to ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_totalAppliedAmount))
                .append(" on invoice ")
                .append(_requestCoreDeliveryChargesId)
                .append(" of ")
                .append(_requestId)
                .append(" by ")
                .append(Originator.trim())
                .append(".")
                .toString();
    }
   
}
