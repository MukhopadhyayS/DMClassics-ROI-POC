package com.mckesson.eig.roi.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import com.mckesson.eig.roi.admin.model.Country;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.roi.request.dao.RequestCoreChargesDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;

public class CountryCodeConfigurationDAOImpl 
extends ROIDAOImpl 
implements CountryCodeConfigurationDAO {
    
    private static final OCLogger LOG = new OCLogger(RequestCoreChargesDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();
    
    /**
     * This method updates an existing entry in CountryCodeConfig Table.
     * @param country
     * 
     */
    @Override
    public void updateCountryCode(Country country) {
        final String logSM = "updateCountryCode(countryCode)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + country);
        }
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("updateCountryCode").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.setParameter("countryCode", country.getCountryCode(), StringType.INSTANCE);
            query.executeUpdate();
            
        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, 
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION,
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), 
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED,
                                   e.getMessage());
        }
      }
    
    /**
     * This method retrieveAllCountries fetches the master list of countries.
     * @return List<Country>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Country> retrieveAllCountries() {
        final String logSM = "retrieveAllCountries()";
        List<Country> countryList = new ArrayList<Country>();       
        
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }
        
        try {
            Session session = getSession();
            String queryString = session.getNamedQuery("fetchAllCountries").getQueryString();
            NativeQuery query = session.createSQLQuery(queryString);
            query.addScalar("countrySeq", LongType.INSTANCE);
            query.addScalar("countryCode", StringType.INSTANCE);
            query.addScalar("countryName", StringType.INSTANCE);
            query.addScalar("defaultCountry", BooleanType.INSTANCE);          
            
            query.setResultTransformer(Transformers.aliasToBean(Country.class));           
            countryList = (List<Country>) query.list();           

        } catch (DataIntegrityViolationException e) {
            throw new ROIException(e, ROIClientErrorCodes.DATA_INTEGRITY_VIOLATION, e.getMessage());
        } catch (HibernateOptimisticLockingFailureException e) {
            throw new ROIException(e, 
                                   ROIClientErrorCodes.OPTIMISTIC_LOCKING_COLLISION, 
                                   e.getMessage());
        } catch (Throwable e) {
            throw new ROIException(e.getCause(), 
                                   ROIClientErrorCodes.DATABASE_OPERATION_FAILED, 
                                   e.getMessage());
        }
        
        return countryList;
    }
}
