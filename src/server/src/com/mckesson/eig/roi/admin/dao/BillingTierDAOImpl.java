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
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.admin.model.BillingTier;
import com.mckesson.eig.roi.admin.model.BillingTiersList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author OFS
 * @date   Mar 25, 2009
 * @since  HPF 13.1 [ROI]; Apr 15, 2008
 */
public class BillingTierDAOImpl
extends ROIDAOImpl
implements BillingTierDAO {

    private static final OCLogger LOG = new OCLogger(BillingTierDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int BILLING_TIER_ID = 0;
    private static final int BILLING_TIER_NAME = 1;
    private static final int SALESTAX_ENABLE = 2;
    private static final int MEDIATYPE_NAME = 3;
    private static final int DESCRIPTION = 4;

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO
     * #createBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
     */
    public BillingTier createBillingTier(BillingTier billingTier) {

        final String logSM = "createBillingTier(billingTier)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTier);
        }

        long id = toPlong((Long) create(billingTier));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: ID == " + id);
        }
        return retrieveBillingTier(id);
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO#retrieveBillingTier(long)
     */
    public BillingTier retrieveBillingTier(long billingTierId) {

        final String logSM = "retrieveBillingTier(billingTierId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTierId);
        }

        BillingTier bt = (BillingTier) get(BillingTier.class, billingTierId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + bt.getName());
        }
        return bt;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO#retrieveAllBillingTiers()
     */
    public BillingTiersList retrieveAllBillingTiers() {

        final String logSM = "retreiveAllBillingTiers()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> billingTiers = (List<Object[]>)
                getHibernateTemplate().execute(new HibernateCallback() {

             public Object doInHibernate(Session s) {

                 return s.createCriteria(BillingTier.class)
                         .createAlias("mediaType", "mediaType")
                         .setProjection(Projections.projectionList()
                                        .add(Projections.property("id"))
                                        .add(Projections.property("name"))
                                        .add(Projections.property("salesTax"))
                                        .add(Projections.property("mediaType.name"))
                                        .add(Projections.property("description")))
                         .addOrder(Order.asc("name"))
                         .list();

             }
         });


        List<BillingTier> tiers = new ArrayList<BillingTier>();
        for (Object[] values : billingTiers) {

            BillingTier tier = new BillingTier();
            tier.setId(toPlong(((Long) values[BILLING_TIER_ID]).longValue()));
            tier.setName((String) values[BILLING_TIER_NAME]);
            tier.setSalesTax((Character) values[SALESTAX_ENABLE]);
            tier.setDescription((String) values[DESCRIPTION]);
            tier.setMediaTypeName((String) values[MEDIATYPE_NAME]);
            tiers.add(tier);
        }

        BillingTiersList tierList = new BillingTiersList(tiers);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of Records == " + tiers.size());
        }
        return tierList;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO
     * #updateBillingTier(com.mckesson.eig.roi.admin.model.BillingTier)
     */
    public BillingTier updateBillingTier(BillingTier billingTier) {

        final String logSM = "updateBillingTier(billingTier)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTier);
        }

        BillingTier latest = (BillingTier) merge(billingTier);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Updated Billing Tier Details " + latest);
        }
        return retrieveBillingTier(latest.getId());
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO#deleteBillingTier(long)
     */
    public BillingTier deleteBillingTier(long id) {

        final String logSM = "deleteBillingTier(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        BillingTier billingTier = retrieveBillingTier(id);
        delete(billingTier);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return billingTier;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO
     * #retrieveBillingTierByName(java.lang.String)
     */
    public BillingTier retrieveBillingTierByName(String name) {

        final String logSM = "retrieveBillingTierByName(name)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<BillingTier> billingTiers = (List<BillingTier>) getHibernateTemplate().
                                         findByNamedQuery("retrieveBillingTierByName",
                                                          name);
        BillingTier billingTier = null;
        if (billingTiers.size() > 0) {
            billingTier = billingTiers.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + billingTier);
        }
       return billingTier;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO#getAssociatedRequestorTypeCount(long)
     */
    public long getAssociatedRequestorTypeCount(long billingTierId) {

        final String logSM = "getAssociatedRequestorTypeCount(billingTierId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + billingTierId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Long> ids = (List<Long>) getHibernateTemplate()
                         .findByNamedQuery("getAssociatedRequestorTypeCountForBillingTier",
                                           new Long(billingTierId));

        long count = toPlong(ids.get(0));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + count);
        }
        return count;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.BillingTierDAO
     * #retrieveBillingTiersByMediaTypeName(java.lang.String)
     */
    public BillingTiersList retrieveBillingTiersByMediaTypeName(String mediaTypeName) {

        final String logSM = "retrieveElectronicBillingTier()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + mediaTypeName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        BillingTiersList btList = new BillingTiersList((List<BillingTier>) getHibernateTemplate().
                                  findByNamedQuery("retrieveBillingTierByMediaTypeName",
                                  new String(mediaTypeName)));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records==" + btList.getBillingTiers().size());
        }
        return btList;
    }

    public BillingTiersList retrieveBillingTierAndMediaTypeName() {

        final String logSM = "retrieveBillingTierAndMediaTypeName()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        ScrollableResults rs = (ScrollableResults) getHibernateTemplate().execute(
        new HibernateCallback() {

            public Object doInHibernate(Session s) {
                return s.getNamedQuery("retrieveBillingTierAndMediaTypeName")
                .scroll();
            }

        });
        ArrayList<BillingTier> billingTiers = new ArrayList<BillingTier>();
        while (rs.next()) {

            BillingTier bt = new BillingTier();
            bt.setId(toPlong(rs.getLong(BILLING_TIER_ID)));
            bt.setName(rs.getString(BILLING_TIER_NAME));
            bt.setSalesTax(rs.getCharacter(SALESTAX_ENABLE));
            bt.setMediaTypeName(rs.getString(MEDIATYPE_NAME));

            billingTiers.add(bt);
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No. of Billing Tiers =" + billingTiers.size());
        }
        return new BillingTiersList(billingTiers);
    }
    
    /**
     * This method is used to retrieve All Standard BillingTiers By Names
     *
     * @return BillingTier
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BillingTier> retrieveAllStandardBillingTiers() {

        final String logSM = "retrieveAllStandardBillingTiers()";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("retrieveAllStandardBillingTiers"); 
            List<BillingTier> billingTiersList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + ">>End:");
            }
            return billingTiersList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

    }
}
