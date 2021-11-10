package com.mckesson.eig.roi.billing.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PastDueInvoices complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastDueInvoices">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PastDueInvoice" type="{urn:eig.mckesson.com}PastDueInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastDueInvoices", propOrder = {
    "pastDueInvoice"
})
public class PastDueInvoices {

    @XmlElement(name = "PastDueInvoice")
    protected List<PastDueInvoice> pastDueInvoice;

    /**
     * Gets the value of the pastDueInvoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pastDueInvoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPastDueInvoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PastDueInvoice }
     * 
     * 
     */
    public List<PastDueInvoice> getPastDueInvoice() {
        if (pastDueInvoice == null) {
            pastDueInvoice = new ArrayList<PastDueInvoice>();
        }
        return this.pastDueInvoice;
    }

    public void setPastDueInvoice(List<PastDueInvoice> pastDueInvoice) {
        this.pastDueInvoice = pastDueInvoice;
    }
    
    

}
