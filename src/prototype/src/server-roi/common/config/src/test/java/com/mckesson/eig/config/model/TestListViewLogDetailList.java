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
 * Test class for ListViewLogDetailList. It tests the methods of
 * ListViewLogDetailList class.
 */
public class TestListViewLogDetailList extends TestCase {
    /**
     * Reference of type <code>ListViewLogDetailList</code>.
     */
    private ListViewLogDetailList _listViewLogDetailList = null;
    /**
     * Reference of type <code>ListViewLogDetail</code>.
     */
    private ListViewLogDetail _listViewLogDetail = null;
    /**
     * Holds instance of component name.
     */
    private static final String COMPONENT_NAME = "Component name";
    /**
     * Holds instance of component sequence.
     */
    private static final int COMPONENT_SEQUENCE = 1;
    /**
     * Member variable of type List holding the list of
     * ListViewLogDetail objects.
     */
    private List _list = null;
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _listViewLogDetailList = new ListViewLogDetailList();
        _listViewLogDetail = new ListViewLogDetail();
        _list = new ArrayList();
    }
    /**
     * Removes the data initialized as a part of the setUp.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _listViewLogDetailList = null;
        _listViewLogDetail = null;
        _list = null;
    }
    /**
     * Test methods, tests the getter and setter methods of
     * <code>listViewLogDetailList</code>
     */
    public void testListViewLogDetailList() {
        _listViewLogDetail.setComponentName(COMPONENT_NAME);
        _listViewLogDetail.setComponentSeq(COMPONENT_SEQUENCE);
        _list.add(_listViewLogDetail);
        _listViewLogDetailList.setListViewLogDetailList(_list);
        assertEquals(_list, _listViewLogDetailList.getListViewLogDetailList());
    }

}
