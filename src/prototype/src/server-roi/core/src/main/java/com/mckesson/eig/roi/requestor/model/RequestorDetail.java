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


/**
 * @author ranjithr
 * @date   Jun 17, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class RequestorDetail
implements Serializable {

    private long _id;
    private long _requestorId;
    private String _systemColumn;
    private String _value;
    private String _system;
    private String _systemDb;
    private String _systemTable;
    private String _systemType;
    private String _reason;
    private long _createdBy;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;

    public RequestorDetail() {
    }

    public RequestorDetail(String type) {
        setSystemColumn(type);
    }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long id) { _requestorId = id; }

    public String getSystemColumn() { return _systemColumn; }
    public void setSystemColumn(String column) { _systemColumn = column; }

    public String getValue() { return _value; }
    public void setValue(String value) { _value = value; }

    public String getSystem() { return _system; }
    public void setSystem(String system) { _system = system; }

    public String getSystemDb() { return _systemDb; }
    public void setSystemDb(String db) { _systemDb = db; }

    public String getSystemTable() { return _systemTable; }
    public void setSystemTable(String table) { _systemTable = table; }

    public String getSystemType() { return _systemType; }
    public void setSystemType(String type) { _systemType = type; }

    public String getReason() { return _reason; }
    public void setReason(String reason) { _reason = reason; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }
}
