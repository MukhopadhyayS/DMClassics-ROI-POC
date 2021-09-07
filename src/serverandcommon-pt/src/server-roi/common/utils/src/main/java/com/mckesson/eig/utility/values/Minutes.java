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

public class Minutes extends Period {

	private static final long serialVersionUID = 1L;

	public Minutes(int count) {
        super(count);
    }

    public Minutes(long count) {
        super(count);
    }

    public Minutes(Number count) {
        super(count);
    }

    @Override
	protected void addTo(Calendar c) {
        c.add(Calendar.MINUTE, toIntValue());
    }

    @Override
	protected String getName() {
        return "minute";
    }

    @Override
	public long toMillis() {
        return getCount() * Milliseconds.MINUTE;
    }
}
