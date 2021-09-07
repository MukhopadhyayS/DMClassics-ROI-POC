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
package com.mckesson.eig.workflow.dao;

import org.jbpm.JbpmConfiguration;
import org.jbpm.persistence.db.DbPersistenceServiceFactory;
import org.jbpm.svc.Services;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * This is the jbpm scheduler which is configured and it keeps running as a low priority thread
 * for every five seconds interval.
 *
 * @author Pranav Amarasekaran
 * @date   Jan 5, 2008
 * @since  HECM 1.0; Jan 5, 2008
 */
public class TaskScheduler
extends AbstractWorkflowDAO {

    /**
     * Creates an instance of task scheduler.
     */
    public TaskScheduler() {
        super();
    }

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(TaskScheduler.class);

    /**
     * This is the method which is scheduled at a particular interval of time as configured in the
     * class level.
     */
    public void execute() {

        LOG.debug("Scheduler execution >>Start ");
        JbpmConfiguration jc = JbpmConfiguration.getInstance();
        ((DbPersistenceServiceFactory) jc.getServiceFactory(Services.SERVICENAME_PERSISTENCE))
        .setSessionFactory(getSessionFactory());
        jc.startJobExecutor();
        LOG.debug("Scheduler execution <<End ");
    }
}
