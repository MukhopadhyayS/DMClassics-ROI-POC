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

package com.mckesson.eig.roi.admin.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.mckesson.eig.roi.admin.model.RelatedBillingTier;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.admin.model.RequestorTypesList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   Mar 30, 2009
 * @since  HPF 13.1 [ROI]; Apr 22, 2008
 */
public class RequestorTypeDAOImpl
extends ROIDAOImpl
implements RequestorTypeDAO {

    private static final Log LOG = LogFactory.getLogger(RequestorTypeDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int REQUESTOR_TYPE_ID = 0;
    private static final int REQUESTOR_TYPE_NAME = 1;
    private static final int RECORD_VIEW = 2;
    private static final int BILLINGTIER_ID = 3;
    private static final int IS_HPF_POSITION = 4;
    private static final int IS_HECM_POSITION = 5;
    private static final int IS_CEVA_POSITION = 6;
    private static final int IS_SUPPLEMENTAL_POSITION = 7;
    private static final int RELATED_BILLINGTIER_ID_POSITION = 8;
    private static final int BTIER_RECORDVERSION_POSITION = 9;
    private static final int SALES_TAX = 10;

    private static final int REQUESTOR_TYPE_ID_POSITION = 0;
    private static final int BILLINGTEMPLATE_ID_POSITION = 1;

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO
     * #createRequestorType(com.mckesson.eig.roi.admin.model.RequestorType)
     */
    public RequestorType createRequestorType(RequestorType requestorType) {

        final String logSM = "createRequestorType(requestorType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorType);
        }

        long id = toPlong((Long) create(requestorType));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }
        return retrieveRequestorType(id);
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO#retrieveRequestorType(long)
     */
    public RequestorType retrieveRequestorType(long id) {

        final String logSM = "retrieveRequestorType(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        RequestorType rt = (RequestorType) get(RequestorType.class, id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + rt);
        }
       return rt;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO#retrieveAllRequestorTypes()
     */
    public RequestorTypesList retrieveAllRequestorTypes() {

        final String logSM = "retrieveAllRequestorTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> requestorTypesBillingTier = (List<Object[]>)
            getHibernateTemplate().execute(new HibernateCallback() {

             public Object doInHibernate(Session s) {

                 return s.createCriteria(RequestorType.class)
                         .createAlias("relatedBillingTier", "relBillTier", Criteria.LEFT_JOIN)
                         .setProjection(Projections.projectionList()
                                        .add(Projections.property("id"))
                                        .add(Projections.property("name"))
                                        .add(Projections.property("rv"))
                                        .add(Projections.property("relBillTier.billingTierId"))
                                        .add(Projections.property("relBillTier.isHPF"))
                                        .add(Projections.property("relBillTier.isHECM"))
                                        .add(Projections.property("relBillTier.isCEVA"))
                                        .add(Projections.property("relBillTier.isSupplemental"))
                                        .add(Projections.property("relBillTier.id"))
                                        .add(Projections.property("relBillTier.recordVersion"))
                                        .add(Projections.property("salesTax")))
                         .addOrder(Order.asc("name"))
                         .list();

             }
         });

        Map<Long, RequestorType> requestorTypeMap = new LinkedHashMap<Long, RequestorType>();

        for (Object[] values : requestorTypesBillingTier) {

            Long rtId = (Long) values[REQUESTOR_TYPE_ID];
            RequestorType rt = new RequestorType();
            if (requestorTypeMap.containsKey(rtId)) {
                rt = requestorTypeMap.get(rtId);
            }
            rt.setId(toPlong(rtId));
            rt.setName((String) values[REQUESTOR_TYPE_NAME]);
            rt.setRv((String) values[RECORD_VIEW]);
            rt.setSalesTax(Character.toUpperCase(((Character) values[SALES_TAX]).charValue()));

            RelatedBillingTier rbt = new RelatedBillingTier();
            rbt.setBillingTierId(toPlong((Long) values[BILLINGTIER_ID]));
            rbt.setIsHPF(toPboolean((Boolean) values[IS_HPF_POSITION]));
            rbt.setIsHECM(toPboolean((Boolean) values[IS_HECM_POSITION]));
            rbt.setIsCEVA(toPboolean((Boolean) values[IS_CEVA_POSITION]));
            rbt.setIsSupplemental(toPboolean((Boolean) values[IS_SUPPLEMENTAL_POSITION]));
            rbt.setId(toPlong((Long) values[RELATED_BILLINGTIER_ID_POSITION]));
            rbt.setRecordVersion(toPint((Integer) values[BTIER_RECORDVERSION_POSITION]));

            rt.getRelatedBillingTier().add(rbt);

            if (!requestorTypeMap.containsKey(rtId)) {
                requestorTypeMap.put(rtId, rt);
            }
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> requestorTypesBillingTemplate = (List<Object[]>)
            getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session s) {

                return s.createCriteria(RequestorType.class)
                        .createAlias("relatedBillingTemplate", "rbt", Criteria.LEFT_JOIN)
                        .setProjection(Projections.projectionList()
                                      .add(Projections.property("id"))
                                      .add(Projections.property("rbt.billingTemplateId")))
                                      .list();

            }
        });

        for (Object[] values : requestorTypesBillingTemplate) {

            Long reqTypeId = toPlong((Long) values[REQUESTOR_TYPE_ID_POSITION]);
            RequestorType rtype = requestorTypeMap.get(reqTypeId);

            if (((Long) values[BILLINGTEMPLATE_ID_POSITION] != null) && (rtype != null)) {
                rtype.getBillingTemplateIds().add((Long) values[BILLINGTEMPLATE_ID_POSITION]);
            }

        }

        RequestorTypesList rtList =
            new RequestorTypesList(new ArrayList<RequestorType>(requestorTypeMap.values()));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records " + rtList.getRequestorTypes().size());
        }

        return rtList;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO
     * #updateRequestorType(com.mckesson.eig.roi.admin.model.RequestorType)
     */
    public RequestorType updateRequestorType(RequestorType requestorType) {

        final String logSM = "updateRequestorType(requestorType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorType);
        }

        RequestorType updated = (RequestorType) merge(requestorType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + updated);
        }
        return updated;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO#deleteRequestorType(long)
     */
    public RequestorType deleteRequestorType(long id) {

        final String logSM = "deleteRequestorType(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        RequestorType requestorType = retrieveRequestorType(id);
        delete(requestorType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + requestorType);
        }
        return requestorType;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO
     * #getRequestorTypeByName(java.lang.String)
     */
    public RequestorType getRequestorTypeByName(String requestorTypeName) {

        final String logSM = "getRequestorTypeByName(requestorTypeName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<RequestorType> requestorTypes =
        		(List<RequestorType>) getHibernateTemplate().findByNamedQuery("getRequestorTypeByName", requestorTypeName);

        RequestorType requestorType = (requestorTypes.size() == 0) ? null : requestorTypes.get(0);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + requestorType);
        }
        return  requestorType;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO#retrieveAllRequestorTypeNames()
     */
    public RequestorTypesList retrieveAllRequestorTypeNames() {

        final String logSM = "retrieveAllRequestorTypeNames()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        ScrollableResults rs = (ScrollableResults) getHibernateTemplate().execute(
            new HibernateCallback() {

            public Object doInHibernate(Session s) {
                return s.getNamedQuery("retrieveRequestorTypesNames")
                .scroll();
            }
            });

        ArrayList<RequestorType> requestorTypes = new ArrayList<RequestorType>();
        while (rs.next()) {

          RequestorType rt = new RequestorType();
          rt.setId(toPlong(rs.getLong(0)));
          rt.setName(rs.getString(1));
          rt.setSalesTax(rs.getCharacter(2));
          requestorTypes.add(rt);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No. of Records = " + requestorTypes.size());
        }
        return new RequestorTypesList(requestorTypes);
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.RequestorTypeDAO#getAssociatedRequestorCount(long)
     */
    public long getAssociatedRequestorCount(long requestorTypeId) {

        final String logSM = " getAssociatedRequestorCount(requestorTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorTypeId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Long> ids = (List<Long>) getHibernateTemplate().findByNamedQuery("getAssociatedRequestorCount",
                                                                 new Long(requestorTypeId));

        long count = toPlong(ids.get(0));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + count);
        }
        return count;
    }
}
