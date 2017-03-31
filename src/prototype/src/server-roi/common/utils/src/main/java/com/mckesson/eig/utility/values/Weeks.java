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
 * This class named as week contain information about week of time.
 */
public class Weeks extends Period {

	private static final long serialVersionUID = 1L;

	/**
     * Passed argument of type <code>integer</code> and called super class
     * constructor.
     *
     * @param count
     *            Passed as an argument.
     */
    public Weeks(int count) {
        super(count);
    }

    /**
     * Passed argument of type <code>long</code> and called super class
     * constructor.
     *
     * @param count
     *            Passed as an argument.
     */
    public Weeks(long count) {
        super(count);
    }

    /**
     * Passed argument of type <code>Number</code> and called super class
     * constructor.
     *
     * @param count
     *            Passed as an argument.
     */
    public Weeks(Number count) {
        super(count);
    }

    /**
     * Passed argument of type <code>Calendar</code> and called super class
     * constructor.
     *
     * @param c
     *            Passed as an argument of Type <code>Calender</code>.
     */
    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.WEEK_OF_YEAR, toIntValue());
    }

    /**
     * Bean method.Returns <code>String</code>.
     *
     * @return <code>string </code>object.
     */
    @Override
	protected String getName() {
        return "week";
    }

    /**
     * It returns value in long type.
     *
     * @return <code>Period</code> Type Object.
     */
    @Override
	public long toMillis() {
        return getCount() * Milliseconds.WEEK;
    }
}
