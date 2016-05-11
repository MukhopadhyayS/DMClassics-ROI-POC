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

package com.mckesson.eig.roi.journal.service;

import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.base.service.BaseROIService;
import com.mckesson.eig.roi.journal.dao.JournalTemplateDAO;
import com.mckesson.eig.roi.journal.model.FinancialLineItemType;
import com.mckesson.eig.roi.journal.model.JournalConstants.TransactionEvent;
import com.mckesson.eig.roi.journal.model.JournalTemplate;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class JournalTemplateServiceImpl extends BaseROIService implements JournalTemplateService {
    private List <JournalTemplate> _templates;
    private List <FinancialLineItemType> _types;

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

    @Override
    public String getJournalTransactionTypeSQL(long TransactionTypeId) {
        List<FinancialLineItemType> types = getTypes();
        if(!CollectionUtilities.isEmpty(types)) {
            for(FinancialLineItemType type : types) {
                if(type.getId() == TransactionTypeId) {
                    return type.getQuery();
                }
            }
        }
        return null;
    }

    @Override
    public List<JournalTemplate> getJournalTemplateByTransactionType(
            TransactionEvent type) {
        List<JournalTemplate> templates = getTemplates();
        List<JournalTemplate> result = new ArrayList<JournalTemplate>();
        if(CollectionUtilities.isEmpty(templates)) {
            return result;
        }
        for(JournalTemplate template : templates) {
            if(template.getTxnTypeId() == type.eventCode()) {
                result.add(template);
            }
        }
        return result;
    }

    
    private List <JournalTemplate> loadJournalTemplate() {
        JournalTemplateDAO dao = (JournalTemplateDAO) getDAO(DAOName.JOURNAL_TEMPLATE_DAO);
        return dao.loadJournalTemplate();
        
    }
    
    private List<FinancialLineItemType> loadFinancialLineItemType() {
        JournalTemplateDAO dao = (JournalTemplateDAO) getDAO(DAOName.JOURNAL_TEMPLATE_DAO);
        return dao.loadFinancialLineItemType();
    }

    private synchronized List<JournalTemplate> getTemplates() {
        if(CollectionUtilities.isEmpty(_templates)) {
            _templates = loadJournalTemplate();
        }
        return _templates;
    }

    private synchronized List<FinancialLineItemType> getTypes() {
        if(CollectionUtilities.isEmpty(_types)) {
            _types = loadFinancialLineItemType();
        }
        return _types;
    }
}
