package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceOrPrebillAndPreviewInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceOrPrebillAndPreviewInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestCoreId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceDueDays" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterTemplateFileId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterTemplateName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoicePrebillDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="invoiceDueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="resendDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="overwriteDueDate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceSalesTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="baseCharge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceBillingLocCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceBillinglocName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceBalanceDue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="amountpaid" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="letterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="willInvoiceShipped" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="autoAdjustments" type="{urn:eig.mckesson.com}RequestCoreDeliveryChargesAdjustmentPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceOrPrebillAndPreviewInfo", propOrder = {
    "_requestCoreId",
    "_type",
    "_invoiceDueDays",
    "_letterTemplateFileId",
    "_letterTemplateName",
    "_requestStatus",
    "_requestDate",
    "_invoicePrebillDate",
    "_invoiceDueDate",
    "_resendDate",
    "_outputMethod",
    "_queuePassword",
    "_overwriteDueDate",
    "_invoiceSalesTax",
    "_baseCharge",
    "_invoiceBillingLocCode",
    "_invoiceBillinglocName",
    "_invoiceBalanceDue",
    "_amountpaid",
    "_notes",
    "_letterType",
    "_willInvoiceShipped",
    "_autoAdjustments"
})
public class InvoiceOrPrebillAndPreviewInfo
implements Serializable {

    private static final long serialVersionUID = 1L;

    //request Id
    @XmlElement(name="requestCoreId")
    private long _requestCoreId;
    
  //type RTF or PDF
    @XmlElement(name="type", required = true)
    private String _type;

    // invoiceduedays
    @XmlElement(name="invoiceDueDays")
    private long _invoiceDueDays;

    @XmlElement(name="letterTemplateFileId")
    private long _letterTemplateFileId;
    
    @XmlElement(name="letterTemplateName", required = true)
    private String _letterTemplateName;
    
    @XmlElement(name="requestStatus", required = true)
    private String _requestStatus;
    
    @XmlElement(name="requestDate", required = true)
    private Date _requestDate;
    
    @XmlElement(name="invoicePrebillDate", required = true)
    private Date _invoicePrebillDate;
    
    @XmlElement(name="invoiceDueDate", required = true)
    private Date _invoiceDueDate;
    
    @XmlElement(name="resendDate", required = true)
    private Date _resendDate;
    
    @XmlElement(name="outputMethod", required = true)
    private String _outputMethod;
    
    @XmlElement(name="queuePassword", required = true)
    private SecureStringAccessor _queuePassword;
    
    @XmlElement(name="overwriteDueDate")
    private boolean _overwriteDueDate;
    
    @XmlElement(name="invoiceSalesTax")
    private double _invoiceSalesTax;
    
    @XmlElement(name="baseCharge")
    private double _baseCharge;
    
    @XmlElement(name="invoiceBillingLocCode", required = true)
    private String _invoiceBillingLocCode;
    
    @XmlElement(name="invoiceBillinglocName", required = true)
    private String _invoiceBillinglocName;
    
    @XmlElement(name="invoiceBalanceDue")
    private double _invoiceBalanceDue;
    
    @XmlElement(name="amountpaid")
    private double _amountpaid;
    
    @XmlElement(name="notes")
    private String[] _notes;
    
    @XmlElement(name="letterType", required = true)
    private String _letterType;
    
    @XmlTransient
    private String _prebillStatus;
    
    @XmlElement(name="willInvoiceShipped")
    private boolean _willInvoiceShipped;
    
    @XmlElement(name="autoAdjustments")
    private List<RequestCoreDeliveryChargesAdjustmentPayment> _autoAdjustments;

    
    
    @XmlTransient
    private double _paymentAmount;
    
    @XmlTransient
    private double _adjustmentAmount;
    
    @XmlTransient
    private boolean _isReleased;
    
    @XmlTransient
    private Date _releasedDate;

    /*property defined for applying unapplied amount to the invoice */
    @XmlTransient
    private boolean _applyUnappliedAmount;
    
    @XmlTransient
    private double _amountToApply;
    
    
    
    

    public String getLetterType() { return _letterType; }
    public void setLetterType(String letterType) { _letterType = letterType; }

    public String[] getNotes() { return _notes; }
    public void setNotes(String[] notes) { _notes = notes; }

    public String getPrebillStatus() { return _prebillStatus; }
    public void setPrebillStatus(String prebillStatus) { _prebillStatus = prebillStatus; }

    public double getInvoiceSalesTax() { return _invoiceSalesTax; }
    public void setInvoiceSalesTax(double invoiceSalesTax) { _invoiceSalesTax = invoiceSalesTax; }

    public double getBaseCharge() { return _baseCharge; }
    public void setBaseCharge(double baseCharge) { _baseCharge = baseCharge; }

    public String getInvoiceBillingLocCode() { return _invoiceBillingLocCode; }
    public void setInvoiceBillingLocCode(String invoiceBillingLocCode) {
        _invoiceBillingLocCode = invoiceBillingLocCode;
    }

    public String getInvoiceBillinglocName() { return _invoiceBillinglocName; }
    public void setInvoiceBillinglocName(String invoiceBillinglocName) {
        _invoiceBillinglocName = invoiceBillinglocName;
    }

    public double getInvoiceBalanceDue() { return _invoiceBalanceDue; }
    public void setInvoiceBalanceDue(double invoiceBalanceDue) {
        _invoiceBalanceDue = invoiceBalanceDue;
    }

    public double getAmountpaid() { return _amountpaid; }
    public void setAmountpaid(double amountpaid) { _amountpaid = amountpaid; }

    public String getLetterTemplateName() { return _letterTemplateName; }
    public void setLetterTemplateName(String letterTemplateName) {
        _letterTemplateName = letterTemplateName;
    }

    public String getRequestStatus() { return _requestStatus; }
    public void setRequestStatus(String requestStatus) { _requestStatus = requestStatus; }

    public Date getRequestDate() { return _requestDate; }
    public void setRequestDate(Date requestDate) { _requestDate = requestDate; }

    public long getLetterTemplateFileId() { return _letterTemplateFileId; }
    public void setLetterTemplateFileId(long letterTemplateFileId) {
        _letterTemplateFileId = letterTemplateFileId;
    }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public Date getInvoicePrebillDate() { return _invoicePrebillDate; }
    public void setInvoicePrebillDate(Date invoicePrebillDate) {
        _invoicePrebillDate = invoicePrebillDate;
    }

    public Date getInvoiceDueDate() { return _invoiceDueDate; }
    public void setInvoiceDueDate(Date invoiceDueDate) { _invoiceDueDate = invoiceDueDate; }

    public Date getResendDate() { return _resendDate; }
    public void setResendDate(Date resendDate) { _resendDate = resendDate; }

    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }

    public boolean getOverwriteDueDate() { return _overwriteDueDate; }
    public void setOverwriteDueDate(boolean overwriteDueDate) {
        _overwriteDueDate = overwriteDueDate;
    }

    public String getQueuePassword() {
        if (_queuePassword == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }

    public long getRequestCoreId() { return _requestCoreId; }
    public void setRequestCoreId(long requestCoreId) { _requestCoreId = requestCoreId; }

    public long getInvoiceDueDays() { return _invoiceDueDays; }
    public void setInvoiceDueDays(long invoicedueDays) { _invoiceDueDays = invoicedueDays; }

    public List<RequestCoreDeliveryChargesAdjustmentPayment> getAutoAdjustments() {
        return _autoAdjustments;
    }
    public void setAutoAdjustments(List<RequestCoreDeliveryChargesAdjustmentPayment> autoAdjpays) {
        _autoAdjustments = autoAdjpays;
    }

    public boolean isApplyUnappliedAmount() { return _applyUnappliedAmount; }
    public void setApplyUnappliedAmount(boolean applyUnappliedAmount) {
        _applyUnappliedAmount = applyUnappliedAmount;
    }

    public double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(double paymentAmount) { _paymentAmount = paymentAmount; }

    public double getAdjustmentAmount() { return _adjustmentAmount; }
    public void setAdjustmentAmount(double adjustmentAmount) {
        _adjustmentAmount = adjustmentAmount;
    }

    public boolean isReleased() { return _isReleased; }
    public void setReleased(boolean isReleased) { _isReleased = isReleased; }

    public Date getReleasedDate() { return _releasedDate; }
    public void setReleasedDate(Date releasedDate) { _releasedDate = releasedDate; }

    public double getAmountToApply() { return _amountToApply; }
    public void setAmountToApply(double amountToApply) { _amountToApply = amountToApply; }
    
    public boolean isWillInvoiceShipped() {
        return _willInvoiceShipped;
    }
    public void setWillInvoiceShipped(boolean willInvoiceShipped) {
        _willInvoiceShipped = willInvoiceShipped;
    }

}
