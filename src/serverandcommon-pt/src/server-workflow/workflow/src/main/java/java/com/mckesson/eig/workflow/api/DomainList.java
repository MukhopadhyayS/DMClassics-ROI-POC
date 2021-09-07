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
package com.mckesson.eig.workflow.api;

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
 * Data structure to provide a list of Domain instances. This wrapper is
 * required, instead of passing a plain list, to include any business
 * methods as part of data object.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DomainList", namespace = EIGConstants.TYPE_NS_V1)
public class DomainList implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = -6091436767259818680L;

    /**
     * Holds the list of domain instances
     */
    private List<Domain> _domainList;

    private long _size;

    /**
     * This constructor instantiates a new instance
     */
    public DomainList() {
        _domainList = new ArrayList<Domain>();
    }

    /**
     * This constructor instantiates this wrapper with the list of domains.
     */
    public DomainList(List<Domain> domainList) {
        setDomainList(domainList);
    }

    /**
     * This method is used to retrieve the domain list from this wrapper.
     * @return actors
     */
    public List<Domain> getDomainList() {
        return _domainList;
    }

    /**
     * This method is used to set the domain list to this wrapper
     * @param actors
     */
    @XmlElement(name = "domainList", type = Domain.class)
    public void setDomainList(List<Domain> domainList) {
        _domainList = domainList;
    }

    public long getSize() {
        return _size;
    }

    @XmlElement(name = "size")
    public void setSize(long size) {
        this._size = size;
    }
    
    public String toString() {
        StringBuffer theDomainList = new StringBuffer();
        theDomainList.append("DomainList["); 
        if (_domainList != null) {
            int size = _domainList.size();
            for (int i = 0; i < size; i++) {
                theDomainList.append(" " + _domainList.get(i).toString() + " ");
            }
        }
        theDomainList.append("]");
        return theDomainList.toString();
   }
}
