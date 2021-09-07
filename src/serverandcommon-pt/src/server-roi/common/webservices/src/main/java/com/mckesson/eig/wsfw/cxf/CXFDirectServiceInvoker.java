/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries. 
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
import java.util.List;

import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.Factory;

import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author sahuly
 * @date   Dec 22, 2008
 * 
 *  Provides a method for enabling xml objects residing in the jms queue.
 */
public class CXFDirectServiceInvoker extends BusinessServiceInvoker {
    
    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(BusinessServiceInvoker.class);

    public CXFDirectServiceInvoker(Factory factory) {
        super(factory);
    }

    public CXFDirectServiceInvoker(Object bean) {
        super(bean);
    }
    
    /**
     * 
     * @see com.mckesson.eig.wsfw.cxf.BusinessServiceInvoker
     *          #invoke(org.apache.cxf.message.Exchange, 
     *                  java.lang.Object, 
     *                  java.lang.reflect.Method, 
     *                  java.util.List)
     */
    @Override
    protected Object invoke(Exchange exchange, 
                            Object serviceObject,
                            Method method, 
                            List<Object> params) {

        long start = System.currentTimeMillis();
        TimedMetric tm = TimedMetric.start();
        TransactionSignatureManager.addTransactionSignatureOnceForHandlers(exchange.getInMessage());

        logInvocation(LOG, serviceObject, method);
        Object result = super.execute(exchange, serviceObject, method, params);
        logCallTime(start);

        return result;        
    }
}
