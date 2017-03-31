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
package com.mckesson.eig.utility.metric;

public final class TimedMetric extends DefaultMetric {

    private long _start = System.currentTimeMillis();

    private TimedMetric() {
    }

    public static TimedMetric start() {
        return new TimedMetric();
    }

    public void resetTimer() {
        _start = System.currentTimeMillis();
    }

    public long getStartTime() {
        return _start;
    }
    
    public void logMetric(Object message) {
        TimedMetricMessage timedMessage = new TimedMetricMessage(_start, System
                .currentTimeMillis(), message);
        super.logMetric(timedMessage);
    }
}
