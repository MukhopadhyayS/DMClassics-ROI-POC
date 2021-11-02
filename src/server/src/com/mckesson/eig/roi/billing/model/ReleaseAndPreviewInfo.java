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
 * <p>Java class for ReleaseAndPreviewInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReleaseAndPreviewInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="releaseId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="docInfoList" type="{urn:eig.mckesson.com}DocInfoList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReleaseAndPreviewInfo", propOrder = {
    "_releaseId",
    "_invoiceId",
    "_docInfoList"
})
public class ReleaseAndPreviewInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @XmlElement(name="releaseId")
    private long _releaseId;
    
    @XmlElement(name="invoiceId")
    private long _invoiceId;
    
    @XmlElement(name="docInfoList")
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
