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
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.Text;
using System.Xml;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.DataVault.Admin;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.DataVault.Requestors;
using McK.EIG.ROI.DataVault.Patient;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.Controller;


namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public class ValidateRequestComment : IVault
    {
        Log log = LogFactory.GetLogger(typeof(ValidateRequestComment));

        #region Fields

        private const string RefId          = "Ref_ID"; 
        private const string RequestRefId   = "ReqRef_ID";        
        private const string RequestComment = "Req_Comment";

        private VaultMode vaultModeType;

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {            
            CommentDetails commentDetails;
            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();
            string reqRefId            = string.Empty;

            while (reader.Read())
            {
                try
                {
                    try
                    {
                        errorMessage.Remove(0, errorMessage.ToString().Length);
                        commentDetails = new CommentDetails();
                        reqRefId = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture);
                        long requestId = ValidateRequestExist(reqRefId);

                        if (requestId == 0)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                  DataVaultErrorCodes.UnknownObject,
                                                  reqRefId,
                                                  DataVaultConstants.RequestInfo + "_" + VaultMode.Create));
                        }

                        commentDetails.RequestId = requestId;
                        commentDetails.EventRemarks = Convert.ToString(reader[RequestComment], CultureInfo.CurrentCulture);

                        RequestValidator validator = new RequestValidator();
                        bool check = validator.ValidateComment(commentDetails);
                        if (!check)
                        {
                            errorMessage.Append(Utility.GetErrorMessage(validator.ClientException));
                        }
                    }
                    catch (ArgumentException cause)
                    {
                        errorMessage.Append(cause.Message);                        
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
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestorModule : DataVaultConstants.UpdateRequestModule,
                            "Request Comment",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(reqRefId, recordCount, cause.Message);
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

        private static long ValidateRequestExist(string reqRefId)
        {
            long count = Utility.GetCount(DataVaultConstants.RequestInfo + "_" + VaultMode.Create, RefId, reqRefId);
            return count;
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.RequestComment; }
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
