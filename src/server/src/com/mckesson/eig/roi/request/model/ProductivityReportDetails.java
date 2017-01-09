package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ProductivityReportDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    private String reqID;
    private String mrn;
    private String facility;
    private String userName;
    private String patientName;  
    private String requestorType;
    private String billable;
    private Timestamp createDate;
    private String pageType;
    private int pages;
    private int reqIDCount;
    private String requestorName;
    
    
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public int getReqIDCount() {
        return reqIDCount;
    }
    public void setReqIDCount(int reqIDCount) {
        this.reqIDCount = reqIDCount;
    }
    public String getPageType() {
        return pageType;
    }
    public void setPageType(String pageType) {
        this.pageType = pageType;
    }
    public String getReqID() {
        return reqID;
    }
    public void setReqID(String reqID) {
        this.reqID = reqID;
    }
    public String getBillable() {
        return billable;
    }
    public void setBillable(String billable) {
        this.billable = billable;
    }
    public String getMrn() {
        return mrn;
    }
    public void setMrn(String mrn) {
        this.mrn = mrn;
    }
    public String getFacility() {
        return facility;
    }
    public void setFacility(String facility) {
        this.facility = facility;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getRequestorType() {
        return requestorType;
    }
    public void setRequestorType(String requestorType) {
        this.requestorType = requestorType;
    }
  
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getRequestorName() {
        return requestorName;
    }
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
   
   
   }
