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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;
/**
 * @author sahuly
 * @date   Feb 10, 2009
 * @since  HECM 2.0; Feb 10, 2009
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "VariableList", namespace = EIGConstants.TYPE_NS_V1)
@XmlRootElement(name = "variableList")
public class VariableList extends BasicWorkflowDO {

    private List<Variable> _variables;

    public List<Variable> getVariables() {
        return _variables;
    }

    @XmlElement(name = "variables", type = Variable.class)
    public void setVariables(List<Variable> variables) {
        this._variables = variables;
    }

    public List<String> getVariablesValue() {

        List<String> variableValues = new ArrayList<String>(_variables.size());
        for (int i = _variables.size(); --i >= 0;) {
            
            String variableValue = _variables.get(i).getValue().toUpperCase();
            if (variableValue.equals("BOTH")) {
                variableValues.add("MANUAL");
            }
            variableValues.add(variableValue);
        }
        return variableValues;
    }

    /**
     *
     * @return
     */
    public Map<String, String> toMap() {

    	Map<String, String> mapVariable = new HashMap<String, String>(0);
        if (_variables == null) {
        	return mapVariable;
        }
    	for (int i = _variables.size(); --i >= 0;) {
        	if (_variables.get(i).getValue() != null
        			&& _variables.get(i).getValue() instanceof String) {
        		mapVariable.put(_variables.get(i).getKey(), _variables.get(i).getValue());
        	}
        }
        return mapVariable;
    }
}
