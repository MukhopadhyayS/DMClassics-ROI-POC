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

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    public partial class BillingPaymentVault
    {
        #region Fields

        //Release Payment Info.
        private const string PayType = "Pay_Type";
        private const string PayNote = "Pay_Note";        

        #endregion

        #region Method

        /// <summary>
        /// Get the payment details for the release.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private ReleaseDetails GetPaymentDetails(ReleaseDetails releaseDetail, string releaseRefId)
        {
            log.EnterFunction();
            long rowcount = 0;

            string query;
            string entity = DataVaultConstants.BillPayInfo + "_" + modeType;

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

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillPayInfo + "_" + modeType, query))
            {
                try
                {   
                    RequestTransaction transaction;
                    while (reader.Read())
                    {
                        rowcount++;

                        string payType     = Convert.ToString(reader[PayType], CultureInfo.CurrentCulture).Trim();
                        double amount      = Convert.ToDouble(reader[Amount], CultureInfo.CurrentCulture);
                        string description = Convert.ToString(reader[PayNote], CultureInfo.CurrentCulture).Trim();

                        if (payType.Length == 0)
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidPayType, payType, rowcount);
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
                        //Validate whether payment method available.
                        PaymentMethodDetails payDetails = (PaymentMethodDetails)AdminVault.GetEntityObject(DataVaultConstants.PaymentMethod, payType);

                        transaction = new RequestTransaction();
                        transaction.TransactionType = TransactionType.Payment;
                        transaction.Description     = payDetails.Name + " " + description;
                        transaction.Amount          = amount;
                        transaction.CreatedDate = DateTime.Now;

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

        #endregion
    }
}
