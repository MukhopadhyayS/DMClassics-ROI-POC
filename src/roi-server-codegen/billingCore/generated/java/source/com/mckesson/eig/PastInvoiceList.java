
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PastInvoiceList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PastInvoiceList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PastInvoices" type="{urn:eig.mckesson.com}PastInvoice" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PastInvoiceList", propOrder = {
    "pastInvoices"
})
public class PastInvoiceList {

    @XmlElement(name = "PastInvoices")
    protected List<PastInvoice> pastInvoices;

    /**
     * Gets the value of the pastInvoices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pastInvoices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPastInvoices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PastInvoice }
     * 
     * 
     */
    public List<PastInvoice> getPastInvoices() {
        if (pastInvoices == null) {
            pastInvoices = new ArrayList<PastInvoice>();
        }
        return this.pastInvoices;
    }

}
