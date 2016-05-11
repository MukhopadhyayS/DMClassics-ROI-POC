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
using System.Collections.Generic;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    [Serializable]
    public class InvoiceDet
    {
        # region Fields

        public const string InvoiceInfoKey = "invoice-info";
        public const string InvoiceBillingLocationCodeKey = "inv-billing-loc-code";
        public const string InvoiceBillingLocationNameKey = "inv-billing-loc-name";
        public const string InvoiceBaseChargeKey = "base-charge";
        public const string AmountPaidKey = "amount-paid";
        public const string InvoiceBalanceDueKey = "inv-balance-due";
        public const string InvoiceSalesTax = "inv-sales-tax";

        private string billingLocationCode;

        private string billingLocationName;

        private double baseCharge;

        private double amountPaid;

        private double balanceDue;

        private double invoiceSalesTax;

        #endregion

        #region Properties
       
        /// <summary>
        /// Gets or sets the value of invoice billing location code.
        /// </summary>
        public string BillingLocationCode
        {
            get { return billingLocationCode; }
            set { billingLocationCode = value; }
        }

        /// <summary>
        /// Gets or sets the value of invoice billing location name.
        /// </summary>
        public string BillingLocationName
        {
            get { return billingLocationName; }
            set { billingLocationName = value; }
        }

        /// <summary>
        /// Gets or sets the invoice base charge amount.
        /// </summary>
        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }            
        }

        /// <summary>
        /// Gets or sets the paid amount.
        /// </summary>
        public double AmountPaid
        {
            get { return amountPaid; }
            set { amountPaid = value; }           
        }

        /// <summary>
        /// Gets or sets the invoice balance due amount.
        /// </summary>
        public double BalanceDue
        {
            get { return balanceDue; }
            set { balanceDue = value; }            
        }

        /// <summary>
        /// Gets or sets the invoice sales tax amount.
        /// </summary>
        public double SalesTax
        {
            get { return invoiceSalesTax; }
            set { invoiceSalesTax = value; }          
        }
        
        #endregion
    }
}
