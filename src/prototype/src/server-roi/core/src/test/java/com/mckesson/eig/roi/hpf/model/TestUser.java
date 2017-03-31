/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.hpf.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mckesson.eig.roi.base.api.ROIException;

import junit.framework.TestCase;

public class TestUser extends TestCase {

    private static final Integer USER_INSTANCE_ID = new Integer(100);
    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "password   ";
    private static final String FULLNAME = "John Smith   ";
    private static final String PIN = "Admin";
    private static final String HOST = "localhost";
    private static final String IP_ADDRESS = "127.0.0.1";

    private static final int RESTRICT_RESULT = 2;
    private static final String TEXT_NAME = "Dr. John Smith";
    private static final String INITIAL = "JES";
    private static final String SUBJECTID = "jsmith@domain";
    private static final int FOUR = 4;
    private static final int HUNDRED = 100;
    private static final int THREE = 3;
    private static final int DOMAIN_ID = 5;
    private static final boolean ACCOUNT_DISABLED = false;
    private static final boolean ADMINISTRATOR = true;
    private static final String FACILITY = "A";
    private static final List<String> FACILITIES = new ArrayList<String>();
    private static final boolean EPN_ENABLED = false;
    private static final String EPN_PREFIX = "E";
    private static final String RV_GROUP = "RV";
    private static final boolean MASK_SSN = false;
    private static final String MASK_BY = "Dr. John Smith";
    private static final Integer SECURITY_ID = new Integer(100);
    private User _user;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FACILITIES.add(FACILITY);
        FACILITIES.add("AD");
        _user = new User();
        _user.setInstanceId(USER_INSTANCE_ID);
        _user.setLoginId(USERNAME);
        _user.setPassword(PASSWORD);
        _user.setFullName(FULLNAME);
        _user.setPin(PIN);
        _user.setSigninHost(HOST);
        _user.setSigninAddress(IP_ADDRESS);
        _user.setRestrictResults(RESTRICT_RESULT);
        _user.setTextName(TEXT_NAME);
        _user.setInitial(INITIAL);
        _user.setSubjectId(SUBJECTID);
        _user.setDomainIdAsInteger(DOMAIN_ID);
        _user.setFacilities(FACILITIES);
        _user.setEpnEnabled(EPN_ENABLED);
        _user.setEpnPrefix(EPN_PREFIX);
        _user.setRvGroup(RV_GROUP);
        _user.setFreeFormFacilities(FACILITIES);
        _user.setMaskSSN(MASK_SSN);
        _user.setMaskBy(MASK_BY);

    }

    @Override
    protected void tearDown() throws Exception {
        _user = null;
        super.tearDown();
    }

    public void testConstructorWithUserIdAndPasswordAndFullName() {
        User u = new User(USERNAME, PASSWORD, FULLNAME);
        assertEquals(USERNAME, u.getLoginId());
        assertEquals(PASSWORD, u.getPassword());
        assertEquals(FULLNAME, u.getFullName());
    }

    public void testAccessors() {
        assertEquals(USER_INSTANCE_ID, _user.getInstanceId());
        assertEquals(USER_INSTANCE_ID.intValue(), _user.getInstanceIdValue());
        assertEquals(USERNAME, _user.getLoginId());
        assertEquals(PASSWORD, _user.getPassword());
        assertEquals(PASSWORD.trim(), _user.getTrimmedPassword());
        assertEquals(FULLNAME, _user.getFullName());
        assertEquals(FULLNAME.trim(), _user.getTrimmedFullName());
        assertEquals(PIN, _user.getTrimmedPin());
        assertEquals(HOST, _user.getSigninHost());
        assertEquals(IP_ADDRESS, _user.getSigninAddress());
        assertEquals(HOST + " (" + IP_ADDRESS + ")", _user.getLocation());
        assertEquals(RESTRICT_RESULT, _user.getRestrictResults());
        assertEquals(TEXT_NAME, _user.getTextName());
        assertEquals(INITIAL, _user.getTrimmedInitial());
        assertEquals(SUBJECTID, _user.getSubjectId());

        _user.setAccountDisabledAsInteger(new Integer(1));
        assertEquals(new Integer(1), _user.getAccountDisabledAsInteger());
        assertTrue(_user.isAccountDisabled());

        _user.setInstanceId(null);
        assertNull(_user.getInstanceId());
        assertEquals(-1, _user.getInstanceIdValue());

        assertNull(_user.getIdle());
        assertEquals(0, _user.getIdleTime().intValue());
        _user.setIdle(new Integer(FOUR));
        assertEquals(FOUR, _user.getIdle().intValue());
        assertEquals(FOUR, _user.getIdleTime().intValue());
        
        assertEquals(FACILITY, _user.getFacilities().get(0));
        assertEquals(EPN_ENABLED, _user.getEpnEnabled());
        assertEquals(EPN_PREFIX, _user.getEpnPrefix());
        assertEquals(RV_GROUP, _user.getRvGroup());
        assertEquals(FACILITY, _user.getFreeFormFacilities().get(0));
        assertEquals(MASK_SSN, _user.getMaskSSN());
        assertEquals(MASK_BY, _user.getMaskBy());
    }

    public void testValidPin() {
        assertTrue(_user.validatePin(PIN));
    }

    public void testNoPin() {
        User u = new User();
        u.setPin(null);
        assertTrue(u.validatePin("foo bar"));
    }

    public void testInvalidPin() {
        assertFalse(_user.validatePin("9999"));
    }

    public void testValidPassword() {
        assertTrue(_user.validatePassword(PASSWORD.trim(), 1));
    }

    public void testInvalidPassword() {
        assertFalse(_user.validatePassword("invalid", 1));
    }

    public void testValidatePasswordIsCaseSensitive() {
        assertFalse(_user.validatePassword("Password", 1));
    }

    public void testIsPhysician() {
        assertTrue(_user.getIsPhysician());

        User u = new User();
        u.setLoginId("J Edgar User");
        u.setPassword("ab");
        assertFalse(u.getIsPhysician());

        u.setPin("");
        assertFalse(u.getIsPhysician());

        u.setPin("   ");
        assertFalse(u.getIsPhysician());

        u.setPin("1234");
        assertTrue(u.getIsPhysician());
    }

    public void testIsAdministrator() {
        assertNull(_user.getAdministratorAsBoolean());
        assertFalse(_user.isAdministrator());

        _user.setAccountDisabled(false);
        _user.setAdministratorAsBoolean(Boolean.TRUE);
        assertTrue(_user.getAdministratorAsBoolean().booleanValue());
        assertTrue(_user.isAdministrator());

        _user.setAccountDisabled(false);
        _user.setAdministratorAsBoolean(Boolean.FALSE);
        assertFalse(_user.getAdministratorAsBoolean().booleanValue());
        assertFalse(_user.isAdministrator());

        _user.setAccountDisabled(true);
        _user.setAdministratorAsBoolean(Boolean.TRUE);
        assertFalse(_user.isAdministrator());

        _user.setAccountDisabled(true);
        _user.setAdministratorAsBoolean(Boolean.FALSE);
        assertFalse(_user.getAdministratorAsBoolean().booleanValue());
        assertFalse(_user.isAdministrator());
    }

    public void testAllowViewAssignment() {
        assertTrue(_user.allowViewAssignment("A"));
    }

    public void testInvalidLoginCount() {
        User u = new User();
        u.setInvalidLogonCount(FOUR);
        assertEquals(FOUR, u.getInvalidLogonCount());
    }

    public void testOverrideRestricionLevel() {
        _user.setOverrideRestrictionLevel(0);
        assertEquals(0, _user.getOverrideRestrictionLevel());
        assertTrue(_user.doNotAllowRestrictionOverrides());
        assertFalse(_user.allowImmediateRestrictionOverrides());
        assertFalse(_user.allowRequestedRestrictionOverrides());
        assertFalse(_user.allowRestrictionOverrides());

        _user.setOverrideRestrictionLevel(1);
        assertFalse(_user.doNotAllowRestrictionOverrides());
        assertTrue(_user.allowRequestedRestrictionOverrides());
        assertFalse(_user.allowImmediateRestrictionOverrides());
        assertTrue(_user.allowRestrictionOverrides());

        _user.setOverrideRestrictionLevel(2);
        assertFalse(_user.doNotAllowRestrictionOverrides());
        assertFalse(_user.allowRequestedRestrictionOverrides());
        assertTrue(_user.allowImmediateRestrictionOverrides());
        assertTrue(_user.allowRestrictionOverrides());
    }

    public void testUserValidation() {
        User u = new User("RHR_MK", "passwd", "Test User");
        u.validateLogin("passwd", THREE);
        assertEquals(User.INVALID_USER, u.getValidateCode());

        u.setInstanceId(new Integer(-1));
        u.validateLogin("passwd", THREE);
        assertEquals(User.INVALID_USER, u.getValidateCode());

        u.setInstanceId(new Integer(HUNDRED));
        u.validateLogin("passwd", THREE);
        assertEquals(User.VALID_USER, u.getValidateCode());
        u.validateLogin("invalid", 0);
        assertEquals(User.INVALID_USER_PASSWORD, u.getValidateCode());
        u.validateLogin("xxx", THREE);
        assertEquals(User.INVALID_USER_PASSWORD, u.getValidateCode());
        u.setAccountDisabled(true);
        u.validateLogin("passwd", THREE);
        assertEquals(User.USER_DISABLED, u.getValidateCode());
        u.setAccountDisabled(false);
        u.setInvalidLogonCount(THREE);
        u.validateLogin("passwd", THREE);
        assertEquals(User.USER_LOCKED_OUT, u.getValidateCode());

        u.setChangePassword(0);
        u.setInvalidLogonCount(0);
        u.validateLogin("passwd", THREE);
        assertEquals(User.VALID_USER, u.getValidateCode());
        u.setChangePassword(2);
        u.validateLogin("passwd", THREE);
        assertEquals(User.CHANGE_PASSWORD, u.getValidateCode());
    }

    // public void testValidateNewPassword() {
    // User u = new User("test", "passwd", "Test User");
    // String[] strings = new String[1];
    //
    // u.validateNewPassword(null, null, "12");
    // assertEquals(
    // User.NEWPASSWORD_OLD_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("", "", "12");
    // assertEquals(
    // User.NEWPASSWORD_OLD_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", null, "12");
    // assertEquals(
    // User.NEWPASSWORD_NEW_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "", "12");
    // assertEquals(
    // User.NEWPASSWORD_NEW_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "12", null);
    // assertEquals(
    // User.NEWPASSWORD_RETYPE_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "12", "");
    // assertEquals(
    // User.NEWPASSWORD_RETYPE_EMPTY,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "passwd", "passwd");
    // assertEquals(
    // User.NEWPASSWORD_SAME,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "abs", "xyz");
    // assertEquals(
    // User.NEWPASSWORD_NOT_MATCH,
    // u.getValidateCode());
    //
    // u.validateNewPassword("invalid", "new-passwd", "new-passwd");
    // assertEquals(
    // User.NEWPASSWORD_INVALID_OLD,
    // u.getValidateCode());
    //
    // u.validateNewPassword("passwd", "new-passwd", "new-passwd");
    // assertEquals(
    // User.NEWPASSWORD_VALID,
    // u.getValidateCode());
    // assertEquals(null, strings[0]);
    // }

    public void testEquals() {
        User first = new User();
        User second = new User();

        assertEquals(first, first);
        assertFalse(first.equals(second));
        assertFalse(first.equals(new Object()));

        first = new User("foo", "bar");
        assertFalse(first.equals(second));

        second = new User("foo", "bar");
        assertEquals(first, second);
        assertEquals(second, first);

        first = new User("user", "pwd");
        second = new User("foo", "bar");
        assertFalse(first.equals(second));
        assertFalse(first.equals(second));
        assertFalse(second.equals(first));
        assertFalse(second.equals(first));

        first = new User();
        second = new User();

        first.setInstanceId(new Integer(1));
        assertFalse(first.equals(second));

        second.setInstanceId(new Integer(1));
        assertEquals(first, second);

        second.setInstanceId(new Integer(2));
        assertFalse(first.equals(second));

        second = null;
        assertFalse(first.equals(second));
    }

    public void testSetIdentity() {
        _user.setIdentity(new Integer(HUNDRED));
        assertEquals(new Integer(HUNDRED), _user.getIdentity());
    }

    public void testHasFullName() {
        assertNotNull(_user.getFullName());
        assertTrue(_user.hasFullName());
        _user.setFullName("");
        assertEquals("", _user.getFullName());
        assertFalse(_user.hasFullName());
    }

    public void testConstructorWithUserId() {
        User u = new User(USERNAME);
        assertEquals(USERNAME, u.getLoginId());
    }

    public void testConstructorWithInstanceId() {
        User u = new User(USER_INSTANCE_ID);
        assertEquals(USER_INSTANCE_ID, u.getInstanceId());
    }

    public void testConstructorWithInstanceIdAndLoginIdAndPassword() {
        User u = new User(USER_INSTANCE_ID, USERNAME, PASSWORD);
        assertEquals(USER_INSTANCE_ID, u.getInstanceId());
        assertEquals(USERNAME, u.getLoginId());
        assertEquals(PASSWORD, u.getPassword());
    }

    public void testConstructorWithUserIdAndPassword() {
        User u = new User(USERNAME, PASSWORD);
        assertEquals(USERNAME, u.getLoginId());
        assertEquals(PASSWORD, u.getPassword());
    }

    public void testGetDomainIdAsInteger() {
        assertEquals(Integer.valueOf(DOMAIN_ID), _user.getDomainIdAsInteger());
    }

    public void testGetRoles() {
        _user.setAccountDisabled(ACCOUNT_DISABLED);
        _user.setAdministrator(ADMINISTRATOR);
        assertNotNull(_user.getRoles());
    }

    public void testSecurity() {
        _user.setSecurityRightsMap(null);
        assertNull(_user.getSecurityRightsMap());
    }

    public void testSecondaryIdentity() {

        assertEquals(USERNAME, _user.getSecondaryIdentity());
    }

    public void testIsRolesEnabled() {
        assertFalse(_user.isRoleEnabled("Administrators"));
    }

    public void testGetDisplayName() {
        assertNotNull(_user.getDisplayName());
    }

    public void testValidateCode() {
        assertNotNull(User.isValid(_user));
    }

    public void testHashCode() {
        assertNotNull(_user.hashCode());
        _user.setDomainIdAsInteger(null);
        assertNotNull(_user.hashCode());
        _user.setLoginId(null);
        assertNotNull(_user.hashCode());
    }
    
    public void testTransferRightsToMap() {
        
        try {
            UserSecurity userSecurity = new UserSecurity();
            userSecurity.setFacility(UserSecurity.ENTERPRISE);
            userSecurity.setSecurityId(SECURITY_ID);
            userSecurity.setUserId(USER_INSTANCE_ID);
            List<UserSecurity> rights = new ArrayList<UserSecurity>();
            rights.add(userSecurity);
            
            HashMap<String, HashMap<Integer, Boolean>> rightsMap = _user.transferRightsToMap(rights);
            _user.setSecurityRightsMap(rightsMap);
            boolean hasRights = _user.getSecurityRightsMap()
                    .get(UserSecurity.ENTERPRISE).get(USER_INSTANCE_ID);
            assertTrue(hasRights);
           
        } catch (ROIException e) {
            fail("TransferRightsToMap should not throw exception." + e.getErrorCode());
        }
        
    }
}
