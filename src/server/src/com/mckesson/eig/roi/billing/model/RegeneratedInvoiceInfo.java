/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright � 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import java.util.Map;

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;

/**
 * @author rajeshkumarg
 * @date   Oct 24, 2011
 * @since  HPF 15.1 [ROI]; Oct 24, 2011
 */
public class RegeneratedInvoiceInfo 
implements Serializable {

    private static final long serialVersionUID = -6702127855836943295L;
    
    private long _id;
    private String _outputMethod;
    private SecureStringAccessor _queuePassword;
    private boolean _isInvoice;
    private Map<String, String> _properties;
    
    public void setId(long id) { _id = id; }
    public long getId() { return _id; }
    
    public String getOutputMethod() {
        return _outputMethod;
    }
    public void setOutputMethod(String outputMethod) {
        _outputMethod = outputMethod;
    }
    public String getQueuePassword() {
        if (_queuePassword == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });
        return builder.toString();
    }
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());
    }
    
    public boolean isInvoice() {
        return _isInvoice;
    }
    public void setInvoice(boolean isInvoice) {
        _isInvoice = isInvoice;
    }
    public void setProperties(Map<String, String> properties) { _properties = properties; }
    public Map<String, String> getProperties() { return _properties; }
    
}
