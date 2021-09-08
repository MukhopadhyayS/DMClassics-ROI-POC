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

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.workflow.api.BasicWorkflowDO;

/**
 * @author sahuly
 * @date   Feb 13, 2009
 * @since  HECM 1.0; Feb 13, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessActorACLS")
public class ProcessActorACLS extends BasicWorkflowDO {
    
    private Set<ProcessActorACL> _processActorACLS;
    
    public ProcessActorACLS() {
        super();
    }
    
    public ProcessActorACLS(Set<ProcessActorACL> processActorACL) {
        setProcessActorACLS(processActorACL);
    }

    public Set<ProcessActorACL> getProcessActorACLS() {
        return _processActorACLS;
    }
    
    @XmlElement(name = "processActorACLS", type = ProcessActorACL.class)
    public void setProcessActorACLS(Set<ProcessActorACL> actorACLS) {
        _processActorACLS = (actorACLS == null) ? new HashSet<ProcessActorACL>() 
                                                        : actorACLS;
    }
}
