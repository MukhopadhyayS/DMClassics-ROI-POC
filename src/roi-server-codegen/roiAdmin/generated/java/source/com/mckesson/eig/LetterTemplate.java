
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LetterTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LetterTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="letterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="letterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uploadFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hasNotes" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LetterTemplate", propOrder = {
    "letterTemplateId",
    "name",
    "description",
    "isDefault",
    "letterType",
    "uploadFile",
    "docId",
    "recordVersion",
    "hasNotes"
})
public class LetterTemplate {

    protected long letterTemplateId;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String description;
    protected boolean isDefault;
    @XmlElement(required = true)
    protected String letterType;
    @XmlElement(required = true)
    protected String uploadFile;
    protected long docId;
    protected int recordVersion;
    protected boolean hasNotes;

    /**
     * Gets the value of the letterTemplateId property.
     * 
     */
    public long getLetterTemplateId() {
        return letterTemplateId;
    }

    /**
     * Sets the value of the letterTemplateId property.
     * 
     */
    public void setLetterTemplateId(long value) {
        this.letterTemplateId = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     */
    public void setIsDefault(boolean value) {
        this.isDefault = value;
    }

    /**
     * Gets the value of the letterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterType() {
        return letterType;
    }

    /**
     * Sets the value of the letterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterType(String value) {
        this.letterType = value;
    }

    /**
     * Gets the value of the uploadFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUploadFile() {
        return uploadFile;
    }

    /**
     * Sets the value of the uploadFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUploadFile(String value) {
        this.uploadFile = value;
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
     * Gets the value of the recordVersion property.
     * 
     */
    public int getRecordVersion() {
        return recordVersion;
    }

    /**
     * Sets the value of the recordVersion property.
     * 
     */
    public void setRecordVersion(int value) {
        this.recordVersion = value;
    }

    /**
     * Gets the value of the hasNotes property.
     * 
     */
    public boolean isHasNotes() {
        return hasNotes;
    }

    /**
     * Sets the value of the hasNotes property.
     * 
     */
    public void setHasNotes(boolean value) {
        this.hasNotes = value;
    }

}
