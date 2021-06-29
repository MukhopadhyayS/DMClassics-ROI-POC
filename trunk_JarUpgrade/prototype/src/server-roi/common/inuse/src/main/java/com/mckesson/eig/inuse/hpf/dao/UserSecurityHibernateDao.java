/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.inuse.hpf.dao;

import java.util.List;

import com.mckesson.eig.inuse.base.dao.BaseInUseDAOImpl;
import com.mckesson.eig.inuse.hpf.model.User;
import com.mckesson.eig.inuse.model.InUseRecord;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author OFS
 * @date   Jun 15, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class UserSecurityHibernateDao
extends BaseInUseDAOImpl {

    private static final Log LOG = LogFactory.getLogger(UserSecurityHibernateDao.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    public UserSecurityHibernateDao() {
        super();
    }

    /**
     * This method will retrieve details of currently logged-in User
     * @param loginId
     * @return user details
     */
    public User retrieveUser(String loginId) {

        final String logSM = "retrieveUser(String)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + loginId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<User> list = (List<User>) getHibernateTemplate().findByNamedQuery("userByLoginId", loginId);
        if (CollectionUtilities.isEmpty(list)) {
            return null;
        }
        User user = list.get(0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: User Name = " + user.getDisplayName());
        }
        return user;
    }
}
