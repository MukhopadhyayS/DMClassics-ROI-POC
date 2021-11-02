
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorAdjustment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorAdjustment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceSeq" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentType" type="{urn:eig.mckesson.com}AdjustmentType"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="unappliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="appliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="prebillAdjustment" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorAdjustment", propOrder = {
    "id",
    "requestorSeq",
    "requestorName",
    "requestorType",
    "invoiceSeq",
    "adjustmentType",
    "reason",
    "amount",
    "unappliedAmount",
    "appliedAmount",
    "adjustmentDate",
    "note",
    "delete",
    "prebillAdjustment"
})
public class RequestorAdjustment {

    protected long id;
    protected long requestorSeq;
    @XmlElement(required = true)
    protected String requestorName;
    @XmlElement(required = true)
    protected String requestorType;
    protected double invoiceSeq;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AdjustmentType adjustmentType;
    @XmlElement(required = true)
    protected String reason;
    protected double amount;
    protected double unappliedAmount;
    protected double appliedAmount;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar adjustmentDate;
    @XmlElement(required = true)
    protected String note;
    protected boolean delete;
    protected boolean prebillAdjustment;

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
     * Gets the value of the requestorSeq property.
     * 
     */
    public long getRequestorSeq() {
        return requestorSeq;
    }

    /**
     * Sets the value of the requestorSeq property.
     * 
     */
    public void setRequestorSeq(long value) {
        this.requestorSeq = value;
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
     * Gets the value of the invoiceSeq property.
     * 
     */
    public double getInvoiceSeq() {
        return invoiceSeq;
    }

    /**
     * Sets the value of the invoiceSeq property.
     * 
     */
    public void setInvoiceSeq(double value) {
        this.invoiceSeq = value;
    }

    /**
     * Gets the value of the adjustmentType property.
     * 
     * @return
     *     possible object is
     *     {@link AdjustmentType }
     *     
     */
    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    /**
     * Sets the value of the adjustmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdjustmentType }
     *     
     */
    public void setAdjustmentType(AdjustmentType value) {
        this.adjustmentType = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the unappliedAmount property.
     * 
     */
    public double getUnappliedAmount() {
        return unappliedAmount;
    }

    /**
     * Sets the value of the unappliedAmount property.
     * 
     */
    public void setUnappliedAmount(double value) {
        this.unappliedAmount = value;
    }

    /**
     * Gets the value of the appliedAmount property.
     * 
     */
    public double getAppliedAmount() {
        return appliedAmount;
    }

    /**
     * Sets the value of the appliedAmount property.
     * 
     */
    public void setAppliedAmount(double value) {
        this.appliedAmount = value;
    }

    /**
     * Gets the value of the adjustmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAdjustmentDate() {
        return adjustmentDate;
    }

    /**
     * Sets the value of the adjustmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAdjustmentDate(XMLGregorianCalendar value) {
        this.adjustmentDate = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the delete property.
     * 
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * Sets the value of the delete property.
     * 
     */
    public void setDelete(boolean value) {
        this.delete = value;
    }

    /**
     * Gets the value of the prebillAdjustment property.
     * 
     */
    public boolean isPrebillAdjustment() {
        return prebillAdjustment;
    }

    /**
     * Sets the value of the prebillAdjustment property.
     * 
     */
    public void setPrebillAdjustment(boolean value) {
        this.prebillAdjustment = value;
    }

}
