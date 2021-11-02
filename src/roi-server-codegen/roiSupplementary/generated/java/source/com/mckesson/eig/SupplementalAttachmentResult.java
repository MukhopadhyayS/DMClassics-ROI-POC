
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementalAttachmentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementalAttachmentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementalAttachmentResult" type="{urn:eig.mckesson.com}SupplementalAttachments"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementalAttachmentResult", propOrder = {
    "supplementalAttachmentResult"
})
public class SupplementalAttachmentResult {

    @XmlElement(required = true)
    protected SupplementalAttachments supplementalAttachmentResult;

    /**
     * Gets the value of the supplementalAttachmentResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalAttachments }
     *     
     */
    public SupplementalAttachments getSupplementalAttachmentResult() {
        return supplementalAttachmentResult;
    }

    /**
     * Sets the value of the supplementalAttachmentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalAttachments }
     *     
     */
    public void setSupplementalAttachmentResult(SupplementalAttachments value) {
        this.supplementalAttachmentResult = value;
    }

}
