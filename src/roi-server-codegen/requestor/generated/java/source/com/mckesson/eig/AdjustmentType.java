
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlEnum;
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

    CUSTOMER_GOODWILL_ADJUSTMENT,
    BAD_DEBT_ADJUSTMENT,
    BILLING_ADJUSTMENT;

    public String value() {
        return name();
    }

    public static AdjustmentType fromValue(String v) {
        return valueOf(v);
    }

}
