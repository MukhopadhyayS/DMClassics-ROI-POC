
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InvoiceDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resendDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoiceNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="templateUsed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="templateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="resendBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceDetails", propOrder = {
    "resendDateTime",
    "invoiceNumber",
    "templateUsed",
    "templateId",
    "resendBy",
    "invoiceAmount",
    "queuePassword"
})
public class InvoiceDetails {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar resendDateTime;
    protected long invoiceNumber;
    @XmlElement(required = true)
    protected String templateUsed;
    protected long templateId;
    @XmlElement(required = true)
    protected String resendBy;
    protected double invoiceAmount;
    @XmlElement(required = true)
    protected String queuePassword;

    /**
     * Gets the value of the resendDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResendDateTime() {
        return resendDateTime;
    }

    /**
     * Sets the value of the resendDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResendDateTime(XMLGregorianCalendar value) {
        this.resendDateTime = value;
    }

    /**
     * Gets the value of the invoiceNumber property.
     * 
     */
    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     */
    public void setInvoiceNumber(long value) {
        this.invoiceNumber = value;
    }

    /**
     * Gets the value of the templateUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateUsed() {
        return templateUsed;
    }

    /**
     * Sets the value of the templateUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateUsed(String value) {
        this.templateUsed = value;
    }

    /**
     * Gets the value of the templateId property.
     * 
     */
    public long getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     */
    public void setTemplateId(long value) {
        this.templateId = value;
    }

    /**
     * Gets the value of the resendBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResendBy() {
        return resendBy;
    }

    /**
     * Sets the value of the resendBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResendBy(String value) {
        this.resendBy = value;
    }

    /**
     * Gets the value of the invoiceAmount property.
     * 
     */
    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * Sets the value of the invoiceAmount property.
     * 
     */
    public void setInvoiceAmount(double value) {
        this.invoiceAmount = value;
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

}
