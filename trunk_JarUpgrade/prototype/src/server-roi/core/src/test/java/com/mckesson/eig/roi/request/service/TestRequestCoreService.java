/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author Karthik Easwaran
 * @date   Jun 3, 2013
 * @since  Jun 3, 2013
 */
public class TestRequestCoreService
extends BaseROITestCase {

    private static RequestCoreService _requestCoreService;

    public void initializeTestData() throws Exception {

        _requestCoreService = (RequestCoreService) getService(RequestCoreServiceImpl.class.getName());
        insertDataSet("test/resources/reports/reportsDataSet.xml");

    };

    public void testRetrieveReleasedDocumentChargesByBillingTier() {
        try {
            _requestCoreService.retrieveReleasedDocumentChargesByBillingTier(1001);
        } catch(Exception e) {
            fail("retrieveReleasedDocumentChargesByBillingTier should not throw exception");
        }
    }

    public void testRetrieveReleasedDocumentChargesByBillingTierInvalidRequestId() {
        try {
            _requestCoreService.retrieveReleasedDocumentChargesByBillingTier(0);
        } catch(ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testHasSecurityRightsForRelease() {
        try {
            _requestCoreService.hasSecurityRightsForRelease();
        } catch(ROIException e) {
            fail("Should not throw Exception");
        }
    }

    public void testRetrieveRequestInvoices() {
        try {
            _requestCoreService.retrieveRequestInvoices(1001);
        } catch(ROIException e) {
            fail("Should not throw Exception");
        }
    }

    public void testRetrieveRequestInvoicesWithInvalidId() {
        try {
            _requestCoreService.retrieveRequestInvoices(-1);
        } catch(ROIException e) {
           assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testsaveRequestCoreCharges() {

        try {

            RequestCoreCharges requestCoreCharges = new RequestCoreCharges();
            requestCoreCharges.setRequestCoreSeq(1001);
            requestCoreCharges.setPreviouslyReleasedCost(45.90);
            requestCoreCharges.setReleaseDate(new Date());
            requestCoreCharges.setReleaseCost(67.88);
            requestCoreCharges.setTotalPages(5);
            requestCoreCharges.setTotalRequestCost(7878.98);
            requestCoreCharges.setBalanceDue(89.98);
            requestCoreCharges.setTotalPagesReleased(6);
            requestCoreCharges.setSalesTaxPercentage(12.23);
            requestCoreCharges.setBillingType("Adj");
            requestCoreCharges.setApplySalesTax(true);
            requestCoreCharges.setBalanceDue(100);
            requestCoreCharges.setBillingType("Testy");
            requestCoreCharges.setSalesTaxPercentage(100);
            requestCoreCharges.setSalesTaxAmount(12);
            requestCoreCharges.setPreviouslyReleasedCost(100);
            requestCoreCharges.setPaymentAmount(200);
            requestCoreCharges.setPaymentAmount(300);
            requestCoreCharges.setOriginalBalance(400);
            requestCoreCharges.setDebitAdjustmentAmount(200);
            requestCoreCharges.setCreditAdjustmentAmount(500);
            requestCoreCharges.setBillingLocCode("AD");
            requestCoreCharges.setBillingLocName("AD");


            RequestCoreChargesDocument requestCoreChargesDocument1 =
                                                new RequestCoreChargesDocument();
            requestCoreChargesDocument1.setModifiedDt(new Date());
            requestCoreChargesDocument1.setModifiedBy(10);
            requestCoreChargesDocument1.setCreatedDt(new Date());
            requestCoreChargesDocument1.setCreatedBy(10);
            requestCoreChargesDocument1.setAmount(4545);
            requestCoreChargesDocument1.setCopies(9);
            requestCoreChargesDocument1.setTotalPages(89);
            requestCoreChargesDocument1.setReleaseCount(7);
            requestCoreChargesDocument1.setIsElectronic(true);
            requestCoreChargesDocument1.setRemoveBaseCharge(true);
            requestCoreChargesDocument1.setHasSalesTax(true);
            requestCoreChargesDocument1.setSalesTaxAmount(89.76);
            requestCoreChargesDocument1.setBillingtierId("-1");
            requestCoreChargesDocument1.setBillingTierName("HPFBillingTier");
            requestCoreChargesDocument1.setPages(45);

            RequestCoreChargesDocument requestCoreChargesDocument2 =
                                                new RequestCoreChargesDocument();
            requestCoreChargesDocument2.setModifiedDt(new Date());
            requestCoreChargesDocument2.setModifiedBy(10);
            requestCoreChargesDocument2.setCreatedDt(new Date());
            requestCoreChargesDocument2.setCreatedBy(10);
            requestCoreChargesDocument2.setAmount(5656);
            requestCoreChargesDocument2.setCopies(10);
            requestCoreChargesDocument2.setTotalPages(54);
            requestCoreChargesDocument2.setReleaseCount(9);
            requestCoreChargesDocument2.setIsElectronic(false);
            requestCoreChargesDocument2.setRemoveBaseCharge(false);
            requestCoreChargesDocument2.setHasSalesTax(false);
            requestCoreChargesDocument2.setSalesTaxAmount(76.98);
            requestCoreChargesDocument2.setBillingtierId("-2");
            requestCoreChargesDocument2.setBillingTierName("MPFBillingTier");
            requestCoreChargesDocument2.setPages(56);


            RequestCoreChargesFee requestCoreChargesFee1 = new RequestCoreChargesFee();
            requestCoreChargesFee1.setModifiedDt(new Date());
            requestCoreChargesFee1.setModifiedBy(10);
            requestCoreChargesFee1.setCreatedDt(new Date());
            requestCoreChargesFee1.setCreatedBy(10);
            requestCoreChargesFee1.setAmount(2323);
            requestCoreChargesFee1.setFeeType("FeeType1");
            requestCoreChargesFee1.setHasSalesTax(false);
            requestCoreChargesFee1.setSalesTaxAmount(200);
            requestCoreChargesFee1.setIsCustomFee(true);

            RequestCoreChargesFee requestCoreChargesFee2 = new RequestCoreChargesFee();
            requestCoreChargesFee2.setModifiedDt(new Date());
            requestCoreChargesFee2.setModifiedBy(10);
            requestCoreChargesFee2.setCreatedDt(new Date());
            requestCoreChargesFee2.setCreatedBy(10);
            requestCoreChargesFee2.setAmount(3232);
            requestCoreChargesFee2.setFeeType("FeeType2");
            requestCoreChargesFee2.setHasSalesTax(false);
            requestCoreChargesFee2.setSalesTaxAmount(100);
            requestCoreChargesFee2.setIsCustomFee(true);


            RequestCoreChargesShipping requestCoreChargesShipping =
                                                new RequestCoreChargesShipping();
            requestCoreChargesShipping.setModifiedDt(new Date());
            requestCoreChargesShipping.setModifiedBy(10);
            requestCoreChargesShipping.setCreatedDt(new Date());
            requestCoreChargesShipping.setCreatedBy(10);
            requestCoreChargesShipping.setAddress1("AD1");
            requestCoreChargesShipping.setAddress2("AD2");
            requestCoreChargesShipping.setAddress3("AD3");
            requestCoreChargesShipping.setAddressType("ADType");
            requestCoreChargesShipping.setCity("AlpharettaCity");
            requestCoreChargesShipping.setCountryCode("USA");
            requestCoreChargesShipping.setOutputMethod("Print");
            requestCoreChargesShipping.setShippingMethod("ShippingMethod");
            requestCoreChargesShipping.setNonPrintableAttachmentQueue("Test Queue");
            requestCoreChargesShipping.setOutputMethod("om");
            requestCoreChargesShipping.setWillReleaseShipped(false);
            requestCoreChargesShipping.setTrackingNumber("Test");
            requestCoreChargesShipping.setState("KA");
            requestCoreChargesShipping.setShippingWeight(100);
            requestCoreChargesShipping.setShippingUrl("TestGoogle");


            Set<RequestCoreChargesDocument> requestCoreChargesDocumentSet =
                                            new HashSet<RequestCoreChargesDocument>();
            requestCoreChargesDocumentSet.add(requestCoreChargesDocument1);
            requestCoreChargesDocumentSet.add(requestCoreChargesDocument2);

            Set<RequestCoreChargesFee> requestCoreChargesFeeSet =
                                            new HashSet<RequestCoreChargesFee>();
            requestCoreChargesFeeSet.add(requestCoreChargesFee1);
            requestCoreChargesFeeSet.add(requestCoreChargesFee2);

            RequestCoreChargesBilling requestCoreChargesBilling = new RequestCoreChargesBilling();
            requestCoreChargesBilling.setRequestCoreChargesFee(requestCoreChargesFeeSet);
            requestCoreChargesBilling.setRequestCoreChargesDocument(requestCoreChargesDocumentSet);


            requestCoreCharges.setRequestCoreChargesShipping(requestCoreChargesShipping);
            requestCoreCharges.setRequestCoreChargesBilling(requestCoreChargesBilling);

            _requestCoreService.saveRequestCoreCharges(requestCoreCharges);
        } catch (ROIException e) {
            fail("Inserting the RequestCoreCharges values failed.");
        }
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return null;
    }

}
