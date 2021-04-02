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
using System.Globalization;
using System.Text;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;


namespace McK.EIG.ROI.DataVault.Admin.Validate
{
    public class ValidateRequestorType: IVault
    {
        #region Fields

        //DataBase Fields
        private const string RefId                  = "Ref_ID";
        private const string RequestorType          = "Requestor_Type";
        private const string RecordView             = "Def_Record_View";
        private const string HpfBillingTire         = "Hpf_Bill_Tier";
        private const string NonHpfBillingTire      = "NonHpf_Bill_Tier";
        private const string BillingTemplate        = "Billing_Template";
        private const string DefaultBillingTemplate = "Default";
        private const string MappingRequestorType   = "RTRef_ID";
        private const string SystemSeed             = "IsSystemSeed";

        private const string ReleaseNumber = "Release_Counter";

        private Hashtable recordViewHT;
        private VaultMode vaultModeType;

        #endregion

        #region Constructor

        public ValidateRequestorType()
        {
            //Initilize the requeired variable.
            Init();            
            //Get all Record view Details
            CacheAllRecordViews();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initilize the requeired variable.
        /// </summary>
        private void Init()
        {
            recordViewHT = new Hashtable(new VaultComparer());            
        }

        /// <summary>
        /// Method gets all Record View details.
        /// </summary>
        private void CacheAllRecordViews()
        {   
            Collection<RecordViewDetails> recordViewDetails = UserData.Instance.RecordViews;
            foreach (RecordViewDetails recordDetail in recordViewDetails)
            {
                recordViewHT.Add(recordDetail.Name.Trim(), recordDetail);
            }
        }

        /// <summary>
        /// System Load the Requestor Types Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            RequestorTypeDetails requestorDetail;

            long recordCount           = 1;
            bool isHeaderExist         = false;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList list             = new ArrayList();
            string reqTypeRefId        = string.Empty;

            while (reader.Read())
            {
                try
                {
                    errorMessage.Remove(0, errorMessage.ToString().Length);
                    requestorDetail = new RequestorTypeDetails();
                    reqTypeRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);

                    string recordView = Convert.ToString(reader[RecordView],
                                                          CultureInfo.CurrentCulture).Trim();
                    string hpfBillingTier = Convert.ToString(reader[HpfBillingTire],
                                                          CultureInfo.CurrentCulture).Trim();
                    string nonHpfBillingTier = Convert.ToString(reader[NonHpfBillingTire],
                                                         CultureInfo.CurrentCulture).Trim();
                    requestorDetail.Name = Convert.ToString(reader[RequestorType],
                                                          CultureInfo.CurrentCulture).Trim();
                    requestorDetail.RecordViewDetails = GetRecordViewDetail(recordView, errorMessage);
                    requestorDetail.HpfBillingTier    = GetBillingTierDetail(hpfBillingTier, true,  errorMessage);
                    requestorDetail.NonHpfBillingTier = GetBillingTierDetail(nonHpfBillingTier, false, errorMessage);
                    GetAssociatedBillingTemplate(reqTypeRefId, requestorDetail, errorMessage);


                    if (vaultModeType == VaultMode.Create)
                    {
                        if (list.Contains(reqTypeRefId))
                        {
                            errorMessage.Append(DataVaultErrorCodes.ReferenceIdRepeated);
                        }
                        else
                        {
                            list.Add(reqTypeRefId);
                        }
   
                    }
                    else
                    {
                        bool isRequestorExist = ValidateRequestorTypeExist(reqTypeRefId);
                        if (!isRequestorExist)
                        {
                            errorMessage.Append(string.Format(CultureInfo.CurrentCulture,
                                                DataVaultErrorCodes.UnknownObject,
                                                reqTypeRefId,
                                                EntityName + "_" + VaultMode.Create));                            
                        }
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
                        ValidateUtility.WriteLog("Admin",
                            (vaultModeType == VaultMode.Create) ? DataVaultConstants.CreateAdminModule : DataVaultConstants.UpdateAdminModule,
                            "Requestor Type",
                            EntityName + "_" + vaultModeType);
                    }
                    ValidateUtility.WriteLog(reqTypeRefId, recordCount, cause.Message);
                }               
                recordCount++;
            }           
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            return null;
        }       

        private RecordViewDetails GetRecordViewDetail(string recordView, StringBuilder errorMessage)
        {            
            if (recordViewHT.Count == 0)
            {
                errorMessage.AppendFormat("Record View Not found for Login user.");
                return null;
            }
            if (!recordViewHT.ContainsKey(recordView))
            {   
                errorMessage.AppendFormat(CultureInfo.CurrentCulture, DataVaultErrorCodes.RecordViewNotFound, recordView);
            }            
            return (RecordViewDetails)recordViewHT[recordView];
        }

        private BillingTierDetails GetBillingTierDetail(string billingTier, bool isHpf, StringBuilder errorMessage)
        {
            
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            string entity = DataVaultConstants.BillingTier + "_" + VaultMode.Create;

            IDataReader reader = Utility.ReadData(entity, RefId, billingTier);            
            BillingTierDetails tierDetails = new BillingTierDetails();
            bool isTierExist = false;
            while(reader.Read())
            {
                isTierExist = true;
                tierDetails.Id = 1;
                string mediaTypeRefId = reader["MTRef_Id"].ToString();
                MediaTypeDetails typeDetails = new MediaTypeDetails();
                typeDetails.Name = GetMediaTypeName(mediaTypeRefId);
                typeDetails.Id = 1;
                tierDetails.MediaType = typeDetails;
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }
            if(!isTierExist)
            {
                errorMessage.Append("Billing Tier not found RefId= " + billingTier);
            }
            if (isHpf)
            {
                if (tierDetails.MediaType.Name != ROIConstants.Electronic)
                {
                    errorMessage.Append("Hpf Billing Tier type is not Electronic, Billing Tier RefId= " + billingTier);
                }
            }

            if (vaultModeType == VaultMode.Update)
            {
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            }
            return tierDetails;
        }

        private static string GetMediaTypeName(string mediaTypeRefId)
        {            
            string entity = DataVaultConstants.MediaType + "_" + VaultMode.Create;
            IDataReader reader = Utility.ReadData(entity, RefId, mediaTypeRefId);
            string mediaTypeName = string.Empty; 
            while (reader.Read())
            {
                mediaTypeName = reader["Media_Type_Name"].ToString();                
            }
            if (!reader.IsClosed)
            {
                reader.Close();
            }            
            return mediaTypeName;
        }
       
        private void GetAssociatedBillingTemplate(string reqTypeRefId, RequestorTypeDetails requestorDetail, StringBuilder errorMessage)
        {               
            long count = Utility.GetCount(DataVaultConstants.RequestorTypeBT + "_" + vaultModeType, MappingRequestorType, reqTypeRefId);
            if (count == 0)
            {
                return;
            }

            using (IDataReader requestorTypeBT = Utility.ReadData(DataVaultConstants.RequestorTypeBT + "_" + vaultModeType, MappingRequestorType, reqTypeRefId))
            {
                requestorTypeBT.Read();

                string defaultBTRefId = Convert.ToString(requestorTypeBT[DefaultBillingTemplate],
                                                          CultureInfo.CurrentCulture).Trim();
                string[] billingRefId = new string[] { };
                string billingtemplates = Convert.ToString(requestorTypeBT[BillingTemplate],
                                                         CultureInfo.CurrentCulture).Trim();
                if (billingtemplates.Length != 0)
                {
                    billingRefId = billingtemplates.Split('~');
                }

                //Check weather the default billing template exist in the associated billing template.
                if (defaultBTRefId.Length > 0)
                {
                    if (string.Compare(defaultBTRefId, "Please Select", true, CultureInfo.CurrentCulture) != 0)
                    {
                        int i = Array.BinarySearch(billingRefId, defaultBTRefId, CaseInsensitiveComparer.DefaultInvariant);
                        if (i < 0)
                        {
                            errorMessage.Append(DataVaultErrorCodes.BillingTemplateNotAssociated + reqTypeRefId);
                        }
                    }
                }

                requestorDetail.AssociatedBillingTemplates.Clear();

                for (int i = 0; i < billingRefId.Length; i++)
                {
                    AssociatedBillingTemplate template = GetBillingTemplateName(billingRefId[i]);
                    template.IsDefault = (billingRefId[i] == defaultBTRefId);
                    requestorDetail.AssociatedBillingTemplates.Add(template);
                }
                if (!requestorTypeBT.IsClosed)
                {
                    requestorTypeBT.Close();
                }
            }
        }

        private AssociatedBillingTemplate GetBillingTemplateName(string billingTemplateRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            string entity = DataVaultConstants.BillingTemplate + "_" + VaultMode.Create;
            long count = Utility.GetCount(entity, RefId, billingTemplateRefId);
            AssociatedBillingTemplate billingTemplate = new AssociatedBillingTemplate();
            if (count != 0)
            {
                billingTemplate.BillingTemplateId = count;
            }
            if (vaultModeType == VaultMode.Update)
            {
                if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            }
            return billingTemplate;
        }

        private bool ValidateRequestorTypeExist(string reqTypeRefId)
        {
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateAdminModule);
            long count = Utility.GetCount(EntityName + "_" + VaultMode.Create, RefId, reqTypeRefId);
            if (DataVaultConstants.IsExcelFile) DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateAdminModule);
            return (count > 0);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get { return DataVaultConstants.RequestorTypeGeneral; }
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

        #endregion
    }
}
