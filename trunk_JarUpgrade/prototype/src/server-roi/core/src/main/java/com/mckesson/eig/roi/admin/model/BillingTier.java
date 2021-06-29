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
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;


/**
 * @author Ganeshramr
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class BillingTier
implements Serializable {

    private long       _id;
    private String     _name;
    private String     _description;
    private long       _createdBy;
    private long       _modifiedBy;
    private Date       _modifiedDate;
    private boolean    _associated;
    private float      _baseCharge;
    private float      _defaultPageCharge;
    private boolean    _active;
    private int        _recordVersion;
    private long       _orgId;
    private Set<PageLevelTier> _pageLevelTier;
    private MediaType _mediaType;
    private String _mediaTypeName;
    private long _mediaTypeId;
    private char _salesTax = 'N';

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.BILLING_TIER_NAME_IS_BLANK,
            pattern = ROIConstants.NAME,
            misMatchErrCode = ROIClientErrorCodes.BILLING_TIER_NAME_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.BILLING_TIER_NAME_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.BILLING_TIER_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) { _name = name; }

    public String getDescription() { return _description; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.BILLING_TIER_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.BILLING_TIER_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { _description = description; }

    public boolean getAssociated() { return _associated; }
    public void setAssociated(boolean a) { _associated = a; }

    public float getBaseCharge() { return _baseCharge; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.BILLING_TIER_CHARGE_IS_BLANK,
            pattern = ROIConstants.CHARGE,
            misMatchErrCode = ROIClientErrorCodes.BILLING_TIER_CHARGE_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.BILLING_TIER_MAX_CHARGE,
            maxLenErrCode = ROIClientErrorCodes.BILLING_TIER_CHARGE_LENGTH_EXCEEDS_LIMIT)
    public void setBaseCharge(float charge) { _baseCharge = charge; }

    public float getDefaultPageCharge() { return _defaultPageCharge; }
    public void setDefaultPageCharge(float baseCharge) { _defaultPageCharge = baseCharge; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public long getOrgId() { return _orgId; }
    public void setOrgId(long seq) { _orgId = seq; }

    public boolean isActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public Set <PageLevelTier> getPageLevelTier()  { return _pageLevelTier; }
    public void setPageLevelTier(Set <PageLevelTier> levelTier) { _pageLevelTier = levelTier; }

    public MediaType getMediaType() { return _mediaType; }
    public void setMediaType(MediaType type) { _mediaType = type; }

    public char getSalesTax() { return _salesTax; }
    public void setSalesTax(char salesTax) {

        _salesTax = (ROIConstants.Y == salesTax || ROIConstants.LY == salesTax)
                        ? ROIConstants.Y : ROIConstants.N;
    }

    /**
     * This method is to transfer the values of old Billing Tier
     * to be persisted into updated Billing Tier.
     *
     * @param old billingTier Details To be copied
     */
    public void copyFrom(BillingTier old) {

        _active     = old.isActive();
        _createdBy  = old.getCreatedBy();
        _orgId      = old.getOrgId();
    }

    /**
     * This method creates the audit comments for create billingTier
     * @return the audit comments for BillingTier creation
     */
    public String toCreateAudit() {

        return "ROI Billing Tier " + _name + " was added.";
    }

    /**
     * This method creates the audit comments for update billingTier
     * @param oldBT oldBillingTier Details
     * @return the audit comments for BillingTier update
     */
    public String toUpdateAudit(BillingTier oldBT) {

        return new StringBuffer().append("ROI Billing Tier ")
        .append(oldBT.getName())
        .append(" name was modified (to the following depending on the modification made):")
        .append(oldBT.getName())
        .append(" - ")
        .append(oldBT.getMediaType().getName())
        .append(" - ")
        .append(oldBT.getBaseCharge())
        .append(" , ")
        .append(" to ")
        .append(_name)
        .append(" - ")
        .append(getMediaType().getName())
        .append(" - ")
        .append(_baseCharge)
        .append(" - ")
        .append("Page Level Tier was modified to the following : Old Page Level Tier Details == ")
        .append(getPageLevelTierDetails(oldBT.getPageLevelTier()))
        .append(" New Page Level Tier Details == ")
        .append(getPageLevelTierDetails(getPageLevelTier())).toString();
    }

    /**
     * This method used to get the pageLevelTier details
     *
     * @param latestBT old pageLevelTier Details
     * @return pageLevelTier details
     */
    private String getPageLevelTierDetails(Set<PageLevelTier> latestBT) {

        int size = latestBT.size();
        PageLevelTier pt;
        String details = null;
        int i = 1;
        for (Iterator<PageLevelTier> iterator = latestBT.iterator(); iterator.hasNext();) {
            pt = iterator.next();
            details = details == null ? i + ". " + pt.toString()
                                      : details + " \n " + i + ". " + pt.toString();
            i++;
        }
        return details;
    }

    /**
     * This method creates the audit comments for delete billingTier
     * @return the audit comments for BillingTier deletion
     */
    public String toDeleteAudit(String name) {

        return "ROI Billing Tier " + name  + " was deleted.";
    }

    @Override
    public String toString() {
     return "BillingTier Id = " + _id + ", "
            + "BillingTier Name = " + _name + ", "
            + "Associated BaseCharge = " + _baseCharge + ", "
            + "Associated MediaType Id = " + getMediaType().getId();
    }

    public String getMediaTypeName() { return _mediaTypeName; }
    public void setMediaTypeName(String mediaTypeName) { _mediaTypeName = mediaTypeName; }

    public long getMediaTypeId() { return _mediaTypeId; }
    public void setMediaTypeId(long mediaTypeId) { _mediaTypeId = mediaTypeId; }
}
