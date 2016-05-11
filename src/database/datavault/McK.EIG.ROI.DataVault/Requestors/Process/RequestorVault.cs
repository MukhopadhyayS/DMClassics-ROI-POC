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

using McK.EIG.Common.Utility.Logging;

using McK.EIG.ROI.DataVault.Base;

namespace McK.EIG.ROI.DataVault.Requestors.Process
{
    /// <summary>
    /// Requestor Data Vault.
    /// </summary>
    public class RequestorVault
    {
        #region Fields

        Log log = LogFactory.GetLogger(typeof(RequestorVault));
        private static Hashtable requestorHT;

        private RequestorInformationVault requestorInformationVault;

        private int releaseCount;

        #endregion

        #region Constructor

        public RequestorVault()
        {
            requestorHT = new Hashtable();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public object LoadData()
        {
            log.EnterFunction();

            //Load PatientInformation vault
            requestorInformationVault = new RequestorInformationVault();
            requestorInformationVault.ModeType = VaultMode.Create;
            Load(requestorInformationVault);
                        
            log.ExitFunction();
            return requestorHT;
        }

        /// <summary>
        /// Call the Entity level load method.
        /// </summary>
        public object Reload()
        {
            log.EnterFunction();

            //Load PatientInformation vault            
            requestorInformationVault.ModeType = VaultMode.Update;
            Load(requestorInformationVault);

            log.ExitFunction();
            return requestorHT;
        }


        private void Load(IVault vault)
        {
            log.EnterFunction();
            log.Debug(DataVaultConstants.StartTag + vault.EntityName);
            log.Debug(DataVaultConstants.StartTime + DateTime.Now);

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
                if (requestorHT.ContainsKey(vault.EntityName))
                {
                    requestorHT[vault.EntityName] = entityobject;
                }
                else
                {
                    requestorHT.Add(vault.EntityName, entityobject);
                }
            }

            log.Debug(DataVaultConstants.EndTime + DateTime.Now);
            log.Debug(DataVaultConstants.EndTag + vault.EntityName);
            log.ExitFunction();
        }

        /// <summary>
        /// Return the Entity ID for the given Reference ID.
        /// </summary>
        /// <param name="refId">Refference ID</param>
        /// <param name="entityType">Type of Entity</param>
        /// <returns>Object</returns>
        public static object GetEntityObject(string refId, string entityType)
        {
            Log log = LogFactory.GetLogger(typeof(RequestorVault));
            log.EnterFunction();

            Hashtable entityTable = (Hashtable)requestorHT[entityType];
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
