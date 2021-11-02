
package com.mckesson.eig.roi.cxfWrapperClasses.billingAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.MediaTypesList;


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
 *         &lt;element name="mediaTypes" type="{urn:eig.mckesson.com}MediaTypeList"/>
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
    "mediaTypes"
})
@XmlRootElement(name = "retrieveAllMediaTypesResponse")
public class RetrieveAllMediaTypesResponse {

    @XmlElement(required = true)
    protected MediaTypesList mediaTypes;

    /**
     * Gets the value of the mediaTypes property.
     * 
     * @return
     *     possible object is
     *     {@link MediaTypeList }
     *     
     */
    public MediaTypesList getMediaTypes() {
        return mediaTypes;
    }

    /**
     * Sets the value of the mediaTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaTypeList }
     *     
     */
    public void setMediaTypes(MediaTypesList value) {
        this.mediaTypes = value;
    }

}
