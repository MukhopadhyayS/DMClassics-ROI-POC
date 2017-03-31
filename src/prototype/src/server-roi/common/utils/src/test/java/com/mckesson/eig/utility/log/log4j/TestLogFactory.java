/*
 * Copyright 2003 McKesson Information Solutions
 *
 * The copyright to the computer program(s) herein
 * is the property of McKesson Information Solutions.
 * The program(s) may be used and/or copied only with
 * the written permission of McKesson Information Solutions
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.mckesson.eig.utility.log.log4j;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mckesson.eig.utility.io.FileLoader;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.testing.CoverageSuite;
import com.mckesson.eig.utility.testing.UnitTestSpringInitializer;
import com.mckesson.eig.utility.util.ClassUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;

public class TestLogFactory extends TestCase {

    public TestLogFactory(String name) {
        super(name);
    }

    public static Test suite() {
        return new CoverageSuite(TestLogFactory.class, LogFactory.class);
    }

    public void testConstructor() {
        Object o = ReflectionUtilities.callPrivateConstructor(LogFactory.class);
        assertNotNull("o should not be null", o);
    }

    public void testConstructorsArePrivate() {
        assertTrue(ClassUtilities.areAllConstructorsPrivate(LogFactory.class));
    }

    public void testLog4JFatal() {
        Log log = LogFactory.getLogger(this.getClass());

        assertEquals(false, log.isErrorEnabled());
        assertEquals(true, log.isFatalEnabled());
    }

    public void testLog4JError() {
        String category = "com.mckesson.eig.utility.context";

        Log log = LogFactory.getLogger(category);

        assertEquals(false, log.isWarnEnabled());
        assertEquals(true, log.isErrorEnabled());
    }

    public void testLog4JWarn() {
        String category = "com.mckesson.eig.utility.configuration";

        Log log = LogFactory.getLogger(category);

        assertEquals(false, log.isInfoEnabled());
        assertEquals(true, log.isWarnEnabled());
    }

    public void testLog4JInfo() {
        String category = "com.mckesson.eig.utility.exception";

        Log log = LogFactory.getLogger(category);

        assertEquals(false, log.isDebugEnabled());
        assertEquals(true, log.isInfoEnabled());
    }

    public void testLog4JOff() {
        String category = "com.mckesson.eig.utility.service";
        Log log = LogFactory.getLogger(category);
        assertEquals(false, log.isFatalEnabled());
    }

    public void testGetLogger() {
        Logger logger = LogManager.getLoggerRepository().getLogger(
                "com.mckesson.eig.utility.service");
        Log log = LogFactory.getLogger(logger);
        assertEquals(false, log.isFatalEnabled());
        
    }
     
    /**
     * Tests the getLogInitializer()method.
     */
    public void testGetInitializer() {
        Log4JInitializer log4jInitializer = (Log4JInitializer) LogFactory.getLogInitializer();
        LogFactory.getLogInitializer().preinitialize();
        String fileName = "com/mckesson/eig/utility/log/log4j/data/log4j.config";
        PropertyConfigurator prop = new PropertyConfigurator();
         log4jInitializer.configure(prop, fileName); 
         assertNotNull(log4jInitializer);
     }
    protected void setUp() throws Exception {
        String fileName = "com/mckesson/eig/utility/log/log4j/data/log4j.config";
        File file = FileLoader.getResourceAsFile(fileName);
        PropertyConfigurator prop = new PropertyConfigurator();
        UnitTestSpringInitializer.init();
        prop.doConfigure(file.getAbsolutePath(), LogFactory.getLogInitializer()
                .getLoggerRepository());
        
    }

}
