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

package com.mckesson.eig.roi.admin.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.CharacterType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

import com.mckesson.eig.roi.admin.model.FeeType;
import com.mckesson.eig.roi.admin.model.FeeTypesList;
import com.mckesson.eig.roi.admin.model.RelatedFeeType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
*@author OFS
* @date   Sep 15, 2008
* @since  HPF 13.1 [ROI]; Apr 4, 2008
*/
public class FeeTypeDAOImpl
extends ROIDAOImpl
implements FeeTypeDAO {

    private static final OCLogger LOG = new OCLogger(FeeTypeDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int FEETYPEID        = 0;
    private static final int FEETYPENAME      = 1;
    private static final int CHARGEAMOUNT     = 2;
    private static final int DESCRIPTION      = 3;
    private static final int SALESTAX         = 4;

    /**
     * @see com.mckesson.eig.roi.admin.dao.FeeTypeDAO
     * #createFeeType(com.mckesson.eig.roi.admin.model.FeeType)
     */
    @Override
    public long createFeeType(FeeType feeType) {

        final String logSM = "createFeeType(feeType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeType.getName());
        }

        long id = toPlong((Long) create(feeType));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: ID:" + id);
        }
        return id;
    }

    /**
     * This method fetches the FeeType details
     *
     * @param feeTypeId Unique id of the FeeType
     * @return FeeType details
     */
    @Override
    public FeeType retrieveFeeType(long feeTypeId) {

        final String logSM = "retrieveFeeType(long feeTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + feeTypeId);
        }

        FeeType feeType = (FeeType) get(FeeType.class, feeTypeId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End : FeeType name " + feeType.getName());
        }
        return feeType;
    }

    /**
     * This method fetches all the FeeTypes
     *
     * @return list of all FeeTypes
     */
    @Override
    public FeeTypesList retrieveAllFeeTypes() {

        final String logSM = "retrieveAllFeeTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> feeTypes = (List<Object[]>) getHibernateTemplate().findByNamedQuery("retrieveAllFeeTypes");

        List<FeeType> feesList = new ArrayList<FeeType>();
        for (Object[] values : feeTypes) {

            FeeType feeType = new FeeType();
            feeType.setId(toPlong(((Long) values[FEETYPEID]).longValue()));
            feeType.setName((String) values[FEETYPENAME]);
            feeType.setChargeAmount(toPdouble((Double) values[CHARGEAMOUNT]));
            feeType.setDescription((String) values[DESCRIPTION]);
            feeType.setSalesTax(Character.toUpperCase(((Character) values[SALESTAX]).charValue()));
            feesList.add(feeType);
        }

        FeeTypesList feetypeslist = new FeeTypesList(feesList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + "No of Records " + feeTypes.size());
        }
        return feetypeslist;
    }

    /**
     * This method fetches name and ids of all fee types
     *
     * @return list of all FeeTypes
     */
    @Override
    public FeeTypesList retrieveAllFeeTypeNames() {

        final String logSM = "retrieveAllFeeTypes()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> feeTypes = (List<Object[]>) getHibernateTemplate().findByNamedQuery("retrieveAllFeeTypeName");

        List<FeeType> feesList = new ArrayList<FeeType>();
        for (Object[] values : feeTypes) {

            FeeType feeType = new FeeType();
            feeType.setId(toPlong(((Long) values[FEETYPEID]).longValue()));
            feeType.setName((String) values[FEETYPENAME]);
            feesList.add(feeType);
        }

        FeeTypesList feetypeslist = new FeeTypesList(feesList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + "No of Records " + feeTypes.size());
        }
        return feetypeslist;
    }

    /**
     * This method updates the FeeType Details
     *
     * @param feeType the details of FeeType to be updated
     * @param selectedFeeType details of the old FeeType
     * @return updated FeeType
     */
    @Override
    public FeeType updateFeeType(FeeType feeType, FeeType selectedFeeType) {

        final String logSM = "updateFeeType(feeType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeType.getName());
        }

        feeType.copyFrom(selectedFeeType);
        FeeType updatedFeeType = (FeeType) merge(feeType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: FeeType updated with Name = " + feeType.getName());
        }
        return updatedFeeType;
    }

    /**
     * This method deletes selected FeeType
     *
     * @param feeTypeId Id of the FeeType to be deleted
     * @return Name of the deleted Fee Type
     */
    @Override
    public String deleteFeeType(long feeTypeId) {

        final String logSM = "deleteFeeType(feeTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeTypeId);
        }

        FeeType feeType = retrieveFeeType(feeTypeId);
        delete(feeType);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return feeType.getName();
    }

    /**
     * This method fetches the FeeType by feeTypeName
     *
     * @param feeTypeName the details of FeeType
     * @return FeeType for the given feeTypeName
     */
    @Override
    public FeeType getFeeTypeByName(String feeTypeName) {

        final String logSM = "getFeeTypeByName(feeTypeName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + feeTypeName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<FeeType> feeTypes = (List<FeeType>) getHibernateTemplate().findByNamedQuery("getFeeTypeByName",
                                                                          feeTypeName);

        FeeType feeType = null;
        if (feeTypes.size() > 0) {
            feeType = feeTypes.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + feeTypes.size());
        }
        return feeType;
    }

    /**
     * This method fetches BillingTempateIds which is associated with given feeTypeId
     *
     * @param feeTypeId to be checked for association
     * @return Number of ids associated with BillingTemplate
     */
    @Override
    public long getAssociatedBillingTemplateCount(long feeTypeId) {

        final String logSM = "getAssociatedBillingTemplateCount(feeTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start" + feeTypeId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Long> ids = (List<Long>) getHibernateTemplate().
                         findByNamedQuery("getAssociatedBillingTemplateCount", new Long(feeTypeId));

        long count = toPlong(ids.get(0));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + count);
        }
        return count;
    }

    /**
     * This method fetches feetypes for the given requestorType
     *
     * @param RequestorTypeId
     * @return list of all feetypes for the given requestorTypeId
     */
    @Override
    public FeeTypesList retrieveAllFeeTypesByRequestorId(long requestorTypeId) {

        final String logSM = "retrieveAllFeeTypesByRequestorId(requestorTypeId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorTypeId:" + requestorTypeId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("getFeeTypesByRequestorId").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);

            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("name", StringType.INSTANCE);
            query.addScalar("chargeAmount", DoubleType.INSTANCE);
            query.addScalar("description", StringType.INSTANCE);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            query.addScalar("orgId", LongType.INSTANCE);
            query.addScalar("salesTax", CharacterType.INSTANCE);

            query.setParameter("requestorTypeId", requestorTypeId);

            query.setResultTransformer(Transformers.aliasToBean(FeeType.class));
            @SuppressWarnings("unchecked")
            List<FeeType> feeTypes = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:" + feeTypes.size());
            }

            return new FeeTypesList(feeTypes);
        } catch (Exception e) {
            throw new ROIException(e,
                                   ROIClientErrorCodes.RETRIEVE_FEE_TYPE_BY_REQUESTOR_TYPE_FAILED);
        }
    }

    @Override
    public List<RelatedFeeType> retrieveAllRelatedFeeByRequestorType(long requestorTypeId) {

        final String logSM = "retrieveAllRelatedFeeByRequestorType(requestorTypeId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorTypeId:" + requestorTypeId);
        }

        Session session = getSession();
        String queryString = session.getNamedQuery("retrieveAllRelatedFeeTypeByRequestorType")
                .getQueryString();
        NativeQuery query = session.createSQLQuery(queryString);

        query.addScalar("id", LongType.INSTANCE);
        query.addScalar("billingTemplateId", LongType.INSTANCE);
        query.addScalar("feeTypeId", LongType.INSTANCE);
        query.addScalar("createdBy", LongType.INSTANCE);
        query.addScalar("modifiedDate", StandardBasicTypes.TIMESTAMP);
        query.addScalar("modifiedBy", LongType.INSTANCE);
        query.addScalar("recordVersion", IntegerType.INSTANCE);

        query.setParameter("requestorTypeId", requestorTypeId);

        query.setResultTransformer(Transformers.aliasToBean(RelatedFeeType.class));

        @SuppressWarnings("unchecked")
        List<RelatedFeeType> relatedFeeTypes = query.list();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No. of RelatedFeeType = " + relatedFeeTypes.size());
        }

        return relatedFeeTypes;
    }
}
