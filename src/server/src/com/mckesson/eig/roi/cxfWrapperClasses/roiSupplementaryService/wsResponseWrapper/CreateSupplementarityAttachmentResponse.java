
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachments;


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
 *         &lt;element name="supplementarityAttachmentsResult" type="{urn:eig.mckesson.com}SupplementarityAttachmentResult"/>
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
    "supplementarityAttachmentsResult"
})
@XmlRootElement(name = "createSupplementarityAttachmentResponse")
public class CreateSupplementarityAttachmentResponse {

    @XmlElement(required = true)
    protected ROISupplementarityAttachments supplementarityAttachmentsResult;

    /**
     * Gets the value of the supplementarityAttachmentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementarityAttachmentResult }
     *     
     */
    public ROISupplementarityAttachments getSupplementarityAttachmentsResult() {
        return supplementarityAttachmentsResult;
    }

    /**
     * Sets the value of the supplementarityAttachmentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementarityAttachmentResult }
     *     
     */
    public void setSupplementarityAttachmentsResult(ROISupplementarityAttachments value) {
        this.supplementarityAttachmentsResult = value;
    }

}
