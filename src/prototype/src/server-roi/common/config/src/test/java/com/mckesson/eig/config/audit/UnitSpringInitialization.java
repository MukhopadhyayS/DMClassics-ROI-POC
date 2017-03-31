/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material 
 * contains confidential, proprietary and trade secret information of 
 * McKesson Information Solutions and is protected under United States and 
 * international copyright and other intellectual property laws. Use, 
 * disclosure, reproduction, modification, distribution, or storage 
 * in a retrieval system in any form or by any means is prohibited without 
 * the prior express written permission of McKesson Information Solutions.
 */
package com.mckesson.eig.config.audit;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * This utility class creates a spring bean factory for use by the junit test
 * cases.
 */
public final class UnitSpringInitialization {

    /**
     * Spring Config file.
     */
    private static final String SPRING_CONFIG_FILE = "/WEB-INF/testApplicationContext.xml";

    /**
     * Gets the Logger for this class.
     */
    private static final Log LOG = LogFactory.getLogger(UnitSpringInitialization.class);
    
    /**
     * BeanFactory that holds the instance of the beans loaded by Spring.
     */
    private static BeanFactory _wfBeanFactory;

    /**
     * Sole constructor.
     */
    private UnitSpringInitialization() {

    }

    /**
     * Loads the Configuration file using Spring.
     */
    public static void init() {
        
        try {
            
            _wfBeanFactory = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);

            // to initialize logger
            _wfBeanFactory.getBean("log_initializer");
            SpringUtilities.getInstance().setBeanFactory(_wfBeanFactory);
        } catch (Exception e) { 
            LOG.error("Error while loading configuration using spring", e);
        }
    }
}
