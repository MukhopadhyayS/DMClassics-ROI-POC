
package com.mckesson.eig.utility.log.log4j.appender;

import com.mckesson.dm.core.common.logging.OCLogger;


public class TestJMSQueueAppender2 extends TestJMSQueueAppender {

    private static OCLogger _log;

    @Override
    public void initLog() {
        TestJMSQueueAppender2._log = new OCLogger(TestJMSQueueAppender2.class);
    }
}

