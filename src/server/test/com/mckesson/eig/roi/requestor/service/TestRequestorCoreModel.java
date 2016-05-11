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

package com.mckesson.eig.roi.requestor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.requestor.model.AdjustmentType;
import com.mckesson.eig.roi.requestor.model.GeneralFeeTypes;
import com.mckesson.eig.roi.requestor.model.RefundLetter;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsFee;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsFeeList;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.UnappliedAdjustmentInfo;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author OFS
 * @date   May 31, 2013
 * @since  ROI 16.0 ; May 31, 2013
 */
public class TestRequestorCoreModel 
extends BaseROITestCase {

    private static String STRING_VALUE = "Test";
    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void testRefundLetterModel() {
        
        RefundLetter refundLetter = new RefundLetter();
        
        refundLetter.setUserName(STRING_VALUE);
        refundLetter.setRequestorName(STRING_VALUE);
        refundLetter.setRequestorTypeName(STRING_VALUE);
        refundLetter.setRequestorId(1);
        refundLetter.setRequestorTypeId(1);
        refundLetter.setRefundAmount(1);
        refundLetter.setPostalCode("638007");
        refundLetter.setCity("CA");
        refundLetter.setCountry("IND");
        refundLetter.setAddress1(STRING_VALUE);
        refundLetter.setAddress2(STRING_VALUE);
        refundLetter.setAddress3(STRING_VALUE);
        refundLetter.setState("TN");
        refundLetter.setUserInstanceId(1);
        refundLetter.setTemplateName(STRING_VALUE);
        refundLetter.setTemplateFileId(1);
        refundLetter.setCountryCode("IND");
        refundLetter.setOutputMethod(STRING_VALUE);
        refundLetter.setQueuePassword(STRING_VALUE);
        refundLetter.setRefundDate(new Date());
        refundLetter.setNote(STRING_VALUE + "~" + STRING_VALUE);
        
        List<String> note = new ArrayList<String>();
        note.add(STRING_VALUE);
        
        refundLetter.setNotes(note);
        
        assertEquals(STRING_VALUE, refundLetter.getUserName());
        assertEquals(STRING_VALUE, refundLetter.getRequestorName());
        assertEquals(STRING_VALUE, refundLetter.getRequestorTypeName());
        assertEquals(1, refundLetter.getRequestorId());
        assertEquals(1, refundLetter.getRequestorTypeId());
        assertEquals(1.0, refundLetter.getRefundAmount());
        assertEquals("638007", refundLetter.getPostalCode());
        assertEquals("CA", refundLetter.getCity());
        assertEquals("IND", refundLetter.getCountry());
        assertEquals(STRING_VALUE, refundLetter.getAddress1());
        assertEquals(STRING_VALUE, refundLetter.getAddress2());
        assertEquals(STRING_VALUE, refundLetter.getAddress3());
        assertEquals("TN", refundLetter.getState());
        assertEquals(1, refundLetter.getUserInstanceId());
        assertEquals(STRING_VALUE, refundLetter.getTemplateName());
        assertEquals(1, refundLetter.getTemplateFileId());
        assertEquals("IND", refundLetter.getCountryCode());
        assertEquals(STRING_VALUE, refundLetter.getOutputMethod());
        assertEquals(STRING_VALUE, refundLetter.getQueuePassword());
        assertNotNull(refundLetter.getRefundDate());
        assertNotNull(refundLetter.getNote());
        assertNotNull(refundLetter.getNotes());
        
    }
    
    public void testRequestorAdjustmentPaymentListModel() {
        
        RequestorAdjustmentPaymentList reqAdjPay = new RequestorAdjustmentPaymentList();
        
        reqAdjPay.setAmount(1);
        reqAdjPay.setAdjustmentAmount(1);
        reqAdjPay.setPaymentAmount(1);
        
        List<RequestorAdjustment>  adjustments = new ArrayList<RequestorAdjustment>();
        
        RequestorAdjustment adjustment = new RequestorAdjustment();
        adjustment.setRequestorSeq(0);
        adjustment.setAmount(1.0);
        adjustment.setUnappliedAmount(1.0);
        adjustment.setReason(STRING_VALUE);
        adjustment.setAdjustmentDate(new Date());
        adjustment.setNote(STRING_VALUE);
        adjustment.setInvoiceSeq(0);
        adjustment.setInvoiceSeq(0);
        adjustment.setAppliedAmount(1.0);
        adjustment.setRequestorName(STRING_VALUE);
        adjustment.setRequestorType(STRING_VALUE);
        adjustment.setDelete(true);
        adjustment.setAdjustmentType(AdjustmentType.BILLING_ADJUSTMENT);
        
        assertNotNull(adjustment.
                constructPostAdjustmentAuditComment(STRING_VALUE, STRING_VALUE));
        assertNotNull(adjustment.
                constructDeleteAdjustmentAuditComment(STRING_VALUE, STRING_VALUE));
        
        adjustments.add(adjustment);
        reqAdjPay.setAdjustments(adjustments);
        
        List<RequestorPayment>  payments = new ArrayList<RequestorPayment>();
        reqAdjPay.setPayments(payments);
        
        assertEquals(1.0, reqAdjPay.getAmount());
        assertEquals(1.0, reqAdjPay.getAdjustmentAmount());
        assertEquals(1.0, reqAdjPay.getPaymentAmount());
        assertNotNull(reqAdjPay.getAdjustments());
        assertNotNull(reqAdjPay.getPayments());
        
    }
    
    public void testUnappliedAdjustmentInfoModel() {
        
        UnappliedAdjustmentInfo unappliedinfo = new UnappliedAdjustmentInfo();
        
        unappliedinfo.setFeeName(STRING_VALUE);
        unappliedinfo.setFeeAmount(1.0);
        unappliedinfo.setAmount(1.0);
        unappliedinfo.setSalesTaxAmount(1.0);
        unappliedinfo.setUnappliedAmount(1.0);
        unappliedinfo.setSalestaxFeeAmount(1.0);
        unappliedinfo.setFeeType(STRING_VALUE);
        unappliedinfo.setReason(STRING_VALUE);
        unappliedinfo.setNote(STRING_VALUE);
        unappliedinfo.setFacilityCode(STRING_VALUE);
        unappliedinfo.setFacilityName(STRING_VALUE);
        unappliedinfo.setIsTaxable(true);
        unappliedinfo.setAdjustmentDate(new Date());
        
        assertEquals(STRING_VALUE, unappliedinfo.getFeeName());
        assertEquals(STRING_VALUE, unappliedinfo.getFeeType());
        assertEquals(STRING_VALUE, unappliedinfo.getReason());
        assertEquals(STRING_VALUE, unappliedinfo.getNote());
        assertEquals(STRING_VALUE, unappliedinfo.getFacilityName());
        assertEquals(STRING_VALUE, unappliedinfo.getFacilityCode());
        assertEquals(1.0, unappliedinfo.getFeeAmount());
        assertEquals(1.0, unappliedinfo.getAmount());
        assertEquals(1.0, unappliedinfo.getSalestaxFeeAmount());
        assertEquals(1.0, unappliedinfo.getSalesTaxAmount());
        assertEquals(1.0, unappliedinfo.getUnappliedAmount());
        assertNotNull(unappliedinfo.getAdjustmentDate());
        
    }
    
    public void testGeneralFeeTypesModel() {
        
        GeneralFeeTypes gnrlFeeType = new GeneralFeeTypes();
        
        gnrlFeeType.setSalesTax(true);
        gnrlFeeType.setFeeType(STRING_VALUE);
        
        assertTrue(gnrlFeeType.getSalesTax());
        assertEquals(STRING_VALUE, gnrlFeeType.getFeeType());
        
    }
    
    public void testRequestorAdjustmentsFeeModel() {

        RequestorAdjustmentsFee regAdjFee = new RequestorAdjustmentsFee();
        
        regAdjFee.setFeeName(STRING_VALUE);
        regAdjFee.setFeeType(STRING_VALUE);
        regAdjFee.setAmount(1.0);
        regAdjFee.setSalestaxAmount(1.0);
        regAdjFee.setIsTaxable(true);
        
        RequestorAdjustmentsFeeList reqFeeList = new RequestorAdjustmentsFeeList();
        
        assertEquals(STRING_VALUE, regAdjFee.getFeeName());
        assertEquals(STRING_VALUE, regAdjFee.getFeeType());
        assertEquals(1.0, regAdjFee.getAmount());
        assertEquals(1.0, regAdjFee.getSalestaxAmount());
        
        List<RequestorAdjustmentsFee> adjFeeList = new ArrayList<RequestorAdjustmentsFee>();
        adjFeeList.add(regAdjFee);
        reqFeeList.setRequestorAdjustmentsFee(adjFeeList);
        assertNotNull(reqFeeList.getRequestorAdjustmentsFee());
        
    }

}
