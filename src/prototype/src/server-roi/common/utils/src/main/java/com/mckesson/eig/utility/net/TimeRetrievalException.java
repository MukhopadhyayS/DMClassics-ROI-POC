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

package com.mckesson.eig.utility.net;

import com.mckesson.eig.utility.exception.ChainedException;

public class TimeRetrievalException extends ChainedException {

	private static final long serialVersionUID = 1L;

	TimeRetrievalException() {
    }

    TimeRetrievalException(String message) {
        super(message);
    }

    TimeRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    TimeRetrievalException(Throwable cause) {
        super(cause);
    }
}
