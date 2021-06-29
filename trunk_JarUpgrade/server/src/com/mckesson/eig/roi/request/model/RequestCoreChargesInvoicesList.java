/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.request.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Karthik Easwaran
 * @date   Oct 12, 2012
 * @since  Oct 12, 2012
 */
public class RequestCoreChargesInvoicesList
implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<RequestCoreChargesInvoice> _invoicesList;
    private boolean _isInvoiced;

    public RequestCoreChargesInvoicesList() { }
    public RequestCoreChargesInvoicesList(List<RequestCoreChargesInvoice> invoicesList) {
        _invoicesList = invoicesList;
    }

    public List<RequestCoreChargesInvoice> getInvoicesList() { return _invoicesList; }
    public void setInvoicesList(List<RequestCoreChargesInvoice> invoicesList) {
        _invoicesList = invoicesList;
    }

    public boolean getIsInvoiced() { return _isInvoiced; }
    public void setIsInvoiced(boolean isInvoiced) { _isInvoiced = isInvoiced; }

}
