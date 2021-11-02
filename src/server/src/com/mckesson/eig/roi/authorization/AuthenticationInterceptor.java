///*
// * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
// * All Rights Reserved.
// *
// * Use of this material is governed by a license agreement. This material
// * contains confidential, proprietary and trade secret information of
// * McKesson Information Solutions and is protected under United States and
// * international copyright and other intellectual property laws. Use,
// * disclosure, reproduction, modification, distribution, or storage
// * in a retrieval system in any form or by any means is prohibited without
// * the prior express written permission of McKesson Information Solutions.
// */
//package com.mckesson.eig.roi.authorization;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.mckesson.eig.wsfw.session.CxfWsSession;
//import org.apache.cxf.binding.soap.SoapHeader;
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.transport.http.AbstractHTTPDestination;
//import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
//import org.apache.wss4j.dom.WSConstants;
//import org.apache.xerces.dom.ElementNSImpl;
//import org.apache.xerces.dom.TextImpl;
//
//import com.mckesson.dm.core.common.logging.OCLogger;
//import com.mckesson.eig.utility.exception.ApplicationException;
//import com.mckesson.eig.utility.exception.ClientErrorCodes;
//
///**
// *
// * @author  Srini Paduri
// * @Date    Jan 16th 2009
// * @since   HECM Release 2.0
// *
// * Overrides and implements BinarySecurityToken along with UsernameToken for security
// * based on WS-SE. Depends on WSS4J framework.
// */
//public class AuthenticationInterceptor extends WSS4JInInterceptor {
//
//    private static final OCLogger LOG = new OCLogger(AuthenticationInterceptor.class);
//
//    public AuthenticationInterceptor(Map<String,Object> properties) {
//        super(properties);
//     }
//
//    /*
//     * (non-Javadoc)
//     * @see org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor#handleMessage(org.apache.cxf.binding
//     * .soap.SoapMessage)
//     *
//     * Implements handleMessage invoked by CXF framework. It compensates for lack of ability in
//     * WSS4JInInterceptor to handle one or the other.
//     * NOTE: Since password call back handler is not invoked for BinarySecurityToken, this method
//     * may need to compensate for WSSession logic activities performed in password call back
//     * handlers.
//     */
//
//    public void handleMessage(SoapMessage msg) throws Fault{
//        try {
//            TransactionSignatureManager.addTransactionSignatureOnceForHandlers(msg);
//            initializeSession(msg);
//
//            String timestamp = getUsernameTokenTimestamp(msg);
//
//            CxfWsSession.setSessionData(CxfWsSession.MESSAGE_TIMESTAMP, timestamp);
//            LOG.debug("UsernameToken UTZ = " + timestamp);
//
//            super.handleMessage(msg);
//
//        }  catch (ApplicationException e) {
//            //throw getFaultHandler().createFault(msgContext, e);
//        } catch (Throwable t) {
//            LOG.debug("Hi......"+t.getMessage());
//            //ApplicationException e = wrapServerError(t);
//            //throw getFaultHandler().createFault(msgContext, e);
//        }
//
//    }
//
//
//    private void initializeSession(SoapMessage msg) {
//        HttpServletRequest request = (HttpServletRequest) msg.get(AbstractHTTPDestination.HTTP_REQUEST);
//        if (request == null) {
//            CxfWsSession.initializeSession();
//        } else {
//            CxfWsSession.initializeSession(request.getSession());
//        }
//    }
//
//    private String getUsernameTokenTimestamp(SoapMessage msg) {
//        String timeStamp = null;
//        try {
//            @SuppressWarnings("unchecked")
//            List<SoapHeader> headers = ( List<SoapHeader> ) msg.get("org.apache.cxf.headers.Header.list");
//            for (SoapHeader header : headers){
//                if(header.getName ().getLocalPart ().equalsIgnoreCase ( WSConstants.WSSE_LN )){
//                    ElementNSImpl securityNode = ( ElementNSImpl ) header.getObject ();
//                    ElementNSImpl elemLevel1 = ( ElementNSImpl ) securityNode.getFirstChild ().getNextSibling ();
//                    if(elemLevel1.getLocalName ().equalsIgnoreCase ( WSConstants.USERNAME_TOKEN_LN ) ||
//                            elemLevel1.getLocalName ().equalsIgnoreCase ( WSConstants.TIMESTAMP_TOKEN_LN )){
//                        while(!elemLevel1.getLocalName ().equalsIgnoreCase ( WSConstants.TIMESTAMP_TOKEN_LN )){
//                            elemLevel1 = ( ElementNSImpl ) elemLevel1.getNextSibling ().getNextSibling ();
//                        }
//                        if(elemLevel1 != null){
//                            ElementNSImpl elemLevel2 = ( ElementNSImpl ) elemLevel1.getFirstChild ().getNextSibling ();
//                            if(elemLevel2.getLocalName ().equalsIgnoreCase ( WSConstants.CREATED_LN )
//                                    || elemLevel2.getLocalName ().equalsIgnoreCase ( WSConstants.EXPIRES_LN )){
//                                while(!elemLevel2.getLocalName ().equalsIgnoreCase ( WSConstants.CREATED_LN )){
//                                    elemLevel2 = ( ElementNSImpl ) elemLevel2.getNextSibling ().getNextSibling ();
//                                }
//                                if(elemLevel2 != null){
//                                    TextImpl createdTimeStampNode = ( TextImpl ) elemLevel2.getFirstChild ();
//                                    timeStamp = createdTimeStampNode.getData ();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            throw new UsernameTokenException(
//                    "Unable to extract Created timestamp from WSSE header", ex,
//                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
//        }
//        if (timeStamp == null) {
//            throw new UsernameTokenException(
//                    "WSSE header Created timestamp was missing.",
//                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
//        }
//        return timeStamp;
//    }
//
//
//}
