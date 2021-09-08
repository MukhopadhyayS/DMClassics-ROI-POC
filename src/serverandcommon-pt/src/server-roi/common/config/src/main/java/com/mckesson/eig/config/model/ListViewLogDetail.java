/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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
package com.mckesson.eig.config.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * The ListViewLogDetail class models the details for the log files 
 * corresponding to the components. It holds the component name and component sequence.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ListViewLogDetail", namespace = EIGConstants.TYPE_NS_V1)
public class ListViewLogDetail {

    /**
     * Member variable of type String holding the name of the component.
     */
    private String _componentName;
    /**
     * Member variable of type long holding the sequence of the component.
     */
    private long _componentSeq;
    /**
     * This method obtains the name of the component that is set.
     *
     * @return name of the component.
     */
    public String getComponentName() {
        return _componentName;
    }
    /**
     * This method obtains the sequence of the component.
     *
     * @return sequence of the component installed.
     */
    public long getComponentSeq() {
        return _componentSeq;
    }
    /**
     * Sets the given component name.
     *
     * @param componentName
     *            Component Name to be set.
     */
    @XmlElement(name = "componentName")
    public void setComponentName(String componentName) {
        this._componentName = componentName;
    }
    /**
     * Sets the given component sequence.
     *
     * @param componentSeq
     *            sequence of the component installed.
     */
    @XmlElement(name = "componentSeq")
    public void setComponentSeq(long componentSeq) {
        this._componentSeq = componentSeq;
    }
}
