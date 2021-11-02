
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ROIAppData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ROIAppData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="hasSSNMasking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="freeFormFacilities" type="{urn:eig.mckesson.com}FreeFormFacilities" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ROIAppData", propOrder = {
    "hasSSNMasking",
    "freeFormFacilities",
    "invoiceDueDays"
})
public class ROIAppData {

    protected boolean hasSSNMasking;
    @XmlElementRef(name = "freeFormFacilities", namespace = "urn:eig.mckesson.com", type = JAXBElement.class, required = false)
    protected List<JAXBElement<List<String>>> freeFormFacilities;
    @XmlElement(type = Integer.class)
    protected List<Integer> invoiceDueDays;

    /**
     * Gets the value of the hasSSNMasking property.
     * 
     */
    public boolean isHasSSNMasking() {
        return hasSSNMasking;
    }

    /**
     * Sets the value of the hasSSNMasking property.
     * 
     */
    public void setHasSSNMasking(boolean value) {
        this.hasSSNMasking = value;
    }

    /**
     * Gets the value of the freeFormFacilities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freeFormFacilities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreeFormFacilities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link String }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<String>>> getFreeFormFacilities() {
        if (freeFormFacilities == null) {
            freeFormFacilities = new ArrayList<JAXBElement<List<String>>>();
        }
        return this.freeFormFacilities;
    }

    /**
     * Gets the value of the invoiceDueDays property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceDueDays property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceDueDays().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getInvoiceDueDays() {
        if (invoiceDueDays == null) {
            invoiceDueDays = new ArrayList<Integer>();
        }
        return this.invoiceDueDays;
    }

}
