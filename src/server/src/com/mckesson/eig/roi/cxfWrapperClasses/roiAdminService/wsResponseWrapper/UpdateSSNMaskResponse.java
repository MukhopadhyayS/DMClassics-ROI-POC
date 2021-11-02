
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.SSNMask;


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
 *         &lt;element name="maskDetails" type="{urn:eig.mckesson.com}SSNMask"/>
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
    "maskDetails"
})
@XmlRootElement(name = "updateSSNMaskResponse")
public class UpdateSSNMaskResponse {

    @XmlElement(required = true)
    protected SSNMask maskDetails;

    /**
     * Gets the value of the maskDetails property.
     * 
     * @return
     *     possible object is
     *     {@link SSNMask }
     *     
     */
    public SSNMask getMaskDetails() {
        return maskDetails;
    }

    /**
     * Sets the value of the maskDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link SSNMask }
     *     
     */
    public void setMaskDetails(SSNMask value) {
        this.maskDetails = value;
    }

}
