
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelatedFeeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedFeeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="feeTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelatedFeeType", propOrder = {
    "id",
    "feeTypeId",
    "billingTemplateId",
    "recordVersion"
})
public class RelatedFeeType {

    protected long id;
    protected long feeTypeId;
    protected long billingTemplateId;
    protected int recordVersion;

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
     * Gets the value of the feeTypeId property.
     * 
     */
    public long getFeeTypeId() {
        return feeTypeId;
    }

    /**
     * Sets the value of the feeTypeId property.
     * 
     */
    public void setFeeTypeId(long value) {
        this.feeTypeId = value;
    }

    /**
     * Gets the value of the billingTemplateId property.
     * 
     */
    public long getBillingTemplateId() {
        return billingTemplateId;
    }

    /**
     * Sets the value of the billingTemplateId property.
     * 
     */
    public void setBillingTemplateId(long value) {
        this.billingTemplateId = value;
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

}
