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
import java.util.List;

import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.MatchCriteria;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.request.model.AuditAndEventList;
import com.mckesson.eig.roi.request.model.Comment;
import com.mckesson.eig.roi.request.model.Comments;
import com.mckesson.eig.roi.request.model.EventTypes;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvents;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorSearchResult;
import com.mckesson.eig.roi.requestor.service.RequestorService;
import com.mckesson.eig.roi.test.BaseROITestCase;

/**
 * @author OFS
 * @date   Aug 05, 2008
 * @since  HPF 13.1 [ROI]; Jul 17, 2008
 */
public class TestRequestEventService
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

    private static long _requestId;

    @Override
    public void initializeTestData()
    throws Exception {
       _requestService = (RequestCoreService) getService(REQUEST_SERVICE);
       _requestorService = (RequestorService) getService(REQUESTOR_SERVICE);
       _requestId = 1001;
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        throw new UnsupportedOperationException("TestRequestEventService.getServiceURL()");
    }

    public void testAddComment() {

        Comment comment = new Comment();
        comment.setName(RequestEvent.TYPE.COMMENT_ADDED.toString());
        comment.setDescription("Testing Comment For Request");
        comment.setRequestId(_requestId);

        _requestService.addEvent(comment);
        assertNotNull(comment);
    }

    public void testAddCommentDescFaliure() {

        try {

            Comment comment = new Comment();
            comment.setName(RequestEvent.TYPE.COMMENT_ADDED.toString());
            comment.setDescription("");
            comment.setRequestId(_requestId);

            _requestService.addEvent(comment);
            fail("Should have thrown exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.EMPTY_REQUEST_EVENT_DESCRIPTION);
        }

    }

    public void testAddCommentIdFaliure() {

        try {

            Comment comment = new Comment();
            comment.setName(RequestEvent.TYPE.COMMENT_ADDED.toString());
            comment.setDescription("tests");
            comment.setRequestId(-1);

            _requestService.addEvent(comment);
            fail("Should have thrown exception");
        } catch (ROIException e) {
            assertError(e, ROIClientErrorCodes.INVALID_REQUEST_ID);
        }
    }

    public void testRetrieveComments() {

        try {

            Comments comments =  _requestService.retrieveComments(_requestId);
            assertNotNull(comments);
        } catch (Exception e) {
            e.printStackTrace();
            fail("RetriveComments should not throw exception");
        }
    }

    public void testRetrieveRequestEvents() {

        try {

            RequestEvents events =  _requestService.getEventHistory(_requestId);
            assertNotNull(events);
        } catch (Exception e) {
            e.printStackTrace();
            fail("RetriveComments should not throw exception");
        }
    }

    public void testRetrieveAllEventTypes() {

        try {

            EventTypes eventTypes = new EventTypes();
            eventTypes = _requestService.retrieveAllEventTypes();
            assertNotNull(eventTypes.getEventTypes());
            assertNotNull(eventTypes);
        } catch (Exception e) {
            fail("RetriveComments should not throw exception");
        }
    }

    private RequestCore requestCreation() {

        long requestorId = 10;
        List<MatchCriteria> list = new ArrayList<MatchCriteria>();

        MatchCriteria p1 = new MatchCriteria();
        p1.setLastName("");
        p1.setFirstName("");
        p1.setSsn("");
        list.add(p1);

        MatchCriteriaList mcl = new MatchCriteriaList();
        mcl.setMatchCriteria(list);

        String reqPassword = null;
        try {

            RequestorSearchResult res = _requestorService.searchMatchingRequestors(mcl);
            if (res.getSearchResults().size() > 0) {
                requestorId = res.getSearchResults().get(0).getId();
            }
            reqPassword = _requestService.getGeneratedPassword();
        } catch (ROIException e) {
            fail("Matching requestor should not thrown an exception");
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    }

    public void testAddAuditEvent() {

        AuditEvent audit = new AuditEvent();
        audit.setActionCode("Test");
        audit.setComment("Test");
        audit.setEncounter("Test");
        audit.setEventId(1);
        audit.setFacility("AD");
        List<AuditEvent> audits = new ArrayList<AuditEvent>();
        audits.add(audit);

        RequestEvent event = new RequestEvent();
        event.setDescription("Test");
        event.setName(RequestEvent.TYPE.ADJUSTMENT_APPLIED.toString());
        event.setRequestId(1001);
        List<RequestEvent> events = new ArrayList<RequestEvent>();
        events.add(event);


        AuditAndEventList auditEventList = new AuditAndEventList();
        auditEventList.setAuditEvent(audits);
        auditEventList.setRequestEvent(events);

        _requestService.addAuditAndEvent(auditEventList);

    }

    public void testAddAuditEventWithInvalidList() {
        _requestService.addAuditAndEvent(null);
    }

    public void testAddAuditEventWithEmptyList() {

        try {

            RequestEvent event = new RequestEvent();
            event.setDescription("Test");
            event.setName(RequestEvent.TYPE.ADJUSTMENT_APPLIED.toString());
            event.setRequestId(0);
            List<RequestEvent> events = new ArrayList<RequestEvent>();
            events.add(event);

            AuditAndEventList auditEventList = new AuditAndEventList();
            auditEventList.setAuditEvent(new ArrayList<AuditEvent>());
            auditEventList.setRequestEvent(events);
            _requestService.addAuditAndEvent(auditEventList);

        } catch(ROIException ex) {
            assertError(ex, ROIClientErrorCodes.CREATE_AUDIT_AND_REQUEST_EVENT_FAILED);
        }
    }
}
