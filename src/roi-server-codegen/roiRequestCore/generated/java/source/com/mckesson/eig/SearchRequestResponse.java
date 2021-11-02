
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
 *         &lt;element name="requestCoreSearchResultList" type="{urn:eig.mckesson.com}RequestCoreSearchResultList"/>
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
    "requestCoreSearchResultList"
})
@XmlRootElement(name = "searchRequestResponse")
public class SearchRequestResponse {

    @XmlElement(required = true)
    protected RequestCoreSearchResultList requestCoreSearchResultList;

    /**
     * Gets the value of the requestCoreSearchResultList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreSearchResultList }
     *     
     */
    public RequestCoreSearchResultList getRequestCoreSearchResultList() {
        return requestCoreSearchResultList;
    }

    /**
     * Sets the value of the requestCoreSearchResultList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreSearchResultList }
     *     
     */
    public void setRequestCoreSearchResultList(RequestCoreSearchResultList value) {
        this.requestCoreSearchResultList = value;
    }

}
