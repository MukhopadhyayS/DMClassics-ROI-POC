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
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.IO;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;
using System.Text;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class ValidateRequestInformation : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(ValidateRequestInformation));

        #region Fields

        //Common Fields
        private const string RefId        = "Ref_ID";
        private const string RequestRefId = "ReqRef_ID";

        //Request Info
        private const string RequestReason = "Req_Reason";
        private const string ReceiptDate   = "Req_Receipt_Dt";        

        StringBuilder errorMessage = new StringBuilder();

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();            
                      
            long recordCount = 1;
            bool isHeaderExist = false;
            string requestRefId = string.Empty;

            RequestDetails requestDetails = null;

            while (reader.Read())
            {
                try
                {
                    log.Debug("Validating Request:" + recordCount);
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    //Refernce Id
                    requestRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    if (vaultModeType == VaultMode.Update)
                    {
                        bool isRequestExist = RetriveRequest(requestRefId);
                        if (!isRequestExist)
                        {
                            errorMessage.AppendLine(string.Format(CultureInfo.CurrentCulture,
                                                          DataVaultErrorCodes.UnknownObject,
                                                          requestRefId,
                                                          EntityName + "_" + VaultMode.Create));
                        }

                    }
                    else
                    {
                        requestDetails = new RequestDetails();
                        string requestReasonRefId    = Convert.ToString(reader[RequestReason], CultureInfo.CurrentCulture).Trim();
                        ReasonDetails reasonDetails  = RetriveRequestReason(requestReasonRefId);
                        requestDetails.RequestReason = reasonDetails.Name;
                        requestDetails.RequestReasonAttribute = reasonDetails.Attribute.ToString();
                        requestDetails.Status = RequestStatus.Logged;
                        requestDetails.StatusChanged = DateTime.Now.Date;
                    }

                    if (Convert.ToString(reader[ReceiptDate], CultureInfo.CurrentCulture).Trim().Length > 0)
                    {
                        requestDetails.ReceiptDate = Convert.ToDateTime(reader[ReceiptDate], CultureInfo.CurrentCulture);
                    }
                    //Get the Requestor Information
                    requestDetails = ProcessRequestRequestor(requestRefId, requestDetails);

                    RequestValidator validator = new RequestValidator();
                    if (!validator.ValidateRequestInfo(requestDetails))
                    {
                        errorMessage.AppendLine(Utility.GetErrorMessage(validator.ClientException));
                    }
                   
                    if (errorMessage.ToString().Trim().Length > 0)
                    {
                        throw new VaultException(errorMessage.ToString().Trim());
                    }                    
                }
                catch (VaultException cause)
                {
                    if (!isHeaderExist)
                    {
                        isHeaderExist = true;
                        ValidateUtility.WriteLog("Request",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestModule : DataVaultConstants.UpdateRequestModule,
                            "Request Information",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(requestRefId, recordCount, cause.Message);
                }               
                recordCount++;
            }

            if (!reader.IsClosed)
            {
                reader.Close();
            }
            log.ExitFunction();
            return null;

        }

        private ReasonDetails RetriveRequestReason(string requestReasonRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            string entity = DataVaultConstants.RequestReason + "_" + VaultMode.Create;
            string query  = (DataVaultConstants.IsExcelFile)
                          ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "='" + requestReasonRefId + "'"
                          : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "='" + requestReasonRefId + "'";
            IDataReader reader = Utility.ReadData(entity, query);
            ReasonDetails reasonDetails = null;
            while (reader.Read())
            {
                reasonDetails = new ReasonDetails();
                string attributeType = reader["Attribute"].ToString();
                switch (attributeType)
                {
                    case "TPO"    : reasonDetails.Attribute = RequestAttr.Tpo; break;
                    case "Non-TPO": reasonDetails.Attribute = RequestAttr.NonTpo; break;
                    default:                        
                        errorMessage.Append(DataVaultErrorCodes.InvalidRequestAttribute);
                        break;

                }
                reasonDetails.Name = Convert.ToString(reader["Reason_Name"], CultureInfo.CurrentCulture);
            }
            if (!reader.IsClosed)
            {
                reader.Close();
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
            if (reasonDetails == null) 
                return new ReasonDetails();
            else
                return reasonDetails;
        }

       
        /// <summary>
        /// Retrive the request for the given request id, this is invoked
        /// during update process of request.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        private bool RetriveRequest(string  requestRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, requestRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
            return (count > 0);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.RequestInfo; }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return vaultModeType;
            }
            set
            {
                vaultModeType = value;
            }
        }
        
        #endregion
    }
}
