//package com.mckesson.eig.roi.authorization;
//
//import com.mckesson.eig.wsfw.session.CxfWsSession;
//import org.apache.wss4j.common.ext.WSSecurityException;
//import org.apache.wss4j.dom.handler.RequestData;
//import org.apache.wss4j.dom.message.token.UsernameToken;
//import org.apache.wss4j.dom.validate.Credential;
//import org.apache.wss4j.dom.validate.Validator;
//import org.springframework.beans.factory.BeanFactory;
//
//import com.mckesson.dm.core.common.logging.OCLogger;
//import com.mckesson.dm.core.common.util.StringUtilities;
//import com.mckesson.eig.iws.security.Ticket;
//import com.mckesson.eig.utility.exception.ClientErrorCodes;
//import com.mckesson.eig.utility.util.SpringUtilities;
//import com.mckesson.eig.wsfw.security.encryption.EncryptionHandler;
//
//public class WssUsernameTokenValidator implements Validator{
//
//    private static final OCLogger LOG = new OCLogger(WssUsernameTokenValidator.class);
//
//
//    @Override
//    public Credential validate(Credential credential, RequestData data)
//            throws WSSecurityException {
//        UsernameToken usernameToken = credential.getUsernametoken();
//        handlePasswords(usernameToken);
//        return credential;
//    }
//
//
//    private void handlePasswords(UsernameToken ut) {
//        String userId = ut.getName();
//        String password = ut.getPassword();
//        validateHeader(userId, password);
//        String decryptedPassword = validateEncryption(userId, password);
//        authenticate(userId, decryptedPassword);
//    }
//
//
//    private void validateHeader(String userName, String password) {
//        LOG.debug("Validating the WSE Security Header");
//        if (StringUtilities.isEmpty(userName)
//                || StringUtilities.isEmpty(password)) {
//            throw new UsernameTokenException(
//                    "Username and/or password is NULL!!",
//                    ClientErrorCodes.SECURITY_TOKEN_MISSING_INFORMATION);
//        }
//    }
//
//
//    private String validateEncryption(String userName, String password) {
//        String decryptedPassword = null;
//        try {
//            decryptedPassword = validatePasswordAsConfigured(userName, password);
//        } catch (Exception ex) {
//            LOG.debug("Unable to decrypt with configured PasswordEncryptionStrategy context, "
//                    + "trying clear text. " + ex.getMessage());
//            decryptedPassword = validatePasswordAsClear(userName, password);
//        }
//
//        return decryptedPassword;
//    }
//
//
//    private String validatePasswordAsConfigured(String userName,
//            String password) {
//        String decryptedPassword = null;
//        EncryptionHandler handler = EncryptionHandler.getInstance();
//        decryptedPassword = handler.decryptText(userName, password,
//                (String) CxfWsSession.getSessionData(CxfWsSession.MESSAGE_TIMESTAMP));
//
//        return decryptedPassword;
//    }
//
//    private String validatePasswordAsClear(String userName, String password) {
//        return password;
//    }
//
//
//
//    /**
//     * General service call processing valids the ticket and authentication user
//     * and password if the ticket is missing or invalid.
//     *
//     * @param userId
//     *            String
//     * @param password
//     *            String
//     */
//    private void authenticate(String userName, String password) {
//        LOG.debug("authentication request processing.");
//        BeanFactory beanFactory = SpringUtilities.getInstance().getBeanFactory();
//        CxfWsSession.setSessionData(CxfWsSession.PD, password);
//        if (!isTicketValid((String) CxfWsSession.getSessionData(CxfWsSession.TICKET),
//                userName)) {
//            AuthenticationStrategy strategy = (AuthenticationStrategy)
//                    beanFactory.getBean("AuthenticationStrategy");
//            AuthenticatedResult result = strategy.authenticate(userName,
//                    password);
//            if (!result.isAuthenticated()) {
//                throw new NotAuthenticatedException(
//                        "Authentication Failed.  User account is not "
//                                + "authenticated (ex: locked, expired, etc)",
//                        ClientErrorCodes.SYSTEM_COULD_NOT_LOG_YOU_ON);
//            }
//        }
//    }
//
//    /**
//     * Validate the ticket that is stored in the session.
//     *
//     * @param ticket
//     *            String
//     * @param userName
//     *            String
//     */
//    private boolean isTicketValid(String ticket, String userName) {
//        try {
//            return Ticket.isValid(ticket, userName);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//}
