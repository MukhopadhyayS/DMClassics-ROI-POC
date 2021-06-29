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

package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;



/**
 * @author manikandans
 * @date   Jun 13, 2008
 * @since  HPF 13.1 [ROI]; Jun 13, 2008
 */
public class Address
implements Serializable {

    private long   _id;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _postalCode;
    private String _state;
    private String _countryName;
    private String _countryCode;
    private long   _createdBy;
    private long   _modifiedBy;
    private Date   _modifiedDate;
    private int    _recordVersion;
    private boolean _newCountry;
    private Long _countrySeq;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getAddress1() { return _address1; }
    @ValidationParams (
                       pattern = ROIConstants.ALLOW_ALL,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_MAIN_ADDRESS,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.ADDRESS1_LENGTH_EXCEEDS_LIMIT)
    public void setAddress1(String address1) { _address1 = address1; }

    public String getAddress2() { return _address2;  }
    @ValidationParams (
                       pattern = ROIConstants.ALLOW_ALL,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_MAIN_ADDRESS,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.ADDRESS2_LENGTH_EXCEEDS_LIMIT)
    public void setAddress2(String address2) { _address2 = address2; }

    public String getAddress3() { return _address3; }
    @ValidationParams (
                       pattern = ROIConstants.ALLOW_ALL,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_MAIN_ADDRESS,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.ADDRESS3_LENGTH_EXCEEDS_LIMIT)
    public void setAddress3(String address3) { _address3 = address3; }

    public String getCity() { return _city; }
    @ValidationParams (
                       pattern = ROIConstants.CITY,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_CITY_NAME,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.CITY_LENGTH_EXCEEDS_LIMIT)
    public void setCity(String city) { _city = city; }

    public String getPostalCode() { return _postalCode;  }
    /* @ValidationParams (
                       pattern = ROIConstants.ZIP,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_ZIP_CODE,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.POSTALCODE_LENGTH_EXCEEDS_LIMIT) */
    public void setPostalCode(String code) {  _postalCode = code;  }

    public String getState() { return _state; }
    @ValidationParams (
                       pattern = ROIConstants.CITY,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_STATE_NAME,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.STATE_LENGTH_EXCEEDS_LIMIT)
    public void setState(String state) { _state = state; }

    public String getCountryName() { return _countryName; }
    public void setCountryName(String countryName) { _countryName = countryName; }

    public String getCountryCode() { return _countryCode; }
    public void setCountryCode(String countryCode) { _countryCode = countryCode; }

    public boolean isNewCountry() {
        return _newCountry;
    }
    public void setNewCountry(boolean newCountry) {
        this._newCountry = newCountry;
    }
    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate;  }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) {  _recordVersion = version; }

    public Long getCountrySeq() { return _countrySeq; }
    public void setCountrySeq(Long countrySeq) {  _countrySeq = countrySeq; }

    @Override
    public String toString() {
        return new StringBuffer().append("Address1:")
                                 .append(_address1)
                                 .append(" Address2:")
                                 .append(_address2)
                                 .append(" Address3:")
                                 .append(_address3)
                                 .append(" City:")
                                 .append(_city)
                                 .append(" State:")
                                 .append(_state)
                                 .append(" PostalCode:")
                                 .append(_postalCode)
                                 .append(" Country:")
                                 .append(_countryName)
                                 .toString();
    }

}
