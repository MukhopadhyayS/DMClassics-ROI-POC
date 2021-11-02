
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelatedBillingTier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelatedBillingTier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="relatedBillingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="billingTierId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="isHPF" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isHECM" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isCEVA" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="isSupplemental" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelatedBillingTier", propOrder = {
    "relatedBillingTierId",
    "billingTierId",
    "isHPF",
    "isHECM",
    "isCEVA",
    "recordVersion",
    "isSupplemental"
})
public class RelatedBillingTier {

    protected long relatedBillingTierId;
    protected long billingTierId;
    protected boolean isHPF;
    protected boolean isHECM;
    protected boolean isCEVA;
    protected int recordVersion;
    protected boolean isSupplemental;

    /**
     * Gets the value of the relatedBillingTierId property.
     * 
     */
    public long getRelatedBillingTierId() {
        return relatedBillingTierId;
    }

    /**
     * Sets the value of the relatedBillingTierId property.
     * 
     */
    public void setRelatedBillingTierId(long value) {
        this.relatedBillingTierId = value;
    }

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
     * Gets the value of the isHPF property.
     * 
     */
    public boolean isIsHPF() {
        return isHPF;
    }

    /**
     * Sets the value of the isHPF property.
     * 
     */
    public void setIsHPF(boolean value) {
        this.isHPF = value;
    }

    /**
     * Gets the value of the isHECM property.
     * 
     */
    public boolean isIsHECM() {
        return isHECM;
    }

    /**
     * Sets the value of the isHECM property.
     * 
     */
    public void setIsHECM(boolean value) {
        this.isHECM = value;
    }

    /**
     * Gets the value of the isCEVA property.
     * 
     */
    public boolean isIsCEVA() {
        return isCEVA;
    }

    /**
     * Sets the value of the isCEVA property.
     * 
     */
    public void setIsCEVA(boolean value) {
        this.isCEVA = value;
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
     * Gets the value of the isSupplemental property.
     * 
     */
    public boolean isIsSupplemental() {
        return isSupplemental;
    }

    /**
     * Sets the value of the isSupplemental property.
     * 
     */
    public void setIsSupplemental(boolean value) {
        this.isSupplemental = value;
    }

}
