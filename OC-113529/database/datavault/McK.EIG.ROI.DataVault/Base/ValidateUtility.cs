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

using McK.EIG.Common.Utility.Logging;
using System.Globalization;

namespace McK.EIG.ROI.DataVault.Base
{
    public static class ValidateUtility
    {
        #region Fields
        private static VaultLogger vaultLog;
        private const string ReferenceId     = "Reference Id        : ";
        private const string RecordNumber    = "Record/Row Number   : ";
        private const string ErrorCause      = "Cause               : ";

        #endregion

        public static void WriteLog(string referenceId, long recordCount, string cause)
        {   
            CreateLogger();
            string message = string.Format(CultureInfo.CurrentCulture,
                                           DataVaultErrorCodes.ErrorMessage,
                                           recordCount,
                                           referenceId,
                                           cause);
            vaultLog.Debug(message);
        }

        public static void WriteLog(string module, string fileName, string useCase, string sheetName)
        {
            CreateLogger();
            vaultLog.DividerLine();
            vaultLog.Debug(DataVaultConstants.ModuleName + module);
            vaultLog.Debug(DataVaultConstants.FileName + fileName);
            vaultLog.Debug(DataVaultConstants.UseCase + useCase);
            vaultLog.Debug(DataVaultConstants.SheetName + sheetName);            
        }

        private static void CreateLogger()
        {
            if (vaultLog == null)
            {
                vaultLog = VaultLogger.GetInstance("VaultLogger");                
            }
        }                      
    }
}
