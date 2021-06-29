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
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Request.Process;
using McK.EIG.ROI.DataVault.Admin.Process;


namespace McK.EIG.ROI.DataVault.Request.Validate
{
    public partial class ValidateRequestStatus : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(ValidateRequestInformation));

        #region Fields

        //Request Status Reasons
        private const string RequestRefId        = "ReqRef_ID";
        private const string RequestStatusReason = "Status_Reasons";
        private const string ReqStatus           = "Req_Status";
        private const string Custom              = "IsCustom";
        private const string RefId               = "Ref_ID";

        private static string ValidReqStatus     = "Validating Request Status:";

        private VaultMode modeType;

        private int releaseCount;

        #endregion

        #region Method

        /// <summary>
        /// System Load the Request Status Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            long recordCount = 1;
            string requestRefId = string.Empty;
            StringBuilder errorMessage = new StringBuilder();
            bool isHeaderExist = false;

            while (reader.Read())
            {
                try
                {
                    log.Debug(ValidReqStatus + recordCount);
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    requestRefId        = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();
                    string statusReason = Convert.ToString(reader[RequestStatusReason], CultureInfo.CurrentCulture);
                    bool isCutomReason  = Convert.ToBoolean(reader[Custom], CultureInfo.CurrentCulture);

                    long requestCount = IsValidateRequest(requestRefId);
                    if (requestCount == 0)
                    {
                        string message = string.Format(CultureInfo.CurrentCulture, "Request "+requestRefId+" not found.");
                        errorMessage.Append(message);
                    }
                    if (!isCutomReason)
                    {
                        long statusCount = ValidateStatusReason(statusReason);
                        if (statusCount == 0)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, "Invalid Status Reason = " +statusReason, requestRefId);
                            errorMessage.AppendLine(message);
                        }
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
                            (ModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestModule : DataVaultConstants.UpdateRequestModule,
                            "Request Status Reason",
                            EntityName + "_" + ModeType);
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

        private long IsValidateRequest(string requestRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
            string query;
            string entity = DataVaultConstants.RequestInfo + "_" + modeType;

            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE Ref_ID= '" + requestRefId + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE Ref_ID= '" + requestRefId + "'";


            IDataReader countReader = Utility.ReadData(entity, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }
            return count;
        }

        private long ValidateStatusReason(string statusReason)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            string query;
            string entity = DataVaultConstants.StatusReason + "_" + modeType;
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + RefId + "= '" + statusReason + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + RefId + "= '" + statusReason + "'";
            
            IDataReader countReader = Utility.ReadData(DataVaultConstants.StatusReason + "_" + modeType, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            if (DataVaultConstants.IsExcelFile)
            {
                if (ModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                }
            }         
            log.ExitFunction();
            return count;
        }

        #endregion


        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.RequestStatusReason; }
        }

        /// <summary>
        /// Return the mode type.
        /// </summary>
        public VaultMode ModeType
        {
            get
            {
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        #endregion
    }
}
