///*
// * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
// * All Rights Reserved.
// *
// * Use of this material is governed by a license agreement. This material
// * contains confidential, proprietary and trade secret information of
// * McKesson Information Solutions and is protected under United States and
// * international copyright and other intellectual property laws. Use,
// * disclosure, reproduction, modification, distribution, or storage
// * in a retrieval system in any form or by any means is prohibited without
// * the prior express written permission of McKesson Information Solutions.
// */
//
//package com.mckesson.eig.roi.authorization;
//
///**
// * Interface used for web service authentication. Multiple implementations of
// * this interface are expected for different authentication strategies - for
// * example HPF authentication and HA Authentication common service.
// */
//public interface AuthenticationStrategy {
//
//    /**
//     * Authenticate a user.
//     *
//     * @param user
//     * @param password
//     * @return result of the authentication
//     */
//    AuthenticatedResult authenticate(String user, String password);
//
//    /**
//     * Authenticate a user and login.  This method allows an
//     * authentication strategy to perform extra work
//     * during a login authentication.
//     *
//     * @param user
//     * @param password
//     * @return result of the authentication
//     */
//    AuthenticatedResult login(String user, String password);
//
//    /**
//     * Authenticate the user without a password check.  Used for
//     * services that do not require a password.
//     *
//     * @param userName
//     */
//    void authenticate(String userName);
//
//}
