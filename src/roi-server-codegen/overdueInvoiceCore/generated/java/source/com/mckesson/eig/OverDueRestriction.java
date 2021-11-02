
package com.mckesson.eig;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OverDueRestriction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OverDueRestriction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GREATER"/>
 *     &lt;enumeration value="BETWEEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OverDueRestriction")
@XmlEnum
public enum OverDueRestriction {

    GREATER,
    BETWEEN;

    public String value() {
        return name();
    }

    public static OverDueRestriction fromValue(String v) {
        return valueOf(v);
    }

}
