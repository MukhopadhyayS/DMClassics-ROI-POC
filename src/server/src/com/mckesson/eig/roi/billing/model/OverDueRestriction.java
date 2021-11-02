package com.mckesson.eig.roi.billing.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlTransient;
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

    GREATER("greater"), BETWEEN("between");

    @XmlTransient
    private final String _overdueRestriction;

    private OverDueRestriction(String overdueRestriction) {
        _overdueRestriction = overdueRestriction;
    }

    @Override
    public String toString() {
        return _overdueRestriction;
    }
    
    
}
