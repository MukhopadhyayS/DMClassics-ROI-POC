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
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

import com.mckesson.eig.utility.io.FileLoader;

public class Log4JInitializer 
implements LogInitializer {

    public Log4JInitializer() {
        setupDefaultLogging();
    }

    public void preinitialize() {
        // LogFactory calls this method to force this class to load, which
        // will cause the class static {} block to run, which calls
        // setupDefaultLogging(). The defaults should only be setup once
        // before the config files are parsed.
    }

    public void setByProperties(String fileName) {
        configureByProperties(fileName);
    }

    private void configureByProperties(String fileName) {
        try {
            String path = toAbsolutePath(fileName);
            clearAppenders();
            PropertyConfigurator.configureAndWatch(path);
        } catch (Exception e) {
            System.err.println("Exception occured in configureByProperties:"
                    + e.getMessage());
        }
    }

    public void setByXml(String fileName) {
        configureByXml(fileName);
    }

    private void configureByXml(String fileName) {
    	
        try {
            //inorder to load the logging file from the jar, modified the code to load URL 
            URL path = toURL(fileName);
            clearAppenders();
            DOMConfigurator.configure(path);
            
        } catch (Exception e) {
            System.err.println("Exception occured in configureByXML:"
                    + e.getMessage());
        }
    }

    public void configure(Configurator c, String fileName) {
        URL url = toURL(fileName);
        clearAppenders();
        c.doConfigure(url, getLoggerRepository());
    }

    private URL toURL(String fileName) {
        URL url = FileLoader.getResourceAsUrl(Log4JInitializer.class
                .getClassLoader(), fileName);
        checkForNotFound(url, fileName);
        return url;
    }

    private String toAbsolutePath(String fileName) {
        File file = FileLoader.getResourceAsFile(Log4JInitializer.class
                .getClassLoader(), fileName);
        checkForNotFound(file, fileName);
        return file.getPath();
    }

    private void checkForNotFound(Object lookup, String fileName) {
        if (lookup == null) {
            throw new IllegalArgumentException("The file (" + fileName
                    + ") could not be found");
        }
    }

    private void clearAppenders() {
        // Don't want duplicate log entries if someone else configured
        // log4j in the VM.
        clearAppenders("com.mckesson.eig");
        clearAppenders("org.apache");
        clearAppenders("org.hibernate");
        clearAppenders("servletunit");
        clearAppenders("net.sf.ehcache");
        clearAppenders("org.exolab.castor");
        clearAppenders("com.mchange");
    }

    private void clearAppenders(String name) {
        Logger logger = getLoggerRepository().getLogger(name);
        logger.removeAllAppenders();
        logger.setAdditivity(false);
    }

    private void setupDefaultLogging() {
        clearAppenders();
        Properties p = new Properties();
        p.setProperty("log4j.appender.eig_default_console",
                "org.apache.log4j.ConsoleAppender");
        p.setProperty("log4j.appender.eig_default_console.layout",
                "org.apache.log4j.PatternLayout");
        p.setProperty(
                "log4j.appender.eig_default_console.layout.ConversionPattern",
                "%d{DATE} | %t | %c{1} | %m%n");
        p.setProperty("log4j.logger.com.mckesson.eig",
                "debug, eig_default_console");
        p.setProperty("log4j.logger.org.apache", "info, eig_default_console");
        p
                .setProperty("log4j.logger.org.hibernate",
                        "info, eig_default_console");
        p.setProperty("log4j.logger.servletunit", "debug, eig_default_console");
        p.setProperty("log4j.logger.net.sf.ehcache",
                "info, eig_default_console");
        p.setProperty("log4j.logger.org.exolab.castor",
                "info, eig_default_console");
        p.setProperty("log4j.logger.com.mchange", "info, eig_default_console");
        PropertyConfigurator.configure(p);
    }

    public LoggerRepository getLoggerRepository() {
        return LogManager.getLoggerRepository();
    }
}
