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
package com.mckesson.eig.utility.testing.jdepend;

import java.io.File;

import jdepend.framework.JDepend;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.testing.UnitTest;

public abstract class AbstractJDependTest extends UnitTest {

    private JDepend _jdepend;

    public AbstractJDependTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        _jdepend = new JDepend();
        File file = FileLoader.getResourceAsFile(AbstractJDependTest.class,
                "jdepend.properties");
        _jdepend.addDirectory(file.getParentFile().getParent());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected String getPackageName() {
        return getClass().getPackage().getName();
    }

    protected JDepend getjdepend() {
        return _jdepend;
    }

    protected void setjdepend(JDepend jdepend) {
        this._jdepend = jdepend;
    }
}
