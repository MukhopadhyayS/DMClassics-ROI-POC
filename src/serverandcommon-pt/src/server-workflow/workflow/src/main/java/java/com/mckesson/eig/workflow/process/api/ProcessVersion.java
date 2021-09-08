/*
 * Copyright 2009-2010 McKesson Corporation and/or one of its subsidiaries. All
 * Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of McKesson
 * copyright and other intellectual property laws. Use, disclosure,
 * reproduction, modification, distribution, or storage in a retrieval system in
 * any form or by any means is prohibited without the prior express written
 * permission of McKesson Information Solutions.
 */
package com.mckesson.eig.workflow.process.api;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import com.mckesson.eig.utility.util.DateUtilities;
import com.mckesson.eig.utility.util.StringUtilities;
import com.mckesson.eig.workflow.api.DomainList;
import com.mckesson.eig.workflow.api.WorkflowException;

/**
 * Model  class represents a workflow process designed and installed in the
 * workflow engine
 * @author McKesson
 * @version 1.0
 * @created 22-Jan-2009 10:06:13 AM
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ProcessVersion")
public class ProcessVersion implements Serializable {

    static final long serialVersionUID = 9035504088669794376L;

    private String _processDescription;
    private String _processName;
    //private String _processGraph;
    private Document _processGraphDocument;
    private String _createdBy;
    private Date _createdTS;
    private String _updatedBy;
    private Date _updatedTS;
    private String _status;
    private String _exceptionEmailAddress;
    private String _lockedBy;
    private Integer _retentionPeriod;
    private Character _notifyExceptions;
    private Boolean _isValid;
    private Date _expirationDate;
    private Integer _maxInstanceDuration;
    private DomainList _domainList;
    private Set<ProcessAttribute> _processAttributes;
    private ProcessAttributeList _processAttributeList;
    private Process _process;
    private Long _versionId;
    private Long _id;
    private Date _effectiveDate;
    //private String _processDefinition;
    private Document _processDefinitionDocument;

    public ProcessVersion() {

        _process = new Process();
        _domainList = new DomainList();
        _processAttributeList = new ProcessAttributeList();
    }
    /**
     * This method is used to retrieve the processAttributeList.
     * @return processAttributeList
     */
    public ProcessAttributeList getProcessAttributeList() {
        return _processAttributeList;
    }

    /**
     * This method is used to set the processAttributeList.
     * @param processAttributeList
     */
    @XmlElement(name = "processAttributeList", type = ProcessAttributeList.class)
    public void setProcessAttributeList(ProcessAttributeList processAttributeList) {
        _processAttributeList = processAttributeList;
    }

    /**
     * This method is used to retrieve the domain List value.
     * @return _domainList
     */
    public DomainList getDomainList() {
        return _domainList;
    }

    /**
     * This method is used to set the domain List value.
     * @param domainList
     */
    @XmlElement(name = "domainList", type = DomainList.class)
    public void setDomainList(DomainList domainList) {
        _domainList = domainList;
    }

    /**
     * This method is used to retrieve the max Instance Duration value.
     * @return _maxInstanceDuration
     */
    public Integer getMaxInstanceDuration() {
        return _maxInstanceDuration;
    }

    /**
     * This method is used to set the max Instance Duration value.
     * @param maxInstanceDuration
     */
    @XmlElement(name = "maxInstanceDuration")
    public void setMaxInstanceDuration(Integer maxInstanceDuration) {
        _maxInstanceDuration = maxInstanceDuration;
    }

    /**
     * This method is used to retrieve the expiration Date value.
     * @return _expirationDate
     */
    public Date getExpirationDate() {
        return _expirationDate;
    }

    /**
     * This method is used to set the expiration Date value.
     * @param expirationDate
     */
    @XmlElement(name = "expirationDate")
    public void setExpirationDate(Date expirationDate) {
        _expirationDate = expirationDate;
    }

    /**
     * This method is used to retrieve the isValid value.
     * @return _isValid
     */
    public Boolean getIsValid() {
        return _isValid;
    }

    /**
     * This method is used to set the is Valid value.
     * @param isValid
     */
    @XmlElement(name = "isValid")
    public void setIsValid(Boolean isValid) {
        _isValid = isValid;
    }

    /**
     * This method is used to retrieve the notify Exceptions value.
     * @return _notifyExceptions
     */
    public Character getNotifyExceptions() {
        return _notifyExceptions;
    }

    /**
     * This method is used to set the notify Exceptions value.
     * @param notifyExceptions
     */
    @XmlElement(name = "notifyExceptions")
    public void setNotifyExceptions(Character notifyExceptions) {
        _notifyExceptions = notifyExceptions;
    }

    /**
     * This method is used to retrieve the retention Period value.
     * @return _retentionPeriod
     */
    public Integer getRetentionPeriod() {
        return _retentionPeriod;
    }

    /**
     * This method is used to set the retention Period value.
     * @param retentionPeriod
     */
    @XmlElement(name = "retentionPeriod")
    public void setRetentionPeriod(Integer retentionPeriod) {
        _retentionPeriod = retentionPeriod;
    }

    /**
     * This method is used to retrieve the locked By value.
     * @return _lockedBy
     */
    public String getLockedBy() {
        return _lockedBy;
    }

    /**
     * This method is used to set the locked By value.
     * @param lockedBy
     */
    @XmlElement(name = "lockedBy")
    public void setLockedBy(String lockedBy) {
        _lockedBy = lockedBy;
    }

    /**
     * This method is used to retrieve the Exception Email Address value.
     * @return _exceptionEmailAddress
     */
    public String getExceptionEmailAddress() {
        return _exceptionEmailAddress;
    }

    /**
     * This method is used to set the exception Email Address value.
     * @param exceptionEmailAddress
     */
    @XmlElement(name = "exceptionEmailAddress")
    public void setExceptionEmailAddress(String exceptionEmailAddress) {
        _exceptionEmailAddress = exceptionEmailAddress;
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
     * This method is used to retrieve the updated By value.
     * @return _updatedBy
     */
    public String getUpdatedBy() {
        return _updatedBy;
    }

    /**
     * This method is used to set the updated by value.
     * @param updatedBy
     */
    @XmlElement(name = "updatedBy")
    public void setUpdatedBy(String updatedBy) {
        _updatedBy = updatedBy;
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
     * This method is used to retrieve the created By value.
     * @return _createdBy
     */
    public String getCreatedBy() {
        return _createdBy;
    }

    /**
     * This method is used to set the created by value.
     * @param createdBy
     */
    @XmlElement(name = "createdBy")
    public void setCreatedBy(String createdBy) {
        _createdBy = createdBy;
    }

    /**
     * This method is used to retrieve the status value.
     * @return _status
     */
    public String getStatus() {
        return _status;
    }

    /**
     * This method is used to set the status value.
     * @param status
     */
    @XmlElement(name = "status")
    public void setStatus(String status) {
        _status = status;
    }

    /**
     * This method is used to retrieve the ProcessVersion Graph value.
     * @return  _processGraph
     */
    public String getProcessGraph() {
        if (getProcessGraphDocument() != null) {
            try {
                return StringUtilities.domToString(getProcessGraphDocument());
            } catch (TransformerException e) {
                throw new WorkflowException(e);
            }
        }
        return null;
    }

    /**
     * This method is used to set the process graph value.
     * @param processGraph
     */
    @XmlElement(name = "processGraph")
    public void setProcessGraph(String processGraph) {
        if (StringUtilities.hasContent(processGraph)) {
            try {
                this.setProcessGraphDocument(StringUtilities.stringToDom(processGraph));
            } catch (SAXException e) {
                throw new WorkflowException(e);
            } catch (ParserConfigurationException e) {
                throw new WorkflowException(e);
            } catch (IOException e) {
                throw new WorkflowException(e);
            }
        }
    }

    /**
     * This method is used to retrieve the ProcessVersion Definition value.
     * @return _processDefinition
     * @throws TransformerException
     */
    public String getProcessDefinition() throws TransformerException {
        if (getProcessDefinitionDocument() != null) {
            return StringUtilities.domToString(getProcessDefinitionDocument());
        }
        return null;
    }

    /**
     * This method is used to set the process definition value.
     * @param processDefinition
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    @XmlElement(name = "processDefinition")
    public void setProcessDefinition(String processDefinition)
            throws SAXException, ParserConfigurationException, IOException {
        if (StringUtilities.hasContent(processDefinition)) {
            setProcessDefinitionDocument(StringUtilities.stringToDom(processDefinition));
        }
    }

    /**
     * This method is used to retrieve the ProcessVersion Description value.
     * @return _processDescription
     */
   public String getProcessDescription() {
        return _processDescription;
    }

   /**
    * This method is used to set the process description value.
    * @param processDescription
    */
    @XmlElement(name = "processDescription")
    public void setProcessDescription(String processDescription) {
        _processDescription = processDescription;
    }

    /**
     * This method is used to retrieve the process name value.
     * @return _processName
     */
    public String getProcessName() {
        return _processName;
    }

    /**
     * This method is used to set the process name value.
     * @param processName
     */
    @XmlElement(name = "processName")
    public void setProcessName(String processName) {
        _processName = processName;
    }

    public Set<ProcessAttribute> getProcessAttributes() {
        return _processAttributes;
    }

    public void setProcessAttributes(Set<ProcessAttribute> attributes) {
        _processAttributes = attributes;
    }

    public Process getProcess() {
        return _process;
    }

    public void setProcess(Process process) {
        this._process = process;
    }

    public Long getVersionId() {
        return _versionId;
    }

    @XmlElement(name = "versionId")
    public void setVersionId(Long id) {
        _versionId = id;
    }

    public Long getId() {
        return _id;
    }

    @XmlElement(name = "id")
    public void setId(Long id) {
        this._id = id;
    }
    
    public Long getProcessVersionId() {
        return _id;
    }

    public void setProcessVersionId(Long id) {
        this._id = id;
    }

    public boolean isDeployed() {
        return StringUtilities.equalsIgnoreCaseWithTrim(this.getStatus(),
                ProcessStatus.PROCESS_STATUS_DEPLOYED);
    }

    /**
     * @return the _processGraphDocument
     */
    public Document getProcessGraphDocument() {
        return _processGraphDocument;
    }
    /**
     * @param graphDocument the _processGraphDocument to set
     */
    @XmlTransient
    public void setProcessGraphDocument(Document graphDocument) {
        _processGraphDocument = graphDocument;
    }

    /**
     * @return the _processDefinitionDocument
     */

    public Document getProcessDefinitionDocument() {
        return _processDefinitionDocument;
    }
    /**
     * @param definitionDocument the _processDefinitionDocument to set
     */
    @XmlTransient
    public void setProcessDefinitionDocument(Document definitionDocument) {
        _processDefinitionDocument = definitionDocument;
    }

    /**
     * This method is used to retrieve the effectiveDate value.
     * @return _effectiveDate
     */
    public Date getEffectiveDate() {
        return _effectiveDate;
    }
    /** This method is used to set the effectiveDate value.
    * @param createdTS
    */
   @XmlElement(name = "effectiveDate")
    public void setEffectiveDate(Date effectiveDate) {
        _effectiveDate = effectiveDate;
    }

   public void setDeployedStatus() {
        this.setStatus(ProcessStatus.PROCESS_STATUS_DEPLOYED);
    }

    public void setNewStatus() {
        this.setStatus(ProcessStatus.PROCESS_STATUS_NEW);
    }

    public void setInProgressStatus() {
        this.setStatus(ProcessStatus.PROCESS_STATUS_WIP);
    }

    public void setValidatedStatus() {
        this.setStatus(ProcessStatus.PROCESS_STATUS_VALIDATE);
    }

    /**
     * This method returns a string representation of the ProcessVersion List that contains
     * ProcessVersion objects.
     *
     * @return strBuff
     */
    public String toString() {
        StringBuffer theProcess = new StringBuffer("ProcessVersion[");
        String processDef;
        try {
            processDef = this.getProcessDefinition();
        } catch (TransformerException e) {
            throw new WorkflowException(e);
        }

        theProcess.append("processId=" + _process.getProcessId()
                + ", processName="  + _processName
                + ", processDescription=" + _processDescription
                + ", processDefinition=" + processDef
                + ", processGraph=" + this.getProcessGraph()
                + ", status=" + _status
                + ", exceptionEmailAddress=" + _exceptionEmailAddress
                + ", lockedBy=" + _lockedBy
                + ", retentionPeriod=" + _retentionPeriod
                + ", notifyExceptions=" + (_notifyExceptions)
                + ", isValid=" +  (_isValid == null ? "FALSE" : "TRUE")
                + ", effectiveDate="
                + (_effectiveDate == null ? "NULL" : DateUtilities.formatISO8601(_effectiveDate))
                + ", expirationDate="
                + (_expirationDate == null ? "NULL" : DateUtilities.formatISO8601(_expirationDate))
                + ", maxInstanceDuration=" + _maxInstanceDuration
                + ", domainList=" + _domainList.toString()
                + ", owners=set of Actors"
                + ", actors=set of Actors"
                + ", processAttributeList=" + _processAttributeList.toString()
                + ", updatedBy=" + _updatedBy
                + ", updatedTS="
                + (_updatedTS == null ? "NULL" : DateUtilities.formatISO8601(_updatedTS))
                + ", createdBy=" + _createdBy
                + ", createdTS="
                + (_createdTS == null ? "NULL" : DateUtilities.formatISO8601(_createdTS))
                + "]");

        return theProcess.toString();
   }
}
