
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Invoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Invoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="baseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalAdjustments" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalPayments" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="currentBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Invoice", propOrder = {
    "id",
    "type",
    "creatorName",
    "createdDate",
    "queuePassword",
    "resendDate",
    "outputMethod",
    "invoiceDueDate",
    "baseCharge",
    "totalAdjustments",
    "totalPayments",
    "currentBalance"
})
public class Invoice {

    protected long id;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String creatorName;
    @XmlElement(required = true)
    protected String createdDate;
    @XmlElement(required = true)
    protected String queuePassword;
    @XmlElement(required = true)
    protected String resendDate;
    @XmlElement(required = true)
    protected String outputMethod;
    @XmlElement(required = true)
    protected String invoiceDueDate;
    protected double baseCharge;
    protected double totalAdjustments;
    protected double totalPayments;
    protected double currentBalance;

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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the creatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * Sets the value of the creatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorName(String value) {
        this.creatorName = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedDate(String value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the queuePassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueuePassword() {
        return queuePassword;
    }

    /**
     * Sets the value of the queuePassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueuePassword(String value) {
        this.queuePassword = value;
    }

    /**
     * Gets the value of the resendDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResendDate() {
        return resendDate;
    }

    /**
     * Sets the value of the resendDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResendDate(String value) {
        this.resendDate = value;
    }

    /**
     * Gets the value of the outputMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputMethod() {
        return outputMethod;
    }

    /**
     * Sets the value of the outputMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputMethod(String value) {
        this.outputMethod = value;
    }

    /**
     * Gets the value of the invoiceDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    /**
     * Sets the value of the invoiceDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceDueDate(String value) {
        this.invoiceDueDate = value;
    }

    /**
     * Gets the value of the baseCharge property.
     * 
     */
    public double getBaseCharge() {
        return baseCharge;
    }

    /**
     * Sets the value of the baseCharge property.
     * 
     */
    public void setBaseCharge(double value) {
        this.baseCharge = value;
    }

    /**
     * Gets the value of the totalAdjustments property.
     * 
     */
    public double getTotalAdjustments() {
        return totalAdjustments;
    }

    /**
     * Sets the value of the totalAdjustments property.
     * 
     */
    public void setTotalAdjustments(double value) {
        this.totalAdjustments = value;
    }

    /**
     * Gets the value of the totalPayments property.
     * 
     */
    public double getTotalPayments() {
        return totalPayments;
    }

    /**
     * Sets the value of the totalPayments property.
     * 
     */
    public void setTotalPayments(double value) {
        this.totalPayments = value;
    }

    /**
     * Gets the value of the currentBalance property.
     * 
     */
    public double getCurrentBalance() {
        return currentBalance;
    }

    /**
     * Sets the value of the currentBalance property.
     * 
     */
    public void setCurrentBalance(double value) {
        this.currentBalance = value;
    }

}
