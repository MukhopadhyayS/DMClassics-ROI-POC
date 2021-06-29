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
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Handles BillingTemplate service invocations
    /// </summary>
    public partial class BillingAdminController
    {
        #region Methods

        #region Service Methods
       
        /// <summary>
        /// This method will creates a new billingTemplate
        /// </summary>
        /// <param name="billingTemplateDetails"> billingTemplate details which has to be created</param>
        /// <returns>The Newly Created billingTemplate Id</returns> 
        public long CreateBillingTemplate(BillingTemplateDetails billingTemplateDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateCreate(billingTemplateDetails))
            {
                throw validator.ClientException;
            }
            BillingTemplate serverBillingTemplate = MapModel(billingTemplateDetails);
            object[] requestParams = new object[] { serverBillingTemplate };
            object response = ROIHelper.Invoke(billingAdminService, "createBillingTemplate", requestParams);
            long billingTemplateId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

            return billingTemplateId;
        }

        /// <summary>
        /// This method will update an existing billingTemplate
        /// </summary>
        /// <param name="billingTemplateDetails">billingTemplate details which has to be updated.</param>
        /// <returns>Returns updated billingTemplate details</returns>
        public BillingTemplateDetails UpdateBillingTemplate(BillingTemplateDetails billingTemplateDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateUpdate(billingTemplateDetails))
            {
                throw validator.ClientException;
            }
            BillingTemplate serverBillingTemplate = MapModel(billingTemplateDetails);
            object[] requestParams = new object[] { serverBillingTemplate };
            ROIHelper.Invoke(billingAdminService, "updateBillingTemplate", requestParams);
            BillingTemplateDetails clientBillingTemplateDetails = MapModel((BillingTemplate)requestParams[0]);

            return clientBillingTemplateDetails;
        }

        /// <summary>
        /// Delete the selected Billing Template .
        /// </summary>
        /// <param name="billingTemplateIds">The array of the billingTemplate ids which has to be deleted</param>
        /// <returns>Returns an array of billingTemplate ids which were  deleted</returns>
        public void DeleteBillingTemplate(long billingTemplateId)
        {
            object[] requestParams = new object[] { billingTemplateId };
            ROIHelper.Invoke(billingAdminService, "deleteBillingTemplate", requestParams);
        }

        /// <summary>
        /// Returns a list of billingTemplate collection.
        /// </summary>
        /// <returns>List of BillingTemplates</returns>
        public Collection<BillingTemplateDetails> RetrieveAllBillingTemplates()
        {
            return RetrieveAllBillingTemplates(true);
        }

        public Collection<BillingTemplateDetails> RetrieveAllBillingTemplates(bool loadAssociation)
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllBillingTemplates", new object[] { loadAssociation });
            Collection<BillingTemplateDetails> billingTemplates = MapModel((BillingTemplate[])response);

            return billingTemplates;
        }
        /// <summary>
        /// Returns billingTemplate details for the given billingTemplateId.
        /// </summary>
        /// <param name="billingTemplateId">The associationId of the billingTemplate which has to be retrived</param>
        /// <returns>Returns a billingTemplate details </returns>
        public BillingTemplateDetails GetBillingTemplate(long billingTemplateId)
        {
            object[] requestParams = new object[] { billingTemplateId };
            object response = ROIHelper.Invoke(billingAdminService, "retrieveBillingTemplate", requestParams);
            BillingTemplateDetails clientBillingTemplateDetails = MapModel((BillingTemplate)response);

            return clientBillingTemplateDetails;
        }

        /// <summary>
        /// Returns the billing and payment information details
        /// </summary>
        /// <param name="requestorTypeId"></param>
        /// <returns></returns>
        public BillingPaymentInfoDetails RetrieveBillingAndPaymentInfo(long requestorTypeId)
        {
            BillingPaymentInfoDetails billingPaymentInfoDetails = new BillingPaymentInfoDetails();
            object response = ROIHelper.Invoke(billingAdminService, "retrieveBillingAndPaymentInfo", new object[] { requestorTypeId });
            billingPaymentInfoDetails = MapModel((BillingPaymentInfo)response);
            return billingPaymentInfoDetails;
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert server billingTemplate to client billingTemplate
        /// </summary>
        /// <param name="serverBillingTemplate"></param>
        /// <returns></returns>
        public static BillingTemplateDetails MapModel(BillingTemplate server)
        {
            if (server == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            BillingTemplateDetails client = new BillingTemplateDetails();
            client.Id              = server.id;
            client.Name            = server.name;
            client.IsAssociated    = server.associated;
            client.RecordVersionId = server.recordVersion;

            if (server.feeTypeIds != null)
            {
                AssociatedFeeType associatedFeeType;
                for (int index = 0; index <= server.feeTypeIds.Length - 1; index++)
                {
                    associatedFeeType           = new AssociatedFeeType();
                    associatedFeeType.FeeTypeId = server.feeTypeIds[index];
                    client.AssociatedFeeTypes.Add(associatedFeeType);
                }
            }
            else if (server.relatedFeeTypes != null)
            {
                AssociatedFeeType associatedFeeType;
                for (int index = 0; index <= server.relatedFeeTypes.Length - 1; index++)
                {
                    associatedFeeType = new AssociatedFeeType();      
              
                    associatedFeeType.FeeTypeId       = server.relatedFeeTypes[index].feeTypeId;
                    associatedFeeType.AssociationId   = server.relatedFeeTypes[index].id;
                    associatedFeeType.RecordVersionId = server.relatedFeeTypes[index].recordVersion;

                    client.AssociatedFeeTypes.Add(associatedFeeType);
                }
            }

            return client;
        }

        /// <summary>
        /// Convert client billingTemplate to server billingTemplate
        /// </summary>
        /// <param name="clientBillingTemplate"></param>
        /// <returns></returns>
        public static BillingTemplate MapModel(BillingTemplateDetails client)
        {
            if (client == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            BillingTemplate server = new BillingTemplate();
            server.id   = client.Id;
            server.name = client.Name;
            server.recordVersion = client.RecordVersionId;

            if (client.AssociatedFeeTypes != null)
            {
                server.relatedFeeTypes = new RelatedFeeType[client.AssociatedFeeTypes.Count];
                int index = 0;
                foreach (AssociatedFeeType associatedFeeType in client.AssociatedFeeTypes)
                {
                    server.relatedFeeTypes[index] = new RelatedFeeType();
                    server.relatedFeeTypes[index].id = associatedFeeType.AssociationId;
                    server.relatedFeeTypes[index].feeTypeId = associatedFeeType.FeeTypeId;
                    server.relatedFeeTypes[index].billingTemplateId = client.Id;
                    server.relatedFeeTypes[index].recordVersion = associatedFeeType.RecordVersionId;

                    index++;
                }
            }

            return server;
        }

        /// <summary>
        /// Convert server billingTemplatelist to client billingTemplatelist
        /// </summary>
        /// <param name="serverBillingTemplates"></param>
        /// <returns></returns>
        public static Collection<BillingTemplateDetails> MapModel(BillingTemplate[] serverBillingTemplates)
        {
            if (serverBillingTemplates == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<BillingTemplateDetails> clientBillingTemplates = new Collection<BillingTemplateDetails>();

            BillingTemplateDetails clientBillingTemplate;
            foreach (BillingTemplate serverBillingTemplate in serverBillingTemplates)
            {
                clientBillingTemplate = MapModel(serverBillingTemplate);
                clientBillingTemplates.Add(clientBillingTemplate);
            }

            return clientBillingTemplates;
        }

        /// <summary>
        /// converts the server to client model
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public static BillingPaymentInfoDetails MapModel(BillingPaymentInfo server)
        {
            BillingPaymentInfoDetails billingPaymentInfoDetails = new BillingPaymentInfoDetails();
            if (server.billingTemplateList != null)
            {
                Collection<BillingTemplateDetails> clientBillingTemplates = new Collection<BillingTemplateDetails>();

                BillingTemplateDetails clientBillingTemplate;
                foreach (BillingTemplate serverBillingTemplate in server.billingTemplateList)
                {
                    clientBillingTemplate = MapModel(serverBillingTemplate);
                    clientBillingTemplates.Add(clientBillingTemplate);
                }
                billingPaymentInfoDetails.BillingTemplateDetails = clientBillingTemplates;
            }

            if (server.feeTypeList != null)
            {
                Collection<FeeTypeDetails> clientFeeTypes = new Collection<FeeTypeDetails>();
                FeeTypeDetails clientFeeType;

                foreach (FeeType serverFeeType in server.feeTypeList)
                {
                    clientFeeType = MapModel(serverFeeType);
                    clientFeeTypes.Add(clientFeeType);
                }
                billingPaymentInfoDetails.FeeTypeDetails = clientFeeTypes;
            }

            if (server.paymentMethodList != null)
            {
                Collection<PaymentMethodDetails> clientPaymentMethods = new Collection<PaymentMethodDetails>();
                PaymentMethodDetails clientPaymentMethod;
                foreach (PaymentMethod serverPaymentMethod in server.paymentMethodList)
                {
                    clientPaymentMethod = MapModel(serverPaymentMethod);
                    clientPaymentMethods.Add(clientPaymentMethod);
                }

                billingPaymentInfoDetails.PaymentMethodDetails = clientPaymentMethods;
            }

            if (server.deliveryMethodList != null)
            {

                Collection<DeliveryMethodDetails> clientDeliveryMethods = new Collection<DeliveryMethodDetails>();

                DeliveryMethodDetails clientDeliveryMethod;
                foreach (DeliveryMethod serverDeliveryMethod in server.deliveryMethodList)
                {
                    clientDeliveryMethod = MapModel(serverDeliveryMethod);
                    clientDeliveryMethods.Add(clientDeliveryMethod);
                }
                billingPaymentInfoDetails.DeliveryMethodDetails = clientDeliveryMethods;
            }

            if (server.weight != null)
            {
                PageWeightDetails weightDetails = MapModel(server.weight);
                billingPaymentInfoDetails.PageWeightDetails = weightDetails;
            }

            if (server.countries != null)
            {
                List<CountryCodeDetails> CountryDetails = new List<CountryCodeDetails>();
                CountryDetails = MapModel(server.countries);
                billingPaymentInfoDetails.CountryCodeDetails = CountryDetails;
            }

            if (server.reasonsList != null)
            {
                Collection<ReasonDetails> reasonsDetails = MapModel(server.reasonsList);
                billingPaymentInfoDetails.ReasonDetails = reasonsDetails;
            }
            if (server.requestorType != null)
            {
                RequestorTypeDetails requestorTypeDetails = MapModel(server.requestorType);
                billingPaymentInfoDetails.RequestorTypeDetails = requestorTypeDetails;
            }
            return billingPaymentInfoDetails;
        }

        /// <summary>
        /// converts the server delivery method details to client delivery method details
        /// </summary>
        /// <param name="serverDeliveryMethod"></param>
        /// <returns></returns>
        public static DeliveryMethodDetails MapModel(DeliveryMethod serverDeliveryMethod)
        {
            if (serverDeliveryMethod == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            DeliveryMethodDetails clientDeliveryMethod = new DeliveryMethodDetails();
            clientDeliveryMethod.Id = serverDeliveryMethod.id;
            clientDeliveryMethod.IsDefault = serverDeliveryMethod.@default;
            clientDeliveryMethod.Name = serverDeliveryMethod.name;
            clientDeliveryMethod.Description = serverDeliveryMethod.description;
            if (serverDeliveryMethod.url != null && serverDeliveryMethod.url.Length > 0)
            {
                clientDeliveryMethod.Url = new Uri(serverDeliveryMethod.url);
            }
            clientDeliveryMethod.RecordVersionId = serverDeliveryMethod.recordVersion;

            return clientDeliveryMethod;
        }

        /// <summary>
        /// converts the server page weight details to client page weight details
        /// </summary>
        /// <param name="serverWeightDetail"></param>
        /// <returns></returns>
        private static PageWeightDetails MapModel(Weight serverWeightDetail)
        {
            PageWeightDetails clientWeightDetails = new PageWeightDetails();
            clientWeightDetails.Id = serverWeightDetail.id;
            clientWeightDetails.PageWeight = serverWeightDetail.unitPerMeasure;
            clientWeightDetails.RecordVersionId = serverWeightDetail.recordVersion;

            return clientWeightDetails;
        }

        /// <summary>
        /// converts the server country code details to client country code details
        /// </summary>
        /// <param name="server"></param>
        /// <returns></returns>
        public static List<CountryCodeDetails> MapModel(Country[] server)
        {
            List<CountryCodeDetails> client = new List<CountryCodeDetails>();
            CountryCodeDetails countryCode;
            foreach (Country count in server)
            {
                countryCode = new CountryCodeDetails();
                countryCode.CountryCode = count.countryCode;
                countryCode.CountryName = count.countryName;
                countryCode.DefaultCountry = count.defaultCountry;
                countryCode.CountrySeq = count.countrySeq;
                client.Add(countryCode);
            }
            return client;
        }

        /// <summary>
        /// converts the server reason details to client reason details
        /// </summary>
        /// <param name="serverReasons"></param>
        /// <returns></returns>
        public static Collection<ReasonDetails> MapModel(Reason[] serverReasons)
        {
            if (serverReasons == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<ReasonDetails> clientReasons = new Collection<ReasonDetails>();
            foreach (Reason serverReason in serverReasons)
            {
                clientReasons.Add(MapModel(serverReason));
            }

            return clientReasons;
        }

        /// <summary>
        /// converts the server reason details to client reason details
        /// </summary>
        /// <param name="serverReason"></param>
        /// <returns></returns>
        private static ReasonDetails MapModel(Reason serverReason)
        {
            ReasonDetails clientReasons = new ReasonDetails();
            clientReasons.Id = serverReason.id;
            clientReasons.Name = serverReason.name;
            clientReasons.DisplayText = serverReason.displayText;
            clientReasons.RecordVersionId = serverReason.recordVersion;
            clientReasons.Type = (ReasonType)Enum.Parse(typeof(ReasonType),
                                                                   serverReason.type, true);

            if (clientReasons.Type == ReasonType.Status)
            {
                clientReasons.RequestStatus = (RequestStatus)serverReason.status;
            }
            else if (clientReasons.Type == ReasonType.Request)
            {
                if (serverReason.tpo)
                {
                    clientReasons.Attribute = RequestAttr.Tpo;
                }
                else if (serverReason.nonTpo)
                {
                    clientReasons.Attribute = RequestAttr.NonTpo;
                }
            }
            return clientReasons;
        }

        /// <summary>
        /// converts the server requestor type details to client requestor type details
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
            clientRequestorType.RecordViewDetails = new RecordViewDetails();

            clientRequestorType.Id = serverRequestorType.requestorTypeId;
            clientRequestorType.Name = serverRequestorType.name;
            clientRequestorType.RecordViewDetails.Name = serverRequestorType.rv;
            clientRequestorType.RecordVersionId = serverRequestorType.recordVersion;
            clientRequestorType.IsAssociated = serverRequestorType.isAssociated;
            if (serverRequestorType.salesTax != null)
            {
                clientRequestorType.SalesTax = (serverRequestorType.salesTax.ToUpper(System.Threading.Thread.CurrentThread.CurrentCulture).Equals("Y")) ? ROIConstants.Yes : ROIConstants.No;
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
                    associatedBillingTemplate.BillingTemplateId = (id.HasValue) ? id.Value : 0;
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

        #endregion

        #endregion
    }
}
