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

package com.mckesson.eig.roi.billing.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.billing.model.ChargeHistory;
import com.mckesson.eig.roi.billing.model.ChargeHistoryList;
import com.mckesson.eig.roi.billing.model.Invoice;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;
import com.mckesson.eig.roi.billing.model.InvoiceDetails;
import com.mckesson.eig.roi.billing.model.InvoiceDetailsList;
import com.mckesson.eig.roi.billing.model.PostPaymentReportDetails;
import com.mckesson.eig.roi.billing.model.PrebillReportDetails;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryUser;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesInvoice;
import com.mckesson.eig.roi.billing.model.RequestorInvoices;
import com.mckesson.eig.roi.billing.model.RequestorLetterInvoice;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
*
* @author OFS
* @date   Jun 04, 2013
*/

public class TestBillingCoreModel
extends BaseROITestCase {

    protected static final Date SAMPLE_DATE = new Date("10/12/12");


     public void testChargeHistoryModel() {

        ChargeHistory chrgHistory = testConstructChargeHistory();

        assertEquals(1.0, chrgHistory.getTotalFeeCharge());
        assertEquals(1.0, chrgHistory.getTotalDocumentCharge());
        assertEquals(1.0, chrgHistory.getTotalShippingCharge());
        assertEquals(1.0, chrgHistory.getTotalSalesTax());
        assertEquals(true, chrgHistory.isUnbillable());
        assertEquals(4.0, chrgHistory.getUnbillableAmount());
        assertNotNull(chrgHistory.getReleaseDate());

        ChargeHistoryList chargeList = new ChargeHistoryList();

        List<ChargeHistory> chargehistoryList = new ArrayList<ChargeHistory>();
        chargehistoryList.add(chrgHistory);
        new ChargeHistoryList(chargehistoryList);
        chargeList.setChargeHistories(chargehistoryList);
        assertNotNull(chargeList.getChargeHistories());
    }

     public ChargeHistory testConstructChargeHistory() {

         ChargeHistory chrgHistory = new ChargeHistory();

         chrgHistory.setTotalFeeCharge(1);
         chrgHistory.setTotalDocumentCharge(1);
         chrgHistory.setTotalShippingCharge(1);
         chrgHistory.setTotalSalesTax(1);
         chrgHistory.setReleaseDate(new Date());
         chrgHistory.setUnbillable(true);
         chrgHistory.setUnbillableAmount(1);
         return chrgHistory;
     }

     public void testRequestorLetterInvoiceModel() {

         RequestorLetterInvoice reqInv = new RequestorLetterInvoice();

         reqInv.setRequestorLetterId(1L);
         reqInv.setInvoiceId(1L);
         reqInv.setOverDueAmount(1.00);
         reqInv.setOverDueDays(1L);
         reqInv.setRequestId(1L);
         reqInv.setBillingLocation("Test");

         assertEquals("Test", reqInv.getBillingLocation());
         assertEquals(1.00, reqInv.getOverDueAmount());
         assertEquals(new Long(1L), reqInv.getOverDueDays());
         assertEquals(new Long(1L), reqInv.getRequestId());
         assertEquals(new Long(1L), reqInv.getInvoiceId());

     }


     public void testPostPaymentReportDetailsModel() {

         PostPaymentReportDetails paymnt = new PostPaymentReportDetails();

         paymnt.setFacility("Test");
         paymnt.setUserName("Test");
         paymnt.setRequestorType("Test");
         paymnt.setCreatedDate(new Timestamp(1L));
         paymnt.setMrn("Test");
         paymnt.setInvoiceNumber("1");
         paymnt.setPaymentMethod("Test");
         paymnt.setPaymentDetails("Test");
         paymnt.setPaymentAmount(1.0);
         paymnt.setRequestId("1");

         assertEquals("Test", paymnt.getFacility());
         assertEquals("Test", paymnt.getUserName());
         assertEquals("Test", paymnt.getRequestorType());
         assertEquals("Test", paymnt.getMrn());
         assertEquals("Test", paymnt.getPaymentMethod());
         assertEquals("Test", paymnt.getPaymentDetails());
         assertEquals(1.0, paymnt.getPaymentAmount());
         assertEquals("1", paymnt.getRequestId());
         assertEquals("1", paymnt.getInvoiceNumber());

     }

     public void testPrebillReportDetailModel() {

         PrebillReportDetails prebillRpt = new PrebillReportDetails();
         prebillRpt.setFacility("Test");
         prebillRpt.setRequestorType("Test");
         prebillRpt.setRequestorName("Test");
         prebillRpt.setRequestorPhone("12345");
         prebillRpt.setRequestId("Test");
         prebillRpt.setPrebillDate(new Timestamp(1L));
         prebillRpt.setPrebillAmount(1.0);
         prebillRpt.setAging("1");
         prebillRpt.setPrebillNumber("1");

         assertEquals("Test", prebillRpt.getFacility());
         assertEquals("Test", prebillRpt.getRequestorType());
         assertEquals("Test", prebillRpt.getRequestorName());
         assertEquals("12345", prebillRpt.getRequestorPhone());
         assertEquals("Test", prebillRpt.getRequestId());
         assertEquals("1", prebillRpt.getPrebillNumber());
         assertNotNull(prebillRpt.getPrebillDate());
         assertEquals(1.0, prebillRpt.getPrebillAmount());
         assertEquals("1", prebillRpt.getAging());
     }

     public void testReleaseHistoryUserModel() {

         ReleaseHistoryUser relHistUsr = new ReleaseHistoryUser();
         relHistUsr.setUserName("Test");
         assertEquals("Test", relHistUsr.getUserName());
     }

     public void testRequestCoreDeliveryChargesAdjustmentPaymentModel() {

         RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                                             constructRequestCoreDeliveryChargesAdjustmentPayment();

         assertEquals(1, adjPay.getRequestorId());
         assertEquals(1, adjPay.getRequestCoreDeliveryChargesId());
         assertEquals(true, adjPay.isNewlyAdded());

         adjPay.toCreditUpdateAudit(1, true, true,
                                    adjPay.getInvoiceAppliedAmount(),
                                    adjPay.getBaseAmount(),
                                    adjPay.getTotalUnappliedAmount(),
                                    adjPay.getTransactionType(),
                                    adjPay.getPaymentMode(),
                                    adjPay.getDescription());

         assertNotNull(adjPay.toCreditUpdateAudit(1, true,
                                    adjPay.getInvoiceAppliedAmount(),
                                    adjPay.getBaseAmount()));

         assertNotNull(adjPay.toAutoAdjustmentComment(1, true,
                 adjPay.getInvoiceAppliedAmount(),
                 adjPay.getBaseAmount()));

         assertNotNull(adjPay.toDebitUpdateAudit(1, true));
     }

    public RequestCoreDeliveryChargesAdjustmentPayment
                                          constructRequestCoreDeliveryChargesAdjustmentPayment() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                                                 new RequestCoreDeliveryChargesAdjustmentPayment();

         adjPay.setRequestorId(1);
         adjPay.setRequestCoreDeliveryChargesId(1);
         adjPay.setInvoiceAppliedAmount(1.0);
         adjPay.setBaseAmount(1.0);
         adjPay.setTotalUnappliedAmount(1.0);
         adjPay.setPaymentMode(ROIConstants.MANUAL);
         adjPay.setDescription("Test");
         adjPay.setPaymentDate(new Date());
         adjPay.setTransactionType(ROIConstants.PAYMENT_TYPE);
         adjPay.setIsDebit(true);
         adjPay.setNewlyAdded(true);
        return adjPay;
    }

     public void testInvoiceDetailsModel() {

         InvoiceDetails invDetails = constructInvoiceDetails();

         assertEquals(1.0, invDetails.getInvoiceAmount());
         assertEquals(1, invDetails.getInvoiceNumber());
         assertEquals(1, invDetails.getTemplateId());
         assertEquals("Sample", invDetails.getQueuePassword());
         assertEquals("Test", invDetails.getResendBy());
         assertEquals(SAMPLE_DATE, invDetails.getResendDateTime());
         assertEquals("Testing", invDetails.getTemplateUsed());

     }

     public void testInvoiceDetailListMOdel() {

         InvoiceDetails invDetails = constructInvoiceDetails();
         InvoiceDetailsList invoiceDetail = new InvoiceDetailsList();
         List<InvoiceDetails> invList = new ArrayList<InvoiceDetails>();
         invList.add(invDetails);

         invoiceDetail.setInvoiceDetailsList(invList);
         assertNotNull(invoiceDetail.getInvoiceDetailsList());

     }

    public InvoiceDetails constructInvoiceDetails() {

        InvoiceDetails invDetails = new InvoiceDetails();

         invDetails.setInvoiceAmount(1.0);
         invDetails.setInvoiceNumber(1);
         invDetails.setQueuePassword("Sample");
         invDetails.setResendBy("Test");
         invDetails.setResendDateTime(SAMPLE_DATE);
         invDetails.setTemplateId(1);
         invDetails.setTemplateUsed("Testing");
        return invDetails;
    }

    public void testRequestCoreDeliveryChargesInvoiceModel() {

        RequestCoreDeliveryChargesInvoice reqDeliveryChargeInv =
                                                            new RequestCoreDeliveryChargesInvoice();

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                                            constructRequestCoreDeliveryChargesAdjustmentPayment();


        Set<RequestCoreDeliveryChargesAdjustmentPayment> deliChargesAdjPay =
                                        new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>();

        deliChargesAdjPay.add(adjPay);
        reqDeliveryChargeInv.setRequestCoreDeliveryChargesAdjustmentPayment(deliChargesAdjPay);
        assertNotNull(reqDeliveryChargeInv.getRequestCoreDeliveryChargesAdjustmentPayment());

    }

    public void testInvoiceModel() {

        Invoice inv = new Invoice();

        inv.setBaseCharge(1.0);
        inv.setCreatedDate("01/01/01");
        inv.setCreatorName(ADMIN_USER);
        inv.setCurrentBalance(1.0);
        inv.setId(1);
        inv.setInvoiceDueDate("01/01/01");
        inv.setOutputMethod("PDF");
        inv.setQueuePassword(ADMIN_PWD);
        inv.setResendDate("10/10/10");
        inv.setTotalAdjustments(1.0);
        inv.setTotalPayments(1.0);
        inv.setType("Test");

        assertEquals(1.0, inv.getBaseCharge());
        assertEquals("01/01/01", inv.getCreatedDate());
        assertEquals(ADMIN_USER, inv.getCreatorName());
        assertEquals(1.0, inv.getCurrentBalance());
        assertEquals(1, inv.getId());
        assertEquals("01/01/01", inv.getInvoiceDueDate());
        assertEquals("PDF", inv.getOutputMethod());
        assertEquals(ADMIN_PWD, inv.getQueuePassword());
        assertEquals("10/10/10", inv.getResendDate());
        assertEquals(1.0, inv.getTotalAdjustments());
        assertEquals(1.0, inv.getTotalPayments());
        assertEquals("Test", inv.getType());

    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForAdj() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                                            constructRequestCoreDeliveryChargesAdjustmentPayment();
        adjPay.setTransactionType(ROIConstants.ADJUSTMENT_TYPE);
        adjPay.setIsDebit(false);

        adjPay.toCreditUpdateAudit(1, true, true,
                                   adjPay.getInvoiceAppliedAmount(),
                                   adjPay.getBaseAmount(),
                                   adjPay.getTotalUnappliedAmount(),
                                   adjPay.getTransactionType(),
                                   adjPay.getPaymentMode(),
                                   adjPay.getDescription());

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForIsDebitTrue() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();
        adjPay.setTransactionType(null);
        adjPay.setIsDebit(true);

        adjPay.toCreditUpdateAudit(1, true, true,
                adjPay.getInvoiceAppliedAmount(),
                adjPay.getBaseAmount(),
                adjPay.getTotalUnappliedAmount(),
                adjPay.getTransactionType(),
                adjPay.getPaymentMode(),
                adjPay.getDescription());

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForAutoAdj() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();
        adjPay.setPaymentMode(ROIConstants.AUTOMATIC);

        adjPay.toCreditUpdateAudit(1, true, true,
                adjPay.getInvoiceAppliedAmount(),
                adjPay.getBaseAmount(),
                adjPay.getTotalUnappliedAmount(),
                adjPay.getTransactionType(),
                adjPay.getPaymentMode(),
                adjPay.getDescription());

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForIsDebitFalse() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();
        adjPay.setTransactionType(null);
        adjPay.setPaymentMode(ROIConstants.AUTOMATIC);

        adjPay.toCreditUpdateAudit(1, true, 1.0, 1.0);

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForPayment() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();
        adjPay.setTransactionType(ROIConstants.PAYMENT_TYPE);
        adjPay.setPaymentMode(ROIConstants.AUTOMATIC);

        adjPay.toCreditUpdateAudit(1, true, 1.0, 1.0);

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelForIsDebit() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();

        adjPay.setTransactionType(ROIConstants.PAYMENT_TYPE);
        adjPay.setPaymentMode(ROIConstants.AUTOMATIC);

        adjPay.toAutoAdjustmentComment(1, false, 1.0, 1.0);

        assertNotNull(adjPay.toDebitUpdateAudit(1, true));
    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelAdj() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();

        adjPay.setTransactionType(ROIConstants.ADJUSTMENT_TYPE);
        adjPay.setPaymentMode(ROIConstants.MANUAL);

        adjPay.toCreditUpdateAudit(1, false, 1.0, 1.0);

    }

    public void testRequestCoreDeliveryChargesAdjustmentPaymentModelIsDebit() {

        RequestCoreDeliveryChargesAdjustmentPayment adjPay =
                constructRequestCoreDeliveryChargesAdjustmentPayment();

        adjPay.setTransactionType(null);
        adjPay.setPaymentMode(ROIConstants.AUTOMATIC);
        adjPay.toCreditUpdateAudit(1, true, false, 1.0, 1.0, 1.0, null, "Test", "Test");

    }

    public void testInvoiceAndLetterInfoModel() {

        InvoiceAndLetterInfo invLetterInfo = new InvoiceAndLetterInfo();

        List<String> reqNotes = new ArrayList<String>();
        reqNotes.add("Test");
        List<String> invNotes = new ArrayList<String>();
        reqNotes.add("Test");

        List<RequestorInvoices> reqInvList = new ArrayList<RequestorInvoices>();
        long[] invIds = {1,2};
        RequestorInvoices reqInv = new RequestorInvoices();
        reqInv.setRequestorId(10);
        reqInv.setRequestorName("Test");
        reqInv.setRequestorType("Test");
        reqInv.setInvoiceIds(invIds);
        reqInvList.add(reqInv);

        assertNotNull(reqInv);
        assertEquals(10, reqInv.getRequestorId());
        assertEquals("Test", reqInv.getRequestorName());
        assertEquals("Test", reqInv.getRequestorType());
        assertNotNull(reqInv.getInvoiceIds());

        invLetterInfo.setRequestorLetterTemplateId(0);
        invLetterInfo.setLetterTemplateId(0);
        invLetterInfo.setInvoiceTemplateId(0);
        invLetterInfo.setIsNewInvoice(true);
        invLetterInfo.setIsLetter(true);
        invLetterInfo.setOutputInvoice(true);
        invLetterInfo.setIsPastInvoice(true);
        invLetterInfo.setReqLetterNotes(reqNotes);
        invLetterInfo.setInvoiceNotes(reqNotes);
        invLetterInfo.setInvoices(reqInvList);
        invLetterInfo.setPastInvIds(invIds);

        assertEquals(invLetterInfo.getRequestorLetterTemplateId(), 0);
        assertEquals(invLetterInfo.getInvoiceTemplateId(), 0);
        assertEquals(invLetterInfo.getLetterTemplateId(), 0);
        assertTrue(invLetterInfo.isOutputInvoice());
        assertTrue(invLetterInfo.getIsLetter());
        assertTrue(invLetterInfo.getIsNewInvoice());
        assertTrue(invLetterInfo.getIsPastInvoice());
        assertNotNull(invLetterInfo.getInvoiceNotes());
        assertNotNull(invLetterInfo.getReqLetterNotes());
        assertNotNull(invLetterInfo.getInvoices());
        assertNotNull(invLetterInfo.getPastInvIds());
    }

}
