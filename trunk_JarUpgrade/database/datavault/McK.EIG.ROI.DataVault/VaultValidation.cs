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
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;

using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Admin.Validate;
using McK.EIG.ROI.DataVault.Patient.Validate;
using McK.EIG.ROI.DataVault.Requestors.Validate;
using McK.EIG.ROI.DataVault.Request.Validate;

namespace McK.EIG.ROI.DataVault
{
    public class VaultValidation
    {
        #region Fields

        private static VaultLogger vaultLog;
        private static string MachineName      = "Datavault Executing Machine Name    : ";
        private static string Date             = "Date Time                           : ";
        private static string Servers          = "Referred Services                   : ";
        private static string ConnectedService = "Service Name : {0}; Connected Port : {1}";

        private bool isValidationSuccess;
        private bool isFileExist;

        #endregion

        #region Methods

        public bool Validate()        
        {  
            CreateLogger();
            vaultLog.Debug(MachineName + Environment.MachineName);
            vaultLog.Debug(Date + DateTime.Now);
            vaultLog.DividerLine();
            vaultLog.Debug(Servers);
            ClientSettingsSection section = (ClientSettingsSection)ConfigurationManager.GetSection(DataVaultConstants.UserAppSettings);
            foreach (SettingElement setting in section.Settings)
            {
                string message = string.Format(CultureInfo.CurrentCulture, ConnectedService, setting.Name, setting.Value.ValueXml.InnerText) ;
                vaultLog.Debug(message);
            }

            try
            {
                isFileExist = Utility.ValidateFiles();
                
                if (isFileExist)
                {
                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
                    ValidateAdmin validateAdmin = new ValidateAdmin();
                    validateAdmin.LoadData();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreatePatientModule);
                    ValidatePatient validatePatient = new ValidatePatient();
                    validatePatient.LoadData();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);
                    ValidateRequestor validateRequestor = new ValidateRequestor();
                    validateRequestor.LoadData();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
                    ValidateRequest validateRequest = new ValidateRequest();
                    validateRequest.LoadData();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
                    validateAdmin.Reload();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdatePatientModule);
                    validatePatient.Reload();

                    if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestorModule);
                    validateRequestor.Reload();

                    //if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateRequestModule);
                    //validateRequest.Reload();
                }
            }
            catch (VaultException cause)
            {
                vaultLog.Debug(cause.Message);
                return false;
            }           
            finally
            {   
                
                string location       = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
                location              = location + @"\" + DataVaultConstants.ValidateLogFileName;
                FileStream fs         = new FileStream(location, FileMode.Open, FileAccess.Read, FileShare.ReadWrite);
                StreamReader reader   = new StreamReader(fs);                
                StringBuilder builder = new StringBuilder();
                builder.Append(reader.ReadToEnd());
                reader.Close();
                fs.Close();
                isValidationSuccess = Regex.IsMatch(builder.ToString(), "Error");
                
                if (isValidationSuccess || !isFileExist)
                {
                    ProcessStartInfo processFile = new ProcessStartInfo();
                    processFile.FileName = location;
                    processFile.WindowStyle = ProcessWindowStyle.Normal;
                    Process openFile = new Process();
                    openFile.StartInfo = processFile;
                    openFile.Start();
                }
            }

            return !isValidationSuccess;
        }

        /// <summary>
        /// Create the Logger Instance.
        /// </summary>
        private static void CreateLogger()
        {
            if (vaultLog == null)
            {
                vaultLog = VaultLogger.GetInstance("VaultLogger");
            }
        }

        #endregion
    }
}
