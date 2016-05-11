/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2011 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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

import com.mckesson.eig.roi.admin.model.InvoiceDueDays;
import com.mckesson.eig.roi.base.dao.ROIDAOImpl;
import com.mckesson.eig.utility.log.Log;
import com.mckesson.eig.utility.log.LogFactory;

/**
 * @author OFS
 * @date   Jul 5, 2011
 * @since  HPF 15.2 [ROI] Jul 5, 2011
 */
public class InvoiceDueDaysDAOImpl
extends ROIDAOImpl
implements InvoiceDueDaysDAO {

    private static final Log LOG = LogFactory.getLogger(InvoiceDueDaysDAOImpl.class);
    private static final boolean DO_DEBUG = LOG.isDebugEnabled();

    /**
     *
     * This method updates the invoice due dates to the database
     * @param modified InvoiceDate
     * @return updated invoiceDate
     */
    @Override
    public InvoiceDueDays updateInvoiceDue(InvoiceDueDays invoiceDue) {

        final String logSM = "updateInvoiceDueDays(invoiceDue)";
        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:");
        }

        InvoiceDueDays updatedInvoiceDue = (InvoiceDueDays) merge(invoiceDue);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }
        return updatedInvoiceDue;
    }

    /**
     *
     * This method gets the retrieveInvoiceDueDays from the Database
     * @return InvoiceDueDays
     */
         
    public InvoiceDueDays retrieveInvoiceDueDays(long id) {
        
        final String logSM = "retrieveRelease(id)";

        if (DO_DEBUG) {
            LOG.debug(logSM + ">>Start:" + id);
        }

        InvoiceDueDays invoiceDueDays = (InvoiceDueDays) get(InvoiceDueDays.class, id);

        if (DO_DEBUG) {
            LOG.debug(logSM + "<<End:");
        }

        return invoiceDueDays;
        
    }
}
