package com.mckesson.eig.roi.requestor.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
 * <p>Java class for RequestorInvoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorInvoice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="invoiceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="charge" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="adjustmentPaymentTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="invoiceStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unBillableAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="paymentDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paymentMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestorAdjPay" type="{urn:eig.mckesson.com}RequestorAdjustmentsPayments" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="modifiedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="createdDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="modifiedDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorInvoice", propOrder = {
    "_requestId",
    "_invoiceType",
    "_description",
    "_charge",
    "_adjustmentAmount",
    "_paymentAmount",
    "_adjustmentPaymentTotal",
    "_balance",
    "_invoiceStatus",
    "_unBillableAmount",
    "_unbillable",
    "_paymentDescription",
    "_paymentMethod",
    "_requestorAdjPay",
    "_id",
    "_createdBy",
    "_modifiedBy",
    "_createdDt",
    "_modifiedDt",
    "_recordVersion",
    "_billingLocation",
    "_appliedAmount",
    "_applyAmount",
    "_refundAmount"
})
public class RequestorInvoice
extends BaseModel {

    private static final long serialVersionUID = 1L;

    @XmlElement(name="requestId")
    private Long _requestId;
    
    @XmlElement(name="invoiceType", required = true)
    private String _invoiceType;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    @XmlElement(name="charge")
    private Double _charge;
    
    @XmlElement(name="adjustmentAmount")
    private Double _adjustmentAmount;
    
    @XmlElement(name="paymentAmount")
    private Double _paymentAmount;
    
    @XmlElement(name="adjustmentPaymentTotal")
    private Double _adjustmentPaymentTotal;
    
    @XmlElement(name="balance")
    private Double _balance;
    
    @XmlElement(name="invoiceStatus", required = true)
    private String _invoiceStatus;
    
    @XmlElement(name="unBillableAmount")
    private Double _unBillableAmount;
    
    @XmlElement(name="appliedAmount")
    private Double _appliedAmount;
    
    @XmlElement(name="applyAmount")
    private Double _applyAmount;
    
    @XmlElement(name="refundAmount")
    private Double _refundAmount;

    @XmlElement(name="paymentDescription", required = true)
    private String _paymentDescription;
    
    @XmlElement(name="paymentMethod", required = true)
    private String _paymentMethod;

    @XmlTransient
    private List<String> _facility;
    
    @XmlElement(name="unbillable")
    private String _unbillable;
    
    @XmlElement(name="billingLocation")
    private String _billingLocation;
    
    @XmlElement(name="requestorAdjPay")
    private List<RequestorAdjustmentsPayments> _requestorAdjPay;
    
    
    
    @XmlElement(name="invoiceId")
    private long _id;
    
    @XmlElement(name="createdBy")
    private long _createdBy;
    
    @XmlElement(name="modifiedBy")
    private long _modifiedBy;
    
    @XmlElement(name="createdDt", required = true)
    private Date _createdDt;
    
    @XmlElement(name="modifiedDt", required = true)
    private Date _modifiedDt;
    
    @XmlElement(name="recordVersion")
    private int _recordVersion; 


    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(long createdBy) {
        _createdBy = createdBy;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }

    public void setModifiedBy(long modifiedBy) {
        _modifiedBy = modifiedBy;
    }

    public Date getCreatedDt() {
        return _createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        _createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return _modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        _modifiedDt = modifiedDt;
    }

    public int getRecordVersion() {
        return _recordVersion;
    }

    public void setRecordVersion(int recordVersion) {
        _recordVersion = recordVersion;
    }
    
    

    public String getUnbillable() {
        return _unbillable;
    }
    public void setUnbillable(String unbillable) {
        this._unbillable = unbillable;
    }

    public Long getRequestId() { return _requestId; }
    public void setRequestId(Long requestId) { _requestId = requestId; }

    public String getInvoiceType() { return _invoiceType; }
    public void setInvoiceType(String invoiceType) { _invoiceType = invoiceType; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public Double getCharge() { return _charge; }
    public void setCharge(Double charge) { _charge = charge; }

    public Double getAdjustmentAmount() { return _adjustmentAmount; }
    public void setAdjustmentAmount(Double adjustmentAmount) {
        _adjustmentAmount = adjustmentAmount;
    }

    public Double getPaymentAmount() { return _paymentAmount; }
    public void setPaymentAmount(Double paymentAmount) { _paymentAmount = paymentAmount; }

    public Double getBalance() { return _balance; }
    public void setBalance(Double balance) { _balance = balance; }

    public String getInvoiceStatus() { return _invoiceStatus; }
    public void setInvoiceStatus(String invoiceStatus) { _invoiceStatus = invoiceStatus; }

    public Double getUnBillableAmount() { return _unBillableAmount; }
    public void setUnBillableAmount(Double unBillableAmount) {
        _unBillableAmount = unBillableAmount;
    }

    public Double getAppliedAmount() {
        return _appliedAmount;
    }
    public void setAppliedAmount(Double appliedAmount) {
        this._appliedAmount = appliedAmount;
    }

    public List<RequestorAdjustmentsPayments> getRequestorAdjPay() { return _requestorAdjPay; }
    public void setRequestorAdjPay(List<RequestorAdjustmentsPayments> requestorAdjPay) {
        _requestorAdjPay = requestorAdjPay;
    }

    public Double getAdjustmentPaymentTotal() {

        if (_adjustmentAmount == null) {

            _adjustmentPaymentTotal = _paymentAmount;
            return _adjustmentPaymentTotal;
        } else if (_paymentAmount == null) {

            _adjustmentPaymentTotal = _adjustmentAmount;
            return _adjustmentPaymentTotal;
        }
        _adjustmentPaymentTotal = _adjustmentAmount + _paymentAmount;
        return _adjustmentPaymentTotal;
    }

    public void setAdjustmentPaymentTotal(Double adjustmentPaymentTotal) {
        _adjustmentPaymentTotal = adjustmentPaymentTotal;
    }

    public String getPaymentDescription() { return _paymentDescription; }
    public void setPaymentDescription(String paymentDescription) {
        _paymentDescription = paymentDescription;
    }

    public String getPaymentMethod() { return _paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { _paymentMethod = paymentMethod; }

    public List<String> getFacility() { return _facility; }
    public void setFacility(List<String> facility) { _facility = facility; }

    public String getFacilityString() {

        if (null == getFacility()) {
            return null;
        }
        String string = getFacility().toString();
        return string.substring(1, string.length() - 1);
     }

    public void setFacilityString(String facility) {

        if (null == facility) {
            return;
        }
        String[] split = facility.split(ROIConstants.FIELD_DELIMITER);

        setFacility(Arrays.asList(split));
    }

    public Double getRefundAmount() { return _refundAmount; }
    public void setRefundAmount(Double refundAmount) { _refundAmount = refundAmount; }

    public String getBillingLocation() { return _billingLocation; }
    public void setBillingLocation(String billingLocation) { _billingLocation = billingLocation; }

    public Double getApplyAmount() { return _applyAmount; }
    public void setApplyAmount(Double applyAmount) { _applyAmount = applyAmount; }

    @Override
    public String toString() {
        return new StringBuffer()
                        .append("RequestId:")
                        .append(_requestId)
                        .append(", InvoiceId:")
                        .append(getId())
                        .append(", InvoiceStatus:")
                        .append(_invoiceStatus)
                        .append(". Balance:")
                        .append(_balance)
                        .toString();
    }

    /**
     * This method create audit comment for Requestor Apply Adjustment
     * @return audit comment for retrieve event
     */
    public String constructApplyAdjustmentAuditComment(long requestorId,
                                String userName, String adjType) {

        return new StringBuffer().append("A ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_applyAmount))
                .append(" adjustment from ")
                .append(adjType)
                .append(" was applied to ")
                .append(_requestId)
                .append("  using invoice ")
                .append(getId())
                .append(" for ")
                .append(requestorId)
                .append(" by ")
                .append(userName.trim())
                .append(".")
                .toString();
    }

    /**
     * This method create audit comment for Requestor Update Adjustment
     * @return audit comment for retrieve event
     */
    public String constructUpdateAdjustmentAuditComment(double oldAppliedAmt,
                    String requestorName, String requestorType, String adjType) {

        return new StringBuffer()
                .append(ROIConstants.CURRENCY_FORMAT.format(oldAppliedAmt))
                .append(" ")
                .append(adjType)
                .append(" adjustment on Request ID ")
                .append(_requestId)
                .append("-Invoice")
                .append(getId())
                .append(" was changed to ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_appliedAmount))
                .append(" for ")
                .append(requestorName)
                .append("-")
                .append(requestorType)
                .append(".")
                .toString();
    }

    /**
     * This method create Event comment for Apply Adjustment
     * @return Event comment for retrieve event
     */
    public String constructApplyAdjustmentEventComment(String adjustmentType) {

        return new StringBuffer().append(adjustmentType)
                .append(" adjustment of ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_appliedAmount))
                .append(" was applied to invoice ")
                .append(getId())
                .append(" of ")
                .append(_requestId)
                .append(".")
                .toString();
    }

   /**
    * This method create Event comment for update adjustment
    * @return audit comment for retrieve event
    */
   public String constructUpdateAdjustmentEventComment(double oldApplyAdjustment,
                           long requestId, String originator, String adjType) {

       return new StringBuffer().append(adjType)
               .append(" adjustment of ")
               .append(ROIConstants.CURRENCY_FORMAT.format(oldApplyAdjustment))
               .append(" was changed to ")
               .append(ROIConstants.CURRENCY_FORMAT.format(_appliedAmount))
               .append(" on invoice ")
               .append(getId())
               .append(" of ")
               .append(requestId)
               .append(" by ")
               .append(StringUtilities.safeTrim(originator))
               .append(".")
               .toString();
   }

}
