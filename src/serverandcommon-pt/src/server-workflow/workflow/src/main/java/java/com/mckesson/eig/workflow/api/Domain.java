package com.mckesson.eig.workflow.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-Jan-2009 11:37:12 AM
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Domain", namespace = EIGConstants.TYPE_NS_V1)

public class Domain implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 3342495171605669818L;
    
    private long _domainId;
    private String _domainName;
    private String _domainDescription;
    
	public Domain() {
	    super();
	}

    public long getDomainId() {
        return _domainId;
    }
    
    /**
     * 
     * @param newVal
     */
    @XmlElement(name = "domainId")
    public void setDomainId(long value) {
        _domainId = value;
    }
    
    public String getDomainName() {
        return _domainName;
    }
    
    /**
     * 
     * @param newVal
     */
    @XmlElement(name = "domainName")
    public void setDomainName(String value) {
        _domainName = value;
    }

    public String getDomainDescription() {
        return _domainDescription;
    }
    
    /**
     * 
     * @param newVal
     */
    @XmlElement(name = "domainDescription")
    public void setDomainDescription(String value) {
        _domainDescription = value;
    }
    /**
     * This method returns a string representation of the Process List that contains
     * Process objects.
     * 
     * @return strBuff 
     */
    public String toString() {
        StringBuffer theDomain = new StringBuffer();
        theDomain.append("Domain[");  
        theDomain.append("domainId=" + _domainId 
                + ", domainName="  + _domainName 
                + ", domainDescription=" + _domainDescription + "]");
        return theDomain.toString();
   }
}
