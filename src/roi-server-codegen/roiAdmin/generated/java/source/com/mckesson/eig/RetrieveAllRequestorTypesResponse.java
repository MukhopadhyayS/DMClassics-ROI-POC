
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
 *         &lt;element name="requestorTypes" type="{urn:eig.mckesson.com}RequestorTypeList"/>
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
    "requestorTypes"
})
@XmlRootElement(name = "retrieveAllRequestorTypesResponse")
public class RetrieveAllRequestorTypesResponse {

    @XmlElement(required = true)
    protected RequestorTypeList requestorTypes;

    /**
     * Gets the value of the requestorTypes property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorTypeList }
     *     
     */
    public RequestorTypeList getRequestorTypes() {
        return requestorTypes;
    }

    /**
     * Sets the value of the requestorTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorTypeList }
     *     
     */
    public void setRequestorTypes(RequestorTypeList value) {
        this.requestorTypes = value;
    }

}
