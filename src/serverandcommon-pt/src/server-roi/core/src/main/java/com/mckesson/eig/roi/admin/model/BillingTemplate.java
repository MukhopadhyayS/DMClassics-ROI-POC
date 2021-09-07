/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;


/**
 * @author Vidhya.C.S
 * @date Mar 16, 2009
 * @since HPF 13.1 [ROI]
 */
public class BillingTemplate
implements Serializable {

    private long _id;
    private String _name;
    private long _createdBy;
    private Date _modifiedDate;
    private long _modifiedBy;
    private int _recordVersion;
    private long _orgId;
    private boolean _active;
    private boolean _associated;
    private Set<RelatedFeeType> _relatedFeeTypes;
    private List <Long> _feeTypeIds;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.BILLING_TEMPLATE_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.BILLING_TEMPLATE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.BILLING_TEMPLATE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

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

    public Set<RelatedFeeType> getRelatedFeeTypes() {
        if (_relatedFeeTypes == null) {
            _relatedFeeTypes = new HashSet<RelatedFeeType>();
        }
        return _relatedFeeTypes;
    }

    public void setRelatedFeeTypes(Set<RelatedFeeType> templateDetails) {
        _relatedFeeTypes = templateDetails;
    }

    public void addRelatedFeeTypes(RelatedFeeType relatedFeeType) {

        if (null == relatedFeeType) {
            return;
        }

        if (_relatedFeeTypes == null) {
            _relatedFeeTypes = new HashSet<RelatedFeeType>();
        }
        _relatedFeeTypes.add(relatedFeeType);

        if (_feeTypeIds == null) {
            _feeTypeIds = new ArrayList<Long>();
        }
        _feeTypeIds.add(relatedFeeType.getFeeTypeId());
    }


    public boolean isActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public boolean isAssociated() { return _associated; }
    public void setAssociated(boolean associated) { _associated = associated; }

    @Override
    public String toString() {

        return "Billing Template ID = " + _id + " Billing Template Name = " + _name;
    }

    /**
     * This method creates the audit comments for create BillingTemplate
     *
     * @return the audit comments for billing template creation
     */
    public String toCreateAudit() {

        return "ROI Billing Template " + _name + " was added.";
    }

    /**
     * This method creates the audit comments for delete billing template
     *
     * @return the audit comments for billing template deletion
     */
    public String toDeleteAudit() {

        return "ROI Billing Template " + _name + " was deleted.";
    }

    /**
     * This method creates the audit comments for update billing template
     *
     * @param userFullName Name of the user
     * @param oldDM old delivery method
     * @return audit comments for update delivery method
     */
    public String toUpdateAudit(BillingTemplate oldBT, FeeTypesList feeTypes) {

        return new StringBuffer().append("ROI Billing Template was modified: ")
                                 .append("Old values: Name ")
                                 .append(oldBT.getName())
                                 .append(" Fee Types ")
                                 .append(getFeeTypeNamesAsCSV(oldBT.getRelatedFeeTypes(),
                                                              feeTypes))

                                 .append(" New Values: Name ")
                                 .append(_name)
                                 .append(" Fee Types ")
                                 .append(getFeeTypeNamesAsCSV(_relatedFeeTypes, feeTypes))
                                 .toString();
    }

    /**
     * This method copies the existing billing template to the newly updated billing template
     * model.
     *
     * @param fromDb Billing Template details to be copied
     */
    public void copyFrom(BillingTemplate fromDb) {

        setCreatedBy(fromDb.getCreatedBy());
        setOrgId(fromDb.getOrgId());
        setActive(fromDb.isActive());
    }

    private String getFeeTypeNamesAsCSV(Set<RelatedFeeType> billingTemplateDetails,
                                        FeeTypesList feeTypes) {

        int size = feeTypes.getFeeTypesList().size();
        FeeType feetype;
        RelatedFeeType btf;
        String details = "";
        for (Iterator <RelatedFeeType> iterator = billingTemplateDetails.iterator();
                                                 iterator.hasNext();) {
            btf = iterator.next();
            for (Iterator <FeeType> iter = feeTypes.getFeeTypesList().iterator();
                                    iter.hasNext();) {
                feetype = iter.next();
                if (feetype.getId() == btf.getFeeTypeId()) {
                    details = details + feetype.getName() + ",";
                    break;
                }
            }
        }
        if (details.lastIndexOf(',') != -1) {
            details = details.substring(0, details.lastIndexOf(','));
        }
        return details;
    }

    public List<Long> getFeeTypeIds() {

        if (_feeTypeIds == null) {
            _feeTypeIds = new ArrayList<Long>();
        }
        return _feeTypeIds;
    }
    public void setFeeTypeIds(List<Long> typeIds) { _feeTypeIds = typeIds; }

}
