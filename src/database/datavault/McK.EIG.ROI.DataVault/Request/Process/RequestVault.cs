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
using System.Data;
using System.Globalization;
using System.IO;

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Request.Process
{
    /// <summary>
    /// Requestor Data Vault.
    /// </summary>
    public class RequestVault
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(RequestVault));
        private static Hashtable requestHT;

        private RequestInformationVault requestInfoVault;
        private RequestCommentVault reqCommentVault;
        private BillingPaymentVault billPayVault;
        private RequestPatientInfo requestPatientInfo;
        private RequestStatusUpdate requestStatusUpdate;

        private int releaseCount;

        #endregion

        #region Constructor

        public RequestVault()
        {
            requestHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public object LoadData()
        {
            log.EnterFunction();

            //Load RequestInformation vault
            requestInfoVault = new RequestInformationVault();
            requestInfoVault.ModeType = VaultMode.Create;
            Load(requestInfoVault);

            //Load Request Patient Information vault
            requestPatientInfo = new RequestPatientInfo();
            requestPatientInfo.ModeType = VaultMode.Create;
            Load(requestPatientInfo);

            //Load Request Status Update vault
            requestStatusUpdate = new RequestStatusUpdate();
            requestStatusUpdate.ModeType = VaultMode.Create;
            Load(requestStatusUpdate);

            //Load Request Comment vault
            reqCommentVault = new RequestCommentVault();
            reqCommentVault.ModeType = VaultMode.Create;
            Load(reqCommentVault);

            //Load the Billing and Payment Module.
            if (DataVaultConstants.IsExcelFile)
            {
                DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.CreateBillPayModule);
            }
            billPayVault = new BillingPaymentVault();
            billPayVault.ModeType = VaultMode.Create;
            Load(billPayVault);
                        
            log.ExitFunction();
            return requestHT;
        }

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public object Reload()
        {
            log.EnterFunction();

            //Load RequestInformation vault            
            requestInfoVault.ModeType = VaultMode.Update;
            Load(requestInfoVault);

            //Load Request Patient Information vault
            requestPatientInfo.ModeType = VaultMode.Update;
            Load(requestPatientInfo);

            //Load Request Status Update vault
            requestStatusUpdate.ModeType = VaultMode.Update;
            Load(requestStatusUpdate);

            //Load the Billing and Payment Module.
            if (DataVaultConstants.IsExcelFile)
            {
                DataSourceNameUtility.SetDefaultDirectoryPath(DataVaultConstants.DataSetPath, DataVaultConstants.UpdateBillPayModule);
            }
            billPayVault.ModeType = VaultMode.Update;
            Load(billPayVault);

            log.ExitFunction();
            return requestHT;
        }

        private void Load(IVault vault)
        {
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTag + vault.EntityName + "_" + vault.ModeType);
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

            if (vault.EntityName == DataVaultConstants.BillGeneralInfo)
            {
                ((BillingPaymentVault)vault).ReleaseCount = releaseCount;
            }
            else if (vault.EntityName == DataVaultConstants.RequestInfo)
            {
                ((RequestInformationVault)vault).ReleaseCount = releaseCount;
            }
            else if (vault.EntityName == DataVaultConstants.RequestPtsInfo)
            {
                ((RequestPatientInfo)vault).ReleaseCount = releaseCount;
            }
            else if (vault.EntityName == DataVaultConstants.RequestStatusReason)
            {
                ((RequestStatusUpdate)vault).ReleaseCount = releaseCount;
            }

            IDataReader reader;
            if (vault.ModeType == VaultMode.Create)
            {
                reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType);
            }
            else
            {
                reader = Utility.ReadData(vault.EntityName + "_" + vault.ModeType, releaseCount);
            }

            object entityobject = vault.Load(reader);
            if (entityobject != null)
            {
                if (requestHT.ContainsKey(vault.EntityName))
                {
                    requestHT[vault.EntityName] = entityobject;
                }
                else
                {
                    requestHT.Add(vault.EntityName, entityobject);
                }
            }

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);
            log.Debug(DataVaultConstants.EndTag + vault.EntityName + "_" + vault.ModeType);
            log.ExitFunction();
        }

        /// <summary>
        /// Return the Entity ID for the given Reference ID.
        /// </summary>
        /// <param name="refId">Refference ID</param>
        /// <param name="entityType">Type of Entity</param>
        /// <returns>Object</returns>
        public static object GetEntityObject(string entityType, string refId )
        {
            Log log = LogFactory.GetLogger(typeof(RequestVault));
            log.EnterFunction();

            Hashtable entityTable = (Hashtable)requestHT[entityType];
            if (entityTable == null)
            {
                string message = string.Format(CultureInfo.CurrentCulture,
                                               DataVaultErrorCodes.EntityNotFound,
                                               entityType);
                log.Debug(message);
                throw new VaultException(message);
            }

            object entityObject = entityTable[refId];
            if (entityObject == null)
            {
                string message = string.Format(CultureInfo.CurrentCulture,
                                               DataVaultErrorCodes.UnknownObject,
                                               refId, entityType);
                log.Debug(message);
                throw new VaultException(message);
            }
            log.ExitFunction();
            return entityObject;
        }
        
        #endregion

        #region Property

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        #endregion
    }
}
