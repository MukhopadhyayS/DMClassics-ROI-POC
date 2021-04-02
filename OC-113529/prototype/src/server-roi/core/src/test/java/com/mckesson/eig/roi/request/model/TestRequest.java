/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.TestCase;

import com.mckesson.eig.roi.admin.model.AdminLoV;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.model.SearchLoV;
import com.mckesson.eig.roi.billing.model.ReleaseCore;
import com.mckesson.eig.roi.billing.model.ReleasePages;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.requestor.model.RequestorDetail;
import com.mckesson.eig.utility.util.DateUtilities;


/**
 * @author OFS
 * @date   April 06, 2009
 * @since  HPF 13.1 [ROI]; Jul 8, 2008
 */
public class TestRequest
extends TestCase {

    private static long _patientRequestorTypeId = 1;
    private static final int CONTENT_COUNT = 20;
    private static Date _validDate;
    private static final String DOC_TYPE_NAME = "DocName";

    public void testSetUp()
    throws Exception {
        setUp();
        _validDate = DateUtilities.parseDate(new SimpleDateFormat(ROIConstants
                .ROI_DATE_FORMAT),
                "10/09/2008");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRequest() {

        RequestCore req = new RequestCore();
        req.setReceiptDate(_validDate);
        assertEquals(_validDate, req.getReceiptDate());
    }

    /**
     * This test case is to cover the RequestEvents model
     */
    public void testRequestEvents() {

        RequestEvents re = new RequestEvents();
        re.setCount(CONTENT_COUNT);
        re.setEvents(new ArrayList<RequestEvent>());

        assertEquals(CONTENT_COUNT, re.getCount());
        assertNotNull(re.getEvents());
    }

    /**
     * This test case is to cover the Comments model
     */
    public void testComments() {

        Comments com = new Comments();
        com.setCount(CONTENT_COUNT);
        com.setComments(new ArrayList<Comment>());

        assertEquals(CONTENT_COUNT, com.getCount());
        assertNotNull(com.getComments());
    }

    /**
     * This test case is to cover the RequestEvent model
     */
    public void testRequestEvent() {

        RequestEvent re = new RequestEvent();
        re.setType("CHANGE_OF_STATUS");
        assertEquals(TYPE.CHANGE_OF_STATUS.toString(), re.getName());
    }

    /**
     * This test case is to cover the RequestEventCriteria model
     */
    public void testRequestEventCriteria() {

        RequestEventCriteria rec = new RequestEventCriteria(CONTENT_COUNT, TYPE.CHANGE_OF_STATUS);
        rec.setRequestId(CONTENT_COUNT);
        assertEquals(CONTENT_COUNT, rec.getRequestId());
    }

    /**
     * This test case is to cover the RequestorDetail model
     */
    public void testRequestorDetail() {

        RequestorDetail rd = new RequestorDetail();
        rd.setRequestorId(_patientRequestorTypeId);
        assertEquals(_patientRequestorTypeId, rd.getRequestorId());
    }

    /**
     * Test case to cover the release model
     */
    public void testRelease() {

        ReleaseCore rel = new ReleaseCore();
        rel.setRequestId(0);
        rel.setCoverLetterFileId(1);
        rel.setInvoiceDueDays(1);
        rel.setInvoiceTemplateId(1);
        rel.setStatusChangeToComplete(true);
        rel.setCoverLetterRequired(true);
        rel.setInvoiceRequired(true);
        rel.setNotes(new ArrayList<String>());
        rel.setPastDueInvoices(new ArrayList<Long>());
        rel.setRoiPages(new ArrayList<ReleasePages>());
        rel.setSupplementalAttachmentsSeq(new ArrayList<Long>());
        rel.setSupplementalDocumentsSeq(new ArrayList<Long>());
        rel.setSupplementarityDocumentsSeq(new ArrayList<Long>());
        rel.setSupplementarityAttachmentsSeq(new ArrayList<Long>());

        assertEquals(0, rel.getRequestId());
        assertEquals(1, rel.getCoverLetterFileId());
        assertEquals(1, rel.getInvoiceDueDays());
        assertEquals(1, rel.getInvoiceTemplateId());
        assertTrue(rel.isCoverLetterRequired());
        assertTrue(rel.isInvoiceRequired());
        assertTrue(rel.isStatusChangeToComplete());
        assertNotNull(rel.getNotes());
        assertNotNull(rel.getPastDueInvoices());
        assertNotNull(rel.getRoiPages());
        assertNotNull(rel.getSupplementalAttachmentsSeq());
        assertNotNull(rel.getSupplementarityAttachmentsSeq());
        assertNotNull(rel.getSupplementalDocumentsSeq());
        assertNotNull(rel.getSupplementarityDocumentsSeq());

    }

    /**
     * Test case to cover the AdminLov model
     */
    public void testAdminLov() {

        AdminLoV lov = new AdminLoV();
        lov.setLovType("sample");
        assertEquals("sample", lov.getLovType());
    }

    /**
     * This test case to cover the User model
     */
    public void testUser() {

        User user = new User();
        user.setFacilities(new ArrayList<String>());
        user.setFreeFormFacilities(new ArrayList<String>());

        assertNotNull(user.getFacilities());
        assertNotNull(user.getFreeFormFacilities());
    }

    /**
     * This test case to cover the SearchLoV model
     */
    public void testSearchLoV() {

        SearchLoV lov = new SearchLoV();
        lov.setDataType(null);
        lov.setCondition(null);
    }

    /**
     * This test case to cover the BillingTemplate model
     */
    public void testBillingTemplate() {

        BillingTemplate bt = new BillingTemplate();
        bt.setFeeTypeIds(new ArrayList<Long>());
        assertNotNull(bt.getFeeTypeIds());
    }

    /**
     * This test to cover the letter template model
     */
    public void testLetterTemplate() {

        LetterTemplate lt = new LetterTemplate();
        lt.setUploadFile(DOC_TYPE_NAME);
        lt.setUploadFile(null);
    }
}
