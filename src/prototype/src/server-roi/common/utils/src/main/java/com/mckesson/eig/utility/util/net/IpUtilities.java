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
package com.mckesson.eig.utility.util.net;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.ConversionUtilities;
/**
 * Provide methods for getting the local host,Ipaddress of the local machine. It
 * also provides methods for getting the hexadecimal representation of the
 * IpAddress,getting the last two bytes of the hexadecimal represented
 * IpAddress.
 * 
 */
public class IpUtilities {
    /**
     * Attributes for reading ip address
     */
    private static final int IP_ADDRESS_SIZE = 8;
    private static final int IP_ADDRESS_START = 4;

    /**
     * Holds the logger of this class.
     */
    private static final Log LOG = LogFactory.getLogger(IpUtilities.class);
    /**
     * Sole Constructor.
     */
    public IpUtilities() {
    }
    /**
     * Returns the raw IP address of this <code>InetAddress</code> object. The
     * result is in network byte order: the highest order byte of the address is
     * in <code>getAddress()[0]</code>.
     * 
     * @return the raw IP address of this object.
     */
    public byte[] getLocalIpAddress() {
        try {
            return getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            LOG.warn("Could not obtain IP address of this computer:  ", e);
            throw new NetException();
        }
    }
    /**
     * Returns the local host.
     * 
     * @return the IP address of the local host.
     * 
     * @exception UnknownHostException
     *                if no IP address for the <code>host</code> could be
     *                found.
     */
    protected InetAddress getLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }
    /**
     * Returns the IP address string in textual presentation.If the local host
     * fails then it returns a default IpAddress.
     * 
     * @return the raw IP address in a string format.
     */
    public String getStringIpAddress() {
        try {
            return getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG
                    .warn(
                            "Could not obtain the IpAddress , So setting this default value ",
                            e);
            return "127.0.0.1";
        }
    }
    /**
     * It returns the string hexadecimal representation of the local IpAddress.
     * 
     * @return hexadecimal representation of the local IpAddress.
     */
    public String getHexRepresentationOfIpAddress() {
        try {
            byte[] address = getLocalIpAddress();

            StringBuffer buffer = new StringBuffer(IP_ADDRESS_SIZE);
            for (int i = 0; i < IP_ADDRESS_SIZE / 2; i++) {
                buffer.append(ConversionUtilities
                        .toZeroPaddedHexByte(address[i]));
            }
            return buffer.toString();
        } catch (NetException e) {
            return "00000000";
        }
    }
    /**
     * Returns a new string(last two bytes of the hexadecimal represented
     * IpAddress).
     * 
     * @return last two bytes of the hexadecimal represented IpAddress.
     */
    public String getLastTwoHexBytesOfIpAddress() {
        return getHexRepresentationOfIpAddress().substring(IP_ADDRESS_START,
                IP_ADDRESS_SIZE);
    }
}
