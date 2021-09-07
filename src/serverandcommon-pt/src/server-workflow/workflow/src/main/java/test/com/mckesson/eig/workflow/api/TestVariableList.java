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

import java.util.ArrayList;
import java.util.List;

/**
 * @author sahuly
 * @date   Feb 16, 2009
 * @since  HECM 2.0; Feb 16, 2009
 */
public class TestVariableList extends junit.framework.TestCase {
    
    private static List<Variable> _variables;
    private static VariableList _variableList;
    
    public void testSettersAndGetters() {
        
        _variableList = new VariableList();
        _variables = new ArrayList<Variable>();
        Variable variable = new Variable();
        variable.setKey("key");
        variable.setValue("value");
        _variables.add(variable);

        _variableList = new VariableList();
        _variableList.setVariables(_variables);

        assertTrue(_variableList.getVariables().size() > 0);
    }
    
    public void testGetVariablesValue() {

        List<String> vaList = _variableList.getVariablesValue();
        assertTrue(vaList.size() > 0);
    }

}
