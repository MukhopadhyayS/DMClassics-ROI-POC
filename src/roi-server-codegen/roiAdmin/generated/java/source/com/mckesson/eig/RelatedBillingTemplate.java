
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelatedBillingTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedBillingTemplate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relatedBillingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "RelatedBillingTemplate", propOrder = {
    "relatedBillingTemplateId",
    "billingTemplateId",
    "isDefault",
    "recordVersion"
})
public class RelatedBillingTemplate {

    protected long relatedBillingTemplateId;
    protected long billingTemplateId;
    protected boolean isDefault;
    protected int recordVersion;

    /**
     * Gets the value of the relatedBillingTemplateId property.
     * 
     */
    public long getRelatedBillingTemplateId() {
        return relatedBillingTemplateId;
    }

    /**
     * Sets the value of the relatedBillingTemplateId property.
     * 
     */
    public void setRelatedBillingTemplateId(long value) {
        this.relatedBillingTemplateId = value;
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
     * Gets the value of the isDefault property.
     * 
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     */
    public void setIsDefault(boolean value) {
        this.isDefault = value;
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
