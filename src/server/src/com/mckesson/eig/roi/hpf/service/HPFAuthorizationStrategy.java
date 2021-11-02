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

import java.util.List;

import com.mckesson.eig.wsfw.security.authorization.AuthorizationStrategy;
import com.mckesson.eig.wsfw.session.CxfWsSession;
import org.springframework.beans.factory.BeanFactory;


import com.mckesson.eig.roi.hpf.dao.UserSecurityHibernateDao;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author Ganeshram
 * @date   Jun 20, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class HPFAuthorizationStrategy implements AuthorizationStrategy{

    private static final BeanFactory BEAN_FACTORY = SpringUtilities.getInstance().getBeanFactory();
    private UserSecurityHibernateDao _dao;
    private WebServiceSecurityManager _mgr;

    public HPFAuthorizationStrategy() {

        super();
        _dao = (UserSecurityHibernateDao)
                BEAN_FACTORY.getBean(UserSecurityHibernateDao.class.getName());

        _mgr = new WebServiceSecurityManager();
    }

	public boolean authorize(String serviceName, String operationName, List< ? > list) {

	    User user = (User)
	                CxfWsSession.getSessionData(HPFAuthenticationStrategy.AUTHENTICATED_ROI_USER);

	    if (!CxfWsSession.isUserSecurityRightsLoaded()) {

	        List<UserSecurity> rights = _dao.getSecurityRight(user.getInstanceId());
	        CxfWsSession.setUserSecurityRights(User.transferRightsToMap(rights));
	    }

	    return _mgr.canAccessWebservice(serviceName, operationName);
	}
}

