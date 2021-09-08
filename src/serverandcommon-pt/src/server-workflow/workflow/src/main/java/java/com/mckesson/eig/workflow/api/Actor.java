/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.workflow.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.worklist.api.WorklistEC;
import com.mckesson.eig.workflow.worklist.api.WorklistException;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0
 *
 * Represents the external actors who will interact with the Workflow component.
 * This is the only link between the identity management system and workflow.
 * An instance of any entity of the identity management system, aka application,
 * can be uniquely represented using this.The identify management system is
 * abstracted out of Workflow component - since each application, which uses
 * Workflow, will have their own way of managing identities. Like HECM might
 * have User, User Groups, Domain etc., as part of their identity system.
 * But HPF may not follow the same and they might have something like
 * Department.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Actor", namespace = EIGConstants.TYPE_NS_V1)
@XmlRootElement(name = "actor")
public class Actor
extends BasicWorkflowDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -4991734685817076605L;

    /**
     * Primary key of the actor entity.
     */
    private long _actorID;

    /**
     * To store application identifier.
     * Ex: 1 for HECM, 2 for HPF, ...
     */
    private int _appID;

    /**
     * To hold the entity type within the application.
     * Ex: 1 for User, 2 for UserGroup, 3 for Domain
     */
    private int _entityType;

    /**
     * To hold the application level unique identifier of the entity.
     */
    private long _entityID;

    /**
     * To hold the version attribute of the entity.
     */
    private int _version;

    //Move this to Entity type Model
    public static final int DOMAIN_ENTITY_TYPE  = 1;
    public static final int GROUP_ENTITY_TYPE   = 2;
    public static final int USER_ENTITY_TYPE    = 3;

    /**
     * This constructor instantiates an Actor.
     */
    public Actor() {
        super();
    }

    /**
     * This constructor instantiates an Actor with the specified appID,
     * entityType and entityID.
     */
    public Actor(int appID, int entityType, long entityID) {

        this();
        _appID = appID;
        _entityType = entityType;
        _entityID   = entityID;
    }

    /**
     * This constructor instantiates an Actor with the specified appID,
     *
     * @param actorInfo
     *           actorID holds the appID, entityType, entityID as String
     */
    public Actor(String actorInfo) {

        this();
        if (actorInfo != null) {

            String[] actor = actorInfo.split("\\.");
            if (actor.length > 2) {
                _appID = Integer.parseInt(actor[0]);
                _entityType = Integer.parseInt(actor[1]);
                _entityID = Long.parseLong(actor[2]);
            }
        }
    }

    /**
     * This method is used to retrieve the Actor ID.
     * @return actor ID
     */
    public final long getActorID() {
        return _actorID;
    }

    /**
     * This method is used to set the Actor ID.
     * @param actorID
     */
    @XmlElement(name = "actorID")
    public void setActorID(long actorID) {
        _actorID = actorID;
    }

    /**
     * This method is used to retrieve the App ID.
     * @return appID
     */
    public int getAppID() {
        return _appID;
    }

    /**
     * This method is used to set the App ID.
     * @param appID
     */
    @XmlElement(name = "appID")
    public void setAppID(int appID) {
        _appID = appID;
    }

    /**
     * This method is used to retrieve the Entity ID.
     * @return entityID
     */
    public long getEntityID() {
        return _entityID;
    }

    /**
     * This method is used to set the Entity ID.
     * @param entityID
     */
    @XmlElement(name = "entityID")
    public void setEntityID(long entityID) {
        _entityID = entityID;
    }

    /**
     * This method is used to retrieve the Entity Type.
     * @return entityType
     */
    public int getEntityType() {
        return _entityType;
    }

    /**
     * This method is used to set the Entity Type.
     * @param entityType
     */
    @XmlElement(name = "entityType")
    public void setEntityType(int entityType) {
        _entityType = entityType;
    }

    /**
     * This method is used to retrieve the version.
     * @return
     */
    public int getVersion() {
        return _version;
    }

    /**
     * This method is used to set the Version.
     * @param version
     */
    @XmlElement(name = "version")
    public void setVersion(int version) {
        _version = version;
    }

    /**
     * This method verifies if a passed actor is null or not.
     * @param owner
     */
    public void validateActor() {

        String errorMsg = StringUtilities.EMPTYSTRING;
        if (_appID < 1) {
            errorMsg = WorklistEC.MSG_INVALID_APP_ID;
        } else if (_entityID < 1) {
            errorMsg = WorklistEC.MSG_INVALID_ENTITY_ID;
        } else if (DOMAIN_ENTITY_TYPE != _entityType
                && GROUP_ENTITY_TYPE  != _entityType
                && USER_ENTITY_TYPE   != _entityType) {
            errorMsg = WorklistEC.MSG_INVALID_ENTITY_TYPE;
        }

        if (StringUtilities.hasContent(errorMsg)) {
            WorklistException we = new WorklistException(WorkflowEC.MSG_INVALID_ACTOR,
                                                         WorkflowEC.EC_INVALID_ACTOR);
            we.setExtendedCode(errorMsg);
            throw we;
        }
    }

    /**
     * This method returns the id representation of the actor object. The id
     * representation of actor is denoted as appID.entityType,entityID and is
     * unique throughout the workflow component.
     *
     * @return actor.toString()
     */
    public String id() {
        return toString();
    }

    @Override
    public String toString() {
        return _appID + "." + _entityType + "." + _entityID;
    }

    @Override
    public boolean equals(Object o) {

        try {

            return ((_actorID > 0)
                    && (_actorID == ((Actor) o)._actorID));

        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id().hashCode();
    }
}
