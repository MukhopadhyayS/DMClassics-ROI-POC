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
using System.Configuration;
using System.Globalization;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Patient.Process;
using McK.EIG.ROI.DataVault.Request.Process;
using McK.EIG.ROI.DataVault.Requestors.Process;

namespace McK.EIG.ROI.DataVault
{
    public class VaultProcess
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(VaultProcess));

        #endregion

        /// <summary>
        /// Initialize the Clean and Fill class.
        /// </summary>
        public void StartProcess()
        {            
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

            if (Utility.ValidateFiles())
            {
                //Load the Admin Module.
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
                AdminVault adminVault = new AdminVault();
                adminVault.LoadData();

                //Load the Patient Module.
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
                PatientVault patientVault = new PatientVault();
                patientVault.LoadData();

                //Load the Requestor Module.
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);
                RequestorVault requestorVault = new RequestorVault();
                requestorVault.LoadData();

                //Load the Request Module.
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                RequestVault requestVault = new RequestVault();
                requestVault.LoadData();

                int totalReleaseCount = Convert.ToInt32(ConfigurationManager.AppSettings["ReleaseCounter"], CultureInfo.CurrentCulture);

                for (int i = 1; i <= totalReleaseCount; i++)
                {
                    //Update the admin vault for rerelease.
                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
                    adminVault.ReleaseCount = i;
                    adminVault.Reload();

                    //Update Paitient Module.
                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdatePatientModule);
                    patientVault.ReleaseCount = i;
                    patientVault.Reload();

                    //Update Requestor Module.
                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestorModule);
                    requestorVault.ReleaseCount = i;
                    requestorVault.Reload();

                    //Update Request Module.
                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                    requestVault.ReleaseCount = i;
                    requestVault.Reload();
                }
            }
            else
            {
                throw new VaultException();
            }

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);
            log.ExitFunction();
        }
    }
}
