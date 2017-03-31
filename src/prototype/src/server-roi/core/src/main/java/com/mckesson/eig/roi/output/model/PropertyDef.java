/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.model;


/**
 * This class contains the Property Defintions
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class PropertyDef {

    /** This holds the property Name*/
    private String _propertyName;
    /** This holds the label*/
    private String _label;
    /** This holds the description*/
    private String _description;
    /** This holds the data type*/
    private String _dataType;
    /** This holds the default value*/
    private String _defaultValue;
    /** This holds the required*/
    private String _required;
    /** This holds the binding property*/
    private String _bindingProperty;
    /** This holds the possible values*/
    private String[] _possibleValues;
    /** This holds the index*/
    private String _index;
    /** This holds the pairPropertyName*/
    private String _pairPropertyName;
    /** This holds the row count*/
    private String _rowCount;
    /** This holds the context menu*/
    private String[] _contextMenu;
    /** This holds the jsAction*/
    private String _jsAction;
    /** This holds the size*/
    private String _size;
    /** This holds the max length*/
    private String _maxLength;

    public String getPropertyName() {
        return _propertyName;
    }

    public void setPropertyName(String propertyName) {
        _propertyName = propertyName;
    }

    public String getLabel() {
        return _label;
    }

    public void setLabel(String label) {
        _label = label;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getDataType() {
        return _dataType;
    }

    public void setDataType(String dataType) {
        _dataType = dataType;
    }

    public String getDefaultValue() {
        return _defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        _defaultValue = defaultValue;
    }

    public String getRequired() {
        return _required;
    }

    public void setRequired(String required) {
        _required = required;
    }

    public String getBindingProperty() {
        return _bindingProperty;
    }

    public void setBindingProperty(String bindingProperty) {
        _bindingProperty = bindingProperty;
    }

    public String[] getPossibleValues() {
        return _possibleValues;
    }

    public void setPossibleValues(String[] possibleValues) {
        _possibleValues = possibleValues;
    }

    public String getIndex() {
        return _index;
    }

    public void setIndex(String index) {
        _index = index;
    }

    public String getPairPropertyName() {
        return _pairPropertyName;
    }

    public void setPairPropertyName(String pairPropertyName) {
        _pairPropertyName = pairPropertyName;
    }

    public String getRowCount() {
        return _rowCount;
    }

    public void setRowCount(String rowCount) {
        _rowCount = rowCount;
    }

    public String[] getContextMenu() {
        return _contextMenu;
    }

    public void setContextMenu(String[] contextMenu) {
        _contextMenu = contextMenu;
    }

    public String getJsAction() {
        return _jsAction;
    }

    public void setJsAction(String jsAction) {
        _jsAction = jsAction;
    }

    public String getSize() {
        return _size;
    }

    public void setSize(String size) {
        _size = size;
    }

    public String getMaxLength() {
        return _maxLength;
    }

    public void setMaxLength(String maxLength) {
        _maxLength = maxLength;
    }
   
}
