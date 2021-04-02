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
using System.Globalization;
using System.IO;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary>
    /// Requestor Data Vault.
    /// </summary>
    public class ValidateRequest
    {
        Log log = LogFactory.GetLogger(typeof(ValidateRequest));

        #region Fields

        private ValidateRequestInformation requestInfoVault;
        private ValidateRequestComment reqCommentVault;
        private ValidateBillingPayment billPayVault;
        private ValidateRequestPatientInfo reqPatientInfoVault;
        private ValidateRequestStatus requestStatusVault;

        #endregion        

        #region Methods

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public void LoadData()
        {
            log.EnterFunction();

            //Load RequestInformation vault
            requestInfoVault = new ValidateRequestInformation();
            requestInfoVault.ModeType = VaultMode.Create;
            Load(requestInfoVault);

            //Load Request Patient Information vault
            reqPatientInfoVault = new ValidateRequestPatientInfo();
            reqPatientInfoVault.ModeType = VaultMode.Create;
            Load(reqPatientInfoVault);

            //Load Request Status Update vault
            requestStatusVault = new ValidateRequestStatus();
            requestStatusVault.ModeType = VaultMode.Create;
            Load(requestStatusVault);

            //Load Request Comment vault
            reqCommentVault = new ValidateRequestComment();
            reqCommentVault.ModeType = VaultMode.Create;
            Load(reqCommentVault);

            //Load the Billing and Payment Module.
            if (DataVaultConstants.IsExcelFile)
            {
                DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
            }
            billPayVault = new ValidateBillingPayment();
            billPayVault.ModeType = VaultMode.Create;
            Load(billPayVault);
                        
            log.ExitFunction();            
        }

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public void Reload()
        {
            log.EnterFunction();

            ////Load RequestInformation vault            
            //requestInfoVault.ModeType = Mode.Update;
            //Load(requestInfoVault);

            //Load Request Patient Information vault
            //reqPatientInfoVault.ModeType = VaultMode.Update;
            //Load(reqPatientInfoVault);

            //Load Request Status Update vault
            //requestStatusVault.ModeType = VaultMode.Update;
            //Load(requestStatusVault);

            //Load the Billing and Payment Module.
            //if (DataVaultConstants.IsExcelFile)
            //{
            //    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
            //}
            //billPayVault.ModeType = Mode.Update;
            //Load(billPayVault);

            log.ExitFunction();            
        }

        private void Load(IVault vault)
        {
            log.EnterFunction();                

            IDataReader reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType);
            vault.Load(reader);
            
            log.ExitFunction();
        }
        
        #endregion       
    }
}
