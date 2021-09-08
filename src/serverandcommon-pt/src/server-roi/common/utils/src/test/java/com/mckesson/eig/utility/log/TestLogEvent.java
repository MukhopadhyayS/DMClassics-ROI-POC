package com.mckesson.eig.utility.log;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestLogEvent extends TestCase {

    private static LogEvent _le;

    public void testinit() {
        TestLogEvent._le = new LogEvent();
   }

    /**
    * Tests remove method with string value of the class LogContext.
    */
   public void testSetterMethods() {

       _le.setAppID("roi");
       Assert.assertEquals(_le.getAppID(), "roi");
       _le.setTimestamp("19 Feb 2008 11:12:11 GMT");
       Assert.assertEquals(_le.getTimestamp(), "19 Feb 2008 11:12:11 GMT");
       _le.setAuthUser("test user");
       Assert.assertEquals(_le.getAuthUser(), "test user");
       _le.setCode("EIDS234");
       Assert.assertEquals(_le.getCode(), "EIDS234");
       _le.setIPAddress("192.168.0.10");
       Assert.assertEquals(_le.getIPAddress(), "192.168.0.10");
       _le.setMessage("Test message");
       Assert.assertEquals(_le.getMessage(), "Test message");
       _le.setDetails("details about error");
       Assert.assertEquals(_le.getDetails(), "details about error");
   }

   public void testToLog() {
       Assert.assertEquals(true, _le.toLog().getClass() == String.class);
   }

   public void testToString() {
       Assert.assertEquals(true, _le.toString().getClass() == String.class);
   }
}
