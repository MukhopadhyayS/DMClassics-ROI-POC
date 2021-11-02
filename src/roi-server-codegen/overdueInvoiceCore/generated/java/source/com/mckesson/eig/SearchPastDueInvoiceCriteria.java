
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchPastDueInvoiceCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPastDueInvoiceCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingLocations" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="requestorTypes" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestorTypeNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overDueFrom" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="overDueTo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="overDueRestriction" type="{urn:eig.mckesson.com}OverDueRestriction"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPastDueInvoiceCriteria", propOrder = {
    "billingLocations",
    "requestorTypes",
    "requestorTypeNames",
    "requestorName",
    "overDueFrom",
    "overDueTo",
    "overDueRestriction"
})
public class SearchPastDueInvoiceCriteria {

    @XmlElement(required = true)
    protected List<String> billingLocations;
    @XmlElement(type = Long.class)
    protected List<Long> requestorTypes;
    @XmlElement(required = true)
    protected List<String> requestorTypeNames;
    protected String requestorName;
    protected long overDueFrom;
    protected Long overDueTo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OverDueRestriction overDueRestriction;

    /**
     * Gets the value of the billingLocations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billingLocations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillingLocations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBillingLocations() {
        if (billingLocations == null) {
            billingLocations = new ArrayList<String>();
        }
        return this.billingLocations;
    }

    /**
     * Gets the value of the requestorTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestorTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestorTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getRequestorTypes() {
        if (requestorTypes == null) {
            requestorTypes = new ArrayList<Long>();
        }
        return this.requestorTypes;
    }

    /**
     * Gets the value of the requestorTypeNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestorTypeNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestorTypeNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRequestorTypeNames() {
        if (requestorTypeNames == null) {
            requestorTypeNames = new ArrayList<String>();
        }
        return this.requestorTypeNames;
    }

    /**
     * Gets the value of the requestorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * Sets the value of the requestorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorName(String value) {
        this.requestorName = value;
    }

    /**
     * Gets the value of the overDueFrom property.
     * 
     */
    public long getOverDueFrom() {
        return overDueFrom;
    }

    /**
     * Sets the value of the overDueFrom property.
     * 
     */
    public void setOverDueFrom(long value) {
        this.overDueFrom = value;
    }

    /**
     * Gets the value of the overDueTo property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOverDueTo() {
        return overDueTo;
    }

    /**
     * Sets the value of the overDueTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOverDueTo(Long value) {
        this.overDueTo = value;
    }

    /**
     * Gets the value of the overDueRestriction property.
     * 
     * @return
     *     possible object is
     *     {@link OverDueRestriction }
     *     
     */
    public OverDueRestriction getOverDueRestriction() {
        return overDueRestriction;
    }

    /**
     * Sets the value of the overDueRestriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link OverDueRestriction }
     *     
     */
    public void setOverDueRestriction(OverDueRestriction value) {
        this.overDueRestriction = value;
    }

}
