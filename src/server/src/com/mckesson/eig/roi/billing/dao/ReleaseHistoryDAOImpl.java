package com.mckesson.eig.roi.billing.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.transaction.annotation.Transactional;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryItem;
import com.mckesson.eig.roi.billing.model.ReleaseHistoryPatient;

@Transactional
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

        NativeQuery sqlQuery = session.createSQLQuery(stringQuery);

        sqlQuery.setParameter(REQUEST_ID, requestId, LongType.INSTANCE);
        sqlQuery.addScalar("patientSeq", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("gender", StringType.INSTANCE);
        sqlQuery.addScalar("dob", DateType.INSTANCE);
        sqlQuery.addScalar("ssn", StringType.INSTANCE);
        sqlQuery.addScalar("facility", StringType.INSTANCE);
        sqlQuery.addScalar("mrn", StringType.INSTANCE);
        sqlQuery.addScalar("epn", StringType.INSTANCE);
        sqlQuery.addScalar("patientLocked", BooleanType.INSTANCE);
        sqlQuery.addScalar("encounterLocked", BooleanType.INSTANCE);
        sqlQuery.addScalar("vip", StringType.INSTANCE);


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

        NativeQuery sqlQuery = session.createSQLQuery(stringQuery);
        sqlQuery.setParameter(REQUEST_ID, requestId, LongType.INSTANCE);
        sqlQuery.addScalar("patientSeq", LongType.INSTANCE);
        sqlQuery.addScalar("patientName", StringType.INSTANCE);
        sqlQuery.addScalar("trackingNumber", StringType.INSTANCE);
        sqlQuery.addScalar("datetime", StandardBasicTypes.TIMESTAMP);
        sqlQuery.addScalar("enctr", StringType.INSTANCE);
        sqlQuery.addScalar("selfPay", BooleanType.INSTANCE);
        sqlQuery.addScalar("documentVersionSubtitle", StringType.INSTANCE);
        sqlQuery.addScalar("pages", IntegerType.INSTANCE);
        sqlQuery.addScalar("requestPassword", StringType.INSTANCE);
        sqlQuery.addScalar("queuePassword", StringType.INSTANCE);
        sqlQuery.addScalar("shippingMethod", StringType.INSTANCE);
        sqlQuery.addScalar("outputMethod", StringType.INSTANCE);
        sqlQuery.addScalar("address1", StringType.INSTANCE);
        sqlQuery.addScalar("address2", StringType.INSTANCE);
        sqlQuery.addScalar("address3", StringType.INSTANCE);
        sqlQuery.addScalar("city", StringType.INSTANCE);
        sqlQuery.addScalar("state", StringType.INSTANCE);
        sqlQuery.addScalar("zipcode", StringType.INSTANCE);
        sqlQuery.addScalar("userName", StringType.INSTANCE);
        sqlQuery.addScalar("userId", LongType.INSTANCE);


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
