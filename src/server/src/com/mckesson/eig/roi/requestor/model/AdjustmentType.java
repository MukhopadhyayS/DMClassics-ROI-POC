/*
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2012 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement.
* This material contains confidential, proprietary and trade secret information of
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws.
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line!
*/

package com.mckesson.eig.roi.requestor.model;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdjustmentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AdjustmentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CUSTOMER_GOODWILL_ADJUSTMENT"/>
 *     &lt;enumeration value="BAD_DEBT_ADJUSTMENT"/>
 *     &lt;enumeration value="BILLING_ADJUSTMENT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AdjustmentType")
@XmlEnum
public enum AdjustmentType {

    CUSTOMER_GOODWILL_ADJUSTMENT("Customer Goodwill"),
    BAD_DEBT_ADJUSTMENT("Bad Debt"),
    BILLING_ADJUSTMENT("Billing");

    @XmlTransient
    private String _type;
    private AdjustmentType(String type) { _type = type; }

    @Override
    public String toString() {
        return _type;
    }

}
