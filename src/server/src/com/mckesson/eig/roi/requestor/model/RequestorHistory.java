package com.mckesson.eig.roi.requestor.model;

import java.io.Serializable;
import java.util.Date;

import com.mckesson.dm.core.common.util.StringUtilities;
import com.mckesson.eig.roi.utils.SecureStringAccessor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestorHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="template" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceBalance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="aging" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prebillStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="migrated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorHistory", propOrder = {
    "id",
    "type",
    "creatorName",
    "createdDate",
    "template",
    "requestPassword",
    "queuePassword",
    "invoiceDueDate",
    "balance",
    "invoiceBalance",
    "aging",
    "status",
    "prebillStatus",
    "requestId",
    "migrated"
})
public class RequestorHistory implements Serializable{
    
   
    private static final long serialVersionUID = -5069710018550691164L;
    private long id;
    
    @XmlElement(required = true)
    private String type;
    
    @XmlElement(required = true)
    private String creatorName;
    
    @XmlElement(required = true)
    private String createdDate;
    
    @XmlElement(required = true)
    private String template;
    
    @XmlElement(required = true)
    private SecureStringAccessor requestPassword;
    
    @XmlElement(required = true)
    private SecureStringAccessor queuePassword;
    
    @XmlElement(required = true)
    private String invoiceDueDate;
    
    private double balance;
    
    private double invoiceBalance;
    
    @XmlElement(required = true)
    private String aging;
    
    @XmlElement(required = true)
    private String status;
    
    @XmlElement(required = true)
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
        if (requestPassword == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        requestPassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setRequestPassword(String requestPassword) {
        requestPassword = StringUtilities.safe(requestPassword);
        this.requestPassword = new SecureStringAccessor(requestPassword.toCharArray());
    }
    public String getQueuePassword() {
        if (queuePassword == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        this.queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
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
