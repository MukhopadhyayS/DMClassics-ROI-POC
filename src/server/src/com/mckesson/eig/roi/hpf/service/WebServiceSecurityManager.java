/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.hpf.service;

import java.util.HashMap;
import java.util.List;

import com.mckesson.eig.wsfw.session.CxfWsSession;
import org.springframework.beans.factory.BeanFactory;

import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class WebServiceSecurityManager {

    private static final OCLogger LOG = new OCLogger(WebServiceSecurityManager.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final String WEB_SERVICE_AUTHORIZATION_TABLE = "WebServiceAuthorizationTable";

    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance().getBeanFactory();

    /**
     * This method validates user's security rights to access this service and operation
     *
     * @param serviceName
     * @param operationName
     * @return true if user has access to the service or no right is required
     */
    public boolean canAccessWebservice(String serviceName, String operationName) {

        String key = key(serviceName, operationName);

        List<Integer> requiredRight = getRequiredRights(key);

        if (!CollectionUtilities.hasContent(requiredRight)) {

            if (DO_DEBUG) {
                LOG.debug("Access is granted because no security right is needed for " + key);
            }
            return true;
        }
        if (DO_DEBUG) {
            LOG.debug("Right user should have : " + requiredRight);
        }
        return validateSecurity(requiredRight);
    }

    private String key(String serviceName, String operationName) {
        return serviceName + '.' + operationName;
    }

    private boolean validateSecurity(List<Integer> requiredRights) {

        @SuppressWarnings("unchecked") // generics not supported by API
        HashMap<String, HashMap<Integer, Boolean>> userRights = CxfWsSession.getUserSecurityRights();

        if (userRights == null) {
            throw new SecurityException("User security right not found.");
        }

        HashMap<Integer, Boolean> idsMap = userRights.get(UserSecurity.ENTERPRISE);
        for (Integer right : requiredRights) {
            if (!Boolean.TRUE.equals(idsMap.get(right))) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> getRequiredRights(String key) {

        WebServiceAuthorizationTable rightsLookup =
            (WebServiceAuthorizationTable) BEAN_FACTORY.getBean(WEB_SERVICE_AUTHORIZATION_TABLE);

        return (rightsLookup == null) ? null : rightsLookup.getRequiredRight(key);
    }
}
