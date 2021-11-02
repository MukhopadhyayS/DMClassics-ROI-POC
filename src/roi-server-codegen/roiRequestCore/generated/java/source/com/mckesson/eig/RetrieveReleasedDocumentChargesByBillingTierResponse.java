
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="requestCoreChargesDocument" type="{urn:eig.mckesson.com}RequestCoreChargesDocument" maxOccurs="unbounded" minOccurs="0"/>
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
    "requestCoreChargesDocument"
})
@XmlRootElement(name = "retrieveReleasedDocumentChargesByBillingTierResponse")
public class RetrieveReleasedDocumentChargesByBillingTierResponse {

    protected List<RequestCoreChargesDocument> requestCoreChargesDocument;

    /**
     * Gets the value of the requestCoreChargesDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestCoreChargesDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestCoreChargesDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestCoreChargesDocument }
     * 
     * 
     */
    public List<RequestCoreChargesDocument> getRequestCoreChargesDocument() {
        if (requestCoreChargesDocument == null) {
            requestCoreChargesDocument = new ArrayList<RequestCoreChargesDocument>();
        }
        return this.requestCoreChargesDocument;
    }

}
