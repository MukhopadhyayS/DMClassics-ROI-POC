
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
 * <p>Java class for RequestDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="encounterSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="chartOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="dateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="globalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="roiVersions" type="{urn:eig.mckesson.com}RequestVersion" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestDocument", propOrder = {
    "documentSeq",
    "encounterSeq",
    "mrn",
    "encounter",
    "facility",
    "name",
    "subtitle",
    "chartOrder",
    "docId",
    "docTypeId",
    "dateTime",
    "patientSeq",
    "globalDocument",
    "roiVersions"
})
public class RequestDocument {

    protected long documentSeq;
    protected long encounterSeq;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected String chartOrder;
    protected long docId;
    protected long docTypeId;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTime;
    protected long patientSeq;
    protected boolean globalDocument;
    protected List<RequestVersion> roiVersions;

    /**
     * Gets the value of the documentSeq property.
     * 
     */
    public long getDocumentSeq() {
        return documentSeq;
    }

    /**
     * Sets the value of the documentSeq property.
     * 
     */
    public void setDocumentSeq(long value) {
        this.documentSeq = value;
    }

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
     * Gets the value of the subtitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Sets the value of the subtitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtitle(String value) {
        this.subtitle = value;
    }

    /**
     * Gets the value of the chartOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChartOrder() {
        return chartOrder;
    }

    /**
     * Sets the value of the chartOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChartOrder(String value) {
        this.chartOrder = value;
    }

    /**
     * Gets the value of the docId property.
     * 
     */
    public long getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     */
    public void setDocId(long value) {
        this.docId = value;
    }

    /**
     * Gets the value of the docTypeId property.
     * 
     */
    public long getDocTypeId() {
        return docTypeId;
    }

    /**
     * Sets the value of the docTypeId property.
     * 
     */
    public void setDocTypeId(long value) {
        this.docTypeId = value;
    }

    /**
     * Gets the value of the dateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    /**
     * Sets the value of the dateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTime(XMLGregorianCalendar value) {
        this.dateTime = value;
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
     * Gets the value of the globalDocument property.
     * 
     */
    public boolean isGlobalDocument() {
        return globalDocument;
    }

    /**
     * Sets the value of the globalDocument property.
     * 
     */
    public void setGlobalDocument(boolean value) {
        this.globalDocument = value;
    }

    /**
     * Gets the value of the roiVersions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roiVersions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoiVersions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestVersion }
     * 
     * 
     */
    public List<RequestVersion> getRoiVersions() {
        if (roiVersions == null) {
            roiVersions = new ArrayList<RequestVersion>();
        }
        return this.roiVersions;
    }

}
