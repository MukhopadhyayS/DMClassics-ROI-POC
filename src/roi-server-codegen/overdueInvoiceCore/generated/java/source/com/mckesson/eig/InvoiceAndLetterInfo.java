
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceAndLetterInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceAndLetterInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="invoices" type="{urn:eig.mckesson.com}RequestorInvoices" maxOccurs="unbounded"/>
 *         &lt;element name="invoiceTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorLetterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="reqLetterNotes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="invoiceNotes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pastInvIds" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="isLetter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isNewInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isPastInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="outputInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="statementCriteria" type="{urn:eig.mckesson.com}RequestorStatementCriteria"/>
 *         &lt;element name="properties" type="{urn:eig.mckesson.com}propertiesMap" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceAndLetterInfo", propOrder = {
    "invoices",
    "invoiceTemplateId",
    "requestorLetterTemplateId",
    "reqLetterNotes",
    "invoiceNotes",
    "pastInvIds",
    "isLetter",
    "isNewInvoice",
    "isPastInvoice",
    "outputInvoice",
    "statementCriteria",
    "properties"
})
public class InvoiceAndLetterInfo {

    @XmlElement(required = true)
    protected List<RequestorInvoices> invoices;
    protected long invoiceTemplateId;
    protected long requestorLetterTemplateId;
    protected List<String> reqLetterNotes;
    protected List<String> invoiceNotes;
    protected Long pastInvIds;
    protected boolean isLetter;
    protected boolean isNewInvoice;
    protected boolean isPastInvoice;
    protected boolean outputInvoice;
    @XmlElement(required = true)
    protected RequestorStatementCriteria statementCriteria;
    protected List<PropertiesMap> properties;

    /**
     * Gets the value of the invoices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestorInvoices }
     * 
     * 
     */
    public List<RequestorInvoices> getInvoices() {
        if (invoices == null) {
            invoices = new ArrayList<RequestorInvoices>();
        }
        return this.invoices;
    }

    /**
     * Gets the value of the invoiceTemplateId property.
     * 
     */
    public long getInvoiceTemplateId() {
        return invoiceTemplateId;
    }

    /**
     * Sets the value of the invoiceTemplateId property.
     * 
     */
    public void setInvoiceTemplateId(long value) {
        this.invoiceTemplateId = value;
    }

    /**
     * Gets the value of the requestorLetterTemplateId property.
     * 
     */
    public long getRequestorLetterTemplateId() {
        return requestorLetterTemplateId;
    }

    /**
     * Sets the value of the requestorLetterTemplateId property.
     * 
     */
    public void setRequestorLetterTemplateId(long value) {
        this.requestorLetterTemplateId = value;
    }

    /**
     * Gets the value of the reqLetterNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqLetterNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqLetterNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReqLetterNotes() {
        if (reqLetterNotes == null) {
            reqLetterNotes = new ArrayList<String>();
        }
        return this.reqLetterNotes;
    }

    /**
     * Gets the value of the invoiceNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoiceNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoiceNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getInvoiceNotes() {
        if (invoiceNotes == null) {
            invoiceNotes = new ArrayList<String>();
        }
        return this.invoiceNotes;
    }

    /**
     * Gets the value of the pastInvIds property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPastInvIds() {
        return pastInvIds;
    }

    /**
     * Sets the value of the pastInvIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPastInvIds(Long value) {
        this.pastInvIds = value;
    }

    /**
     * Gets the value of the isLetter property.
     * 
     */
    public boolean isIsLetter() {
        return isLetter;
    }

    /**
     * Sets the value of the isLetter property.
     * 
     */
    public void setIsLetter(boolean value) {
        this.isLetter = value;
    }

    /**
     * Gets the value of the isNewInvoice property.
     * 
     */
    public boolean isIsNewInvoice() {
        return isNewInvoice;
    }

    /**
     * Sets the value of the isNewInvoice property.
     * 
     */
    public void setIsNewInvoice(boolean value) {
        this.isNewInvoice = value;
    }

    /**
     * Gets the value of the isPastInvoice property.
     * 
     */
    public boolean isIsPastInvoice() {
        return isPastInvoice;
    }

    /**
     * Sets the value of the isPastInvoice property.
     * 
     */
    public void setIsPastInvoice(boolean value) {
        this.isPastInvoice = value;
    }

    /**
     * Gets the value of the outputInvoice property.
     * 
     */
    public boolean isOutputInvoice() {
        return outputInvoice;
    }

    /**
     * Sets the value of the outputInvoice property.
     * 
     */
    public void setOutputInvoice(boolean value) {
        this.outputInvoice = value;
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

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertiesMap }
     * 
     * 
     */
    public List<PropertiesMap> getProperties() {
        if (properties == null) {
            properties = new ArrayList<PropertiesMap>();
        }
        return this.properties;
    }

}
