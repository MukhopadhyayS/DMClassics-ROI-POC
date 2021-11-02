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


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.model.PostPaymentReportDetails;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.request.model.Comment;
import com.mckesson.eig.roi.request.model.ProductivityReportDetails;
import com.mckesson.eig.roi.request.model.RequestCore;
import com.mckesson.eig.roi.request.model.RequestCoreChargesDocument;
import com.mckesson.eig.roi.request.model.RequestCoreSearchCriteria;
import com.mckesson.eig.roi.request.model.RequestCoreSearchResult;
import com.mckesson.eig.roi.request.model.RequestEvent;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.request.model.RequestEventCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorCore;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;

/**
 * @author OFS
 * @date Jun 29, 2012
 * @since Jun 29, 2012
 *
 */
public interface RequestCoreDAO
extends ROIDAO {

    /**
     * This method retrieves the request based on the requestId
     * @param requestId - request Id to be retrieved
     * @return Request
     */
    RequestCore retrieveRequest(long requestId);

    /**
     * This method creates a new request
     * @param request Request information
     * @return Request Request Details
     */
    RequestCore createRequest(RequestCore request);

    /**
     * This method deletes the request
     * @param request to be deleted
     */
     void deleteRequest(long requestId);

     /**
      * This method updates the request
      * @param request Request to be updated
      * @return updatedRequest
      */
     RequestCore updateRequest(RequestCore request);

     /**
      * This method creates request event
      * @param event to be created
      */
     void createRequestEvent(RequestEvent event);

     /**
      * Searches the request based on the request or patient criteria given
      *
      * @param searchCriteria
      * @return list of request whihc matched the given search criteria
      */
     List<RequestCoreSearchResult> searchRequest(RequestCoreSearchCriteria searchCriteria);

     /**
      * This method will retrieve the Requestor for given Request Id
      * @param requestId
      * @return
      */
     RequestorCore retrieveRequestor(long requestId);

     /**
      * This method will retrieve the all the Requestor for given invoices Ids
      * @param requestId
      * @return list of requestor
      */
     List<RequestorCore> retrieveAllRequestorByInvoiceIds(List<Long> invoiceIds);

     /**
      * This method will retrieve all types of Request Events or Events based on input type
      * @param criteria Input search criteria
      * @return List of Request Events
      */
     List < ? extends RequestEvent> getEventHistory(RequestEventCriteria criteria);

     /**
      * This method returns count of request ids for matching input criteria
      * @param criteria Input search criteris
      * @return No. of request ids
      */
     long getRequestCount(RequestCoreSearchCriteria searchCriteria);

     /**
      * deletes the request event based on the requestId and the eventType
      * @param requestId
      * @param eventType
      */
     void deleteLatestRequestEvent(long requestId, TYPE eventType);

     /**
      * This method saves the list of request events
      * to the database
      * @param reqEvents
      * This method is added for CR# 365598
      *
      */
     void createAllRequestEvent(List<RequestEvent> reqEvents);

     /**
      * This method retrieves the list of docuemnt Charges
      * @param requestId
      * @return list of document charges
      *
      */
     List<RequestCoreChargesDocument> retrieveAllDocumentCharges(long requestId);
     
     /**
      * Retrieves the list of all invoices for the given request Id
      * @param requestId
      * @return
      */
     RequestorInvoicesList retrieveRequestInvoiceDetails(long requestId);
     
     /**
      * This method updates the unbillable
      * 
      * @param requestCoreId
      * @param unbillable
      * @param creditAdjAmt
      * @param date
      * @param user
      */
     void updateRequestCoreUnbillable(long requestCoreId, boolean unbillable,
             Timestamp date, User user);
     
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
     public List<ProductivityReportDetails> retriveProductivityReportDetails(
             String[] facility, List<String> username, String[] requestorType,
             Date fromDate, Date toDate,String resultType);

     
}
