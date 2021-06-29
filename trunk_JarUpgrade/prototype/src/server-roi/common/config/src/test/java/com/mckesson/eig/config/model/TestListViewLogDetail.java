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

import junit.framework.TestCase;
/**
 * Test class for ListViewLogDetail. It tests the methods of
 * ListViewLogDetail class.
 */
public class TestListViewLogDetail extends TestCase {

    /**
     * Reference of type <code>ListViewLogInfo</code>.
     */
    private ListViewLogDetail _listViewLogDetail = null;
    /**
     * Member variable of type String holding the name of the component.
     */
    private String _componentName = "Component Name";
    /**
     * Member variable of type String holding the sequence of the component.
     */
    private long _componentSeq = 1;
    /**
     * setUp method for the test case.
     *
     * @throws Exception
     *             of type Exception.
     */
    protected void setUp() throws Exception {
        super.setUp();
        _listViewLogDetail = new ListViewLogDetail();
    }
    /**
     * Removes the data initialized as a part of the setUp.
     *
     * @throws Exception
     *             On tear down.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _listViewLogDetail = null;
    }
    /**
     * Test methods, tests the getter and setter methods of
     * <code>componentName</code>
     */
    public void testComponentName() {
        _listViewLogDetail.setComponentName(_componentName);
        assertEquals(_componentName, _listViewLogDetail.getComponentName());
    }
    /**
     * Test methods, tests the getter and setter methods of
     * <code>componentSeq</code>
     */
    public void testComponentSeq() {
        _listViewLogDetail.setComponentSeq(_componentSeq);
        assertEquals(_componentSeq, _listViewLogDetail.getComponentSeq());
    }
}
