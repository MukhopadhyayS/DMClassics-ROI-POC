
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreDeliveryChargesAdjustmentPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreDeliveryChargesAdjustmentPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestCoreDeliveryChargesId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceAppliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="baseAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalUnappliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="transactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDebit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="newlyAdded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreDeliveryChargesAdjustmentPayment", propOrder = {
    "id",
    "createdBy",
    "modifiedBy",
    "createdDt",
    "modifiedDt",
    "recordVersion",
    "requestorId",
    "requestCoreDeliveryChargesId",
    "invoiceAppliedAmount",
    "baseAmount",
    "totalUnappliedAmount",
    "paymentMode",
    "description",
    "paymentDate",
    "transactionType",
    "isDebit",
    "newlyAdded"
})
public class RequestCoreDeliveryChargesAdjustmentPayment {

    protected long id;
    protected long createdBy;
    protected long modifiedBy;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDt;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDt;
    protected int recordVersion;
    protected long requestorId;
    protected long requestCoreDeliveryChargesId;
    protected double invoiceAppliedAmount;
    protected double baseAmount;
    protected double totalUnappliedAmount;
    @XmlElement(required = true)
    protected String paymentMode;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar paymentDate;
    @XmlElement(required = true)
    protected String transactionType;
    protected boolean isDebit;
    protected boolean newlyAdded;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     */
    public long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     */
    public void setCreatedBy(long value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     */
    public long getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     */
    public void setModifiedBy(long value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the createdDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDt() {
        return createdDt;
    }

    /**
     * Sets the value of the createdDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDt(XMLGregorianCalendar value) {
        this.createdDt = value;
    }

    /**
     * Gets the value of the modifiedDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDt() {
        return modifiedDt;
    }

    /**
     * Sets the value of the modifiedDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDt(XMLGregorianCalendar value) {
        this.modifiedDt = value;
    }

    /**
     * Gets the value of the recordVersion property.
     * 
     */
    public int getRecordVersion() {
        return recordVersion;
    }

    /**
     * Sets the value of the recordVersion property.
     * 
     */
    public void setRecordVersion(int value) {
        this.recordVersion = value;
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
     * Gets the value of the invoiceAppliedAmount property.
     * 
     */
    public double getInvoiceAppliedAmount() {
        return invoiceAppliedAmount;
    }

    /**
     * Sets the value of the invoiceAppliedAmount property.
     * 
     */
    public void setInvoiceAppliedAmount(double value) {
        this.invoiceAppliedAmount = value;
    }

    /**
     * Gets the value of the baseAmount property.
     * 
     */
    public double getBaseAmount() {
        return baseAmount;
    }

    /**
     * Sets the value of the baseAmount property.
     * 
     */
    public void setBaseAmount(double value) {
        this.baseAmount = value;
    }

    /**
     * Gets the value of the totalUnappliedAmount property.
     * 
     */
    public double getTotalUnappliedAmount() {
        return totalUnappliedAmount;
    }

    /**
     * Sets the value of the totalUnappliedAmount property.
     * 
     */
    public void setTotalUnappliedAmount(double value) {
        this.totalUnappliedAmount = value;
    }

    /**
     * Gets the value of the paymentMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * Sets the value of the paymentMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMode(String value) {
        this.paymentMode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaymentDate(XMLGregorianCalendar value) {
        this.paymentDate = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionType(String value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the isDebit property.
     * 
     */
    public boolean isIsDebit() {
        return isDebit;
    }

    /**
     * Sets the value of the isDebit property.
     * 
     */
    public void setIsDebit(boolean value) {
        this.isDebit = value;
    }

    /**
     * Gets the value of the newlyAdded property.
     * 
     */
    public boolean isNewlyAdded() {
        return newlyAdded;
    }

    /**
     * Sets the value of the newlyAdded property.
     * 
     */
    public void setNewlyAdded(boolean value) {
        this.newlyAdded = value;
    }

}
