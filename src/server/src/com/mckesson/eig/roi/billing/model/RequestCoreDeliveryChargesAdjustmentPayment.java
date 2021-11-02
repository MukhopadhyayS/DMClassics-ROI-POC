package com.mckesson.eig.roi.billing.model;

import java.text.NumberFormat;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RequestCoreDeliveryChargesAdjustmentPayment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestCoreDeliveryChargesAdjustmentPayment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="requestCoreDeliveryChargesId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentMode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="transactionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDebit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="newlyAdded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestCoreDeliveryChargesAdjustmentPayment", propOrder = {
    "_requestorId",
    "_requestCoreDeliveryChargesId",
    "_baseAmount",
    "_paymentMode",
    "_description",
    "_paymentDate",
    "_transactionType",
    "_isDebit",
    "_isNewlyAdded"
})
public class RequestCoreDeliveryChargesAdjustmentPayment extends BaseModel  {
   
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="id")
    private long _requestorId;
    
    @XmlElement(name="requestCoreDeliveryChargesId")
    private long _requestCoreDeliveryChargesId;
    
    @XmlElement(name="amount")
    private double _baseAmount;
    
    @XmlElement(name="paymentMode", required = true)
    private String _paymentMode;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    @XmlElement(name="paymentDate", required = true, nillable = true)
    private Date _paymentDate;
    
    @XmlElement(name="transactionType", required = true)
    private String _transactionType;
    
    @XmlElement(name="isDebit")
    private boolean _isDebit;
    
    @XmlElement(name="newlyAdded")
    private boolean _isNewlyAdded;
    
    
    @XmlTransient
    private double _invoiceAppliedAmount;
    
    @XmlTransient
    private double _totalUnappliedAmount;
    
    

    private static final NumberFormat CURRENCY_FORMAT_US = NumberFormat
            .getCurrencyInstance(ROIConstants.INVOICE_LOCALE);
    
    public long getRequestorId() {
        return _requestorId;
    }
    public void setRequestorId(long requestorId) {
        _requestorId = requestorId;
    }
    public long getRequestCoreDeliveryChargesId() {
        return _requestCoreDeliveryChargesId;
    }
    public void setRequestCoreDeliveryChargesId(long requestCoreDeliveryChargesId) {
        _requestCoreDeliveryChargesId = requestCoreDeliveryChargesId;
    }
    public double getInvoiceAppliedAmount() {
        return _invoiceAppliedAmount;
    }
    public void setInvoiceAppliedAmount(double invoiceAppliedAmount) {
        _invoiceAppliedAmount = invoiceAppliedAmount;
    }
    public double getTotalUnappliedAmount() {
        return _totalUnappliedAmount;
    }
    public double getBaseAmount() {
        return _baseAmount;
    }
    public void setBaseAmount(double baseAmount) {
        _baseAmount = baseAmount;
    }
    public void setTotalUnappliedAmount(double totalUnappliedAmount) {
        _totalUnappliedAmount = totalUnappliedAmount;
    }    
    public String getPaymentMode() {
        return _paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        _paymentMode = paymentMode;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String description) {
        _description = description;
    }
    public Date getPaymentDate() {
        return _paymentDate;
    }
    public void setPaymentDate(Date paymentDate) {
        _paymentDate = paymentDate;
    }
    public String getTransactionType() {
        return _transactionType;
    }
    public void setTransactionType(String transactionType) {
        _transactionType = transactionType;
    }
    public boolean getIsDebit() {
        return _isDebit;
    }
    public void setIsDebit(boolean isDebit) {
        _isDebit = isDebit;
    }
    
    public void setNewlyAdded(boolean isNewlyAdded) { _isNewlyAdded = isNewlyAdded; }
    public boolean isNewlyAdded() { return _isNewlyAdded; }
    
    public String toCreditUpdateAudit(long invoiceId,boolean isAudit,boolean isDebit,
            Double preBal, Double currBal,Double amount,String transactionType,String paymentMode,String description) {

        StringBuffer comment = new StringBuffer();

        comment.append(toCreditAutoUpdateAudit(invoiceId, isAudit, isDebit, preBal,
                    currBal,amount,transactionType,paymentMode,description));

        return comment.toString();
    }
    
    private String toCreditAutoUpdateAudit(long invoiceId, boolean isAudit, boolean isDebit,
                                           Double preBal, Double currBal,Double amount,String transactionType,String paymentMode,String description) {
        
        StringBuffer comment = new StringBuffer();
        if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(transactionType)) {
            
            comment.append((isAudit) ? "Payment " : "");
            comment.append(formatCurrency(amount));
            comment.append(" was ");
            comment.append((isAudit) ? "automatically " : "");
            comment.append("posted to " + invoiceId);
            comment.append(". Method of payment " + paymentMode);
            comment.append(". " + getDelimitedDescription(description));
        } else if(ROIConstants.ADJUSTMENT_TYPE.equalsIgnoreCase(transactionType)){
            
            comment.append("Automatic credit adjustment of ");
            comment.append(formatCurrency(amount));  
            comment.append(" was made by the system on Invoice " + invoiceId);  
            comment.append(". Invoice # " + invoiceId);
            comment.append(" balance has been updated from " + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
            comment.append(". " + description);
        }
        else
        {
        if (isDebit) {

            comment.append("An auto \"Debit Adjustment\" of ");
            comment.append(formatCurrency(amount));
            comment.append(" on Invoice " + invoiceId);
            comment.append(" was made by the system. ");
            comment.append("Invoice # " + invoiceId);
            comment.append(" balance has been updated from "
                    + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
        } else {

            comment.append("Automatic credit adjustment of ");
            comment.append(formatCurrency(amount));
            comment.append(" was made by the system on Invoice ");
            comment.append(invoiceId);
            comment.append(". Invoice # " + invoiceId);
            comment.append(" balance has been updated from "
                    + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
        }
        }
        return comment.toString();
    }

    private String getDelimitedDescription(String description) {

        if (StringUtilities.isEmpty(description)) {
            return "";
        }
        String[] desc = getDescription().split(ROIConstants.PAYMENT_DELIMITER);
        if (desc.length > 1) {
            return desc[1];
        }
        return "";
    }
    
    
    public String toCreditUpdateAudit(long invoiceId, boolean isAudit,
                                      Double preBal, Double currBal) {
        
        StringBuffer comment = new StringBuffer();
        
        if (ROIConstants.MANUAL.equalsIgnoreCase(getPaymentMode())) {
            comment.append(toCreditManualUpdateAudit(invoiceId, isAudit));
        } else {
            comment.append(toCreditAutoUpdateAudit(invoiceId, isAudit, preBal, currBal));
        }
        
        return comment.toString();
    }
    
    public String toAutoAdjustmentComment(long invoiceId, boolean isDebit,
                                          Double preBal, Double currBal) {
        
        StringBuffer comment = new StringBuffer();
        
        // if the comment is modified, update the comment in 
        // BillingDAOImpl.deleteInvoiceAutoAdjEvent(invoiceId)
        // since the comment is considered for deletion of event when deleting Invoice/Prebill.
        if (isDebit) {
            
            comment.append("An auto \"Debit Adjustment\" of ");
            comment.append(formatCurrency(getInvoiceAppliedAmount()));
            comment.append(" on Invoice " + invoiceId);
            comment.append(" was made by the system. ");
            comment.append("Invoice # " + invoiceId);
            comment.append(" balance has been updated from " + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
        } else {
            
            comment.append("Automatic credit adjustment of ");  
            comment.append(formatCurrency(getInvoiceAppliedAmount()));
            comment.append(" was made by the system on Invoice ");
            comment.append(invoiceId);
            comment.append(". Invoice # " + invoiceId);
            comment.append(" balance has been updated from " + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
        }
        
        return comment.toString();
    }
    
    private String formatCurrency(Double amount) {

        if (null == amount) {
         amount = new Double(0.00);   
        }
        return CURRENCY_FORMAT_US.format(amount);
    }
    
    private String toCreditManualUpdateAudit(long invoiceId, boolean isAudit) {
        
        StringBuffer comment = new StringBuffer();
        
        if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(_transactionType)) {

            comment.append((isAudit) ? "Payment " : "");
            comment.append(formatCurrency(getInvoiceAppliedAmount()));
            comment.append(" was posted to " + invoiceId);
            comment.append(". Method of payment " + _paymentMode);
            comment.append(". " + getDelimitedDescription(getDescription()));
        } else {
            
            comment.append("Credit ");
            comment.append((isAudit) ? "amount " : "adjustment ");
            comment.append(formatCurrency(getInvoiceAppliedAmount()));
            comment.append(" was posted to " + invoiceId);
            comment.append(". " + getDescription());
        }
        
        return comment.toString();
    }

    private String toCreditAutoUpdateAudit(long invoiceId, boolean isAudit,
                                           Double preBal, Double currBal) {
        
        StringBuffer comment = new StringBuffer();
        if (ROIConstants.PAYMENT_TYPE.equalsIgnoreCase(_transactionType)) {
            
            comment.append((isAudit) ? "Payment " : "");
            comment.append(formatCurrency(getInvoiceAppliedAmount()));
            comment.append(" was ");
            comment.append((isAudit) ? "automatically " : "");
            comment.append("posted to " + invoiceId);
            comment.append(". Method of payment " + _paymentMode);
            comment.append(". " + getDelimitedDescription(getDescription()));
        } else {
            
            comment.append("Automatic credit adjustment of ");
            comment.append(formatCurrency(getInvoiceAppliedAmount()));  
            comment.append(" was made by the system on Invoice " + invoiceId);  
            comment.append(". Invoice # " + invoiceId);
            comment.append(" balance has been updated from " + formatCurrency(preBal));
            comment.append(" to " + formatCurrency(currBal));
            comment.append(". " + getDescription());
        }
        
        return comment.toString();
    }
    
    public String toDebitUpdateAudit(long invoiceId, boolean isAudit) {
        
        StringBuffer comment = new StringBuffer();
        comment.append("Debit "); 
        comment.append((isAudit) ? "amount " : "adjustment ");
        comment.append(formatCurrency(getInvoiceAppliedAmount()));
        comment.append(" was posted to " + invoiceId + ". ");
        comment.append(getDescription());
        
        return comment.toString();
    }    
   
}
