
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
 * <p>Java class for RequestorRefund complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorRefund">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refundDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="refundAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="templateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="templateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="statementCriteria" type="{urn:eig.mckesson.com}RequestorStatementCriteria"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorRefund", propOrder = {
    "requestorId",
    "requestorName",
    "requestorType",
    "note",
    "refundDate",
    "refundAmount",
    "templateId",
    "templateName",
    "outputMethod",
    "queuePassword",
    "notes",
    "statementCriteria"
})
public class RequestorRefund {

    protected long requestorId;
    @XmlElement(required = true)
    protected String requestorName;
    @XmlElement(required = true)
    protected String requestorType;
    @XmlElement(required = true)
    protected String note;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar refundDate;
    protected double refundAmount;
    protected long templateId;
    @XmlElement(required = true)
    protected String templateName;
    @XmlElement(required = true)
    protected String outputMethod;
    @XmlElement(required = true)
    protected String queuePassword;
    protected List<String> notes;
    @XmlElement(required = true)
    protected RequestorStatementCriteria statementCriteria;

    /**
     * Gets the value of the requestorId property.
     * 
     */
    public long getRequestorId() {
        return requestorId;
    }

    /**
     * Sets the value of the requestorId property.
     * 
     */
    public void setRequestorId(long value) {
        this.requestorId = value;
    }

    /**
     * Gets the value of the requestorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * Sets the value of the requestorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorName(String value) {
        this.requestorName = value;
    }

    /**
     * Gets the value of the requestorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorType() {
        return requestorType;
    }

    /**
     * Sets the value of the requestorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorType(String value) {
        this.requestorType = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the refundDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRefundDate() {
        return refundDate;
    }

    /**
     * Sets the value of the refundDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRefundDate(XMLGregorianCalendar value) {
        this.refundDate = value;
    }

    /**
     * Gets the value of the refundAmount property.
     * 
     */
    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Sets the value of the refundAmount property.
     * 
     */
    public void setRefundAmount(double value) {
        this.refundAmount = value;
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
     * Gets the value of the templateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the value of the templateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateName(String value) {
        this.templateName = value;
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
     * Gets the value of the statementCriteria property.
     * 
     * @return
     *     possible object is
     *     {@link RequestorStatementCriteria }
     *     
     */
    public RequestorStatementCriteria getStatementCriteria() {
        return statementCriteria;
    }

    /**
     * Sets the value of the statementCriteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestorStatementCriteria }
     *     
     */
    public void setStatementCriteria(RequestorStatementCriteria value) {
        this.statementCriteria = value;
    }

}
