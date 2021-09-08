/**
 * Copyright © 2010 McKesson Corporation and/or one of its subsidiaries.
 * All rights reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.audit;

import com.mckesson.eig.utility.exception.ApplicationException;

public class AuditException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public AuditException() {
    }

    public AuditException(String message) {
        super(message);
    }

    public AuditException(Throwable cause) {
        super(cause);
    }

    public AuditException(String message, Throwable cause) {
        super(message, cause);
    }
}
