package com.mckesson.eig.roi.billing.model;

import java.sql.Timestamp;


public class PostPaymentReportDetails {
    
    private String userName;
    private String requestorType;
    private String requestorName;
    private String requestId;
    private String mrn;
    private String invoiceNumber;
    private String paymentMethod ;
    private String paymentDetails;
    private double paymentAmount;
    private String facility;
    private String paymentId;
    private Timestamp createdDate;
    
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMrn() {
        return mrn;
    }
    public void setMrn(String mrn) {
        this.mrn = mrn;
    }
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentDetails() {
        return paymentDetails;
    }
    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    public double getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
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
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getRequestorName() {
        return requestorName;
    }
    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }
    
    
}
