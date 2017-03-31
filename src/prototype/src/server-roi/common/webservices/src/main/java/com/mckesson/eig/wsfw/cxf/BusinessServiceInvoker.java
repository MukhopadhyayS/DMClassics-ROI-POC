/*
 * Copyright 2008-2010 McKesson Corporation and/or one of its subsidiaries.
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.JAXWSMethodInvoker;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.model.BindingOperationInfo;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.eig.wsfw.Token;

/**
 *
 * @author N.Shah Ghazni
 * @date   Dec 24, 2008
 *
 * 
 * Implement message processing by walking over JAXWSMethodInvoker of the envelope
 * body, invoking the appropriate methods on the service object.
 */
public class BusinessServiceInvoker extends JAXWSMethodInvoker {

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory.getLogger(BusinessServiceInvoker.class);
    
    /**
     * Constructor making a call to its parent class.
     */
    public BusinessServiceInvoker(Factory factory) {
        super(factory);
    }
    
    /**
     * Constructor making a call to its parent class.
     */
    public BusinessServiceInvoker(Object bean) {
        super(bean);
    }

    /**
     * This method encapsulates the method invocation.
     *
     * @param exchange
     *            Exchange Instance
     * @param serviceObject
     *            the target object
     * @param method
     *            the target method.
     * @param params
     *            the method arguments
     * @throws Exception
     *             if error occurs during invocation.
     *
     * @return Object after executing.
     *
     */
    @Override
    protected Object invoke(Exchange exchange,
                            Object serviceObject,
                            Method method,
                            List<Object> params) {

        long start = System.currentTimeMillis();
        TimedMetric tm = TimedMetric.start();

        boolean trackSession           = shouldTrackSession(exchange.getInMessage());
        boolean forceInvalidateSession = canInvalidateSession(exchange);

        HttpSession session = CXFUtil.getHttpSession(exchange.getInMessage());

        Object result = null;
        try {

            logInvocation(serviceObject, method);
            result = execute(exchange, serviceObject, method, params);
            logCallTime(start);
        }  finally {

            tm.logMetric("Web Service Invocation: " + serviceObject.getClass().getName()
                                                    + '.' + method.getName() + "()");
            if (!trackSession || forceInvalidateSession) {

                invalidate(session);
                LOG.debug("HTTP Session Invalidated.");
            }
        }

        return result;
    }
    /**
     * This method encapsulates the method invocation.
     * 
     * @param exchange
     *          
     * @param serviceObject
     * 
     * @param method
     *        target method
     *          
     * @param params
     *        mehtod parameters
     *          
     * @return
     */
    protected Object execute(Exchange exchange, 
                             Object serviceObject,
                             Method method, 
                             List<Object> params) {
    	
        // Set the value of params to null, iff the service method has no parameter.
    	if (!hasParams(exchange)) {
    	    params = null;
    	}

        Object result = super.invoke(exchange, serviceObject, method, params);
        return result;
    }
    
    /**
     * Helper method used to find the service method has the parameter or not. 
     * 
     * @param exchange
     * 
     * @return true if the service method has parameter, otherwise false
     */
    private boolean hasParams(Exchange exchange) {

        BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
        Class< ? >[] paramTypes  = (((Method) bop.getOperationInfo()
                                                   .getProperty(Method.class.getName()))
                                                   .getParameterTypes());
        return paramTypes.length > 0;
    }

    private boolean canInvalidateSession(Exchange exchange) {
        return ConversionUtilities.toBooleanValue(
                            ((HashMap< ? , ? >) exchange.get(Endpoint.class.getName()))
                                                        .get("forceInvalidateSession"),
                                                              false);
    }

    /**
     * Returns <code>false</code> if the SOAPHeader is explicitly set to
     * <code>false</code> otherwise returns true.
     *
     * @param mc
     *            The Message Context.
     * @return Returns <code>true</code> if the SOAPHeader is present
     *         <code>false</code> otherwise.
     */
    protected boolean shouldTrackSession(Message msg) {
        return ConversionUtilities.toBooleanValue(
                                   CXFUtil.getEigSoapHeader(msg, "trackSession"), true);
    }

    /**
     * Logs the Class Object and its Method Executed.
     *
     * @param target
     *            Entity.
     * @param method
     *            Method which has got executed.
     */
    protected void logInvocation(Object target, Method method) {
        logInvocation(LOG, target, method);
    }

    /**
     * Logs the Class Object and its Method Executed.
     *
     * @param logger
     *            Holds the instance of Log.
     * @param target
     *            Entity.
     * @param method
     *            Method which has got executed.
     */
    protected void logInvocation(Log logger, Object target, Method method) {

        if (logger.isDebugEnabled()) {

            logger.debug("Invoking: " + target.getClass().getName() + '.'
                                      + method.getName()            + "()");
        }
    }

    /**
     * Logs the call time.(Logs the System Time in milliseconds when the method
     * is invocated.).
     *
     * @param start
     *            Time when the Method is invoked.
     */
    protected void logCallTime(long start) {
        logCallTime(LOG, start);
    }

    /**
     * Logs the Call Time if its <code>DEBUG</code> level is enabled.
     *
     * @param logger
     *            Log
     * @param start
     *            time when the method is invoked.
     */
    protected void logCallTime(Log logger, long start) {

        if (logger.isDebugEnabled()) {

            long duration = System.currentTimeMillis() - start;
            logger.debug("Done; call time = " + duration + " (ms)");
        }
    }

    /**
     * Invalidates this session and unbinds any objects bound to it.
     *
     * @param session
     *            to invalidate.
     */
    protected void invalidate(HttpSession session) {

        try {
            session.invalidate();
        } catch (IllegalStateException e) {
            LOG.debug("Session already invalidated");
        } catch (Throwable t) {
            LOG.error(t);
        }
    }
}
