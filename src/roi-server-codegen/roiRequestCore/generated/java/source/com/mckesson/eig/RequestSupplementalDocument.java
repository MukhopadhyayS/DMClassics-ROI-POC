
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestSupplementalDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestSupplementalDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="documentCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformfacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestSupplementalDocument", propOrder = {
    "documentSeq",
    "documentCoreSeq",
    "supplementalId",
    "patientSeq",
    "billingTierId",
    "mrn",
    "facility",
    "docName",
    "encounter",
    "docFacility",
    "freeformfacility",
    "department",
    "subtitle",
    "pageCount",
    "dateOfService",
    "comment",
    "isSelectedForRelease",
    "isReleased"
})
public class RequestSupplementalDocument {

    protected long documentSeq;
    protected long documentCoreSeq;
    protected long supplementalId;
    protected long patientSeq;
    protected long billingTierId;
    @XmlElement(required = true)
    protected String mrn;
    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String docName;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String docFacility;
    @XmlElement(required = true)
    protected String freeformfacility;
    @XmlElement(required = true)
    protected String department;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected String pageCount;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfService;
    @XmlElement(required = true)
    protected String comment;
    protected boolean isSelectedForRelease;
    protected boolean isReleased;

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
     * Gets the value of the documentCoreSeq property.
     * 
     */
    public long getDocumentCoreSeq() {
        return documentCoreSeq;
    }

    /**
     * Sets the value of the documentCoreSeq property.
     * 
     */
    public void setDocumentCoreSeq(long value) {
        this.documentCoreSeq = value;
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
     * Gets the value of the billingTierId property.
     * 
     */
    public long getBillingTierId() {
        return billingTierId;
    }

    /**
     * Sets the value of the billingTierId property.
     * 
     */
    public void setBillingTierId(long value) {
        this.billingTierId = value;
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
     * Gets the value of the freeformfacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeformfacility() {
        return freeformfacility;
    }

    /**
     * Sets the value of the freeformfacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeformfacility(String value) {
        this.freeformfacility = value;
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
     * Gets the value of the isSelectedForRelease property.
     * 
     */
    public boolean isIsSelectedForRelease() {
        return isSelectedForRelease;
    }

    /**
     * Sets the value of the isSelectedForRelease property.
     * 
     */
    public void setIsSelectedForRelease(boolean value) {
        this.isSelectedForRelease = value;
    }

    /**
     * Gets the value of the isReleased property.
     * 
     */
    public boolean isIsReleased() {
        return isReleased;
    }

    /**
     * Sets the value of the isReleased property.
     * 
     */
    public void setIsReleased(boolean value) {
        this.isReleased = value;
    }

}
