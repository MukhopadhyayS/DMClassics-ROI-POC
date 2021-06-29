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

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Base.Model;

using McK.EIG.ROI.DataVault.Base;


namespace McK.EIG.ROI.DataVault.Admin.Process
{
    /// <summary>
    /// Payment Method Data Vault
    /// </summary>
    public class PaymentMethodVault : IVault
    {
        private Log log = LogFactory.GetLogger(typeof(PaymentMethodVault));

        #region Fields
        
        //DataBase Fields
        private const string RefId         = "Ref_ID";
        private const string PMName        = "Payment_Name";
        private const string PMDescription = "Description";
        private const string PMIsDisplay   = "Form_Field";
        private const string SystemSeed    = "IsSystemSeed";

        private Hashtable paymentMethodHT;
        private VaultMode modeType;

        #endregion

        #region Constructor

        public PaymentMethodVault()
        {
            paymentMethodHT = new Hashtable(new VaultComparer());
            CacheAllPaymentMethod();
        }

        #endregion

        #region Methods

        /// <summary>
        /// System Load the DeliveryMethod Vault.
        /// </summary>
        public object Load(IDataReader reader)
        {
            log.EnterFunction();
            
            long recordCount;
            try
            {
                recordCount = 1;
                PaymentMethodDetails paymentDetail;
                while (reader.Read())
                {
                    string payRefId = Convert.ToString(reader[RefId], CultureInfo.CurrentCulture);                    
                    paymentDetail = new PaymentMethodDetails();
                    paymentDetail.Name        = reader[PMName].ToString();
                    paymentDetail.Description = reader[PMDescription].ToString();                    
                    paymentDetail.IsDisplay   = reader[PMIsDisplay].ToString().Length != 0 ?
                                                Convert.ToBoolean(reader[PMIsDisplay], CultureInfo.CurrentCulture) :
                                                false;
                    if (modeType == VaultMode.Create)
                    {
                        bool isSystemSeed = (Convert.ToString(reader[SystemSeed],
                                             CultureInfo.CurrentCulture).Trim().Length == 0) ?
                                             false : Convert.ToBoolean(reader[SystemSeed], CultureInfo.CurrentCulture);

                        //Save the BillingTemplate object.
                        CreatePaymentMethod(paymentDetail, payRefId, isSystemSeed, recordCount);
                    }
                    else
                    {
                        UpdatePaymentMethod(paymentDetail, payRefId, recordCount);
                    }

                   
                    recordCount++;
                }
            }
            catch (DbException cause)
            {
                log.FunctionFailure(cause);
                throw new ROIException(DataVaultErrorCodes.OdbcError, cause);
            }
            catch (InvalidCastException cause)
            {
                log.FunctionFailure(cause);
                throw new VaultException(DataVaultErrorCodes.InvalidArgument);
            }        
            finally
            {
                reader.Close();
                reader.Dispose();
            }
            log.ExitFunction();
            return paymentMethodHT;
        }

        private void CreatePaymentMethod(PaymentMethodDetails paymentDetail, string payRefId, bool isSystemSeed, long recordCount)
        {
            log.EnterFunction();
            if (isSystemSeed)
            {
                PaymentMethodDetails methodDetails = paymentMethodHT[paymentDetail.Name] as PaymentMethodDetails;
                if (methodDetails == null)
                {
                    string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidSystemSeedData, methodDetails.Name, EntityName);
                    log.Debug(message);
                    throw new VaultException(message);
                }
                methodDetails.Description = paymentDetail.Description;
                methodDetails.IsDisplay = paymentDetail.IsDisplay;

                methodDetails = SaveUpdatedPaymentMethod(methodDetails, recordCount);

                paymentMethodHT.Remove(paymentDetail.Name);
                paymentMethodHT.Add(payRefId, methodDetails);
            }
            else
            {
                paymentMethodHT.Add(payRefId, SavePaymentMethodDetail(paymentDetail, recordCount));
            }
            log.ExitFunction();
        }

        private void UpdatePaymentMethod(PaymentMethodDetails paymentDetail, string payRefId, long recordCount)
        {
            log.EnterFunction();
            if (!paymentMethodHT.ContainsKey(payRefId))
            {
                string message = string.Format(CultureInfo.CurrentCulture, DataVaultErrorCodes.InvalidData, payRefId, EntityName);
                log.Debug(message);
                throw new VaultException(message);
            }

            PaymentMethodDetails methodDetails = paymentMethodHT[payRefId] as PaymentMethodDetails;            
            methodDetails.Name = paymentDetail.Name;
            methodDetails.Description = paymentDetail.Description;
            methodDetails.IsDisplay = paymentDetail.IsDisplay;

            methodDetails = SaveUpdatedPaymentMethod(methodDetails, recordCount);
            paymentMethodHT[payRefId] = methodDetails;
            log.ExitFunction();
        }

        

        private void CacheAllPaymentMethod()
        {
            log.EnterFunction();
            try
            {
                BillingAdminController billingController = BillingAdminController.Instance;
                Collection<PaymentMethodDetails> paymentMethodDetails = billingController.RetrieveAllPaymentMethods();
                foreach (PaymentMethodDetails details in paymentMethodDetails)
                {
                    PaymentMethodDetails paydetails = billingController.GetPaymentMethod(details.Id);
                    paymentMethodHT.Add(details.Name.Trim(), paydetails);
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
        /// Passes the Payment Method object to the BillingAdminController for further process
        /// </summary>
        /// <param name="mediaTypes">MediaType object.</param>
        private PaymentMethodDetails SavePaymentMethodDetail(PaymentMethodDetails paymentDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the BillingAdminController to save the PaymentMethod Object.
                long id = BillingAdminController.Instance.CreatePaymentMethod(paymentDetail);
                paymentDetail.Id = id;

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return paymentDetail;
        }

        private PaymentMethodDetails SaveUpdatedPaymentMethod(PaymentMethodDetails paymentDetail, long recordCount)
        {
            log.EnterFunction();
            try
            {
                log.Debug(string.Format(CultureInfo.CurrentCulture,
                                       DataVaultConstants.ProcessStartTag,
                                       recordCount,
                                       DateTime.Now));

                //Call the BillingAdminController to save the PaymentMethod Object.
                paymentDetail = BillingAdminController.Instance.UpdatePaymentMethod(paymentDetail);

                log.Debug(DataVaultConstants.ProcessEndTag + DateTime.Now);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);
                throw;
            }
            log.ExitFunction();
            return paymentDetail;
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
                return DataVaultConstants.PaymentMethod;
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
