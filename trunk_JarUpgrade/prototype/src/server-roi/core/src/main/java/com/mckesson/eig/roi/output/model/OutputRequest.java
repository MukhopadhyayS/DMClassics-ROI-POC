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
 * This method contains the Output Request object
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class OutputRequest {

   
    /** This holds the list of Request Part objects*/
    private List<RequestPart> _requestParts;
    /** This holds  the destination Id*/
    private int _destId;
    /**  This holds the destination name*/
    private String _destName;
    /** This holds the destination type*/
    private String _destType;
    /** This holds  the list of map models*/
    private List<MapModel> _properties;
    /** This holds  the list of map models*/
    private List<MapModel> _labels;
    /** This holds the request Id*/
    private String _requestId;
    /** This holds the submit info */
    private SubmitInfo _submitInfo;
    /** This holds the list of OutputTransform*/
    private List<OutputTransform> _outputTransforms;
    
    public List<RequestPart> getRequestParts() {
        return _requestParts;
    }
    
    public void setRequestParts(List<RequestPart> requestParts) {
        _requestParts = requestParts;
    }
    
    public int getDestId() {
        return _destId;
    }
    
    public void setDestId(int destId) {
        _destId = destId;
    }
    
    public String getDestName() {
        return _destName;
    }
    
    public void setDestName(String destName) {
        _destName = destName;
    }
    
    public String getDestType() {
        return _destType;
    }
    
    public void setDestType(String destType) {
        _destType = destType;
    }
    
    public List<MapModel> getProperties() {
        return _properties;
    }
    
    public void setProperties(List<MapModel> properties) {
        _properties = properties;
    }
    
    public List<MapModel> getLabels() {
        return _labels;
    }
    
    public void setLabels(List<MapModel> labels) {
        _labels = labels;
    }
    
    public String getRequestId() {
        return _requestId;
    }
    
    public void setRequestId(String requestId) {
        _requestId = requestId;
    }
    
    public SubmitInfo getSubmitInfo() {
        return _submitInfo;
    }
    
    public void setSubmitInfo(SubmitInfo submitInfo) {
        _submitInfo = submitInfo;
    }
    
    public List<OutputTransform> getOutputTransforms() {
        return _outputTransforms;
    }
    
    public void setOutputTransforms(List<OutputTransform> outputTransforms) {
        _outputTransforms = outputTransforms;
    }
    
}
