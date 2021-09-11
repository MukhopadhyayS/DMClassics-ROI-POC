/* Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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
package com.mckesson.eig.audit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * This utility class creates a spring bean factory for use by the junit test
 * cases.
 */
public final class UnitTestSpringInitialization {

    /**
     * Location of the Spring configuration file.
     */
    private static final String SPRING_CONFIG_FILE 
                            = "com/mckesson/eig/audit/auditApplicationContext.xml";
    /**
     * BeanFactory that holds the instance of the beans loaded by Spring.
     */
    private static final BeanFactory FACTORY = new XmlBeanFactory(
            new ClassPathResource(SPRING_CONFIG_FILE));
    /**
     * Instance of Logger.
     */
    private static final String SPRING_CONFIG_FILE_ROI
                        = "com/mckesson/eig/audit/auditApplicationContextROI.xml";
    
    private static final BeanFactory ROI_FACTORY = new XmlBeanFactory(
            new ClassPathResource(SPRING_CONFIG_FILE_ROI));

//    private static final Log LOG = LogFactory
//            .getLogger(UnitTestSpringInitialization.class);

    /**
     * Hidden default constructor to support the use of this class a static only.
     */
    private UnitTestSpringInitialization() {
        
    }
    
    /**
     * The Static method that the testCases should call to load Spring
     * configuration.
     */
    public static void init() {
        try {
            // Creates and registers the datasource with a JNDI name.
            InitResources.getInstance().initialiseContainer();
            // Loads the Configuration file for Hibernate using Spring
            SpringUtilities.getInstance().setBeanFactory(FACTORY);
        } catch (Exception e) {
            e.printStackTrace();
            //LOG.error("Error while loading configuration using spring", e);
        }
    }

    public static void initRoi() {
        try {
            // Creates and registers the datasource with a JNDI name.
            InitResources.getInstance().initialiseContainerRoi();
            // Loads the Configuration file for Hibernate using Spring
            SpringUtilities.getInstance().setBeanFactory(ROI_FACTORY);
        } catch (Exception e) {
            e.printStackTrace();
            //LOG.error("Error while loading configuration using spring", e);
        }
    }
}
