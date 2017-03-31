package com.mckesson.eig.utility.metric;

import java.util.ArrayList;
import java.util.List;

public final class AccumulateTimedMetric extends DefaultMetric {
    private long _start = System.currentTimeMillis();
    private List<TimedMetricMessage> _messages = new ArrayList<TimedMetricMessage>();

    private AccumulateTimedMetric() {
    }

    public static AccumulateTimedMetric start() {
        return new AccumulateTimedMetric();
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
        _messages.add(timedMessage);
    }

    public void printMetric() {
    	for (TimedMetricMessage message : _messages) {
    		super.logMetric(message);
    	}
    }
}
