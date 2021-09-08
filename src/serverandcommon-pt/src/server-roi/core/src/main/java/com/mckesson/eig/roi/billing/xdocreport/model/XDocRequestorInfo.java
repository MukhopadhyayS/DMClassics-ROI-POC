/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2014 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.xdocreport.model;

import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Mar 17, 2014
 * @since  HPF 16.2 [ROI]; Mar 17, 2014
 */
public class XDocRequestorInfo {

    private String _id = StringUtilities.EMPTYSTRING;
    private String _name = StringUtilities.EMPTYSTRING;
    private String _type = StringUtilities.EMPTYSTRING;
    private String _typeId = StringUtilities.EMPTYSTRING;
    private String _phone = StringUtilities.EMPTYSTRING;
    private String _homePhone = StringUtilities.EMPTYSTRING;
    private String _workPhone = StringUtilities.EMPTYSTRING;
    private String _cellPhone = StringUtilities.EMPTYSTRING;
    private String _address1 = StringUtilities.EMPTYSTRING;
    private String _address2 = StringUtilities.EMPTYSTRING;
    private String _address3 = StringUtilities.EMPTYSTRING;
    private String _city = StringUtilities.EMPTYSTRING;
    private String _state = StringUtilities.EMPTYSTRING;
    private String _postalCode = StringUtilities.EMPTYSTRING;
    private String _country = StringUtilities.EMPTYSTRING;
    private String _countryCode = StringUtilities.EMPTYSTRING;
    private String _createdDate = StringUtilities.EMPTYSTRING;
    
    public XDocRequestorInfo(RequestorInfo requestorInfo) {
        
        if (requestorInfo == null) {
            return;
        }
        
        //Setting requestor details.
        setId(StringUtilities.safe(requestorInfo.getId()));
        setName(StringUtilities.safe(requestorInfo.getName()));
        setType(StringUtilities.safe(requestorInfo.getType()));
        setPhone(StringUtilities.safe(requestorInfo.getPhone()));
        setHomePhone(StringUtilities.safe(requestorInfo.getHomePhone()));
        setWorkPhone(StringUtilities.safe(requestorInfo.getWorkPhone()));
        setCellPhone(StringUtilities.safe(requestorInfo.getCellPhone()));
        setAddress1(StringUtilities.safe(requestorInfo.getAddress1()));
        setAddress2(StringUtilities.safe(requestorInfo.getAddress2()));
        setAddress3(StringUtilities.safe(requestorInfo.getAddress3()));
        setCity(StringUtilities.safe(StringUtilities.safe(requestorInfo.getCity())));
        setState(StringUtilities.safe(requestorInfo.getState()));
        setPostalCode(StringUtilities.safe(requestorInfo.getPostalCode()));
        setCountry(StringUtilities.safe(requestorInfo.getCountry()));
        setCountryCode(StringUtilities.safe(requestorInfo.getCountryCode()));
        setCreatedDate(StringUtilities.safe(requestorInfo.getCreatedDate()));   
    }

    public String getId() { return _id; }
    public void setId(String id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getPhone() { return _phone; }
    public void setPhone(String phone) { _phone = phone; }

    public String getAddress1() { return _address1; }
    public void setAddress1(String address1) { _address1 = address1; }

    public String getAddress2() { return _address2; }
    public void setAddress2(String address2) { _address2 = address2; }

    public String getAddress3() { return _address3; }
    public void setAddress3(String address3) { _address3 = address3; }

    public String getCity() { return _city; }
    public void setCity(String city) { _city = city; }

    public String getState() { return _state; }
    public void setState(String state) { _state = state; }

    public String getPostalCode() { return _postalCode; }
    public void setPostalCode(String postalCode) { _postalCode = postalCode; }

    public String getHomePhone() { return _homePhone; }
    public void setHomePhone(String homePhone) { _homePhone = homePhone; }

    public String getWorkPhone() { return _workPhone; }
    public void setWorkPhone(String workPhone) { _workPhone = workPhone; }

    public String getCellPhone() { return _cellPhone; }
    public void setCellPhone(String cellPhone) { _cellPhone = cellPhone; }

    public String getCountry() { return _country; }
    public void setCountry(String country) { _country = country; }

    public String getCountryCode() { return _countryCode; }
    public void setCountryCode(String countryCode) { _countryCode = countryCode; }

    public String getTypeId() { return _typeId; }
    public void setTypeId(String typeId) { _typeId = typeId; }

    public String getCreatedDate() { return _createdDate; }
    public void setCreatedDate(String createdDate) { _createdDate = createdDate; }
}
