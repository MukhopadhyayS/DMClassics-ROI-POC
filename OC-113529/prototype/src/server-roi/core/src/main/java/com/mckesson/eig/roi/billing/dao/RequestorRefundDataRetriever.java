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

package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.mckesson.eig.roi.base.dao.BaseLetterDataRetriever;
import com.mckesson.eig.roi.billing.letter.model.LetterData;
import com.mckesson.eig.roi.billing.letter.model.Note;
import com.mckesson.eig.roi.billing.letter.model.RequestorInfo;
import com.mckesson.eig.roi.requestor.dao.RequestorStatementDAO;
import com.mckesson.eig.roi.requestor.model.RefundLetter;
import com.mckesson.eig.roi.requestor.model.RequestorStatementInfo;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;


/**
 * The Class RequestorRefundDataRetriever.
 *
 * @author Elangovan Shanmugam
 * @date   Jan 16, 2013
 */

public class RequestorRefundDataRetriever
extends BaseLetterDataRetriever {

    private static final Log LOG = LogFactory.getLogger(RequestorRefundDataRetriever.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *  This method is to retrieve the data required to generate requestor letter
     *  for the given requestor letter id
     *
     */
    @Override
    public Object retrieveLetterData(long refundLetterId, long requestId) {

        final String logSM = "retrieveLetterData(refundLetterId, RequestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: RefundLetterId:" + refundLetterId
                            + ", RequestId:" + requestId);
        }

        RefundLetter refundLetter = retrieveRefundLetterDetails(refundLetterId);
        Object templateData = constructTemplateDataModel(refundLetter);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End");
        }

        return templateData;

    }

    /**
     * @see com.mckesson.eig.roi.base.dao.LetterDataRetriever
     * #constructTemplateDataModel(java.lang.Object)
     */
    @Override
    public Object constructTemplateDataModel(Object data) {

        RefundLetter reqStmt = (RefundLetter) data;
        LetterData letterData = new LetterData();

        letterData.setTemplateName(reqStmt.getTemplateName());
        letterData.setTemplateFileId(String.valueOf(reqStmt.getTemplateFileId()));
        List<String> notes = reqStmt.getNotes();
        if (CollectionUtilities.hasContent(notes)) {

            List<Note> notesList = new ArrayList<Note>();
            Note note;
            for (String noteString : notes) {

                note = new Note();
                note.setDescription(noteString);
                notesList.add(note);
            }

            List<Note> templateNotes = new ArrayList<Note>();
            Note tNote = new Note();
            tNote.setChildItems(notesList);
            templateNotes.add(tNote);
            letterData.setNotesList(templateNotes);
        }

        letterData.setOutputMethod(reqStmt.getOutputMethod());
        letterData.setQueuePassword(reqStmt.getQueuePassword());

        RequestorInfo requestorInfo = new RequestorInfo();
        requestorInfo.setId(String.valueOf(reqStmt.getRequestorId()));
        requestorInfo.setName(reqStmt.getRequestorName());
//        requestorInfo.setPhone(reqStmt.getPhone());
//        requestorInfo.setCellPhone(reqStmt.getCellPhone());
//        requestorInfo.setHomePhone(reqStmt.getHomePhone());
//        requestorInfo.setWorkPhone(reqStmt.getWorkPhone());
        requestorInfo.setType(reqStmt.getRequestorTypeName());
        requestorInfo.setAddress1(reqStmt.getAddress1());
        requestorInfo.setAddress2(reqStmt.getAddress2());
        requestorInfo.setAddress3(reqStmt.getAddress3());
        requestorInfo.setCity(reqStmt.getCity());
        requestorInfo.setState(reqStmt.getState());
        requestorInfo.setCountry(reqStmt.getCountry());
        requestorInfo.setCountryCode(reqStmt.getCountryCode());
        requestorInfo.setPostalCode(reqStmt.getPostalCode());

        letterData.setRequestor(requestorInfo);
        letterData.setRefundAmount(formatToCurrency(reqStmt.getRefundAmount()));
        letterData.setUserName(reqStmt.getUserName());

        return letterData;
    }

    /**
     * retrieves the refund information
     * @param refundLetterId
     * @return retrieved refund letter
     */
    private RefundLetter retrieveRefundLetterDetails(long refundLetterId) {

        Session session = getSessionFactory().getCurrentSession();
        String queryString =
                session.getNamedQuery("retrieveRequestorRefund").getQueryString();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("refundId", refundLetterId, StandardBasicTypes.LONG);

        query.addScalar("requestorId", StandardBasicTypes.LONG);
        query.addScalar("refundAmount", StandardBasicTypes.DOUBLE);
        query.addScalar("refundDate", StandardBasicTypes.TIMESTAMP);
        query.addScalar("note", StandardBasicTypes.STRING);
        query.addScalar("userName", StandardBasicTypes.STRING);
        query.addScalar("templateFileId", StandardBasicTypes.LONG);
        query.addScalar("templateName", StandardBasicTypes.STRING);
        query.addScalar("outputMethod", StandardBasicTypes.STRING);
        query.addScalar("queuePassword", StandardBasicTypes.STRING);

        query.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("createdBy", StandardBasicTypes.LONG);
        query.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedBy", StandardBasicTypes.LONG);
        query.addScalar("recordVersion", StandardBasicTypes.INTEGER);
        query.setResultTransformer(Transformers.aliasToBean(RefundLetter.class));

        RefundLetter refundLetter = (RefundLetter) query.uniqueResult();
        refundLetter.setUserInstanceId(refundLetter.getCreatedBy());

        RequestorStatementDAO dao = getRequestorStatementDAO();

        long requestorId = refundLetter.getRequestorId();
        RequestorStatementInfo requestorInfo = dao.retrieveRequestorInfo(requestorId);

        refundLetter.setRequestorName(requestorInfo.getName());
        refundLetter.setRequestorTypeName(requestorInfo.getRequestorTypeName());
        refundLetter.setRequestorTypeId(requestorInfo.getRequestorTypeId());
        refundLetter.setAddress1(requestorInfo.getAddress1());
        refundLetter.setAddress2(requestorInfo.getAddress2());
        refundLetter.setAddress3(requestorInfo.getAddress3());
        refundLetter.setCity(requestorInfo.getCity());
        refundLetter.setState(requestorInfo.getState());
        refundLetter.setPostalCode(requestorInfo.getPostalCode());

        return refundLetter;
    }

}
