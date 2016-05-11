/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.billing.letter.model;

import java.util.List;


/**
 * @author OFS
 * @date   Feb 19, 2009
 * @since  HPF 13.1 [ROI]; Feb 19, 2009
 */
public class RequestorInfo {

    private String _id;
    private String _name;
    private String _type;
    private String _typeId;
    private String _phone;
    private String _homePhone;
    private String _workPhone;
    private String _cellPhone;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _state;
    private String _postalCode;
    private String _country;
    private String _countryCode;
    private String _createdDate;

    private List<RequestorTransaction> _transactions;
    private RequestorAccount _account;
    private RequestorAging _aging;

    public String getId() { return _id; }
    public void setId(String id) { _id = id; }

    public String getName() { return _name; }
    public void setName(String name) { _name = name; }

    public String getType() { return _type; }
    public void setType(String type) { _type = type; }

    public String getPhone() { return _phone; }
    public void setPhone(String phone) { _phone = phone; }

    public List<RequestorTransaction> getTransactions() { return _transactions; }
    public void setTransactions(List<RequestorTransaction> transactions) {
        _transactions = transactions;
    }

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

    public RequestorAccount getAccount() { return _account; }
    public void setAccount(RequestorAccount account) { _account = account; }

    public RequestorAging getAging() { return _aging; }
    public void setAging(RequestorAging aging) { _aging = aging; }

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
