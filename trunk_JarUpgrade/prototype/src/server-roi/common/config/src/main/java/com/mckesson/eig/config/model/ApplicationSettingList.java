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

package com.mckesson.eig.config.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;


/**
 * Model class holding list of ApplicationSetting configuration data
 *
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "applicationSettingList", namespace = EIGConstants.TYPE_NS_V1)
public class ApplicationSettingList {

    /**
    * Member variable containing list of ApplicationSetting objects
    */
    private List<ApplicationSetting> _applicationSettings;

    public List<ApplicationSetting> getApplicationSettings() {
        return _applicationSettings;
    }

    @XmlElement(name = "applicationSettings", type = ApplicationSetting.class)
    public void setApplicationSettings(List<ApplicationSetting> settings) {
        _applicationSettings = settings;
    }

}
