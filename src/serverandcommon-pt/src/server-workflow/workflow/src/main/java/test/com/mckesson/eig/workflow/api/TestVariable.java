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
 * @author sahuly
 * @date   Feb 16, 2009
 * @since  HECM 2.0; Feb 16, 2009
 */
public class TestVariable extends junit.framework.TestCase {
    
    private static Variable _variable;
    
    public void testSettersAndGetters() {
        
        _variable = new Variable();
        _variable.setKey("key");
        _variable.setValue("value");

        assertEquals(_variable.getKey(), "key");
        assertEquals(_variable.getValue(), "value");
    }
}
