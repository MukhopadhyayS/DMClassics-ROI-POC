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


import java.util.ArrayList;
import java.util.List;

import com.mckesson.eig.roi.admin.model.DeliveryMethod;
import com.mckesson.eig.roi.admin.model.DeliveryMethodList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]
 */
public class DeliveryMethodDAOImpl
extends ROIDAOImpl
implements DeliveryMethodDAO {

    private static final Log LOG = LogFactory.getLogger(DeliveryMethodDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int DELIVERYMETHODID        = 0;
    private static final int DELIVERYMETHODNAME      = 1;
    private static final int DELIVERYMETHODDESC      = 2;
    private static final int DELIVERYMETHODURL       = 3;
    private static final int DELIVERYMETHODISDEFAULT = 4;



    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO
     * #createDeliveryMethod(com.mckesson.eig.roi.admin.model.DeliveryMethod)
     */
    public long createDeliveryMethod(DeliveryMethod deliveryMethod) {

        final String logSM = "createDeliveryMethod(deliveryMethod)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethod);
        }

        long id = toPlong((Long) create(deliveryMethod));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Created Delivery Method : " + deliveryMethod);
        }
        return id;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO#retrieveDeliveryMethod(long)
     */
    public DeliveryMethod retrieveDeliveryMethod(long deliveryMethodId) {

        final String logSM = "retrieveDeliveryMethod(deliveryMethodId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethodId);
        }

        DeliveryMethod deliveryMethod = (DeliveryMethod)
                                         get(DeliveryMethod.class, deliveryMethodId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Delivery Method " + deliveryMethod);
        }
        return deliveryMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO#retrieveAllDeliveryMethods()
     */
    public DeliveryMethodList retrieveAllDeliveryMethods() {

        final String logSM = "retrieveAllDeliveryMethods()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<DeliveryMethod> deliveryMethod = (List<DeliveryMethod>) getHibernateTemplate().
                                                findByNamedQuery("retrieveAllDeliveryMethods");

        DeliveryMethodList dml = new DeliveryMethodList(deliveryMethod);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + "No of Records :" + deliveryMethod.size());
        }
        return dml;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO
     * #updateDeliveryMethod(com.mckesson.eig.roi.admin.model.DeliveryMethod,
     *                       com.mckesson.eig.roi.admin.model.DeliveryMethod)
     */
    public DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod,
                                               DeliveryMethod originalDeliveryMethod) {

        final String logSM = "updateDeliveryMethod(deliveryMethod, originalDeliveryMethod)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethod.getName());
        }

        deliveryMethod.copyFrom(originalDeliveryMethod);
        DeliveryMethod dm = (DeliveryMethod) merge(deliveryMethod);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Updated Delivery Method :" + dm);
        }
        return dm;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO
     * #getDeliveryMethodByName(java.lang.String)
     */
    public DeliveryMethod getDeliveryMethodByName(String deliveryMethodName) {

        final String logSM = "getDeliveryMethodByName(deliveryMethodName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethodName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List <DeliveryMethod> deliveryMethods = (List<DeliveryMethod>) getHibernateTemplate().
                                                findByNamedQuery("getDeliveryMethodByName",
                                                                 deliveryMethodName);

        DeliveryMethod dMethod = null;
        if (deliveryMethods.size() > 0) {
            dMethod = deliveryMethods.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return dMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO#deleteDeliveryMethod(long)
     */
    public DeliveryMethod deleteDeliveryMethod(long deliveryMethodId) {

        final String logSM = "deleteDeliveryMethod(deliveryMethodId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + deliveryMethodId);
        }

        DeliveryMethod deliveryMethod = retrieveDeliveryMethod(deliveryMethodId);
        delete(deliveryMethod);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return deliveryMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO#getDefaultDeliveryMethod()
     */
    public DeliveryMethod getDefaultDeliveryMethod() {

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<DeliveryMethod> deliveryMethod = (List<DeliveryMethod>) getHibernateTemplate().
                                              findByNamedQuery("getIsDefaultAsTrue");
        DeliveryMethod dm = null;
        if (deliveryMethod.size() > 0) {
             dm = deliveryMethod.get(0);
        }
        return dm;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO
     * #clearDefaultDeliveryMethod(com.mckesson.eig.roi.admin.model.DeliveryMethod)
     */
    public void clearDefaultDeliveryMethod(DeliveryMethod dm) {

        dm.setIsDefault(false);
        merge(dm);
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.DeliveryMethodDAO#retrieveAllDeliveryMethodNames()
     */
    public DeliveryMethodList retrieveAllDeliveryMethodNames() {

        final String logSM = "retrieveAllDeliveryMethodNames()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked")//not supported by 3party API
        List<Object[]> dMethods = (List<Object[]>) getHibernateTemplate()
                                .findByNamedQuery("retrieveAllDeliveryMethodNames");

        List<DeliveryMethod> dmList = new ArrayList<DeliveryMethod>();
        for (Object[] values : dMethods) {

            DeliveryMethod dm = new DeliveryMethod();
            dm.setId(toPlong(((Long) values[DELIVERYMETHODID]).longValue()));
            dm.setName((String) values[DELIVERYMETHODNAME]);
            dm.setDesc((String) values[DELIVERYMETHODDESC]);
            dm.setUrl(((String) values[DELIVERYMETHODURL]));
            dm.setIsDefault(toPboolean(((Boolean) values[DELIVERYMETHODISDEFAULT])));

            dmList.add(dm);
        }

        DeliveryMethodList list = new DeliveryMethodList(dmList);
        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return list;
    }
}
