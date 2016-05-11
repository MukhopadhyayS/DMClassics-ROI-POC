package com.mckesson.eig.roi.billing.model;

import java.sql.Timestamp;

public class PrebillReportDetails {
    
    private String facility;
    private String requestorType;
    private String requestorName;
    private String requestorPhone;
    private String requestId;
    private String prebillNumber;
    private Timestamp prebillDate;
    private double prebillAmount;
    private String aging;
    
    public String getFacility() {
        return facility;
    }
    public void setFacility(String facility) {
        this.facility = facility;
    }
    public String getRequestorType() {
        return requestorType;
    }
    public void setRequestorType(String requestorType) {
        this.requestorType = requestorType;
    }
    public String getRequestorName() {
        return requestorName;
    }
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
    public String getRequestorPhone() {
        return requestorPhone;
    }
    public void setRequestorPhone(String requestorPhone) {
        this.requestorPhone = requestorPhone;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getPrebillNumber() {
        return prebillNumber;
    }
    public void setPrebillNumber(String prebillNumber) {
        this.prebillNumber = prebillNumber;
    }
    public Timestamp getPrebillDate() {
        return prebillDate;
    }
    public void setPrebillDate(Timestamp prebillDate) {
        this.prebillDate = prebillDate;
    }
    public double getPrebillAmount() {
        return prebillAmount;
    }
    public void setPrebillAmount(double prebillAmount) {
        this.prebillAmount = prebillAmount;
    }
    public String getAging() {
        return aging;
    }
    public void setAging(String aging) {
        this.aging = aging;
    }
}
