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
using System.ComponentModel;
using System.Globalization;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Request.Model
{

    public enum TransactionType
    {
        [Description("Adj")]
        Adjustment = 0,

        [Description("Pay")]
        Payment = 1,
        
        [Description("Auto Adj")]
        AutoAdjustment = 2
    }

    public enum AdjustmentPaymentType
    {
        [Description("Automatic")]
        Automatic = 0,

        [Description("Manual")]
        Manual = 1
    }

    /// <summary>
    /// RequestTransactionComparer
    /// </summary>
    public class RequestTransactionComparer : GenericComparer
    {
        protected override object GetPropertyValue(object from, PropertyDescriptor descriptor)
        {
            return "FormattedDate".Equals(descriptor.Name) ? ((RequestTransaction)from).CreatedDate : base.GetPropertyValue(from, descriptor);
        }
    }

    /// <summary>
    /// Model contains the transaction details of the request
    /// </summary>
    [Serializable]
    public class RequestTransaction
    {
        # region Fields

        public const string RequestTransactionKey = "request-transaction";
        public const string CreatedDateKey     = "date";
        public const string AmountKey          = "amount";
        public const string DescriptionKey     = "description";
        public const string IsDebitKey         = "is-debit";
        public const string TransactionTypeKey = "transaction-type";
        public const string PaymentModeKey     = "payment-mode";
        //CR#377173
        public const string AmountFormat = "^((\\d{1,13})|(\\d{1,13}\\.\\d{1,2})|(\\.\\d{1,2}))$";

        public const string ValidAmountFormat = "^((\\d{1,6})|(\\d{1,6}\\.\\d{1,2})|(\\.\\d{1,2}))$";

        private long id;

        private DateTime createdDate;
        
        private double amount;

        private string description;

        private TransactionType transactionType;

        private bool isDebit;

        private double formattedAmount;

        private string transaction;

        private string paymentMode;

        private string reasonName;

        private AdjustmentPaymentType adjustmentPaymentType;

        private bool isNewlyAdded;
        
        #endregion

        #region Properties

        public long Id
        {
            get { return id; }
            set { id = value; }
        }

        public DateTime CreatedDate
        {
            get { return createdDate; }
            set { createdDate = value; }
        }

        public string FormattedDate
        {
            get { return createdDate.ToShortDateString(); }
        }

        /// <summary>
        /// Gets or sets the amount.
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set 
            { 
                amount = value;
                formattedAmount = amount;
            }
        }

        /// <summary>
        /// Gets or sets the description.
        /// </summary>
        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public TransactionType TransactionType
        {
            get { return transactionType; }
            set 
            { 
                transactionType = value;
                transaction = EnumUtilities.GetDescription(transactionType);
            }
        }

        public AdjustmentPaymentType AdjustmentPaymentType
        {
            get { return adjustmentPaymentType; }
            set { adjustmentPaymentType = value; }
        }

        public bool IsNewlyAdded
        {
            get { return isNewlyAdded; }
            set { isNewlyAdded = value; }
        }

        /// <summary>
        /// Gets or sets the value of IsDebit
        /// </summary>
        public bool IsDebit
        {
            get { return isDebit; }
            set 
            { 
                isDebit = value;
                //if (!isDebit)
                //{
                //    formattedAmount = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ROIConstants.Overpay, ReleaseDetails.FormattedAmount(amount));
                //}
                //else
                //{
                //    formattedAmount = ReleaseDetails.FormattedAmount(amount);
                //}
            }
        }

        /// <summary>
        /// Gets or sets the value of PaymentMode
        /// </summary>
        public string PaymentMode
        {
            get { return paymentMode; }
            set { paymentMode = value; }
        }

        public double FormattedAmount
        {
            get { return formattedAmount; }
        }

        public string Transaction
        {
            get { return transaction; }
        }

        public string ReasonName
        {
            get { return reasonName; }
            set { reasonName = value; }
        }

        //To display value in the grid. CR#359230
        public string PaymentTypeDescription
        {
            get { return description.Replace(ROIConstants.Delimiter, " "); }
        }

        #endregion
    }
}
