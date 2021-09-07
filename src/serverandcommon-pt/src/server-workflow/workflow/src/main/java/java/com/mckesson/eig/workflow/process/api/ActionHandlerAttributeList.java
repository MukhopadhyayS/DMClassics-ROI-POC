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
package com.mckesson.eig.workflow.process.api;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author McKesson Corporation
 * @date   January 30, 2009
 * @since  HECM 2.0
 *
 * Data structure to provide a list of ActionHandlerAttributes instances. This wrapper is
 * required, instead of passing a plain list, to include any business
 * methods as part of data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ActionHandlerAttributeList", namespace = EIGConstants.TYPE_NS_V1)
public class ActionHandlerAttributeList implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -6091436767259818680L;

    /**
     * Holds the list of actionhandler attribute instances
     */
    private List<ActionHandlerAttribute> _actionHandlerAttributeList;

    private long _size;

    /**
     * This constructor instantiates a new instance
     */
    public ActionHandlerAttributeList() {
        _actionHandlerAttributeList = new ArrayList<ActionHandlerAttribute>();
    }

    /**
     * This constructor instantiates this wrapper with the list of ActionHandlerAttribute.
     */
    public ActionHandlerAttributeList(List<ActionHandlerAttribute> actionHandlerAttributeList) {
        setActionHandlerAttributeList(actionHandlerAttributeList);
    }

    /**
     * This method is used to retrieve the Action Handler Attribute list from this wrapper.
     * @return _actionHandlerAttributeList
     */
    public List<ActionHandlerAttribute> getActionHandlerAttributeList() {
        return _actionHandlerAttributeList;
    }

    /**
     * This method is used to set the Action handler attribute list
     * @param ActionHandlerAttributeList
     */
    @XmlElement(name = "actionHandlerAttributeList", type = ActionHandlerAttribute.class)
    public void setActionHandlerAttributeList(
            List<ActionHandlerAttribute> actionHandlerAttributeList) {
        _actionHandlerAttributeList = actionHandlerAttributeList;
    }

    public long getSize() {
        return _size;
    }

    @XmlElement(name = "size")
    public void setSize(long size) {
        this._size = size;
    }
    
    public String toString() {
        StringBuffer theList = new StringBuffer();
        theList.append("ActionHandlerAttributeList["); 
        if (_actionHandlerAttributeList != null) {
            int size = _actionHandlerAttributeList.size();
            for (int i = 0; i < size; i++) {
                theList.append(" " + _actionHandlerAttributeList.get(i).toString() + " ");
            }
        }
        theList.append("]");
        return theList.toString();
   }
}
