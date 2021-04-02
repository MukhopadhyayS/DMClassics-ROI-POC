/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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


package com.mckesson.eig.inuse.base.api;

import com.mckesson.eig.utility.exception.ClientErrorCodes;


/**
 * @author OFS
 * @date   Nov 05, 2008
 * @since  HPF 13.1 [INUSE]; Nov 05, 2008
 */
public enum InUseClientErrorCodes {

    //Client Error Codes
    SYSTEM_COULD_NOT_LOG_YOU_ON(ClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON),
    OPTIMISTIC_LOCKING_COLLISION(ClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION),
    DATA_INTEGRITY_VIOLATION(ClientErrorCodes.DATA_INTEGRITY_VIOLATION),
    INVALID_ID(ClientErrorCodes.INVALID_ID),
    RECORD_NOT_FOUND("IN.USE.0.0.1"),
    INVALID_APPLICATION_DATA("IN.USE.0.0.2"),
    RECORD_ALREADY_IN_USE("IN.USE.0.0.3"),
    IN_USE_OPERATION_FAILED("IN.USE.0.0.4"),
    OBJECT_TYPE_SHOULD_NOT_BE_EMPTY("IN.USE.0.0.5"),
    OBJECT_ID_SHOULD_NOT_BE_EMPTY("IN.USE.0.0.6"),
    EXPIRES_MINUTES_SHOULD_NOT_BE_LESS_THAN_ZERO("IN.USE.0.0.7"),
    INVALID_APPLICATION_ID("IN.USE.0.0.8"),
    USER_ID_SHOULD_NOT_BE_EMPTY("IN.USE.0.0.9");

    private final  String _errorCode;

    private InUseClientErrorCodes(String code) { _errorCode = code; }

    @Override
    public String toString() { return _errorCode; }
}
