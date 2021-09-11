/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.util;

import com.mckesson.eig.utility.transaction.TransactionId;
import com.mckesson.eig.wsfw.EIGConstants;
import com.mckesson.eig.wsfw.session.WsSession;

public final class SOAPWrapper {

    //Always writes version 1 of name space. When name spacing is fixed to wsdl's name space,
    //this must be modified.
    private static final String SOAP_ENVELOPE_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        + "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
        + " xmlns:ns=\"" + EIGConstants.TYPE_NS_V1 + "\">";

    private static final String SOAP_HEADER_BEGIN = "<soap:Header>"
            + "<TransactionId>";

    private static final String SOAP_HEADER_APP = "</TransactionId><applicationId>";

    private static final String SOAP_HEADER_END = "</applicationId></soap:Header><soap:Body>";

    private static final String SOAP_ENVELOPE_END = "</soap:Body></soap:Envelope>";

    private SOAPWrapper() {
    }

    /**
     * This utility method constructs SoapEnvelope around plain XML message.
     * It expects transactionid to be set in LogContext.
     *
     * @param msg
     * @return
     * @throws Exception
     */
    public static String buildSoapEnvelope(String msg) {
        // TODO: Add MDC context data feature to OCLogger.
        TransactionId id = new TransactionId("MockTransactionID");
                // ((TransactionId) LogContext.get("transactionid"));

        String transID = (id == null ? "" : id.getValue());

        String appID = (String) WsSession.getSessionData(WsSession.APP_ID);

        appID = (appID == null ? "" : appID);

        return new StringBuffer().append(SOAP_ENVELOPE_BEGIN).append(
                SOAP_HEADER_BEGIN).append(transID).append(SOAP_HEADER_APP)
                .append(appID).append(SOAP_HEADER_END).append(msg).append(
                        SOAP_ENVELOPE_END).toString();
    }
}
