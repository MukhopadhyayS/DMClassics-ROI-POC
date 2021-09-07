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
 * Representative model class for process variable format:
 * <<ProcessId>>.<<ActivityName>>.<<VariableName>>
 * .<<VariableType:VariableSubtype>>.<<ValueType:ValueSubtype>>.<<index>>
 *
 * @author eo837ew
 *
 */

public class ProcessVariable {

    private String _processId;

    private String _activityName;

    private String _variableName;

    private String _variableTypeSubtype;

    /**
     * index is used only for result set values.
     * Examples: SelectedContent, database query results.
     */
    private String _index;

    private String _value;

    private String _valueTypeSubtype;

    public String getProcessId() {
        return _processId;
    }

    public void setProcessId(String processId) {
        this._processId = processId;
    }

    public String getActivityName() {
        return _activityName;
    }

    public void setActivityName(String activityName) {
        this._activityName = activityName;
    }

    public String getVariableName() {
        return _variableName;
    }

    public void setVariableName(String variableName) {
        this._variableName = variableName;
    }

    public String getVariableTypeSubtype() {
        return _variableTypeSubtype;
    }

    public void setVariableTypeSubtype(String variableTypeSubtype) {
        this._variableTypeSubtype = variableTypeSubtype;
    }

    public String getIndex() {
        return _index;
    }

    public void setIndex(String index) {
        this._index = index;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String value) {
        this._value = value;
    }

    public String getValueTypeSubtype() {
        return _valueTypeSubtype;
    }

    public void setValueTypeSubtype(String valueTypeSubtype) {
        this._valueTypeSubtype = valueTypeSubtype;
    }

    public String toKeyString() {

        String variableType =  _variableTypeSubtype.substring(0, _variableTypeSubtype.indexOf(":"));

        String value = _processId + "." + _activityName + "." + _variableName + "."
                        + variableType + "." + _valueTypeSubtype;
        return value;
    }

    public String toValueString() {

        String value = _processId + "." + _activityName + "." + _variableName + "."
                        + _variableTypeSubtype + "." + _valueTypeSubtype + "=" + _value;
        return value;
    }

}
