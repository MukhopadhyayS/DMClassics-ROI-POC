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

package com.mckesson.eig.workflow.api;


/**
 * @author McKesson
 * @date   March 29, 2009
 * @since  HECM 2.0; March 29, 2009
 */
public class TestProcessVariable extends junit.framework.TestCase {

    private static ProcessVariable _pv;

    public void testSettersAndGetters() {

        _pv = new ProcessVariable();
        _pv.setProcessId("100");
        _pv.setActivityName("testActivity");
        _pv.setVariableName("testVariableName");
        _pv.setVariableTypeSubtype("Content:5001");
        _pv.setValueTypeSubtype("contenttypeid:int");
        _pv.setValue("10");
        _pv.setIndex("0");


        assertEquals(_pv.getProcessId(), "100");
        assertEquals(_pv.getActivityName(), "testActivity");
        assertEquals(_pv.getVariableName(), "testVariableName");
        assertEquals(_pv.getVariableTypeSubtype(), "Content:5001");
        assertEquals(_pv.getValueTypeSubtype(), "contenttypeid:int");
        assertEquals(_pv.getValue(), "10");
        assertEquals(_pv.getIndex(), "0");
    }
}
