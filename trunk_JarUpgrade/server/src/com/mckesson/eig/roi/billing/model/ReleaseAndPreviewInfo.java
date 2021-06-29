/**
 * 
 */
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;

/**
 *
 */
public class ReleaseAndPreviewInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long _releaseId;
    private long _invoiceId;
    private DocInfoList _docInfoList;
	/**
	 * @param _releaseId the _releaseId to set
	 */
	public void setReleaseId(long _releaseId) {
		this._releaseId = _releaseId;
	}
	/**
	 * @return the _releaseId
	 */
	public long getReleaseId() {
		return _releaseId;
	}
	/**
	 * @param _invoiceId the _invoiceId to set
	 */
	public void setInvoiceId(long _invoiceId) {
		this._invoiceId = _invoiceId;
	}
	/**
	 * @return the _invoiceId
	 */
	public long getInvoiceId() {
		return _invoiceId;
	}
	/**
	 * @param _docInfoList the _docInfoList to set
	 */
	public void setDocInfoList(DocInfoList _docInfoList) {
		this._docInfoList = _docInfoList;
	}
	/**
	 * @return the _docInfoList
	 */
	public DocInfoList getDocInfoList() {
		return _docInfoList;
	}
    
}
