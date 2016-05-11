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

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;


/**
 * @author Rethinamt
 * @date   Jul 29, 2011
 * @since  HPF 15.2 [ROI]; Apr 15, 2008
 */
public class TaxPerFacility
 implements Serializable, Comparable<TaxPerFacility> {

    private static final long serialVersionUID = 1L;
    private long       _id;
    private String     _code;
    private String     _name;
    private String     _description;
    private long       _createdBy;
    private long       _modifiedBy;
    private Date       _modifiedDate;
    private char       _default = 'N';
    private float      _taxPercentage;
    private int        _recordVersion;

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String facilityName) { _name = facilityName; }

    public String getCode() { return _code; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.FACILITY_CODE_IS_BLANK,
            pattern = ROIConstants.FACILITY_CODE,
            misMatchErrCode = ROIClientErrorCodes.FACILITY_CODE_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.FACILITY_CODE_MAX_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FACILITY_CODE_LENGTH_EXCEEDS_LIMIT)
    public void setCode(String code) { _code = code; }

    public String getDescription() { return _description; }

    @ValidationParams (
            pattern = ROIConstants.ALLOW_ALL,
            misMatchErrCode = ROIClientErrorCodes.FACILITY_DESC_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.DEFAULT_FIELD_LENGTH,
            maxLenErrCode = ROIClientErrorCodes.FACILITY_DESC_LENGTH_EXCEEDS_LIMIT)
    public void setDescription(String description) { _description = description; }

    public char getDefault() { return _default; }
    public void setDefault(char defaul) { 
        _default = (ROIConstants.Y  == defaul || ROIConstants.LY == defaul)
        ? ROIConstants.Y : ROIConstants.N;
    }

    public float getTaxPercentage() { return _taxPercentage; }

    @ValidationParams (
            isMandatory = true,
            isMandatoryErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_IS_BLANK,
            pattern = ROIConstants.SALES_TAX,
            misMatchErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_CONTAINS_INVALID_CHAR,
            maxLength = ROIConstants.SALESTAX_MAX_PERCENTAGE,
            maxLenErrCode = ROIClientErrorCodes.SALESTAX_PERCENTAGE_LENGTH_EXCEEDS_LIMIT)
    public void setTaxPercentage(float charge) { _taxPercentage = charge; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }


    /**
     * This method creates the audit comments for create billingTier
     * @return the audit comments for BillingTier creation
     */
    public String toCreateAudit() {

        String salesTaxAudit = "Billing location has been configured";
        
        if (_taxPercentage > 0) {
            salesTaxAudit = "; Sales tax was applied for the Billing location "  + _code;
        }

        return salesTaxAudit + ".";
    }

    /**
     * This method creates the audit comments for update billingTier
     * @param oldTax oldBillingTier Details
     * @return the audit comments for BillingTier update
     */
    public String toUpdateAudit(TaxPerFacility oldTax) {
        
        StringBuffer salesTaxAudit = new StringBuffer();
        salesTaxAudit.append("Billing location has been changed ") 
                     .append(oldTax.getName() + " to " + _name);
        
        if (_taxPercentage > 0) {

            salesTaxAudit.append("; Sales Tax was applied for the Billing location ")
                         .append(_name + ".");
        } else {
            salesTaxAudit.append("; Sales Tax was removed for the Billing location ")
                         .append(_name + ".");
        }

        return salesTaxAudit.toString();
    }

    /**
     * This method creates the audit comments for delete SalesTaxPerFacility
     * @return the audit comments for SalesTaxPerFacility deletion
     */
    public String toDeleteAudit() {

        return "Billing location has been deleted " + _name  + " .";
    }

    /**
     * This method copies the Salestaxfacility details
     *
     * @param from Salestaxfacility details to be copied
     */
    public void copyFrom(TaxPerFacility from) {

        _createdBy  = from.getCreatedBy();
    }

    @Override
    public String toString() {
     return "SalesTaxFacility Id = " + _id + ", "
            + "SalesTaxFacility Code = " + _code + ", "
            + "Tax Percentage = " + _taxPercentage;
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
