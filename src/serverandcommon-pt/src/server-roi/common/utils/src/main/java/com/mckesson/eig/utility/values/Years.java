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
 * This class represents Year information.It has information about time
 * criteria.
 */
public class Years extends Period {

	private static final long serialVersionUID = 1L;

	/**
     * Takes as an <code>integer</code> argument and calling super class
     * constructor.Sets value as a type <code>Long</code>.
     *
     * @param count
     *            This constructor is setting <code>int</code> value.
     */

    public Years(int count) {
        super(count);
    }

    /**
     * Takes as an <code>long</code> argument and calling super class
     * constructor.Sets value as a type <code>Long</code>.
     *
     * @param count
     *            This constructor which is taking <code>long</code> value.
     */
    public Years(long count) {
        super(count);
    }

    /**
     * Takes as an <code>Number</code> argument and calling super class
     * constructor.Sets value as a type <code>Long</code>.
     *
     * @param count
     *            This constructor which is taking numeric value of type
     *            (Abstract)class <code>Number</code>.
     */
    public Years(Number count) {
        super(count);
    }

    /**
     * This method is taking <code>Calender</code>object as parameter and it
     * adds specified year and corrosponding value to type
     *  <code>Calendar</code>.
     *
     * @param c
     *            Takes parmeter of <code>Calender</code> Object.
     */
    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.YEAR, toIntValue());
    }

    /**
     * Bean method.returns year of type <code>String</code>.
     *
     * @return <code>string</code> year.
     */
    @Override
	protected String getName() {
        return "year";
    }

    /**
     * Passing argument as a <code>String</code>,that is passing to method
     * toInteger as a an argument that convert it into
     *  type <code>Integer</code>.
     *
     * @param value
     *            Passing as a Parameter of type <code>String</code>.
     * @return Result of rational operator.
     */

    public static Years create(String value) {
        Integer number = ConversionUtilities.toInteger(value, null);
        return (number == null) ? null : new Years(number);
    }
}
