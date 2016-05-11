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

package com.mckesson.eig.roi.request.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.billing.model.SalesTaxSummary;
import com.mckesson.eig.roi.billing.service.BillingCoreService;
import com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author OFS
 * @date   Jul 2, 2009
 * @since  HPF 13.1 [ROI]; Sep 18, 2008
 */
public class TestRequestService
extends BaseROITestCase {

    protected static final String REQUEST_SERVICE =
        "com.mckesson.eig.roi.request.service.RequestServiceImpl";

    protected static final String  BILLING_SERVICE =
        "com.mckesson.eig.roi.billing.service.BillingServiceImpl";

    private static RequestCoreService _requestService;
    private static BillingCoreService _billingService;

    private static final String PHONE_NUMBER = "123456";
    private static final String NAME = "Test User";
    private static final String PATIENT = "patient";
    private static final String FACILITY = "A";
    private static final long MAX_COUNT = 500;

    public void initializeTestData() throws Exception {
        
       _requestService = (RequestCoreService) getService(RequestCoreServiceImpl.class.getName());
       _billingService = (BillingCoreService) getService(BillingCoreServiceImpl.class.getName());
    }

    /**
     * This test case to generate the request level password
     */
    public void testGetGeneratedPassword() {

        try {
            
            String password = _requestService.getGeneratedPassword();
            assertNotNull(password);
        } catch (Exception e) {
            fail("Generating the request level password failed.");
        }
    }

    /**
     * This test case to create the request
     */
    public void testCreateRequest() {

        try {

            RequestCore request = _requestService.createRequest(setupRequest());
            RequestCore req = _requestService.retrieveRequest(request.getId(), true);
            assertNotSame("The created request id should be greater than zero", 0, request.getId());
            assertEquals(request.getId(), req.getId());
            assertNotNull(req);
        } catch (ROIException e) {
            fail("Creating request should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to delete request
     */
    public void testDeleteRequest() {

        try {

            RequestCore req = setupRequest();
            req.setStatus(ROIConstants.DEFAULT_STATUS_PENDED);
            RequestCore request = _requestService.createRequest(req);

            _requestService.deleteRequest(request.getId());

        } catch (ROIException e) {
            fail("Deleting request should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to delete request having releases
     */
    public void testDeleteRequestHavingReleases() {

        try {

            ReleaseCore rel = releaseCreation();
            long requestId = rel.getRequestId();

            // creating original release
            ReleaseAndPreviewInfo releaseId = _billingService.createReleaseAndPreviewInfo(rel,true,12.0);

            RequestCore request = _requestService.retrieveRequest(rel.getRequestId(),false);

            _billingService.createReleaseAndPreviewInfo(rel,true,12.0);
            _requestService.deleteRequest(requestId);

            fail("Deleting request having release is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELETE_OPERATION_FAILED);
        }
    }

    private RequestCoreCharges setupDraftRelease(long requestId) {
        
        RequestCoreCharges charges = new RequestCoreCharges();
        charges.setRequestCoreSeq(requestId);
        charges.setApplySalesTax(false);
        charges.setBalanceDue(10);
        charges.setBillingLocCode("AD");
        charges.setBillingLocName("AD");
        charges.setBillingType("");
        charges.setCreditAdjustmentAmount(0.00);
        charges.setDebitAdjustmentAmount(0.00);
        charges.setDisplayBillingPaymentInfo(true);
        charges.setPaymentAmount(0.00);
        charges.setPreviouslyReleasedCost(0.00);
        charges.setReleaseCost(10.00);
        charges.setReleased(false);
        charges.setSalesTaxAmount(1.00);
        charges.setSalesTaxPercentage(10.00);
        charges.setTotalPages(10);
        charges.setTotalPagesReleased(0);
        charges.setTotalRequestCost(10.00);
        charges.setUnbillable(false);

        Set<RequestCoreChargesDocument> requestCoreChargesDocument = new HashSet<RequestCoreChargesDocument>();
        RequestCoreChargesDocument doc = new RequestCoreChargesDocument();
        doc.setAmount(3);
        doc.setBillingtierId("-1");
        doc.setBillingTierName("Electronic");
        doc.setCopies(1);
        doc.setHasSalesTax(false);
        doc.setIsElectronic(true);
        doc.setPages(3);
        doc.setSalesTaxAmount(0);
        doc.setTotalPages(3);
        requestCoreChargesDocument.add(doc);
        RequestCoreChargesBilling requestCoreChargesBilling = new RequestCoreChargesBilling();
        requestCoreChargesBilling.setRequestCoreChargesDocument(requestCoreChargesDocument);
        
        Set<RequestCoreChargesFee> requestCoreChargesFee = new HashSet<RequestCoreChargesFee>();
        RequestCoreChargesFee fee = new RequestCoreChargesFee();
        fee.setAmount(4);
        fee.setFeeType("Custom");
        fee.setHasSalesTax(true);
        fee.setIsCustomFee(true);
        fee.setSalesTaxAmount(1);
        requestCoreChargesFee.add(fee);
        
        requestCoreChargesBilling.setRequestCoreChargesFee(requestCoreChargesFee);
        charges.setRequestCoreChargesBilling(requestCoreChargesBilling);
        
        RequestCoreChargesShipping shipping = new RequestCoreChargesShipping();
        shipping.setAddress1("setAddress1");
        shipping.setAddress2("setAddress2");
        shipping.setAddress3("setAddress3");
        shipping.setAddressType("Main Address");
        shipping.setOutputMethod("Print");
        shipping.setShippingCharge(2);
        shipping.setShippingMethod("Print");
        shipping.setShippingUrl("http://roitest.com/");
        shipping.setShippingWeight(0.11);
        
        charges.setRequestCoreChargesShipping(shipping);
        
        SalesTaxSummary summary  = new SalesTaxSummary();
        List<SalesTaxReason> reason = new ArrayList<SalesTaxReason>();
        SalesTaxReason r = new SalesTaxReason();
        r.setReason("Test Sales Tax Summary");
        r.setReasonDate(new Date());
        reason.add(r);
        summary.setSalesTaxReason(reason);
        charges.setSalesTaxSummary(summary);
        
        return charges;
    }

    /**
     * This test case to create the request
     */
    public void testCreateRequestWithDetailsNull() {

        try {

            _requestService.createRequest(new RequestCore());
            fail("Creating request with null details is not accepted, but it created");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_MODEL_IS_EMPTY);
        }
    }

    /**
     * This test case to create request with null and verify if it returns the appropriate
     * exception
     */
    public void testCreateRequestWithNull() {

        try {

            _requestService.createRequest(null);
            fail("Creating request with null is not valid, but it accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
        }
    }

    /**
     * This test case to retrieve the request
     */
    public void testRetrieveRequest() {

        try {

            RequestCore req = setupRequest();
            req.setStatus(ROIConstants.STATUS_CANCELED);

            RequestCore createdRequest = _requestService.createRequest(req);
            RequestCore request = _requestService.retrieveRequest(createdRequest.getId(), false);
            assertNotNull(request);
            assertEquals(createdRequest.getId(), request.getId());

        } catch (ROIException e) {
            fail("Retrieve request should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to retrieve the request with invalid id and verify if it returns
     * the appropriate exception
     */
    public void testRetrieveRequestWithInvalidID() {

        try {

            _requestService.retrieveRequest(0, false);
            fail("Retrieving request with invalid id is not valid, but it accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    /**
     * This test case to delete the request with invalid id and verify if it
     * returns the appropriate exception
     */
    public void testDeleteRequestWithInvalidID() {

        try {

            _requestService.deleteRequest(0);
            fail("Deleting request with invalid id should throw exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    /**
     * This test case to update the request
     */
    public void testUpdateRequest() {

        try {

            RequestCore createRequest = _requestService.createRequest(setupRequest());
            RequestCore req = _requestService.retrieveRequest(createRequest.getId(), false);

            req.setStatus(ROIConstants.STATUS_DENIED);
            req.setStatusReason("reasonupdated");

            RequestCore request = _requestService.updateRequest(req);
            assertEquals(req.getId(), req.getId());

        } catch (ROIException e) {
            fail("Updating request should not throw exception" + e.getErrorCode());
        }
    }

    /**
     * This test case to update the request with null
     * and verify if it returns the appropriate exception
     */
    public void testUpdateRequestWithNull() {

        try {

            _requestService.updateRequest(null);
            fail("Updating request with null is not accepted, but it updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
        }
    }

    /**
     * This test case to create the request with null user and verify if it returns the
     *  appropriate exception
     */
    public void testCreateRequestWithNullUser() {

        try {

            initSession(null);
            _requestService.createRequest(setupRequest());
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     * This test case to retrieve the request
     */
    public void testRetrieveRequestWithNullUser() {

        try {

            initSession();

            RequestCore req = setupRequest();
            req.setStatus(ROIConstants.STATUS_CANCELED);
            RequestCore createdRequest = _requestService.createRequest(req);

            initSession(null);
            RequestCore request = _requestService.retrieveRequest(createdRequest.getId(), false);
            assertEquals(createdRequest.getId(), request.getId());

        } catch (ROIException e) {
           assertError(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     * This test case to delete the request with null user and verify if it returns the
     *  appropriate exception
     */
    public void testDeleteRequestWithNullUser() {

        try {

            initSession();
            RequestCore request = _requestService.createRequest(setupRequest());

            initSession(null);
            _requestService.deleteRequest(request.getId());

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     * This test case to update the request with null user and verify if it
     * returns the appropriate exception
     */
    public void testUpdateRequestWithNullUser() {

        try {

            initSession();
            RequestCore request = _requestService.createRequest(setupRequest());
            RequestCore forUpdate = _requestService.retrieveRequest(request.getId(), false);

            initSession(null);
            _requestService.updateRequest(forUpdate);

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_OPERATION_FAILED);
        }
    }

    /**
     * This test case to update request with patient requestor
     */
    public void testUpdateRequestWithPatientRequestor() {

        try {

            initSession();
            ReleaseCore rel = releaseCreation();
            long requestId = rel.getRequestId();

            RequestCore request = _requestService.retrieveRequest(rel.getRequestId(), false);

            request.setStatus(ROIConstants.STATUS_DENIED);
            request.setStatusReason("reasonupdated");

            RequestCore req = _requestService.updateRequest(request);

        } catch (ROIException e) {
            fail("Updating request should not throw exception" + e.getErrorCode());
        }
    }


    private ReleaseCore releaseCreation() {

        InvoiceOrPrebillAndPreviewInfo releaseInfo = new InvoiceOrPrebillAndPreviewInfo();
        ReleaseCore rel =  new ReleaseCore();

        RequestCore request = setupRequest();
        RequestCore requ = _requestService.createRequest(request);
        
        RequestCoreCharges requestCoreCharges = setupDraftRelease(requ.getId());
        _requestService.saveRequestCoreCharges(requestCoreCharges);
        
        rel.setRequestId(requ.getId());
        rel.setInvoiceRequired(true);
        
        releaseInfo.setRequestCoreId(requ.getId());
        releaseInfo.setBaseCharge(10);
        releaseInfo.setAdjustmentAmount(2);
        releaseInfo.setAmountpaid(3);
        releaseInfo.setInvoiceBalanceDue(5);
        releaseInfo.setRequestStatus("Completed");
        releaseInfo.setRequestDate(new Date());
        releaseInfo.setInvoiceBillingLocCode("AD");
        releaseInfo.setInvoiceBillinglocName("AD");
        releaseInfo.setInvoiceDueDate(new Date());
        releaseInfo.setInvoiceDueDays(0);
        releaseInfo.setInvoicePrebillDate(new Date());
        releaseInfo.setInvoiceSalesTax(1);
        releaseInfo.setLetterTemplateName("Invoice");
        releaseInfo.setLetterTemplateFileId(getLetterTemplateFileId());
        releaseInfo.setLetterType("Invoice");
        releaseInfo.setOutputMethod("Print");
        releaseInfo.setOverwriteDueDate(true);
        releaseInfo.setReleased(true);
        releaseInfo.setType(ROIConstants.TEMPLATE_FILE_TYPE);
        
        rel.setInvoiceOrPrebillAndPreviewInfo(releaseInfo);
        
        return rel;
    }
    
    private RequestCore setupRequest() {

        try {
            
            long requestorId = 1001;
            String reqPassword = _requestService.getGeneratedPassword();

            RequestCore request = new RequestCore();
            request.setRequestPassword(reqPassword);
            request.setRequestReason("Migration");
            request.setStatus("Logged");
            request.setReceiptDate(Calendar.getInstance().getTime());
            request.setStatusChangedDt(Calendar.getInstance().getTime());
            request.setStatusReason("Test");
    
            RequestorCore requestorCore = new RequestorCore();
    
            requestorCore.setFirstName(NAME);
            requestorCore.setCellPhone(PHONE_NUMBER);
            requestorCore.setHomePhone(PHONE_NUMBER);
            requestorCore.setRequestorType(-1L);
            requestorCore.setRequestorTypeName(PATIENT);
            requestorCore.setWorkPhone(PHONE_NUMBER);
            requestorCore.setFax(PHONE_NUMBER);
            requestorCore.setContactName(NAME);
            requestorCore.setContactPhone(PHONE_NUMBER);
            requestorCore.setLastName(NAME);
            requestorCore.setMiddleName("");
            requestorCore.setSuffix(NAME);
            requestorCore.setWorkPhone(PHONE_NUMBER);
            requestorCore.setRequestorSeq(requestorId);
    
            request.setRequestorDetail(requestorCore);
    
            return request;
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException("TestRequestService.getServiceURL()");
    }
    
    @Override
    protected void tearDown() throws Exception {
    }
}
