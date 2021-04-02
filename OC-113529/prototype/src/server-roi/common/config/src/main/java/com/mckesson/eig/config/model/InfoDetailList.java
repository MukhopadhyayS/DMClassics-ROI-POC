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
 * <code>InfoDetailList</code> provides the list of available log file details
 * corresponding to all the components installed in the machine.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "InfoDetailList", namespace = EIGConstants.TYPE_NS_V1)
public class InfoDetailList {
    
    /**
     * Instantiates an InfoDetailList
     */
    public InfoDetailList() {
    }
    
    /**
     * Instantiates an InfoDetailList with the list of info details
     * @param infoDetailList
     */
    public InfoDetailList(List<ListViewLogInfoList> infoDetailList) {
        this._listViewLogInfoList = infoDetailList;
    }
    
    /**
     * Member variable of type List that holds the list of
     * <code>ListViewLogInfoList</code>.
     */
    private List<ListViewLogInfoList> _listViewLogInfoList;

    /**
     * Method that returns the log file details for all
     * the components installed in the machine.
     *
     * @return listViewLogInfoList
     */
    public List<ListViewLogInfoList> getListViewLogInfoList() {
        return _listViewLogInfoList;
    }

     /**
     * Sets the listViewLogInfoList.
     *
     * @param listViewLogInfoList
     *            list of listViewLogInfoList objects to be set.
     */
    @XmlElement(name = "listViewLogInfoList")
    public void setListViewLogInfoList(List<ListViewLogInfoList> listViewLogInfoList) {
        _listViewLogInfoList = listViewLogInfoList;
    }

}
