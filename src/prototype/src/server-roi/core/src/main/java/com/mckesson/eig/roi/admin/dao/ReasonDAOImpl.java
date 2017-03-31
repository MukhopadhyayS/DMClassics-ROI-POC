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

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.mckesson.eig.roi.admin.model.Reason;
import com.mckesson.eig.roi.admin.model.ReasonsList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   June 08, 2009
 * @since  HPF 13.1 [ROI]; Apr 28, 2008
 */
public class ReasonDAOImpl
extends ROIDAOImpl
implements ReasonDAO {

    private static final Log LOG = LogFactory.getLogger(ReasonDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final int ID = 0;
    private static final int NAME = 1;
    private static final int DISPLAY_TEXT = 2;
    private static final int TYPE = 3;
    private static final int STATUS = 4;
    private static final int TPO = 5;
    private static final int NON_TPO = 6;

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #createReason(com.mckesson.eig.roi.admin.model.Reason)
     */
    public long createReason(Reason reason) {

        final String logSM = "createReason(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reason.getName());
        }

        long id = toPlong((Long) create(reason));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + reason);
        }
        return id;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO#retrieveAllReasonsByType(java.lang.String)
     */
    public ReasonsList retrieveAllReasonsByType(final String reasonType) {

        final String logSM = "retrieveAllReasonsByType(reasonType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Reason Type" + reasonType);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> rs
        =  (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session s) {
                return s.createCriteria(Reason.class)
                        .setProjection(Projections.projectionList()
                               .add(Projections.property("id"))
                               .add(Projections.property("name"))
                               .add(Projections.property("displayText"))
                               .add(Projections.property("type"))
                               .add(Projections.property("status"))
                               .add(Projections.property("tpo"))
                               .add(Projections.property("nonTpo")))

                         .add((Restrictions.eq("type", reasonType)))
                         .addOrder(Order.asc("name"))
               .list();
            }
            }
        );

        List<Reason> reasons = new ArrayList <Reason>();
        for (Object[] values : rs) {

            Reason reason = new Reason();

            reason.setId(toPlong((Long) values[ID]));
            reason.setName((String) values[NAME]);
            reason.setDisplayText((String) values[DISPLAY_TEXT]);
            reason.setType((String) values[TYPE]);
            reason.setStatus(toPint((Integer) values[STATUS]));
            reason.setTpo(toPboolean((Boolean) values[TPO]));
            reason.setNonTpo(toPboolean((Boolean) values[NON_TPO]));

            reasons.add(reason);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No.of Reasons : " + reasons.size());
        }
        return new ReasonsList(reasons);
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #getReasonByName(java.lang.String, java.lang.String)
     */
    public Reason getReasonByName(String reasonName, String reasonType) {

        final String logSM = "getReasonByName(reasonName, reasonType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reasonName + reasonType);
        }

        Object[] values = {reasonName, reasonType};

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Reason> reasons = (List<Reason>)getHibernateTemplate().
                               findByNamedQuery("getReasonByName", values);

        Reason reason = null;
        if (reasons.size() > 0) {
            reason = reasons.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + reason);
        }
        return reason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #getReasonByDispText(java.lang.String, java.lang.String)
     */
    public Reason getReasonByDispText(String reasonDisplayText, String reasonType) {

        final String logSM = "getReasonByDispText(reasonDisplayText, reasonType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reasonDisplayText + reasonType);
        }

        Object[] values = {reasonDisplayText, reasonType};

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Reason> reasons = (List<Reason>) getHibernateTemplate().
                               findByNamedQuery("getReasonByDispText", values);

        Reason reason = null;
        if (reasons.size() > 0) {
            reason = reasons.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + reason);
        }
        return reason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #getStatusReasonByName(com.mckesson.eig.roi.admin.model.Reason)
     */
    public Reason getStatusReasonByName(Reason reason) {

        final String logSM = "getStatusReasonByName(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reason);
        }

        Object[] values = {reason.getType(), reason.getName(), reason.getStatus()};

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Reason> reasons = (List<Reason>) getHibernateTemplate().
                               findByNamedQuery("getStatusReasonByName", values);

        Reason statusReason = null;
        if (reasons.size() > 0) {
            statusReason = reasons.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + statusReason);
        }
        return statusReason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #getStatusReasonByDispText(com.mckesson.eig.roi.admin.model.Reason)
     */
    public Reason getStatusReasonByDispText(Reason reason) {

        final String logSM = "getStatusReasonByDispText(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reason);
        }

        Object[] values = {reason.getType(), reason.getDisplayText(), reason.getStatus()};

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Reason> reasons = (List<Reason>) getHibernateTemplate().
                               findByNamedQuery("getStatusReasonByDispText", values);

        Reason statusReason = null;
        if (reasons.size() > 0) {
            statusReason = reasons.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + statusReason);
        }
        return statusReason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO#retrieveReason(long)
     */
    public Reason retrieveReason(long reasonId) {

        final String logSM = "retrieveReason(reasonId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Reason ID : " + reasonId);
        }

        Reason reason = (Reason) get(Reason.class, reasonId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Retrieved reason : " + reason);
        }
        return reason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO#deleteReason(long)
     */
    public Reason deleteReason(long reasonId) {

        final String logSM = "deleteReason(reasonId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reasonId);
        }

        Reason reason = retrieveReason(reasonId);
        delete(reason);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return reason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO
     * #updateReason(com.mckesson.eig.roi.admin.model.Reason)
     */
    public Reason updateReason(Reason reason) {

        final String logSM = "updateReason(reason)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + reason);
        }

        Reason updatedReason = (Reason) merge(reason);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + updatedReason);
        }
        return updatedReason;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.ReasonDAO#getReasonsByStatus(int)
     */
    public List<String> getReasonsByStatus(int statusId) {

        final String logSM = "getReasonsByStatus(statusId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:StatusId : " + statusId);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<String> statusReasons = (List<String>) getHibernateTemplate().findByNamedQuery("getReasonsByStatus",
                                                                             statusId);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Size Of Reasons:" + statusReasons.size());
        }
        return statusReasons;
    }
}
