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

import java.util.GregorianCalendar;
import java.util.Calendar;

import junit.framework.TestCase;

/**
 * Test class for ActionHandlerAttribute. 
 *
 */
public class TestActionHandlerAttribute extends TestCase {

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
        ActionHandlerAttribute actionHandlerAttribute = new ActionHandlerAttribute();
        assertNotNull(actionHandlerAttribute);
        assertNull(actionHandlerAttribute.getActionHandlerName());
        assertNull(actionHandlerAttribute.getAttributeName());
        assertNull(actionHandlerAttribute.getAttributeDefaultValue());
        assertNull(actionHandlerAttribute.getAttributeType());
        assertNull(actionHandlerAttribute.getUpdatedTS());
        assertNull(actionHandlerAttribute.getCreatedTS());
    }

    /**
     * tests the getter and setter methods
     */
    public void testGettersAndSetters() {
        ActionHandlerAttribute actionHandlerAttribute = new ActionHandlerAttribute();
        actionHandlerAttribute.setActionHandlerName("ActionHandlerName");
        assertEquals("ActionHandlerName", actionHandlerAttribute.getActionHandlerName());
        
        actionHandlerAttribute.setAttributeName("AttributeName");
        assertEquals("AttributeName", actionHandlerAttribute.getAttributeName());
        
        actionHandlerAttribute.setAttributeDefaultValue("AttributeDefaultValue");
        assertEquals("AttributeDefaultValue", actionHandlerAttribute.getAttributeDefaultValue());
        
        actionHandlerAttribute.setAttributeType("AttributeType");
        assertEquals("AttributeType", actionHandlerAttribute.getAttributeType());
        
        Date testDate = new Date();
        actionHandlerAttribute.setUpdatedTS(testDate);
        assertSame(testDate, actionHandlerAttribute.getUpdatedTS());
        
        actionHandlerAttribute.setCreatedTS(testDate);
        assertSame(testDate, actionHandlerAttribute.getCreatedTS());        
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {
        ActionHandlerAttribute actionHandlerAttribute = new ActionHandlerAttribute();
        actionHandlerAttribute.setActionHandlerName("ActionHandlerName");
        assertEquals("ActionHandlerName", actionHandlerAttribute.getActionHandlerName());
        
        actionHandlerAttribute.setAttributeName("AttributeName");
        assertEquals("AttributeName", actionHandlerAttribute.getAttributeName());
        
        actionHandlerAttribute.setAttributeDefaultValue("AttributeDefaultValue");
        assertEquals("AttributeDefaultValue", actionHandlerAttribute.getAttributeDefaultValue());
        
        actionHandlerAttribute.setAttributeType("AttributeType");
        assertEquals("AttributeType", actionHandlerAttribute.getAttributeType());
        
        final int year = 2009;
        final int month = 12;
        final int day = 25;
        final int hour = 11;
        final int minute = 12;
        final int second = 13;

        Calendar testCalendar = 
            new GregorianCalendar(year, month, day, hour, minute, second);
        Date testDate = testCalendar.getTime();
        
        actionHandlerAttribute.setUpdatedTS(testDate);
        assertSame(testDate, actionHandlerAttribute.getUpdatedTS());
        
        actionHandlerAttribute.setCreatedTS(testDate);
        assertSame(testDate, actionHandlerAttribute.getCreatedTS());

        
       assertEquals("ActionHandlerAttribute[actionHandlerName=ActionHandlerName, "
        		+ "attributeName=AttributeName, "
        		+ "attributeDefaultValue=AttributeDefaultValue, "
        		+ "attributeType=AttributeType, updatedTS=2010-01-25T11:12:13-05:00, "
        		+ "createdTS=2010-01-25T11:12:13-05:00]", 
        		actionHandlerAttribute.toString());
    }

}
