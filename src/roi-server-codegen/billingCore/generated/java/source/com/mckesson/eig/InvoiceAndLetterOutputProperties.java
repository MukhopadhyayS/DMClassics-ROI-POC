
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceAndLetterOutputProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceAndLetterOutputProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="forInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="forLetter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="forRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="releaseId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceAndLetterOutputProperties", propOrder = {
    "queuePassword",
    "outputMethod",
    "forInvoice",
    "forLetter",
    "forRelease",
    "invoiceId",
    "letterId",
    "releaseId"
})
public class InvoiceAndLetterOutputProperties {

    @XmlElement(required = true)
    protected String queuePassword;
    @XmlElement(required = true)
    protected String outputMethod;
    protected boolean forInvoice;
    protected boolean forLetter;
    protected boolean forRelease;
    protected long invoiceId;
    protected long letterId;
    protected long releaseId;

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
     * Gets the value of the forInvoice property.
     * 
     */
    public boolean isForInvoice() {
        return forInvoice;
    }

    /**
     * Sets the value of the forInvoice property.
     * 
     */
    public void setForInvoice(boolean value) {
        this.forInvoice = value;
    }

    /**
     * Gets the value of the forLetter property.
     * 
     */
    public boolean isForLetter() {
        return forLetter;
    }

    /**
     * Sets the value of the forLetter property.
     * 
     */
    public void setForLetter(boolean value) {
        this.forLetter = value;
    }

    /**
     * Gets the value of the forRelease property.
     * 
     */
    public boolean isForRelease() {
        return forRelease;
    }

    /**
     * Sets the value of the forRelease property.
     * 
     */
    public void setForRelease(boolean value) {
        this.forRelease = value;
    }

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(long value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the letterId property.
     * 
     */
    public long getLetterId() {
        return letterId;
    }

    /**
     * Sets the value of the letterId property.
     * 
     */
    public void setLetterId(long value) {
        this.letterId = value;
    }

    /**
     * Gets the value of the releaseId property.
     * 
     */
    public long getReleaseId() {
        return releaseId;
    }

    /**
     * Sets the value of the releaseId property.
     * 
     */
    public void setReleaseId(long value) {
        this.releaseId = value;
    }

}
