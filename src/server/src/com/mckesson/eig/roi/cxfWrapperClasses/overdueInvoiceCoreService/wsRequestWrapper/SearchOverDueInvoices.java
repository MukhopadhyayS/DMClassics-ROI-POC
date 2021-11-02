
package com.mckesson.eig.roi.cxfWrapperClasses.overdueInvoiceCoreService.wsRequestWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;


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
 *         &lt;element name="PastDueInvoiceCriteria" type="{urn:eig.mckesson.com}SearchPastDueInvoiceCriteria"/>
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
    "pastDueInvoiceCriteria"
})
@XmlRootElement(name = "searchOverDueInvoices")
public class SearchOverDueInvoices {

    @XmlElement(name = "PastDueInvoiceCriteria", required = true)
    protected SearchPastDueInvoiceCriteria pastDueInvoiceCriteria;

    /**
     * Gets the value of the pastDueInvoiceCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPastDueInvoiceCriteria }
     *     
     */
    public SearchPastDueInvoiceCriteria getPastDueInvoiceCriteria() {
        return pastDueInvoiceCriteria;
    }

    /**
     * Sets the value of the pastDueInvoiceCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPastDueInvoiceCriteria }
     *     
     */
    public void setPastDueInvoiceCriteria(SearchPastDueInvoiceCriteria value) {
        this.pastDueInvoiceCriteria = value;
    }

}
