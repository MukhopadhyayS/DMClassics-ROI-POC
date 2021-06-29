package com.mckesson.eig.roi.admin.model;

import java.io.Serializable;
import java.util.Date;

public class MUDocTypeModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int _id;
    private Date _createdDate;
    private int _createdBy;
    private Date _modifiedDate;
    private int _modifiedBy;
    private int _recordVersion;
    private String _muDocType;
    private String _muDocCode;
    private String _description;

    public Date getCreatedDate() {
        return _createdDate;
    }
    public void setCreatedDate(Date date) {
        _createdDate = date;
    }
    public int getCreatedBy() {
        return _createdBy;
    }
    public void setCreatedBy(int by) {
        _createdBy = by;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String _description) {
        this._description = _description;
    }

  
   
   
    
    
    public int getModifiedBy() {
        return _modifiedBy;
    }
    public void setModifiedBy(int by) {
        _modifiedBy = by;
    }
    public Date getModifiedDate() {
        return _modifiedDate;
    }
    public void setModifiedDate(Date date) {
        _modifiedDate = date;
    }
    public String getMuDocCode() {
        return _muDocCode;
    }
    public void setMuDocCode(String docCode) {
        _muDocCode = docCode;
    }
    public String getMuDocType() {
        return _muDocType;
    }
    public void setMuDocType(String docType) {
        _muDocType = docType;
    }
    public int getRecordVersion() {
        return _recordVersion;
    }
    public void setRecordVersion(int version) {
        _recordVersion = version;
    }
    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }

}
