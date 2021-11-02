
package com.mckesson.eig.roi.cxfWrapperClasses.roiAdminService.wsResponseWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.admin.model.InvoiceDueDays;


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
 *         &lt;element name="InvoiceDue" type="{urn:eig.mckesson.com}InvoiceDue"/>
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
    "invoiceDue"
})
@XmlRootElement(name = "updateInvoiceDueResponse")
public class UpdateInvoiceDueResponse {

    @XmlElement(name = "InvoiceDue", required = true)
    protected InvoiceDueDays invoiceDue;

    /**
     * Gets the value of the invoiceDue property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDue }
     *     
     */
    public InvoiceDueDays getInvoiceDue() {
        return invoiceDue;
    }

    /**
     * Sets the value of the invoiceDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDue }
     *     
     */
    public void setInvoiceDue(InvoiceDueDays value) {
        this.invoiceDue = value;
    }

}
