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


namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Class inserts the requestor information.
    /// </summary>
    public class RequestCommentVault : IVault
    {
        Log log = LogFactory.GetLogger(typeof(RequestCommentVault));

        #region Fields
                
        private const string RequestRefId   = "ReqRef_ID";        
        private const string RequestComment = "Req_Comment";

        private VaultMode modeType;

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
                CommentDetails commentDetails;

                while (reader.Read())
                {
                    commentDetails = new CommentDetails();
                    object requestId = RequestVault.GetEntityObject(DataVaultConstants.RequestInfo, 
                                                                    Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture));

                    commentDetails.RequestId    = Convert.ToInt64(requestId, CultureInfo.CurrentCulture);
                    commentDetails.EventRemarks = Convert.ToString(reader[RequestComment],
                                                                   CultureInfo.CurrentCulture);
                    commentDetails.EventType    = EventType.CommentAdded;

                    SaveRequestComment(commentDetails, recordCount);

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
        /// Passes the Requestor information object to the RequestorController for further process
        /// </summary>
        /// <param name="patientDetails">Requestor Info Details.</param>
        /// <param name="recordCount">Record Count</param>
        private void SaveRequestComment(CommentDetails commentDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                        DataVaultConstants.ProcessStartTag,
                                        recordCount,
                                        DateTime.Now));

                //Call the RequestorController to save the RequestorInformation.
                commentDetails = RequestController.Instance.CreateComment(commentDetails);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
                log.ExitFunction();                
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
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
