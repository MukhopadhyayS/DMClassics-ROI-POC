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
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public partial class RequestInformationVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(RequestInformationVault));

        #region Fields

        //Common Fields
        private const string RefId         = "Ref_ID";
        private const string RequestRefId  = "ReqRef_ID";

        //Request Info
        private const string RequestReason = "Req_Reason";
        private const string ReceiptDate   = "Req_Receipt_Dt";        

        private Hashtable requestInfoHT;

        private VaultMode modeType;

        private int releaseCount;

        #endregion

        #region Constructor

        public RequestInformationVault()
        {   
            requestInfoHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Requestor Information Vault.
        /// </summary>
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
                    string requestRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    if (modeType == VaultMode.Update)
                    {
                        if (!requestInfoHT.ContainsKey(requestRefId))
                        {
                            string errorMeg = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, requestRefId, EntityName + "_" + modeType);
                            log.Debug(errorMeg);
                            throw new VaultException(errorMeg);
                        }
                        requestDetails = RetriveRequest(Convert.ToInt64(requestInfoHT[requestRefId], CultureInfo.CurrentCulture));
                    }
                    else
                    {
                        requestDetails = new RequestDetails();
                        string requestReason = Convert.ToString(reader[RequestReason], CultureInfo.CurrentCulture).Trim();                        
                        ReasonDetails reasonDetails = (ReasonDetails)AdminVault.GetEntityObject(DataVaultConstants.RequestReason, requestReason);
                        requestDetails.RequestReason = reasonDetails.Name;
                        requestDetails.RequestReasonAttribute = reasonDetails.Attribute.ToString();

                        requestDetails.Status        = RequestStatus.Logged;
                        requestDetails.StatusChanged = DateTime.Now.Date;
                    }

                    if (Convert.ToString(reader[ReceiptDate], CultureInfo.CurrentCulture).Trim().Length > 0)
                    {
                        requestDetails.ReceiptDate = Convert.ToDateTime(reader[ReceiptDate], CultureInfo.CurrentCulture);
                    }
                    //Get the Requestor Information
                    requestDetails = ProcessRequestRequestor(requestRefId, requestDetails);

                    if (modeType == VaultMode.Create)
                    {
                        requestInfoHT.Add(requestRefId, SaveRequestInformation(requestDetails, recordCount));
                    }
                    else
                    {
                       requestDetails = UpdateRequestDetails(requestDetails, recordCount);
                    }

                    recordCount++;
                }
                log.ExitFunction();
                return requestInfoHT;
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
        /// Passes the Requestor information object to the RequestorController for further process
        /// </summary>
        /// <param name="requestDetails">Requestor Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private long SaveRequestInformation(RequestDetails requestDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));


                //Call the RequestorController to save the RequestorInformation.
                requestDetails = RequestController.Instance.CreateRequest(requestDetails);
                

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
                log.ExitFunction();
                return requestDetails.Id;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
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

                //Call the RequestController to save the RequestInformation.
                requestDetails.Requestor = GetRequestorInfo(requestDetails.Requestor.Id);
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
            get { return DataVaultConstants.RequestInfo; }
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
