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
 * This class represents the Data structure to provide a list of domain instances.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DomainList")
public class DomainList {

    /**
    * Serial Version ID for this Serializable.
    */
    private static final long serialVersionUID = 1L;

    /**
    * Holds the a of actions.
    */
    private List<Domain> _domains;

    /**
    * Instantiates an task list with the list of domains.
    * @param domainList
    */
    public DomainList(List<Domain> domainList) {
         this._domains = domainList;
    }

    /**
    * Instantiates a domain list.
    */
    public DomainList() {
        this._domains = new ArrayList<Domain>();
    }

    /**
    * This method is used to get the list of domains.
    * @return domains
    */
    public List<Domain> getDomains() {
        return _domains;
    }

    /**
    * This method is used to set the list of domains.
    * @param domainList
    */
    @XmlElement(name = "domains", type = Domain.class)
    public void setDomains(List<Domain> domainList) {
        this._domains = domainList;
    }

    public int getSize() {
        return this._domains.size();
    }
}
