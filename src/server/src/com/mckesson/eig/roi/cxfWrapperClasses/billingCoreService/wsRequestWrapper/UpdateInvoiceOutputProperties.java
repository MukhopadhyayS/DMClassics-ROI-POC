
package com.mckesson.eig.roi.cxfWrapperClasses.billingCoreService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;


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
 *         &lt;element name="invoiceAndLetterOutputProperties" type="{urn:eig.mckesson.com}InvoiceAndLetterOutputProperties"/>
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
    "invoiceAndLetterOutputProperties"
})
@XmlRootElement(name = "updateInvoiceOutputProperties")
public class UpdateInvoiceOutputProperties {

    @XmlElement(required = true)
    protected InvoiceAndLetterOutputProperties invoiceAndLetterOutputProperties;

    /**
     * Gets the value of the invoiceAndLetterOutputProperties property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceAndLetterOutputProperties }
     *     
     */
    public InvoiceAndLetterOutputProperties getInvoiceAndLetterOutputProperties() {
        return invoiceAndLetterOutputProperties;
    }

    /**
     * Sets the value of the invoiceAndLetterOutputProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceAndLetterOutputProperties }
     *     
     */
    public void setInvoiceAndLetterOutputProperties(InvoiceAndLetterOutputProperties value) {
        this.invoiceAndLetterOutputProperties = value;
    }

}
