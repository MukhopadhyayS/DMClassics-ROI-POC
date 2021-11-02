
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestSupplementalAttachment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestSupplementalAttachment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mrn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="supplementalId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentCoreSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encounter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeformfacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDeleted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateOfService" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="attachmentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileext" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="printable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submittedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSelectedForRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isReleased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="externalSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestSupplementalAttachment", propOrder = {
    "facility",
    "mrn",
    "patientSeq",
    "supplementalId",
    "attachmentSeq",
    "attachmentCoreSeq",
    "billingTierId",
    "type",
    "encounter",
    "docFacility",
    "freeformfacility",
    "subtitle",
    "isDeleted",
    "pageCount",
    "dateOfService",
    "attachmentDate",
    "uuid",
    "volume",
    "path",
    "filename",
    "filetype",
    "fileext",
    "printable",
    "submittedBy",
    "comment",
    "isSelectedForRelease",
    "isReleased",
    "externalSource"
})
public class RequestSupplementalAttachment {

    @XmlElement(required = true)
    protected String facility;
    @XmlElement(required = true)
    protected String mrn;
    protected long patientSeq;
    protected long supplementalId;
    protected long attachmentSeq;
    protected long attachmentCoreSeq;
    protected long billingTierId;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String encounter;
    @XmlElement(required = true)
    protected String docFacility;
    @XmlElement(required = true)
    protected String freeformfacility;
    @XmlElement(required = true)
    protected String subtitle;
    @XmlElement(required = true)
    protected String isDeleted;
    @XmlElement(required = true)
    protected String pageCount;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateOfService;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar attachmentDate;
    @XmlElement(required = true)
    protected String uuid;
    @XmlElement(required = true)
    protected String volume;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected String filename;
    @XmlElement(required = true)
    protected String filetype;
    @XmlElement(required = true)
    protected String fileext;
    @XmlElement(required = true)
    protected String printable;
    @XmlElement(required = true)
    protected String submittedBy;
    @XmlElement(required = true)
    protected String comment;
    protected boolean isSelectedForRelease;
    protected boolean isReleased;
    @XmlElement(required = true)
    protected String externalSource;

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
     * Gets the value of the attachmentSeq property.
     * 
     */
    public long getAttachmentSeq() {
        return attachmentSeq;
    }

    /**
     * Sets the value of the attachmentSeq property.
     * 
     */
    public void setAttachmentSeq(long value) {
        this.attachmentSeq = value;
    }

    /**
     * Gets the value of the attachmentCoreSeq property.
     * 
     */
    public long getAttachmentCoreSeq() {
        return attachmentCoreSeq;
    }

    /**
     * Sets the value of the attachmentCoreSeq property.
     * 
     */
    public void setAttachmentCoreSeq(long value) {
        this.attachmentCoreSeq = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     * Gets the value of the isDeleted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * Sets the value of the isDeleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDeleted(String value) {
        this.isDeleted = value;
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
     * Gets the value of the attachmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAttachmentDate() {
        return attachmentDate;
    }

    /**
     * Sets the value of the attachmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAttachmentDate(XMLGregorianCalendar value) {
        this.attachmentDate = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolume(String value) {
        this.volume = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the filetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * Sets the value of the filetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiletype(String value) {
        this.filetype = value;
    }

    /**
     * Gets the value of the fileext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileext() {
        return fileext;
    }

    /**
     * Sets the value of the fileext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileext(String value) {
        this.fileext = value;
    }

    /**
     * Gets the value of the printable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintable() {
        return printable;
    }

    /**
     * Sets the value of the printable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintable(String value) {
        this.printable = value;
    }

    /**
     * Gets the value of the submittedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * Sets the value of the submittedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmittedBy(String value) {
        this.submittedBy = value;
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

    /**
     * Gets the value of the externalSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalSource() {
        return externalSource;
    }

    /**
     * Sets the value of the externalSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalSource(String value) {
        this.externalSource = value;
    }

}
