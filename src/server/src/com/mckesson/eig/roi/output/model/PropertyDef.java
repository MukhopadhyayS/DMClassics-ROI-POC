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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="propertyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="defaultValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="required" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bindingProperty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="possibleValues" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pairPropertyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rowCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contextMenu" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="jsAction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxLength" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyDef", propOrder = {
    "_propertyName",
    "_label",
    "_description",
    "_dataType",
    "_defaultValue",
    "_required",
    "_bindingProperty",
    "_possibleValues",
    "_index",
    "_pairPropertyName",
    "_rowCount",
    "_contextMenu",
    "_jsAction",
    "_size",
    "_maxLength"
})
public class PropertyDef {

    
    @XmlElement(name="propertyName", required = true)
    private String _propertyName;
    
    @XmlElement(name="label", required = true)
    private String _label;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    @XmlElement(name="dataType", required = true)
    private String _dataType;
    
    @XmlElement(name="defaultValue", required = true)
    private String _defaultValue;
    
    @XmlElement(name="required", required = true)
    private String _required;
    
    @XmlElement(name="bindingProperty", required = true)
    private String _bindingProperty;
    
    @XmlElement(name="possibleValues")
    private String[] _possibleValues;
    
    @XmlElement(name="index", required = true)
    private String _index;
    
    @XmlElement(name="pairPropertyName", required = true)
    private String _pairPropertyName;
    
    @XmlElement(name="rowCount", required = true)
    private String _rowCount;
    
    @XmlElement(name="contextMenu")
    private String[] _contextMenu;
    
    @XmlElement(name="jsAction", required = true)
    private String _jsAction;
    
    @XmlElement(name="size", required = true)
    private String _size;
    
    @XmlElement(name="maxLength", required = true)
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
