package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;
import java.util.Date;

public class RequestorHistory implements Serializable{
    
   
    private static final long serialVersionUID = -5069710018550691164L;
    private long id;
    private String type;
    private String creatorName;
    private String createdDate;
    private String template;
    private String requestPassword;
    private String queuePassword;
    private String invoiceDueDate;
    private double balance;
    private double invoiceBalance;
    private String aging;
    private String status;
    private String prebillStatus;
    private long requestId;
    private boolean migrated;
    
     
    public long getRequestId() {
        return requestId;
    }
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
    public double getInvoiceBalance() {
        return invoiceBalance;
    }
    public void setInvoiceBalance(double invoiceBalance) {
        this.invoiceBalance = invoiceBalance;
    }
    public String getPrebillStatus() {
        return prebillStatus;
    }
    public void setPrebillStatus(String prebillStatus) {
        this.prebillStatus = prebillStatus;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }
    public String getRequestPassword() {
        return requestPassword;
    }
    public void setRequestPassword(String requestPassword) {
        this.requestPassword = requestPassword;
    }
    public String getQueuePassword() {
        return queuePassword;
    }
    public void setQueuePassword(String queuePassword) {
        this.queuePassword = queuePassword;
    }
    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }
    public void setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public String getAging() {
        return aging;
    }
    public void setAging(String aging) {
        this.aging = aging;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isMigrated() {
        return migrated;
    }
    public void setMigrated(boolean migrated) {
        this.migrated = migrated;
    }
}
