/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.admin.model;


import java.io.Serializable;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;


/**
*
* @author ganeshramr
* @date   Mar 16, 2009
* @since  HPF 13.1 [ROI]; Apr 4, 2008
*/
public class FeeType
implements Serializable {

    private long    _id;
    private String  _name;
    private double  _chargeAmount;
    private String  _description;
    private boolean _associated;
    private long    _createdBy;
    private Date    _modifiedDate;
    private long    _modifiedBy;
    private int     _recordVersion;
    private long    _orgId;
    private char _salesTax = 'N';

    // added new field to define the association
    private long    _billingTemplateId;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.FEE_TYPE_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.FEE_TYPE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.FEE_TYPE_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FEE_TYPE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) {  _name = name; }

    public double getChargeAmount() { return _chargeAmount; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.FEE_TYPE_AMOUNT_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FEE_TYPE_AMOUNT_LENGTH_EXCEEDS_LIMIT)
    public void setChargeAmount(double amount) { _chargeAmount = amount; }

    public String getDescription() { return _description; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.FEE_TYPE_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FEE_TYPE_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { _description = description; }

    public boolean isAssociated() { return _associated; }
    public void setAssociated(boolean associated) {  _associated = associated; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long id) { _orgId = id; }

    public char getSalesTax() { return _salesTax; }
    public void setSalesTax(char salesTax) {

        _salesTax = (ROIConstants.Y == salesTax || ROIConstants.LY == salesTax)
                        ? ROIConstants.Y : ROIConstants.N;
    }


    public long getBillingTemplateId() {
        return _billingTemplateId;
    }
    public void setBillingTemplateId(long billingTemplateId) {
        _billingTemplateId = billingTemplateId;
    }
    @Override
    public String toString() {

        return "Fee Type ID = " + getId() + " Fee Type Name = " + getName();
    }

    /**
     * This method creates the audit comments for create feeType
     * @return the audit comments for FeeType creation
     */
    public String toCreateAudit() {

        String salesTaxAudit = ".";
        if (ROIConstants.Y == _salesTax || ROIConstants.LY == _salesTax) {

            salesTaxAudit = ", Sales Tax applied for the " + _name + ".";
        }
        return "ROI fee type " + _name + " was added" + salesTaxAudit;
    }

    /**
     * This method creates the audit comments for delete FeeType
     *
     * @param name Name of the FeeType
     * @return the audit comments for FeeType deletion
     */
    public String toDeleteAudit(String name) {

        return "ROI fee type " + name  + " was deleted.  ";
    }

    /**
     * This method creates the audit comments for update FeeType
     * @param oldFT new FeeType Details
     * @return audit comments for update FeeType
     */
    public String toUpdateAudit(FeeType oldFT) {

        String salesTaxAudit = "";

        if (' ' == _salesTax) {
            salesTaxAudit = ".";
        } else if (ROIConstants.Y == _salesTax || ROIConstants.LY == _salesTax) {
            salesTaxAudit = ", Sales Tax applied for the " + _name + ".";
        } else {
            salesTaxAudit = ", Sales Tax removed for the " + _name + ".";
        }


        return new StringBuffer().append("ROI fee type ")
                                 .append(oldFT.getName()).append(" was modified. ")
                                 .append("Old values - fee type name ")
                                 .append(oldFT.getName())
                                 .append(", amount ")
                                 .append(oldFT.getChargeAmount())
                                 .append(" New Values - fee type name ")
                                 .append(_name)
                                 .append(", and amount ")
                                 .append(_chargeAmount)
                                 .append(salesTaxAudit).toString();
    }

    /**
     * This method copies the FeeType details
     *
     * @param from FeeType details to be copied
     */
    public void copyFrom(FeeType from) {

        _createdBy = from.getCreatedBy();
        _orgId     = from.getOrgId();
    }

}
