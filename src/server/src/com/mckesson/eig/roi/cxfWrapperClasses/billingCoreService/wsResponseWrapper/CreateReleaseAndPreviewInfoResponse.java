
package com.mckesson.eig.roi.cxfWrapperClasses.billingCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.ReleaseAndPreviewInfo;


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
 *         &lt;element name="releaseAndPreviewInfo" type="{urn:eig.mckesson.com}ReleaseAndPreviewInfo"/>
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
    "releaseAndPreviewInfo"
})
@XmlRootElement(name = "createReleaseAndPreviewInfoResponse")
public class CreateReleaseAndPreviewInfoResponse {

    @XmlElement(required = true)
    protected ReleaseAndPreviewInfo releaseAndPreviewInfo;

    /**
     * Gets the value of the releaseAndPreviewInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ReleaseAndPreviewInfo }
     *     
     */
    public ReleaseAndPreviewInfo getReleaseAndPreviewInfo() {
        return releaseAndPreviewInfo;
    }

    /**
     * Sets the value of the releaseAndPreviewInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReleaseAndPreviewInfo }
     *     
     */
    public void setReleaseAndPreviewInfo(ReleaseAndPreviewInfo value) {
        this.releaseAndPreviewInfo = value;
    }

}
