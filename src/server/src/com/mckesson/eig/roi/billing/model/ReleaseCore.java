/**
 *
 */
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;

import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
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
    "_coverLetterFileId",
    "_coverLetterRequired",
    "_invoiceDueDays",
    "_invoiceRequired",
    "_invoiceTemplateId",
    "_roiPages",
    "_supplementarityAttachmentsSeq",
    "_supplementarityDocumentsSeq",
    "_supplementalAttachmentsSeq",
    "_supplementalDocumentsSeq",
    "_pastDueInvoices",
    "_notes",
    "_coverLetterNotes",
    "_requestId",
    "_statusChangeToComplete",
    "_invoiceOrPrebillAndPreviewInfo",
    "_statementCriteria"
})
public class ReleaseCore implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@XmlElement(name="coverLetterFileId")
	private long _coverLetterFileId;
	
	@XmlElement(name="coverLetterRequired")
    private boolean _coverLetterRequired;
	
	@XmlElement(name="invoiceDueDays")
	private long _invoiceDueDays;
	
	@XmlElement(name="invoiceRequired")
	private boolean _invoiceRequired;
	
	@XmlElement(name="invoiceTemplateId")
	private long _invoiceTemplateId;
	
	@XmlElement(name="roiPages", nillable = true)
	private List<ReleasePages> _roiPages;
	
	@XmlElement(name="supplementarityAttachmentsSeq", nillable = true)
	private List<Long> _supplementarityAttachmentsSeq;
	
	@XmlElement(name="supplementarityDocumentsSeq", nillable = true)
    private List<Long> _supplementarityDocumentsSeq;
	
	@XmlElement(name="supplementalAttachmentsSeq", nillable = true)
    private List<Long> _supplementalAttachmentsSeq;
	
	@XmlElement(name="supplementalDocumentsSeq", nillable = true)
    private List<Long> _supplementalDocumentsSeq;
	
	@XmlElement(name="pastDueInvoices", nillable = true)
    private List<Long> _pastDueInvoices;
	
	@XmlElement(name="notes", nillable = true)
    private List<String> _notes;
	
	@XmlElement(name="coverLetterNotes", nillable = true)
    private List<String> _coverLetterNotes;
	
	@XmlElement(name="requestId")
	private long _requestId;
	
	@XmlElement(name="statusChangeToComplete")
    private boolean _statusChangeToComplete;
	
	@XmlElement(name="invoiceOrPrebillAndPreviewInfo", required = true)
    private InvoiceOrPrebillAndPreviewInfo _invoiceOrPrebillAndPreviewInfo;
	
	@XmlElement(name="statementCriteria", required = true)
    private RequestorStatementCriteria _statementCriteria;

    
    
    
    
    
    /**
     * @return the requestId
     */
    public long getRequestId() {
        return _requestId;
    }
    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(long requestId) {
        this._requestId = requestId;
    }
    /**
     * @return the statusChangeToComplete
     */
    public boolean isStatusChangeToComplete() {
        return _statusChangeToComplete;
    }
    /**
     * @param statusChangeToComplete the statusChangeToComplete to set
     */
    public void setStatusChangeToComplete(boolean statusChangeToComplete) {
        this._statusChangeToComplete = statusChangeToComplete;
    }
    /**
     * @return the pastDueInvoices
     */
    public List<Long> getPastDueInvoices() {
        return _pastDueInvoices;
    }
    /**
     * @param pastDueInvoices the pastDueInvoices to set
     */
    public void setPastDueInvoices(List<Long> pastDueInvoices) {
        this._pastDueInvoices = pastDueInvoices;
    }
    /**
     * @return the invoiceDueDays
     */
    public long getInvoiceDueDays() {
        return _invoiceDueDays;
    }
    /**
     * @param invoiceDueDays the invoiceDueDays to set
     */
    public void setInvoiceDueDays(long invoiceDueDays) {
        this._invoiceDueDays = invoiceDueDays;
    }
    /**
     * @return the coverLetterRequired
     */
    public boolean isCoverLetterRequired() {
        return _coverLetterRequired;
    }
    /**
     * @param coverLetterRequired the coverLetterRequired to set
     */
    public void setCoverLetterRequired(boolean coverLetterRequired) {
        this._coverLetterRequired = coverLetterRequired;
    }
    /**
     * @return the coverLetterFileId
     */
    public long getCoverLetterFileId() {
        return _coverLetterFileId;
    }
    /**
     * @param coverLetterFileId the coverLetterFileId to set
     */
    public void setCoverLetterFileId(long coverLetterFileId) {
        this._coverLetterFileId = coverLetterFileId;
    }
    /**
     * @return the invoiceRequired
     */
    public boolean isInvoiceRequired() {
        return _invoiceRequired;
    }
    /**
     * @param invoiceRequired the invoiceRequired to set
     */
    public void setInvoiceRequired(boolean invoiceRequired) {
        this._invoiceRequired = invoiceRequired;
    }
    /**
     * @return the invoiceTemplateId
     */
    public long getInvoiceTemplateId() {
        return _invoiceTemplateId;
    }
    /**
     * @param invoiceTemplateId the invoiceTemplateId to set
     */
    public void setInvoiceTemplateId(long invoiceTemplateId) {
        this._invoiceTemplateId = invoiceTemplateId;
    }
	/**
	 * @param _roiPagesSeq the _roiPagesSeq to set
	 */
	public void setRoiPages(List<ReleasePages> _roiPages) {
		this._roiPages = _roiPages;
	}
	/**
	 * @return the _roiPagesSeq
	 */
	public List<ReleasePages> getRoiPages() {
		return _roiPages;
	}
	/**
	 * @param _supplementarityAttachmentsSeq the _supplementarityAttachmentsSeq to set
	 */
	public void setSupplementarityAttachmentsSeq(List<Long> _supplementarityAttachmentsSeq) {
		this._supplementarityAttachmentsSeq = _supplementarityAttachmentsSeq;
	}
	/**
	 * @return the _supplementarityAttachmentsSeq
	 */
	public List<Long> getSupplementarityAttachmentsSeq() {
		return _supplementarityAttachmentsSeq;
	}
	/**
	 * @param _supplementarityDocumentsSeq the _supplementarityDocumentsSeq to set
	 */
	public void setSupplementarityDocumentsSeq(List<Long> _supplementarityDocumentsSeq) {
		this._supplementarityDocumentsSeq = _supplementarityDocumentsSeq;
	}
	/**
	 * @return the _supplementarityDocumentsSeq
	 */
	public List<Long> getSupplementarityDocumentsSeq() {
		return _supplementarityDocumentsSeq;
	}
	/**
	 * @param _supplementalAttachmentsSeq the _supplementalAttachmentsSeq to set
	 */
	public void setSupplementalAttachmentsSeq(List<Long> _supplementalAttachmentsSeq) {
		this._supplementalAttachmentsSeq = _supplementalAttachmentsSeq;
	}
	/**
	 * @return the _supplementalAttachmentsSeq
	 */
	public List<Long> getSupplementalAttachmentsSeq() {
		return _supplementalAttachmentsSeq;
	}
	/**
	 * @param _supplementalDocumentsSeq the _supplementalDocumentsSeq to set
	 */
	public void setSupplementalDocumentsSeq(List<Long> _supplementalDocumentsSeq) {
		this._supplementalDocumentsSeq = _supplementalDocumentsSeq;
	}
	/**
	 * @return the _supplementalDocumentsSeq
	 */
	public List<Long> getSupplementalDocumentsSeq() {
		return _supplementalDocumentsSeq;
	}
	/**
	 * @param _notes the _notes to set
	 */
	public void setNotes(List<String> _notes) {
		this._notes = _notes;
	}
	/**
	 * @return the _notes
	 */
	public List<String> getNotes() {
		return _notes;
	}

    /**
     * @return the invoiceOrPrebillAndPreviewInfo
     */
    public InvoiceOrPrebillAndPreviewInfo getInvoiceOrPrebillAndPreviewInfo() {
        return _invoiceOrPrebillAndPreviewInfo;
    }

    /**
     * @param invoiceInfo the invoiceOrPrebillAndPreviewInfo to set
     */
    public void setInvoiceOrPrebillAndPreviewInfo(InvoiceOrPrebillAndPreviewInfo invoiceInfo) {
        _invoiceOrPrebillAndPreviewInfo = invoiceInfo;
    }
    public RequestorStatementCriteria getStatementCriteria() { return _statementCriteria; }
    public void setStatementCriteria(RequestorStatementCriteria statementCriteria) {
        _statementCriteria = statementCriteria;
    }
    public List<String> getCoverLetterNotes() { return _coverLetterNotes; }
    public void setCoverLetterNotes(List<String> coverLetterNotes) {
        _coverLetterNotes = coverLetterNotes;
    }

}
