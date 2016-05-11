package com.mckesson.eig.roi.requestor.model;

import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.utility.util.StringUtilities;

public class RequestorAdjustment
extends BaseModel {

    private static final long serialVersionUID = 1L;
    private long _requestorSeq;
    private String _requestorName;
    private String _requestorType;
    private long _invoiceSeq;

    private AdjustmentType _adjustmentType;
    private Double _amount;
    private String _reason;
    private String _note;
    private Date _adjustmentDate;

    private Double _unappliedAmount;
    private Double _appliedAmount;
    private boolean _delete;

    public long getRequestorSeq() { return _requestorSeq; }
    public void setRequestorSeq(long requestorSeq) { this._requestorSeq = requestorSeq; }

    public String getReason() { return _reason; }
    public void setReason(String reason) { this._reason = reason; }

    public Double getAmount() { return _amount; }
    public void setAmount(Double amount) { this._amount = amount; }

    public Double getUnappliedAmount() { return _unappliedAmount; }
    public void setUnappliedAmount(Double unappliedAmount) {
        this._unappliedAmount = unappliedAmount;
    }

    public Date getAdjustmentDate() { return _adjustmentDate; }
    public void setAdjustmentDate(Date adjustmentDate) { this._adjustmentDate = adjustmentDate; }

    public String getNote() { return _note; }
    public void setNote(String note) { this._note = note; }

    public long getInvoiceSeq() { return _invoiceSeq; }
    public void setInvoiceSeq(long invoiceSeq) { this._invoiceSeq = invoiceSeq; }

    public Double getAppliedAmount() { return _appliedAmount; }
    public void setAppliedAmount(Double appliedAmount) { _appliedAmount = appliedAmount; }

    public String getRequestorName() {return _requestorName;}
    public void setRequestorName(String requestorName) {_requestorName = requestorName;}

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String requestorType) { _requestorType = requestorType; }

    public boolean isDelete() { return _delete; }
    public void setDelete(boolean delete) { _delete = delete; }

    public AdjustmentType getAdjustmentType() { return _adjustmentType; }
    public void setAdjustmentType(AdjustmentType adjustmentType) {
        _adjustmentType = adjustmentType;
    }

    public String getAdjustmentTypeAsString() {
        return (null == _adjustmentType) ? null : _adjustmentType.name();
    }
    public void setAdjustmentTypeAsString(String adjustmentType) {
        _adjustmentType = AdjustmentType.valueOf(adjustmentType);
    }

    public String getAdjustmentTypeByValue() {
        return (null == _adjustmentType) ? null : _adjustmentType.toString();
    }
    public void setAdjustmentTypeByValue(String adjustmentType) {

        _adjustmentType = null;
        AdjustmentType[] values = AdjustmentType.values();
        for (AdjustmentType type : values) {

            if (type.toString().equalsIgnoreCase(adjustmentType)) {

                _adjustmentType = type;
                break;
            }
        }
    }

    /**
     * This method create audit comment for Requestor Post Adjustment
     * @return audit comment for retrieve event
     */
    public String constructPostAdjustmentAuditComment(String userName, String adjType) {

        return new StringBuffer().append("A ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_amount))
                .append(" adjustment from ")
                .append(adjType)
                .append(" was recorded for ")
                .append(_requestorName)
                .append(" by ")
                .append(StringUtilities.safeTrim(userName))
                .toString();
    }

    /**
     * This method create audit comment for Requestor Delete Adjustment
     * @return audit comment for retrieve event
     */
    public String constructDeleteAdjustmentAuditComment(String userName, String requestorName) {

        return new StringBuffer().append("A ")
                .append(ROIConstants.CURRENCY_FORMAT.format(_unappliedAmount))
                .append(" adjustment was deleted from ")
                .append(requestorName)
                .append("'s account by ")
                .append(StringUtilities.safeTrim(userName))
                .append(".")
                .toString();
    }

}
