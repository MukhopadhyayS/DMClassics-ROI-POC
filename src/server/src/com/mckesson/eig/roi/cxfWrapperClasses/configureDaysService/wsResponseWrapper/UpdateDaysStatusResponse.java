
package com.mckesson.eig.roi.cxfWrapperClasses.configureDaysService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="configureDaysStatusResElement" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "configureDaysStatusResElement"
})
@XmlRootElement(name = "updateDaysStatusResponse")
public class UpdateDaysStatusResponse {

    protected boolean configureDaysStatusResElement;

    /**
     * Gets the value of the configureDaysStatusResElement property.
     * 
     */
    public boolean isConfigureDaysStatusResElement() {
        return configureDaysStatusResElement;
    }

    /**
     * Sets the value of the configureDaysStatusResElement property.
     * 
     */
    public void setConfigureDaysStatusResElement(boolean value) {
        this.configureDaysStatusResElement = value;
    }

}
