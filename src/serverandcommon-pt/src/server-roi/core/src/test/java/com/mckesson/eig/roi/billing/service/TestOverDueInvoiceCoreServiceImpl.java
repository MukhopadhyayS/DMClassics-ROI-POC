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

package com.mckesson.eig.roi.billing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.admin.service.ROIAdminService;
import com.mckesson.eig.roi.admin.service.ROIAdminServiceImpl;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;
import com.mckesson.eig.roi.billing.model.OverDueDocInfo;
import com.mckesson.eig.roi.billing.model.OverDueDocInfoList;
import com.mckesson.eig.roi.billing.model.OverDueRestriction;
import com.mckesson.eig.roi.billing.model.PastDueInvoice;
import com.mckesson.eig.roi.billing.model.RequestorInvoices;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria.DateRange;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
public class TestOverDueInvoiceCoreServiceImpl
extends BaseROITestCase {

    private static OverDueInvoiceCoreService _overDueInvoiceService;
    private static ROIAdminService _adminService;
    private static long _letterTemplateId;
    private static long _requestorId = 1;
    private static long _invoiceId = 1;

    public void initializeTestData() {

        _overDueInvoiceService = (OverDueInvoiceCoreService)
                                    getService(OverDueInvoiceCoreServiceImpl.class.getName());
        _adminService = (ROIAdminService)
                getService(ROIAdminServiceImpl.class.getName());

        refreshTestData("test/resources/reports/reportsDataSet.xml");
        _letterTemplateId = getLetterTemplateFileId();
        _requestorId = 1001;
        _invoiceId = 1001;
    }

    public void testSearchOverDueInvoiceCore() {

        try {

            SearchPastDueInvoiceCriteria criteria = getOverDueInvoiceCriteria();
            SearchPastDueInvoiceResult overDueInvoices = _overDueInvoiceService.searchOverDueInvoices(criteria);
            List<PastDueInvoice> pastDueInvoices = overDueInvoices.getPastDueInvoices();

            if (null != pastDueInvoices
                    && !pastDueInvoices.isEmpty()) {

                PastDueInvoice pastDueInvoice = pastDueInvoices.get(0);
                _requestorId = pastDueInvoice.getRequestorId();
                _invoiceId = pastDueInvoice.getInvoiceNumber();
            }


        } catch (Exception ex) {

            fail("Search OverDue invoice should not throw Exception");
        }
    }

    public void testSearchOverDueInvoiceCoreWithEmptyRequestorName() {

        try {

            SearchPastDueInvoiceCriteria criteria = getOverDueInvoiceCriteria();
            criteria.setRequestorName("");
            SearchPastDueInvoiceResult overDueInvoices = _overDueInvoiceService.searchOverDueInvoices(criteria);
            List<PastDueInvoice> pastDueInvoices = overDueInvoices.getPastDueInvoices();

            if (null != pastDueInvoices
                    && !pastDueInvoices.isEmpty()) {

                PastDueInvoice pastDueInvoice = pastDueInvoices.get(0);
                _requestorId = pastDueInvoice.getRequestorId();
                _invoiceId = pastDueInvoice.getInvoiceNumber();
            }


        } catch (Exception ex) {

            fail("Search OverDue invoice should not throw Exception");
        }
    }

    private SearchPastDueInvoiceCriteria getOverDueInvoiceCriteria() {

        SearchPastDueInvoiceCriteria criteria = new SearchPastDueInvoiceCriteria();
        criteria.setBillingLocations(new String[]{"AD", "TEST", "A"});
        criteria.setRequestorTypes(new Long[]{-1L, -2L});
        criteria.setOverDueRestriction(OverDueRestriction.GREATER);
        criteria.setOverDueFrom(0);
        return criteria;
    }

    public void testSearchOverDueInvoiceCoreWithNullCriteria() {

        try {

            _overDueInvoiceService.searchOverDueInvoices(null);
            fail("Search invoice with Invalid criteria is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_SEARCH_CRITERIA);
        }
    }

    public void testSearchOverDueInvoiceCoreWithInvalidCriteria() {

        try {


            SearchPastDueInvoiceCriteria criteria = getOverDueInvoiceCriteria();
            criteria.setBillingLocations(new String[]{null, "TEST", "A"});

            _overDueInvoiceService.searchOverDueInvoices(criteria);
            fail("Search invoice with Invalid overDueDays is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }
    }

    public void testSearchOverDueInvoiceCoreWithInvalidOverDueInvoice() {

        try {


            SearchPastDueInvoiceCriteria criteria = new SearchPastDueInvoiceCriteria();
            criteria.setBillingLocations(new String[]{"AD", "TEST", "A"});
            criteria.setRequestorTypes(new Long[]{-1L, -2L});
            criteria.setOverDueRestriction(OverDueRestriction.GREATER);
            criteria.setOverDueFrom(-1);

            _overDueInvoiceService.searchOverDueInvoices(criteria);
            fail("Search invoice with Invalid overDueDays is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OVERDUE_DAYS_IS_INVALID);
        }
    }

    public void testSearchOverDueInvoiceCoreWithInvalidOverDueRestriction() {

        try {


            SearchPastDueInvoiceCriteria criteria = new SearchPastDueInvoiceCriteria();
            criteria.setBillingLocations(new String[]{"AD", "TEST", "A"});
            criteria.setRequestorTypes(new Long[]{-1L, -2L});
            criteria.setOverDueRestriction(null);
            criteria.setOverDueFrom(1);

            _overDueInvoiceService.searchOverDueInvoices(criteria);
            fail("Search invoice with Invalid Restriction is not permitted ,but created ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.OVERDUE_RESTRICTION_IS_BLANK);
        }
    }

    public void testPreviewInvoice() {

        try {

            InvoiceAndLetterInfo info = new InvoiceAndLetterInfo();
            info.setInvoiceTemplateId(_letterTemplateId);
            info.setRequestorLetterTemplateId(_letterTemplateId);
            info.setOutputInvoice(true);

            List<RequestorInvoices> invoices = new ArrayList<RequestorInvoices>();
            RequestorInvoices inv = new RequestorInvoices();
            inv.setInvoiceIds(new long[]{_invoiceId});
            inv.setRequestorId(_requestorId);
            invoices.add(inv);

            info.setInvoices(invoices);

            _overDueInvoiceService.regenerateInvoiceCoreAndLetter(info, true);

        } catch (Exception cause) {
            fail("Preview overdue invoice should not throw exception");
        }
    }

    public void testRegenerateInvoice() {

        try {

            InvoiceAndLetterInfo info = new InvoiceAndLetterInfo();
            info.setInvoiceTemplateId(_letterTemplateId);
            info.setRequestorLetterTemplateId(_letterTemplateId);

            List<RequestorInvoices> invoices = new ArrayList<RequestorInvoices>();
            RequestorInvoices inv = new RequestorInvoices();
            inv.setInvoiceIds(new long[]{_invoiceId});
            inv.setRequestorId(_requestorId);

            invoices.add(inv);

            Map<String, String> prop = new HashMap<String, String>();
            prop.put(ROIConstants.OUTPUT_METHOD, "SaveAsFile");
            prop.put(ROIConstants.QUEUE_PD, "1234");

            info.setProperties(prop);
            info.setInvoices(invoices);

            RequestorStatementCriteria statementCriteria = new RequestorStatementCriteria();
            statementCriteria.setDateRange(DateRange.DAYS_30);
            statementCriteria.setTemplateFileId(_letterTemplateId);
            statementCriteria.setPastInvIds(Arrays.asList(new Long[]{ _invoiceId }));
            info.setStatementCriteria(statementCriteria);

            _overDueInvoiceService.regenerateInvoiceCoreAndLetter(info, true);

        } catch (Exception cause) {
            fail("Preview overdue invoice should not throw exception");
        }
    }

    public void testRegenerateInvoiceWithEmptyInvoiceId() {

        try {

            InvoiceAndLetterInfo info = new InvoiceAndLetterInfo();
            info.setInvoiceTemplateId(_letterTemplateId);
            info.setRequestorLetterTemplateId(_letterTemplateId);

            List<RequestorInvoices> invoices = new ArrayList<RequestorInvoices>();
            RequestorInvoices inv = new RequestorInvoices();
            inv.setInvoiceIds(new long[]{ 1001 });
            inv.setRequestorId(_requestorId);

            invoices.add(inv);

            Map<String, String> prop = new HashMap<String, String>();
            prop.put(ROIConstants.OUTPUT_METHOD, "SaveAsFile");
            prop.put(ROIConstants.QUEUE_PD, "1234");

            info.setProperties(prop);
            info.setInvoices(invoices);

            RequestorStatementCriteria statementCriteria = new RequestorStatementCriteria();
            statementCriteria.setDateRange(DateRange.DAYS_30);
            statementCriteria.setTemplateFileId(_letterTemplateId);
            info.setStatementCriteria(statementCriteria);

            OverDueDocInfoList docInfo =
                                _overDueInvoiceService.regenerateInvoiceCoreAndLetter(info, false);
            OverDueDocInfo overDueDocInfo = docInfo.getOverDueDocInfos().get(0);

            _overDueInvoiceService.regenerateLetter(overDueDocInfo.getId(),
                                                    ROIConstants.TEMPLATE_FILE_TYPE,
                                                    "Invoice");


        } catch (Exception cause) {
            fail("Preview overdue invoice should not throw exception");
        }
    }

    public void testPreviewInvoiceWithInvalidInvoiceId() {

        try {

            InvoiceAndLetterInfo info = new InvoiceAndLetterInfo();
            info.setInvoiceTemplateId(_letterTemplateId);
            info.setRequestorLetterTemplateId(_letterTemplateId);
            List<RequestorInvoices> invoices = new ArrayList<RequestorInvoices>();
            info.setInvoices(invoices);

            _overDueInvoiceService.regenerateInvoiceCoreAndLetter(info, true);

            fail("Preview invoice with Invalid Invoice Id is not permitted ,but Viewed ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.REQUESTOR_INVOICES_IS_BLANK);
        }
    }

    public void testPreviewInvoiceWithInvalidTemplateId() throws Exception {

        try {

            InvoiceAndLetterInfo info = new InvoiceAndLetterInfo();
            List<RequestorInvoices> invoices = new ArrayList<RequestorInvoices>();
            RequestorInvoices inv = new RequestorInvoices();
            inv.setInvoiceIds(new long[]{_invoiceId});
            inv.setRequestorId(_requestorId);
            invoices.add(inv);
            info.setInvoices(invoices);

            _overDueInvoiceService.regenerateInvoiceCoreAndLetter(info, true);
            super.tearDown();

            fail("Preview invoice with Invalid Template Id is not permitted ,but Viewed ");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.TEMPLATE_ID_IS_BLANK);
        }
    }


    /**
     * @see com.mckesson.eig.roi.test.BaseROITestCase#getServiceURL(java.lang.String)
     */
    @Override
    protected String getServiceURL(String serviceMethod) {
        // TODO Auto-generated method stub
        return null;
    }

    /** (non-Javadoc)
     * @see com.mckesson.eig.roi.test.BaseROITestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
    }

}
