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
using System.Text;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Web_References.BillingAdminWS;

namespace McK.EIG.ROI.Client.Admin.Controller
{
    /// <summary>
    /// Handles Mediaype service invocations
    /// </summary>
    public partial class BillingAdminController
    {
        #region Service Methods

        /// <summary>
        /// Creates a new Fee Type.
        /// </summary>
        /// <param name="feeTypeDetails"> Fee Type information to be created</param>
        /// <returns>New Fee Type Id</returns>
        public long CreateFeeType(FeeTypeDetails feeTypeDetails)
        {
            
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateCreate(feeTypeDetails))
            {
                throw validator.ClientException;
            }
            FeeType serverFeeType = MapModel(feeTypeDetails);
            object[] requestParams = new object[] { serverFeeType };
            object response = ROIHelper.Invoke(billingAdminService, "createFeeType", requestParams);
            long feeTypeId = Convert.ToInt64(response, System.Threading.Thread.CurrentThread.CurrentCulture);

            return feeTypeId;
        }

        /// <summary>
        /// Updates an existing Fee Type.
        /// </summary>
        /// <param name="feeTypeDetails">Fee Type information to be updated.</param>

        public FeeTypeDetails UpdateFeeType(FeeTypeDetails feeTypeDetails)
        {
            BillingAdminValidator validator = new BillingAdminValidator();
            if (!validator.ValidateUpdate(feeTypeDetails))
            {
                throw validator.ClientException;
            }
            FeeType serverFeeType = MapModel(feeTypeDetails);
            object[] requestParams = new object[] { serverFeeType };
            ROIHelper.Invoke(billingAdminService, "updateFeeType", requestParams);
            FeeTypeDetails clientFeeTypeDetails = MapModel((FeeType)requestParams[0]);
            
            return clientFeeTypeDetails;
        }

        /// <summary>
        /// Deleltes selected Fee Type.
        /// </summary>
        /// <param name="id">Selected Fee Type Id</param>
        public void DeleteFeeType(long id)
        {
            object[] requestParams = new object[] { id };
            ROIHelper.Invoke(billingAdminService, "deleteFeeType", requestParams);
        }

        /// <summary>
        ///  Returns the selected Fee Type id detials.
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>

        public FeeTypeDetails RetrieveFeeType(long id)
        {
            object[] requestParams = new object[] { id };
            object response = ROIHelper.Invoke(billingAdminService, "retrieveFeeType", requestParams);
            FeeTypeDetails clientFeeTypeDetails = MapModel((FeeType) response);
            
            return clientFeeTypeDetails;
        }

        /// <summary>
        /// Retrieves all FeeTypes.
        /// </summary>
        /// <returns></returns>
        public Collection<FeeTypeDetails> RetrieveAllFeeTypes()
        {
            return RetrieveAllFeeTypes(true);
        }

        /// <summary>
        /// Retrieves all FeeTypes.
        /// </summary>
        /// <returns></returns>
        public Collection<FeeTypeDetails> RetrieveAllFeeTypes(bool detailedFetch)
        {
            object response = ROIHelper.Invoke(billingAdminService, "retrieveAllFeeTypes", new object[] { detailedFetch });
            Collection<FeeTypeDetails> feeTypes = MapModel((FeeType[])response);
            
            return feeTypes;
        }

        #endregion

        #region Model Mapping
        /// <summary>
        /// Converts from server Fee Type object to client Fee Type object.
        /// </summary>
        /// <param name="serverFeeType">Server Fee Type details</param>
        /// <returns>Client Fee Type details</returns>
        public static FeeTypeDetails MapModel(FeeType serverFeeType)
        {
            if (serverFeeType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            FeeTypeDetails clientFeeType = new FeeTypeDetails();

            clientFeeType.Id              = serverFeeType.feeTypeId;
            clientFeeType.Name            = serverFeeType.name;
            clientFeeType.Description     = serverFeeType.description;
            clientFeeType.Amount          = serverFeeType.chargeAmount;
            clientFeeType.IsAssociated    = serverFeeType.isAssociated;
            clientFeeType.RecordVersionId = serverFeeType.recordVersion;
            if (!string.IsNullOrEmpty(serverFeeType.salesTax))
            {
                clientFeeType.SalesTax = string.Compare(serverFeeType.salesTax, "Y", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 ? ROIConstants.Yes : ROIConstants.No;                
            }
            else
            {
                clientFeeType.SalesTax = ROIConstants.No;
            }
            
            return clientFeeType;
        }

        /// <summary>
        /// Converts from client Fee Type object to server Fee Type object.
        /// </summary>
        /// <param name="serverFeeType">Server Fee Type details</param>
        /// <returns>Server Fee Type details</returns>
        public static FeeType MapModel(FeeTypeDetails clientFeeType)
        {
            if (clientFeeType == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }
            else
            {
                FeeType serverFeeType = new FeeType();

                serverFeeType.feeTypeId = clientFeeType.Id;
                serverFeeType.name = clientFeeType.Name;
                serverFeeType.description = clientFeeType.Description;
                serverFeeType.chargeAmount = clientFeeType.Amount;
                serverFeeType.isAssociated = clientFeeType.IsAssociated;
                serverFeeType.recordVersion = clientFeeType.RecordVersionId;
                serverFeeType.salesTax = string.Compare(clientFeeType.SalesTax, "yes", true, System.Threading.Thread.CurrentThread.CurrentCulture) == 0 ? "Y" : "N";
                return serverFeeType;
            }
        }

        /// <summary>
        /// Converts a list of server Fee Type objects to client Fee Type object. 
        /// </summary>
        /// <param name="serverFeeTypes"></param>
        /// <returns>A Collection of client Fee Type details</returns>

        public static Collection<FeeTypeDetails> MapModel(FeeType[] serverFeeTypes)
        {
            if (serverFeeTypes == null)
            {
                throw new ROIException(ROIErrorCodes.ArgumentIsNull);
            }

            Collection<FeeTypeDetails> clientFeeTypes = new Collection<FeeTypeDetails>();
            FeeTypeDetails clientFeeType;

            foreach (FeeType serverFeeType in serverFeeTypes)
            {
                clientFeeType = MapModel(serverFeeType);
                clientFeeTypes.Add(clientFeeType);
            }

            return clientFeeTypes;
        }

        #endregion
    }
}
