#region Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
/* 
BEGIN-COPYRIGHT-COMMENT Do not remove or modify this line!

* Copyright © 2010 McKesson Corporation and/or one of its subsidiaries. All Rights Reserved.
* Use of this software and related documentation is governed by a license agreement. 
* This material contains confidential, proprietary and trade secret information of 
* McKesson Information Solutions and is protected under United States
* and international copyright and other intellectual property laws. 
* Use, disclosure, reproduction, modification, distribution, or storage
* in a retrieval system in any form or by any means is prohibited without the 
* prior express written permission of McKesson Information Solutions.

END-COPYRIGHT-COMMENT  Do not remove or modify this line! 
*/
#endregion
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Web_References.OutputServiceWS;
using McK.EIG.ROI.Client.Request.View;
//using McK.EIG.ROI.Client.Web_References.DestinationServiceWS;

namespace McK.EIG.ROI.Client.Request.Controller
{
    public partial class OutputController : ROIController
    {
        #region Fields

        private static object syncRoot = new Object();
        
        //private static variable of OutputController
        private static volatile OutputController outputController;

        //Private variable to create instance of OutputService 
        private ROIOutputServiceWse outputService;
        private const string outputType = "type";

        //output server property keys
        private const string MediaKey = "output.file.media";
        private const string RequestIdKey = "output.file.requestId";
        private const string RequestDateKey = "output.file.request.date";
        private const string ReleaseNumberKey = "ouput.file.releaseNum";
        private const string SecureKey = "output.file.password";
        private const string RequestSecureKey = "output.file.request.password";
        private const string FaxNumberKey = "FAX_NUMBER";
        private const string EmailAddrKey = "to";
        private const string EmailSubjectKey = "subject";
        private const string EmailMessageKey = "body";
        private const string PrependKey = "output.file.prepend";
        private const string WhereKey = "output.queue.where";
        private const string CommentKey = "output.queue.comment";

        private const string requestSource = "roi.";

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the Request Service.
        /// </summary>
        private OutputController()
        {
            outputService = new ROIOutputServiceWse();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Method used to submit output request
        /// </summary>
        /// <param name="release"></param>
        /// <returns></returns>
        public long SubmitOutputRequest(OutputRequestDetails outputRequestDetails,
                                          DestinationType destinationType,
                                          OutputViewDetails outputViewDetails,
                                          bool isRelease)
        {
            return SubmitOutputRequest(outputRequestDetails, destinationType, outputViewDetails, isRelease, false);
        }

        /// <summary>
        /// Method used to submit output request
        /// </summary>
        /// <param name="release"></param>
        /// <returns></returns>
        public long SubmitOutputRequest(OutputRequestDetails outputRequestDetails,
                                          DestinationType destinationType,
                                          OutputViewDetails outputViewDetails,
                                          bool isRelease, bool isOverDueWatermark)
        {
            OutputRequest outputRequest = MapModel(outputRequestDetails, destinationType,
                                                               outputViewDetails, isRelease,
                                                               isOverDueWatermark);

            object[] requestParams = new object[] { outputRequest };
            object response = ROIHelper.Invoke(outputService, "submitOutputRequest", requestParams);
           // outputJob SubmitOutputRequestResponse = (outputJob)response;
            long SubmitOutputRequestResponse = (long)response;

            // output submit task has been made into synchoronous call,
            // there is no need of making an webservice call to retrive the submit status of the job.
            //requestParams = new object[] { SubmitOutputRequestResponse };
            //response = ROIHelper.Invoke(outputService, "queryOutputJob", requestParams);
           // response = 200;
            //long queryOutputJobResponse = (long)response;
            return SubmitOutputRequestResponse;//Convert.ToInt16(SubmitOutputRequestResponse);
        }

        /// <summary>
        /// Retrieve destination (Print or Fax or File) properties
        /// </summary>
        /// <param name="outputMethod"></param>
        /// <returns></returns>
        public OutputPropertyDetails RetrieveDestinations(string outputMethod)
        {
            object[] requestParams = new object[] { outputMethod };
                object response = ROIHelper.Invoke(outputService, "getDestinations", requestParams);
                map[] servicePropertyResponse = RetrieveServiceProperties();
                OutputPropertyDetails outputPropertyDetails = MapModel((DestInfo[])response, servicePropertyResponse);
                OutputPropertyDetailsCache.AddData(outputMethod, outputPropertyDetails);
                return outputPropertyDetails;
        }

        /// <summary>
        /// Retrieve static properties of Header, Footer & Watermark
        /// </summary>
        /// <returns></returns>
        public map[] RetrieveServiceProperties()
        {          
            object response = ROIHelper.Invoke(outputService, "getServiceProperties", new object[0]);
            OutputFeature output = (OutputFeature)response;
            return (map[])output.attributes;
        }
		

       

        private static OutputPropertyDetails MapModel(DestInfo[] destInfos,
                                                          map[] servicePropertyResponse)
        {
            OutputPropertyDetails outputPropertyDetails = new OutputPropertyDetails();
            OutputViewDetails outputViewDetails = new OutputViewDetails();

            outputPropertyDetails.OutputViewDetails = outputViewDetails;

            foreach (DestInfo destInfo in destInfos)
            {
                OutputDestinationDetails outputDestinationDetails = new OutputDestinationDetails();
                outputDestinationDetails.Name = destInfo.name;
                outputDestinationDetails.Type = destInfo.type;
                outputDestinationDetails.Id = destInfo.id;
                outputDestinationDetails.Comment = destInfo.description;

                foreach (propertyMap mapModel in destInfo.properties)
                {
                    switch (mapModel.key.ToString())
                    {
                        case "requirePassword":
                            {
                                if (mapModel.value.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("yes") || mapModel.value.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals("true"))
                                {
                                    outputDestinationDetails.PasswordRequired = true;
                                }
                                else
                                {
                                    outputDestinationDetails.PasswordRequired = false;
                                }                                
                                break;
                            }
                        case "defaultPassword":
                            {
                                outputDestinationDetails.SecuredSecretWord = Convert.ToString(mapModel.value,System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                        case "fileDefaultPassword":
                            {
                                outputDestinationDetails.SecuredSecretWord = Convert.ToString(mapModel.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                        case "mediaType":
                            {
                                outputDestinationDetails.Media = Convert.ToString(mapModel.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                        case "location":
                            {
                                outputDestinationDetails.Where = Convert.ToString(mapModel.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                        case "deviceId":
                            {
                                outputDestinationDetails.DeviceID = Convert.ToInt32(mapModel.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                        case "labelTemplate":
                            {
                                outputDestinationDetails.TemplateName = Convert.ToString(mapModel.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                break;
                            }
                    }
                }

                if (destInfo.statusInfo != null)
                {
                    outputDestinationDetails.Status = destInfo.statusInfo.status;
                }
                

                PropertyDefinition clientPropertyDefinition;
                if (destInfo.propertyDefs != null)
                {
                    foreach (PropertyDef serverPropertyDefinition in destInfo.propertyDefs)
                    {
                        if (serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.List.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)) ||
                           serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.String.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)) ||
                           serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.Number.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)) ||
                           serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.Collection.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)) ||
                           serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.Boolean.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)))
                        {
                            clientPropertyDefinition = new PropertyDefinition();

                            clientPropertyDefinition.Label = serverPropertyDefinition.label;
                            clientPropertyDefinition.PropertyName = serverPropertyDefinition.propertyName;
                            clientPropertyDefinition.Description = serverPropertyDefinition.description;
                            clientPropertyDefinition.DataType = serverPropertyDefinition.dataType;
                            clientPropertyDefinition.DefaultValue = serverPropertyDefinition.defaultValue;

                            foreach (propertyMap propertyMapTypeProperty in destInfo.properties)
                            {
                                if (serverPropertyDefinition.propertyName.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(propertyMapTypeProperty.key.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)))
                                {
                                    clientPropertyDefinition.DefaultValue = Convert.ToString(propertyMapTypeProperty.value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                                }
                            }

                            if (serverPropertyDefinition.possibleValues != null)
                            {
                                foreach (string possibleValue in serverPropertyDefinition.possibleValues)
                                {
                                    clientPropertyDefinition.PossibleValues.Add(possibleValue);
                                }
                            }

                            outputDestinationDetails.PropertyDefinitions.Add(clientPropertyDefinition);

                            //if (serverPropertyDefinition.dataType.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture).Equals(PropertyType.String.ToString().ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture)) &&
                            //   serverPropertyDefinition.possibleValues.Length == 0)
                            //{
                            //outputDestinationDetails.PropertyDefinitions.Remove(clientPropertyDefinition);
                            //}
                        }
                    }
                }
                else
                {
                    //backward compatiability - convert properties into property definitions
                    foreach (propertyMap propertyMapTypeProperty in destInfo.properties)
                    {

                        clientPropertyDefinition = new PropertyDefinition();
                        clientPropertyDefinition.PropertyName = propertyMapTypeProperty.key;
                        clientPropertyDefinition.DefaultValue = propertyMapTypeProperty.value;
                        outputDestinationDetails.PropertyDefinitions.Add(clientPropertyDefinition);
                    }

                }
                outputPropertyDetails.OutputDestinationDetails.Add(outputDestinationDetails);
            }

            if (servicePropertyResponse != null)
            {
                foreach (map property in servicePropertyResponse)
                {
                    switch (property.key)
                    {
                        case "WATERMARK_ENABLED":
                            {
                                if (string.Equals(property.value, Boolean.TrueString, StringComparison.CurrentCultureIgnoreCase))
                                {
                                    outputViewDetails.IsWatermark = true;
                                }
                                break;
                            }
                        case "WATERMARK_TEXT":
                            {
                                outputViewDetails.Watermark = property.value;
                                break;
                            }
                        case "DISPLAY_ABSOLUTE_PAGE_NUMBER_HEADER":
                        case "DISPLAY_RELATIVE_PAGE_NUMBER_HEADER":
                        case "DISPLAY_PATIENT_NAME_HEADER":
                        case "DISPLAY_PATIENT_ENCOUNTER_HEADER":
                        case "DISPLAY_PATIENT_MRN_HEADER":
                            {
                                if (string.Equals(property.value, Boolean.TrueString, StringComparison.CurrentCultureIgnoreCase))
                                {
                                    outputViewDetails.IsHeader = true;
                                    outputViewDetails.IsHeaderEnabled = true;
                                }
                                 break;
                            }
                        case "DISPLAY_ABSOLUTE_PAGE_NUMBER_FOOTER":
                        case "DISPLAY_RELATIVE_PAGE_NUMBER_FOOTER":
                        case "DISPLAY_PATIENT_NAME_FOOTER":
                        case "DISPLAY_PATIENT_ENCOUNTER_FOOTER":
                        case "DISPLAY_PATIENT_MRN_FOOTER":
                            {
                                if (string.Equals(property.value, Boolean.TrueString, StringComparison.CurrentCultureIgnoreCase))
                                {
                                    outputViewDetails.IsFooter = true;
                                    outputViewDetails.IsFooterEnabled = true;
                                }
                                break;
                            }
                    }
                }
            }
            return outputPropertyDetails;
        }

        /// <summary>
        /// Converts client model into server.
        /// </summary>
        /// <param name="clientOutputRequest"></param>
        /// <returns></returns>
        private static OutputRequest MapModel(OutputRequestDetails clientOutputRequest,
                                                    DestinationType destinationType,
                                                    OutputViewDetails outputViewDetails,
                                                    bool isRelease,
                                                    bool isOverDueWatermark)
        {
            long requestId = clientOutputRequest.RequestId;
            long releaseId = clientOutputRequest.ReleaseId;
            string requestSecretWord = clientOutputRequest.RequestSecretWord;
            string requestDateNoFormat = clientOutputRequest.RequestDateTextNoFormat;
            string requestDateFormatted = clientOutputRequest.RequestDateTextFormatted;

            OutputRequest serverOutputRequest = new OutputRequest();
            List<MapModel> mapModelLists = new List<MapModel>();
            if(requestId > 0) 
            {
                 mapModelLists.Add(CreateMapModel("ROIRequestId", requestId.ToString()));
            }
 
            serverOutputRequest.requestId = requestSource + requestId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
            serverOutputRequest.destName = clientOutputRequest.OutputDestinationDetails.Name;
            serverOutputRequest.destType = clientOutputRequest.OutputDestinationDetails.Type;
            serverOutputRequest.destId = clientOutputRequest.OutputDestinationDetails.Id;

            List<RequestPart> serverRequestParts = new List<RequestPart>();

            List<OutputTransform> serverOutputTransforms = new List<OutputTransform>();
            List<MapModel> mapModels = new List<MapModel>();
            MapModel mapModel = new MapModel();
            OutputTransform serverOutputTransform = new OutputTransform();

            List<OutputTransform> headerFooterTransforms;

            // Loop through the ROI and HPF and Attachment request parts
            foreach (RequestPartDetails requestPartDetails in clientOutputRequest.RequestParts)
            {
                if (destinationType == DestinationType.Disc)
                {
                    if (requestPartDetails.ContentSource == OutputPropertyDetails.RequestContentSource)
                    {
                        List<MapModel> labelMapModelList;
                        foreach (PropertyDetails propertyDetails in requestPartDetails.PropertyLists)
                        {
                            labelMapModelList = new List<MapModel>();
                            string requestCreated = Convert.ToDateTime(propertyDetails.RequestCreated).
                                                                       ToString("MM/dd/yyyy HH:mm", CultureInfo.InvariantCulture);
                            string requestCreatedForDisc = Convert.ToDateTime(propertyDetails.RequestCreated).
                                                                              ToString("yyyy-MM-dd-HH-mm", CultureInfo.InvariantCulture);
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.RequestorNameSecure, propertyDetails.RequestorName));
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.RequestIDSecure, propertyDetails.RequestID.ToString()));
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.RequestCreatedSecure, requestCreated));
                            if (propertyDetails.RequestCompleted != null)
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.RequestCompletedSecure, propertyDetails.RequestCompleted.ToString()));
                            }
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.TotalPageCountSecure, propertyDetails.TotalPageCount.ToString()));
                            if (propertyDetails.OutputNotes!=null && propertyDetails.OutputNotes.Length > 0)
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.NotesSecure, propertyDetails.OutputNotes));
                            }
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.EnounterCountSecure, propertyDetails.EncounterCount.ToString()));
                            labelMapModelList.Add(CreateMapModel(PropertyDetails.PatientCountSecure, propertyDetails.PatientCount.ToString()));
                            if (!string.IsNullOrEmpty(propertyDetails.PatientName))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.PatientNameSecureForDisc, propertyDetails.PatientName));
                            }
                            if (!string.IsNullOrEmpty(propertyDetails.DOB))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.PatientDOBSecure, propertyDetails.DOB));
                            }
                            if (!string.IsNullOrEmpty(propertyDetails.MRN))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.PatientMRNSecure, propertyDetails.MRN));
                            }
                            if (!string.IsNullOrEmpty(propertyDetails.Encounter))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.EncounterNumberSecure, propertyDetails.Encounter));
                            }
                            if (!string.IsNullOrEmpty(propertyDetails.AdmitDate))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.AdmitDateSecure, propertyDetails.AdmitDate.ToString()));
                            }

                            if (!string.IsNullOrEmpty(propertyDetails.DischargeDate))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.DischargeDateSecure, propertyDetails.DischargeDate.ToString()));
                            }
                            if (!string.IsNullOrEmpty(propertyDetails.PatientType))
                            {
                                labelMapModelList.Add(CreateMapModel(PropertyDetails.PatientTypeSecure, propertyDetails.PatientType));
                            }
                            OutputDestinationDetails destinationDetails = clientOutputRequest.OutputDestinationDetails;
                            mapModelLists.Add(CreateMapModel(PropertyDetails.DiscQueueValueSecure, ROIController.EncryptOcSecurity(ROIController.DecryptAES(destinationDetails.SecuredSecretWord))));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.IsEncryptedValueSecure, (null == destinationDetails.IsEncryptedPassword) ? "false" : destinationDetails.IsEncryptedPassword.ToString()));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.ReleaseNumForDiscSecure, releaseId.ToString()));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.ReleaseIDForDiscSecure, propertyDetails.RequestID.ToString()));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.RequestSecureForDisc, ROIController.EncryptOcSecurity(ROIController.DecryptAES(requestSecretWord))));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.DiscMediaSecure, destinationDetails.DiscType));
                            mapModelLists.Add(CreateMapModel(PropertyDetails.RequestDateForDiscSecure, requestCreatedForDisc));
                            if (!("None").Equals(destinationDetails.TemplateName))
                                mapModelLists.Add(CreateMapModel(PropertyDetails.DiscLabelTemplate, destinationDetails.TemplateName));
                            serverOutputRequest.labels = labelMapModelList.ToArray();
                        }
                    }
                }
                if (requestPartDetails.ContentSource == OutputPropertyDetails.ROIContentSource)
                {
                    RequestPart roiRequestPartType = new RequestPart();

                    roiRequestPartType.contentId = requestPartDetails.ContentId;
                    roiRequestPartType.contentSourceName = requestPartDetails.ContentSource;
                    roiRequestPartType.contentSourceType = requestPartDetails.ContentSource;

                    if (isRelease)
                    {
                        mapModelLists.Add(CreateMapModel(PrependKey, roiRequestPartType.contentId));
                    }

                    List<MapModel> mapModelList = new List<MapModel>();
                    mapModelList.Add(CreateMapModel(PropertyDetails.FileIdsSecure, requestPartDetails.PropertyLists[0].FileIds));

                    //if (requestPartDetails.PropertyLists[0].IsRequestorGrouping)
                    if (clientOutputRequest.IsGroupingEnabled)
                    {   
                        mapModelList.Add(CreateMapModel(PropertyDetails.RequestorGroupingSecure, requestPartDetails.PropertyLists[0].RequestorGrouping));
                    }

                    roiRequestPartType.properties = mapModelList.ToArray();
                    serverRequestParts.Add(roiRequestPartType);
                }
                else if (requestPartDetails.ContentSource == OutputPropertyDetails.ReleaseContentSource)
                {
                    
                    RequestPart roiRequestPartType = new RequestPart();

                    roiRequestPartType.contentId = requestPartDetails.ContentId;
                    roiRequestPartType.contentSourceName = requestPartDetails.ContentSource;
                    roiRequestPartType.contentSourceType = requestPartDetails.ContentSource;


                    List<MapModel> mapModelList = new List<MapModel>();
                    mapModelList.Add(CreateMapModel(PropertyDetails.FileIdsSecure, requestPartDetails.PropertyLists[0].FileIds));
                    mapModelList.Add(CreateMapModel(PropertyDetails.FileTypesSecure, requestPartDetails.PropertyLists[0].FileTypes));

                    roiRequestPartType.properties = mapModelList.ToArray();
                    serverRequestParts.Add(roiRequestPartType);

                    if (isRelease)
                    {
                        //Create Header Footer object
                        //set header footer to false by default
                        serverOutputTransform = new OutputTransform();
                        mapModels = new List<MapModel>();
                        mapModels.Add(CreateMapModel(OutputPropertyDetails.HeaderChecked, Boolean.FalseString));
                        mapModels.Add(CreateMapModel(OutputPropertyDetails.FooterChecked, Boolean.FalseString));

                        if ((outputViewDetails.IsHeaderEnabled) || (outputViewDetails.IsFooterEnabled))
                        {
                            if ((outputViewDetails.IsHeader) || (outputViewDetails.IsFooter))
                            {
                                mapModels = new List<MapModel>();
                                mapModels.Add(CreateMapModel(OutputPropertyDetails.HeaderChecked, outputViewDetails.IsHeader.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                                mapModels.Add(CreateMapModel(OutputPropertyDetails.FooterChecked, outputViewDetails.IsFooter.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));

                            }
                        }
                        serverOutputTransform.properties = mapModels.ToArray();
                        serverOutputTransforms.Add(serverOutputTransform);
                    }
                }
                else if (requestPartDetails.ContentSource == OutputPropertyDetails.ResendMPFAttachmentContentSource
                    || requestPartDetails.ContentSource == OutputPropertyDetails.ResendNonMpfAttachmentContentSource
                    || requestPartDetails.ContentSource == OutputPropertyDetails.ResendMPFContentSource)
                {
                    RequestPart roiRequestPartType = new RequestPart();

                    //to send request to output, sourcename and source type should be set to ROIContentSource
                    roiRequestPartType.contentId = "0";
                    roiRequestPartType.contentSourceName = requestPartDetails.ContentSource;
                    roiRequestPartType.contentSourceType = requestPartDetails.ContentSource;
                    String pageIds = requestPartDetails.PropertyLists[0].PageIds;

                    if (null != pageIds && pageIds.Length > 0)
                    {
                        List<MapModel> mapModelList = new List<MapModel>();
                        mapModelList.Add(CreateMapModel(PropertyDetails.FileIdsSecure, requestPartDetails.ContentSource + "." + pageIds));

                        roiRequestPartType.properties = mapModelList.ToArray();
                        serverRequestParts.Add(roiRequestPartType);
                    }
                }
            }

            

            if (destinationType == DestinationType.Print)
            {
                // Create Page_Separator object
                if (outputViewDetails.IsPageSeparator)
                {
                    serverOutputTransform = new OutputTransform();
                    mapModels = new List<MapModel>();
                    serverOutputTransform.transformName = OutputPropertyDetails.PageSeparatorKey;
                    serverOutputTransform.transformType = OutputPropertyDetails.PageSeparatorKey;
                    mapModel = new MapModel();
                    mapModel.key = OutputPropertyDetails.PageSeparatorKey;
                    mapModel.value = outputViewDetails.IsPageSeparator.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture);
                    mapModels.Add(mapModel);
                    serverOutputTransform.properties = mapModels.ToArray();
                    serverOutputTransforms.Add(serverOutputTransform);
                }
                // To display where and comments fields in ViewOutputSettings
                mapModelLists.Add(CreateMapModel(WhereKey, clientOutputRequest.OutputDestinationDetails.Where));
                mapModelLists.Add(CreateMapModel(CommentKey, clientOutputRequest.OutputDestinationDetails.Comment));
            }

            if ((isRelease) || (isOverDueWatermark))
            {
                // Create Watermark transform object
                if (outputViewDetails.IsWatermark)
                {
                    serverOutputTransform = new OutputTransform();
                    mapModels = new List<MapModel>();
                    serverOutputTransform.transformType = OutputPropertyDetails.WatermarkKey;
                    serverOutputTransform.transformName = OutputPropertyDetails.WatermarkKey;
                   	//CR#378189 Fix
                   	mapModels.Add(CreateMapModel(OutputPropertyDetails.WatermarkKey, outputViewDetails.Watermark));
					serverOutputTransform.properties = mapModels.ToArray();
                    serverOutputTransforms.Add(serverOutputTransform);
                }
            }

            if (destinationType == DestinationType.Print)
            {
                if (clientOutputRequest.OutputDestinationDetails.PropertyDefinitions.Count > 0)
                {
                    foreach (PropertyDefinition propertyDefinition in clientOutputRequest.OutputDestinationDetails.PropertyDefinitions)
                    {
                        if ((!string.IsNullOrEmpty(propertyDefinition.PropertyName)) || (!string.IsNullOrEmpty(propertyDefinition.Label) || (!string.IsNullOrEmpty(propertyDefinition.DefaultValue))))
                        {
                            mapModelLists.Add(CreateMapModel(propertyDefinition.PropertyName, propertyDefinition.DefaultValue));
                        }
                    }
                }
            }

            if (destinationType == DestinationType.File)
            {
                mapModelLists.Add(CreateMapModel(MediaKey, clientOutputRequest.OutputDestinationDetails.Media));
                mapModelLists.Add(CreateMapModel(RequestIdKey, requestId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                mapModelLists.Add(CreateMapModel(ReleaseNumberKey, releaseId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                mapModelLists.Add(CreateMapModel(RequestDateKey, requestDateNoFormat));
                mapModelLists.Add(CreateMapModel(PropertyDetails.IsEncryptedValueSecure, (null == clientOutputRequest.OutputDestinationDetails.IsEncryptedPassword) ? "false" : clientOutputRequest.OutputDestinationDetails.IsEncryptedPassword.ToString()));
                if (!string.IsNullOrEmpty(clientOutputRequest.OutputDestinationDetails.SecuredSecretWord.Trim()))
                {
                    mapModelLists.Add(CreateMapModel(SecureKey, ROIController.EncryptOcSecurity(ROIController.DecryptAES(clientOutputRequest.OutputDestinationDetails.SecuredSecretWord))));
                }
                if (!string.IsNullOrEmpty(requestSecretWord))
                {
                    mapModelLists.Add(CreateMapModel(RequestSecureKey, ROIController.EncryptOcSecurity(ROIController.DecryptAES(requestSecretWord))));
                }
                if(isOverDueWatermark) // To verify the call initiated by overdue invoices
                {
                    mapModelLists.Add(CreateMapModel(RequestSecureKey, string.Empty));
                }
            }

            if (destinationType == DestinationType.Fax)
            {
                mapModelLists.Add(CreateMapModel(FaxNumberKey, clientOutputRequest.OutputDestinationDetails.Fax));
                // To display where and comments fields in ViewOutputSettings
                mapModelLists.Add(CreateMapModel(WhereKey, clientOutputRequest.OutputDestinationDetails.Where));
                mapModelLists.Add(CreateMapModel(CommentKey, clientOutputRequest.OutputDestinationDetails.Comment));
            }

            mapModelLists.Add(CreateMapModel(PropertyDetails.RequestorGroupingEnabled, clientOutputRequest.IsGroupingEnabled.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
			if (destinationType == DestinationType.Email)
            {
                PropertyDefinition subjectProp = new PropertyDefinition();
                subjectProp.PropertyName = ROIConstants.EmailSubjectKey;
                PropertyDefinition msgProp = new PropertyDefinition();
                msgProp.PropertyName = ROIConstants.EmailMessageKey;

                String subject = emailRequestParameters(clientOutputRequest.OutputDestinationDetails.PropertyDefinitions,
                                        subjectProp,
                                        DateTime.Now.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture),
                                        requestId.ToString(),
                                        requestDateFormatted);

                String emailText = emailRequestParameters(clientOutputRequest.OutputDestinationDetails.PropertyDefinitions,
                                        msgProp,
                                        DateTime.Now.ToString(ROIConstants.DateFormat, System.Threading.Thread.CurrentThread.CurrentUICulture),
                                        requestId.ToString(),
                                        requestDateFormatted);

                mapModelLists.Add(CreateMapModel(EmailAddrKey, clientOutputRequest.OutputDestinationDetails.EmailAddr));
                mapModelLists.Add(CreateMapModel(EmailSubjectKey, subject));
                mapModelLists.Add(CreateMapModel(EmailMessageKey, emailText));
                mapModelLists.Add(CreateMapModel(RequestIdKey, requestId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                mapModelLists.Add(CreateMapModel(ReleaseNumberKey, releaseId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture)));
                mapModelLists.Add(CreateMapModel(RequestDateKey, requestDateNoFormat));
                if (!string.IsNullOrEmpty(clientOutputRequest.OutputDestinationDetails.SecuredSecretWord.Trim()))
                {
                    mapModelLists.Add(CreateMapModel(SecureKey, ROIController.EncryptOcSecurity(ROIController.DecryptAES(clientOutputRequest.OutputDestinationDetails.SecuredSecretWord))));
                    mapModelLists.Add(CreateMapModel(PropertyDetails.IsEncryptedValueSecure, (null == clientOutputRequest.OutputDestinationDetails.IsEncryptedPassword) ? "false" : clientOutputRequest.OutputDestinationDetails.IsEncryptedPassword.ToString()));
                }
                if (!string.IsNullOrEmpty(requestSecretWord))
                {
                    mapModelLists.Add(CreateMapModel(RequestSecureKey, ROIController.EncryptOcSecurity(ROIController.DecryptAES(requestSecretWord))));
                }
            }

            serverOutputRequest.properties = mapModelLists.ToArray();

            // Fill SubmitInfo object
            SubmitInfo serverSubmitInfo = new SubmitInfo();
            serverSubmitInfo.application = OutputPropertyDetails.ROIContentSource;
            serverSubmitInfo.submitDate = DateTime.Now;
          //  serverSubmitInfo.submitDateSpecified = true;
            serverSubmitInfo.submitMachine = Environment.MachineName;
            serverSubmitInfo.submitterData = UserData.Instance.UserId;
            serverSubmitInfo.user = UserData.Instance.UserId;
            serverOutputRequest.submitInfo = serverSubmitInfo;

            serverOutputRequest.requestParts = serverRequestParts.ToArray(); // Add request parts
            serverOutputRequest.submitInfo = serverSubmitInfo;        // Add submitinfo
            serverOutputRequest.outputTransforms = serverOutputTransforms.ToArray(); // Add transforms 

            return serverOutputRequest;
        }

        private static string emailRequestParameters(Collection<PropertyDefinition> destinationProperties,
                                                PropertyDefinition propDef,
                                                String releaseDate,
                                                String requestId,
                                                String requestDate)
        {
            String indexValue = String.Empty;
            if (destinationProperties.Count > 0)
            {
                int indexProp = destinationProperties.IndexOf(propDef);
                indexValue = destinationProperties[indexProp].SelectedValue;
            }

            return replaceRequestParameters(indexValue,
                                             releaseDate,
                                             requestId,
                                             requestDate);
        }

        private static string replaceRequestParameters(string inputValue,
                                                String releaseDate,
                                                String requestId,
                                                String requestDate)
        {
            string rtnStr = string.Empty;
            if (!string.IsNullOrEmpty(inputValue))
            {
                rtnStr = inputValue;
                rtnStr = rtnStr.Replace(ROIConstants.EmailReleaseDate, releaseDate);
                rtnStr = rtnStr.Replace(ROIConstants.EmailRequestDate, requestDate);
                rtnStr = rtnStr.Replace(ROIConstants.EmailRequestId, requestId);
            }


            return rtnStr;
        }

        //private static property CreatePropertyMap(string name, string value)
        //{
        //    property property = new property();
        //    property.name = name;
        //    property.value = value;
        //    return property;
        //}

        private static MapModel CreateMapModel(string name, string value)
        {
            MapModel mapModel = new MapModel();
            mapModel.key = name;
            //CR#378189 Fix				
            mapModel.value = (value == null) ? string.Empty : value;
            return mapModel;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get the BillingController Instance
        /// </summary>
        public new static OutputController Instance
        {
            get
            {
                if (outputController == null)
                {
                    lock (syncRoot)
                    {
                        if (outputController == null)
                        {
                            outputController = new OutputController();
                        }
                    }
                }
                return outputController;
            }
        }

        #endregion
    }
}
