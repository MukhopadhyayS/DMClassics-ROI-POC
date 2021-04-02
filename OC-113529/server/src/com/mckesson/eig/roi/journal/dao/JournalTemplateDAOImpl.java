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

package com.mckesson.eig.roi.journal.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.journal.model.FinancialLineItemType;
import com.mckesson.eig.roi.journal.model.JournalTemplate;
import com.mckesson.dm.core.common.logging.OCLogger;

public class JournalTemplateDAOImpl extends ROIDAOImpl implements JournalTemplateDAO {
    
    private static final OCLogger LOG = new OCLogger(JournalTemplateDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    private static final String JOURNAL_TEMPLATE = "select_all_from_Journal_Detail_Template";
    private static final String LINE_ITEM_TYPE = "select_all_from_FinancialEntity_LineItem_Type";

    public List <JournalTemplate> loadJournalTemplate() {
        final String logSM = "loadJournalTemplate()";
        if (DO_DEBUG) {
            LOG.debug(logSM);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery(JOURNAL_TEMPLATE).getQueryString();
            
            SQLQuery query = selectJournalDetailTemplate(session, queryString, JournalTemplate.class);

            List<JournalTemplate> templates = (List<JournalTemplate>) query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + templates);
            }
            if (templates == null ) {
                return new ArrayList<JournalTemplate>();
            }
            return templates;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
     }

    protected SQLQuery selectJournalDetailTemplate(Session session, String query, Class clazz) {
        SQLQuery sqlQuery = super.prepareSQLQuery(session, query, clazz);
        sqlQuery.addScalar("txnTypeId", Hibernate.LONG);
        sqlQuery.addScalar("lineItemTypeId", Hibernate.LONG);
        sqlQuery.addScalar("ledgerAccountId", Hibernate.LONG);
        sqlQuery.addScalar("credit", Hibernate.BOOLEAN);
        sqlQuery.addScalar("increase", Hibernate.BOOLEAN);
        sqlQuery.addScalar("active", Hibernate.BOOLEAN);
        return sqlQuery;
    }
    
    
    public List<FinancialLineItemType> loadFinancialLineItemType() {
        final String logSM = "loadFinancialLineItemType()";
        if (DO_DEBUG) {
            LOG.debug(logSM);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery(LINE_ITEM_TYPE).getQueryString();
            
            SQLQuery query = selectLineItemType(session, queryString, FinancialLineItemType.class);

            List<FinancialLineItemType> types = (List<FinancialLineItemType>) query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + types);
            }
            if (types == null ) {
                return new ArrayList<FinancialLineItemType>();
            }
            return types;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }

    protected SQLQuery selectLineItemType(Session session, String query, Class clazz) {
        SQLQuery sqlQuery = super.prepareSQLQuery(session, query, clazz);
        sqlQuery.addScalar("entityId", Hibernate.LONG);
        sqlQuery.addScalar("txnTypeId", Hibernate.LONG);
        sqlQuery.addScalar("name", Hibernate.STRING);
        sqlQuery.addScalar("code", Hibernate.STRING);
        sqlQuery.addScalar("query", Hibernate.STRING);
        return sqlQuery;
    }
    
    
    
}
