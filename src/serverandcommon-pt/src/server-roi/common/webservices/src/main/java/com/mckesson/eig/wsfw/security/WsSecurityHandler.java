package com.mckesson.eig.wsfw.security;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.ws.axis.security.WSDoAllReceiver;
import org.apache.ws.security.WSConstants;
import org.w3c.dom.NodeList;

import com.mckesson.eig.utility.exception.ApplicationException;
import com.mckesson.eig.utility.exception.ClientErrorCodes;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.exception.UsernameTokenException;
import com.mckesson.eig.wsfw.session.WsSession;
import com.mckesson.eig.wsfw.transaction.TransactionSignatureManager;

/**
 * This handler class is called by Axis and WSS4J to allow us to validate a
 * username & password that are passed in the SOAP Header according to the
 * WS-Security standard.
 * 
 * This class extends the WSDoAllReceiver from axis and overrides the invoke
 * method in WSDoAllReceiver. This is done to capture all the errors and throw
 * back a user defined error in case of authentication failure.
 */
public class WsSecurityHandler extends WSDoAllReceiver {

    private static final OCLogger LOG = new OCLogger(WsSecurityHandler.class);

    /**
     * Instantiates the <code>FaultHandler</code> class.
     */
    private final FaultHandler _faultHandler = new FaultHandler();

    /**
     * This method overrides the invoke method in WSDoAllReceiver
     * 
     * @param msgContext
     *            message context.
     * @throws AxisFault
     */
    public void invoke(MessageContext msgContext) throws AxisFault {
        try {
            TransactionSignatureManager.addTransactionSignatureOnceForHandlers(msgContext);
            initializeSession(msgContext);
            String timestamp = getUsernameTokenTimestamp(msgContext);
            WsSession.setSessionData(WsSession.MESSAGE_TIMESTAMP, timestamp);
            LOG.debug("UsernameToken UTZ = " + timestamp);
            super.invoke(msgContext);
        } catch (ApplicationException e) {
            throw getFaultHandler().createFault(msgContext, e);
        } catch (Throwable t) {
            ApplicationException e = wrapServerError(t);
            throw getFaultHandler().createFault(msgContext, e);
        }
    }
    
    protected FaultHandler getFaultHandler() {
        return _faultHandler;
    }

    private void initializeSession(MessageContext context) {
        HttpServletRequest request = (HttpServletRequest) context
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        if (request == null) {
            WsSession.initializeSession();
        } else {
            WsSession.initializeSession(request.getSession());
        }
    }

    private String getUsernameTokenTimestamp(MessageContext context)
            throws AxisFault, SOAPException {

        String timeStamp = null;

        try {
            SOAPHeader header = context.getCurrentMessage().getSOAPEnvelope()
                    .getHeader();
            NodeList nodeList = header.getChildNodes();
            for (int ii = 0, count = nodeList.getLength(); ii < count; ii++) {

                SOAPHeaderElement elemLevel1 = (SOAPHeaderElement) nodeList
                        .item(ii);
                if (elemLevel1.getName().equals(WSConstants.WSSE_LN)) {

                    NodeList secondNodeList = elemLevel1.getChildNodes();
                    for (int jj = 0; jj < secondNodeList.getLength(); jj++) {

                        MessageElement elemLevel2 = (MessageElement) secondNodeList
                                .item(jj);
                        if (elemLevel2.getName().equals(WSConstants.USERNAME_TOKEN_LN) 
                            || (elemLevel2.getName().equals(WSConstants.TIMESTAMP_TOKEN_LN))) {

                            NodeList thirdNodeList = elemLevel2.getChildNodes();
                            for (int kk = 0; kk < thirdNodeList.getLength(); kk++) {
                                MessageElement elemLevel3 = (MessageElement) thirdNodeList
                                        .item(kk);
                                if (elemLevel3.getName().equals(
                                        WSConstants.CREATED_LN)) {
                                    timeStamp = elemLevel3.getValue();
                                    return timeStamp;
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            throw new UsernameTokenException(
                    "Unable to extract Created timestamp from WSSE header", ex,
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        }

        if (timeStamp == null) {
            throw new UsernameTokenException(
                    "WSSE header Created timestamp was missing.",
                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
        }

        return timeStamp;
    }
    
    /**
     * This generates an ApplicationExeption based on the caught
     * throwable exception seen.
     * 
     * @param thr
     *            Throwable exception.
     * @throws AxisFault
     */    
    ApplicationException wrapServerError(Throwable thr) {
        ApplicationException ex = null;
        // is it a special one to look for?
        String errorCode = getFaultHandler().panFaultForReturnableErrorCode(thr);
        if (errorCode != null) {
            ex = new ApplicationException(thr, errorCode);
        } else {
            // not a special one, just generate a default 
            ex = new ApplicationException(
                    "Authentication failed.", thr);
        }
        return ex;
    }
}
