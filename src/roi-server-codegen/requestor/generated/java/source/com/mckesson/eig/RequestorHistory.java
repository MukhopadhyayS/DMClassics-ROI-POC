
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="template" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="aging" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prebillStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="migrated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorHistory", propOrder = {
    "id",
    "type",
    "creatorName",
    "createdDate",
    "template",
    "requestPassword",
    "queuePassword",
    "invoiceDueDate",
    "balance",
    "invoiceBalance",
    "aging",
    "status",
    "prebillStatus",
    "requestId",
    "migrated"
})
public class RequestorHistory {

    protected long id;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String creatorName;
    @XmlElement(required = true)
    protected String createdDate;
    @XmlElement(required = true)
    protected String template;
    @XmlElement(required = true)
    protected String requestPassword;
    @XmlElement(required = true)
    protected String queuePassword;
    @XmlElement(required = true)
    protected String invoiceDueDate;
    protected double balance;
    protected double invoiceBalance;
    @XmlElement(required = true)
    protected String aging;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String prebillStatus;
    protected long requestId;
    protected boolean migrated;

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
     * Gets the value of the template property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplate(String value) {
        this.template = value;
    }

    /**
     * Gets the value of the requestPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestPassword() {
        return requestPassword;
    }

    /**
     * Sets the value of the requestPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestPassword(String value) {
        this.requestPassword = value;
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
     * Gets the value of the balance property.
     * 
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     */
    public void setBalance(double value) {
        this.balance = value;
    }

    /**
     * Gets the value of the invoiceBalance property.
     * 
     */
    public double getInvoiceBalance() {
        return invoiceBalance;
    }

    /**
     * Sets the value of the invoiceBalance property.
     * 
     */
    public void setInvoiceBalance(double value) {
        this.invoiceBalance = value;
    }

    /**
     * Gets the value of the aging property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAging() {
        return aging;
    }

    /**
     * Sets the value of the aging property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAging(String value) {
        this.aging = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the prebillStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrebillStatus() {
        return prebillStatus;
    }

    /**
     * Sets the value of the prebillStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrebillStatus(String value) {
        this.prebillStatus = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the migrated property.
     * 
     */
    public boolean isMigrated() {
        return migrated;
    }

    /**
     * Sets the value of the migrated property.
     * 
     */
    public void setMigrated(boolean value) {
        this.migrated = value;
    }

}
