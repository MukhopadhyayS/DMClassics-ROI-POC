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
using System.Data;
using System.Globalization;

using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Admin.Model;

using McK.EIG.ROI.DataVault.Base;
using McK.EIG.ROI.DataVault.Admin.Process;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    public partial class BillingPaymentVault
    {
        #region Fields

        //Adjustemtn charge info.
        private const string AdjType      = "Adj_Type";
        private const string AdjReason    = "Reason";
        private const string CustomReason = "IsCustomReason";

        private const string Credit = "Credit";
        private const string Debit  = "Debit";

        private string reasonName;
 
        #endregion

        #region Method

        /// <summary>
        /// Get the adjustment details for the release.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private ReleaseDetails GetAdjustmentChargeDetails(ReleaseDetails releaseDetail, string releaseRefId)
        {
            log.EnterFunction();
            long rowcount = 0;
            releaseDetail.RequestTransactions.Clear();

            string query;
            string entity = DataVaultConstants.BillAdjInfo + "_" + modeType;

            if (modeType == VaultMode.Create)
            {
                query = (DataVaultConstants.IsExcelFile)
                        ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                        : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "' AND Release_Counter = " + releaseCount
                         : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "' AND Release_Counter = " + releaseCount;
            }

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillAdjInfo + "_" + modeType, query))
            {
                try
                {   
                    RequestTransaction transaction;

                    while (reader.Read())
                    {
                        rowcount++;                        

                        string adjType     = Convert.ToString(reader[AdjType], CultureInfo.CurrentCulture).Trim();
                        double amount      = Convert.ToDouble(reader[Amount], CultureInfo.CurrentCulture);
                        string description = Convert.ToString(reader[AdjReason], CultureInfo.CurrentCulture).Trim();
                        bool isCustom      = (Convert.ToString(reader[CustomReason], CultureInfo.CurrentCulture).Length == 0) ? false :
                                              Convert.ToBoolean(reader[CustomReason], CultureInfo.CurrentCulture);
                        
                        if (adjType.Length == 0)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidAdjType, adjType, rowcount);
                            log.Debug(message);
                            throw new VaultException(message);
                        }
                        if (amount <= 0)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidAmount, rowcount);
                            log.Debug(message);
                            throw new VaultException(message);
                        }
                        if (description.Length == 0)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidDesc, rowcount);
                            log.Debug(message);
                            throw new VaultException(message);
                        }

                        transaction = new RequestTransaction();

                        transaction.TransactionType = TransactionType.Adjustment;
                        transaction.IsDebit         = (adjType == Credit) ? false : true;
                        transaction.Description     = (isCustom) ? description : GetAdjustmentReason(description);
                        transaction.ReasonName      = (isCustom) ? string.Empty : reasonName;
                        transaction.Amount          = (adjType == Credit) ? -amount : amount;
                        transaction.CreatedDate     = DateTime.Now;
                        releaseDetail.RequestTransactions.Add(transaction);
                    }
                    log.ExitFunction();
                    return releaseDetail;
                }
                catch (ArgumentException cause)
                {
                    log.FunctionFailure(cause);
                    throw new VaultException(DataVaultErrorCodes.InvalidArgument, cause);
                }
                catch (FormatException cause)
                {
                    log.FunctionFailure(cause);
                    throw new VaultException(DataVaultErrorCodes.InvalidArgument, cause);
                }
                catch (InvalidCastException cause)
                {
                    log.FunctionFailure(cause);
                    throw new VaultException(DataVaultErrorCodes.InvalidArgument, cause);
                }
            }
        }

        private string GetAdjustmentReason(string description)
        {
            log.EnterFunction();
            ReasonDetails details = (ReasonDetails)AdminVault.GetEntityObject(DataVaultConstants.AdjustmentReason, description);
            log.ExitFunction();
            reasonName = details.Name;
            return details.DisplayText;
        }

        #endregion
    }
}
