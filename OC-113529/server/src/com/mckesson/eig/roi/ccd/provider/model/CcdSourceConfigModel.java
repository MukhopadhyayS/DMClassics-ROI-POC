package com.mckesson.eig.roi.ccd.provider.model;

import java.io.Serializable;
import java.util.Date;

public class CcdSourceConfigModel implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int _id;
    private int _sourceId;
    private String _configKey;
    private String _configValue;
    private String _description;
    private int _createdBy;
    private int _modifiedBy;
    private Date _createdDate;
    private Date _modifiedDate;
    private int _recordVersion;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getSourceId() {
        return _sourceId;
    }

    public void setSourceId(int extId) {
        _sourceId = extId;
    }

    public String getConfigKey() {
        return _configKey;
    }

    public void setConfigKey(String configKey) {
        _configKey = configKey;
    }

    public String getConfigValue() {
        return _configValue;
    }

    public void setConfigValue(String configValue) {
        _configValue = configValue;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }
    public int getCreatedBy() {
        return _createdBy;
    }
    public void setCreatedBy(int createdBy) {
        _createdBy = createdBy;
    }

    public int getModifiedBy() {
        return _modifiedBy;
    }
    public void setModifiedBy(int modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDate() {
        return _createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        _createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return _modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate) {
        _modifiedDate = modifiedDate;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }
    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }

}
