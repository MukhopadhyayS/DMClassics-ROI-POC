package com.mckesson.eig.roi.supplementary.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.mckesson.eig.roi.base.model.BaseModel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class ROIDocumentCommon extends BaseModel {
    
    @XmlElement(name="docName")
    private String _docName;
   
    @XmlElement(name="encounter")
    private String _encounter;
    
    @XmlElement(name="docFacility")
    private String _docFacility;
    
    @XmlTransient
    private String _freeformfacility;
    
    @XmlElement(name="department")
    private String _department;
    
    @XmlElement(name="subtitle")
    private String _subtitle;
    
    @XmlElement(name="pageCount")
    private String _pageCount;
    
    @XmlElement(name="dateOfService")
    private Date _dateOfService;
    
    @XmlElement(name="comment")
    private String _comment;
    

    public String getDocName() {
        return _docName;
    }

    public void setDocName(String docName) {
        _docName = docName;
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

    public String getDepartment() {
        return _department;
    }

    public void setDepartment(String department) {
        _department = department;
    }

    public String getSubtitle() {
        return _subtitle;
    }

    public void setSubtitle(String subtitle) {
        _subtitle = subtitle;
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

    public String getComment() {
        return _comment;
    }

    public void setComment(String comment) {
        _comment = comment;
    }
    
    public void copy(ROIDocumentCommon common) {
        setDocName(common.getDocName());
        setEncounter(common.getEncounter());
        setDocFacility(common.getDocFacility());
        setFreeformfacility(common.getFreeformfacility());
        setDepartment(common.getDepartment());
        setSubtitle(common.getSubtitle());
        setPageCount(common.getPageCount());
        setDateOfService(common.getDateOfService());
        setComment(common.getComment());
    }
    

}
