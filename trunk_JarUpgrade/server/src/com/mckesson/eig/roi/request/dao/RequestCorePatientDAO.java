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


package com.mckesson.eig.roi.request.dao;

import java.util.List;
import java.util.Map;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.request.model.DeletePatientList;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;

/**
 * @author OFS
 * @date Jul 6, 2012
 * @since Jul 6, 2012
 *
 */
public interface RequestCorePatientDAO
extends ROIDAO {

    public enum RequestDSRMappingKeys {

        PATIENTS,
        ENCOUNTERS,
        DOCUMENTS,
        GLOBALDOCUMENTS,
        GLOBALDOCUMENTSVERSIONS,
        GLOBALDOCUMENTSPAGES,
        VERSIONS,
        PAGES,
        NONHPF_DOCUMENTS,
        ATTACHMENTS
    };


    /**
     * This method creates or update a patients Information
     * @param patient Request information
     * @return long value as a update status
     */
    <T extends BaseModel> void updatePatient(long requestId,
                                             List<RequestPatient> patient,
                                             T baseModel);

    /**
     * This method creates or update a encounters Information
     * @param encounter Request information
     */
    <T extends BaseModel> void updateEncounter(long requestId,
                                               List<RequestEncounter> encounter);

    /**
     * This method creates or update a encounters Information
     * @param encounter Request information
     */
    <T extends BaseModel> void updateGlobalDocuments(long requestId,
                                                     List<RequestDocument> globalDocuments);

    /**
     * This method creates or update a document Information
     * @param document Request information
     */
    <T extends BaseModel> void updateDocument(long requestId,
                                              List<RequestDocument> documents);

    /**
     * This method creates or update a page Information
     * @param page Request information
     */
    <T extends BaseModel> void updateVersion(long requestId,
                                             List<RequestVersion> versions);

    /**
     * This method creates or update a page Information
     * @param page Request information
     */
    <T extends BaseModel> void updateGlobalDocumentsVersion(long requestId,
                                                            List<RequestVersion> versions);

    /**
     * This method creates or update a page Information
     * @param page Request information
     * @return long value as a update status
     */
    <T extends BaseModel> void insertPage(long requestId,
                                          List<RequestPage> pages);

    /**
     * This method creates or update a page Information
     * @param page Request information
     * @return long value as a update status
     */
    <T extends BaseModel> void insertGlobalDocumentsPage(long requestId,
                                                         List<RequestPage> pages);

    /**
     * This method creates or update a page Information
     * @param page Request information
     * @return long value as a update status
     */
    <T extends BaseModel> void updatePage(long requestId,
                                          List<RequestPage> pages);

    /**
     * This method retrieves the request based on the requestId
     * @param id - patient Id
     * @return map of RequestPatients, RequestEncounters, RequestDocuments, RequestPages
     */
    Map<RequestDSRMappingKeys, List<? extends BaseModel>> retrieveRequestPatient(long requestId);

   /**
    * This method deletes the patient information
    * @param patientsInfo
    * @param requestId
    */
    void  removePatientInfo(DeletePatientList patientsInfo, long requestId);

    /**
     * This method creates or update a supplemental document Information
     * @param supplementaldocument Request information
     */
    <T extends BaseModel> void updateSupplementalDocument(long requestId,
            List<RequestSupplementalDocument> supplementalDocument, T baseModel);

    /**
     * This method creates or update a supplemental document Information
     * @param supplementalAttachment Request information
     */
    <T extends BaseModel> void updateSupplementalAttachment(long requestId,
            List<RequestSupplementalAttachment> supplementalAttachment, T baseModel);

    /**
     * This method Retrieves the list of patients that are related to the request.
     * @param requestId
     * @return list of patients
     */
    List<RequestPatient> retrieveAllPatientForRequest(long requestId);

    /**
     * This method Retrieves the list of patients that are related to the invoices.
     * @param list of invoice Ids
     * @return list of patients
     */
    List<RequestPatient> retrieveAllInvoicePatientsByInvoiceIds(List<Long> invoiceIds);

    /**
     * This method Retrieves the list of all the  statistics that are related to the request.
     * @param requestId
     * @return list of statistics
     */
    List<MUROIOutboundStatistics> retrieveAllStatisticsDetailsByRequestId(long requestId);
    /**
     * Method to get the mrn from external source documents table
     * @param requestId
     * @param patientId
     * @return
     */
    List<String> retrieveExternalSourceDocuments(long requestId,List<Long> patientIds);
}
