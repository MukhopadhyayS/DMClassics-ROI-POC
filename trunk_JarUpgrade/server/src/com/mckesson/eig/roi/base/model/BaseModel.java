package com.mckesson.eig.roi.base.model;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseModel implements Serializable {
    private long _id;
    private long _createdBy;
    private long _modifiedBy;
    private Date _createdDt;
    private Date _modifiedDt;
    private int _recordVersion; 

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(long createdBy) {
        _createdBy = createdBy;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
}
