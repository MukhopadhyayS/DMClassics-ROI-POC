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

/**
 * @author rajeshkumarg
 * @date   Oct 21, 2011
 * @since  HPF 15.1 [ROI]; Oct 21, 2011
 */
public class PastInvoice
implements Serializable {

    private static final long serialVersionUID = 7442357003367239848L;

    private long _invoiceId;
    private String _createdDate;
    private Double _amount;

    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }

    public String getCreatedDate() { return _createdDate; }
    public void setCreatedDate(String createdDate) { _createdDate = createdDate; }

    public Double getAmount() { return _amount; }
    public void setAmount(Double amount) { _amount = amount; }

}
