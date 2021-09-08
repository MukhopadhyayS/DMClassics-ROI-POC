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
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.mckesson.eig.workflow.api.Actor;

/**
 * @author OFS
 *
 * @date Apr 20, 2009
 * @since HECM 1.0.3; Apr 20, 2009
 */
public class TestProcessVersionInfo extends TestCase {


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

    	ProcessVersionInfo processVersionInfo = new ProcessVersionInfo();
        assertNotNull(processVersionInfo);
    }

    /**
     * tests the getter and setter methods
     */
    public void testGettersAndSetters() {

        final int entityType = 3;
        Actor actor = new Actor(1, entityType, 1);
        ProcessVersionInfo pvInfo = new ProcessVersionInfo();
        Set<Actor> actors = new HashSet<Actor>();
        actors.add(actor);
        pvInfo.setVersionId(1);
        assertEquals(1, pvInfo.getVersionId());

        Date date = new Date();
        pvInfo.setEffectiveDateTime(date);
        assertEquals(date, pvInfo.getEffectiveDateTime());

        pvInfo.setExpireDateTime(date);
        assertEquals(date, pvInfo.getExpireDateTime());

        ProcessAttributeList paList = new ProcessAttributeList();
        pvInfo.setProcessAttributeList(paList);

        Set<ProcessAttribute> paSet = new HashSet<ProcessAttribute>();
        ProcessAttribute pa1 = new ProcessAttribute();
        pa1.setAttributeName("PROCESS_TYPE");
        pa1.setAttributeValue("BOTH");
        paSet.add(pa1);
        pvInfo.setProcessAttributesSet(paSet);
        assertTrue(pvInfo.getProcessAttributesSet().size() > 0);

        pvInfo.setProcessDescription("Description");
        assertEquals("Description", pvInfo.getProcessDescription());

        pvInfo.setProcessInfo(new ProcessInfo());
        assertNotNull(pvInfo.getProcessInfo());

        pvInfo.setProcessName("New Process");
        assertEquals("New Process", pvInfo.getProcessName());

        pvInfo.setProcessAttributeList(null);
        assertNotNull(pvInfo.getProcessAttributeList());
    }

    /**
     * tests the getter and setter methods
     */
    public void testToString() {

    	ProcessVersionInfo testProcessInfo = new ProcessVersionInfo();

        assertEquals("ProcessVersionInfo[versionId=0, "
            + "processName=null, "
            + "processDescription=null, "
            + "expireDateTime=null, "
            + "effectiveDateTime=null]", testProcessInfo.toString());

        testProcessInfo.setVersionId(1);
        testProcessInfo.setProcessName("New Process");
        testProcessInfo.setProcessDescription("Description");

        Date date = new Date();
        testProcessInfo.setEffectiveDateTime(date);
        testProcessInfo.setExpireDateTime(date);

        assertEquals("ProcessVersionInfo[versionId=1"
                + ", processName=New Process"
                + ", processDescription=Description"
                + ", expireDateTime=" + date
                + ", effectiveDateTime=" + date + "]", testProcessInfo.toString());
    }
}
