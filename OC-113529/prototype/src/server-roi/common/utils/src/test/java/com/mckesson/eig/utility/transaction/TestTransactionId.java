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
package com.mckesson.eig.utility.transaction;
import junit.framework.TestCase;
/**
 * Validates the method which returns the <code>String</code> object
 * representing a <code>Long</code>'s value and a methods which returns the
 * id,Ip address of its class.
 */
public class TestTransactionId extends TestCase {
    /**
     * Random long value for testing.
     */
    private static final String TEST_ID = "1234567890";
    /**
     * Fixed user Id for testing.
     */
    private static final String USER_ID = "Test123";
    /**
     * string which is used to test <code>toString</code> method.
     */
    private static final String TEST_STRING = "1234567890";
    /**
     * Textual representation of a Ip address.
     */
    private static final String IP_ADDRESS = "10.234.56.";

    private TransactionId _transactionId;
    /**
     * Constructs the test case with the given name.
     * 
     * @param testName
     *            Test case name.
     */
    public TestTransactionId(String testName) {
        super(testName);
    }
    /**
     * Set up the test. Creates an instance of the class that's need to be
     * tested.
     * 
     * @throws Exception
     *             if the set up is not made properly.
     */
    protected void setUp() throws Exception {
        _transactionId = new TransactionId(TEST_ID);
    }
    /**
     * This will remove all the data associated with the test.
     * 
     * @throws Exception
     *             if the tear down is not made properly.
     */
    protected void tearDown() throws Exception {
        _transactionId = null;
    }
    /**
     * Tests the constructor which gets this id.
     */
    public void testId() {
        _transactionId = new TransactionId(TEST_ID);
        assertNotNull(_transactionId);
    }
    /**
     * Tests the constructor of the testable class which accepts user Id and Ip
     * Address as parameters.
     */
    public void testConstructor() {
        _transactionId = new TransactionId(USER_ID, IP_ADDRESS);
        assertNotNull(_transactionId);
    }
    /**
     * Checks for the retreival of the id.
     */
    public void testGetvalue() {
        _transactionId = new TransactionId(TEST_ID);
        String testId = _transactionId.getValue();
        assertEquals(TEST_ID, testId);
    }
    /**
     * Checks for the retreival of the Ip address even its not provided.
     */
    public void testGetIpAddress() {
        _transactionId = new TransactionId(USER_ID, null);
        String testIpAddress = _transactionId.getIpAddress();
        assertNotNull(testIpAddress);
    }
    /**
     * Tests whether <code>toString</code> returns a <code>String</code>
     * object representing this <code>Long</code>'s value.
     */
    public void testToString() {
        _transactionId = new TransactionId(TEST_ID);
        String testString = _transactionId.toString();
        assertEquals(TEST_STRING, testString);
    }
}
