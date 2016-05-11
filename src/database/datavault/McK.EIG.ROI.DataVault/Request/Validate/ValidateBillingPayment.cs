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
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;

using McK.EIG.ROI.DataVault.Admin;
using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Validate
{
    /// <summary> 
    /// Class Release the request or save the Draft. 
    /// </summary> 
    public partial class ValidateBillingPayment : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(ValidateBillingPayment));

        #region Fields

        //Common Fields 
        private const string RefId = "Ref_ID";
        private const string RequestRefId = "ReqRef_ID";
        private const string ReleaseRefId = "RelRef_ID";
        private const string Amount = "Amount";
        private const string TotalCount = "TotalCount";

        private const string RequestRelease = "IsRelease";

        private VaultMode vaultModeType;

        private ReleaseDetails releaseDetail;

        private double paymentTotal;
        private double adjustmentTotal;
        private double previouslyReleasedCost;
        private double docChargeTotal;
        private double feeChargeTotal;

        private ArrayList modifyBillingTierRefIdList = new ArrayList();

        StringBuilder errorMessage = new StringBuilder();

        #endregion

        #region Methods

        /// <summary> 
        /// System Load the Billing_PaymentVault Vault. 
        /// </summary> 
        public object Load(IDataReader reader)
        {
            log.EnterFunction();

            long recordCount = 1;
            bool isHeaderExist = false;
            string reqRefId = string.Empty;
            string releaseRefId = string.Empty;

            while (reader.Read())
            {
                errorMessage.Remove(0, errorMessage.ToString().Length);
                try
                {

                    reqRefId = Convert.ToString(reader[RequestRefId], CultureInfo.CurrentCulture).Trim();
                    releaseRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture).Trim();

                    releaseDetail = new ReleaseDetails();
                    if (RequestExist(reqRefId))
                    {
                        ArrayList billingTemplatesRefIds = new ArrayList();
                        Hashtable feeTypeRefIds = new Hashtable(new VaultComparer());
                        string billingTierRefId = string.Empty;
                        string nonHpfBillingTierRefId = string.Empty;
                        string defaultBillingTemplate = string.Empty;


                        //Get the Request Information selected for release. 
                        string requestorRefId = GetRequestorRefId(reqRefId);
                        string requestorTypeRefId = GetRequestorTypeRefId(requestorRefId);
                        GetRequestorTypeDetails(requestorTypeRefId, ref billingTierRefId, ref nonHpfBillingTierRefId, ref billingTemplatesRefIds, ref defaultBillingTemplate);
                        GetBillingTemplateDetails(billingTemplatesRefIds, feeTypeRefIds);

                        //Get the Release Information. 
                        releaseDetail.RequestId = 1;
                        GetModifyNonHpfBillingTierRefId(reqRefId);
                        GetDocumentChargeDetails(releaseRefId, billingTierRefId, nonHpfBillingTierRefId, modifyBillingTierRefIdList);
                        GetFeeChargeDetails(feeTypeRefIds, releaseRefId);
                        releaseDetail = GetShippingDetails(releaseDetail, releaseRefId);
                        GetAdjustmentChargeDetails(releaseDetail, releaseRefId);
                        GetPaymentDetails(releaseDetail, releaseRefId);

                        //Whether the request need to be released. 
                        bool isReleased = (reader[RequestRelease].ToString().Length == 0) ? false :
                                           Convert.ToBoolean(reader[RequestRelease], CultureInfo.CurrentCulture);
                        if (isReleased)
                        {
                            if (releaseDetail.ShippingDetails.OutputMethod != OutputMethod.None)
                            {
                                releaseDetail.IsReleased = true;

                            }
                            else
                            {
                                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.OutputMethodNotSpecified, releaseRefId);
                                errorMessage.AppendLine(message);
                            }
                        }
                    }
                    else
                    {
                        errorMessage.Append("Request RefId '" + reqRefId + "' not found for Release RefId" + releaseRefId);
                    }
                    if (errorMessage.ToString().Trim().Length > 0)
                    {
                        throw new VaultException(errorMessage.ToString().Trim());
                    }

                }
                catch (VaultException cause)
                {
                    if (!isHeaderExist)
                    {
                        isHeaderExist = true;
                        ValidateUtility.WriteLog("BillingPayment",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateRequestModule : DataVaultConstants.UpdateRequestModule,
                            "Billing Payment",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(releaseRefId, recordCount, cause.Message);
                }
                recordCount++;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            log.ExitFunction();
            return null;
        }

        private void GetModifyNonHpfBillingTierRefId(string reqRefId)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);

            string entity = DataVaultConstants.RequestSupDocs + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RequestRefId + "= '" + reqRefId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RequestRefId + "= '" + reqRefId + "'";

            //Validation of the Billing Template sheet. 
            IDataReader requestReader = Utility.ReadData(entity, query);
            string billingTierRefId = string.Empty;
            while (requestReader.Read())
            {
                billingTierRefId = Convert.ToString(requestReader["NonHPF_BillingTier"], CultureInfo.CurrentCulture);
                if (!((modifyBillingTierRefIdList.Contains(billingTierRefId)) && (billingTierRefId.Length == 0)))
                {
                    modifyBillingTierRefIdList.Add(billingTierRefId);
                }               
            }

            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }
            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
        }

        private void GetBillingTemplateDetails(ArrayList billingTemplatesRefIds, Hashtable feeTypeTable)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);

            string entity = DataVaultConstants.BillingTemplate + "_" + VaultMode.Create;
            IDataReader requestReader;
            ArrayList list;

            foreach (string billId in billingTemplatesRefIds)
            {
                string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "= '" + billId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "= '" + billId + "'";

                requestReader = Utility.ReadData(entity, query);
                while (requestReader.Read())
                {
                    list = new ArrayList();
                    list.AddRange(Convert.ToString(requestReader["Fee_Type"], CultureInfo.CurrentCulture).Trim().Split('~'));
                    feeTypeTable.Add(billId, list);
                    break;
                }
                if (!requestReader.IsClosed)
                {
                    requestReader.Close();
                }
            }

            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
        }

        private void GetRequestorTypeDetails(string requestorTypeRefId, ref string billingTierRefId, ref string nonHpfBillingTierRefId, ref ArrayList billingTemplatesRefIds, ref string defaultBillingTier)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);

            string entity = DataVaultConstants.RequestorTypeGeneral + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "= '" + requestorTypeRefId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "= '" + requestorTypeRefId + "'";

            //Validation of the Billing Template sheet. 
            IDataReader requestReader = Utility.ReadData(entity, query);
            while (requestReader.Read())
            {
                billingTierRefId = Convert.ToString(requestReader["Hpf_Bill_Tier"], CultureInfo.CurrentCulture);
                nonHpfBillingTierRefId = Convert.ToString(requestReader["NonHpf_Bill_Tier"], CultureInfo.CurrentCulture);
                break;
            }
            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }
            entity = DataVaultConstants.RequestorTypeBT + "_" + VaultMode.Create;
            query = (DataVaultConstants.IsExcelFile)
                           ? "SELECT * FROM [" + entity + "$] WHERE RTRef_ID = '" + requestorTypeRefId + "'"
                           : "SELECT * FROM " + entity + ".csv WHERE RTRef_ID = '" + requestorTypeRefId + "'";

            requestReader = Utility.ReadData(entity, query);
            while (requestReader.Read())
            {
                string[] template = Convert.ToString(requestReader["Billing_Template"], CultureInfo.CurrentCulture).Split('~');
                billingTemplatesRefIds.AddRange(template);
                defaultBillingTier = Convert.ToString(requestReader["Default"], CultureInfo.CurrentCulture);
                break;
            }
            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }


            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
        }

        private string GetRequestorTypeRefId(string requestorRefId)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestorModule);

            string entity = DataVaultConstants.RequestorInfo + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RefId + "= '" + requestorRefId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RefId + "= '" + requestorRefId + "'";

            //Validation of the Billing Template sheet. 
            IDataReader requestReader = Utility.ReadData(entity, query);
            string requestorTypeRefId = string.Empty;
            while (requestReader.Read())
            {
                requestorTypeRefId = Convert.ToString(requestReader["Reqor_Type"], CultureInfo.CurrentCulture);
                break;
            }

            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }
            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
            return requestorTypeRefId;
        }

        private string GetRequestorRefId(string reqRefId)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);

            string entity = DataVaultConstants.RequestRequorInfo + "_" + VaultMode.Create;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT * FROM [" + entity + "$] WHERE " + RequestRefId + "= '" + reqRefId + "'"
                            : "SELECT * FROM " + entity + ".csv WHERE " + RequestRefId + "= '" + reqRefId + "'";

            //Validation of the Billing Template sheet. 
            IDataReader requestReader = Utility.ReadData(entity, query);
            string requestorRefId = string.Empty;
            while (requestReader.Read())
            {
                requestorRefId = Convert.ToString(requestReader["ReqorRef_ID"], CultureInfo.CurrentCulture);
                break;
            }

            if (!requestReader.IsClosed)
            {
                requestReader.Close();
            }
            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
            return requestorRefId;
        }

        private bool RequestExist(string reqRefId)
        {
            log.EnterFunction();
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateRequestModule);
            long count = Utility.GetCount(DataVaultConstants.RequestInfo + "_" + VaultMode.Create, RefId, reqRefId);

            if (DataVaultConstants.IsExcelFile)
            {
                if (vaultModeType == VaultMode.Create)
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
                }
                else
                {
                    DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
                }
            }
            log.ExitFunction();
            return (count > 0);
        }

        #endregion

        #region Properties

        /// <summary> 
        /// Return current entity name. 
        /// </summary> 
        public string EntityName
        {
            get { return DataVaultConstants.BillGeneralInfo; }
        }

        /// <summary> 
        /// Return the mode type. 
        /// </summary> 
        public VaultMode ModeType
        {
            get
            {
                return vaultModeType;
            }
            set
            {
                vaultModeType = value;
            }
        }

        #region ChargeDetails

        /// <summary> 
        /// Get the Total Document Charge amount. 
        /// </summary> 
        public double DocumentChargeTotal
        {
            get
            {
                return docChargeTotal;
            }
        }


        /// <summary> 
        /// Get the total fee charge amount (Fee Charge from admin and Custom FeeCharge.) 
        /// </summary> 
        public double FeeChargeTotal
        {
            get
            {
                return feeChargeTotal;
            }
        }

        /// <summary> 
        /// Pending release cost 
        /// </summary> 
        public double PendingReleaseCost
        {
            get
            {
                return DocumentChargeTotal + FeeChargeTotal + releaseDetail.ShippingDetails.ShippingCharge;
            }
        }

        /// <summary> 
        /// Total request cost - sum of pending release cost and previously released cost 
        /// </summary> 
        public double TotalRequestCost
        {
            get
            {
                return PendingReleaseCost + previouslyReleasedCost;
            }
        }

        /// <summary> 
        /// Adjutement and Payment Total sum. 
        /// </summary> 
        public double AdjustmentPaymentTotal
        {
            get { return -paymentTotal + adjustmentTotal; }
        }

        /// <summary> 
        /// Balance due- ((TotalRequestCost - PaymentTotal) +/- AdjustmentTotal) 
        /// </summary> 
        public double BalanceDue
        {
            get
            {
                return TotalRequestCost + AdjustmentPaymentTotal;
            }
        }

        #endregion

        #endregion
    }
}
