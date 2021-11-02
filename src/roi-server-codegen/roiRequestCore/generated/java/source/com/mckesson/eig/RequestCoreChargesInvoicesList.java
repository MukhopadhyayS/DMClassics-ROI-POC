
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestCoreChargesInvoicesList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesInvoicesList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreChargesInvoice" type="{urn:eig.mckesson.com}RequestCoreChargesInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isInvoiced" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesInvoicesList", propOrder = {
    "requestCoreChargesInvoice",
    "isInvoiced"
})
public class RequestCoreChargesInvoicesList {

    protected List<RequestCoreChargesInvoice> requestCoreChargesInvoice;
    protected boolean isInvoiced;

    /**
     * Gets the value of the requestCoreChargesInvoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestCoreChargesInvoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestCoreChargesInvoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestCoreChargesInvoice }
     * 
     * 
     */
    public List<RequestCoreChargesInvoice> getRequestCoreChargesInvoice() {
        if (requestCoreChargesInvoice == null) {
            requestCoreChargesInvoice = new ArrayList<RequestCoreChargesInvoice>();
        }
        return this.requestCoreChargesInvoice;
    }

    /**
     * Gets the value of the isInvoiced property.
     * 
     */
    public boolean isIsInvoiced() {
        return isInvoiced;
    }

    /**
     * Sets the value of the isInvoiced property.
     * 
     */
    public void setIsInvoiced(boolean value) {
        this.isInvoiced = value;
    }

}
