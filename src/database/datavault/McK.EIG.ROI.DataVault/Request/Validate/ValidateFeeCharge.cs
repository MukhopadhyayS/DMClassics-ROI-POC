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
using System.Collections.ObjectModel;
using System.Data;
using System.Globalization;
using System.Text;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

using McK.EIG.ROI.DataVault.Admin.Process;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    public partial class ValidateBillingPayment
    {
        #region Fields
        
        private const string BTemplateRefID = "BTemplate_RefID";
        private const string FeeTypeRefId = "FeeTypeRefID";
        private const string CustomFee = "IsCustom";

        #endregion

        #region Methods

        /// <summary>
        /// Get the fee charge information.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private void GetFeeChargeDetails(Hashtable feeChargeList, string releaseRefId)
        {            
            log.EnterFunction();
            string billingTemplate = GetBillingTemplate(releaseRefId);
            ArrayList feeList = new ArrayList();
            if (billingTemplate != null)
            {
                if (feeChargeList.ContainsKey(billingTemplate))
                {
                    feeList = (ArrayList)feeChargeList[billingTemplate];
                }
                else
                {
                    errorMessage.Append("Billing template is not associated with the release Template RefId is '" + billingTemplate + "'.");
                    return ;
                }
            }
            GetFeeChargeInfo(feeList, releaseRefId, billingTemplate);
            log.ExitFunction();            
        }

        /// <summary>
        /// Method returns the billing template selected for release.
        /// </summary>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private string GetBillingTemplate(string releaseRefId)
        {
            log.EnterFunction();
            string query;
            string entity = DataVaultConstants.BillFeeInfoGeneral + "_" + vaultModeType;           
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                    : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";
                               

            //Validation of the Billing Template sheet.
            IDataReader countReader = Utility.ReadData(DataVaultConstants.BillFeeInfoGeneral + "_" + vaultModeType, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            
            //If more then one billing template is found.
            if (count > 1)
            {
                string message = "More then one Billing template found for the release RefId "+releaseRefId;
                errorMessage.AppendLine(message);
            }
            
            //if no billing template provided by user.
            if (count == 0)
            {
                return string.Empty;
            }            

            
            query = (DataVaultConstants.IsExcelFile)
                    ? "SELECT * FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                    : "SELECT * FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";


            string billTemplate = string.Empty;
            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillFeeInfoGeneral + "_" + vaultModeType, query))
            {
                while (reader.Read())
                {
                    billTemplate = Convert.ToString(reader[BTemplateRefID], CultureInfo.CurrentCulture).Trim();                  

                }
                if (!reader.IsClosed)
                {
                    reader.Close();
                }
            }
            log.ExitFunction();
            return billTemplate;
        }
      

        /// <summary>
        /// Get the fee charge details selected for the release.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        private void GetFeeChargeInfo(ArrayList feeList, string releaseRefId, string associatedBillingTemplate)
        {
            log.EnterFunction();

            string FeeChgExcelCreate = "Select * From [" + DataVaultConstants.BillFeeChargeInfo + "_" + vaultModeType + "$] WHERE " + ReleaseRefId + " = '{0}' ";
            string FeeChgCsvCreate = "Select *  From " + DataVaultConstants.BillFeeChargeInfo + "_" + vaultModeType + ".csv WHERE " + ReleaseRefId + " = '{0}' ";

            string query;
            
            query = (DataVaultConstants.IsExcelFile)
                    ? string.Format(CultureInfo.CurrentCulture, FeeChgExcelCreate, releaseRefId)
                    : string.Format(CultureInfo.CurrentCulture, FeeChgCsvCreate, releaseRefId);
            
            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillFeeChargeInfo + "_" + vaultModeType, query))
            {
                while (reader.Read())
                {
                    string feeTypeRefId = Convert.ToString(reader[FeeTypeRefId], CultureInfo.CurrentCulture);
                    //double amount = Convert.ToInt64(reader[Amount], CultureInfo.CurrentCulture);
                    bool isCustom = (Convert.ToString(reader[CustomFee], CultureInfo.CurrentCulture).Length == 0) ? false :
                                      Convert.ToBoolean(reader[CustomFee], CultureInfo.CurrentCulture);
                    if (!isCustom)
                    {
                        bool check = feeList.Contains(feeTypeRefId);
                        if (!check)
                        {                            
                            errorMessage.AppendLine("Unknown Fee type found for Release, ");
                            errorMessage.Append("Associated billing or Default Billing template RefId is '" + associatedBillingTemplate + "', ");
                            errorMessage.Append("Associated Fee type RefId= '" + feeTypeRefId + "'");
                            errorMessage.Append("Fee type is not associated with billing template.");
                        }
                    }
                }
            }

            log.ExitFunction();
        }

        #endregion
    }
}
