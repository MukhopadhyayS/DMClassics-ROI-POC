/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.config.audit;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.audit.local.AuditLocalService;
import com.mckesson.eig.audit.model.AuditEvent;
import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author Sahul Hameed Y
 * @date   Apr 17, 2008
 * @since  HECM 1.0; Apr 17, 2008
 */
public class TestLogConfigAuditManager extends TestCase {
    
    /**
     * Holds the reference of the AuditEvent class.
     */
    private AuditEvent _auditEvent;
    private static final Long USER_ID = new Long(111);
    private static final long OBJECT_ID = 11;
    private static final long EVENT_ID = 11;
    private static final String COMMENT = "Test Comment";
    private MockAuditLocalService _service;

    /**
     * Setup method for the test case.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        super.setUp();
        WsSession.setSessionUserId(USER_ID);
        UnitSpringInitialization.init();
        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
        _service = (MockAuditLocalService) beanFactory.getBean(AuditLocalService.class.getName());
        _service.setPassThrough(false);
    }

    public void testPrivateConstructor() {
        
        try {
            ReflectionUtilities.callPrivateConstructor(LogConfigurationAuditManager.class);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    
    /**
     * Test method for the prepareLogAuditEvent method of the AuditManager
     * class.
     */
    public void testPrepareLogAuditEvent() {
        AuditEvent auditEvent = LogConfigurationAuditManager.prepareinitialAuditEvent();
        assertBaseAuditInfo(auditEvent);
    }

    /**
     * Test method for the prepareLogAuditEvent method that has parameters
     * domainId, objectId, eventId, and comment of the AuditManager class.
     */
    public void testPrepareLogAuditEventWithIdsAndComments() {
        AuditEvent auditEvent = LogConfigurationAuditManager.prepareLogAuditEvent(OBJECT_ID, 
                                                                                  COMMENT);
        assertBaseParamAuditInfo(auditEvent);
    }

    /**
     * Test method for the prepareLogAuditEvent method that has parameters
     * domainId, objectId, eventId, comment and revision of the AuditManager
     * class.
     */
    public void testPrepareLogAuditEventWithIdsAndCommentsPlusRevision() {

        AuditEvent auditEvent = LogConfigurationAuditManager.prepareLogAuditEvent(OBJECT_ID, 
                                                                                  COMMENT);
        assertBaseParamAuditInfo(auditEvent);
    }

    /**
     * Test method for the prepareLogAuditEvent method that has parameters
     * domainId, objectId, eventId, comment and componentId of the AuditManager
     * class.
     */
    public void testPrepareLogAuditEventWithIdsAndCommentsPlusComponentId() {
        
        AuditEvent auditEvent = LogConfigurationAuditManager.prepareLogAuditEvent(OBJECT_ID, 
                                                                                  COMMENT);
        assertBaseParamAuditInfo(2, auditEvent);
        assertEquals(2, auditEvent.getComponentId());
    }

    /**
     * Helper method for asserting on common checked values
     *
     * @param auditEvent
     */
    private void assertBaseAuditInfo(AuditEvent auditEvent) {
        assertBaseAuditInfo(AuditEvent.SYSTEM_ADMINISTRATION, auditEvent);
    }

    /**
     * Helper method for asserting on common checked values
     *
     * @param componentId
     * @param auditEvent
     */
    private void assertBaseAuditInfo(long componentId, AuditEvent auditEvent) {
        assertNotNull(auditEvent);
        assertEquals(componentId, auditEvent.getComponentId());
        assertEquals(AuditEvent.SUCCESS, auditEvent.getEventStatus()
                .longValue());
        assertEquals(USER_ID, auditEvent.getUserId());
        assertNotNull(auditEvent.getEventStart());
    }

    /**
     * Helper method for asserting on common checked values for passed
     * parameters
     *
     * @param auditEvent
     */
    private void assertBaseParamAuditInfo(AuditEvent auditEvent) {
        assertBaseParamAuditInfo(AuditEvent.SYSTEM_ADMINISTRATION, auditEvent);
    }

    /**
     * Helper method for asserting on common checked values for passed
     * parameters
     *
     * @param componentId
     * @param auditEvent
     */
    private void assertBaseParamAuditInfo(long componentId,
            AuditEvent auditEvent) {
        assertBaseAuditInfo(componentId, auditEvent);
        assertEquals(OBJECT_ID, auditEvent.getObjectId().longValue());
        assertEquals(EVENT_ID, auditEvent.getEventId());
        assertEquals(COMMENT, auditEvent.getComment());
    }

    /**
     * Test method for the createAuditEntry method of the AuditManager class.
     */
    public void testCreateAuditEntry() {
        assertCreateAuditEntry(true);
    }

    /**
     * Test failure state for the createAuditEntry method of the AuditManager
     * class.
     */
    public void testCreateAuditEntryFails() {
        _service.setCreateSuccessful(false);
        assertCreateAuditEntry(false);
    }

    private void assertCreateAuditEntry(boolean flag) {
        _auditEvent = LogConfigurationAuditManager.prepareinitialAuditEvent();
        assertEquals(flag, LogConfigurationAuditManager.createAuditEntry(_auditEvent));
    }

    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
