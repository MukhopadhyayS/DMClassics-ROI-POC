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
public class TestProcessAttributeList extends junit.framework.TestCase {
    
    private static ProcessAttributeList _processAttributeList;
    
    public void testConstructors() {
        
        _processAttributeList = new ProcessAttributeList();
        assertNotNull(_processAttributeList);
        _processAttributeList = new ProcessAttributeList(new ArrayList<ProcessAttribute>());
        assertNotNull(_processAttributeList.getProcessAttributeList());
    }
    
    public void testSettersAndGetters() {

        List<ProcessAttribute> processAttrList = new ArrayList<ProcessAttribute>();
        ProcessAttribute pa = new ProcessAttribute();
        pa.setProcessId(1);
        pa.setAttributeName("process_type");
        pa.setAttributeType("both");
        pa.setAttributeValue("String");
        processAttrList.add(pa);

        _processAttributeList.setProcessAttributeList(processAttrList);
        assertEquals(_processAttributeList.getProcessAttributeList().size(), 1);
    }
    
    public void testToString() {
        
        String processAttributeToString = 
          new StringBuffer().append("ProcessAttributeList[ ")
                            .append("Process_Attribute[processId=1, ")  
                            .append("attributeName=process_type, ")
                            .append("attributeValue=String, ")
                            .append("attributeType=both, ")
                            .append("updatedTS=NULL")
                            .append(", createdTS=NULL")
                            .append("] ]").toString();
        assertEquals(processAttributeToString, _processAttributeList.toString());
    }
}
