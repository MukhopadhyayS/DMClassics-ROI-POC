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
package com.mckesson.eig.wsfw.axis;

import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.IntHolder;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;
import org.apache.axis.MessageContext;
import org.apache.axis.constants.Scope;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeader;
import org.apache.axis.providers.java.RPCProvider;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.utils.XMLUtils;
import org.springframework.beans.factory.BeanFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.mckesson.eig.utility.io.IOUtilities;
//import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.eig.wsfw.transaction.TransactionSignatureManager;

/**
 * Implement message processing by walking over RPCElements of the envelope
 * body, invoking the appropriate methods on the service object.
 */
public class BusinessServiceHandler extends RPCProvider {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger(BusinessServiceHandler.class);

    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * Instantiates the <code>FaultHandler</code> class.
     */
    private final FaultHandler _faultHandler = new FaultHandler();

    /**
     * Constructor making a call to its parent class.
     */
    public BusinessServiceHandler() {
        super();
    }

    /**
     * Use the spring bean factory to create the business service object. The
     * spring bean is at session scope so that it can be reused across calls.
     * 
     * @param messageContext
     *            messageContext having the current element.
     * @param service
     *            Its the Handler.
     * @param className
     *            Name of the class.
     * @param scopeHolder
     *            to set its int coded value.
     * @return buisness service object for the required class.
     * @throws Exception
     *             general exception.
     * @see org.apache.axis.providers.java.JavaProvider#getServiceObject
     *      (org.apache.axis.MessageContext, org.apache.axis.Handler,
     *      java.lang.String, javax.xml.rpc.holders.IntHolder)
     */
    public Object getServiceObject(MessageContext messageContext,
            Handler service, String className, IntHolder scopeHolder)
            throws Exception {
        scopeHolder.value = Scope.SESSION.getValue();
        return BEAN_FACTORY.getBean(className.toString());
    }

    /**
     * This method encapsulates the method invocation.
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
     * 
     */
    protected Object invokeMethod(MessageContext messageContext, Method method,
            Object obj, Object[] argValues) throws Exception {
        long start = System.currentTimeMillis();
        TimedMetric tm = TimedMetric.start();
        boolean trackSession = shouldTrackSession(messageContext);
        boolean forceInvalidateSession = StringUtilities.toBooleanValue(
                (String) messageContext.getProperty("forceInvalidateSession"),
                false);
        TransactionSignatureManager.addTransactionSignatureOnceForHandlers(messageContext);
        HttpServletRequest request = getHttpServletRequest(messageContext);
        HttpSession session = request.getSession();
		/*US9117 - ROI Output - make ROI server run successfully within Tomcat cluster
		   Commented the following line to because Token object prevents session serialization */
        //Token.acquire(session);
        WsSession.initializeSession(session);

        try {
            logInvocation(obj, method);
            Object result = execute(messageContext, method, obj, argValues);
            logCallTime(start);
            return result;
        } catch (Throwable t) {
            throw getFaultHandler().createFault(messageContext, t);
        } finally {
 		/*US9117 - ROI Output - make ROI server run successfully within Tomcat cluster
		   Commented the following line to because Token object prevents session serialization */
           //Token.release(session);
            tm.logMetric("Web Service Invocation: " + obj.getClass().getName()
                    + '.' + method.getName() + "()");
            if (!trackSession || forceInvalidateSession) {
                invalidate(session);
                LOG.debug("Invalidating the HTTP Session");
            }
        }
    }

    /**
     * This method encapsulates the method invocation.
     * 
     * @param messageContext
     *            MessageContext
     * @param method
     *            the target method.
     * @param obj
     *            the target object
     * @param argValues
     *            the method arguments
     * @return result Object after invoking.
     * @throws Exception
     *             if error ocurrs during invocation.
     */
    protected Object execute(MessageContext messageContext, Method method,
            Object obj, Object[] argValues) throws Exception {
        Object result = super.invokeMethod(messageContext, method, obj,
                argValues);
        return result;
    }

    /**
     * Loads the WSDL for this service .If not able to load it generates the
     * WSDL for this service.
     * 
     * Put in the "WSDL" property of the message context as a
     * org.w3c.dom.Document.
     * 
     * @param messageContext
     *            MessageContext.
     * 
     * @throws AxisFault
     *             if an SOAP fault occurs.
     */
    public void generateWSDL(MessageContext messageContext) throws AxisFault {
        Document doc = loadWSDL(messageContext);
        if (doc == null) {
            super.generateWSDL(messageContext);
        }
    }

    /**
     * Loads the WSDL file.
     * 
     * @param messageContext
     *            messageContext.
     * @return loaded WSDL.
     */
    private Document loadWSDL(MessageContext messageContext) {
        String path = null;
        InputStream in = null;
        Document doc = null;
        try {
            path = getWSDLPath(messageContext);
            if (StringUtilities.hasContent(path)) {
                HttpServlet servlet = getHttpServlet(messageContext);
                in = servlet.getServletContext().getResourceAsStream(path);
                if (in != null) {
                    doc = XMLUtils.newDocument(in);
                    messageContext.setProperty("WSDL", doc);
                }
            }
        } catch (Throwable t) {
            LOG.error("Could not load WSDL file: " + path, t);
        } finally {
            IOUtilities.close(in);
        }
        return doc;
    }

    /**
     * Returns the WSDL path.
     * 
     * @param messageContext
     *            MessageContext.
     * @return the WSDL path.
     */
    private String getWSDLPath(MessageContext messageContext) {
        return (String) messageContext.getService().getOption("eig-wsdl");
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
    protected void logInvocation(OCLogger logger, Object target, Method method) {
        if (logger.isDebugEnabled()) {
            logger.debug("Invoking: " + target.getClass().getName() + '.'
                    + method.getName() + "()");
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
    protected void logCallTime(OCLogger logger, long start) {
        if (logger.isDebugEnabled()) {
            long duration = System.currentTimeMillis() - start;
            logger.debug("Done; call time = " + duration + " (ms)");
        }
    }

    /**
     * Returns <code>HttpServlet</code> for this context.
     * 
     * @param context
     *            The Message Context.
     * @return HttpServlet
     */
    private HttpServlet getHttpServlet(MessageContext context) {
        return (HttpServlet) context.getProperty(HTTPConstants.MC_HTTP_SERVLET);
    }

    /**
     * Returns <code>HttpServletRequest</code> for this context.
     * 
     * @param context
     *            The Message Context.
     * @return HttpServletRequest
     */
    protected HttpServletRequest getHttpServletRequest(MessageContext context) {
        return (HttpServletRequest) context
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
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
            LOG.error("Exception occurred in invalidate ",t);
        }
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
    protected boolean shouldTrackSession(MessageContext mc) {
        return ConversionUtilities.toBooleanValue(getEigSoapHeader(mc,
                "trackSession"), true);
    }

    /**
     * Returns the SOAP header ,<code>null</code> if header is not present.
     * 
     * @param messageContext
     *            MessageContext
     * @param headerLocalName
     *            local Name of the header.
     * @return Eig SOAP header.
     */
    protected String getEigSoapHeader(MessageContext messageContext,
            String headerLocalName) {
        try {
            SOAPHeader header = (SOAPHeader) messageContext.getRequestMessage()
                    .getSOAPHeader();
            if (header != null) {
                QName headerName = new QName("urn:eig.mckesson.com",
                        headerLocalName, "eig");
                MessageElement e = header.getChildElement(headerName);
                if (e != null) {
                    Node node = e.getFirstChild();
                    if (node != null && node.getNodeType() == Node.TEXT_NODE) {
                        return node.getNodeValue();
                    }
                }
            }
        } catch (Exception e) {
            LOG.info(e.getLocalizedMessage());
            // We can just swallow the exception and return null - the header is
            // not present.
        }
        return null;
    }

    /**
     * Returns this <code> faultHandler </code>.
     * 
     * @return this faultHandler.
     */
    protected FaultHandler getFaultHandler() {
        return _faultHandler;
    }
}
