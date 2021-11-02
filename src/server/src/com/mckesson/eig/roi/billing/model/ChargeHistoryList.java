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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargeHistoryList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeHistoryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chargeHistories" type="{urn:eig.mckesson.com}ChargeHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeHistoryList", propOrder = {
    "_chargeHistories"
})
public class ChargeHistoryList 
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="chargeHistories")
    private List<ChargeHistory> _chargeHistories;
    
    public ChargeHistoryList() { }
    public ChargeHistoryList(List<ChargeHistory> chargeHistory) {
        setChargeHistories(chargeHistory);
    }
    
    public List<ChargeHistory> getChargeHistories() { return _chargeHistories; }
    public void setChargeHistories(List<ChargeHistory> chargeHistories) {
        _chargeHistories = chargeHistories;
    }

}
