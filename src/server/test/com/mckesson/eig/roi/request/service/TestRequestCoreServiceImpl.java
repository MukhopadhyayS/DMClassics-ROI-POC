/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.billing.model.SalesTaxSummary;
import com.mckesson.eig.roi.request.model.AuditAndEventList;
import com.mckesson.eig.roi.request.model.DeletePatientList;
import com.mckesson.eig.roi.request.model.FreeFormFacility;
import com.mckesson.eig.roi.request.model.PaginationData;
import com.mckesson.eig.roi.request.model.ProductivityReportDetails;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesInvoice;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResult;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResultList;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestPatientsList;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;
import com.mckesson.eig.roi.request.model.SaveRequestPatientsList;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author OFS
 * @date   Jul 5, 2012
 * @since  Jul 5, 2012
 */
public class TestRequestCoreServiceImpl
extends BaseROITestCase {

    protected static final String REQUESTOR_SERVICE =
            "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";

    private static RequestCoreService _requestCoreService;
    private static RequestCore _request;
    private static RequestorService _requestorService;
    private static RequestPatientsList _requestPatientList = new RequestPatientsList();
    private static SaveRequestPatientsList _savePatientList = new SaveRequestPatientsList();

    private static long _requestCoreSeq = 7;
    private static long _patientSeq = 0;
    private static long _hpfPatientSeq = 0;
    private static long _suppPatientSeq = 0;
    private static long _encounterSeq = 0;
    private static long _documentSeq = 0;
    private static long _docId = 0;
    private static long _versionSeq = 0;
    private static long _pageSeq = 0;
    private static int _five = 5;

    private static IDatabaseConnection conn = null;
    private static FlatXmlDataSet dataSet = null;

    private static final String PHONE_NUMBER = "123456";
    private static final String NAME = "Test User";
    private static final String PATIENT = "patient";
    private static final String FACILITY = "A";
    private static final long MAX_COUNT = 500;


    public void initializeTestData() throws Exception {

       _requestCoreService = (RequestCoreService)
                                   getService(RequestCoreServiceImpl.class.getName());
       _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);
       _request = new RequestCore();
       insertDataSet("test/resources/reports/reportsDataSet.xml");
       _requestPatientList = _requestCoreService.retrieveRequestPatient(1001);
    }

    public void testCreateRequest() {

        try {

            _request = _requestCoreService.createRequest(setupRequest());
            assertNotNull(_request);

        } catch (ROIException e) {
            fail("Creating Request has thrown exception" + e.getErrorCode());
        }
    }

    public void testRetrieveRequests() {

        try {

            RequestCore retrievedRequest = _requestCoreService.retrieveRequest(
                    _request.getId(), true);
            assertNotNull(retrievedRequest);

        } catch (ROIException e) {
            fail("Retrieving Request has thrown exception" + e.getErrorCode());
        }
    }



    public void testCreateRequestWithInvalidRequest() {

        try {

            _requestCoreService.createRequest(null);
            fail("Create Request with Invalid Request is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
        }
    }

    public void testCreateRequestWithInvalidRequestor() {

        try {

             RequestCore request = _request;
             request.setRequestorDetail(null);
             _requestCoreService.createRequest(request);
             fail("Create Request with Invalid Requestor is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_MODEL_IS_EMPTY);
        }
    }

    public void testSearchRequestWithPaginationData() {

        try {

            RequestCoreSearchCriteria criteria = constructSearchCriteria();
            PaginationData pagination = new PaginationData();
            pagination.setCount(500);
            pagination.setStartIndex(0);
            criteria.setPaginationData(pagination);

             assertNotNull(_requestCoreService.searchRequest(criteria));

        } catch (ROIException e) {
            fail("search Request has thrown exception" + e.getErrorCode());
        }
    }


    public void testRetrieveRequest() {

        try {

            RequestCore retrievedRequest = _requestCoreService.retrieveRequest(_request.getId(),true);
            assertNotNull(retrievedRequest);

        } catch (ROIException e) {
            fail("Retrieving Request has thrown exception" + e.getErrorCode());
        }
    }

    public void testRetrieveRequestWithInvalidRequestId() {

        try {

             _requestCoreService.retrieveRequest(0, true);
             fail("Retrieve Request with Invalid RequestId is not permitted ,but retrieved ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testSaveRequestPatient() {

        try {

            SaveRequestPatientsList requestPatients = setRequestPatientValue();
            _requestPatientList = _requestCoreService.saveRequestPatient(requestPatients);
            assertNotNull(_requestPatientList);
        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testSaveRequestPatientWithSupplementalDocument() {

        try {

            SaveRequestPatientsList requestPatients = setRequestPatientValue();
            requestPatients.setUpdatePatients(new ArrayList<RequestPatient>());

            RequestPatient requestPatient = _requestPatientList.getRequestPatients().get(0);
            _patientSeq = requestPatient.getPatientSeq();

            List<RequestSupplementalDocument> document = constructRequestSupplementalDocument(0, requestPatient.getMrn(), requestPatient.getFacility(), true);
            for (RequestSupplementalDocument doc : document) {
                doc.setPatientSeq(_patientSeq);
            }

            requestPatients.setUpdateSupplementalDocument(document);

            assertNotNull(_requestCoreService.saveRequestPatient(requestPatients));
        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testSaveRequestPatientWithSupplementalAttachment() {

        try {

            SaveRequestPatientsList requestPatients = setRequestPatientValue();
            requestPatients.setUpdatePatients(new ArrayList<RequestPatient>());

            RequestPatient requestPatient = _requestPatientList.getRequestPatients().get(0);
            _patientSeq = requestPatient.getPatientSeq();

            List<RequestSupplementalAttachment> attachments = constructRequestAttachment(0, requestPatient.getMrn(), requestPatient.getFacility(), true);
            for (RequestSupplementalAttachment attachment : attachments) {
                attachment.setPatientSeq(_patientSeq);
            }

            requestPatients.setUpdateSupplementalAttachement(attachments);
            assertNotNull(_requestCoreService.saveRequestPatient(requestPatients));
        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testSaveRequestPatientWithNewEncounters() {

        try {

            SaveRequestPatientsList patientList = new SaveRequestPatientsList();
            patientList.setRequestId(_requestPatientList.getRequestId());

            List<RequestPatient> requestPatientsList = _requestPatientList.getRequestPatients();
            RequestPatient hpfrequestPatient = new RequestPatient();
            RequestPatient supprequestPatient  = new RequestPatient();
            for (RequestPatient requestPatient : requestPatientsList) {

                if (requestPatient.isHpf()) {
                    hpfrequestPatient = requestPatient;
                    _hpfPatientSeq = requestPatient.getPatientSeq();
                } else {
                    supprequestPatient = requestPatient;
                    _suppPatientSeq = requestPatient.getPatientSeq();
                }

                if (hpfrequestPatient.getPatientSeq() != 0 && supprequestPatient.getPatientSeq() != 0)
                    break;

            }

            patientList.setUpdateEncounters(constructRequestEncounters(_hpfPatientSeq, hpfrequestPatient.getMrn(), hpfrequestPatient.getFacility()));

            RequestEncounter roiEncounters = hpfrequestPatient.getRoiEncounters().get(0);
            _encounterSeq = roiEncounters.getEncounterSeq();

            List<RequestDocument> constructRequestDocuments = constructRequestDocuments(_encounterSeq, roiEncounters.getMrn(),
                    roiEncounters.getFacility(), roiEncounters.getName(), roiEncounters.getRoiDocuments().get(0).getDocId());
            for (RequestDocument doc : constructRequestDocuments) {
                doc.setPatientSeq(_patientSeq);
            }
            patientList.setUpdateDocuments(constructRequestDocuments);

            RequestDocument document = roiEncounters.getRoiDocuments().get(0);
            _documentSeq = document.getDocumentSeq();
            _docId = document.getDocId();

            patientList.setUpdateVersions(constructRequestVersions(_documentSeq, _docId));

            RequestVersion version = document.getRoiVersions().get(0);
            _versionSeq = version.getVersionSeq();

            patientList.setUpdatePages(constructRequestPages(_versionSeq));

            RequestPage page = version.getRoiPages().get(0);
            _pageSeq = page.getPageSeq();

            _requestPatientList = _requestCoreService.saveRequestPatient(patientList);
            assertNotNull(_requestPatientList);
        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testSaveRequestPatientWithPages() {

        try {

            SaveRequestPatientsList requestPatients = setRequestPatientValue();
            List<RequestPage> pages = new ArrayList<RequestPage>();
            RequestPage requestPage = new RequestPage();
            requestPage.setPageSeq(1L);
            pages.add(requestPage);
            requestPatients.setUpdatePages(pages);

            _requestPatientList = _requestCoreService.saveRequestPatient(requestPatients);
            assertNotNull(_requestPatientList);
        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testCreateRequestPatientWithInvalidRequestId() {

        try {

            _savePatientList = setRequestPatientValue();
             _savePatientList.setRequestId(0);
             _requestCoreService.saveRequestPatient(_savePatientList);
             fail("Create Request patient with Invalid RequestId is not permitted ,but retrieved ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_CORE_ID_IS_EMPTY);
        }
    }

    public void testRetrieveRequestPatient() {

        try {

             RequestPatientsList patient =
                                 _requestCoreService.retrieveRequestPatient(1001);
             assertNotNull(patient);
        } catch (ROIException e) {
            fail("Retrieve Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testRetrieveRequestPatientWithInvalidId() {

        try {

            _requestCoreService.retrieveRequestPatient(0);
            fail("Retrieve Request patient with Invalid RequestId is not permitted ,"
                                                                                + "but retrieved ");
            } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testSaveRequestPatientWithInvalidPatientDetails() {

        try {

            _requestCoreService.saveRequestPatient(null);
            fail("Save Request patient with Invalid patient is not permitted ,but retrieved ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
        }
    }

    public void testSaveRequestPatientWithDeletePatientDetails() {

        try {

            SaveRequestPatientsList requestPatients = setRequestPatientValue();
            RequestPatientsList requestPatientList =
                                    _requestCoreService.saveRequestPatient(requestPatients);

            String mrn = requestPatients.getUpdatePatients().get(0).getMrn();
            _requestCoreService.saveRequestPatient(constructDeletePatient(requestPatientList, mrn));

        } catch (ROIException e) {
            fail("Create Request patient should not throw exception" + e.getErrorCode());
        }
    }

    public void testUpdateRequest() {

        try {

            RequestCore request = setupRequest();
            request.setId(_request.getId());
            RequestCore retrievedRequest  = _requestCoreService.updateRequest(request);
            assertNotNull(retrievedRequest);

        } catch (ROIException e) {
            fail("update Request has thrown exception" + e.getErrorCode());
        }
    }

    public void testUpdateRequestWithInvalidRequestId() {

        try {

            _requestCoreService.updateRequest(setupRequest());
            fail("Update Request with Invalid Request Id is not permitted ,but updated ");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testUpdateRequestWithInvalidRequestorDetails() {

        try {

            RequestCore request = _request;
            request.setRequestorDetail(null);
            _requestCoreService.updateRequest(_request);
            fail("Update Request with Invalid Request is not permitted ,but updated ");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_MODEL_IS_EMPTY);
        }
    }

    public void testUpdateRequestWithNullRequest() {

        try {

             _requestCoreService.updateRequest(null);
            fail("Retrieve Request with Invalid RequestId is not permitted ,but retrieved ");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_MODEL_IS_EMPTY);
        }
    }

    public void testSearchRequest() {

        try {

             assertNotNull(_requestCoreService.searchRequest(constructSearchCriteria()));

        } catch (ROIException e) {
            fail("search Request has thrown exception" + e.getErrorCode());
        }
    }

    public void testSearchRequestwithInvalidCritria() {

        try {

            assertNotNull(_requestCoreService.searchRequest(null));
            fail("Search Request with Invalid Criteria is not permitted ,but retrieved ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
        }
    }

    private RequestCoreSearchCriteria constructSearchCriteria() {

        RequestCoreSearchCriteria searchCriteria = new RequestCoreSearchCriteria();
        searchCriteria.setRequestId(_request.getId());
        searchCriteria.setMaxCount(MAX_COUNT);
        return searchCriteria;
    }


    public void testsaveRequestCoreCharges() {

        try {

            RequestCoreCharges requestCoreCharges = new RequestCoreCharges();
            requestCoreCharges.setRequestCoreSeq(_request.getId());
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
            requestCoreCharges.setBillingLocCode(FACILITY);
            requestCoreCharges.setBillingLocName(FACILITY);


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

    public void testSaveRequestCoreBillingPaymentInfo(){

        try{

            RequestCore request = _requestCoreService.createRequest(setupRequest());
            RequestCoreCharges requestCoreCharges = new RequestCoreCharges();
            requestCoreCharges.setModifiedDt(new Date());
            requestCoreCharges.setModifiedBy(3);
            requestCoreCharges.setRequestCoreSeq(request.getId());
            requestCoreCharges.setBillingType("TestBillingType");
            requestCoreCharges.setBalanceDue(111);
            requestCoreCharges.setPreviouslyReleasedCost(111);
            requestCoreCharges.setTotalRequestCost(111);
            requestCoreCharges.setTotalPagesReleased(111);
            requestCoreCharges.setTotalPages(111);
            requestCoreCharges.setSalesTaxAmount(111);
            requestCoreCharges.setSalesTaxPercentage(111);
            requestCoreCharges.setReleaseDate(new Date());
            requestCoreCharges.setReleaseCost(111);
            requestCoreCharges.setApplySalesTax(true);
            requestCoreCharges.setSalesTaxPercentage(111);
            requestCoreCharges.setSalesTaxAmount(111);
            requestCoreCharges.setPreviouslyReleasedCost(111);
            requestCoreCharges.setPaymentAmount(111);
            requestCoreCharges.setPaymentAmount(111);
            requestCoreCharges.setOriginalBalance(111);
            requestCoreCharges.setDebitAdjustmentAmount(111);
            requestCoreCharges.setCreditAdjustmentAmount(111);
            requestCoreCharges.setBillingLocCode(FACILITY);
            requestCoreCharges.setBillingLocName(FACILITY);


            RequestCoreChargesDocument requestCoreChargesDocument1 =
                                                new RequestCoreChargesDocument();
            requestCoreChargesDocument1.setModifiedDt(new Date());
            requestCoreChargesDocument1.setModifiedBy(1);
            requestCoreChargesDocument1.setAmount(100);
            requestCoreChargesDocument1.setBillingTierName("MPFBillingTier");
            requestCoreChargesDocument1.setPages(1);
            requestCoreChargesDocument1.setBillingtierId("-2");
            requestCoreChargesDocument1.setCopies(1);
            requestCoreChargesDocument1.setIsElectronic(false);
            requestCoreChargesDocument1.setReleaseCount(1);
            requestCoreChargesDocument1.setRemoveBaseCharge(false);
            requestCoreChargesDocument1.setTotalPages(1);
            requestCoreChargesDocument1.setHasSalesTax(true);
            requestCoreChargesDocument1.setSalesTaxAmount(100);

            RequestCoreChargesDocument requestCoreChargesDocument2 =
                                            new RequestCoreChargesDocument();
            requestCoreChargesDocument2.setModifiedDt(new Date());
            requestCoreChargesDocument2.setModifiedBy(2);
            requestCoreChargesDocument2.setAmount(200);
            requestCoreChargesDocument2.setBillingTierName("MPFBillingTier");
            requestCoreChargesDocument2.setPages(2);
            requestCoreChargesDocument2.setBillingtierId("-2");
            requestCoreChargesDocument2.setCopies(2);
            requestCoreChargesDocument2.setIsElectronic(true);
            requestCoreChargesDocument2.setReleaseCount(2);
            requestCoreChargesDocument2.setRemoveBaseCharge(true);
            requestCoreChargesDocument2.setTotalPages(2);
            requestCoreChargesDocument2.setHasSalesTax(true);
            requestCoreChargesDocument2.setSalesTaxAmount(100);

            Set<RequestCoreChargesDocument> requestCoreChargesDocument =
                                            new HashSet<RequestCoreChargesDocument>();
            requestCoreChargesDocument.add(requestCoreChargesDocument1);
            requestCoreChargesDocument.add(requestCoreChargesDocument2);

            RequestCoreChargesFee requestCoreChargesFee1 = new RequestCoreChargesFee();
            requestCoreChargesFee1.setModifiedDt(new Date());
            requestCoreChargesFee1.setModifiedBy(1);
            requestCoreChargesFee1.setAmount(100);
            requestCoreChargesFee1.setFeeType("TestDoc1");
            requestCoreChargesFee1.setIsCustomFee(false);
            requestCoreChargesFee1.setHasSalesTax(true);
            requestCoreChargesFee1.setSalesTaxAmount(100);


            RequestCoreChargesFee requestCoreChargesFee2 = new RequestCoreChargesFee();
            requestCoreChargesFee2.setModifiedDt(new Date());
            requestCoreChargesFee2.setModifiedBy(2);
            requestCoreChargesFee2.setAmount(200);
            requestCoreChargesFee2.setFeeType("TestDoc2");
            requestCoreChargesFee2.setIsCustomFee(true);
            requestCoreChargesFee2.setHasSalesTax(true);
            requestCoreChargesFee2.setSalesTaxAmount(100);

            Set<RequestCoreChargesFee> requestCoreChargesFee = new HashSet<RequestCoreChargesFee>();
            requestCoreChargesFee.add(requestCoreChargesFee1);
            requestCoreChargesFee.add(requestCoreChargesFee2);

            RequestCoreChargesBilling requestCoreChargesBilling = new RequestCoreChargesBilling();
            requestCoreChargesBilling.setRequestCoreChargesDocument(requestCoreChargesDocument);
            requestCoreChargesBilling.setRequestCoreChargesFee(requestCoreChargesFee);


            RequestCoreChargesShipping requestCoreChargesShipping =
                                            new RequestCoreChargesShipping();
            requestCoreChargesShipping.setModifiedDt(new Date());
            requestCoreChargesShipping.setModifiedBy(22);
            requestCoreChargesShipping.setAddress1("Test1Updated");
            requestCoreChargesShipping.setAddress2("Test2Updated");
            requestCoreChargesShipping.setAddress3("Test3Updated");
            requestCoreChargesShipping.setAddressType("TestTypeUpdated");
            requestCoreChargesShipping.setCity("TestCityUpdated");
            requestCoreChargesShipping.setCountryCode("TestCountryUpdated");
            requestCoreChargesShipping.setOutputMethod("TestOPMethodUpdated");
            requestCoreChargesShipping.setShippingMethod("TestSPMethodUpdated");
            requestCoreChargesShipping.setPostalCode("Test-101");
            requestCoreChargesShipping.setShippingCharge(300);
            requestCoreChargesShipping.setShippingMethodId(100);
            requestCoreChargesShipping.setShippingUrl("google");
            requestCoreChargesShipping.setShippingWeight(100);
            requestCoreChargesShipping.setState("KA");
            requestCoreChargesShipping.setTrackingNumber("Test");


            RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment = new RequestCoreDeliveryChargesAdjustmentPayment();
            requestCoreDeliveryChargesAdjustmentPayment.setBaseAmount(222);
            requestCoreDeliveryChargesAdjustmentPayment.setPaymentDate(new Date());
            requestCoreDeliveryChargesAdjustmentPayment.setDescription("Test Payment 222");
            requestCoreDeliveryChargesAdjustmentPayment.setIsDebit(true);
            requestCoreDeliveryChargesAdjustmentPayment.setPaymentMode("check");
            requestCoreDeliveryChargesAdjustmentPayment.setTransactionType("TRANSFER");


            RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment1 = new RequestCoreDeliveryChargesAdjustmentPayment();
            requestCoreDeliveryChargesAdjustmentPayment1.setBaseAmount(111);
            requestCoreDeliveryChargesAdjustmentPayment1.setPaymentDate(new Date());
            requestCoreDeliveryChargesAdjustmentPayment1.setDescription("Test Payment 111");
            requestCoreDeliveryChargesAdjustmentPayment1.setIsDebit(true);
            requestCoreDeliveryChargesAdjustmentPayment1.setPaymentMode("check");
            requestCoreDeliveryChargesAdjustmentPayment1.setTransactionType("DEBIT");


            Set<RequestCoreDeliveryChargesAdjustmentPayment> requestCoreDeliveryChargesAdjustmentPaymentList =
                    new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>();
            requestCoreDeliveryChargesAdjustmentPaymentList.add(requestCoreDeliveryChargesAdjustmentPayment);
            requestCoreDeliveryChargesAdjustmentPaymentList.add(requestCoreDeliveryChargesAdjustmentPayment1);

            RequestCoreChargesInvoice requestCoreChargesInvoice1 = new RequestCoreChargesInvoice();

            requestCoreChargesInvoice1.setRequestCoreDeliveryChargesAdjustmentPayment(requestCoreDeliveryChargesAdjustmentPaymentList);

            Set<RequestCoreChargesInvoice> requestCoreChargesInvoiceSet =
                                            new HashSet<RequestCoreChargesInvoice>();
            requestCoreChargesInvoiceSet.add(requestCoreChargesInvoice1);


            SalesTaxSummary salesTaxSummary = new SalesTaxSummary();
            SalesTaxReason salesTaxReason = new SalesTaxReason();
            salesTaxReason.setModifiedDt(new Date());
            salesTaxReason.setModifiedBy(1);
            salesTaxReason.setReason("Payment Done");
            salesTaxReason.setReasonDate(new Date());
            List<SalesTaxReason> salesTaxReasonList = new ArrayList<SalesTaxReason>();
            salesTaxReasonList.add(salesTaxReason);
            salesTaxSummary.setSalesTaxReason(salesTaxReasonList);


            requestCoreCharges.setRequestCoreChargesBilling(requestCoreChargesBilling);
            requestCoreCharges.setRequestCoreChargesShipping(requestCoreChargesShipping);
            requestCoreCharges.setSalesTaxSummary(salesTaxSummary);

            _requestCoreService.saveRequestCoreCharges(requestCoreCharges);
            _requestCoreSeq = request.getId();
        } catch (Exception e) {
            fail("updateRequestCoreCharges failed");
        }
    }

    public void testRetrieveRequestBillingPaymentInfo() {

        try {

            RequestCoreCharges requestCoreChargesBillingInfo
                = _requestCoreService.retrieveRequestCoreCharges(_requestCoreSeq);
            assertEquals(true, requestCoreChargesBillingInfo != null);
        } catch (Exception e) {
            fail("retrieveRequestBillingPaymentInfo failed");
        }
    }

    public void testDeleteRequest() {

        try {
            _requestCoreService.deleteRequest(_request.getId());
        } catch (ROIException e) {
            fail("Delete request should not throw exception");
        }
    }

    public void testDeleteRequestWithInvalidId() {

        try {
            _requestCoreService.deleteRequest(0);
            fail("Delete Request with Invalid Request Id is not permitted ,but deleted ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testRetrieveRequestWithNonExistRequestId() {

        try {

             _requestCoreService.retrieveRequest(_request.getId(),false);
             fail("Retrieve Request with Invalid RequestId is not permitted ,but retrieved ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.RETRIEVE_OPERATION_FAILED);
        }
    }

    public void testRequestCoreSearchCriteriaModel() {

        RequestCoreSearchCriteria criteria = new RequestCoreSearchCriteria();
        PaginationData pagination = new PaginationData();
        pagination.setCount(500);
        pagination.setStartIndex(0);
        criteria.setRequestId(_request.getId());
        criteria.setMaxCount(MAX_COUNT);
        criteria.setPaginationData(pagination);
        criteria.setFacility("A");
        criteria.setBalanceDue(0);
        criteria.setEncounter("T001");
        criteria.setMrn("M001");
        criteria.setPatientFirstName("Test");
        criteria.setPatientId(11);
        criteria.setRequestStatus("Logged");
        criteria.setInvoiceNumber(0);
        criteria.setPatientLastName("Test");
        criteria.setPatientSsn("Test");
        criteria.setRequestorId(0);
        criteria.setRequestReason("Test");
        criteria.setBalanceDueOperator(">");

        assertEquals(_request.getId(), criteria.getRequestId());
        assertEquals(MAX_COUNT, criteria.getMaxCount());
        assertEquals("A", criteria.getFacility());
        assertEquals(0.0, criteria.getBalanceDue());
        assertEquals("T001", criteria.getEncounter());
        assertEquals("M001", criteria.getMrn());
        assertEquals(11, criteria.getPatientId());
        assertEquals("Logged", criteria.getRequestStatus());
        assertEquals(0, criteria.getInvoiceNumber());
        assertEquals("Test", criteria.getPatientLastName());
        assertEquals("Test", criteria.getPatientSsn());
        assertEquals(0, criteria.getRequestorId());
        assertEquals(">", criteria.getBalanceDueOperator());

    }

    public void testRequestCoreSearchResultModel() {

        RequestCoreSearchResult result = new RequestCoreSearchResult();

        result.setBalance(1.0);
        result.setEncounterString("Test");
        result.setFacility("A");
        result.setPatientLocked(true);
        result.setPatientsString("A");
        result.setRequestId(0);
        result.setRequestorName("A");
        result.setRequestorType(0);
        result.setRequestorTypeName("A");
        result.setUpdatedBy("A");
        result.setRequestorTypeName("A");
        result.setRequestStatus("Logged");
        result.setSubtitle("Test");

        RequestCoreSearchResultList searchResult = new RequestCoreSearchResultList();

        List<RequestCoreSearchResult> requestCoreSearchResult = new ArrayList<RequestCoreSearchResult>();
        requestCoreSearchResult.add(result);
        searchResult.setRequestCoreSearchResult(requestCoreSearchResult);
        searchResult.setMaxCountExceeded(true);
        assertEquals(requestCoreSearchResult, searchResult.getRequestCoreSearchResult());
        assertEquals(true, searchResult.isMaxCountExceeded());

        assertEquals(1.0, result.getBalance());
        assertEquals("Test", result.getEncounterString());
        assertEquals("A", result.getFacility());
        assertEquals(true, result.isPatientLocked());
        assertEquals("A", result.getPatientsString());
        assertEquals(0, result.getRequestId());
        assertEquals("A", result.getRequestorName());
        assertEquals(0, result.getRequestorType());
        assertEquals("A", result.getRequestorTypeName());
        assertEquals("A", result.getUpdatedBy());
        assertEquals("Logged", result.getRequestStatus());
        assertEquals("Test", result.getSubtitle());

    }

    public void testRequestCoreChargesInvoice () {

        RequestCoreChargesInvoice invoice = new RequestCoreChargesInvoice();
        invoice.setInvoicedAmount(1.0);
        invoice.setPaymentAmount(1.2);

        assertEquals(1.0, invoice.getInvoicedAmount());
        assertEquals(1.2, invoice.getPaymentAmount());
    }
    public void testAuditAndEventListModel() {

        AuditAndEventList auditEventList = new AuditAndEventList();
        AuditEvent audit = new AuditEvent();
        RequestEvent request = new RequestEvent();
        List<RequestEvent> requestList = new ArrayList<RequestEvent>();
        List<AuditEvent> auditList = new ArrayList<AuditEvent>();
        requestList.add(request);
        auditList.add(audit);
        auditEventList.setAuditEvent(auditList);
        auditEventList.setRequestEvent(requestList);
        assertEquals(requestList.get(0), auditEventList.getRequestEvent().get(0));
        assertEquals(auditList.get(0), auditEventList.getAuditEvent().get(0));

    }

    public void testFreeFormFacilityModel() {

        FreeFormFacility freeFormFac = new FreeFormFacility();
        freeFormFac.setFreeFormFacilityName("AD");
        freeFormFac.setId(22);
        assertEquals("AD", freeFormFac.getFreeFormFacilityName());
        assertEquals(22, freeFormFac.getId());
    }

    public void testRequestDocumentmodel() {

        RequestDocument doc = new RequestDocument();

        doc.setChartOrder("A");
        doc.setDocId(10);
        doc.setDocumentSeq(10);
        doc.setDocTypeId(10);
        doc.setDocumentSeq(10);
        doc.setDuid("A");
        doc.setMrn("A");
        doc.setSubtitle("A");
        doc.setName("A");
        doc.setPatientSeq(10);

        assertEquals("A", doc.getChartOrder());
        assertEquals(10, doc.getDocId());
        assertEquals(10, doc.getDocumentSeq());
        assertEquals(10, doc.getDocTypeId());
        assertEquals(10, doc.getDocumentSeq());
        assertEquals("A", doc.getDuid());
        assertEquals("A", doc.getMrn());
        assertEquals("A", doc.getSubtitle());
        assertEquals("A", doc.getName());
        assertEquals(10, doc.getPatientSeq());

    }

    private RequestCore setupRequest() {

        try {
            
            long requestorId = 1001;
            String reqPassword;
            reqPassword = _requestCoreService.getGeneratedPassword();

            RequestCore request = new RequestCore();
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
            request.setRequestPassword(reqPassword);
            request.setRequestReason("Migration");
            request.setStatus("Logged");
            request.setReceiptDate(Calendar.getInstance().getTime());
            request.setStatusChangedDt(Calendar.getInstance().getTime());
            request.setStatusReason("Test");
    
            return request;
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;

    }

    public SaveRequestPatientsList setRequestPatientValue() {

        _savePatientList =  new SaveRequestPatientsList();
        _savePatientList.setRequestId(1001);
        _savePatientList.setUpdatePatients(constructRequestPatient());

        return _savePatientList;
    }

    /**
     * @return
     */
    private List<RequestPatient> constructRequestPatient() {

        List<RequestPatient> patients = new ArrayList<RequestPatient>();

        boolean isHpf = true;
        for (int i = 0; i < 2; i++) {

            String time = System.currentTimeMillis() + "";

            RequestPatient patient = new RequestPatient();
            patient.setName(NAME);
            patient.setGender("M");
            patient.setMrn(NAME + time.substring(time.length() - _five, time.length()));
            patient.setEpn("EPfaf");
            patient.setSsn(NAME + "01");
            patient.setDob(new Date());
            patient.setFacility(FACILITY);
            patient.setEncounterLocked(false);
            patient.setPatientLocked(false);
            patient.setRequestId(_request.getId());
            patient.setHpf(isHpf);
            if (isHpf) {
            	patient.setRoiEncounters(constructRequestEncounters(0, patient.getMrn(),
                                                                    patient.getFacility()));
            } else {
                patient.setSupplementalId(1001L);
            }
            patient.setNonHpfDocuments(constructRequestSupplementalDocument(0, patient.getMrn(),
                                                                            patient.getFacility(), isHpf));
            patient.setAttachments(constructRequestAttachment(0, patient.getMrn(),
                                                             patient.getFacility(), isHpf));
            isHpf = !(isHpf);
            patients.add(patient);
        }
        return patients;
    }

    private List<RequestEncounter> constructRequestEncounters(long patientSeq, String mrn, String facility) {

        List<RequestEncounter> encounterList = new ArrayList<RequestEncounter>();

        String time = System.currentTimeMillis() + "";
        RequestEncounter encounter = new RequestEncounter();
        encounter.setPatientSeq(patientSeq);
        encounter.setAdmitdate(new Date());
        encounter.setDischargeDate(new Date());
        encounter.setFacility(facility);
        encounter.setHasDeficiency(false);
        encounter.setMrn(mrn);
        encounter.setLocked(false);
        encounter.setName(NAME + time.substring(time.length() - _five, time.length()));
        encounter.setPatientService(PATIENT);
        encounter.setPatientType(PATIENT);
        encounter.setVip(false);

        encounter.setRoiDocuments(constructRequestDocuments(0, mrn, facility, encounter.getName(), 0));
        encounterList.add(encounter);

        return encounterList;
    }

    private List<RequestDocument> constructRequestDocuments(long encounterSeq, String mrn, String facility, String encounter, long docId) {

        List<RequestDocument>  documentsList = new ArrayList<RequestDocument>();

        String time = System.currentTimeMillis() + "";
        RequestDocument document = new RequestDocument();
        document.setEncounterSeq(encounterSeq);
        document.setChartOrder("123");
        document.setDateTime(new Date());
        document.setDocId(docId);
        document.setName("101 COLD DATAVAULT");
        document.setSubtitle("TEST");
        document.setMrn(mrn);
        document.setFacility(facility);
        document.setEncounter(encounter);

        document.setRoiVersions(constructRequestVersions(0, document.getDocId()));
        documentsList.add(document);
        return documentsList;
    }

    private List<RequestVersion> constructRequestVersions(long documentSeq, long docId) {

        List<RequestVersion> versionsList = new ArrayList<RequestVersion>();

        RequestVersion version  = new RequestVersion();
        version.setDocumentSeq(documentSeq);
        version.setVersionNumber(1);
        version.setDocId(docId);
        version.setRoiPages(constructRequestPages(0));
        versionsList.add(version);
        return versionsList;
    }

    private List<RequestPage> constructRequestPages(long versionSeq) {

        try {
            List<RequestPage> pagesList = new ArrayList<RequestPage>();

            for (int i = 0; i < 5; i++) {

            String time = System.currentTimeMillis() + "";
            RequestPage page = new RequestPage();
            page.setVersionSeq(versionSeq);
            page.setContentCount(1);
            page.setImnetId("IMAGES1   " + time.substring(time.length() - 9,
                                                          time.length()));
            page.setPageNumber(1);
            page.setReleased(false);
            page.setSelectedForRelease(false);

            pagesList.add(page);
            Thread.sleep(10);
            System.out.println(page.getImnetId());
            }
            return pagesList;

        } catch (InterruptedException ex) {
            throw new ROIException();
        }
    }

    private List<RequestSupplementalDocument> constructRequestSupplementalDocument(long documentSeq,
                                                                            String mrn,
                                                                            String facility,
                                                                            boolean isHpf) {

        try {

            List<RequestSupplementalDocument> documents =
                                                new ArrayList<RequestSupplementalDocument>();

            RequestSupplementalDocument doc = new RequestSupplementalDocument();
            doc.setComment("Test Comment");
            doc.setDateOfService(new Date());
            doc.setDepartment("LABORATORY");
            doc.setDocFacility("A");
            doc.setDocName("101 COLD Datavault");
            doc.setDocumentSeq(1001L);
            doc.setDocumentCoreSeq(documentSeq);
            doc.setEncounter("TESTE1001");
            doc.setFacility(facility);
            doc.setMrn(mrn);
            doc.setPageCount("1");
            doc.setSubtitle("Test");
            doc.setReleased(false);
            doc.setBillingTierId(-2L);
            doc.setSelectedForRelease(false);
            if (!isHpf) {
                doc.setSupplementalId(1L);
            }

            documents.add(doc);
            return documents;

        } catch (Exception ex) {
            throw new ROIException();
        }
    }

    private List<RequestSupplementalAttachment> constructRequestAttachment(long attachmentSeq,
                                                                           String mrn,
                                                                           String facility,
                                                                           boolean isHpf) {

        try {

            List<RequestSupplementalAttachment> attachments =
                    new ArrayList<RequestSupplementalAttachment>();

            RequestSupplementalAttachment attachment = new RequestSupplementalAttachment();
            attachment.setComment("Test Comment");
            attachment.setAttachmentDate(new Date());
            attachment.setFileext("pdf");
            attachment.setFilename("Test File");
            attachment.setIsDeleted("0");
            attachment.setPath("ab\\bc\\cd\\");
            attachment.setSubmittedBy("Test User");
            attachment.setUuid("abcd-efgh-ijkl-mnop-qrst-uvwx-yz");
            attachment.setVolume("\\\\EIGDEV242\\global$\\Attachment");
            attachment.setDateOfService(new Date());
            attachment.setDocFacility("A");
            attachment.setAttachmentSeq(1001L);
            attachment.setAttachmentCoreSeq(attachmentSeq);
            attachment.setEncounter("TESTE1001");
            attachment.setFacility(facility);
            attachment.setMrn(mrn);
            attachment.setPageCount("1");
            attachment.setSubtitle("Test");
            attachment.setReleased(false);
            attachment.setSelectedForRelease(false);
            attachment.setType("Test");
            attachment.setBillingTierId(-2L);
            if (!isHpf) {
                attachment.setSupplementalId(1L);
            }

            attachments.add(attachment);
            return attachments;

        } catch (Exception ex) {
            throw new ROIException();
        }
    }

    /**
     * @see com.mckesson.eig.roi.test.BaseROITestCase#getServiceURL(java.lang.String)
     */
    @Override
    protected String getServiceURL(String serviceMethod) {
        return null;
    }

    private SaveRequestPatientsList constructDeletePatient(RequestPatientsList requestPatientList, String mrn) {

        List<Long> pageSeq = new ArrayList<Long>();
        List<Long> encounterSeq = new ArrayList<Long>();
        List<Long> versionSeq = new ArrayList<Long>();
        List<Long> documentSeq = new ArrayList<Long>();
        List<Long> patientSeq = new ArrayList<Long>();
        List<Long> attachSeq = new ArrayList<Long>();
        List<Long> docSeq = new ArrayList<Long>();

        List<RequestPatient> requestPatients = requestPatientList.getRequestPatients();
        RequestPatient requestPatient = null;
        for (RequestPatient patient : requestPatients) {

            if (patient.isHpf() && mrn.equals(patient.getMrn())) {

                requestPatient = patient;
                break;
            }
        }

        if (requestPatient.getGlobalDocuments() != null)
        for (RequestDocument golbalDoc : requestPatient.getGlobalDocuments()) {
            documentSeq.add(golbalDoc.getDocumentSeq());
            break;
        }

        if (requestPatient.getAttachments() != null)
        for (RequestSupplementalAttachment attach : requestPatient.getAttachments()) {
            attachSeq.add(attach.getAttachmentCoreSeq());
            break;
        }

        if (requestPatient.getRoiEncounters() != null)
        for (RequestEncounter encounter : requestPatient.getRoiEncounters()) {
            encounterSeq.add(encounter.getEncounterSeq());
            for (RequestDocument document : encounter.getRoiDocuments()) {
                docSeq.add(document.getDocumentSeq());
                for (RequestVersion version : document.getRoiVersions()) {
                    versionSeq.add(version.getVersionSeq());
                    for (RequestPage page : version.getRoiPages()) {
                        pageSeq.add(page.getPageSeq());
                        break;
                    }
                    if (!pageSeq.isEmpty())
                        break;
                }
                if (!versionSeq.isEmpty())
                    break;
            }
            if (!docSeq.isEmpty())
                break;
        }

        DeletePatientList patient = new DeletePatientList();
        patient.setDocSeq(docSeq);
        patient.setVersionSeq(versionSeq);
        patient.setPageSeq(pageSeq);
        patient.setEncounterSeq(encounterSeq);
        patient.setPageSeq(patientSeq);
        patient.setAttachmentSeq(attachSeq);
        patient.setDocumentSeq(docSeq);
        patient.setDarPatientSeq(docSeq);
        patient.setSupplementalPatientSeq(docSeq);

        SaveRequestPatientsList savePatientList = new SaveRequestPatientsList();
        savePatientList.setDeletePatient(patient);
        savePatientList.setRequestId(requestPatientList.getRequestId());

        return savePatientList;
    }

    public void testProductivityReportDetailsModel() {

        ProductivityReportDetails prodRepDetails = new ProductivityReportDetails();

        prodRepDetails.setBillable("Sample");
        prodRepDetails.setFacility("A");
        prodRepDetails.setMrn("Test");
        prodRepDetails.setPages(_five);
        prodRepDetails.setPageType("Test");
        prodRepDetails.setPatientName("Tester");
        prodRepDetails.setReqID("1");
        prodRepDetails.setReqIDCount(_five);
        prodRepDetails.setRequestorType("Test");
        prodRepDetails.setUserName("Admin");

        assertEquals("Sample", prodRepDetails.getBillable());
        assertEquals("A", prodRepDetails.getFacility());
        assertEquals("Test", prodRepDetails.getMrn());
        assertEquals(_five, prodRepDetails.getPages());
        assertEquals("Test", prodRepDetails.getPageType());
        assertEquals("Tester", prodRepDetails.getPatientName());
        assertEquals("1", prodRepDetails.getReqID());
        assertEquals(_five, prodRepDetails.getReqIDCount());
        assertEquals("Test", prodRepDetails.getRequestorType());
        assertEquals("Admin", prodRepDetails.getUserName());

    }


}
