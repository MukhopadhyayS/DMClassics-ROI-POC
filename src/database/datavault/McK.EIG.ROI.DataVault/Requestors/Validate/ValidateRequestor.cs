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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Requestors.Validate
{
    /// <summary>
    /// Requestor Data Vault.
    /// </summary>
    public class ValidateRequestor
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(ValidateRequestor));        

        private ValidateRequestorInformation requestorInformationVault;

        #endregion

        #region Methods

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public void LoadData()
        {
            log.EnterFunction();

            //Load PatientInformation vault
            requestorInformationVault = new ValidateRequestorInformation();
            requestorInformationVault.ModeType = VaultMode.Create;
            Load(requestorInformationVault);
                        
            log.ExitFunction();
            
        }

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public void Reload()
        {
            log.EnterFunction();

            //Load PatientInformation vault            
            requestorInformationVault.ModeType = VaultMode.Update;
            Load(requestorInformationVault);

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
