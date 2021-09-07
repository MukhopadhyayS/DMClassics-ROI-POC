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

import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0
 *
 * Data structure to provide a list of Actor instances. This wrapper is
 * required, instead of passing a plain list, to include any business
 * methods as part of data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Actors", namespace = EIGConstants.TYPE_NS_V1)
@XmlRootElement(name = "actors")
public class Actors
extends BasicWorkflowDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -3585570518053728876L;

    /**
     * Holds the collection of actor instances
     */
    private Set<Actor> _actors;

    private long _size;

    /**
     * This constructor instantiates a new instance
     */
    public Actors() {
        super();
    }

    /**
     * This constructor instantiates this wrapper without any actors.
     */
    public Actors(Set<Actor> actors) {
        this();
        setActors(actors);
    }

    /**
     * This method is used to retrieve the actors from this wrapper.
     * @return actors
     */
    public Set<Actor> getActors() {
        return _actors;
    }

    /**
     * This method is used to set the actors to this wrapper
     * @param actors
     */
    @XmlElement(name = "actors", type = Actor.class)
    public void setActors(Set<Actor> actors) {
        _actors = actors;
    }

    /**
     * This method returns all the actor IDs that are associated with
     * this wrapper.
     * @return actorIDs
     */
    public Long[] getActorIDs() {

        if (_actors == null) {
            return new Long[0];
        }

        int size = _actors.size();
        Long[] ids = new Long[size];
        int count = 0;
        for (Iterator i = _actors.iterator(); i.hasNext(); count++) {
            ids[count] = ((Actor) i.next()).getActorID();
        }
        return ids;
    }

    /**
     * This method returns all the actor information in the
     * concatenated format(appID.entityID.entityType) that are associated with
     * this wrapper.
     * @return actorIDs
     */
    public String[] getAllActorDetails() {

        if (_actors == null) {
            return new String[0];
        }

        int size = _actors.size();
        String[] ids = new String[size];
        int count = 0;
        for (Iterator i = _actors.iterator(); i.hasNext(); count++) {
            ids[count] = ((Actor) i.next()).toString();
        }
        return ids;
    }

    public void appendAuditComment(StringBuffer sb, String role) {

        if (_actors == null) {
            return;
        }

        sb.append(R_DELIM);

        int count = 1;
        for (Iterator i = _actors.iterator(); i.hasNext(); count++) {
            sb.append(role)
              .append(".")
              .append(count)
              .append("=")
              .append(i.next().toString())
              .append(R_DELIM);
        }
        sb.deleteCharAt(sb.length() - 1);
    }

    public long getSize() {
        return _size;
    }

    @XmlElement(name = "size")
    public void setSize(long size) {
        this._size = size;
    }

    /**
     * Method to validate whether the actors is null, the actor instances of the actors are null
     *
     */
    public void validateActors() {

    	for (Iterator<Actor> iterator = getActors().iterator(); iterator.hasNext();) {

			Actor actor = iterator.next();
			actor.validateActor();
		}
    }
}
