
package com.mckesson.eig.roi.cxfWrapperClasses.ccdConversionService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.ccd.model.CcdConvertValue;


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
 *         &lt;element name="ccdConvertResult" type="{urn:eig.mckesson.com}ccdConvertResult"/>
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
    "ccdConvertResult"
})
@XmlRootElement(name = "ccdConvertResponse")
public class CcdConvertResponse {

    @XmlElement(required = true)
    protected CcdConvertValue ccdConvertResult;

    /**
     * Gets the value of the ccdConvertResult property.
     * 
     * @return
     *     possible object is
     *     {@link CcdConvertResult }
     *     
     */
    public CcdConvertValue getCcdConvertResult() {
        return ccdConvertResult;
    }

    /**
     * Sets the value of the ccdConvertResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CcdConvertResult }
     *     
     */
    public void setCcdConvertResult(CcdConvertValue value) {
        this.ccdConvertResult = value;
    }

}
