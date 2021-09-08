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

import java.util.List;

/**
 * This class contains the output transforms 
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class OutputTransform {
    
    /** This holds the list of map models*/
    private List<MapModel> _properties;
    /** This holds the transformName*/
    private String _transformName;
    /** This holds the transformType*/
    private String _transformType;
    
    
    public List<MapModel> getProperties() {
        return _properties;
    }
    
    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }
    
    public String getTransformName() {
        return _transformName;
    }
    
    public void setTransformName(String transformName) {
        _transformName = transformName;
    }
    
    public String getTransformType() {
        return _transformType;
    }
    
    public void setTransformType(String transformType) {
        _transformType = transformType;
    }
}
