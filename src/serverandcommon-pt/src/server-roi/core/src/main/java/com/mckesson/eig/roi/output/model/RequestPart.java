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
 * This class contains the Request Part
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class RequestPart {

    /** This holds the contentId*/
    private String _contentId;
    /** This holds the contentSourceName*/
    private String _contentSourceName;
    /** This holds the contentSoureType*/
    private String _contentSourceType;
    /** This holds the list of map models*/
    private List<MapModel> _properties;
    /** This holds the list of output transforms*/
    private List<OutputTransform> _outputTransforms;

    public String getContentId() {
        return _contentId;
    }

    public void setContentId(String contentId) {
        _contentId = contentId;
    }

    public String getContentSourceName() {
        return _contentSourceName;
    }

    public void setContentSourceName(String contentSourceName) {
        _contentSourceName = contentSourceName;
    }

    public String getContentSourceType() {
        return _contentSourceType;
    }

    public void setContentSourceType(String contentSourceType) {
        _contentSourceType = contentSourceType;
    }

    public List<MapModel> getProperties() {
        return _properties;
    }

    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }

    public List<OutputTransform> getOutputTransforms() {
        return _outputTransforms;
    }

    public void setOutputTransforms(List<OutputTransform> outputTransforms) {
        _outputTransforms = outputTransforms;
    }
    
    
}
