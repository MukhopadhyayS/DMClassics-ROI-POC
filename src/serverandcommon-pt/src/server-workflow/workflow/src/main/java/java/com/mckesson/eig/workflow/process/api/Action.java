package com.mckesson.eig.workflow.process.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.mckesson.eig.wsfw.EIGConstants;

/**
 * @author McKesson Corporation
 * @version 1.0
 * @created 23-Jan-2009 11:34:44 AM
 */

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "Action", namespace = EIGConstants.TYPE_NS_V1)
public class Action {


    private long _actionId;
    
	public Action() {

	}
    /**
     * This method is used to get the action Id.
     *
     * @return actionId
     */
    public long getActionId() {
        return _actionId;
    }

    /**
     * This method is used to set the action ID.
     *
     * @param actionID
     */
    @XmlElement(name = "actionId")
    public void setActionId(long actionId) {
        _actionId = actionId;
    }
	
	public void finalize() throws Throwable {

	}

}


