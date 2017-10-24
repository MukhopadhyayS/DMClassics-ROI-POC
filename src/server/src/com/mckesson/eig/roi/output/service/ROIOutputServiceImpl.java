/*
 * Copyright 2014 McKesson Corporation and/or one of its subsidiaries.
 * All Rights Reserved.
 *
 * Use of this material is governed by a license agreement. This material
 * contains confidential, proprietary and trade secret information of
 * McKesson Corporation and is protected under United States and
 * international copyright and other intellectual property laws. Use,
 * disclosure, reproduction, modification, distribution, or storage
 * in a retrieval system in any form or by any means is prohibited without
 * the prior express written permission of McKesson Corporation.
 */

package com.mckesson.eig.roi.output.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.mckesson.eig.roi.base.api.ROIClientErrorCodes;
import com.mckesson.eig.roi.base.api.ROIException;
import com.mckesson.eig.roi.output.model.DestInfo;
import com.mckesson.eig.roi.output.model.DestInfoList;
import com.mckesson.eig.roi.output.model.MapModel;
import com.mckesson.eig.roi.output.model.OutputContent;
import com.mckesson.eig.roi.output.model.OutputFeature;
import com.mckesson.eig.roi.output.model.OutputQueue;
import com.mckesson.eig.roi.output.model.OutputRequest;
import com.mckesson.eig.roi.output.model.OutputTransform;
import com.mckesson.eig.roi.output.model.PropertyDef;
import com.mckesson.eig.roi.output.model.RequestPart;
import com.mckesson.eig.roi.output.model.TaskAttributes;
import com.mckesson.eig.roi.output.rest.webservice.proxy.OutputQueueWebserviceInterface;
import com.mckesson.eig.roi.output.rest.webservice.proxy.OutputWebserviceInterface;
import com.mckesson.eig.roi.output.rest.webservice.proxy.ROIOutputWebServiceInterface;
import com.mckesson.dm.core.common.logging.OCLogger;
import com.mckesson.eig.roi.webservice.proxy.BaseRestService;
import com.mckesson.eig.utility.util.CollectionUtilities;
import com.mckesson.eig.utility.util.ObjectUtilities;



/**
 * This class implements the Output services for ROI
 * @author Karthik Easwaran(OFS)
 * @author Shahm Nattarshah.
 *
 */
public class ROIOutputServiceImpl
extends BaseRestService
implements ROIOutputService {

    /**
     * Object represents the Log4JWrapper object.
     */
    private static final OCLogger LOG = new OCLogger(ROIOutputServiceImpl.class);

    /**
     * This method is used to submit the output Request
     */
    @Override
    public long submitOutputRequest(OutputRequest request) {

        try {

            List<RequestPart> requestParts = request.getRequestParts();
            List<OutputContent> content = new ArrayList<OutputContent>();
            OutputContent output;
            for (RequestPart part : requestParts) {

                output = new OutputContent();
                output.setContentType(part.getContentSourceType());
                output.setAttributes(getProperties(part.getProperties()));
                content.add(output);
            }

            OutputQueue outputQueue = new OutputQueue();
            outputQueue.setQueueId(request.getDestId());
            outputQueue.setQueueType(request.getDestType());
            outputQueue.setName(request.getDestName());
            outputQueue.setAttributes(getProperties(request.getProperties()));

            HashMap<String, String> taskProperties = new HashMap<String, String>();
            taskProperties.putAll(getProperties(request.getProperties()));

            List<OutputTransform> outputTransforms = request.getOutputTransforms();
            if (outputTransforms != null) {

                for (OutputTransform transForm : outputTransforms) {
                    taskProperties.putAll(getProperties(transForm.getProperties()));
                }
            }
            
            //if labels are set send labels as task properties
            if (request.getLabels() != null && request.getLabels().size() > 0) {
            	taskProperties.putAll(getProperties(request.getLabels()));
            }

            TaskAttributes attributes = new TaskAttributes();
            attributes.setAttributes(taskProperties);
            ObjectMapper mapper = new ObjectMapper();

            ROIOutputWebServiceInterface proxy = getProxy(ROIOutputWebServiceInterface.class);
            long submitTask = proxy.submitTask(mapper.writeValueAsString(content),
                                               mapper.writeValueAsString(outputQueue),
                                               mapper.writeValueAsString(attributes));
            return submitTask;

        } catch (Exception e) {

            LOG.error("Submit job failed", e);
            throw new ROIException(ROIClientErrorCodes.SUBMIT_JOB_FAILED, e.getMessage());
        }
    }


    /**
     * converts the given map model to the hasmap attributes
     * @param properties
     * @return
     */
    private HashMap<String, String> getProperties(List<MapModel> properties) {

        HashMap<String, String> map = new HashMap<String, String>();
        if (CollectionUtilities.hasContent(properties)) {

            for (MapModel model : properties) {
                map.put(ObjectUtilities.toString(model.getKey()), ObjectUtilities.toString( model.getValue()));
            }
        }
        return map;
    }

    /**
     * This method is used to get Service properties
     */
    @Override
    public OutputFeature getServiceProperties() {

        try {

        OutputWebserviceInterface proxy = getProxy(OutputWebserviceInterface.class);
        OutputFeature outputServiceProperty = proxy.getServiceProperties("ROI");
        return outputServiceProperty;

        } catch (Exception e) {

            LOG.error("Get service properties failed", e);
            throw new ROIException(ROIClientErrorCodes.GET_SERVICE_PROPERTIES, e.getMessage());
        }

    }

    /**
     * This method is used to get the output job object
     */
    @Override
    public long queryOutputJob(int job) {

        try {

            OutputWebserviceInterface proxy = getProxy(OutputWebserviceInterface.class);
            long statusId = proxy.queryOutputJob(job);
            return statusId;

        } catch (Exception e) {

            LOG.error("query output job failed", e);
            throw new ROIException(ROIClientErrorCodes.QUERY_OUTPUT_JOB, e.getMessage());
        }

    }

    /**
     *  This method is used to get the destinations
     */
    @Override
    public DestInfoList getDestinations(String typeName) {

        try {

            DestInfoList destInfoList = new DestInfoList();

            OutputQueueWebserviceInterface proxy = getProxy(OutputQueueWebserviceInterface.class);
            List<OutputQueue> queues = proxy.getQueuesByType(typeName);

            List<DestInfo> info = new ArrayList<DestInfo>();
            DestInfo dest;
			if(null != queues && queues.size() > 0) {
				for (OutputQueue queue : queues) {

					dest = new DestInfo();
					dest.setId(queue.getQueueId());
					dest.setName(queue.getName());
					dest.setType(queue.getQueueType());
					dest.setDescription(queue.getDescription());
					
					HashMap<String, Object> attributeMap = new HashMap<String, Object>();
					attributeMap.putAll(queue.getAttributes());
					dest.setProperties(attributeMap);
					
					//populate disc labels list
					if ("DISC".equalsIgnoreCase(typeName)) {
						//load labels
						String templateLocation = queue.getAttributes().get("templateLocation");
						if (templateLocation != null ) {
							PropertyDef labelPropertyDef = 
									getLabelTemplatePropertyDef(templateLocation, queue.getAttributes().get("labelTemplate"));
							PropertyDef[] destPropertyDefs = new PropertyDef[1];
							destPropertyDefs[0]=labelPropertyDef;
							dest.setPropertyDefs(destPropertyDefs);
						}
					}
				
					info.add(dest);
				}
		    }
            destInfoList.setDestInfoList(info);
            return destInfoList;

        } catch (Exception e) {

            LOG.error("Get destinations failed", e);
            throw new ROIException(ROIClientErrorCodes.GET_DESTINATIONS, e.getMessage());
        }
    }
    
    private PropertyDef getLabelTemplatePropertyDef(String templateLocation, String defaultTemplate) {
    	//load labels
    	PropertyDef labelPropertyDef = new PropertyDef();
     	labelPropertyDef.setPropertyName("labelTemplate");
     	labelPropertyDef.setDataType("Collection");
    	labelPropertyDef.setDefaultValue(defaultTemplate);
    	String[] emptyList = {""};
    	labelPropertyDef.setPossibleValues(emptyList);
     	
    	if (templateLocation != null ) {
            OutputQueueWebserviceInterface proxy = getProxy(OutputQueueWebserviceInterface.class);
            List<String> labelsList = proxy.getLabelTemplateFilenames(templateLocation);
    		if (labelsList != null && labelsList.size() > 0) {
               	labelPropertyDef = new PropertyDef();
             	labelPropertyDef.setPropertyName("labelTemplate");
             	labelPropertyDef.setDataType("Collection");
            	labelPropertyDef.setDefaultValue(defaultTemplate);
            	String[] labelsListArray = new String[labelsList.size()];
            	
            	labelsList.toArray(labelsListArray);
            	labelPropertyDef.setPossibleValues(labelsListArray);
    		}
    	}
    	
    	return labelPropertyDef;
   	
    }
    
}
