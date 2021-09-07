package com.mckesson.eig.workflow.process.api;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.util.DateUtilities;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-Jan-2009 11:37:12 AM
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessAttribute")
public class ProcessAttribute implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 3342495171605669818L;
    
    private long _processId;
    private long _versionId;
    private String _attributeName;
    private String _attributeValue;
    private String _attributeType;
    private Date _createdTS;
    private Date _updatedTS;

    public ProcessAttribute() {
	    super();
	}

    /**
     * This method is used to retrieve the updatedTS value.
     * @return _updatedTS
     */
    public Date getUpdatedTS() {
        return _updatedTS;
    }

    /**
     * This method is used to set the updatedTS value.
     * @param updatedTS
     */
    @XmlElement(name = "updatedTS")
    public void setUpdatedTS(Date updatedTS) {
        _updatedTS = updatedTS;
    }

    /**
     * This method is used to retrieve the createdTS value.
     * @return _createdTS
     */
    public Date getCreatedTS() {
        return _createdTS;
    }

    /**
     * This method is used to set the createdTS value.
     * @param createdTS
     */
    @XmlElement(name = "createdTS")
    public void setCreatedTS(Date createdTS) {
        _createdTS = createdTS;
    }
    
    /**
     * This method is used to retrieve the process Id value.
     * @return _processId
     */
   public long getProcessId() {
        return _processId;
    }

   /**
    * This method is used to set the process Id value.
    * @param processId
    */
    @XmlElement(name = "processId")
    public void setProcessId(long processId) {
        _processId = processId;
    }

    /**
     * This method is used to retrieve the attribute name value.
     * @return _attributeName
     */
    public String getAttributeName() {
        return _attributeName;
    }

    /**
     * This method is used to set the attribute name value.
     * @param attributeName
     */
    @XmlElement(name = "attributeName")
    public void setAttributeName(String attributeName) {
        _attributeName = attributeName;
    }
    /**
     * This method is used to retrieve the attribute value.
     * @return _attributeValue
     */
    public String getAttributeValue() {
        return _attributeValue;
    }
    
    /**
     * This method is used to set the attribute value.
     * @param attributeValue
     */
    @XmlElement(name = "attributeValue")
    public void setAttributeValue(String attributeValue) {
        _attributeValue = attributeValue;
    }  
    /**
     * This method is used to retrieve the attribute Type value.
     * @return _attributeType
     */
    public String getAttributeType() {
        return _attributeType;
    }
    
    /**
     * This method is used to set the attribute Type value.
     * @param attributeType
     */
    @XmlElement(name = "attributeType")
    public void setAttributeType(String attributeType) {
        _attributeType = attributeType;
    }
    
    public long getVersionId() {
        return _versionId;
    }

    @XmlElement(name = "versionId")
    public void setVersionId(long id) {
        _versionId = id;
    }

    /**
     * This method returns a string representation of the ProcessVersion List that contains
     * ProcessVersion objects.
     * 
     * @return strBuff 
     */
    public String toString() {
        StringBuffer theAttribute = new StringBuffer("Process_Attribute[");
        theAttribute.append("processId=" + _processId 
                + ", attributeName="  + _attributeName 
                + ", attributeValue="  + _attributeValue 
                + ", attributeType=" + _attributeType 
                + ", updatedTS=" 
                + (_updatedTS == null ? "NULL" : DateUtilities.formatISO8601(_updatedTS))
                + ", createdTS=" 
                + (_createdTS == null ? "NULL" : DateUtilities.formatISO8601(_createdTS))
                + "]");
        return theAttribute.toString();
   }
}
