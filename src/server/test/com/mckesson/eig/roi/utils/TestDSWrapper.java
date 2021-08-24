/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/
package com.mckesson.eig.roi.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import junit.framework.TestCase;

/**
*
* @author OFS
* @date   June 24, 2013
* @since  HPF 16.0 [ROI]; June 24, 2013
*/
public class TestDSWrapper
extends TestCase {

    private static DSWrapper dsWrapper;
    private static String dbServer;
    private static String database;
    private static String driverClass;
    private static String dbUserName;
    private static String dbPassword;
    private static String dbURL;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Properties props = new Properties();
        props.load(TestDSWrapper.class.getClassLoader().getResourceAsStream("com/mckesson/eig/roi/test/ds.properties"));
        dbServer = props.getProperty("DATABASE_SERVER");
        database = props.getProperty("DATABASE");
        dbUserName = props.getProperty("DATABASE_USER");
        dbPassword = props.getProperty("DATABASE_PASSWORD");
        dbURL = "jdbc:jtds:sqlserver://" + dbServer + ":1433;DatabaseName=" + database;
        driverClass = "net.sourceforge.jtds.jdbc.Driver";
        //dsWrapper = new DSWrapper(driverClass, dbURL, dbUserName, dbPassword, 2, 20);
        dsWrapper = new DSWrapper(driverClass, dbURL, dbUserName, dbPassword);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetConnection() {
        try {
            Connection con = dsWrapper.getConnection();
            assertNotNull(con);
            dsWrapper.close();
        } catch (SQLException e) {
            assertTrue(e instanceof SQLException);
            e.printStackTrace();
        }
    }

    /*public void testDSWrapper() throws FileNotFoundException {
        try {
            dsWrapper.setIdleMaxAge(5);
            dsWrapper.setLogWriter(new PrintWriter(AccessFileLoader.getFile("file")));
            //dsWrapper.setMaximumConnection(20);
            //dsWrapper.setMinmumConnection(2);
            dsWrapper.unwrap(DSWrapper.class);
            dsWrapper.isWrapperFor(DSWrapper.class);
            Connection con = dsWrapper.getConnection();
            assertNotNull(con);
            dsWrapper.close();
            assertNotNull(dsWrapper.getLogWriter());
        } catch (Exception e) {
            assertTrue(e instanceof SQLException);
            e.printStackTrace();
        }
    }*/

    public void testGetConnectionWithDBURL() {
        try {
            DSWrapper dsWraper = new DSWrapper(driverClass, dbURL, dbUserName, dbPassword);
            Connection con = dsWraper.getConnection(dbUserName, dbPassword);
            assertNotNull(con);
            dsWraper.close();
        } catch (SQLException e) {
            assertTrue(e instanceof SQLException);
            e.printStackTrace();
        }
    }

    public void testGetConnectionWithMinimumInput() {
        try {
            DSWrapper ds = new DSWrapper(driverClass, dbURL);
            Connection con = ds.getConnection();
            assertNotNull(con);
            ds.close();
        } catch (SQLException e) {
            assertTrue(e instanceof SQLException);
            e.printStackTrace();
        }
    }

}
