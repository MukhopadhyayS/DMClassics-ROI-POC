/**
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

/**
 * This class represents the build version which includes Minor Version and
 * Major Version as well as the Build Number.
 */

public class Version {

    /**
     * Constant which represent Major version.
     */
    private static final String MAJOR = "build.major";
    /**
     * Constant which represent Minor version.
     */
    private static final String MINOR = "build.minor";
    /**
     * Constant which represent Build Revision.
     */
    private static final String REVISION = "build.revision";
    /**
     * Constant which represent Build Number.
     */
    private static final String BUILD = "build.number";
    /**
     * Constant which represent Build Date.
     */
    private static final String DATE = "build.date";
    /**
     * Constant which represent Project Name.
     */
    private static final String NAME = "project.name";

    /**
     * Variable which is corrospondent to Abstact Class.
     */
    private final ResourceBundle _bundle;

    /**
     * Passing appropriate path of type <code>String</code> for getting
     * information of ResourceBundle and save it to variable of type
     * <code>ResourceBundle</code>.
     * 
     * @param path
     *            Passed as an argument of type String.
     */
    public Version(String path) {
        _bundle = ResourceBundle.getBundle(path);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getName() {
        return _bundle.getString(NAME);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getDate() {
        return _bundle.getString(DATE);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getMajor() {
        return _bundle.getString(MAJOR);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getMinor() {
        return _bundle.getString(MINOR);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getRevision() {
        return _bundle.getString(REVISION);
    }

    /**
     * This returns <code>String</code> by calling method getString on
     * ResourceBundle object and taking argument of type <code>String</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getBuild() {
        return _bundle.getString(BUILD);
    }

    /**
     * This returns required Resource Bundle that includes <code>Major version
     * </code>,<code>Minor version</code>,<code>Revision</code> and 
     * <code>Build Number</code>.
     * 
     * @return <code>ResourceBundle</code>Object.
     */
    public String getFullVersion() {
        return getMajor() + "." + getMinor() + "." + getRevision() + "."
                + getBuild();
    }
}
