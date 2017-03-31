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

/**
 * @author OFS
 * @date Jun 29, 2012
 * @since Jun 29, 2012
 *
 */
public class RequestCore
implements Serializable {

    private static final long serialVersionUID = 1L;

    private long    _id;
    private String  _status;
    private Date    _receiptDate;
    private String  _statusReason;
    private String  _requestReason;
    private String  _requestReasonAttribute;

    private Date  _statusChangedDt;
    private String  _requestPassword;
    private int     _recordVersion;
    private Date    _createdDate;
    private Date    _completedDate;
    private Date    _modifiedDate;
    private int     _modifiedBy;
    private String  _modifiedByUser;
    private int     _createdBy;
    private String _defaultFacility;
    private String _defaultFacilityCode;

    private long _releaseCount;
    private boolean _hasDraftRelease;
    private Double _balanceDue;

    // added for Authorization request
    private String _authDoc;
    private String _authDocName;
    private String _authDocSubtitle;
    private Date   _authDocDateTime;
    private String _conversionSource;

    private RequestorCore _requestorDetail;
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
