
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocTypeAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocTypeAudit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codeSetName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nonDocumentTypeChangeAudit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocTypeAudit", propOrder = {
    "docType",
    "docName",
    "fromValue",
    "toValue",
    "codeSetName",
    "nonDocumentTypeChangeAudit"
})
public class DocTypeAudit {

    @XmlElement(required = true)
    protected String docType;
    @XmlElement(required = true)
    protected String docName;
    @XmlElement(required = true)
    protected String fromValue;
    @XmlElement(required = true)
    protected String toValue;
    @XmlElement(required = true)
    protected String codeSetName;
    @XmlElement(required = true)
    protected String nonDocumentTypeChangeAudit;

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
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
     * Gets the value of the fromValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromValue() {
        return fromValue;
    }

    /**
     * Sets the value of the fromValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromValue(String value) {
        this.fromValue = value;
    }

    /**
     * Gets the value of the toValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToValue() {
        return toValue;
    }

    /**
     * Sets the value of the toValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToValue(String value) {
        this.toValue = value;
    }

    /**
     * Gets the value of the codeSetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSetName() {
        return codeSetName;
    }

    /**
     * Sets the value of the codeSetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSetName(String value) {
        this.codeSetName = value;
    }

    /**
     * Gets the value of the nonDocumentTypeChangeAudit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonDocumentTypeChangeAudit() {
        return nonDocumentTypeChangeAudit;
    }

    /**
     * Sets the value of the nonDocumentTypeChangeAudit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonDocumentTypeChangeAudit(String value) {
        this.nonDocumentTypeChangeAudit = value;
    }

}
