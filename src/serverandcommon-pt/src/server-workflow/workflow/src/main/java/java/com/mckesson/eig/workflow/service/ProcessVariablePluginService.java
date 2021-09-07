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
package com.mckesson.eig.workflow.service;

import java.util.List;

import com.mckesson.eig.workflow.api.ProcessVariable;

/**
 * Interface for implementing process variable lookups from various systems
 * through data types.
 *
 * @author eo837ew
 *
 */
public interface ProcessVariablePluginService {

    /**
     * Interface for implementing process variable lookups from various systems
     * through data types.
     *
     * @param variableList
     * @return
     */
    List <ProcessVariable> getProcessVariableValues(List <ProcessVariable> variableList);
}
