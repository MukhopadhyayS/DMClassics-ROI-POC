/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;


/**
 * <p>Java class for TaxPerFacility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxPerFacility">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="default" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taxPercentage" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
@XmlType(name = "TaxPerFacility", propOrder = {
    "id",
    "code",
    "name",
    "description",
    "_default",
    "taxPercentage",
    "recordVersion"
})
public class TaxPerFacility
 implements Serializable, Comparable<TaxPerFacility> {

    private static final long serialVersionUID = 1L;
    private long       id;
    @XmlElement(required = true)
    private String     code;
    @XmlElement(required = true)
    private String     name;
    private String     description;
    @XmlTransient
    private long       createdBy;
    @XmlTransient
    private long       modifiedBy;
    @XmlTransient
    private Date       modifiedDate;
    @XmlElement(name = "default")
    private String       _default = "N";
    private float      taxPercentage;
    private int        recordVersion;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String facilityName) { name = facilityName; }

    public String getCode() { return code; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.FACILITY_CODE_IS_BLANK,
            pattern = ROIConstants.FACILITY_CODE,
            misMatchErrCode = ROIClientErrorCodes.FACILITY_CODE_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.FACILITY_CODE_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FACILITY_CODE_LENGTH_EXCEEDS_LIMIT)
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.FACILITY_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FACILITY_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { this.description = description; }

    public char getDefault() {
        if (_default != "" && _default != null) {
            return _default.charAt(0);
        } else
            return 'N';
    }

    public void setDefault(char defaul) {
        _default = Character.toString(
                (ROIConstants.Y == defaul || ROIConstants.LY == defaul)
                        ? ROIConstants.Y
                        : ROIConstants.N);
    }

    public float getTaxPercentage() { return taxPercentage; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_IS_BLANK,
            pattern = ROIConstants.SALES_TAX,
            misMatchErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.SALESTAX_MAX_PERCENTAGE,
            maxLenErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_LENGTH_EXCEEDS_LIMIT)
    public void setTaxPercentage(float charge) { taxPercentage = charge; }

    public int getRecordVersion() { return recordVersion; }
    public void setRecordVersion(int version) { recordVersion = version; }

    public long getCreatedBy() { return createdBy; }
    public void setCreatedBy(long by) { createdBy = by; }

    public long getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(long by) { modifiedBy = by; }

   
    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date date) { modifiedDate = date; }


    /**
     * This method creates the audit comments for create billingTier
     * @return the audit comments for BillingTier creation
     */
    public String toCreateAudit() {

        String salesTaxAudit = "Billing location has been configured";
        
        if (taxPercentage > 0) {
            salesTaxAudit = "; Sales tax was applied for the Billing location "  + code;
        }

        return salesTaxAudit + ".";
    }

    /**
     * This method creates the audit comments for update billingTier
     * @param oldTax oldBillingTier Details
     * @return the audit comments for BillingTier update
     */
    public String toUpdateAudit(TaxPerFacility oldTax) {
        // Bhaskar
        // Consider moving all these strings to some constants.
        // If any of these strings are to be visible in audit log then also consider language localization.
        StringBuffer salesTaxAudit = new StringBuffer();
        salesTaxAudit.append("Billing location has been changed ") 
                     .append(oldTax.getName() + " to " + name);
        
        if (taxPercentage > 0) {

            salesTaxAudit.append("; Sales Tax was applied for the Billing location ")
                         .append(name + ".");
        } else {
            salesTaxAudit.append("; Sales Tax was removed for the Billing location ")
                         .append(name + ".");
        }

        return salesTaxAudit.toString();
    }

    /**
     * This method creates the audit comments for delete SalesTaxPerFacility
     * @return the audit comments for SalesTaxPerFacility deletion
     */
    public String toDeleteAudit() {

        return "Billing location has been deleted " + name  + " .";
    }

    /**
     * This method copies the Salestaxfacility details
     *
     * @param from Salestaxfacility details to be copied
     */
    public void copyFrom(TaxPerFacility from) {

        createdBy  = from.getCreatedBy();
    }

    @Override
    public String toString() {
        // Bhaskar
        // Consider moving all these strings to some constants.
     return "SalesTaxFacility Id = " + id + ", "
            + "SalesTaxFacility Code = " + code + ", "
            + "Tax Percentage = " + taxPercentage;
    }
        
   /**
* compares the object and sort it out
*/
   @Override
   public int compareTo(TaxPerFacility o) {
      
       if (null == o) {
           return 0;
       }
       if (null == getName() || getName().trim().length() <= 0) {
           return -1;
       }

       if (null == o.getName() || o.getName().trim().length() <= 0) {
           return 1;
       }
      
       return compareFacilityString(o);
   }
   
   /**
    * @param o
    * @return
    */
   private int compareFacilityString(TaxPerFacility o) {
      
       int len1 = getName().length(), len2 = o.getName().length();
       for (int i1 = 0, i2 = 0; i1 < len1 && i2 < len2; i1++, i2++) {
          
           char c1 = getName().charAt(i1);
           char c2 = o.getName().charAt(i2);
           boolean isSpecialChar1 = isSpecialChar(c1);
           boolean isSpecialChar2 = isSpecialChar(c2);
          
           if (!compareCharacters(isSpecialChar1, isSpecialChar2)) {
               return (isSpecialChar2) ? 1 : -1;
           }
          
           if (c1 != c2) {
               c1 = Character.toUpperCase(c1);
               c2 = Character.toUpperCase(c2);
               if (c1 != c2) {
                   c1 = Character.toLowerCase(c1);
                   c2 = Character.toLowerCase(c2);
                   if (c1 != c2) {
                       return c1 - c2;
                   }
               }
           }
       }
       return len1 - len2;
   }
    /**
     * @param isSpecialChar1
     * @param isSpecialChar2
     * @return
     */
   private boolean compareCharacters(boolean isSpecialChar1, boolean isSpecialChar2) {
       return ((isSpecialChar1 && isSpecialChar2) || !(isSpecialChar1 || isSpecialChar2));
   }
   /**
    * @param c1
    * @return
    */
   private boolean isSpecialChar(char c1) {
       return c1 < 'A' ||  (c1 > 'Z' && c1 < 'a') || c1 > 'z';
   }
}
