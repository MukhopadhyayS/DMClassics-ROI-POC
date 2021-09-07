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
@XmlType(name = "ActionHandler", namespace = EIGConstants.TYPE_NS_V1)
public class ActionHandler implements Serializable {

    /**
     * Serial Version ID for this Serializable.
     */
    private  static final long serialVersionUID = 3342495171605669818L;
    
    private String _actionHandlerName;
    private char _isActive;
    private String _implementationClass;
    private ActionHandlerAttributeList _actionHandlerAttributeList;
    private Date _createdTS;
    private Date _updatedTS;
 
	public ActionHandler() {
	}
    /**
     * This method is used to retrieve the actionHandlerAttributeList value.
     * @return _actionHandlerAttributeList
     */
    public ActionHandlerAttributeList getActionHandlerAttributeList() {
        return _actionHandlerAttributeList;
    }

    /**
     * This method is used to set the actionHandlerAttributeList value.
     * @param actionHandlerAttributeList
     */
    @XmlElement(name = "actionHandlerAttributeList", type = ActionHandlerAttributeList.class)
    public void setActionHandlerAttributeList(
            ActionHandlerAttributeList actionHandlerAttributeList) {
        _actionHandlerAttributeList = actionHandlerAttributeList;
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
     * This method is used to retrieve the _isActive value.
     * @return _isActive
     */
   public char getIsActive() {
        return _isActive;
    }
    
   /**
    * This method is used to set the isActive value.
    * @param isActive
    */
    @XmlElement(name = "isActive")
    public void setIsActive(char isActive) {
        _isActive = isActive;
    }
     /**
     * This method is used to retrieve the implementationClass value.
     * @return _implementationClass
     */
    public String getImplementationClass() {
        return _implementationClass;
    }
    
    /**
     * This method is used to set the implementationClass value.
     * @param implementationClass
     */
    @XmlElement(name = "implementationClass")
    public void setImplementationClass(String implementationClass) {
        _implementationClass = implementationClass;
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
     * This method returns a string representation of the ActionHandler.
     * 
     * @return strBuff 
     */
    public String toString() {
        StringBuffer theAttribute = new StringBuffer();
        theAttribute.append("ActionHandler[");  
        theAttribute.append("actionHandlerName=" + _actionHandlerName 
                + ", isActive="  + _isActive 
                + ", implementationClass="  + _implementationClass 
                + ", updatedTS=" 
                + (_updatedTS == null ? "NULL" : DateUtilities.formatISO8601(_updatedTS))
                + ", createdTS=" 
                + (_createdTS == null ? "NULL" : DateUtilities.formatISO8601(_createdTS))
                + ", actionHandlerAttributeList=" + _actionHandlerAttributeList.toString() 
                + "]");
        return theAttribute.toString();
   }
}
