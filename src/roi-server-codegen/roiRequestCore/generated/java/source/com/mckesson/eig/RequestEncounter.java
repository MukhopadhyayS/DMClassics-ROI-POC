
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
 * <p>Java class for RequestEncounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestEncounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="encounterSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="roiDocuments" type="{urn:eig.mckesson.com}RequestDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isVip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isLocked" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="hasDeficiency" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="admitdate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dischargeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestEncounter", propOrder = {
    "encounterSeq",
    "patientSeq",
    "roiDocuments",
    "name",
    "mrn",
    "facility",
    "patientType",
    "patientService",
    "isVip",
    "isLocked",
    "hasDeficiency",
    "admitdate",
    "dischargeDate"
})
public class RequestEncounter {

    protected long encounterSeq;
    protected long patientSeq;
    protected List<RequestDocument> roiDocuments;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String patientType;
    @XmlElement(required = true)
    protected String patientService;
    protected boolean isVip;
    protected boolean isLocked;
    protected boolean hasDeficiency;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar admitdate;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dischargeDate;

    /**
     * Gets the value of the encounterSeq property.
     * 
     */
    public long getEncounterSeq() {
        return encounterSeq;
    }

    /**
     * Sets the value of the encounterSeq property.
     * 
     */
    public void setEncounterSeq(long value) {
        this.encounterSeq = value;
    }

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
     * Gets the value of the roiDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roiDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoiDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestDocument }
     * 
     * 
     */
    public List<RequestDocument> getRoiDocuments() {
        if (roiDocuments == null) {
            roiDocuments = new ArrayList<RequestDocument>();
        }
        return this.roiDocuments;
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
     * Gets the value of the patientType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientType() {
        return patientType;
    }

    /**
     * Sets the value of the patientType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientType(String value) {
        this.patientType = value;
    }

    /**
     * Gets the value of the patientService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientService() {
        return patientService;
    }

    /**
     * Sets the value of the patientService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientService(String value) {
        this.patientService = value;
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
     * Gets the value of the isLocked property.
     * 
     */
    public boolean isIsLocked() {
        return isLocked;
    }

    /**
     * Sets the value of the isLocked property.
     * 
     */
    public void setIsLocked(boolean value) {
        this.isLocked = value;
    }

    /**
     * Gets the value of the hasDeficiency property.
     * 
     */
    public boolean isHasDeficiency() {
        return hasDeficiency;
    }

    /**
     * Sets the value of the hasDeficiency property.
     * 
     */
    public void setHasDeficiency(boolean value) {
        this.hasDeficiency = value;
    }

    /**
     * Gets the value of the admitdate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAdmitdate() {
        return admitdate;
    }

    /**
     * Sets the value of the admitdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAdmitdate(XMLGregorianCalendar value) {
        this.admitdate = value;
    }

    /**
     * Gets the value of the dischargeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDischargeDate() {
        return dischargeDate;
    }

    /**
     * Sets the value of the dischargeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDischargeDate(XMLGregorianCalendar value) {
        this.dischargeDate = value;
    }

}
