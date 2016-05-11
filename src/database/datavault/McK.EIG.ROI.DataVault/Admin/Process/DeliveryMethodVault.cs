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
    /// Delivery Method Data Vault
    /// </summary>
    public class DeliveryMethodVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(DeliveryMethodVault));

        #region Fields

        private const string RefId         = "Ref_ID";
        private const string DMName        = "Name";
        private const string DMDescription = "Description";
        private const string DMUrl         = "URL";
        private const string DMIsDefault   = "Default";
        private const string SystemSeed    = "IsSystemSeed";

        private Hashtable deliveryMethodHT;

        private VaultMode modeType;

        #endregion

        #region Constructor

        public DeliveryMethodVault()
        {
            deliveryMethodHT = new Hashtable(new VaultComparer());
            CacheAllDeliveryMethod();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Delivery Method Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            long recordCount;
            
            try
            {
                recordCount = 1;
                DeliveryMethodDetails deliveryMethod;
                while (reader.Read())
                {
                    string deliveryRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);
                    

                    string url = reader[DMUrl].ToString();
                    deliveryMethod = new DeliveryMethodDetails();                    
                    deliveryMethod.Name = reader[DMName].ToString();
                    deliveryMethod.Description = reader[DMDescription].ToString();
                    deliveryMethod.Url = (url.Length == 0) ? null : new System.Uri(url);
                    deliveryMethod.IsDefault = reader[DMIsDefault].ToString().Length != 0 ?
                                                 Convert.ToBoolean(reader[DMIsDefault], CultureInfo.CurrentCulture) :
                                                 false;
                    if (modeType == VaultMode.Create)
                    {
                        bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                             CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                             false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);
                        //Save the BillingTemplate object.
                        CreateDeliveryMethod(deliveryMethod, deliveryRefId, isSystemSeed, recordCount);
                    }
                    else
                    {
                        UpdateDeliveryMethod(deliveryMethod, deliveryRefId, recordCount);
                    }
                    recordCount++;
                }
            }
            catch (DbException cause)
            {   
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.OdbcError);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            catch (FormatException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }
            finally
            {
                reader.Close();               
            }
            log.ExitFunction();
            return deliveryMethodHT;
        }

        private void CreateDeliveryMethod(DeliveryMethodDetails deliveryMethod, string deliveryRefId, bool isSystemSeed, long recordCount)
        {
            log.EnterFunction();
            if (isSystemSeed)
            {
                DeliveryMethodDetails methodDetails = deliveryMethodHT[deliveryMethod.Name] as DeliveryMethodDetails;
                if (methodDetails == null)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSystemSeedData, deliveryMethod.Name, EntityName);
                    log.Debug(message);
                    throw new VaultException(message);
                }
                methodDetails.Name        = deliveryMethod.Name;
                methodDetails.Description = deliveryMethod.Description;
                methodDetails.Url         = deliveryMethod.Url;
                methodDetails.IsDefault   = deliveryMethod.IsDefault;

                methodDetails = SaveUpdatedDeliveryMethod(methodDetails, recordCount);
                deliveryMethodHT.Remove(deliveryMethod.Name);
                deliveryMethodHT.Add(deliveryRefId, methodDetails);
            }
            else
            {
                //Save the Delivery Method detail.
                deliveryMethodHT.Add(deliveryRefId, SaveDeliveryMethodDetail(deliveryMethod, recordCount));
            }
            log.ExitFunction();
        }

        private void UpdateDeliveryMethod(DeliveryMethodDetails deliveryMethod, string deliveryRefId, long recordCount)
        {
            log.EnterFunction();
            if (!deliveryMethodHT.ContainsKey(deliveryRefId))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, deliveryRefId, EntityName);
                log.Debug(message);
                throw new VaultException(message);
            }
            DeliveryMethodDetails methodDetails = deliveryMethodHT[deliveryRefId] as DeliveryMethodDetails;
            
            methodDetails.Name        = deliveryMethod.Name;
            methodDetails.Description = deliveryMethod.Description;
            methodDetails.Url         = deliveryMethod.Url;
            methodDetails.IsDefault   = deliveryMethod.IsDefault;

            methodDetails = SaveUpdatedDeliveryMethod(methodDetails, recordCount);            
            deliveryMethodHT[deliveryRefId] = methodDetails;
            log.ExitFunction();
        }

        private void CacheAllDeliveryMethod()
        {
            log.EnterFunction();
            try
            {
                ROIAdminController adminController = ROIAdminController.Instance;
                Collection<DeliveryMethodDetails> deliveryMethodDetails = adminController.RetrieveAllDeliveryMethods();
                foreach (DeliveryMethodDetails details in deliveryMethodDetails)
                {
                    DeliveryMethodDetails methodDetails = adminController.GetDeliveryMethod(details.Id);
                    deliveryMethodHT.Add(details.Name.Trim(), methodDetails);
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
        /// Passes the DeliveryMethod object to the ROIAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">DeliveryMethod object.</param>
        private DeliveryMethodDetails SaveDeliveryMethodDetail(DeliveryMethodDetails deliveryMethod, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the ROIAdminController to save the DeliveryMethod Object.
                long id = ROIAdminController.Instance.CreateDeliveryMethod(deliveryMethod);
                deliveryMethod.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return deliveryMethod;
        }

        private DeliveryMethodDetails SaveUpdatedDeliveryMethod(DeliveryMethodDetails deliveryMethod, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the ROIAdminController to save the DeliveryMethod Object.
                deliveryMethod = ROIAdminController.Instance.UpdateDeliveryMethod(deliveryMethod);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return deliveryMethod;
        }

        #endregion

        #region Properties
        /// <summary>
        /// Return current entity name.
        /// </summary>
        public string EntityName
        {
            get 
            {
                return DataVaultConstants.DeliveryMethod;
            }
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
        #endregion
    }
}
