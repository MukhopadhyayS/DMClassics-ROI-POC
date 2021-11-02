
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
 *         &lt;element name="requestCoreChargesInvoicesList" type="{urn:eig.mckesson.com}RequestCoreChargesInvoicesList"/>
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
    "requestCoreChargesInvoicesList"
})
@XmlRootElement(name = "retrieveInvoicesAndAdjPayResponse")
public class RetrieveInvoicesAndAdjPayResponse {

    @XmlElement(required = true)
    protected RequestCoreChargesInvoicesList requestCoreChargesInvoicesList;

    /**
     * Gets the value of the requestCoreChargesInvoicesList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestCoreChargesInvoicesList }
     *     
     */
    public RequestCoreChargesInvoicesList getRequestCoreChargesInvoicesList() {
        return requestCoreChargesInvoicesList;
    }

    /**
     * Sets the value of the requestCoreChargesInvoicesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestCoreChargesInvoicesList }
     *     
     */
    public void setRequestCoreChargesInvoicesList(RequestCoreChargesInvoicesList value) {
        this.requestCoreChargesInvoicesList = value;
    }

}
