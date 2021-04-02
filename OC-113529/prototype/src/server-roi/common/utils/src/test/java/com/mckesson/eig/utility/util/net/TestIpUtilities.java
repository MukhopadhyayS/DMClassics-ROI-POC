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

import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Test;

import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTest;

/**
 * @author Kenneth Partlow
 */
public class TestIpUtilities extends UnitTest {
    private static final int ASSERT_TEST = 4;
    private static final int ASSERT_TEST1 = 8;

    private IpUtilities _ipUtilities;

    public TestIpUtilities(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return new CoverageSuite(TestIpUtilities.class, IpUtilities.class);
    }

    @Override
	protected void setUp() throws Exception {
        _ipUtilities = new IpUtilities();
    }

    public void test() {
        assertEquals(ASSERT_TEST1, _ipUtilities
                .getHexRepresentationOfIpAddress().length());
    }

    public void testGetLastTwoHexBytesOfIpAddress() {
        assertEquals(ASSERT_TEST, _ipUtilities.getLastTwoHexBytesOfIpAddress()
                .length());
    }

    public void testGetLocalIpAddress() {
        try {
            byte[] comp = InetAddress.getLocalHost().getAddress();
            IpUtilities utility = new IpUtilities();
            byte[] ip = utility.getLocalIpAddress();
            verifyIps(comp, ip);
        } catch (Exception e) {
            fail("Exception was thrown in getLocalIpAddress");
        }
    }

    public void verifyIps(byte[] comp, byte[] actual) {
        for (int i = 0; i < comp.length; i++) {
            assertEquals(comp[i], actual[i]);
        }
    }

    public void testGetLocalIpAddressThrowsException() {
        try {
            IpUtilities sub = new IpUtilitiesSub();
            sub.getLocalIpAddress();
            fail();
        } catch (NetException e) {
        }
    }

    public void testGetHexRepresentationThatThrowsException() {
        IpUtilities sub = new IpUtilitiesSub();
        assertEquals("00000000", sub.getHexRepresentationOfIpAddress());
    }

    private class IpUtilitiesSub extends IpUtilities {
        @Override
		protected InetAddress getLocalHost() throws UnknownHostException {
            throw new UnknownHostException();
        }
    }

}
