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
package com.mckesson.eig.utility.version;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestCommonVersion extends TestVersion {

    public TestCommonVersion(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestCommonVersion.class, CommonVersion.class);
    }

    protected Version getVersion() {
        return new CommonVersion();
    }

    protected String getBundlePath() {
        return CommonVersion.VERSION_PATH;
    }

}
