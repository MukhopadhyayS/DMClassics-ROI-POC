
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupplementarityAttachmentResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplementarityAttachmentResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="supplementarityAttachmentResult" type="{urn:eig.mckesson.com}SupplementarityAttachments"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplementarityAttachmentResult", propOrder = {
    "supplementarityAttachmentResult"
})
public class SupplementarityAttachmentResult {

    @XmlElement(required = true)
    protected SupplementarityAttachments supplementarityAttachmentResult;

    /**
     * Gets the value of the supplementarityAttachmentResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityAttachments }
     *     
     */
    public SupplementarityAttachments getSupplementarityAttachmentResult() {
        return supplementarityAttachmentResult;
    }

    /**
     * Sets the value of the supplementarityAttachmentResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityAttachments }
     *     
     */
    public void setSupplementarityAttachmentResult(SupplementarityAttachments value) {
        this.supplementarityAttachmentResult = value;
    }

}
