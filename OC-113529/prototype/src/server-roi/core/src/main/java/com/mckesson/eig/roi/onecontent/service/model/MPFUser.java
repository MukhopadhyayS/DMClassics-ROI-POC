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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author ais
 *
 */

public class MPFUser  
implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _name;
    private String _password;
    private Integer _userInstanceId;
    private String _pvGroup;
    private String _rvGroup;
    private Integer _idle;
    private String _textName;
    private String _fullName;
    private String _pin;
    private String _sign;
    private String _audit;
    private Boolean _accountDisabled;
    private Integer _invalidLogonCount;
    private Integer _overrideType;
    private Boolean _changePassword;
    private Integer _restrictResults;
    private Integer _domainInstanceId;
    private String _physGroup;
    private Boolean _physGroupSign;
    private Boolean _physGroupText;
    private Boolean _physGroupDict;
    private String _lockout;
    private String _groupLimit;
    private String _keyIdLimit;
    private String _physGroupName;
    private String _contentPassword;
    private Boolean _adminFlag;
    private List<String> _facilities;
    private String _epnOrGpi;
    private List<String> _userSqlFragments;
    private boolean _maskMrn;
    private boolean _maskSsn;
    private boolean _physicianFlag;
    
    /** The Active Directory domain. */
    private String _adDomain;
    
    /** The Active Directory user. */
    private String _adUser;

    private String _attestationRequirement;

    public MPFUser() {

    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Integer getUserInstanceId() {
        return _userInstanceId;
    }

    public void setUserInstanceId(Integer userInstanceId) {
        _userInstanceId = userInstanceId;
    }

    public String getPvGroup() {
        return _pvGroup;
    }

    public void setPvGroup(String pvGroup) {
        _pvGroup = pvGroup;
    }

    public String getRvGroup() {
        return _rvGroup;
    }

    public void setRvGroup(String rvGroup) {
        _rvGroup = rvGroup;
    }

    public Integer getIdle() {
        return _idle;
    }

    public void setIdle(Integer idle) {
        _idle = idle;
    }

    public String getTextName() {
        return _textName;
    }

    public void setTextName(String textName) {
        _textName = textName;
    }

    public String getFullName() {
        return _fullName;
    }

    public void setFullName(String fullName) {
        _fullName = fullName;
    }
    
    public String getPin() {
        return _pin;
    }

    public void setPin(String pin) {
        _pin = pin;
    }
    
    public String getAudit() {
        return _audit;
    }

    public void setAudit(String audit) {
        _audit = audit;
    }

    public Boolean isAccountDisabled() {
        return _accountDisabled;
    }

    public void setAccountDisabled(Boolean accountDisabled) {
        _accountDisabled = accountDisabled;
    }

    public Integer getInvalidLogonCount() {
        return _invalidLogonCount;
    }

    public void setInvalidLogonCount(Integer invalidLogonCount) {
        _invalidLogonCount = invalidLogonCount;
    }

    public Integer getOverrideType() {
        return _overrideType;
    }

    public void setOverrideType(Integer overrideType) {
        _overrideType = overrideType;
    }

    public Boolean isChangePassword() {
        return _changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        _changePassword = changePassword;
    }

    public Integer getRestrictResults() {
        return _restrictResults;
    }

    public void setRestrictResults(Integer restrictResults) {
        _restrictResults = restrictResults;
    }

    public Integer getDomainInstanceId() {
        return _domainInstanceId;
    }

    public void setDomainInstanceId(Integer domainInstanceId) {
        _domainInstanceId = domainInstanceId;
    }

    public String getPhysGroup() {
        return _physGroup;
    }

    public void setPhysGroup(String physGroup) {
        _physGroup = physGroup;
    }

    public Boolean isPhysGroupSign() {
        return _physGroupSign;
    }

    public void setPhysGroupSign(Boolean physGroupSign) {
        _physGroupSign = physGroupSign;
    }

    public Boolean isPhysGroupText() {
        return _physGroupText;
    }

    public void setPhysGroupText(Boolean physGroupText) {
        _physGroupText = physGroupText;
    }

    public Boolean isPhysGroupDict() {
        return _physGroupDict;
    }

    public void setPhysGroupDict(Boolean physGroupDict) {
        _physGroupDict = physGroupDict;
    }

    public Boolean isLockout() {
        return !(null == _lockout || !_lockout.equalsIgnoreCase("Y"));
    }

    public void setLockout(String lockout) {
        _lockout = lockout;
    }

    public String getSign() {
        return _sign;
    }

    public void setSign(String sign) {
        _sign = sign;
    }

    public String getGroupLimit() {
        return _groupLimit;
    }

    public void setGroupLimit(String groupLimit) {
        _groupLimit = groupLimit;
    }

    //_keyIdLimit
    public String getKeyIdLimit() {
        return _keyIdLimit;
    }
    
    public void setKeyIdLimit(String keyIdLimit) {
        this._keyIdLimit = keyIdLimit;
    }
    
    public String getPhysGroupName() {
        return _physGroupName;
    }

    public void setPhysGroupName(String physGroupName) {
        _physGroupName = physGroupName;
    }

    public Boolean getAdminFlag() {
        return _adminFlag;
    }

    public void setAdminFlag(Boolean adminFlag) {
        _adminFlag = adminFlag;
    }

    public List<String> getFacilities() {
        return _facilities;
    }

    public void setFacilities(List<String> facilities) {
        this._facilities = facilities;
    }

    public Boolean hasAccessToFacility(String facility) {
        for (Integer i = 0; i < _facilities.size(); i++) {
            if (_facilities.get(i).trim().equals(facility.trim())) {
                return true;
            }
        }
        return false;
    }    
    
    public String getEpnOrGpi() {
        return _epnOrGpi;
    }
    
    public void setEpnOrGpi(String epnOrGpi) {
        this._epnOrGpi = epnOrGpi;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }
    
    public void setUserSqlFragments(List<String> userSqlFragments) {
        this._userSqlFragments = userSqlFragments;
    }
    
    public List<String> getUserSqlFragments() {
        return _userSqlFragments;
        
    }

    @JsonIgnore
    public boolean isPhysician() {
        
        boolean result = (_pin != null && !_pin.trim().equals("")
                        && _audit != null && !_audit.trim().equals("")
                        && _textName != null && !_textName.trim().equals("")) ? true : false;

        return result;        
    }

    public boolean isMaskMrn() {
        return _maskMrn;
    }

    public void setMaskMrn(boolean maskMrn) {
        _maskMrn = maskMrn;
    }
    
    public boolean isMaskSsn() {
        return _maskSsn;
    }

    public void setMaskSsn(boolean maskSsn) {
        _maskSsn = maskSsn;
    }
    
    public boolean isPhysicianFlag() {
        return _physicianFlag;
    }

    public void setPhysicianFlag(boolean physicianFlag) {
        _physicianFlag = physicianFlag;
    }

    /**
     * Gets the Active Directory domain.
     *
     * @return the Active Directory domain
     */
    public String getAdDomain() {
        return _adDomain;
    }

    /**
     * Sets the Active Directory domain.
     *
     * @param adDomain the new Active Directory domain
     */
    public void setAdDomain(String adDomain) {
        _adDomain = adDomain;
    }

    /**
     * Gets the Active Directory user.
     *
     * @return the Active Directory user
     */
    public String getAdUser() {
        return _adUser;
    }

    /**
     * Sets the Active Directory user.
     *
     * @param adUser the new Active Directory user
     */
    public void setAdUser(String adUser) {
        _adUser = adUser;
    }

    /**
     * @return the contentPassword
     */
    public String getContentPassword() {
        return this._contentPassword;
    }

    /**
     * @param contentPassword the contentPassword to set
     */
    public void setContentPassword(String contentPassword) {
        this._contentPassword = contentPassword;
    }
    
    public String getAttestationRequirement() {
        return _attestationRequirement;
    }

    public void setAttestationRequirement(String attestationRequirement) {
        if (null == attestationRequirement) {
            attestationRequirement = "N";
        }
        this._attestationRequirement = attestationRequirement;
    }
}
