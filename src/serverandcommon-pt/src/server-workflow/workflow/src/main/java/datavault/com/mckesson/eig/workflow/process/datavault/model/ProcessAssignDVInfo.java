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
package com.mckesson.eig.workflow.process.datavault.model;

/**
 * @author OFS
 *
 * @date Apr 2, 2009
 * @since HECM 1.0.3; Apr 2, 2009
 */
public class ProcessAssignDVInfo extends ProcessDVInfo {

    private int _actorId;
    private int _entityType;
    private String _name;
    private String _permission;
    private String _actorName;

    /**
     * @return actorId
     */
    public int getActorId() {
        return _actorId;
    }

    /**
     * @param actorId
     */
    public void setActorId(int actorId) {
        this._actorId = actorId;
    }


    /**
     * @return entityType
     */
    public int getEntityType() {
        return _entityType;
    }

    /**
     * @param entityType
     */
    public void setEntityType(int entityType) {
        this._entityType = entityType;
    }


    /**
     * @return permission
     */
    public String getPermission() {
        return _permission;
    }

    /**
     * @param permission
     */
    public void setPermission(String permission) {
        this._permission = permission;
    }

    /**
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this._name = name;
    }

    /**
     * @return actorName
     */
    public String getActorName() {
        return _actorName;
    }

    /**
     * @param actorName
     */
    public void setActorName(String actorName) {
        this._actorName = actorName;
    }
}
