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



/**
 * @author Ganeshram
 * @date   Jun 17, 2008
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class UserSecurity implements java.io.Serializable {

	private int _userId;
	private String _facility;
	private int _securityId;

	public static final String ENTERPRISE = "E_P_R_S";
	public static final int ROI_VIP_STATUS = 6106;
    private static final int SEVENTEEN = 17;
    private static final int THIRTY_SEVEN = 37;


	public UserSecurity() {
	}

	public UserSecurity(int userId, String facility, int securityId) {
        _userId = userId;
        _facility = facility;
        _securityId = securityId;
    }

    public int getUserId() { return _userId; }
    public void setUserId(int userId) { _userId = userId; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public int getSecurityId() { return _securityId; }
    public void setSecurityId(int securityId) { _securityId = securityId; }

    @Override
    public boolean equals(Object other) {

        if ((other == null)) {
            return false;
        }
        if (!(other instanceof UserSecurity)) {
            return false;
        }
        UserSecurity castOther = (UserSecurity) other;

        return (getUserId() == castOther.getUserId())
                && ((getFacility() != null) && (castOther.getFacility() != null)
                                && getFacility().equals(castOther.getFacility()))
                && (getSecurityId() == castOther.getSecurityId());
    }

    @Override
    public int hashCode() {
        int result = SEVENTEEN;

        result = THIRTY_SEVEN * result + getUserId();
        result = THIRTY_SEVEN * result + (getFacility() == null ? 0 : getFacility().hashCode());
        result = THIRTY_SEVEN * result + getSecurityId();
        return result;
    }

}
