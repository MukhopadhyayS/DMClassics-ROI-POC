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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Admin.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    public partial class BillingPaymentVault
    {
        #region Fields

        //Billing Tier details.
        private const string BillingTierRefId = "BillingTier";
        private const string BillingCopies    = "Copies";
        private const string BillingPage      = "Pages";

        private const string ExcelValidateDocument = "Select {0}, Count(*) as TotalCount From [{1}$] WHERE {2} = '{3}' ";
        private const string CsvValidateDocument   = "Select {0}, Count(*) as TotalCount From {1}.csv WHERE {2} = '{3}' ";

        #endregion

        #region Methods

        /// <summary>
        /// Get the document charge details for the Request need to be released.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        private ReleaseDetails GetDocumentChargeDetails(ReleaseDetails releaseDetail, string releaseRefId)
        {
            log.EnterFunction();

            string query;
            string entity = DataVaultConstants.BillDocChargeInfo + "_" + modeType;

            if (modeType == VaultMode.Create)
            {
                query = (DataVaultConstants.IsExcelFile)
                        ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                        : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + " = '" + releaseRefId + "'";
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                        ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "' AND Release_Counter = " + releaseCount
                        : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + " = '" + releaseRefId + "' AND Release_Counter = " + releaseCount;
            }

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillDocChargeInfo + "_" + modeType, query))
            {
                bool check;
                try
                {
                    while (reader.Read())
                    {
                        check = false;
                        string billingTierRefID = Convert.ToString(reader[BillingTierRefId],
                                                                   CultureInfo.CurrentCulture);
                        BillingTierDetails tierDetails = (BillingTierDetails)AdminVault.GetEntityObject
                                                         (DataVaultConstants.BillingTier,
                                                          billingTierRefID);
                        foreach (DocumentChargeDetails documentCharge in releaseDetail.DocumentCharges)
                        {
                            if (tierDetails.Id.Equals(documentCharge.BillingTierId))
                            {
                                check = true;
                                documentCharge.Copies = Convert.ToInt32(reader[BillingCopies], CultureInfo.CurrentCulture);
                            }
                        }
                        if (!check)
                        {
                            throw new VaultException("Billing Tier not found RefId = " + tierDetails.Id);
                        }
                    }
                }
                catch (ArgumentException cause)
                {
                    throw new VaultException(cause.Message, cause);
                }
                catch (OverflowException cause)
                {
                    throw new VaultException(cause.Message, cause);
                }
                catch (FormatException cause)
                {
                    throw new VaultException(cause.Message, cause);
                }
                catch (Exception cause)
                {
                    throw new VaultException(cause.Message, cause);
                }
            }                
        
            log.ExitFunction();
            return releaseDetail;
        }

        #endregion 
    }
}
