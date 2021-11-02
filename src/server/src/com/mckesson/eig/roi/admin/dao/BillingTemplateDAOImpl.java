/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.admin.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.admin.model.BillingTemplate;
import com.mckesson.eig.roi.admin.model.BillingTemplatesList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author OFS
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; May 20, 2008
 */
@Transactional
public class BillingTemplateDAOImpl
extends ROIDAOImpl
implements BillingTemplateDAO {

    private static final OCLogger LOG = new OCLogger(BillingTemplateDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#createBillingTemplate
     * (com.mckesson.eig.roi.admin.model.BillingTemplate)
     */
    public long createBillingTemplate(BillingTemplate billingTemplate) {

        final String logSM = "createBillingTemplate(billingTemplate)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTemplate.getName());
        }

        long id = toPlong((Long) create(billingTemplate));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: ID:" + id);
        }
        return id;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#retrieveBillingTemplate(long)
     */
    public BillingTemplate retrieveBillingTemplate(long billingTemplateId) {

        final String logSM = "retrieveBillingTemplate(long billingTemplateId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + billingTemplateId);
        }

        BillingTemplate billingTemplate = (BillingTemplate) get(BillingTemplate.class,
                                                                billingTemplateId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End : BillingTemplate name " + billingTemplate.getName());
        }
        return billingTemplate;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#retrieveAllBillingTemplates()
     */
    public BillingTemplatesList retrieveAllBillingTemplates() {

        final String logSM = "retrieveAllBillingTemplates()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> billingTemplates = (List<Object[]>)
            getHibernateTemplate().execute(new HibernateCallback<Object>() {

             public Object doInHibernate(Session s) {

                 return s.createCriteria(BillingTemplate.class)
                         .createAlias("relatedFeeTypes", "relatedFeeTypes")
                         .setProjection(Projections.projectionList()
                                        .add(Projections.property("id"))
                                        .add(Projections.property("name"))
                                        .add(Projections.property("relatedFeeTypes.feeTypeId")))
                         .addOrder(Order.asc("name"))
                         .list();

             }
         });

        Map<Long, BillingTemplate> billingTemplateMap = new HashMap<Long, BillingTemplate>();

        for (Object[] values : billingTemplates) {

            Long btId = toPlong((Long) values[0]);
            BillingTemplate bt = new BillingTemplate();
            if (billingTemplateMap.containsKey(btId)) {
                bt = billingTemplateMap.get(btId);
            }
            bt.setId(toPlong(btId.longValue()));
            bt.setName((String) values[1]);

            bt.getFeeTypeIds().add(toPlong((Long) values[2]));

            if (!billingTemplateMap.containsKey(btId)) {
                billingTemplateMap.put(btId, bt);
            }
        }

        BillingTemplatesList billingTemplateslist =
              new BillingTemplatesList(new ArrayList<BillingTemplate>(billingTemplateMap.values()));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + "No of Records " + billingTemplates.size());
        }
        return billingTemplateslist;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#deleteBillingTemplate(long)
     */
    public BillingTemplate deleteBillingTemplate(long billingTemplateId) {

        final String logSM = "deleteBillingTemplate(billingTemplateId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTemplateId);
        }

        BillingTemplate billingTemplate = retrieveBillingTemplate(billingTemplateId);
        delete(billingTemplate);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return billingTemplate;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#updateBillingTemplate
     * (com.mckesson.eig.roi.admin.model.BillingTemplate,
     *  com.mckesson.eig.roi.admin.model.BillingTemplate)
     */
    public BillingTemplate updateBillingTemplate(BillingTemplate billingTemplate,
                                                 BillingTemplate originalBillingTemplate) {

        final String logSM = "updateBillingTemplate(billingTemplate)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTemplate.getName());
        }

        billingTemplate.copyFrom(originalBillingTemplate);
        BillingTemplate bt = (BillingTemplate) merge(billingTemplate);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Updated Delivery Method :" + bt);
        }
        return bt;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#getAssociatedRequestorTypeCount(long)
     */
    public long getAssociatedRequestorTypeCount(long billingTemplateId) {

        final String logSM = "getAssociatedRequestorTypeCount(billingTemplateId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + billingTemplateId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<?> ids = getHibernateTemplate().findByNamedQuery("getAssociatedRequestorTypeCount",
                                                                  new Long(billingTemplateId));
        long count = toPlong((Long)ids.get(0));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + count);
        }
        return count;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO
     * #getBillingTemplateByName(java.lang.String)
     */
    public BillingTemplate getBillingTemplateByName(String billingTemplateName) {

        final String logSM = "getBillingTemplateByName(billingTemplateName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTemplateName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<?> billingTemplates = getHibernateTemplate().
                              findByNamedQuery("getBillingTemplateName", billingTemplateName);

        BillingTemplate billingTemplate = null;
        if (billingTemplates.size() > 0) {
            billingTemplate = (BillingTemplate)billingTemplates.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + billingTemplates.size());
        }
        return billingTemplate;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO#retrieveAllBillingTemplateName()
     */
    public BillingTemplatesList retrieveAllBillingTemplateName() {

        final String logSM = "retrieveAllBillingTemplateName()";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        ScrollableResults rs = (ScrollableResults) getHibernateTemplate().execute(
            new HibernateCallback() {

            public Object doInHibernate(Session s) {
                return s.getNamedQuery("retrieveAllBillingTemplateName")
                .scroll();
           }
        });

        ArrayList<BillingTemplate> billingTemplates = new ArrayList<BillingTemplate>();
        while (rs.next()) {

          BillingTemplate bt = new BillingTemplate();
          bt.setId(toPlong(rs.getLong(0)));
          bt.setName(rs.getString(1));
          billingTemplates.add(bt);
       }
       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End: No. of BillingTemplates = " + billingTemplates.size());
       }
       return new BillingTemplatesList(billingTemplates);
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTemplateDAO
     * #retrieveAllBillingTemplatesByRequestorType(long)
     */
    public BillingTemplatesList retrieveAllBillingTemplatesByRequestorType(long requestorTypeId) {

        final String logSM = "retrieveAllBillingTemplatesByRequestorType(requestorTypeId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorTypeId:" + requestorTypeId);
        }

//        Session session = getSession();
        Session session = currentSession();
        String queryString = session.getNamedQuery("retrieveBillingTemplateByRequestorType")
                                    .getQueryString();
        NativeQuery sqlQuery = session.createSQLQuery(queryString);

        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("active", BooleanType.INSTANCE);
        sqlQuery.addScalar("createdBy", LongType.INSTANCE);
        sqlQuery.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP );
        sqlQuery.addScalar("modifiedBy", LongType.INSTANCE);
        sqlQuery.addScalar("recordVersion", IntegerType.INSTANCE);
        sqlQuery.addScalar("orgId", LongType.INSTANCE);

        sqlQuery.setParameter("requestorTypeId", requestorTypeId);

        sqlQuery.setResultTransformer(Transformers.aliasToBean(BillingTemplate.class));

        @SuppressWarnings("unchecked")
        List<BillingTemplate> billingTemplates = sqlQuery.list();

       if (DO_DEBUG) {
           LOG.debug(logSM + "<<End: No. of BillingTemplates = " + billingTemplates.size());
       }
       return new BillingTemplatesList(billingTemplates);
    }
}
