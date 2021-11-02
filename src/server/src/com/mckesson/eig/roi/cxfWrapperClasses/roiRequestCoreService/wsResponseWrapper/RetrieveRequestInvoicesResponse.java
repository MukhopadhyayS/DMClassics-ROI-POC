
package com.mckesson.eig.roi.cxfWrapperClasses.roiRequestCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;


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
 *         &lt;element name="requestorInvoicesList" type="{urn:eig.mckesson.com}RequestorInvoicesList"/>
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
    "requestorInvoicesList"
})
@XmlRootElement(name = "retrieveRequestInvoicesResponse")
public class RetrieveRequestInvoicesResponse {

    @XmlElement(required = true)
    protected RequestorInvoicesList requestorInvoicesList;

    /**
     * Gets the value of the requestorInvoicesList property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorInvoicesList }
     *     
     */
    public RequestorInvoicesList getRequestorInvoicesList() {
        return requestorInvoicesList;
    }

    /**
     * Sets the value of the requestorInvoicesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorInvoicesList }
     *     
     */
    public void setRequestorInvoicesList(RequestorInvoicesList value) {
        this.requestorInvoicesList = value;
    }

}
