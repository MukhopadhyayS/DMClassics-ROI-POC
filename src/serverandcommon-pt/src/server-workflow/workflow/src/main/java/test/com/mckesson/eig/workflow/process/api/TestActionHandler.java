/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.workflow.process.api;

import java.util.Date;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import java.util.GregorianCalendar;
import java.util.Calendar;

import junit.framework.TestCase;

/**
 * Test class for Content. It tests the methods of Content class
 *
 */
public class TestActionHandler extends TestCase {

    private final long _testId = 1L;
    /**
     * Instantiates the logger for the class.
     */
    private static final Log LOG = LogFactory
            .getLogger(TestActionHandler.class);

    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
     }

    /**
     * Method tearDown() removes the data from Database.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * tests the Process constructors
     */
    public void testConstructors() {
        ActionHandler actionHandler = new ActionHandler();
        assertNotNull(actionHandler);
        assertNull(actionHandler.getActionHandlerName());
        assertEquals(0, actionHandler.getIsActive());
        assertNull(actionHandler.getImplementationClass());
        assertNull(actionHandler.getActionHandlerAttributeList());
        assertNull(actionHandler.getUpdatedTS());
        assertNull(actionHandler.getCreatedTS());
    }

    /**
     * tests the getter and setter methods
     */
    public void testGettersAndSetters() {
        ActionHandler actionHandler = new ActionHandler();        
        actionHandler.setActionHandlerName("ActionHandlerName");
        assertEquals("ActionHandlerName", actionHandler.getActionHandlerName());
        
        actionHandler.setIsActive('Y');
        assertEquals('Y', actionHandler.getIsActive());
        
        actionHandler.setImplementationClass("ImplementationClass");
        assertEquals("ImplementationClass", actionHandler.getImplementationClass());
        
        ActionHandlerAttributeList ahaList = new ActionHandlerAttributeList();
        actionHandler.setActionHandlerAttributeList(ahaList);
        assertSame(ahaList, actionHandler.getActionHandlerAttributeList());
        
        Date testDate = new Date();
        actionHandler.setUpdatedTS(testDate);
        assertSame(testDate, actionHandler.getUpdatedTS());
        
        actionHandler.setCreatedTS(testDate);
        assertSame(testDate, actionHandler.getCreatedTS());
        
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {
        ActionHandler actionHandler = new ActionHandler();        
        actionHandler.setActionHandlerName("ActionHandlerName");
        assertEquals("ActionHandlerName", actionHandler.getActionHandlerName());
        
        actionHandler.setIsActive('Y');
        assertEquals('Y', actionHandler.getIsActive());
        
        actionHandler.setImplementationClass("ImplementationClass");
        assertEquals("ImplementationClass", actionHandler.getImplementationClass());
        
        ActionHandlerAttributeList ahaList = new ActionHandlerAttributeList();
        actionHandler.setActionHandlerAttributeList(ahaList);
        assertSame(ahaList, actionHandler.getActionHandlerAttributeList());
        final int year = 2009;
        final int month = 12;
        final int day = 25;
        final int hour = 11;
        final int minute = 12;
        final int second = 13;

        Calendar testCalendar = 
            new GregorianCalendar(year, month, day, hour, minute, second);
        Date testDate = testCalendar.getTime();
        
        actionHandler.setUpdatedTS(testDate);
        assertSame(testDate, actionHandler.getUpdatedTS());
        
        actionHandler.setCreatedTS(testDate);
        assertSame(testDate, actionHandler.getCreatedTS());

        assertEquals("ActionHandler[actionHandlerName=ActionHandlerName, isActive=Y, "
        + "implementationClass=ImplementationClass, updatedTS=2010-01-25T11:12:13-05:00, "
        + "createdTS=2010-01-25T11:12:13-05:00, "
        + "actionHandlerAttributeList=ActionHandlerAttributeList[]]",
        actionHandler.toString());
    }
}
