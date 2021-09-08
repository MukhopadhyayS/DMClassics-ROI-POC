/*
 * Copyright 2007-2009 McKesson Corporation and/or one of its subsidiaries.
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

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 *
 * Model class holding Application Specific Setting/Configuration data
 *
 * @author kayalvizhik
 * @date   Mar 24, 2009
 * @since  HECM 2.0; Mar 24, 2009
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "applicationSetting", namespace = EIGConstants.TYPE_NS_V1)
public class ApplicationSetting {

    public ApplicationSetting() {

    }

    /**
     * Member variable holding the application id.
     */
    private long _appId;

    /**
     * Member variable holding the application name.
     */
    private String _appName;

    /**
    *  Member variable holding the application settings as xmlstring
    *
	* 	<application-settings name= " ">
	*		<item type= "email-server-setting">
	*			<property name = "host-address" value = "{HOST_ADDRESS}" />
	*			<property name = "port-number" value = "{PORT}" />
	*		</item >
	*		<item type= "email-address-setting">
	*			<property name = "sender-name" value = "{SENDER_NAME}" />
	*			<property name = "sender-email" value = "{SENDER_EMAIL}" />
	*			<property name = "reply-to-name" value = "{REPLY_TO_NAME}" />
	*			<property name = "reply-to-email" value = "{REPLY_TO_EMAIL}" />
	*		</item >
	*	</application-settings>
    */
    private String _settings;

    private Date _modifiedDateTime;

    public long getAppId() {
        return _appId;
    }

    @XmlElement(name = "appId")
    public void setAppId(long id) {
        _appId = id;
    }

    public String getAppName() {
        return _appName;
    }

    @XmlElement(name = "appName")
    public void setAppName(String name) {
        _appName = name;
    }

    public String getSettings() {
        return _settings;
    }

    @XmlElement(name = "settings")
    public void setSettings(String settings) {
        this._settings = settings;
    }

    public Date getModifiedDateTime() {
        return _modifiedDateTime;
    }

    @XmlElement(name = "modifiedDateTime")
    public void setModifiedDateTime(Date dateTime) {
        _modifiedDateTime = dateTime;
    }

}
