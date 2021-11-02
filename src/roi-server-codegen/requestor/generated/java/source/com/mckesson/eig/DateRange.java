
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DateRange.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DateRange">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DAYS_30"/>
 *     &lt;enumeration value="DAYS_60"/>
 *     &lt;enumeration value="DAYS_90"/>
 *     &lt;enumeration value="DAYS_120"/>
 *     &lt;enumeration value="YEAR_TO_DATE"/>
 *     &lt;enumeration value="ALL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DateRange")
@XmlEnum
public enum DateRange {

    DAYS_30,
    DAYS_60,
    DAYS_90,
    DAYS_120,
    YEAR_TO_DATE,
    ALL;

    public String value() {
        return name();
    }

    public static DateRange fromValue(String v) {
        return valueOf(v);
    }

}
