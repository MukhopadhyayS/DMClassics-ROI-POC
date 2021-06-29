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

import com.mckesson.eig.roi.admin.model.PaymentMethod;
import com.mckesson.eig.roi.admin.model.PaymentMethodList;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;


/**
 * @author OFS
 * @date   Mar 16, 2009
 * @since  HPF 13.1 [ROI]; Apr 16, 2008
 */
public class PaymentMethodDAOImpl
extends ROIDAOImpl
implements PaymentMethodDAO {

    private static final Log LOG = LogFactory.getLogger(PaymentMethodDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    private static final int PAYMENTMETHODID        = 0;
    private static final int PAYMENTMETHODNAME      = 1;
    private static final int PAYMENTMETHODDESC      = 2;
    private static final int PAYMENTMETHODISDISP    = 3;

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO
     * #createPaymentMethod(com.mckesson.eig.roi.admin.model.PaymentMethod)
     */
    public long createPaymentMethod(PaymentMethod paymentMethod) {

        final String logSM = "createPaymentMethod(paymentMethod)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentMethod.getName());
        }

        long id = toPlong((Long) create(paymentMethod));

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Created Id :" + id);
        }
        return id;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO#retrievePaymentMethod(long)
     */
    public PaymentMethod retrievePaymentMethod(long paymentMethodId) {

        final String logSM = "retrievePaymentMethod(paymentMethodId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Payment Method Id :" + paymentMethodId);
        }

        PaymentMethod paymentMethod = (PaymentMethod) get(PaymentMethod.class, paymentMethodId);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + paymentMethod.getName());
        }
        return paymentMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO#retrieveAllPaymentMethods()
     */
    public PaymentMethodList retrieveAllPaymentMethods() {

        final String logSM = "retrieveAllPaymentMethods()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<PaymentMethod> paymentMethods = (List<PaymentMethod>)getHibernateTemplate().
                                                findByNamedQuery("retrieveAllPaymentMethods");

        PaymentMethodList pml = new PaymentMethodList(paymentMethods);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: No of Records " + paymentMethods.size());
        }
        return pml;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO#deletePaymentMethod(long)
     */
    public PaymentMethod deletePaymentMethod(long paymentMethodId) {

        final String logSM = "deletePaymentMethod(paymentMethodId)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start: Id : " + paymentMethodId);
        }

        PaymentMethod paymentMethod = retrievePaymentMethod(paymentMethodId);
        delete(paymentMethod);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return paymentMethod;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO
     * #updatePaymentMethod(com.mckesson.eig.roi.admin.model.PaymentMethod,
     *                      com.mckesson.eig.roi.admin.model.PaymentMethod)
     */
    public PaymentMethod updatePaymentMethod(PaymentMethod paymentMethod,
                                             PaymentMethod originalPaymentMethod) {

        final String logSM = "updatePaymentMethod(paymentMethod, originalPaymentMethod)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentMethod);
        }

        paymentMethod.copyFrom(originalPaymentMethod);
        PaymentMethod pm = (PaymentMethod) merge(paymentMethod);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End: Updated Payment Method :" + pm);
        }
        return pm;
    }

    /**
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO#getPaymentMethodByName(java.lang.String)
     */
    public PaymentMethod getPaymentMethodByName(String paymentMethodName) {

        final String logSM = "getPaymentMethodByName(paymentMethodName)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + paymentMethodName);
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<PaymentMethod> paymentMethods = (List<PaymentMethod>) getHibernateTemplate().
            findByNamedQuery("getPaymentMethodByName", paymentMethodName);

        PaymentMethod pMethod = null;
        if (paymentMethods.size() > 0) {
            pMethod = paymentMethods.get(0);
        }

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return pMethod;
    }

    /**
     *
     * @see com.mckesson.eig.roi.admin.dao.PaymentMethodDAO#retrieveAllPaymentMethodNames()
     */
    public PaymentMethodList retrieveAllPaymentMethodNames() {

        final String logSM = "retrieveAllPaymentMethodNames()";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        @SuppressWarnings("unchecked") // not supported by 3rdParty API
        List<Object[]> payments = (List<Object[]>) getHibernateTemplate()
                                    .findByNamedQuery("retrieveAllPaymentMethodNames");

        List<PaymentMethod> paymentList = new ArrayList<PaymentMethod>();
        for (Object[] values : payments) {

            PaymentMethod feeType = new PaymentMethod();
            feeType.setId(toPlong(((Long) values[PAYMENTMETHODID]).longValue()));
            feeType.setName((String) values[PAYMENTMETHODNAME]);
            feeType.setDesc((String) values[PAYMENTMETHODDESC]);
            feeType.setIsDisplay(toPboolean((Boolean) values[PAYMENTMETHODISDISP]));

            paymentList.add(feeType);
        }

        PaymentMethodList list = new PaymentMethodList(paymentList);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:" + payments.size());
        }
        return list;
    }
}
