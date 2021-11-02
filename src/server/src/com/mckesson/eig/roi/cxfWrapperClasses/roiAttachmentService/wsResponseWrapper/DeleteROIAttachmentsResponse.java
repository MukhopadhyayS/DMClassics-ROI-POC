
package com.mckesson.eig.roi.cxfWrapperClasses.roiAttachmentService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.AttachmentDeleteStatusList;


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
 *         &lt;element name="AttachmentDeleteStatusList" type="{urn:eig.mckesson.com}AttachmentDeleteStatusList"/>
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
    "attachmentDeleteStatusList"
})
@XmlRootElement(name = "deleteROIAttachmentsResponse")
public class DeleteROIAttachmentsResponse {

    @XmlElement(name = "AttachmentDeleteStatusList", required = true)
    protected AttachmentDeleteStatusList attachmentDeleteStatusList;

    /**
     * Gets the value of the attachmentDeleteStatusList property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentDeleteStatusList }
     *     
     */
    public AttachmentDeleteStatusList getAttachmentDeleteStatusList() {
        return attachmentDeleteStatusList;
    }

    /**
     * Sets the value of the attachmentDeleteStatusList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentDeleteStatusList }
     *     
     */
    public void setAttachmentDeleteStatusList(AttachmentDeleteStatusList value) {
        this.attachmentDeleteStatusList = value;
    }

}
