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
        /// This method will create a new delivery method.
        /// </summary>
        /// <param name="deliveryMethodDetails"></param>
        /// <returns></returns>
        public long CreateDeliveryMethod(DeliveryMethodDetails deliveryMethodDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateCreate(deliveryMethodDetails))
            {
                throw validator.ClientException;
            }
            DeliveryMethod serverDeliveryMethod = MapModel(deliveryMethodDetails);
            object[] requestParams = new object[] { serverDeliveryMethod };
            object response = ROIHelper.Invoke(roiAdminService, "createDeliveryMethod", requestParams);
            long deliveryMethodId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

            return deliveryMethodId;
        }

        /// <summary>
        /// This method will update an existing delivery method.
        /// </summary>
        /// <param name="deliveryMethodDetails"></param>
        public DeliveryMethodDetails UpdateDeliveryMethod(DeliveryMethodDetails deliveryMethodDetails)
        {
            ROIAdminValidator validator = new ROIAdminValidator();
            if (!validator.ValidateUpdate(deliveryMethodDetails))
            {
                throw validator.ClientException;
            }
            DeliveryMethod serverDeliveryMethod = MapModel(deliveryMethodDetails);
            object[] requestParams = new object[] { serverDeliveryMethod };
            ROIHelper.Invoke(roiAdminService, "updateDeliveryMethod", requestParams);
            DeliveryMethodDetails clientDeliveryMethodDetails = MapModel((DeliveryMethod)requestParams[0]);

            return clientDeliveryMethodDetails;
        }

        /// <summary>
        /// Deletes Delivery method from database.
        /// </summary>
        /// <param name="deliveryMethodId"></param>
        public void DeleteDeliveryMethod(long deliveryMethodId)
        {
            object[] requestParams = new object[] { deliveryMethodId };
            ROIHelper.Invoke(roiAdminService, "deleteDeliveryMethod", requestParams);
        }

        /// <summary>
        /// Returns a list of DeliveryMethod.
        /// </summary>
        /// <returns></returns>
        public Collection<DeliveryMethodDetails> RetrieveAllDeliveryMethods()
        {
            return RetrieveAllDeliveryMethods(true);
        }

        /// <summary>
        /// Returns a list of DeliveryMethod.
        /// </summary>
        /// <param name="detailedFetch">true - the fields that required to be fetched, 
        /// false - name and id alone are to be fetched</param>
        /// <returns></returns>
        public Collection<DeliveryMethodDetails> RetrieveAllDeliveryMethods(bool detailedFetch)
        {
            object response = ROIHelper.Invoke(roiAdminService, "retrieveAllDeliveryMethods", new object[] { detailedFetch });
            Collection<DeliveryMethodDetails> deliveryMethods = MapModel((DeliveryMethod[])response);

            return deliveryMethods;
        }

        /// <summary>
        /// Returns DeliveryMethod Details for the given deliveryMethodId.
        /// </summary>
        /// <param name="DeliveryMethodId"></param>
        /// <returns></returns>
        public DeliveryMethodDetails GetDeliveryMethod(long deliveryMethodId)
        {
            object[] requestParams = new object[] { deliveryMethodId };
            object response = ROIHelper.Invoke(roiAdminService, "retrieveDeliveryMethod", requestParams);
            DeliveryMethodDetails clientDeliveryMethodDetails = MapModel((DeliveryMethod)response);

            return clientDeliveryMethodDetails;
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert Server Delivery Method to Client Delivery Method.
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
            clientDeliveryMethod.Id          = serverDeliveryMethod.id;
            clientDeliveryMethod.IsDefault   = serverDeliveryMethod.@default;
            clientDeliveryMethod.Name        = serverDeliveryMethod.name;
            clientDeliveryMethod.Description = serverDeliveryMethod.description;
            if (serverDeliveryMethod.url != null && serverDeliveryMethod.url.Length > 0)
            {
                clientDeliveryMethod.Url = new Uri(serverDeliveryMethod.url);
            }
            clientDeliveryMethod.RecordVersionId = serverDeliveryMethod.recordVersion;

            return clientDeliveryMethod;
        }

        /// <summary>
        /// Convert client delivery method to server delivery method.
        /// </summary>
        /// <param name="clientDeliveryMethod"></param>
        /// <returns></returns>
        public static DeliveryMethod MapModel(DeliveryMethodDetails clientDeliveryMethod)
        {
            if (clientDeliveryMethod == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            DeliveryMethod serverDeliveryMethod = new DeliveryMethod();

            serverDeliveryMethod.id           = clientDeliveryMethod.Id;
            serverDeliveryMethod.@default     = clientDeliveryMethod.IsDefault;
            serverDeliveryMethod.name         = clientDeliveryMethod.Name;
            serverDeliveryMethod.description  = clientDeliveryMethod.Description;
            if (clientDeliveryMethod.Url != null)
            {
                serverDeliveryMethod.url = clientDeliveryMethod.Url.OriginalString;
            }
            serverDeliveryMethod.recordVersion = clientDeliveryMethod.RecordVersionId;

            return serverDeliveryMethod;
        }

        /// <summary>
        /// Convert server delivery Method list to client delivery method list.
        /// </summary>
        /// <param name="serverDeliveryMethods"></param>
        /// <returns></returns>
        public static Collection<DeliveryMethodDetails> MapModel(DeliveryMethod[] serverDeliveryMethods)
        {
            if (serverDeliveryMethods == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<DeliveryMethodDetails> clientDeliveryMethods = new Collection<DeliveryMethodDetails>();

            DeliveryMethodDetails clientDeliveryMethod;
            foreach (DeliveryMethod serverDeliveryMethod in serverDeliveryMethods)
            {
                clientDeliveryMethod = MapModel(serverDeliveryMethod);
                clientDeliveryMethods.Add(clientDeliveryMethod);
            }

            return clientDeliveryMethods;
        }

        #endregion

        #endregion
    }
}
