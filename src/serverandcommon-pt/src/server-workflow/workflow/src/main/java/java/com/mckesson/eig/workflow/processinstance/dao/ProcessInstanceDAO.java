/*
 * Copyright 2009 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.workflow.processinstance.dao;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;

/**
 * DAO Class for all ProcessInstance related operations.
 *
 * @author McKesson
 * @date   March 20, 2009
 * @since  HECM 2.0; March 20, 2009
 */
public class ProcessInstanceDAO extends AbstractProcessInstanceDAO {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final Log LOG = LogFactory.getLogger(ProcessInstanceDAO.class);

    /**
     * This method is used to create process instance history record.
     *
     * @param processInstanceHistory
     */
    public void createProcessInstanceHistory(final ProcessInstanceHistory processInstanceHistory) {
        LOG.debug("enter ProcessInstanceDao::createProcessInstanceHistory()");

         getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s) {
                            s.save(processInstanceHistory);
                        return s;
                    }
                });

        LOG.debug("leave ProcessInstanceDao::createProcessInstanceHistory()");
    }
}
