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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.eig.utility.util.ObjectUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;


/**
 * @author N.Shah Ghazni
 * @date   Dec 14, 2008
 */
public class EIGSoap11FaultOutInterceptor extends AbstractSoapInterceptor {



    public static final String LOCALHOST = "127.0.0.1";

    /**
     * Prefix as represented in the XML.
     *
     */
    public static final String EIG_PREFIX = "eig";

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_EXCEPTION = new QName(EIGConstants.TYPE_NS_V1,
                                                           "exception",
                                                           EIG_PREFIX);

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_MESSAGE = new QName(EIGConstants.TYPE_NS_V1,
                                                         "message",
                                                         EIG_PREFIX);

    /**
     * Qualified Names representation.
     */
    private static final QName QNAME_SERVERNAME =
                                new QName(EIGConstants.TYPE_NS_V1, "servername", EIG_PREFIX);

    /**
     * Qualified Names representation for errorCode tag.
     */
    private static final QName QNAME_ERRORCODE = new QName(EIGConstants.TYPE_NS_V1,
                                                           "errorcode",
                                                           EIG_PREFIX);

    /**
     * Qualified Names representation for errorCode tag.
     */
    private static final QName QNAME_ERRORCODE_EXT = new QName(EIGConstants.TYPE_NS_V1,
                                                               "errorcodeext",
                                                               EIG_PREFIX);

    public EIGSoap11FaultOutInterceptor() {

        super(Phase.MARSHAL);
        getBefore().add(Soap11FaultOutInterceptor.class.getName());
    }

    public void handleMessage(SoapMessage message) {
        addFaultDetails(message, (Fault) message.getContent(Exception.class));
    }

    public static Fault addFaultDetails(SoapMessage message, Fault fault) {

        Throwable target = fault.getCause();

        fault.setFaultCode(getFaultCodeAsQName(message.getVersion(), target.getCause()));
        fault.setMessage(deriveFaultString(target));
        addDetails(target, fault);

        return fault;
    }

    private static void addDetails(Throwable t, Fault f) {

        f.setDetail(null);
        Element detail = f.getOrCreateDetail();

        addDetail(detail, QNAME_EXCEPTION, deriveFaultString(t));
        addDetail(detail, QNAME_MESSAGE, getMessage(t));

        if (t instanceof ApplicationException) {

            ApplicationException appEx = (ApplicationException) t;
            addDetail(detail, QNAME_ERRORCODE, getReturnableErrorCode(appEx));
            addDetail(detail, QNAME_ERRORCODE_EXT, appEx.getExtendedCode());

            List< ? > list = appEx.getAllNestedCauses();
            Throwable nested;
            for (Iterator< ? > i = list.iterator(); i.hasNext();) {

                nested = (Throwable) i.next();
                addDetail(detail, QNAME_EXCEPTION,     deriveFaultString(nested));
                addDetail(detail, QNAME_MESSAGE,       getMessage(nested));
                if (nested instanceof ApplicationException) {
                    ApplicationException ae = (ApplicationException) nested;
                    addDetail(detail, QNAME_ERRORCODE, getReturnableErrorCode(ae));
                    addDetail(detail, QNAME_ERRORCODE_EXT, ae.getExtendedCode());
                }
            }
        }

        addDetail(detail, QNAME_SERVERNAME, getHostName());
    }

    /**
     * Helper method used to return the host address.
     * @return
     */
    private static String getHostName() {

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return LOCALHOST;
        }
    }

    private static void addDetail(Element detail, QName name, String value) {

        detail.setTextContent(value);
    }

    /**
     * It returns the published name of the occured exception.
     *
     * @param t
     *            throwable(cause).
     * @return Published name.
     */
    private static String deriveFaultString(Throwable t) {
        return ObjectUtilities.getUnqualifiedClassName(t);
    }

    /**
     * It returns the fault code as String.
     *
     * @param messageContext
     *            MEssageContext.
     * @param throwable
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
    private static QName getFaultCodeAsQName(SoapVersion soapVersion, Throwable throwable) {

        String faultCode = null;
        if (soapVersion instanceof Soap11) {
            faultCode = (throwable instanceof UsernameTokenException) ? "Client" : "Server";
        } else {
            faultCode = (throwable instanceof UsernameTokenException) ? "Sender" : "Receiver";
        }
        return new QName(soapVersion.getEnvelope().getNamespaceURI(), faultCode);
    }

    /**
     * Returns the Exception message along with the published Name.
     *
     * @param t
     *            Throwable(cause).
     * @return Formatted message.
     */
    private static String getMessage(Throwable t) {

        String message = t.getMessage();
        return (StringUtilities.hasContent(message)) ? message : "Unknown error";
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
