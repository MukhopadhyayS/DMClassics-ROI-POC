package com.mckesson.eig.roi.webservice.util.rest.security;

public final class SecurityConstants {

	private SecurityConstants() {
	}

	public static final String KEY_HTTP_REQUEST = "HTTP.REQUEST";

	public static final String TOKEN = "Token";

	public static final String AUTHORIZATION = "Authorization";

	public static final String AUTHORIZATION_DELIMETER = ":";

	public static final int AUTHORIZATION_MAX_ARGS = 5;

	public static final String USER_SESSION = "UserSession";

	public static final String KEY_HTTP_RESPONSE = "HTTP.RESPONSE";

	public static final String SET_COOKIE = "Set-Cookie";

	public static final String KEY_HTTP_REQUEST_URI = "org.apache.cxf.request.uri";

	public static final String METHOD_CALL_VALIDATE_PIN = "validatePin";

	public static final String USER_AGENT = "user-agent";

	public static final String HOST = "host";

	public static final String DEVICE_TYPE = "device-type";

	public static final String DEVICE_ID = "device-id";

	public static final String CLIENT_TYPE = "client-type";

	public static final String CLIENT_VERSION = "protocol";

	public static final String CLIENT_LOCALE = "locale";

    public static final String PD_ENCRYPTED = "pwdEncrypted";

	public static final String SERVER_TIME = "ServerTime";

    public static final String SECURITY_TOKEN = "SecurityToken";

    public static final String MODULE = "module";

}
