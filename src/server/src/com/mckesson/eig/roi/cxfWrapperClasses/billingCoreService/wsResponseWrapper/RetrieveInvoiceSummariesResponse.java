
package com.mckesson.eig.roi.cxfWrapperClasses.billingCoreService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.InvoiceHistory;


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
 *         &lt;element name="invoiceHistory" type="{urn:eig.mckesson.com}InvoiceHistory"/>
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
    "invoiceHistory"
})
@XmlRootElement(name = "retrieveInvoiceSummariesResponse")
public class RetrieveInvoiceSummariesResponse {

    @XmlElement(required = true)
    protected InvoiceHistory invoiceHistory;

    /**
     * Gets the value of the invoiceHistory property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceHistory }
     *     
     */
    public InvoiceHistory getInvoiceHistory() {
        return invoiceHistory;
    }

    /**
     * Sets the value of the invoiceHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceHistory }
     *     
     */
    public void setInvoiceHistory(InvoiceHistory value) {
        this.invoiceHistory = value;
    }

}
