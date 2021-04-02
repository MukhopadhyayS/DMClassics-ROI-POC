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
 * Test class for ListViewLogInfoList. It tests the methods of
 * ListViewLogInfoList class
 *
 */
public class TestListViewLogInfoList extends TestCase {

    /**
     * Reference of type <code>ListViewLogInfoList</code>.
     */
    private ListViewLogInfoList _listViewLogInfoList = null;

    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _listViewLogInfoList = new ListViewLogInfoList();
    }
    /**
     * Removes the data initialized as a part of the setUp.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _listViewLogInfoList = null;
    }

    /**
     * Test method, tests whether the object of type
     * <code>ListViewLogInfoList</code> is null or not.
     */
    public void testListViewLogInfoList() {
        assertNotNull(_listViewLogInfoList);
    }

    /**
     * Test methods, tests the getter and setter methods of
     * <code>listViewLogInfo</code>
     */
    public void testListViewlogInfo() {
        List list = new ArrayList();
        list.add("list");
        _listViewLogInfoList.setListViewLogInfoList(list);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(list, _listViewLogInfoList.getListViewLogInfoList());
        assertNotSame(2, list.size());
    }

}
