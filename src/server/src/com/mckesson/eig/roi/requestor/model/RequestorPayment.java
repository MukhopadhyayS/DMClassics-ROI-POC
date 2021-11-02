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

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreDeliveryChargesId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="paymentId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="totalAppliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="lastAppliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoicedBaseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceAmountPaid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoicePaymentTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prebillPayment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorPayment", propOrder = {
    "_requestCoreDeliveryChargesId",
    "_paymentId",
    "_requestId",
    "_totalAppliedAmount",
    "_lastAppliedAmount",
    "_invoicedBaseCharge",
    "_invoiceBalance",
    "_invoiceAmountPaid",
    "_invoicePaymentTotal",
    "_facility",
    "_prebillPayment"
})
public class RequestorPayment extends BaseModel{


    private static final long serialVersionUID = 1L;

    @XmlElement(name="requestCoreDeliveryChargesId")
    private long _requestCoreDeliveryChargesId;
    
    @XmlElement(name="paymentId")
    private long _paymentId;
    
    @XmlElement(name="requestId")
    private long _requestId;
    
    @XmlElement(name="totalAppliedAmount")
    private double _totalAppliedAmount;
    
    @XmlElement(name="lastAppliedAmount")
    private double _lastAppliedAmount;
    
    @XmlElement(name="invoicedBaseCharge")
    private double _invoicedBaseCharge;
    
    @XmlElement(name="invoiceBalance")
    private double _invoiceBalance;
    
    @XmlElement(name="invoiceAmountPaid")
    private double _invoiceAmountPaid;
    
    @XmlElement(name="invoicePaymentTotal")
    private double _invoicePaymentTotal;
    
    @XmlTransient
    private double _unAppliedAmount;
    
    @XmlTransient
    private double _refundAmount;
    
    @XmlElement(name="facility", required = true)
    private String _facility;
    
    @XmlElement(name="prebillPayment")
    private boolean _prebillPayment;
    
    
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
    public boolean isPrebillPayment() {
        return _prebillPayment;
    }
    public void setPrebillPayment(boolean prebillPayment) {
        _prebillPayment = prebillPayment;
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
