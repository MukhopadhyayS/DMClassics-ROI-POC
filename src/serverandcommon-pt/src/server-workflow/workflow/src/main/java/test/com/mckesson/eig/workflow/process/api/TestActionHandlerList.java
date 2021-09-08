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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;


/**
 * Test class for ProcessList.
 *
 */
public class TestActionHandlerList extends TestCase {

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
        ActionHandlerList ahList = new ActionHandlerList();
        assertNotNull(ahList);
        assertTrue(ahList.getActionHandlerList().size() == 0);
        
        List<ActionHandler> anAHList = new ArrayList<ActionHandler>();
        ahList = new ActionHandlerList(anAHList);
        assertNotNull(ahList.getActionHandlerList());
        assertTrue(ahList.getActionHandlerList().size() == 0);
    }

    /**
     * tests the getter and setter methods
     */
    public void testGettersAndSetters() {
        ActionHandlerList ahList = new ActionHandlerList();
        assertNotNull(ahList);
        assertTrue(ahList.getActionHandlerList().size() == 0);
        
        List<ActionHandler> aList = new ArrayList<ActionHandler>();
        ActionHandler aAH = new ActionHandler();
        aAH.setActionHandlerName("actionHandlerName");
        aAH.setImplementationClass("com.mckesson.implementationClass1");
        aAH.setIsActive('Y');
        final int year = 2009;
        final int month = 12;
        final int day = 25;
        final int hour = 11;
        final int minute = 12;
        final int second = 13;
        Calendar testCalendar = 
            new GregorianCalendar(year, month, day, hour, minute, second);
        Date testDate = testCalendar.getTime();

        aAH.setCreatedTS(testDate);
        aAH.setUpdatedTS(testDate);
        aAH.setActionHandlerAttributeList(new ActionHandlerAttributeList());
        aList.add(aAH);
        
        ahList.setActionHandlerList(aList);
        ahList.setSize(1);

        assertNotNull(ahList.getActionHandlerList());
        assertTrue(ahList.getActionHandlerList().size() == 1);
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {
        ActionHandlerList ahList = new ActionHandlerList();
        assertNotNull(ahList);
        assertTrue(ahList.getActionHandlerList().size() == 0);
        
        List<ActionHandler> aList = new ArrayList();
        ActionHandler aAH = new ActionHandler();
        aAH.setActionHandlerName("actionHandlerName");
        aAH.setImplementationClass("com.mckesson.implementationClass1");
        aAH.setIsActive('Y');
        final int year = 2009;
        final int month = 12;
        final int day = 25;
        final int hour = 11;
        final int minute = 12;
        final int second = 13;
        Calendar testCalendar = 
            new GregorianCalendar(year, month, day, hour, minute, second);
        Date testDate = testCalendar.getTime();

        aAH.setCreatedTS(testDate);
        aAH.setUpdatedTS(testDate);
        aAH.setActionHandlerAttributeList(new ActionHandlerAttributeList());
        aList.add(aAH);
        
        ahList.setActionHandlerList(aList);
        ahList.setSize(1);
        
        assertEquals("ActionHandlerList[ ActionHandler[actionHandlerName=actionHandlerName, "
                + "isActive=Y, implementationClass=com.mckesson.implementationClass1, "
        + "updatedTS=2010-01-25T11:12:13-05:00, createdTS=2010-01-25T11:12:13-05:00, "
        + "actionHandlerAttributeList=ActionHandlerAttributeList[]] ]", 
        ahList.toString());         
    }
}
