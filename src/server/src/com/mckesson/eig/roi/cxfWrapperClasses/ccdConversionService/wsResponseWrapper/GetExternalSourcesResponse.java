
package com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.ccd.provider.local.CcdSourceDtoList;


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
 *         &lt;element name="ccdSourceDtoList" type="{urn:eig.mckesson.com}CcdSourceDtoList"/>
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
    "ccdSourceDtoList"
})
@XmlRootElement(name = "getExternalSourcesResponse")
public class GetExternalSourcesResponse {

    @XmlElement(required = true)
    protected CcdSourceDtoList ccdSourceDtoList;

    /**
     * Gets the value of the ccdSourceDtoList property.
     * 
     * @return
     *     possible object is
     *     {@link CcdSourceDtoList }
     *     
     */
    public CcdSourceDtoList getCcdSourceDtoList() {
        return ccdSourceDtoList;
    }

    /**
     * Sets the value of the ccdSourceDtoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcdSourceDtoList }
     *     
     */
    public void setCcdSourceDtoList(CcdSourceDtoList value) {
        this.ccdSourceDtoList = value;
    }

}
