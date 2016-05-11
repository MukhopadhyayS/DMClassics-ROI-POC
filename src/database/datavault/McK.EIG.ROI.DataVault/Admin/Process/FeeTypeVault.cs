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
    /// Fee Type Data Vault
    /// </summary>
    public class FeeTypeVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(FeeTypeVault));

        #region Fields

        //DBFields
        private const string RefId          = "Ref_ID";
        private const string FTName         = "Fee_Name";
        private const string FTChargeAmount = "Amount";
        private const string FTDescription  = "Description";

        private Hashtable feeTypeHT;
        private VaultMode modeType;

        #endregion

        #region Constructor 

        public FeeTypeVault()
        {
            feeTypeHT = new Hashtable(new VaultComparer());
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the Fee Type Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {   
            log.EnterFunction();
            long recordCount;
            try
            {
                recordCount = 1;
                FeeTypeDetails feeType;
                while (reader.Read())
                {
                    string feetypeRefID = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);

                    feeType             = new FeeTypeDetails();
                    feeType.Name        = Convert.ToString(reader[FTName], CultureInfo.CurrentCulture);
                    feeType.Amount      = (Convert.ToString(reader[FTChargeAmount], CultureInfo.CurrentCulture).Trim().Length == 0) ? 0 :
                                           Convert.ToDouble(reader[FTChargeAmount], CultureInfo.CurrentCulture);
                    feeType.Description = Convert.ToString(reader[FTDescription], CultureInfo.CurrentCulture);

                    if (modeType == VaultMode.Create)
                    {
                        feeTypeHT.Add(feetypeRefID, SaveFeeTypeDetail(feeType, recordCount));
                    }
                    else
                    {
                        UpdateFeeTypes(feeType, feetypeRefID, recordCount);
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
            finally
            {
                reader.Close();                
            }
            log.ExitFunction();
            return feeTypeHT;
        }

        private void UpdateFeeTypes(FeeTypeDetails feeType, string feetypeRefID, long recordCount)
        {
            log.EnterFunction();
            if (!feeTypeHT.ContainsKey(feetypeRefID))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, feetypeRefID, EntityName + "_" + VaultMode.Create);
                log.Debug(message);
                throw new VaultException(message);
            }

            FeeTypeDetails feeDetails = feeTypeHT[feetypeRefID] as FeeTypeDetails;
            feeDetails.Name        = feeType.Name;
            feeDetails.Amount      = feeType.Amount;
            feeDetails.Description = feeType.Description;

            feeDetails = SaveUpdatedFeeTypes(feeDetails, recordCount);
            feeTypeHT[feetypeRefID] = feeDetails;

            log.ExitFunction();
        }

        /// <summary>
        /// Passes the fee type object to the BillingAdminController for further process
        /// </summary>
        /// <param name="feeTypes">FeeType object.</param>
        private FeeTypeDetails SaveFeeTypeDetail(FeeTypeDetails feeTypes, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the BillingAdminController to save the FeeType Object.
                long id = BillingAdminController.Instance.CreateFeeType(feeTypes);
                feeTypes.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return feeTypes;
        }

        private FeeTypeDetails SaveUpdatedFeeTypes(FeeTypeDetails feeDetails, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                         DataVaultConstants.ProcessStartTag,
                                         recordCount,
                                         DateTime.Now));

                //Call the BillingAdminController to save the FeeType Object.
                feeDetails = BillingAdminController.Instance.UpdateFeeType(feeDetails);                

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return feeDetails;
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
                return DataVaultConstants.FeeType;
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
