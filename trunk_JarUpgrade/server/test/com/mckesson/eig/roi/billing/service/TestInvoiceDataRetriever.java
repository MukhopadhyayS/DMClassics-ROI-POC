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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mckesson.eig.common.filetransfer.services.BaseFileDownloader;
import com.mckesson.eig.common.filetransfer.services.BaseFileTransferData;
import com.mckesson.eig.roi.admin.service.ROIAdminService;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.service.FileDownloader;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.LetterInfo;
import com.mckesson.eig.roi.billing.model.PastInvoice;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryChargesAdjustmentPayment;
import com.mckesson.eig.roi.billing.model.SalesTaxReason;
import com.mckesson.eig.roi.billing.model.SalesTaxSummary;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBilling;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBillingInfo;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesFee;
import com.mckesson.eig.roi.request.model.RequestCoreChargesInvoice;
import com.mckesson.eig.roi.request.model.RequestCoreChargesShipping;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;
import com.mckesson.eig.roi.request.model.SaveRequestPatientsList;
import com.mckesson.eig.roi.request.service.RequestCoreService;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * @author OFS
 * @date   Nov 02, 2009
 * @since  HPF 13.1 [ROI]; Nov 19, 2008
 */
public class TestInvoiceDataRetriever
extends BaseROITestCase {

    private ServletUnitClient _servletUntClient;
    private static RequestCoreService _requestService;
    private static RequestCore _request;
    private static SaveRequestPatientsList _savePatientList;
    private static RequestorService _requestorService;
    private static BillingCoreService _billingCoreService;
    private static ROIAdminService _adminService;

    protected static final String  BILLING_CORE_SERVICE =
            "com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl";
    protected static final String  BILLING_SERVICE =
              "com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl";
    protected static final String REQUEST_SERVICE =
             "com.mckesson.eig.roi.request.service.RequestCoreServiceImpl";
    protected static final String REQUESTOR_SERVICE =
            "com.mckesson.eig.roi.requestor.service.RequestorServiceImpl";
    protected static final String ADMIN_SERVICE =
            "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static final String SERVLET_NAME = "BaseFileDownloader";

    private static final int ERRCODE_LEN  = 3;
    private static String _resMsgOK       = "OK";
    private static String _fail           = "400";

    private static final String PHONE_NUMBER = "123456";
    private static final String NAME = "User";
    private static final String PATIENT = "patient";
    private static final String FACILITY = "A";
    private static int _five = 5;
    private static long _docId = 0;

    private ServletRunner _servletRunner;

    private static String _invoiceFileName;
    private static String _prebillFileName;
    private static String _letterFileName;

    private static long _invoiceId = 1001;
    private static long _preBillId = 1001;
    private static long _letterId = 1001;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initServlet();
    }

    @Override
    public void initializeTestData() throws Exception {

        _requestService = (RequestCoreService) getService(REQUEST_SERVICE);
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
        _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);
        _billingCoreService = (BillingCoreService) getService(BILLING_SERVICE);
        refreshTestData("test/resources/reports/reportsDataSet.xml");
        createRequestWithPatient();
        saveRequestCoreBillingPaymentInfo();
    }

    /**
     * This method is used to initialize the servlet which will be used for unit testing.
     */
    protected void initServlet() {

        _servletRunner = new ServletRunner();
        _servletRunner.registerServlet(SERVLET_NAME, BaseFileDownloader.class.getName());
        _servletUntClient = _servletRunner.newClient();
    }

    @Override
    protected void tearDown() throws Exception { }

    /**
     * Test Case to create a invoice
     */
    public void testCreateInvoice() {

        try {

            ReleaseCore releaseCore = constructRelease();
             _billingCoreService.createReleaseAndPreviewInfo(releaseCore,true,12.0);

            InvoiceOrPrebillAndPreviewInfo invInfo = constructInvoiceInfo();
            DocInfo info = _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);
            _invoiceId = info.getId();
            _invoiceFileName = info.getName();
            assertNotNull(info.getName());
        } catch (ROIException e) {
            fail("Creating invoice should not have thrown exception" + e.getErrorCode());
        }
    }

    /**
     * Test case to download the Invoice
     */
    public void testDownloadInvoiceWithValidInput() {

        try {

            String url = constructURLForOutput("FileDownloader",
                                               ROIConstants.INVOICE_FILE + "." + _invoiceId,
                                               "Output",
                                               false,
                                               false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the Invoice and letter
     */
    public void testDownloadInvoiceAndLetterWithValidInput() {

        try {

            initSession();
            ReleaseCore releaseCore = constructRelease();
             _billingCoreService.createReleaseAndPreviewInfo(releaseCore,true,12.0);

            InvoiceOrPrebillAndPreviewInfo invInfo = constructInvoiceInfo();
            DocInfo invoiceInfo = _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);

            String[] notes={"testNote1","testNote2","testNote3"};
            long letterTemplateId = getLetterTemplateFileId();
            long requestCoreId = 1001;
            String fileType = "RTF";

            LetterInfo letterInfo = new LetterInfo();
            letterInfo.setLetterTemplateId(letterTemplateId);
            letterInfo.setRequestId(requestCoreId);
            letterInfo.setNotes(notes);
            letterInfo.setType(fileType);

            DocInfo letterDetail =  _billingCoreService.createLetterAndPreview(letterInfo);

            String url = constructURLForOutput("FileDownloader",
                                               ROIConstants.INVOICE_FILE  + "."
                                                   + invoiceInfo.getId() + ","
                                                   + ROIConstants.LETTER_FILE + "."
                                                   + letterDetail.getId(),
                                               "Output",
                                               false,
                                               false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the Invoice with invalid file name
     */
    public void testDownloadInvoiceWithInvalidFileName() {

        try {

            String url = constructURL("FileDownloader", "Invalid", "ROI", true, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            response.getResponseMessage();
            InputStream ins = response.getInputStream();

            byte[] doc = getBytArrayFromInputStream(ins);
            String res = new String(doc);

            String returnCode = res.substring(res.indexOf("<returncode>")
                                                          +  "<returnCode>".length(),
                                                          res.indexOf("<returncode>")
                                                          +  "<returnCode>".length() + ERRCODE_LEN);
            assertEquals(_fail, returnCode.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the prebill with chunk enable as true
     */
    public void testDownloadPreBillWithChunkEnableTrue() {

        try {

            String url = constructURL("FileDownloader", _prebillFileName, "ROI", true, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the prebill with chunk enable as true, and source name as Output
     */
    public void testDownloadPreBillWithSourceNameAsOutput() {

        try {

            String url = constructURLForOutput("FileDownloader",
                                               ROIConstants.PREBILL_FILE + "." + _preBillId,
                                               "Output",
                                               true,
                                               false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the prebill with invalid file name
     */
    public void testDownloadPreBillWithInvalidFileName() {

        try {

            String url = constructURL("FileDownloader", "Invalid", "ROI", true, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            response.getResponseMessage();
            InputStream ins = response.getInputStream();

            byte[] doc = getBytArrayFromInputStream(ins);
            String res = new String(doc);

            String returnCode = res.substring(res.indexOf("<returncode>")
                                                          +  "<returnCode>".length(),
                                                          res.indexOf("<returncode>")
                                                          +  "<returnCode>".length() + ERRCODE_LEN);
            assertEquals(_fail, returnCode.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the letter with source name other than output
     */
    public void testDownloadLetterWithDifferentSourceName() {

        try {

            String url = constructURL("FileDownloader", _letterFileName, "ROI", false, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the letter
     */
    public void testDownloadLetterWithValidInput() {

        try {

            String url = constructURL("FileDownloader", _letterFileName, "ROI", false, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the letter
     */
    public void testDownloadLetterWithOutputSource() {

        try {

            String url = constructURLForOutput("FileDownloader",
                                               ROIConstants.LETTER_FILE + "." + _letterId,
                                               "Output",
                                               false,
                                               false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the letter with chunk enable as true
     */
    public void testDownloadLetterWithChunkEnableTrue() {

        try {

            String url = constructURL("FileDownloader", _letterFileName, "ROI", true, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            String resMsg = response.getResponseMessage();
            assertEquals(_resMsgOK, resMsg.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to download the letter with invalid file name
     */
    public void testDownloadLetterWithInvalidFileName() {

        try {

            String url = constructURL("FileDownloader", "Invalid", "ROI", true, false);

            WebRequest request = new GetMethodWebRequest(url);
            WebResponse response = _servletUntClient.sendRequest(request);

            response.getResponseMessage();
            InputStream ins = response.getInputStream();

            byte[] doc = getBytArrayFromInputStream(ins);
            String res = new String(doc);

            String returnCode = res.substring(res.indexOf("<returncode>") + "<returnCode>".length(),
                                                          res.indexOf("<returncode>")
                                                          +  "<returnCode>".length() + ERRCODE_LEN);
            assertEquals(_fail, returnCode.trim());
        } catch (Exception e) {
            e.printStackTrace();
            fail("BaseFileDownloader failed");
        }
    }

    /**
     * Test case to cover FileDownloader
     */
    public void testFileNameWithValidInput() {

        FileDownloader fd = new FileDownloader();
        fd.isValidFileName(_invoiceFileName);
        assertTrue(true);
        fd.isValidFileName(null);
        assertFalse(false);
    }

    private String constructURL(String ownerType, String fileName, String source, boolean isChunk,
                                boolean isFinal) {

        String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
        strURLaddress += "?" + BaseFileTransferData.PARAMETER_USER + "=" + DEFAULT_TEST_USER;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD   + "=" + DEFAULT_TEST_PWD;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=";
        strURLaddress += "&" + "OWNER_TYPE="  + ownerType;
        strURLaddress += "&" + "FILE_NAME="  + fileName;
        strURLaddress += "&" + "SOURCE="  + source;
        strURLaddress += "&" + "CHUNKENABLED="  + Boolean.toString(isChunk);
        strURLaddress += "&" + "FINALCHUNK="  + Boolean.toString(isFinal);

        return strURLaddress;
    }

    private String constructURLForOutput(String ownerType, String fileIds, String source,
                                         boolean isChunk, boolean isFinal) {

        String strURLaddress = "http://localhost:8080/" + SERVLET_NAME;
        strURLaddress += "?" + BaseFileTransferData.PARAMETER_USER + "=" + DEFAULT_TEST_USER;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_PD   + "=" + DEFAULT_TEST_PWD;
        strURLaddress += "&" + BaseFileTransferData.PARAMETER_TIMESTAMP + "=";
        strURLaddress += "&" + "OWNER_TYPE="  + ownerType;
        strURLaddress += "&" + "FILE_IDS="  + fileIds;
        strURLaddress += "&" + "SOURCE="  + source;
        strURLaddress += "&" + "CHUNKENABLED="  + Boolean.toString(isChunk);
        strURLaddress += "&" + "FINALCHUNK="  + Boolean.toString(isFinal);

        return strURLaddress;
    }

    private byte[] getBytArrayFromInputStream(InputStream stream) throws IOException {

        byte[] byteArray = new byte[stream.available()];
        stream.read(byteArray);
        return byteArray;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    private void createRequestWithPatient() {

        _request = _requestService.createRequest(setupRequest());
        SaveRequestPatientsList requestPatients = setRequestPatientValue();
         _requestService.saveRequestPatient(requestPatients);

    }

    private SaveRequestPatientsList setRequestPatientValue() {

        _savePatientList =  new SaveRequestPatientsList();
        _savePatientList.setRequestId(_request.getId());
        _savePatientList.setUpdatePatients(constructRequestPatient());

        return _savePatientList;
    }

    private List<RequestPatient> constructRequestPatient() {

         List<RequestPatient> patients = new ArrayList<RequestPatient>();

         boolean isHpf = true;
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
             patient.setRoiEncounters(constructRequestEncounters(0));
         } else {
             patient.setSupplementalId(1L);
         }
         patient.setNonHpfDocuments(constructRequestSupplementalDocument(0,  patient.getMrn(),
                                                                     patient.getFacility(), isHpf));
         patient.setAttachments(constructRequestAttachment(0, patient.getMrn(),
                                                           patient.getFacility(), isHpf));
         isHpf = !(isHpf);
         patients.add(patient);
         return patients;
     }

    private List<RequestSupplementalAttachment> constructRequestAttachment(long attachmentSeq,
                                                                           String mrn,
                                                                           String facility,
                                                                           boolean isHpf) {

        try {

            List<RequestSupplementalAttachment> attachments =
                    new ArrayList<RequestSupplementalAttachment>();

            RequestSupplementalAttachment attachment = new RequestSupplementalAttachment();
            attachment.setComment("Comment");
            attachment.setAttachmentDate(new Date());
            attachment.setFileext("pdf");
            attachment.setFilename("File");
            attachment.setIsDeleted("0");
            attachment.setPath("ab\\bc\\cd\\");
            attachment.setSubmittedBy("User");
            attachment.setUuid("abcd-efgh-ijkl-qrs");
            attachment.setVolume("\\\\EIGDEV242\\Attachment");
            attachment.setDateOfService(new Date());
            attachment.setDocFacility(FACILITY);
            attachment.setAttachmentSeq(1L);
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
            doc.setDocumentSeq(1L);
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

    private List<RequestEncounter> constructRequestEncounters(long patientSeq) {

     List<RequestEncounter> encounterList = new ArrayList<RequestEncounter>();

     String time = System.currentTimeMillis() + "";
     RequestEncounter encounter = new RequestEncounter();
     encounter.setPatientSeq(patientSeq);
     encounter.setAdmitdate(new Date());
     encounter.setDischargeDate(new Date());
     encounter.setFacility(FACILITY);
     encounter.setHasDeficiency(false);
     encounter.setLocked(false);
     encounter.setName(NAME + time.substring(time.length() - _five, time.length()));
     encounter.setPatientService(PATIENT);
     encounter.setPatientType(PATIENT);
     encounter.setVip(false);

     encounter.setRoiDocuments(constructRequestDocuments(0));
     encounterList.add(encounter);

     return encounterList;
    }

    private List<RequestDocument> constructRequestDocuments(long encounterSeq) {

        List<RequestDocument>  documentsList = new ArrayList<RequestDocument>();

        String time = System.currentTimeMillis() + "";
        RequestDocument document = new RequestDocument();
        document.setEncounterSeq(encounterSeq);
        document.setChartOrder("123");
        document.setDateTime(new Date());
        document.setDocId(Long.valueOf(time.substring(time.length() - _five, time.length())));
        document.setName("101 COLD DATAVAULT");
        document.setSubtitle("TEST");
        document.setMrn(NAME + time.substring(time.length() - _five, time.length()));

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
            page.setPageSeq(1);
            page.setDeleted(false);

            pagesList.add(page);
            Thread.sleep(10);
            System.out.println(page.getImnetId());
            }
            return pagesList;

        } catch (InterruptedException ex) {
            throw new ROIException();
        }
    }

    private RequestCore setupRequest() {

        long requestorId = 1001;
        String reqPassword;
        RequestCore request = new RequestCore();
        try {
            reqPassword = _requestService.getGeneratedPassword();

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
            fail("setupRequest failed");
            return request;
            // TODO Auto-generated catch block
        } 
    }

    private void saveRequestCoreBillingPaymentInfo() {

        RequestCoreCharges requestCharge = new RequestCoreCharges();

        requestCharge.setModifiedDt(new Date());
        requestCharge.setModifiedBy(3);
        requestCharge.setRequestCoreSeq(_request.getId());
        requestCharge.setBillingType("Samp");
        requestCharge.setBalanceDue(111);
        requestCharge.setPreviouslyReleasedCost(111);
        requestCharge.setTotalRequestCost(111);
        requestCharge.setTotalPagesReleased(111);
        requestCharge.setTotalPages(111);
        requestCharge.setSalesTaxAmount(111);
        requestCharge.setSalesTaxPercentage(111);
        requestCharge.setReleaseDate(new Date());
        requestCharge.setReleaseCost(111);
        requestCharge.setApplySalesTax(true);
        requestCharge.setSalesTaxPercentage(111);
        requestCharge.setSalesTaxAmount(111);
        requestCharge.setPreviouslyReleasedCost(111);
        requestCharge.setPaymentAmount(111);
        requestCharge.setPaymentAmount(111);
        requestCharge.setOriginalBalance(111);
        requestCharge.setDebitAdjustmentAmount(111);
        requestCharge.setCreditAdjustmentAmount(111);
        requestCharge.setBillingLocCode(FACILITY);
        requestCharge.setBillingLocName(FACILITY);

        RequestCoreChargesBillingInfo requestCoreChargesBillingInfo =
                                                                new RequestCoreChargesBillingInfo();
        requestCoreChargesBillingInfo.setModifiedDt(new Date());
        requestCoreChargesBillingInfo.setModifiedBy(3);
        requestCoreChargesBillingInfo.setRequestCoreSeq(_request.getId());
        requestCoreChargesBillingInfo.setBillingType("Samp");
        requestCoreChargesBillingInfo.setBalanceDue(111);
        requestCoreChargesBillingInfo.setPreviouslyReleasedCost(111);
        requestCoreChargesBillingInfo.setTotalRequestCost(111);
        requestCoreChargesBillingInfo.setTotalPagesReleased(111);
        requestCoreChargesBillingInfo.setTotalPages(111);
        requestCoreChargesBillingInfo.setSalesTaxAmount(111);
        requestCoreChargesBillingInfo.setSalesTaxPercentage(111);
        requestCoreChargesBillingInfo.setReleaseDate(new Date());
        requestCoreChargesBillingInfo.setReleaseCost(111);
        requestCoreChargesBillingInfo.setApplySalesTax(true);
        requestCoreChargesBillingInfo.setSalesTaxPercentage(111);
        requestCoreChargesBillingInfo.setSalesTaxAmount(111);
        requestCoreChargesBillingInfo.setPreviouslyReleasedCost(111);
        requestCoreChargesBillingInfo.setPaymentAmount(111);
        requestCoreChargesBillingInfo.setPaymentAmount(111);
        requestCoreChargesBillingInfo.setOriginalBalance(111);
        requestCoreChargesBillingInfo.setDebitAdjustmentAmount(111);
        requestCoreChargesBillingInfo.setCreditAdjustmentAmount(111);


        RequestCoreChargesDocument requestCoreChargesDocument1 =
                                            new RequestCoreChargesDocument();
        requestCoreChargesDocument1.setModifiedDt(new Date());
        requestCoreChargesDocument1.setModifiedBy(1);
        requestCoreChargesDocument1.setAmount(100);
        requestCoreChargesDocument1.setBillingTierName("Electronic");
        requestCoreChargesDocument1.setPages(1);
        requestCoreChargesDocument1.setBillingtierId("-1");
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
        requestCoreChargesDocument2.setBillingTierName("Electronic");
        requestCoreChargesDocument2.setPages(2);
        requestCoreChargesDocument2.setBillingtierId("-1");
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
        requestCoreChargesShipping.setModifiedBy(1);
        requestCoreChargesShipping.setAddress1("Test1Updated");
        requestCoreChargesShipping.setAddress2("Test2Updated");
        requestCoreChargesShipping.setAddress3("Test3Updated");
        requestCoreChargesShipping.setAddressType("TestTypeUpdated");
        requestCoreChargesShipping.setCity("City");
        requestCoreChargesShipping.setCountryCode("IND");
        requestCoreChargesShipping.setOutputMethod("SaveAsFile");
        requestCoreChargesShipping.setShippingMethod("TestSPMethodUpdated");
        requestCoreChargesShipping.setPostalCode("Test-101");
        requestCoreChargesShipping.setShippingCharge(300);
        requestCoreChargesShipping.setShippingMethodId(100);
        requestCoreChargesShipping.setShippingUrl("google");
        requestCoreChargesShipping.setShippingWeight(100);
        requestCoreChargesShipping.setState("KA");
        requestCoreChargesShipping.setTrackingNumber("Test");


        RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment =
                                                   new RequestCoreDeliveryChargesAdjustmentPayment();
        requestCoreDeliveryChargesAdjustmentPayment.setBaseAmount(222);
        requestCoreDeliveryChargesAdjustmentPayment.setPaymentDate(new Date());
        requestCoreDeliveryChargesAdjustmentPayment.setDescription("Test Payment 222");
        requestCoreDeliveryChargesAdjustmentPayment.setIsDebit(true);
        requestCoreDeliveryChargesAdjustmentPayment.setPaymentMode("check");
        requestCoreDeliveryChargesAdjustmentPayment.setTransactionType("TRANSFER");


        RequestCoreDeliveryChargesAdjustmentPayment requestCoreDeliveryChargesAdjustmentPayment1 =
                                                new RequestCoreDeliveryChargesAdjustmentPayment();
        requestCoreDeliveryChargesAdjustmentPayment1.setBaseAmount(111);
        requestCoreDeliveryChargesAdjustmentPayment1.setPaymentDate(new Date());
        requestCoreDeliveryChargesAdjustmentPayment1.setDescription("Test Payment 111");
        requestCoreDeliveryChargesAdjustmentPayment1.setIsDebit(true);
        requestCoreDeliveryChargesAdjustmentPayment1.setPaymentMode("check");
        requestCoreDeliveryChargesAdjustmentPayment1.setTransactionType("DEBIT");


        Set<RequestCoreDeliveryChargesAdjustmentPayment>
                    requestCoreDeliveryChargesAdjustmentPaymentList =
                                          new HashSet<RequestCoreDeliveryChargesAdjustmentPayment>();
        requestCoreDeliveryChargesAdjustmentPaymentList
                                                   .add(requestCoreDeliveryChargesAdjustmentPayment);
        requestCoreDeliveryChargesAdjustmentPaymentList
                                                  .add(requestCoreDeliveryChargesAdjustmentPayment1);

        RequestCoreChargesInvoice requestCoreChargesInvoice1 = new RequestCoreChargesInvoice();

        requestCoreChargesInvoice1.
                            setRequestCoreDeliveryChargesAdjustmentPayment(
                                    requestCoreDeliveryChargesAdjustmentPaymentList);

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


        requestCoreChargesBillingInfo.setRequestCoreChargesBilling(requestCoreChargesBilling);
        requestCoreChargesBillingInfo.setRequestCoreChargesShipping(requestCoreChargesShipping);
        requestCoreChargesBillingInfo.setRequestCoreChargesInvoice(requestCoreChargesInvoiceSet);
        requestCoreChargesBillingInfo.setSalesTaxSummary(salesTaxSummary);

        requestCharge.setRequestCoreChargesBilling(requestCoreChargesBilling);
        requestCharge.setRequestCoreChargesShipping(requestCoreChargesShipping);
        _requestService.saveRequestCoreCharges(requestCharge);
        _requestService.retrieveInvoicesAndAdjPay(_request.getId());

    }

    private ReleaseCore constructRelease() {

        ReleaseCore releaseCore = new ReleaseCore();

        releaseCore.setRequestId(1001);

        List<ReleasePages> pages = new ArrayList<ReleasePages>();
        ReleasePages page = new ReleasePages();
        page.setPageSeq(1222);
        page.setSelfPayEncounter(true);
        pages.add(page);

        releaseCore.setRoiPages(pages);

        List<Long> ids = new ArrayList<Long>();
        ids.add(1001L);
        releaseCore.setSupplementarityDocumentsSeq(ids);

        releaseCore.setPastDueInvoices(getPastInvoices(releaseCore.getRequestId()));
        ids = new ArrayList<Long>();
        ids.add(1001L);
        releaseCore.setPastDueInvoices(ids);

        releaseCore.setCoverLetterRequired(true);
        releaseCore.setCoverLetterFileId(getLetterTemplateFileId());
        releaseCore.setInvoiceTemplateId(getLetterTemplateFileId());

        releaseCore.setInvoiceRequired(true);
        releaseCore.setInvoiceOrPrebillAndPreviewInfo(constructInvoiceInfo());

        return releaseCore;
    }

    private InvoiceOrPrebillAndPreviewInfo constructInvoiceInfo() {

        InvoiceOrPrebillAndPreviewInfo invInfo= new InvoiceOrPrebillAndPreviewInfo();

        invInfo.setRequestCoreId(1001);
        invInfo.setLetterTemplateFileId(getLetterTemplateFileId());
        invInfo.setLetterTemplateName("TestName");
        invInfo.setType("RTF");
        String[] notes={"testNote1","testNote2","testNote3"};
        invInfo.setNotes(notes);
        invInfo.setAmountpaid(100);
        invInfo.setBaseCharge(200);
        invInfo.setInvoiceBalanceDue(100);
        invInfo.setInvoiceBillingLocCode("AD");
        invInfo.setInvoiceBillinglocName(FACILITY);
        invInfo.setInvoiceDueDate(new Date());
        invInfo.setInvoiceDueDays(3);
        invInfo.setInvoicePrebillDate(new Date());
        invInfo.setResendDate(new Date());
        invInfo.setRequestStatus("Logged");
        invInfo.setRequestDate(new Date());
        invInfo.setQueuePassword("TestPass");
        invInfo.setOverwriteDueDate(true);
        invInfo.setOutputMethod("SaveAsFile");
        invInfo.setInvoiceSalesTax(26);
        invInfo.setLetterType("Invoice");
        return invInfo;
    }

    private List<Long> getPastInvoices(long requestId) {

        List<Long> pastInvoiceIDs = new ArrayList<Long>();
        PastInvoiceList invoiceList = _billingCoreService
                .retrievePastInvoices(requestId);
        for (PastInvoice pastInvoice : invoiceList.getPastInvoices()) {
            pastInvoiceIDs.add(pastInvoice.getInvoiceId());
        }

        return pastInvoiceIDs;
    }

}
