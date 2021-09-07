package com.mckesson.eig.alert.mpf.security.service;

import javax.xml.soap.SOAPException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.transport.http.HTTPConstants;

import com.mckesson.eig.ConfigurationPortType;
import com.mckesson.eig.ConfigurationServiceLocator;
import com.mckesson.eig.wsfw.model.authentication.AuthenticatedResult;
import com.mckesson.eig.wsfw.security.AuthenticationStrategy;
import com.mckesson.eig.wsfw.session.WsSession;

public class MPFAuthenticationStrategy 
implements AuthenticationStrategy {
	
    private String _authenticationUrl;

	@Override
	public AuthenticatedResult authenticate(String userId, String password) {

		AuthenticatedResult result = new AuthenticatedResult();
		result.setState(AuthenticatedResult.AUTHENTICATED);
        if (!isAuthenticatedWithHPF(userId, password)) {

            result.setState(AuthenticatedResult.AUTHENTICATION_FAILED);
            return result;
        }

        return result;
    }

    private boolean isAuthenticatedWithHPF(String username, String password) {

        try {

            ConfigurationPortType portType = getConfigurationPortType(WsSession.getSessionId());
            portType.getConfiguration(username, password);
            return true;
        } catch (Exception e) {
            System.out.println("FAILED");
            return false;
        }
    }

    private ConfigurationPortType getConfigurationPortType(String sessionId)
    throws Exception {

        ConfigurationServiceLocator sl = new ConfigurationServiceLocator();
        sl.setconfigurationEndpointAddress(getAuthenticationUrl());

        ConfigurationPortType portType = sl.getconfiguration();

        Stub stub = (Stub) portType;

        //For Axis to maintain session
        stub.setMaintainSession(true);
        stub._setProperty(HTTPConstants.HEADER_COOKIE, "JSESSIONID=" + sessionId);

        // This trackSession property is used to track session for HPF Server with respect any
        // client application.
        stub.setHeader(createHeaderElement("urn:eig.mckesson.com", "trackSession", "true"));

        return portType;
    }

    /**
     * Creates a new header element
     *
     * @param name
     *          Name of the header element
     *
     * @param value
     *          Value of the header element
     *
     * @return Instance of new <code>SecurityHeaderElement</code>
     *
     * @throws SOAPException
     */
    private static SOAPHeaderElement createHeaderElement(String namespace,
                                                         String name,
                                                         String value)
    throws SOAPException {

        SOAPHeaderElement elem = new SOAPHeaderElement(namespace, name);
        elem.addTextNode(value);
        return elem;
    }

    /**
     *
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#login(java.lang.String,
     * java.lang.String)
     */
    public AuthenticatedResult login(String userId, String password) {
        return authenticate(userId, password);
    }

    /**
     *
     * @see com.mckesson.eig.wsfw.security.AuthenticationStrategy#authenticate(java.lang.String)
     */
    public void authenticate(String userName) {
    }

	public String getAuthenticationUrl() {
		return _authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		_authenticationUrl = authenticationUrl;
	}

}
