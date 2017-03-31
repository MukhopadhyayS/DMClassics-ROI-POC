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

package com.mckesson.eig.utility.util.net;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author McKesson
 *
 */
public class TestMailValidationUtilities extends UnitTest {

    static final int GOOD_PORT = 8;
    static final int BAD_PORT_MAX = 88888;
    static final int BAD_PORT_MIN = -1;

    static final String GOOD_SERVER =
        "Server123456789012345678901234567890123456789012345678901234567890";

    static final String BAD_SERVER = GOOD_SERVER
        + "Server123456789012345678901234567890123456789012345678901234567890"
        + "Server123456789012345678901234567890123456789012345678901234567890"
        + "Server123456789012345678901234567890123456789012345678901234567890";

    static final String BAD_LOCAL =
        "Local123456789012345678901234567890123456789012345678901234567890";

    static final String BAD_DOMAIN =
        "Domain123456789012345678901234567890123456789012345678901234567890"
        + "Domain123456789012345678901234567890123456789012345678901234567890"
        + "Domain123456789012345678901234567890123456789012345678901234567890"
        + "Domain123456789012345678901234567890123456789012345678901234567890";


    public static Test suite() {
        return new CoverageSuite(TestMailValidationUtilities.class, MailValidationUtilities.class);
    }

    public void testValidMailServerName() {
         assertTrue(MailValidationUtilities.isValidMailServerName(GOOD_SERVER));
    }

    public void testValidMailServerPort() {
        assertTrue(MailValidationUtilities.isValidMailServerPort(GOOD_PORT));
   }

    public void testInvalidMailServerName() {
        //bad server name - empty
        assertFalse(MailValidationUtilities.isValidMailServerName(""));
        //bad server name - too long
        assertFalse(MailValidationUtilities.isValidMailServerName(BAD_SERVER));
   }

   public void testInvalidMailServerPort() {

        //bad port > max
        assertFalse(MailValidationUtilities.isValidMailServerPort(BAD_PORT_MAX));

        //bad port < min
        assertFalse(MailValidationUtilities.isValidMailServerPort(BAD_PORT_MIN));
    }

    public void testValidMailIds() {
        assertTrue(MailValidationUtilities.isValidMailId("billy@mckesson.com"));
        assertTrue(MailValidationUtilities.isValidMailId("bob@mckesson.ca"));

        //LOCAL is equal to max
        assertTrue(MailValidationUtilities.isValidMailId(BAD_LOCAL
                .substring(0, MailValidationUtilities.MAX_LOCAL_EMAIL_ID_LENGTH) + "@123.com"));

       //DOMAIN is equal to max
        final String suffix = ".com";
        assertTrue(MailValidationUtilities.isValidMailId("local@"
             + BAD_DOMAIN
             .substring(0, MailValidationUtilities.MAX_DOMAIN_EMAIL_ID_LENGTH - suffix.length())
             + suffix));
    }

    public void testInvalidMailIds() {

        //no spaces or empty allowed
        assertFalse(MailValidationUtilities.isValidMailId(""));
        assertFalse(MailValidationUtilities.isValidMailId(" "));

        //email is too long
        assertFalse(MailValidationUtilities.isValidMailId(BAD_LOCAL + "@" + BAD_DOMAIN));

        //LOCAL is too long
        assertFalse(MailValidationUtilities.isValidMailId(BAD_LOCAL + "@123.com"));

        //DOMAIN is too long
        assertFalse(MailValidationUtilities.isValidMailId("local@" + BAD_DOMAIN));

        //no @
        assertFalse(MailValidationUtilities.isValidMailId("no_at.com"));

        //no domain qualifier
        assertFalse(MailValidationUtilities.isValidMailId("no_at123com"));

        //more than one @
        assertFalse(MailValidationUtilities.isValidMailId("bad@123@456.com"));
    }
}
