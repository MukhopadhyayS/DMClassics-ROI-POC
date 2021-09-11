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

package com.mckesson.eig.workflow.engine.service;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public abstract class BaseWorkflowEngineImpl extends HibernateDaoSupport {

	private JbpmConfiguration _jc;

	/**
     * This method basically acts as a accessor method for jbpm configuration. if the configuration
     * is not loaded this method loads the configuration and sets the persistence factory that will
     * be used by the jbpm for further transactions
     *
     * @return JbpmConfiguration
     */
    protected JbpmConfiguration getJC() {

        if (_jc == null) {

            _jc = JbpmConfiguration.getInstance();
            ((DbPersistenceServiceFactory) _jc.getServiceFactory(Services.SERVICENAME_PERSISTENCE))
                  .setSessionFactory(getSessionFactory());
        }
        return _jc;
    }

    /**
     * This method creates the JbpmContext from the Jbpm  configuration.
     *
     * @return JbpmContext
     */
    public JbpmContext createJbpmContext() {
        JbpmContext jbpmContext = getJC().createJbpmContext();
        jbpmContext.setSession(getHibernateTemplate().getSessionFactory().getCurrentSession());
        return jbpmContext;
    }

    public void closeJbpmContex(JbpmContext jbpmContext) {
        if (jbpmContext != null) {
            jbpmContext.close();
        }
    }
}
