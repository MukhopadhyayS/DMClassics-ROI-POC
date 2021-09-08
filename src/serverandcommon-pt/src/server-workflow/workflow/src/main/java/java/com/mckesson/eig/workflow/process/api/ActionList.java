/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * Information Solutions and is protected under United States and international
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.process.api;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-January-2009 9:57:00 AM
 *
 * This class represents the Data structure to provide a list of Action instances.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ActionList")
public class ActionList {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 1L;

    /**
    * Holds the a of actions.
    */
    private List<Action> _actions;

    /**
    * Instantiates an task list with the list of actions.
    * @param actionList
    */
    public ActionList(List<Action> actionList) {
         this._actions = actionList;
    }

    /**
    * Instantiates an action list.
    */
    public ActionList() {
        this._actions = new ArrayList<Action>();
    }

    /**
    * This method is used to get the list of actions.
    * @return actions
    */
    public List<Action> getActions() {
        return _actions;
    }

    /**
    * This method is used to set the list of actions.
    * @param actionList
    */
    @XmlElement(name = "actions", type = Action.class)
    public void setActions(List<Action> actionList) {
        this._actions = actionList;
    }

    public int getSize() {
        return this._actions.size();
    }
}
