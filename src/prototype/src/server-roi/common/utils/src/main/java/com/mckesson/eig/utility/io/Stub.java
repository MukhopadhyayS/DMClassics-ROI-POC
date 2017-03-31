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

package com.mckesson.eig.utility.io;

import java.io.Serializable;

import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * Provides method which returns the new instance of the specified class.
 */
public class Stub implements Serializable {

	private static final long serialVersionUID = -800538853185795524L;

	/**
     * Class name.
     */
    private final String _type;

    /**
     * Ensures the string <code>type</code> have content.
     *
     * @param type
     *            Name of the class.
     */
    public Stub(String type) {
        StringUtilities.verifyHasContent(type);
        _type = type;
    }

    /**
     * Returns the new instance of the specified class.
     *
     * @return a newly instantiated Object of type <code>_type</code>.
     */
    private Object readResolve() {
        return ReflectionUtilities.newInstance(_type);
    }
}
