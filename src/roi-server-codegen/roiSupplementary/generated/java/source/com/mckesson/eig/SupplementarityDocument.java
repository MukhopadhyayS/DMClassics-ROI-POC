
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SupplementarityDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformFacility" type="{urn:eig.mckesson.com}FreeFormFacility"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityDocument", propOrder = {
    "id",
    "mrn",
    "facility",
    "comment",
    "dateOfService",
    "department",
    "docFacility",
    "docName",
    "encounter",
    "pageCount",
    "subtitle",
    "freeformFacility"
})
public class SupplementarityDocument {

    protected long id;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String comment;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfService;
    @XmlElement(required = true)
    protected String department;
    @XmlElement(required = true)
    protected String docFacility;
    @XmlElement(required = true)
    protected String docName;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String pageCount;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected FreeFormFacility freeformFacility;

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
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the dateOfService property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfService() {
        return dateOfService;
    }

    /**
     * Sets the value of the dateOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfService(XMLGregorianCalendar value) {
        this.dateOfService = value;
    }

    /**
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the docFacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocFacility() {
        return docFacility;
    }

    /**
     * Sets the value of the docFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocFacility(String value) {
        this.docFacility = value;
    }

    /**
     * Gets the value of the docName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Sets the value of the docName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocName(String value) {
        this.docName = value;
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
     * Gets the value of the pageCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageCount() {
        return pageCount;
    }

    /**
     * Sets the value of the pageCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageCount(String value) {
        this.pageCount = value;
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
     * Gets the value of the freeformFacility property.
     * 
     * @return
     *     possible object is
     *     {@link FreeFormFacility }
     *     
     */
    public FreeFormFacility getFreeformFacility() {
        return freeformFacility;
    }

    /**
     * Sets the value of the freeformFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeFormFacility }
     *     
     */
    public void setFreeformFacility(FreeFormFacility value) {
        this.freeformFacility = value;
    }

}
