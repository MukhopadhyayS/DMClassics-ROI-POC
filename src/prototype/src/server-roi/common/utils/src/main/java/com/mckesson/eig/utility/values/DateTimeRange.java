/**
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Corporation and/or one of its subsidiaries and is protected 
 * under United States and international copyright and other intellectual
 * property laws. Use, disclosure, reproduction, modification, distribution,
 * or storage in a retrieval system in any form or by any means is prohibited
 * without the prior express written permission of McKesson Corporation.
 */
package com.mckesson.eig.utility.values;

import com.mckesson.eig.utility.util.ObjectUtilities;

public class DateTimeRange {

    private DateTime _low;

    private DateTime _high;
    
    public DateTimeRange(DateTime dateTime, Period period) {
        ObjectUtilities.verifyNotNull(dateTime);
        if (period == null) {
            _low = dateTime;
            _high = dateTime;
        } else {
            _low = dateTime.subtract(period);
            _high = dateTime.add(period);
        }
    }
    
    public DateTimeRange(DateTime low, DateTime high) {
        ObjectUtilities.verifyNotNull(low);
        ObjectUtilities.verifyNotNull(high);
        _low = low;
        _high = high;
    }
    
    public DateTimeRange withinNearestDay() {
        DateTime low = _low.asMidnight();
        DateTime high = _high.asMidnight().add(new Days(1));
        return new DateTimeRange(low, high);
    }
    
    public boolean within(DateTime checkDate) {
        return ((checkDate.after(_low)) && (checkDate.before(_high)));
    }
    
    public boolean within(DateTimeRange checkRange) {
        return ((checkRange.getLow().after(_low)) && (checkRange.getHigh()
                .before(_high)));
    }
    
    public DateTime getLow() {
        return _low;
    }
    
    public DateTime getHigh() {
        return _high;
    }
}
