
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
 *         &lt;element name="attachmentLocation" type="{urn:eig.mckesson.com}attLocation"/>
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
    "attachmentLocation"
})
@XmlRootElement(name = "retrieveAttachmentLocationResponse")
public class RetrieveAttachmentLocationResponse {

    @XmlElement(required = true)
    protected AttLocation attachmentLocation;

    /**
     * Gets the value of the attachmentLocation property.
     * 
     * @return
     *     possible object is
     *     {@link AttLocation }
     *     
     */
    public AttLocation getAttachmentLocation() {
        return attachmentLocation;
    }

    /**
     * Sets the value of the attachmentLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttLocation }
     *     
     */
    public void setAttachmentLocation(AttLocation value) {
        this.attachmentLocation = value;
    }

}
