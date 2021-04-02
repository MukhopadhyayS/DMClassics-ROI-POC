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

import com.mckesson.eig.utility.util.ConversionUtilities;

/**
 * This class contains information about Day.
 */
public class Days extends Period {

	private static final long serialVersionUID = 1L;

	/**
     * @param count
     *            Passed as an argument of type <code>integer</code>.
     */
    public Days(int count) {
        super(count);
    }

    /**
     * @param count
     *            Passed as an argument of type <code>long</code>.
     */
    public Days(long count) {
        super(count);
    }

    /**
     * @param count
     *            Passed as an argument <code>Number</code>.
     */
    public Days(Number count) {
        super(count);
    }

    /**
     * @param c
     *            Passed as an argument of Type <code>Calendar</code>.
     */
    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.DAY_OF_YEAR, toIntValue());
    }

    /**
     * @return object of Type <code>String</code>.
     */
    @Override
	protected String getName() {
        return "day";
    }

    /**
     * @return count type <code>long</code>.
     */
    @Override
	public long toMillis() {
        return getCount() * Milliseconds.DAY;
    }

    /**
     * @param value
     *            Passed as an argument of Type String,which is to be parsed.
     * @return <code>null</code> value or <code>integer</code> value.
     */
    public static Days create(String value) {
        Integer number = ConversionUtilities.toInteger(value, null);
        return (number == null) ? null : new Days(number);
    }
}
