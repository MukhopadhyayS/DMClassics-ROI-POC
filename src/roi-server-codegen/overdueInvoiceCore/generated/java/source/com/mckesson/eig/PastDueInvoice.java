
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PastDueInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastDueInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overdueAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phoneNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overDueDays" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceAge" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastDueInvoice", propOrder = {
    "billingLocation",
    "invoiceNumber",
    "requestId",
    "overdueAmount",
    "requestorName",
    "phoneNumber",
    "requestorType",
    "requestorId",
    "overDueDays",
    "invoiceAge"
})
public class PastDueInvoice {

    @XmlElement(required = true)
    protected String billingLocation;
    protected long invoiceNumber;
    protected long requestId;
    protected double overdueAmount;
    @XmlElement(required = true)
    protected String requestorName;
    @XmlElement(required = true)
    protected String phoneNumber;
    @XmlElement(required = true)
    protected String requestorType;
    protected long requestorId;
    protected long overDueDays;
    protected long invoiceAge;

    /**
     * Gets the value of the billingLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingLocation() {
        return billingLocation;
    }

    /**
     * Sets the value of the billingLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingLocation(String value) {
        this.billingLocation = value;
    }

    /**
     * Gets the value of the invoiceNumber property.
     * 
     */
    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     */
    public void setInvoiceNumber(long value) {
        this.invoiceNumber = value;
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
     * Gets the value of the overdueAmount property.
     * 
     */
    public double getOverdueAmount() {
        return overdueAmount;
    }

    /**
     * Sets the value of the overdueAmount property.
     * 
     */
    public void setOverdueAmount(double value) {
        this.overdueAmount = value;
    }

    /**
     * Gets the value of the requestorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * Sets the value of the requestorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorName(String value) {
        this.requestorName = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorType(String value) {
        this.requestorType = value;
    }

    /**
     * Gets the value of the requestorId property.
     * 
     */
    public long getRequestorId() {
        return requestorId;
    }

    /**
     * Sets the value of the requestorId property.
     * 
     */
    public void setRequestorId(long value) {
        this.requestorId = value;
    }

    /**
     * Gets the value of the overDueDays property.
     * 
     */
    public long getOverDueDays() {
        return overDueDays;
    }

    /**
     * Sets the value of the overDueDays property.
     * 
     */
    public void setOverDueDays(long value) {
        this.overDueDays = value;
    }

    /**
     * Gets the value of the invoiceAge property.
     * 
     */
    public long getInvoiceAge() {
        return invoiceAge;
    }

    /**
     * Sets the value of the invoiceAge property.
     * 
     */
    public void setInvoiceAge(long value) {
        this.invoiceAge = value;
    }

}
