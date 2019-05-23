/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.admin.service;


import java.util.List;

import com.mckesson.eig.User;
import com.mckesson.eig.roi.admin.model.AttachmentLocation;
import com.mckesson.eig.roi.admin.model.Country;
import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.DeliveryMethodList;
import com.mckesson.eig.roi.admin.model.Designation;
import com.mckesson.eig.roi.admin.model.DocTypeAuditList;
import com.mckesson.eig.roi.admin.model.DocTypeDesignations;
import com.mckesson.eig.roi.admin.model.Gender;
import com.mckesson.eig.roi.admin.model.InvoiceDueDays;
import com.mckesson.eig.roi.admin.model.LetterTemplate;
import com.mckesson.eig.roi.admin.model.LetterTemplateList;
import com.mckesson.eig.roi.admin.model.Note;
import com.mckesson.eig.roi.admin.model.NotesList;
import com.mckesson.eig.roi.admin.model.OutputServerProperties;
import com.mckesson.eig.roi.admin.model.ROIAppData;
import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.Reasons;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.admin.model.RequestStatusMap;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.RequestorTypesList;
import com.mckesson.eig.roi.admin.model.SSNMask;
import com.mckesson.eig.roi.admin.model.Weight;



/**
 * This is the ROI Admin service to perform Delivery Method operations.
 *
 * @author ranjithr
 * @date   Jul 02, 2009
 * @since  HPF 13.1 [ROI]
 */
public interface ROIAdminService {

    /**
     * This method creates a new Delivery method and return the created
     * delivery method id
     *
     * @param deliveryMethod DeliveryMethod to be created
     * @return created delivery method id
     */
    long createDeliveryMethod(DeliveryMethod deliveryMethod);

    /**
     * This method retrieves an existing delivery method.
     *
     * @param deliveryMethodId Id of DeliveryMethod
     * @return delivery method
     */
    DeliveryMethod retrieveDeliveryMethod(long deliveryMethodId);

    /**
     * This method retrieves all the available delivery methods.
     *
     * @return List of delivery methods
     */
    DeliveryMethodList retrieveAllDeliveryMethods(boolean fetchDetails);

    /**
     * This method updates an existing delivery method.
     *
     * @param deliveryMethod DeliveryMethod details
     * @return delivery method
     */
    DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod);

    /**
     * This method deletes a delivery method.
     *
     * @param deliveryMethodId Id of the delivery method
     */
    void deleteDeliveryMethod(long deliveryMethodId);

    /**
     * This method retrieves an existing weight.
     *
     * @return retrieved weight.
     */
    Weight retrieveWeight();

    /**
     * This method updates an existing weight.
     *
     * @param wt updatedWeight
     * @return updated Weight
     */
    Weight updateWeight(Weight wt);

    /**
     * This method creates a new Reason and return the created
     * reason id
     *
     * @param reason Reason to be created
     * @return created reason id
     */
    long createReason(Reason reason);

    /**
     * This method retrieves an existing reasons by the type.
     *
     * @param reasonType type of the reasons to be retrieved
     * @return list of reasons
     */
    ReasonsList retrieveAllReasonsByType(String reasonType);

    /**
     * This method retrieves an existing reason.
     *
     * @param reasonId Id of the Reason
     * @return reason
     */
    Reason retrieveReason(long reasonId);

    /**
     * This method deletes a reason.
     *
     * @param reasonId Id of the reason
     */
    void deleteReason(long reasonId);

    /**
     * This method updates an existing reason
     *
     * @param reason Reason details
     * @return updated reason
     */
    Reason updateReason(Reason reason);

    /**
     * This method retrieves all the available request status
     *
     * @return status Map
     */
    RequestStatusMap retrieveAllRequestStatus();

     /**
     * This method creates a new RequstorType and returns the created
     * requestorTypeId
     * @param requestorType RequestorType to be created
     * @return created requestorType Id
     */
    RequestorType createRequestorType(RequestorType requestorType);

    /**
     * This method fetches the requstorType based on the requestorTypeId
     * @param requestorTypeId Id of RequestorType
     * @return Requestor type
     */
    RequestorType retrieveRequestorType(long requestorTypeId);

    /**
     * This method retrieves all the available requestorTypes.
     * @param boolean true/false
     * @return List of requestor types
     */
    RequestorTypesList retrieveAllRequestorTypes(boolean loadAssociations);

    /**
     * This method deletes a requstorType
     * @param requestorTypeId
     */
    void deleteRequestorType(long requestorTypeId);

    /**
     * This method updates existing requestorType.
     *
     * @param requestorType RequestorType details
     * @return updated Requestor Type
     */
    RequestorType updateRequestorType(RequestorType requestorType);

    /**
     * This method creates, updates, and deletes the designated document types
     * @param codeSetId id of the CodeSet
     * @param docTypes designated document types detail
     */
    void designateDocumentTypes(long codeSetId, DocTypeDesignations docType,DocTypeAuditList docTypeAuditList);

    /**
     * This method creates new LetterTemplate and returns the created
     * letterTemplateId
     * @param letterTemplate LetterTemplate to be created
     * @return created letterTemplateId
     */
    long createLetterTemplate(LetterTemplate letterTemplate);

    /**
     * This method fetches the letterTemplate based on the letterTemplateId
     * @param letterTemplateId Id of LetterTemplate
     * @return LetterTemplate
     */
    LetterTemplate retrieveLetterTemplate(long letterTemplateId);

    /**
     * This method retrieves all the available letterTemplates
     *
     * @return List of letterTemplates
     */
    LetterTemplateList retrieveAllLetterTemplates();

    /**
     * This method deletes a letterTemplate
     *
     * @param letterTemplateId
     */
    void deleteLetterTemplate(long letterTemplateId);

    /**
     * This method updates existing letterTemplate.
     *
     * @param letterTemplate LetterTemplate details
     * @return updated LetterTemplate
     */
    LetterTemplate updateLetterTemplate(LetterTemplate letterTemplate);

    /**
     * This method is to retrieve the reasons by status
     * @param statusId status id
     * @return List of reason names
     */
    Reasons retrieveReasonsByStatus(int statusId);

    /**
     * This method creates a new note
     * @param note Note to be created
     * @return created noteId
     */
    long createNote(Note note);

    /**
     * This method fetches a note based on the noteId
     * @param noteId Id of the Note
     * @return Note
     */
    Note retrieveNote(long noteId);

    /**
     * This method retrieve all the available notes
     * @param type type of the notes
     * @return list of Notes
     */
    NotesList retrieveAllNotes();

    /**
     * This method deletes a note based on the noteId
     * @param noteId id of Note
     */
    void deleteNote(long noteId);

    /**
     * This method updates a note
     * @param note Note details to be updated
     * @return updated Note
     */
    Note updateNote(Note note);

    /**
     * This method retrieves the ssn mask values
     * @return ssn mask
     */
    SSNMask retrieveSSNMask();

    /**
     * This method updates the ssn mask values based on the input
     * @param ssnMask SSNMask to be updated
     * @return ssn updated SSNMask
     */
    SSNMask updateSSNMask(SSNMask ssnMask);

    /**
     * This method fetches the designations based on the code set id
     * @param codeSetId code set id to be retrieve
     * @return DocTypeDesignations
     */
    DocTypeDesignations retrieveDesignations(long codeSetId);

    /**
     * This method is to retrieve the document types ids
     * @param docType
     * @return Designation details
     */
    Designation retrieveDocTypeIdsByDesignation(String docType);

    /**
     * This method check whether letter template is available for the letter type
     * @param letterType
     * @return
     */
    boolean hasLetterTemplate(String letterType);

    /**
     * This method is to load ROI Application specific global data
     * @return
     */
    ROIAppData retrieveROIAppData();

    /**
     * This method is used to get the logged in user object for external use.
     * @param userId
     * @return
     */
    User getUser(String userId);

    /**
     * This method is used to enable the output service form ROI application.
     * @param doEnable
     */
    void enableOutputService(boolean doEnable);

    /**
     * This method is used to retrieve output service properties.
     * @return
     */
    OutputServerProperties retrieveOutputServerProperties();

    /**
     * This Method is used to save output service properties.
     * @param outputServerProperties
     */
    void saveOutputServerProperties(OutputServerProperties outputServerProperties);

    void updateAttachmentLocation(AttachmentLocation attachmentLocation);

    AttachmentLocation retrieveAttachmentLocation();

    /**
     * This method retrieves an existing invoicedueDetails.
     *
     * @return retrieved invoicedueDetails.
     */
    InvoiceDueDays retrieveInvoiceDueDays();

    /**
     * This method updates an existing invoicedueDetails.
     *
     * @param invoiceDate updatedinvoicedueDetails
     * @return updated invoicedueDetails
     */
    InvoiceDueDays updateInvoiceDueDays(InvoiceDueDays invoiceDueDays);


	/**
     * This method is to retrieve the MU document types ids
     * 
     * @param
     * @return List<String>
     */
    List<String> retrieveMUDocTypes();
    
    /** 
     * This method is to update Country Code
     * 
     * @param countryCode 
     */
    void updateCountryCode(Country country);
    
    /**
     * Method retrieveAllCountries() fetches the master list
     * of countries.
     * @return List<Country>
     */
    List<Country> retrieveAllCountries();
    
    /** 
     * This method is used to update the Unbillable RequestFlag in SysParms_Global table
     * 
     * @param checked
     * 
     */
    void updateUnbillableRequestFlag(boolean checked);
    
    /** 
     * This method is used to retrieve the Unbillable RequestFlag in SysParms_Global table
     * 
     * 
     */
    boolean retrieveUnbillableRequestFlag(); 
    
    /**
     * This method is to retrieve all the gender details
     * 
     * @param
     * @return List<Gender>
     */
    List<Gender> retrieveAllGenders();
    
}
