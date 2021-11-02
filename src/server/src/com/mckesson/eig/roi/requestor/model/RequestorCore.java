/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/


package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ValidationParams;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Requestor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Requestor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorType" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestorTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="middleName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="workPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="homePhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cellPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contactName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contactPhone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Requestor", propOrder = {
    "_requestorSeq",
    "_requestorType",
    "_requestorTypeName",
    "_firstName",
    "_middleName",
    "_lastName",
    "_workPhone",
    "_homePhone",
    "_cellPhone",
    "_contactName",
    "_contactPhone",
    "_fax"
})
@SuppressWarnings("serial")
public class RequestorCore
implements Serializable {

    @XmlTransient
    private long    _requestId;
    
    @XmlElement(name="requestorId")
    private long    _requestorSeq;
    
    @XmlElement(name="requestorType")
    private Long    _requestorType;
    
    @XmlElement(name="firstName", required = true)
    private String  _firstName;
    
    @XmlElement(name="middleName", required = true)
    private String  _middleName;
    
    @XmlElement(name="lastName", required = true)
    private String  _lastName;
    
    @XmlElement(name="requestorTypeName", required = true)
    private String  _requestorTypeName;
    
    @XmlElement(name="workPhone", required = true)
    private String  _workPhone;
    
    @XmlElement(name="homePhone", required = true)
    private String  _homePhone;
    
    @XmlElement(name="cellPhone", required = true)
    private String  _cellPhone;
    
    @XmlElement(name="contactName", required = true)
    private String  _contactName;
    
    @XmlElement(name="contactPhone", required = true)
    private String  _contactPhone;
    
    @XmlElement(name="fax", required = true)
    private String  _fax;
    
    @XmlTransient
    private String  _suffix;
    
    @XmlTransient
    private String _mainAddress1;
    
    @XmlTransient
    private String _mainAddress2;
    
    @XmlTransient
    private String _mainAddress3;
    
    @XmlTransient
    private String _mainCity;
    
    @XmlTransient
    private String _mainPostalCode;
    
    @XmlTransient
    private String _mainState;
    
    @XmlTransient
    private String _mainCountryName;
    
    @XmlTransient
    private String _mainCountryCode;

    @XmlTransient
    private long    _id;
    
    @XmlTransient
    private Date    _createdDate;
    
    @XmlTransient
    private Date    _modifiedDate;
    
    @XmlTransient
    private int     _modifiedBy;
    
    @XmlTransient
    private int     _createdBy;
    

    public long getId() { return _id; }
    public void setId(long id) {  _id = id; }

    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { _modifiedDate = modifiedDate; }

    public int getModifiedBy() {  return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy; }

    public int getCreatedBy() { return _createdBy; }
    public void setCreatedBy(int createdBy) { _createdBy = createdBy; }

    public void setRequestorType(Long requestorType) { _requestorType = requestorType; }
    public Long getRequestorType() { return _requestorType; }

    @ValidationParams (isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK,
                       maxLength = 50,
                       maxLenErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setRequestorTypeName(String requestorTypeName) {
        _requestorTypeName = requestorTypeName;
    }
    public String getRequestorTypeName() { return _requestorTypeName; }

    @ValidationParams (maxLength = 256,
                     maxLenErrCode = ROIClientErrorCodes.REQUESTOR_WORK_PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setWorkPhone(String workPhone) { _workPhone = workPhone; }
    public String getWorkPhone() { return _workPhone; }

    @ValidationParams (maxLength = 256,
                    maxLenErrCode = ROIClientErrorCodes.REQUESTOR_HOME_PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setHomePhone(String homePhone) { _homePhone = homePhone; }
    public String getHomePhone() { return _homePhone; }

    public void setContactName(String contactName) { _contactName = contactName; }
    public String getContactName() { return _contactName; }

    @ValidationParams (maxLength = 256,
                  maxLenErrCode = ROIClientErrorCodes.REQUESTOR_CONTACT_PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setContactPhone(String contactPhone) { _contactPhone = contactPhone; }
    public String getContactPhone() { return _contactPhone; }

    @ValidationParams (maxLength = 20,
                     maxLenErrCode = ROIClientErrorCodes.REQUESTOR_FAX_NUMBER_LENGTH_EXCEEDS_LIMIT)
    public void setFax(String fax) { _fax = fax; }
    public String getFax() { return _fax; }

    @ValidationParams (maxLength = 256,
                     maxLenErrCode = ROIClientErrorCodes.REQUESTOR_FIRST_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setFirstName(String firstName) { _firstName = firstName; }
    public String getFirstName() { return _firstName; }

    @ValidationParams (maxLength = 256,
                    maxLenErrCode = ROIClientErrorCodes.REQUESTOR_MIDDLE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setMiddleName(String middleName) { _middleName = middleName; }
    public String getMiddleName() { return _middleName; }

    @ValidationParams (maxLength = 256,
                    maxLenErrCode = ROIClientErrorCodes.REQUESTOR_LAST_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setLastName(String lastName) { _lastName = lastName; }
    public String getLastName() { return _lastName; }

    @ValidationParams (maxLength = 256,
                    maxLenErrCode = ROIClientErrorCodes.REQUESTOR_CELL_PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setCellPhone(String cellPhone) { _cellPhone = cellPhone; }
    public String getCellPhone() { return _cellPhone; }

    @ValidationParams (maxLength = 256,
                    maxLenErrCode = ROIClientErrorCodes.REQUESTOR_SUFFIX_LENGTH_EXCEEDS_LIMIT)
    public void setSuffix(String suffix) { _suffix = suffix; }
    public String getSuffix() { return _suffix; }

    public void setRequestorSeq(long requestorSeq) { _requestorSeq = requestorSeq; }
    public long getRequestorSeq() { return _requestorSeq; }

    public long getRequestId() { return _requestId; }
    public void setRequestId(long requestId) { _requestId = requestId; }

    public String getMainAddress1() {
		return _mainAddress1;
	}
	public void setMainAddress1(String mainAddress1) {
		this._mainAddress1 = mainAddress1;
	}
	public String getMainAddress2() {
		return _mainAddress2;
	}
	public void setMainAddress2(String mainAddress2) {
		this._mainAddress2 = mainAddress2;
	}
	public String getMainAddress3() {
		return _mainAddress3;
	}
	public void setMainAddress3(String mainAddress3) {
		this._mainAddress3 = mainAddress3;
	}
	public String getMainCity() {
		return _mainCity;
	}
	public void setMainCity(String mainCity) {
		this._mainCity = mainCity;
	}
	public String getMainPostalCode() {
		return _mainPostalCode;
	}
	public void setMainPostalCode(String mainPostalCode) {
		this._mainPostalCode = mainPostalCode;
	}
	public String getMainState() {
		return _mainState;
	}
	public void setMainState(String mainState) {
		this._mainState = mainState;
	}
	public String getMainCountryName() {
		return _mainCountryName;
	}
	public void setMainCountryName(String mainCountryName) {
		this._mainCountryName = mainCountryName;
	}
	public String getMainCountryCode() {
		return _mainCountryCode;
	}
	public void setMainCountryCode(String mainCountryCode) {
		this._mainCountryCode = mainCountryCode;
	}
	@Override
    public String toString() {

        return new StringBuffer()
                    .append("Requestor:")
                    .append(_id)
                    .append(" , Requestor Seq:")
                    .append(_requestorSeq)
                    .append(" , Request Name:")
                    .append(_firstName)
                    .append(_middleName)
                    .append(_lastName)
                    .append(" , Requestor Type:")
                    .append(_requestorTypeName)
                .toString();
    }

}
