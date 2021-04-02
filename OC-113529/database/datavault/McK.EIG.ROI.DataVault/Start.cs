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

#region Namespaces

using System;
using System.Configuration;
using System.Globalization;
using System.IO;
using System.Reflection;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.Controller;

using McK.EIG.ROI.DataVault.Base;

#endregion

namespace McK.EIG.ROI.DataVault
{
    /// <summary>
    /// Execution of DataVault starts here.
    /// </summary>
    public static class Start
    {
        private static Log log = LogFactory.GetLogger(typeof(Start));

        #region Fields

        private static string Initilizing          = "Initializing...";
        private static string InitilizingCompleted = "Initialization Completed.";
        private static string DividerLine          = "-------------------------";
        private static string InputQuery           = "Do you want to proceed with validation ?";
        private static string InputInfo            = "Press 'y' to start validation or 'n' to skip validation";
        private static string InvalidInput         = "Invalid input.";        
        private static string ValidationFailed     = "Validation Failed, Please Check the Validation Log file and Fix the issue in excel or csv file.";
        private static string ProcessingData       = "Processing data......";
        private static string DataImported         = "Data Successfully imported.";

        #endregion

        #region Methods

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            try
            {   
                bool isValidationSuccess = false;

                string location = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
                location = location + @"\" + DataVaultConstants.ValidateLogFileName;
                if (File.Exists(location))
                {
                    File.Delete(location);
                }

                //Log Configuration file is mapped.
                LogFactory.SetConfigFileName(DataVaultConstants.LogConfigFileName);

                log.Debug(Initilizing);
                Init();
                log.Debug(InitilizingCompleted);
                log.Debug(DividerLine);
                log.Debug(InputQuery);
                log.Debug(InputInfo);

                getInput : 
                    string validationRequired = Console.ReadLine().ToString(CultureInfo.CurrentCulture);

                if (string.Compare(validationRequired, "y", true, CultureInfo.CurrentCulture) == 0)
                {
                    Console.WriteLine("Validating data......");
                    VaultValidation vaultValidation = new VaultValidation();
                    isValidationSuccess = vaultValidation.Validate();
                    ProcessData(isValidationSuccess);
                }
                else if (string.Compare(validationRequired, "n", true, CultureInfo.CurrentCulture) == 0)
                {
                    ProcessData(true);
                }
                else
                {
                    log.Debug(InvalidInput);
                    log.Debug(InputInfo);
                    goto getInput;
                }
            }
            catch (ROIException cause)
            {
                log.Debug(cause);
                string message = Utility.GetErrorMessage(cause);
                Console.Write("Error occurred: " + message);
                Console.Read();
            }
            catch (VaultException cause)
            {
                log.Debug(cause);
                Console.Write("Error occurred: " + cause.Message);
            }
            catch (Exception cause)
            {
                log.Debug(cause);
                Console.Write("Error occurred: " + cause.Message);
            }            
        }

        private static void ProcessData(bool isValidationSuccess)
        {
            if (isValidationSuccess)
            {
                log.Debug(ProcessingData);
                VaultProcess process = new VaultProcess();
                process.StartProcess();
                log.Debug(DataImported);
                Console.Read();
            }
            else
            {
                log.Debug(ValidationFailed);
            }
        }

        private static void Init()
        {
            DirectoryInfo info = new DirectoryInfo(DataVaultConstants.DataSetPath);
            if (!info.Exists)
            {
                log.Error(DataVaultErrorCodes.DirectoryNotFound + info.FullName);
                throw new VaultException(DataVaultErrorCodes.DirectoryNotFound + info.FullName);
            }

            //Create UserDSN and map the DataSet path to DSN.
            DataSourceNameUtility.CreateUserDataSourceName(info.FullName, DataVaultConstants.IsExcelFile);

            //Create the User Session
            UserData userData = UserData.Instance;
            userData.UserId = DataVaultConstants.UserId;
            userData.Password = DataVaultConstants.Password;
            userData = ROIController.Instance.LogOn();

        }

        

        #endregion
    }
}
