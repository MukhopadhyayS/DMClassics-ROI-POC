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
import com.mckesson.eig.roi.base.dao.ROIDAO;

/**
 * @author OFS
 * @date   Jul 5, 2011
 * @since  HPF 15.2 [ROI]; Jul 5, 2011
 */
public interface InvoiceDueDaysDAO
extends ROIDAO {

    /**
     * This method fetches the existing InvoiceDate
     *
     * @return existing InvoiceDate.
     */
     InvoiceDueDays retrieveInvoiceDueDays(long id);

    /**
     * This method updates the InvoiceDueDates Details
     *
     * @param modified invoiceDue to be updated
     * @return updated InvoiceDate
     */
     InvoiceDueDays updateInvoiceDue(InvoiceDueDays invoiceDue);
}
