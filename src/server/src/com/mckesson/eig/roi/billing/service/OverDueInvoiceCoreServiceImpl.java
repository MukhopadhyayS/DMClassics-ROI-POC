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

package com.mckesson.eig.roi.billing.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.LetterDataRetriever;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.billing.dao.OverDueInvoiceCoreDAO;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.model.InvoiceAndLetterInfo;
import com.mckesson.eig.roi.billing.model.OverDueDocInfo;
import com.mckesson.eig.roi.billing.model.OverDueDocInfoList;
import com.mckesson.eig.roi.billing.model.PropertiesMap;
import com.mckesson.eig.roi.billing.model.RequestCoreDeliveryCharges;
import com.mckesson.eig.roi.billing.model.RequestorInvoices;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria;
import com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceResult;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;

/**
 * @author Karthik Easwaran
 * @date   Aug 28, 2012
 * @since  Aug 28, 2012
 */
@WebService(serviceName="OverdueInvoiceCoreService", endpointInterface="com.mckesson.eig.roi.billing.service.OverDueInvoiceCoreService",
targetNamespace="urn:eig.mckesson.com", portName="OverDueInvoiceCore", name="OverDueInvoiceCoreServiceImpl")
public class OverDueInvoiceCoreServiceImpl
extends AbstractBillingService
implements OverDueInvoiceCoreService {

    private static final OCLogger LOG = new OCLogger(OverDueInvoiceCoreServiceImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String FILENAME_DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss.SSS";


    /**
     * @see com.mckesson.eig.roi.billing.service.OverDueInvoiceCoreService
     * #searchPastDueInvoices(com.mckesson.eig.roi.billing.model.SearchPastDueInvoiceCriteria)
     */
    @Override
    public SearchPastDueInvoiceResult searchOverDueInvoices(SearchPastDueInvoiceCriteria criteria) {

        final String logSM = "searchOverDueInvoices(criteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }


        OverDueInvoiceServiceValidator validator = new OverDueInvoiceServiceValidator();
        if (!validator.validateOverDueInvoiceSearchCriteria(criteria)) {
            throw validator.getException();
        }

        OverDueInvoiceCoreDAO dao =
                            (OverDueInvoiceCoreDAO) getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);
        try {

            SearchPastDueInvoiceResult result = dao.searchOverDueInvoices(criteria);
            String comment = null;

            if("".equalsIgnoreCase(criteria.getRequestorName()))
               comment = new StringBuffer("Past Due invoices are searched based on facility:")
                                .append(Arrays.toString(criteria.getBillingLocations()))
                                .append(" >> retrieved ")
                                .append(result.toString()).toString();
            else
                comment = new StringBuffer("Search Performed in \"ROI\". ")
                                 .append("Type of Search:- Requestor. ")
                                 .append("Search Criteria entered:- ")
                                 .append("Filter By: ")
                                 .append("Facility: " + Arrays.toString(criteria.getBillingLocations()) + ",")
                                 .append("RequestorType: " + Arrays.toString(criteria.getRequestorTypeNames()) + ",")
                                 .append("Requestor Name - " + criteria.getRequestorName()).toString();

                doAudit(comment,
                        getUser().getInstanceId(),
                        dao.getDate(),
                        "".equalsIgnoreCase(criteria.getRequestorName()) ? ROIConstants.NORMAL_AUDIT_CODE
                                : ROIConstants.AUDIT_ACTION_CODE_SEARCH,
                       ROIConstants.DEFAULT_FACILITY,
                       null,null);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:No. Of Overdue Invoices: "
                                + result.getPastDueInvoiceList().getPastDueInvoice().size());
            }

            return result;
        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {

            LOG.error("Exception occured in searchOverDueInvoices",e);
            throw new ROIException(e, ROIClientErrorCodes.SEARCH_PASTDUE_INVOICE_OPERATION_FAILED);
        }
    }


    @Override
    public OverDueDocInfoList regenerateInvoiceCoreAndLetter(InvoiceAndLetterInfo invLetterInfo,
                                                         boolean forPreview) {

        final String logSM = "regenerateInvoiceCoreAndLetter(previewInfo)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        // validates the invoiceAndLetter model
        OverDueInvoiceServiceValidator validator = new OverDueInvoiceServiceValidator();
        if (!validator.validateInvoiceAndLetterInfo(invLetterInfo, forPreview)) {
            throw validator.getException();
        }

        try {

            LetterTemplateDocument reqLetterTemplate  = getLetterTemplateDocument(
                                                  invLetterInfo.getRequestorLetterTemplateId());
            // Regenerate Invoice and Letter
            OverDueDocInfoList docInfoList = regenerateInvoice(invLetterInfo,
                                                 reqLetterTemplate, forPreview);
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End: regenerateInvoiceAndLetter");
            }

            return docInfoList;

        } catch (ROIException e) {
            throw e;
        } catch (Throwable t) {
            throw new ROIException(t, ROIClientErrorCodes.REGENERATION_OPERATION_FAILED);
        }
    }

    /**
     * regenerates the list of requestor letter and the invoices
     *
     * @param invLetterInfo
     * @param invoiceTemplate
     * @param reqLetterTemplate
     * @param forPreview
     * @return docInfoList
     */
    private OverDueDocInfoList regenerateInvoice(InvoiceAndLetterInfo invLetterInfo,
                                                 LetterTemplateDocument reqLetterTemplate,
                                                 boolean forPreview) {

        // Collect all invoice Ids and retrieve its corresponding release info.
        List<RequestorInvoices>  reqInvs = invLetterInfo.getInvoices();
        // Filenames used as a parameter to generate PDF
        List<String> fileNames = new ArrayList<String>();

        List<RequestorLetter>  reqLettersList = new ArrayList<RequestorLetter>();

        List<RequestCoreDeliveryCharges> invoicesList = new ArrayList<RequestCoreDeliveryCharges>();
        if (invLetterInfo.isOutputInvoice()) {

            BillingCoreService bcService = (BillingCoreService)
                    getService(ServiceName.BILLING_CORE_SERVICE);

            invoicesList = bcService.retrieveAllInvoicesByIds(getAllInvoiceIds(reqInvs));
        }

        OverDueInvoiceCoreDAO overDueDAO =
                (OverDueInvoiceCoreDAO) getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);
        Date date = overDueDAO.getDate();

        List<OverDueDocInfo> docInfos = new ArrayList<OverDueDocInfo>();
        // Local variable to store requestor specific invoices
        for (RequestorInvoices invoices : reqInvs) {

            long requestorId = invoices.getRequestorId();
            RequestorStatementCriteria statementCriteria = invLetterInfo.getStatementCriteria();
            if (null != statementCriteria) {

                statementCriteria.setRequestorId(requestorId);
                statementCriteria.setTemplateFileId(
                                        invLetterInfo.getRequestorLetterTemplateId());
                statementCriteria.setNotes(invLetterInfo.getReqLetterNotes());
            }

            // if the call is only for preview then no need constructing and
            // saving the requestor letter and regenerated invoice
            if (forPreview) {

                generateRequestorLetterPreview(statementCriteria,
                                               docInfos,
                                               reqLetterTemplate,
                                               fileNames);

                if (invLetterInfo.isOutputInvoice()) {

                    List<RequestCoreDeliveryCharges> requestorInvoices  =
                            getRequestorInvoices(invoices, invoicesList);

                    generateRequestorInvoicesPreview(requestorInvoices,
                            docInfos,
                            fileNames);
                }
            } else {

                RequestorLetter  requestorLetter = null;
                if (null != reqLetterTemplate) {

                    requestorLetter = constructRequestorLetterModel(invLetterInfo,
                                                                    invoices,
                                                                    date);
                    reqLettersList.add(requestorLetter);
                }
            }
        }

        if (forPreview) {

            String generatedFileName = generatePDF(fileNames,
                                                   ROIConstants.OVERDUE_INVOICE_FILE);

            return new OverDueDocInfoList(docInfos , generatedFileName);
        }

        if (!reqLettersList.isEmpty()) {
            reqLettersList = overDueDAO.createRequestorLetter(reqLettersList);
        }

        List<OverDueDocInfo> infoList =
                            constructDocInfoList(invLetterInfo, reqLettersList);
        return new OverDueDocInfoList(infoList);

    }

    /**
     * generates the preview for the requestor invoices which are overdue
     * and adds the preview file to the docInfo
     * @param reqInvoices - list overdue invoice for the requestor
     * @param docInfos - docinfos which contains the filename information of the generates preview
     * @param invoiceTemplate - template for the preview
     * @param fileNames - list of all file names of the generated pDf used for concatenation
     */
    private void generateRequestorInvoicesPreview(List<RequestCoreDeliveryCharges> reqInvoices,
                                                  List<OverDueDocInfo> docInfos,
                                                  List<String> fileNames) {

        if (CollectionUtilities.isEmpty(reqInvoices)) {
            return;
        }

        LetterDataRetriever dataRetriever = getLetterDataRetriever(ROIConstants.INVOICE);
        LetterTemplateDocument invoiceTemplate;

        for (RequestCoreDeliveryCharges invoice : reqInvoices) {

            invoiceTemplate = getLetterTemplateDocument(invoice.getLetterTemplateFileId());
            Object templateData = dataRetriever.constructTemplateDataModel(invoice);

            String invFileName = generatePreview(fileNames, invoiceTemplate, templateData);
            
            LetterData letterData = (LetterData) templateData;
            // Checkmarx: ROI - Java - Heap_Inspection - Clear the queue password value post processing 
            if (null != letterData) {
                letterData.setQueuePassword(null);
            }
            //create the preview for the overdue invoice
            docInfos.add(new OverDueDocInfo(invoice.getId(),
                                            invFileName,
                                            ROIConstants.OVERDUE_INVOICE_FILE,
                                            null));
        }
    }


    /**
     * generates the preview PDF of the requestor letter
     *
     * @param requestorLetter - the requestor letter model to be used for generate preview
     * @param docInfos - list of docinfos which contains the genrated filename and the type
     * @param template - requestor letter template
     * @param fileNames - list of filenames of the generated PDF, finally used for PDF Concatenate
     */
    private void generateRequestorLetterPreview(RequestorStatementCriteria statementCriteria,
                                                List<OverDueDocInfo> docInfos,
                                                LetterTemplateDocument template,
                                                List<String> fileNames) {

        if (null == statementCriteria || null == template) {
            return;
        }

        RequestorStatementDAO dao = (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

        RequestorStatementInfo requestorInfo = retrieveRequestorStatementInfo(statementCriteria);
        LetterDataRetriever retriever = getLetterDataRetriever(ROIConstants.REQUESTORLETTER);
        LetterData letterData = (LetterData) retriever.constructTemplateDataModel(requestorInfo);

        String fileName = generateLetter(statementCriteria.getRequestorId(),
                                         ROIConstants.TEMPLATE_FILE_TYPE,
                                         ROIConstants.REQUESTORLETTER,
                                         letterData,
                                         dao.getDate());
        // Checkmarx: ROI - Java - Heap_Inspection - Clear the queue password value post processing 
        if (null != letterData) {
            letterData.setQueuePassword(null);
        }
        docInfos.add(new OverDueDocInfo(0, fileName, ROIConstants.REQUESTOR_LETTER_FILE, null));
        fileNames.add(getCacheFileName(fileName));

    }


    /**
     * Constructs the list of docinfos for the output server,
     * such that using the docinfos the output server downloads
     * the requestor file from the ROI server
     *
     * @param invLetterInfo - invletterInfo for the generation of the re
     * @param reqLettersList - list of requestorletters
     * @param regeneratedInvs - list of regenerated invoices
     * @return
     */
    private List<OverDueDocInfo> constructDocInfoList(InvoiceAndLetterInfo invLetterInfo,
                                                 List<RequestorLetter> reqLettersList) {


        List<RequestorInvoices> requestorInvoices = invLetterInfo.getInvoices();
        List<OverDueDocInfo> docInfos = new ArrayList<OverDueDocInfo>();
        OverDueDocInfo docInfo;
        String requestorGroupingKey;
        // iterates the requestor invoices for the construction of the
        // docInfolist as the order of the client request
        for (RequestorInvoices reqInvoice : requestorInvoices) {

            requestorGroupingKey = getRequestorGroupingkey(reqInvoice);
            // iterates the list of all saved rerquestor invoices
            // to find the Requestor letter corresponding to the requestor invoices
            Iterator<RequestorLetter> iterator = reqLettersList.iterator();
            while (iterator.hasNext()) {

                RequestorLetter letter = iterator.next();
                if (letter.getRequestorId() == reqInvoice.getRequestorId()) {

                    // once the matching requestor is found, the loop is terminated
                    docInfo = new OverDueDocInfo(letter.getRequestorLetterId(), null,
                                          ROIConstants.REQUESTOR_LETTER_FILE, requestorGroupingKey);
                    docInfos.add(docInfo);
                    break;
                }
            }

            // if the invoice is not required for output, only requesor statement is required.
            if (!invLetterInfo.isOutputInvoice()) {
                continue;
            }

            // A single requestor may have multiple invoices
            // hence iterated through all the invoices for the construction of docInfoList
            long[] invoiceIds = reqInvoice.getInvoiceIds();
            for (long invoiceId : invoiceIds) {

                // once the matching invoice is found the loop is terminated
                docInfo = new OverDueDocInfo(invoiceId, null,
                                  ROIConstants.INVOICE, requestorGroupingKey);
                docInfos.add(docInfo);
            }
        }

        return docInfos;
    }


    /**
     * gets the list of invoices corresponding to the given requestor
     *
     * @param reqInvoices
     * @param invoicesList
     * @return list iof all invoices associated with the given requestor
     *
     */
    private List<RequestCoreDeliveryCharges> getRequestorInvoices(RequestorInvoices reqInvoices,
                                                List<RequestCoreDeliveryCharges> invoicesList) {

        List<RequestCoreDeliveryCharges> invoices = new ArrayList<RequestCoreDeliveryCharges>();
        for (long id : reqInvoices.getInvoiceIds()) {

            for (RequestCoreDeliveryCharges rcDeliveryCharges : invoicesList) {
                // compares the invoice id with the release info id
                // to generate invoices based on the requestor order
                if (id == rcDeliveryCharges.getId()) {

                    invoices.add(rcDeliveryCharges);
                    break;
                }
            }
        }

        return invoices;
    }

    /**
     * This method generates the PDF document based on the given template
     * and the releaseInfo which parses the release XML and constructs the
     * Item model object
     *
     * @param fileNames
     * @param doc
     * @param info
     * @param templateData
     * @return filename of the generated invoices
     */
    private String generatePreview(List<String> fileNames,
                                    LetterTemplateDocument template,
                                    Object templateData) {

        String fileName = new StringBuffer(ROIConstants.OVERDUE_INVOICE_FILE)
                     .append("." + formatDate(new Date(), FILENAME_DATE_FORMAT))
                     .append(ROIConstants.OUTPUT_FILE_EXT).toString();

        String path = getCacheFileName(fileName);

        //   generate the invoices based on the given template and the item model
        getLetterGenerator(ROIConstants.TEMPLATE_FILE_TYPE).generateLetter(templateData, template, path);

        fileNames.add(path);
        return fileName;
    }

    /**
     * generates the requestor grouping key for the grouping of invoices
     * based on the requestor, which is used for output the requestor letters and their invoices
     *
     * @param invoices
     * @return
     */
    private String getRequestorGroupingkey(RequestorInvoices invoices) {

        long requestorId = invoices.getRequestorId();
        String requestorType = invoices.getRequestorType();
        String requestorName = invoices.getRequestorName();
        return  requestorId + "-" + requestorType + "-" + requestorName;
    }

    /**
     * Constructs the RequestorLetter model from the list of RequestCoreDeliverCharges
     *
     * @param rInfos - list of all invoices for the requestor
     * @param invLetterInfo
     * @param requestorInvoices - requestor invoices
     * @param date - date to be set for default details
     * @return constructed requestor letter model
     */
    private RequestorLetter constructRequestorLetterModel(InvoiceAndLetterInfo invLetterInfo,
                                                       RequestorInvoices requestorInvoices,
                                                       Date date) {

        RequestorStatementCriteria statementCriteria = invLetterInfo.getStatementCriteria();

        RequestorStatementInfo requestorInfo = retrieveRequestorStatementInfo(statementCriteria);
        RequestorLetter reqLetter = constructRequestorLetterFromStatementInfo(requestorInfo);

        List<PropertiesMap> properties = invLetterInfo.getProperties();
        
        Map<String, String> prop = new HashMap<>();
        
        for(PropertiesMap p : properties) {
            prop.put(p.getName(), p.getValue());
        }
        
        if (null != prop && !prop.isEmpty()) { // if the property is null, then it is forPreview

            reqLetter.setQueuePassword(prop.get(ROIConstants.QUEUE_PD));
            reqLetter.setOutputMethod(prop.get(ROIConstants.OUTPUT_METHOD));
            reqLetter.setTemplateName(prop.get(ROIConstants.REQUESTOR_LETTER_TEMPLATE_NAME_KEY));
        }

        reqLetter.setRequestTemplateId(invLetterInfo.getRequestorLetterTemplateId());
        reqLetter.setCreatedBy(getUser().getInstanceIdValue());
        reqLetter.setRequestorId(requestorInvoices.getRequestorId());

        List<String> reqLetterNotes = invLetterInfo.getReqLetterNotes();
        reqLetter.setNotes(reqLetterNotes);

        //default details are set
        setDefaultDetails(reqLetter, date, true);
        return reqLetter;
    }

    /**
     * This method concatenates all the invoice ids of all the requestors
     * into an array
     *
     * @param reqInvoices
     * @return
     */
    private List<Long> getAllInvoiceIds(List<RequestorInvoices> reqInvoices) {

        Set<Long> invoiceIds = new HashSet<Long>();
        for (RequestorInvoices requestorInvoice : reqInvoices) {
            for (long id : requestorInvoice.getInvoiceIds()) {
                invoiceIds.add(id);
            }
        }

        List<Long> ids = new ArrayList<Long>();
        ids.addAll(invoiceIds);
        return ids;
    }

    /**
     * This method is used to generate the Letter for Output
     * This method is used by the ROI output services to genrate the Requestor letter
     * or the regenerated invoice File
     *
     * @param id
     * @param fileType
     * @param type
     * @return
     */
    
    
    @Override
    @WebMethod(exclude = true)
    public String regenerateLetter(long id, String fileType, String type) {

        final String logSM = "regenerateLetter(lovId, fileType, file)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        ROIDAO dao = getDAO(DAOName.OVERDUE_INVOICE_CORE_DAO);
        String fileName = generateInvoiceLetter(id, fileType, type, null, dao.getDate());

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return fileName;
    }

    /**
     * generates the requestor invoices
     *
     * @param id
     * @param docType
     * @param type
     * @param templateData
     * @param date
     * @return filename of the generated output PDF file
     */
    private String generateInvoiceLetter(long id, String docType, String type,
                                         Object templateData, Date date) {

        try {

            LetterDataRetriever dataRetriever = getLetterDataRetriever(type);
            Object letterData = null;
            if (templateData != null) {
                letterData = templateData;
            } else {
                letterData = dataRetriever.retrieveLetterData(id, 0);
            }

            LetterData data = (LetterData) letterData;
            String templateFileId = data.getTemplateFileId();

            long templateId = 0;
            if (null == templateFileId) {
                throw new ROIException(ROIClientErrorCodes.TEMPLATE_ID_IS_BLANK);
            }
            templateId = Long.parseLong(templateFileId);

            // Retrieve the corresponding template from the database
            LetterTemplateDocument templateDoc = dataRetriever.retrieveLetterTemplate(templateId);

            // filename of the Output PDF
            String fileName = type + "." + id + "."
                                + formatDate(date, FILENAME_DATE_FORMAT)
                                + ROIConstants.OUTPUT_FILE_EXT;

            String path = getCacheFileName(fileName);

            // Get the letter generator and generates the letter with the
            // template data and
            // corresponding template
            getLetterGenerator(docType).generateLetter(letterData, templateDoc, path);
            return fileName;

        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED);
        }

    }

}
