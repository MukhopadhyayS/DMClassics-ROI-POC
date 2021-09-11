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
package com.mckesson.eig.wsfw.cxf;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author N.Shah Ghazni
 * @Dec 16, 2008
 *
 * Initializes SpringUtilities with the spring context.
 */
public class ConfigurationServlet
extends CXFServlet {

    /**
     *
     * @see org.apache.cxf.transport.servlet.CXFServlet
     *      #loadBus(javax.servlet.ServletConfig)
     */
    @Override
    public void loadBus(ServletConfig servletConfig){

        super.loadBus(servletConfig);

        // try to pull an existing ApplicationContext out of the
        // ServletContext
        ServletContext svCtx = getServletContext();
        Object ctxObject = svCtx
                        .getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");

        BeanFactory beanFactory = (BeanFactory) ctxObject;
        SpringUtilities.getInstance().setBeanFactory(beanFactory);

        System.out.println("ConfigurationServlet:loadBus>> Bean factory is set");
    }

}
