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

package com.mckesson.eig.utility.exception;

import com.mckesson.eig.utility.testing.UnitTest;

public class TestClientErrorCodes extends UnitTest {
    public TestClientErrorCodes(String name) {
        super(name);
    }

    public void testConstructor() {
        try {
            new ClientErrorCodes();
            fail("Exception should have been thrown");
        } catch (Exception e) {
            assertEquals("Not to be instantiated", e.getMessage());
        }
    }
    public void testGetAValue() {
        try {
            assertEquals("Other Server Error",
                ClientErrorCodes.ERROR_CODE_DESC_MAP.get(ClientErrorCodes.OTHER_SERVER_ERROR));
         } catch (Exception e) {
             fail("Exception should NOT have been thrown");
        }
    }

 }
