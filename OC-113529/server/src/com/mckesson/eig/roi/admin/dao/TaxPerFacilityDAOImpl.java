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


import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mckesson.eig.roi.admin.model.TaxPerFacility;
import com.mckesson.eig.roi.admin.model.TaxPerFacilityList;
import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIConstants;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;


/**
 * @author rethinamt
 * @date   Sep 15, 2008
 * @since  HPF 13.1 [ROI]; Mar 26, 2008
 */
public class TaxPerFacilityDAOImpl
extends ROIDAOImpl
implements TaxPerFacilityDAO {

    private static final OCLogger LOG = new OCLogger(TaxPerFacilityDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    @Override
    public long createTaxPerFacility(TaxPerFacility salesTaxPerFacility) {

        final String logSM = "createSalesTaxFacility(salesTaxPerFacility)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + salesTaxPerFacility.getCode());
        }

        long id = toPlong((Long) create(salesTaxPerFacility));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Id:" + id);
        }
        return id;

    }

    @Override
    public TaxPerFacility retrieveTaxPerFacility(
            long salesTaxPerFacilityId) {

        final String logSM = "retrieveSalesTaxFacility(salesTaxPerFacilityId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + salesTaxPerFacilityId);
        }

        TaxPerFacility salesTaxFacility = 
                        (TaxPerFacility) get(TaxPerFacility.class, salesTaxPerFacilityId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Name:" + salesTaxFacility.getCode());
        }
        return salesTaxFacility;
    }

    @Override
    public TaxPerFacilityList retrieveAllTaxPerFacilities() {

        final String logSM = "retrieveAllSalesTaxFacility()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<TaxPerFacility> salesTaxFacilities = (List<TaxPerFacility>) getHibernateTemplate().
                                        findByNamedQuery("retrieveAllSalesTaxFacility");

        Collections.sort(salesTaxFacilities);
        TaxPerFacilityList sl = new TaxPerFacilityList(salesTaxFacilities);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of records:" + sl.getSalesTaxFacilityList().size());
        }
        return sl;
    }


    @Override
    public TaxPerFacility updateTaxPerFacility(
            TaxPerFacility salesTaxFacility, TaxPerFacility old) {

        final String logSM = "updateSalesTaxFacility(mediaType)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + salesTaxFacility);
        }

        salesTaxFacility.copyFrom(old);
        TaxPerFacility uSTF = (TaxPerFacility) merge(salesTaxFacility);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return uSTF;
    }

    @Override
    public TaxPerFacility deleteTaxPerFacility(long salesTaxPerFacilityId) {

        final String logSM = "deleteSalesTaxFacility(salesTaxPerFacilityId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + salesTaxPerFacilityId);
        }

        TaxPerFacility salesTaxFacility = retrieveTaxPerFacility(salesTaxPerFacilityId);
        delete(salesTaxFacility);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return salesTaxFacility;
    }


    @Override
    public TaxPerFacility getSalesTaxFacilityByCode(
            String salesTaxPerFacilityCode) {

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<TaxPerFacility> salesTaxFacility = (List<TaxPerFacility>) getHibernateTemplate().
                                              findByNamedQuery("getSalesTaxFacilityByCode",
                                                      salesTaxPerFacilityCode);
        TaxPerFacility sTF = null;
        if (salesTaxFacility.size() > 0) {
             sTF = salesTaxFacility.get(0);
        }
        return sTF;
    }

    @Override
    public TaxPerFacility getDefaultTaxPerFacility() {

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<TaxPerFacility> salesTaxFacility = (List<TaxPerFacility>) getHibernateTemplate().
                                              findByNamedQuery("getDefaultAsTrue");
        TaxPerFacility sTF = null;
        if (salesTaxFacility.size() > 0) {
             sTF = salesTaxFacility.get(0);
        }
        return sTF;
    }

    @Override
    public void clearDefaultTaxPerFacility(TaxPerFacility defaultTaxFac) {

        defaultTaxFac.setDefault(ROIConstants.N);
        merge(defaultTaxFac);
    }

    @SuppressWarnings("unchecked")
    public List<String> retrieveAllFacilitiesByUser(final String loginId) {

        final String logSM = "retrieveAllFacilitiesByUser(loginId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + loginId);
        }

        List<String> userFacility = null;
        try {
            
            userFacility = (List<String>) getHibernateTemplate()
                    .findByNamedQuery("getFacilitiesByUser", loginId);
        } catch (DataAccessException e) {
            throw new ROIException(ROIClientErrorCodes.DATA_ACCESS_EXCEPTION);
        }
        
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return userFacility;
    }

}
