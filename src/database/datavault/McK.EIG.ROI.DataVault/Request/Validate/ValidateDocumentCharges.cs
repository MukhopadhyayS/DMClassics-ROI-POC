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
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Admin.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    public partial class ValidateBillingPayment
    {
        #region Fields

        //Billing Tier details. 
        private const string BillingTierRefId = "BillingTier";
        private const string BillingCopies = "Copies";
        private const string BillingPage = "Pages";

        private const string ExcelValidateDocument = "Select {0}, Count(*) as TotalCount From [{1}$] WHERE {2} = '{3}' ";
        private const string CsvValidateDocument = "Select {0}, Count(*) as TotalCount From {1}.csv WHERE {2} = '{3}' ";

        #endregion

        #region Methods

        /// <summary> 
        /// Get the document charge details for the Request need to be released. 
        /// </summary> 
        /// <param name="releaseDetail"></param> 
        /// <param name="releaseRefId"></param> 
        private void GetDocumentChargeDetails(string releaseRefId, string billingTierRefId, string nonHpfBillingTierRefId, ArrayList modifyBillingTierRefIdList)
        {
            log.EnterFunction();
            string query = DataVaultConstants.IsExcelFile
                        ? string.Format(CultureInfo.CurrentCulture, ExcelValidateDocument, BillingTierRefId, DataVaultConstants.BillDocChargeInfo + "_" + vaultModeType, ReleaseRefId, releaseRefId)
                        : string.Format(CultureInfo.CurrentCulture, CsvValidateDocument, BillingTierRefId, DataVaultConstants.BillDocChargeInfo + "_" + vaultModeType, ReleaseRefId, releaseRefId);
            query = query + " GROUP BY " + BillingTierRefId;

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillDocChargeInfo + "_" + vaultModeType, query))
            {
                while (reader.Read())
                {                    
                    string billingPaymentBillingTierId = reader[BillingTierRefId].ToString();
                    if (!(billingTierRefId.Equals(billingPaymentBillingTierId) || nonHpfBillingTierRefId.Equals(billingPaymentBillingTierId) || modifyBillingTierRefIdList.Contains(billingPaymentBillingTierId)))
                    {
                        errorMessage.AppendLine("'" + billingPaymentBillingTierId + "' Billing Tier Not associated with the release RefId '" + releaseRefId + " '");
                    }
                }
            }
            log.ExitFunction();
        }

        #endregion
    }
}
