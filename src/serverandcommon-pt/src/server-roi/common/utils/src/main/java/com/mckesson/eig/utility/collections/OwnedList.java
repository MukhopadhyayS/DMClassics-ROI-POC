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

package com.mckesson.eig.utility.collections;

import java.io.Serializable;
import java.util.List;

/**
 * Provides method for replacing a particular object with a specified one and
 * also method for returning specified objects.
 */
public interface OwnedList<O, T> extends List<T>, Serializable {
    /**
     * Returns the specified Object.
     *
     * @return an object.
     */
    O getOwner();

    /**
     * Invoked when the user sets the object.
     *
     * @param owner
     *            Object which has to be set.
     */
    void setOwner(O owner);
}
