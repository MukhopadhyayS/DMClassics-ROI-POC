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
package com.mckesson.eig.roi.billing.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rajeshkumarg
 * @date   Oct 21, 2011
 * @since  HPF 15.1 [ROI]; Oct 21, 2011
 */
public class InvoiceDetails
implements Serializable {

    private static final long serialVersionUID = -871163821294006609L;

    private Date _resendDateTime;
    private long _invoiceNumber;
    private String _templateUsed;
    private long _templateId;
    private String _resendBy;
    private double _invoiceAmount;
    private String _queuePassword;

    public Date getResendDateTime() { return _resendDateTime; }
    public void setResendDateTime(Date resendDateTime) { _resendDateTime = resendDateTime; }

    public long getInvoiceNumber() { return _invoiceNumber; }
    public void setInvoiceNumber(long invoiceNumber) { _invoiceNumber = invoiceNumber; }

    public String getTemplateUsed() { return _templateUsed; }
    public void setTemplateUsed(String templateUsed) { _templateUsed = templateUsed; }

    public String getResendBy() { return _resendBy; }
    public void setResendBy(String resendBy) {  _resendBy = resendBy; }

    public double getInvoiceAmount() { return _invoiceAmount; }
    public void setInvoiceAmount(double invoiceAmount) { _invoiceAmount = invoiceAmount; }

    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }

    public void setTemplateId(long templateId) { _templateId = templateId; }
    public long getTemplateId() { return _templateId; }
}
