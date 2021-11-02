///*
// * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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
//import java.util.HashMap;
//import java.util.Iterator;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.xml.namespace.QName;
//
//import com.mckesson.eig.wsfw.session.CxfWsSession;
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.message.Message;
//import org.apache.ws.security.WSConstants;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import com.mckesson.dm.core.common.logging.OCLogger;
//
///**
// * @author N.Shah Ghazni
// * @date   Dec 13, 2008
// */
//public final class CXFUtil {
//
//    private static final OCLogger LOG = new OCLogger(CXFUtil.class);
//
//    private static final String KEY_HTTP_REQUEST = "HTTP.REQUEST";
//    private static final String SECURITY_NAMESPACE_URI =
//        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
//    private static final String SECURITY_LOCALPART = "Security";
//    private static final String SECURITY_PREFIX = "";
//
//    private CXFUtil() {
//    }
//
//    /**
//     * Initialize the <code>WsSession</code> and also add the transaction signature
//     *
//     * @param message Instance of <code>Message</code>
//     */
//    public static void prepareRequest(Message message) {
//
//        CxfWsSession.initializeSession(getHttpSession(message));
//        TransactionSignatureManager.addTransactionSignatureOnceForHandlers(message);
//    }
//
//    public static void setHeaderValuesInSession(Message message) {
//
//        CxfWsSession.setSessionData(CxfWsSession.MESSAGE_TIMESTAMP, getTimeStamp((SoapMessage) message));
//        CxfWsSession.setSessionData(CxfWsSession.TXN_ID, getEigSoapHeader(message,  CxfWsSession.TXN_ID));
//        CxfWsSession.setSessionData(CxfWsSession.APP_ID, getEigSoapHeader(message,  CxfWsSession.APP_ID));
//        CxfWsSession.setSessionData(CxfWsSession.SECURITY_HEADER_MAP,
//                                getSecurityMap((SoapMessage) message));
//    }
//
//    /**
//     * Returns <code>HttpServlet</code> for this context.
//     *
//     * @param context
//     *            The Message Context.
//     * @return HttpServlet
//     */
//    public static HttpSession getHttpSession(Message message) {
//        return getHttpServletRequest(message).getSession();
//    }
//
//    private static String getTimeStamp(SoapMessage message) {
//
//        Header secHeader = null;
//        for (Iterator<Header> i = message.getHeaders().iterator(); i.hasNext();) {
//            Header header = i.next();
//            if (WSConstants.WSSE_LN.equals(header.getName().getLocalPart())) {
//                secHeader = header;
//                break;
//            }
//        }
//
//        if (secHeader == null) {
//            return null;
//        }
//
//        NodeList nodeList = ((Node) secHeader.getObject()).getChildNodes();
//        for (int jj = 0; jj < nodeList.getLength(); jj++) {
//
//            Element elemLevel2 = (Element) nodeList.item(jj);
//            if (elemLevel2.getLocalName().equals(WSConstants.TIMESTAMP_TOKEN_LN)) {
//
//                NodeList secondNodeList = elemLevel2.getChildNodes();
//                for (int kk = 0; kk < secondNodeList.getLength(); kk++) {
//
//                    Element elemLevel3 = (Element) secondNodeList.item(kk);
//                    if (elemLevel3.getLocalName().equals(WSConstants.CREATED_LN)) {
//                        return elemLevel3.getFirstChild().getTextContent();
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    public static HashMap<String, String> getSecurityMap(SoapMessage message) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        QName headerName = new QName(SECURITY_NAMESPACE_URI, SECURITY_LOCALPART, SECURITY_PREFIX);
//        Header header = message.getHeader(headerName);
//        if (header != null) {
//            Node securityNode = (Node) header.getObject();
//            fillMap(securityNode, map);
//        }
//        return map;
//    }
//
//    private static void fillMap(Node node, HashMap<String, String> map) {
//        if (node != null && node.hasChildNodes()) {
//            NodeList children = node.getChildNodes();
//            for (int i = 0; i < children.getLength(); i++) {
//                Node childNode = children.item(i);
//                if (childNode != null) {
//                    Node childFirstChild = childNode.getFirstChild();
//                    if (childNode.getNodeType() == Node.TEXT_NODE) {
//                        map.put(childNode.getPrefix() + ":" + childNode.getLocalName(),
//                                childNode.getNodeValue());
//                    } else if (childFirstChild != null && childFirstChild.getNodeType()
//                            == Node.TEXT_NODE) {
//                        map.put(childNode.getPrefix() + ":"   + childNode.getLocalName(),
//                                childFirstChild.getNodeValue());
//                    } else {
//                        fillMap(childNode, map);
//                    }
//                }
//            }
//        }
//    }
//
//    public static String getEigSoapHeader(Message msg, String headerLocalName) {
//
//        try {
//
//            if (msg instanceof SoapMessage) {
//
//                SoapMessage message = (SoapMessage) msg;
//                QName headerName = new QName(EIGConstants.TYPE_NS_V1, headerLocalName, "eig");
//                Header header = message.getHeader(headerName);
//
//                if (header != null) {
//
//                    Node node = ((Node) header.getObject()).getFirstChild();
//                    if ((node != null) && (Node.TEXT_NODE == node.getNodeType())) {
//                        return node.getNodeValue();
//                    }
//                }
//            }
//        } catch (Exception e) {
//
//            LOG.debug(e.getLocalizedMessage());
//            // We can just swallow the exception and return null - if the header is
//            // not present.
//        }
//        return null;
//    }
//
//    public static HttpServletRequest getHttpServletRequest(Message message) {
//        return (HttpServletRequest) message.get(KEY_HTTP_REQUEST);
//    }
//}
