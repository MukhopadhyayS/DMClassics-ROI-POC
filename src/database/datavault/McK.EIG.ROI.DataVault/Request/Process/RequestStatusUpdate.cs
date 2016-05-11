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
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Admin.Process;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class RequestStatusUpdate : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(RequestStatusUpdate));

        #region Fields

        //Request Status
        private const string RequestRefId        = "ReqRef_ID";
        private const string RequestStatusReason = "Status_Reasons";
        private const string ReqStatus           = "Req_Status";
        private const string Custom              = "IsCustom";

        private int releaseCount;

        private VaultMode modeType;

        #endregion

        #region Methods

        public object Load(IDataReader reader)
        {
            log.EnterFunction();

            long recordCount;

            try
            {
                recordCount = 1;
                RequestDetails requestDetails;

                while (reader.Read())
                {
                    //Refernce Id
                    string requestRefId = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();                    
                    StringBuilder reasonBuilder = new StringBuilder();
                    long requestId = Convert.ToInt64(RequestVault.GetEntityObject(DataVaultConstants.RequestInfo, requestRefId), CultureInfo.CurrentCulture);

                    requestDetails = GetRequestInfo(requestId);
                    requestDetails.Requestor = GetRequestorInfo(requestDetails.Requestor.Id);
                    RequestStatus oldStatus = requestDetails.Status;
                    
                    string status       = Convert.ToString(reader[ReqStatus], CultureInfo.CurrentCulture).Trim();
                    string statusReason = Convert.ToString(reader[RequestStatusReason], CultureInfo.CurrentCulture);
                    bool isCutomReason  = Convert.ToBoolean(reader[Custom], CultureInfo.CurrentCulture);

                    if (isCutomReason)
                    {
                        reasonBuilder.Append(statusReason.Trim()).Append(ROIConstants.StatusReasonDelimiter);
                        requestDetails.FreeformReasons = requestDetails.FreeformReasons + statusReason.Trim() + ROIConstants.StatusReasonDelimiter;
                    }
                    else
                    {
                        ReasonDetails details = AdminVault.GetEntityObject(DataVaultConstants.StatusReason, statusReason) as ReasonDetails;
                        reasonBuilder.Append(details.Name.Trim()).Append(ROIConstants.StatusReasonDelimiter);
                    }
                    if (status.Length > 0)
                    {
                        requestDetails.Status = (RequestStatus)Enum.Parse(typeof(RequestStatus), status, true);
                        if (oldStatus != requestDetails.Status)
                        {
                            requestDetails.StatusChanged = DateTime.Now.Date;
                        }
                    }
                    requestDetails.StatusReason = reasonBuilder.ToString().TrimEnd(ROIConstants.StatusReasonDelimiter.ToCharArray());

                    if (StatusValidation(requestDetails))
                    {
                        requestDetails = UpdateRequestDetails(requestDetails, recordCount);
                    }
                    else
                    {
                        string message = string.Format(CultureInfo.CurrentCulture,
                                                       DataVaultErrorCodes.RequestReasonRequired,
                                                       requestDetails.Status, requestRefId);
                        log.Debug(message);
                        throw new VaultException(message);
                    }

                    requestDetails = UpdateRequestDetails(requestDetails, recordCount);                    

                    recordCount++;
                }
                log.ExitFunction();
                return null;
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message);
            }
            finally
            {
                reader.Close();
            }
        }

        /// <summary>
        /// Passes the Request information object to the RequestorController for further process
        /// </summary>
        /// <param name="requestDetails">Requestor Info Details.</param>        
        private RequestDetails UpdateRequestDetails(RequestDetails requestDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                      DataVaultConstants.ProcessStartTag,
                                      recordCount,
                                      DateTime.Now));

                //Call the RequestorController to save the RequestorInformation.
                requestDetails = RequestController.Instance.UpdateRequest(requestDetails);

                log.Debug(DataVaultConstants.ProcessEndTag);
                log.ExitFunction();
                return requestDetails;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Validation for the Request Reason.
        /// </summary>
        /// <param name="requestDetails"></param>
        /// <returns></returns>
        private bool StatusValidation(RequestDetails requestDetails)
        {
            log.EnterFunction();
            bool isReasonRequired = RequestDetails.IsReasonRequired(requestDetails.Status);
            if (isReasonRequired)
            {
                return requestDetails.StatusReason.Length > 0;
            }
            log.ExitFunction();
            return true;
        }

        /// <summary>
        /// Retrive the request for the given request id, this is invoked
        /// during update process of request.
        /// </summary>
        /// <param name="requestId"></param>
        /// <returns></returns>
        private RequestDetails RetriveRequest(long requestId)
        {
            log.EnterFunction();
            try
            {
                return RequestController.Instance.RetrieveRequest(requestId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
        }

        /// <summary>
        /// Get the request details for the given requestID.
        /// </summary>
        /// <param name="requestId">Request ID.</param>        
        private RequestDetails GetRequestInfo(long requestId)
        {
            log.EnterFunction();
            RequestDetails requestDetails;
            try
            {
                requestDetails = RequestController.Instance.RetrieveRequest(requestId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestDetails;
        }

        /// <summary>
        /// Get the requestor details for the given requestorID.
        /// </summary>
        /// <param name="requestId">Request ID.</param>        
        private RequestorDetails GetRequestorInfo(long requestorId)
        {
            log.EnterFunction();
            RequestorDetails requestorDetails;
            try
            {
                requestorDetails = RequestorController.Instance.RetrieveRequestor(requestorId);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestorDetails;
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
