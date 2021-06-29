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

package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author OFS
 * @date   Sep 08, 2011
 * @since  HPF 15.1 [ROI]
 */
public class SearchPastDueInvoiceResult
implements Serializable {

    private static final long serialVersionUID = 8301182555899423931L;
    private boolean _maxCountExceeded;
    private List<PastDueInvoice> _pastDueInvoices;

    public List<PastDueInvoice> getPastDueInvoices() { return _pastDueInvoices; }
    public void setPastDueInvoices(List<PastDueInvoice> pastDueInvoices) {
        _pastDueInvoices = pastDueInvoices;
    }

    public boolean getMaxCountExceeded() { return _maxCountExceeded; }
    public void setMaxCountExceeded(boolean maxCountExceeded) {
        _maxCountExceeded = maxCountExceeded;
    }

    @Override
    public String toString() {

        return new StringBuffer().append(" Size of SearchPastDueInvoice List:")
                                 .append(_pastDueInvoices.size())
                                 .toString();
    }
}
