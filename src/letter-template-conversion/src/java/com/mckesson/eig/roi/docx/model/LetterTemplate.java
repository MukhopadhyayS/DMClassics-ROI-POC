package com.mckesson.eig.roi.docx.model;

public class LetterTemplate {

	private long _id;
	private String _name;
	private byte[] _source;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * @return the template
	 */
	public byte[] getSource() {
		return _source;
	}
	
	/**
	 * @param template the template to set
	 */
	public void setSource(byte[] source) {
		_source = source;
	}

	public long getId() {
		return _id;
	}

	public void setId(long _id) {
		this._id = _id;
	}
}
