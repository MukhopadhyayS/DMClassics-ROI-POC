/**
 *
 */
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;

import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;

/**
 *
 */
public class ReleaseCore implements Serializable {

	private static final long serialVersionUID = 1L;

	private long _requestId;
    private boolean _statusChangeToComplete;
    private List<ReleasePages> _roiPages;
    private List<Long> _supplementarityAttachmentsSeq;
    private List<Long> _supplementarityDocumentsSeq;
    private List<Long> _supplementalAttachmentsSeq;
    private List<Long> _supplementalDocumentsSeq;
    private List<Long> _pastDueInvoices;
    private long _invoiceDueDays;
    private boolean _coverLetterRequired;
    private long _coverLetterFileId;
    private boolean _invoiceRequired;
    private long _invoiceTemplateId;
    private List<String> _notes;
    private List<String> _coverLetterNotes;
    private RequestorStatementCriteria _statementCriteria;

    private InvoiceOrPrebillAndPreviewInfo _invoiceOrPrebillAndPreviewInfo;
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
