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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;
/**
 * <code>ListViewLogDetailList</code> provides the list of available
 * log file details corresponding to the components.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ListViewLogDetailList", namespace = EIGConstants.TYPE_NS_V1)
public class ListViewLogDetailList {
    
    /**
     * Member variable of type List that holds the list of
     * <code>ListViewLogDetail</code>.
     */
    private List<ListViewLogDetail> _listViewLogDetailList;
    
    /**
     * Instantiates an ListViewLogDetailList
     */
    public ListViewLogDetailList() {
    }
    
    /**
     * Instantiates an ListViewLogDetailList which holds the list of log files and their info
     */
    public ListViewLogDetailList(List<ListViewLogDetail> list) {
        _listViewLogDetailList = list;
    }
    
    /**
     * Method that returns the log file details for the components
     * installed in the machine.
     *
     * @return listViewLogDetailList
     */
    public List<ListViewLogDetail> getListViewLogDetailList() {
        return _listViewLogDetailList;
    }
    /**
     * Sets the listViewLogDetailList.
     *
     * @param listViewLogDetailList
     *            list of listViewLogDetailList objects to be set.
     */
    @XmlElement(name = "listViewLogDetail")
    public void setListViewLogDetailList(List<ListViewLogDetail> listViewLogDetailList) {
        this._listViewLogDetailList = listViewLogDetailList;
    }
}
