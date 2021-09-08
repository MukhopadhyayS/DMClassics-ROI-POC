package com.mckesson.eig.workflow.security.api;
import java.util.ArrayList;
import java.util.List;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-Jan-2009 11:37:12 AM
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SecurityProperties", namespace = EIGConstants.TYPE_NS_V1)
public class SecurityProperties {

    private int _autoLogOffTime;
    private int _autoLogOffWaitTime;
    private List<SecurityRight> _securityRights;

    public SecurityProperties() {
        _securityRights = new ArrayList<SecurityRight>();
    }

    public SecurityProperties(List<SecurityRight> value) {
        _securityRights = value;
    }

    public List<SecurityRight> getSecurityRights() {
        return _securityRights;
    }

    @XmlElement(name = "securityRights", type = SecurityRight.class)
    public void setSecurityRights(List<SecurityRight> value) {
        _securityRights = value;
    }
    
    public int getAutoLogOffTime() {
        return _autoLogOffTime;
    }

    @XmlElement(name = "autoLogOffTime")
    public void setAutoLogOffTime(int value) {
        _autoLogOffTime = value;
    }
    
    public int getAutoLogOffWaitTime() {
        return _autoLogOffWaitTime;
    }

    @XmlElement(name = "autoLogOffWaitTime")
    public void setAutoLogOffWaitTime(int value) {
        _autoLogOffWaitTime = value;
    }
}
