
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attLocation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attLocation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attachmentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="attachmentLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attLocation", propOrder = {
    "attachmentID",
    "attachmentLocation"
})
public class AttLocation {

    protected long attachmentID;
    @XmlElement(required = true)
    protected String attachmentLocation;

    /**
     * Gets the value of the attachmentID property.
     * 
     */
    public long getAttachmentID() {
        return attachmentID;
    }

    /**
     * Sets the value of the attachmentID property.
     * 
     */
    public void setAttachmentID(long value) {
        this.attachmentID = value;
    }

    /**
     * Gets the value of the attachmentLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    /**
     * Sets the value of the attachmentLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentLocation(String value) {
        this.attachmentLocation = value;
    }

}
