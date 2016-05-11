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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Requestor Type Data Vault.
    /// </summary>
    public class RequestorTypeVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(RequestorTypeVault));

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
        private Hashtable requestorTypeHT;
        private VaultMode modeType;

        private int releaseCount;

        #endregion

        #region Constructor

        public RequestorTypeVault()
        {
            //Initilize the requeired variable.
            Init();
            //Get all Requestor Type Details.
            CacheAllSeedData();
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
            recordViewHT    = new Hashtable(new VaultComparer());
            requestorTypeHT = new Hashtable(new VaultComparer());            
        }

        /// <summary>
        /// System Load the Requestor Types Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            try
            {   
                long recordcount = 1;

                while (reader.Read())
                {
                    RequestorTypeDetails requestorDetail;
                    string reqTypeRefId          = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    
                    if (modeType == VaultMode.Create)
                    {
                        requestorDetail = new RequestorTypeDetails();
                        requestorDetail.Name = Convert.ToString(reader[RequestorType],
                                               CultureInfo.CurrentCulture).Trim();
                    }
                    else
                    {
                        if (!requestorTypeHT.ContainsKey(reqTypeRefId))
                        {
                            string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, reqTypeRefId, EntityName + "_" + VaultMode.Create);
                            log.Debug(message);
                            throw new VaultException(message);
                        }
                        requestorDetail = requestorTypeHT[reqTypeRefId] as RequestorTypeDetails;
                    }
                    string recordView                 = Convert.ToString(reader[RecordView],
                                                        CultureInfo.CurrentCulture).Trim();
                    requestorDetail.RecordViewDetails = GetRecordViewDetail(recordView);
                    string hpfBillingTier             = Convert.ToString(reader[HpfBillingTire],
                                                        CultureInfo.CurrentCulture).Trim();
                    string nonHpfBillingTier          = Convert.ToString(reader[NonHpfBillingTire],
                                                        CultureInfo.CurrentCulture).Trim();
                    requestorDetail.HpfBillingTier    = GetBillingTierDetail(hpfBillingTier, true);
                    requestorDetail.NonHpfBillingTier = GetBillingTierDetail(nonHpfBillingTier, false);

                    if (modeType == VaultMode.Create)
                    {
                        bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                             CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                             false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                        GetAssociatedBillingTemplate(reqTypeRefId, requestorDetail);
                        //Save the BillingTemplate object.
                        CreateRequestorType(requestorDetail, reqTypeRefId, isSystemSeed, recordcount);
                    }
                    else
                    {
                        //Save the Requestor Type.
                        requestorDetail = UpdateRequestorType(requestorDetail, recordcount);
                        requestorTypeHT[reqTypeRefId] = requestorDetail;
                    }
                    recordcount++;
                }
                if (modeType == VaultMode.Update)
                {
                    UpdateAssociatedBillTemplate();
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (ArgumentException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            finally
            {
                reader.Close();
            }

            log.ExitFunction();
            return requestorTypeHT;            
        }

        private void CreateRequestorType(RequestorTypeDetails detail, string reqTypeRefId, bool isSystemSeed, long recordcount)
        {
            log.EnterFunction();
            if (isSystemSeed)
            {
                RequestorTypeDetails requestorDetail = requestorTypeHT[detail.Name] as RequestorTypeDetails;
                if (requestorDetail == null)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSystemSeedData, detail.Name, EntityName);
                    log.Debug(message);
                    throw new VaultException(message);
                }
                requestorDetail.RecordViewDetails = detail.RecordViewDetails;
                requestorDetail.HpfBillingTier    = detail.HpfBillingTier;
                requestorDetail.NonHpfBillingTier = detail.NonHpfBillingTier;
                requestorDetail.AssociatedBillingTemplates.Clear();
                foreach (AssociatedBillingTemplate billingTempl in detail.AssociatedBillingTemplates)
                {
                    requestorDetail.AssociatedBillingTemplates.Add(billingTempl);
                }

                //Save the Requestor Type.
                requestorDetail = UpdateRequestorType(requestorDetail, recordcount);
                requestorTypeHT.Remove(requestorDetail.Name);
                requestorTypeHT.Add(reqTypeRefId, requestorDetail);
            }
            else
            {
                //Save the Requestor Type.
                requestorTypeHT.Add(reqTypeRefId, SaveRequestorType(detail, recordcount));
            }
            log.ExitFunction();
        }

        private void UpdateAssociatedBillTemplate()
        {
            long count;
            string entity = DataVaultConstants.RequestorTypeBT + "_" + ModeType;
            string query = (DataVaultConstants.IsExcelFile)
                            ? "SELECT " + MappingRequestorType + " FROM [" + entity + "$] WHERE Release_Counter = " + releaseCount
                            : "SELECT " + MappingRequestorType + " FROM " + entity + ".csv WHERE Release_Counter = " + releaseCount;

            using (IDataReader requestorTypeBT = Utility.ReadData(EntityName, query))
            {
                count = 1;
                while (requestorTypeBT.Read())
                {
                    string reqRefId = Convert.ToString(requestorTypeBT[MappingRequestorType],
                                                       CultureInfo.CurrentCulture).Trim();
                    RequestorTypeDetails requestorDetail = requestorTypeHT[reqRefId] as RequestorTypeDetails;
                    if (requestorDetail == null)
                    {
                        string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, reqRefId, DataVaultConstants.RequestorTypeBT + "_" + modeType);
                        log.Debug(message);
                        throw new VaultException(message);
                    }
                    GetUpdatedAssociatedBT(reqRefId, requestorDetail);
                    requestorDetail = UpdateRequestorType(requestorDetail, count);
                    requestorTypeHT[reqRefId] = requestorDetail;
                }
            }
        }
       
        /// <summary>
        /// Get the associated billing template for the requestor type.
        /// </summary>
        /// <param name="reqTypeRefId"></param>
        private void GetAssociatedBillingTemplate(string reqTypeRefId, RequestorTypeDetails requestorDetail)
        {
            log.EnterFunction();
            //Validate of billing template.
            long count = Utility.GetCount(DataVaultConstants.RequestorTypeBT + "_" + modeType, MappingRequestorType, reqTypeRefId);
            if(count == 0)
            {
                return;
            }
            if (count > 1)
            {
                string message = "More then one Billing templates found for the requestor type reference id" + reqTypeRefId;
                log.Debug(message);
                throw new VaultException(message);
            }

            using (IDataReader requestorTypeBT = Utility.ReadData(DataVaultConstants.RequestorTypeBT + "_" + modeType, MappingRequestorType, reqTypeRefId))
            {
                requestorTypeBT.Read();

                string defaultBTRefId = Convert.ToString(requestorTypeBT[DefaultBillingTemplate],
                                                          CultureInfo.CurrentCulture).Trim();
                string[] billingRefId = new string[] { };
                string billingtemplates =Convert.ToString(requestorTypeBT[BillingTemplate],
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

                            string message = DataVaultErrorCodes.BillingTemplateNotAssociated + reqTypeRefId;
                            log.Debug(message);
                            throw new VaultException(message);
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
            }
            log.ExitFunction();
        }

        /// <summary>
        /// Get the associated billing template for the requestor type.
        /// </summary>
        /// <param name="reqTypeRefId"></param>
        private void GetUpdatedAssociatedBT(string reqTypeRefId, RequestorTypeDetails requestorDetail)
        {
            log.EnterFunction();
            //Validate of billing template.

            string countQuery;
            string query;
            string entity = DataVaultConstants.RequestorTypeBT + "_" + modeType;

            if (DataVaultConstants.IsExcelFile)
            {
                countQuery = "SELECT COUNT(*) FROM [" + entity + "$] WHERE RTRef_ID = '" + reqTypeRefId + "' AND Release_Counter = " + releaseCount;
                query      = "SELECT * FROM [" + entity + "$]  WHERE RTRef_ID  = '" + reqTypeRefId + "' AND Release_Counter = " + releaseCount;
            }
            else
            {
                countQuery = "SELECT COUNT(*) FROM " + entity + ".csv WHERE RTRef_ID = '" + reqTypeRefId + "' AND Release_Counter = " + releaseCount;
                query = "SELECT * FROM " + entity + ".csv  WHERE RTRef_ID  = '" + reqTypeRefId + "' AND Release_Counter = " + releaseCount;
            }
            IDataReader requestorCount = Utility.ReadData(EntityName, countQuery);
            requestorCount.Read();
            long count = requestorCount.GetInt64(0);
            requestorCount.Close();

            if (count == 0)
            {
                return;
            }
            if (count > 1)
            {
                string message = "More then one Billing templates found for the requestor type reference id" + reqTypeRefId;
                log.Debug(message);
                throw new VaultException(message);
            }

            using (IDataReader requestorTypeBT = Utility.ReadData(EntityName, query))
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

                            string message = DataVaultErrorCodes.BillingTemplateNotAssociated + reqTypeRefId;
                            log.Debug(message);
                            throw new VaultException(message);
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
            }
            log.ExitFunction();
        }

        private AssociatedBillingTemplate GetBillingTemplateName(string templRefId)
        {
            log.EnterFunction();
            BillingTemplateDetails templDetails = (BillingTemplateDetails)AdminVault.GetEntityObject(DataVaultConstants.BillingTemplate, templRefId);            
            log.ExitFunction();
            return ConvertToAssociatedBillingTemplate(templDetails);
        }

        /// <summary>
        /// Create the AssociatedBillingTemplate.
        /// </summary>
        /// <param name="templateDetails"></param>
        /// <returns></returns>
        private AssociatedBillingTemplate ConvertToAssociatedBillingTemplate(BillingTemplateDetails templateDetails)
        {
            log.EnterFunction();
            AssociatedBillingTemplate associatedBillingTemplate = new AssociatedBillingTemplate();
            associatedBillingTemplate.BillingTemplateId = templateDetails.Id;
            associatedBillingTemplate.Name = templateDetails.Name;
            log.ExitFunction();
            return associatedBillingTemplate;
        }

        /// <summary>
        /// Load all seed data.
        /// </summary>
        private void CacheAllSeedData()
        {
            log.EnterFunction();
            try
            {
                ROIAdminController adminController = ROIAdminController.Instance;
                Collection<RequestorTypeDetails> requestorTypeDetails = adminController.RetrieveAllRequestorTypes();
                foreach (RequestorTypeDetails requestorTypeDetail in requestorTypeDetails)
                {
                    RequestorTypeDetails requestorDetail = adminController.GetRequestorType(requestorTypeDetail.Id);
                    requestorTypeHT.Add(requestorTypeDetail.Name.Trim(), requestorDetail);
                }
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(cause.Message, cause);
            }

            log.ExitFunction();
        }

        /// <summary>
        /// Method gets all Record View details.
        /// </summary>
        private void CacheAllRecordViews()
        {
            log.EnterFunction();

            //Collection<RecordViewDetails> recordViewDetails = ROIAdminController.Instance.RetrieveAllRecordViews(false);
            Collection<RecordViewDetails> recordViewDetails = UserData.Instance.RecordViews;
            foreach (RecordViewDetails recordDetail in recordViewDetails)
            {
                recordViewHT.Add(recordDetail.Name.Trim(), recordDetail);
            }

            log.ExitFunction();
        }

        /// <summary>
        /// Return the RecordViewDeatils for the given record view name.
        /// </summary>
        /// <param name="recordView">RecordView.</param>
        /// <returns>RecordViewDetails.</returns>
        private RecordViewDetails GetRecordViewDetail(string recordView)
        {
            log.EnterFunction();
            if (recordViewHT.Count == 0)
            {
                return null;
            }
            if (!recordViewHT.ContainsKey(recordView))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.RecordViewNotFound, recordView);
                log.Debug(message);
                throw new VaultException(message);
            }
            log.ExitFunction();
            return (RecordViewDetails)recordViewHT[recordView];
        }

        /// <summary>
        /// Returns the BillingTierDetails for the given Billing Tier Name.
        /// </summary>
        /// <param name="electronicBillingTier"></param>
        /// <returns></returns>
        private BillingTierDetails GetBillingTierDetail(string electronicBillingTier, bool isHpf)
        {
            log.EnterFunction();
            BillingTierDetails tierDetails = (BillingTierDetails)AdminVault.GetEntityObject(DataVaultConstants.BillingTier, electronicBillingTier);
            if (isHpf)
            {
                if (tierDetails.MediaType.Name != ROIConstants.Electronic)
                {
                    throw new VaultException(DataVaultErrorCodes.ElectronicBillingTier);
                }
            }
            log.ExitFunction();
            return tierDetails;
        }

       
       
        /// <summary>
        /// Passes the Requestor Type Document Type details object to the ROIAdminController for further process.
        /// </summary>
        private RequestorTypeDetails SaveRequestorType(RequestorTypeDetails requestorDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));
                //Call the ROIAdminController to save the Requestor Type details.
                requestorDetails = ROIAdminController.Instance.CreateRequestorType(requestorDetails);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestorDetails;
        }

        private RequestorTypeDetails UpdateRequestorType(RequestorTypeDetails requestorDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));
                //Call the ROIAdminController to save the Requestor Type details.
                requestorDetail = ROIAdminController.Instance.UpdateRequestorType(requestorDetail);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return requestorDetail;
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
                return modeType;
            }
            set
            {
                modeType = value;
            }
        }

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        #endregion
    }
}
