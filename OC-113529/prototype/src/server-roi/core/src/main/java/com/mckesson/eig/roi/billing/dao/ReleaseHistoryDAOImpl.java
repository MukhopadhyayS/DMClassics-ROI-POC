package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryItem;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryPatient;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

public class ReleaseHistoryDAOImpl
extends ROIDAOImpl
implements ReleaseHistoryDAO {

    /** The Constant LOG. */
    private static final Log LOG = LogFactory.getLogger(ReleaseHistoryDAOImpl.class);
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

        Session session = getSessionFactory().getCurrentSession();


        String stringQuery = session.getNamedQuery(
                "retrieveReleasePatientsByRequestId").getQueryString();

        SQLQuery sqlQuery = session.createSQLQuery(stringQuery);

        sqlQuery.setLong(REQUEST_ID, requestId);
        sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
        sqlQuery.addScalar("name", StandardBasicTypes.STRING);
        sqlQuery.addScalar("gender", StandardBasicTypes.STRING);
        sqlQuery.addScalar("dob", StandardBasicTypes.DATE);
        sqlQuery.addScalar("ssn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
        sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("epn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("patientLocked", StandardBasicTypes.BOOLEAN);
        sqlQuery.addScalar("encounterLocked", StandardBasicTypes.BOOLEAN);
        sqlQuery.addScalar("vip", StandardBasicTypes.STRING);


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

        Session session = getSessionFactory().getCurrentSession();

        String stringQuery = session.getNamedQuery(
                "retrieveReleaseHistoryByRequestId").getQueryString();

        SQLQuery sqlQuery = session.createSQLQuery(stringQuery);
        sqlQuery.setLong(REQUEST_ID, requestId);
        sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
        sqlQuery.addScalar("patientName", StandardBasicTypes.STRING);
        sqlQuery.addScalar("trackingNumber", StandardBasicTypes.STRING);
        sqlQuery.addScalar("datetime", StandardBasicTypes.TIMESTAMP);
        sqlQuery.addScalar("enctr", StandardBasicTypes.STRING);
        sqlQuery.addScalar("selfPay", StandardBasicTypes.BOOLEAN);
        sqlQuery.addScalar("documentVersionSubtitle", StandardBasicTypes.STRING);
        sqlQuery.addScalar("pages", StandardBasicTypes.INTEGER);
        sqlQuery.addScalar("requestPassword", StandardBasicTypes.STRING);
        sqlQuery.addScalar("queuePassword", StandardBasicTypes.STRING);
        sqlQuery.addScalar("shippingMethod", StandardBasicTypes.STRING);
        sqlQuery.addScalar("outputMethod", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address1", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address2", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address3", StandardBasicTypes.STRING);
        sqlQuery.addScalar("city", StandardBasicTypes.STRING);
        sqlQuery.addScalar("state", StandardBasicTypes.STRING);
        sqlQuery.addScalar("zipcode", StandardBasicTypes.STRING);
        sqlQuery.addScalar("userName", StandardBasicTypes.STRING);
        sqlQuery.addScalar("userId", StandardBasicTypes.LONG);


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
