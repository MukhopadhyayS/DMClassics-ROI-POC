/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright ? 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RequestorType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RequestorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestorTypeId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rvDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordVersion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="relatedBillingTemplate" type="{urn:eig.mckesson.com}RelatedBillingTemplate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedBillingTier" type="{urn:eig.mckesson.com}RelatedBillingTier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="billingTemplateIds" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isAssociated" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="salesTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="invoiceOptional" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestorType", propOrder = {"id", "name", "description", "rv",
        "rvDesc", "recordVersion", "relatedBillingTemplate",
        "relatedBillingTier", "billingTemplateIds", "isAssociated", "_salesTax",
        "_invoiceOptional"})
public class RequestorType implements Serializable {

    @XmlElement(name = "requestorTypeId")
    private long id;
    @XmlElement(required = true)
    private String name;
    private String description;
    private String rv;
    private String rvDesc;
    private int recordVersion;
    private Set<RelatedBillingTemplate> relatedBillingTemplate;
    private Set<RelatedBillingTier> relatedBillingTier;
    @XmlElement(nillable = true)
    private List<Long> billingTemplateIds;
    private boolean isAssociated;
    
    @XmlElement(name="salesTax")
    private String _salesTax = "N";
    
    @XmlElement(name="invoiceOptional")
    private String _invoiceOptional = "N";
    @XmlTransient
    private long _createdBy;
    @XmlTransient
    private long _modifiedBy;
    @XmlTransient
    private Date _modifiedDate;
    @XmlTransient
    private long _orgId;
    @XmlTransient
    private boolean _active;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @ValidationParams(isMandatory = true, isMandatoryErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_IS_BLANK, pattern = ROIConstants.NAME, misMatchErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_CONTAINS_INVALID_CHAR, maxLength = ROIConstants.DEFAULT_FIELD_LENGTH, maxLenErrCode = ROIClientErrorCodes.REQUESTOR_TYPE_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsAssociated() {
        return isAssociated;
    }
    public void setIsAssociated(boolean associated) {
        this.isAssociated = associated;
    }

    public long getCreatedBy() {
        return _createdBy;
    }
    public void setCreatedBy(long by) {
        _createdBy = by;
    }

    public long getModifiedBy() {
        return _modifiedBy;
    }
    public void setModifiedBy(long by) {
        _modifiedBy = by;
    }

    public Date getModifiedDate() {
        return _modifiedDate;
    }
    public void setModifiedDate(Date date) {
        _modifiedDate = date;
    }

    public long getOrgId() {
        return _orgId;
    }
    public void setOrgId(long seq) {
        _orgId = seq;
    }

    public boolean isActive() {
        return _active;
    }
    public void setActive(boolean active) {
        _active = active;
    }

    public String getRv() {
        return rv;
    }
    public void setRv(String view) {
        this.rv = view;
    }

    public String getRvDesc() {
        return rvDesc;
    }
    public void setRvDesc(String viewDescription) {
        this.rvDesc = viewDescription;
    }

    public int getRecordVersion() {
        return recordVersion;
    }
    public void setRecordVersion(int version) {
        this.recordVersion = version;
    }

    public char getSalesTax() { 
        if(_salesTax!="" && _salesTax!=null) {
            return _salesTax.charAt(0); 
           } else return 'N';
        }
    public void setSalesTax(char salesTax) {

        _salesTax = Character.toString((ROIConstants.Y == salesTax || ROIConstants.LY == salesTax)
                ? ROIConstants.Y : ROIConstants.N);
    }

    public char getInvoiceOptional() {
        return _invoiceOptional.charAt(0);
    }

    public void setInvoiceOptional(char invoiceOptional) {
        _invoiceOptional = Character
                .toString((ROIConstants.Y == invoiceOptional
                        || ROIConstants.LY == invoiceOptional)
                                ? ROIConstants.Y
                                : ROIConstants.N);
    }

    public Set<RelatedBillingTemplate> getRelatedBillingTemplate() {
        return relatedBillingTemplate;
    }
    public void setRelatedBillingTemplate(
            Set<RelatedBillingTemplate> typeToBillingTemplate) {
        this.relatedBillingTemplate = typeToBillingTemplate;
    }

    public List<Long> getBillingTemplateIds() {

        if (billingTemplateIds == null) {
            billingTemplateIds = new ArrayList<Long>();
        }
        return billingTemplateIds;
    }

    public void setBillingTemplateIds(List<Long> typeIds) {
        this.billingTemplateIds = typeIds;
    }

    /**
     * This method transfers the values of old requestorType to be persisted
     * into updated requestorType
     * 
     * @param old
     *            requestorType details to be copied
     */
    public void copyFrom(RequestorType old) {

        _active = old.isActive();
        _createdBy = old.getCreatedBy();
        _orgId = old.getOrgId();
    }

    @Override
    public String toString() {
        return new StringBuffer().append("RequestorType Id = ").append(id)
                .append(", RequestorType Name = ").append(name)
                .append(", RecordView = ").append(rv).toString();
    }

    /**
     * This method creates the audit comments for create RequestorType
     * 
     * @return the audit comments for RequestorType creation
     */
    public String toCreateAudit() {

        String salesTaxAudit = ".";
        if (Character.toString(ROIConstants.Y) == _salesTax
                || Character.toString(ROIConstants.LY) == _salesTax) {

            salesTaxAudit = ", Sales Tax has been applied for the " + name
                    + ".";
        }
        return "ROI Requestor type " + name + " was added" + salesTaxAudit;
    }

    /**
     * This method creates the audit comments for deleteRequestorType
     * 
     * @return the audit comments for RequestorType deletion
     */
    public String toDeleteAudit(String name) {

        return "ROI Requestor type " + name + " was deleted.";
    }

    /**
     * This method creates the audit comments for updateRequestorType
     * 
     * @param oldRT
     *            oldRequrstorType Details
     * @return the audit comment for requestorType update
     */
    public String toUpdateAudit(RequestorType oldRT, BillingTemplatesList bt,
            String oldBillingTierName, String newBillingTierName) {

        String salesTaxAudit = "";
		
		// Bhaskar
        // Consider moving all these strings to some constants.
        // If any of these strings are to be visible in audit log then also consider language localization.
		if ("" == _salesTax) {
            salesTaxAudit = ".";
        } else if (Character.toString(ROIConstants.Y) == _salesTax
                || Character.toString(ROIConstants.LY) == _salesTax) {
            salesTaxAudit = ", Sales Tax has been applied for the " + name
                    + ".";
        } else {
            salesTaxAudit = ", Sales Tax has been removed for the " + name
                    + ".";
        }

        return new StringBuffer()
                .append("ROI Requestor type was modified. Old values: Name ")
                .append(oldRT.getName()).append("; Default Record View ")
                .append(oldRT.getRv()).append("; Electronic Billing Tier ")
                .append(oldBillingTierName).append("; Billing Template Names ")
                .append(getBillingTemplateNamesAsCSV(
                        oldRT.getRelatedBillingTemplate(), bt))
                .append("; New values: Name ").append(name)
                .append("; Default Record View ").append(rv)
                .append("; Electronic Billing Tier ").append(newBillingTierName)
                .append("; Billing Template Names ")
                .append(getBillingTemplateNamesAsCSV(
                        getRelatedBillingTemplate(), bt))
                .append(salesTaxAudit).toString();
    }

    /**
     * This method get the billingTemplateName for the billingTemplate Id
     * 
     * @param rtBTemplate
     *            BillingTemplate Details
     * @param bt
     *            List of BillingTemplates
     * @return name of the BillingTemplate
     */
    private String getBillingTemplateNamesAsCSV(
            Set<RelatedBillingTemplate> rtBTemplate, BillingTemplatesList bt) {

        RelatedBillingTemplate rtb;
        BillingTemplate bTemplate;
        String detail = "";

        for (Iterator<RelatedBillingTemplate> it = rtBTemplate.iterator(); it
                .hasNext();) {
            rtb = it.next();
            for (Iterator<BillingTemplate> i = bt.getBillingTemplates()
                    .iterator(); i.hasNext();) {
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
        if (relatedBillingTier == null) {
            relatedBillingTier = new HashSet<RelatedBillingTier>();
        }
        return relatedBillingTier;
    }

    public void setRelatedBillingTier(Set<RelatedBillingTier> billingTier) {
        this.relatedBillingTier = billingTier;
    }

}
