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
package com.mckesson.eig.utility.util;

import org.springframework.beans.factory.BeanFactory;

/**
 * This class holds a reference to the spring bean factory. The bean factory
 * will be set during web application startup or in the setup() method of junit
 * test cases.
 */

public final class SpringUtilities {

    private static SpringUtilities _instance = null;
    private BeanFactory _beanFactory = null;

    /**
     * Private constructor, called only once as it is a singleton class.
     */
    private SpringUtilities() {
    }

    /**
     * Static method which creates an instance for the first time.
     * @return SpringUtilities
     */
    public static SpringUtilities getInstance() {
        if (_instance == null) {
            _instance = new SpringUtilities();
        }
        return _instance;
    }

    /**
     * Returns the beanfactory that is set as a part of initialization from webcontext.
     * @return BeanFactory
     */
    public BeanFactory getBeanFactory() {
        return _beanFactory;
    }

    /**
     * Sets the beanfactory that is set as a part of initialization from webcontext.
     * @param BeanFactory
     */
    public void setBeanFactory(BeanFactory factory) {
        _beanFactory = factory;
    }
}
