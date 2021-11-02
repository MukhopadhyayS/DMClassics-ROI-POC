
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.OutputServerProperties;


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
 *         &lt;element name="outputServerProperties" type="{urn:eig.mckesson.com}OutputServerProperties"/>
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
    "outputServerProperties"
})
@XmlRootElement(name = "saveOutputServerProperties")
public class SaveOutputServerProperties {

    @XmlElement(required = true)
    protected OutputServerProperties outputServerProperties;

    /**
     * Gets the value of the outputServerProperties property.
     * 
     * @return
     *     possible object is
     *     {@link OutputServerProperties }
     *     
     */
    public OutputServerProperties getOutputServerProperties() {
        return outputServerProperties;
    }

    /**
     * Sets the value of the outputServerProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputServerProperties }
     *     
     */
    public void setOutputServerProperties(OutputServerProperties value) {
        this.outputServerProperties = value;
    }

}
