package com.mckesson.eig.roi.supplementary.model;

import java.io.File;
import java.util.Date;

import com.mckesson.eig.roi.base.model.BaseModel;

public class ROIAttachmentCommon 
extends BaseModel {
    
    private static final long serialVersionUID = 1L;

    private static final int TWO = 2;
    private static final int FIVE = 5;
    private static final int EIGHT = 8;

    private String _type;
    private String _encounter;
    private String _docFacility;
    private String _freeformfacility;
    private String _subtitle;
    private String _isDeleted;
    private String _pageCount;
    private Date _dateOfService;
    private Date _attachmentDate;
    private String _uuid;
    private String _volume;
    private String _path;
    private String _filename;
    private String _filetype;
    private String _fileext;
    private String _printable;
    private String _submittedBy;
    private String _comment;
    private String _externalSource;
    private String _facility;

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getEncounter() {
        return _encounter;
    }

    public void setEncounter(String encounter) {
        _encounter = encounter;
    }

    public String getDocFacility() {
        return _docFacility;
    }

    public void setDocFacility(String docFacility) {
        _docFacility = docFacility;
    }

    public String getFreeformfacility() {
        return _freeformfacility;
    }

    public void setFreeformfacility(String freeformfacility) {
        _freeformfacility = freeformfacility;
    }

    public String getSubtitle() {
        return _subtitle;
    }

    public void setSubtitle(String subtitle) {
        _subtitle = subtitle;
    }

    public String getIsDeleted() {
        return _isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        _isDeleted = isDeleted;
    }

    public String getPageCount() {
        return _pageCount;
    }

    public void setPageCount(String pageCount) {
        _pageCount = pageCount;
    }

    public Date getDateOfService() {
        return _dateOfService;
    }

    public void setDateOfService(Date dateOfService) {
        _dateOfService = dateOfService;
    }

    public Date getAttachmentDate() {
        return _attachmentDate;
    }

    public void setAttachmentDate(Date attachmentDate) {
        _attachmentDate = attachmentDate;
    }

    public String getUuid() {
        return _uuid;
    }

    public void setUuid(String uuid) {
        _uuid = uuid;
    }

    public String getVolume() {
        return _volume;
    }

    public void setVolume(String volume) {
        _volume = volume;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        _path = path;
    }

    public String getFilename() {
        return _filename;
    }

    public void setFilename(String filename) {
        _filename = filename;
    }

    public String getFiletype() {
        return _filetype;
    }

    public void setFiletype(String filetype) {
        _filetype = filetype;
    }

    public String getFileext() {
        return _fileext;
    }

    public String getExternalSource() {
        return _externalSource;
    }

    public void setExternalSource(String externalSource) {
        _externalSource = externalSource;
    }

    public void setFileext(String fileext) {
        _fileext = fileext;
    }

    public String getPrintable() {
        return _printable;
    }

    public void setPrintable(String printable) {
        _printable = printable;
    }

    public String getSubmittedBy() {
        return _submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        _submittedBy = submittedBy;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String comment) {
        _comment = comment;
    }

    public String getFacility() {
        return _facility;
    }

    public void setFacility(String facility) {
            _facility = facility;
    }
    
    public void setPathForUuid(String uuid) {
        String path = "";
        if (uuid.length() >= TWO) {
            path += uuid.substring(0, TWO) + File.separator;
        }
        if (uuid.length() >= FIVE) {
            path += uuid.substring(TWO, FIVE) + File.separator;
        }
        if (uuid.length() >= EIGHT) {
            path += uuid.substring(FIVE, EIGHT) + File.separator;
        }
        setPath(path);
    }

    public void copy(ROIAttachmentCommon common) {
        setType(common.getType());
        setEncounter(common.getEncounter());
        setDocFacility(common.getDocFacility());
        setFreeformfacility(common.getFreeformfacility());
        setSubtitle(common.getSubtitle());
        setPageCount(common.getPageCount());
        setDateOfService(common.getDateOfService());
        setComment(common.getComment());
        setExternalSource(common.getExternalSource());
    }

}
