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

package com.mckesson.eig.utility.collections.comparators;

import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.values.DateTime;

public class PropertyComparatorDateDescending<T> extends PropertyComparator<T> {

    public PropertyComparatorDateDescending(String method) {
        super(method);
    }

	@Override
	public int compare(T o1, T o2) {
        DateTime dt1 = (DateTime) ReflectionUtilities.callMethod(o1, getMethodName());
        DateTime dt2 = (DateTime) ReflectionUtilities.callMethod(o2, getMethodName());

        return (-1 * dt1.compareTo(dt2));
    }

}
