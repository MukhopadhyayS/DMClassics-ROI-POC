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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.request.model.PaginationData;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResultList;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.DateUtilities;


/**
 * @author OFS
 * @date   Sep 02, 2008
 * @since  HPF 13.1 [ROI]; Jul 8, 2008
 */
public class TestRequestSearchService
extends BaseROITestCase {

    protected static final String REQUEST_SERVICE =
        "com.mckesson.eig.roi.request.service.RequestCoreServiceImpl";

    protected static final String REQUESTOR_SERVICE =
            "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";

    private static RequestCoreService _requestService;
    private static RequestorService _requestorService;

    private static final String PHONE_NUMBER = "123456";
    private static final String NAME = "Test User";
    private static final String PATIENT = "patient";

    @Override
    public void initializeTestData() throws Exception {
       _requestService = (RequestCoreService) getService(REQUEST_SERVICE);
       _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);
       requestCreation();
    }
    /*
     * This test case for Request Search
     */
    public void testRequestSearchForValidPaginationData() {

        try {

            RequestCoreSearchResultList res = _requestService.searchRequest(getFetchCriteria());
            assertNotNull(res);

        } catch (ROIException e) {
            fail();
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestCount() {

        try {

            long res = _requestService.getRequestCount(getFetchCriteria());
            assertTrue(res > 0);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request Count");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestCountWithEncounterSearch() {

        try {

            RequestCoreSearchCriteria criteria = getFetchCriteria();
            criteria.setEncounter("ROITESTE01");
            long res = _requestService.getRequestCount(criteria);
            assertTrue(res > 0);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request Count");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestCountWithPatientSearch() {

        try {

            RequestCoreSearchCriteria criteria = getFetchCriteria();

            Date dtFormate = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                    .ROI_DATE_FORMAT),
                    "01/01/2001");
            criteria.setPatientFirstName("Roi");
            criteria.setPatientLastName("Test1");
            criteria.setPatientEpn("FralickM10");
            criteria.setPatientSsn("FralickM10");
            criteria.setMrn("FralickM10");
            criteria.setFacility("AD");
            criteria.setRequestStatus("Logged");
            criteria.setRequestReason("Migration");
            criteria.setPatientDob(dtFormate);
            criteria.setPatientId(1001);
            criteria.setReceiptDateFrom(dtFormate);
            criteria.setReceiptDateTo(dtFormate);
            criteria.setCompletedDateFrom(dtFormate);
            criteria.setCompletedDateTo(dtFormate);

            long res = _requestService.getRequestCount(criteria);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request Count");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestCountWithRequestSearch() {

        try {

            RequestCoreSearchCriteria criteria = getFetchCriteria();
            criteria.setRequestorName("Test01");
            criteria.setRequestorType(1001);
            criteria.setRequestorId(1001);

            long res = _requestService.getRequestCount(criteria);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request Count");
        }
    }

    public void testRequestCountWithInvoiceSearch() {

        try {

            RequestCoreSearchCriteria criteria = getFetchCriteria();
            criteria.setInvoiceNumber(1001);
            criteria.setBalanceDueOperator("<");
            _requestService.getRequestCount(criteria);

        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request Count");
        }
    }

    /*
     * This test case is to cover the Request Search with null
     */
    public void testRequestCountWithNull() {

        try {

            long res = _requestService.getRequestCount(null);
            assertTrue(res > 0);
        } catch (ROIException e) {
            e.printStackTrace();
            assertError(e, ROIClientErrorCodes.REQUEST_PATIENT_IS_EMPTY);
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearch() {

        try {

            RequestCoreSearchResultList res = _requestService.searchRequest(getFetchCriteria());
            res.isMaxCountExceeded();

            System.out.println("No of Request == " + res.getRequestCoreSearchResult().size());
            assertNotNull(res);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearchWithSorting() {

        try {

            RequestCoreSearchResultList res = _requestService.searchRequest(getFetchCriteria());
            res.isMaxCountExceeded();

            assertNotNull(res);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request model");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearchForBetween() {

        try {

            PaginationData pag = new PaginationData();
            pag.setStartIndex(0);
            pag.setCount(2);
            pag.setIsDesc(false);
            pag.setSortBy("REQUEST_ID");

            RequestCoreSearchResultList res = _requestService.searchRequest(getFetchCriteria());
            res.isMaxCountExceeded();

            assertNotNull(res);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request model");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearchPaginationInvalid() {

        try {

            _requestService.searchRequest(null);
            fail("Testing request model");

        } catch (ROIException e) {
            assertTrue(true);
        }
    }

    /*
     * This test case for Request Search
     */
    public void testRequestSearchForInvalidPaginationData() {

        try {

            PaginationData pag = new PaginationData();
            pag.setStartIndex(-1);
            pag.setCount(-1);
            pag.setIsDesc(false);
            pag.setSortBy("REQUEST_ID");
            RequestCoreSearchCriteria criteria = getFetchCriteria();
            criteria.setPaginationData(pag);
            RequestCoreSearchResultList res =  _requestService.searchRequest(criteria);
            if (res.isMaxCountExceeded()) {
                fail("Testing request model");
            }

        } catch (ROIException e) {
            assertTrue(true);
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearchForAtLeast() {

        try {

            requestCreationWithAtleastBalance();

            PaginationData pag = new PaginationData();
            pag.setStartIndex(0);
            pag.setCount(2);
            pag.setIsDesc(false);
            pag.setSortBy("REQUEST_ID");

            RequestCoreSearchCriteria criteria = getFetchCriteriaForAtLeastBalance();
            criteria.setPaginationData(pag);
            criteria.setPatientLastName("Fra");

            RequestCoreSearchResultList res = _requestService.searchRequest(criteria);
            res.isMaxCountExceeded();
            assertNotNull(res);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request model");
        }
    }

    /*
     * This test case is to cover the Request Search
     */
    public void testRequestSearchForAtMost() {

        try {

            requestCreationWithAtMostBalance();

            PaginationData pag = new PaginationData();
            pag.setStartIndex(0);
            pag.setCount(2);
            pag.setIsDesc(false);
            pag.setSortBy("REQUEST_ID");

            RequestCoreSearchCriteria criteria = getFetchCriteriaForAtMostBalance();
            criteria.setPaginationData(pag);
            criteria.setPatientLastName("Fra");

            RequestCoreSearchResultList res = _requestService.searchRequest(criteria);
            res.isMaxCountExceeded();

            assertNotNull(res);
        } catch (ROIException e) {
            e.printStackTrace();
            fail("Testing request model");
        }
    }

    private RequestCore requestCreation() {

        RequestCore req = setupRequest();
        try {

            req = _requestService.createRequest(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return req;
    }

    private RequestCoreSearchCriteria getFetchCriteria() {

        RequestCoreSearchCriteria criteria = new RequestCoreSearchCriteria();

        PaginationData pag = new PaginationData();
        pag.setStartIndex(1);
        pag.setCount(2);

        //criteria.setPaginationData(pag);
        criteria.setMaxCount(10);

        criteria.setRequestId(1001);
        return criteria;
    }

    private RequestCore requestCreationWithAtleastBalance() {

        RequestCore req = setupRequest();
        try {

            req = _requestService.createRequest(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return req;
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


    private RequestCoreSearchCriteria getFetchCriteriaForAtLeastBalance() {

        RequestCoreSearchCriteria criteria = new RequestCoreSearchCriteria();
        criteria.setMaxCount(10);
        return criteria;
    }

    private RequestCore requestCreationWithAtMostBalance() {

        RequestCore req = setupRequest();
        try {

            req = _requestService.createRequest(req);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return req;
    }


    private RequestCoreSearchCriteria getFetchCriteriaForAtMostBalance() {

        RequestCoreSearchCriteria criteria = new RequestCoreSearchCriteria();

        final int count = 10;
        criteria.setMaxCount(count);
        return criteria;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException("TestRequestSearchService.getServiceURL()");
    }

}
