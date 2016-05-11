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
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains the invoice charge details of the request
    /// </summary>
    [Serializable]
    public class InvoiceChargeDetails : ROIModel
    {
        # region Fields

        private long id;
        private double baseCharge;
        private double paidAmount;
        private double balanceDue;
        private double invoiceSalesTax;
        private DateTime createdDate;
        private double previousBalanceDue;
        private bool isInvoiced;

        //variables for storing the sum of adjustment types.
        private double totalCreditAdjustment;
        private double totalDebitAdjustment;
        private double totalPayment;
        private double totalAdjustmentPayment;

        //variables for maintaining the taxable component.
        private double taxableAmount;
        private double nonTaxableAmount;
        private double taxCharge;

        private List<RequestTransaction> requestTransactions;

        private double formattedBalanceDue;
        private string billingLocationCode;
        private string billingLocationName;

        #endregion

        #region Properties

        /// <summary>
        /// This property is used to get or sets the Invoice Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice base charge.
        /// </summary>
        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice paid amount
        /// </summary>
        public double PaidAmount
        {
            get { return paidAmount; }
            set { paidAmount = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice balance due.
        /// </summary>
        public double BalanceDue
        {
            get { return balanceDue; }
            set 
            { 
                balanceDue = value;
                formattedBalanceDue = balanceDue;
            }
        }

        /// <summary>
        /// Gets or sets the previous invoice balance due
        /// </summary>
        public double PreviousBalanceDue
        {
            get { return previousBalanceDue; }
            set { previousBalanceDue = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice salestax.
        /// </summary>
        public double InvoiceSalesTax
        {
            get { return invoiceSalesTax; }
            set { invoiceSalesTax = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice credit adjustment total.
        /// </summary>
        public double TotalCreditAdjustment
        {
            get { return totalCreditAdjustment; }
            set { totalCreditAdjustment = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice debit adjustment total.
        /// </summary>
        public double TotalDebitAdjustment
        {
            get { return totalDebitAdjustment; }
            set { totalDebitAdjustment = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice payment total.
        /// </summary>
        public double TotalPayment
        {
            get { return totalPayment; }
            set { totalPayment = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice total adjustment payment total.
        /// </summary>
        public double TotalAdjustmentPayment
        {
            get { return totalAdjustmentPayment; }
            set { totalAdjustmentPayment = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice taxable amount.
        /// </summary>
        public double TaxableAmount
        {
            get { return taxableAmount; }
            set { taxableAmount = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice non-taxable amount.
        /// </summary>
        public double NonTaxableAmount
        {
            get { return nonTaxableAmount; }
            set { nonTaxableAmount = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice tax charge.
        /// </summary>
        public double TaxCharge
        {
            get { return taxCharge; }
            set { taxCharge = value; }
        }

        /// <summary>
        /// Gets or Sets the invoice created date
        /// </summary>
        public DateTime CreatedDate
        {
            get { return createdDate; }
            set { createdDate = value; }
        }

        /// <summary>
        /// Gets or Sets the Billing Location Code
        /// </summary>
        public string BillingLocationCode
        {
            get { return billingLocationCode; }
            set { billingLocationCode = value; }
        }

        /// <summary>
        /// CR#367826 - Gets or sets the IsInvoiced property
        /// </summary>
        public bool IsInvoiced
        {
            get { return isInvoiced; }
            set { isInvoiced = value; }
        }

        /// <summary>
        /// Gets or Sets the Billing Location Name
        /// </summary>
        public string BillingLocationName
        {
            get { return billingLocationName; }
            set { billingLocationName = value; }
        }

        /// <summary>
        ///  Property holds the request transactions
        /// </summary>
        public List<RequestTransaction> RequestTransactions
        {
            get
            {
                if (requestTransactions == null)
                {
                    requestTransactions = new List<RequestTransaction>();
                }
                return requestTransactions;
            }
            set { requestTransactions = value; }
        }

        public double FormattedBalanceDue
        {
            get { return formattedBalanceDue; }
        }

        #endregion
    }
}
