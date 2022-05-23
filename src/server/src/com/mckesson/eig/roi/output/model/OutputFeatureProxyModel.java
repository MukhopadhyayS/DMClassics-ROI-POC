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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutputFeature", propOrder = {
    "_outputFeatureId",
    "_name",
    "_description",
    "_attributes"
})
public class OutputFeatureProxyModel extends AbstractOutputAttributes
{

    @XmlElement(name="outputFeatureId")
    private Long _outputFeatureId;
    
    @XmlElement(name="name", required = true)
    private String _name;
    
    @XmlElement(name="description", required = true)
    private String _description;
    
    
    public Long getOutputFeatureId() {
        return _outputFeatureId;
    }

    public void setOutputFeatureId(Long outputFeatureId) {
        _outputFeatureId = outputFeatureId;
    }

    public String getName() {
        return _name;
    }
    
    public void setName(String name) {
        _name = name;
    }
    
    public String getDescription() {
        return _description;
    }
    
    public void setDescription(String description) {
        _description = description;
    }
}