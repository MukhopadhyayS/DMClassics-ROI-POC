
package com.mckesson.eig.roi.cxfWrapperClasses.roiOutputService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.output.model.OutputFeature;


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
 *         &lt;element name="OutputFeature" type="{urn:eig.mckesson.com}OutputFeature"/>
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
    "outputFeature"
})
@XmlRootElement(name = "getServicePropertiesResponse")
public class GetServicePropertiesResponse {

    @XmlElement(name = "OutputFeature", required = true)
    protected OutputFeature outputFeature;

    /**
     * Gets the value of the outputFeature property.
     * 
     * @return
     *     possible object is
     *     {@link OutputFeature }
     *     
     */
    public OutputFeature getOutputFeature() {
        return outputFeature;
    }

    /**
     * Sets the value of the outputFeature property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFeature }
     *     
     */
    public void setOutputFeature(OutputFeature value) {
        this.outputFeature = value;
    }

}
