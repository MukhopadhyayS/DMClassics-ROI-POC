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

/**
 * @author sahuly
 * @date   Feb 16, 2009
 * @since  HECM 2.0; Feb 16, 2009
 */
public class TestProcessActorACL extends junit.framework.TestCase {
    
    public void testConstructor() {

        ProcessActorACL processActorACL = new ProcessActorACL();
        assertNotNull(processActorACL);
        
        processActorACL = new ProcessActorACL(1, "assigned", new Actor(1, 1, 1));
        assertNotNull(processActorACL);
    }
    
    public void testSettersAndGetters() {

        ProcessActorACL processActorACL = new ProcessActorACL();
        Actor actor = new Actor(1, 1, 1);
        Date date = new Date();

        processActorACL.setActor(actor);
        processActorACL.setCreatedTS(date);
        processActorACL.setPermissionName("assigned");
        processActorACL.setProcessId(1);
        processActorACL.setUpdatedTS(date);
        //processActorACL.setVersion(1);

        assertEquals(processActorACL.getActor().getEntityID(), 1);
        assertEquals(processActorACL.getProcessId(), 1);
        assertEquals(processActorACL.getCreatedTS(), date);
        assertEquals(processActorACL.getPermissionName(), "assigned");
        assertEquals(processActorACL.getUpdatedTS(), date);
        //assertEquals(processActorACL.getVersion(), 1);
    }
}
