package com.mckesson.eig.roi.supplementary.model;

import java.io.File;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.mckesson.eig.roi.base.model.BaseModel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class ROIAttachmentCommon 
extends BaseModel {
    
    private static final long serialVersionUID = 1L;

    private static final int TWO = 2;
    private static final int FIVE = 5;
    private static final int EIGHT = 8;

    @XmlElement(name="type")
    private String _type;
    
    @XmlElement(name="encounter")
    private String _encounter;
    
    @XmlElement(name="docFacility")
    private String _docFacility;
    
    @XmlTransient
    private String _freeformfacility;
    
    @XmlElement(name="subtitle")
    private String _subtitle;
    
    @XmlElement(name="isDeleted")
    private String _isDeleted;
    
    @XmlElement(name="pageCount")
    private String _pageCount;
    
    @XmlElement(name="dateOfService",nillable = true)
    private Date _dateOfService;
    
    @XmlElement(name="attachmentDate",nillable = true)
    private Date _attachmentDate;
    
    @XmlElement(name="uuid")
    private String _uuid;
    
    @XmlElement(name="volume")
    private String _volume;
    
    @XmlElement(name="path")
    private String _path;
    
    @XmlElement(name="filename")
    private String _filename;
    
    @XmlElement(name="filetype")
    private String _filetype;
    
    @XmlElement(name="fileext")
    private String _fileext;
    
    @XmlElement(name="printable")
    private String _printable;
    
    @XmlElement(name="submittedBy")
    private String _submittedBy;
    
    @XmlElement(name="comment")
    private String _comment;
    
    @XmlElement(name="externalSource")
    private String _externalSource;
    
    @XmlTransient
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
