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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.VariableList;

/**
 * @author sahuly
 * @date   Feb 13, 2009
 * @since  HECM 1.0; Feb 13, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessInstance")
public class ProcessInstance {
    
    //public ProcessInstanceList m_ProcessInstanceList;
    //private ContentList contents;
    private String _description;
    private long _domainId;
    private long _id;
    private String _name;
    private long _processId;
    private VariableList _variables;
    //public ContentList m_ContentList;
    //public VariableList m_VariableList;

    public String getDescription() {
        return _description;
    }

    @XmlElement(name = "description")
    public void setDescription(String description) {
        this._description = description;
    }

    public long getDomainId() {
        return _domainId;
    }

    @XmlElement(name = "domainID")
    public void setDomainId(long id) {
        _domainId = id;
    }

    public long getId() {
        return _id;
    }

    @XmlElement(name = "id")
    public void setId(long id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    @XmlElement(name = "name")
    public void setName(String name) {
        this._name = name;
    }

    public long getProcessId() {
        return _processId;
    }

    @XmlElement(name = "processId")
    public void setProcessId(long id) {
        _processId = id;
    }

    public VariableList getVariables() {
        return _variables;
    }

    @XmlElement(name = "variables", type = VariableList.class)
    public void setVariables(VariableList variables) {
        this._variables = variables;
    }
}
