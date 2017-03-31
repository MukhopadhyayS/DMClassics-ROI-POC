/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
package com.mckesson.eig.roi.hpf.dao;

import java.util.List;

import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.hpf.model.UserFacility;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.test.BaseROITestCase;


/**
*
* @author OFS
* @date   June 24, 2013
* @since  HPF 16.0 [ROI]; June 24, 2013
*/
public class TestUserSecurityHibernateDao 
extends BaseROITestCase {
    
    private static UserSecurityHibernateDao _dao;
    
    @Override
    public void initializeTestData()
    throws Exception {
        _dao = (UserSecurityHibernateDao) getService(UserSecurityHibernateDao.class.getName());
    }

    @Override
    protected String getServiceURL(String serviceMethod) {
        return "";
    }
    
    public void testRetrieveUser() {
        
        User user = _dao.retrieveUser("ADMIN");
        assertEquals("ADMIN", user.getLoginId().trim());
    }
    
    public void testGetSecurityRight() {
        
        List<UserSecurity> userSecurity = _dao.getSecurityRight(1);
        assertNotNull(userSecurity);
    }
    
    public void testGetUserFacility() {
        
        List<UserFacility> userFacility = _dao.getUserFacility(1);
        assertNotNull(userFacility);
    }

    public void testGetSysParams() {
    
        Object[] sysParams = _dao.getSysParams();
        assertNotNull(sysParams);
    }
    
    public void testRetrieveUserWithInvalidUserId() {
        
        User user = _dao.retrieveUser("Invalid");
        assertNull(user);
    }

}
