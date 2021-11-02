
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorAdjustmentsFee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorAdjustmentsFee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="feeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salestaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="feeType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorAdjustmentsFee", propOrder = {
    "feeName",
    "amount",
    "salestaxAmount",
    "feeType",
    "isTaxable"
})
public class RequestorAdjustmentsFee {

    @XmlElement(required = true)
    protected String feeName;
    protected double amount;
    protected double salestaxAmount;
    @XmlElement(required = true)
    protected String feeType;
    protected boolean isTaxable;

    /**
     * Gets the value of the feeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeName() {
        return feeName;
    }

    /**
     * Sets the value of the feeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeName(String value) {
        this.feeName = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the salestaxAmount property.
     * 
     */
    public double getSalestaxAmount() {
        return salestaxAmount;
    }

    /**
     * Sets the value of the salestaxAmount property.
     * 
     */
    public void setSalestaxAmount(double value) {
        this.salestaxAmount = value;
    }

    /**
     * Gets the value of the feeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * Sets the value of the feeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeType(String value) {
        this.feeType = value;
    }

    /**
     * Gets the value of the isTaxable property.
     * 
     */
    public boolean isIsTaxable() {
        return isTaxable;
    }

    /**
     * Sets the value of the isTaxable property.
     * 
     */
    public void setIsTaxable(boolean value) {
        this.isTaxable = value;
    }

}
