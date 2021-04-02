/*
 * Copyright 2007 McKesson Corporation and/or one of its subsidiaries. 
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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test class for InfoDetailList. It tests the methods of
 * InfoDetailList class.
 */
public class TestInfoDetailList extends TestCase {
    /**
     * Reference of type <code>InfoDetailList</code>.
     */
    private InfoDetailList _infoDetailList = null;
    /**
     * Reference of type <code>ListViewLogInfo</code>.
     */
    private ListViewLogInfo _listViewLogInfo = null;
    /**
     * Holds instance of component name.
     */
    private static final String COMPONENT_NAME = "Component name";
    /**
     * Holds instance of component path.
     */
    private static final String COMPONENT_PATH = "Component path";
    /**
     * Holds instance of component sequence.
     */
    private static final int COMPONENT_SEQUENCE = 1;
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _infoDetailList = new InfoDetailList();
        _listViewLogInfo = new ListViewLogInfo();
    }

    /**
     * Removes the data initialized as a part of the setUp.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _infoDetailList = null;
        _listViewLogInfo = null;
    }
    /**
     * Test methods, tests the getter and setter methods of
     * <code>InfoDetailList</code>
     */
    public void testListViewLogInfoList() {
        List listViewLogInfoList = new ArrayList();
        _listViewLogInfo.setComponentName(COMPONENT_NAME);
        _listViewLogInfo.setComponentPath(COMPONENT_PATH);
        _listViewLogInfo.setComponentSequence(COMPONENT_SEQUENCE);
        listViewLogInfoList.add(_listViewLogInfo);
        _infoDetailList.setListViewLogInfoList(listViewLogInfoList);
        assertEquals(_infoDetailList.getListViewLogInfoList(),
                listViewLogInfoList);
    }

}
