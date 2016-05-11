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

#region Namespace

using System;
using System.Data.Odbc;
using System.Globalization;
using System.Runtime.InteropServices;
using System.Security;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.Common.Utility.Logging;

using Microsoft.Win32;

#endregion

namespace McK.EIG.ROI.DataVault.Base
{
    /// <summary>
    /// Create the User DSN
    /// </summary>
    public static class DataSourceNameUtility
    {
        #region Fields

        private static Log log = LogFactory.GetLogger(typeof(DataSourceNameUtility));

        #endregion

        #region Method

        #region Win32 API Import Code

        /// <summary>
        /// Win32 API Imports
        /// </summary>
        
        [SuppressUnmanagedCodeSecurityAttribute]
        internal static class SafeNativeMethods
        {
            [DllImport("ODBCCP32.dll")]
            [return: MarshalAs(UnmanagedType.Bool)]
            private static extern bool SQLConfigDataSource(IntPtr hwndParent,
            int request, string lpszDriver, string lpszAttributes);

            public static bool CreateDataSource(IntPtr hwndParent,
            int request,
            string lpszDriver,
            string lpszAttributes)
            {
                return SQLConfigDataSource(hwndParent,
                request,
                lpszDriver,
                lpszAttributes);
            }
        }

        /// <summary>
        /// Create the new UserDSN, with specfied name and the directroy path.
        /// </summary>
        /// <param name="dataSourceName">DSN Name</param>
        /// <param name="directroyPath">Directroy path that set to DSN</param>
        /// <returns>Returns true if Data Source Name created successfully.</returns>
        private static bool AddUserDataSourceName(string dataSourceName, string directroyPath, bool isXlsFile)
        {
            bool addDsnSuccess;
            if (!isXlsFile)
            {
                addDsnSuccess = SafeNativeMethods.CreateDataSource((IntPtr)0,
                                1,
                                "Microsoft Text Driver (*.txt; *.csv)\0",
                                "DSN=" + dataSourceName + "\0Uid=Admin\0pwd=\0DefaultDir=" + directroyPath + "\0");
            }
            else
            {
                addDsnSuccess = SafeNativeMethods.CreateDataSource((IntPtr)0,
                                    1,
                                    "Microsoft Excel Driver (*.xls)\0",
                                    "DSN=" + dataSourceName + "\0Uid=Admin\0pwd=\0DefaultDir=" + directroyPath + "\0DBQ=" + string.Empty + "\0");
            }

            return addDsnSuccess;
        }

        #endregion

        #region DSNCreation
        /// <summary>
        /// Create the UserDSN and map the directroy path to it.
        /// </summary>        
        /// <param name="directoryPath">Directory path that refferred by DSN</param>
        public static void CreateUserDataSourceName(string directoryPath, bool isExcelFile)
        {
            log.EnterFunction();
            try
            {                
                string dataSourceName = isExcelFile ? DataVaultConstants.XlsDataSourceName : DataVaultConstants.CsvDataSourceName;

                //Get Reister Key available for the current user.
                RegistryKey baseRegisterKey = Registry.CurrentUser;
                RegistryKey odbcRegisterKey = baseRegisterKey.OpenSubKey(@"SOFTWARE\ODBC\ODBC.INI", true);

                if (!IsDsnExists(dataSourceName, odbcRegisterKey))
                {
                    if (!isExcelFile)
                    {
                        //Create the UserDSN for csv file type.
                        AddUserDataSourceName(dataSourceName, directoryPath, isExcelFile);

                        //Map the directory path to the UserDSN created.
                        foreach (string keyName in odbcRegisterKey.GetSubKeyNames())
                        {
                            int i = string.Compare(keyName, dataSourceName, true, CultureInfo.CurrentCulture);
                            if (i == 0)
                            {
                                RegistryKey dataSourceKey = odbcRegisterKey.OpenSubKey(keyName, true);
                                dataSourceKey.SetValue("DefaultDir", directoryPath, RegistryValueKind.String);
                                break;
                            }
                        }
                    }
                    else
                    {
                        AddUserDataSourceName(dataSourceName, directoryPath, isExcelFile);
                    }
                }
                else
                {
                    if (!isExcelFile)
                    {
                        //Map the directory path to the UserDSN created.
                        foreach (string keyName in odbcRegisterKey.GetSubKeyNames())
                        {
                            int i = string.Compare(keyName, dataSourceName, true, CultureInfo.CurrentCulture);
                            if (i == 0)
                            {
                                RegistryKey dataSourceKey = odbcRegisterKey.OpenSubKey(keyName, true);
                                dataSourceKey.SetValue("DefaultDir", directoryPath, RegistryValueKind.String);
                                break;
                            }
                        }
                    }
                }
            }
            catch (SecurityException securityEx)
            {
                log.FunctionFailure(securityEx);
                throw new VaultException(DataVaultErrorCodes.AccessPermissionToRegistry);
            }
            catch (ArgumentException arguEx)
            {
                log.FunctionFailure(arguEx);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (ObjectDisposedException objectEx)
            {
                log.FunctionFailure(objectEx);
                throw new VaultException(DataVaultErrorCodes.ObjectDispose);
            }
            catch (Exception cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.Unknown,cause);
            }
            log.ExitFunction();
        }

        public static void SetDefaultDirectoryPath(string directoryPath, string fileName)
        {
            //Get Reister Key available for the current user.
            RegistryKey baseRegisterKey = Registry.CurrentUser;
            RegistryKey odbcRegisterKey = baseRegisterKey.OpenSubKey(@"SOFTWARE\ODBC\ODBC.INI", true);

            //Map the directory path to the UserDSN created.
            foreach (string keyName in odbcRegisterKey.GetSubKeyNames())
            {
                int i = string.Compare(keyName, DataVaultConstants.XlsDataSourceName, true, CultureInfo.CurrentCulture);
                if (i == 0)
                {
                    RegistryKey dataSourceKey = odbcRegisterKey.OpenSubKey(keyName, true);
                    dataSourceKey.SetValue("DefaultDir", directoryPath, RegistryValueKind.String);
                    dataSourceKey.SetValue("DBQ", directoryPath + @"\" + fileName, RegistryValueKind.String);
                    break;
                }
            }
        }

       
        /// <summary>
        /// Check is DSN already exists
        /// </summary>
        /// <param name="dataSourceName">dataSourceName</param>
        /// <param name="odbcRegisterKey">Available user DSN</param>
        /// <returns>Returns true if exists</returns>
        private static bool IsDsnExists(string dataSourceName, RegistryKey odbcRegisterKey)
        {
            log.EnterFunction();
            foreach (string keyName in odbcRegisterKey.GetSubKeyNames())
            {
                int i = string.Compare(keyName, dataSourceName, true, CultureInfo.CurrentCulture);
                if (i == 0)
                {                    
                    return true;                    
                }                
            }            
            log.ExitFunction();            
            return false;
        }
        #endregion

        #endregion
    }
}
