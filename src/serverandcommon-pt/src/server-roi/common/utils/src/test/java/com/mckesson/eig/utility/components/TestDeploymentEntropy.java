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
package com.mckesson.eig.utility.components;

import junit.framework.TestCase;

/**
 * @author OFS
 *
 * @date May 4, 2009
 * @since HECM 1.0.3; May 4, 2009
 */
public class TestDeploymentEntropy extends TestCase {

    /**
     *
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp()
    throws Exception {

    	super.setUp();
    	System.setProperty("application.home", System.getProperty("user.dir"));
    }

    public void testRevealDeployment() {

    	DeploymentEntropy de = new DeploymentEntropy("../../../bin/eig-utils.jar");
    	de.revealDeployment();

    	de = new DeploymentEntropy("../../../bin/testFail.jar");
    	de.revealDeployment();
    }
}
