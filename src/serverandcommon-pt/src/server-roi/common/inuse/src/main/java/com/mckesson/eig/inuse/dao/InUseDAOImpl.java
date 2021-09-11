/*
 * Copyright 2008 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Information Solutions and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Information Solutions.
 */

package com.mckesson.eig.inuse.dao;


import java.util.Calendar;
import java.util.List;

import com.mckesson.dm.core.common.logging.OCLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.mckesson.eig.inuse.base.dao.BaseInUseDAOImpl;
import com.mckesson.eig.inuse.model.InUseRecord;
//import com.mckesson.eig.utility.log.Log;
//import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   Nov 11, 2008
 * @since  ROI HPF 13.1
 */
public class InUseDAOImpl
extends BaseInUseDAOImpl
implements InUseDAO {

    private static final OCLogger LOG = new OCLogger(InUseDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * @see InUseDAO#createRecord(InUseRecord)
     */
    public InUseRecord createRecord(InUseRecord inUseRecord) {

        final String logSM = "createRecord(inUseRecord)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: inUseRecord:" + inUseRecord);
        }

        long sequence = (Long) create(inUseRecord);

        inUseRecord = retrieveBySequence(sequence);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: inUseRecord:" + inUseRecord);
        }

        return inUseRecord;
    }

    /**
     * @see InUseDAO#updateRecord(InUseRecord)
     */
    public InUseRecord updateRecord(InUseRecord inUseRecord) {

        final String logSM = "updateRecord(inUseRecord)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: inUseRecord:" + inUseRecord);
        }

        InUseRecord mergedRecord = (InUseRecord) merge(inUseRecord);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: inUseRecord: " + mergedRecord);
        }
        return mergedRecord;
    }

    /**
     * @see InUseDAO#retrieveExpiredRecords()
     */
    public List<InUseRecord> retrieveExpiredRecords(int gracePeriodMinutes) {

        final String logSM = "retrieveExpiredRecords()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: ");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <InUseRecord> records = (List <InUseRecord> ) getHibernateTemplate().findByNamedQuery(
                "retrieveExpiredRecords",
                new Object[] { gracePeriodMinutes, Calendar.getInstance().getTime() });

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Records: " + records.size());
        }

        return records;
    }

    /**
     * @see InUseDAO#deleteRecord(InUseRecord)
     */
    public void deleteRecord(InUseRecord inUseRecord) {

        final String logSM = "deleteRecord(inUseRecord)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: inUseRecord:" + inUseRecord);
        }

        delete(inUseRecord);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: ");
        }
    }

    /**
     * @see InUseDAO#retrieveAll()
     */
    public List<InUseRecord> retrieveAll() {

        final String logSM = "retrieveAllForType()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: ");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <InUseRecord> records = (List <InUseRecord> )getHibernateTemplate().findByNamedQuery("retrieveAll");

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: count: " + records.size());
        }

        return records;
    }

    /**
     * @see InUseDAO#retrieveAllByType(String)
     */
    public List<InUseRecord> retrieveAllByType(String objectType) {

        final String logSM = "retrieveAllForType(objectType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: objectType: " + objectType);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <InUseRecord> records = (List <InUseRecord> )getHibernateTemplate().findByNamedQuery(
                "retrieveAllForType",
                new Object[] { objectType });

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: count: " + records.size());
        }

        return records;
    }

    /**
     * @see InUseDAO#retrieveByObjectTypeAndID(String, String)
     */
    public InUseRecord retrieveByObjectTypeAndID(String objectType, String objectID) {

        final String logSM = "retrieveByObjectTypeAndID(objectType, objectID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: objectType: " + objectType + " objectID: " + objectID);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <InUseRecord> records = (List <InUseRecord> )getHibernateTemplate().findByNamedQuery(
                "retrieveByObjectTypeAndID",
                new Object[] { objectType, objectID });

        InUseRecord found = (records.size() == 1) ? records.get(0) : null;

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: InUseRecord: " + found);
        }

        return found;
    }


    /**
     * @see InUseDAO#deleteRecords(List)
     */
    public void deleteRecords(List<InUseRecord> inUseRecords) {

        final String logSM = "deleteRecords(inUseRecords)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: records: " + inUseRecords.size());
        }

        getHibernateTemplate().deleteAll(inUseRecords);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: ");
        }
    }

    /**
     * @see InUseDAO
     * #retrieveInUseRecordsByIds(java.lang.String,
     *                            java.lang.String, java.lang.String, java.lang.String)
     */
    public List<InUseRecord> retrieveInUseRecordsByIds(final String objType,
                                          final String objIds,
                                          final String appId,
                                          final String userId) {

       final String logSM = "retrieveObjectIds(objType, objIds, appId, userId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + objIds);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<InUseRecord> records =
            (List<InUseRecord>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session s) {

                Query query = s.getNamedQuery("retrieveInUseRecordsByIds");
                query.setParameter("ObjType", objType);
                query.setParameterList("IDS", objIds.split("\\,"));
                query.setParameter("appId", appId);
                query.setParameter("userId", userId);

                return query.list();
            }
        });

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + records.size());
        }
        return records;
    }

    /**
     * @see InUseDAO
     * #retrieveRecordByAppIdAndUserID(java.lang.String, java.lang.String)
     */
    public InUseRecord retrieveRecordByAppIdAndUserID(String appID, String userID) {

        final String logSM = "retrieveRecordByAppIdAndUserID(appID, userID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <InUseRecord> records = (List <InUseRecord> )getHibernateTemplate().findByNamedQuery(
                                    "retrieveByAppIDAndUserID",
                                    new Object[] { appID, userID});

        InUseRecord found = (records.size() == 1) ? records.get(0) : null;

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + found);
        }

        return found;
    }

    private InUseRecord retrieveBySequence(long sequence) {

        return (InUseRecord) getHibernateTemplate().get("com.mckesson.eig.inuse.model.InUseRecord",
                                                         sequence);
    }
}
