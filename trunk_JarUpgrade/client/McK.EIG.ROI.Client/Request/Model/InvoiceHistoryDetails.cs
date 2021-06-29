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

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains the Invoice history details of the request
    /// </summary>
    [Serializable]
    public class InvoiceHistoryDetails : ROIModel
    {
        #region Fields

        //holds the Invoice Id
        private long id;

        //holds the Invoice type
        private string invoiceType;

        //holds the Invoice created date
        private DateTime? invoiceCreatedDate;

        //holds Invoice resend date.
        private DateTime? resendDate;

        //holds Invoice output method
        private string outputMethod;

        //holds Invoice due date
        private DateTime? invoiceDueDate;

        //holds Invoice base charge
        private double baseCharge;

        //holds Invoice total adjustments
        private double totalAdjustments;

        //holds Invoice total payments
        private double totalPayments;

        //holds Invoice current balance
        private double currentBalance;

        #endregion

        #region Methods

        /// <summary>
        /// Holds the details of invoice id
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Holds the details of invoice creator type
        /// </summary>
        public string InvoiceType
        {
            get { return invoiceType; }
            set { invoiceType = value; }
        }

        /// <summary>
        /// Holds the details of invoice created date
        /// </summary>
        public DateTime? InvoiceCreatedDate
        {
            get { return invoiceCreatedDate; }
            set { invoiceCreatedDate = value; }
        }

        /// <summary>
        /// Holds the details of resend date
        /// </summary>
        public DateTime? ResendDate
        {
            get { return resendDate; }
            set { resendDate = value; }
        }

        /// <summary>
        /// Holds details of the request password
        /// </summary>
        public string OutputMethod
        {
            get { return outputMethod; }
            set { outputMethod = value; }
        }

        /// <summary>
        /// Holds details of the invoice due date
        /// </summary>
        public DateTime? InvoiceDueDate
        {
            get { return invoiceDueDate; }
            set { invoiceDueDate = value; }
        }

        /// <summary>
        /// Holds the details of base charge
        /// </summary>
        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }
        }

        /// <summary>
        /// Holds the details of total adjustments
        /// </summary>
        public double TotalAdjustments
        {
            get { return totalAdjustments; }
            set { totalAdjustments = value; }
        }

        /// <summary>
        /// Holds the details of total payments
        /// </summary>
        public double TotalPayments
        {
            get { return totalPayments; }
            set { totalPayments = value; }
        }

        /// <summary>
        /// Holds the details of current balance
        /// </summary>
        public double CurrentBalance
        {
            get { return currentBalance; }
            set { currentBalance = value; }
        }
        #endregion

    }
}
