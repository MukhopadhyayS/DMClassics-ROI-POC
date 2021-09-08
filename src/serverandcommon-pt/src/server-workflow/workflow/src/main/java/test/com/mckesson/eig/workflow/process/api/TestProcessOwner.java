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

import com.mckesson.eig.workflow.api.Actor;

public class TestProcessOwner extends junit.framework.TestCase {

    public void testConstructor() {

        ProcessOwner processOwner = new ProcessOwner();
        assertNotNull(processOwner);
        assertNotNull(processOwner);
    }

    public void testSettersAndGetters() {

        ProcessOwner processOwner = new ProcessOwner();
        Actor actor = new Actor(1, 1, 1);
        Date date = new Date();

        processOwner.setActor(actor);
        processOwner.setCreatedTS(date);

        processOwner.setProcessId(1);
        processOwner.setUpdatedTS(date);


        assertEquals(processOwner.getActor().getEntityID(), 1);
        assertEquals(processOwner.getProcessId(), 1);
        assertEquals(processOwner.getCreatedTS(), date);

        assertEquals(processOwner.getUpdatedTS(), date);

    }
}
