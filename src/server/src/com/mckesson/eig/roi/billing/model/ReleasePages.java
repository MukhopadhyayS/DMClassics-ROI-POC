/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright (c) 2013 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ROIPages complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ROIPages">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pageSeq" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="selfPayEncounter" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ROIPages", propOrder = {
    "_pageSeq",
    "_selfPayEncounter"
})
public class ReleasePages
implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @XmlElement(name="pageSeq")
    private long _pageSeq;
    
    @XmlElement(name="selfPayEncounter")
    private boolean _selfPayEncounter;
    
    

    public long getPageSeq() { return _pageSeq; }
    public void setPageSeq(long pageSeq) { _pageSeq = pageSeq; }

    public boolean isSelfPayEncounter() { return _selfPayEncounter; }
    public void setSelfPayEncounter(boolean selfPayEncounter) {
        _selfPayEncounter = selfPayEncounter;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                    .append("PageSeq: ")
                    .append(getPageSeq())
                    .append(", IsSelfPayEncounter: ")
                    .append(isSelfPayEncounter())
                    .toString();
    }

}
