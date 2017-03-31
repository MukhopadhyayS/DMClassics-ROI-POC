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

import java.util.Map;


/**
 * This method contains the destination information
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 */
public class DestInfo {

    /** This contains the service Id*/
    private String _serviceId;
    /** This holds the status info Object*/
    private StatusInfo _statusInfo;
    /** This holds the arrays of property Definitions*/
    private PropertyDef[] _propertyDefs;
    /** This holds the arrays of job option Definitions*/
    private PropertyDef[] _jobOptionDefs;
    /** This holds the id*/
    private int _id;
    /** This holds the name*/
    private String _name;
    /** This holds the type*/
    private String _type;
    /** This holds the properties*/
    private Map<String, ? extends Object> _properties;
    /** This holds the description*/
    private String _description;
    
    /*name
    type
    id
    description
    properties*/

    
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public Map<String, ? extends Object> getProperties() {
        return _properties;
    }

    public void setProperties(Map<String, ? extends Object> properties) {
        _properties = properties;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    public String getServiceId() {
        return _serviceId;
    }

    public void setServiceId(String serviceId) {
        _serviceId = serviceId;
    }

    public StatusInfo getStatusInfo() {
        return _statusInfo;
    }
    
    public void setStatusInfo(StatusInfo statusInfo) {
        _statusInfo = statusInfo;
    }
    
    public PropertyDef[] getPropertyDefs() {
        return _propertyDefs;
    }
    
    public void setPropertyDefs(PropertyDef[] propertyDefs) {
        _propertyDefs = propertyDefs;
    }
    
    public PropertyDef[] getJobOptionDefs() {
        return _jobOptionDefs;
    }
    
    public void setJobOptionDefs(PropertyDef[] jobOptionDefs) {
        _jobOptionDefs = jobOptionDefs;
    }
}
