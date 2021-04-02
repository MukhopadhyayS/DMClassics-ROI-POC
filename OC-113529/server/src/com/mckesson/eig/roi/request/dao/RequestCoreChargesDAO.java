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

package com.mckesson.eig.roi.request.dao;

import java.util.Date;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
/**
 * @author Keane
 * @date July 24, 2012
 */
public interface RequestCoreChargesDAO extends ROIDAO {

    /**
     * This method will delete the RequestCoreCharges for the particular request.
     * @param requestId
     */
    void deleteRequestCoreCharges(long requestId);

    /**
     * This method will delete the RequestCoreChargesDocument for the particular request.
     * @param requestCoreChargesId
     */
    void deleteRequestCoreChargesDocument(long requestCoreChargesId);


    /**
     * This method will delete the RequestCoreChargesFee for the particular request.
     * @param requestCoreChargesId
     */
    void deleteRequestCoreChargesFee(long  requestCoreChargesId);

    /**
     *This method will delete the RequestCoreChargesShipping for the particular request.
     * @param requestCoreChargesId
     */
    void deleteRequestCoreChargesShipping(long  requestCoreChargesId);

    /**
    * This method retrieves the RequestBillingPayment values
    *
    * @param requestId
    * @return RequestBillingPaymentInfo
    */
    RequestCoreCharges retrieveRequestCoreBillingPaymentInfo(long requestCoreChargesSeq);

   /**
    * This method saves the RequestCoreCharges values
    *
    * @param requestCoreCharges
    * @return
    */
   long saveRequestCoreCharges(RequestCoreCharges requestCoreCharges);

   /**
    * This method creates the RequestCoreCharges values for Document Charges
    * info
    *
    * @param requestCoreChargesDocument
    * @return
    */
   void createRequestCoreChargesDocument(RequestCoreChargesDocument requestCoreChargesDocument);

   /**
    * This method creates the RequestCoreCharges values for Fee Charges info
    *
    * @param requestCoreChargesFee
    * @return
    */
   void createRequestCoreChargesFee(RequestCoreChargesFee requestCoreChargesFee);

   /**
    * This method retrieves the RequestCoreCharges Values
    * @param requestId
    * @return
    */
   RequestCoreCharges retrieveRequestCoreCharges(long requestCoreId);

   /**
    * This method retrieves the RequestCoreCharges values for Shipping
    *
    * @param requestCoreChargesSeq
    * @return RequestCoreChargesShipping
    */
   RequestCoreChargesShipping retrieveRequestCoreChargesShipping(Long requestCoreChargesSeq);

   /**
    * This method updates the requestcorecharges DisplayBillingInfo to false
    * for the given requestId
    *
    * @param requestId
    */
   void updateRequestCoreChargesAsReleased(long requestId);

   /**
    * This method updates the requestcorecharges DisplayBillingInfo to true
    * for the given requestId
    *
    * @param requestId
    */
   void updateRequestCoreChargesAsUnReleased(long requestId);
   /**
    * This method retrieves the output type
    *
    * @param requestId
    * @return outputType
    *
    */
   String retrieveOutputType(long requestId);

   /**
    * Clear the invoicebaseCharge and InvoiceAutoAdjustments from the RequestCore Charges
    */
   void clearRequestReleaseCost(long requestId);

   /**
    * revert the releaseCost from the RequestCore Charges
    */
   void revertRequestReleaseCost(long requestId, double releaseCost);
   
   /**
    * update the releaseCost from the RequestCore Charges
    */
   void updateRequestReleaseCost(long requestId, double releaseCost,Date modifiedDt,int modifiedBySeq);

   /**
    *  This method updates the request balance for the adjustments made
    *  @param requestCoreId
    *  @param balanceDue
    *  @param creditAdjAmt
    *  @param date
    *  @param user
    */
   /*public void updateRequestBalanceForAdjustments(long requestCoreId,
    * double balanceDue,double creditAdjAmt,Timestamp date,User user);*/

}
