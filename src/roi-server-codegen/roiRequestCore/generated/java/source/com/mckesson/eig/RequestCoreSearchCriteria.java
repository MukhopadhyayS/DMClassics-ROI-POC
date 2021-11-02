
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreSearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSsn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientEpn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientDob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balanceDueOperator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="completedDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="completedDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="receiptDateFrom" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="receiptDateTo" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paginationData" type="{urn:eig.mckesson.com}PaginationData"/>
 *         &lt;element name="maxCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreSearchCriteria", propOrder = {
    "requestId",
    "patientLastName",
    "patientFirstName",
    "mrn",
    "patientSsn",
    "encounter",
    "patientEpn",
    "patientId",
    "patientDob",
    "facility",
    "requestStatus",
    "requestReason",
    "invoiceNumber",
    "balanceDue",
    "balanceDueOperator",
    "requestorName",
    "requestorTypeName",
    "requestorType",
    "requestorId",
    "completedDateFrom",
    "completedDateTo",
    "receiptDateFrom",
    "receiptDateTo",
    "paginationData",
    "maxCount"
})
public class RequestCoreSearchCriteria {

    protected long requestId;
    @XmlElement(required = true)
    protected String patientLastName;
    @XmlElement(required = true)
    protected String patientFirstName;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String patientSsn;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String patientEpn;
    protected long patientId;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar patientDob;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String requestStatus;
    @XmlElement(required = true)
    protected String requestReason;
    protected long invoiceNumber;
    protected double balanceDue;
    @XmlElement(required = true)
    protected String balanceDueOperator;
    @XmlElement(required = true)
    protected String requestorName;
    @XmlElement(required = true)
    protected String requestorTypeName;
    protected long requestorType;
    protected long requestorId;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completedDateFrom;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completedDateTo;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar receiptDateFrom;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar receiptDateTo;
    @XmlElement(required = true)
    protected PaginationData paginationData;
    protected long maxCount;

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
     * Gets the value of the patientLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientLastName() {
        return patientLastName;
    }

    /**
     * Sets the value of the patientLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientLastName(String value) {
        this.patientLastName = value;
    }

    /**
     * Gets the value of the patientFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientFirstName() {
        return patientFirstName;
    }

    /**
     * Sets the value of the patientFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientFirstName(String value) {
        this.patientFirstName = value;
    }

    /**
     * Gets the value of the mrn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMrn() {
        return mrn;
    }

    /**
     * Sets the value of the mrn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMrn(String value) {
        this.mrn = value;
    }

    /**
     * Gets the value of the patientSsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientSsn() {
        return patientSsn;
    }

    /**
     * Sets the value of the patientSsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientSsn(String value) {
        this.patientSsn = value;
    }

    /**
     * Gets the value of the encounter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounter() {
        return encounter;
    }

    /**
     * Sets the value of the encounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounter(String value) {
        this.encounter = value;
    }

    /**
     * Gets the value of the patientEpn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientEpn() {
        return patientEpn;
    }

    /**
     * Sets the value of the patientEpn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientEpn(String value) {
        this.patientEpn = value;
    }

    /**
     * Gets the value of the patientId property.
     * 
     */
    public long getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     */
    public void setPatientId(long value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the patientDob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPatientDob() {
        return patientDob;
    }

    /**
     * Sets the value of the patientDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPatientDob(XMLGregorianCalendar value) {
        this.patientDob = value;
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
     * Gets the value of the requestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestStatus() {
        return requestStatus;
    }

    /**
     * Sets the value of the requestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestStatus(String value) {
        this.requestStatus = value;
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
     * Gets the value of the balanceDueOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceDueOperator() {
        return balanceDueOperator;
    }

    /**
     * Sets the value of the balanceDueOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceDueOperator(String value) {
        this.balanceDueOperator = value;
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
     * Gets the value of the requestorTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorTypeName() {
        return requestorTypeName;
    }

    /**
     * Sets the value of the requestorTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorTypeName(String value) {
        this.requestorTypeName = value;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     */
    public long getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     */
    public void setRequestorType(long value) {
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
     * Gets the value of the completedDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompletedDateFrom() {
        return completedDateFrom;
    }

    /**
     * Sets the value of the completedDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompletedDateFrom(XMLGregorianCalendar value) {
        this.completedDateFrom = value;
    }

    /**
     * Gets the value of the completedDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompletedDateTo() {
        return completedDateTo;
    }

    /**
     * Sets the value of the completedDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompletedDateTo(XMLGregorianCalendar value) {
        this.completedDateTo = value;
    }

    /**
     * Gets the value of the receiptDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiptDateFrom() {
        return receiptDateFrom;
    }

    /**
     * Sets the value of the receiptDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiptDateFrom(XMLGregorianCalendar value) {
        this.receiptDateFrom = value;
    }

    /**
     * Gets the value of the receiptDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiptDateTo() {
        return receiptDateTo;
    }

    /**
     * Sets the value of the receiptDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiptDateTo(XMLGregorianCalendar value) {
        this.receiptDateTo = value;
    }

    /**
     * Gets the value of the paginationData property.
     * 
     * @return
     *     possible object is
     *     {@link PaginationData }
     *     
     */
    public PaginationData getPaginationData() {
        return paginationData;
    }

    /**
     * Sets the value of the paginationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginationData }
     *     
     */
    public void setPaginationData(PaginationData value) {
        this.paginationData = value;
    }

    /**
     * Gets the value of the maxCount property.
     * 
     */
    public long getMaxCount() {
        return maxCount;
    }

    /**
     * Sets the value of the maxCount property.
     * 
     */
    public void setMaxCount(long value) {
        this.maxCount = value;
    }

}
