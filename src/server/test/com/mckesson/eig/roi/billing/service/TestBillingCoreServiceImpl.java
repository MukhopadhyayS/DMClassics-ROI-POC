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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.mckesson.eig.roi.admin.service.ROIAdminService;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterOutputProperties;
import com.mckesson.eig.roi.billing.model.InvoiceOrPrebillAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.LetterInfo;
import com.mckesson.eig.roi.billing.model.PastInvoiceList;
import com.mckesson.eig.roi.billing.model.RegeneratedInvoiceDetails;
import com.mckesson.eig.roi.billing.model.ReleaseAndPreviewInfo;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryList;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 *
 * @author OFS
 * @date   Sep 28, 2009
 * @since  HPF 13.1 [ROI]; Nov 3, 2008
 */

public class TestBillingCoreServiceImpl
extends BaseROITestCase {

    private static BillingCoreService _billingCoreService;
    protected static final String  BILLING_CORE_SERVICE =
            "com.mckesson.eig.roi.billing.service.BillingCoreServiceImpl";
    protected static final String ADMIN_SERVICE =
            "com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl";

    private static ROIAdminService _adminService;

    private static long REQUEST_CORE_DELIVERY_CHARGES_ID = 1001;
    private static long RELEASE_ID = 1001;
    private static long LETTER_ID = 1001;

    @Override
    public void initializeTestData() throws Exception {

        _billingCoreService = (BillingCoreService) getService(BILLING_CORE_SERVICE);
        _adminService = (ROIAdminService) getService(ADMIN_SERVICE);
        refreshTestData("test/resources/reports/reportsDataSet.xml");
    }

    @Test
	public void testCreateInvoiceOrPrebillAndPreview_NULL_Info() {

		try {
			_billingCoreService.createInvoiceOrPrebillAndPreview(null);
			fail("Create Invoice or Prebill with null value not permitted,but created");
		} catch (ROIException e) {
			assertEquals(ROIClientErrorCodes.INVOICES_AND_LETTER_INFO_IS_NULL.toString(),
			             e.getErrorCode());
		}
	}

    @Test
    public void testCreateInvoiceOrPrebillAndPreview() {

        InvoiceOrPrebillAndPreviewInfo invInfo = constructInvoiceInfo();

        try {

            DocInfo docInfo =  _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);
            assertNotNull(docInfo);
        } catch (Exception e) {
            fail("Create Invoice or preBill throws Exception :" + e.getMessage());
        }
    }

    @Test
    public void testCreatePrebillAndPreview() {

        InvoiceOrPrebillAndPreviewInfo invInfo = constructInvoiceInfo();
        invInfo.setLetterType("Prebill");

        try {

            DocInfo docInfo =  _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);

            invInfo.setLetterType("Invoice");
            docInfo =  _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);

            assertNotNull(docInfo);
        } catch (Exception e) {
            fail("Create Invoice or preBill throws Exception :" + e.getMessage());
        }
    }


    @Test
    public void testViewInvoiceWithInvalidType() {

        try {

            _billingCoreService.viewInvoice(-1, null);
            fail("View Invoice with invalid type will throws exception.");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED.toString(),
                                                                                 e.getErrorCode());
        }
    }

    @Test
    public void testAutoApplyToInvoiceWithInvalidInvoiceId() {

        try {

            _billingCoreService.autoApplyToInvoice(1001,0);
            fail("Auto apply to Invoice with invalid invoice Id should throws exception");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.INVOICE_ID_IS_BLANK.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testRetrieveInvoiceSummariesWithInvalidReqId() {

        try {
            _billingCoreService.retrieveInvoiceSummaries(0);
            fail("Retrieve Invoice Summaries with null value not permitted,but created");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.INVALID_REQUEST_ID.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testRetrieveInvoiceSummaries() {

        try {
            _billingCoreService.retrieveInvoiceSummaries(1001);
        } catch (Exception e) {
            fail("Retrieve Invoice Summaries throws Exception :" + e.getMessage());
        }
    }

    @Test
    public void testViewInvoice() {

        try {
            _billingCoreService.viewInvoice(1001, ROIConstants.TEMPLATE_FILE_TYPE);
        } catch (Exception e) {
            fail("View Invoice should not throws Exception :" + e.getMessage());
        }
    }

    @Test
    public void testCreateReleaseAndPreviewInfo_NULL_RELEASE() {

        try {
            _billingCoreService.createReleaseAndPreviewInfo(null,true,1.0);
            fail("Create Release with invalid value is not permitted, but created");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.INVALID_RELEASE.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testCreateReleaseAndPreviewInfo() {

        ReleaseCore releaseCore = constructRelease();

        try {

            ReleaseAndPreviewInfo previewInfo = _billingCoreService.
                                                           createReleaseAndPreviewInfo(releaseCore,true,10.0);

            RELEASE_ID = previewInfo.getReleaseId();
            _billingCoreService.autoApplyToInvoice(releaseCore.getRequestId(), previewInfo.getInvoiceId());

            InvoiceAndLetterOutputProperties outputProperties = new InvoiceAndLetterOutputProperties();
            outputProperties.setForInvoice(true);
            outputProperties.setForRelease(true);
            outputProperties.setReleaseId(previewInfo.getReleaseId());
            outputProperties.setInvoiceId(previewInfo.getInvoiceId());
            outputProperties.setOutputMethod("SaveAsFile");

            _billingCoreService.updateInvoiceOutputProperties(outputProperties);

            assertNotNull(previewInfo);
            assertTrue(previewInfo.getReleaseId() > 0);
        } catch (Exception e) {
            fail("Create Relese and Preview throws Exception :" + e.getMessage());
        }
    }

    @Test
    public void testCancelRelease_INVALID_RELEASE() {

        try {
            _billingCoreService.cancelRelease(-1, -1);
            fail();
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.INVALID_RELEASE.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testCancelRelease() {

        try {

            ReleaseCore releaseCore = constructRelease();
            ReleaseAndPreviewInfo previewInfo =
                    _billingCoreService.createReleaseAndPreviewInfo(releaseCore, true, 10.0);

            _billingCoreService.autoApplyToInvoice(releaseCore.getRequestId(), previewInfo.getInvoiceId());

            InvoiceAndLetterOutputProperties outputProperties = new InvoiceAndLetterOutputProperties();
            outputProperties.setForInvoice(true);
            outputProperties.setForRelease(true);
            outputProperties.setReleaseId(previewInfo.getReleaseId());
            outputProperties.setInvoiceId(previewInfo.getInvoiceId());
            outputProperties.setOutputMethod("SaveAsFile");

            _billingCoreService.updateInvoiceOutputProperties(outputProperties);


            _billingCoreService.cancelRelease(previewInfo.getReleaseId(),previewInfo.getInvoiceId());

        } catch (ROIException e) {
            fail("Cancel Release Operation throws Exception :" + e.getMessage());
        }
    }

    @Test
    public void testRetrieveReleaseHistoryListWithInvalidReqId() {

        try {

            _billingCoreService.retrieveReleaseHistoryList(0);
            fail("Retrieve Release HIstory With Invalid Request Id is not permitted,but retrieved");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.INVALID_REQUEST_ID.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testRetrieveReleaseHistoryList() {

        try {

            ReleaseHistoryList retval = _billingCoreService.retrieveReleaseHistoryList(1001);
            assertNotNull("ReleaseHistoryList object is not null", retval);

            int historySize = retval.getHistory().size();
            assertTrue("History list should not be empty, size : " +
                                                                    historySize, historySize >= 0);

            int patientSize = retval.getReleasedPatients().size();
            assertTrue("Patients list  should not be emplty, size : " +
                                                                    patientSize, patientSize >= 0);

        } catch (ROIException e) {
            fail("Retrieve Release History Operation throws Exception :" + e.getMessage());
        }

    }

    @Test
    public void testCreateLetterWithInvalidTemplate() {

        try {

            String[] notes={"testNote1","testNote2","testNote3"};
            long letterTemplateId = 0;
            long requestCoreId = 1001;

            LetterInfo letterInfo = new LetterInfo();
            letterInfo.setLetterTemplateId(letterTemplateId);
            letterInfo.setRequestId(requestCoreId);
            letterInfo.setNotes(notes);
            letterInfo.setType(ROIConstants.TEMPLATE_FILE_TYPE);

            _billingCoreService.createLetterAndPreview(letterInfo);
            fail("Create Letter With Invalid letter Info is not permitted,but retrieved");
        } catch (ROIException e) {
            assertEquals(ROIClientErrorCodes.LETTER_TEMPLATE_ID_EQUAL_TO_ZERO.toString(), e.getErrorCode());
        }
    }

    @Test
    public void testCreateLetter() {

        String[] notes={"testNote1","testNote2","testNote3"};
        long requestCoreId = 1001;

        LetterInfo letterInfo = new LetterInfo();
        letterInfo.setLetterTemplateId(getLetterTemplateFileId());
        letterInfo.setRequestId(requestCoreId);
        letterInfo.setNotes(notes);
        letterInfo.setType(ROIConstants.TEMPLATE_FILE_TYPE);

        try {

            DocInfo docInfo =  _billingCoreService.createLetterAndPreview(letterInfo);
            assertNotNull(docInfo);
            LETTER_ID = docInfo.getId();

        } catch (Exception ex) {
            fail("Create Letter should not throw exception");
        }
    }

    @Test
    public void testCancelCoverLetter() {

        try {
             _billingCoreService.cancelCoverLetter(1001,LETTER_ID);
        } catch (Exception ex) {
            fail("Cancel cover Letter throw exception : " + ex.getMessage());
        }
    }

    @Test
    public void testCancelCoverLetterWithInvalidCoverLetterId() {

        try {
            _billingCoreService.cancelCoverLetter(1001,0);
            fail("Cancel cover Letter With Invalid Coverletter Id not permitted, but created");
        } catch (ROIException ex) {
            assertEquals(ROIClientErrorCodes.INVALID_COVER_LETTER_ID.toString(), ex.getErrorCode());
         }
    }

    @Test
    public void testRetrievePastInvoice() {

        try {
            PastInvoiceList retrievePastInvoices =
                                        _billingCoreService.retrievePastInvoices(1001);
            assertTrue(retrievePastInvoices.getPastInvoices().size() > 0);

        } catch (Exception ex) {
            fail("retrieve past invoice throw exception :" + ex.getMessage());
        }
    }

    @Test
    public void testRetrievePastInvoiceWithInvalidRequestId() {

        try {
             _billingCoreService.retrievePastInvoices(0);
            fail("retrieve past invoice with invalid requestId should throw exception");

        } catch (ROIException ex) {
            assertError(ex, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    @Test
    public void testCancelInvoiceOrPrebillAndPreview() {

        try {

            InvoiceOrPrebillAndPreviewInfo invInfo = constructInvoiceInfo();
            DocInfo docInfo =  _billingCoreService.createInvoiceOrPrebillAndPreview(invInfo);
            _billingCoreService.cancelInvoiceOrPrebillAndPreview(docInfo.getId());
        } catch (Exception ex) {
            fail("Cancel Invoice or Prebill throw exception : " + ex.getMessage());
        }
    }

    @Test
    public void testSetDisplayBillingInfo() {

        try {
            _billingCoreService.setDisplayBillingInfo(1001, false);
            _billingCoreService.setDisplayBillingInfo(1001, true);
        } catch (Exception ex) {
            fail(" Set Display Billing Info should not throw exception : " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateInvoiceOutputPropertiesWithInvalidInvId() {

        try {
            InvoiceAndLetterOutputProperties outputProperties =
                                                            new InvoiceAndLetterOutputProperties();
            outputProperties.setForInvoice(true);
            outputProperties.setInvoiceId(0);
            _billingCoreService.updateInvoiceOutputProperties(outputProperties);
            fail(" Update Output Properties with Invalid output" +
            		                                        " properties not permited,but created");
        } catch (ROIException ex) {
            assertEquals(ROIClientErrorCodes.INVALID_INVOICE_ID.toString(), ex.getErrorCode());
        }
    }

    @Test
    public void testUpdateInvoiceOutputPropertiesWithInvalidRequestId() {

        try {
            InvoiceAndLetterOutputProperties outputProperties =
                                                            new InvoiceAndLetterOutputProperties();
            outputProperties.setForLetter(true);
            outputProperties.setLetterId(0);
            _billingCoreService.updateInvoiceOutputProperties(outputProperties);
            fail(" Update Output Properties with Invalid output" +
                                                            " properties not permited,but created");
        } catch (ROIException ex) {
            assertEquals(ROIClientErrorCodes.INVALID_LETTER_ID.toString(), ex.getErrorCode());
        }
    }

    @Test
    public void testUpdateInvoiceProperties() {

        try {
            InvoiceAndLetterOutputProperties outputProperties =
                                                            new InvoiceAndLetterOutputProperties();
            _billingCoreService.updateInvoiceOutputProperties(outputProperties);
        } catch (Exception ex) {
            fail(" Set Display Billing Info throw exception : " + ex.getMessage());
        }
    }

    @Test
    public void testRetrieveChargeHistoryWithInvalidReqId() {

        try {
            _billingCoreService.retrieveChargeHistory(Long.MIN_VALUE);
            fail(" Retrieve Charge History With Invalid RequestId not permitted,but created");
        } catch (ROIException ex) {
            assertEquals(ROIClientErrorCodes.INVALID_REQUEST_ID.toString(), ex.getErrorCode());
        }
    }

    @Test
    public void testRetrieveChargeHistory() {

        try {
            InvoiceAndLetterOutputProperties outputProperties =
                    new InvoiceAndLetterOutputProperties();
            _billingCoreService.updateInvoiceOutputProperties(outputProperties);
        } catch (Exception ex) {
            fail(" Set Display Billing Info throw exception : " + ex.getMessage());
        }
    }

    private InvoiceOrPrebillAndPreviewInfo constructInvoiceInfo() {

        InvoiceOrPrebillAndPreviewInfo invInfo= new InvoiceOrPrebillAndPreviewInfo();

        invInfo.setRequestCoreId(1001);
        invInfo.setLetterTemplateFileId(getLetterTemplateFileId());
        invInfo.setLetterTemplateName("TestName");
        invInfo.setType(ROIConstants.TEMPLATE_FILE_TYPE);
        String[] notes={"testNote1","testNote2","testNote3"};
        invInfo.setNotes(notes);
        invInfo.setAmountpaid(100);
        invInfo.setBaseCharge(200);
        invInfo.setInvoiceBalanceDue(100);
        invInfo.setInvoiceBillingLocCode("AD");
        invInfo.setInvoiceBillinglocName("AD");
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

    /**
     * This Method constructs Relese details
     * @return
     */
    private ReleaseCore constructRelease() {

        ReleaseCore releaseCore = new ReleaseCore();

		releaseCore.setRequestId(1001);

		List<ReleasePages> pages = new ArrayList<ReleasePages>();

		ReleasePages page = new ReleasePages();
		page.setPageSeq(1100);
		page.setSelfPayEncounter(true);
		pages.add(page);

		page = new ReleasePages();
        page.setPageSeq(1111);
        page.setSelfPayEncounter(true);
        pages.add(page);
		releaseCore.setRoiPages(pages);

		List<Long> ids = new ArrayList<Long>();
		ids.add(new Long(1001));
		releaseCore.setSupplementarityDocumentsSeq(ids);

		ids = new ArrayList<Long>();
		ids.add(new Long(1001));
		releaseCore.setPastDueInvoices(ids);

		releaseCore.setCoverLetterRequired(true);
		releaseCore.setCoverLetterFileId(getLetterTemplateFileId());
		releaseCore.setInvoiceTemplateId(getLetterTemplateFileId());
		releaseCore.setInvoiceOrPrebillAndPreviewInfo(constructInvoiceInfo());
		releaseCore.setInvoiceRequired(true);

		RequestorStatementCriteria stmtCriteria = new RequestorStatementCriteria();
		stmtCriteria.setDateRange(DateRange.DAYS_30);
		stmtCriteria.setRequestorId(1001);
		stmtCriteria.setTemplateFileId(getLetterTemplateFileId());
		releaseCore.setStatementCriteria(stmtCriteria);
        return releaseCore;
    }

	@Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }

    @Override
    protected void tearDown() throws Exception {
        //overridded to avoid stopping the open office instance
        return;
    }

    public void testRegeneratedInvoiceDetails() {

        RegeneratedInvoiceDetails regInv = new RegeneratedInvoiceDetails();

        regInv.setBalanceDue(1);
        regInv.setBaseCharge(1);
        regInv.setRequestCoreDeliveryChargesId(1);
        regInv.setResendDt(new Date());
        regInv.setRequestCreditAmount(1);
        regInv.setRequestDebitAmount(1);
        regInv.setRequestPaymentAmount(1);
        regInv.setRequestBalanceDue(1);
        regInv.setRequestStatus("Test");
        regInv.setLetterTemplateName("Test");
        regInv.setLetterTemplateFileId(1);
        regInv.setQueuePassword("Test");
        regInv.setOutputMethod("Test");
        regInv.setAmountPaid(1);
        regInv.setNotes("Test");
        regInv.setSalesTax(1.00);
        regInv.setBillingLocation("Test");
        regInv.setInvoiceDueDate(new Date());
        regInv.setRequestId(1L);
        regInv.setInvoiceDate(new Date());
        regInv.setRequestDate(new Date());
        regInv.setPreviouslyReleasedCost(1.00);
        regInv.setReleaseCost(1.00);
        regInv.setReleaseDate(new Date());
        regInv.setTotalPagesReleased(1L);
        regInv.setTotalPages(1L);
        regInv.setTotalRequestCost(1.00);
        regInv.setOriginalBalance(1.00);

        assertEquals(1.0, regInv.getBalanceDue());
        assertEquals(1.0, regInv.getBaseCharge());
        assertEquals(1, regInv.getRequestCoreDeliveryChargesId());
        assertEquals(1.0, regInv.getRequestCreditAmount());
        assertEquals(1.0, regInv.getRequestDebitAmount());
        assertEquals(1.0, regInv.getRequestPaymentAmount());
        assertEquals(1.0, regInv.getRequestBalanceDue());
        assertEquals("Test", regInv.getRequestStatus());
        assertEquals("Test", regInv.getLetterTemplateName());
        assertEquals(1, regInv.getLetterTemplateFileId());
        assertEquals("Test", regInv.getQueuePassword());
        assertEquals("Test", regInv.getOutputMethod());
        assertEquals(1.0, regInv.getAmountPaid());
        assertEquals("Test", regInv.getNotes());
        assertEquals(1.00, regInv.getSalesTax());
        assertEquals("Test", regInv.getBillingLocation());
        assertEquals(1.00, regInv.getPreviouslyReleasedCost());
        assertEquals(1.00, regInv.getReleaseCost());
        assertEquals(1.00, regInv.getTotalRequestCost());
        assertEquals(1.00, regInv.getOriginalBalance());

    }

}
