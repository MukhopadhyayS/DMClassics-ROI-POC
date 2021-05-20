/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/

package com.mckesson.eig.roi.base.dao;


import java.io.BufferedReader;
import java.io.Reader;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.model.AddressType;
import com.mckesson.eig.roi.base.model.ContactType;
import com.mckesson.eig.roi.base.model.EmailPhoneType;
import com.mckesson.eig.roi.hpf.model.UserSecurity;
import com.mckesson.eig.roi.inuse.base.api.InUseClientErrorCodes;
import com.mckesson.eig.roi.inuse.base.api.InUseException;
import com.mckesson.eig.roi.muroioutbound.model.MUROIOutboundStatistics;
import com.mckesson.eig.roi.request.model.FreeFormFacility;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.SpringUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 * @author OFS
 * @date   Sep 01, 2009
 * @since  HPF 13.1 [ROI]; Feb 14, 2008
 */
public class ROIDAOImpl
extends HibernateDaoSupport
implements ROIDAO {

    private static final OCLogger LOG = new OCLogger(ROIDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static long _diff;
    private static boolean _diffSet;
    private static Map<String, Long> _roiEmailPhoneTypes;
    private static Map<String, Long> _roiAddressTypes;
    private static Map<String, Long> _roiContactTypes;
    
    private static final NumberFormat CURRENCY_FORMAT_US = NumberFormat.getCurrencyInstance(
    		                                                    ROIConstants.INVOICE_LOCALE);


    /**
     * This method sets the time difference in milliseconds b/w Database server and JVM
     */
    private void setDateDiff() {

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Timestamp> list = (List<Timestamp>) getHibernateTemplate().findByNamedQuery("getDBDate");
        _diff = list.get(0).getTime() - System.currentTimeMillis();
    }

    /**
     *
     * @see com.mckesson.eig.roi.base.dao.ROIDAO#getDate()
     */
    public Timestamp getDate() {

        if (!_diffSet) {
            setDateDiff();
            _diffSet = true;
        }

        return new Timestamp(System.currentTimeMillis() + _diff);
    }

    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    /**
     * This method is used for storing individual objects and handling data
     * integrity violation exceptions.
     *
     * @param object Object to create in the database.
     * @return The primary key value of the object inserted.
     */
    public Serializable create(Object object) {

        try {
            return getHibernateTemplate().save(object);
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        }
    }
    
    /**
     * This method is used for storing individual objects and handling data
     * integrity violation exceptions.
     *
     * @param object Object to create in the database.
     * @return The primary key value of the object inserted.
     */
    public Serializable createFile(Object object) {        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Transaction trans = session.beginTransaction();
            Serializable ser = session.save(object);
            trans.commit();
            return ser;
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        }
        catch(Throwable e) {
            throw new ROIException(e.getCause(),
                    ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
        finally {
            session.close();
        }
    }

    /**
     * This method is used for updating the object passed.
     * @param object -Object to be merged in the database.
     */
    public Object merge(Object object) {

        try {

            return getHibernateTemplate().merge(object);
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        }
    }

    /**
     * This method is used for delete of individual objects.
     * @param object - Object to delete in the database.
     */
    public void delete(Object object) {

        try {
            getHibernateTemplate().delete(object);
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        }
    }

    /**
     * This method is used for retrieving individual object
     *
     * @param cls Object to retrieve in the database.
     * @return id The primary key value of the object to be retrieved.
     */
    public Object get(Class< ? > cls, Serializable id) {

        Object obj = getHibernateTemplate().get(cls, id);

        if (obj == null) {
           throw new ROIException(ROIClientErrorCodes.INVALID_ID, id.toString());
        } else {
            return obj;
        }
    }

    /**
     * This method retrieves the email phone types from data base and performs caching
     * @param type -type of email phone to be processed.
     * @return id of the email phone type.
     */
    public long getEmailPhoneTypeId(String type) {

        if (_roiEmailPhoneTypes == null) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<EmailPhoneType> list = (List<EmailPhoneType>) getHibernateTemplate().
                                        findByNamedQuery("getEmailPhoneTypes");

            _roiEmailPhoneTypes = new HashMap <String, Long>();

            for (EmailPhoneType phType : list) {
                _roiEmailPhoneTypes.put(phType.getName().toUpperCase(), phType.getId());
            }
        }

        return _roiEmailPhoneTypes.get(type.toUpperCase());
    }

    /**
     * This method retrieves the address types from data base and performs caching
     * @param type -type of address to be processed.
     * @return id of the address type.
     */
    public long getAddressTypeId(String type) {

        if (_roiAddressTypes == null) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<AddressType> list = (List<AddressType>)  getHibernateTemplate().
                                        findByNamedQuery("getAddressTypes");

            _roiAddressTypes = new HashMap <String, Long>();

            for (AddressType addType : list) {
                _roiAddressTypes.put(addType.getName().toUpperCase(), addType.getId());
            }
       }

        return _roiAddressTypes.get(type.toUpperCase());
    }

    /**
     * This method retrieves the contact types from data base and performs caching
     * @param type -type of contact to be processed.
     * @return id of the contact type.
     */
    public long getContactTypeId(String type) {

        if (_roiContactTypes == null) {

            @SuppressWarnings("unchecked") // not supported by 3rdParty API
            List<ContactType> list = (List<ContactType>) getHibernateTemplate().
                                        findByNamedQuery("getContactTypes");

            _roiContactTypes = new HashMap <String, Long>();

            for (ContactType contactType : list) {
                _roiContactTypes.put(contactType.getName().toUpperCase(), contactType.getId());
            }
        }

        return _roiContactTypes.get(type.toUpperCase());
    }

    /**
     * @see com.mckesson.eig.roi.hpf.dao.ROIHPFDAO#retrieveFreeFormFacilities(java.lang.Integer)
     */
    public List<String> retrieveFreeFormFacilitiesByUser(final long  userId) {

        final String logSM = "retrieveFreeFormFacilities(key, userId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:UserId =" + userId);
        }
        
        try {
            
            long start = System.currentTimeMillis();
            
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveROIFreeFormFacilityByUser")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("freeFormFacilityName", StringType.INSTANCE);
            query.setParameter("createdBy", userId);
            
            @SuppressWarnings("unchecked") // not supported by the 3rdParty API
            List<String> facs = query.list();
            
            if (DO_DEBUG) {
                LOG.debug(">>NO.of records = " + facs.size());
                LOG.debug(">>Query Executiong Time = " + (System.currentTimeMillis() - start));
             }
            return facs;
        } catch (Exception ex) {
            throw new ROIException(
                            ROIClientErrorCodes.RETRIEVE_FREE_FORM_FACILITIES_OPERATION_FAILED);
        }
        
    }

    /**
     * @see com.mckesson.eig.roi.hpf.dao.ROIHPFDAO#
     * createFreeFormFacilities(com.mckesson.eig.roi.request.model.FreeFormFacility)
     */
    public FreeFormFacility createFreeFormFacilities(FreeFormFacility freeformFacility) {
        
        final String logSM = "createFreeFormFacilities(freeformFacility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:FreeformFacility =" + freeformFacility);
        }
        
        try {
            
            long start = System.currentTimeMillis();
            
            Session session = getSession();
            String queryString = session.getNamedQuery("createROIFreeFormFacility")
                                        .getQueryString();
            
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("freeFormFacilityName", freeformFacility.getFreeFormFacilityName());
            query.setParameter("createdDt", freeformFacility.getCreatedDt());
            query.setParameter("createdBy", freeformFacility.getCreatedBy());
            query.setParameter("modifiedDt", freeformFacility.getModifiedDt());
            query.setParameter("modifiedBy", freeformFacility.getModifiedBy());
            query.setParameter("recordVersion", freeformFacility.getRecordVersion());
            
            query.addScalar("freeformFacilityId", LongType.INSTANCE);
            
            @SuppressWarnings("unchecked") // not supported by the 3rdParty API
            List<Long> freeformFacilityId = query.list();
            
            if (freeformFacilityId.size() <= 0) {
                throw new ROIException(ROIClientErrorCodes.RETRIEVE_FREEFORM_FACILITY_FAILED);
            }
            
            long id = freeformFacilityId.get(0).longValue();
            freeformFacility.setId(id);
            
            if (DO_DEBUG) {
                LOG.debug(">>Query Executiong Time = " + (System.currentTimeMillis() - start));
            }
            return freeformFacility;
        } catch (Exception ex) {
            throw new ROIException(
                    ROIClientErrorCodes.RETRIEVE_FREE_FORM_FACILITIES_OPERATION_FAILED);
        }
        
    }
    
    /**
     * @see com.mckesson.eig.roi.hpf.dao.ROIHPFDAO#
     * retrieveFreeFormFacilitiesByName(java.lang.String)
     */
    public FreeFormFacility retrieveFreeFormFacilitiesByName(String facility, long userId) {

        final String logSM = "retrieveFreeFormFacilitiesByName(facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:Facility =" + facility + ", UserId:" + userId);
        }
        
        try {
            
            long start = System.currentTimeMillis();
            
            Session session = getSession();
            String queryString = session.getNamedQuery("retrieveROIFreeFormFacilityByName")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("id", LongType.INSTANCE);
            query.addScalar("freeFormFacilityName", StringType.INSTANCE);
            query.addScalar("createdDt", DateType.INSTANCE);
            query.addScalar("createdBy", LongType.INSTANCE);
            query.addScalar("modifiedDt", DateType.INSTANCE);
            query.addScalar("modifiedBy", LongType.INSTANCE);
            query.addScalar("recordVersion", IntegerType.INSTANCE);
            
            query.setParameter("freeFormFacilityName", facility);
            query.setParameter("createdBy", userId);

            query.setResultTransformer(Transformers.aliasToBean(FreeFormFacility.class));
            
            @SuppressWarnings("unchecked") // not supported by the 3rdParty API
            List<FreeFormFacility> facs = query.list();
            
            if (DO_DEBUG) {
                LOG.debug(">>NO.of records = " + facs.size());
                LOG.debug(">>Query Executiong Time = " + (System.currentTimeMillis() - start));
             }
            return (facs.size() <= 0) ? null : facs.get(0);
        } catch (Exception ex) {
            throw new ROIException(
                            ROIClientErrorCodes.RETRIEVE_FREE_FORM_FACILITIES_OPERATION_FAILED);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> retrieveGlobalInvoiceDueDays() {
        
        if (DO_DEBUG) {
            LOG.debug(">>Start :  = ");
        }
        
        try {
            
            List<Integer> invoiceDueDays  = (List<Integer>) getHibernateTemplate()
                                            .findByNamedQuery("retrieveInvoiceDueDays");

            if (DO_DEBUG) {
                LOG.debug(">>NO.of records = " + invoiceDueDays.size());
            }
            return invoiceDueDays;
            
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
            
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                    e.getMessage());
            
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }
    }

    public long toPlong(Long data) {
        return (null == data) ? 0 : data.longValue(); 
    }
    
    public int toPint(Integer data) {
        return (null == data) ? 0 : data.intValue(); 
    }
    
    public double toPdouble(Double data) {
        return (null == data) ? 0.0 : data.doubleValue(); 
    }
    
    public boolean toPboolean(Boolean data) {
        return (null == data) ? false : data.booleanValue(); 
    }
    
    /**
     * This method converts the given clob data into the String data
     * 
     * @param clobData
     * @return String
     */
    public String getStringData(Clob clobData) {
        
        try {
            
            if (null == clobData || clobData.length() <= 0) {
                return "";
            }
          
            Reader read = clobData.getCharacterStream();
            BufferedReader reader = new BufferedReader(read);
            StringBuffer xml = new StringBuffer();
            String data;

            while ((data = reader.readLine()) != null) {
                xml.append(data);
            }
            return xml.toString();
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED);
        }
    }
    
    protected double supressDollarSymbol(String amount) {
        
        try {
            
            return (StringUtilities.isEmpty(amount)) ? 0 
                    : CURRENCY_FORMAT_US.parse(amount).doubleValue();
        } catch (ParseException e) {
            throw new ROIException(ROIClientErrorCodes.INVALID_CURRENCY_TYPE);
        } 
    }

    protected String appendDollarSymbol(double amount) {
        
        return CURRENCY_FORMAT_US.format(amount);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserSecurity retrieveROIUserSecurity(String userID) {

        final String logSM = "retrieveROIUserSecurity(userID)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + userID);
        }


        List<UserSecurity> userSecurity = null;
        try {
            
            Session session = getSession();
            String queryString = session.getNamedQuery("getSecurityRights")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("userId", userID, StringType.INSTANCE);
            query.setParameter("facilityCode", ROIConstants.DEFAULT_FACILITY, StringType.INSTANCE);
            query.setParameter("securityDesc", ROIConstants.ROI_ADMINISTRATION_SECURITY, StringType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(UserSecurity.class));          
            userSecurity = query.list();
            
        } catch (DataAccessException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_ACCESS_EXCEPTION, e.getMessage());
        }
        
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return userSecurity.size() > 0 ? userSecurity.get(0) : null;
    }
    
    /**
     * The given {@link java.util.Date} object is converted to {@link java.sql.Timestamp}
     *
     * @param date
     * @return java.sql.Date
     */
    public java.sql.Timestamp getSQLTimeStamp(Date date) {
       
        if (null == date) {
            return null;
        }
       
        return new java.sql.Timestamp(date.getTime());
    }
	
	/**
     * Method to insert rows into ROI_OUTBOUND_STATISTICS
     * 
     * @param List
     *            <MUROIOutboundStatistics> muOutboundStatistics
     */
    public void bulkInsert(List<MUROIOutboundStatistics> muOutboundStatistics) {

        try {
            for (Iterator it = muOutboundStatistics.iterator(); it.hasNext();) {
                getHibernateTemplate().saveOrUpdate(it.next());
            }
        } catch (DataAccessException e) {
            throw new ROIException(
                    ROIClientErrorCodes.MU_CREATEROIOUTBOUND_BULK_INSERT, e
                            .getMessage());
        }
    }
    
    protected NativeQuery prepareSQLQuery(Session session, String query, Class clazz) {
        NativeQuery nativeQuery = session.createSQLQuery(query);
        nativeQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        nativeQuery.addScalar("id", LongType.INSTANCE);
        nativeQuery.addScalar("createdBy", IntegerType.INSTANCE);
        nativeQuery.addScalar("createdDt", StandardBasicTypes.TIMESTAMP);
        nativeQuery.addScalar("modifiedDt", StandardBasicTypes.TIMESTAMP);
        nativeQuery.addScalar("modifiedBy", IntegerType.INSTANCE);
        return nativeQuery;
    }
    
    /** 
     * TO DO:  remove obsolete - do not user - does not support new "Roles" implementation
     */
    @SuppressWarnings("unchecked")
    @Override
    public UserSecurity retrieveROIUserSecurityForOutputType(String userID) {

        final String logSM = "retrieveROIUserSecurityForOutputType(userID,securityName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + userID);
        }

        
        List<UserSecurity> userSecurity = null;
        try {
            
            Session session = getSession();
            String queryString = session.getNamedQuery("getSecurityRightsForOutputType")
                                        .getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("userId", userID, StringType.INSTANCE);
            query.setParameter("facilityCode", ROIConstants.DEFAULT_FACILITY, StringType.INSTANCE);

            query.setResultTransformer(Transformers.aliasToBean(UserSecurity.class));          
            userSecurity = query.list();
            
        } catch (DataAccessException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_ACCESS_EXCEPTION, e.getMessage());
        }
        
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return userSecurity.size() > 0 ? userSecurity.get(0) : null;
        
    }
    
    /**
     * This method retrieve the EIWDATAConfiguration location from database
     * @param key
     * @return configuration location
     */
    @Override
    public String retrieveEIWDATAConfiguration(String key) {

        final String logSM = "retrieveEIWDATAConfiguration()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + key);
        }
    
        // not supported by 3rdParty API
        @SuppressWarnings("unchecked")
        List<String> eiwData = (List<String>) getHibernateTemplate().findByNamedQuery(
                                     "retrieveEIWDATAConfiguration", key);
    
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        if (CollectionUtilities.isEmpty(eiwData) 
                || StringUtilities.isEmpty(eiwData.get(0))) {
            return null;
        }
        
        return eiwData.get(0);
    }
    
    protected String getSpecialCharSearchStr(String orig) {
        if(!StringUtilities.isEmpty(orig)) {
            String result = orig.replaceAll("_", "+_");
            result = result.replaceAll("%", "+%");
            return result;
        }
        return "";
    }

	
    /**
     * This method is used to return the corresponding local service
     * instance for the specified service name by accessing the ROI Service Factory.
     *
     * @param daoName Name of the local DAO.
     * @return Instance of the DAO.
     */
    protected ROIDAO getDAO(String daoName) {
        return (ROIDAO) SpringUtilities.getInstance().getBeanFactory().
                                                          getBean(daoName);
    }
}
