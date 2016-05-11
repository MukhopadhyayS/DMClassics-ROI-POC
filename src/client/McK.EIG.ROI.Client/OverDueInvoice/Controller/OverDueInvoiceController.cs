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
using System.Collections;
using System.Collections.Generic;
using System.Globalization;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.OverDueInvoice.Model;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.Client.Web_References.OverDueInvoiceCoreWS;

namespace McK.EIG.ROI.Client.OverDueInvoice.Controller
{
    /// <summary>
    /// PastDueInvoiceController
    /// </summary>
    public class OverDueInvoiceController : ROIController
    {
        #region Fields

        private OverdueInvoiceCoreServiceWse overDueInvoiceCoreService;

        #endregion

        #region Constructor

        private OverDueInvoiceController()
        {
            overDueInvoiceCoreService = new OverdueInvoiceCoreServiceWse();
        }

        #endregion        

        #region Methods

        #region ServiceMethods
        /// <summary>
        /// Find all the invoices that are overdue.
        /// </summary>
        /// <param name="searchCriteria"></param>
        /// <returns></returns>
        public OverDueInvoiceSearchResult FindOverDueInvoices(OverDueInvoiceSearchCriteria searchCriteria)
        {
            OverDueInvoiceValidator OverDueInvoiceValidator = new OverDueInvoiceValidator();

            //CR#359163
            if ((!OverDueInvoiceValidator.ValidateOverDueInvoiceSearch(searchCriteria)) 
                 || (!OverDueInvoiceValidator.ValidateSearchFields(searchCriteria)))
            {
                throw OverDueInvoiceValidator.ClientException;
            }

            SearchPastDueInvoiceCriteria serverSearchCriteria = MapModel(searchCriteria);
            object[] roiRequestParams = new object[] { serverSearchCriteria };
            object response = ROIHelper.Invoke(overDueInvoiceCoreService, "searchOverDueInvoices", roiRequestParams);
            OverDueInvoiceSearchResult result = new OverDueInvoiceSearchResult();
            if (response != null)
            {
                result = MapModel((SearchPastDueInvoiceResult)response);

                if (result.MaxCountExceeded)
                {
                    result.SearchResult.RemoveAt(searchCriteria.MaxRecord);
                }
            }
            return result;
        }

        /// <summary>
        /// Preview all overdue invoices.
        /// </summary>
        /// <param name="clientInvoiceList"></param>
        /// <returns></returns>
        public DocumentInfoList PreviewOverDueInvoices(PreviewOverDueInvoices clientInvoiceList)
        {
            InvoiceAndLetterInfo serverInvoiceAndLetterInfo = MapModel(clientInvoiceList);
            object[] requestParams = new object[] { serverInvoiceAndLetterInfo, clientInvoiceList.IsPreview };
            object response = ROIHelper.Invoke(overDueInvoiceCoreService, "regenerateInvoiceCoreAndLetter", requestParams);
            return MapModel((OverDueDocInfoList)response);
        }       

        #endregion

        #region Model Mapping

        /// <summary>
        /// Converts the client model into server.
        /// </summary>
        /// <param name="previewOverDueInvoices"></param>
        /// <returns></returns>
        public static InvoiceAndLetterInfo MapModel(PreviewOverDueInvoices previewOverDueInvoices)
        {   
            InvoiceAndLetterInfo serverInvoiceAndLetterInfo = new InvoiceAndLetterInfo();
            serverInvoiceAndLetterInfo.invoiceTemplateId = previewOverDueInvoices.InvoiceTemplateId;
            serverInvoiceAndLetterInfo.requestorLetterTemplateId = previewOverDueInvoices.RequestorLetterTemplateId;
            serverInvoiceAndLetterInfo.reqLetterNotes = previewOverDueInvoices.RequestorLetterNotes.ToArray();
            serverInvoiceAndLetterInfo.invoiceNotes = previewOverDueInvoices.InvoiceNotes.ToArray();
            serverInvoiceAndLetterInfo.outputInvoice = previewOverDueInvoices.IsOutputInvoice;
            if (previewOverDueInvoices.RequestorStatementDetail.DateRange != McK.EIG.ROI.Client.Requestors.Model.DateRange.None)
            {
                serverInvoiceAndLetterInfo.statementCriteria = new RequestorStatementCriteria();
                serverInvoiceAndLetterInfo.statementCriteria.dateRange = (McK.EIG.ROI.Client.Web_References.OverDueInvoiceCoreWS.DateRange)Enum.Parse(typeof(McK.EIG.ROI.Client.Web_References.OverDueInvoiceCoreWS.DateRange), previewOverDueInvoices.RequestorStatementDetail.DateRange.ToString());
            }
             
            if ((previewOverDueInvoices.PropertiesMap != null) && (previewOverDueInvoices.PropertiesMap.Count > 0))
            {
                List<propertiesMap> properties = new List<propertiesMap>();
                IDictionaryEnumerator propertyList = previewOverDueInvoices.PropertiesMap.GetEnumerator();
                while (propertyList.MoveNext())
                {
                    propertiesMap propertiesMap = new propertiesMap();
                    propertiesMap.name = propertyList.Key.ToString();
                    propertiesMap.value = propertyList.Value.ToString();
                    properties.Add(propertiesMap);
                }
                serverInvoiceAndLetterInfo.properties = properties.ToArray();
            }          

            List<RequestorInvoices> serverRequestorInvoices = new List<RequestorInvoices>();
            RequestorInvoices serverRequestorInvoiceDetails;
            foreach (RequestorInvoicesDetails requestorInvoicesDetails in previewOverDueInvoices.RequestorInvoicesList)
            {
                serverRequestorInvoiceDetails = new RequestorInvoices();
                serverRequestorInvoiceDetails.requestorId = requestorInvoicesDetails.RequestorId;
                serverRequestorInvoiceDetails.requestorName = requestorInvoicesDetails.RequestorName;
                serverRequestorInvoiceDetails.requestorType = requestorInvoicesDetails.RequestorType;
                serverRequestorInvoiceDetails.invoiceIds = ((List<long>)requestorInvoicesDetails.InvoiceIds).ToArray();
                serverRequestorInvoices.Add(serverRequestorInvoiceDetails);
            }
            serverInvoiceAndLetterInfo.invoices = serverRequestorInvoices.ToArray();
            return serverInvoiceAndLetterInfo;
        }

        /// <summary>
        /// Converts the server model into client.
        /// </summary>
        /// <param name="documentInfos"></param>
        /// <returns></returns>
        private static DocumentInfoList MapModel(OverDueDocInfoList documentInfos)
        {
            DocumentInfoList documentInfoList = new DocumentInfoList();
            documentInfoList.Name = documentInfos.name;
            foreach (OverDueDocInfo docInfo in documentInfos.overDueDocInfos)
            {
                documentInfoList.DocumentInfoCollection.Add(MapModel(docInfo));
            }
            return documentInfoList;
        }

        /// <summary>
        /// Converts the server model into client.
        /// </summary>
        /// <param name="documentInfo"></param>
        /// <returns></returns>
        private static DocumentInfo MapModel(OverDueDocInfo overDuedocumentInfo)
        {
            DocumentInfo clientDocumentInfo = new DocumentInfo();

            clientDocumentInfo.Id = overDuedocumentInfo.id;
            clientDocumentInfo.Name = overDuedocumentInfo.name;
            clientDocumentInfo.Type = overDuedocumentInfo.type;
            clientDocumentInfo.RequestorGroupingKey = overDuedocumentInfo.requestorGroupingKey;

            return clientDocumentInfo;
        }

        /// <summary>
        /// Convert server model into client.
        /// </summary>
        /// <param name="serverSearchResult"></param>
        /// <returns></returns>
        private static OverDueInvoiceSearchResult MapModel(SearchPastDueInvoiceResult serverSearchResult)
        {
            OverDueInvoiceSearchResult clientSearchResult = new OverDueInvoiceSearchResult();
            clientSearchResult.MaxCountExceeded = serverSearchResult.maxCountExceeded;

            PastDueInvoice[] pastDueInvoiceResult = serverSearchResult.PastDueInvoiceList;
            OverDueInvoiceDetails overDueInvoiceDetails;
            if (pastDueInvoiceResult == null)
            {
                return clientSearchResult;
            }

            foreach (PastDueInvoice pastDueInvoice in pastDueInvoiceResult)
            {
                overDueInvoiceDetails = new OverDueInvoiceDetails();

                overDueInvoiceDetails.Id              = pastDueInvoice.invoiceNumber;
                overDueInvoiceDetails.InvoiceNumber   = pastDueInvoice.invoiceNumber;
                overDueInvoiceDetails.BillingLocation = pastDueInvoice.billingLocation;
                overDueInvoiceDetails.RequestorId     = pastDueInvoice.requestorId;
                overDueInvoiceDetails.RequestorType   = pastDueInvoice.requestorType;
                overDueInvoiceDetails.RequestorName   = pastDueInvoice.requestorName;
                overDueInvoiceDetails.PhoneNumber     = pastDueInvoice.phoneNumber;
                overDueInvoiceDetails.RequestId       = pastDueInvoice.requestId;
                overDueInvoiceDetails.OverDueAmount   = pastDueInvoice.overdueAmount;
                overDueInvoiceDetails.OverDueDays     = pastDueInvoice.overDueDays;
                overDueInvoiceDetails.InvoiceAge      = pastDueInvoice.invoiceAge;

                clientSearchResult.SearchResult.Add(overDueInvoiceDetails);
            }
            return clientSearchResult;
        }

        /// <summary>
        /// Convert Client Requestor Search Criteria to server Requestor Search Criteria.
        /// </summary>
        /// <param name="clientSearchCriteria"></param>
        /// <returns></returns>
        private static SearchPastDueInvoiceCriteria MapModel(OverDueInvoiceSearchCriteria client)
        {
            SearchPastDueInvoiceCriteria server = new SearchPastDueInvoiceCriteria();

            server.billingLocations = new string[client.FacilityCode.Count];
            client.FacilityCode.CopyTo(server.billingLocations, 0);

            server.requestorTypes = new long[client.RequestorType.Count];
            client.RequestorType.CopyTo(server.requestorTypes, 0);

            server.requestorTypeNames = new string[client.RequestorTypeName.Count];
            client.RequestorTypeName.CopyTo(server.requestorTypeNames, 0);

            server.requestorName = client.RequestorName;
            
            server.overDueFrom = client.From;
            server.overDueTo = client.To;

            if (client.To <= 120)
            {
                server.overDueToSpecified = true;
                server.overDueRestriction = OverDueRestriction.BETWEEN;
            }
            else
            {
                server.overDueToSpecified = false;
                server.overDueRestriction = OverDueRestriction.GREATER;
            }
            return server;
        }

        #endregion

        #endregion

        #region Properties

        private static object syncRoot = new Object();
        private static volatile OverDueInvoiceController pastDueInvoiceController;

        /// <summary>
        /// Gets the PastDueInvoiceController Instance
        /// </summary>
        public new static OverDueInvoiceController Instance
        {
            get
            {
                if (pastDueInvoiceController == null)
                {
                    lock (syncRoot)
                    {
                        if (pastDueInvoiceController == null)
                        {
                            pastDueInvoiceController = new OverDueInvoiceController();
                        }
                    }
                }
                return pastDueInvoiceController;
            }
        }

        #endregion
    }
}
