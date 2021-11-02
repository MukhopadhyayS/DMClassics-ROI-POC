
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreChargesDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requestCoreChargesSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="copies" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="billingTierName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="billingtierId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="releaseCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isElectronic" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="removeBaseCharge" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasSalesTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="salesTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesDocument", propOrder = {
    "id",
    "createdBy",
    "modifiedBy",
    "createdDt",
    "modifiedDt",
    "recordVersion",
    "requestCoreChargesSeq",
    "amount",
    "copies",
    "billingTierName",
    "totalPages",
    "pages",
    "billingtierId",
    "releaseCount",
    "isElectronic",
    "removeBaseCharge",
    "hasSalesTax",
    "salesTaxAmount"
})
public class RequestCoreChargesDocument {

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
    protected long requestCoreChargesSeq;
    protected double amount;
    protected int copies;
    @XmlElement(required = true)
    protected String billingTierName;
    protected int totalPages;
    protected int pages;
    @XmlElement(required = true)
    protected String billingtierId;
    protected int releaseCount;
    protected boolean isElectronic;
    protected boolean removeBaseCharge;
    protected boolean hasSalesTax;
    protected double salesTaxAmount;

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
     * Gets the value of the requestCoreChargesSeq property.
     * 
     */
    public long getRequestCoreChargesSeq() {
        return requestCoreChargesSeq;
    }

    /**
     * Sets the value of the requestCoreChargesSeq property.
     * 
     */
    public void setRequestCoreChargesSeq(long value) {
        this.requestCoreChargesSeq = value;
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
     * Gets the value of the copies property.
     * 
     */
    public int getCopies() {
        return copies;
    }

    /**
     * Sets the value of the copies property.
     * 
     */
    public void setCopies(int value) {
        this.copies = value;
    }

    /**
     * Gets the value of the billingTierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingTierName() {
        return billingTierName;
    }

    /**
     * Sets the value of the billingTierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingTierName(String value) {
        this.billingTierName = value;
    }

    /**
     * Gets the value of the totalPages property.
     * 
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the value of the totalPages property.
     * 
     */
    public void setTotalPages(int value) {
        this.totalPages = value;
    }

    /**
     * Gets the value of the pages property.
     * 
     */
    public int getPages() {
        return pages;
    }

    /**
     * Sets the value of the pages property.
     * 
     */
    public void setPages(int value) {
        this.pages = value;
    }

    /**
     * Gets the value of the billingtierId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingtierId() {
        return billingtierId;
    }

    /**
     * Sets the value of the billingtierId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingtierId(String value) {
        this.billingtierId = value;
    }

    /**
     * Gets the value of the releaseCount property.
     * 
     */
    public int getReleaseCount() {
        return releaseCount;
    }

    /**
     * Sets the value of the releaseCount property.
     * 
     */
    public void setReleaseCount(int value) {
        this.releaseCount = value;
    }

    /**
     * Gets the value of the isElectronic property.
     * 
     */
    public boolean isIsElectronic() {
        return isElectronic;
    }

    /**
     * Sets the value of the isElectronic property.
     * 
     */
    public void setIsElectronic(boolean value) {
        this.isElectronic = value;
    }

    /**
     * Gets the value of the removeBaseCharge property.
     * 
     */
    public boolean isRemoveBaseCharge() {
        return removeBaseCharge;
    }

    /**
     * Sets the value of the removeBaseCharge property.
     * 
     */
    public void setRemoveBaseCharge(boolean value) {
        this.removeBaseCharge = value;
    }

    /**
     * Gets the value of the hasSalesTax property.
     * 
     */
    public boolean isHasSalesTax() {
        return hasSalesTax;
    }

    /**
     * Sets the value of the hasSalesTax property.
     * 
     */
    public void setHasSalesTax(boolean value) {
        this.hasSalesTax = value;
    }

    /**
     * Gets the value of the salesTaxAmount property.
     * 
     */
    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    /**
     * Sets the value of the salesTaxAmount property.
     * 
     */
    public void setSalesTaxAmount(double value) {
        this.salesTaxAmount = value;
    }

}
