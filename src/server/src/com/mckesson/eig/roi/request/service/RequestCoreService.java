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

package com.mckesson.eig.roi.request.service;


import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.request.model.AuditAndEventList;
import com.mckesson.eig.roi.request.model.Comments;
import com.mckesson.eig.roi.request.model.EventTypes;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreCharges;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreChargesInvoicesList;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResultList;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvents;
import com.mckesson.eig.roi.request.model.RequestPatientsList;
import com.mckesson.eig.roi.request.model.SaveRequestPatientsList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;


/**
 * @author OFS
 * @date   Jun 29, 2012
 * @since  Jun 29, 2012
 */
public interface RequestCoreService {


    /**
     * This method searches the request based on the given criteria
     * @param request core searchCriteria-  request details
     * @return RequestCoreSearchResultList - searchRequest details
     */
    RequestCoreSearchResultList searchRequest(RequestCoreSearchCriteria searchCriteria);

    /**
     * This method creates the new request
     * @param request -  request details
     * @return Request - createdRequest details
     */
    RequestCore createRequest(RequestCore request);

    /**
     * This method retrieves the request details
     * @param id - Request ID
     * @return RequestCore- created request details
     */
    RequestCore retrieveRequest(long id, boolean isSearchRetrieve);

    /**
     * This method delete the request
     * @param id - Request ID
     */
     void deleteRequest(long id);

     /**
      * This method updates the request information
      * @param request -Request details to be updated
      * @return Request details
      */
     RequestCore updateRequest(RequestCore request);

     /**
      * This method retrieves the patient details
      * @param id - patient ID
      * @return RequestPatientsList - List of patient Details
      */
     RequestPatientsList retrieveRequestPatient(long id);

     /**
      * This method retrieves the patient details
      * @param RequestPatientsList - List of patient Details
      * @return RequestPatientsList - List of patient Details
      */
     RequestPatientsList saveRequestPatient(SaveRequestPatientsList patientDetails);

     /**
      * This method retrieves the RequestCoreCharges values
      *
      * @param requestId
      * @return RequestCoreCharges
      */
     RequestCoreCharges retrieveRequestCoreCharges(long requestCoreId);


     /**
      * This method saves the RequestCoreCharges values
      *
      * @param requestCoreCharges
      * @return
      */
     void saveRequestCoreCharges(RequestCoreCharges requestCoreCharges);
     /**
      * This method is to retrieve InvoicesAndAdjPay
      * @param requestCoreId
      * @return RequestCoreChargesInvoicesList
      */
     RequestCoreChargesInvoicesList retrieveInvoicesAndAdjPay(long requestCoreId);

     /**
      * This method will retrieve all entered comments for the input Request Id
      * @param requestId Input Request Id
      * @return List of comments
      */
     Comments retrieveComments(long requestId);

     /**
      * This method will retrieve all the request event details for the input Request Id
      * @param requestId Input request Id
      * @return List of Request Events
      */
     RequestEvents getEventHistory(long requestId);

     /**
      * This method will retrieve all the Request Event type names
      * @return List of event type names
      */
     EventTypes retrieveAllEventTypes();

     /**
      * This method will retrieve requests based on the search criteria
      * @param criteria Input search criteria
      * @return No of records
      */
     long getRequestCount(RequestCoreSearchCriteria criteria);

     /**
      * This method will create an entry in Request Event
      * @param event
      * @return event
      */
     RequestEvent addEvent(RequestEvent event);
     /**
      * This method will generate the request level password
      * @return generated password.
     * @throws Exception 
      */
     String getGeneratedPassword() throws Exception;

     /**
      * This method will create an entry in Request Event & Audit Event
      * @param RequestEvent, AuditEvent.
      *
      *
      */
     void addAuditAndEvent(AuditAndEventList auditAndEventList);

     /**
      * Retrieves the list of released document charges based on the billingTier Id
      * This is used for the remove basecharge for the used billingTier
      * @param requestId
      * @return list of document charges
      */
     List<RequestCoreChargesDocument> retrieveReleasedDocumentChargesByBillingTier(long requestId);

     /**
      * Retrieves the list of all invoices for the given request Id
      * @param requestId
      * @return
      */
     RequestorInvoicesList retrieveRequestInvoices(long requestId);
     
     /**
      * Method to retrieve ProductivityReportDetails 
      * 
      * @param fromDate
      * @param toDate
      * @param muDocName
      * @param facility
      * @param resultType
      * @return list
      */
     public List<Object[]> retrieveProductivityReportDetails( String[] facility, List<String> userName,
             String[] requestorType,Date fromDate,Date toDate,String resultType);
     
     /**
      * This method validates whether the user has security rights to release a document
      * @return boolean
      */
     public boolean hasSecurityRightsForRelease();
}
