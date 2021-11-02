/**
 * 
 */
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LetterInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LetterInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="letterTemplateId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LetterInfo", propOrder = {
    "_letterTemplateId",
    "_requestId",
    "_notes",
    "_type"
})
public class LetterInfo implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	
	@XmlElement(name="letterTemplateId")
	private long _letterTemplateId;
	
	@XmlElement(name="requestId")
	private long _requestId;
	
	@XmlElement(name="notes")
	private String[] _notes;
	
	@XmlElement(name="type", required = true)
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
