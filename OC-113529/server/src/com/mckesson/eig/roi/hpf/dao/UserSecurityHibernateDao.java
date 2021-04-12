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

package com.mckesson.eig.roi.hpf.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.hpf.model.UserFacility;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.utils.SqlEncoderAdvanced;

/**
 * @author OFS
 * @date May 26, 2009
 * @since HPF 13.1 [ROI]; Jun 17, 2008
 */
public class UserSecurityHibernateDao extends HibernateDaoSupport {

    private static final OCLogger LOG = new OCLogger(UserSecurityHibernateDao.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static Object[] _params;
    private static final SqlEncoderAdvanced ENCODER_ADVANCED = new SqlEncoderAdvanced();

    public UserSecurityHibernateDao() {
        super();
    }

    /**
     * This method will retrieve details of currently logged-in User
     * 
     * @param loginId
     * @return user details
     */
    public User retrieveUser(final String loginId) {

        final String logSM = "retrieveUser(String)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + loginId);
        }
        User user = (User) getHibernateTemplate().execute(new HibernateCallback() {

                    String cleanLoginId = ENCODER_ADVANCED.encodeForSql(loginId);
                    public Object doInHibernate(Session s) {
                        return s.createQuery("select u from User u where u.loginId ='" + cleanLoginId + "'").uniqueResult();
                    }

                });
        if (user == null) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: User Name = " + user.getDisplayName());
        }
        return user;
    }

    /**
     * This method will retrieve all security rights related with the user
     * 
     * @param userId
     *            user instanceId
     * @return list of associated security rights
     */
    public List<UserSecurity> getSecurityRight(final Integer userId) {

        final String logSM = "getSecurityRight(Integer)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: UserId = " + userId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<UserSecurity> us = (List<UserSecurity>) getHibernateTemplate()
                .execute(new HibernateCallback() {

                    public Object doInHibernate(Session s) {

                        return s.createQuery(
                                "select us from UserSecurity us where us.userId = ?0"
                                        + " and us.facility = ?1")
                                .setParameter(0, userId)
                                .setParameter(1, UserSecurity.ENTERPRISE)
                                .getResultList();

                    }

                });
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No. of Records = " + us.size());
        }
        return us;
    }

    /**
     * This method will retrieve all security rights related with the user
     * 
     * @param userId
     *            user instanceId
     * @return list of associated security rights
     */
    public List<UserFacility> getUserFacility(final Integer userId) {

        final String logSM = "getSecurityRight(Integer)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: UserId = " + userId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<UserFacility> us = (List<UserFacility>) getHibernateTemplate()
                .execute(new HibernateCallback() {

                    public Object doInHibernate(Session s) {

                        return s.createQuery(
                                "select us from UserFacility us where us.userId = ?")
                                .setParameter(0, userId).list();
                    }

                });
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No. of Records = " + us.size());
        }
        return us;
    }

    /**
     * This method will retrieve EPN state i.e enabled/disabled and EPN Prefix
     * string
     * 
     * @return sysParams will hold EPN state and EPN prefix
     */
    public Object[] getSysParams() {

        final String logSM = "getSysParams()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        if (_params == null) {

            _params = (Object[]) getHibernateTemplate()
                    .execute(new HibernateCallback() {

                        public Object doInHibernate(Session session) {

                            final String query = "SELECT EMPI_Enabled, EMPI_Prefix FROM SYSPARMS";
                            return session.createSQLQuery(query).uniqueResult();
                        }
                    });
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: EpnEnabled = " + _params[0]
                    + " EpnPrefix = " + _params[1]);
        }
        return _params;
    }
    
    
    //Support OCSecurity Internal Security Model
    public DataSource getDataSource() {
        
        SessionFactory sessionFactory = this.getSessionFactory();
        DataSource source = SessionFactoryUtils.getDataSource(sessionFactory);
       
        return source;
    }      
}
