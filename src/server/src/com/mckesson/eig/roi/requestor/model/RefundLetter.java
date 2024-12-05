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

package com.mckesson.eig.roi.requestor.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mckesson.dm.core.common.util.StringUtilities;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.utils.SecureStringAccessor;

/**
 * @author elangovans
 *
 */
public class RefundLetter
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private long _requestorId;
    private String _requestorName;
    private String _requestorTypeName;
    private long _requestorTypeId;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _country;
    private String _countryCode;
    private String _postalCode;
    private String _state;
    private double _refundAmount;
    private String _userName;
    private long _userInstanceId;

    private String _templateName;
    private long _templateFileId;
    private String _outputMethod;
    private String _queuePassword;
    private List<String> _notes;
    private Date _refundDate;

    public String getUserName() { return _userName; }
    public void setUserName(String userName) { _userName = userName; }

    public String getRequestorName() { return _requestorName; }
    public void setRequestorName(String requestorName) { _requestorName = requestorName; }

    public String getRequestorTypeName() { return _requestorTypeName; }
    public void setRequestorTypeName(String requestorType) { _requestorTypeName = requestorType; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }

    public long getRequestorTypeId() { return _requestorTypeId; }
    public void setRequestorTypeId(long requestorTypeId) { _requestorTypeId = requestorTypeId; }

    public double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(double refundAmount) { _refundAmount = refundAmount; }

    public String getPostalCode() { return _postalCode; }
    public void setPostalCode(String postalCode) { _postalCode = postalCode; }

    public String getCity() { return _city; }
    public void setCity(String city) { _city = city; }

    public String getCountry() { return _country; }
    public void setCountry(String country) { _country = country; }

    public String getAddress1() { return _address1; }
    public void setAddress1(String address1) { _address1 = address1; }

    public String getAddress2() { return _address2; }
    public void setAddress2(String address2) { _address2 = address2; }

    public String getAddress3() { return _address3; }
    public void setAddress3(String address3) { _address3 = address3; }

    public String getState() { return _state; }
    public void setState(String state) { _state = state; }

    public long getUserInstanceId() { return _userInstanceId; }
    public void setUserInstanceId(long userInstanceId) { _userInstanceId = userInstanceId; }

    public String getTemplateName() { return _templateName; }
    public void setTemplateName(String templateName) { _templateName = templateName; }

    public long getTemplateFileId() { return _templateFileId; }
    public void setTemplateFileId(long templateFileId) { _templateFileId = templateFileId; }

    public List<String> getNotes() { return _notes; }
    public void setNotes(List<String> notes) { _notes = notes; }

    public String getCountryCode() { return _countryCode; }
    public void setCountryCode(String countryCode) { _countryCode = countryCode; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public String getQueuePassword() { 
        if (_queuePassword == null) {
            return null;
        }
        return _queuePassword;
    }
    public void setQueuePassword(String queuePassword) { 
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = queuePassword;
    }

    public Date getRefundDate() { return _refundDate; }
    public void setRefundDate(Date refundDate) { _refundDate = refundDate; }

    public String getNote() {

        if (null == getNotes()) {
            return null;
        }
        String string = getNotes().toString();
        return string.substring(1, string.length() - 1);
    }
    public void setNote(String note) {

        if (null == note) {
            return;
        }
        String[] split = null;
        if(note.contains(ROIConstants.FIELD_SEPERATOR))
           split = note.split(ROIConstants.FIELD_SEPERATOR);
        else
           split = note.split(ROIConstants.FIELD_DELIMITER);
        setNotes(Arrays.asList(split));
    }

}
