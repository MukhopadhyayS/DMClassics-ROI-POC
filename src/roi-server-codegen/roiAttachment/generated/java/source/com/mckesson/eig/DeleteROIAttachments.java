
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
 *         &lt;element name="AttachmentSequenceList" type="{urn:eig.mckesson.com}AttachmentSequenceList"/>
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
    "attachmentSequenceList"
})
@XmlRootElement(name = "deleteROIAttachments")
public class DeleteROIAttachments {

    @XmlElement(name = "AttachmentSequenceList", required = true)
    protected AttachmentSequenceList attachmentSequenceList;

    /**
     * Gets the value of the attachmentSequenceList property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentSequenceList }
     *     
     */
    public AttachmentSequenceList getAttachmentSequenceList() {
        return attachmentSequenceList;
    }

    /**
     * Sets the value of the attachmentSequenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentSequenceList }
     *     
     */
    public void setAttachmentSequenceList(AttachmentSequenceList value) {
        this.attachmentSequenceList = value;
    }

}
