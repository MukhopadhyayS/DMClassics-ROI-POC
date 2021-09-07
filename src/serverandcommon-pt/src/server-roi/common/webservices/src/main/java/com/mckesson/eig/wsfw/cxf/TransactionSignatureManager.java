package com.mckesson.eig.wsfw.cxf;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;

import com.mckesson.eig.utility.transaction.TransactionSignature;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.session.WsSession;

/**
 * @author N.Shah Ghazni
 * @date   Dec 13, 2008
 */
public final class TransactionSignatureManager {

    private static final String MESSAGE_HASHCODE = "MessageHashCode";

    private TransactionSignatureManager() {
    }

    /**
     * It puts the context value( userName,ipAddress )as identified by the key
     * code parameter.
     *
     * @param mc
     *            The Message Context.
     */
    public static void addTransactionSignatureOnceForHandlers(Message msg) {

        int mcHashCode = msg.hashCode();
        Integer code   = (Integer) WsSession.getSessionData(MESSAGE_HASHCODE);

        if ((code != null) && (code.intValue() == mcHashCode)) {
            return;
        }
        WsSession.setSessionData(MESSAGE_HASHCODE, new Integer(mcHashCode));
        addTransactionSignature(msg);
    }

    private static void addTransactionSignature(Message msg) {

        String ipaddress           = StringUtilities.EMPTYSTRING;
        String username            = (String) WsSession.getSessionData(WsSession.USER_NAME);
        HttpServletRequest request = CXFUtil.getHttpServletRequest(msg);

        if (request != null) {
            ipaddress = request.getRemoteAddr();
        }
        String transactionId = CXFUtil.getEigSoapHeader(msg, "transactionId");

        WsSession.setSessionData(WsSession.CLIENT_IP, ipaddress);

        if ((transactionId != null) && (transactionId.trim().length() > 0)) {
            // Found the transaction id, use it in the signature
            TransactionSignature.add(username, ipaddress, transactionId);
        } else {
            // Failed to parse a transaction id from the soap header.
            // Create a signature with the transaction id automatically
            // generated.
            TransactionSignature.add(username, ipaddress);
        }
    }
}
