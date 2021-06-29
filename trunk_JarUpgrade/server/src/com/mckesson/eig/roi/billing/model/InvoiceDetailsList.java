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
 * @author rajeshkumarg
 * @date   Oct 21, 2011
 * @since  HPF 15.1 [ROI]; Oct 21, 2011
 */
public class InvoiceDetailsList 
implements Serializable {
    
    private static final long serialVersionUID = 1241544998046794726L;
    
    private List<InvoiceDetails> _invoiceDetailsList;

    public InvoiceDetailsList() { };
    
    public InvoiceDetailsList(List<InvoiceDetails> invoiceDetailsList) {
        setInvoiceDetailsList(invoiceDetailsList);
    }

    public List<InvoiceDetails> getInvoiceDetailsList() { return _invoiceDetailsList; }
    public void setInvoiceDetailsList(List<InvoiceDetails> invoiceDetailsList) {
        _invoiceDetailsList = invoiceDetailsList;
    }
}
