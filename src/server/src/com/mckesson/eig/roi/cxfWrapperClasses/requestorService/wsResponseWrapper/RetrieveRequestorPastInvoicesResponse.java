
package com.mckesson.eig.roi.cxfWrapperClasses.requestorService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.PastInvoiceList;


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
 *         &lt;element name="PastInvoiceList" type="{urn:eig.mckesson.com}PastInvoiceList"/>
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
    "pastInvoiceList"
})
@XmlRootElement(name = "retrieveRequestorPastInvoicesResponse")
public class RetrieveRequestorPastInvoicesResponse {

    @XmlElement(name = "PastInvoiceList", required = true)
    protected PastInvoiceList pastInvoiceList;

    /**
     * Gets the value of the pastInvoiceList property.
     * 
     * @return
     *     possible object is
     *     {@link PastInvoiceList }
     *     
     */
    public PastInvoiceList getPastInvoiceList() {
        return pastInvoiceList;
    }

    /**
     * Sets the value of the pastInvoiceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PastInvoiceList }
     *     
     */
    public void setPastInvoiceList(PastInvoiceList value) {
        this.pastInvoiceList = value;
    }

}
