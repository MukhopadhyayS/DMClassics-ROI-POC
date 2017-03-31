
package com.mckesson.eig.utility.log.log4j.appender;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogEvent;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

public class TestJMSQueueAppender extends TestCase {

    private static final String SPRING_CONFIG_FILE =
                                    "com/mckesson/eig/utility/log/log4j/appender/data/spring.xml";
    private static BeanFactory _beanFactory;
    private static Log _log;

    public void init() {

        InitResources.getInstance().initialiseContainer();
        TestJMSQueueAppender._beanFactory =
                    new ClassPathXmlApplicationContext(TestJMSQueueAppender.SPRING_CONFIG_FILE);
        TestJMSQueueAppender._beanFactory.getBean("log_initializer");
        SpringUtilities.getInstance().setBeanFactory(TestJMSQueueAppender._beanFactory);
    }

    public void initLog() {

        TestJMSQueueAppender._log = LogFactory.getLogger(TestJMSQueueAppender.class);
    }

    public void testSetup() {
        init();
        initLog();
    }

    public void testJMSAppender() {

        LogEvent le = new LogEvent();
        le.setAppID("roi");
        le.setAuthUser("sethurman");
        le.setCode("EIDS234");
        le.setMessage("Test message");
        le.setDetails("details about error");
        TestJMSQueueAppender._log.fatal(le);
        TestJMSQueueAppender._log.fatal("object testing...");
    }
}
