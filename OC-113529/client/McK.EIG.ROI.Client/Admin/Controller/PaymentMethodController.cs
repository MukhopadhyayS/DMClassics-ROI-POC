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
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Handles Paymentmethods service invocations
    /// </summary>
    public partial class BillingAdminController
    {
        #region Methods

        #region Service Methods

        /// <summary>
        /// Method to create a new PaymentMethod
        /// </summary>
        /// <param name="PaymentMethodDetails"> PaymentMethod details which has to be created</param>
        /// <returns>The Newly Created PaymentMethod Id</returns> 
        public long CreatePaymentMethod(PaymentMethodDetails paymentMethodDetails)
        {
            BillingAdminValidator validator     = new BillingAdminValidator();
            if (!validator.ValidateCreate(paymentMethodDetails))
            {
                throw validator.ClientException;
            }
            PaymentMethod serverPaymentMethod = MapModel(paymentMethodDetails);
            object[] requestParams  = new object[] { serverPaymentMethod };
            object response         = ROIHelper.Invoke(billingAdminService, "createPaymentMethod", requestParams);
            long paymentMethodId    = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);
            
            return paymentMethodId;
        }

        /// <summary>
        /// Method to update an existing PaymentMethod
        /// </summary>
        /// <param name="PaymentMethodDetails">PaymentMethod details to update.</param>
        public PaymentMethodDetails UpdatePaymentMethod(PaymentMethodDetails paymentMethodDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateUpdate(paymentMethodDetails))
            {
                throw validator.ClientException;
            }
            PaymentMethod serverPaymentMethod   = MapModel(paymentMethodDetails);
            object[] requestParams              = new object[] { serverPaymentMethod };
            ROIHelper.Invoke(billingAdminService, "updatePaymentMethod", requestParams);
            PaymentMethodDetails clientPaymentMethodDetails = MapModel((PaymentMethod)requestParams[0]);
            
            return clientPaymentMethodDetails;
        }

        /// <summary>
        /// Method to delete an existing PaymentMethod.
        /// </summary>
        /// <param name="PaymentMethodIds">PaymentMethod id to delete</param>
        public void DeletePaymentMethod(long paymentMethodId)
        {
            object[] requestParams = new object[] { paymentMethodId };
            ROIHelper.Invoke(billingAdminService, "deletePaymentMethod", requestParams);
        }

        /// <summary>
        /// Returns a list of PaymentMethods.
        /// </summary>
        /// <returns>List of PaymentMethods</returns>
        public Collection<PaymentMethodDetails> RetrieveAllPaymentMethods()
        {
            return RetrieveAllPaymentMethods(true);
        }

        /// <summary>
        /// Returns a list of PaymentMethods.
        /// </summary>
        /// <param name="detailedFetch">true - the fields that required to be fetched, 
        /// false - name and id alone are to be fetched</param>
        /// <returns></returns>
        public Collection<PaymentMethodDetails> RetrieveAllPaymentMethods(bool detailedFetch)
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllPaymentMethods", new object[] { detailedFetch });
            Collection<PaymentMethodDetails> paymentMethods = MapModel((PaymentMethod[])response);
            
            return paymentMethods;
        }

        /// <summary>
        /// Returns PaymentMethod details for the given PaymentMethodId.
        /// </summary>
        /// <param name="PaymentMethodId">The id of the PaymentMethod which has to be retrieved</param>
        /// <returns>Returns a PaymentMethod details </returns>
        public PaymentMethodDetails GetPaymentMethod(long paymentMethodId)
        {
            object[] requestParams  = new object[] { paymentMethodId };
            object response         = ROIHelper.Invoke(billingAdminService, "retrievePaymentMethod", requestParams);
            PaymentMethodDetails clientPaymentMethodDetails = MapModel((PaymentMethod)response);
            
            return clientPaymentMethodDetails;
        }

        #endregion

        #region Model Mapper

        /// <summary>
        /// Convert client to server paymentMethod
        /// </summary>
        /// <param name="clientPaymentMethod"></param>
        /// <returns></returns>
        public static PaymentMethod MapModel(PaymentMethodDetails clientPaymentMethod)
        {
            if (clientPaymentMethod == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            PaymentMethod serverPaymentMethod   = new PaymentMethod();
            serverPaymentMethod.paymentMethodId = clientPaymentMethod.Id;
            serverPaymentMethod.name            = clientPaymentMethod.Name;
            serverPaymentMethod.description     = clientPaymentMethod.Description;
            serverPaymentMethod.display         = clientPaymentMethod.IsDisplay;
            serverPaymentMethod.recordVersion   = clientPaymentMethod.RecordVersionId;
        
            return serverPaymentMethod;
        }

        /// <summary>
        /// Convert server to client paymentMethod
        /// </summary>
        /// <param name="serverPaymentMethod"></param>
        /// <returns></returns>
        public static PaymentMethodDetails MapModel(PaymentMethod serverPaymentMethod)
        {
            if (serverPaymentMethod == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            PaymentMethodDetails clientPaymentMethod = new PaymentMethodDetails();
            clientPaymentMethod.Id                   = serverPaymentMethod.paymentMethodId;
            clientPaymentMethod.Name                 = serverPaymentMethod.name;
            clientPaymentMethod.Description          = serverPaymentMethod.description;
            clientPaymentMethod.IsDisplay            = serverPaymentMethod.display;
            clientPaymentMethod.RecordVersionId      = serverPaymentMethod.recordVersion;
            
            return clientPaymentMethod;
        }

        /// <summary>
        /// Convert server to client PaymentMethodlist
        /// </summary>
        /// <param name="serverPaymentMethods"></param>
        /// <returns></returns>
        public static Collection<PaymentMethodDetails> MapModel(PaymentMethod[] serverPaymentMethods)
        {
            if (serverPaymentMethods == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            Collection<PaymentMethodDetails> clientPaymentMethods = new Collection<PaymentMethodDetails>();
            PaymentMethodDetails clientPaymentMethod;
            foreach (PaymentMethod serverPaymentMethod in serverPaymentMethods)
            {
                clientPaymentMethod = MapModel(serverPaymentMethod);
                clientPaymentMethods.Add(clientPaymentMethod);
            }

            return clientPaymentMethods;
        }

        #endregion

        #endregion
    }
}
