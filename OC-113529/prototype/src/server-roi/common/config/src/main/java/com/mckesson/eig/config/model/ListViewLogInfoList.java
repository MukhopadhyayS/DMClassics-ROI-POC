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
 * Used for wrapping <code>ListViewLogInfo</code> into a list.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListViewLogInfoList", namespace = EIGConstants.TYPE_NS_V1)
public class ListViewLogInfoList {
    
    /**
     * Member variable of type List that holds the list of
     * <code>ListViewLogInfo</code>.
     */
    private List<ListViewLogInfo> _listViewLogInfoList;
    
    /**
     * Instantiates an ListViewLogInfoList
     */
    public ListViewLogInfoList() {
    }
    
    /**
     * Instantiates an ListViewLogInfoList which holds the list of log files abd their info
     */
    public ListViewLogInfoList(List<ListViewLogInfo> listViewInfoList) {
        this._listViewLogInfoList = listViewInfoList;
    }
    
    /**
     * Method that returns the ListViewLogInfoList.
     *
     * @return list of listViewLogInfoList.
     */
    public List<ListViewLogInfo> getListViewLogInfoList() {
        return _listViewLogInfoList;
    }
    /**
     * Sets the listViewLogInfoList.
     *
     * @param listViewLogInfoList
     *            list of listViewLogInfoList objects to be set.
     */
    @XmlElement(name = "listViewLogInfo", type = ListViewLogInfo.class)
    public void setListViewLogInfoList(List<ListViewLogInfo> listViewLogInfoList) {
        this._listViewLogInfoList = listViewLogInfoList;
    }

}
