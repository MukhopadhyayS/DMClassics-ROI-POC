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
 * @author ranjithr
 * @date   Mar 25, 2009
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class RequestorType
implements Serializable {

    private long    _id;
    private String  _name;
    private String  _description;
    private long    _createdBy;
    private long    _modifiedBy;
    private Date    _modifiedDate;
    private boolean _isAssociated;
    private long    _orgId;
    private boolean _active;
    private String  _rv;
    private String  _rvDesc;
    private int     _recordVersion;
    private char _salesTax = 'N';

    private List<Long> _billingTemplateIds;

    private Set<RelatedBillingTemplate> _relatedBillingTemplate;
    private Set<RelatedBillingTier> _relatedBillingTier;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDescription() { return _description; }
    public void setDescription(String description) { _description = description; }

    public boolean getIsAssociated() { return _isAssociated; }
    public void setIsAssociated(boolean associated) { _isAssociated = associated; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long seq) { _orgId = seq; }

    public boolean isActive() {  return _active; }
    public void setActive(boolean active) { _active = active; }

    public String getRv() { return _rv; }
    public void setRv(String view) {  _rv = view; }

    public String getRvDesc() {  return _rvDesc;  }
    public void setRvDesc(String viewDescription) { _rvDesc = viewDescription; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public char getSalesTax() { return _salesTax; }
    public void setSalesTax(char salesTax) { 
        _salesTax = (ROIConstants.Y  == salesTax || ROIConstants.LY == salesTax)
                        ? ROIConstants.Y : ROIConstants.N;
    }
    
    public Set<RelatedBillingTemplate> getRelatedBillingTemplate() {
        return _relatedBillingTemplate;
    }
    public void setRelatedBillingTemplate(Set<RelatedBillingTemplate> typeToBillingTemplate) {
        _relatedBillingTemplate = typeToBillingTemplate;
    }

    public List<Long> getBillingTemplateIds() {

        if (_billingTemplateIds == null) {
            _billingTemplateIds = new ArrayList<Long>();
        }
        return _billingTemplateIds;
    }

    public void setBillingTemplateIds(List<Long> typeIds) { _billingTemplateIds = typeIds; }


    /**
     * This method transfers the values of old requestorType to be persisted into
     * updated requestorType
     * @param old requestorType details to be copied
     */
    public void copyFrom(RequestorType old) {

        _active     = old.isActive();
        _createdBy  = old.getCreatedBy();
        _orgId      = old.getOrgId();
    }

    @Override
    public String toString() {
     return new StringBuffer().append("RequestorType Id = ")
                              .append(_id)
                              .append(", RequestorType Name = ")
                              .append(_name)
                              .append(", RecordView = ")
                              .append(_rv).toString();
    }

    /**
     * This method creates the audit comments for create RequestorType
     * @return the audit comments for RequestorType creation
     */
    public String toCreateAudit() {

        String salesTaxAudit = ".";
        if (ROIConstants.Y == _salesTax || ROIConstants.LY == _salesTax) {
            
            salesTaxAudit = ", Sales Tax has been applied for the " + _name + ".";
        }
        return "ROI Requestor type " + _name + " was added" + salesTaxAudit;
    }

    /**
     * This method creates the audit comments for deleteRequestorType
     * @return the audit comments for RequestorType deletion
     */
    public String toDeleteAudit(String name) {

        return "ROI Requestor type " + _name + " was deleted.";
    }

    /**
     * This method creates the audit comments for updateRequestorType
     * @param oldRT oldRequrstorType Details
     * @return the audit comment for requestorType update
     */
    public String toUpdateAudit(RequestorType oldRT,
                                BillingTemplatesList bt,
                                String oldBillingTierName,
                                String newBillingTierName) {
        
        String salesTaxAudit = "";
        
        if (' ' == _salesTax) {
            salesTaxAudit = ".";
        } else if (ROIConstants.Y == _salesTax || ROIConstants.LY == _salesTax) {
                salesTaxAudit = ", Sales Tax has been applied for the " + _name + ".";
        } else {
            salesTaxAudit = ", Sales Tax has been removed for the " + _name + ".";
        }

        return new StringBuffer()
                    .append("ROI Requestor type was modified. Old values: Name ")
            		.append(oldRT.getName())
            		.append("; Default Record View ")
            		.append(oldRT.getRv())
            		.append("; Electronic Billing Tier ")
            		.append(oldBillingTierName)
            		.append("; Billing Template Names ")
            		.append(getBillingTemplateNamesAsCSV(oldRT.getRelatedBillingTemplate(), bt))
            		.append("; New values: Name ")
            		.append(_name)
            		.append("; Default Record View ")
            		.append(_rv)
            		.append("; Electronic Billing Tier ")
            		.append(newBillingTierName)
            		.append("; Billing Template Names ")
            		.append(getBillingTemplateNamesAsCSV(getRelatedBillingTemplate(), bt))
            		.append(salesTaxAudit).toString();
    }

    /**
     * This method get the billingTemplateName for the billingTemplate Id
     * @param rtBTemplate BillingTemplate Details
     * @param bt List of BillingTemplates
     * @return name of the BillingTemplate
     */
    private String getBillingTemplateNamesAsCSV(Set<RelatedBillingTemplate> rtBTemplate,
                                           BillingTemplatesList bt) {

        RelatedBillingTemplate rtb;
        BillingTemplate bTemplate;
        String detail = "";

        for (Iterator<RelatedBillingTemplate> it = rtBTemplate.iterator(); it.hasNext();) {
            rtb = it.next();
            for (Iterator<BillingTemplate> i = bt.getBillingTemplates().iterator(); i.hasNext();) {
                bTemplate = i.next();
                if (bTemplate.getId() == rtb.getBillingTemplateId()) {
                    detail = detail + bTemplate.getName() + ",";
                    break;
                }
            }
        }
        if ((detail != null) && (detail.lastIndexOf(',') != -1)) {
            detail = detail.substring(0, detail.lastIndexOf(','));
        }
        return detail;
    }

    public Set<RelatedBillingTier> getRelatedBillingTier() {
        if (_relatedBillingTier == null) {
            _relatedBillingTier = new HashSet <RelatedBillingTier>();
        }
        return _relatedBillingTier;
    }

    public void setRelatedBillingTier(Set<RelatedBillingTier> billingTier) {
        _relatedBillingTier = billingTier;
    }

}
