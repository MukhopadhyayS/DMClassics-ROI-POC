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
    /// Adjustment reason data vault
    /// </summary>
    public class AdjustmentReasonsVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(AdjustmentReasonsVault));

        #region Fields

        //DataBase Fields
        private const string RefId = "Ref_ID";
        private const string AdjustmentReasonName        = "Reason_Name";
        private const string AdjustmentReasonDisplayText = "Display_Text";

        private VaultMode modeType;
        private Hashtable adjustmentReasonHT;
        #endregion

        #region Constructor

        public AdjustmentReasonsVault()
        {
            adjustmentReasonHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the AdjustmentReason Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            
            long recordcount;
            try
            {
                recordcount = 1;
                ReasonDetails reasonDetail;
                while (reader.Read())
                {
                    string reasonRefId       = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    reasonDetail             = new ReasonDetails();
                    reasonDetail.Type        = ReasonType.Adjustment;
                    reasonDetail.Name        = reader[AdjustmentReasonName].ToString();
                    reasonDetail.DisplayText = reader[AdjustmentReasonDisplayText].ToString();

                    //Save the AdjustmentReason object.
                    adjustmentReasonHT.Add(reasonRefId, SaveAdjustmentReasonsDetail(reasonDetail, recordcount));

                    recordcount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.OdbcError, cause);
            }            
            finally
            {
                reader.Close();
                reader.Dispose();
            }
            log.ExitFunction();
            return adjustmentReasonHT;
        }

        /// <summary>
        /// Passes the AdjustmentReason object to the ROIAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">MediaType object.</param>
        private ReasonDetails SaveAdjustmentReasonsDetail(ReasonDetails reasonDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the ROIAdminController to save the AdjustmentReason Object.
                long id = ROIAdminController.Instance.CreateReason(reasonDetail);
                reasonDetail.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
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
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get
            {
                return DataVaultConstants.AdjustmentReason;
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
