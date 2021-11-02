
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
 *         &lt;element name="mediaTypeDetails" type="{urn:eig.mckesson.com}MediaType"/>
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
    "mediaTypeDetails"
})
@XmlRootElement(name = "retrieveMediaTypeResponse")
public class RetrieveMediaTypeResponse {

    @XmlElement(required = true)
    protected MediaType mediaTypeDetails;

    /**
     * Gets the value of the mediaTypeDetails property.
     * 
     * @return
     *     possible object is
     *     {@link MediaType }
     *     
     */
    public MediaType getMediaTypeDetails() {
        return mediaTypeDetails;
    }

    /**
     * Sets the value of the mediaTypeDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaType }
     *     
     */
    public void setMediaTypeDetails(MediaType value) {
        this.mediaTypeDetails = value;
    }

}
