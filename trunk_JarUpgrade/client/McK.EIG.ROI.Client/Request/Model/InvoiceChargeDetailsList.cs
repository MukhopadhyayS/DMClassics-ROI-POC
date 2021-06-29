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
    /// <summary>
    /// Model contains the list of invoice charge details of the request
    /// </summary>
    [Serializable]
    public class InvoiceChargeDetailsList : ROIModel
    {
        #region Fields

        //holds the request Id
        private long id;

        //holds the list of Invoice Charge details
        private List<InvoiceChargeDetails> invoiceCharges;

        //holds the previous request balance due
        private double previousRequestBalanceDue;

        //holds the current request balance due
        private double currentRequestBalanceDue;

        //holds the total credit adjustment
        private double totalCreditAdjustment;

        //holds the total debit adjustment
        private double totalDebitAdjustment;

        //holds the total payment.
        private double totalPayment;

        #endregion

        #region Methods

        /// <summary>
        /// Holds the details of Request id
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }
        
        /// <summary>
        /// Holds the details of list of Invoice Charges
        /// </summary>
        public List<InvoiceChargeDetails> InvoiceCharges
        {
            get 
            {
                if (invoiceCharges == null)
                {
                    return new List<InvoiceChargeDetails>();
                }
                return invoiceCharges; 
            }
            set { invoiceCharges = value; }
        }

        /// <summary>
        /// Holds the details of previous request balance due
        /// </summary>
        public double PreviousRequestBalanceDue
        {
            get { return previousRequestBalanceDue; }
            set { previousRequestBalanceDue = value; }
        }

        /// <summary>
        /// Holds the details of current request balance due
        /// </summary>
        public double CurrentRequestBalanceDue
        {
            get { return currentRequestBalanceDue; }
            set { currentRequestBalanceDue = value; }
        }

        /// <summary>
        /// Holds the details of total credit adjustment
        /// </summary>
        public double TotalCreditAdjustment
        {
            get { return totalCreditAdjustment; }
            set { totalCreditAdjustment = value; }
        }

        /// <summary>
        /// Holds the details of total debit adjustment
        /// </summary>
        public double TotalDebitAdjustment
        {
            get { return totalDebitAdjustment; }
            set { totalDebitAdjustment = value; }
        }

         /// <summary>
        /// Holds the details of total payment amount.
        /// </summary>
        public double TotalPayment
        {
            get { return totalPayment; }
            set { totalPayment = value; }
        }

        #endregion
    }
}
