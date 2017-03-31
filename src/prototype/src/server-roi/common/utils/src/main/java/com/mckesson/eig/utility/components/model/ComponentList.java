/*
 * Copyright 2008-2009 McKesson Corporation and/or one of its subsidiaries. 
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

package com.mckesson.eig.utility.components.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class acts as a wrapper to hold a list of component details.
 * 
 * @author Sahul Hameed Y
 * @date   Apr 7, 2008
 * @since  Utils; Apr 7, 2008
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ComponentList")
public class ComponentList {
    
    private List<ComponentInfo> _components;
    
    public ComponentList() {
    }
    
    public ComponentList(List<ComponentInfo> components) {
        _components = components;
    }

    public List<ComponentInfo> getComponents() {
        return _components;
    }

    @XmlElement(name = "components")
    public void setComponents(List<ComponentInfo> components) {
        _components = components;
    }
}
