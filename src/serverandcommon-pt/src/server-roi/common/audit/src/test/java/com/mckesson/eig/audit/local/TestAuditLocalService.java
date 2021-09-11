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

package com.mckesson.eig.audit.local;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.audit.UnitTestSpringInitialization;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.audit.model.AuditEventList;
import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.session.WsSession;
/**
 * Validates the createAuditEntry in AuditLocalService scenarios for these
 * methods are tested through the service .This tests all the possible
 * exceptions.
 */
public class TestAuditLocalService extends TestCase {

    private static BeanFactory _beanFactory;
    private static final long TIMESTAMP = 1176843143977L;
    private static final int EVENT_ID = 777;
    private static final int DOMAIN_ID = 888;
    private static final int EVENT_ID1 = 555;
    private static final int DOMAIN_ID1 = 666;
    private static final long COMPONENT_ID = 999999L;
    private static final long EVENT_STATUS = 99999L;
    private static final long OBJECT_ID = 5555L;
    private static final long REVISION1 = 111111L;
    private static final long REVISION2 = 222222L;
    private static final long REVISION3 = 333333L;
    private static final long USER_ID = 000001L;
    private static final long USER_ID1 = 000002L;

    private static final String MRN = "MRN";
    private static final String ENCOUNTER = "ENCOUNTER";
    private static final String FACILITY = "A";
    private static final String ACTION = "X";
    private static final String COMMENT = "COMMENT";

    protected static final String URL = "jnp://127.0.0.1:1099";
    protected static final String JMS_QUEUE_NAME = "queue/eigAudit";
    protected static final String CONNECTION_FACTORY = "java:activemq/QueueConnectionFactory";
    private static final String SOAP_ENVELOPE_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
        + "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
        + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">";

    private static final String SOAP_HEADER_BEGIN = "<soap:Header>"
        + "<TransactionId xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";

    private static final String SOAP_HEADER_USER = "</TransactionId>"
        + "<Username xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";

    private static final String SOAP_HEADER_CLIENT_IP = "</Username>"
        + "<ClientIP xmlns=\"" + EIGConstants.TYPE_NS_V1 + "\">";

    private static final String SOAP_HEADER_END = "</ClientIP>"
         + "</soap:Header><soap:Body>"
         + " <createAuditEntry>";

    private static final String AUDIT_EVENT = "<auditEvent>"
            + "<comment>COMMENT</comment>"
            + "<componentId>999999</componentId>"
            + "<domainId>888</domainId>"
            + "<eventEnd>2007-04-17T16:52:23.977-04:00</eventEnd>"
            + "<eventId>777</eventId>"
            + "<eventStart>2007-04-17T16:52:23.977-04:00</eventStart>"
            + "<eventStatus>99999</eventStatus>"
            + "<location>127.0.0.1</location>"
            + "<objectId>5555</objectId>"
            + "<revision1>111111</revision1>"
            + "<revision2>222222</revision2>"
            + "<revision3>333333</revision3>"
            + "<userId>1</userId>"
            + "<workflowReason>WORKFLOWREASON</workflowReason>"
            + "</auditEvent>";

    private static final String AUDIT_EVENT_ROI = "<auditEvent>"
        + "<actionCode>X</actionCode>"
        + "<comment>COMMENT</comment>"
        + "<componentId>0</componentId>"
        + "<domainId>0</domainId>"
        + "<encounter>ENCOUNTER</encounter>"
        + "<eventId>0</eventId>"
        + "<eventStart>2007-04-17T16:52:23.977-04:00</eventStart>"
        + "<facility>A</facility>"
        + "<location>127.0.0.1</location>"
        + "<mrn>MRN</mrn>"
        + "<userId>1</userId>"
        + "</auditEvent>";

    private static final String SOAP_ENVELOPE_END
        = "</createAuditEntry></soap:Body></soap:Envelope>";

    private static final String USER_NAME = "toarvindh";

    private static String _transID;

    @Override
	protected void setUp() throws Exception {

        super.setUp();
        UnitTestSpringInitialization.init();
        _beanFactory = SpringUtilities.getInstance().getBeanFactory();

        TransactionId transactionId = new TransactionId(USER_NAME, "127.0.0.1");
        _transID = transactionId.getValue();

        WsSession.setSessionData(WsSession.CLIENT_IP, "127.0.0.1");
        WsSession.setSessionData(WsSession.USER_NAME, USER_NAME);
        //LogContext.put("transactionid", transactionId);
    }

    public void testClientAudit() {

        AuditEvent audit = new AuditEvent(EVENT_ID, DOMAIN_ID);
        audit.setComment("COMMENT");
        audit.setComponentId(COMPONENT_ID);
        audit.setEventEnd(new Date(TIMESTAMP));
        audit.setEventStart(new Date(TIMESTAMP));
        audit.setEventStatus(EVENT_STATUS);
        audit.setLocation("127.0.0.1");
        audit.setObjectId(OBJECT_ID);
        audit.setRevision1(REVISION1);
        audit.setRevision2(REVISION2);
        audit.setRevision3(REVISION3);
        audit.setUserId(USER_ID);
        audit.setWorkflowReason("WORKFLOWREASON");

        AuditLocalService service = (AuditLocalService) _beanFactory
                .getBean(AuditLocalService.class.getName());
        service.createAuditEntry(audit);
        String auditEventString = buildSoapEnvelopeWrapper(audit, service);
        assertEquals((SOAP_ENVELOPE_BEGIN + SOAP_HEADER_BEGIN + _transID + SOAP_HEADER_USER
                + USER_NAME + SOAP_HEADER_CLIENT_IP + audit.getLocation() + SOAP_HEADER_END
                + AUDIT_EVENT + SOAP_ENVELOPE_END),
                auditEventString);
    }
    
    public void testClientAuditList() { 
    	
    	 AuditEvent audit = initializeAudit();

         AuditLocalService service = (AuditLocalService) _beanFactory
                 .getBean(AuditLocalService.class.getName());
         AuditEventList auditEventList = new AuditEventList();
         List<AuditEvent> auditEvents = new ArrayList<AuditEvent>();
         auditEvents.add(audit);
         auditEventList.setAuditEvent(auditEvents);
         service.createAuditEntryList(auditEventList);
         String auditEventString = buildSoapEnvelopeWrapper(audit, service);
         assertEquals((SOAP_ENVELOPE_BEGIN + SOAP_HEADER_BEGIN + _transID + SOAP_HEADER_USER
                 + USER_NAME + SOAP_HEADER_CLIENT_IP + audit.getLocation() + SOAP_HEADER_END
                 + AUDIT_EVENT + SOAP_ENVELOPE_END),
                 auditEventString);
    }

	/**
	 * @return
	 */
	private AuditEvent initializeAudit() {
		AuditEvent audit = new AuditEvent();
    	 audit.setEventId(EVENT_ID1);
    	 audit.setDomainId(DOMAIN_ID1);
         audit.setComment("COMMENT");
         audit.setComponentId(COMPONENT_ID);
         audit.setEventEnd(new Date(TIMESTAMP));
         audit.setEventStart(new Date(TIMESTAMP));
         audit.setEventStatus(EVENT_STATUS);
         audit.setLocation("127.0.0.1");
         audit.setObjectId(OBJECT_ID);
         audit.setRevision1(REVISION1);
         audit.setRevision2(REVISION2);
         audit.setRevision3(REVISION3);
         audit.setUserId(USER_ID1);
         audit.setWorkflowReason("WORKFLOWREASON");
		return audit;
	}

    private String buildSoapEnvelopeWrapper(AuditEvent audit,
            AuditLocalService service) {
        String auditEventString = null;
        try {
            auditEventString = service.buildSoapEnvelope(audit);
        } catch (Exception e) {
            fail();
        }
        return auditEventString;
    }

    public void testClientAuditRoi() {
        AuditEvent audit = new AuditEvent();
        audit.setEventStart(new Date(TIMESTAMP));
        audit.setUserId(USER_ID);
        audit.setMrn(MRN);
        audit.setEncounter(ENCOUNTER);
        audit.setFacility(FACILITY);
        audit.setActionCode(ACTION);
        audit.setComment(COMMENT);
        audit.setLocation("127.0.0.1");
        AuditLocalService service = (AuditLocalService) _beanFactory
        .getBean(AuditLocalService.class.getName());
        service.createAuditEntry(audit);

        String auditEventString = buildSoapEnvelopeWrapper(audit, service);

        assertEquals((SOAP_ENVELOPE_BEGIN + SOAP_HEADER_BEGIN + _transID + SOAP_HEADER_USER
                + USER_NAME + SOAP_HEADER_CLIENT_IP + audit.getLocation() + SOAP_HEADER_END
                + AUDIT_EVENT_ROI + SOAP_ENVELOPE_END),
                auditEventString);
    }
}
