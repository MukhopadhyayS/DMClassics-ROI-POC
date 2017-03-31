package com.mckesson.eig.roi.ccd.provider.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CcdSourceModel implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int _id;
    private String _sourceName;
    private String _providerName;
    private String _description;
    private String _extType;
    private int _createdBy;
    private int _modifiedBy;
    private Date _createdDate;
    private Date _modifiedDate;
    private int _recordVersion;
    private List<CcdSourceConfigModel> _ccdProviderConfigModel;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getSourceName() {
        return _sourceName;
    }

    public void setSourceName(String name) {
        _sourceName = name;
    }

    public String getProviderName() {
        return _providerName;
    }

    public void setProviderName(String displayName) {
        _providerName = displayName;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getExtType() {
        return _extType;
    }

    public void setExtType(String extType) {
        _extType = extType;
    }

    public List<CcdSourceConfigModel> getCcdProviderConfigModel() {
        return _ccdProviderConfigModel;
    }

    public void setCcdProviderConfigModel(
            List<CcdSourceConfigModel> providerConfigModel) {
        _ccdProviderConfigModel = providerConfigModel;
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
