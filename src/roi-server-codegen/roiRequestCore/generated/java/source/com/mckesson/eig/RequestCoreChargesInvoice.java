
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
 * <p>Java class for RequestCoreChargesInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreChargesInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="test" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestCoreDeliveryChargesId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceCreatedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoicedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="isInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="requestCoreDeliveryChargesAdjustmentPayment" type="{urn:eig.mckesson.com}RequestCoreDeliveryChargesAdjustmentPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreChargesInvoice", propOrder = {
    "test",
    "requestCoreDeliveryChargesId",
    "invoiceCreatedDt",
    "invoicedAmount",
    "paymentAmount",
    "isInvoice",
    "requestCoreDeliveryChargesAdjustmentPayment"
})
public class RequestCoreChargesInvoice {

    @XmlElement(required = true)
    protected String test;
    protected long requestCoreDeliveryChargesId;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoiceCreatedDt;
    protected double invoicedAmount;
    protected double paymentAmount;
    protected boolean isInvoice;
    protected List<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPayment;

    /**
     * Gets the value of the test property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTest() {
        return test;
    }

    /**
     * Sets the value of the test property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTest(String value) {
        this.test = value;
    }

    /**
     * Gets the value of the requestCoreDeliveryChargesId property.
     * 
     */
    public long getRequestCoreDeliveryChargesId() {
        return requestCoreDeliveryChargesId;
    }

    /**
     * Sets the value of the requestCoreDeliveryChargesId property.
     * 
     */
    public void setRequestCoreDeliveryChargesId(long value) {
        this.requestCoreDeliveryChargesId = value;
    }

    /**
     * Gets the value of the invoiceCreatedDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvoiceCreatedDt() {
        return invoiceCreatedDt;
    }

    /**
     * Sets the value of the invoiceCreatedDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvoiceCreatedDt(XMLGregorianCalendar value) {
        this.invoiceCreatedDt = value;
    }

    /**
     * Gets the value of the invoicedAmount property.
     * 
     */
    public double getInvoicedAmount() {
        return invoicedAmount;
    }

    /**
     * Sets the value of the invoicedAmount property.
     * 
     */
    public void setInvoicedAmount(double value) {
        this.invoicedAmount = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     */
    public void setPaymentAmount(double value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the isInvoice property.
     * 
     */
    public boolean isIsInvoice() {
        return isInvoice;
    }

    /**
     * Sets the value of the isInvoice property.
     * 
     */
    public void setIsInvoice(boolean value) {
        this.isInvoice = value;
    }

    /**
     * Gets the value of the requestCoreDeliveryChargesAdjustmentPayment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestCoreDeliveryChargesAdjustmentPayment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestCoreDeliveryChargesAdjustmentPayment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestCoreDeliveryChargesAdjustmentPayment }
     * 
     * 
     */
    public List<RequestCoreDeliveryChargesAdjustmentPayment> getRequestCoreDeliveryChargesAdjustmentPayment() {
        if (requestCoreDeliveryChargesAdjustmentPayment == null) {
            requestCoreDeliveryChargesAdjustmentPayment = new ArrayList<RequestCoreDeliveryChargesAdjustmentPayment>();
        }
        return this.requestCoreDeliveryChargesAdjustmentPayment;
    }

}
