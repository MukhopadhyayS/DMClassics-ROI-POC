/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
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

package com.mckesson.eig.inuse.hpf.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.password.Password;
import com.mckesson.eig.utility.password.PasswordMD5;
import com.mckesson.eig.utility.password.PasswordPassThru;
import com.mckesson.eig.utility.util.ConversionUtilities;
import com.mckesson.eig.utility.util.ReflectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 *
 * @author OFS
 * @date   Jun 15, 2009
 * @since  ROI HPF 13.1
 */
public class User implements java.io.Serializable {

    private static final OCLogger LOG = new OCLogger(User.class);

    public static final int VALID_USER = 0;
    public static final int INVALID_USER = 1;
    public static final int USER_LOCKED_OUT = 2;
    public static final int USER_DISABLED = 3;
    public static final int CHANGE_PASSWORD = 4;
    public static final int INVALID_USER_PASSWORD = 5;

    public static final int NEWPASSWORD_VALID = 0;
    public static final int NEWPASSWORD_OLD_EMPTY = 1;
    public static final int NEWPASSWORD_NEW_EMPTY = 2;
    public static final int NEWPASSWORD_RETYPE_EMPTY = 3;
    public static final int NEWPASSWORD_SAME = 4;
    public static final int NEWPASSWORD_NOT_MATCH = 5;
    public static final int NEWPASSWORD_INVALID_OLD = 6;

    public static final int DONOT_ALLOW_RESTRICTION_OVERRIDES = 0;
    public static final int ALLOW_REQUESTED_RESTRICTION_OVERRIDES = 1;
    public static final int ALLOW_IMMEDIATE_RESTRICTION_OVERRIDES = 2;
    public static final int NINTY_NINE = 100;

    private String _password;
    private String _loginId;
    private String _fullName;
    private String _pin;
    private Integer _idle;
    private Integer _accountDisabled;
    private Integer _overrideRestrictionLevel;
    private Boolean _administrator;
    private Integer _invalidLogonCount;
    private int _validateCode;
    private Integer _changePassword;
    private String _signinAddress = "";
    private String _signinHost = "";
    private Integer _restrictResults;
    private String _textName;
    private String _initial;
    private String _subjectId;
    private Integer _domainId = new Integer(NINTY_NINE);
    private Password _passwordEncryptor;

    private Object _identity;
    public User() {
    }

    public User(String loginId, String password, String fullName) {
        setLoginId(loginId);
        setPassword(password);
        setFullName(fullName);
    }

    public User(String loginId, String password) {
        setLoginId(loginId);
        setPassword(password);
    }

    public User(String loginId) {
        setLoginId(loginId);
    }

    public User(int instanceID, String loginID, String password) {
        setInstanceId(new Integer(instanceID));
        setLoginId(loginID);
        setPassword(password);
    }

    public User(Integer instanceId) {
        setInstanceId(instanceId);
    }

    public Object getIdentity() {
        return _identity;
    }
    public void setIdentity(Object identity) {
        _identity = identity;
    }


    public Integer getInstanceId() {
        return (Integer) getIdentity();
    }

    public int getInstanceIdValue() {
        if (getInstanceId() == null) {
            return -1;
        }
        return getInstanceId().intValue();
    }

    public void setInstanceId(Integer instanceId) {
        setIdentity(instanceId);
    }

    public String getLoginId() {
        return _loginId;
    }

    public void setLoginId(String id) {
        _loginId = id;
    }

    public Object getSecondaryIdentity() {
        return getLoginId();
    }

    public String getPassword() {
        return _password;
    }


    public String getTrimmedPassword() {
        return StringUtilities.trim(getPassword());
    }

    public void setPassword(String password) {
        _password = password;
    }

    public String getFullName() {
        return _fullName;
    }

    public String getTrimmedFullName() {
        return StringUtilities.trim(_fullName);
    }

    public void setFullName(String s) {
        _fullName = s;
    }

    public String getPin() {
        return _pin;
    }

    public String getTrimmedPin() {
        return StringUtilities.trim(getPin());
    }

    public void setPin(String pin) {
        _pin = pin;
    }

    protected Boolean getAdministratorAsBoolean() {
        return _administrator;
    }

    protected void setAdministratorAsBoolean(Boolean value) {
        _administrator = value;
    }

    public void setAdministrator(boolean value) {
        setAdministratorAsBoolean(new Boolean(value));
    }

    public boolean isAdministrator() {
        if (isAccountDisabled()) {
            return false;
        }
        return ConversionUtilities.toBooleanValue(getAdministratorAsBoolean());
    }

    public List<String> getRoles() {
        List<String> results = new ArrayList<String>();
        // TODO add a Role class
        if (isAdministrator()) {
            results.add("Administrators");
        }
        if (isPhysician()) {
            results.add("Physicians");
        }
        return results;
    }

    public boolean isRoleEnabled(String roleName) {
        return (getRoles().indexOf(roleName) != -1);
    }

    public boolean validatePin(String pin) {
        return StringUtilities.isEmpty(getPin()) || isPinMatch(pin);
    }

    public int getValidateCode() {
        return _validateCode;
    }

    public static boolean isValid(User user) {
        return (user != null) && user.isValid();
    }

    public boolean isValid() {
        return (getValidateCode() == User.VALID_USER)
               || (getValidateCode() == User.CHANGE_PASSWORD);
    }



    // TODO the int result codes need to be refactored to objects
    public void validateLogin(String password, int maxAttemptsAllowed) {

        if (!isValidInstanceId()) {

            _validateCode = User.INVALID_USER;
            User.LOG.debug("Account for "
                            + getLoginId()
                            + " has an invalid instance ID: "
                            + getInstanceId());
        } else if (isAccountLocked(maxAttemptsAllowed)) {

            _validateCode = User.USER_LOCKED_OUT;
            User.LOG.debug("Account for " + getLoginId() + " is locked.");
        } else if (isAccountDisabled()) {

            _validateCode = User.USER_DISABLED;
            User.LOG.debug("Account for " + getLoginId() + " is disabled.");
        } else if (validatePassword(password, maxAttemptsAllowed)) {

            if (getChangePassword() != 0) {
                _validateCode = User.CHANGE_PASSWORD;
                User.LOG.debug("Username and password valid. User must change password.");
            } else {
                _validateCode = User.VALID_USER;
                User.LOG.debug("Username and password valid.");
            }
        } else {

            _validateCode = User.INVALID_USER_PASSWORD;
            User.LOG.debug("Account for "
                    + getLoginId()
                    + " - password entered does not match. Check encryption settings.");
        }
    }

    private boolean isValidInstanceId() {
        return getInstanceIdValue() > -1;
    }

    protected boolean isAccountLocked(int maxAttemptsAllowed) {
        if (maxAttemptsAllowed <= 0) {
            return false;
        }
        return (getInvalidLogonCount() >= maxAttemptsAllowed);
    }

    public boolean getIsPhysician() {
        return isPhysician();
    }

    public boolean isPhysician() {
        return StringUtilities.trimLength(getPin()) > 0;
    }

    public Integer getIdle() {
        return _idle;
    }

    public Integer getIdleTime() {
        return (_idle == null) ? new Integer(0) : _idle;
    }

    public void setIdle(Integer idle) {
        _idle = idle;
    }

    public Integer getAccountDisabledAsInteger() {
        return _accountDisabled;
    }

    public void setAccountDisabledAsInteger(Integer accountDisabled) {
        _accountDisabled = accountDisabled;
    }

    public boolean isAccountDisabled() {
        return ConversionUtilities.toBooleanValue(getAccountDisabledAsInteger());
    }

    public void setAccountDisabled(boolean b) {
        setAccountDisabledAsInteger(ConversionUtilities.toInteger(b));
    }

    public int getOverrideRestrictionLevel() {
        return ConversionUtilities.toInt(getOverrideRestrictionLevelAsInteger());
    }

    public void setOverrideRestrictionLevel(int overrideRestrictionLevel) {
        setOverrideRestrictionLevelAsInteger(new Integer(overrideRestrictionLevel));
    }

    public boolean doNotAllowRestrictionOverrides() {
        return getOverrideRestrictionLevel() == User.DONOT_ALLOW_RESTRICTION_OVERRIDES;
    }

    public boolean allowRequestedRestrictionOverrides() {
        return getOverrideRestrictionLevel() == User.ALLOW_REQUESTED_RESTRICTION_OVERRIDES;
    }

    public boolean allowImmediateRestrictionOverrides() {
        return getOverrideRestrictionLevel() == User.ALLOW_IMMEDIATE_RESTRICTION_OVERRIDES;
    }

    public boolean allowRestrictionOverrides() {
        return allowRequestedRestrictionOverrides() || allowImmediateRestrictionOverrides();
    }

    public int getInvalidLogonCount() {
        return ConversionUtilities.toInt(getInvalidLogonCountAsInteger());
    }

    public void setInvalidLogonCount(int invalidLogonCount) {
        setInvalidLogonCountAsInteger(new Integer(invalidLogonCount));
    }

    public boolean allowViewAssignment(String facility) {
        return true;
    }

    public int getChangePassword() {
        return ConversionUtilities.toInt(getChangePasswordAsInteger());
    }

    public void setChangePassword(int changePassword) {
        setChangePasswordAsInteger(new Integer(changePassword));
    }

    public String getSigninAddress() {
        return _signinAddress;
    }

    public void setSigninAddress(String signinAddress) {
        _signinAddress = signinAddress;
    }

    public String getSigninHost() {
        return _signinHost;
    }

    public void setSigninHost(String signinHost) {
        _signinHost = signinHost;
    }

    public String getLocation() {
        return getSigninHost() + " (" + getSigninAddress() + ")";
    }

    public int getRestrictResults() {
        return ConversionUtilities.toInt(getRestrictResultsAsInteger());
    }

    public void setRestrictResults(int restrictResults) {
        setRestrictResultsAsInteger(new Integer(restrictResults));
    }


    public String getTextName() {
        return _textName;
    }

    public void setTextName(String string) {
        _textName = string;
    }

    public String getTrimmedInitial() {
        return StringUtilities.safeTrim(getInitial());
    }

    public String getInitial() {
        return _initial;
    }

    public void setInitial(String string) {
        _initial = string;
    }

    public Integer getOverrideRestrictionLevelAsInteger() {
        return _overrideRestrictionLevel;
    }

    public void setOverrideRestrictionLevelAsInteger(Integer i) {
        _overrideRestrictionLevel = i;
    }

    public Integer getInvalidLogonCountAsInteger() {
        return _invalidLogonCount;
    }

    public void setInvalidLogonCountAsInteger(Integer i) {
        _invalidLogonCount = i;
    }

    public Integer getChangePasswordAsInteger() {
        return _changePassword;
    }

    public void setChangePasswordAsInteger(Integer i) {
        _changePassword = i;
    }

    public Integer getRestrictResultsAsInteger() {
        return _restrictResults;
    }

    public void setRestrictResultsAsInteger(Integer i) {
        _restrictResults = i;
    }

    public String getSubjectId() {
        return _subjectId;
    }

    public void setSubjectId(String subjectId) {
        _subjectId = subjectId;
    }

    public void setDomainIdAsInteger(Integer i) {
        _domainId = i;
    }

    public Integer getDomainIdAsInteger() {
        return _domainId;
    }

    public Password getPasswordEncryptor() {
        return _passwordEncryptor;
    }

    public boolean hasFullName() {
        return StringUtilities.hasContent(getFullName());
    }

    protected boolean validatePassword(String password, int maxAttemptsAllowed) {
        boolean result = isPasswordMatch(password);
        if (!result && (maxAttemptsAllowed > 0)) {
            setInvalidLogonCount(getInvalidLogonCount() + 1);
        } else {
            setInvalidLogonCount(0);
        }
        return result;
    }

    private boolean isPinMatch(String pin) {
        return isEncryptedMatch(getTrimmedPin(), pin);
    }

    private boolean isPasswordMatch(String password) {
        return isEncryptedMatch(getTrimmedPassword(), password);
    }


    private boolean isEncryptedMatch(String x, String y) {
        for (Iterator<Object> i = getAllPossibleEncryptors().iterator(); i.hasNext();) {
            Password encryptor = (Password) i.next();
            if (equals(x, encryptor.encrypt(y)) || equals(encryptor.encrypt(x), y)) {
                return true;
            }
        }
        return false;
    }

    private boolean equals(String x, String y) {
        return StringUtilities.equals(x, y);
    }

    private Collection<Object> getAllPossibleEncryptors() {
        Map<String, Object> map = new HashMap<String, Object>();
        putEncryptor(map, getPasswordEncryptor());
        putEncryptor(map, PasswordPassThru.class);
        putEncryptor(map, PasswordMD5.class);
        return map.values();
    }

    private void putEncryptor(Map<String, Object> map, Password encryptor) {
        if (encryptor != null) {
            map.put(encryptor.getClass().getName(), encryptor);
        }
    }

    private void putEncryptor(Map<String, Object> map, Class< ? > encryptor) {
        if (!map.containsKey(encryptor.getName())) {
            map.put(encryptor.getName(), ReflectionUtilities.newInstance(encryptor));
        }
    }

    public String getDisplayName() {
        return StringUtilities.hasContent(getFullName()) ? getFullName() : getLoginId();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User castOther = (User) other;

        Object otherId = castOther.getIdentity();
        if ((_identity == null) || (otherId == null)) {
            String otherLoginId = castOther.getLoginId();
            if ((_loginId == null) || (otherLoginId == null)) {
                return false;
            }
            return otherLoginId.trim().equalsIgnoreCase(_loginId.trim());
        }

        return _identity.equals(otherId);
    }

    @Override
    public int hashCode() {
        if ((_identity == null) && (_loginId == null)) {
            return System.identityHashCode(this);
        }
        if (_identity != null) {
            return _identity.hashCode();
        }
       return _loginId.hashCode();
    }
}
