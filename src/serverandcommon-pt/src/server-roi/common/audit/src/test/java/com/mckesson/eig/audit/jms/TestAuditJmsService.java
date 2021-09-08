/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.audit.jms;

import java.io.File;
import java.io.InputStream;

import junit.framework.TestCase;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.audit.jms.CXFSoapRequestBuilder.Parameter;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Testcase which tests Auditing via JMS transport without deploying it in any
 * container.
 */
public class TestAuditJmsService extends TestCase {

    private CXFSoapRequestBuilder _requestBuilder;
    private ServletUnitClient _client;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        UnitTestSpringInitialization.init();
        File webxml = new File("war/WEB-INF/web.xml");
        ServletRunner sr = new ServletRunner(webxml);
        _client = sr.newClient();
        _client.setExceptionsThrownOnErrorStatus(false);
        _requestBuilder = new CXFSoapRequestBuilder();
    }

    /**
     * Validates <code>createAuditEntry</code> method of the service.Builds a
     * SOAPRequest to be sent and tests the response whether the exact values
     * are retrieved from the service.
     */
    public void testCreateAuditEntry() {
        try {
            _requestBuilder.setOperationData("createAuditEntry");
            Parameter auditEvent = _requestBuilder.addParameter("auditEvent", "");
            auditEvent.addParameter("eventId", "2");
            auditEvent.addParameter("domainId", "2");
            auditEvent.addParameter("componentId", "2");
            auditEvent.addParameter("eventStart", "2007-04-17T16:52:23.977-04:00");
            auditEvent.addParameter("eventEnd", "2007-04-17T16:52:23.977-04:00");
            auditEvent.addParameter("objectId", "3");
            auditEvent.addParameter("revision1", "3");
            auditEvent.addParameter("revision2", "3");
            auditEvent.addParameter("revision3", "3");
            auditEvent.addParameter("comment", "comment");
            auditEvent.addParameter("eventStatus", "6");
            auditEvent.addParameter("location", "loction");
            auditEvent.addParameter("userId", "8");
            auditEvent.addParameter("workflowReason", "rrr");
            InputStream requestMessage = _requestBuilder.buildSoapRequest();
            WebRequest request = new PostMethodWebRequest(
                    "http://hostname.ingored.com/services/audit",
                    requestMessage, "text/xml");
            request.setHeaderField("SOAPAction", "");
            WebResponse response = _client.getResponse(request);
            assertEquals("text/xml", response.getContentType());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
