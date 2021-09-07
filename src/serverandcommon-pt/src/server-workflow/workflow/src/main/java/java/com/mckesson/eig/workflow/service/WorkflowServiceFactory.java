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
package com.mckesson.eig.workflow.service;

import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.workflow.dao.WorkflowDAO;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * This class is used to provide the corresponding instance of
 * WorklistLocalService to the business service.
 */
public final class WorkflowServiceFactory {

    private WorkflowServiceFactory() { }

    /**
     * This method is used to return the corresponding local service instance
     * for the specified service name by looking up the spring application
     * config file.
     *
     * @param serviceName
     *          Name of the service.
     *
     * @return WorkflowLocalService
     *          Local service implementation.
     */
    public static WorkflowDAO getWorkflowDAO(String serviceName) {
        return (WorkflowDAO) SpringUtilities.getInstance().getBeanFactory().getBean(serviceName);
    }

    /**
     * This method is used to return the corresponding audit manager instance
     * for the specified service name by looking up the spring application
     * config file.
     *
     * @param serviceName
     *          Name of the service.
     *
     * @return WorkflowAuditManger
     *          audit manager implementation.
     */
    public static WorkflowAuditManger getWorkflowAuditManager(String serviceName) {

        return (WorkflowAuditManger) SpringUtilities.getInstance()
                                                    .getBeanFactory().getBean(serviceName);
    }
}
