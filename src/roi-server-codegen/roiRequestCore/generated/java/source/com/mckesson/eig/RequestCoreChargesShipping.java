
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreChargesShipping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesShipping">
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
 *         &lt;element name="shippingCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="postalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="addressType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingWeight" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newCountry" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="willReleaseShipped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="shippingMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingMethodId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nonPrintableAttachmentQueue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesShipping", propOrder = {
    "id",
    "createdBy",
    "modifiedBy",
    "createdDt",
    "modifiedDt",
    "recordVersion",
    "requestCoreChargesSeq",
    "shippingCharge",
    "postalCode",
    "addressType",
    "state",
    "shippingUrl",
    "address1",
    "address2",
    "shippingWeight",
    "trackingNumber",
    "address3",
    "city",
    "countryCode",
    "countryName",
    "newCountry",
    "willReleaseShipped",
    "shippingMethod",
    "outputMethod",
    "shippingMethodId",
    "nonPrintableAttachmentQueue"
})
public class RequestCoreChargesShipping {

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
    protected double shippingCharge;
    @XmlElement(required = true)
    protected String postalCode;
    @XmlElement(required = true)
    protected String addressType;
    @XmlElement(required = true)
    protected String state;
    @XmlElement(required = true)
    protected String shippingUrl;
    @XmlElement(required = true)
    protected String address1;
    @XmlElement(required = true)
    protected String address2;
    protected double shippingWeight;
    @XmlElement(required = true)
    protected String trackingNumber;
    @XmlElement(required = true)
    protected String address3;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String countryCode;
    @XmlElement(required = true)
    protected String countryName;
    protected boolean newCountry;
    protected boolean willReleaseShipped;
    @XmlElement(required = true)
    protected String shippingMethod;
    @XmlElement(required = true)
    protected String outputMethod;
    protected long shippingMethodId;
    @XmlElement(required = true)
    protected String nonPrintableAttachmentQueue;

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
     * Gets the value of the shippingCharge property.
     * 
     */
    public double getShippingCharge() {
        return shippingCharge;
    }

    /**
     * Sets the value of the shippingCharge property.
     * 
     */
    public void setShippingCharge(double value) {
        this.shippingCharge = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressType(String value) {
        this.addressType = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the shippingUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingUrl() {
        return shippingUrl;
    }

    /**
     * Sets the value of the shippingUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingUrl(String value) {
        this.shippingUrl = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the shippingWeight property.
     * 
     */
    public double getShippingWeight() {
        return shippingWeight;
    }

    /**
     * Sets the value of the shippingWeight property.
     * 
     */
    public void setShippingWeight(double value) {
        this.shippingWeight = value;
    }

    /**
     * Gets the value of the trackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Sets the value of the trackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumber(String value) {
        this.trackingNumber = value;
    }

    /**
     * Gets the value of the address3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * Sets the value of the address3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress3(String value) {
        this.address3 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the newCountry property.
     * 
     */
    public boolean isNewCountry() {
        return newCountry;
    }

    /**
     * Sets the value of the newCountry property.
     * 
     */
    public void setNewCountry(boolean value) {
        this.newCountry = value;
    }

    /**
     * Gets the value of the willReleaseShipped property.
     * 
     */
    public boolean isWillReleaseShipped() {
        return willReleaseShipped;
    }

    /**
     * Sets the value of the willReleaseShipped property.
     * 
     */
    public void setWillReleaseShipped(boolean value) {
        this.willReleaseShipped = value;
    }

    /**
     * Gets the value of the shippingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Sets the value of the shippingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShippingMethod(String value) {
        this.shippingMethod = value;
    }

    /**
     * Gets the value of the outputMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputMethod() {
        return outputMethod;
    }

    /**
     * Sets the value of the outputMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputMethod(String value) {
        this.outputMethod = value;
    }

    /**
     * Gets the value of the shippingMethodId property.
     * 
     */
    public long getShippingMethodId() {
        return shippingMethodId;
    }

    /**
     * Sets the value of the shippingMethodId property.
     * 
     */
    public void setShippingMethodId(long value) {
        this.shippingMethodId = value;
    }

    /**
     * Gets the value of the nonPrintableAttachmentQueue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonPrintableAttachmentQueue() {
        return nonPrintableAttachmentQueue;
    }

    /**
     * Sets the value of the nonPrintableAttachmentQueue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonPrintableAttachmentQueue(String value) {
        this.nonPrintableAttachmentQueue = value;
    }

}
