package com.mckesson.eig.roi.requestor.model;

import java.util.Arrays;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.util.StringUtilities;

public class RequestorInvoice
extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Long _requestId;
    private String _invoiceType;
    private String _description;
    private Double _charge;
    private Double _adjustmentAmount;
    private Double _paymentAmount;
    private Double _adjustmentPaymentTotal;
    private Double _balance;
    private String _invoiceStatus;
    private Double _unBillableAmount;
    private Double _appliedAmount;
    private Double _applyAmount;
    private Double _refundAmount;

    private String _paymentDescription;
    private String _paymentMethod;

    private List<String> _facility;
    private String _unbillable;
    private String _billingLocation;

    private List<RequestorAdjustmentsPayments> _requestorAdjPay;

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
