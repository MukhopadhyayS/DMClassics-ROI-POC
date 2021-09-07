/**
 * 
 */
package com.mckesson.eig.roi.output.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputQueue 
extends AbstractOutputAttributes {

    private int _queueId;
    private String _name;
    private String _description;
    private String _queueType;
    

    public OutputQueue() {
    }


    /**
     * @return the queueId
     */
    public int getQueueId() {
        return _queueId;
    }

    /**
     * @param queueId the queueId to set
     */
    public void setQueueId(int queueId) {
        _queueId = queueId;
    }


    /**
     * @return the name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return _description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        _description = description;
    }


    public String getQueueType() {
        return _queueType;
    }


    public void setQueueType(String queueType) {
        _queueType = queueType;
    }
}
