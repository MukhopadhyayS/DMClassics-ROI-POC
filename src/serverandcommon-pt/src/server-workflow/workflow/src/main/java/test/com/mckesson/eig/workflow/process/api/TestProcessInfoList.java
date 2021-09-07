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

package com.mckesson.eig.workflow.process.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kayalvizhik
 * @date   Feb 20, 2009
 * @since  HECM 2.0; Feb 20, 2009
 */
public class TestProcessInfoList extends junit.framework.TestCase {

private static ProcessInfoList _processInfoList;

    public void testConstructors() {

        _processInfoList = new ProcessInfoList();
        assertNotNull(_processInfoList);
        _processInfoList = new ProcessInfoList(new ArrayList<ProcessInfo>());
        assertNotNull(_processInfoList.getProcessList());
    }

    public void testSettersAndGetters()
    throws Throwable {

        List<ProcessInfo> processAttrList = new ArrayList<ProcessInfo>();
        ProcessInfo process = new ProcessInfo();
        processAttrList.add(process);

        _processInfoList.setProcessList(processAttrList);
        assertEquals(_processInfoList.getProcessList().size(), 1);
        _processInfoList.finalize();
    }

    public void testToString() {

        String processAttributeToString =
          new StringBuffer().append("ProcessInfoList[ ProcessInfo[processId=0, ")
          		.append("owners=set of Actors, ")
          		.append("actors=set of Actors] ]").toString();
        assertEquals(processAttributeToString, _processInfoList.toString());
    }

}
