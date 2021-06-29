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

using McK.EIG.ROI.Client.Admin.Model;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Web_References.ROIAdminWS;


namespace McK.EIG.ROI.Client.Admin.Controller
{
    public partial class ROIAdminController
    {
        
        #region Methods

        #region Service Methods

        /// <summary>
        /// This method will creates a new requestor type
        /// </summary>
        /// <param name="requestorTypeDetails"> requestor type details which has to be created</param>
        /// <returns>The Newly Created requestortype Id</returns> 
        public RequestorTypeDetails CreateRequestorType(RequestorTypeDetails requestorTypeDetails)
        {        
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateCreate(requestorTypeDetails))
            {
                throw validator.ClientException;
            }

            RequestorType serverRequestorType = MapModel(requestorTypeDetails);
            object[] requestParams            = new object[] { serverRequestorType };
            ROIHelper.Invoke(roiAdminService, "createRequestorType", requestParams);
            RequestorTypeDetails clientRequestorTypeDetails = MapModel((RequestorType)requestParams[0]);
           
            return clientRequestorTypeDetails;
        }

        /// <summary>
        /// This method will update an existing requesto rtype
        /// </summary>
        /// <param name="requestorTypeDetails">requestor type details which has to be updated.</param>
        /// <returns>Returns updated requestortype details</returns>
        public RequestorTypeDetails UpdateRequestorType(RequestorTypeDetails requestorTypeDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdate(requestorTypeDetails))
            {
                throw validator.ClientException;
            }

            RequestorType serverRequestorType = MapModel(requestorTypeDetails);
            object[] requestParams            = new object[] { serverRequestorType };
            ROIHelper.Invoke(roiAdminService, "updateRequestorType", requestParams);

            RequestorTypeDetails clientRequestorTypeDetails = MapModel((RequestorType)requestParams[0]);

            return clientRequestorTypeDetails;
        }

        /// <summary>
        /// Deletes the specified requestor type from the database.
        /// </summary>
        /// <param name="requestorTypeId"> The requestortype id which has to be deleted</param>        
        public void DeleteRequestorType(long requestorTypeId)
        {
            object[] requestParams = new object[] { requestorTypeId };
            ROIHelper.Invoke(roiAdminService, "deleteRequestorType", requestParams);
        }
     
        /// <summary>
        /// Returns a list of requestor type.
        /// </summary>
        /// <returns></returns>
        public Collection<RequestorTypeDetails> RetrieveAllRequestorTypes(bool loadAssociations)
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAllRequestorTypes", new object[] { loadAssociations });            
            Collection<RequestorTypeDetails> requestorTypes = MapModel((RequestorType[])response);
            return requestorTypes;
        }

        public Collection<RequestorTypeDetails> RetrieveAllRequestorTypes()
        {
            return RetrieveAllRequestorTypes(true);
        }
       
        /// <summary>
        /// Returns requestor type details for the given requestor type id.
        /// </summary>
        /// <param name="requestorTypeId"></param>
        /// <returns></returns>    
        public RequestorTypeDetails GetRequestorType(long requestorTypeId)
        {
            object[] requestParams = new object[] { requestorTypeId };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveRequestorType", requestParams);
            RequestorTypeDetails clientRequestorTypeDetails = MapModel((RequestorType)response);

            return clientRequestorTypeDetails;
        }
        
        #endregion

        #region Model Mapping

        /// <summary>
        /// Convert client requestor type to server requestor type
        /// </summary>
        /// <param name="clientMediaType"></param>
        /// <returns></returns>
        public static RequestorType MapModel(RequestorTypeDetails clientRequestorType)
        {
            if (clientRequestorType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            RequestorType serverRequestorType = new RequestorType();

            serverRequestorType.requestorTypeId     = clientRequestorType.Id;
            serverRequestorType.recordVersion       = clientRequestorType.RecordVersionId;
            serverRequestorType.name                = clientRequestorType.Name;
            serverRequestorType.rv                  = clientRequestorType.RecordViewDetails.Name;
            serverRequestorType.rvDesc              = clientRequestorType.RecordViewDetails.Name;
            serverRequestorType.relatedBillingTier  = PrepareBillingTiersForRequestorType(clientRequestorType);
            serverRequestorType.salesTax            = clientRequestorType.SalesTax.ToLower(System.Threading.Thread.CurrentThread.CurrentCulture).Equals("yes") ? "Y" : "N";
            serverRequestorType.invoiceOptional     = clientRequestorType.InvoiceOptional.ToLower(System.Threading.Thread.CurrentThread.CurrentCulture).Equals("yes") ? "Y" : "N";


            if (clientRequestorType.AssociatedBillingTemplates != null)
            {
                serverRequestorType.relatedBillingTemplate
                                                    = new RelatedBillingTemplate[clientRequestorType.AssociatedBillingTemplates.Count];
                int index = 0;

                foreach (AssociatedBillingTemplate AssociatedBillingTemplate in clientRequestorType.AssociatedBillingTemplates)
                {
                    serverRequestorType.relatedBillingTemplate[index] = new RelatedBillingTemplate();
                    serverRequestorType.relatedBillingTemplate[index].billingTemplateId = AssociatedBillingTemplate.BillingTemplateId;
                    serverRequestorType.relatedBillingTemplate[index].relatedBillingTemplateId = AssociatedBillingTemplate.AssociationId;
                    serverRequestorType.relatedBillingTemplate[index].recordVersion = AssociatedBillingTemplate.RecordVersionId;
                    serverRequestorType.relatedBillingTemplate[index].isDefault = AssociatedBillingTemplate.IsDefault;
                    index++;
                }
            }
            return serverRequestorType;
        }

        /// <summary>
        /// Convert server requestor type to client requestor type
        /// </summary>
        /// <param name="serverRequestorType"></param>
        /// <returns></returns>
        public static RequestorTypeDetails MapModel(RequestorType serverRequestorType)
        {
            if (serverRequestorType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
          
            RequestorTypeDetails clientRequestorType = new RequestorTypeDetails();
            clientRequestorType.RecordViewDetails    = new RecordViewDetails();
            
            clientRequestorType.Id                      = serverRequestorType.requestorTypeId;
            clientRequestorType.Name                    = serverRequestorType.name;
            clientRequestorType.RecordViewDetails.Name  = serverRequestorType.rv;
            clientRequestorType.RecordVersionId         = serverRequestorType.recordVersion;
            clientRequestorType.IsAssociated            = serverRequestorType.isAssociated;
            if (serverRequestorType.salesTax != null)
            {
                clientRequestorType.SalesTax = (serverRequestorType.salesTax.ToUpper(System.Threading.Thread.CurrentThread.CurrentCulture).Equals("Y")) ? ROIConstants.Yes : ROIConstants.No;
            }
            if (serverRequestorType.invoiceOptional != null)
            {
                clientRequestorType.InvoiceOptional = (serverRequestorType.invoiceOptional.ToUpper(System.Threading.Thread.CurrentThread.CurrentCulture).Equals("Y")) ? ROIConstants.Yes : ROIConstants.No;
            }

            if (serverRequestorType.relatedBillingTier != null)
            {
                foreach (RelatedBillingTier releatedBillingTier in serverRequestorType.relatedBillingTier)
                {
                    if (releatedBillingTier.isHPF)
                    {
                        clientRequestorType.HpfBillingTier = new BillingTierDetails();
                        clientRequestorType.HpfBillingTier.Id = releatedBillingTier.billingTierId;
                    }
                    else if (releatedBillingTier.isSupplemental)
                             
                    {
                        clientRequestorType.NonHpfBillingTier = new BillingTierDetails();
                        clientRequestorType.NonHpfBillingTier.Id = releatedBillingTier.billingTierId;
                    }
                }
            }
            
            if (serverRequestorType.billingTemplateIds != null)
            {
                AssociatedBillingTemplate associatedBillingTemplate;
                for (int index = 0; index <= serverRequestorType.billingTemplateIds.Length - 1; index++)
                {
                    associatedBillingTemplate = new AssociatedBillingTemplate();

                    long? id = serverRequestorType.billingTemplateIds[index];
                    associatedBillingTemplate.BillingTemplateId = (id.HasValue) ? id.Value : 0 ;
                    clientRequestorType.AssociatedBillingTemplates.Add(associatedBillingTemplate);
                }
            }
            else if (serverRequestorType.relatedBillingTemplate != null)
            {
                AssociatedBillingTemplate associatedBillingTemplate;
                for (int index = 0; index <= serverRequestorType.relatedBillingTemplate.Length - 1; index++)
                {
                    associatedBillingTemplate = new AssociatedBillingTemplate();

                    associatedBillingTemplate.BillingTemplateId = serverRequestorType.relatedBillingTemplate[index].billingTemplateId;
                    associatedBillingTemplate.AssociationId = serverRequestorType.relatedBillingTemplate[index].relatedBillingTemplateId;
                    associatedBillingTemplate.RecordVersionId = serverRequestorType.relatedBillingTemplate[index].recordVersion;
                    associatedBillingTemplate.IsDefault = serverRequestorType.relatedBillingTemplate[index].isDefault;

                    clientRequestorType.AssociatedBillingTemplates.Add(associatedBillingTemplate);                    
                }
            }
            return clientRequestorType;
        }

        /// <summary>
        /// Convert server RequestorTypelist to client RequestorTypelist
        /// </summary>
        /// <param name="serverBillingTiers"></param>
        /// <returns></returns>
        public static Collection<RequestorTypeDetails> MapModel(RequestorType[] serverRequestorTypes)
        {
            if (serverRequestorTypes == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<RequestorTypeDetails> clientRequestorTypes = new Collection<RequestorTypeDetails>();
            RequestorTypeDetails clientRequestorType;
            foreach (RequestorType serverRequestorType in serverRequestorTypes)
            {
                clientRequestorType = MapModel(serverRequestorType);
                clientRequestorTypes.Add(clientRequestorType);
            }
            return clientRequestorTypes;
        }

        private static RelatedBillingTier[] PrepareBillingTiersForRequestorType(RequestorTypeDetails requestorType)
        {
            List<RelatedBillingTier> relatedBillingTiers = new List<RelatedBillingTier>();

            RelatedBillingTier hpfBillingTier = new RelatedBillingTier();
            hpfBillingTier.billingTierId = requestorType.HpfBillingTier.Id;
            hpfBillingTier.isHPF = true;
            relatedBillingTiers.Add(hpfBillingTier);

            RelatedBillingTier nonHpfBillingTier = new RelatedBillingTier();
            nonHpfBillingTier.billingTierId = requestorType.NonHpfBillingTier.Id;
            nonHpfBillingTier.isSupplemental = true;
            relatedBillingTiers.Add(nonHpfBillingTier);

            return relatedBillingTiers.ToArray();
        }

        #endregion

        #endregion
    }
}
