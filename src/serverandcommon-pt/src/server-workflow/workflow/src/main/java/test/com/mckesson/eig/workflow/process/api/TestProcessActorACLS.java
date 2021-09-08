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

import java.util.HashSet;
import java.util.Set;

/**
 * @author sahuly
 * @date   Feb 16, 2009
 * @since  HECM 2.0; Feb 16, 2009
 */
public class TestProcessActorACLS extends junit.framework.TestCase {
    
    public void testConstructor() {

        ProcessActorACLS processActorACLs = new ProcessActorACLS();
        assertNotNull(processActorACLs);
        
        Set<ProcessActorACL> processACLS = new HashSet<ProcessActorACL>();
        processActorACLs = new ProcessActorACLS(processACLS);
        assertNotNull(processActorACLs);
    }
    
    public void testSettersAndGetters() {
        
        Set<ProcessActorACL> processACLS = new HashSet<ProcessActorACL>();
        ProcessActorACL processActorACL = new ProcessActorACL();
        processACLS.add(processActorACL);
        
        ProcessActorACLS processActorACLS = new ProcessActorACLS();
        processActorACLS.setProcessActorACLS(processACLS);
        assertEquals(processActorACLS.getProcessActorACLS().size(), 1);
        
        processActorACLS.setProcessActorACLS(null);
        assertNotNull(processActorACLS.getProcessActorACLS());
    }

}
