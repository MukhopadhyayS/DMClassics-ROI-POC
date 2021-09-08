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

public class Milliseconds extends Period {

	private static final long serialVersionUID = 1L;

	public static final long SECOND = 1000L;

    public static final long MINUTE = SECOND * 60L;

    public static final long HOUR = MINUTE * 60L;

    public static final long DAY = HOUR * 24L;

    public static final long WEEK = DAY * 7L;

    public Milliseconds(int count) {
        super(count);
    }

    public Milliseconds(long count) {
        super(count);
    }

   public Milliseconds(Number count) {
        super(count);
    }

    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.MILLISECOND, toIntValue());
    }

    @Override
	protected String getName() {
        return "millisecond";
    }

    @Override
	public long toMillis() {
        return getCount();
    }
}
