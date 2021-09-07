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
 * @author sahuly
 * @date   Feb 17, 2009
 * @since  HECM 2.0; Feb 17, 2009
 */
public class TestProcessList extends junit.framework.TestCase {
    
private static ProcessList _processList;
    
    public void testConstructors() {
        
        _processList = new ProcessList();
        assertNotNull(_processList);
        _processList = new ProcessList(new ArrayList<Process>());
        assertNotNull(_processList.getProcessList());
    }
    
    public void testSettersAndGetters() 
    throws Throwable {

        List<Process> processAttrList = new ArrayList<Process>();
        Process process = new Process();
        processAttrList.add(process);
        
        _processList.setProcessList(processAttrList);
        assertEquals(_processList.getProcessList().size(), 1);
        _processList.finalize();
    }
    
    public void testToString() {
        
        String processAttributeToString = 
          new StringBuffer().append("ProcessList[ ")
                            .append("Process[ProcessId=0, ")  
                            .append("ModifiedUserId=null, ")
                            .append("ProcessVersions=null, ")
                            .append("OwnerActors=null, ")
                            .append("AssignedActors=null, ")
                            .append("Owners=null, ")
                            .append("ProcessACLS=null, ")
                            .append("ModifiedDateTime=null, ")
                            .append("CreateDateTime=null")
                            .append("] ]").toString();
        assertEquals(processAttributeToString, _processList.toString());
    }

}
