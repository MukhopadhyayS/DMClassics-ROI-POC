/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

 * Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
 * Use of this software and related documentation is governed by a license agreement.
 * This material contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States
 * and international copyright and other intellectual property laws.
 * Use, disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without the
 * prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
 */

package com.mckesson.eig.roi.request.service;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.dao.RequestCorePatientDAO.RequestDSRMappingKeys;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreChargesBillingInfo;
import com.mckesson.eig.roi.request.model.RequestDocument;
import com.mckesson.eig.roi.request.model.RequestEncounter;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestPage;
import com.mckesson.eig.roi.request.model.RequestPatient;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.request.model.RequestVersion;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date Aug 1, 2012
 * @since Aug 1, 2012
 */
public class RequestCoreServiceHelper
extends BaseROIService {

    private static final NumberFormat CURRENCY_FORMAT_US = NumberFormat
                                            .getCurrencyInstance(ROIConstants.INVOICE_LOCALE);

    /**
     * @param request
     * @param oldStatusReasons
     * @param newStatus
     * @return
     */
    public boolean isReasonChanged(RequestCore request, String oldStatusReasons, String newStatus) {

        boolean isReasonChanged = false;
        if (!newStatus.equalsIgnoreCase(ROIConstants.DEFAULT_STATUS_PREBILLED)) {

            if (null == oldStatusReasons && null == request.getStatusReason()) {
                isReasonChanged = false;
            } else if (null == oldStatusReasons && null != request.getStatusReason()) {
                isReasonChanged = true;
            } else {
                isReasonChanged = !oldStatusReasons.equalsIgnoreCase(request.getStatusReason());
            }
        }
        return isReasonChanged;
    }

    /**
     * This method creates the request event for this request
     *
     * @param request
     *            Request
     * @param oldStatus
     * @param dao
     * @param statusChanged
     * @param statusReasonChanged
     */
    public void createEvent(RequestCore request,
                            String oldStatus,
                            RequestCoreDAO dao,
                            boolean statusChanged,
                            boolean statusReasonChanged) {

        Timestamp date = dao.getDate();
        String newStatus = request.getStatus();

        if (statusChanged) {

            RequestEvent event = new RequestEvent();
            event.setRequestId(request.getId());
            event.setStatusChange(oldStatus, newStatus);
            setDefaultDetails(event, date, true);
            dao.createRequestEvent(event);
            // to audit status change event
            auditStatusChange(request, oldStatus, newStatus, date);
        }

        if (statusReasonChanged) {

            RequestEvent event = new RequestEvent();
            event.setRequestId(request.getId());
            event.setStatusReasonsChange(newStatus, request.getStatusReason());
            setDefaultDetails(event, date, true);
            dao.createRequestEvent(event);
        }
    }

    /**
     * This method will audit in case there is a status change
     *
     * @param request
     * @param old
     * @param newStatus
     * @param date
     */
    public void auditStatusChange(RequestCore request,
                                  String old,
                                  String newStatus,
                                  Timestamp date) {

        String code = null;

        if ((ROIConstants.DENIED_STATUS).equalsIgnoreCase(request.getStatus())) {
            code = ROIConstants.REQUEST_AUDIT_ACTION_CODE_DENY;
        } else if ((ROIConstants.CANCELED_STATUS).equalsIgnoreCase(request
                .getStatus())) {
            code = ROIConstants.REQUEST_AUDIT_ACTION_CODE_CANCEL;
        } else if ((ROIConstants.STATUS_PENDED).equalsIgnoreCase(request
                .getStatus())) {
            code = ROIConstants.REQUEST_AUDIT_ACTION_CODE_PENDED;
        } else {
            code = ROIConstants.REQUEST_AUDIT_ACTION_CODE_UPDATE;
        }

        auditRequest(request.toCreateEvent(request.getId(), old, newStatus),
                     getUser().getInstanceId(), date, code);

    }

    /**
     * This method constructs the list of request patients from the given
     * patient details map the patient detaila map should have key -
     * RequestaPatientDetails enum value - list of patients details
     *
     * @param patients
     * @return
     */
    public List<RequestPatient> contructRequestPatientDetails(Map<RequestDSRMappingKeys,
                                                              List<? extends BaseModel>> patients) {

        //Constructing map for Pages based on versions
        List<? extends BaseModel> patientDetails = patients.get(RequestDSRMappingKeys.PAGES);
        Map<Long, List<RequestPage>> retrievePage = constructPageMap(patientDetails);

      //Constructing map for Version based on documents
        patientDetails = patients.get(RequestDSRMappingKeys.VERSIONS);
        Map<Long, List<RequestVersion>> retrieveVersion =
                                constructVersionMap(patientDetails, retrievePage);

      //Constructing map for Documents based on encounters
        patientDetails = patients.get(RequestDSRMappingKeys.DOCUMENTS);
        Map<Long, List<RequestDocument>> retrieveDocument =
                                constructDocumentMap(patientDetails, retrieveVersion);

        patientDetails = patients.get(RequestDSRMappingKeys.GLOBALDOCUMENTSPAGES);
        Map<Long, List<RequestPage>> retrievedGlobalPages = constructPageMap(patientDetails);

        patientDetails = patients.get(RequestDSRMappingKeys.GLOBALDOCUMENTSVERSIONS);
        Map<Long, List<RequestVersion>> retrievedGlobalVersion =
                                        constructVersionMap(patientDetails, retrievedGlobalPages);

        patientDetails = patients.get(RequestDSRMappingKeys.GLOBALDOCUMENTS);
        Map<Long, List<RequestDocument>> retrievedGlobalDocuments =
                constructGlobalDocumentsMap(patientDetails, retrievedGlobalVersion);

      //Constructing map for Encounter based on patients
        patientDetails = patients.get(RequestDSRMappingKeys.ENCOUNTERS);
        Map<Long, List<RequestEncounter>> retrieveEncounter =
                                constructEncounterMap(patientDetails, retrieveDocument);

      //Constructing map for Patients
        patientDetails = patients.get(RequestDSRMappingKeys.PATIENTS);
        Map<Long, RequestPatient> retrievePatient =
                                constructRequestPatient(patientDetails, retrieveEncounter);

        constructRequestPatientGlobalDocs(retrievePatient, retrievedGlobalDocuments);
        //Constructing map for Patients
        patientDetails = patients.get(RequestDSRMappingKeys.NONHPF_DOCUMENTS);
        constructRequestPatientNonHpfDocs(patientDetails, retrievePatient);

        //Constructing map for Patients
        patientDetails = patients.get(RequestDSRMappingKeys.ATTACHMENTS);
        constructRequestPatientAttachments(patientDetails, retrievePatient);

        Collection<RequestPatient> patientCollections = retrievePatient.values();
        if (null == patientCollections || patientCollections.isEmpty()) {
            return null;
        }

        List<RequestPatient> patientsList = new ArrayList<RequestPatient>();
        patientsList.addAll(patientCollections);

        return patientsList;
    }

    /**
     * gets the list of global documents
     * @param documents
     * @param retrievedGlobalVersion
     * @return
     */
    private Map<Long, List<RequestDocument>> constructGlobalDocumentsMap(
                                               List<? extends BaseModel> requestDocument,
                                               Map<Long, List<RequestVersion>> retrieveVersion) {

        if ((null == requestDocument || requestDocument.size() <= 0)
                && (null == retrieveVersion || retrieveVersion.size() <= 0)) {
            return null;
        }

        Map<Long, List<RequestDocument>> documents = new LinkedHashMap<Long, List<RequestDocument>>();
        List<RequestDocument> documentList = new ArrayList<RequestDocument>();

        Iterator<? extends BaseModel> iterator = requestDocument.iterator();
        //Constructing document map based on patient
        while (iterator.hasNext()) {

            RequestDocument document = (RequestDocument) iterator.next();
            if (null != retrieveVersion && retrieveVersion.containsKey(document.getDocumentSeq())) {

                List<RequestVersion> versionsList = retrieveVersion.get(document.getDocumentSeq());
                Comparator<RequestVersion> comparator = Comparator.comparingLong(RequestVersion::getVersionNumber); 
                Collections.sort(versionsList, comparator);
                document.setRoiVersions(versionsList);
                retrieveVersion.remove(document.getDocumentSeq());
            } else {

                // if a document does not contains any pages,
                // it is not eligible to show in the DSR tree
                continue;
            }

            if (documents.containsKey(document.getPatientSeq())) {

                List<RequestDocument> docList = documents.get(document.getPatientSeq());
                docList.add(document);
            } else {

                documentList = new ArrayList<RequestDocument>();
                documentList.add(document);
                documents.put(document.getPatientSeq(), documentList);
            }
        }

        return documents;
    }

    /**
     * maps the global documents for the request patients
     * @param retrievedPatients
     * @param retrieveDocument
     */
    private void constructRequestPatientGlobalDocs(Map<Long, RequestPatient> retrievedPatients,
                                                   Map<Long, List<RequestDocument>> retrieveDocument) {

        if (CollectionUtilities.isEmpty(retrievedPatients)
                || CollectionUtilities.isEmpty(retrieveDocument)) {
            return;
        }

        Set<Entry<Long, RequestPatient>> entrySet = retrievedPatients.entrySet();
        Iterator<Entry<Long, RequestPatient>> iterator = entrySet.iterator();

        //Constructing encounter map based on patients
        while (iterator.hasNext()) {

            Entry<Long, RequestPatient> patientEntry =  iterator.next();
            long patientSeq = patientEntry.getKey();
            RequestPatient patient = patientEntry.getValue();

            if (null != retrieveDocument && retrieveDocument.containsKey(patientSeq)) {
                List<RequestDocument> globalDocuments = retrieveDocument.get(patientSeq);
                Comparator<RequestDocument> comparator = Comparator.comparing(RequestDocument::getChartOrderAsInt); 
                Collections.sort(globalDocuments, comparator);
                patient.setGlobalDocuments(globalDocuments);
                retrieveDocument.remove(patientSeq);
            }
        }
    }

    /**
     * From the given list of pages, group the pages based on the versions
     *
     * @param requestPage
     * @return
     */
    public Map<Long, List<RequestPage>> constructPageMap(List<? extends BaseModel> requestPage) {

        if (null == requestPage || requestPage.size() <= 0) {
            return null;
        }

        Map<Long, List<RequestPage>> pages = new LinkedHashMap<Long, List<RequestPage>>();
        List<RequestPage> pageList = new ArrayList<RequestPage>();

        Iterator<? extends BaseModel> iterator = requestPage.iterator();
        //Constructing page map based on versions
        while (iterator.hasNext()) {

            RequestPage page = (RequestPage) iterator.next();
            if (pages.containsKey(page.getVersionSeq())) {

                List<RequestPage> list = pages.get(page.getVersionSeq());
                list.add(page);
            } else {

                pageList = new ArrayList<RequestPage>();
                pageList.add(page);
                pages.put(page.getVersionSeq(), pageList);
            }
        }

        return pages;
    }

    /**
     * From the given list of versions, group the versions based on the
     * documents
     *
     * @param requestPage
     * @return
     */
    public Map<Long, List<RequestVersion>> constructVersionMap(
                                                       List<? extends BaseModel> requestVersion,
                                                       Map<Long, List<RequestPage>> retrievePage) {

        if ((null == requestVersion || requestVersion.size() <= 0)
                && (null == retrievePage || retrievePage.size() <= 0)) {
            return null;
        }

        Map<Long, List<RequestVersion>> versions = new LinkedHashMap<Long, List<RequestVersion>>();
        List<RequestVersion> versionList = new ArrayList<RequestVersion>();

        Iterator<? extends BaseModel> iterator = requestVersion.iterator();
        //Constructing version map based on documents
        while (iterator.hasNext()) {

            RequestVersion version = (RequestVersion) iterator.next();
            if (null != retrievePage && retrievePage.containsKey(version.getVersionSeq())) {

                List<RequestPage> pagesList = retrievePage.get(version.getVersionSeq());
                Comparator<RequestPage> comparator = Comparator.comparing(RequestPage::getPageNumber); 
                Collections.sort(pagesList, comparator);
                version.setRoiPages(pagesList);
                retrievePage.remove(version.getVersionSeq());
            } else {

                // if a version does not contains any pages,
                // it is not eligible to show in the DSR tree
                continue;
            }

            if (versions.containsKey(version.getDocumentSeq())) {

                List<RequestVersion> versionsList = versions.get(version.getDocumentSeq());
                versionsList.add(version);
            } else {

                versionList = new ArrayList<RequestVersion>();
                versionList.add(version);
                versions.put(version.getDocumentSeq(), versionList);
            }

        }

        return versions;
    }

    /**
     * From the given list of documents, group the document based on the
     * encounter
     *
     * @param requestPage
     * @return
     */
    public Map<Long, List<RequestDocument>> constructDocumentMap(
                                                List<? extends BaseModel> requestDocument,
                                                Map<Long, List<RequestVersion>> retrieveVersion) {

        if ((null == requestDocument || requestDocument.size() <= 0)
                && (null == retrieveVersion || retrieveVersion.size() <= 0)) {
            return null;
        }

        Map<Long, List<RequestDocument>> documents = new LinkedHashMap<Long, List<RequestDocument>>();
        List<RequestDocument> documentList = new ArrayList<RequestDocument>();

        Iterator<? extends BaseModel> iterator = requestDocument.iterator();
        //Constructing document map based on encounter
        while (iterator.hasNext()) {

            RequestDocument document = (RequestDocument) iterator.next();
            if (null != retrieveVersion && retrieveVersion.containsKey(document.getDocumentSeq())) {

                List<RequestVersion> versionsList = retrieveVersion.get(document.getDocumentSeq());
                Comparator<RequestVersion> comparator = Comparator.comparing(RequestVersion::getVersionNumber); 
                Collections.sort(versionsList, comparator);
                document.setRoiVersions(versionsList);
                retrieveVersion.remove(document.getDocumentSeq());
            } else {
                // if a document does not contains any pages,
                // it is not eligible to show in the DSR tree
                continue;
            }

            if (documents.containsKey(document.getEncounterSeq())) {

                List<RequestDocument> docList = documents.get(document.getEncounterSeq());
                docList.add(document);
            } else {

                documentList = new ArrayList<RequestDocument>();
                documentList.add(document);
                documents.put(document.getEncounterSeq(), documentList);
            }
        }
        return documents;
    }

    /**
     * From the given list of encounters, group the versions based on the
     * patients
     * @param requestPage
     * @return
     */
    public Map<Long, List<RequestEncounter>> constructEncounterMap(
                                                List<? extends BaseModel> requestEncounter,
                                                Map<Long, List<RequestDocument>> retrievedDocs) {

        if ((null == requestEncounter || requestEncounter.size() <= 0)
                && (null == retrievedDocs || retrievedDocs.size() <= 0)) {
            return null;
        }

        Map<Long, List<RequestEncounter>> encounters = new LinkedHashMap<Long, List<RequestEncounter>>();
        List<RequestEncounter> encounterList = new ArrayList<RequestEncounter>();

        Iterator<? extends BaseModel> iterator = requestEncounter.iterator();
        //Constructing encounter map based on patients
        while (iterator.hasNext()) {

            RequestEncounter encounter = (RequestEncounter) iterator.next();
            if (null != retrievedDocs && retrievedDocs.containsKey(encounter.getEncounterSeq())) {

                List<RequestDocument> docList = retrievedDocs.get(encounter.getEncounterSeq());
                Comparator<RequestDocument> comparator = Comparator.comparing(RequestDocument::getChartOrderAsInt); 
                Collections.sort(docList, comparator);
                encounter.setRoiDocuments(docList);
                retrievedDocs.remove(encounter.getEncounterSeq());
            } else {
                // if a encounter does not contains any pages,
                // it is not eligible to show in the DSR tree
                continue;
            }

            if (encounters.containsKey(encounter.getPatientSeq())) {

                List<RequestEncounter> docList = encounters.get(encounter.getPatientSeq());
                docList.add(encounter);
            } else {

                encounterList = new ArrayList<RequestEncounter>();
                encounterList.add(encounter);
                encounters.put(encounter.getPatientSeq(), encounterList);
            }
        }
        return encounters;
    }

    /**
     * From the given list of patients, group the patients based on the request
     * @param requestPatient
     * @param retrievedEnc
     * @return
     */
    public Map<Long, RequestPatient> constructRequestPatient(
                                        List<? extends BaseModel> requestPatient,
                                        Map<Long, List<RequestEncounter>> retrievedEnc) {

        if ((null == requestPatient || requestPatient.size() <= 0)
                && (null == retrievedEnc || retrievedEnc.size() <= 0)) {
            return new HashMap<Long, RequestPatient>(0);
        }

        Map<Long, RequestPatient> patients = new LinkedHashMap<Long, RequestPatient>();

        Iterator<? extends BaseModel> iterator = requestPatient.iterator();
        while (iterator.hasNext()) {

            RequestPatient patient = (RequestPatient) iterator.next();
            if (null != retrievedEnc && retrievedEnc.containsKey(patient.getPatientSeq())) {
               List<RequestEncounter> encList = retrievedEnc.get(patient.getPatientSeq());
                Comparator<RequestEncounter> comparator = Comparator.comparing(RequestEncounter::getAdmitdate); 
                Collections.sort(encList, comparator.reversed());
                patient.setRoiEncounters(encList);
                retrievedEnc.remove(patient.getPatientSeq());
            }

            patients.put(patient.getPatientSeq(), patient);
        }
        return patients;
    }

    /**
     * From the given list of patients, the non hpf documents are mapped to the patients
     * @param requestNonHpfDocs
     * @param retrievedPat
     * @return
     */
    public Map<Long, RequestPatient> constructRequestPatientNonHpfDocs(
            List<? extends BaseModel> requestNonHpfDocs,
            Map<Long, RequestPatient> retrievedPat) {

        if ((null == requestNonHpfDocs || requestNonHpfDocs.size() <= 0)
                || (null == retrievedPat || retrievedPat.size() <= 0)) {
            return retrievedPat;
        }

        Iterator<? extends BaseModel> iterator = requestNonHpfDocs.iterator();
        while (iterator.hasNext()) {

            RequestSupplementalDocument docs = (RequestSupplementalDocument) iterator.next();
            if (retrievedPat.containsKey(docs.getPatientSeq())) {

                RequestPatient patient = retrievedPat.get(docs.getPatientSeq());
                patient.addNonHpfDocuments(docs);

            } else {
                throw new ROIException(ROIClientErrorCodes.REQUEST_CONTAINS_INVALID_DOCUMENTS,
                                  "Request Contains documents which are not mapped to a patient");
            }
        }
        return retrievedPat;
    }

    /**
     * From the given list of patients, the attachments are mapped to the patients
     * @param requestAttachments
     * @param retrievedPat
     * @return
     */
    public Map<Long, RequestPatient> constructRequestPatientAttachments(
            List<? extends BaseModel> requestAttachments,
            Map<Long, RequestPatient> retrievedPat) {

        if ((null == requestAttachments || requestAttachments.size() <= 0)
                || (null == retrievedPat || retrievedPat.size() <= 0)) {
            return retrievedPat;
        }

        Iterator<? extends BaseModel> iterator = requestAttachments.iterator();
        while (iterator.hasNext()) {

            RequestSupplementalAttachment attachment =
                                    (RequestSupplementalAttachment) iterator.next();

            if (retrievedPat.containsKey(attachment.getPatientSeq())) {

                RequestPatient patient = retrievedPat.get(attachment.getPatientSeq());
                patient.addSupplementalAttachments(attachment);

            } else {
                throw new ROIException(ROIClientErrorCodes.REQUEST_CONTAINS_INVALID_ATTACHMENTS,
                        "Request Contains attachments which are not mapped to a patient");
            }
        }
        return retrievedPat;
    }

    /**
     * @param invCharges
     * @param date
     * @param comment
     * @return
     */
    protected RequestEvent createRequestEvent(RequestCoreChargesBillingInfo invoiceCharges,
                                            Timestamp date, StringBuffer comment) {

        if (comment.length() <= 0) {
            return null;
        }

        RequestEvent event = new RequestEvent();
        event.setRequestId(invoiceCharges.getRequestCoreSeq());
        event.setDescription(comment.toString());
        event.setOriginator(getUser().getDisplayName());
        event.setCreatedBy(getUser().getInstanceId());
        setDefaultDetails(event, date, false);
        return event;
    }

    /**
     * This method format the given value to the corresponding currenct type
     *
     * @param amount
     * @return
     */
    private String formatCurrency(Double amount) {

        if (null == amount) {
         amount = new Double(0.00);
        }
        return CURRENCY_FORMAT_US.format(amount);
    }

}
