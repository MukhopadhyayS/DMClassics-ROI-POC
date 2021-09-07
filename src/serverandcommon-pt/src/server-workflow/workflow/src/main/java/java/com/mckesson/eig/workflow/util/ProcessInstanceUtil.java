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

package com.mckesson.eig.workflow.util;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.Utilities.ServiceNames;
import com.mckesson.eig.workflow.processinstance.api.ProcessInstanceHistory;
import com.mckesson.eig.workflow.processinstance.dao.ProcessInstanceDAO;


public final class ProcessInstanceUtil {

    private ProcessInstanceUtil() {

    }

    private static ProcessInstanceDAO _piDao = (ProcessInstanceDAO)
         SpringUtilities.getInstance().getBeanFactory().getBean(ServiceNames.PROCESS_INSTANCE_DAO);

    /**
     * Logs process instance history in the database.
     *
     * @param processInstanceHistory
     */
    public static void createProcessInstanceHistory(ProcessInstanceHistory processInstanceHistory) {
        /**
         * Log process instance history to tables in WLIST.
         *
         * When deleting a process instance, process instance history should be cleaned out as well.
         *
         * Should be in work flow util as it could be used from action handlers and
         * process instance engine.
         */

        _piDao.createProcessInstanceHistory(processInstanceHistory);
    }
}
