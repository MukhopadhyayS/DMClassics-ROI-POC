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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Request Reason Data Vault
    /// </summary>
    public class RequestReasonsVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(RequestReasonsVault));

        #region Fields

        //DataBaseFields
        private const string RefId = "Ref_ID";
        private const string RequestReasonName        = "Reason_Name";
        private const string RequestReasonDisplayText = "Display_Text";
        private const string RequestReasonAttribute   = "Attribute";

        private VaultMode modeType;
        private Hashtable requestReasonHT;
        #endregion

        #region Constructor

        public RequestReasonsVault()
        {
            requestReasonHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Request Reason Vault.
        /// </summary>
        /// <param name="reader"></param>
        /// <returns></returns>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
        
            long recordCount;
            try
            {
                recordCount = 1;
                ReasonDetails reasonDetail;
                while (reader.Read())
                {
                    string reasonRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    reasonDetail = new ReasonDetails();
                    
                    reasonDetail.Type        = ReasonType.Request;
                    reasonDetail.Name        = reader[RequestReasonName].ToString();
                    reasonDetail.DisplayText = reader[RequestReasonDisplayText].ToString();
                    string attributeType = reader[RequestReasonAttribute].ToString();
                    switch (attributeType)
                    {
                        case "TPO"            : reasonDetail.Attribute = RequestAttr.Tpo; break;
                        case "Non-TPO"        : reasonDetail.Attribute = RequestAttr.NonTpo; break;
                        default :
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidRequestAttribute, attributeType);
                            throw new VaultException(message);
                    }
                    requestReasonHT.Add(reasonRefId, SaveRequestReasonDetail(reasonDetail, recordCount));
                    recordCount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            finally
            {
                reader.Close();
                reader.Dispose();
            }
            log.ExitFunction();
            return requestReasonHT;
        }

        /// <summary>
        /// Passes the RequestReason object to the ROIAdminController for further process
        /// </summary>
        /// <param name="reasonDetail"></param>
        /// <param name="recordCount"></param>
        /// <returns></returns>
        private ReasonDetails SaveRequestReasonDetail(ReasonDetails reasonDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the ROIAdminController to save the RequestReason
                long id = ROIAdminController.Instance.CreateReason(reasonDetail);
                reasonDetail.Id =id;
                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch(ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return reasonDetail;
        }

        #endregion

        #region Properties
       
        /// <summary>
        /// Return Current Entity Name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.RequestReason;
            }
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
        #endregion
    }
}
