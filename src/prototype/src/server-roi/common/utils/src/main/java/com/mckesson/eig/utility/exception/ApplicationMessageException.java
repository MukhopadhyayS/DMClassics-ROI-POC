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

public class ApplicationMessageException extends ApplicationException {

	private static final long serialVersionUID = 5976583716092685317L;

	private String[] _messageKeys;

    public ApplicationMessageException() {
    }

    public ApplicationMessageException(String logMessage) {
        super(logMessage);
    }

    public ApplicationMessageException(String logMessage, String[] messageKeys) {
        super(logMessage);
        _messageKeys = messageKeys;
    }

    public ApplicationMessageException(Throwable cause) {
        super(cause);
    }

    public ApplicationMessageException(String message, String[] messageKeys,
            Throwable cause) {
        super(message, cause);
        _messageKeys = messageKeys;
    }

    public String[] getMessageKeys() {
        return _messageKeys;
    }
}
