package com.mckesson.eig.roi.requestor.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestorAdjustmentsPayments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorAdjustmentsPayments">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="appliedAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="txnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="unAppliedAmt" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorAdjustmentsPayments", propOrder = {
    "_id",
    "_date",
    "_paymentAmount",
    "_appliedAmount",
    "_txnType",
    "_description",
    "_paymentMethod",
    "_invoiceId",
    "_unAppliedAmt"
})
public class RequestorAdjustmentsPayments {

    @XmlElement(name="id")
    private Long _id;
    
    @XmlElement(name="date", required = true)
    private Date _date;
    
    @XmlElement(name="paymentAmount")
    private Double _paymentAmount;
    
    @XmlElement(name="txnType", required = true)
    private String _txnType;
    
    @XmlElement(name="invoiceId")
    private long _invoiceId;
    
    @XmlElement(name="unAppliedAmt")
    private Double _unAppliedAmt;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    @XmlElement(name="paymentMethod", required = true)
    private String _paymentMethod;
    
    @XmlElement(name="appliedAmount")
    private Double _appliedAmount;
    
    @XmlTransient
    private Double _refundAmount;
    
    @XmlTransient
    private Double _amount; 
    
    @XmlTransient
    private long _requestorId;
    
    @XmlTransient
    private boolean _prebillPaymentsAdjustments;
    
    
    

    public Double getAmount() {
        return _amount;
    }
    public void setAmount(Double amount) {
        this._amount = amount;
    }
    public Long getId() { return _id; }
    public void setId(Long id) { _id = id; }

    public Date getDate() { return _date; }
    public void setDate(Date date) { _date = date; }

    public String getTxnType() { return _txnType; }
    public void setTxnType(String txnType) { _txnType = txnType; }

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }

    public Double getUnAppliedAmt() { return _unAppliedAmt; }
    public void setUnAppliedAmt(Double unAppliedAmt) { _unAppliedAmt = unAppliedAmt; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public String getPaymentMethod() { return _paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { _paymentMethod = paymentMethod; }

    public Double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(Double paymentAmount) { _paymentAmount = paymentAmount; }

    public Double getAppliedAmount() { return _appliedAmount; }
    public void setAppliedAmount(Double appliedAmount) { _appliedAmount = appliedAmount; }

    public Double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(Double refundAmount) { _refundAmount = refundAmount; }
    
    public long getRequestorId() { return _requestorId; }
    public void setRequestorId(long requestorId) { _requestorId = requestorId; }
    
    public boolean isPrebillPaymentsAdjustments() {
        return _prebillPaymentsAdjustments;
    }
    public void setPrebillPaymentsAdjustments(boolean prebillPaymentsAdjustments) {
        _prebillPaymentsAdjustments = prebillPaymentsAdjustments;
    }
    
    @Override
    public String toString() {
        return new StringBuffer()
                    .append("Transaction Type:")
                    .append(_txnType)
                    .append(", TransactionId:")
                    .append(_id)
                    .toString();
    }

}
