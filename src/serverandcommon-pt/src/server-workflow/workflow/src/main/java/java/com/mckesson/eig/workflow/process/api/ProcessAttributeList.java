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

/**
 * @author McKesson Corporation
 * @date   January 30, 2009
 * @since  HECM 2.0
 *
 * Data structure to provide a list of Domain instances. This wrapper is
 * required, instead of passing a plain list, to include any business
 * methods as part of data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessAttributeList")
public class ProcessAttributeList implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private  static final long serialVersionUID = -6091436767259818680L;

    /**
     * Holds the list of domain instances
     */
    private List<ProcessAttribute> _processAttributeList;

    private long _size;

    /**
     * This constructor instantiates a new instance
     */
    public ProcessAttributeList() {
        _processAttributeList = new ArrayList<ProcessAttribute>();
    }

    /**
     * This constructor instantiates this wrapper with the list of ProcessAtrribute .
     */
    public ProcessAttributeList(List<ProcessAttribute> processAttributeList) {
        setProcessAttributeList(processAttributeList);
    }

    /**
     * This method is used to retrieve the processAttribute list from this wrapper.
     * @return processAttributeList
     */
    public List<ProcessAttribute> getProcessAttributeList() {
        return _processAttributeList;
    }

    /**
     * This method is used to set the process attribute list
     * @param processAttributeList
     */
    @XmlElement(name = "processAttributeList", type = ProcessAttribute.class)
    public void setProcessAttributeList(List<ProcessAttribute> processAttributeList) {
        _processAttributeList = processAttributeList;
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
        theList.append("ProcessAttributeList["); 
        if (_processAttributeList != null) {
            int size = _processAttributeList.size();
            for (int i = 0; i < size; i++) {
                theList.append(" " + _processAttributeList.get(i).toString() + " ");
            }
        }
        theList.append("]");
        return theList.toString();
   }
}
