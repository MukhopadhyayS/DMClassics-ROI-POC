package com.mckesson.eig.workflow.process.api;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-Jan-2009 11:37:12 AM
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ActionHandlerAttribute", namespace = EIGConstants.TYPE_NS_V1)
public class ActionHandlerAttribute implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private static final long serialVersionUID = 3342495171605669818L;
    
    private String _actionHandlerName;
    private String _attributeName;
    private String _attributeDefaultValue;
    private String _attributeType;
    private Date _createdTS;
    private Date _updatedTS;

	public ActionHandlerAttribute() {
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
     * This method is used to retrieve the _actionHandler Name value.
     * @return _actionHandlerName
     */
   public String getActionHandlerName() {
        return _actionHandlerName;
    }
    
   /**
    * This method is used to set the _actionHandler Name value.
    * @param actionHandlerName
    */
    @XmlElement(name = "actionHandlerName")
    public void setActionHandlerName(String actionHandlerName) {
        _actionHandlerName = actionHandlerName;
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
     * This method is used to retrieve the attribute default value.
     * @return _attributeDefaultValue
     */
    public String getAttributeDefaultValue() {
        return _attributeDefaultValue;
    }
    
    /**
     * This method is used to set the attribute default value.
     * @param attributeDefaultValue
     */
    @XmlElement(name = "attributeDefaultValue")
    public void setAttributeDefaultValue(String attributeDefaultValue) {
        _attributeDefaultValue = attributeDefaultValue;
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
    /**
     * This method returns a string representation of the ActionHandlerAttribute objects.
     * 
     * @return strBuff 
     */
    public String toString() {
        StringBuffer theAttribute = new StringBuffer();
        theAttribute.append("ActionHandlerAttribute[");  
        theAttribute.append("actionHandlerName="  + _actionHandlerName 
                + ", attributeName="  + _attributeName 
                + ", attributeDefaultValue="  + _attributeDefaultValue 
                + ", attributeType=" + _attributeType 
                + ", updatedTS=" 
                + (_updatedTS == null ? "NULL" : DateUtilities.formatISO8601(_updatedTS))
                + ", createdTS=" 
                + (_createdTS == null ? "NULL" : DateUtilities.formatISO8601(_createdTS))
                + "]");
        return theAttribute.toString();
   }
}
