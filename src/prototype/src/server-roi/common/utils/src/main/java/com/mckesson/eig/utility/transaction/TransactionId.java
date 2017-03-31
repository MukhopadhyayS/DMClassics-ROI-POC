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
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.utility.util.net.IpUtilities;

/**
 * Provide methods which resolves the IpAddress of the local machine and method
 * for generating the transaction id.
 * 
 */
public class TransactionId {
    private String _id;
    private String _ipAddress;
    /**
     * Random value used for generating hashcode.
     */
    private static final long HASH_VALUE = 7L;
    /**
     * Random value used for generatiing the hashcode.
     */
    private static final long LONG_VALUE = 37L;

    /**
     * Sets this <code>_ipAddress</code> and generates an Id for this userId
     * and ipAddress.If this IpAddress is <code>null</code> then it returns
     * the IpAddress of the local machine.(but if the local host fails then a
     * default id is returned.).
     * 
     * @param userId
     *            Its the User Id.
     * @param ipAddress
     *            Machines IpAddress.
     */
    public TransactionId(String userId, String ipAddress) {
        _ipAddress = resolveIpAddress(ipAddress);
        _id = createId(userId, _ipAddress);
    }
    /**
     * Sets this <code>id</code>.
     * 
     * @param id
     *            id to be set.
     */
    public TransactionId(String id) {
        _id = id;
    }
    /**
     * Returns the value of this <code>id</code> as a long value.
     * 
     * @return value of this _id as long value.
     */
    public String getValue() {
        return _id;
    }
    /**
     * Returns the String representation of this <code>_id</code>.
     * 
     * @return String representation of this <code>_id</code>.
     */
    public String toString() {
        return _id.toString();
    }
    /**
     * Returns this IpAddress.
     * 
     * @return this IpAddress.
     */
    public String getIpAddress() {
        return _ipAddress;
    }
    /**
     * Returns the transaction id. The transactiond id is the long hashcode of
     * this userId ,Ip address and current time in milliseconds.
     * 
     * @param userId
     *            user Id.
     * @param ipAddress
     *            Ip Address.
     * @return transaction id.
     */
    private String createId(String userId, String ipAddress) {
        long hash = HASH_VALUE;
        hash = LONG_VALUE * hash + StringUtilities.safe(userId).hashCode();
        hash = LONG_VALUE * hash + StringUtilities.safe(ipAddress).hashCode();
        hash = LONG_VALUE * hash + System.currentTimeMillis();
        String id = "SERV_" + hash;
        return id;
    }
    /**
     * Returns a valid IpAddress passed to it.If its <code>null</code> then
     * the local machines IpAddress is returned.Further if the localhost fails
     * before getting the IpAddress then a default IpAddress(127.0.0.1)is
     * returned.
     * 
     * @param ipAddress
     *            IpAddress.
     * @return textual representation of the IpAddress.
     */
    private String resolveIpAddress(String ipAddress) {
        IpUtilities util = new IpUtilities();
        if (!StringUtilities.isEmpty(ipAddress)) {
            return ipAddress;
        }
        return util.getStringIpAddress();
    }
}
