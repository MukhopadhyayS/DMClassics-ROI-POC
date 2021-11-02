
package com.mckesson.eig.roi.cxfWrapperClasses.overdueInvoiceCoreService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;


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
 *         &lt;element name="InvoiceAndLetterInfo" type="{urn:eig.mckesson.com}InvoiceAndLetterInfo"/>
 *         &lt;element name="forPreview" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "invoiceAndLetterInfo",
    "forPreview"
})
@XmlRootElement(name = "regenerateInvoiceCoreAndLetter")
public class RegenerateInvoiceCoreAndLetter {

    @XmlElement(name = "InvoiceAndLetterInfo", required = true)
    protected InvoiceAndLetterInfo invoiceAndLetterInfo;
    protected boolean forPreview;

    /**
     * Gets the value of the invoiceAndLetterInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceAndLetterInfo }
     *     
     */
    public InvoiceAndLetterInfo getInvoiceAndLetterInfo() {
        return invoiceAndLetterInfo;
    }

    /**
     * Sets the value of the invoiceAndLetterInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceAndLetterInfo }
     *     
     */
    public void setInvoiceAndLetterInfo(InvoiceAndLetterInfo value) {
        this.invoiceAndLetterInfo = value;
    }

    /**
     * Gets the value of the forPreview property.
     * 
     */
    public boolean isForPreview() {
        return forPreview;
    }

    /**
     * Sets the value of the forPreview property.
     * 
     */
    public void setForPreview(boolean value) {
        this.forPreview = value;
    }

}
