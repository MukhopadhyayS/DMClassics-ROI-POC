
package com.mckesson.eig.roi.cxfWrapperClasses.roiSupplementaryService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment;


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
 *         &lt;element name="supplementalAttachmentsResult" type="{urn:eig.mckesson.com}SupplementalAttachmentResult"/>
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
    "supplementalAttachmentsResult"
})
@XmlRootElement(name = "createSupplementalAttachmentResponse")
public class CreateSupplementalAttachmentResponse {

    @XmlElement(required = true)
    protected ROISupplementalAttachment supplementalAttachmentsResult;

    /**
     * Gets the value of the supplementalAttachmentsResult property.
     * 
     * @return
     *     possible object is
     *     {@link SupplementalAttachmentResult }
     *     
     */
    public ROISupplementalAttachment getSupplementalAttachmentsResult() {
        return supplementalAttachmentsResult;
    }

    /**
     * Sets the value of the supplementalAttachmentsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplementalAttachmentResult }
     *     
     */
    public void setSupplementalAttachmentsResult(ROISupplementalAttachment value) {
        this.supplementalAttachmentsResult = value;
    }

}
