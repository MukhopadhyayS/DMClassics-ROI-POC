
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="associated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="feeTypeIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedFeeTypes" type="{urn:eig.mckesson.com}RelatedFeeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingTemplate", propOrder = {
    "id",
    "name",
    "associated",
    "recordVersion",
    "feeTypeIds",
    "relatedFeeTypes"
})
public class BillingTemplate {

    protected long id;
    @XmlElement(required = true)
    protected String name;
    protected boolean associated;
    protected int recordVersion;
    @XmlElement(type = Long.class)
    protected List<Long> feeTypeIds;
    protected List<RelatedFeeType> relatedFeeTypes;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
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
     * Gets the value of the feeTypeIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the feeTypeIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeeTypeIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getFeeTypeIds() {
        if (feeTypeIds == null) {
            feeTypeIds = new ArrayList<Long>();
        }
        return this.feeTypeIds;
    }

    /**
     * Gets the value of the relatedFeeTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedFeeTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedFeeTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedFeeType }
     * 
     * 
     */
    public List<RelatedFeeType> getRelatedFeeTypes() {
        if (relatedFeeTypes == null) {
            relatedFeeTypes = new ArrayList<RelatedFeeType>();
        }
        return this.relatedFeeTypes;
    }

}
