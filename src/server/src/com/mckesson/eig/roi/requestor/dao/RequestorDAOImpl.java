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

package com.mckesson.eig.roi.requestor.dao;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.admin.dao.RequestorTypeDAO;
import com.mckesson.eig.roi.admin.model.RequestorType;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.PlainSqlBatchProcessor;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.Address;
import com.mckesson.eig.roi.base.model.BaseModel;
import com.mckesson.eig.roi.base.model.MatchCriteriaList;
import com.mckesson.eig.roi.billing.model.RequestorLetterHistory;
import com.mckesson.eig.roi.hpf.model.User;
import com.mckesson.eig.roi.requestor.model.AdjustmentInfo;
import com.mckesson.eig.roi.requestor.model.Requestor;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustment;
import com.mckesson.eig.roi.requestor.model.RequestorAdjustmentsPayments;
import com.mckesson.eig.roi.requestor.model.RequestorCharges;
import com.mckesson.eig.roi.requestor.model.RequestorHistory;
import com.mckesson.eig.roi.requestor.model.RequestorHistoryList;
import com.mckesson.eig.roi.requestor.model.RequestorInvoice;
import com.mckesson.eig.roi.requestor.model.RequestorInvoicesList;
import com.mckesson.eig.roi.requestor.model.RequestorPayment;
import com.mckesson.eig.roi.requestor.model.RequestorPaymentList;
import com.mckesson.eig.roi.requestor.model.RequestorPrebill;
import com.mckesson.eig.roi.requestor.model.RequestorPrebillsList;
import com.mckesson.eig.roi.requestor.model.RequestorRefund;
import com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetails;
import com.mckesson.eig.roi.requestor.model.RequestorUnappliedAmountDetailsList;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * The Class RequestorDAOImpl.
 *
 * @author OFS
 * @date   Jun 01, 2009
 * @since  HPF 13.1 [ROI]; Jun 17, 2008
 */

public class RequestorDAOImpl
extends ROIDAOImpl
implements RequestorDAO {

    /** The Constant LOG. */
    private static final OCLogger LOG = new OCLogger(RequestorDAOImpl.class);

    /** The Constant DO_DEBUG. */
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /** The Constant REQUEST_SEARCH. */
    private static final StringBuffer REQUEST_SEARCH = new StringBuffer()

    .append("SELECT")
    .append("   req1.ROI_Requestor_Seq as col_0_0_, ")
    .append("   req1.NameFirst as col_1_0_, ")
    .append("   req1.NameLast as col_2_0_, ")
    .append("   req1.Active as col_3_0_, ")
    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'DOB' ")
    .append("       and rr2.value is not null) as col_4_0,")

    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'EPN' ")
    .append("       and rr2.value is not null) as col_5_0_,")

    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'SSN' ")
    .append("       and rr2.value is not null) as col_6_0_,")

    .append("   req1.ROI_RequestorType_Seq as col_7_0_, ")
    .append("   address.AddressLine1 as col_8_0_, ")
    .append("   address.AddressLine2 as col_9_0_, ")
    .append("   address.AddressLine3 as col_10_0_, ")
    .append("   address.City as col_11_0_, ")
    .append("   address.State as col_12_0_, ")
    .append("   address.PostalCode as col_13_0_, ")

    .append("   (select top 1 convert(varchar, emailphone4_.EmailPhone) ")
    .append("       from ROI_RequestorToEmailPhone relatedema3_ ")
    .append("       join ROI_EmailPhone emailphone4_ ")
    .append("       on relatedema3_.ROI_EmailPhone_Seq=emailphone4_.ROI_EmailPhone_Seq")
    .append("       and relatedema3_.roi_emailphonetype_seq = (select roi_emailphonetype_seq ")
    .append("       from roi_emailphonetype where name='Home') ")
    .append("       and emailphone4_.EmailPhone is not null ")
    .append("       where req1.ROI_Requestor_Seq=relatedema3_.ROI_Requestor_Seq) as home,")

    .append("   (select top 1 convert(varchar,emailphone4_.EmailPhone) ")
    .append("       from ROI_RequestorToEmailPhone relatedema3_ ")
    .append("       join ROI_EmailPhone emailphone4_ ")
    .append("       on relatedema3_.ROI_EmailPhone_Seq=emailphone4_.ROI_EmailPhone_Seq ")
    .append("       and relatedema3_.roi_emailphonetype_seq = (select roi_emailphonetype_seq ")
    .append("       from roi_emailphonetype where name='Work') ")
    .append("       and emailphone4_.EmailPhone is not null ")
    .append("       where req1.ROI_Requestor_Seq=relatedema3_.ROI_Requestor_Seq) as work,")

    .append("   (select top 1 convert(varchar, emailphone4_.EmailPhone) ")
    .append("       from ROI_RequestorToEmailPhone relatedema3_ ")
    .append("       join ROI_EmailPhone emailphone4_ ")
    .append("       on relatedema3_.ROI_EmailPhone_Seq=emailphone4_.ROI_EmailPhone_Seq ")
    .append("       and relatedema3_.roi_emailphonetype_seq = (select roi_emailphonetype_seq ")
    .append("       from roi_emailphonetype where name='MainFax') ")
    .append("       and emailphone4_.EmailPhone is not null ")
    .append("       where req1.ROI_Requestor_Seq=relatedema3_.ROI_Requestor_Seq) as fax,")
    .append("   req1.modified_Dt, ")

    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'MRN' ")
    .append("       and rr2.value is not null) as MRN, ")

    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'FACILITY' ")
    .append("       and rr2.value is not null) as FACILITY, ")

    .append("   (select top 1 rr2.value from ROI_RequestorRestriction rr2 ")
    .append("       where rr2.ROI_Requestor_Seq = req1.ROI_Requestor_Seq ")
    .append("       and rr2.SystemColumn = 'FREEFORM_FACILITY' ")
    .append("       and rr2.value is not null) as FREEFORM_FACILITY, ")

    .append("   (select rcl.Country_Name from ROI_CountryLov rcl ")
    .append("       where rcl.ROI_CountryLovID = address.ROI_CountryLovID) as col_21_0_  ")

    .append("   FROM ROI_Requestor req1 ")
    .append("   left outer join ROI_RequestorRestriction rr")
    .append("       on rr.ROI_Requestor_Seq = req1.ROI_Requestor_Seq")
    .append("   left outer join ROI_RequestorToAddress relatedadd1_")
    .append("       on req1.ROI_Requestor_Seq=relatedadd1_.ROI_Requestor_Seq  ")
    .append("       and relatedadd1_.ROI_AddressType_Seq = (select roi_addresstype_seq ")
    .append("       from roi_addresstype where name='Main') ")
    .append("   left outer join ROI_Address address")
    .append("       on relatedadd1_.ROI_Address_Seq=address.ROI_Address_Seq  ");

    /** The Constant GROUP_BY_CLAUSE. */
    private static final StringBuffer GROUP_BY_CLAUSE = new StringBuffer()
        .append("   GROUP BY req1.modified_dt, req1.ROI_Requestor_Seq, req1.NameFirst, ")
        .append("   req1.NameLast,req1.Active,req1.ROI_RequestorType_Seq, address.AddressLine1,")
        .append("   address.AddressLine2, address.AddressLine3, address.City, ")
        .append("   address.State, address.PostalCode,address.ROI_CountryLovID ");

    /** The Constant RECENT_REQUEST_IDS. */

    private static final StringBuffer RECENT_REQUEST_IDS = new StringBuffer()
        .append(" req1.ROI_Requestor_Seq IN (SELECT distinct ROI_Requestor_Seq " +
                "FROM ROI_RequestCoreRequestor ")
        .append("as r WHERE ")
        .append("r.Created_Dt >= :RequestorDate)");


    /** The Constant REQUESTORID. */
    private static final int REQUESTORID        = 0;

    /** The Constant REQUESTORFIRSTNAME. */
    private static final int REQUESTORFIRSTNAME = 1;

    /** The Constant REQUESTORNAME. */
    private static final int REQUESTORNAME      = 2;

    /** The Constant REQUESTORACTIVE. */
    private static final int REQUESTORACTIVE    = 3;

    /** The Constant REQUESTORDOB. */
    private static final int REQUESTORDOB       = 4;

    /** The Constant REQUESTOREPN. */
    private static final int REQUESTOREPN       = 5;

    /** The Constant REQUESTORSSN. */
    private static final int REQUESTORSSN       = 6;

    /** The Constant REQUESTORTYPE. */
    private static final int REQUESTORTYPE      = 7;

    /** The Constant ADDRESS1. */
    private static final int ADDRESS1           = 8;

    /** The Constant ADDRESS2. */
    private static final int ADDRESS2           = 9;

    /** The Constant ADDRESS3. */
    private static final int ADDRESS3           = 10;

    /** The Constant CITY. */
    private static final int CITY               = 11;

    /** The Constant STATE. */
    private static final int STATE              = 12;

    /** The Constant PINCODE. */
    private static final int PINCODE            = 13;

    /** The Constant FAX. */
    private static final int FAX                = 16;

    /** The Constant HOME. */
    private static final int HOME               = 14;

    /** The Constant WORK. */
    private static final int WORK               = 15;

    /** The Constant REQUESTORMRN. */
    private static final int REQUESTORMRN       = 18;

    /** The Constant REQUESTORFACILITY. */
    private static final int REQUESTORFACILITY  = 19;

    /** The Constant IS_FREEFORM_FAC. */
    private static final int IS_FREEFORM_FAC    = 20;

    /** The Constant COUNTRY_NAME. */
    private static final int COUNTRY_NAME    = 21;

    private final HashMap<String, String> parameters =  new HashMap<String, String>(); 
    /**
     * Find requestor.
     *
     * @param crit the crit
     * @return the list
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
     * #findRequestor(com.mckesson.eig.roi.requestor.model.RequestorSearchCriteria)
     */
    @Override
    public List<Requestor> findRequestor(final RequestorSearchCriteria crit) {

        final String logSM = "findRequestor(searchCriteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + crit);
        }

        final StringBuffer sql = new StringBuffer(REQUEST_SEARCH);
        StringBuffer whereClause = new StringBuffer();

        parameters.clear();
        
        // add search condition
        addCritiera(whereClause, crit);
       
        addRequestorRestrictions(crit, whereClause);

        if (!crit.isAllRequestors()) {

            if (whereClause.toString().length() > 0) {
                whereClause.append(" AND ");
            }
            whereClause.append(RECENT_REQUEST_IDS);
        }

        if (whereClause.toString().length() > 0) {
            sql.append(" WHERE ").append(whereClause);
        }
        sql.append(GROUP_BY_CLAUSE.toString());
        sql.append(" ORDER BY req1.modified_Dt desc, req1.NameFirst, req1.NameLast ");

        if (DO_DEBUG) {
            LOG.debug(logSM + "Requestor Search SQL:" + sql.toString());
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> rs
        =  (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session s) {

                Query query = s.createSQLQuery(sql.toString());
                if (!crit.isAllRequestors()) {
                    query.setParameter("RequestorDate", crit.getRecentRequestorDate());

                }
                Set<String> keys = parameters.keySet();
                for(String key: keys){
                    String value = parameters.get(key);
                    query.setParameter(key, value);
                }

                return query.setFirstResult(0).setMaxResults(crit.getMaxCount() + 1).list();
            }
        });

        Map<Long, Requestor> reqMap = new LinkedHashMap<Long, Requestor>();
        List<Requestor> requestors = new ArrayList <Requestor>();

        for (Object[] values : rs) {

            Requestor requestor = new Requestor();
            requestor.setId(toPint((Integer) values[REQUESTORID]));
            requestor.setFirstName((String) values[REQUESTORFIRSTNAME]);
            requestor.setLastName((String) values[REQUESTORNAME]);
            requestor.setActive(toPboolean((Boolean) values[REQUESTORACTIVE]));
            requestor.setDateOfBirth((String) values[REQUESTORDOB]);
            requestor.setDob(requestor.getDateOfBirth(), ROIConstants.ROI_DATE_FORMAT);
            requestor.setEpn((String) values[REQUESTOREPN]);
            requestor.setSsn((String) values[REQUESTORSSN]);
            requestor.setMrn((String) values[REQUESTORMRN]);
            requestor.setFacility((String) values[REQUESTORFACILITY]);

            requestor.setFreeFormFacility(toPboolean(Boolean.getBoolean(
                                                    (String) values [IS_FREEFORM_FAC])));
            requestor.setType(toPint((Integer) values[REQUESTORTYPE]));

            Address address = new Address();
            address.setAddress1((String) values[ADDRESS1]);
            address.setAddress2((String) values[ADDRESS2]);
            address.setAddress3((String) values[ADDRESS3]);
            address.setCity((String) values[CITY]);
            address.setState((String) values[STATE]);
            address.setPostalCode((String) values[PINCODE]);
            address.setCountryName((String) values[COUNTRY_NAME]);
            requestor.setHomePhone((String) values[HOME]);
            requestor.setWorkPhone((String) values[WORK]);
            requestor.setFax((String) values[FAX]);
            requestor.setMainAddress(address);
            requestors.add(requestor);
        }
        if (requestors.size() == 0) { //return if no matching requestors found
            return new ArrayList <Requestor>();
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No. of Requestors :" + reqMap.size());
        }
         return requestors;
    }

    /**
     * This method will add the input search criteria to build search query.
     *
     * @param whereClause Where clause
     * @param crit Input Search Criteria
     */
    private void addCritiera(StringBuffer whereClause,
                             RequestorSearchCriteria crit) {

        boolean hasCriteria = false;

        if (!StringUtilities.isEmpty(crit.getLastName())) {

            String key = putParameters(getSpecialCharSearchStr(crit.getLastName())+ "%", "LastName");
            whereClause.append(" req1.NameLast LIKE :").append(key).append(" ESCAPE('+')");
            //whereClause.append(" req1.NameLast LIKE ('").append(getSpecialCharSearchStr(crit.getLastName())).append("%') ESCAPE('+')");
            hasCriteria = true;
        }
        if (!StringUtilities.isEmpty(crit.getFirstName())) {

            if (hasCriteria) {
                whereClause.append(" AND ");
            }
            
            String key = putParameters(getSpecialCharSearchStr(crit.getFirstName())+ "%", "firstName");
            whereClause.append(" req1.NameFirst LIKE :").append(key).append(" ESCAPE('+')");
            hasCriteria = true;
        }
        if (crit.getType() != 0) {

            if (hasCriteria) {
                whereClause.append(" AND ");
            }
            
            String key = putParameters(String.valueOf(crit.getType()), "Type");
            whereClause.append(" req1.ROI_RequestorType_seq = ").append(":").append(key);
            hasCriteria = true;
        } /*else {
            
            if (hasCriteria) {
                whereClause.append(" AND ");
            }
            
            // if the selected requestor is patient requestor type, then we have to shown only the patient requestor.
            whereClause.append(" req1.ROI_RequestorType_seq ");
            if (!crit.isPatientRequestor()) {
                whereClause.append(" !");
            }
            
            String key = putParameters(String.valueOf(crit.getPatientRequestorTypeId()), "PatientRequestorTypeId");
            whereClause.append("= ").append(":").append(key);
            hasCriteria = true;
        }*/
        
        if (!crit.isAllStatus()) {

            if (hasCriteria) {
                whereClause.append(" AND ");
            }
            whereClause.append(" req1.active = ").append(crit.isActiveRequestors() ? 1 : 0);
        }
    }

    /**
     * Adds the requestor restrictions.
     *
     * @param crit the crit
     * @param whereClause the where clause
     */
    private void addRequestorRestrictions(RequestorSearchCriteria crit,
                                          StringBuffer whereClause) {

        boolean hasRestriction = false;
        StringBuffer addRestriction = new StringBuffer();

        if (!StringUtilities.isEmpty(crit.getEpn())) {

            String key = putParameters(crit.getEpn()+"%", "EPN");
            
            addRestriction.append(" req1.roi_requestor_seq IN (SELECT roi_requestor_seq")
                          .append(" FROM roi_requestorrestriction rr")
                          .append(" WHERE rr.systemColumn = 'EPN'")
                          .append(" and rr.Value like ")
                          .append(":")
                          .append(key)
                          .append(")");

            hasRestriction = true;
        }
        if (!StringUtilities.isEmpty(crit.getSsn())) {

            if (hasRestriction) {
                addRestriction.append(" AND ");
            }
            
            String key = putParameters(crit.getSsn()+"%", "SSN");
            
            addRestriction.append(" req1.roi_requestor_seq IN (SELECT roi_requestor_seq")
                          .append(" FROM roi_requestorrestriction rr")
                          .append(" WHERE rr.systemColumn = 'SSN'")
                          .append(" and rr.Value like ")
                          .append(":")
                          .append(key)
                          .append(")");

            hasRestriction = true;
        }
        if (!StringUtilities.isEmpty(crit.getMrn())) {

            if (hasRestriction) {
                addRestriction.append(" AND ");
            }
            
            String key = putParameters(crit.getMrn()+"%", "MRN");
            addRestriction.append(" req1.roi_requestor_seq IN (SELECT roi_requestor_seq")
                          .append(" FROM roi_requestorrestriction rr")
                          .append(" WHERE rr.systemColumn = 'MRN'")
                          .append(" and rr.Value like ")
                          .append(":")
                          .append(key)
                          .append(")");

            hasRestriction = true;
        }
        if (!StringUtilities.isEmpty(crit.getDateOfBirth())) {

            if (hasRestriction) {
                addRestriction.append(" AND ");
            }
            
            String key = putParameters(crit.getDateOfBirth()+"%", "DateOfBirth");
            addRestriction.append(" req1.roi_requestor_seq IN (SELECT roi_requestor_seq")
                          .append(" FROM roi_requestorrestriction rr")
                          .append(" WHERE rr.systemColumn = 'DOB'")
                          .append(" and rr.Value like ")
                          .append(":")
                          .append(key)
                          .append(")");

            hasRestriction = true;
        }

        // add method to resolve checkstyle error
        addFacilityRestriction(hasRestriction, addRestriction, crit);

        if (addRestriction.toString().length() > 0) {

            if (whereClause.toString().length() > 0) {
                whereClause.append(" AND  ");
            }
            whereClause.append("( ").append(addRestriction).append(" )");
        }
    }

    /**
     * Adds the facility restriction.
     *
     * @param hasRestriction the has restriction
     * @param addRestriction the add restriction
     * @param crit the crit
     */
    private void addFacilityRestriction(boolean hasRestriction,
                                        StringBuffer addRestriction,
                                        RequestorSearchCriteria crit) {

        if (!StringUtilities.isEmpty(crit.getFacility())) {

            if (hasRestriction) {
                addRestriction.append(" AND ");
            }
            String key = putParameters(String.valueOf(crit.isFreeFormFacility()), "FreeFremFacility");
            String keyFacility = putParameters(crit.getFacility(), "Facility");
            addRestriction.append(" req1.roi_requestor_seq IN (")
                          .append("  SELECT roi_requestor_seq")
                          .append("  FROM roi_requestorrestriction rr")
                          .append("  WHERE rr.systemColumn = 'FACILITY'")
                          .append("  AND rr.Value = ")
                          .append(":")
                          .append(keyFacility)
                          .append(" )")
                          .append(" AND")
                          .append(" req1.roi_requestor_seq IN (")
                          .append("  SELECT roi_requestor_seq")
                          .append("  FROM roi_requestorrestriction rr")
                          .append("  WHERE rr.systemColumn = 'FREEFORM_FACILITY'")
                          .append("  AND rr.Value = :")
                          .append(key)
                          .append(")");
        }
    }

    /**
     * This method is to retrieve list of all recent requestor ids.
     *
     * @param recentRequestorDate the recent requestor date
     * @return list of recent requestor's ids
     */
    //TODO : remove seams not used anywhere
//    private List<Long> getAllRecentRequestors(Date recentRequestorDate) {
//
//        final String logSM = "getAllRecentRequestors()";
//        if (DO_DEBUG) {
//            LOG.debug(logSM + ">>Start:");
//        }
//
//        @SuppressWarnings("unchecked") // not supported by 3rdParty API
//
//        List<String> ids = getHibernateTemplate()
//                                .findByNamedQuery("getAllRecentRequestors", recentRequestorDate);
//
//        ArrayList<Long> reqids = new ArrayList<Long>();
//        for (String id : ids) {
//            reqids.add(toPlong(Long.parseLong(id)));
//        }
//
//        if (DO_DEBUG) {
//            LOG.debug(logSM + "<<End: No. of Recent Requestors :" + ids.size());
//        }
//        return reqids;
//    }

    /**
     * Creates the requestor.
     *
     * @param requestor the requestor
     * @return the long
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
     * #createRequestor(com.mckesson.eig.roi.requestor.model.Requestor)
     */
    @Override
    public long createRequestor(Requestor requestor) {

        final String logSM = "createRequestor(requestor)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestor);
        }

        long id = toPlong((Long) create(requestor));

        LOG.debug(logSM + "<<End: Created RequestorId : " + id);
        return id;
    }

    /**
     * Retrieve requestor.
     *
     * @param requestorId the requestor id
     * @return the requestor
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO#retrieveRequestor(long)
     */
    @Override
    public Requestor retrieveRequestor(long requestorId) {

        final String logSM = "requestorId(requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: RequestorID : " + requestorId);
        }

        Requestor req = (Requestor) get(Requestor.class, requestorId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + req);
        }
        return req;
    }

    /**
     * Gets the associated request count.
     *
     * @param id the id
     * @return the associated request count
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO#getAssociatedRequestCount(long)
     */
    @Override
    public long getAssociatedRequestCount(long id) {

        final String logSM = "getAssociatedRequestCount(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId : " + id);
        }

        Session session = getSession();
        String stringQuery = session.getNamedQuery(
                "getAssociatedRequestCount").getQueryString();
        SQLQuery sqlQuery = session.createSQLQuery(stringQuery);
        sqlQuery.setLong("id", id);
        sqlQuery.addScalar("count", Hibernate.LONG);
        long count = (Long) sqlQuery.uniqueResult();

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Count :" + count);
        }
        return count;
    }

    /**
     * Delete requestor.
     *
     * @param requestorId the requestor id
     * @return the requestor
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO#deleteRequestor(long)
     */
    @Override
    public Requestor deleteRequestor(long requestorId) {

        final String logSM = "deleteRequestor()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:RequestorId: " + requestorId);
        }

        Requestor req = retrieveRequestor(requestorId);
        delete(req);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:Deleted RequestorId :" + requestorId);
        }
        return req;
    }

    /**
     * Update requestor.
     *
     * @param requestor the requestor
     * @return the requestor
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
     * #updateRequestor(com.mckesson.eig.roi.requestor.model.Requestor)
     */
    @Override
    public Requestor updateRequestor(Requestor requestor) {

        final String logSM = "updateRequestor(requestor)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestor);
        }
        try {

            Requestor updatedRequestor = (Requestor) merge(requestor);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:Updated Requestor : " + updatedRequestor);
            }
            return updatedRequestor;
        } catch (ROIException e) {

            if (ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION.toString()
            .equals(e.getErrorCode())) {

                throw new ROIException(ROIClientErrorCodes.REQUESTOR_INUSE);
            }
            throw e;
        }
    }

    /**
     * Retrieve requestor ids by name.
     *
     * @param name the name
     * @return the list
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
     * #retrieveRequestorIdsByName(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")  // not supported by 3rdParty API
    public List<Long> retrieveRequestorIdsByName(String name) {

        final String logSM = "getRequestorByName(name)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Requestor Name: " + name);
        }

        String[] names = StringUtilities.split(name, ",");
        List<Long> requestorIds = new ArrayList <Long>();

        if (names.length < 2) {
            requestorIds = getHibernateTemplate()
                               .findByNamedQuery("retrieveRequestorByLastName", name);
        } else {

            requestorIds = getHibernateTemplate()
                               .findByNamedQueryAndNamedParam("retrieveRequestorByName",
                                                                  new String[] {"lName", "fName"},
                                                                  new String[] {names[0].trim(),
                                                                                names[1].trim()
                                                                  });

        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No.of Requestors :" + requestorIds.size());
        }
        return requestorIds;
    }

    /**
     * Gets the matching requestors.
     *
     * @param list the list
     * @param rtDAO the rt dao
     * @return the matching requestors
     * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
     * #getMatchingRequestors(com.mckesson.eig.roi.base.model.MatchCriteriaList,
     * com.mckesson.eig.roi.admin.dao.RequestorTypeDAO)
     */
    @Override
    public List<Requestor> getMatchingRequestors(MatchCriteriaList list,
                                                 RequestorTypeDAO rtDAO) {

        final String logSM = "getMatchingRequestors(list, rtDAO)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + list.getMatchCriteria().size());
        }

        DetachedCriteria c = DetachedCriteria.forClass(Requestor.class);
        RequestorType rt =  rtDAO.getRequestorTypeByName(ROIConstants.REQUESTOR_TYPE_PATIENT);
        Map<String, String[]> map = list.getMatchCriteriaValues();
        c.add(Restrictions.eq("type", rt.getId()));
        Conjunction j =  Restrictions.conjunction();

        if (map.get(MatchCriteriaList.NAME_LIST) != null) {
            j.add(Restrictions.in("lastName", map.get(MatchCriteriaList.NAME_LIST)));
        }
        if (map.get(MatchCriteriaList.FIRST_NAME_LIST) != null) {
            j.add(Restrictions.in("firstName", map.get(MatchCriteriaList.FIRST_NAME_LIST)));
        }
        if (map.get(MatchCriteriaList.SSN_LIST) != null) {
            j.add(Restrictions.in("ssn", map.get(MatchCriteriaList.SSN_LIST)));
        }
        if (map.get(MatchCriteriaList.EPN_LIST) != null) {
            j.add(Restrictions.in("epn", map.get(MatchCriteriaList.EPN_LIST)));
        }
        if (map.get(MatchCriteriaList.DOB_LIST) != null) {
            j.add(Restrictions.in("dateOfBirth", map.get(MatchCriteriaList.DOB_LIST)));
        }
        if (map.get(MatchCriteriaList.MRN_LIST) != null) {
            j.add(Restrictions.in("mrn", map.get(MatchCriteriaList.MRN_LIST)));
        }
        if (map.get(MatchCriteriaList.FACILITY_LIST) != null) {
            j.add(Restrictions.in("facility", map.get(MatchCriteriaList.FACILITY_LIST)));
        }
        c.add(j);

        @SuppressWarnings("unchecked") // not supported by third party API
        List<Requestor> requestors = getHibernateTemplate().findByCriteria(c);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:No.of Requestors Matching:" + requestors.size());
        }
        return requestors;
    }

    /**
     * This method retrieves the requestor letter history from the database.
     *
     * @param requestorId the requestor id
     * @return List
     */
    @Override
    public List<RequestorLetterHistory> retrieveRequestorLetterHistory(long requestorId) {

        final String logSM = "retrieveRequestorLetterHistory(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: requestor Id : " + requestorId);
        }

        try {
            Session session = getSession();
            String queryString = session.
                                 getNamedQuery("retrieveRequestorLetterHistory").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, Hibernate.LONG);

            query.addScalar("requestorLetterId", Hibernate.LONG);
            query.addScalar("createdDate", Hibernate.TIMESTAMP);
            query.addScalar("resendDate", Hibernate.STRING);
            query.addScalar("outputMethod", Hibernate.STRING);
            query.addScalar("createdBy", Hibernate.STRING);
            query.addScalar("requestTemplateId", Hibernate.LONG);
            query.addScalar("templateUsed", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);

            query.setResultTransformer(Transformers.aliasToBean(RequestorLetterHistory.class));

            @SuppressWarnings("unchecked")
            List<RequestorLetterHistory> requestorLetterList =
                                                        query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End" + requestorLetterList);
            }
            return requestorLetterList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                                                                    e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(),
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }

    @Override
    public RequestorInvoicesList retrieveRequestorInvoices(long requestorId) {
        final String logSM = "retrieveRequestorInvoices(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }

        List<RequestorInvoice> requestInvoices = retrieveRequestorInvoiceDetails(requestorId);
        if (CollectionUtilities.isEmpty(requestInvoices)) {
            return null;
        }

        List<Long> invoiceIds = new ArrayList<Long>();
        for (RequestorInvoice reqInvoice : requestInvoices) {
             if ("Invoice".equalsIgnoreCase(reqInvoice.getInvoiceType()) || "Prebill".equalsIgnoreCase(reqInvoice.getInvoiceType())) {
                 invoiceIds.add(reqInvoice.getId());
             }
        }
        List<RequestorAdjustmentsPayments> reqAdjPay = null;

        if (!invoiceIds.isEmpty()) {
            reqAdjPay = retrieveAdjustmentsPayments(invoiceIds);
        }

        for (RequestorInvoice reqInvoice : requestInvoices) {

            if (reqInvoice.getBalance() == 0.0
                    && "Invoice".equalsIgnoreCase(reqInvoice.getInvoiceType())) {

                reqInvoice.setInvoiceStatus("Closed");
                reqInvoice.setInvoiceType("Closed Invoice");
            } else if (reqInvoice.getBalance() != 0.0
                    && "Invoice".equalsIgnoreCase(reqInvoice.getInvoiceType())) {

                reqInvoice.setInvoiceStatus("Open");
                reqInvoice.setInvoiceType("Open Invoice");
            } 

            double amount = 0.00;
            List<RequestorAdjustmentsPayments> adjPays =
                                        new ArrayList<RequestorAdjustmentsPayments>();
            if (reqAdjPay == null) {
                continue;
            }

            for (RequestorAdjustmentsPayments adjPay : reqAdjPay) {

                if (reqInvoice.getId() == adjPay.getInvoiceId()) {

                    adjPays.add(adjPay);
                    if ("Payment".equalsIgnoreCase(adjPay.getTxnType())
                            && adjPay.getAppliedAmount() != null) {

                        amount = amount + adjPay.getAppliedAmount();
                        reqInvoice.setPaymentAmount(amount);
                    }
                }
            }

            reqInvoice.setRequestorAdjPay(adjPays);
        }

        return new RequestorInvoicesList(requestInvoices);
    }
    
    @Override
    public RequestorPrebillsList retrieveRequestorPrebills(long requestorId) {
        final String logSM = "retrieveRequestorPrebills(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }

        List<RequestorPrebill> requestPrebills = retrieveRequestorPrebillDetails(requestorId);
        if (CollectionUtilities.isEmpty(requestPrebills)) {
            return null;
        }

        List<Long> prebillIds = new ArrayList<Long>();
        for (RequestorPrebill reqPrebill : requestPrebills) {
            prebillIds.add(reqPrebill.getId());
        }
        List<RequestorAdjustmentsPayments> reqAdjPay = null;

        if (!prebillIds.isEmpty()) {
            reqAdjPay = retrieveAdjustmentsPayments(prebillIds);
        }

        for (RequestorPrebill reqPrebill : requestPrebills) {

            double amount = 0.00;
            List<RequestorAdjustmentsPayments> adjPays =
                                        new ArrayList<RequestorAdjustmentsPayments>();
            if (reqAdjPay == null) {
                continue;
            }

            for (RequestorAdjustmentsPayments adjPay : reqAdjPay) {

                if (reqPrebill.getId() == adjPay.getInvoiceId()) {

                    adjPays.add(adjPay);
                    if ("Payment".equalsIgnoreCase(adjPay.getTxnType())
                            && adjPay.getAppliedAmount() != null) {

                        amount = amount + adjPay.getAppliedAmount();
                        reqPrebill.setPaymentAmount(amount);
                    }
                }
            }

            reqPrebill.setRequestorAdjPay(adjPays);
        }

        return new RequestorPrebillsList(requestPrebills);
    }

    @SuppressWarnings("unchecked")
    private List<RequestorInvoice> retrieveRequestorInvoiceDetails(long requestorId) {
        final String logSM = "retrieveRequestorInvoiceDetails(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorInvoices")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestorId", requestorId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestId", Hibernate.LONG);
            query.addScalar("invoiceType", Hibernate.STRING);
            query.addScalar("charge", Hibernate.DOUBLE);
            query.addScalar("balance", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("adjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("refundAmount", Hibernate.DOUBLE);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDescription", Hibernate.STRING);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("facilityString", Hibernate.STRING);
            query.addScalar("billingLocation", Hibernate.STRING);
            query.addScalar("unbillable", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("unBillableAmount", Hibernate.DOUBLE);
            query.setResultTransformer(Transformers.aliasToBean(RequestorInvoice.class));
            List<RequestorInvoice> requestInvoiceList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestInvoiceList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<RequestorPrebill> retrieveRequestorPrebillDetails(long requestCoreId) {
        final String logSM = "retrieveRequestorPrebillDetails(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestCoreId);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorPrebills")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestCoreId", requestCoreId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestId", Hibernate.LONG);
            query.addScalar("invoiceType", Hibernate.STRING);
            query.addScalar("charge", Hibernate.DOUBLE);
            query.addScalar("balance", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("adjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("refundAmount", Hibernate.DOUBLE);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDescription", Hibernate.STRING);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("facilityString", Hibernate.STRING);
            query.addScalar("billingLocation", Hibernate.STRING);
            query.addScalar("unbillable", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("unBillableAmount", Hibernate.DOUBLE);
            query.setResultTransformer(Transformers.aliasToBean(RequestorPrebill.class));
            List<RequestorPrebill> requestorPrebillList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestorPrebillList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<RequestorAdjustmentsPayments> retrieveAdjustmentsPayments(List<Long> invoiceIds) {
        final String logSM = "retrieveAdjustmentsPayments(invoiceIds)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + invoiceIds);
        }
        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorAdjustmentsPayments")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameterList("invoiceIds", invoiceIds, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("date", Hibernate.TIMESTAMP);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("appliedAmount", Hibernate.DOUBLE);
            query.addScalar("unAppliedAmt", Hibernate.DOUBLE);
            query.addScalar("refundAmount", Hibernate.DOUBLE);
            query.addScalar("txnType", Hibernate.STRING);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("requestorId", Hibernate.LONG);
            query.addScalar("invoiceId", Hibernate.LONG);
            query.setResultTransformer(Transformers.aliasToBean(
                                                        RequestorAdjustmentsPayments.class));
            List<RequestorAdjustmentsPayments> reqAdjPay = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return reqAdjPay;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }
    /*@Override
    @SuppressWarnings("unchecked")
    public RequestorAdjustmentsFeeList retrieveRequestorAdjustmentFee(long requestorId){
        final String logSM = "retrieveRequestorAdjustmentFee(Long requestorId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }
        try{
            Session session = getSession();
            String queryString = session.getNamedQuery(
                    "retrieveRequestorAdjustmentsFee").getQueryString();;
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestorId", requestorId, Hibernate.LONG);
            query.addScalar("feeName", Hibernate.STRING);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("salestaxAmount", Hibernate.DOUBLE);
            query.addScalar("feeType", Hibernate.STRING);
            query.addScalar("isTaxable", Hibernate.BOOLEAN);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);


            query.setResultTransformer(Transformers.aliasToBean(RequestorAdjustmentsFee.class));
            List<RequestorAdjustmentsFee> adjFee = query.list();
            RequestorAdjustmentsFeeList requestorAdjustmentsFeeList
                                                    = new RequestorAdjustmentsFeeList();
            requestorAdjustmentsFeeList.setRequestorAdjustmentsFee(adjFee);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestorAdjustmentsFeeList;
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
    }*/

    /**
     * This method retrieves only the Invoices associated to the requestor
     * @param requestorId
     * @param adjustmentId
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RequestorInvoice> retrieveOnlyRequestorInvoices(long requestorId,
                                                                long adjustmentId) {

        final String logSM = "retrieveOnlyRequestorInvoices(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveOnlyRequestorInvoices")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestorId", requestorId, Hibernate.LONG);
            query.setParameter("adjustmentId", adjustmentId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("requestId", Hibernate.LONG);
            query.addScalar("invoiceType", Hibernate.STRING);
            query.addScalar("charge", Hibernate.DOUBLE);
            query.addScalar("balance", Hibernate.DOUBLE);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("appliedAmount", Hibernate.DOUBLE);
            query.addScalar("refundAmount", Hibernate.DOUBLE);
            query.addScalar("adjustmentAmount", Hibernate.DOUBLE);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("paymentDescription", Hibernate.STRING);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("facilityString", Hibernate.STRING);
            query.addScalar("createdDt", Hibernate.TIMESTAMP);
            query.addScalar("createdBy", Hibernate.LONG);
            query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
            query.addScalar("modifiedBy", Hibernate.LONG);
            query.addScalar("recordVersion", Hibernate.INTEGER);
            query.addScalar("billingLocation", Hibernate.STRING);
            query.setResultTransformer(Transformers.aliasToBean(RequestorInvoice.class));
            List<RequestorInvoice> requestInvoiceList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return requestInvoiceList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
     }

    /**This method is used to save requestor's adjustment info
     * @param adjustmentInfo
     * @param taxPercentage
     * @param billingLocationFacility
     * @param billingLocationCode
     * @param reason
     * @return
     */
    @Override
    public long saveAdjustmentInfo(RequestorAdjustment requestorAdjustment) {

        final String logSM = "saveAdjustmentInfo";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorAdjustment);
        }
        try {

            Session session = getSession();
            Query query = session.getNamedQuery("saveAdjustmentInfo");
            query.setParameter("requestorSeq", requestorAdjustment.getRequestorSeq(), Hibernate.LONG);
            query.setParameter("createdDate", requestorAdjustment.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestorAdjustment.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", requestorAdjustment.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestorAdjustment.getModifiedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", requestorAdjustment.getRecordVersion(), Hibernate.INTEGER);
            query.setParameter("reason", requestorAdjustment.getReason(), Hibernate.STRING);
            query.setParameter("amount", requestorAdjustment.getAmount(), Hibernate.DOUBLE);
            query.setParameter("unappliedAmount", requestorAdjustment.getUnappliedAmount(), Hibernate.DOUBLE);
            query.setParameter("adjustmentType", requestorAdjustment.getAdjustmentType().toString(), Hibernate.STRING);
            query.setParameter("adjustmentDate", requestorAdjustment.getAdjustmentDate(), Hibernate.TIMESTAMP);
            query.setParameter("note", requestorAdjustment.getNote(), Hibernate.STRING);
            query.setParameter("isDeleted", requestorAdjustment.isDelete(), Hibernate.BOOLEAN);

            BigDecimal adjValue = (BigDecimal) query.uniqueResult();

            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
            return adjValue.longValue();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method is used to save the details into the mapping table
     * @param adjustmentInfo
     * @param amount
     * @param invoiceId
     */
    @Override
    public long saveDeliveryChargesMapping(AdjustmentInfo adjustmentInfo, double amount, long invoiceId, long requestorAdjId) {

        final String logSM = "saveDeliveryChargesMapping(adjustmentInfo)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentInfo);
        }

        try {

            RequestorAdjustment requestorAdjustment = adjustmentInfo.getRequestorAdjustment();

            Session session = getSession();
            Query query = session.getNamedQuery("createRequestCoreDeliveryChargesMapping");

            query.setParameter("reqDelChargesSeq", invoiceId, Hibernate.LONG);
            query.setParameter("requestorAdjSeq", requestorAdjId, Hibernate.LONG);
            query.setParameter("amount", amount, Hibernate.DOUBLE);
            query.setParameter("createdDate", requestorAdjustment.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", requestorAdjustment.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", requestorAdjustment.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", requestorAdjustment.getModifiedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", requestorAdjustment.getRecordVersion(), Hibernate.INTEGER);
            query.setParameter("prebillAdjustment", requestorAdjustment.isPrebillAdjustment(), Hibernate.BOOLEAN);

            BigDecimal adjToInvoiceValue = (BigDecimal) query.uniqueResult();

            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
            return adjToInvoiceValue.longValue();

        }catch (DataIntegrityViolationException e) {
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

    /**
     * This method is used to save requestor's adjustment fee info
     * @param adjustmentInfo
     * @param feeName
     * @param feeType
     * @param amount
     * @param salesTaxAmount
     * @param isTaxable
     */
    /*@Override
    public void createAdjustmentFeeInfo(AdjustmentInfo adjustmentInfo,
            String feeName, String feeType, double amount,
            double salesTaxAmount, boolean isTaxable)
    {
        final String logSM = "createAdjustmentFeeInfo";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentInfo);
        }
        try
        {
            Session session = getSession();
            Query query = session.getNamedQuery("createAdjustmentFeeInfo");
            query.setParameter("requestorSeq", adjustmentInfo
                    .getRequestorAdjustment().getRequestorSeq(), Hibernate.LONG);
            query.setParameter("feeName",feeName, Hibernate.STRING);
            query.setParameter("amount",amount, Hibernate.DOUBLE);
            query.setParameter("salestaxAmount",salesTaxAmount, Hibernate.DOUBLE);
            query.setParameter("feeType",feeType, Hibernate.STRING);
            query.setParameter("isTaxableFlag",isTaxable, Hibernate.BOOLEAN);
            query.setParameter("createdDate", adjustmentInfo
                    .getRequestorAdjustment().getCreatedDt(),
                    Hibernate.TIMESTAMP);
            query.setParameter("createdBy", adjustmentInfo
                    .getRequestorAdjustment().getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", adjustmentInfo
                    .getRequestorAdjustment().getModifiedDt(),
                    Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", adjustmentInfo
                    .getRequestorAdjustment().getModifiedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", adjustmentInfo
                    .getRequestorAdjustment().getRecordVersion(),
                    Hibernate.INTEGER);
            query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
        }catch (DataIntegrityViolationException e) {
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
    }*/

    /**
     * This method will create RequestorPayment.
     * @param paymentInfo
     * @return paymentId
     */
    @SuppressWarnings("unchecked")
    @Override
    public long createRequestorPayment(RequestorPaymentList paymentInfo) {

        final String logSM = "createRequestorPayment(paymentInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentInfo);
        }

        try {
            Session session = getSession();
            Query query = session.getNamedQuery("createRequestorPayment");

            query.setParameter("requestorSeq", paymentInfo.getRequestorId(), Hibernate.LONG);
            query.setParameter("createdDate", paymentInfo.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", paymentInfo.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", paymentInfo.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", paymentInfo.getModifiedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", paymentInfo.getRecordVersion(), Hibernate.INTEGER);
            query.setParameter("amount", paymentInfo.getPaymentAmount(), Hibernate.DOUBLE);
            query.setParameter("paymentMode", paymentInfo.getPaymentMode(), Hibernate.STRING);
            query.setParameter("paymentDate", paymentInfo.getPaymentDate(), Hibernate.TIMESTAMP);
            query.setParameter("description", paymentInfo.getDescription(), Hibernate.STRING);
            query.setParameter("unappliedAmount", paymentInfo.getUnAppliedAmount(),
                                                Hibernate.DOUBLE);

            List<BigDecimal> requestCoreDeliveryChargesPaymentList = query.list();
            long requestorPaymentId = 0;
            if (requestCoreDeliveryChargesPaymentList != null
                    && requestCoreDeliveryChargesPaymentList.size() > 0) {

                requestorPaymentId = requestCoreDeliveryChargesPaymentList.get(0).longValue();
            }

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:RequestorPaymentId "
                                + requestorPaymentId);
            }

            return requestorPaymentId;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

    }

    /**
     * This method will update RequestorPayment.
     * @param paymentInfo
     * @return paymentId
     */
    @Override
    public long updateRequestorPayment(RequestorPaymentList paymentInfo) {

        final String logSM = "updateRequestorPayment(paymentInfo)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentInfo);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestorPayment");

            query.setParameter("modifiedDate", paymentInfo.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", paymentInfo.getModifiedBy(), Hibernate.LONG);
            query.setParameter("unappliedAmount", paymentInfo.getUnAppliedAmount(),
                                Hibernate.DOUBLE);
            query.setParameter("requestorPaymentId", paymentInfo.getPaymentId(), Hibernate.LONG);

            query.executeUpdate();

            return paymentInfo.getPaymentId();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                  e.getMessage());
        }

    }

    /**
     * This method will retrieve RequestCoreDeliveryChargestoRequestorPayment.
     * @param paymentId
     * @return list of RequestorPayment object.
     */
    @Override
    public List<RequestorPayment> retrieveInvoiceToPayment(long paymentId) {

        final String logSM = "retrieveInvoiceToPayment(paymentId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentId);
        }

        try {

            final Session session = getSession();
            String queryString = session
                    .getNamedQuery("retrieveInvoiceToPayment")
                                        .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("paymentId", paymentId, Hibernate.LONG);
            query.addScalar("requestCoreDeliveryChargesId", Hibernate.LONG);
            query.addScalar("paymentId", Hibernate.LONG);
            query.addScalar("totalAppliedAmount", Hibernate.DOUBLE);
            query.addScalar("prebillPayment", Hibernate.BOOLEAN);
            query.setResultTransformer(Transformers.aliasToBean(RequestorPayment.class));
            List<RequestorPayment> invoiceToPaymentList = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return invoiceToPaymentList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
    }


    /**
     * This method will update the RequestCoreDeliveryCharges.
     * @param paymentInfo
     * @throws SQLException
     */
    @Override
    public void updateRequestorInvoice(List<RequestorPayment> paymentList) {

        final String logSM = "updateRequestorInvoice(paymentList)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentList);
        }

        try {

            final Session session = getSession();
            PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

                @Override
                protected Session getSession() {
                    return session;
                }

                @Override
                protected long getBatchSize() {
                    return ROIConstants.SQL_BATCH_SIZE;
                }

                @Override
                protected <T extends BaseModel> void addToBatch(PreparedStatement pStmt,
                        T object) throws SQLException {

                    RequestorPayment paymentInfo = (RequestorPayment) object;

                    int index = 1;
                    pStmt.setLong(index++, paymentInfo.getRequestCoreDeliveryChargesId());
                    pStmt.setDouble(index++,  paymentInfo.getInvoiceBalance());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(paymentInfo.getModifiedDt()));
                    pStmt.setLong(index++, paymentInfo.getModifiedBy());
                    pStmt.setLong(index++, paymentInfo.getRequestCoreDeliveryChargesId());
                    pStmt.setDouble(index++,  paymentInfo.getInvoiceBalance());
                    pStmt.setTimestamp(index++, getSQLTimeStamp(paymentInfo.getModifiedDt()));
                    pStmt.setLong(index++, paymentInfo.getModifiedBy());
                    pStmt.setLong(index++, paymentInfo.getRequestCoreDeliveryChargesId());

                }
            };
            String query = session.getNamedQuery("updateRequestorInvoice").getQueryString();
            processor.execute(paymentList, query);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

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

    @Override
    public void updateRequestorAdjustment(RequestorAdjustment requestorAdjustment) {

         final String logSM = "updateRequestorAdjustment(requestorAdjustment)";

         if (DO_DEBUG) {
             LOG.debug(logSM + ">>Start:" + requestorAdjustment);
         }
         try {

             Session session = getSession();
             Query query = session.getNamedQuery("updateRequestorAdjustment");

             query.setParameter("adjustmentId", requestorAdjustment.getId(), Hibernate.LONG);
             query.setParameter("unappliedAmount", requestorAdjustment.getUnappliedAmount(),
                                                                                  Hibernate.DOUBLE);
             query.setParameter("modifiedDt", requestorAdjustment.getModifiedDt(), Hibernate.TIMESTAMP);
             query.setParameter("modifiedBy", requestorAdjustment.getModifiedBy(), Hibernate.LONG);
             query.executeUpdate();

             if (DO_DEBUG) {
                 LOG.debug(logSM + ">>End:");
             }

         } catch (DataIntegrityViolationException e) {
             throw new ROIException(e,
                                     ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
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

    /**
     * This method will all deletes all the records in
     * RequestCoreDeliveryChargestoRequestorAdjustment table by adjustmentId.
     * @param paymentId
     */
    @Override
    public void deleteMappedInvoicesByAdjustmentAndInvoiceId(long adjustmentId, long invoiceId) {

        final String logSM =
                           "deleteMappedInvoicesByAdjustmentAndInvoiceId(adjustmentId, invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentId + " , " + invoiceId);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("deleteMappedInvoicesByAdjustmentAndInvoiceId");
            query.setParameter("adjustmentId", adjustmentId, Hibernate.LONG);
            query.setParameter("invoiceId", invoiceId, Hibernate.LONG);
            query.executeUpdate();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                                  ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

    }

    @Override
    public List<RequestorInvoice> retrieveInvoiceIdsForAdjustments(long adjustmentId) {
        final String logSM = "retrieveInvoiceIdsForAdjustments(adjustmentId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + adjustmentId);
        }

        try {

            Session session = getSession();
            String queryString = session.
                           getNamedQuery("retrieveMappedInvoicesByAdjustmentId").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("adjustmentId", adjustmentId, Hibernate.LONG);
            query.addScalar("id", Hibernate.LONG);
            query.addScalar("appliedAmount", Hibernate.DOUBLE);
            query.setResultTransformer(Transformers.aliasToBean(RequestorInvoice.class));
            @SuppressWarnings("unchecked")
            List<RequestorInvoice> invoiceList  = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }
            return invoiceList;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }

    }

	/**
     * This method will load the Requestor History
     * @param requestorId
     * @return RequestorHistory
     */
    @Override
    @SuppressWarnings("unchecked")
    public RequestorHistoryList retrieveRequestorSummaries(long requestorId) {
        final String logSM = "retrieveRequestorSummaries(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestorId" + requestorId);
        }
        try{

            Session session = getSession();
            String queryString =
                    session.getNamedQuery("retrieveRequestorSummaries").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, Hibernate.LONG);

            query.addScalar("id", Hibernate.LONG);
            query.addScalar("type", Hibernate.STRING);
            query.addScalar("invoiceDueDate", Hibernate.STRING);
            query.addScalar("queuePassword", Hibernate.STRING);
            query.addScalar("balance", Hibernate.DOUBLE);
            query.addScalar("invoiceBalance", Hibernate.DOUBLE);
            query.addScalar("creatorName", Hibernate.STRING);
            query.addScalar("createdDate", Hibernate.STRING);
            query.addScalar("prebillStatus", Hibernate.STRING);
            query.addScalar("template", Hibernate.STRING);
            query.addScalar("status", Hibernate.STRING);
            query.addScalar("requestPassword", Hibernate.STRING);
            query.addScalar("aging", Hibernate.STRING);
            query.addScalar("requestId",Hibernate.LONG);
            query.addScalar("migrated",Hibernate.BOOLEAN);

            query.setResultTransformer(Transformers.aliasToBean(RequestorHistory.class));
            List<RequestorHistory> reqHistoryList = query.list();
            RequestorHistoryList reqHistoryLists = new RequestorHistoryList();
            reqHistoryLists.setRequestorHistory(reqHistoryList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return reqHistoryLists;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This methos is used to retrieve the unapplied amount details for a particular requestor
     * @param requestId
     * @return RequestorUnappliedAmountDetailsList
     */
    @Override
    @SuppressWarnings("unchecked")
    public RequestorUnappliedAmountDetailsList retrieveUnappliedAmountDetails(long requestId) {
        final String logSM = "retrieveUnappliedAmountDetails(requestId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestorId" + requestId);
        }
        try{

            Session session = getSession();
            String queryString =
                    session.getNamedQuery("retrieveRequestorUnappliedAmountDetails").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestId", requestId, Hibernate.LONG);

            query.addScalar("type", Hibernate.STRING);
            query.addScalar("amount", Hibernate.DOUBLE);

            query.setResultTransformer(Transformers.aliasToBean(RequestorUnappliedAmountDetails.class));
            List<RequestorUnappliedAmountDetails> reqAmtDetailsList = query.list();
            RequestorUnappliedAmountDetailsList reqAmtDetailsLists = new RequestorUnappliedAmountDetailsList();
            reqAmtDetailsLists.setRequestorUnappliedAmountDetails(reqAmtDetailsList);

            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End");
            }

            return reqAmtDetailsLists;

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public List<RequestorAdjustmentsPayments> retrieveRequestorPaymentDetails(
            long requestId) {

        final String logSM = "retrieveRequestorPaymentDetails(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorPaymentDetails")
                    .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, Hibernate.LONG);
            query.addScalar("id", Hibernate.LONG);
            query.addScalar("paymentAmount", Hibernate.DOUBLE);
            query.addScalar("paymentMethod", Hibernate.STRING);
            query.addScalar("date", Hibernate.TIMESTAMP);
            query.addScalar("description", Hibernate.STRING);
            query.addScalar("unAppliedAmt", Hibernate.DOUBLE);
            query.addScalar("invoiceId", Hibernate.LONG);
            query.addScalar("txnType", Hibernate.STRING);
            query.addScalar("appliedAmount", Hibernate.DOUBLE);
            query.addScalar("requestorId", Hibernate.LONG);

            query.setResultTransformer(Transformers.aliasToBean(RequestorAdjustmentsPayments.class));
            @SuppressWarnings("unchecked")
           List<RequestorAdjustmentsPayments> adjPayList = query.list();

             if (DO_DEBUG) {
              LOG.debug(logSM + "<<End");
             }
            return adjPayList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    @Override
    public List<RequestorAdjustment> retrieveRequestorAdjustmentDetails(long requestId) {

        final String logSM = "retrieveRequestorAdjustmentDetails(requestId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveRequestorAdjustmentDetails")
                    .getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);
            query.setParameter("requestId", requestId, Hibernate.LONG);
            query.addScalar("invoiceSeq", Hibernate.LONG);
            query.addScalar("requestorSeq", Hibernate.LONG);
            query.addScalar("reason", Hibernate.STRING);
            query.addScalar("amount", Hibernate.DOUBLE);
            query.addScalar("unappliedAmount", Hibernate.DOUBLE);
            query.addScalar("adjustmentDate", Hibernate.TIMESTAMP);
            query.addScalar("adjustmentTypeByValue", Hibernate.STRING);
            query.addScalar("note", Hibernate.STRING);

            query.setResultTransformer(Transformers.aliasToBean(RequestorAdjustment.class));
            @SuppressWarnings("unchecked")
           List<RequestorAdjustment> adjList = query.list();

             if (DO_DEBUG) {
              LOG.debug(logSM + "<<End");
             }
            return adjList;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }
    }

    /**
     * This method will update RequestorPayment.
     * @param paymentInfo
     * @return paymentId
     */
    @Override
    public void updateRequestorPaymentDetails(long requestorPayId,double unappliedAmt,
            Timestamp date,User user) {

        final String logSM = "updateRequestorPaymentDetails(requestorPayId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorPayId);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestorPaymentDetails");

            query.setParameter("modifiedDate", date, Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy",user.getInstanceId(), Hibernate.INTEGER);
            query.setParameter("unappliedAmount", unappliedAmt,
                                Hibernate.DOUBLE);
            query.setParameter("requestorPaymentId", requestorPayId, Hibernate.LONG);

            query.executeUpdate();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                  e.getMessage());
        }

    }

    /**
     * This method will update RequestorAdjustment.
     * @param paymentInfo
     * @return paymentId
     */
    @Override
    public void updateRequestorAdjustmentDetails(long requestorAdjId,
            double appliedAmt, double unappliedAmt, Timestamp date, User user) {
        final String logSM = "updateRequestorAdjustmentDetails(requestorPayId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + requestorAdjId);
        }

        try {

            Session session = getSession();
            Query query = session.getNamedQuery("updateRequestorAdjustmentDetails");

            query.setParameter("modifiedDate", date, Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy",user.getInstanceId(), Hibernate.INTEGER);
            query.setParameter("unappliedAmount", unappliedAmt, Hibernate.DOUBLE);
           /* query.setParameter("appliedAmount", appliedAmt,
                    Hibernate.DOUBLE); for cr 377572*/
            query.setParameter("requestorAdjustmentId", requestorAdjId, Hibernate.LONG);

            query.executeUpdate();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                  e.getMessage());
        }
    }


    @Override
    public RequestorCharges retrieveRequestorCharges(long requestorId) {

        final String logSM = "retrieveRequestorCharges(requestorId)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestorId" + requestorId);
        }

        try {

            Session session = getSession();
            String queryString =
               session.getNamedQuery("retrieveRequestorCharges").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", requestorId, Hibernate.LONG);

            query.addScalar("invoiceBalance", Hibernate.DOUBLE);
            query.addScalar("unAppliedPayment", Hibernate.DOUBLE);
            query.addScalar("unAppliedAdjustment", Hibernate.DOUBLE);

            query.setResultTransformer(Transformers.aliasToBean(RequestorCharges.class));
            List<RequestorCharges> requestorCharges = query.list();

            if (DO_DEBUG) {
                LOG.debug(logSM);
            }

            return CollectionUtilities.isEmpty(requestorCharges) ? new RequestorCharges()
                                                                 : requestorCharges.get(0);

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }

    @Override
    public long createRequestorRefund(RequestorRefund refundDetails) {

        final String logSM = "createRequestorRefund(refundDetails)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:requestorId" + refundDetails);
        }

        try {
            StringBuilder freeFormNotes = new StringBuilder();
            String freeFormNote = null;
            if(CollectionUtilities.hasContent(refundDetails.getNotes()))
            {
               for(String note : refundDetails.getNotes())
               {
                   freeFormNotes.append(note).append(ROIConstants.FIELD_SEPERATOR);
               }
               freeFormNote = freeFormNotes.toString().substring(0, freeFormNotes.toString().length() - 1);
            }
            Session session = getSession();
            String queryString =
                    session.getNamedQuery("createRequestorRefund").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.setParameter("requestorId", refundDetails.getRequestorId(), Hibernate.LONG);
            query.setParameter("refundAmount", refundDetails.getRefundAmount(), Hibernate.DOUBLE);
            query.setParameter("refundDate", refundDetails.getRefundDate(), Hibernate.DATE);
            query.setParameter("note", refundDetails.getNote(), Hibernate.STRING);
            query.setParameter("templateId", refundDetails.getTemplateId(), Hibernate.LONG);
            query.setParameter("templateName", refundDetails.getTemplateName(), Hibernate.STRING);
            query.setParameter("outputMethod", refundDetails.getOutputMethod(), Hibernate.STRING);
            query.setParameter("queuePassword", refundDetails.getQueuePassword(), Hibernate.STRING);
            query.setParameter("freeformnote", freeFormNote, Hibernate.STRING);
            query.setParameter("createdDate", refundDetails.getCreatedDt(), Hibernate.TIMESTAMP);
            query.setParameter("createdBy", refundDetails.getCreatedBy(), Hibernate.LONG);
            query.setParameter("modifiedDate", refundDetails.getModifiedDt(), Hibernate.TIMESTAMP);
            query.setParameter("modifiedBy", refundDetails.getModifiedBy(), Hibernate.LONG);
            query.setParameter("recordVersion", refundDetails.getRecordVersion(), Hibernate.INTEGER);

            BigDecimal idValue = (BigDecimal) query.uniqueResult();

            if (DO_DEBUG) {
                LOG.debug(logSM);
            }

            return (idValue == null) ? 0 : idValue.longValue();

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e,
                    ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
        } catch (Exception e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
        }

    }

    /**
    *
    * @see com.mckesson.eig.roi.requestor.dao.RequestorDAO
    *  #retrieveRequestorUnappliedPayments(long)
    */
    @Override
   public List<RequestorPayment> retrieveRequestorUnappliedPayments(long requestorId) {

       final String logSM = "retrieveRequestorUnappliedPayments(requestorId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:RequestorId" + requestorId);
       }
       try {

           Session session = getSession();
           String queryString =
                   session.getNamedQuery("retrieveRequestorUnappliedPayments")
                          .getQueryString();

           SQLQuery query = session.createSQLQuery(queryString);
           query.setParameter("requestorId", requestorId, Hibernate.LONG);

           query.addScalar("paymentId", Hibernate.LONG);
           query.addScalar("totalAppliedAmount", Hibernate.DOUBLE);
           query.addScalar("unAppliedAmount", Hibernate.DOUBLE);
           query.addScalar("refundAmount", Hibernate.DOUBLE);
           query.setResultTransformer(Transformers.aliasToBean(RequestorPayment.class));
           @SuppressWarnings("unchecked")
           List<RequestorPayment> reqPay = query.list();

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End");
           }
           return reqPay;
       } catch (DataIntegrityViolationException e) {
           throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Exception e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                  e.getMessage());
       }
   }

   /**
    * This method will create RequestCoreDeliveryChargestoRequestorPayment.
    * @param paymentList
    */
   @Override
   public void updateRequestorPayment(List<RequestorPayment> paymentList, final boolean isRefund) {

       final String logSM = "updateRequestorPayment(paymentList)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + paymentList);
       }

       try {

           final Session session = getSession();
           PlainSqlBatchProcessor processor = new PlainSqlBatchProcessor() {

               @Override
               protected Session getSession() {
                   return session;
               }

               @Override
               protected long getBatchSize() {
                   return ROIConstants.SQL_BATCH_SIZE;
               }

               @Override
               protected <T extends BaseModel> void addToBatch(PreparedStatement pStmt,
                       T object) throws SQLException {

                   int index = 1;
                   RequestorPayment paymentInfo = (RequestorPayment) object;

                   if (isRefund) {
                       pStmt.setDouble(index++, paymentInfo.getRefundAmount());
                   }
                   pStmt.setTimestamp(index++, getSQLTimeStamp(paymentInfo.getModifiedDt()));
                   pStmt.setLong(index++, paymentInfo.getModifiedBy());
                   pStmt.setDouble(index++, paymentInfo.getUnAppliedAmount());
                   pStmt.setLong(index++, paymentInfo.getPaymentId());

               }
           };

           String qryName;
           if (isRefund) {
               qryName = "updateRefundPayment";
           } else {
               qryName = "updatePayment";
           }
           String query = session.getNamedQuery(qryName).getQueryString();
           processor.execute(paymentList, query);

           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                   e.getMessage());
       } catch (Exception e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                   e.getMessage());
       }

   }

   /**
    * Method to retrieve the requestor's Adjustment and Payment for cancel request
    *
    * @param invoiceId
    * @return - List<RequestorAdjustmentsPayments>
    */
   @Override
   public List<RequestorAdjustmentsPayments> retrieveRequestorAdjAndPayDetailsForCancelReq(long invoiceId) {

       final String logSM = "retrieveRequestorAdjAndPayDetailsForCancelReq(long invoiceId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start:" + invoiceId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("retrieveRequestorAdjAndPayDetailsForCancelReq")
                   .getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);
           query.setParameter("invoiceId", invoiceId, Hibernate.LONG);
           query.addScalar("appliedAmount", Hibernate.DOUBLE);
           query.addScalar("id", Hibernate.LONG);
           query.addScalar("unAppliedAmt", Hibernate.DOUBLE);
           query.addScalar("amount", Hibernate.DOUBLE);
           query.addScalar("txnType", Hibernate.STRING);
           query.addScalar("prebillPaymentsAdjustments", Hibernate.BOOLEAN);
           query.setResultTransformer(Transformers.aliasToBean(RequestorAdjustmentsPayments.class));
           @SuppressWarnings("unchecked")
          List<RequestorAdjustmentsPayments> invoiceList = query.list();

            if (DO_DEBUG) {
             LOG.debug(logSM + "<<End");
            }
           return invoiceList;
       } catch (DataIntegrityViolationException e) {
           throw new ROIException(e,
                   ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(e,
                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
       } catch (Exception e) {
           throw new ROIException(e.getCause(),
                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
       }
   }

   /**
    * Retrieve requestor payment.
    * @param paymentId
    * @return the requestor payment
    */
   @SuppressWarnings("unchecked")
   @Override
   public RequestorPaymentList retrieveRequestorPayment(long paymentId) {

       final String logSM = "retrieveRequestorPayment(paymentId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + paymentId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("retrieveRequestorPayment").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);
           query.addScalar("paymentId", Hibernate.LONG);
           query.addScalar("requestorId", Hibernate.LONG);
           query.addScalar("createdDt", Hibernate.TIMESTAMP);
           query.addScalar("createdBy", Hibernate.LONG);
           query.addScalar("modifiedDt", Hibernate.TIMESTAMP);
           query.addScalar("modifiedBy", Hibernate.LONG);
           query.addScalar("recordVersion", Hibernate.INTEGER);
           query.addScalar("paymentAmount", Hibernate.DOUBLE);
           query.addScalar("paymentMode", Hibernate.STRING);
           query.addScalar("description", Hibernate.STRING);
           query.addScalar("unAppliedAmount", Hibernate.DOUBLE);
           query.addScalar("paymentDate", Hibernate.TIMESTAMP);
           query.setResultTransformer(Transformers.aliasToBean(RequestorPaymentList.class));

           query.setParameter("paymentId", paymentId);
           List<RequestorPaymentList> paymentList = query.list();

           if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:");
           }
           return CollectionUtilities.isEmpty(paymentList) ? null : paymentList.get(0);

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
   }

   /**
    * Retrieve requestor adjustment.
    * @param adjustmentId
    * @return the requestor adjustment
    */
   @SuppressWarnings("unchecked")
   @Override
   public RequestorAdjustment retrieveRequestorAdjustment(long adjustmentId) {

       final String logSM = "retrieveRequestorAdjustment(adjustmentId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + adjustmentId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("retrieveRequestorAdjustment").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);

           query.addScalar("id", Hibernate.LONG);
           query.addScalar("amount", Hibernate.DOUBLE);
           query.addScalar("reason", Hibernate.STRING);
           query.addScalar("adjustmentTypeByValue", Hibernate.STRING);
           query.addScalar("unappliedAmount", Hibernate.DOUBLE);
           query.addScalar("adjustmentDate", Hibernate.TIMESTAMP);
           query.addScalar("note", Hibernate.STRING);

           query.setResultTransformer(Transformers.aliasToBean(RequestorAdjustment.class));

           query.setParameter("adjustmentId", adjustmentId);
           List<RequestorAdjustment> adjusmentList = query.list();

           if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:" + adjusmentList.size());
           }
           return CollectionUtilities.isEmpty(adjusmentList)? null : adjusmentList.get(0);

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
   }

   /**
    * Delete requestor payment.
    * @param paymentId
    */
   @Override
   public void deleteRequestorPayment(long paymentId) {

       final String logSM = "deleteRequestorPayment(paymentId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + paymentId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("deleteRequestorPayment").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);
           query.setParameter("paymentId", paymentId);
           query.executeUpdate();

           if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:");
           }

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
   }

   /**
    * Delete requestor adjustment.
    * @param adjustmentId
    */
   @Override
   public void deleteRequestorAdjustment(long adjustmentId) {

       final String logSM = "deleteRequestorAdjustment(adjustmentId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + adjustmentId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("deleteRequestorAdjustment").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);
           query.setParameter("adjustmentId", adjustmentId);
           query.executeUpdate();

           if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:");
           }

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
   }

   /**
    * Retrieve mapped invoices by adjustment and invoice id.
    * @param adjustmentId
    * @param invoiceId
    * @return the adjToInvoice ids
    */
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> retrieveMappedInvoicesByAdjustmentAndInvoiceId(
            long adjustmentId, long invoiceId) {

       final String logSM = "retrieveRequestorAdjustment(adjustmentId)";
       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + adjustmentId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery(
                    "retrieveMappedInvoicesByAdjustmentAndInvoiceId").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);

           query.addScalar("mappingId", Hibernate.LONG);
           query.setParameter("adjustmentId", adjustmentId);
           query.setParameter("invoiceId", invoiceId);

           List<Long> mappingIds = query.list();

           if (DO_DEBUG) {
              LOG.debug(logSM + "<<End:" + mappingIds.size());
           }

           return mappingIds;
       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                  e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
    }

    /**
     * Retrieve mapped invoices by payment and invoice id.
     * @param paymentId
     * @param invoiceId
     * @return the payToInvoice ids
     */
     @SuppressWarnings("unchecked")
     @Override
     public List<Long> retrieveMappedInvoicesByPaymentAndInvoiceId(
             long paymentId, long invoiceId) {

        final String logSM = "retrieveMappedInvoicesByPaymentAndInvoiceId(paymentId, invoiceId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: " + paymentId);
        }

        try {

            Session session = getSession();
            String queryString = session.getNamedQuery(
                     "retrieveMappedInvoicesByPaymentAndInvoiceId").getQueryString();
            SQLQuery query = session.createSQLQuery(queryString);

            query.addScalar("mappingId", Hibernate.LONG);
            query.setParameter("paymentId", paymentId);
            query.setParameter("invoiceId", invoiceId);
            List<Long> mappingIds = query.list();

            if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:" + mappingIds.size());
            }

            return mappingIds;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }
    }

     /**
      * This method is used to save the details into the mapping table
      * @param paymentInfo
      * @return paymentTOInvoiceId
      */
     @Override
     public long createInvoiceToPayment(RequestorPayment paymentInfo) {

         final String logSM = "createInvoiceToPayment(paymentInfo)";
         if (DO_DEBUG) {
             LOG.debug(logSM + ">>Start:" + paymentInfo);
         }

         try {

             Session session = getSession();
             Query query = session.getNamedQuery("createInvoiceToPayment");

             query.setParameter("invoiceId",
                    paymentInfo.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
             query.setParameter("paymentId", paymentInfo.getPaymentId(), Hibernate.LONG);
             query.setParameter("createdDate", paymentInfo.getCreatedDt(), Hibernate.TIMESTAMP);
             query.setParameter("createdBy", paymentInfo.getCreatedBy(), Hibernate.LONG);
             query.setParameter("modifiedDate", paymentInfo.getModifiedDt(), Hibernate.TIMESTAMP);
             query.setParameter("modifiedBy", paymentInfo.getModifiedBy(), Hibernate.LONG);
             query.setParameter("recordVersion", paymentInfo.getRecordVersion(), Hibernate.INTEGER);
             query.setParameter("amount", paymentInfo.getLastAppliedAmount(), Hibernate.DOUBLE);
             query.setParameter("prebillPayment", paymentInfo.isPrebillPayment(), Hibernate.BOOLEAN);
             
             BigDecimal payToInvoiceValue = (BigDecimal) query.uniqueResult();

             if (DO_DEBUG) {
                 LOG.debug(logSM);
             }
             return payToInvoiceValue.longValue();

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

     /**
      * This method will deletes the records in RequestCoreDeliveryChargestoRequestorPayment
      *  table by paymentId and invoiceId.
      * @param paymentList
      */
     @Override
     public void deleteInvoiceToPayment(RequestorPayment paymentInfo) {

         final String logSM = "deleteInvoiceToPayment(paymentInfo)";
         if (DO_DEBUG) {
             LOG.debug(logSM + ">>Start:" + paymentInfo);
         }

         try {

             Session session = getSession();
             Query query = session.getNamedQuery("deleteMappedInvoices");

             query.setParameter("invoiceId",
                    paymentInfo.getRequestCoreDeliveryChargesId(), Hibernate.LONG);
             query.setParameter("paymentId", paymentInfo.getPaymentId(), Hibernate.LONG);

             query.executeUpdate();

             if (DO_DEBUG) {
                 LOG.debug(logSM);
             }

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

	/**
    * Retrieve Requestor adjustment and payment details.
    * @param adjustmentId
    */
   @Override
   public long retrieveRequestorAdjPaymentCount(long requestorId) {

       final String logSM = "retrieveRequestorAdjPaymentCount(requestorId)";

       if (DO_DEBUG) {
           LOG.debug(logSM + ">>Start: " + requestorId);
       }

       try {

           Session session = getSession();
           String queryString = session.getNamedQuery("retrieveRequestorAdjPaymentCount").getQueryString();
           SQLQuery query = session.createSQLQuery(queryString);
           query.setParameter("requestorId", requestorId);
           query.addScalar("adjPaymentCount", Hibernate.LONG);

           List<Long> list = query.list();
           if (DO_DEBUG) {
               LOG.debug(logSM + "<<End:");
           }

           return CollectionUtilities.isEmpty(list) ? 0 : list.get(0);

       } catch (DataIntegrityViolationException e) {
           throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
       } catch (HibernateOptimisticLockingFailureException e) {
           throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                   e.getMessage());
       } catch (Throwable e) {
           throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
       }
   }
   
   
   private String putParameters(String value, String key) {
       String updatedKey = key + (UUID.randomUUID().toString().replace("-", "_"));
       parameters.put(updatedKey, value);
       return updatedKey;
       
   }
}
