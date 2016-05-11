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

package com.mckesson.eig.roi.requestor.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ValidationParams;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Dec 30, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */
public class Requestor
implements Serializable {

    private long _id;
    private String _lastName;
    private String _firstName;
    private long _type;
    private String _requestorType;
    private String _epn;
    private String _ssn;
    private Date _dob;
    private String _mrn;
    private String _facility;
    private boolean _freeFormFacility;
    private String _freeFormFacilityExists;
    private String _dateOfBirth;
    private boolean _prePayRequired;
    private boolean _certLetterRequired;
    private Address _mainAddress;
    private Address _altAddress;
    private String _homePhone;
    private String _workPhone;
    private String _cellPhone;
    private String _email;
    private String _fax;
    private String _contactName;
    private String _contactPhone;
    private String _contactEmail;
    private boolean _associated;
    private boolean _active;
    private boolean _frequent;
    private boolean _patient;

    private Set<RelatedContact> _relatedContacts;
    private Set<RelatedAddress> _relatedAddress;
    private Set<RelatedEmailPhone> _relatedEmailPhones;
    private Set<RequestorDetail> _requestorDetails;

    private long _createdBy;
    private long _modifiedBy;
    private Date _modifiedDate;
    private int _recordVersion;
    private String _salesTax;

    public Requestor() { }
    public Requestor(Long id) { _id = id; }

    public long getId() { return _id; }
    public void setId(long id) { _id = id; }

    public String getLastName() { return _lastName; }

    @ValidationParams (
                       isMandatory = true,
                       isMandatoryErrCode = ROIClientErrorCodes.REQUESTOR_NAME_IS_MANDATORY,
                       pattern = ROIConstants.NAME,
                       misMatchErrCode = ROIClientErrorCodes.REQUESTOR_NAME_CONTAINS_INVALID_CHAR,
                       maxLength = ROIConstants.REQUESTOR_NAME_MAX_LEN,
                       maxLenErrCode = ROIClientErrorCodes.REQUESTOR_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setLastName(String name) { _lastName = name; }

    public long getType() { return _type; }
    public void setType(long type) { _type = type; }

    public String getRequestorType() { return _requestorType; }
    public void setRequestorType(String type) { _requestorType = type; }

    public String getEpn() { return _epn; }
    @ValidationParams (
                       pattern = ROIConstants.SUPPLEMENTAL_DETAILS,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_PATIENT_EPN,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.
                           REQUESTOR_PATIENT_EPN_LENGTH_EXCEEDS_LIMIT)
    public void setEpn(String epn) { _epn = epn; }

    public String getSsn() { return _ssn; }
    @ValidationParams (
                       pattern = ROIConstants.SUPPLEMENTAL_DETAILS,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_PATIENT_SSN,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.
                           REQUESTOR_PATIENT_SSN_LENGTH_EXCEEDS_LIMIT)

    public void setSsn(String ssn) { _ssn = ssn; }

    public Date getDob() { return _dob; }
    public void setDob(Date dob) { _dob = dob; }

    public String getDateOfBirth() { return _dateOfBirth; }
    public void setDateOfBirth(String ofBirth) { _dateOfBirth = ofBirth; }

    public boolean isPrePayRequired() { return _prePayRequired; }
    public void setPrePayRequired(boolean payRequired) { _prePayRequired = payRequired; }

    public boolean isCertLetterRequired() { return _certLetterRequired; }
    public void setCertLetterRequired(boolean letterRequired) {
        _certLetterRequired = letterRequired;
    }

    public String getHomePhone() { return _homePhone; }
    @ValidationParams (
                       pattern = ROIConstants.PHONE,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_HOME_PHONE,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setHomePhone(String phone) { _homePhone = phone; }

    public String getWorkPhone() { return _workPhone; }
    @ValidationParams (
                       pattern = ROIConstants.PHONE,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_WORK_PHONE,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setWorkPhone(String phone) { _workPhone = phone; }

    public String getCellPhone() { return _cellPhone; }
    @ValidationParams (
                       pattern = ROIConstants.PHONE,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_CELL_PHONE,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.PHONE_LENGTH_EXCEEDS_LIMIT)

    public void setCellPhone(String phone) { _cellPhone = phone; }

    public String getEmail() { return _email; }
    @ValidationParams (
                       pattern = ROIConstants.MAIL,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_EMAIL,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.REQUESTOR_EMAIL_LENGTH_EXCEEDS_LIMIT)
    public void setEmail(String email) { _email = email; }

    public String getFax() { return _fax; }
    @ValidationParams (
                       pattern = ROIConstants.PHONE,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_FAX,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.REQUESTOR_FAX_LENGTH_EXCEEDS_LIMIT)
    public void setFax(String fax) { _fax = fax; }

    public String getContactName() { return _contactName; }
    @ValidationParams (
                       pattern = ROIConstants.NAME,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_CONTACT_NAME,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.
                                           REQUESTOR_CONTACT_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setContactName(String name) { _contactName = name; }

    public String getContactPhone() { return _contactPhone; }
    @ValidationParams (
                       pattern = ROIConstants.PHONE,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_CONTACT_PHONE_NUMBER,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.CONTACT_PHONE_LENGTH_EXCEEDS_LIMIT)
    public void setContactPhone(String phone) { _contactPhone = phone; }

    public String getContactEmail() { return _contactEmail; }
    @ValidationParams (
                       pattern = ROIConstants.MAIL,
                       misMatchErrCode = ROIClientErrorCodes.INVALID_EMAIL,
                       maxLength = ROIConstants.REQUESTOR_SUPPLEMENTAL_DETAILS_LENGTH,
                       maxLenErrCode = ROIClientErrorCodes.CONTACT_EMAIL_LENGTH_EXCEEDS_LIMIT)
    public void setContactEmail(String email) { _contactEmail = email; }

    public long getCreatedBy() { return _createdBy; }
    public void setCreatedBy(long by) { _createdBy = by; }

    public long getModifiedBy() { return _modifiedBy; }
    public void setModifiedBy(long by) { _modifiedBy = by; }

    public Date getModifiedDate() { return _modifiedDate; }
    public void setModifiedDate(Date date) { _modifiedDate = date; }

    public int getRecordVersion() { return _recordVersion; }
    public void setRecordVersion(int version) { _recordVersion = version; }

    public Set<RelatedContact> getRelatedContacts() {
        if (_relatedContacts == null) {
            _relatedContacts = new HashSet<RelatedContact>();
        }
        return _relatedContacts;
    }
    public void setRelatedContacts(Set<RelatedContact> contact) { _relatedContacts = contact; }

    public Address getMainAddress() { return _mainAddress; }
    public void setMainAddress(Address address) {

        _mainAddress = ((address != null) && (StringUtilities.hasContent(address.getAddress1())
                                          || StringUtilities.hasContent(address.getAddress2())
                                          || StringUtilities.hasContent(address.getAddress3())
                                          || StringUtilities.hasContent(address.getCity())
                                          || StringUtilities.hasContent(address.getPostalCode())
                                          || StringUtilities.hasContent(address.getState())
                                          || StringUtilities.hasContent(address.getCountryCode())
                                          || StringUtilities.hasContent(address.getCountryName())
                                          || (address.getCountrySeq() != null && address.getCountrySeq() != 0)))
                                          ? address
                                          : null;
    }

    public Address getAltAddress() { return _altAddress; }
    public void setAltAddress(Address address) {
        _altAddress = ((address != null) && (StringUtilities.hasContent(address.getAddress1())
                                         || StringUtilities.hasContent(address.getAddress2())
                                         || StringUtilities.hasContent(address.getAddress3())
                                         || StringUtilities.hasContent(address.getCity())
                                         || StringUtilities.hasContent(address.getPostalCode())
                                         || StringUtilities.hasContent(address.getState())
                                         || StringUtilities.hasContent(address.getCountryCode())
                                         || StringUtilities.hasContent(address.getCountryName())
                                         || (address.getCountrySeq() != null && address.getCountrySeq() != 0)))
                                         ? address
                                         : null;
    }

    public Set<RelatedAddress> getRelatedAddress() {
        if (_relatedAddress == null) {
            _relatedAddress = new HashSet<RelatedAddress>();
        }
        return _relatedAddress;
    }
    public void setRelatedAddress(Set<RelatedAddress> address) { _relatedAddress = address; }

    public Set<RelatedEmailPhone> getRelatedEmailPhones() {
        if (_relatedEmailPhones == null) {
            _relatedEmailPhones = new HashSet<RelatedEmailPhone>();
        }
        return _relatedEmailPhones;
    }
    public void setRelatedEmailPhones(Set<RelatedEmailPhone> emailPhones) {
        _relatedEmailPhones = emailPhones;
    }

    public Set<RequestorDetail> getRequestorDetails() {
        if (_requestorDetails == null) {
            _requestorDetails = new HashSet<RequestorDetail>();
        }
        return _requestorDetails;
    }
    public void setRequestorDetails(Set<RequestorDetail> details) { _requestorDetails = details; }

    public boolean isAssociated() { return _associated; }
    public void setAssociated(boolean associated) { _associated = associated; }

    public boolean isActive() { return _active; }
    public void setActive(boolean active) { _active = active; }

    public boolean isFrequent() { return _frequent; }
    public void setFrequent(boolean frequent) { _frequent = frequent; }

    public boolean isPatient() { return _patient; }
    public void setPatient(boolean patient) { _patient = patient; }

    @Override
    public String toString() {
        return "RequestorId: " + _id + " LastName: " + _lastName + "Type: " + _type;
    }

    /**
     * This method is to get the address of the specified type
     * @param type address type id
     * @return related address details
     */
    public RelatedAddress getRelatedAddressOfType(long type) {

        if (_relatedAddress != null) {
            for (RelatedAddress ra : _relatedAddress) {
                if (ra.getAddressType() == type) {
                    return ra;
                }
            }
        }
        return null;
    }

    /**
     * This method is to get the address of the specified type
     * @param type address type id
     * @return address details
     */
    public Address getAddressOfType(long type) {

        RelatedAddress ra = getRelatedAddressOfType(type);
        if (ra != null) {
            return ra.getAddress();
        } else {
            return null;
        }
    }

    /**
     * This method is to get the email, phone or fax of the specified type
     * @param type emailphone type id
     * @return related email phone details
     */
    public RelatedEmailPhone getRelatedEmailPhoneOfType(long type) {

        if (_relatedEmailPhones != null) {
            for (RelatedEmailPhone rep : _relatedEmailPhones) {
                if (rep.getEmailPhoneType() == type) {
                    return rep;
                }
            }
        }
        return null;
    }

    /**
     * This method is to get the email, phone or fax of the specified type
     * @param type emailphone type id
     * @return email,phone or fax value
     */
    public String getEmailPhoneOfType(long type) {

        RelatedEmailPhone rep = getRelatedEmailPhoneOfType(type);
        if (rep != null) {
            return rep.getEmailPhone().getEmailPhone();
        } else {
            return null;
        }
    }

    /**
     * This method is to get the contact of the specified type
     * @param type contact type id
     * @return related contact details
     */
    public RelatedContact getRelatedContactOfType(long type) {

        if (_relatedContacts != null) {
            for (RelatedContact rc : _relatedContacts) {
                if (rc.getContactType() == type) {
                    return rc;
                }
            }
        }
        return null;
    }

    /**
     * This method is to get the contact of the specified type
     * @param type contact type id
     * @return contact value
     */
    public String getContactOfType(long type) {

        RelatedContact rec = getRelatedContactOfType(type);
        if (rec != null) {
            return rec.getContact().getLastName();
        } else {
            return null;
        }
    }

    /**
     * This method is to get the contact's email or phone of the specified type
     * @param contactType contact type id
     * @param emailPhoneType emailphone type id
     * @return contact email phone details
     */
    public ContactEmailPhone getRelatedContactEmailPhoneOfType(long contactType,
                                                               long emailPhoneType) {

        RelatedContact rc = getRelatedContactOfType(contactType);
        if ((rc != null) && (rc.getContact() != null)
            && (rc.getContact().getContactEmailPhones() != null)) {
            for (ContactEmailPhone cep : rc.getContact().getContactEmailPhones()) {
                if (cep.getEmailPhoneType() == emailPhoneType) {
                    return cep;
                }
            }
        }
        return null;
    }

    /**
     * This method is to get the contact's email or phone of the specified type
     * @param contactType contact type id
     * @param emailPhoneType emailphone type id
     * @return email,phone or fax value
     */
    public String getContactEmailPhoneOfType(long contactType, long emailPhoneType) {

        ContactEmailPhone cep = getRelatedContactEmailPhoneOfType(contactType, emailPhoneType);
        if (cep != null) {
            return cep.getEmailPhone().getEmailPhone();
        } else {
            return null;
        }
    }

    /**
     * This method is to get the requestor details of the specified type
     * @param type Requestor detail type
     * @return Object
     */
    public Object getRequestorDetailOfType(String type) {

        RequestorDetail rd = getRelatedRequestorDetailOfType(type);
        if (rd != null) {
            if (rd.getSystemColumn().equalsIgnoreCase(type)) {
                if (rd.getSystemType().equalsIgnoreCase(ROIConstants.DB_TYPE_VARCHAR)) {
                    return rd.getValue();
                } else if (rd.getSystemType().equalsIgnoreCase(ROIConstants.DB_TYPE_DATETIME)
                            && (rd.getValue() != null)) {
                        return DateUtilities.
                            parseDate(new SimpleDateFormat(ROIConstants
                                                               .ROI_DATE_FORMAT),
                                                               _dateOfBirth);
                }
            }
        }
        return null;
    }

    /**
     * This method is to get the requestor details of the specified type
     * @param type Requestor detail type
     * @return RequestorDetail
     */
    public RequestorDetail getRelatedRequestorDetailOfType(String type) {

        if (_requestorDetails != null) {
            for (RequestorDetail rd : _requestorDetails) {
                if (rd.getSystemColumn().equalsIgnoreCase(type)) {
                    return rd;
                }
            }
        }
        return null;
    }

    /**
     * This method is to set the dob field by parsing the date
     * dateOfBirth
     * @param formatString formate of the data
     */
    @SuppressWarnings("static-access")
    public void setDob(String dateString, String formatString) {

        if (dateString == null) {
            setDob(null);
        } else {

         Date dt = DateUtilities.parseDate(new SimpleDateFormat(formatString), dateString);
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(dt);
         calendar.add(calendar.HOUR, ROIConstants.REQUESTOR_DOB_ADDED_HOURS);
         Date dob = calendar.getTime();
         setDob(dob);
        }
    }

    /**
     * This method is to check the whether the contact details are mapped or not
     * @return true if the condition satisfies else false
     */
    public boolean shouldMapContact() {
        if (!StringUtilities.isEmpty(_contactName) || !StringUtilities.isEmpty(_contactEmail)
            || !StringUtilities.isEmpty(_contactPhone)) {
            return true;
        }
        return false;
    }

    /**
     * This method is to convert date into string of the given format
     * @param formatString date format
     */
    public void formatDateOfBirth(String formatString) {
        if (_dob != null) {
            setDateOfBirth(new SimpleDateFormat(formatString).format(_dob));
        }
    }

    /**
     * This method creates the audit comments for creating requestor
     * @param name Requestor name
     * @param type Requestor type name
     * @return audit comments for creating the requestor
     */
    public String toCreateAudit(String name, String type) {
        return "ROI user " + name.trim() + " created new requestor " + _lastName + " for " + type;
    }

    /**
     * This method creates the audit comments for deleting requestor
     * @param name Requestor name
     * @param type Requestor type name
     * @return audit comments for deleting the requestor
     */
    public String toDeleteAudit(String name, String type) {
        return "ROI user " + name.trim() + " deleted requestor " + _lastName + " from " + type;
    }

    /**
     * This method creates the audit comments for update requestors
     * @param name Name of the user
     * @param old Requestor details
     * @param type Name of the requestor type
     * @return audit comments for updating the requestor
     */
    public String toUpdateAudit(String name, Requestor old, String type) {

        return new StringBuffer().append("ROI user ")
                                 .append(name)
                                 .append(" modified requestor data: ")
                                 .append(old.getLastName())
                                 .append(", ")
                                 .append(type)
                                 .append(", ")
                                 .append(old.isCertLetterRequired())
                                 .append(", ")
                                 .append(old.isPrePayRequired())
                                 .append(", ")
                                 .append(old.getMainAddress())
                                 .append(", ")
                                 .append(old.getAltAddress())
                                 .append("to ")
                                 .append(getLastName())
                                 .append(", ")
                                 .append(type)
                                 .append(", ")
                                 .append(isCertLetterRequired())
                                 .append(", ")
                                 .append(isPrePayRequired())
                                 .append(", ")
                                 .append(getMainAddress())
                                 .append(",")
                                 .append(getAltAddress())
                                 .toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Requestor)) {
            return false;
        }
        Requestor requestor = (Requestor) obj;
        return getId() == requestor.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public String getMrn() { return _mrn; }
    public void setMrn(String mrn) { _mrn = mrn; }

    public String getFacility() { return _facility; }
    public void setFacility(String facility) { _facility = facility; }

    public boolean isFreeFormFacility() { return _freeFormFacility; }
    public void setFreeFormFacility(boolean isFreeFormFacility) {
        _freeFormFacility = isFreeFormFacility;
    }

    public String getFreeFormFacilityExists() { return _freeFormFacilityExists; }
    public void setFreeFormFacilityExists(String formFacilityExists) {
        _freeFormFacilityExists = formFacilityExists;
    }

   public String getFirstName() { return _firstName; }

    @ValidationParams (
                       pattern = ROIConstants.NAME,
                       misMatchErrCode = ROIClientErrorCodes.REQUESTOR_NAME_CONTAINS_INVALID_CHAR,
                       maxLength = ROIConstants.REQUESTOR_NAME_MAX_LEN,
                       maxLenErrCode = ROIClientErrorCodes.REQUESTOR_NAME_LENGTH_EXCEEDS_LIMIT)
    public void setFirstName(String name) { _firstName = name; }

    public String getSalesTax() { return _salesTax; }
    public void setSalesTax(String salesTax) { _salesTax = salesTax; }

    /**
     * This method create audit commend for create event
     * @return audit comment for retrieve event
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
