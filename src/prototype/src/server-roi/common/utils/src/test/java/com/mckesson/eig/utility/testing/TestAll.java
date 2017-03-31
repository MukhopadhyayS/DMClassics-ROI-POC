/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.testing;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * <strong>UtilTestSuite</strong>
 * </p>
 * 
 * This class will test all the TestCase(s) in the com.mckesson.eig.common.util
 * directory.
 * 
 */
public class TestAll extends TestSuite {
    /**
     * Constructor for UtilTestSuite.
     * 
     * @param arg0
     */
    public TestAll(String arg0) {
        super(arg0);
    }

    /**
     * Test all classes in our directory.
     */
    public static Test suite() {
        return new DynamicTestSuite(TestAll.class);
    }

}
