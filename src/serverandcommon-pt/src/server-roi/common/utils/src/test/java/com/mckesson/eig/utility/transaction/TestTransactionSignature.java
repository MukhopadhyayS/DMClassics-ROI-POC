/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.utility.transaction;

import java.util.Hashtable;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.MDC;

/**
 * Tests to validate the methods which put and clears the context value to a
 * map.
 */
public class TestTransactionSignature extends TestCase {
    /**
     * Fixed user Id for testing.
     */
    private static final String LOGIN_ID = "Test_Id";

    /**
     * Raw Ip address.
     */
    private static final String IP_ADDRESS = "Test_10.13.45.645";

    /**
     * Random long value for testing.
     */
    private static final String TRANSACTION_ID = "1234567890";

    /**
     * Constructs the test case with the given name.
     *
     * @param testName
     *            Test case name.
     */
    public TestTransactionSignature(String testName) {
        super(testName);
    }

    /**
     * Set up the test. Creates an instance of the class that's need to be
     * tested.
     *
     * @throws Exception
     *             if the set up is not made properly.
     */
    @Override
	protected void setUp() throws Exception {
        assertNotNull(TransactionSignature.getTransactionSignature());
    }

    /**
     * This will remove all the data associated with the test.
     *
     * @throws Exception
     *             if the tear down is not made properly.
     */
    @Override
	protected void tearDown() throws Exception {
        TransactionSignature.clear();
    }

    /**
     * It checks whether the <code>add</code> method of the testable class put
     * a context value (Login id,Ip address and Transaction Id ) in to the
     * current thread's context map.(If the Ip address is not provided it adds a
     * raw Ip address and in case of transaction id it generates a new
     * transaction id and adds it to the context map along with other details
     * provided.)
     */
    public void testAdd() {
        try {
            TransactionSignature.add(LOGIN_ID, IP_ADDRESS, TRANSACTION_ID);
            Hashtable< ? , ? > testHashtable = getContext();
            assertFalse(testHashtable.isEmpty());

            TransactionSignature.add(LOGIN_ID, IP_ADDRESS);
            Hashtable< ? , ? > testContext = getContext();
            assertFalse(testContext.isEmpty());
            assertNotNull(testContext.get("transactionid"));

            TransactionSignature.add("USER_123");
            Hashtable< ? , ? > testAll = getContext();
            assertFalse(testAll.isEmpty());
            assertNotNull(testAll.get("transactionid"));
            assertNotNull(testAll.get("ipaddress"));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Ensures whether all the mappings are removed from this map.
     */
    public void testClear() {
        try {
            TransactionSignature.add(LOGIN_ID, IP_ADDRESS, TRANSACTION_ID);
            TransactionSignature.clear();
            Map< ? , ? > testAfterClear = getContext();
            assertTrue(testAfterClear.isEmpty());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Returns the content added to the <code>Hashtable</code>.
     *
     * @return added contents.
     */
    public Hashtable< ? , ? > getContext() {
        Hashtable< ? , ? > testHashtable = MDC.getContext();
        return testHashtable;
    }
}
