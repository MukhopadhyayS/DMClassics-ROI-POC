/*
 * Copyright 2007-2008 McKesson Corporation and/or one of its subsidiaries. All
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
package com.mckesson.eig.workflow.worklist.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author Pranav Amarasekaran
 * @date   Aug 30, 2007
 * @since  HECM 1.0
 *
 * Data structure to provide a list of Worklist instances. This wrapper
 * is required, instead of returning a plain list, to include any business
 * methods as part of data object. Also this provides scope for indexing
 * the Worklist instances based on various fields.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ListWorklist", namespace = EIGConstants.TYPE_NS_V1)
public class ListWorklist
extends BasicWorklistDO {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -3039333218824299473L;

    /**
     * Holds the list of worklist.
     */
    private List<Worklist> _worklists;

    /**
     * This will create a new instance
     */
    public ListWorklist() {
        super();
    }

    /**
     * Instantiates the wrapper with the list of worklist.
     */
    public ListWorklist(List<Worklist> worklists) {
        this();
        setWorklists(worklists);
    }

    /**
     * This method is used to retrieve the worklists from the wrapper.
     * @return workliists
     */
    public List<Worklist> getWorklists() {
        return _worklists;
    }

    /**
     * This method is used to set the worklists to the wrapper.
     * @param worklists
     */
    @XmlElement(name = "worklists", type = Worklist.class)
    public void setWorklists(List<Worklist> worklists) {
        _worklists = (worklists == null) ? new ArrayList<Worklist>() : worklists;
    }
}
