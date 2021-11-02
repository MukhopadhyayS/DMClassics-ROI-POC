
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rvDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="relatedBillingTemplate" type="{urn:eig.mckesson.com}RelatedBillingTemplate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedBillingTier" type="{urn:eig.mckesson.com}RelatedBillingTier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="billingTemplateIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isAssociated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="salesTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceOptional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorType", propOrder = {
    "requestorTypeId",
    "name",
    "description",
    "rv",
    "rvDesc",
    "recordVersion",
    "relatedBillingTemplate",
    "relatedBillingTier",
    "billingTemplateIds",
    "isAssociated",
    "salesTax",
    "invoiceOptional"
})
public class RequestorType {

    protected long requestorTypeId;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    protected String rv;
    protected String rvDesc;
    protected int recordVersion;
    protected List<RelatedBillingTemplate> relatedBillingTemplate;
    protected List<RelatedBillingTier> relatedBillingTier;
    @XmlElement(nillable = true)
    protected List<Long> billingTemplateIds;
    protected boolean isAssociated;
    protected String salesTax;
    protected String invoiceOptional;

    /**
     * Gets the value of the requestorTypeId property.
     * 
     */
    public long getRequestorTypeId() {
        return requestorTypeId;
    }

    /**
     * Sets the value of the requestorTypeId property.
     * 
     */
    public void setRequestorTypeId(long value) {
        this.requestorTypeId = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the rv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRv() {
        return rv;
    }

    /**
     * Sets the value of the rv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRv(String value) {
        this.rv = value;
    }

    /**
     * Gets the value of the rvDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRvDesc() {
        return rvDesc;
    }

    /**
     * Sets the value of the rvDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRvDesc(String value) {
        this.rvDesc = value;
    }

    /**
     * Gets the value of the recordVersion property.
     * 
     */
    public int getRecordVersion() {
        return recordVersion;
    }

    /**
     * Sets the value of the recordVersion property.
     * 
     */
    public void setRecordVersion(int value) {
        this.recordVersion = value;
    }

    /**
     * Gets the value of the relatedBillingTemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedBillingTemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedBillingTemplate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedBillingTemplate }
     * 
     * 
     */
    public List<RelatedBillingTemplate> getRelatedBillingTemplate() {
        if (relatedBillingTemplate == null) {
            relatedBillingTemplate = new ArrayList<RelatedBillingTemplate>();
        }
        return this.relatedBillingTemplate;
    }

    /**
     * Gets the value of the relatedBillingTier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedBillingTier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedBillingTier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedBillingTier }
     * 
     * 
     */
    public List<RelatedBillingTier> getRelatedBillingTier() {
        if (relatedBillingTier == null) {
            relatedBillingTier = new ArrayList<RelatedBillingTier>();
        }
        return this.relatedBillingTier;
    }

    /**
     * Gets the value of the billingTemplateIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billingTemplateIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillingTemplateIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getBillingTemplateIds() {
        if (billingTemplateIds == null) {
            billingTemplateIds = new ArrayList<Long>();
        }
        return this.billingTemplateIds;
    }

    /**
     * Gets the value of the isAssociated property.
     * 
     */
    public boolean isIsAssociated() {
        return isAssociated;
    }

    /**
     * Sets the value of the isAssociated property.
     * 
     */
    public void setIsAssociated(boolean value) {
        this.isAssociated = value;
    }

    /**
     * Gets the value of the salesTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesTax() {
        return salesTax;
    }

    /**
     * Sets the value of the salesTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesTax(String value) {
        this.salesTax = value;
    }

    /**
     * Gets the value of the invoiceOptional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceOptional() {
        return invoiceOptional;
    }

    /**
     * Sets the value of the invoiceOptional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceOptional(String value) {
        this.invoiceOptional = value;
    }

}
