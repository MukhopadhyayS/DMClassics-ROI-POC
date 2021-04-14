package com.mckesson.eig.roi.supplementary.model;

import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.request.model.FreeFormFacility;

public class ROISupplementalPatient
extends BaseModel{

    private static final long serialVersionUID = 1L;

    private String _mrn;
    private String _facility;
    private String _epn;
    private String _lastName;
    private String _firstName;
    private String _gender;
    private String _dateOfBirth;
    private String _ssn;
    private String _address1;
    private String _address2;
    private String _address3;
    private String _city;
    private String _state;
    private String _zip;
    private String _homephone;
    private String _workphone;
    private Boolean _vip;
    private String _countryCode;
    private boolean _newCountry;
    private String _countryName;
    private boolean _hasRequest;
    private FreeFormFacility _freeformFacility;

    public String getMrn() {
        return _mrn;
    }

    public void setMrn(String mrn) {
        _mrn = mrn;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getGender() {
        return _gender;
    }

    public void setGender(String gender) {
        _gender = gender;
    }

    public String getDateOfBirth() {
        return _dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        _dateOfBirth = dateOfBirth;
    }

    public String getSsn() {
        return _ssn;
    }

    public void setSsn(String ssn) {
        _ssn = ssn;
    }

    public String getEpn() {
        return _epn;
    }

    public void setEpn(String epn) {
        _epn = epn;
    }

    public String getFacility() {
        return _facility;
    }

    public void setFacility(String facility) {
        _facility = facility;
    }

    public String getAddress1() {
        return _address1;
    }

    public void setAddress1(String address1) {
        _address1 = address1;
    }

    public String getAddress2() {
        return _address2;
    }

    public void setAddress2(String address2) {
        _address2 = address2;
    }

    public String getAddress3() {
        return _address3;
    }

    public void setAddress3(String address3) {
        _address3 = address3;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public String getZip() {
        return _zip;
    }

    public void setZip(String zip) {
        _zip = zip;
    }

    public String getHomephone() {
        return _homephone;
    }

    public void setHomephone(String homephone) {
        _homephone = homephone;
    }

    public String getWorkphone() {
        return _workphone;
    }

    public void setWorkphone(String workphone) {
        _workphone = workphone;
    }

    public Boolean getVip() {
        return _vip;
    }

    public void setVip(Boolean vip) {
        _vip = vip;
    }

    public boolean isVip() {
        if (null == getVip()) {
            return false;
        }
        return getVip().booleanValue();
    }

    public String getCountryCode() {
        return _countryCode;
    }

    public void setCountryCode(String countryCode) {
        _countryCode = countryCode;
    }

    public boolean isNewCountry() {
        return _newCountry;
    }

    public void setNewCountry(boolean newCountry) {
        _newCountry = newCountry;
    }

    public String getCountryName() {
        return _countryName;
    }

    public void setCountryName(String countryName) {
        _countryName = countryName;
    }

    public boolean isHasRequest() {
        return _hasRequest;
    }

    public void setHasRequest(boolean hasRequest) {
        _hasRequest = hasRequest;
    }

    public FreeFormFacility getFreeformFacility() { return _freeformFacility; }
    public void setFreeformFacility(FreeFormFacility freeformFacility) {
        _freeformFacility = freeformFacility;
    }

    public void setFreeformFacilityCode(String facilityCode) {

        if  (null == facilityCode) {
            return;
        }
        if (null == _freeformFacility) {
            _freeformFacility = new FreeFormFacility();
        }
        _freeformFacility.setFreeFormFacilityName(facilityCode);

    }
    public String getFreeformFacilityCode() {

        if (null == _freeformFacility) {
            return null;
        }
        return _freeformFacility.getFreeFormFacilityName();
    }

    public void setFreeformFacilityId(long facilityId) {

        if (0 == facilityId) {
            return;
        }

        if (null == _freeformFacility) {
            _freeformFacility = new FreeFormFacility();
        }
        _freeformFacility.setId(facilityId);

    }
    public long getFreeformFacilityId() {

        if (null == _freeformFacility) {
            return 0;
        }
        return _freeformFacility.getId();
    }

    public void copy(ROISupplementalPatient p) {
        setMrn(p.getMrn());
        setFacility(p.getFacility());
        setEpn(p.getEpn());
        setLastName(p.getLastName());
        setFirstName(p.getFirstName());
        setGender(p.getGender());
        setDateOfBirth(p.getDateOfBirth());
        setSsn(p.getSsn());
        setAddress1(p.getAddress1());
        setAddress2(p.getAddress2());
        setAddress3(p.getAddress3());
        setCity(p.getCity());
        setState(p.getState());
        setZip(p.getZip());
        setHomephone(p.getHomephone());
        setWorkphone(p.getWorkphone());
        setVip(p.getVip());
        setCountryCode(p.getCountryCode());
        setNewCountry(p.isNewCountry());
        setCountryName(p.getCountryName());
        setHasRequest(p.isHasRequest());
        setFreeformFacility(p.getFreeformFacility());
    }

    /**
     * This method create audit commend for create event
     * @return audit comment for Retrieve event
     */
    public String constructSearchRetrieveAudit() {

        return new StringBuffer().append("Application : ROI. ")
                .append("The entry with \"")
                .append(_lastName + ", ")
                .append(_firstName)
                .append("\" was selected from the search results")
                .append(".")
                .toString();

    }
}