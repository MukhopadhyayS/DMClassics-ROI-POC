package com.mckesson.eig.roi.supplementary.dao;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

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
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;
import com.mckesson.eig.utility.util.CollectionUtilities;

public class ROISupplementaryDAOImpl extends ROIDAOImpl implements ROISupplementaryDAO {

    private static final Log LOG = LogFactory
            .getLogger(ROISupplementaryDAOImpl.class);
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
        List<ROISupplementalPatient> patients = (List<ROISupplementalPatient>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementalDocument> documents = (List<ROISupplementalDocument>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementalAttachment> attachments = (List<ROISupplementalAttachment>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementarityDocument> documents = (List<ROISupplementarityDocument>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementarityAttachment> attachments = (List<ROISupplementarityAttachment>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementalDocument> documents = (List<ROISupplementalDocument>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementalAttachment> attachments = (List<ROISupplementalAttachment>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementarityDocument> documents = (List<ROISupplementarityDocument>) getHibernateTemplate().findByNamedQuery(
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
        List<ROISupplementarityAttachment> attachments = (List<ROISupplementarityAttachment>) getHibernateTemplate().findByNamedQuery(
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
            Session session = getSessionFactory().getCurrentSession();
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
            
            Session session = getSessionFactory().getCurrentSession();
            String queryString;
            SQLQuery query;
            if (encounter != null && freeformFacility != null) {
                
                queryString = 
                        session.getNamedQuery("searchSupplementaryForEncounterAndFreeformFacility")
                               .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("encounter", encounter, StandardBasicTypes.STRING);
                query.setParameter("freeformFacility", freeformFacility, StandardBasicTypes.STRING);
                
            } else if (encounter != null) {
                
                queryString = session.getNamedQuery("searchSupplementaryForEncounter")
                        .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("encounter", encounter, StandardBasicTypes.STRING);
                
            } else {

                queryString = session.getNamedQuery("searchSupplementaryForFreeformFacility")
                                     .getQueryString();
                query = session.createSQLQuery(queryString);
                query.setParameter("freeformFacility", freeformFacility, StandardBasicTypes.STRING);
            }
            
            query.addScalar("id", StandardBasicTypes.LONG);
            List<Long> result = query.list();
            return result == null ? new ArrayList<Long>() : result;
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                    e.getMessage());
        }
    }
    
    protected SQLQuery prepareSQLQuery(Session session, String query, Class clazz) {
        SQLQuery sqlQuery = super.prepareSQLQuery(session, query, clazz);
        sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
        sqlQuery.addScalar("epn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("lastName", StandardBasicTypes.STRING);
        sqlQuery.addScalar("firstName", StandardBasicTypes.STRING);
        sqlQuery.addScalar("gender", StandardBasicTypes.STRING);
        sqlQuery.addScalar("dateOfBirth", StandardBasicTypes.STRING);
        sqlQuery.addScalar("ssn", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address1", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address2", StandardBasicTypes.STRING);
        sqlQuery.addScalar("address3", StandardBasicTypes.STRING);
        sqlQuery.addScalar("city", StandardBasicTypes.STRING);
        sqlQuery.addScalar("zip", StandardBasicTypes.STRING);
        sqlQuery.addScalar("homephone", StandardBasicTypes.STRING);
        sqlQuery.addScalar("workphone", StandardBasicTypes.STRING);
        sqlQuery.addScalar("vip", StandardBasicTypes.BOOLEAN);
        sqlQuery.addScalar("freeformFacilityCode", StandardBasicTypes.STRING);
        sqlQuery.addScalar("freeformFacilityId", StandardBasicTypes.LONG);
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
            
            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("checkDuplicate");
            
            query.setParameter("lastName", lastName, StandardBasicTypes.STRING);
            query.setParameter("firstName", firstName, StandardBasicTypes.STRING);
            query.setParameter("supplementalId", supplementalId, StandardBasicTypes.LONG);
            
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
            Session session = getSessionFactory().getCurrentSession();
            String sql = session.getNamedQuery("retrieveAttachment").getQueryString();
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setParameter("uuid", uuid, StandardBasicTypes.STRING);
            sqlQuery.addScalar("volume", StandardBasicTypes.STRING);
            sqlQuery.addScalar("path", StandardBasicTypes.STRING);
            sqlQuery.addScalar("uuid", StandardBasicTypes.STRING);
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

            Session session = getSessionFactory().getCurrentSession();
            Query query = session.getNamedQuery("retrievePatientCoreIdByPatientId");
           
            query.setParameter("patientId",patientId,StandardBasicTypes.LONG);

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

            Session session = getSessionFactory().getCurrentSession();
            String query = null;
            
            if(isSupplemental)
               query = session.getNamedQuery("getSupplementalDocumentsCore").getQueryString();
            else
               query = session.getNamedQuery("getSupplementarityDocumentsCore").getQueryString();
            
            SQLQuery sqlQuery = session.createSQLQuery(query);
            
            sqlQuery.setParameter("patientId",patientId,StandardBasicTypes.LONG);
            
            sqlQuery.addScalar("documentCoreSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("documentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("supplementalId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("billingTierId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("docName", StandardBasicTypes.STRING);
            sqlQuery.addScalar("encounter", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docFacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("freeformfacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("department", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            sqlQuery.addScalar("pageCount", StandardBasicTypes.STRING);
            sqlQuery.addScalar("dateOfService", StandardBasicTypes.DATE);
            sqlQuery.addScalar("comment", StandardBasicTypes.STRING);
            sqlQuery.addScalar("id", StandardBasicTypes.LONG);
            sqlQuery.addScalar("createdBy", StandardBasicTypes.LONG);
            sqlQuery.addScalar("modifiedBy", StandardBasicTypes.LONG);
            sqlQuery.addScalar("createdDt", StandardBasicTypes.DATE);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.DATE);
            sqlQuery.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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

            Session session = getSessionFactory().getCurrentSession();
            String query = null;
            
            if(isSupplemental)
               query = session.getNamedQuery("getSupplementalAttachmentsCore").getQueryString();
            else
               query = session.getNamedQuery("getSupplementarityAttachmentsCore").getQueryString();
            
            SQLQuery sqlQuery = session.createSQLQuery(query);
            
            sqlQuery.setParameter("patientId",patientId,StandardBasicTypes.LONG);
            
            sqlQuery.addScalar("attachmentCoreSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("attachmentSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("patientSeq", StandardBasicTypes.LONG);
            sqlQuery.addScalar("supplementalId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("mrn", StandardBasicTypes.STRING);
            sqlQuery.addScalar("facility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("billingTierId", StandardBasicTypes.LONG);
            sqlQuery.addScalar("encounter", StandardBasicTypes.STRING);
            sqlQuery.addScalar("docFacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("freeformfacility", StandardBasicTypes.STRING);
            sqlQuery.addScalar("subtitle", StandardBasicTypes.STRING);
            sqlQuery.addScalar("isDeleted", StandardBasicTypes.BOOLEAN);
            sqlQuery.addScalar("pageCount", StandardBasicTypes.STRING);
            sqlQuery.addScalar("dateOfService", StandardBasicTypes.DATE);
            sqlQuery.addScalar("attachmentDate", StandardBasicTypes.DATE);
            sqlQuery.addScalar("uuid", StandardBasicTypes.STRING);
            sqlQuery.addScalar("volume", StandardBasicTypes.STRING);
            sqlQuery.addScalar("path", StandardBasicTypes.STRING);
            sqlQuery.addScalar("filename", StandardBasicTypes.STRING);
            sqlQuery.addScalar("filetype", StandardBasicTypes.STRING);
            sqlQuery.addScalar("fileext", StandardBasicTypes.STRING);
            sqlQuery.addScalar("printable", StandardBasicTypes.STRING);
            sqlQuery.addScalar("submittedBy", StandardBasicTypes.STRING);
            sqlQuery.addScalar("comment", StandardBasicTypes.STRING);
            sqlQuery.addScalar("externalSource", StandardBasicTypes.STRING);
            sqlQuery.addScalar("type", StandardBasicTypes.STRING);
            sqlQuery.addScalar("id", StandardBasicTypes.LONG);
            sqlQuery.addScalar("createdBy", StandardBasicTypes.LONG);
            sqlQuery.addScalar("modifiedBy", StandardBasicTypes.LONG);
            sqlQuery.addScalar("createdDt", StandardBasicTypes.DATE);
            sqlQuery.addScalar("modifiedDt", StandardBasicTypes.DATE);
            sqlQuery.addScalar("recordVersion", StandardBasicTypes.INTEGER);
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
