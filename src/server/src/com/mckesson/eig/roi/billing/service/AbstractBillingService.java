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

package com.mckesson.eig.roi.billing.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mckesson.eig.roi.admin.dao.LetterTemplateDAO;
import com.mckesson.eig.roi.admin.model.LetterTemplateDocument;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.LetterDataRetriever;
import com.mckesson.eig.roi.base.dao.ROIDAO;
import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.base.service.LetterDataBuilder;
import com.mckesson.eig.roi.base.service.LetterGenerator;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.model.DocInfo;
import com.mckesson.eig.roi.billing.model.DocInfoList;
import com.mckesson.eig.roi.billing.model.RequestorLetter;
import com.mckesson.eig.roi.journal.service.JournalService;
import com.mckesson.eig.roi.muroioutbound.service.MUUpdateROIOutboundServiceCore;
import com.mckesson.eig.roi.request.dao.RequestCoreDAO;
import com.mckesson.eig.roi.request.model.RequestEvent.TYPE;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.AdjustmentType;
import com.mckesson.eig.roi.requestor.model.RequestorAccount;
import com.mckesson.eig.roi.requestor.model.RequestorAging;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorStatementCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.roi.requestor.model.RequestorTransaction;
import com.mckesson.eig.roi.utils.AccessFileLoader;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;


/**
 * @author OFS
 * @date   Sep 21, 2011
 * @since  Sep 21, 2011
 */
public class AbstractBillingService extends BaseROIService {

    private static final OCLogger LOG = new OCLogger(AbstractBillingService.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    protected static final long ONE_DAY = 1000 * 60 * 60 * 24 * 1;
    protected static final String BILL_DATE_FORMAT = "MM/dd/yyyy";
    private static final String FILENAME_DATE_FORMAT = "yyyy.MM.dd.hh.mm.ss.SSS";

    /**
     * Adds the given number of days to the CurrentDate
     * @param noofDays
     * @return String - return date in String Type formatted
     */
    protected String getDateStringByDaysAhead(long noofDays) {

        Date date = new Date();
        long dt = date.getTime() + (ONE_DAY * noofDays);
        date.setTime(dt);

        return formatDate(date, ROIConstants.INVOICE_DATE_FORMAT);
    }

    /**
     * This method gets the LetterDataRetriever with the given key
     * @param key
     * @return LetterDataRetriever
     */
    protected LetterDataRetriever getLetterDataRetriever(String key) {

        return (LetterDataRetriever) SpringUtilities.getInstance().
                                   getBeanFactory().getBean(key + "_LetterDataRetriever");
    }

    /**
     * This method gets the LetterDataBuilder with the given key
     * @param key
     * @return LetterDataBuilder
     */
    protected LetterDataBuilder getLetterDataBuilder(String key) {

        return (LetterDataBuilder) SpringUtilities.getInstance().
                                   getBeanFactory().getBean(key + "_LetterDataBuilder");
    }

    protected String formatDate(Date date, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * This method gets the LetterGenerator with the given key
     * @param key
     * @return LetterGenerator
     */
    protected LetterGenerator getLetterGenerator(String key) {

        return (LetterGenerator) SpringUtilities.getInstance().
                                 getBeanFactory().getBean(key + "_LetterGenerator");
    }

    /**
     * This method used to retrieves the file from the cache
     * @param fileName name of the filename
     * @return filepath
     */
    protected String getCacheFileName(String fileName) {

        final String logSM = "getFileCachePath(fileName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:FileName : " + fileName);
        }

        String cacheDir = retrieveTempDirectory()
                          + File.separatorChar
                          + ROIConstants.OUTPUT_CACHE_DIR;

        boolean dirCreated = false;
        try {
            File f = AccessFileLoader.getFile(cacheDir);
            dirCreated = f.mkdir();
        } catch (IOException e) {
                 LOG.error("Exception in getCacheFileName");
        }
        
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Cache dir created " + dirCreated);
        }
        return cacheDir + File.separator + fileName;
    }

    /**
     * This method generates the todays date with the
     * @return Date
     */
    protected Date getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        return calendar.getTime();
    }
    /**
     *
     * @param fileNames
     * @return
     */
    protected String generatePDF(List<String> fileNames, String type) {

        String file = type
                + "." + formatDate(new Date(), FILENAME_DATE_FORMAT)
                +  ROIConstants.OUTPUT_FILE_EXT;

        String path = getCacheFileName(file);

        PdfUtilities.concatenate(fileNames, path);
        PdfUtilities.encrypt(path);
        deleteFiles(fileNames);

        return file;
    }

    /**
     * Calculate the difference between the given two date String
     *
     */
    protected long calculateDaysDifference(String dateString1, String dateString2) {

        try {

            Date date = new SimpleDateFormat(BILL_DATE_FORMAT).parse(dateString1);
            Date date1 = new SimpleDateFormat(BILL_DATE_FORMAT).parse(dateString2);
            long datediff = date.getTime() - date1.getTime();

            return (datediff / (ONE_DAY));
        } catch (ParseException e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INVALID_INVOICE_DATE_FORMAT);
        }
    }

    /**
     * Calculate the difference between the todays date and give date in days
     *
     */
    protected long calculateDaysDifference(String dateString) {

        try {

            Date date = new SimpleDateFormat(BILL_DATE_FORMAT).parse(dateString);
            long datediff = date.getTime() - getCurrentDate().getTime();

            return (datediff / (ONE_DAY));
        } catch (ParseException e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INVALID_INVOICE_DATE_FORMAT);
        }
    }

    /**
     * Parse the given date string into date based on the given formatter
     *
     */
    protected Date parseDateString(String dateString, DateFormat formatter) {

        if (StringUtilities.isEmpty(dateString)) {
            return null;
        }

        try {

            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.INVALID_INVOICE_DATE_FORMAT);
        }
    }

    /**
     * This method deletes the given list of files
     * @param fileNames
     */
    protected void deleteFiles(List<String> fileNames) {
        try {
            if ((fileNames != null) && (fileNames.size() > 0)) {
                for (String name : fileNames) {
                    File f = AccessFileLoader.getFile(name);
                    f.delete();
                }
            }
        } catch(Exception e) {
            LOG.error("Exception in deleteFiles");
        }
    }

    /**
     *
     * @param templateFileId
     * @return
     */
    protected LetterTemplateDocument getLetterTemplateDocument(long templateFileId) {

        LetterTemplateDocument doc = null;
        if (templateFileId != 0) {
            doc = ((LetterTemplateDAO) getDAO(DAOName.LETTER_TEMPLATE_DAO))
                        .retrieveLetterTemplateDocument(templateFileId);
        }
        return doc;
    }

    /**
     * @param listOfDocInfos
     * @return
     */
    protected DocInfoList mergeDocInfos(List<DocInfo> listOfDocInfos, String type) {

        if (CollectionUtilities.isEmpty(listOfDocInfos)) {
            return null;
        }

        List<String> fileNames = new ArrayList<String>(listOfDocInfos.size());
        for (DocInfo docInfo : listOfDocInfos) {
            fileNames.add(getCacheFileName(docInfo.getName()));
        }

        return new DocInfoList(listOfDocInfos, generatePDF(fileNames, type));
    }

    protected void deleteLatestRequestEvent(long requestId, TYPE eventType) {

        final String logSM = "deleteLatestRequestEvent(requestId, eventType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestId:" + requestId + ", EventType:" + eventType);
        }

        try {

            if (requestId <= 0) {
                throw new ROIException(ROIClientErrorCodes.INVALID_COVER_LETTER);
            }

            RequestCoreDAO requestCoreDao =  (RequestCoreDAO) getDAO(DAOName.REQUEST_CORE_DAO);
            requestCoreDao.deleteLatestRequestEvent(requestId, eventType);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:");
            }

        } catch (ROIException e) {
            throw e;
        } catch (Throwable e) {
            LOG.error("Exception occured in deleteLatestRequestEvent",e);
            throw new ROIException(e, ROIClientErrorCodes.LETTER_CREATION_FAILED);
        }
    }

    protected String generateLetter(long id,
                                    String docType,
                                    String type,
                                    Object item,
                                    Date date) {

        try {

            LetterDataRetriever dataRetriever = getLetterDataRetriever(type);
            Object letterData = null;
            if (item != null) {
                letterData = item;
            } else {
                letterData = dataRetriever.retrieveLetterData(id, 0);
            }

            LetterData data = (LetterData) letterData;
            String templateFileId = data.getTemplateFileId();
            if (null == templateFileId) {
                throw new ROIException(ROIClientErrorCodes.TEMPLATE_ID_IS_BLANK);
            }

            long templateId = Long.parseLong(templateFileId);
            LetterTemplateDocument template = dataRetriever.retrieveLetterTemplate(templateId);

            String fileName = type + "." + id + "."
                    + formatDate(date, FILENAME_DATE_FORMAT)
                    + ROIConstants.OUTPUT_FILE_EXT;

            String path = getCacheFileName(fileName);

            // Get the letter generator and generates the letter with the
            // template data and
            // corresponding template
            getLetterGenerator(docType).generateLetter(letterData, template, path);
            return fileName;
        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED);
        }
    }


    protected List<String> generateLetter(long invoiceId,
                                          long requestId,
                                          String docType,
                                          String retrieverType,
                                          String letterType,
                                          long letterTemplateId,
                                          String[] notes) {

        try {

            List<String> retValues = new ArrayList<String>();
            LetterDataRetriever dataRetriever = getLetterDataRetriever(retrieverType);

            LetterTemplateDocument template = null;

            // Convert the retrieved data into a form which can be fed to a letter template
            Object templateDataObj = dataRetriever.retrieveLetterData(invoiceId, requestId);
            LetterData templateData = (LetterData) templateDataObj;

            if (letterTemplateId <= 0) {
                letterTemplateId = Long.valueOf(templateData.getTemplateFileId());
            }

            template = dataRetriever.retrieveLetterTemplate(letterTemplateId);

            if (notes != null && notes.length > 0) {

                List<Note> notesList = new ArrayList<Note>();
                Note notesTemp = new Note();
                List<Note> noteList = new ArrayList<Note>();
                for (String noteDesc : notes) {

                    Note note = new Note();
                    note.setDescription(noteDesc);
                    noteList.add(note);
                }
                notesTemp.setChildItems(noteList);
                notesList.add(notesTemp);

                templateData.setNotesList(notesList);
            }

            ROIDAO dao = getDAO(DAOName.ADMINLOV_DAO);
            Date date = dao.getDate();

            String fileName = new StringBuffer(letterType)
                                   .append(".").append(templateData.getInvoiceId())
                                   .append(".").append(formatDate(date, FILENAME_DATE_FORMAT))
                                   .append(ROIConstants.OUTPUT_FILE_EXT).toString();


            String path = getCacheFileName(fileName);

            // Get the letter generator and
            // generates the letter with the template data and corresponding template
            getLetterGenerator(docType).generateLetter(templateData, template, path);
            retValues.add(fileName);
            retValues.add(template.getName());
            retValues.add(templateData.getInvoiceId());

            return retValues;
        } catch (ROIException e) {
            throw e;
        } catch (Exception e) {
            throw new ROIException(e, ROIClientErrorCodes.GENERATE_LETTER_OPERATION_FAILED);
        }

    }
     /**
     * Method to get the instance for MUCreateROIOutboundServiceCoreImpl
     */
    protected MUUpdateROIOutboundServiceCore getMUUpdateROIOutboundServiceCore() {
        return (MUUpdateROIOutboundServiceCore)
                                            getService(ServiceName.MU_UPDATE_ROIOUTBOUND_SERVICE);
    }

    /**
     * creates the past invoice for the given past invoices ids
     * @param requestId
     * @param invoiceIds
     * @param templateId
     * @return docInfo list
     */
    protected List<DocInfo> generatePastInvoices(List<Long> invoiceIds) {

        final String logSM = "generatePastInvoices(pastInvoices)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceIds);
        }

        if (CollectionUtilities.isEmpty(invoiceIds)) {
            return new ArrayList<DocInfo>();
        }

        LetterDataRetriever  retriever = getLetterDataRetriever(ROIConstants.INVOICE);
        LetterTemplateDocument template;

        DocInfo docInfo;
        List<DocInfo> docInfos = new ArrayList<DocInfo>();
        for (Long invoiceId : invoiceIds) {

            Object letterData = retriever.retrieveLetterData(invoiceId, 0);

            LetterData data = (LetterData) letterData;
            String templateFileId = data.getTemplateFileId();

            long id = StringUtilities.isEmpty(templateFileId) ? 0
                                                           : Long.parseLong(templateFileId.trim());
            template = retriever.retrieveLetterTemplate(id);

            String fileName = new StringBuffer(ROIConstants.INVOICE)
            .append("." + formatDate(new Date(), FILENAME_DATE_FORMAT))
            .append(ROIConstants.OUTPUT_FILE_EXT).toString();

            String path = getCacheFileName(fileName);

            // generate the invoices based on the given template and the item model
            getLetterGenerator(ROIConstants.TEMPLATE_FILE_TYPE).generateLetter(letterData,
                                                                          template,
                                                                          path);

            docInfo = new DocInfo(invoiceId, fileName, ROIConstants.INVOICE);
            docInfos.add(docInfo);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Generated Past Invoices");
        }

        return docInfos;
    }

    /**
     * retrieves the requestor Statement Info objcet
     * @param criteria
     * @return retrieved requestor Statement Info
     */
    protected RequestorStatementInfo retrieveRequestorStatementInfo(
                                                        RequestorStatementCriteria criteria) {

        RequestorStatementDAO dao = (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

        long requestorId = criteria.getRequestorId();
        List<RequestorTransaction> requestorTransactions = dao
                .retrieveRequestorTransactions(requestorId, criteria.getDateRange());

        RequestorStatementInfo requestorInfo = dao.retrieveRequestorInfo(requestorId);
        RequestorAging agingBalances = dao.retrieveRequestorAgingBalances(requestorId);
        RequestorAccount accountBalances = dao.retrieveRequestorAccountBalances(requestorId);

        requestorInfo.setRequestorAccount(accountBalances);
        requestorInfo.setTransactions(requestorTransactions);
        requestorInfo.setRequestorAging(agingBalances);

        requestorInfo.setTemplateFileId(criteria.getTemplateFileId());
        requestorInfo.setTemplateName(criteria.getTemplateName());
        requestorInfo.setOutputMethod(criteria.getOutputMethod());
        requestorInfo.setQueuePassword(criteria.getQueuePassword());
        requestorInfo.setNotes(criteria.getNotes());
        requestorInfo.setDateRange(criteria.getDateRange());

        return requestorInfo;
    }

    /**
     * setting data to RequestorLetter from RequestorStatementInfo.
     */
    protected RequestorLetter constructRequestorLetterFromStatementInfo(
                                                    RequestorStatementInfo requestorInfo) {

        RequestorLetter requestorLetter = new RequestorLetter();
        requestorLetter.setNotes(requestorInfo.getNotes());
        requestorLetter.setOutputMethod(requestorInfo.getOutputMethod());
        requestorLetter.setQueuePassword(requestorInfo.getQueuePassword());
        requestorLetter.setRequestorAddress1(requestorInfo.getAddress1());
        requestorLetter.setRequestorAddress2(requestorInfo.getAddress2());
        requestorLetter.setRequestorAddress3(requestorInfo.getAddress3());
        requestorLetter.setRequestorCity(requestorInfo.getCity());
        requestorLetter.setRequestorCountry(requestorInfo.getCountry());
        requestorLetter.setRequestorId(requestorInfo.getId());
        requestorLetter.setRequestorName(requestorInfo.getName());
        requestorLetter.setRequestorPhone(requestorInfo.getHomePhone());
        requestorLetter.setRequestorPostalCode(requestorInfo.getPostalCode());
        requestorLetter.setRequestorState(requestorInfo.getState());
        requestorLetter.setRequestTemplateId(requestorInfo.getTemplateFileId());
        requestorLetter.setResendDate(requestorInfo.getResendDate());
        requestorLetter.setTemplateName(requestorInfo.getTemplateName());
        requestorLetter.setDateRange(requestorInfo.getDateRange());

        RequestorAccount requestorAccount = requestorInfo.getRequestorAccount();
        if (null != requestorAccount) {

            requestorLetter.setCharges(requestorAccount.getCharge());
            requestorLetter.setCreditAdjustmentAmount(requestorAccount.getCreditAdjustmentAmount());
            requestorLetter.setDebitAdjustmentAmount(requestorAccount.getDebitAdjustmentAmount());
            requestorLetter.setAdjustmentAmount(requestorAccount.getAdjustmentAmount());
            requestorLetter.setPaymentAmount(requestorAccount.getPaymentAmount());
            requestorLetter.setUnAppliedAdjustment(requestorAccount.getUnAppliedAdjustment());
            requestorLetter.setUnAppliedPayment(requestorAccount.getUnAppliedPayment());
            requestorLetter.setUnAppliedAmount(requestorAccount.getUnAppliedAmount());
            requestorLetter.setBalances(requestorAccount.getBalances());

        }
        RequestorAging requestorAging = requestorInfo.getRequestorAging();
        if (null != requestorAging) {

            requestorLetter.setBalance30(requestorAging.getBalance30());
            requestorLetter.setBalance60(requestorAging.getBalance60());
            requestorLetter.setBalance90(requestorAging.getBalance90());
            requestorLetter.setBalanceOther(requestorAging.getBalanceOther());
        }

        return requestorLetter;
    }

    /**
     * setting data to RequestorLetter from RequestorStatementInfo.
     */
    protected RequestorLetter constructRequestorLetterFromRefundInfo(
                                                            RequestorStatementInfo requestorInfo) {

        RequestorLetter requestorLetter = new RequestorLetter();

        requestorLetter.setNotes(requestorInfo.getNotes());
        requestorLetter.setOutputMethod(requestorInfo.getOutputMethod());
        requestorLetter.setQueuePassword(requestorInfo.getQueuePassword());
        requestorLetter.setRequestorAddress1(requestorInfo.getAddress1());
        requestorLetter.setRequestorAddress2(requestorInfo.getAddress2());
        requestorLetter.setRequestorAddress3(requestorInfo.getAddress3());
        requestorLetter.setRequestorCity(requestorInfo.getCity());
        requestorLetter.setRequestorId(requestorInfo.getId());
        requestorLetter.setRequestorName(requestorInfo.getName());
        requestorLetter.setRequestorPhone(requestorInfo.getHomePhone());
        requestorLetter.setRequestorPostalCode(requestorInfo.getPostalCode());
        requestorLetter.setRequestorState(requestorInfo.getState());
        requestorLetter.setRequestTemplateId(requestorInfo.getTemplateFileId());
        requestorLetter.setResendDate(requestorInfo.getResendDate());
        requestorLetter.setTemplateName(requestorInfo.getTemplateName());
        requestorLetter.setDateRange(requestorInfo.getDateRange());

        RequestorAccount requestorAccount = requestorInfo.getRequestorAccount();
        if (null != requestorAccount) {

            requestorLetter.setCharges(requestorAccount.getCharge());
            requestorLetter.setCreditAdjustmentAmount(requestorAccount.getCreditAdjustmentAmount());
            requestorLetter.setDebitAdjustmentAmount(requestorAccount.getDebitAdjustmentAmount());
            requestorLetter.setAdjustmentAmount(requestorAccount.getAdjustmentAmount());
            requestorLetter.setPaymentAmount(requestorAccount.getPaymentAmount());
            requestorLetter.setUnAppliedAdjustment(requestorAccount.getUnAppliedAdjustment());
            requestorLetter.setUnAppliedPayment(requestorAccount.getUnAppliedPayment());
            requestorLetter.setUnAppliedAmount(requestorAccount.getUnAppliedAmount());
            requestorLetter.setBalances(requestorAccount.getBalances());

        }
        RequestorAging requestorAging = requestorInfo.getRequestorAging();
        if (null != requestorAging) {

            requestorLetter.setBalance30(requestorAging.getBalance30());
            requestorLetter.setBalance60(requestorAging.getBalance60());
            requestorLetter.setBalance90(requestorAging.getBalance90());
            requestorLetter.setBalanceOther(requestorAging.getBalanceOther());
        }

        return requestorLetter;
    }


    /**
     * Based on the amount given in the payment list
     * the list of unapplied payments are grouped and assigned to the
     * payments list in the return model.
     *
     * @param adjPayList
     * @param date
     * @param unappliedAdjPayments
     * @return adjPayList
     */
    protected RequestorPaymentList applyPayments(RequestorPaymentList payList,
            List<RequestorPayment> unappliedPayments, Date date) {

        double balance = payList.getPaymentAmount();
        List<RequestorPayment> payments = new ArrayList<RequestorPayment>();
        RequestorPayment payment =  null;
        double appliedAmt = 0;
        double newUnappliedAmount = 0;
        double unAppliedAmt = 0;
        double refundAmount = 0;

        for (RequestorPayment reqPayment : unappliedPayments) {

            if (balance <= 0) {
                break;
        	}

            appliedAmt = 0;
            newUnappliedAmount = 0;
            unAppliedAmt = reqPayment.getUnAppliedAmount();
            refundAmount = reqPayment.getRefundAmount();

            if (unAppliedAmt > balance) {

                newUnappliedAmount = (unAppliedAmt - balance);
                appliedAmt = balance;
                balance = 0;
            } else {

                appliedAmt = unAppliedAmt;
                balance -= unAppliedAmt;
                newUnappliedAmount = 0;

            }

            refundAmount += (unAppliedAmt - newUnappliedAmount);
            
            payment = new RequestorPayment();
            payment.setPaymentId(reqPayment.getPaymentId());
            payment.setLastAppliedAmount(appliedAmt);
            payment.setUnAppliedAmount(newUnappliedAmount);
            payment.setRefundAmount(refundAmount);
            setDefaultDetails(payment, date, false);

            payments.add(payment);
        }

        payList.setPaymentList(payments);

        return payList;
    }

    /**
     * generate preview of the requestor refund
     * @param criteria
     * @return docinfo containing the generated pdf details
     */
    protected DocInfo previewRequestorStatement(RequestorStatementCriteria criteria) {

        RequestorStatementDAO dao =
                (RequestorStatementDAO) getDAO(DAOName.REQUESTOR_STATEMENT_DAO);

        RequestorStatementInfo requestorInfo = retrieveRequestorStatementInfo(criteria);
        LetterDataRetriever  retriever =
                                    getLetterDataRetriever(ROIConstants.REQUESTORLETTER);
        LetterData letterData =
                          (LetterData) retriever.constructTemplateDataModel(requestorInfo);

        String fileName = generateLetter(criteria.getRequestorId(),
                                         ROIConstants.TEMPLATE_FILE_TYPE,
                                         ROIConstants.REQUESTORLETTER,
                                         letterData,
                                         dao.getDate());
        // Checkmarx: ROI - Java - Heap_Inspection - Clear the queue password value post processing 
        if (null != letterData) {
            letterData.setQueuePassword(null);
        }
        DocInfo  docInfo = new DocInfo(0, fileName, ROIConstants.REQUESTOR_LETTER_FILE);
        return docInfo;
    }

    /**
     * This method will retrieve the EIWDATAConfiguration location
     * @param key
     * @return EIWDATAConfiguration location
     */
    public String retrieveTempDirectory() {

        try {

            ROIDAO dao = getDAO(DAOName.ATTACHMENT_DAO);
            String location = dao
                    .retrieveEIWDATAConfiguration(ROIConstants.TEMP_DIRECTORY_LOCATION_KEY);

            if (location == null) {
                throw new ROIException(ROIClientErrorCodes.TEMP_DIRECTORY_CONFIG_MISSING);
            }
            return location;

        } catch (ROIException e) {

            LOG.error("Temp Directory is not configured in the Database", e);
            throw e;
        } catch (Throwable e) {

            LOG.error("Temp Directory is not configured in the Database", e);
            throw new ROIException(e, ROIClientErrorCodes.TEMP_DIRECTORY_CONFIG_MISSING,
                                   "Temp Directory is not configured in the Database");
        }
    }

    /**
     * This method will Create Journal entry for adjustment activities
     * @param transType
     * @param adjType
     * @param id
     */
    public void createJournalEntryForAdjustment(String transType, AdjustmentType adjType, long id) {

        JournalService journalService = (JournalService) getService(ServiceName.JOURNEL_SERVICE);

        if (ROIConstants.UNAPPLY_ADJ_FROM_INVOICE.equals(transType)) {

            if (AdjustmentType.BAD_DEBT_ADJUSTMENT.equals(adjType)) {
                journalService.createUnapplyBadDebtAdjustmentFromInvoiceJE(id);
            } else if (AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT.equals(adjType)) {
                journalService.createUnapplyGoodWillAdjustmentFromInvoiceJE(id);
            } else if (AdjustmentType.BILLING_ADJUSTMENT.equals(adjType)) {
                journalService.createUnapplyAdjustmentFromInvoiceJE(id);
            }

        } else if (ROIConstants.APPLY_ADJ_TO_INVOICE.equals(transType)) {

            if (AdjustmentType.BAD_DEBT_ADJUSTMENT.equals(adjType)) {
                journalService.createApplyBadDebtAdjustmentToInvoiceJE(id);
            } else if (AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT.equals(adjType)) {
                journalService.createApplyGoodWillAdjustmentToInvoiceJE(id);
            } else if (AdjustmentType.BILLING_ADJUSTMENT.equals(adjType)) {
                journalService.createApplyAdjustmentToInvoiceJE(id);
            }

        } else if (ROIConstants.REMOVE_UNAPPLIED_ADJUSTMENT.equals(transType)) {

            if (AdjustmentType.BAD_DEBT_ADJUSTMENT.equals(adjType)) {
                journalService.createRemoveBadDebtJE(id);
            } else if (AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT.equals(adjType)) {
                journalService.createRemoveCustomerGoodwillJE(id);
            } else if (AdjustmentType.BILLING_ADJUSTMENT.equals(adjType)) {
                journalService.createRemoveAdjustmentJE(id);
            }
        } else if (ROIConstants.CREATE_ADJUSTMENT.equals(transType)) {

            if (AdjustmentType.BAD_DEBT_ADJUSTMENT.equals(adjType)) {
                journalService.createRecordBadDebtJE(id);
            } else if (AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT.equals(adjType)) {
                journalService.createRecordCustomerGoodwillJE(id);
            } else if (AdjustmentType.BILLING_ADJUSTMENT.equals(adjType)) {
                journalService.createCreateAdjustmentJE(id);
            }
        }

    }

}
