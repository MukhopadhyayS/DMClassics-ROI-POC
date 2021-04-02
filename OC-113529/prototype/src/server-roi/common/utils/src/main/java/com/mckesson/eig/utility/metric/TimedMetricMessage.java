package com.mckesson.eig.utility.metric;

import com.mckesson.eig.utility.util.StringUtilities;

public class TimedMetricMessage {

    /**
     * Indicates the splitter used to format the log message
     */
    private static final String MESSAGE_SPLITTER = "@";

    private long _start;
    private long _stop;
    private Object _message;

    public TimedMetricMessage(long start, long stop, Object message) {
        _start = start;
        _stop = stop;
        _message = message;
    }

    /**
     * Formats the log message with out the message splitter '@'
     *
     * @param message
     * 			Contains the message has to log.
     *
     * @return Formatted message with out message splitter.
     */
    private Object format(Object message) {

        String msg = StringUtilities.EMPTYSTRING;
        if (message != null && message instanceof String) {

            msg = (String) message;
            int index = msg.indexOf(MESSAGE_SPLITTER);
            if (index != -1) {
                msg = msg.substring(0, index);
            }
        }
        return msg;
    }

    public String toString() {
        long duration = _stop - _start;
        StringBuffer msg = new StringBuffer();
        msg.append("Start|");
        msg.append(_start);
        msg.append("|Stop|");
        msg.append(_stop);
        msg.append("|Duration|");
        msg.append(duration);
        msg.append("|");
        msg.append(format(_message));
        return msg.toString();
    }
}
