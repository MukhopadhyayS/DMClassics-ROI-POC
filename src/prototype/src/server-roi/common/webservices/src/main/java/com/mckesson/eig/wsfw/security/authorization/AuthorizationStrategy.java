package com.mckesson.eig.wsfw.security.authorization;

import java.util.List;

public interface AuthorizationStrategy {
    boolean authorize(String serviceName, String operationName, List< ? > params);
}
