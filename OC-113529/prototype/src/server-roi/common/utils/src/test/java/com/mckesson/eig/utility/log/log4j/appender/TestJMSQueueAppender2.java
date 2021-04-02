
package com.mckesson.eig.utility.log.log4j.appender;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


public class TestJMSQueueAppender2 extends TestJMSQueueAppender {

    private static Log _log;

    @Override
    public void initLog() {
        TestJMSQueueAppender2._log = LogFactory.getLogger(TestJMSQueueAppender2.class);
    }

    @Override
    public void testJMSAppender() {
        super.testSetup();
        TestJMSQueueAppender2._log.fatal("test message");
    }
}

