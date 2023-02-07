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

import com.mckesson.eig.roi.utils.SecureStringAccessor;
import com.mckesson.eig.utility.util.StringUtilities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceAndLetterOutputProperties complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceAndLetterOutputProperties">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="queuePassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="forInvoice" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="forLetter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="forRelease" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="letterId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="releaseId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceAndLetterOutputProperties", propOrder = {
    "_queuePassword",
    "_outputMethod",
    "_forInvoice",
    "_forLetter",
    "_forRelease",
    "_invoiceId",
    "_letterId",
    "_releaseId"
})
public class InvoiceAndLetterOutputProperties 
implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="queuePassword", required = true)
    private String _queuePassword;
    
    @XmlElement(name="outputMethod", required = true)
    private String _outputMethod;
    
    @XmlElement(name="forInvoice")
    private boolean _forInvoice;
    
    @XmlElement(name="forLetter")
    private boolean _forLetter;
    
    @XmlElement(name="forRelease")
    private boolean _forRelease;
    
    @XmlElement(name="invoiceId")
    private long _invoiceId;
    
    @XmlElement(name="letterId")
    private long _letterId;
    
    @XmlElement(name="releaseId")
    private long _releaseId;
    
    
    
    public String getQueuePassword() {
        if (_queuePassword == null) {
            return null;
        }

      /*  StringBuilder builder = new StringBuilder();
       _queuePassword.DoHylandAccess((chars, tempStr) -> {
            builder.append(chars);
        });       
        
        return builder.toString();*/
        
        return _queuePassword;
    }
    public void setQueuePassword(String queuePassword) {
        queuePassword = StringUtilities.safe(queuePassword);
        /* _queuePassword = new SecureStringAccessor(queuePassword.toCharArray());*/
        
        _queuePassword = queuePassword;
    }
    
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
                        .toString();
    }

}
