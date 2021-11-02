///*
// * Created on Mar 16, 2005
// *
// * Copyright  2004 McKesson Corporation and/or one of its
// * subsidiaries. All Rights Reserved.
// *
// * Use of this material is governed by a license agreement.
// * This material contains confidential, proprietary and trade
// * secret information of McKesson Information Solutions and is
// * protected under United States and international copyright and
// * other intellectual property laws. Use, disclosure,
// * reproduction, modification, distribution, or storage in a
// * retrieval system in any form or by any means is prohibited
// * without the prior express written permission of McKesson
// * Information Solutions.
// */
//package com.mckesson.eig.roi.authorization;
//
//import com.mckesson.eig.utility.util.StringUtilities;
//
///**
// * AuthenticatedResult contains the resulting state of an authentication event.
// * This information includes success or failure, related message information, a
// * session ticket, a user identifier, an auto logoff period (in minutes) and a
// * session identifier.
// *
// * This is a mapped class that corresponds to the AuthenticationResult class in
// * the HA security.
// *
// * @version
// * @author
// */
//public class AuthenticatedResult implements java.io.Serializable {
//
//    /**
//     * Successful authentication.
//     */
//    public static final int AUTHENTICATED = 0;
//    /**
//     * Failed authentication.
//     */
//    public static final int AUTHENTICATION_FAILED = 1;
//    /**
//     * Successful authentication, the user is warned their password will soon
//     * expire
//     */
//    //hardcoded password veracode fix changing Password varibale to PD
//    public static final int AUTHENTICATED_EXPIRING_PD = 2;
//    /**
//     * Successful authentication. the user's password has already expired but a
//     * grace login has been allowed
//     */
//    public static final int AUTHENTICATED_GRACE_LOGIN = 3;
//    static final String PD_DISABLED_MSG_STR = "Account is not enabled";
//    static final String PD_LOCKED_MSG_STR = "Account is locked out";
//    static final String PD_IS_EXPIRED_MSG_STR = "password is expired";
//    static final String PD_HAS_EXPIRED_MSG_STR = "password has expired";
//
//    private String _additionalInformation;
//    private Integer _state;
//    private String _ticket;
//    private String _userIdentifier;
//
//    /**
//     * Returns the value of the variable additionalInformation.
//     *
//     * @return the value of the variable additionalInformation.
//     */
//    public String getAdditionalInfo() {
//        return _additionalInformation;
//    }
//
//    /**
//     * Sets the value of the variable additionalInformation.
//     *
//     * @param value
//     *            the new variable value.
//     */
//    public void setAdditionalInfo(String additionalInfo) {
//        _additionalInformation = additionalInfo;
//    }
//
//    /**
//     * Returns the value of the variable state.
//     *
//     * @return the value of the variable state.
//     */
//    public Integer getState() {
//        if (_state != null) {
//            return _state;
//        }
//        return AUTHENTICATION_FAILED;
//    }
//
//    /**
//     * Sets the value of the variable state.
//     *
//     * @param value
//     *            the new variable value.
//     */
//    public void setState(Integer state) {
//        _state = state;
//    }
//
//    /**
//     * Returns the value of the variable ticket.
//     *
//     * @return the value of the variable ticket.
//     */
//    public String getTicket() {
//        return _ticket;
//    }
//
//    /**
//     * Sets the value of the variable ticket.
//     *
//     * @param value
//     *            the new variable value.
//     */
//    public void setTicket(String ticket) {
//        _ticket = ticket;
//    }
//
//    /**
//     * Returns the value of the variable userIdentifier.
//     *
//     * @return the value of the variable userIdentifier.
//     */
//    public String getUserId() {
//        return _userIdentifier;
//    }
//
//    /**
//     * Sets the value of the variable userIdentifier.
//     *
//     * @param value
//     *            the new variable value.
//     */
//    public void setUserId(String userId) {
//        _userIdentifier = userId;
//    }
//    /**
//     * Helper method to determine if the account is disabled
//     */
//    public boolean isDisabled() {
//        return (getState().intValue() == AUTHENTICATION_FAILED
//                && StringUtilities.equals(
//                        getAdditionalInfo(), PD_DISABLED_MSG_STR));
//    }
//
//
//    /**
//     * Helper method to determine if the account is locked
//     */
//    public boolean isLocked() {
//        return (getState().intValue() == AUTHENTICATION_FAILED
//                && StringUtilities.equals(
//                        getAdditionalInfo(), PD_LOCKED_MSG_STR));
//    }
//
//    /**
//     * Help method to determine if the account is expired
//     */
//    public boolean isExpired() {
//        return (getState().intValue() == AUTHENTICATION_FAILED
//                && (StringUtilities.containsIgnoreCaseWithTrim(getAdditionalInfo(),
//                        PD_IS_EXPIRED_MSG_STR)
//                || StringUtilities.containsIgnoreCaseWithTrim(getAdditionalInfo(),
//                        PD_HAS_EXPIRED_MSG_STR)))
//                || getState().intValue() == AUTHENTICATED_GRACE_LOGIN;
//    }
//
//    /**
//     * Helper method to determine if the user authenticated
//     */
//    public boolean isAuthenticated() {
//        return getState() != AUTHENTICATION_FAILED && getState() != AUTHENTICATED_GRACE_LOGIN;
//    }
//
//    /**
//     * Help method to determine if the account password expiring
//     */
//    public boolean isPdExpiring() {
//        return getState().intValue() == AUTHENTICATED_EXPIRING_PD;
//    }
//
//    /**
//     * Returns formated string
//     */
//    public String toString() {
//        return "state:" + _state + "\nadditionalInfo:" + _additionalInformation
//                + "\nticket:" + _ticket + "\nuserId:" + _userIdentifier;
//    }
//}
