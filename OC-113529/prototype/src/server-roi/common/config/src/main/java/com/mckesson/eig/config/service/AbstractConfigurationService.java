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

package com.mckesson.eig.config.service;

import com.mckesson.eig.config.audit.ConfigurationAuditManager;
import com.mckesson.eig.config.dao.ConfigurationDAO;
import com.mckesson.eig.utility.util.SpringUtilities;

/**
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 *
 * It holds all the common methods which have to be used in the
 * business service layer of the ConfigServer component. All the implementation
 * classes of business services of  ConfigServer components extend this
 * abstract class.
 */

public abstract class AbstractConfigurationService {

    protected AbstractConfigurationService() {
        super();
    }

    /**
     * This method is used to return the corresponding local service
     * instance for the specified service name
     *
     * @param daoName
     *          Name of the local service.
     *
     * @return ConfigurationDAO
     *          dao implementation.
     */
    protected ConfigurationDAO getDAO(String daoName) {
        return (ConfigurationDAO) SpringUtilities.getInstance().getBeanFactory().getBean(daoName);
    }

    /**
     * This method is used to return the corresponding audit manager
     * instance for the specified service name
     *
     * @param managerName
     *          Name of the manager.
     *
     * @return ConfigurationAuditManager
     *          audit manager implementation.
     */
    protected ConfigurationAuditManager getAuditManager(String managerName) {
        return (ConfigurationAuditManager) SpringUtilities.getInstance().
                                                             getBeanFactory().getBean(managerName);
    }
}
