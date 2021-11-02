
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
 *         &lt;element name="requestCoreSearchCriteria" type="{urn:eig.mckesson.com}RequestCoreSearchCriteria"/>
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
    "requestCoreSearchCriteria"
})
@XmlRootElement(name = "getRequestCount")
public class GetRequestCount {

    @XmlElement(required = true)
    protected RequestCoreSearchCriteria requestCoreSearchCriteria;

    /**
     * Gets the value of the requestCoreSearchCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreSearchCriteria }
     *     
     */
    public RequestCoreSearchCriteria getRequestCoreSearchCriteria() {
        return requestCoreSearchCriteria;
    }

    /**
     * Sets the value of the requestCoreSearchCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreSearchCriteria }
     *     
     */
    public void setRequestCoreSearchCriteria(RequestCoreSearchCriteria value) {
        this.requestCoreSearchCriteria = value;
    }

}
