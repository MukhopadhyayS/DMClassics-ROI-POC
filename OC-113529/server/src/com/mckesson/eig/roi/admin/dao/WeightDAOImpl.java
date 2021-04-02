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

import java.util.List;

import com.mckesson.eig.roi.admin.model.Weight;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.dm.core.common.logging.OCLogger;

/**
 * @author OFS
 * @date Sep 15, 2008
 * @since HPF 13.1 [ROI]; Mar 26, 2008
 */
public class WeightDAOImpl
extends ROIDAOImpl
implements WeightDAO {

    private static final OCLogger LOG = new OCLogger(WeightDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     * @see com.mckesson.eig.roi.admin.dao.WeightDAO#retrieveWeight()
     */
    public Weight retrieveWeight() {

        final String logSM = "retrieveWeight()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Weight> weights = getHibernateTemplate().findByNamedQuery("getWeight");

        Weight weight = null;
        if (weights.size() > 0) {
            weight = weights.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + weight);
        }
        return weight;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.WeightDAO
     * #updateWeight(com.mckesson.eig.roi.admin.model.Weight)
     */
    public Weight updateWeight(Weight wt) {

        final String logSM = "updateWeight(wt)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        Weight updatedWt = (Weight) merge(wt);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return updatedWt;
    }
}
