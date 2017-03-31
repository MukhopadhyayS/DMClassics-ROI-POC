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

package com.mckesson.eig.wsfw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * Initializes SpringUtilities with the spring context.
 */
public class ConfigurationServlet extends HttpServlet {

    /**
     * Spring Config.file.
     */
    private static final String INIT_SPRING_CONFIG_FILE = 
        "eig.SpringConfigFile";

    /**
     * Initialize the spring container.
     * 
     * @throws ServletException
     *             if the request/response could not be handled.
     * 
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        loadSpringConfig();
    }

    /**
     * Called by the server through the service method to allow a servlet to
     * handle a GET request.
     * 
     * @param request
     *            Servlets request.
     * @param response
     *            Servlets response.
     * @throws ServletException
     *             if the request for GET could not be handled.
     * @throws IOException
     *             If an IO error occurs.
     * 
     * @see javax.servlet.http.HttpServlet
     *      #doGet(javax.servlet.http.HttpServletRequest,
     *          javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        loadSpringConfig();
    }

    /**
     * Called by the server through the service method to allow a servlet to
     * handle a POST request.
     * 
     * @param request
     *            servlets request.
     * @param response
     *            servlets response.
     * @throws ServletException
     *             if the responsefor POST could not be handled.
     * @throws IOException
     *             if an IO error occurs.
     * 
     * @see javax.servlet.http.HttpServlet
     *      #doPost(javax.servlet.http.HttpServletRequest,
     *          javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        loadSpringConfig();
    }

    /**
     * Initialize the spring container.
     * 
     */
    private void loadSpringConfig() {
        // Initialize the spring container
        System.out.println("ConfigurationServlet.loadSpringConfig()");

        String springConfigFile = getInitParameter(INIT_SPRING_CONFIG_FILE);
        BeanFactory springContext = new ClassPathXmlApplicationContext(
                springConfigFile);

        SpringUtilities.getInstance().setBeanFactory(springContext);
    }
}
