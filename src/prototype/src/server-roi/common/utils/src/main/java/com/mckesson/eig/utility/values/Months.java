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
 * This class represents information about Months.
 */
public class Months extends Period {

	private static final long serialVersionUID = 1L;

	/**
     * This method is taking argument of type <code>integer</code> , using
     * this argument called super class constructor.
     *
     * @param count
     *            Passed as an argument of Type <code>int</code>.
     */
    public Months(int count) {
        super(count);
    }

    /**
     * This method is taking argument of type <code>long</code> , using this
     * argument called super class constructor.
     *
     * @param count
     *            Passed as an argument of Type <code>long</code>.
     */
    public Months(long count) {
        super(count);
    }

    /**
     * This method is taking argument of type <code>Number</code> , using this
     * argument called super class constructor.
     *
     * @param count
     *            Passed as an argument of Type <code>Number</code>.
     */
    public Months(Number count) {
        super(count);
    }

    /**
     * This method is taking argument of type <code>Calendar</code> , using
     * this argument called super class constructor.
     *
     * @param c
     *            Passed as an argument of Type <code>Calendar</code>.
     */
    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.MONTH, toIntValue());
    }

    /**
     * Calling Bean.Returns value of type <code>String</code>.
     *
     * @return <code>String</code> object.
     */
    @Override
	protected String getName() {
        return "month";
    }
}
