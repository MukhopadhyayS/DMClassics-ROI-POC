package com.mckesson.eig.wsfw.axis;

import java.lang.reflect.Method;

import org.apache.axis.MessageContext;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.wsfw.transaction.TransactionSignatureManager;

/**
 * Provides a method for enabling xml objects residing in the jms queue.
 * 
 */
public class DirectServiceHandler extends BusinessServiceHandler {
    
    /**
     * Gets the Logger for this class.
     */
    private static final Log LOG = LogFactory
            .getLogger(DirectServiceHandler.class);
   
    /**
     * constructor making a call to parent class.
     */
    public DirectServiceHandler() {
        super();
    }
    
    /**
     * Called directly from an instantiated AxisServer instead of originating
     * via an http request. This enables xml objects residing in the jms queue
     * to be deserialized and stuffed back into the axis workflow.
     * 
     * @param messageContext
     *            MessageContext
     * @param method
     *            the target method.
     * @param obj
     *            the target object
     * @param argValues
     *            the method arguments
     * @throws Exception
     *             if error ocurrs during invocation.
     * 
     * @return Object after executing.
     */
    protected Object invokeMethod(MessageContext messageContext, Method method,
            Object obj, Object[] argValues) throws Exception {
        long start = System.currentTimeMillis();
        TimedMetric tm = TimedMetric.start();
        TransactionSignatureManager.addTransactionSignatureForDirectServiceHandler(messageContext);
        try {
            logInvocation(LOG, obj, method);
            Object result = execute(messageContext, method, obj, argValues);
            logCallTime(LOG, start);
            tm.logMetric("Web Service Invocation: " + obj.getClass().getName()
                    + '.' + method.getName() + "()");
            return result;
        } catch (Throwable t) {
            throw getFaultHandler().createFault(messageContext, t);
        }
    }
}
