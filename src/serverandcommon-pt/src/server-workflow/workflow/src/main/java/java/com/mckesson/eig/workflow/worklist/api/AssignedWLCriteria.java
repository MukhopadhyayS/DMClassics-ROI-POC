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
package com.mckesson.eig.workflow.worklist.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.Actors;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Dec 6, 2007
 * @since  HECM 1.0
 *
 * Data structure to provide a list of all owning actors and assigned actors.
 * This wrapper is required to pass it as a parameter to fetch all the assigned
 * worklist for these actors.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "AssignedWLCriteria", namespace = EIGConstants.TYPE_NS_V1)
public class AssignedWLCriteria
extends BasicWorklistDO {

    /**
     * List of owning actors.
     */
    private Actors _owners;

    /**
     * List of assigned actors.
     */
    private Actors _assignedTo;

    /**
    * Empty worklists will be fetched, iff it is true else only worklist
    * having tasks will be fetched
    */
    private boolean _showEmptyWorklists;

    /**
     * Owner worklists will be fetched, iff it is true else all the worklist fetched.
     */
    private boolean _showOnlyOwnerWL;

    /**
     * Instantiates an AssignedWLCriteria.
     */
    public AssignedWLCriteria() {
        super();
    }

    /**
     * Instantiates an AssignedWLCriteria with the owning actors ,assigned actors
     * and show empty worklists.
     */
    public AssignedWLCriteria(Actors owners,
                              Actors assignedTo,
                              boolean showEmptyWorklists) {
        this();
        setOwners(owners);
        setAssignedTo(assignedTo);
        setShowEmptyWorklists(showEmptyWorklists);
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
        _owners = owners;
    }

    /**
     * This method is used to retrieve the assigned actors.
     * @return assignedActors
     */
    public Actors getAssignedTo() {
        return _assignedTo;
    }

    /**
     * This method is used to set the assigned actors.
     * @param assignedActors
     */
    @XmlElement(name = "assignedTo", type = Actors.class)
    public void setAssignedTo(Actors assignedTo) {
        _assignedTo = assignedTo;
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

    /**
     * Gets the ShowOnlyOwnerWL
     * 
     * @return _showOnlyOwnerWL
     */
    public boolean getShowOnlyOwnerWL() {
		return _showOnlyOwnerWL;
	}

    /**
     * Sets the ShowOnlyOwnerWL
     * 
     * @param showOnlyOwnerWL
     */
    @XmlElement(name = "showOnlyOwnerWL")
	public void setShowOnlyOwnerWL(boolean showOnlyOwnerWL) {
		this._showOnlyOwnerWL = showOnlyOwnerWL;
	}
}
