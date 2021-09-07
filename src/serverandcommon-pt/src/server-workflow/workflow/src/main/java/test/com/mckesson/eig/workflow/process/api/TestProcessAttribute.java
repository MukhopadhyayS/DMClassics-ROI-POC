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

import java.util.Date;

import com.mckesson.eig.utility.util.DateUtilities;


/**
 * @author sahuly
 * @date   Feb 16, 2009
 * @since  HECM 2.0; Feb 16, 2009
 */
public class TestProcessAttribute  extends junit.framework.TestCase {
    
    private static ProcessAttribute _processAttribute;
    private static Date _date = new Date();
    
    public void testSettersAndGetters() {
        
        _processAttribute = new ProcessAttribute();
        _processAttribute.setAttributeName("process_type");
        _processAttribute.setVersionId(1);
        _processAttribute.setAttributeType("both");
        _processAttribute.setAttributeValue("String");
        _processAttribute.setCreatedTS(_date);
        _processAttribute.setProcessId(1);
        _processAttribute.setUpdatedTS(_date);
        
        assertEquals(_processAttribute.getAttributeName(), "process_type");
        assertEquals(_processAttribute.getVersionId(), 1);
        assertEquals(_processAttribute.getAttributeType(), "both");
        assertEquals(_processAttribute.getAttributeValue(), "String");
        assertEquals(_processAttribute.getCreatedTS(), _date);
        assertEquals(_processAttribute.getProcessId(), 1);
        assertEquals(_processAttribute.getUpdatedTS(), _date);
    }
    
    public void testToString() {
        
        String processAttributeToString = 
          new StringBuffer().append("Process_Attribute[processId=1, ")
                            .append("attributeName=process_type, ")
                            .append("attributeValue=String, ")
                            .append("attributeType=both, ")
                            .append("updatedTS=")
                            .append(DateUtilities.formatISO8601(_date))
                            .append(", createdTS=")
                            .append(DateUtilities.formatISO8601(_date))
                            .append("]").toString();
        assertEquals(processAttributeToString, _processAttribute.toString());
    }
}
