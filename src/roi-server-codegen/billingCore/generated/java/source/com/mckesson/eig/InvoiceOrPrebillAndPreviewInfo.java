
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InvoiceOrPrebillAndPreviewInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceOrPrebillAndPreviewInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterTemplateFileId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterTemplateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoicePrebillDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="overwriteDueDate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceSalesTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="baseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceBillingLocCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceBillinglocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceBalanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="amountpaid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="letterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="willInvoiceShipped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="autoAdjustments" type="{urn:eig.mckesson.com}RequestCoreDeliveryChargesAdjustmentPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceOrPrebillAndPreviewInfo", propOrder = {
    "requestCoreId",
    "type",
    "invoiceDueDays",
    "letterTemplateFileId",
    "letterTemplateName",
    "requestStatus",
    "requestDate",
    "invoicePrebillDate",
    "invoiceDueDate",
    "resendDate",
    "outputMethod",
    "queuePassword",
    "overwriteDueDate",
    "invoiceSalesTax",
    "baseCharge",
    "invoiceBillingLocCode",
    "invoiceBillinglocName",
    "invoiceBalanceDue",
    "amountpaid",
    "notes",
    "letterType",
    "willInvoiceShipped",
    "autoAdjustments"
})
public class InvoiceOrPrebillAndPreviewInfo {

    protected long requestCoreId;
    @XmlElement(required = true)
    protected String type;
    protected long invoiceDueDays;
    protected long letterTemplateFileId;
    @XmlElement(required = true)
    protected String letterTemplateName;
    @XmlElement(required = true)
    protected String requestStatus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoicePrebillDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoiceDueDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar resendDate;
    @XmlElement(required = true)
    protected String outputMethod;
    @XmlElement(required = true)
    protected String queuePassword;
    protected boolean overwriteDueDate;
    protected double invoiceSalesTax;
    protected double baseCharge;
    @XmlElement(required = true)
    protected String invoiceBillingLocCode;
    @XmlElement(required = true)
    protected String invoiceBillinglocName;
    protected double invoiceBalanceDue;
    protected double amountpaid;
    protected List<String> notes;
    @XmlElement(required = true)
    protected String letterType;
    protected boolean willInvoiceShipped;
    protected List<RequestCoreDeliveryChargesAdjustmentPayment> autoAdjustments;

    /**
     * Gets the value of the requestCoreId property.
     * 
     */
    public long getRequestCoreId() {
        return requestCoreId;
    }

    /**
     * Sets the value of the requestCoreId property.
     * 
     */
    public void setRequestCoreId(long value) {
        this.requestCoreId = value;
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
     * Gets the value of the invoiceDueDays property.
     * 
     */
    public long getInvoiceDueDays() {
        return invoiceDueDays;
    }

    /**
     * Sets the value of the invoiceDueDays property.
     * 
     */
    public void setInvoiceDueDays(long value) {
        this.invoiceDueDays = value;
    }

    /**
     * Gets the value of the letterTemplateFileId property.
     * 
     */
    public long getLetterTemplateFileId() {
        return letterTemplateFileId;
    }

    /**
     * Sets the value of the letterTemplateFileId property.
     * 
     */
    public void setLetterTemplateFileId(long value) {
        this.letterTemplateFileId = value;
    }

    /**
     * Gets the value of the letterTemplateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterTemplateName() {
        return letterTemplateName;
    }

    /**
     * Sets the value of the letterTemplateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterTemplateName(String value) {
        this.letterTemplateName = value;
    }

    /**
     * Gets the value of the requestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestStatus() {
        return requestStatus;
    }

    /**
     * Sets the value of the requestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestStatus(String value) {
        this.requestStatus = value;
    }

    /**
     * Gets the value of the requestDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the value of the requestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestDate(XMLGregorianCalendar value) {
        this.requestDate = value;
    }

    /**
     * Gets the value of the invoicePrebillDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvoicePrebillDate() {
        return invoicePrebillDate;
    }

    /**
     * Sets the value of the invoicePrebillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvoicePrebillDate(XMLGregorianCalendar value) {
        this.invoicePrebillDate = value;
    }

    /**
     * Gets the value of the invoiceDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvoiceDueDate() {
        return invoiceDueDate;
    }

    /**
     * Sets the value of the invoiceDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvoiceDueDate(XMLGregorianCalendar value) {
        this.invoiceDueDate = value;
    }

    /**
     * Gets the value of the resendDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResendDate() {
        return resendDate;
    }

    /**
     * Sets the value of the resendDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResendDate(XMLGregorianCalendar value) {
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
     * Gets the value of the overwriteDueDate property.
     * 
     */
    public boolean isOverwriteDueDate() {
        return overwriteDueDate;
    }

    /**
     * Sets the value of the overwriteDueDate property.
     * 
     */
    public void setOverwriteDueDate(boolean value) {
        this.overwriteDueDate = value;
    }

    /**
     * Gets the value of the invoiceSalesTax property.
     * 
     */
    public double getInvoiceSalesTax() {
        return invoiceSalesTax;
    }

    /**
     * Sets the value of the invoiceSalesTax property.
     * 
     */
    public void setInvoiceSalesTax(double value) {
        this.invoiceSalesTax = value;
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
     * Gets the value of the invoiceBillingLocCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceBillingLocCode() {
        return invoiceBillingLocCode;
    }

    /**
     * Sets the value of the invoiceBillingLocCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceBillingLocCode(String value) {
        this.invoiceBillingLocCode = value;
    }

    /**
     * Gets the value of the invoiceBillinglocName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceBillinglocName() {
        return invoiceBillinglocName;
    }

    /**
     * Sets the value of the invoiceBillinglocName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceBillinglocName(String value) {
        this.invoiceBillinglocName = value;
    }

    /**
     * Gets the value of the invoiceBalanceDue property.
     * 
     */
    public double getInvoiceBalanceDue() {
        return invoiceBalanceDue;
    }

    /**
     * Sets the value of the invoiceBalanceDue property.
     * 
     */
    public void setInvoiceBalanceDue(double value) {
        this.invoiceBalanceDue = value;
    }

    /**
     * Gets the value of the amountpaid property.
     * 
     */
    public double getAmountpaid() {
        return amountpaid;
    }

    /**
     * Sets the value of the amountpaid property.
     * 
     */
    public void setAmountpaid(double value) {
        this.amountpaid = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNotes() {
        if (notes == null) {
            notes = new ArrayList<String>();
        }
        return this.notes;
    }

    /**
     * Gets the value of the letterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterType() {
        return letterType;
    }

    /**
     * Sets the value of the letterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterType(String value) {
        this.letterType = value;
    }

    /**
     * Gets the value of the willInvoiceShipped property.
     * 
     */
    public boolean isWillInvoiceShipped() {
        return willInvoiceShipped;
    }

    /**
     * Sets the value of the willInvoiceShipped property.
     * 
     */
    public void setWillInvoiceShipped(boolean value) {
        this.willInvoiceShipped = value;
    }

    /**
     * Gets the value of the autoAdjustments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the autoAdjustments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutoAdjustments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestCoreDeliveryChargesAdjustmentPayment }
     * 
     * 
     */
    public List<RequestCoreDeliveryChargesAdjustmentPayment> getAutoAdjustments() {
        if (autoAdjustments == null) {
            autoAdjustments = new ArrayList<RequestCoreDeliveryChargesAdjustmentPayment>();
        }
        return this.autoAdjustments;
    }

}
