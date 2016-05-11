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

import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author OFS
 * @date Sep 25, 2012
 * @since Sep 25, 2012
 *
 */
public class InvoiceAndLetterOutputProperties 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String _queuePassword;
    private String _outputMethod;
    private boolean _forInvoice;
    private boolean _forLetter;
    private boolean _forRelease;
    private long _invoiceId;
    private long _letterId;
    private long _releaseId;
    
    public String getQueuePassword() { return _queuePassword; }
    public void setQueuePassword(String queuePassword) { _queuePassword = queuePassword; }
    
    public String getOutputMethod() { return _outputMethod; }
    public void setOutputMethod(String outputMethod) { _outputMethod = outputMethod; }
    
    public boolean isForInvoice() { return _forInvoice; }
    public void setForInvoice(boolean forInvoice) { _forInvoice = forInvoice; }
    
    public boolean isForLetter() { return _forLetter; }
    public void setForLetter(boolean forLetter) { _forLetter = forLetter; }
    
    public long getInvoiceId() { return _invoiceId; }
    public void setInvoiceId(long invoiceId) { _invoiceId = invoiceId; }
    
    public long getLetterId() { return _letterId; }
    public void setLetterId(long letterId) { _letterId = letterId; }
    
    public boolean isForRelease() { return _forRelease; }
    public void setForRelease(boolean forRelease) { _forRelease = forRelease; }
    
    public long getReleaseId() { return _releaseId; }
    public void setReleaseId(long releaseId) { _releaseId = releaseId; }
    
    @Override
    public String toString() {
        return new StringBuffer()
                        .append("InvoiceId:")
                        .append(_invoiceId)
                        .append(", LetterId:")
                        .append(_letterId)
                        .append(", ReleaseId:")
                        .append(_releaseId)
                        .append(", OutpuMethod")
                        .append(_outputMethod)
                        .append(", Has QueuePassword")
                        .append(StringUtilities.isEmpty(_queuePassword))
                        .toString();
    }

}
