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

import java.util.ResourceBundle;

import junit.framework.Test;
import junit.framework.TestCase;

import com.mckesson.eig.utility.testing.CoverageSuite;

public class TestVersion extends TestCase {

    private Version _version;
    private ResourceBundle _bundle;

    public TestVersion(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestVersion.class, Version.class);
    }

    protected void setUp() {
        _bundle = ResourceBundle.getBundle(getBundlePath());
        _version = getVersion();
    }

    protected void tearDown() {
        _bundle = null;
        _version = null;
    }

    protected String getBundlePath() {
        return "com.mckesson.eig.utility.version.data.test";
    }

    protected Version getVersion() {
        return new Version(getBundlePath());
    }

    public void testConstructor() {
        assertNotNull(getVersion());
    }

    public void testGetName() {
        assertEquals(_bundle.getString("project.name"), _version.getName());
    }

    public void testGetDate() {
        assertEquals(_bundle.getString("build.date"), _version.getDate());
    }

    public void testGetMajor() {
        assertEquals(_bundle.getString("build.major"), _version.getMajor());
    }

    public void testGetMinor() {
        assertEquals(_bundle.getString("build.minor"), _version.getMinor());
    }

    public void testGetRevision() {
        assertEquals(_bundle.getString("build.revision"), _version
                .getRevision());
    }

    public void testGetBuild() {
        assertEquals(_bundle.getString("build.number"), _version.getBuild());
    }

    public void testGetFullVersion() {
        String full = _version.getMajor() + "." + _version.getMinor() + "."
                + _version.getRevision() + "." + _version.getBuild();
        assertEquals(full, _version.getFullVersion());
    }

}
