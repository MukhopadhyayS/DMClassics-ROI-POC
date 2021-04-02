
package com.mckesson.eig.utility.log.log4j.appender;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


public class TestJMSQueueAppender1 extends TestJMSQueueAppender {

    private static Log _log;

    @Override
    public void initLog() {
        TestJMSQueueAppender1._log = LogFactory.getLogger(TestJMSQueueAppender1.class);
    }


    @Override
    public void testJMSAppender() {
        super.testSetup();
        TestJMSQueueAppender1._log.fatal("test message");
    }
}

