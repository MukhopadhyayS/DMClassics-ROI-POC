package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryItem;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryPatient;
import com.mckesson.dm.core.common.logging.OCLogger;

public class ReleaseHistoryDAOImpl
extends ROIDAOImpl
implements ReleaseHistoryDAO {

    /** The Constant LOG. */
    private static final OCLogger LOG = new OCLogger(ReleaseHistoryDAOImpl.class);
    /** The Constant DO_DEBUG. */
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    @SuppressWarnings("unchecked")
    @Override
    public List<ReleaseHistoryPatient> retrievePatients(long requestId) {

        final String logSM = "retrievePatients(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        List<ReleaseHistoryPatient> realesePatients = new ArrayList<ReleaseHistoryPatient>();

        Session session = getSession();


        String stringQuery = session.getNamedQuery(
                "retrieveReleasePatientsByRequestId").getQueryString();

        SQLQuery sqlQuery = session.createSQLQuery(stringQuery);

        sqlQuery.setLong(REQUEST_ID, requestId);
        sqlQuery.addScalar("patientSeq", Hibernate.LONG);
        sqlQuery.addScalar("name", Hibernate.STRING);
        sqlQuery.addScalar("gender", Hibernate.STRING);
        sqlQuery.addScalar("dob", Hibernate.DATE);
        sqlQuery.addScalar("ssn", Hibernate.STRING);
        sqlQuery.addScalar("facility", Hibernate.STRING);
        sqlQuery.addScalar("mrn", Hibernate.STRING);
        sqlQuery.addScalar("epn", Hibernate.STRING);
        sqlQuery.addScalar("patientLocked", Hibernate.BOOLEAN);
        sqlQuery.addScalar("encounterLocked", Hibernate.BOOLEAN);
        sqlQuery.addScalar("vip", Hibernate.STRING);


        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(ReleaseHistoryPatient.class));

        realesePatients = sqlQuery.list();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: release patient size : "
                    + realesePatients.size());
        }

        return realesePatients;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ReleaseHistoryItem> retrieveReleaseHistory(long requestId) {

        final String logSM = "retrieveReleaseHistory(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        List<ReleaseHistoryItem> releaseHistory = new ArrayList<ReleaseHistoryItem>();

        Session session = getSession();

        String stringQuery = session.getNamedQuery(
                "retrieveReleaseHistoryByRequestId").getQueryString();

        SQLQuery sqlQuery = session.createSQLQuery(stringQuery);
        sqlQuery.setLong(REQUEST_ID, requestId);
        sqlQuery.addScalar("patientSeq", Hibernate.LONG);
        sqlQuery.addScalar("patientName", Hibernate.STRING);
        sqlQuery.addScalar("trackingNumber", Hibernate.STRING);
        sqlQuery.addScalar("datetime", Hibernate.TIMESTAMP);
        sqlQuery.addScalar("enctr", Hibernate.STRING);
        sqlQuery.addScalar("selfPay", Hibernate.BOOLEAN);
        sqlQuery.addScalar("documentVersionSubtitle", Hibernate.STRING);
        sqlQuery.addScalar("pages", Hibernate.INTEGER);
        sqlQuery.addScalar("requestPassword", Hibernate.STRING);
        sqlQuery.addScalar("queuePassword", Hibernate.STRING);
        sqlQuery.addScalar("shippingMethod", Hibernate.STRING);
        sqlQuery.addScalar("outputMethod", Hibernate.STRING);
        sqlQuery.addScalar("address1", Hibernate.STRING);
        sqlQuery.addScalar("address2", Hibernate.STRING);
        sqlQuery.addScalar("address3", Hibernate.STRING);
        sqlQuery.addScalar("city", Hibernate.STRING);
        sqlQuery.addScalar("state", Hibernate.STRING);
        sqlQuery.addScalar("zipcode", Hibernate.STRING);
        sqlQuery.addScalar("userName", Hibernate.STRING);
        sqlQuery.addScalar("userId", Hibernate.LONG);


        sqlQuery.setResultTransformer(Transformers
                .aliasToBean(ReleaseHistoryItem.class));

        releaseHistory = sqlQuery.list();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: release history size : "
                    + releaseHistory.size());
        }

        return releaseHistory;
    }

}
