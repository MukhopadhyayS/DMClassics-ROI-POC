
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
 *         &lt;element name="configureDaysStatusReqElement" type="{urn:eig.mckesson.com}ConfigureDaysDtoList"/>
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
    "configureDaysStatusReqElement"
})
@XmlRootElement(name = "retrieveConfigureDaysStatusResponse")
public class RetrieveConfigureDaysStatusResponse {

    @XmlElement(required = true)
    protected ConfigureDaysDtoList configureDaysStatusReqElement;

    /**
     * Gets the value of the configureDaysStatusReqElement property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigureDaysDtoList }
     *     
     */
    public ConfigureDaysDtoList getConfigureDaysStatusReqElement() {
        return configureDaysStatusReqElement;
    }

    /**
     * Sets the value of the configureDaysStatusReqElement property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigureDaysDtoList }
     *     
     */
    public void setConfigureDaysStatusReqElement(ConfigureDaysDtoList value) {
        this.configureDaysStatusReqElement = value;
    }

}
