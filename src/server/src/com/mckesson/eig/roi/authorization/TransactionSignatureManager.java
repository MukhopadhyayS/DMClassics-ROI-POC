//package com.mckesson.eig.roi.authorization;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.mckesson.eig.wsfw.session.CxfWsSession;
//import org.apache.cxf.message.Message;
//
//import com.mckesson.eig.utility.transaction.TransactionSignature;
//import com.mckesson.eig.utility.util.StringUtilities;
//
///**
// * @author N.Shah Ghazni
// * @date   Dec 13, 2008
// */
//public final class TransactionSignatureManager {
//
//    private static final String MESSAGE_HASHCODE = "MessageHashCode";
//
//    private TransactionSignatureManager() {
//    }
//
//    /**
//     * It puts the context value( userName,ipAddress )as identified by the key
//     * code parameter.
//     *
//     * @param mc
//     *            The Message Context.
//     */
//    public static void addTransactionSignatureOnceForHandlers(Message msg) {
//
//        int mcHashCode = msg.hashCode();
//        Integer code   = (Integer) CxfWsSession.getSessionData(MESSAGE_HASHCODE);
//
//        if ((code != null) && (code.intValue() == mcHashCode)) {
//            return;
//        }
//        CxfWsSession.setSessionData(MESSAGE_HASHCODE, new Integer(mcHashCode));
//        addTransactionSignature(msg);
//    }
//
//    private static void addTransactionSignature(Message msg) {
//
//        String ipaddress           = StringUtilities.EMPTYSTRING;
//        String username            = (String) CxfWsSession.getSessionData(CxfWsSession.USER_NAME);
//        HttpServletRequest request = CXFUtil.getHttpServletRequest(msg);
//
//        if (request != null) {
//            ipaddress = request.getRemoteAddr();
//        }
//        String transactionId = CXFUtil.getEigSoapHeader(msg, "transactionId");
//
//        CxfWsSession.setSessionData(CxfWsSession.CLIENT_IP, ipaddress);
//
//        if ((transactionId != null) && (transactionId.trim().length() > 0)) {
//            // Found the transaction id, use it in the signature
//            TransactionSignature.add(username, ipaddress, transactionId);
//        } else {
//            // Failed to parse a transaction id from the soap header.
//            // Create a signature with the transaction id automatically
//            // generated.
//            TransactionSignature.add(username, ipaddress);
//        }
//    }
//}
