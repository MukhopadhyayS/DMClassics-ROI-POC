
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="mediaTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "mediaTypeId"
})
@XmlRootElement(name = "createMediaTypeResponse")
public class CreateMediaTypeResponse {

    protected long mediaTypeId;

    /**
     * Gets the value of the mediaTypeId property.
     * 
     */
    public long getMediaTypeId() {
        return mediaTypeId;
    }

    /**
     * Sets the value of the mediaTypeId property.
     * 
     */
    public void setMediaTypeId(long value) {
        this.mediaTypeId = value;
    }

}
