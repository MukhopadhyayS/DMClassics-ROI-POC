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

/**
 * Provide methods to put the context value as identified by the key code
 * parameter and for removing the mapping associated it with.
 * 
 */
public final class TransactionSignature {
    // TODO: implement MDC on OCLogger and use oclogger in all the methods here.
    /**
     * Sole constructor.
     */
    private TransactionSignature() {
    }
    /**
     * Returns the instance of this class.
     * 
     * @return instance of this class.
     */
    public static TransactionSignature getTransactionSignature() {
        return new TransactionSignature();
    }
    /**
     * It puts the context value as identified by the key code parameter.
     * 
     * @param loginId
     *            User ID.
     * @param ipAddress
     *            Machines IpAddress.
     * @param transactionId
     *            this transaction id.
     */
    public static void add(String loginId, String ipAddress, String transactionId) {
//        LogContext.put("ipaddress", ipAddress);
//        LogContext.put("userid", loginId);
//        LogContext.put("transactionid", new TransactionId(transactionId));
    }

    /**
     * It puts the context value as identified by the key code parameter.Since
     * the transaction id is not provided ,it will call the
     * <code>TransactionId</code> method for generating it.
     * 
     * @param loginId
     *            User ID.
     * @param ipAddress
     *            Machines IpAddress.
     */
    public static void add(String loginId, String ipAddress) {
//        LogContext.put("ipaddress", ipAddress);
//        LogContext.put("userid", loginId);
//        LogContext.put("transactionid", new TransactionId(loginId, ipAddress));
    }

    /**
     * It puts the context value as identified by the key code parameter.Since
     * both the transaction id and IpAddress is not provided ,it will call the
     * <code>TransactionId</code> method for generating it and for resolving
     * the IpAddress..
     * 
     * @param loginId
     *            User ID.
     */
    public static void add(String loginId) {
        TransactionId transId = new TransactionId(loginId);
//        LogContext.put("ipaddress", transId.getIpAddress());
//        LogContext.put("userid", loginId);
//        LogContext.put("transactionid", transId);
    }

    /**
     * Removes all mappings associated with this.
     */
    public static void clear() {
//        LogContext.clear();
    }
}
