package com.mckesson.eig.roi.supplementary.dao;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.base.model.SearchCondition;
import com.mckesson.eig.roi.base.model.SearchCondition.OPERATION;
import com.mckesson.eig.roi.base.model.SearchCriteria;
import com.mckesson.eig.roi.request.model.RequestSupplementalAttachment;
import com.mckesson.eig.roi.request.model.RequestSupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalDocument;
import com.mckesson.eig.roi.supplementary.model.ROISupplementalPatient;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityAttachment;
import com.mckesson.eig.roi.supplementary.model.ROISupplementarityDocument;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class ROISupplementaryDAOImpl extends ROIDAOImpl implements ROISupplementaryDAO {

    private static final OCLogger LOG = new OCLogger(ROISupplementaryDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    private static final String ENCOUNTER_SEARCH = "encounter";
    private static final String FREEFORM_FACILITY_SEARCH = "freeformfacility";
    private static final String PATIENT_SEQ = "ROI_SupplementalPatients_Seq";

    @Override
    public long createSupplementalPatient(ROISupplementalPatient patient) {
        final String logSM = "createSupplementalPatient(patient)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patient);
        }

        long id = toPlong((Long) create(patient));
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    @Override
    public long createSupplementalDocument(ROISupplementalDocument document) {
        final String logSM = "createSupplementalDocument(document)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }

        long id = toPlong((Long) create(document));
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    @Override
    public long createSupplementalAttachment(
            ROISupplementalAttachment attachment) {
        final String logSM = "createSupplementalAttachment(attachment)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }

        long id = toPlong((Long) create(attachment));
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    @Override
    public long createSupplementarityDocument(
            ROISupplementarityDocument document) {
        final String logSM = "createSupplementarityDocument(document)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }

        long id = toPlong((Long) create(document));
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    @Override
    public long createSupplementarityAttachment(
            ROISupplementarityAttachment attachment) {
        final String logSM = "createSupplementalPatient(attachment)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }

        long id = toPlong((Long) create(attachment));
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + id);
        }

        return id;
    }

    @Override
    public ROISupplementalPatient getSupplementalPatient(long id) {
        final String logSM = "getROISupplementalPatient(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:PatientId : " + id);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementalPatient> patients = getHibernateTemplate().findByNamedQuery(
                "getROISupplementalPatientById", new Long(id));

        if (CollectionUtilities.isEmpty(patients)) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Patients :" + patients);
        }

        return patients.get(0);
    }

    @Override
    public List<ROISupplementalDocument> getSupplementalDocumentsByPatientId(long patientId) {
        final String logSM = "getSupplementalDocuments(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:PatientId : " + patientId);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementalDocument> documents = getHibernateTemplate().findByNamedQuery(
                "getROISupplementalDocumentsByPatientId", new Long(patientId));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Documents :" + documents);
        }

        return documents;
    }

    @Override
    public List<ROISupplementalAttachment> getSupplementalAttachmentsByPatientId(long patientId) {
        final String logSM = "getSupplementalAttachments(patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:PatientId : " + patientId);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementalAttachment> attachments = getHibernateTemplate().findByNamedQuery(
                "getROISupplementalAttachmentsByPatientId", new Long(patientId));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Attachments :" + attachments);
        }

        return attachments;
    }

    @Override
    public List<ROISupplementarityDocument> getSupplementarityDocuments(String mrn, String facility) {
        final String logSM = "getSupplementarityDocuments(mrn, facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:mrn, facility : " + mrn + "," + facility);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementarityDocument> documents = getHibernateTemplate().findByNamedQuery(
                "getROISupplementarityDocumentsByMrnFacility", new Object[]{mrn, facility});

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Documents :" + documents);
        }

        return documents;
    }

    @Override
    public List<ROISupplementarityAttachment> getSupplementarityAttachments(String mrn, String facility) {
        final String logSM = "getSupplementarityAttachments(mrn, facility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:mrn, facility : " + mrn + "," + facility);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementarityAttachment> attachments = getHibernateTemplate().findByNamedQuery(
                "getROISupplementarityAttachmentsByMrnFacility", new Object[]{mrn, facility});

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Attachments :" + attachments);
        }

        return attachments;
    }

    @Override
    public ROISupplementalDocument getSupplementalDocument(long id) {
        final String logSM = "getSupplementalDocument(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:id : " + id);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementalDocument> documents = getHibernateTemplate().findByNamedQuery(
                "getROISupplementalDocumentById", new Long(id));

        if (CollectionUtilities.isEmpty(documents)) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Documents :" + documents);
        }

        return documents.get(0);
    }
    
    @Override
    public ROISupplementalAttachment getSupplementalAttachment(long id) {
        final String logSM = "getROISupplementalAttachment(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:id : " + id);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementalAttachment> attachments = getHibernateTemplate().findByNamedQuery(
                "getROISupplementalAttachmentById", new Long(id));

        if (CollectionUtilities.isEmpty(attachments)) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Attachments :" + attachments);
        }

        return attachments.get(0);
    }
    
    @Override
    public ROISupplementarityDocument getSupplementarityDocument(long id) {
        final String logSM = "getROISupplementarityDocument(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:id : " + id);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementarityDocument> documents = getHibernateTemplate().findByNamedQuery(
                "getROISupplementarityDocumentById", new Long(id));

        if (CollectionUtilities.isEmpty(documents)) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Documents :" + documents);
        }

        return documents.get(0);
    }
    
    @Override
    public ROISupplementarityAttachment getSupplementarityAttachment(long id) {
        final String logSM = "getROISupplementarityAttachment(id)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:id : " + id);
        }

        @SuppressWarnings("unchecked")
        // not supported by 3rdParty API
        List<ROISupplementarityAttachment> attachments = getHibernateTemplate().findByNamedQuery(
                "getROISupplementarityAttachmentById", new Long(id));

        if (CollectionUtilities.isEmpty(attachments)) {
            return null;
        }
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Attachments :" + attachments);
        }

        return attachments.get(0);
    }
    
    @Override
    public int deleteSupplementalPatient(long patientId) {
        final String logSM = "deleteSupplementalPatient()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }
        String updateQuery = "DELETE ROISupplementalPatient p "
                + "where p.id = " + patientId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int deleteSupplementalDocument(long docId) {
        final String logSM = "deleteSupplementalDocument()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + docId);
        }
        String updateQuery = "DELETE ROISupplementalDocument p "
                + "where p.id = " + docId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int deleteSupplementalAttachment(long attachmentId) {
        final String logSM = "deleteSupplementalAttachment()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachmentId);
        }
        String updateQuery = "DELETE ROISupplementalAttachment p "
                + "where p.id = " + attachmentId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    @Override
    public int deleteSupplementalDocumentByPatientId (long patientId) {
        final String logSM = "deleteSupplementalDocumentByPatientId()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }
        String updateQuery = "DELETE ROISupplementalDocument p "
                + "where p.patientId = " + patientId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    @Override
    public int deleteSupplementalAttachmentByPatientId (long patientId) {
        final String logSM = "deleteSupplementalAttachmentByPatientId()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }
        String updateQuery = "DELETE ROISupplementalAttachment p "
                + "where p.patientId = " + patientId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int deleteSupplementarityDocument(long docId) {
        final String logSM = "deleteSupplementarityDocument()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + docId);
        }
        String updateQuery = "DELETE ROISupplementarityDocument p "
                + "where p.id = " + docId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int deleteSupplementarityAttachment(long attachmentId) {
        final String logSM = "deleteSupplementarityAttachment()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachmentId);
        }
        String updateQuery = "DELETE ROISupplementarityAttachment p "
                + "where p.id = " + attachmentId;
        try {
            int recordupdated = getHibernateTemplate().bulkUpdate(updateQuery);
            return recordupdated;
        } catch (Exception ex) {
            return 0;
        }
    }
    
    @Override
    public boolean updateSupplementalPatient(ROISupplementalPatient patient) {
        final String logSM = "updateSupplementalPatient";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patient);
        }
        merge(patient);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    @Override
    public boolean updateSupplementalDocument(ROISupplementalDocument document) {
        final String logSM = "updateSupplementalDocument";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }
        merge(document);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    @Override
    public boolean updateSupplementalAttachment(ROISupplementalAttachment attachment) {
        final String logSM = "updateSupplementalAttachment";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }
        merge(attachment);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    @Override
    public boolean updateSupplementarityDocument(ROISupplementarityDocument document) {
        final String logSM = "updateSupplementarityAttachment";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + document);
        }
        merge(document);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    @Override
    public boolean updateSupplementarityAttachment(
            ROISupplementarityAttachment attachment) {
        final String logSM = "updateSupplementarityAttachment";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + attachment);
        }
        merge(attachment);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return true;
    }

    @Override
    public List<ROISupplementalPatient> searchSupplementalPatients(SearchCriteria criteria) {
        final String logSM = "searchSupplementalPatients(criteria)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:lastName: " + criteria);
        }
        try {
            
            boolean allowSearch = processEncounterSearch(criteria);

            // if only perform encounter search
            if (!allowSearch || criteria.getConditions().size() == 0) {
                return new ArrayList<ROISupplementalPatient>();
            }
            Session session = getSession();
            String sql = session.getNamedQuery("searchSupplmentalPatients").getQueryString();
            //
            HashMap<String, String>  parameterMap =  new HashMap<String, String>(); 
            sql += criteria.getWhereClause(parameterMap);
            
            SQLQuery query = prepareSQLQuery(session, sql, ROISupplementalPatient.class);

            Set<String> keys = parameterMap.keySet();
            for(String key: keys){
                String value = parameterMap.get(key);
                query.setParameter(key, value);
            }
         
            List<ROISupplementalPatient> result = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:searchSupplementalPatients result :" + result);
            }
            return result == null ? new ArrayList<ROISupplementalPatient>() : result;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    
    private boolean processEncounterSearch(SearchCriteria criteria) {
        
        boolean allowSearch = true;
        if (null == criteria.getConditions()) {
            return false;
        }
        
        String encounter = null;
        String freeformFacility = null;
        List<SearchCondition> removeConditions = new ArrayList<SearchCondition>();
        
        for(SearchCondition c : criteria.getConditions()) {

            if(c.getKey().equalsIgnoreCase(ENCOUNTER_SEARCH)) {
                
                // gets the encounter if the search criteria contains encounter 
                encounter = c.getValue();
                removeConditions.add(c);
            } else if (c.getKey().equalsIgnoreCase(FREEFORM_FACILITY_SEARCH)) {

                // gets the freeform facility if the search criteria contains the freeform facility
                freeformFacility = c.getValue();
                removeConditions.add(c);
            }
        }

        // if the search does not contains both the encounter and freeform facility
        // then the further search is based on the other criteria is allowed.
        if (encounter == null && freeformFacility == null) {
            return allowSearch;
        }
        
        // removes the search condition encounter and freeform facility 
        criteria.getConditions().removeAll(removeConditions); 
        List<Long> result = 
                    getPatientIdsWithEncounterAndFreeformFacility(encounter, freeformFacility);
        
        if (CollectionUtilities.isEmpty(result)) {
            // The search criterias are all in AND condition
            // hence if the returned result is empty, 
            // we should not allow serach based on other criteria  
            allowSearch = false;
        } else {

            SearchCondition condition = new SearchCondition();
            condition.setKey(PATIENT_SEQ);
            condition.setOperation(OPERATION.In.name());
            condition.setValue(buildStrForList(result));
            // add the new search condition to search based on the patient sequence
            criteria.addCondition(condition);
        }
        
        return allowSearch;
    }
    
    /**
     * Constructs the search condition for the in list
     * @param list
     * @return
     */
    private String buildStrForList(List<Long> list) {
        
        String result = "";
        Iterator<Long> iterator = list.iterator();
        while(iterator.hasNext()) {
            result += iterator.next();
            if(iterator.hasNext()) {
                result += ",";
            }
        }
        return result;    
    }
    
      
    private List<Long> getPatientIdsWithEncounterAndFreeformFacility(String encounter,
                                                                     String freeformFacility) {
        
        try {
            
            Session session = getSession();
            String queryString;
            SQLQuery query;
            if (encounter != null && freeformFacility != null) {
                
                queryString = 
                        session.getNamedQuery("searchSupplementaryForEncounterAndFreeformFacility")
                               .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("encounter", encounter, Hibernate.STRING);
                query.setParameter("freeformFacility", freeformFacility, Hibernate.STRING);
                
            } else if (encounter != null) {
                
                queryString = session.getNamedQuery("searchSupplementaryForEncounter")
                        .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("encounter", encounter, Hibernate.STRING);
                
            } else {

                queryString = session.getNamedQuery("searchSupplementaryForFreeformFacility")
                                     .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("freeformFacility", freeformFacility, Hibernate.STRING);
            }
            
            query.addScalar("id", Hibernate.LONG);
            List<Long> result = query.list();
            return result == null ? new ArrayList<Long>() : result;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    
    protected SQLQuery prepareSQLQuery(Session session, String query, Class clazz) {
        SQLQuery sqlQuery = super.prepareSQLQuery(session, query, clazz);
        sqlQuery.addScalar("mrn", Hibernate.STRING);
        sqlQuery.addScalar("facility", Hibernate.STRING);
        sqlQuery.addScalar("epn", Hibernate.STRING);
        sqlQuery.addScalar("lastName", Hibernate.STRING);
        sqlQuery.addScalar("firstName", Hibernate.STRING);
        sqlQuery.addScalar("gender", Hibernate.STRING);
        sqlQuery.addScalar("dateOfBirth", Hibernate.STRING);
        sqlQuery.addScalar("ssn", Hibernate.STRING);
        sqlQuery.addScalar("address1", Hibernate.STRING);
        sqlQuery.addScalar("address2", Hibernate.STRING);
        sqlQuery.addScalar("address3", Hibernate.STRING);
        sqlQuery.addScalar("city", Hibernate.STRING);
        sqlQuery.addScalar("zip", Hibernate.STRING);
        sqlQuery.addScalar("homephone", Hibernate.STRING);
        sqlQuery.addScalar("workphone", Hibernate.STRING);
        sqlQuery.addScalar("vip", Hibernate.BOOLEAN);
        sqlQuery.addScalar("freeformFacilityCode", Hibernate.STRING);
        sqlQuery.addScalar("freeformFacilityId", Hibernate.LONG);
        return sqlQuery;
    }

    @Override
    public boolean checkDuplicates(String lastName, String firstName, long supplementalId) {
        final String logSM = "checkDuplicates(lastName, firstName, supplementalId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:lastName: " + lastName
                            + ", firstName:" + firstName + ", supplementalId:" + supplementalId);
        }
        
        try {
            
            Session session = getSession();
            Query query = session.getNamedQuery("checkDuplicate");
            
            query.setParameter("lastName", lastName, Hibernate.STRING);
            query.setParameter("firstName", firstName, Hibernate.STRING);
            query.setParameter("supplementalId", supplementalId, Hibernate.LONG);
            
            int count = ((Integer)(query.uniqueResult())).intValue();
           
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:checkDuplicates count :" + count);
            }
            if (count > 0) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    
    @Override
    public String retrieveAttachmentPath(String uuid) {
        final String logSM = "retrieveAttachment(uuid)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:uuid: " + uuid);
        }
        try {
            Session session = getSession();
            String sql = session.getNamedQuery("retrieveAttachment").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setParameter("uuid", uuid, Hibernate.STRING);
            sqlQuery.addScalar("volume", Hibernate.STRING);
            sqlQuery.addScalar("path", Hibernate.STRING);
            sqlQuery.addScalar("uuid", Hibernate.STRING);
            List<Object[]> result = sqlQuery.list();
            if (DO_DEBUG) {
                LOG.debug(logSM + "<<End:searchSupplementalPatients result :" + result);
            }
            if (result == null || result.size() == 0) {
                return null;
            }
            Object[] obj = (Object[])result.get(0);
            return obj[0] + File.separator + obj[1] + File.separator + obj[2];
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    
    public List<BigInteger> retrievePatientCoreIdByPatientId(long patientId) {
        final String logSM = "retrievePatientCoreIdByPatientId(patientId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId);
        }
        try {

            Session session = getSession();
            Query query = session.getNamedQuery("retrievePatientCoreIdByPatientId");
           
            query.setParameter("patientId",patientId,Hibernate.LONG);

            @SuppressWarnings("unchecked")
            List<BigInteger> idValue = query.list();
            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
            return idValue != null && idValue.size() > 0 ? idValue : new ArrayList<BigInteger>();
    } catch (DataIntegrityViolationException e) {
        throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
    } catch (HibernateOptimisticLockingFailureException e) {
        throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
    } catch (Exception e) {
        throw new ROIException(e.getCause(),
                ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
    }
    }
    
    public List<RequestSupplementalDocument> getDocumentsCore(long patientId,boolean isSupplemental) {
        final String logSM = "getDocumentsCore(docId,isSupplemental)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId + isSupplemental);
        }
        try {

            Session session = getSession();
            String query = null;
            
            if(isSupplemental)
               query = session.getNamedQuery("getSupplementalDocumentsCore").getQueryString();
            else
               query = session.getNamedQuery("getSupplementarityDocumentsCore").getQueryString();
            
            SQLQuery sqlQuery = session.createSQLQuery(query);
            
            sqlQuery.setParameter("patientId",patientId,Hibernate.LONG);
            
            sqlQuery.addScalar("documentCoreSeq", Hibernate.LONG);
            sqlQuery.addScalar("documentSeq", Hibernate.LONG);
            sqlQuery.addScalar("patientSeq", Hibernate.LONG);
            sqlQuery.addScalar("supplementalId", Hibernate.LONG);
            sqlQuery.addScalar("mrn", Hibernate.STRING);
            sqlQuery.addScalar("facility", Hibernate.STRING);
            sqlQuery.addScalar("billingTierId", Hibernate.LONG);
            sqlQuery.addScalar("docName", Hibernate.STRING);
            sqlQuery.addScalar("encounter", Hibernate.STRING);
            sqlQuery.addScalar("docFacility", Hibernate.STRING);
            sqlQuery.addScalar("freeformfacility", Hibernate.STRING);
            sqlQuery.addScalar("department", Hibernate.STRING);
            sqlQuery.addScalar("subtitle", Hibernate.STRING);
            sqlQuery.addScalar("pageCount", Hibernate.STRING);
            sqlQuery.addScalar("dateOfService", Hibernate.DATE);
            sqlQuery.addScalar("comment", Hibernate.STRING);
            sqlQuery.addScalar("id", Hibernate.LONG);
            sqlQuery.addScalar("createdBy", Hibernate.LONG);
            sqlQuery.addScalar("modifiedBy", Hibernate.LONG);
            sqlQuery.addScalar("createdDt", Hibernate.DATE);
            sqlQuery.addScalar("modifiedDt", Hibernate.DATE);
            sqlQuery.addScalar("recordVersion", Hibernate.INTEGER);
            sqlQuery.setResultTransformer(Transformers.aliasToBean(RequestSupplementalDocument.class));
            
            @SuppressWarnings("unchecked")
            List<RequestSupplementalDocument> reqSuppDocument = sqlQuery.list();
            
            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
            return reqSuppDocument;
    } catch (DataIntegrityViolationException e) {
        throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
    } catch (HibernateOptimisticLockingFailureException e) {
        throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
    } catch (Exception e) {
        throw new ROIException(e.getCause(),
                ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
    }
    }  
    
    public List<RequestSupplementalAttachment> getAttachmentsCore(long patientId,boolean isSupplemental) {
        final String logSM = "getAttachmentsCore(attId,isSupplemental)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + patientId + isSupplemental);
        }
        try {

            Session session = getSession();
            String query = null;
            
            if(isSupplemental)
               query = session.getNamedQuery("getSupplementalAttachmentsCore").getQueryString();
            else
               query = session.getNamedQuery("getSupplementarityAttachmentsCore").getQueryString();
            
            SQLQuery sqlQuery = session.createSQLQuery(query);
            
            sqlQuery.setParameter("patientId",patientId,Hibernate.LONG);
            
            sqlQuery.addScalar("attachmentCoreSeq", Hibernate.LONG);
            sqlQuery.addScalar("attachmentSeq", Hibernate.LONG);
            sqlQuery.addScalar("patientSeq", Hibernate.LONG);
            sqlQuery.addScalar("supplementalId", Hibernate.LONG);
            sqlQuery.addScalar("mrn", Hibernate.STRING);
            sqlQuery.addScalar("facility", Hibernate.STRING);
            sqlQuery.addScalar("billingTierId", Hibernate.LONG);
            sqlQuery.addScalar("encounter", Hibernate.STRING);
            sqlQuery.addScalar("docFacility", Hibernate.STRING);
            sqlQuery.addScalar("freeformfacility", Hibernate.STRING);
            sqlQuery.addScalar("subtitle", Hibernate.STRING);
            sqlQuery.addScalar("isDeleted", Hibernate.BOOLEAN);
            sqlQuery.addScalar("pageCount", Hibernate.STRING);
            sqlQuery.addScalar("dateOfService", Hibernate.DATE);
            sqlQuery.addScalar("attachmentDate", Hibernate.DATE);
            sqlQuery.addScalar("uuid", Hibernate.STRING);
            sqlQuery.addScalar("volume", Hibernate.STRING);
            sqlQuery.addScalar("path", Hibernate.STRING);
            sqlQuery.addScalar("filename", Hibernate.STRING);
            sqlQuery.addScalar("filetype", Hibernate.STRING);
            sqlQuery.addScalar("fileext", Hibernate.STRING);
            sqlQuery.addScalar("printable", Hibernate.STRING);
            sqlQuery.addScalar("submittedBy", Hibernate.STRING);
            sqlQuery.addScalar("comment", Hibernate.STRING);
            sqlQuery.addScalar("externalSource", Hibernate.STRING);
            sqlQuery.addScalar("type", Hibernate.STRING);
            sqlQuery.addScalar("id", Hibernate.LONG);
            sqlQuery.addScalar("createdBy", Hibernate.LONG);
            sqlQuery.addScalar("modifiedBy", Hibernate.LONG);
            sqlQuery.addScalar("createdDt", Hibernate.DATE);
            sqlQuery.addScalar("modifiedDt", Hibernate.DATE);
            sqlQuery.addScalar("recordVersion", Hibernate.INTEGER);
            sqlQuery.setResultTransformer(Transformers.aliasToBean(RequestSupplementalAttachment.class));
            
            @SuppressWarnings("unchecked")
            List<RequestSupplementalAttachment> reqSuppAttachment = sqlQuery.list();
            
            if (DO_DEBUG) {
                LOG.debug(logSM);
            }
            return reqSuppAttachment;
    } catch (DataIntegrityViolationException e) {
        throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
    } catch (HibernateOptimisticLockingFailureException e) {
        throw new ROIException(e, ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, e.getMessage());
    } catch (Exception e) {
        throw new ROIException(e.getCause(),
                ROIClientErrorCodes.DATABASE_OPERATION_FAILED, e.getMessage());
    }
    }  
}
