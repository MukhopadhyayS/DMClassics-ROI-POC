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
package com.mckesson.eig.wsfw.test.axis;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * This utility class creates a spring bean factory for use by the junit test
 * cases.
 */
public final class UnitSpringInitialization {

    /**
     * Spring Config file.
     */
    private static final String SPRING_CONFIG_FILE = 
        "testApplicationContext.xml";

    /**
     * Gets the BeanFactory.
     */
    private static final BeanFactory FACTORY = new XmlBeanFactory(
            new ClassPathResource(SPRING_CONFIG_FILE));

    /**
     * Gets the Logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(UnitSpringInitialization.class);

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
            SpringUtilities.getInstance().setBeanFactory(FACTORY);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error while loading configuration using spring", e);
        }
    }
}
