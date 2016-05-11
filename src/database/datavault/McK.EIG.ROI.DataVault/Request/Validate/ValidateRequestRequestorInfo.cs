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
using System.Data;
using System.Data.Common;
using System.Globalization;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Requestors.Process;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class ValidateRequestInformation : IVault
    {
        #region Fields

        //Request Requestor Info        
        private const string RequestorRefId        = "ReqorRef_ID";
        private const string RequestorHomePhone    = "Reqor_Home_Ph";
        private const string RequestorWorkPhone    = "Reqor_Work_Ph";
        private const string RequestorCellPhone    = "Reqor_Cell_Ph";
        private const string RequestorFax          = "Reqor_Fax";
        private const string RequestorContactName  = "Cont_Name";
        private const string RequestorContactPhone = "Cont_Ph";
        private const string RequestorType         = "Reqor_Type";
        private const string RequestorName         = "Reqor_Name";
        
        #endregion

        #region Methods
        
        #region Requestor Info

        /// <summary>
        /// Retrive the Requestor Info Details for the specified Request RefId.
        /// </summary>
        /// <param name="requestRefId">Requestor Reference Id</param>
        /// <param name="requestDetails">Request Details</param>
        private RequestDetails ProcessRequestRequestor(string requestRefId, RequestDetails requestDetails)
        {
            string query;
            string entity = DataVaultConstants.RequestRequorInfo + "_" + vaultModeType;
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + RequestRefId + "= '" + requestRefId + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + RequestRefId + "= '" + requestRefId + "'";

            //Validation of the Billing Template sheet.
            IDataReader countReader = Utility.ReadData(entity, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            
            if (count == 0)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.RequestorNotFound, requestRefId);
                    errorMessage.AppendLine(message);
                }
                return requestDetails;
            }            
            else if (count == 1)
            {
                if ((vaultModeType == VaultMode.Update) && requestDetails.IsReleased)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.RequestorCannotChange, requestRefId);
                    errorMessage.AppendLine(message);
                }
            }

           
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT * FROM [" + entity + "$] WHERE " + RequestRefId + "= '" + requestRefId + "'"
                    : "SELECT * FROM " + entity + ".csv WHERE " + RequestRefId + "= '" + requestRefId + "'";

           
            using (IDataReader requestorReader = Utility.ReadData(DataVaultConstants.RequestRequorInfo + "_" + vaultModeType, query))
            {
                while (requestorReader.Read())
                {
                    string requestorRefId = Convert.ToString(requestorReader[RequestorRefId], CultureInfo.CurrentCulture);
                    GetRequestorDetails(requestorRefId, requestDetails);
                    string workPhone    = Convert.ToString(requestorReader[RequestorWorkPhone], CultureInfo.CurrentCulture).Trim();
                    string homePhone    = Convert.ToString(requestorReader[RequestorHomePhone], CultureInfo.CurrentCulture).Trim();
                    string cellPhone    = Convert.ToString(requestorReader[RequestorCellPhone], CultureInfo.CurrentCulture).Trim();
                    string contactName  = Convert.ToString(requestorReader[RequestorContactName], CultureInfo.CurrentCulture).Trim();
                    string contactPhone = Convert.ToString(requestorReader[RequestorContactPhone], CultureInfo.CurrentCulture).Trim();

                    requestDetails.RequestorWorkPhone = (workPhone.Length > 0) ? workPhone : requestDetails.RequestorWorkPhone;
                    requestDetails.RequestorHomePhone = (homePhone.Length > 0) ? homePhone : requestDetails.RequestorHomePhone;
                    requestDetails.RequestorCellPhone = (cellPhone.Length > 0) ? cellPhone : requestDetails.RequestorCellPhone;
                    requestDetails.RequestorContactName = (contactName.Length > 0) ? contactName : requestDetails.RequestorContactName;
                    requestDetails.RequestorContactPhone = (contactPhone.Length > 0) ? contactPhone : requestDetails.RequestorContactPhone;
                }
            }
            return requestDetails;
        }

        /// <summary>
        /// Get the Requestor Details from Requestor excel sheet.
        /// </summary>
        /// <param name="requestorRefId">Requestor RefId</param>
        /// <param name="requestDetails">Request Details</param>
        private void GetRequestorDetails(string requestorRefId, RequestDetails requestDetails)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);
            string entity = DataVaultConstants.RequestorInfo + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                          ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "='" + requestorRefId + "'"
                          : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "='" + requestorRefId + "'";
            IDataReader requestorDetailsReader = Utility.ReadData(entity, query);

            while (requestorDetailsReader.Read())
            {                
                requestDetails.RequestorId = 1;
                requestDetails.RequestorType = 1;
                requestDetails.RequestorName        = requestorDetailsReader["Reqor_Name"].ToString();                
                requestDetails.RequestorTypeName    = requestorDetailsReader["Reqor_Type"].ToString();
                requestDetails.RequestorWorkPhone   = requestorDetailsReader["Reqor_Work_Ph"].ToString();
                requestDetails.RequestorHomePhone   = requestorDetailsReader["Reqor_Home_Ph"].ToString();
                requestDetails.RequestorCellPhone   = requestorDetailsReader["Reqor_Cell_Ph"].ToString();
                requestDetails.RequestorContactName = requestorDetailsReader["Cont_Name"].ToString();
                requestDetails.RequestorContactPhone = requestorDetailsReader["Cont_Ph"].ToString();
                requestDetails.Requestor = new RequestorDetails();
                requestDetails.Requestor.IsActive = requestorDetailsReader["Reqor_Active"].ToString().Equals("True") ? true : false;
            }
            if (!requestorDetailsReader.IsClosed)
            {
                requestorDetailsReader.Close();
            }
            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
        }

        #endregion
       
        #endregion
    }
}
