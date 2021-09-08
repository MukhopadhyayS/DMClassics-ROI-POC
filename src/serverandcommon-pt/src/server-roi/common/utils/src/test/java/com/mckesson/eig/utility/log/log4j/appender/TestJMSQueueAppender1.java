
package com.mckesson.eig.utility.log.log4j.appender;

import com.mckesson.dm.core.common.logging.OCLogger;


public class TestJMSQueueAppender1 extends TestJMSQueueAppender {

    private static OCLogger _log;

    @Override
    public void initLog() {
        TestJMSQueueAppender1._log = new OCLogger(TestJMSQueueAppender1.class);
    }
}

