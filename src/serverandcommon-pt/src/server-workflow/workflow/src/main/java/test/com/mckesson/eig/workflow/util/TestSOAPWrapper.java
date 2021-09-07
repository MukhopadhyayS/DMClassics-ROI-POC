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

import junit.framework.TestCase;

/**
 * Test class for SOAPWrapper utility.
 *
 */
public class TestSOAPWrapper extends TestCase {


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
     * tests buildSoapEnvelope method.
     */
    public void testbuildSoapEnvelope() {

        String msg = "Test Message";

        String expectedResult = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope"
        	+ " xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
        	+ " xmlns:ns=\"http://eig.mckesson.com/xsd/2009/01\">"
            + "<soap:Header>"
            + "<TransactionId></TransactionId>"
            + "<applicationId></applicationId>"
            + "</soap:Header><soap:Body>"
            + msg
            + "</soap:Body></soap:Envelope>";

        String soapMessage = SOAPWrapper.buildSoapEnvelope(msg);

        assertEquals(expectedResult, soapMessage);
    }

}
