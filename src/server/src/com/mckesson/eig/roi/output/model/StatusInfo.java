/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.model;

import java.util.Date;
import java.util.List;

/**
 * This class contains the Status Information
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class StatusInfo {
    
    /** This holds the status code*/
    private int _statusCode;
    /** This holds the status*/
    private String _status;
    /** This holds the detail*/
    private String _detail;
    /** This holds the status date*/
    private Date _statusDate;
    /** This holds the status source*/
    private String _statusSource;
    /** This holds the list of status data*/
    private List<MapModel> _statusData;

    public int getStatusCode() {
        return _statusCode;
    }

    public void setStatusCode(int statusCode) {
        _statusCode = statusCode;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String status) {
        _status = status;
    }

    public String getDetail() {
        return _detail;
    }

    public void setDetail(String detail) {
        _detail = detail;
    }

    public Date getStatusDate() {
        return _statusDate;
    }

    public void setStatusDate(Date statusDate) {
        _statusDate = statusDate;
    }

    public String getStatusSource() {
        return _statusSource;
    }

    public void setStatusSource(String statusSource) {
        _statusSource = statusSource;
    }

    public List<MapModel> getStatusData() {
        return _statusData;
    }

    public void setStatusData(List<MapModel> statusData) {
        _statusData = statusData;
    }
    
}
