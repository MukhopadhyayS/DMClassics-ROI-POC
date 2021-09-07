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
package com.mckesson.eig.workflow.dao;

import com.mckesson.eig.workflow.api.Actor;

/**
 * @author Pranav Amarasekaran
 * @date   Sep 20, 2007
 * @since  HECM 1.0; Sep 20, 2007
 *
 * This interface declares the local service methods that are common to the
 * workflow component.
 */
public interface WorkflowDAO {

    /**
     * This method can be used to get the internal id of the specified in
     * the work flow system. The specified actor can be any external application
     * like HECM, HPF, ...
     *
     * @param actor
     *          Actor with appID, entityType and entityID.
     *
     * @return actor
     *          Actor containing the identifier and three attributes.
     */
    Actor loadActor(Actor actor);
}
