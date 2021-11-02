
package com.mckesson.eig;

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
 *         &lt;element name="detailedFetch" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "detailedFetch"
})
@XmlRootElement(name = "retrieveAllDeliveryMethods")
public class RetrieveAllDeliveryMethods {

    protected boolean detailedFetch;

    /**
     * Gets the value of the detailedFetch property.
     * 
     */
    public boolean isDetailedFetch() {
        return detailedFetch;
    }

    /**
     * Sets the value of the detailedFetch property.
     * 
     */
    public void setDetailedFetch(boolean value) {
        this.detailedFetch = value;
    }

}
