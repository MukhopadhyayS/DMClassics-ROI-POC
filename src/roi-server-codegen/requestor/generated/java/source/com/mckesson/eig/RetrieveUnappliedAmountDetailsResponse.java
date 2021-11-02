
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
 *         &lt;element name="requestorUnappliedAmountDetailsList" type="{urn:eig.mckesson.com}RequestorUnappliedAmountDetailsList"/>
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
    "requestorUnappliedAmountDetailsList"
})
@XmlRootElement(name = "retrieveUnappliedAmountDetailsResponse")
public class RetrieveUnappliedAmountDetailsResponse {

    @XmlElement(required = true)
    protected RequestorUnappliedAmountDetailsList requestorUnappliedAmountDetailsList;

    /**
     * Gets the value of the requestorUnappliedAmountDetailsList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorUnappliedAmountDetailsList }
     *     
     */
    public RequestorUnappliedAmountDetailsList getRequestorUnappliedAmountDetailsList() {
        return requestorUnappliedAmountDetailsList;
    }

    /**
     * Sets the value of the requestorUnappliedAmountDetailsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorUnappliedAmountDetailsList }
     *     
     */
    public void setRequestorUnappliedAmountDetailsList(RequestorUnappliedAmountDetailsList value) {
        this.requestorUnappliedAmountDetailsList = value;
    }

}
