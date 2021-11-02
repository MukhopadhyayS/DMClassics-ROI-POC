
package com.mckesson.eig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReleaseCore complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseCore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="coverLetterFileId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="coverLetterRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="roiPages" type="{urn:eig.mckesson.com}ROIPages" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementarityAttachmentsSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementarityDocumentsSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalAttachmentsSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="supplementalDocumentsSeq" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pastDueInvoices" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="coverLetterNotes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="statusChangeToComplete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceOrPrebillAndPreviewInfo" type="{urn:eig.mckesson.com}InvoiceOrPrebillAndPreviewInfo"/>
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
@XmlType(name = "ReleaseCore", propOrder = {
    "coverLetterFileId",
    "coverLetterRequired",
    "invoiceDueDays",
    "invoiceRequired",
    "invoiceTemplateId",
    "roiPages",
    "supplementarityAttachmentsSeq",
    "supplementarityDocumentsSeq",
    "supplementalAttachmentsSeq",
    "supplementalDocumentsSeq",
    "pastDueInvoices",
    "notes",
    "coverLetterNotes",
    "requestId",
    "statusChangeToComplete",
    "invoiceOrPrebillAndPreviewInfo",
    "statementCriteria"
})
public class ReleaseCore {

    protected long coverLetterFileId;
    protected boolean coverLetterRequired;
    protected long invoiceDueDays;
    protected boolean invoiceRequired;
    protected long invoiceTemplateId;
    @XmlElement(nillable = true)
    protected List<ROIPages> roiPages;
    @XmlElement(nillable = true)
    protected List<Long> supplementarityAttachmentsSeq;
    @XmlElement(nillable = true)
    protected List<Long> supplementarityDocumentsSeq;
    @XmlElement(nillable = true)
    protected List<Long> supplementalAttachmentsSeq;
    @XmlElement(nillable = true)
    protected List<Long> supplementalDocumentsSeq;
    @XmlElement(nillable = true)
    protected List<Long> pastDueInvoices;
    @XmlElement(nillable = true)
    protected List<String> notes;
    @XmlElement(nillable = true)
    protected List<String> coverLetterNotes;
    protected long requestId;
    protected boolean statusChangeToComplete;
    @XmlElement(required = true)
    protected InvoiceOrPrebillAndPreviewInfo invoiceOrPrebillAndPreviewInfo;
    @XmlElement(required = true)
    protected RequestorStatementCriteria statementCriteria;

    /**
     * Gets the value of the coverLetterFileId property.
     * 
     */
    public long getCoverLetterFileId() {
        return coverLetterFileId;
    }

    /**
     * Sets the value of the coverLetterFileId property.
     * 
     */
    public void setCoverLetterFileId(long value) {
        this.coverLetterFileId = value;
    }

    /**
     * Gets the value of the coverLetterRequired property.
     * 
     */
    public boolean isCoverLetterRequired() {
        return coverLetterRequired;
    }

    /**
     * Sets the value of the coverLetterRequired property.
     * 
     */
    public void setCoverLetterRequired(boolean value) {
        this.coverLetterRequired = value;
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
     * Gets the value of the invoiceRequired property.
     * 
     */
    public boolean isInvoiceRequired() {
        return invoiceRequired;
    }

    /**
     * Sets the value of the invoiceRequired property.
     * 
     */
    public void setInvoiceRequired(boolean value) {
        this.invoiceRequired = value;
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
     * Gets the value of the roiPages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roiPages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoiPages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ROIPages }
     * 
     * 
     */
    public List<ROIPages> getRoiPages() {
        if (roiPages == null) {
            roiPages = new ArrayList<ROIPages>();
        }
        return this.roiPages;
    }

    /**
     * Gets the value of the supplementarityAttachmentsSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementarityAttachmentsSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementarityAttachmentsSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementarityAttachmentsSeq() {
        if (supplementarityAttachmentsSeq == null) {
            supplementarityAttachmentsSeq = new ArrayList<Long>();
        }
        return this.supplementarityAttachmentsSeq;
    }

    /**
     * Gets the value of the supplementarityDocumentsSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementarityDocumentsSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementarityDocumentsSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementarityDocumentsSeq() {
        if (supplementarityDocumentsSeq == null) {
            supplementarityDocumentsSeq = new ArrayList<Long>();
        }
        return this.supplementarityDocumentsSeq;
    }

    /**
     * Gets the value of the supplementalAttachmentsSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementalAttachmentsSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementalAttachmentsSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementalAttachmentsSeq() {
        if (supplementalAttachmentsSeq == null) {
            supplementalAttachmentsSeq = new ArrayList<Long>();
        }
        return this.supplementalAttachmentsSeq;
    }

    /**
     * Gets the value of the supplementalDocumentsSeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplementalDocumentsSeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplementalDocumentsSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getSupplementalDocumentsSeq() {
        if (supplementalDocumentsSeq == null) {
            supplementalDocumentsSeq = new ArrayList<Long>();
        }
        return this.supplementalDocumentsSeq;
    }

    /**
     * Gets the value of the pastDueInvoices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pastDueInvoices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPastDueInvoices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getPastDueInvoices() {
        if (pastDueInvoices == null) {
            pastDueInvoices = new ArrayList<Long>();
        }
        return this.pastDueInvoices;
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
     * Gets the value of the coverLetterNotes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coverLetterNotes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoverLetterNotes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCoverLetterNotes() {
        if (coverLetterNotes == null) {
            coverLetterNotes = new ArrayList<String>();
        }
        return this.coverLetterNotes;
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
     * Gets the value of the statusChangeToComplete property.
     * 
     */
    public boolean isStatusChangeToComplete() {
        return statusChangeToComplete;
    }

    /**
     * Sets the value of the statusChangeToComplete property.
     * 
     */
    public void setStatusChangeToComplete(boolean value) {
        this.statusChangeToComplete = value;
    }

    /**
     * Gets the value of the invoiceOrPrebillAndPreviewInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceOrPrebillAndPreviewInfo }
     *     
     */
    public InvoiceOrPrebillAndPreviewInfo getInvoiceOrPrebillAndPreviewInfo() {
        return invoiceOrPrebillAndPreviewInfo;
    }

    /**
     * Sets the value of the invoiceOrPrebillAndPreviewInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceOrPrebillAndPreviewInfo }
     *     
     */
    public void setInvoiceOrPrebillAndPreviewInfo(InvoiceOrPrebillAndPreviewInfo value) {
        this.invoiceOrPrebillAndPreviewInfo = value;
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
