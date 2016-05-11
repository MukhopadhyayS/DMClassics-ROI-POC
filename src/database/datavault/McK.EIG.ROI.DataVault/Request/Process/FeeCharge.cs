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

namespace McK.EIG.ROI.DataVault.Request.Process
{
    public partial class BillingPaymentVault
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
        private ReleaseDetails GetFeeChargeDetails(ReleaseDetails releaseDetail, string releaseRefId)
        {            
            log.EnterFunction();
            AssociatedBillingTemplate billingTemplate = GetBillingTemplate(releaseRefId);            
            if (billingTemplate != null)
            {
                string previousBillingType = releaseDetail.BillingType;
                releaseDetail.BillingType = Convert.ToString(billingTemplate.BillingTemplateId, CultureInfo.CurrentCulture);
                GetFeeChargeForBillingTemplate(releaseDetail, previousBillingType);
            }            
            GetFeeChargeInfo(releaseDetail, releaseRefId);
            log.ExitFunction();
            return releaseDetail;
        }

        /// <summary>
        /// Method returns the billing template selected for release.
        /// </summary>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private AssociatedBillingTemplate GetBillingTemplate(string releaseRefId)
        {
            log.EnterFunction();
            List<AssociatedBillingTemplate> associatedBTList = new List<AssociatedBillingTemplate>();
            AssociatedBillingTemplate billingType = null;

            //Get the list of Associated Billing Template for the release.
            foreach (AssociatedBillingTemplate billingTemplate in requestorTypeDetails.AssociatedBillingTemplates)
            {   
                associatedBTList.Add(billingTemplate);
            }

            string query;
            string entity = DataVaultConstants.BillFeeInfoGeneral + "_" + modeType;

            if (modeType == VaultMode.Create)
            {
                query = (DataVaultConstants.IsExcelFile)
                        ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "'"
                        : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "'";
                        
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                         ? "SELECT COUNT(*) FROM [" + entity + "$] WHERE " + ReleaseRefId + "= '" + releaseRefId + "' AND Release_Counter = " + releaseCount
                         : "SELECT COUNT(*) FROM " + entity + ".csv WHERE " + ReleaseRefId + "= '" + releaseRefId + "' AND Release_Counter = " + releaseCount;
            }

            //Validation of the Billing Template sheet.
            IDataReader countReader = Utility.ReadData(DataVaultConstants.BillFeeInfoGeneral + "_" + modeType, query);
            countReader.Read();
            long count = countReader.GetInt64(0);
            countReader.Close();
            
            //If more then one billing template is found.
            if (count > 1)
            {
                string message = "More then one Billing template found for the request.";
                log.Debug(message);
                throw new VaultException(message);
            }
            
            //if no billing template provided by user.
            if (count == 0)
            {
                billingType = GetDefaultBillingTemplate(associatedBTList);
            }            

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

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillFeeInfoGeneral + "_" + modeType, query))
            {
                while (reader.Read())
                {
                    string billTemplate = Convert.ToString(reader[BTemplateRefID], CultureInfo.CurrentCulture).Trim();
                    if (billTemplate.Length != 0)
                    {
                        BillingTemplateDetails billingDetails = (BillingTemplateDetails)AdminVault.GetEntityObject(DataVaultConstants.BillingTemplate, billTemplate);
                        //billing template provide by user.
                        billingType = GetAssociatedBillingTemplate(associatedBTList, billingDetails.Id, releaseRefId);
                    }
                    else
                    {
                        //empty billing template provided by user.
                        billingType = GetDefaultBillingTemplate(associatedBTList);
                    }
                }
            }
            log.ExitFunction();
            return billingType;
        }


        /// <summary>
        /// Return the Default Billing Template associated for the release.
        /// </summary>
        /// <param name="associatedBTList"></param>
        /// <returns></returns>
        private AssociatedBillingTemplate GetDefaultBillingTemplate(List<AssociatedBillingTemplate> associatedBTList)
        {
            log.EnterFunction();
            AssociatedBillingTemplate defaultBillingTemplate = associatedBTList.Find(delegate(AssociatedBillingTemplate item) { return item.IsDefault; });
            log.ExitFunction();
            return defaultBillingTemplate;
        }

        /// <summary>
        /// Get the associated Billing Template for the release.
        /// </summary>
        /// <param name="name"></param>
        /// <param name="releaseRefId"></param>
        /// <returns></returns>
        private AssociatedBillingTemplate GetAssociatedBillingTemplate(List<AssociatedBillingTemplate> associatedBTList, long templateId, string releaseRefId)
        {
            log.EnterFunction();
            AssociatedBillingTemplate associatedBillingTemplate = associatedBTList.Find(delegate(AssociatedBillingTemplate item) { return (item.BillingTemplateId == templateId); });
            if (associatedBillingTemplate == null)
            {
                string message = "Billing Template " + templateId + " is not associated with this release RefID " + releaseRefId;
                log.Debug(message);
                throw new VaultException(message);
            }
            log.ExitFunction();
            return associatedBillingTemplate;
        }

        /// <summary>
        /// Return the Fee charges for the selected billing template.
        /// </summary>
        /// <param name="billingTemplateName"></param>
        /// <param name="releaseDetail"></param>
        private void GetFeeChargeForBillingTemplate(ReleaseDetails releaseDetail, string previousBillingType)
        {
            log.EnterFunction();
            long billingTemplateId = Convert.ToInt64(releaseDetail.BillingType, CultureInfo.CurrentCulture);
            if (modeType == VaultMode.Create)
            {
                releaseDetail.FeeCharges.Clear();
                AddDefaultFeeType(billingTemplateId, releaseDetail);
            }
            else
            {
                if (releaseDetail.BillingType != previousBillingType)
                {
                    //long previousBillingTypeId = Convert.ToInt64(previousBillingType, CultureInfo.CurrentCulture);
                    //BillingTemplateDetails template = BillingAdminController.Instance.GetBillingTemplate(previousBillingTypeId);
                    //Collection<FeeChargeDetails> feeDetails = GetDefaultFeeTypes(template);
                    //foreach (FeeChargeDetails details in feeDetails)
                    //{
                    //    releaseDetail.FeeCharges.Remove(details);
                    //}
                    releaseDetail.FeeCharges.Clear();
                    AddDefaultFeeType(billingTemplateId, releaseDetail);
                }
            }
           
            log.ExitFunction();
        }

        private void AddDefaultFeeType(long billingTemplateId, ReleaseDetails releaseDetail)
        {
            BillingTemplateDetails template = BillingAdminController.Instance.GetBillingTemplate(billingTemplateId);
            Collection<FeeChargeDetails> feeDetails = GetDefaultFeeTypes(template);
            foreach (FeeChargeDetails fee in feeDetails)
            {
                releaseDetail.FeeCharges.Add(fee);
            }
        }

        private Collection<FeeChargeDetails> GetDefaultFeeTypes(BillingTemplateDetails template)
        {
            try
            {
                Collection<FeeChargeDetails> feeList = new Collection<FeeChargeDetails>();
                FeeChargeDetails feeCharge;
                foreach (AssociatedFeeType associatedFee in template.AssociatedFeeTypes)
                {
                    FeeTypeDetails feeType = BillingAdminController.Instance.RetrieveFeeType(associatedFee.FeeTypeId);
                    feeCharge = new FeeChargeDetails();
                    feeCharge.Amount = feeType.Amount;
                    feeCharge.FeeType = feeType.Name;
                    feeList.Add(feeCharge);
                }
                return feeList;
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }
        }

        /// <summary>
        /// Get the fee charge details selected for the release.
        /// </summary>
        /// <param name="releaseDetail"></param>
        /// <param name="releaseRefId"></param>
        private void GetFeeChargeInfo(ReleaseDetails releaseDetail, string releaseRefId)
        {
            log.EnterFunction();
            if (ValidFeeCharge(releaseRefId))
            {
                List<FeeChargeDetails> defaultFeeChgDetails = new List<FeeChargeDetails>();
                foreach (FeeChargeDetails fee in releaseDetail.FeeCharges)
                {
                    defaultFeeChgDetails.Add(fee);
                }

                string FeeChgExcelCreate = "Select * From [" + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + "$] WHERE " + ReleaseRefId + " = '{0}' ";
                string FeeChgCsvCreate = "Select *  From " + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + ".csv WHERE " + ReleaseRefId + " = '{0}' ";

                string FeeChgExcelUpdate = "Select * From [" + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + "$] WHERE " + ReleaseRefId + " = '{0}' AND Release_Counter = " + releaseCount;
                string FeeChgCsvUpdate = "Select * From " + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + ".csv WHERE " + ReleaseRefId + " = '{0}' AND Release_Counter = " + releaseCount;

                string query;
                if (modeType == VaultMode.Create)
                {
                    query = (DataVaultConstants.IsExcelFile)
                            ? string.Format(CultureInfo.CurrentCulture, FeeChgExcelCreate, releaseRefId)
                            : string.Format(CultureInfo.CurrentCulture, FeeChgCsvCreate, releaseRefId);
                }
                else
                {
                    query = (DataVaultConstants.IsExcelFile)
                        ? string.Format(CultureInfo.CurrentCulture, FeeChgExcelUpdate, releaseRefId)
                        : string.Format(CultureInfo.CurrentCulture, FeeChgCsvUpdate, releaseRefId);
                }

                using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillFeeChargeInfo + "_" + modeType, query))
                {
                    while (reader.Read())
                    {
                        string feeName = Convert.ToString(reader[FeeTypeRefId], CultureInfo.CurrentCulture);
                        double amount = Convert.ToInt64(reader[Amount], CultureInfo.CurrentCulture);
                        bool isCustom = (Convert.ToString(reader[CustomFee], CultureInfo.CurrentCulture).Length == 0) ? false :
                                          Convert.ToBoolean(reader[CustomFee], CultureInfo.CurrentCulture);
                        Operation operationType = Operation.Add;
                        if (modeType == VaultMode.Update)
                        {
                            operationType = (Operation)Enum.Parse(typeof(Operation),
                                             Convert.ToString(reader[DataVaultConstants.Operation], CultureInfo.CurrentCulture).Trim(),
                                             true);
                        }
                        switch (operationType)
                        {
                            case Operation.Add:
                                if (isCustom)
                                {
                                    releaseDetail.FeeCharges.Add(GetCustomFee(feeName, amount, true));
                                }
                                else
                                {
                                    FeeTypeDetails feeInfo = (FeeTypeDetails)AdminVault.GetEntityObject(DataVaultConstants.FeeType, feeName);
                                    UpdateFeeDetails(releaseDetail, defaultFeeChgDetails, false, feeInfo.Name, amount);
                                }
                                break;
                            case Operation.Modify:
                                if (isCustom)
                                {
                                    UpdateFeeDetails(releaseDetail, defaultFeeChgDetails, true, feeName, amount);
                                }
                                else
                                {
                                    FeeTypeDetails feeInfo = (FeeTypeDetails)AdminVault.GetEntityObject(DataVaultConstants.FeeType, feeName);
                                    UpdateFeeDetails(releaseDetail, defaultFeeChgDetails, false, feeInfo.Name, amount);
                                }
                                break;
                            case Operation.Delete:
                                if (isCustom)
                                {
                                    DeleateFeeDetails(releaseDetail, defaultFeeChgDetails, feeName);
                                }
                                else
                                {
                                    string message = "Fee Type cannot be deleted, since it is Default Fee types" + feeName;
                                    log.Debug(message);
                                    throw new VaultException(message);
                                }
                                break;
                        }
                    }
                }
            }
            log.ExitFunction();
        }

        private void UpdateFeeDetails(ReleaseDetails releaseDetail, List<FeeChargeDetails> feeChgDetails, bool isCustom, string feeName, double amount)
        {
            FeeChargeDetails tempfee = feeChgDetails.Find(delegate(FeeChargeDetails item) { return item.FeeType == feeName; });
            if (tempfee == null)
            {
                if (isCustom)
                {
                    releaseDetail.FeeCharges.Add(GetCustomFee(feeName, amount, true));
                    return;
                }
                string message = "'" + feeName + "'Fee Type Not found to update.";
                log.Debug(message);
                throw new VaultException(message);
            }
            FeeChargeDetails feeDetails = releaseDetail.FeeCharges[releaseDetail.FeeCharges.IndexOf(tempfee)];
            feeDetails.Amount = amount;
        }

        private void DeleateFeeDetails(ReleaseDetails releaseDetail, List<FeeChargeDetails> feeChgDetails, string feeName)
        {
            FeeChargeDetails tempfee = feeChgDetails.Find(delegate(FeeChargeDetails item) { return item.FeeType == feeName; });
            if (tempfee == null)
            {
                string message = "'" + feeName + "'Fee Type Not found to delete.";
                log.Debug(message);
                throw new VaultException(message);
            }
            FeeChargeDetails feeDetails = releaseDetail.FeeCharges[releaseDetail.FeeCharges.IndexOf(tempfee)];
            releaseDetail.FeeCharges.Remove(feeDetails);
        }

        /// <summary>
        /// Check the duplication entires are present in excel or csv sheet.
        /// if exist method throw the vault exception and stop execution of the vault.
        /// </summary>
        /// <param name="releaseRefId">releaseRefId</param>
        /// <returns>Return True, if there is no duplicate Entries </returns>
        private bool ValidFeeCharge(string releaseRefId)
        {
            log.EnterFunction();
            string FeeChgExcelCreate = "Select FeeTypeRefID, Count(*) as TotalCount From [" + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + "$] WHERE " + ReleaseRefId + " = '{0}' GROUP BY FeeTypeRefID, IsCustom";
            string FeeChgCsvCreate = "Select FeeTypeRefID, Count(*) as TotalCount From " + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + ".csv WHERE " + ReleaseRefId + " = '{0}' GROUP BY FeeTypeRefID, IsCustom";

            string FeeChgExcelUpdate = "Select FeeTypeRefID, Count(*) as TotalCount From [" + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + "$] WHERE " + ReleaseRefId + " = '{0}' AND Release_Counter = " + releaseCount + " GROUP BY FeeTypeRefID, IsCustom";
            string FeeChgCsvUpdate = "Select FeeTypeRefID, Count(*) as TotalCount From " + DataVaultConstants.BillFeeChargeInfo + "_" + modeType + ".csv WHERE " + ReleaseRefId + " = '{0}' AND Release_Counter = " + releaseCount + " GROUP BY FeeTypeRefID, IsCustom";
            
            string query;
            if (modeType == VaultMode.Create)
            {
                query = (DataVaultConstants.IsExcelFile)
                        ? string.Format(CultureInfo.CurrentCulture, FeeChgExcelCreate, releaseRefId)
                        : string.Format(CultureInfo.CurrentCulture, FeeChgCsvCreate, releaseRefId);
            }
            else
            {
                query = (DataVaultConstants.IsExcelFile)
                    ? string.Format(CultureInfo.CurrentCulture, FeeChgExcelUpdate, releaseRefId)
                    : string.Format(CultureInfo.CurrentCulture, FeeChgCsvUpdate, releaseRefId);
            }
            

            log.Debug(query);
            StringBuilder errorMessage = new StringBuilder();

            using (IDataReader reader = Utility.ReadData(DataVaultConstants.BillFeeChargeInfo + "_" + modeType, query))
            {
                while (reader.Read())
                {
                    if (Convert.ToInt64(reader[TotalCount], CultureInfo.CurrentCulture) > 1)
                    {
                        errorMessage.AppendFormat(CultureInfo.CurrentCulture, DataVaultErrorCodes.FeeChargeRepeated, reader[FeeTypeRefId].ToString(), releaseRefId);
                    }
                }
            }

            if (errorMessage.Length > 0)
            {
                log.Debug(errorMessage.ToString());
                throw new VaultException(errorMessage.ToString());
            }
            log.ExitFunction();
            return true;
        }

        /// <summary>
        /// Return FeeCharge Details object
        /// </summary>
        /// <param name="feeName"></param>
        /// <param name="amount"></param>
        /// <param name="isCustom"></param>
        /// <returns></returns>
        private FeeChargeDetails GetCustomFee(string feeName, double amount, bool isCustom)
        {
            log.EnterFunction();
            FeeChargeDetails chargeDetails = new FeeChargeDetails();
            chargeDetails.FeeType = feeName;
            chargeDetails.Amount = amount;
            chargeDetails.IsCustomFee = isCustom;            
            log.ExitFunction();
            return chargeDetails;
        }

        #endregion
    }
}
