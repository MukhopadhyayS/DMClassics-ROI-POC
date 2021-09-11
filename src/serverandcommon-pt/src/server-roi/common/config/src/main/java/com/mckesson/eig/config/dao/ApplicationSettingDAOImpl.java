/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.config.dao;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.eig.config.model.ApplicationSetting;
import com.mckesson.eig.config.model.ApplicationSettingList;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */

public class ApplicationSettingDAOImpl
extends AbstractConfigurationDAOImpl
implements ApplicationSettingDAO {

    /**
     * Gets the logger for this class.
     */
    private static final OCLogger LOG = new OCLogger( ApplicationSettingDAOImpl.class);

    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *
     * @see ApplicationSettingDAO#getApplicationSettings()
     */
    public ApplicationSettingList getApplicationSettings() {

        final String logSourceMethod = "getApplicationSettings()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        ApplicationSettingList applnSettingList =
                        (ApplicationSettingList) getHibernateTemplate().execute(
        new HibernateCallback() {
            public Object doInHibernate(Session s) {

                List resultList = s.createCriteria(ApplicationSetting.class).list();
                return toApplicationSettingList(resultList);
            }
        });

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return applnSettingList;
    }

    /**
     *
     * @see ApplicationSettingDAO#getApplicationSetting(long)
     */
    public ApplicationSetting getApplicationSetting(final long appId) {

        final String logSourceMethod = "getApplicationSetting()";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        ApplicationSetting applnSettingList =
            (ApplicationSetting) getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session s) {

            List resultList = s.createCriteria(ApplicationSetting.class)
                               .add(Restrictions.idEq(appId))
                               .list();
            return toApplicationSetting(resultList);
        }
        });

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return applnSettingList;
    }

    /**
     *
     * @see ApplicationSettingDAO
     *              #updateApplicationSetting(com.mckesson.eig.config.model.ApplicationSetting)
     */
    public Boolean updateApplicationSetting(final ApplicationSetting applnSetting) {

        final String logSourceMethod = "updateApplicationSetting(globalSetting)";
        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + ">>Start");
        }

        final String updateQuery = new StringBuffer()
                        .append(" UPDATE com.mckesson.eig.config.model.ApplicationSetting \n")
                        .append(" SET appName =:APP_NAME, settings =:SETTINGS,            \n ")
                        .append(" modifiedDateTime =:DATE_TIME WHERE appId = :APP_ID      \n ")
                        .toString();

        Boolean isUpdated = (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
        public Object doInHibernate(Session s) {

            return  s.createQuery(updateQuery)
                     .setParameter("APP_NAME",  applnSetting.getAppName())
                     .setParameter("SETTINGS",  applnSetting.getSettings())
                     .setTimestamp("DATE_TIME", getDate())
                     .setParameter("APP_ID",    applnSetting.getAppId())
                     .executeUpdate() > 0;
         }
        });

        if (DO_DEBUG) {
            LOG.debug(logSourceMethod + "<<End");
        }
        return isUpdated;
    }

    /**
    * Method to get the ApplicationSetting from obj and convert it to GlobalConfiguration object
    *
    * @param resultList
    *
    * @return
    */
    private ApplicationSetting toApplicationSetting(Object obj) {

        ApplicationSetting applicatinSetting = new ApplicationSetting();

        if (obj instanceof List) {

            List listObj = (List) obj;
            if (CollectionUtilities.isEmpty(listObj)) {
                return null;
            }
            obj = listObj.get(0);
        }

        if (obj instanceof ApplicationSetting) {

            ApplicationSetting appSetting = (ApplicationSetting) obj;
            applicatinSetting.setAppId(appSetting.getAppId());
            applicatinSetting.setAppName(appSetting.getAppName());
            applicatinSetting.setSettings(appSetting.getSettings());

        }
        return applicatinSetting;
    }

    /**
    * Method to get the settingxml string from the given resultList and convert it to
    * GlobalConfiguration object
    *
    * @param resultList
    *
    * @return
    */
    private ApplicationSettingList toApplicationSettingList(List list) {

        ApplicationSettingList applnSettingList = new ApplicationSettingList();
        List<ApplicationSetting> resultList = new ArrayList<ApplicationSetting>(0);
        ApplicationSetting applnSetting = null;
        for (int i = list.size(); --i >= 0;) {

            applnSetting = toApplicationSetting(list.get(i));
            resultList.add(applnSetting);
        }
        applnSettingList.setApplicationSettings(resultList);
        return applnSettingList;
    }
}
