
package com.mckesson.eig.roi.cxfWrapperClasses.roiAttachmentService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.supplementary.model.AttachmentInfoList;


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
 *         &lt;element name="AttachmentInfoList" type="{urn:eig.mckesson.com}AttachmentInfoList"/>
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
    "attachmentInfoList"
})
@XmlRootElement(name = "getAllAttachmentsResponse")
public class GetAllAttachmentsResponse {

    @XmlElement(name = "AttachmentInfoList", required = true)
    protected AttachmentInfoList attachmentInfoList;

    /**
     * Gets the value of the attachmentInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentInfoList }
     *     
     */
    public AttachmentInfoList getAttachmentInfoList() {
        return attachmentInfoList;
    }

    /**
     * Sets the value of the attachmentInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentInfoList }
     *     
     */
    public void setAttachmentInfoList(AttachmentInfoList value) {
        this.attachmentInfoList = value;
    }

}
