package com.mckesson.eig.roi.request.model;

import java.util.Date;
import java.util.Set;

import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreChargesInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="test" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestCoreDeliveryChargesId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceCreatedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoicedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="isInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="requestCoreDeliveryChargesAdjustmentPayment" type="{urn:eig.mckesson.com}RequestCoreDeliveryChargesAdjustmentPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesInvoice", propOrder = {
    "test",
    "_requestCoreDeliveryChargesId",
    "_invoiceCreatedDt",
    "_invoicedAmount",
    "_paymentAmount",
    "_isInvoice",
    "_requestCoreDeliveryChargesAdjustmentPayment"
})
public class RequestCoreChargesInvoice {

    @XmlElement(name="requestCoreDeliveryChargesId")
    private long _requestCoreDeliveryChargesId;
    
    @XmlElement(name="invoiceCreatedDt", required = true, nillable = true)
    private Date _invoiceCreatedDt;
    
    @XmlElement(name="invoicedAmount")
    private double _invoicedAmount;
    
    @XmlElement(name="paymentAmount")
    private double _paymentAmount;
    
    @XmlElement(name="isInvoice")
    private boolean _isInvoice;
    
    @XmlElement(name="requestCoreDeliveryChargesAdjustmentPayment")
    private Set<RequestCoreDeliveryChargesAdjustmentPayment> _requestCoreDeliveryChargesAdjustmentPayment;

    protected String test;
    
    
    
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }

    public void setRequestCoreDeliveryChargesId(
            long _requestCoreDeliveryChargesId) {
        this._requestCoreDeliveryChargesId = _requestCoreDeliveryChargesId;
    }

    public Date getInvoiceCreatedDt() {
        return _invoiceCreatedDt;
    }

    public void setInvoiceCreatedDt(Date _invoiceCreatedDt) {
        this._invoiceCreatedDt = _invoiceCreatedDt;
    }

    public double getInvoicedAmount() {
        return _invoicedAmount;
    }

    public void setInvoicedAmount(double invoicedAmount) {
        this._invoicedAmount = invoicedAmount;
    }

    public double getPaymentAmount() {
        return _paymentAmount;
    }

    public void setPaymentAmount(double _paymentAmount) {
        this._paymentAmount = _paymentAmount;
    }
    public Set<RequestCoreDeliveryChargesAdjustmentPayment> getRequestCoreDeliveryChargesAdjustmentPayment() {
        return _requestCoreDeliveryChargesAdjustmentPayment;
    }

    public void setRequestCoreDeliveryChargesAdjustmentPayment(
            Set<RequestCoreDeliveryChargesAdjustmentPayment> _requestCoreDeliveryChargesAdjPay) {
        _requestCoreDeliveryChargesAdjustmentPayment = _requestCoreDeliveryChargesAdjPay;
    }

    public boolean getIsInvoice() { return _isInvoice; }
    public void setIsInvoice(boolean isInvoiced) { _isInvoice = isInvoiced; }

}
