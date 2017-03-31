/**
 * 
 */
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;

/**
 * @author ais
 *
 */
public class LetterInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long _letterTemplateId;
	private long _requestId;
	private String[] _notes;
	private String _type;
	/**
	 * @param _letterTemplateId the _letterTemplateId to set
	 */
	public void setLetterTemplateId(long _letterTemplateId) {
		this._letterTemplateId = _letterTemplateId;
	}
	/**
	 * @return the _letterTemplateId
	 */
	public long getLetterTemplateId() {
		return _letterTemplateId;
	}
	/**
	 * @param _requestId the _requestId to set
	 */
	public void setRequestId(long _requestId) {
		this._requestId = _requestId;
	}
	/**
	 * @return the _requestId
	 */
	public long getRequestId() {
		return _requestId;
	}
	/**
	 * @param _notes the _notes to set
	 */
	public void setNotes(String[] _notes) {
		this._notes = _notes;
	}
	/**
	 * @return the _notes
	 */
	public String[] getNotes() {
		return _notes;
	}
	/**
	 * @param _type the _type to set
	 */
	public void setType(String _type) {
		this._type = _type;
	}
	/**
	 * @return the _type
	 */
	public String getType() {
		return _type;
	}
}
