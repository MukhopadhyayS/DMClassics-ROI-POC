
package com.mckesson.eig;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorSearchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorSearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allRequestors" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freeFormFacility" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="maxCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="activeRequestors" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="allStatus" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="patientRequestor" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorSearchCriteria", propOrder = {
    "allRequestors",
    "type",
    "requestorTypeName",
    "lastName",
    "firstName",
    "ssn",
    "mrn",
    "facility",
    "freeFormFacility",
    "epn",
    "dob",
    "maxCount",
    "activeRequestors",
    "allStatus",
    "patientRequestor"
})
public class RequestorSearchCriteria {

    protected boolean allRequestors;
    protected Long type;
    protected String requestorTypeName;
    protected String lastName;
    protected String firstName;
    protected String ssn;
    protected String mrn;
    protected String facility;
    protected boolean freeFormFacility;
    protected String epn;
    @XmlElementRef(name = "dob", namespace = "urn:eig.mckesson.com", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dob;
    protected int maxCount;
    protected boolean activeRequestors;
    protected boolean allStatus;
    protected boolean patientRequestor;

    /**
     * Gets the value of the allRequestors property.
     * 
     */
    public boolean isAllRequestors() {
        return allRequestors;
    }

    /**
     * Sets the value of the allRequestors property.
     * 
     */
    public void setAllRequestors(boolean value) {
        this.allRequestors = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setType(Long value) {
        this.type = value;
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
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the value of the ssn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsn(String value) {
        this.ssn = value;
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
     * Gets the value of the freeFormFacility property.
     * 
     */
    public boolean isFreeFormFacility() {
        return freeFormFacility;
    }

    /**
     * Sets the value of the freeFormFacility property.
     * 
     */
    public void setFreeFormFacility(boolean value) {
        this.freeFormFacility = value;
    }

    /**
     * Gets the value of the epn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEpn() {
        return epn;
    }

    /**
     * Sets the value of the epn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEpn(String value) {
        this.epn = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDob(JAXBElement<XMLGregorianCalendar> value) {
        this.dob = value;
    }

    /**
     * Gets the value of the maxCount property.
     * 
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Sets the value of the maxCount property.
     * 
     */
    public void setMaxCount(int value) {
        this.maxCount = value;
    }

    /**
     * Gets the value of the activeRequestors property.
     * 
     */
    public boolean isActiveRequestors() {
        return activeRequestors;
    }

    /**
     * Sets the value of the activeRequestors property.
     * 
     */
    public void setActiveRequestors(boolean value) {
        this.activeRequestors = value;
    }

    /**
     * Gets the value of the allStatus property.
     * 
     */
    public boolean isAllStatus() {
        return allStatus;
    }

    /**
     * Sets the value of the allStatus property.
     * 
     */
    public void setAllStatus(boolean value) {
        this.allStatus = value;
    }

    /**
     * Gets the value of the patientRequestor property.
     * 
     */
    public boolean isPatientRequestor() {
        return patientRequestor;
    }

    /**
     * Sets the value of the patientRequestor property.
     * 
     */
    public void setPatientRequestor(boolean value) {
        this.patientRequestor = value;
    }

}
