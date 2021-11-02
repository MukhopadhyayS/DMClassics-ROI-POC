
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
    "requestCoreDeliveryChargesId",
    "paymentId",
    "requestId",
    "totalAppliedAmount",
    "lastAppliedAmount",
    "invoicedBaseCharge",
    "invoiceBalance",
    "invoiceAmountPaid",
    "invoicePaymentTotal",
    "facility",
    "prebillPayment"
})
public class RequestorPayment {

    protected long requestCoreDeliveryChargesId;
    protected long paymentId;
    protected long requestId;
    protected double totalAppliedAmount;
    protected double lastAppliedAmount;
    protected double invoicedBaseCharge;
    protected double invoiceBalance;
    protected double invoiceAmountPaid;
    protected double invoicePaymentTotal;
    @XmlElement(required = true)
    protected String facility;
    protected boolean prebillPayment;

    /**
     * Gets the value of the requestCoreDeliveryChargesId property.
     * 
     */
    public long getRequestCoreDeliveryChargesId() {
        return requestCoreDeliveryChargesId;
    }

    /**
     * Sets the value of the requestCoreDeliveryChargesId property.
     * 
     */
    public void setRequestCoreDeliveryChargesId(long value) {
        this.requestCoreDeliveryChargesId = value;
    }

    /**
     * Gets the value of the paymentId property.
     * 
     */
    public long getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the value of the paymentId property.
     * 
     */
    public void setPaymentId(long value) {
        this.paymentId = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the totalAppliedAmount property.
     * 
     */
    public double getTotalAppliedAmount() {
        return totalAppliedAmount;
    }

    /**
     * Sets the value of the totalAppliedAmount property.
     * 
     */
    public void setTotalAppliedAmount(double value) {
        this.totalAppliedAmount = value;
    }

    /**
     * Gets the value of the lastAppliedAmount property.
     * 
     */
    public double getLastAppliedAmount() {
        return lastAppliedAmount;
    }

    /**
     * Sets the value of the lastAppliedAmount property.
     * 
     */
    public void setLastAppliedAmount(double value) {
        this.lastAppliedAmount = value;
    }

    /**
     * Gets the value of the invoicedBaseCharge property.
     * 
     */
    public double getInvoicedBaseCharge() {
        return invoicedBaseCharge;
    }

    /**
     * Sets the value of the invoicedBaseCharge property.
     * 
     */
    public void setInvoicedBaseCharge(double value) {
        this.invoicedBaseCharge = value;
    }

    /**
     * Gets the value of the invoiceBalance property.
     * 
     */
    public double getInvoiceBalance() {
        return invoiceBalance;
    }

    /**
     * Sets the value of the invoiceBalance property.
     * 
     */
    public void setInvoiceBalance(double value) {
        this.invoiceBalance = value;
    }

    /**
     * Gets the value of the invoiceAmountPaid property.
     * 
     */
    public double getInvoiceAmountPaid() {
        return invoiceAmountPaid;
    }

    /**
     * Sets the value of the invoiceAmountPaid property.
     * 
     */
    public void setInvoiceAmountPaid(double value) {
        this.invoiceAmountPaid = value;
    }

    /**
     * Gets the value of the invoicePaymentTotal property.
     * 
     */
    public double getInvoicePaymentTotal() {
        return invoicePaymentTotal;
    }

    /**
     * Sets the value of the invoicePaymentTotal property.
     * 
     */
    public void setInvoicePaymentTotal(double value) {
        this.invoicePaymentTotal = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacility(String value) {
        this.facility = value;
    }

    /**
     * Gets the value of the prebillPayment property.
     * 
     */
    public boolean isPrebillPayment() {
        return prebillPayment;
    }

    /**
     * Sets the value of the prebillPayment property.
     * 
     */
    public void setPrebillPayment(boolean value) {
        this.prebillPayment = value;
    }

}
