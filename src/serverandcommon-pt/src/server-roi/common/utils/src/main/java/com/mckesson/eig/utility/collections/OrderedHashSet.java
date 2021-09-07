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

import java.util.Collection;
import java.util.LinkedHashSet;

public class OrderedHashSet<T> extends LinkedHashSet<T> {
	private static final long serialVersionUID = 1L;

	/**
     * Holds the instance of OrderedHashMap.
     */
    public OrderedHashSet() {
    }

    public OrderedHashSet(Collection<T> c) {
    	super(c);
    }

    public OrderedHashSet(int initialCapacity) {
    	super(initialCapacity);
	}

	public OrderedHashSet(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
    }
}
