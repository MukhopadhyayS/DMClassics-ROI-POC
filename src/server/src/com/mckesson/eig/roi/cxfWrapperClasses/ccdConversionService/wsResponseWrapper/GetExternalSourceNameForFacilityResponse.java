
package com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper;

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
 *         &lt;element name="externalsourcename" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "externalsourcename"
})
@XmlRootElement(name = "getExternalSourceNameForFacilityResponse")
public class GetExternalSourceNameForFacilityResponse {

    @XmlElement(required = true)
    protected String externalsourcename;

    /**
     * Gets the value of the externalsourcename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalsourcename() {
        return externalsourcename;
    }

    /**
     * Sets the value of the externalsourcename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalsourcename(String value) {
        this.externalsourcename = value;
    }

}
