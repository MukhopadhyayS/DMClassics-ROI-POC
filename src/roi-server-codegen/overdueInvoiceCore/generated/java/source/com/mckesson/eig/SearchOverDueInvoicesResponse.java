
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
 *         &lt;element name="searchPastDueInvoiceResult" type="{urn:eig.mckesson.com}SearchPastDueInvoiceResult"/>
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
    "searchPastDueInvoiceResult"
})
@XmlRootElement(name = "searchOverDueInvoicesResponse")
public class SearchOverDueInvoicesResponse {

    @XmlElement(required = true)
    protected SearchPastDueInvoiceResult searchPastDueInvoiceResult;

    /**
     * Gets the value of the searchPastDueInvoiceResult property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPastDueInvoiceResult }
     *     
     */
    public SearchPastDueInvoiceResult getSearchPastDueInvoiceResult() {
        return searchPastDueInvoiceResult;
    }

    /**
     * Sets the value of the searchPastDueInvoiceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPastDueInvoiceResult }
     *     
     */
    public void setSearchPastDueInvoiceResult(SearchPastDueInvoiceResult value) {
        this.searchPastDueInvoiceResult = value;
    }

}
