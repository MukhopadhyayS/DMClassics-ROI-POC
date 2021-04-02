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

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.NetworkUtils;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;

/**
 * Handles the FaultCode and records the cause and message of the exceptions
 * occured while the application is in progress.
 * 
 */
public class FaultHandler {

    /**
     * NameSpace as represented in the XML.
     */
    public static final String EIG_NAMESPACE = "urn:eig.mckesson.com";

    /**
     * Prefix as represented in the XML.
     * 
     */
    public static final String EIG_PREFIX = "eig";

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_EXCEPTION = new QName(EIG_NAMESPACE,
            "exception", EIG_PREFIX);

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_MESSAGE = new QName(EIG_NAMESPACE,
            "message", EIG_PREFIX);

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_SERVERNAME = new QName(EIG_NAMESPACE,
            "servername", EIG_PREFIX);

    /**
     * Qualified Names representation for errorCode tag.
     */
    private static final QName QNAME_ERRORCODE = new QName(EIG_NAMESPACE,
            "errorcode", EIG_PREFIX);

    /**
     * Qualified Names representation for errorCode tag.
     */
    private static final QName QNAME_ERRORCODE_EXT = new QName(EIG_NAMESPACE,
            "errorcodeext", EIG_PREFIX);

    /**
     * Map for recording the cause and message.
     */
    private static final Map SENDER_EXCEPTIONS;

    static {
        Map m = new HashMap();
        // Keep the strings (published) constant, even if the exception name
        // (private) changes.
        addSenderException(m, UsernameTokenException.class,
                "UsernameTokenException");
        SENDER_EXCEPTIONS = Collections.unmodifiableMap(m);
    }

    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory.getLogger(FaultHandler.class);

    /**
     * Sole Constructor.
     */
    public FaultHandler() {
    }

    /**
     * Make an AxisFault based on a passed Exception.Further it sets a fault
     * code string that is turned into a qname and add the details.
     * 
     * @param mc
     *            Message Context.
     * @param t
     *            Throwable
     * @return AxisFault after adding the details of the fault.
     */
    public AxisFault createFault(MessageContext mc, Throwable t) {
        Throwable target = unwrap(t);
        AxisFault f;
        if (target instanceof Exception) {
            // The fault is logged later, which also prints the nested
            // exception's stack trace.
            f = AxisFault.makeFault((Exception) target);
        } else {
            // Can't nest Error subclasses in the fault, so log it.
            LOG.error(target);
            f = new AxisFault();
        }
        f.setFaultCodeAsString(getFaultCodeAsString(mc, target));
        f.setFaultString(deriveFaultString(target));
        addDetails(mc, target, f);
        return f;
    }

    /**
     * It returns the published name of the occured exception.
     * 
     * @param t
     *            throwable(cause).
     * @return Published name.
     */
    protected String deriveFaultString(Throwable t) {
        String published = getPublishedName(t);
        if (published != null) {
            return published;
        }
        return ObjectUtilities.getUnqualifiedClassName(t);
    }

    /**
     * It returns the fault code as String.
     * 
     * @param messageContext
     *            MEssageContext.
     * @param t
     *            Throwable(cause).
     * 
     * @return fault code.
     * 
     * <p>
     * <a
     * href="http://www.ws-i.org/Profiles/BasicProfile-1.0-2004-04-16.html#refinement16443080">
     * http://www.ws-i.org/Profiles/BasicProfile-1.0-2004-04-16.html#refinement16443080
     * </a>
     * </p>
     * 
     * <p>
     * <a href="http://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383510">
     * http://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383510 </a>
     * </p>
     * 
     * <p>
     * <a href="http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#soapfault">
     * http://www.w3.org/TR/2003/REC-soap12-part1-20030624/#soapfault </a>
     * </p>
     */
    protected String getFaultCodeAsString(MessageContext messageContext,
            Throwable t) {
        SOAPConstants constants = messageContext.getSOAPConstants();
        if (SOAPConstants.SOAP12_CONSTANTS.equals(constants)) {
            if (isSenderException(t)) {
                return "Sender";
            }
            return "Receiver";
        }
        if (isSenderException(t)) {
            return "Client";
        }
        return "Server";
    }

    /**
     * Clears all fault subcodes and all fault details.Further it create an
     * element of the given qname and add it to the details.
     * 
     * @param mc
     *            MessageContext.
     * @param t
     *            Throwable(cause).
     * @param f
     *            AxisFault.
     */
    protected void addDetails(MessageContext mc, Throwable t, AxisFault fault) {
        fault.clearFaultDetails();
        fault.clearFaultSubCodes();
        
        fault.addFaultDetail(QNAME_EXCEPTION, deriveFaultString(t));
        fault.addFaultDetail(QNAME_MESSAGE, getMessage(t));
        
        if (t instanceof ApplicationException) {
            ApplicationException appEx = (ApplicationException) t;
            addErrorcodeTag(fault, t, QNAME_ERRORCODE);
            addExtendedTag(fault, t, QNAME_ERRORCODE_EXT);

            List list = appEx.getAllNestedCauses();
            for (Iterator i = list.iterator(); i.hasNext();) {
                Throwable nested = (Throwable) i.next();
                fault.addFaultDetail(QNAME_EXCEPTION, deriveFaultString(nested));
                fault.addFaultDetail(QNAME_MESSAGE, getMessage(nested));
                addErrorcodeTag(fault, nested, QNAME_ERRORCODE);
                addExtendedTag(fault, nested, QNAME_ERRORCODE_EXT);
            }
        }
        fault.addFaultDetail(QNAME_SERVERNAME, NetworkUtils.getLocalHostname());
    }

    protected void addErrorcodeTag(AxisFault f, Throwable th, QName tag) {
        if (th instanceof ApplicationException) {
            ApplicationException appEx = (ApplicationException) th;
            f.addFaultDetail(tag, getReturnableErrorCode(appEx));
        }
    }

    protected void addExtendedTag(AxisFault f, Throwable th, QName tag) {
        if (th instanceof ApplicationException) {
            ApplicationException appEx = (ApplicationException) th;
            f.addFaultDetail(tag, appEx.getExtendedCode());
        }
    }
    
    /**
     * Returns the Exception message along with the published Name.
     * 
     * @param t
     *            Throwable(cause).
     * @return Formatted message.
     */
    protected String getMessage(Throwable t) {
        String message = t.getMessage();

        // client code ALWAYS needs a message entry
        if (StringUtilities.hasContent(message)) {
            return message;
        }
        return "Unknown error";
    }

    /**
     * Returns the thrown target exception.
     * 
     * @param t
     *            Throwable
     * @return thrown target exception.(cause of this exception.)
     */
    protected Throwable unwrap(Throwable t) {
        if (t instanceof InvocationTargetException) {
            return ((InvocationTargetException) t).getTargetException();
        }
        return t;
    }
    
    /**
     * Returns an error code for the client, if it finds certain 
     * keywords in the thrown error.
     * 
     * @param thr
     *            Throwable
     * @return either client error code or null if no matching conditions
     */    
    public String panFaultForReturnableErrorCode(Throwable thr) {
        String errorCode = null;
        if (thr instanceof AxisFault) {
            AxisFault fault = (AxisFault) thr;
            String errorMessage = fault.getFaultString();
            if (errorMessage != null) {
                // for now the only one to check for is the "Timestamp expired"
                // exception
                if ((errorMessage.indexOf("timestamp") != -1)
                        && (errorMessage.indexOf("expired") != -1)) {
                    errorCode = ClientErrorCodes.SOAP_MESSAGE_TIMED_OUT;
                }
            }
        }
        return errorCode;
    }

    /**
     * Validates an entity for exception.
     * 
     * @param t
     *            Throwable.
     * @return <code>true</code> if entity is found in the Map
     *         <code>false</code> otherwise.
     */
    private static boolean isSenderException(Throwable t) {
        return SENDER_EXCEPTIONS.containsKey(t.getClass().getName());
    }

    /**
     * Gets the published name of the entity from the map which throwed the
     * exception.
     * 
     * @param t
     *            Throwable.
     * @return Name of the entity which throwed the exception.
     */
    private static String getPublishedName(Throwable t) {
        return (String) SENDER_EXCEPTIONS.get(t.getClass().getName());
    }

    /**
     * Associates the name of this entity and published error message.
     * 
     * @param m
     *            Map
     * @param type
     *            UserNameTokenException.class
     * @param published
     *            String to be published.
     */
    private static void addSenderException(Map m, Class type, 
            String published) {
        m.put(type.getName(), published);
    }

    /**
     * Returns the error code for the fault. If none has been set yet, return a
     * default.
     * 
     * @param aex
     *            ApplicationException
     * @return Error code as String
     */
    private static String getReturnableErrorCode(ApplicationException aex) {
        return aex.getErrorCode() == null
                ? ClientErrorCodes.OTHER_SERVER_ERROR
                : aex.getErrorCode();
    }
}
