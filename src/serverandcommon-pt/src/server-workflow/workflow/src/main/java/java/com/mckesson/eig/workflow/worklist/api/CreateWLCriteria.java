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
package com.mckesson.eig.workflow.worklist.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Shah Ghazni Nattar Shah
 * @date   Nov 29, 2007
 * @since  HECM 1.0
 *
 * This class is used to provide a list of all owning actors and actors with
 * create privilege.This wrapper is required to pass it as a parameter to fetch
 * all the worklist having tasks belongs to the owing actors and tasks created by
 * privileged actors
 *
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "CreateWLCriteria", namespace = EIGConstants.TYPE_NS_V1)
public class CreateWLCriteria
extends BasicWorklistDO {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 5248142920772260530L;

    /**
     * List of owning actors.
     */
    private Actors _owners;

    /**
     * List of created by actors.
     */
    private Actors _createdBy;

    /**
    * Empty worklists will be fetched, iff it is true else only worklist
    * having tasks will be fetched
    */
    private boolean _showEmptyWorklists;

    /**
     * Instantiates an AssignedWLCriteria.
     */
    public CreateWLCriteria() {
        super();
    }

    /**
     * Instantiates an AssignedWLCriteria with the owning actors and
     * the assigned actors.
     */
    public CreateWLCriteria(Actors owners, Actors createdBy, boolean showEmptyWorklist) {
        this();
        setOwners(owners);
        setCreatedBy(createdBy);
        setShowEmptyWorklists(showEmptyWorklist);
    }

    /**
     * This method is used to retrieve the owning actors.
     * @return owners
     */
    public Actors getOwners() {
        return _owners;
    }

    /**
     * This method is used to set the owning actors.
     * @param owners
     */
    @XmlElement(name = "owners", type = Actors.class)
    public void setOwners(Actors owners) {
        this._owners = owners;
    }

    /**
     * This method is used to get the actor who created the task
     * @return createdBy
     */
    public Actors getCreatedBy() {
        return _createdBy;
    }

    /**
    *  This method is used to set the actor who created the task
    * @param createdBy
    */
    @XmlElement(name = "createdBy", type = Actors.class)
    public void setCreatedBy(Actors createdBy) {
        this._createdBy = createdBy;
    }

    /**
    * Sets the showEmptyWorklists
    *
    * @return _showEmptyWorklists
    */
    public boolean getShowEmptyWorklists() {
        return _showEmptyWorklists;
    }

    /**
    * Gets the showEmptyWorklists
    *
    * @param showEmptyWorklists
    */
    @XmlElement(name = "showEmptyWorklists")
    public void setShowEmptyWorklists(boolean showEmptyWorklists) {
        this._showEmptyWorklists = showEmptyWorklists;
    }
}
