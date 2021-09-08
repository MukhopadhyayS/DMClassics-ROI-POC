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

package com.mckesson.eig.workflow.Utilities;

import com.mckesson.eig.workflow.engine.ProcessEngine;
import com.mckesson.eig.workflow.engine.service.ProcessEngineImpl;
import com.mckesson.eig.workflow.process.dao.ProcessDAO;
import com.mckesson.eig.workflow.process.dao.ProcessListDAO;
import com.mckesson.eig.workflow.process.service.ProcessServiceImpl;
import com.mckesson.eig.workflow.processinstance.dao.ProcessInstanceDAO;

/**
 * @author Senthil Paramasivam
 * Define constants for the service names
 *
 */
public final class ServiceNames {

    /**
     *
     */
    public static final String PROCESS_ENGINE = ProcessEngine.class.getName();
    public static final String PROCESS_DAO = ProcessDAO.class.getName();
    public static final String PROCESSLIST_DAO = ProcessListDAO.class.getName();
    public static final String PROCESS_SERVICE = ProcessServiceImpl.class.getName();
    public static final String PROCESS_INSTANCE_DAO = ProcessInstanceDAO.class.getName();
    public static final String PROCESS_ENGINE_IMPL = ProcessEngineImpl.class.getName();
    public static final String HIBERNATE_SESSION_FACTORY = "hibernateSessionFactory";


    private ServiceNames() {

    }

}
