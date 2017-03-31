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
package com.mckesson.eig.utility.io;

import java.net.MalformedURLException;
import java.net.URL;

import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author tvqzas2
 *Test Case to test the class JDK14MinusURLToFileConverter
 */
public class TestJDK14MinusURLToFileConverter extends UnitTest {
/**
 * Private variable.
 */
private  JDK14MinusURLToFileConverter _jdk14MinusURLToFileConverter;
    /**
     * Costructs the test case .
     * @param arg0
     * Name of the test case
     */
    public TestJDK14MinusURLToFileConverter(String arg0) {
        super(arg0);
    }
    /**
     * @see junit.framework.TestCase#setUp()
     * @throws Exception
     * Initialises the data member 
     */
    protected void setUp() throws Exception {
        super.setUp();
         _jdk14MinusURLToFileConverter = new JDK14MinusURLToFileConverter();
    }
    /**
     *  @see junit.framework.TestCase#tearDown()
     *  @throws Exception
     *  sets the instance to null
     */
    protected void tearDown() throws Exception {
         super.tearDown();
        _jdk14MinusURLToFileConverter = null;
    }
    
 /**
 * Tests the ToFile method.
 */
public void testToFile() {
     try {
        URL url = new URL("http://foo.bar:8080");
         _jdk14MinusURLToFileConverter = new JDK14MinusURLToFileConverter();
        assertNotNull(_jdk14MinusURLToFileConverter.toFile(url));
        assertNull(_jdk14MinusURLToFileConverter.toFile(new URL("//foo.bar")));
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
     
 }
}
