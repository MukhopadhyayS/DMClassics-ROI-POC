package com.mckesson.eig.wsfw.security.authorization;

import org.apache.axis.AxisFault;
import org.apache.axis.Handler;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.handlers.BasicHandler;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.metric.TimedMetric;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.wsfw.axis.FaultHandler;
import com.mckesson.eig.wsfw.model.authorization.AuthorizationException;
import com.mckesson.eig.wsfw.transaction.TransactionSignatureManager;

/**
 * This handler class is called by Axis
 * validate if uthe user has access the webservice
 *
 * This class extends the BasicHandler from axis
 */

public class AuthorizationHandler extends BasicHandler {
    private static final String ACCESS_TO_SERVICE_DENIED = "Access to service denied.";
    private static final String INVALID_SERVICE_NAME = "Service Name is invalid";
    private static final String INVALID_OPERATION_NAME = "Operation Name is invalid";
    private static final String BEAN_ID_AUTHORIZATION_STRATEGY = "AuthorizationStrategy";
    /**
     * Gets the logger for this class.
     */
    private static final Log LOG = LogFactory
            .getLogger(AuthorizationHandler.class);
    /**
     * Gets the beanfactory that is set as a part of initialization from
     * webcontext.
     */
    private static final BeanFactory BEAN_FACTORY = SpringUtilities
            .getInstance().getBeanFactory();

    /**
     * Instantiates the <code>FaultHandler</code> class.
     */
    private final FaultHandler _faultHandler = new FaultHandler();

    /**
     * This method overrides the invoke method from base class to call into our
     * authorization implementation
     *
     * @param msgContext
     *            message context.
     * @throws AxisFault
     */
    public void invoke(MessageContext msgContext) throws AxisFault {
        LOG.debug("**** Enter AuthorizationHandler ****");
        TimedMetric tm = TimedMetric.start();
        TransactionSignatureManager.addTransactionSignatureOnceForHandlers(msgContext);

        /*
         * Find the Service by name the user is attempting to access
         */
        String serviceName = getWebServiceName(msgContext);
        if (StringUtilities.isEmpty(serviceName)) {
            LOG.debug(INVALID_SERVICE_NAME);
            throw createAxisFault(msgContext, INVALID_SERVICE_NAME);
        }

        /*
         * Find the Operation on the webservice being called
         */
        String operationName = getOperationName(msgContext);
        if (StringUtilities.isEmpty(operationName)) {
            LOG.debug(INVALID_OPERATION_NAME);
            //Should throw exception when OperationDesc in MessageContext is null?
            //throw createAxisFault(msgContext, INVALID_OPERATION_NAME);
        }
        /*
         * Create the bean for SecurityAuthorization - Spring config defines
         * "AuthorizationStrategy"
         */
        AuthorizationStrategy authorizationStrategy = getAuthorizationServiceProvider();

        /*
         * Permit or Deny the user access to the service
         */
        if (!authorizationStrategy.authorize(serviceName, operationName, null)) {
            tm.logMetric("Authorize Deny Web Service Operation: " + serviceName
                    + '.' + operationName + "()");
            throw createAxisFault(msgContext, ACCESS_TO_SERVICE_DENIED);
        }
        tm.logMetric("Authorize Allow Web Service Operation: " + serviceName
                + '.' + operationName + "()");

        LOG.debug("**** Leave AuthorizationHandler ****");
    }

    protected AuthorizationStrategy getAuthorizationServiceProvider() {
        return (AuthorizationStrategy) BEAN_FACTORY
                .getBean(BEAN_ID_AUTHORIZATION_STRATEGY);
    }

    private AxisFault createAxisFault(MessageContext msgContext, String message) {
        AuthorizationException e = new AuthorizationException(message);
        AxisFault fault = _faultHandler.createFault(msgContext, e);
        return fault;
    }

    private String getOperationName(MessageContext msgContext) {
        OperationDesc operation = msgContext.getOperation();
        if (operation == null) {
            return StringUtilities.EMPTYSTRING;
        }
        String operationName = operation.getName();
        LOG.debug("*** operationName :" + operationName);
        return operationName;
    }

    private String getWebServiceName(MessageContext msgContext) {
        Handler service = msgContext.getService();
        if (service == null) {
            return StringUtilities.EMPTYSTRING;
        }
        String serviceName = service.getName();
        LOG.debug("Service Name : " + serviceName);
        return serviceName;
    }
}

