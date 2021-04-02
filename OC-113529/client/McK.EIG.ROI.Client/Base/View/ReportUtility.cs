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
using System.Text;
using System.IO;
using System.Runtime.InteropServices;
using System.Security;
using Microsoft.Win32;
using System.Globalization;
using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Base.View
{
    public static class ReportUtility
    {

        #region Fields

        private static RegistryKey dataSourceRoot;
        private static char separatorChar = Path.DirectorySeparatorChar;

        #endregion

        #region SetRegistryValue

        /// <summary>
        /// Sets the default direstory of the DSN
        /// </summary>
        /// <param name="dataSourceName"></param>
        /// <param name="defaultDirectory"></param>
        public static void SetRegistryValue(string dataSourceName, string defaultDirectory)
        {
            RegistryKey dsnKey;
            if (!VerifyDataSourceName(dataSourceName))
            {
                AddUserDataSourceName(dataSourceName, defaultDirectory);
            }
            else
            {
                foreach (string keyName in dataSourceRoot.GetSubKeyNames())
                {
                    int checkDsn = string.Compare(keyName, dataSourceName, true, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    if (checkDsn == 0)
                    {
                        dsnKey = dataSourceRoot.OpenSubKey(keyName, true);
                        dsnKey.SetValue("DefaultDir", defaultDirectory, RegistryValueKind.String);
                    }
                }
            }
        }

        #endregion

        #region VerifyDataSourceName

        /// <summary>
        /// Checks the registry and returns boolean 
        /// </summary>
        /// <param name="dataSourceName"></param>
        /// <returns></returns>
        public static bool VerifyDataSourceName(string dataSourceName)
        {
            RegistryKey baseRegKey = Registry.CurrentUser;
            bool flag = false;
            dataSourceRoot = baseRegKey.OpenSubKey(@"SOFTWARE\ODBC\ODBC.INI", true);
            if (dataSourceRoot == null)
            {
                return flag;
            }
            foreach (string keyName in dataSourceRoot.GetSubKeyNames())
            {
                int checkDsn = string.Compare(keyName, dataSourceName, true, System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (checkDsn == 0)
                {
                    flag = true;
                }
            }
            return flag;

        }

        #endregion

        #region Win32 API Methods

        #region AddUserDsn

        /// <summary>
        /// Creates a Datasource Name
        /// </summary>
        /// <param name="dsnName">Datasource name</param>
        /// <param name="dbPath">Default directory of the CSV file</param>
        /// <returns></returns>
        public static bool AddUserDataSourceName(string dataSourceName, string dbPath)
        {
            bool addDsnSuccess = NativeMethods.SafeNativeMethods.CreateDataSource((IntPtr)0,
                1,
                "Microsoft Text Driver (*.txt; *.csv)\0",
                "DSN=" + dataSourceName + "\0Uid=\0pwd=\0DefaultDir=" +
                dbPath + "\0");

            return addDsnSuccess;
        }

        #endregion

        internal static class NativeMethods
        {
            #region CreateDataSource
            /// <summary>
            /// Win32 API Imports
            /// </summary>
            [DllImport("ODBCCP32.dll")]
            [return: MarshalAs(UnmanagedType.Bool)]
            public static extern bool SQLConfigDataSource(IntPtr hwndParent,
            int request, string lpszDriver, string lpszAttributes);
            [SuppressUnmanagedCodeSecurityAttribute]
            internal static class SafeNativeMethods
            {

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
        }
        #endregion

        #endregion

        #region DeleteFilesFromDirectory

        /// <summary>
        /// Cleans the Cache directories
        /// </summary>
        /// <param name="path">Cache folder</param>
        public static void DeleteFilesFromDirectory(string path, string fileNotToDelete)
        {
            if (Validator.Validate(path, ROIConstants.FilepathValidation))
            {
                string[] files = Directory.GetFiles(path);
                string csvFileName = path + separatorChar + fileNotToDelete;

                foreach (string file in files)
                {
                    if (file != csvFileName)
                    {
                        File.Delete(file);
                    }
                }
            }
        }

        #endregion      

        #region WriteCsvFile

        /// <summary>
        /// Writes the Csv File to another Csv File
        /// </summary>
        /// <param name="readStream"></param>
        /// <param name="writeStream"></param>
        public static void ReadWriteStream(Stream readStream, Stream writeStream)
        {
            if (readStream == null)
            {
                return;
            }

            if (writeStream == null)
            {
                return;
            }
            int Length = 256;
            Byte[] buffer = new Byte[Length];
            int bytesRead = readStream.Read(buffer, 0, Length);
            while (bytesRead > 0)
            {
                writeStream.Write(buffer, 0, bytesRead);
                bytesRead = readStream.Read(buffer, 0, Length);
            }
            readStream.Close();
            writeStream.Close();
        }

        #endregion

    }
}
