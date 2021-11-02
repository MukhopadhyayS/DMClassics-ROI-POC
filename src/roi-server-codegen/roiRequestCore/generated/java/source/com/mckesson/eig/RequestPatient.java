
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestPatient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestPatient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="encounterLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isVip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hpf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiEncounters" type="{urn:eig.mckesson.com}RequestEncounter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachments" type="{urn:eig.mckesson.com}RequestSupplementalAttachment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nonHpfDocuments" type="{urn:eig.mckesson.com}RequestSupplementalDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="globalDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="homePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="workPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestPatient", propOrder = {
    "patientSeq",
    "requestId",
    "mrn",
    "name",
    "facility",
    "freeformFacility",
    "dob",
    "gender",
    "epn",
    "ssn",
    "patientLocked",
    "encounterLocked",
    "isVip",
    "hpf",
    "roiEncounters",
    "supplementalId",
    "attachments",
    "nonHpfDocuments",
    "globalDocuments",
    "firstName",
    "lastName",
    "address1",
    "address2",
    "address3",
    "city",
    "state",
    "zip",
    "homePhone",
    "workPhone"
})
public class RequestPatient {

    protected long patientSeq;
    protected long requestId;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String freeformFacility;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dob;
    @XmlElement(required = true)
    protected String gender;
    @XmlElement(required = true)
    protected String epn;
    @XmlElement(required = true)
    protected String ssn;
    protected boolean patientLocked;
    protected boolean encounterLocked;
    protected boolean isVip;
    protected boolean hpf;
    protected List<RequestEncounter> roiEncounters;
    protected long supplementalId;
    protected List<RequestSupplementalAttachment> attachments;
    protected List<RequestSupplementalDocument> nonHpfDocuments;
    protected List<RequestDocument> globalDocuments;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String address1;
    @XmlElement(required = true)
    protected String address2;
    @XmlElement(required = true)
    protected String address3;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String state;
    @XmlElement(required = true)
    protected String zip;
    @XmlElement(required = true)
    protected String homePhone;
    @XmlElement(required = true)
    protected String workPhone;

    /**
     * Gets the value of the patientSeq property.
     * 
     */
    public long getPatientSeq() {
        return patientSeq;
    }

    /**
     * Sets the value of the patientSeq property.
     * 
     */
    public void setPatientSeq(long value) {
        this.patientSeq = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     * Gets the value of the freeformFacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeformFacility() {
        return freeformFacility;
    }

    /**
     * Sets the value of the freeformFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeformFacility(String value) {
        this.freeformFacility = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDob(XMLGregorianCalendar value) {
        this.dob = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
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
     * Gets the value of the patientLocked property.
     * 
     */
    public boolean isPatientLocked() {
        return patientLocked;
    }

    /**
     * Sets the value of the patientLocked property.
     * 
     */
    public void setPatientLocked(boolean value) {
        this.patientLocked = value;
    }

    /**
     * Gets the value of the encounterLocked property.
     * 
     */
    public boolean isEncounterLocked() {
        return encounterLocked;
    }

    /**
     * Sets the value of the encounterLocked property.
     * 
     */
    public void setEncounterLocked(boolean value) {
        this.encounterLocked = value;
    }

    /**
     * Gets the value of the isVip property.
     * 
     */
    public boolean isIsVip() {
        return isVip;
    }

    /**
     * Sets the value of the isVip property.
     * 
     */
    public void setIsVip(boolean value) {
        this.isVip = value;
    }

    /**
     * Gets the value of the hpf property.
     * 
     */
    public boolean isHpf() {
        return hpf;
    }

    /**
     * Sets the value of the hpf property.
     * 
     */
    public void setHpf(boolean value) {
        this.hpf = value;
    }

    /**
     * Gets the value of the roiEncounters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roiEncounters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoiEncounters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestEncounter }
     * 
     * 
     */
    public List<RequestEncounter> getRoiEncounters() {
        if (roiEncounters == null) {
            roiEncounters = new ArrayList<RequestEncounter>();
        }
        return this.roiEncounters;
    }

    /**
     * Gets the value of the supplementalId property.
     * 
     */
    public long getSupplementalId() {
        return supplementalId;
    }

    /**
     * Sets the value of the supplementalId property.
     * 
     */
    public void setSupplementalId(long value) {
        this.supplementalId = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestSupplementalAttachment }
     * 
     * 
     */
    public List<RequestSupplementalAttachment> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<RequestSupplementalAttachment>();
        }
        return this.attachments;
    }

    /**
     * Gets the value of the nonHpfDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nonHpfDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNonHpfDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestSupplementalDocument }
     * 
     * 
     */
    public List<RequestSupplementalDocument> getNonHpfDocuments() {
        if (nonHpfDocuments == null) {
            nonHpfDocuments = new ArrayList<RequestSupplementalDocument>();
        }
        return this.nonHpfDocuments;
    }

    /**
     * Gets the value of the globalDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the globalDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlobalDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestDocument }
     * 
     * 
     */
    public List<RequestDocument> getGlobalDocuments() {
        if (globalDocuments == null) {
            globalDocuments = new ArrayList<RequestDocument>();
        }
        return this.globalDocuments;
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
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the homePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the value of the homePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomePhone(String value) {
        this.homePhone = value;
    }

    /**
     * Gets the value of the workPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhone(String value) {
        this.workPhone = value;
    }

}
