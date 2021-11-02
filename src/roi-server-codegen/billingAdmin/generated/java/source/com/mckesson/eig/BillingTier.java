
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingTier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingTier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salesTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="associated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="baseCharge" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="defaultPageCharge" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mediaType" type="{urn:eig.mckesson.com}MediaType" minOccurs="0"/>
 *         &lt;element name="mediaTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="mediaTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pageLevelTier" type="{urn:eig.mckesson.com}PageLevelTier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingTier", propOrder = {
    "billingTierId",
    "name",
    "description",
    "salesTax",
    "associated",
    "baseCharge",
    "defaultPageCharge",
    "recordVersion",
    "mediaType",
    "mediaTypeId",
    "mediaTypeName",
    "pageLevelTier"
})
public class BillingTier {

    protected long billingTierId;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    protected String salesTax;
    protected boolean associated;
    protected float baseCharge;
    protected float defaultPageCharge;
    protected int recordVersion;
    protected MediaType mediaType;
    protected long mediaTypeId;
    protected String mediaTypeName;
    protected List<PageLevelTier> pageLevelTier;

    /**
     * Gets the value of the billingTierId property.
     * 
     */
    public long getBillingTierId() {
        return billingTierId;
    }

    /**
     * Sets the value of the billingTierId property.
     * 
     */
    public void setBillingTierId(long value) {
        this.billingTierId = value;
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
     * Gets the value of the associated property.
     * 
     */
    public boolean isAssociated() {
        return associated;
    }

    /**
     * Sets the value of the associated property.
     * 
     */
    public void setAssociated(boolean value) {
        this.associated = value;
    }

    /**
     * Gets the value of the baseCharge property.
     * 
     */
    public float getBaseCharge() {
        return baseCharge;
    }

    /**
     * Sets the value of the baseCharge property.
     * 
     */
    public void setBaseCharge(float value) {
        this.baseCharge = value;
    }

    /**
     * Gets the value of the defaultPageCharge property.
     * 
     */
    public float getDefaultPageCharge() {
        return defaultPageCharge;
    }

    /**
     * Sets the value of the defaultPageCharge property.
     * 
     */
    public void setDefaultPageCharge(float value) {
        this.defaultPageCharge = value;
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
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link MediaType }
     *     
     */
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaType }
     *     
     */
    public void setMediaType(MediaType value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the mediaTypeId property.
     * 
     */
    public long getMediaTypeId() {
        return mediaTypeId;
    }

    /**
     * Sets the value of the mediaTypeId property.
     * 
     */
    public void setMediaTypeId(long value) {
        this.mediaTypeId = value;
    }

    /**
     * Gets the value of the mediaTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaTypeName() {
        return mediaTypeName;
    }

    /**
     * Sets the value of the mediaTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaTypeName(String value) {
        this.mediaTypeName = value;
    }

    /**
     * Gets the value of the pageLevelTier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pageLevelTier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPageLevelTier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PageLevelTier }
     * 
     * 
     */
    public List<PageLevelTier> getPageLevelTier() {
        if (pageLevelTier == null) {
            pageLevelTier = new ArrayList<PageLevelTier>();
        }
        return this.pageLevelTier;
    }

}
