
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchPastDueInvoiceResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPastDueInvoiceResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxCountExceeded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PastDueInvoiceList" type="{urn:eig.mckesson.com}PastDueInvoices"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPastDueInvoiceResult", propOrder = {
    "maxCountExceeded",
    "pastDueInvoiceList"
})
public class SearchPastDueInvoiceResult {

    protected boolean maxCountExceeded;
    @XmlElement(name = "PastDueInvoiceList", required = true)
    protected PastDueInvoices pastDueInvoiceList;

    /**
     * Gets the value of the maxCountExceeded property.
     * 
     */
    public boolean isMaxCountExceeded() {
        return maxCountExceeded;
    }

    /**
     * Sets the value of the maxCountExceeded property.
     * 
     */
    public void setMaxCountExceeded(boolean value) {
        this.maxCountExceeded = value;
    }

    /**
     * Gets the value of the pastDueInvoiceList property.
     * 
     * @return
     *     possible object is
     *     {@link PastDueInvoices }
     *     
     */
    public PastDueInvoices getPastDueInvoiceList() {
        return pastDueInvoiceList;
    }

    /**
     * Sets the value of the pastDueInvoiceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PastDueInvoices }
     *     
     */
    public void setPastDueInvoiceList(PastDueInvoices value) {
        this.pastDueInvoiceList = value;
    }

}
