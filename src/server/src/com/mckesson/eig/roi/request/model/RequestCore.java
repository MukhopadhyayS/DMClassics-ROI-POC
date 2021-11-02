/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;
import com.mckesson.eig.roi.requestor.model.RequestorCore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="receiptDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="completedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="statusReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestReasonAttribute" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="statusChangedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedByUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="defaultFacility" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultFacilityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorDetail" type="{urn:eig.mckesson.com}Requestor"/>
 *         &lt;element name="releaseCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="hasDraftRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="balanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="authDoc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocSubtitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="authDocDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="conversionSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "_id",
    "_status",
    "_receiptDate",
    "_completedDate",
    "_statusReason",
    "_requestReason",
    "_requestReasonAttribute",
    "_statusChangedDt",
    "_requestPassword",
    "_createdBy",
    "_modifiedBy",
    "_modifiedDate",
    "_modifiedByUser",
    "_createdDate",
    "_defaultFacility",
    "_defaultFacilityCode",
    "_requestorDetail",
    "_releaseCount",
    "_hasDraftRelease",
    "_balanceDue",
    "_authDoc",
    "_authDocName",
    "_authDocSubtitle",
    "_authDocDateTime",
    "_conversionSource"
})
public class RequestCore
implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name="id")
    private long    _id;
    
    @XmlElement(name="status", required = true)
    private String  _status;
    
    @XmlElement(name="receiptDate", required = true, nillable = true)
    private Date    _receiptDate;
    
    @XmlElement(name="completedDate", required = true, nillable = true)
    private Date    _completedDate;
    
    @XmlElement(name="statusReason", required = true)
    private String  _statusReason;
    
    @XmlElement(name="requestReason", required = true)
    private String  _requestReason;
    
    @XmlElement(name="requestReasonAttribute", required = true)
    private String  _requestReasonAttribute;

    @XmlElement(name="statusChangedDt", required = true, nillable = true)
    private Date  _statusChangedDt;
    
    @XmlElement(name="requestPassword", required = true)
    private String  _requestPassword;
    
    @XmlTransient
    private int     _recordVersion;
    
    @XmlElement(name="createdDate", required = true, nillable = true)
    private Date    _createdDate;
    
    @XmlElement(name="modifiedDate", required = true, nillable = true)
    private Date    _modifiedDate;
    
    @XmlElement(name="modifiedBy")
    private int     _modifiedBy;
    
    @XmlElement(name="modifiedByUser", required = true, nillable = true)
    private String  _modifiedByUser;
    
    @XmlElement(name="createdBy")
    private int     _createdBy;
    
    @XmlElement(name="defaultFacility", required = true)
    private String _defaultFacility;
    
    @XmlElement(name="defaultFacilityCode", required = true)
    private String _defaultFacilityCode;

    @XmlElement(name="releaseCount")
    private long _releaseCount;
    
    @XmlElement(name="hasDraftRelease")
    private boolean _hasDraftRelease;
    
    @XmlElement(name="balanceDue")
    private Double _balanceDue;

    // added for Authorization request
    @XmlElement(name="authDoc", required = true)
    private String _authDoc;
    
    @XmlElement(name="authDocName", required = true)
    private String _authDocName;
    
    @XmlElement(name="authDocSubtitle", required = true)
    private String _authDocSubtitle;
    
    @XmlElement(name="authDocDateTime", required = true, nillable = true)
    private Date   _authDocDateTime;
    
    @XmlElement(name="conversionSource", required = true)
    private String _conversionSource;

    @XmlElement(name="requestorDetail", required = true)
    private RequestorCore _requestorDetail;
    
    @XmlTransient
    private boolean _unbillable = true;
    
    

    public long getId() { return _id; }
    public void setId(long id) {  _id = id; }

    @ValidationParams (isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUEST_STATUS_IS_BLANK,
                       maxLength = 25,
                       maxLenErrCode = ROIClientErrorCodes.REQUEST_STATUS_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public void setStatus(String status) { _status = status; }
    public String getStatus() {  return _status; }

    @ValidationParams (isMandatory = false,
                       maxLength = 256,
                       maxLenErrCode = ROIClientErrorCodes.STATUS_REASON_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public void setStatusReason(String statusReason) {  _statusReason = statusReason; }
    public String getStatusReason() { return _statusReason; }

    public int getModifiedBy() {  return _modifiedBy; }
    public void setModifiedBy(int modifiedBy) { _modifiedBy = modifiedBy; }

    public Date getCreatedDate() { return _createdDate; }
    public void setCreatedDate(Date createdDate) { _createdDate = createdDate; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int recordVersion) { _recordVersion = recordVersion; }

    @ValidationParams (isMandatory = false,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUEST_PASSWORD_IS_BLANK,
                       maxLength = 256,
                       maxLenErrCode = ROIClientErrorCodes.REQUEST_PASSWORD_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public void setRequestPassword(String requestPassword) { _requestPassword = requestPassword; }
    public String getRequestPassword() { return _requestPassword; }

    @ValidationParams (isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUEST_REASON_IS_BLANK,
                       maxLength = 256,
                       maxLenErrCode = ROIClientErrorCodes.REQUEST_REASON_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public void setRequestReason(String requestReason) { _requestReason = requestReason; }
    public String getRequestReason() { return _requestReason; }

    @ValidationParams (isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.RECEIPT_DATE_IS_BLANK,
                       maxLenErrCode = ROIClientErrorCodes.RECEIPT_DATE_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public Date getReceiptDate() { return _receiptDate; }
    public void setReceiptDate(Date receiptDate) { _receiptDate = receiptDate; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { _modifiedDate = modifiedDate; }

    public void setCreatedBy(int createdBy) { _createdBy = createdBy; }
    public int getCreatedBy() { return _createdBy; }

    public Date getStatusChangedDt() { return _statusChangedDt; }
    public void setStatusChangedDt(Date statusChangedDt) { _statusChangedDt = statusChangedDt; }

    public RequestorCore getRequestorDetail() { return _requestorDetail; }
    public void setRequestorDetail(RequestorCore requestorDetail) {
        _requestorDetail = requestorDetail; }

    public void setCompletedDate(Date completedDate) { _completedDate = completedDate; }
    public Date getCompletedDate() { return _completedDate; }

    public void setModifiedByUser(String modifiedByUser) { _modifiedByUser = modifiedByUser; }
    public String getModifiedByUser() { return _modifiedByUser; }

    public String getDefaultFacility() { return _defaultFacility; }
    public void setDefaultFacility(String defaultFacility) {
        _defaultFacility = defaultFacility;
    }

    public String getDefaultFacilityCode() { return _defaultFacilityCode; }
    public void setDefaultFacilityCode(String defaultFacilityCode) {
        _defaultFacilityCode = defaultFacilityCode;
    }

    public long getReleaseCount() { return _releaseCount; }
    public void setReleaseCount(long releaseCount) { _releaseCount = releaseCount; }

    public boolean isHasDraftRelease() { return _hasDraftRelease; }
    public void setHasDraftRelease(boolean hasDraftRelease) { _hasDraftRelease = hasDraftRelease; }

    @ValidationParams (isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUEST_REASON_ATTRIBUTE_IS_BLANK,
                       maxLength = 256,
                       maxLenErrCode = ROIClientErrorCodes.REQUEST_REASON_ATTRIBUTE_LENGTH_EXCEEDS_LIMIT,
                       pattern = ROIConstants.ALLOW_ALL)
    public String getRequestReasonAttribute() { return _requestReasonAttribute; }
    public void setRequestReasonAttribute(String requestReasonAttribute) {
        _requestReasonAttribute = requestReasonAttribute;
    }

    public Double getBalanceDue() { return _balanceDue; }
    public void setBalanceDue(Double balanceDue) { _balanceDue = balanceDue; }

    public String getAuthDoc() { return _authDoc; }
    public void setAuthDoc(String authDoc) { _authDoc = authDoc; }

    public String getAuthDocName() { return _authDocName; }
    public void setAuthDocName(String authDocName) { _authDocName = authDocName; }

    public String getAuthDocSubtitle() { return _authDocSubtitle; }
    public void setAuthDocSubtitle(String authDocSubtitle) { _authDocSubtitle = authDocSubtitle; }

    public Date getAuthDocDateTime() { return _authDocDateTime; }
    public void setAuthDocDateTime(Date authDocDateTime) { _authDocDateTime = authDocDateTime; }

    public boolean isUnbillable() { return _unbillable; }
    public void setUnbillable(boolean unbillable) { _unbillable = unbillable; }

    public String getConversionSource() { return _conversionSource; }
    public void setConversionSource(String conversionSource) {
        _conversionSource = conversionSource;
    }

    /**
     * This method set the old details to merge request
     * @param req old request details
     */
    public void setOldDetails(RequestCore req) {

       _createdBy = req.getCreatedBy();
       _createdDate = req.getCreatedDate();
    }

    /**
     * This method creates the audit comments for retrieve request
     * @return the audit comments for retrieve request
     */
    public String createAuditComment() {
        return "Request Created - " + _id;
    }

    /**
     * This method create audit commend for create event
     * @param id request id
     * @param oldName old status name
     * @param newName new status name
     * @return audit comment for create event
     */
    public String toCreateEvent(long id, String oldName, String newName) {

        return new StringBuffer().append("The status of request ")
                                 .append(id)
                                 .append(" was changed from ")
                                 .append(oldName)
                                 .append(" to ")
                                 .append(newName)
                                 .append(".")
                                 .toString();

    }

    /**
     * This method create audit commend for create event
     * @param id request id
     * @return audit comment for create event
     */
    public String constructSearchRetrieveAudit() {

        return new StringBuffer().append("Application : ROI. ")
                .append("The request with Request ID ")
                .append(_id)
                .append(" was selected from the search results")
                .append(".")
                .toString();

    }

    /**
     * This method creates the audit comments for retrieve request
     * @return the audit comments for retrieve request
     */
    public String viewAuditComment() {
        return "Request Viewed - " + _id;
    }

    /**
     * This method creates the audit comments for update request
     * @return the audit comments for update request
     */
    public String updateAuditComment() {
        return "Request Updated - " + _id;
    }

    @Override
    public String toString() {

        return new StringBuffer()
                    .append("request:")
                    .append(_id)
                    .append(" , Status:")
                    .append(_status)
                    .append(" , Request reason:")
                    .append(_requestReason)
                .toString();
    }

}
