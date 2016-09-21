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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.RelatedBillingTemplate;
import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.service.BillingAdminService;
import com.mckesson.eig.roi.admin.service.ROIAdminService;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.roi.base.model.MatchCriteria;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.base.model.ROILoV;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.DocInfoList;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceInfo;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.service.RequestCoreService;
import com.mckesson.eig.roi.request.service.RequestCoreServiceImpl;
import com.mckesson.eig.roi.requestor.dao.RequestorDAO;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.AdjustmentType;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsPayments;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorHistoryList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorSearchResult;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author ranjithr
 * @date   Dec 24, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class TestRequestorServiceImpl
extends BaseROITestCase {

    protected static final String REQUESTOR_SERVICE =
        "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";
    protected static final String REQUEST_SERVICE =
            "com.mckesson.eig.roi.request.service.RequestCoreServiceImpl";

    protected static final String  ADMIN_SERVICE =
        "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    protected static final String  AD_SERVICE =
        "com.mckesson.eig.roi.admin.service.BillingAdminServiceImpl";

    protected static final String REQUESTOR_TYPE_DAO = "RequestorTypeDAO";
    private static final int MAX_COUNT = ROIConstants.REQUESTOR_MAX_COUNT;
    protected static final String AUTHENTICATED_ROI_USER = "authenticated_roi_user";
    protected static final String REQUESTOR_DAO = "RequestorDAO";

    private static RequestCoreService _requestService;
    private static RequestorService _requestorService;
    private static RequestorTypeDAO _requestorTypeDao;
    private static ROIAdminService _adminService;
    private static BillingAdminService   _service;
    private static RequestorDAO _requestorDao;

    private static long   _patientRequestorTypeId;
    private static long   _createdRequestorId;
    private static long   _requestorIdWithOutMainAddr;
    private static long   _requestorIdWithOutAltAddr;
    private static long   _requestorIdWithOutContact;
    private static long   _requestorIdWithNameAndType;
    private static long   _requestorIdForUpdate;
    private static RequestCore    _request;
    private static Set<ROILoV> _lovList;

    private static String _requestorName  = "Requestor";
    private static String _invalidRequestorName  = "!@=+&.,";
    private static String _invalidCityName  = "!@=+&.,";
    private static String _invalidZipCode  = "!@=+&.,";
    private static String _ssnValue       = "SSN";
    private static String _invalidName    = "A";
    private static String _epnValue;
    private static String _invalidEpnValue = "$.?<:,{[*";
    private static String _invalidSSNValue = "$.?<:,{[*";
    private static String _invalidPhoneValue = "z$.@!#$";
    private static String _invalidEmailValue = "$@.!#%&*";
    private static int    _requestorCount = 1;
    private static int    _count          = 2;

    private static Date   _dobForUpdate;
    private static Date   _validDob;

    private static long _createdRequestorTypeId;
    private static Set<RelatedBillingTemplate> _rtBTemplate;
    private static Set<RelatedBillingTier> _rtBTier;
    private static Set<RelatedFeeType> _btfSet;

    private static long _feeTypeId;

    private static String _requestorTypeName;
    private static String _requstorTypeDesc;
    private static String _rv;
    private static String _rvDesc;
    private static String _billingTemplateName;
    private static BillingTemplatesList  _btList;

    private static final int HPF_BILLINGTIER_ID = -1;
    private static final int NON_HPF_BILLINGTIER_ID = -2;
    private static final String EPN = "11";
    private static final String SSN = "11";
    private static final long REQUESTOR_ID = 1001;
    private static final long REQUESTOR_TYPE_ID = 1001;

    @Override
    public void initializeTestData()
    throws Exception {

        long seed = System.nanoTime();
        _requestorTypeName = "Name" + seed;
        _billingTemplateName = "BTName" + seed;
        _requstorTypeDesc = "RequestorDesc" + seed;
        _rv   = "All Documents";
        _rvDesc = "All Documents";

        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
        _service = (BillingAdminService) getService(AD_SERVICE);
        _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);

        _epnValue = getUser().getEpnPrefix();
        _validDob = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                                                                 .ROI_DATE_FORMAT),
                                                                 "10/09/2008");
        _dobForUpdate = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                                                                     .ROI_DATE_FORMAT),
                                                                     "06/04/2008");
        _requestorTypeDao = (RequestorTypeDAO) SpringUtilities.getInstance().getBeanFactory()
                                                              .getBean(REQUESTOR_TYPE_DAO);

        _patientRequestorTypeId = _requestorTypeDao
                                 .getRequestorTypeByName(ROIConstants.REQUESTOR_TYPE_PATIENT)
                                 .getId();

        _requestService = (RequestCoreService) getService(RequestCoreServiceImpl.class.getName());

        _requestorDao = (RequestorDAO) SpringUtilities.getInstance().getBeanFactory()
                .getBean(REQUESTOR_DAO);

        createFeeTypeForTesting();
        createBillingTemplate();
        _btList = _service.retrieveAllBillingTemplates(true);
        createRequestorType();
        insertDataSet("test/resources/reports/reportsDataSet.xml");

        Requestor req = createRequestor();
        _createdRequestorId = _requestorService.createRequestor(req);
    }

    public void testRetrieveRequestorPastInvoice() {

        try {
            _requestorService.retrieveRequestorPastInvoices(1001);
           // fail("retrieve past invoice with invalid requestId should throw exception");

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testCreateRequestorStatement() {

        try {
            long id =
                 _requestorService.createRequestorStatement(constructRequestorStatementCriteria());
            assertEquals(true, id != 0);

        } catch (ROIException ex) {
            fail("Create Requestor Statement Operation Failed");
        }
    }

    public void testCreateRequestorStatementWithInvalidStatementCriteria() {

        try {

            _requestorService.createRequestorStatement(null);

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_STATEMENT_CRITERIA);
        }
    }

    public void testCreateRequestorStatementWithInvalidRequestorId() {

        try {

            RequestorStatementCriteria statementCriteria = constructRequestorStatementCriteria();
            statementCriteria.setRequestorId(-1);
            _requestorService.createRequestorStatement(statementCriteria);

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }
    }

    public void testCreateRequestorStatementWithInvalidTemplateId() {

        try {

            RequestorStatementCriteria statementCriteria = constructRequestorStatementCriteria();
            statementCriteria.setTemplateFileId(0);
            _requestorService.createRequestorStatement(statementCriteria);

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_TEMPLATE_ID);
        }
    }

    public void testCreateRequestorStatementWithInvalidDateRange() {

        try {

            RequestorStatementCriteria statementCriteria = constructRequestorStatementCriteria();
            statementCriteria.setDateRange(null);
            _requestorService.createRequestorStatement(statementCriteria);

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_DATE_RANGE);
        }
    }

    public void testGenerateRequestorStatementWithInvalidStatementCriteria() {

        try {

            _requestorService.generateRequestorStatement(null);

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_STATEMENT_CRITERIA);
        }
    }

    public void testGenerateRequestorStatement() {

        try {

           DocInfoList docInfo =
                _requestorService.generateRequestorStatement(constructRequestorStatementCriteria());
           assertNotNull(docInfo);

        } catch (ROIException ex) {
            fail("Create Requestor Statement Operation Failed");
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestorsWithInvalidId() {

        try {

            Requestor requestor = createRequestor();
            long reqId = _requestorService.createRequestor(requestor);

            Requestor req = _requestorService.retrieveRequestor(reqId, true);

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(req.getLastName());
            searchCriteria.setFirstName(req.getFirstName());
            searchCriteria.setMaxCount(MAX_COUNT);

            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertNotSame("The search result size should be greater than zero",
                          0,
                          searchResult.getSearchResults().size());

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestorsWithNameAndType() {

        try {

            Requestor requestor = createRequestor();
            long reqId = _requestorService.createRequestor(requestor);

            Requestor req = _requestorService.retrieveRequestor(reqId,false);

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(req.getLastName());
            searchCriteria.setFirstName(req.getFirstName());
            searchCriteria.setRecentRequestorDate(new Date());
            searchCriteria.setMaxCount(MAX_COUNT);

            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertNotSame("The search result size should be greater than zero",
                          0,
                          searchResult.getSearchResults().size());

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestors() {

        try {

            Requestor requestor = createRequestor();
            requestor.setActive(true);
            long reqId = _requestorService.createRequestor(requestor);
            Requestor req = _requestorService.retrieveRequestor(reqId,false);

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setDob(req.getDob());
            searchCriteria.setEpn(req.getEpn());
            searchCriteria.setSsn(_ssnValue);
            searchCriteria.setMrn("MRN");
            searchCriteria.setFacility(req.getFacility());
            searchCriteria.setFreeFormFacility(true);
            searchCriteria.setLastName(req.getLastName());
            searchCriteria.setFirstName(req.getFirstName());
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setActiveRequestors(true);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertNotSame("The search result size should be greater than zero",
                          0,
                          searchResult.getSearchResults().size());

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestorsWithValidSearchCriteria() {

        try {

            Requestor requestor = createRequestor();
            requestor.setActive(true);
            long reqId = _requestorService.createRequestor(requestor);

            Requestor req = _requestorService.retrieveRequestor(reqId,false);

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setDateOfBirth("");
            searchCriteria.setEpn(req.getEpn());
            searchCriteria.setMrn(req.getMrn());
            searchCriteria.setFacility(req.getFacility());
            searchCriteria.setFreeFormFacility(Boolean.valueOf(req.getFreeFormFacilityExists()));
            searchCriteria.setLastName(req.getLastName());
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setActiveRequestors(true);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertNotSame("The search result size should be greater than zero",
                          0,
                          searchResult.getSearchResults().size());

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the recent requestors based on the valid search criteria
     */
    public void testFindRecentRequestorsWithValidData() {

        try {

            Requestor requestor = createRequestor();
            long reqId = _requestorService.createRequestor(requestor);

            Requestor req = _requestorService.retrieveRequestor(reqId,false);

            _request = _requestService.createRequest(requestCreation(req));

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(false);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_requestorName);
            searchCriteria.setDob(_validDob);
            searchCriteria.setEpn(req.getEpn());
            searchCriteria.setSsn(req.getSsn());
            searchCriteria.setMrn(req.getMrn());
            searchCriteria.setFacility(req.getFacility());
            searchCriteria.setFreeFormFacility(Boolean.valueOf(req.getFreeFormFacilityExists()));
            searchCriteria.setMaxCount(MAX_COUNT);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertNotSame("The search result size should be greater than zero",
                          0,
                          searchResult.getSearchResults().size());

        } catch (ROIException e) {
            fail("Searching recent requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the requestors with invalid length for epn
     */
    public void testFindRequestorsWithInvalidLengthForEPN() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(false);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_invalidName);
            searchCriteria.setEpn(EPN);
            searchCriteria.setMaxCount(MAX_COUNT);
            _requestorService.findRequestor(searchCriteria);
            fail("Searching recent requestors with name length "
                 + "less then two character is not permitted, but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_EPN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
        }
    }

    /*
     * Test method to find the requestors with invalid length for ssn
     */
    public void testFindRequestorsWithInvalidLengthForSSN() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(false);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_invalidName);
            searchCriteria.setEpn(EPN);
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setSsn(SSN);
            _requestorService.findRequestor(searchCriteria);
            fail("Searching requestors with ssn length less then three character is not permitted,"
                 + " but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_SSN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
        }
    }

    public void testRetrieveRequestorLetterHistoryWithInvalidRequestor() {

        try {
            _requestorService.retrieveRequestorLetterHistory(0);
            fail("Retrieve Requestor Letter With Invalid Requestor not permitted,"
                    + " but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }
    }

    public void testRetrieveRequestorLetterHistory() {

        try {
            _requestorService.retrieveRequestorLetterHistory(_createdRequestorId);

        } catch (ROIException e) {
            fail("Retrieve Requestor Letter throws Exception," + e.getMessage());
        }
    }

    public void testViewRequestorLetterWithInvalidId() {

        try {
            _requestorService.viewRequestorLetter(0, "request");
            fail("View Requestor Letter With Invalid Requestor not permitted," + " but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }


    public void testCreateRequestorLetterEntry() {

        try {

            RegeneratedInvoiceInfo invInfo = new RegeneratedInvoiceInfo();
            invInfo.setId(1001);
            invInfo.setInvoice(true);
            invInfo.setOutputMethod("SaveAsFile");
            invInfo.setQueuePassword("1234");
            Map<String, String> props = new TreeMap<String, String>();
            props.put(ROIConstants.QUEUE_PD, "1234");
            props.put(ROIConstants.OUTPUT_METHOD, "SaveAsFile");

            invInfo.setProperties(props);
            _requestorService.createRequestorLetterEntry(invInfo);
        } catch (ROIException e) {
            fail("Create Requestor Letter Entry throws Exception," + e.getMessage());
        }
    }

    public void testCreateRequestorLetterEntryWithInvalidInvoiceInfo() {

        try {
            _requestorService.createRequestorLetterEntry(null);
            fail("Create Requestor Letter Entry With Invalid Requestor not permitted," + " but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_PROPERTIES);
        }
    }

    public void testViewRequestorLetter() {

        try {
            _requestorService.viewRequestorLetter(1001, ROIConstants.TEMPLATE_FILE_TYPE);
        } catch (ROIException e) {
            fail("View Requestor Letter throws Exception," + e.getMessage());
        }
    }

    /*
     * Test method to find the requestors with requestor type not as patient
     * but having epn, ssn values
     */
    public void testFindRequestorsWithInvalidSearchCriteria() {

        try {

            final long nonpatientId = 2;
            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(nonpatientId);
            searchCriteria.setLastName(_requestorName);
            searchCriteria.setDob(_validDob);
            searchCriteria.setSsn(_ssnValue);
            searchCriteria.setMrn("MRN");
            searchCriteria.setFacility("FACILITY");
            searchCriteria.setSsn(_epnValue);
            searchCriteria.setMaxCount(MAX_COUNT);
             _requestorService.findRequestor(searchCriteria);
            fail("Searching requestors with non patient type "
                 + "and having ssn, epn, dob value is not permitted, but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_INVALID_SEARCH_CRITERIA);
        }
    }

    /*
     * Test method to find the requestors with requestor type not as patient
     * but having dob values
     */
    public void testFindRequestorsWithDOBForNonPatientType() {

        try {

            final long nonpatientId = 2;
            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(nonpatientId);
            searchCriteria.setLastName(_requestorName);
            searchCriteria.setDob(_validDob);
            searchCriteria.setMaxCount(MAX_COUNT);
             _requestorService.findRequestor(searchCriteria);
            fail("Searching requestors with non patient type "
                 + "and having dob value is not permitted, but accepted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_INVALID_SEARCH_CRITERIA);
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestorsWithMaxCount() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_requestorName);
            searchCriteria.setMaxCount(_count);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertTrue(searchResult.isMaxCountExceeded());
            assertEquals(searchResult.getRequestorType(), _patientRequestorTypeId);
        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /*
     * Test method to find the all requestors with search criteria as null
     */
    public void testFindAllRequestorsWithNullSearchCriteria() {

        try {

             _requestorService.findRequestor(null);
            fail("Searching all requestors with search criteria as null is not permitted.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_SEARCH_OPERATION_FAILED);
        }
    }

    /*
     * Test method to find the all requestors based on the valid search criteria
     * This test case is to cover the Requestor model
     */
    public void testFindRequestorsWithNameAlone() {

        try {

            Requestor requestorToCreate = createRequestor();
            long reqId = _requestorService.createRequestor(requestorToCreate);

            Requestor req = _requestorService.retrieveRequestor(reqId,false);

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setLastName(req.getLastName());
            searchCriteria.setFirstName(req.getFirstName());
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setActiveRequestors(false);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertTrue(searchResult.getSearchResults().size() > 0);
            Requestor requestor = new Requestor();
            for (Requestor re : searchResult.getSearchResults()) {
                if (re.getId() == reqId) {
                    requestor = re;
                }
            }
            if (!requestor.isAssociated()) {
                requestor.setAssociated(false);
            }
            assertEquals(requestor.getLastName(), req.getLastName());
        } catch (ROIException e) {
            fail("Searching all requestors with name alone should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor with valid requestor detail
     */
    public void testCreateRequestor() {

        try {

            Requestor req = createRequestor();
            _createdRequestorId = _requestorService.createRequestor(req);
            assertNotSame("The created requestor id sholud be greater than zero",
                          0,
                          _createdRequestorId);

        } catch (ROIException e) {
            fail("Service method createRequestor() should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method to test the requestor model with null
     */
    public void testCreateRequestorWithNull() {

        try {

            _requestorService.createRequestor(null);
            fail("Requestor creation with null is not accepted, but it created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This method is to create requestor with Invalid Data.
     */
    public void testCreateRequestorWithInvalidData() {

        try {

            Requestor req = createRequestor();
            req.setLastName(appendString(_invalidRequestorName));
            req.setEpn(appendString(_invalidEpnValue));
            req.setSsn(appendString(_invalidSSNValue));
            req.setHomePhone(appendString(_invalidPhoneValue));
            req.setWorkPhone(appendString(_invalidPhoneValue));
            req.setCellPhone(appendString(_invalidPhoneValue));
            req.setEmail(appendString(_invalidEmailValue));
            req.setFax(appendString(_invalidPhoneValue));
            req.setContactName(appendString(_invalidRequestorName));
            req.setContactEmail(appendString(_invalidEmailValue));
            req.setContactPhone(appendString(_invalidPhoneValue));

            Address main = new Address();
            main.setAddress1(appendString("mainaddress1"));
            main.setAddress2(appendString("mainaddress2"));
            main.setAddress3(appendString("mainaddress3"));
            main.setCity(appendString(_invalidCityName));
            main.setState(appendString(_invalidCityName));
            main.setPostalCode(appendString(_invalidZipCode));

            Address alt = new Address();
            alt.setAddress1(appendString("alteraddress1"));
            alt.setAddress2(appendString("alteraddress1"));
            alt.setAddress3(appendString("alteraddress1"));
            alt.setCity(appendString(_invalidCityName));
            alt.setState(appendString(_invalidCityName));
            alt.setPostalCode(appendString(_invalidZipCode));

            req.setAltAddress(alt);
            req.setMainAddress(main);

            _createdRequestorId = _requestorService.createRequestor(req);
            fail("createRequestor with invalid data not allowed.");

        } catch (ROIException e) {

            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_PATIENT_EPN);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_EPN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_PATIENT_SSN);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_SSN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.PHONE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_EMAIL);
            assertError(e, ROIClientErrorCodes.REQUESTOR_EMAIL_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_FAX_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_CONTACT_NAME);
            assertError(e, ROIClientErrorCodes.REQUESTOR_CONTACT_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.CONTACT_PHONE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS1_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS2_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS3_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_CITY_NAME);
            assertError(e, ROIClientErrorCodes.CITY_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_STATE_NAME);
            assertError(e, ROIClientErrorCodes.STATE_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This method is to create requestor without requestor main address
     */
    public void testCreateRequestorWithOutMainAddress() {

        try {

            Requestor req = createRequestor();
            req.setMainAddress(null);
            req.setActive(true);
            _requestorIdWithOutMainAddr = _requestorService.createRequestor(req);
            assertNotSame("The created requestor id sholud be greater than zero",
                          0,
                          _requestorIdWithOutMainAddr);

        } catch (ROIException e) {
            fail("Creating requestor without main address should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor without requestor alternate address
     */
    public void testCreateRequestorWithOutAltAddress() {

        try {

            Requestor req = createRequestor();
            req.setAltAddress(null);
            _requestorIdWithOutAltAddr = _requestorService.createRequestor(req);
            assertNotSame("The created requestor id sholud be greater than zero",
                          0,
                          _requestorIdWithOutAltAddr);

        } catch (ROIException e) {
            fail("Creating requestor without alternate address should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor without contact information
     */
    public void testCreateRequestorWithOutContactInformation() {

        try {

            Requestor req = createRequestor();
            req.setHomePhone(null);
            req.setWorkPhone(null);
            req.setCellPhone(null);
            req.setEmail(null);
            req.setFax(null);
            req.setContactName(null);
            req.setContactEmail(null);
            req.setContactPhone(null);
            _requestorIdWithOutContact = _requestorService.createRequestor(req);
            assertNotSame("The created requestor id sholud sholud be greater than zero",
                          0,
                          _requestorIdWithOutContact);

        } catch (ROIException e) {
            fail("Creating requestor without contact information should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor with requestor name and type only
     */
    public void testCreateRequestorWithNameAndType() {

        try {

            Requestor req = new Requestor();
            req.setLastName("Requestor");
            req.setFirstName("reqfname");
            req.setType(_patientRequestorTypeId);
            _requestorIdWithNameAndType = _requestorService.createRequestor(req);
            assertNotSame("The created requestor id sholud sholud be greater than zero",
                          0,
                          _requestorIdWithNameAndType);

        } catch (ROIException e) {
            fail("Creating requestor with only name and type should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor without requestor name
     */
    public void testCreateRequestorWithOutName() {

        try {

            Requestor req = new Requestor();
            req.setLastName(null);
            req.setType(_patientRequestorTypeId);
            _requestorService.createRequestor(req);
            fail("Creating requestor without name is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_IS_MANDATORY);
        }
    }

   public void testCreateRequestorWithMaxLengthFields() {

        try {

            Requestor req = createRequestorWithMaxLengthFields();
             _requestorService.createRequestor(req);
            fail("Creating requestor with type as zero is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_EPN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_SSN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_CONTACT_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS1_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS2_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS3_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.CITY_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.STATE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_DOB_CONTAINS_FUTURE_DATE);
        }
    }

    private static Requestor _matchingReq;
    /**
     * Create requestor for requestor matching
     */
    public void testCreateRequestorForMatching() {

        _matchingReq = createRequestor();
        _matchingReq.setFirstName("PatientFname" + System.currentTimeMillis());
        _matchingReq.setLastName("PatientLname" + System.currentTimeMillis());
        _matchingReq.setSsn("SSN" + System.currentTimeMillis());

        try {
            long id = _requestorService.createRequestor(_matchingReq);
            assertTrue(id != 0);
        } catch (ROIException e) {
            fail("createRequestorForMatching method should not thrown an exception"
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor with null user
     */
    public void testCreateRequestorWithNullUser() {

        try {

            initSession(null);
            Requestor req = createRequestor();
            _requestorService.createRequestor(req);
            fail("Creating requestor with null user is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This method is to retrieve an existing requestor
     */
    public void testRetrieveRequestor() {

        try {

            initSession();
            Requestor retrievedRequestor = _requestorService.retrieveRequestor(_createdRequestorId,
                                                                               false);
            assertEquals(_createdRequestorId, retrievedRequestor.getId());
        } catch (ROIException e) {
            fail("Retrieve requestor by valid requestor id should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to retrieve an existing requestor with invalid id
     */
    public void testRetrieveRequestorByInvalidId() {

        try {

            _requestorService.retrieveRequestor(Integer.MAX_VALUE, false);
            fail("Retrieve requestor by invalid requestor id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This method is to update the existing requestor by adding main address to it
     */
    public void testUpdateRequestorWithInvalidData() {

        try {
            Requestor createreq = createRequestor();
            long id = _requestorService.createRequestor(createreq);
            Requestor retrievedReq = _requestorService.retrieveRequestor(id, false);

            retrievedReq.setLastName(appendString(_invalidRequestorName));
            retrievedReq.setEpn(appendString(_invalidEpnValue));
            retrievedReq.setSsn(appendString(_invalidSSNValue));
            retrievedReq.setHomePhone(appendString(_invalidPhoneValue));
            retrievedReq.setWorkPhone(appendString(_invalidPhoneValue));
            retrievedReq.setCellPhone(appendString(_invalidPhoneValue));
            retrievedReq.setEmail(appendString(_invalidEmailValue));
            retrievedReq.setFax(appendString(_invalidPhoneValue));
            retrievedReq.setContactName(appendString(_invalidRequestorName));
            retrievedReq.setContactEmail(appendString(_invalidEmailValue));
            retrievedReq.setContactPhone(appendString(_invalidPhoneValue));

            Address main = new Address();
            main.setAddress1(appendString("mainaddress1"));
            main.setAddress2(appendString("mainaddress2"));
            main.setAddress3(appendString("mainaddress3"));
            main.setCity(appendString(_invalidCityName));
            main.setState(appendString(_invalidCityName));
            main.setPostalCode(appendString(_invalidZipCode));

            Address alt = new Address();
            alt.setAddress1(appendString("alteraddress1"));
            alt.setAddress2(appendString("alteraddress1"));
            alt.setAddress3(appendString("alteraddress1"));
            alt.setCity(appendString(_invalidCityName));
            alt.setState(appendString(_invalidCityName));
            alt.setPostalCode(appendString(_invalidZipCode));

            retrievedReq.setAltAddress(alt);
            retrievedReq.setMainAddress(main);
             _requestorService.updateRequestor(retrievedReq);
            fail("Updating requestor with invalid data not allowed.");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_CONTAINS_INVALID_CHAR);
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_PATIENT_EPN);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_EPN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_PATIENT_SSN);
            assertError(e, ROIClientErrorCodes.REQUESTOR_PATIENT_SSN_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.PHONE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_EMAIL);
            assertError(e, ROIClientErrorCodes.REQUESTOR_EMAIL_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.REQUESTOR_FAX_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_CONTACT_NAME);
            assertError(e, ROIClientErrorCodes.REQUESTOR_CONTACT_NAME_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.CONTACT_PHONE_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS1_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS2_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.ADDRESS3_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_CITY_NAME);
            assertError(e, ROIClientErrorCodes.CITY_LENGTH_EXCEEDS_LIMIT);
            assertError(e, ROIClientErrorCodes.INVALID_STATE_NAME);
            assertError(e, ROIClientErrorCodes.STATE_LENGTH_EXCEEDS_LIMIT);
        }
    }

    /**
     * This method is to update the existing requestor by adding main address to it
     */
    public void testUpdateRequestorByAddingMainAddress() {

        try {

            Requestor retrievedReq = _requestorService
                                    .retrieveRequestor(_requestorIdWithOutMainAddr, false);
            clearRequestorDetails(retrievedReq);

            Address main = new Address();
            main.setAddress1("Updated Main Address 1");
            main.setAddress2("Updated Main Address 2");
            main.setAddress3("Updated Main Address 3");
            main.setCity("TRICHY");
            main.setState("TN");
            main.setPostalCode("62500-1230");
            main.setCountryCode("IN");
            main.setCountryName("INDIA");
            main.setCountrySeq(-126L);

            retrievedReq.setMainAddress(main);
            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getId(), updatedReq.getId());
        } catch (ROIException e) {
          fail("Updating requestor by adding main address should not thrown exception."
               + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor by adding alt address to it
     */
    public void testUpdateRequestorByAddingAltAddress() {

        try {

            Requestor retrievedReq = _requestorService
                                    .retrieveRequestor(_requestorIdWithOutAltAddr, false);
            clearRequestorDetails(retrievedReq);

            Address alt = new Address();
            alt.setAddress1("Updated Alt Address 1");
            alt.setAddress2("Updated Alt Address 2");
            alt.setAddress3("Updated Alt Address 3");
            alt.setCity("TRICHY");
            alt.setState("TN");
            alt.setPostalCode("62508-1236");
            alt.setCountryCode("IN");
            alt.setCountryName("INDIA");
            alt.setCountrySeq(-126L);

            retrievedReq.setAltAddress(alt);
            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getId(), updatedReq.getId());
        } catch (ROIException e) {
          fail("Updating requestor by adding alternate address should not thrown exception."
               + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor by adding contact information to it
     */
    public void testUpdateRequestorByAddingContactInfo() {

        try {

            Requestor retrievedReq = _requestorService
                                    .retrieveRequestor(_requestorIdWithOutContact, false);
            clearRequestorDetails(retrievedReq);

            retrievedReq.setHomePhone("2222222222");
            retrievedReq.setWorkPhone("1111111111");
            retrievedReq.setCellPhone("3333333333");
            retrievedReq.setEmail("updated@mck.com");
            retrievedReq.setFax("+12345");
            retrievedReq.setContactName("UpdatedContact");
            retrievedReq.setContactEmail("updatedcontact@mck.com");
            retrievedReq.setContactPhone("444444000");

            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getHomePhone(), updatedReq.getHomePhone());
            assertEquals(retrievedReq.getContactEmail(), updatedReq.getContactEmail());
        } catch (ROIException e) {
          fail("Updating requestor by adding alternate address should not thrown exception."
               + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor by adding contact information to it
     */
    public void testUpdateRequestorContact() {

        try {

            Requestor req = createRequestor();
            long id = _requestorService.createRequestor(req);
            Requestor retrievedReq = _requestorService.retrieveRequestor(id, false);
            clearRequestorDetails(retrievedReq);

            retrievedReq.setContactName("UpdatedContact");
            retrievedReq.setContactEmail("updatedcontact@mck.com");
            retrievedReq.setContactPhone("444444000");

            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getContactEmail(), updatedReq.getContactEmail());
        } catch (ROIException e) {
          fail("Updating requestor by adding alternate address should not thrown exception."
               + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor detail
     */
    public void testUpdateRequestorDetail() {

        try {

            Requestor req = createRequestor();
            _requestorIdForUpdate = _requestorService.createRequestor(req);
            Requestor retrievedReq = _requestorService.retrieveRequestor(_requestorIdForUpdate,
                                                                         false);
            clearRequestorDetails(retrievedReq);

            retrievedReq.setEpn(_epnValue + "Updated");
            retrievedReq.setSsn("UpdatedSSN");
            retrievedReq.setDob(_dobForUpdate);

            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getEpn(), updatedReq.getEpn());
            assertEquals(retrievedReq.getSsn(), updatedReq.getSsn());
        } catch (ROIException e) {
            fail("Updating requestor detail should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor detail epn
     */
    public void testUpdateRequestorDetailEPN() {

        try {

            Requestor req = createRequestor();
            req.setEpn(null);
            long id = _requestorService.createRequestor(req);
            Requestor retrievedReq = _requestorService.retrieveRequestor(id, false);
            clearRequestorDetails(retrievedReq);
            retrievedReq.setEpn(_epnValue + "UPDATED");
            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(retrievedReq.getEpn(), updatedReq.getEpn());
        } catch (ROIException e) {
            fail("Updating requestor detail epn should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor with name as null
     */
    public void testUpdateRequestorWithNameAsNull() {

        try {

            Requestor retrievedReq = _requestorService.retrieveRequestor(_createdRequestorId,false);
            clearRequestorDetails(retrievedReq);
            retrievedReq.setType(_patientRequestorTypeId);
            retrievedReq.setLastName(null);
            retrievedReq.setEpn("EPN");
            retrievedReq.setSsn("SSN");
            retrievedReq.setDob(_validDob);
            _requestorService.updateRequestor(retrievedReq);
            fail("Updating requestor with name as null is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_IS_MANDATORY);
        }
    }

    /**
     * This method is to update the existing requestor type
     */
    public void testUpdateRequestorType() {

        try {

            Requestor retrievedReq = _requestorService.retrieveRequestor(_createdRequestorId,false);
            clearRequestorDetails(retrievedReq);
            retrievedReq.setType(_createdRequestorTypeId);
            retrievedReq.setLastName("Requestor");
            retrievedReq.setEpn("EPN");
            retrievedReq.setSsn("SSN");
            retrievedReq.setDob(_validDob);
            Requestor updatedReq = _requestorService.updateRequestor(retrievedReq);
            assertEquals(_createdRequestorTypeId, updatedReq.getType());
        } catch (ROIException e) {
          fail("Update requestor type in requestor should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * This method is to update the existing requestor with null user
     */
    public void testUpdateRequestorWithNullUser() {

        try {

            initSession(null);
            Requestor retrievedReq = _requestorService.retrieveRequestor(_createdRequestorId,false);
            clearRequestorDetails(retrievedReq);
            retrievedReq.setType(_patientRequestorTypeId);
            retrievedReq.setLastName("Requestor");
            retrievedReq.setEpn("EPN");
            retrievedReq.setSsn("SSN");
            retrievedReq.setDob(_validDob);
            _requestorService.updateRequestor(retrievedReq);
            fail("Updating requestor with null user is not permitted, but updated");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This method is to delete an existing requestor with null user
     */
    public void testDeleteRequestorWithNullUser() {

        try {

            Requestor req = createRequestor();
            _createdRequestorId = _requestorService.createRequestor(req);

            initSession(null);
            _requestorService.deleteRequestor(_createdRequestorId);
            fail("Deleting requestor with null user is not permitted, but deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /**
     * This test case to delete the requestor associated with request and verify if
     * it returns the appropriate exception
     */
    public void testDeleteRequestorAssociatedWithRequest() {

        try {

            initSession();
            Requestor req = _requestorService.retrieveRequestor(_requestorIdWithOutAltAddr, false);
            _requestService.createRequest(requestCreation(req));

            _requestorService.retrieveRequestor(req.getId(), false);
            _requestorService.deleteRequestor(_requestorIdWithOutAltAddr);
            fail("Deleting requestor associated with request is not valid, but it deleted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_ASSOCIATED_WITH_REQUEST_IS_DELETED);
        }
    }

    /**
     * This method is to delete an existing requestor
     */
    public void testDeleteRequestor() {

        try {

            initSession();
            _requestorService.deleteRequestor(_requestorIdWithOutMainAddr);

            _requestorService.retrieveRequestor(_requestorIdWithOutMainAddr, false);
            fail("Retrieving the deleted requestor id is not permitted, but retrieved");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_ID);
        }
    }

    /**
     * This method is to check the duplicate name while creating the requestor
     */
    public void testCheckDuplicateRequestorNameForCreate() {

        try {

            Requestor requestor = createRequestor();
            long id = _requestorService.createRequestor(requestor);

            Requestor req = _requestorService.retrieveRequestor(id, false);
            String name = req.getLastName() + "," + req.getFirstName();
            boolean value = _requestorService.checkDuplicateRequestorName(0, name);
            assertTrue(value);
        } catch (ROIException e) {
            fail("service method checkDuplicateRequestorName() should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to check the duplicate requestor name
     * while updating the existing requestor's name
     */
    public void testCheckDuplicateRequestorNameForUpdateDiffRequestor() {

        try {

            Requestor req = createRequestor();
            long firstId = _requestorService.createRequestor(req);

            Requestor firstReq = _requestorService.retrieveRequestor(firstId, false);

            Requestor requestor = createRequestor();
            long secondId = _requestorService.createRequestor(requestor);

            boolean value = _requestorService.checkDuplicateRequestorName(secondId,
                                                                          firstReq.getLastName());

            assertTrue(value);
        } catch (ROIException e) {
            fail("service method checkDuplicateRequestorName() should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to check the duplicate name while updating the requestor name with the name
     * which is already exist for that requestor
     */
    public void testCheckDuplicateRequestorNameForUpdateSameRequestor() {

        try {

            Requestor req = createRequestor();
            long id = _requestorService.createRequestor(req);

            Requestor oldReq = _requestorService.retrieveRequestor(id, false);
            boolean value = _requestorService.checkDuplicateRequestorName(id, oldReq.getLastName());
            assertFalse(value);
        } catch (ROIException e) {
            fail("service method checkDuplicateRequestorName() should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to check the duplicate requestor name while crating the requestor
     * with name as null
     */
    public void testCheckDuplicateRequestorNameWithNameAsNull() {

        try {

             _requestorService.checkDuplicateRequestorName(0, null);
            fail("Check duplicate requestor name with name as null is not permitted, but accepted");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_NAME_IS_MANDATORY);
        }
    }

    /**
     * This method is to check the duplicate requestor name while creating the requestor
     * with non existing requestor name
     */
    public void testCheckDuplicateRequestorNamewithNonExistingName() {

        try {

            boolean value = _requestorService.checkDuplicateRequestorName(0,
                                                                         "ab" + System.nanoTime());
            assertFalse(value);
        } catch (ROIException e) {
            fail("Check duplicate name with non existing name should not thrown exception."
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to create requestor with invalid epn
     */
    public void testCreateRequestorWithInvalidEPN() {

        try {

            Requestor req = createRequestor();
            req.setEpn("TestEPN");
            _requestorService.createRequestor(req);
            fail("Creating requestor with invalid epn prefix is not permitted, but created");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.EPN_PREFIX_DIFFERS);
        }
    }

    /**
     * This method is to cover the equals method
     */
    public void testCreateRequestorWithEquals() {

        try {

            Requestor req = new Requestor(_createdRequestorId);
            _requestorService.equals(req);
        } catch (Exception e) {
            fail("Requestor equals method not covered");
        }
    }

    public void testRequestorConcurrency() {

        try {

            Requestor req = createRequestor();
            long id = _requestorService.createRequestor(req);
            Requestor copyRequestor = _requestorService.retrieveRequestor(id, false);

            _requestor1 = (Requestor) deepCopy(copyRequestor);
            _requestor1.setLastName("testdeep");
            _requestorService.updateRequestor(_requestor1);

            _requestor2 = (Requestor) deepCopy(copyRequestor);
            _requestor2.setLastName("testanother");
            _requestorService.updateRequestor(_requestor2);

            fail("service method updateRequestor() should thrown Exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_INUSE);
        } catch (Exception e) {
            fail("service method updateRequestor should not thrown Exception.");
        }
    }

    /**
     * This method is to find all requestor with invalid epn length
     */
    public void testFindAllRequestorsWithInvalidEPNLength() {

        try {

            initSession(null);
            loginUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
            getUser().setEpnEnabled(true);
            getUser().setEpnPrefix(null);
            initSession(getUser());

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_invalidName);
            searchCriteria.setEpn("A");
            searchCriteria.setMaxCount(MAX_COUNT);
             _requestorService.findRequestor(searchCriteria);
            fail("Searching allrequestors with name length < 2 character is not permitted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_EPN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
        }
    }

    /**
     * This method is to find all requestor with valid epn length
     */
    public void testFindAllRequestorsWithValidEPNLength() {

        try {

            initSession(null);
            loginUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
            getUser().setEpnEnabled(true);
            getUser().setEpnPrefix("EPN");
            initSession(getUser());

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_invalidName);
            searchCriteria.setEpn(_epnValue);
            searchCriteria.setSsn(_ssnValue);
            searchCriteria.setMrn("MRN");
            searchCriteria.setMaxCount(MAX_COUNT);

            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            assertTrue(searchResult.getSearchResults().size() == 0);
        } catch (ROIException e) {
            fail("Searching all requestor with valid epn length should not thrown exception"
                 + e.getErrorCode());
        }
    }

    /**
     * This method is to find all requestor with epn as disabled
     */
    public void testFindAllRequestorsWithDisableEPN() {

        try {

            initSession(null);
            loginUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
            getUser().setEpnEnabled(false);
            initSession(getUser());

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(false);
            searchCriteria.setType(_patientRequestorTypeId);
            searchCriteria.setLastName(_invalidName);
            searchCriteria.setEpn(EPN);
            searchCriteria.setMaxCount(MAX_COUNT);

            searchCriteria.setMaxCount(MAX_COUNT);
             _requestorService.findRequestor(searchCriteria);
            fail("Searching all requestors with epn length "
                 + "less then three character is not permitted");

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_EPN_SHOULD_HAVE_ATLEAST_THREE_CHARACTERS);
        }
    }

    /**
     * This method test matching requestor for given matchCriteria list
     */
    public void testSearchMatchingRequestorsEpnEnabled() {

        getUser().setEpnEnabled(true);
        List<MatchCriteria> list = new ArrayList <MatchCriteria>();

        MatchCriteria p1 = new MatchCriteria();
        p1.setLastName("test");
        p1.setFirstName("test");
        p1.setSsn("test");
        list.add(p1);

        MatchCriteria p2 = new MatchCriteria();
        p2.setLastName("TEST");
        p2.setFirstName("TEST");
        p2.setEpn("TEST");
        list.add(p2);

        MatchCriteriaList mcl = new MatchCriteriaList();
        mcl.setMatchCriteria(list);
        try {
            _requestorService.searchMatchingRequestors(mcl);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MATCH_CRITERIA_LIST_INVALID);
        }
    }

    /**
     * This method test user data coverage detail
    */
    public void testUserDetailCoverage() {

        try {
            getUser().setMaskBy("user");
            getUser().setMaskSSN(false);
            assertEquals("user", getUser().getMaskBy());
            assertEquals(false, getUser().getMaskSSN());
        } catch (Exception e) {
            fail("User Data Not covered");
        }
    }

    /**
     * This method test matching requestor for given matchCriteria list
     */
    public void testSearchMatchingRequestors() {

        initSession(null);
        loginUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
        getUser().setEpnEnabled(false);
        initSession(getUser());

        List<MatchCriteria> list = new ArrayList <MatchCriteria>();

        MatchCriteria p1 = new MatchCriteria();
        p1.setLastName(_matchingReq.getLastName());
        p1.setFirstName(_matchingReq.getFirstName());
        p1.setSsn(_matchingReq.getSsn());
        list.add(p1);

        MatchCriteria p2 = new MatchCriteria();
        p2.setLastName(_matchingReq.getLastName());
        p2.setFirstName(_matchingReq.getFirstName());
        p2.setEpn(_matchingReq.getEpn());
        list.add(p2);

        MatchCriteria p3 = new MatchCriteria();
        p3.setLastName(_matchingReq.getLastName());
        p3.setFirstName(_matchingReq.getFirstName());
        p3.setDob(_matchingReq.getDob());
        list.add(p3);

        MatchCriteria p4 = new MatchCriteria();
        p4.setSsn(_matchingReq.getSsn());
        p4.setEpn(_matchingReq.getEpn());
        list.add(p4);

        MatchCriteria p5 = new MatchCriteria();
        p5.setSsn(_matchingReq.getSsn());
        p5.setDob(_matchingReq.getDob());
        list.add(p5);

        MatchCriteria p6 = new MatchCriteria();
        p6.setEpn(_matchingReq.getEpn());
        p6.setDob(_matchingReq.getDob());
        list.add(p6);

        MatchCriteriaList mcl = new MatchCriteriaList();
        mcl.setMatchCriteria(list);

        try {

            RequestorSearchResult res = _requestorService.searchMatchingRequestors(mcl);
            assertTrue(res.getSearchResults().size() > 0);
        } catch (ROIException e) {
            fail("Matching requestor should not thrown an exception");
        }
    }

    /**
     * This method test matching requestor for given matchCriteria list
     */
    public void testSearchMatchingRequestorsDobNull() {

        initSession(null);
        loginUser(DEFAULT_TEST_USER, DEFAULT_TEST_PWD);
        getUser().setEpnEnabled(false);
        initSession(getUser());

        ArrayList<MatchCriteria> list = new ArrayList <MatchCriteria>();

        MatchCriteria p1 = new MatchCriteria();
        p1.setLastName(_matchingReq.getLastName());
        p1.setSsn(_matchingReq.getSsn());
        list.add(p1);

        MatchCriteria p2 = new MatchCriteria();
        p2.setLastName(_matchingReq.getLastName());
        p2.setEpn(_matchingReq.getEpn());
        list.add(p2);

        MatchCriteria p4 = new MatchCriteria();
        p4.setSsn(_matchingReq.getSsn());
        p4.setEpn(_matchingReq.getEpn());
        list.add(p4);

        MatchCriteriaList mcl = new MatchCriteriaList();
        mcl.setMatchCriteria(list);

        try {

            RequestorSearchResult res = _requestorService.searchMatchingRequestors(mcl);
            assertTrue(res.getSearchResults().size() > 0);
        } catch (ROIException e) {
            fail("Matching requestor should not thrown an exception");
        }
    }

    public void testSearchMatchingRequestorsWithEmptyList() {

        MatchCriteriaList mcl = new MatchCriteriaList();
        try {
             _requestorService.searchMatchingRequestors(mcl);
            fail("testSearchMatchingRequestorsWithEmptyList() should throw an exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.EMPTY_MATCH_CRITERIA_LIST);
        }
    }

    public void testSearchMatchingRequestorsWithEmptyCriteria() {

        MatchCriteriaList mcl = new MatchCriteriaList();
        ArrayList<MatchCriteria> list = new ArrayList <MatchCriteria>();
        MatchCriteria mc = new MatchCriteria();
        list.add(mc);
        mcl.setMatchCriteria(list);

        try {
             _requestorService.searchMatchingRequestors(mcl);
            fail("testSearchMatchingRequestorsWithEmptyList() should throw an exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.MATCH_CRITERIA_LIST_CONTAINS_EMPTY_MATCH_CRITERIA);
        }
    }

    public void testSearchMatchingRequestorWithoutSession() {

        initSession(null);
        List<MatchCriteria> list = new ArrayList <MatchCriteria>();
        MatchCriteria p1 = new MatchCriteria();
        p1.setLastName(_matchingReq.getLastName());
        p1.setSsn(_matchingReq.getSsn());
        list.add(p1);

        MatchCriteriaList mcl = new MatchCriteriaList();
        mcl.setMatchCriteria(list);

        try {
            RequestorSearchResult res = _requestorService.searchMatchingRequestors(mcl);
             assertTrue(res.getSearchResults().size() > 0);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_OPERATION_FAILED);
        }
    }

    /*
     * test for getting associated request per requester
     *
     * */

    public void testGetAssociatedRequestCount(){
        long reatVal = _requestorDao.getAssociatedRequestCount(1001);
        assertTrue(reatVal > 0);
    }


    /*
     * Test method to find the all requestors based on the valid search criteria
     */
    public void testFindAllRequestorsForRequestorType() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setActiveRequestors(true);
            searchCriteria.setType(REQUESTOR_TYPE_ID);
            searchCriteria.setMaxCount(MAX_COUNT);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            int size =    searchResult.getSearchResults().size();
            assertNotSame("The search result size should be greater than zero, resultSize: " + size,
                          0,
                       size);

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }


    public void testFindAllRequestorsNOTypeSpecified() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setAllStatus(true);
            searchCriteria.setMaxCount(MAX_COUNT);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            int size =    searchResult.getSearchResults().size();
            assertNotSame("The search result size should be greater than zero, resultSize: " + size,
                          0,
                       size);

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }



    public void testFindAllRequestorsWithTypeSpecified() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(true);
            searchCriteria.setActiveRequestors(true);
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setType(REQUESTOR_TYPE_ID);
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            int size =    searchResult.getSearchResults().size();
            assertNotSame("The search result size should be greater than zero, resultSize: " + size,
                          0,
                       size);

        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }


    public void testFindRecentRequestorsForType() {

        try {

            RequestorSearchCriteria searchCriteria = new  RequestorSearchCriteria();
            searchCriteria.setAllRequestors(false);
            searchCriteria.setActiveRequestors(true);
            searchCriteria.setType(REQUESTOR_TYPE_ID);
            searchCriteria.setMaxCount(MAX_COUNT);
            searchCriteria.setRecentRequestorDate(new Date());
            RequestorSearchResult searchResult = _requestorService.findRequestor(searchCriteria);
            int size = searchResult.getSearchResults().size();
            assertNotSame("The search result size should be greater than zero, resultSize: " + size,
                          0,
                       size);


        } catch (ROIException e) {
            fail("Searching all requestors with valid input should not thrown exception."
                 + e.getErrorCode());
        }
    }


    private static Requestor _requestor1;
    private static Requestor _requestor2;
    public Object deepCopy(Object oldObj) throws Exception {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(oldObj);              // serialize and pass the object
            oos.flush();
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); // E
            ois = new ObjectInputStream(bin);
            return ois.readObject();                 // return the new object
        } catch (Exception e) {
            System.out.println("Exception in ObjectCloner = " + e);
            throw (e);
        } finally {
            oos.close();
            ois.close();
        }
    }

    private Requestor createRequestor() {

        Requestor req = new Requestor();
        req.setPrePayRequired(true);
        req.setCertLetterRequired(true);
        req.setType(_patientRequestorTypeId);
        req.setLastName("Requestor" + _requestorCount);
        req.setFirstName("Requestorfname" + _requestorCount);
        req.setEpn(_epnValue + "ABC" + _requestorCount);
        req.setSsn("SSN" + _requestorCount);
        req.setFacility("FACILITY" + _requestorCount);
        req.setFreeFormFacility(true);
        req.setMrn("MRN" + _requestorCount);
        req.setDob(_validDob);
        req.setHomePhone("11111" + _requestorCount);
        req.setWorkPhone("22222" + _requestorCount);
        req.setCellPhone("33333" + _requestorCount);
        req.setEmail("requestor" + _requestorCount + "@mck.com");
        req.setFax("5555555" + _requestorCount);
        req.setContactName("Contact" + _requestorCount);
        req.setContactEmail("contact" + _requestorCount + "@mck.com");
        req.setContactPhone("4444444" + _requestorCount);

        Address main = new Address();
        main.setAddress1("MainAddress1" + _requestorCount);
        main.setAddress2("MainAddress2" + _requestorCount);
        main.setAddress3("MainAddress3" + _requestorCount);
        main.setCity("Atlanta");
        main.setState("Alpahretta");
        main.setCountryCode("USA");
        main.setPostalCode("11001-2678");
        main.setCountrySeq(-273L);
        main.setNewCountry(true);

        Address alt = new Address();
        alt .setAddress1("AltAddress1" + _requestorCount);
        alt .setAddress2("AltAddress2" + _requestorCount);
        alt .setAddress3("AltAddress3" + _requestorCount);
        alt .setCity("Atlanta");
        alt .setState("Alpharetta");
        alt .setPostalCode("11006-1278");
        alt.setCountryCode("USA");
        alt.setCountrySeq(-273L);
        alt.setNewCountry(true);

        req.setAltAddress(alt);
        req.setMainAddress(main);
        _requestorCount++;
        return req;
    }

    private Requestor createRequestorWithMaxLengthFields() {

        Requestor req = new Requestor();
        req.setPrePayRequired(true);
        req.setCertLetterRequired(true);
        req.setType(_patientRequestorTypeId);
        req.setLastName("Requestor" + getMaxString());
        req.setEpn(_epnValue + getMaxString());
        req.setSsn("SSN" + getMaxString());
        req.setMrn("MRN" + getMaxString());
        req.setFacility("FACILITY" + getMaxString());
        Date dt = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                                                               .ROI_DATE_FORMAT),
                                                               "10/09/2020");
        req.setDob(dt);
        req.setHomePhone("11111" + getMaxString());
        req.setWorkPhone("22222" + getMaxString());
        req.setCellPhone("33333" + getMaxString());
        req.setEmail("requestor" + getMaxString() + "@mck.com");
        req.setFax("5555555" + getMaxString());
        req.setContactName("Contact" + getMaxString());
        req.setContactEmail("contact" + getMaxString() + "@mck.com");
        req.setContactPhone("4444444" + getMaxString());

        Address main = new Address();
        main.setAddress1("MainAddress1" + getMaxString());
        main.setAddress2("MainAddress2" + getMaxString());
        main.setAddress3("MainAddress3" + getMaxString());
        main.setCity(getMaxString());
        main.setState(getMaxString());
        main.setPostalCode(getMaxString());

        Address alt = new Address();
        alt.setAddress1("AltAddress1" + getMaxString());
        alt.setAddress2("AltAddress2" + getMaxString());
        alt.setAddress3("AltAddress3" + getMaxString());
        alt.setCity(getMaxString());
        alt.setState(getMaxString());
        alt.setPostalCode("1100678" + getMaxString());

        req.setAltAddress(alt);
        req.setMainAddress(main);
        _requestorCount++;
        return req;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    private void clearRequestorDetails(Requestor requestor) {

        requestor.getRelatedAddress().clear();
        requestor.getRelatedEmailPhones().clear();
        requestor.getRelatedContacts().clear();
        requestor.getRequestorDetails().clear();
    }

    private String getMaxString() {
        return  new StringBuffer().append("TESTPATIENTMAXLENGTHTESTPATIENTMAXLENGTHTESTPATIENTEND")
                                  .append("MAXLENGTHTESTPATIENTMAXLENGTHTESTPATIENTMAXLENGTHTEEND")
                                  .append("MAXLENGTHTESTPATIENTMAXLENGTHTESTPATIENTMAXLENGTHTEEND")
                                  .append("MAXLENGTHTESTPATIENTMAXLENGTHTESTPATIENTMAXLENGTHTEEND")
                                  .append("MAXLENGTHTESTPATIENTMAXLENGTHTESTPATIENTMAXLENGTHTEEND")
                                  .toString();
    }

    private RequestCore requestCreation(Requestor requestor) {

        RequestCore req = new RequestCore();
        RequestorCore requestorDetail = new RequestorCore();

        requestorDetail.setContactName("XX-YY");
        requestorDetail.setContactPhone("123456789");
        requestorDetail.setFirstName("ZZZ");
        requestorDetail.setRequestorTypeName(requestor.getRequestorType());
        requestorDetail.setRequestorType(requestor.getType());
        requestorDetail.setRequestorSeq(requestor.getId());



        req.setStatus(ROIConstants.STATUS_LOGGED);
        req.setRequestorDetail(requestorDetail);
        req.setRequestReason("reason");
        req.setReceiptDate(new Date());

        req.setStatusReason("reason1,reason2");

        return req;
    }

    /**
     * This test case for createRequestorType and verify if it returns the newly created
     * requestorTypeId
     */
    public void createRequestorType() {

        try {

            RequestorType rt = _adminService.createRequestorType(baseCreation());
            _createdRequestorTypeId = rt.getId();
            assertNotSame("Created requestor type id should be greater than zero", 0, rt.getId());
        } catch (ROIException e) {
            fail("Creating requestor type should not thrown exception." + e.getErrorCode());
        }
    }

    /**
     * Create a Fee Type for testing Billing Template
     */
    public void createFeeTypeForTesting() {

        try {

            FeeType feeType = new FeeType();
            feeType.setName("FeeName" + System.nanoTime());
            feeType.setDescription("FeeDesc");
            final double ca = 234.45;
            feeType.setChargeAmount(ca);

            _feeTypeId = _service.createFeeType(feeType);
        } catch (ROIException e) {
            fail("create feetype should not thrown exception" + e.getErrorCode());
        }
    }

    /**
     * Tests creation of Billing Template
     */
    public void createBillingTemplate() {

        try {

            BillingTemplate billingTemplate = new BillingTemplate();
            billingTemplate.setName(_billingTemplateName + System.nanoTime());

            _btfSet = new HashSet<RelatedFeeType>();
            RelatedFeeType btf = new RelatedFeeType();
            btf.setFeeTypeId(_feeTypeId);
            _btfSet.add(btf);

            billingTemplate.setRelatedFeeTypes(_btfSet);

           _service.createBillingTemplate(billingTemplate);
        } catch (ROIException e) {
            fail("Creating billing template should not thrown exception." + e.getErrorCode());
        }
    }

    private RequestorType baseCreation() {

        List<BillingTemplate> btList = _btList.getBillingTemplates();

        RequestorType rt = new RequestorType();
        rt.setName(_requestorTypeName);
        rt.setDescription(_requstorTypeDesc);
        rt.setRv(_rv);
        rt.setRvDesc(_rvDesc);
        rt.setIsAssociated(true);
        _rtBTemplate = new HashSet <RelatedBillingTemplate>();
        RelatedBillingTemplate rtBT = new RelatedBillingTemplate();
        rtBT.setBillingTemplateId(btList.get(0).getId());
        rtBT.setIsDefault(true);

        _rtBTemplate.add(rtBT);
        rt.setRelatedBillingTemplate(_rtBTemplate);

        _rtBTier = new HashSet<RelatedBillingTier>();
        RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
        hpfBillingTier.setBillingTierId(HPF_BILLINGTIER_ID);
        hpfBillingTier.setIsHPF(true);
        hpfBillingTier.setIsHECM(false);
        hpfBillingTier.setIsCEVA(false);
        hpfBillingTier.setIsSupplemental(false);

        RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
        nonHpfBillingTier.setBillingTierId(NON_HPF_BILLINGTIER_ID);
        nonHpfBillingTier.setIsHPF(false);
        nonHpfBillingTier.setIsHECM(false);
        nonHpfBillingTier.setIsCEVA(false);
        nonHpfBillingTier.setIsSupplemental(true);

        _rtBTier.add(hpfBillingTier);
        _rtBTier.add(nonHpfBillingTier);
        rt.setRelatedBillingTier(_rtBTier);
        return rt;
    }

    @Test
    public void testRetrieveRequestorInvoicesDAO() {
       RequestorInvoicesList reqInvList =  _requestorDao.retrieveRequestorInvoices(1001);
       Assert.assertNotNull(reqInvList);

    }

    @Test
    public void testRetrieveRequestorInvoicesService() {
       RequestorInvoicesList reqInvList =  _requestorService.retrieveRequestorInvoices(1001);
       Assert.assertNotNull(reqInvList);

    }

    @Test
    public void testRetrieveRequestorInvoicesWihInvalidId() {

        try {

            _requestorService.retrieveRequestorInvoices(0);
            fail("Retrieve Requestor invoices for the requestor Id '0' should throw exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }

    }

    public void testRetrieveAdjustmentInfo(){
        AdjustmentInfo adjInfo= _requestorService.retrieveAdjustmentInfo(1001);
        Assert.assertNotNull(adjInfo);

    }
    public void testSaveAdjustmentInfo(){

        AdjustmentInfo adjustmentInfo = new AdjustmentInfo();

        RequestorAdjustment requestorAdjustment= new RequestorAdjustment();
        requestorAdjustment.setAdjustmentDate(new Date());
        requestorAdjustment.setCreatedDt(new Date());
        requestorAdjustment.setModifiedDt(new Date());
        requestorAdjustment.setAmount(20.0);
        requestorAdjustment.setUnappliedAmount(70.0);
        requestorAdjustment.setNote("test test");
        requestorAdjustment.setInvoiceSeq(1001);
        requestorAdjustment.setRequestorSeq(1001);
        requestorAdjustment.setReason("reason");
        requestorAdjustment.setAdjustmentType(AdjustmentType.BILLING_ADJUSTMENT);
        adjustmentInfo.setRequestorAdjustment(requestorAdjustment);

        RequestorInvoicesList requestorInvoicesList = new RequestorInvoicesList();
        RequestorInvoice requestorInvoice = new RequestorInvoice();
        requestorInvoice.setId(1001);
        requestorInvoice.setBalance(32.12);
        requestorInvoice.setAppliedAmount(21.87);
        requestorInvoice.setApplyAmount(21.87);
        requestorInvoice.setRequestId(1001L);
        List<RequestorInvoice> invoicesList = new ArrayList<RequestorInvoice>();
        invoicesList.add(requestorInvoice);
        requestorInvoicesList.setRequestorInvoices(invoicesList);
        adjustmentInfo.setRequestorInvoicesList(requestorInvoicesList);

        try {

            _requestorService.saveAdjustmentInfo(adjustmentInfo);
        } catch(ROIException e) {
            fail("Saving Adjustment Info should not thrown exception." + e.getErrorCode());
        }

    }

    public void testSaveAdjustmentInfoWithUpdateValues(){

        AdjustmentInfo adjustmentInfo = new AdjustmentInfo();

        RequestorAdjustment requestorAdjustment= new RequestorAdjustment();
        requestorAdjustment.setAdjustmentDate(new Date());
        requestorAdjustment.setCreatedDt(new Date());
        requestorAdjustment.setModifiedDt(new Date());
        requestorAdjustment.setAmount(20.0);
        requestorAdjustment.setUnappliedAmount(70.0);
        requestorAdjustment.setNote("test test");
        requestorAdjustment.setInvoiceSeq(1001);
        requestorAdjustment.setRequestorSeq(1001);
        requestorAdjustment.setReason("reason");
        requestorAdjustment.setAdjustmentType(AdjustmentType.BILLING_ADJUSTMENT);
        adjustmentInfo.setRequestorAdjustment(requestorAdjustment);

        RequestorInvoicesList requestorInvoicesList = new RequestorInvoicesList();
        RequestorInvoice requestorInvoice = new RequestorInvoice();
        requestorInvoice.setId(1001);
        requestorInvoice.setBalance(32.12);
        requestorInvoice.setAppliedAmount(21.87);
        requestorInvoice.setApplyAmount(21.87);
        requestorInvoice.setRequestId(1001L);
        List<RequestorInvoice> invoicesList = new ArrayList<RequestorInvoice>();
        invoicesList.add(requestorInvoice);
        requestorInvoicesList.setRequestorInvoices(invoicesList);
        adjustmentInfo.setRequestorInvoicesList(requestorInvoicesList);

        try {

            long adjustmentId = _requestorDao.saveAdjustmentInfo(requestorAdjustment);
            requestorAdjustment.setId(adjustmentId);
            _requestorService.saveAdjustmentInfo(adjustmentInfo);

            requestorInvoice.setBalance(22.12);
            requestorInvoice.setAppliedAmount(31.87);
            requestorInvoice.setApplyAmount(31.87);
            requestorInvoice.setRequestId(1001L);
            requestorInvoice.setId(adjustmentId);

            _requestorService.saveAdjustmentInfo(adjustmentInfo);

        } catch(ROIException e) {
            fail("Saving Adjustment Info should not thrown exception." + e.getErrorCode());
        }

    }

    public void testRetrieveAdjustmentInfoByAdjustmentId() {
        AdjustmentInfo adjInfo= _requestorService.retrieveAdjustmentInfoByAdjustmentId(1,1001);
        Assert.assertNotNull(adjInfo);
    }

    private RequestorStatementCriteria constructRequestorStatementCriteria() {

        RequestorStatementCriteria criteria = new RequestorStatementCriteria();
        criteria.setDateRange(DateRange.DAYS_60);
        criteria.setOutputMethod("SaveAsFile");
        criteria.setTemplateFileId(getLetterTemplateFileId());
        criteria.setTemplateName("RequestorLetter");

        Requestor requestor = createRequestor();
        long reqId = _requestorService.createRequestor(requestor);
        criteria.setRequestorId(reqId);

        return criteria;
    }

    public void testRetrieveRequestorSummaries()
    {
        RequestorHistoryList reqHisList = _requestorService.retrieveRequestorSummaries(1513);
        Assert.assertNotNull(reqHisList);
    }

    public void testViewRequestorDetails() {

        try {

            DocInfo docInfo = _requestorService.viewRequestorDetails(1001, _request.getId(),
                    ROIConstants.TEMPLATE_FILE_TYPE,"Invoice",
                                                                        "Invoice");
            Assert.assertNotNull(docInfo);
        } catch (ROIException e) {
            fail("Viewing Requestor Detail should not thrown exception." + e.getErrorCode());
        }
    }

    public void testRetrieveUnappliedAmountDetails()
    {
        RequestorUnappliedAmountDetailsList reqList = _requestorService.retrieveUnappliedAmountDetails(1000654);
        Assert.assertNotNull(reqList);
    }

    public void testDeleteRequestorPayment() {

        try {
            _requestorService.deleteRequestorPayment(1003, "Requestor");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
        }
    }

    public void testDeleteInvalidRequestorPayment() {

        try {
            _requestorService.deleteRequestorPayment(Long.MAX_VALUE, "Requestor");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_PAYMENT_ID);
        }
    }

    public void testDeleteRequestorAdjustment() {

        try {
            _requestorService.saveAdjustmentInfo(constructAdjustmentInfo(1003));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DELETE_REQUESTOR_ADJUSTMENT_OPERATION_FAILED);
        }
    }

    public void testDeleteInvalidRequestorAdjustment() {

        try {
            _requestorService.saveAdjustmentInfo(constructAdjustmentInfo(Long.MAX_VALUE));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_ADJUSTMENT_ID);
        }
    }

    public void testUpdateAdjustmentInfo(){

        try {

            long adjustmentId = _requestorDao.saveAdjustmentInfo(constructRequestorAdjustment());

            RequestorAdjustment reqAdjustment = _requestorDao.retrieveRequestorAdjustment(adjustmentId);

            reqAdjustment.setUnappliedAmount(reqAdjustment.getUnappliedAmount() - 10);
            reqAdjustment.setModifiedDt(new Date());
            reqAdjustment.setModifiedBy(1);

            _requestorDao.updateRequestorAdjustment(reqAdjustment);

        } catch(ROIException e) {
            fail("Saving Adjustment Info should not thrown exception." + e.getErrorCode());
        }

    }

    private AdjustmentInfo constructAdjustmentInfo(long id) {

        RequestorAdjustment adjustment = new RequestorAdjustment();
        adjustment.setRequestorName("Requestor");
        adjustment.setId(id);
        adjustment.setDelete(true);
        AdjustmentInfo adjInfo = new AdjustmentInfo();
        adjInfo.setRequestorAdjustment(adjustment);
        return adjInfo;

    }

    public void testCreateRequestorPayment() {

        try {
            _requestorService.createRequestorPayment(constructPaymentDetails());
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT);
        }
    }

    public void testCreateRequestorPaymentWithoutApply() {

        try {
            _requestorService.createRequestorPayment(constructRequestorPaymentInfo(
                    false, true, false, false));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT);
        }
    }

    public void testCreateRequestorPaymentWithApply() {

        try {
            _requestorService.createRequestorPayment(constructRequestorPaymentInfo(
                                                true, true, false, false));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT);
        }
    }

    public void testUpdateRequestorPayment() {

        try {
            _requestorService.updateRequestorPayment(constructRequestorPaymentInfo(
                                                true, false, false, false));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UPDATE_REQUESTOR_PAYMENT);
        }
    }

    public void testUpdateRequestorPaymentWithNewApply() {

        try {

            RequestorPaymentList paymentList = constructPaymentDetails();
            long paymentId = _requestorDao.createRequestorPayment(paymentList);
            paymentList.setPaymentId(paymentId);

            RequestorInvoice reqInvoice = null;
            List<RequestorInvoice> reqInvoices = getRequestorInvoice(paymentList.getRequestorId());

            for (RequestorInvoice reqInv : reqInvoices) {

                if ("Open Invoice".equalsIgnoreCase(reqInv.getInvoiceType())) {
                    reqInvoice = reqInv;
                    break;
                }
            }

            RequestorPayment payment = new RequestorPayment();
            payment.setFacility(reqInvoice.getBillingLocation());
            payment.setInvoiceBalance(0);
            payment.setLastAppliedAmount(reqInvoice.getBalance());
            payment.setRequestCoreDeliveryChargesId(reqInvoice.getId());
            payment.setRequestId(reqInvoice.getRequestId());
            payment.setTotalAppliedAmount(reqInvoice.getBalance());
            payment.setPaymentId(paymentId);

            List<RequestorPayment> payments = new ArrayList<RequestorPayment>();
            payments.add(payment);
            paymentList.setPaymentList(payments);

            _requestorService.updateRequestorPayment(paymentList);

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UPDATE_REQUESTOR_PAYMENT);
        }
    }

    public void testDeleteRequestorApplyPayment() {

        try {
            _requestorService.updateRequestorPayment(constructRequestorPaymentInfo(
                                                false, false, true, false));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.UPDATE_REQUESTOR_PAYMENT);
        }
    }

    public void testCreateRequestorPaymentWithoutRequestorId() {

        RequestorPaymentList paymentList = new RequestorPaymentList();
        paymentList.setPaymentAmount(100);
        paymentList.setPaymentDate(new Date());
        paymentList.setPaymentMode("Check");
        paymentList.setRequestorName(_requestorName);
        paymentList.setRequestorType(_requestorTypeName);
        paymentList.setUnAppliedAmount(100);
        paymentList.setDescription("Payment_$100");

        try {
            _requestorService.createRequestorPayment(paymentList);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }
    }

    public void testCreateRequestorPaymentAndCloseInvoice() {

        try {
            _requestorService.createRequestorPayment(constructRequestorPaymentInfo(
                                                true, false, false, true));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CREATE_REQUESTOR_PAYMENT);
        }
    }

    public void testUpdateRequestorPaymentAndCloseInvoice() {

        try {
            _requestorService.updateRequestorPayment(constructRequestorPaymentInfo(
                                                true, false, false, true));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_PAYMENT_IS_NULL);
        }
    }

    public void testUpdateRequestorPaymentWithoutPaymentId() {

        try {
            RequestorPaymentList paymentList = constructRequestorPaymentInfo(
                                                false, false, false, false);
            paymentList.setPaymentList(new ArrayList<RequestorPayment>());
            _requestorService.updateRequestorPayment(paymentList);
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_PAYMENT_IS_NULL);
        }
    }

    public void testUpdateRequestorPaymentWithoutMappedInvoices() {

        try {
            _requestorService.updateRequestorPayment(constructRequestorPaymentInfo(
                                                false, false, false, false));
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_PAYMENT_IS_NULL);
        }
    }

    public void testCreateRequestorRefund() {

        try {

            RequestorPaymentList paymentDetails = constructPaymentDetails();
            paymentDetails.setPaymentAmount(100000.00);
            paymentDetails.setUnAppliedAmount(100000.00);
            _requestorService.createRequestorPayment(paymentDetails);
            _requestorService.createRequestorRefund(constructRequestorRefund());

        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.CREATE_REQUESTOR_REFUND_OPERTARION_FAILED);
        }
    }

    public void testCreateRequestorRefundWithInvalidInput() {

        try {
            _requestorService.createRequestorRefund(new RequestorRefund());
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }
    }

    private RequestorPaymentList constructRequestorPaymentInfo(boolean applyToInvoice,
            boolean isNewPayment, boolean isDeleteApplyPayment, boolean isCloseInvoice) {


        RequestorPaymentList paymentList = constructPaymentDetails();
        List<RequestorInvoice> reqInvoices = getRequestorInvoice(paymentList.getRequestorId());

        if (null == reqInvoices || reqInvoices.isEmpty()) {
            return paymentList;
        }

        RequestorInvoice reqInvoice = null;
        for (RequestorInvoice reqInv : reqInvoices) {

            if ("Open Invoice".equalsIgnoreCase(reqInv.getInvoiceType())) {
                reqInvoice = reqInv;
                break;
            }
        }

        if (null == reqInvoice) {
            return paymentList;
        }

        if (isNewPayment) {

            RequestorPayment payment = new RequestorPayment();
            payment.setFacility(reqInvoice.getBillingLocation());
            payment.setInvoiceBalance(reqInvoice.getBalance() - 10);
            payment.setLastAppliedAmount(10);
            payment.setRequestCoreDeliveryChargesId(reqInvoice.getId());
            payment.setRequestId(reqInvoice.getRequestId());
            payment.setTotalAppliedAmount(10);

            List<RequestorPayment> payments = new ArrayList<RequestorPayment>();
            payments.add(payment);
            paymentList.setPaymentList(payments);

            return paymentList;

        } else {

            double totalAppliedAmountToInv = 0;
            double appliedAmount = 10;
            long paymentId = 0;
            boolean firstIterate = true;

            int i = 0;
            while (i < reqInvoices.size() && paymentId == 0) {

                reqInvoice = reqInvoices.get(i);
                for (RequestorAdjustmentsPayments adjPay : reqInvoice.getRequestorAdjPay()) {

                    if ("Payment".equals(adjPay.getTxnType())) {

                        if (firstIterate) {
                            paymentId = adjPay.getId();
                            totalAppliedAmountToInv += adjPay .getAppliedAmount();
                            firstIterate = false;
                        } else if (paymentId == adjPay.getId()) {
                            totalAppliedAmountToInv += adjPay.getAppliedAmount();
                        }
                    }

                }
                i++;
            }

            paymentList = getRequestorPayment(paymentId);
            RequestorPayment payment = new RequestorPayment();

            payment.setPaymentId(paymentId);
            payment.setFacility(reqInvoice.getBillingLocation());
            payment.setRequestCoreDeliveryChargesId(reqInvoice.getId());
            payment.setRequestId(reqInvoice.getRequestId());

            if (isDeleteApplyPayment) {
                payment.setInvoiceBalance(reqInvoice.getBalance() + totalAppliedAmountToInv);
                payment.setLastAppliedAmount(0);
                payment.setTotalAppliedAmount(0);
            } else if (isCloseInvoice) {
                payment.setInvoiceBalance(0);
                payment.setLastAppliedAmount(reqInvoice.getBalance());
                payment.setTotalAppliedAmount(totalAppliedAmountToInv + reqInvoice.getBalance());
            } else {
                payment.setInvoiceBalance(reqInvoice.getBalance() - appliedAmount);
                payment.setLastAppliedAmount(appliedAmount);
                payment.setTotalAppliedAmount(totalAppliedAmountToInv + appliedAmount);

            }

            List<RequestorPayment> payments = new ArrayList<RequestorPayment>();
            payments.add(payment);

            paymentList.setPaymentList(payments);
            if (isDeleteApplyPayment) {
                paymentList.setUnAppliedAmount(paymentList.getUnAppliedAmount() + totalAppliedAmountToInv);
            } else if (isCloseInvoice) {
                paymentList.setUnAppliedAmount(paymentList.getUnAppliedAmount() - reqInvoice.getBalance());
            } else {
                paymentList.setUnAppliedAmount(paymentList.getUnAppliedAmount() - appliedAmount);
            }

            paymentList.setRequestorId(REQUESTOR_ID);
            paymentList.setRequestorName(_requestorName);
            paymentList.setRequestorType(_requestorTypeName);

            return paymentList;
        }

    }

    private RequestorPaymentList constructPaymentDetails() {

        RequestorPaymentList paymentList = new RequestorPaymentList();
        paymentList.setPaymentAmount(100);
        paymentList.setPaymentDate(new Date());
        paymentList.setPaymentMode("Check");
        paymentList.setRequestorId(REQUESTOR_ID);
        paymentList.setRequestorName(_requestorName);
        paymentList.setRequestorType(_requestorTypeName);
        paymentList.setUnAppliedAmount(100);
        paymentList.setDescription("Payment_$100");
        paymentList.setCreatedBy(getUser().getInstanceIdValue());
        paymentList.setCreatedDt(new Date());
        paymentList.setModifiedBy(getUser().getInstanceIdValue());
        paymentList.setModifiedDt(new Date());

        return paymentList;
    }

    private List<RequestorInvoice> getRequestorInvoice(long requestorId) {

        RequestorInvoicesList invoiceList = _requestorService.retrieveRequestorInvoices(requestorId);

        List<RequestorInvoice> invoices = new ArrayList<RequestorInvoice>();
        if (!CollectionUtilities.isEmpty(invoiceList.getRequestorInvoices())) {
            for (RequestorInvoice requestorInvoice : invoiceList.getRequestorInvoices()) {

                if (requestorInvoice.getInvoiceType().contains("Invoice")) {
                    invoices.add(requestorInvoice);
                }
            }
        }

        return invoices;
    }

    private RequestorPaymentList getRequestorPayment(long paymentId) {

        return _requestorDao.retrieveRequestorPayment(paymentId);
    }

    private RequestorAdjustment constructRequestorAdjustment() {

        RequestorAdjustment adjustment = new RequestorAdjustment();
        adjustment.setAdjustmentDate(new Date());
        adjustment.setAdjustmentType(AdjustmentType.BILLING_ADJUSTMENT);
        adjustment.setAmount(0.0);
        adjustment.setAppliedAmount(0.0);
        adjustment.setCreatedBy(1);
        adjustment.setCreatedDt(new Date());
        adjustment.setModifiedBy(1);
        adjustment.setModifiedDt(new Date());
        adjustment.setNote("note");
        adjustment.setReason("Reason");
        adjustment.setRecordVersion(1);
        adjustment.setRequestorSeq(1001);
        adjustment.setUnappliedAmount(100.00);

        return adjustment;
    }

    private RequestorRefund constructRequestorRefund() {

        RequestorRefund reqRefund = new RequestorRefund();

        reqRefund.setNote("Refund_$1");
        reqRefund.setOutputMethod("Check");
        reqRefund.setRefundAmount(1);
        reqRefund.setRefundDate(new Date());
        reqRefund.setRequestorId(REQUESTOR_ID);
        reqRefund.setRequestorName(_requestorName);
        reqRefund.setRequestorType(_requestorTypeName);
        reqRefund.setTemplateId(getLetterTemplateFileId());
        reqRefund.setTemplateName("Test");

        RequestorStatementCriteria statementCriteria = new RequestorStatementCriteria();
        statementCriteria.setDateRange(RequestorStatementCriteria.DateRange.DAYS_60);
        List<String> notes = new ArrayList<String>();
        notes.add("Note1");
        notes.add("Note2");
        notes.add("Note3");
        statementCriteria.setNotes(notes);
        statementCriteria.setOutputMethod("Check");
        List<Long> pastInvIds = new ArrayList<Long>();
        pastInvIds.add(1001L);
        statementCriteria.setPastInvIds(pastInvIds);
        statementCriteria.setRequestorId(1001);
        statementCriteria.setTemplateFileId(getLetterTemplateFileId());
        statementCriteria.setTemplateName("Test");
        reqRefund.setStatementCriteria(statementCriteria);

        return reqRefund;

    }

    public void testViewRequestorRefundWithInvalidRefundDetails() {

        try {

            _requestorService.viewRequestorRefund(null);
        } catch(ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_REFUND_DETAILS);
        }
    }

    public void testViewRequestorRefundWithInvalidRequestorId() {

        try {

            RequestorRefund reqRefund = constructRequestorRefund();
            reqRefund.setRequestorId(-1);
            _requestorService.viewRequestorRefund(reqRefund);
        } catch(ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_REQUESTOR_ID);
        }
    }


    public void testViewRequestorRefund() {

        try {

            RequestorRefund requestorRefund = constructRequestorRefund();
            DocInfoList docList =  _requestorService.viewRequestorRefund(requestorRefund);
            assertNotNull(docList);
        } catch(ROIException ex) {
            fail("View Requestor Refund Operation Failed");
        }
    }

    @Override
    protected void tearDown() throws Exception { }
}
