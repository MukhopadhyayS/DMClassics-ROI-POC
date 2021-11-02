
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codeSetId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="docTypeDesignation" type="{urn:eig.mckesson.com}DocTypeDesignations"/>
 *         &lt;element name="docTypeAuditList" type="{urn:eig.mckesson.com}DocTypeAuditList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codeSetId",
    "docTypeDesignation",
    "docTypeAuditList"
})
@XmlRootElement(name = "designateDocumentTypes")
public class DesignateDocumentTypes {

    protected long codeSetId;
    @XmlElement(required = true)
    protected DocTypeDesignations docTypeDesignation;
    @XmlElement(required = true)
    protected DocTypeAuditList docTypeAuditList;

    /**
     * Gets the value of the codeSetId property.
     * 
     */
    public long getCodeSetId() {
        return codeSetId;
    }

    /**
     * Sets the value of the codeSetId property.
     * 
     */
    public void setCodeSetId(long value) {
        this.codeSetId = value;
    }

    /**
     * Gets the value of the docTypeDesignation property.
     * 
     * @return
     *     possible object is
     *     {@link DocTypeDesignations }
     *     
     */
    public DocTypeDesignations getDocTypeDesignation() {
        return docTypeDesignation;
    }

    /**
     * Sets the value of the docTypeDesignation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocTypeDesignations }
     *     
     */
    public void setDocTypeDesignation(DocTypeDesignations value) {
        this.docTypeDesignation = value;
    }

    /**
     * Gets the value of the docTypeAuditList property.
     * 
     * @return
     *     possible object is
     *     {@link DocTypeAuditList }
     *     
     */
    public DocTypeAuditList getDocTypeAuditList() {
        return docTypeAuditList;
    }

    /**
     * Sets the value of the docTypeAuditList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocTypeAuditList }
     *     
     */
    public void setDocTypeAuditList(DocTypeAuditList value) {
        this.docTypeAuditList = value;
    }

}
