
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiptDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="completedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="statusReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReasonAttribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusChangedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedByUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="defaultFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultFacilityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorDetail" type="{urn:eig.mckesson.com}Requestor"/>
 *         &lt;element name="releaseCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="hasDraftRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="authDoc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocSubtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="conversionSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "id",
    "status",
    "receiptDate",
    "completedDate",
    "statusReason",
    "requestReason",
    "requestReasonAttribute",
    "statusChangedDt",
    "requestPassword",
    "createdBy",
    "modifiedBy",
    "modifiedDate",
    "modifiedByUser",
    "createdDate",
    "defaultFacility",
    "defaultFacilityCode",
    "requestorDetail",
    "releaseCount",
    "hasDraftRelease",
    "balanceDue",
    "authDoc",
    "authDocName",
    "authDocSubtitle",
    "authDocDateTime",
    "conversionSource"
})
public class Request {

    protected long id;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar receiptDate;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completedDate;
    @XmlElement(required = true)
    protected String statusReason;
    @XmlElement(required = true)
    protected String requestReason;
    @XmlElement(required = true)
    protected String requestReasonAttribute;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusChangedDt;
    @XmlElement(required = true)
    protected String requestPassword;
    protected long createdBy;
    protected long modifiedBy;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(required = true, nillable = true)
    protected String modifiedByUser;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(required = true)
    protected String defaultFacility;
    @XmlElement(required = true)
    protected String defaultFacilityCode;
    @XmlElement(required = true)
    protected Requestor requestorDetail;
    protected long releaseCount;
    protected boolean hasDraftRelease;
    protected double balanceDue;
    @XmlElement(required = true)
    protected String authDoc;
    @XmlElement(required = true)
    protected String authDocName;
    @XmlElement(required = true)
    protected String authDocSubtitle;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar authDocDateTime;
    @XmlElement(required = true)
    protected String conversionSource;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the receiptDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiptDate() {
        return receiptDate;
    }

    /**
     * Sets the value of the receiptDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiptDate(XMLGregorianCalendar value) {
        this.receiptDate = value;
    }

    /**
     * Gets the value of the completedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompletedDate() {
        return completedDate;
    }

    /**
     * Sets the value of the completedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompletedDate(XMLGregorianCalendar value) {
        this.completedDate = value;
    }

    /**
     * Gets the value of the statusReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * Sets the value of the statusReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusReason(String value) {
        this.statusReason = value;
    }

    /**
     * Gets the value of the requestReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestReason() {
        return requestReason;
    }

    /**
     * Sets the value of the requestReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestReason(String value) {
        this.requestReason = value;
    }

    /**
     * Gets the value of the requestReasonAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestReasonAttribute() {
        return requestReasonAttribute;
    }

    /**
     * Sets the value of the requestReasonAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestReasonAttribute(String value) {
        this.requestReasonAttribute = value;
    }

    /**
     * Gets the value of the statusChangedDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusChangedDt() {
        return statusChangedDt;
    }

    /**
     * Sets the value of the statusChangedDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusChangedDt(XMLGregorianCalendar value) {
        this.statusChangedDt = value;
    }

    /**
     * Gets the value of the requestPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestPassword() {
        return requestPassword;
    }

    /**
     * Sets the value of the requestPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestPassword(String value) {
        this.requestPassword = value;
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
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the modifiedByUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedByUser() {
        return modifiedByUser;
    }

    /**
     * Sets the value of the modifiedByUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedByUser(String value) {
        this.modifiedByUser = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the defaultFacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultFacility() {
        return defaultFacility;
    }

    /**
     * Sets the value of the defaultFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultFacility(String value) {
        this.defaultFacility = value;
    }

    /**
     * Gets the value of the defaultFacilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultFacilityCode() {
        return defaultFacilityCode;
    }

    /**
     * Sets the value of the defaultFacilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultFacilityCode(String value) {
        this.defaultFacilityCode = value;
    }

    /**
     * Gets the value of the requestorDetail property.
     * 
     * @return
     *     possible object is
     *     {@link Requestor }
     *     
     */
    public Requestor getRequestorDetail() {
        return requestorDetail;
    }

    /**
     * Sets the value of the requestorDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Requestor }
     *     
     */
    public void setRequestorDetail(Requestor value) {
        this.requestorDetail = value;
    }

    /**
     * Gets the value of the releaseCount property.
     * 
     */
    public long getReleaseCount() {
        return releaseCount;
    }

    /**
     * Sets the value of the releaseCount property.
     * 
     */
    public void setReleaseCount(long value) {
        this.releaseCount = value;
    }

    /**
     * Gets the value of the hasDraftRelease property.
     * 
     */
    public boolean isHasDraftRelease() {
        return hasDraftRelease;
    }

    /**
     * Sets the value of the hasDraftRelease property.
     * 
     */
    public void setHasDraftRelease(boolean value) {
        this.hasDraftRelease = value;
    }

    /**
     * Gets the value of the balanceDue property.
     * 
     */
    public double getBalanceDue() {
        return balanceDue;
    }

    /**
     * Sets the value of the balanceDue property.
     * 
     */
    public void setBalanceDue(double value) {
        this.balanceDue = value;
    }

    /**
     * Gets the value of the authDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthDoc() {
        return authDoc;
    }

    /**
     * Sets the value of the authDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthDoc(String value) {
        this.authDoc = value;
    }

    /**
     * Gets the value of the authDocName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthDocName() {
        return authDocName;
    }

    /**
     * Sets the value of the authDocName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthDocName(String value) {
        this.authDocName = value;
    }

    /**
     * Gets the value of the authDocSubtitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthDocSubtitle() {
        return authDocSubtitle;
    }

    /**
     * Sets the value of the authDocSubtitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthDocSubtitle(String value) {
        this.authDocSubtitle = value;
    }

    /**
     * Gets the value of the authDocDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuthDocDateTime() {
        return authDocDateTime;
    }

    /**
     * Sets the value of the authDocDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuthDocDateTime(XMLGregorianCalendar value) {
        this.authDocDateTime = value;
    }

    /**
     * Gets the value of the conversionSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionSource() {
        return conversionSource;
    }

    /**
     * Sets the value of the conversionSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionSource(String value) {
        this.conversionSource = value;
    }

}
