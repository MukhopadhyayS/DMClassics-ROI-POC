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

package com.mckesson.eig.workflow.util;

import javax.xml.bind.JAXBException;

import junit.framework.TestCase;

/**
 * Test class for JAXBUtil.
 *
 */
public class TestJAXBUtil extends TestCase {


    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
     }

    /**
     * tearDown() method for test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * tests marshallObjectMethod in JAXBUtil.
     */
    public void testMarshallObjectMethod() {
        String method = "testMarshallObjectMethod";
        System.out.println(method + "  Start >>");

        TestClass testObject = new TestClass();
        testObject.setTestString("TESTSTRINGVALUE");

        String result = "";

        try {
            result = JAXBUtil.marshallObject(testObject);
        } catch (JAXBException e) {
            System.out.println("Exception in JAXBUtil test: " + e.getMessage());
            fail("marshalling of testObject failed");
        }

        assertEquals("<test><testString>TESTSTRINGVALUE</testString></test>", result);

        System.out.println(method + "  End >>");
    }
}


