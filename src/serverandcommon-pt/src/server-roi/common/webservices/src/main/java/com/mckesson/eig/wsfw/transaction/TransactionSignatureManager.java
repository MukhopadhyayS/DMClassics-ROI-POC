package com.mckesson.eig.wsfw.transaction;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeader;
import org.apache.axis.transport.http.HTTPConstants;
import org.w3c.dom.Node;

import com.mckesson.eig.utility.transaction.TransactionSignature;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.wsfw.session.WsSession;

public final class TransactionSignatureManager {
    
    private static final OCLogger LOG = new OCLogger(TransactionSignatureManager.class);
    private static final String MESSAGE_CONTEXT_HASHCODE = "MessageContextHashCode";
    private TransactionSignatureManager() {
    }
    
    /**
     * It puts the context value( userName,ipAddress )as identified by the key
     * code parameter.
     * 
     * @param mc
     *            The Message Context.
     */
    public static void addTransactionSignatureOnceForHandlers(MessageContext mc) {
        int mcHashCode = mc.hashCode();
        Integer code = (Integer) WsSession.getSessionData(MESSAGE_CONTEXT_HASHCODE);
        if (code != null && code.intValue() == mcHashCode) {
            return;
        }
        WsSession.setSessionData(MESSAGE_CONTEXT_HASHCODE, new Integer(mcHashCode));
        addTransactionSignature(mc);
    }

    private static void addTransactionSignature(MessageContext mc) {
        String username = (String) WsSession.getSessionData(WsSession.USER_NAME);
        String ipaddress = getHttpServletRequest(mc).getRemoteAddr();
        WsSession.setSessionData(WsSession.CLIENT_IP, ipaddress);

        String transactionId = getEigSoapHeader(mc, "transactionId");
        if (transactionId != null && transactionId.trim().length() > 0) {
            // Found the transaction id, use it in the signature
            TransactionSignature.add(username, ipaddress, transactionId);
        } else {
            // Failed to parse a transaction id from the soap header.
            // Create a signature with the transaction id automatically
            // generated.
            TransactionSignature.add(username, ipaddress);
        }
    }
    
    public static void addTransactionSignatureForDirectServiceHandler(MessageContext mc) {
        
        int mcHashCode = mc.hashCode();
        Integer code = (Integer) WsSession.getSessionData(MESSAGE_CONTEXT_HASHCODE);
        if (code != null && code.intValue() == mcHashCode) {
            return;
        }
        WsSession.setSessionData(MESSAGE_CONTEXT_HASHCODE, new Integer(mcHashCode));

        String transactionId = getEigSoapHeader(mc, "TransactionId");
        String userName = getEigSoapHeader(mc, "Username");
        String clientIP = getEigSoapHeader(mc, "ClientIP");

        if (transactionId != null && transactionId.trim().length() > 0) {
            TransactionSignature.add(userName, clientIP, transactionId);
        } else {
            TransactionSignature.add(userName, clientIP);
        }
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
    private static String getEigSoapHeader(MessageContext messageContext,
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
     * Returns <code>HttpServletRequest</code> for this context.
     * 
     * @param context
     *            The Message Context.
     * @return HttpServletRequest
     */
    private static HttpServletRequest getHttpServletRequest(MessageContext context) {
        return (HttpServletRequest) context
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
    }
}
