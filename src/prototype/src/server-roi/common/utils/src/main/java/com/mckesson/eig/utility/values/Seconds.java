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

package com.mckesson.eig.utility.values;

import java.util.Calendar;

/**
 * This class contains info about second from DateTime.
 *
 */
public class Seconds extends Period {

	private static final long serialVersionUID = 1L;

	/**
     * Passed argument of type <code>integer</code> and is calling super class
     * code <code>Period</code>.
     *
     * @param count
     *            Passed as an argument.
     */
    public Seconds(int count) {
        super(count);
    }

    /**
     * Passed argument of type <code>long</code> and is calling super class
     * code <code>Period</code>.
     *
     * @param count
     *            Passed as an argument.
     */
    public Seconds(long count) {
        super(count);
    }

    /**
     * Passed argument of type <code>Number</code> and is calling super class
     * code <code>Period</code>.
     *
     * @param count
     *            Passed as an argument.
     */
    public Seconds(Number count) {
        super(count);
    }

    /**
     * Passed argument of type <code>Calendar</code> and set second.
     *
     * @param c
     *            Passed as an argument of Type <code>Calendar</code>.
     */
    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.SECOND, toIntValue());
    }

    /**
     * Calling Bean and return object of type <code>String</code>.
     *
     * @return <code>String</code> Object.
     */
    @Override
	protected String getName() {
        return "second";
    }

    /**
     * Calling superclass <code>Period</code> method getCount() and return
     * type <code>Long</code>.
     *
     * @return count Type <code>Long</code>.
     */
    @Override
	public long toMillis() {
        return getCount() * Milliseconds.SECOND;
    }
}
