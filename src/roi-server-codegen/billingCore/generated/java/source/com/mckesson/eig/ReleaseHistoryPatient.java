
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReleaseHistoryPatient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseHistoryPatient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="epn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientLocked" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounterLocked" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseHistoryPatient", propOrder = {
    "patientSeq",
    "name",
    "gender",
    "dob",
    "ssn",
    "facility",
    "mrn",
    "epn",
    "patientLocked",
    "encounterLocked",
    "vip"
})
public class ReleaseHistoryPatient {

    protected long patientSeq;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String gender;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dob;
    @XmlElement(required = true)
    protected String ssn;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String epn;
    @XmlElement(required = true)
    protected String patientLocked;
    @XmlElement(required = true)
    protected String encounterLocked;
    @XmlElement(required = true)
    protected String vip;

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
     * Gets the value of the patientLocked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientLocked() {
        return patientLocked;
    }

    /**
     * Sets the value of the patientLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientLocked(String value) {
        this.patientLocked = value;
    }

    /**
     * Gets the value of the encounterLocked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterLocked() {
        return encounterLocked;
    }

    /**
     * Sets the value of the encounterLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterLocked(String value) {
        this.encounterLocked = value;
    }

    /**
     * Gets the value of the vip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVip() {
        return vip;
    }

    /**
     * Sets the value of the vip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVip(String value) {
        this.vip = value;
    }

}
