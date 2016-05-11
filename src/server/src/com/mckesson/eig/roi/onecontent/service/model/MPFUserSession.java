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

package com.mckesson.eig.roi.onecontent.service.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @author ais
 *
 */
public class MPFUserSession 
implements Serializable {

    private static final long serialVersionUID = 1L;

    private MPFUser _user;

    private Map<String, String> _accessControlList;

    private String _clientMachine;

    private String _serverMachine;

    private String _deviceId;

    private String _deviceType;

    private String _clientType;

    private boolean _pinValidated = false;

    private String _clientBrowserName;

    private String _clientOSName;

    private String _callingSystemName;

    private String _tenantId;

    private List<String> _adLinkedHPFUserNames;

    private String _securityToken;

    private String _serverTime;

    private String _module;
    
    private String _adSelfMapping;
    
    private String _domainUserName;
    
    private String _domain;

    public String getModule() {
        return _module;
    }

    public void setModule(String module) {
        this._module = module;
    }

    public MPFUser getUser() {
        return _user;
    }

    public void setUser(MPFUser user) {
        this._user = user;
    }

    public Map<String, String> getAccessControlList() {
        return _accessControlList;
    }

    public void setAccessControlList(Map<String, String> accessControlList) {
        this._accessControlList = accessControlList;
    }

    public String getClientMachine() {
        return _clientMachine;
    }

    public void setClientMachine(String clientMachine) {
        this._clientMachine = clientMachine;
    }

    public String getServerMachine() {
        return _serverMachine;
    }

    public void setServerMachine(String serverMachine) {
        this._serverMachine = serverMachine;
    }

    public String getDeviceId() {
        return _deviceId;
    }

    public void setDeviceId(String deviceId) {
        this._deviceId = deviceId;
    }

    public String getDeviceType() {
        return _deviceType;
    }

    public void setDeviceType(String deviceType) {
        this._deviceType = deviceType;
    }

    public String getClientType() {
        return _clientType;
    }

    public void setClientType(String clientType) {
        this._clientType = clientType;
    }

    public boolean isPinValidated() {
        return _pinValidated;
    }

    public void setPinValidated(boolean pinValidated) {
        _pinValidated = pinValidated;
    }

    public String getClientBrowserName() {
        return _clientBrowserName;
    }

    public void setClientBrowserName(String clientBrowserName) {
        _clientBrowserName = clientBrowserName;
    }

    public String getClientOSName() {
        return _clientOSName;
    }

    public void setClientOSName(String clientOSName) {
        _clientOSName = clientOSName;
    }

    public String getCallingSystemName() {
        return _callingSystemName;
    }

    public void setCallingSystemName(String callingSystemName) {
        _callingSystemName = callingSystemName;
    }

    public String getTenantId() {
        return _tenantId;
    }

    public void setTenantId(String tenantId) {
        _tenantId = tenantId;
    }

    public List<String> getAdLinkedHPFUserNames() {
        return _adLinkedHPFUserNames;
    }

    public void setAdLinkedHPFUserNames(List<String> adLinkedHPFUserNames) {
        _adLinkedHPFUserNames = adLinkedHPFUserNames;
    }

    public String getSecurityToken() {
        return _securityToken;
    }

    public void setSecurityToken(String securityToken) {
        _securityToken = securityToken;
    }

    public String getServerTime() {
        return _serverTime;
    }

    public void setServerTime(String serverTime) {
        _serverTime = serverTime;
    }

    public String getAdSelfMapping() {
        return _adSelfMapping;
    }

    public void setAdSelfMapping(String adSelfMapping) {
        _adSelfMapping = adSelfMapping;
    }
    
    public String getDomainUserName() {
        return _domainUserName;
    }

    public void setDomainUserName(String domainUserName) {
        _domainUserName = domainUserName;
    }
    
    public String getDomain() {
        return _domain;
    }

    public void setDomain(String domain) {
        _domain = domain;
    }

}