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

namespace McK.EIG.ROI.Client.Request.Model
{
    /// <summary>
    /// Model contains past invoice details
    /// </summary>
    [Serializable]
    public class PastInvoiceDetails
    {
        #region Fields

        private bool invoiceSelected;
        private long invoiceId;
        private DateTime? createdDate;
        private double amount;

        #endregion

        #region Methods

        /// <summary>
        /// Gets or sets the invoice selected
        /// </summary>
        public bool InvoiceSelected
        {
            get { return invoiceSelected; }
            set { invoiceSelected = value; }
        }

        /// <summary>
        /// Gets or sets invoice Id.
        /// </summary>
        public long InvoiceId
        {
            get { return invoiceId; }
            set { invoiceId = value; }
        }

        /// <summary>
        /// Gets or sets created date.
        /// </summary>
        public DateTime? CreatedDate
        {
            get { return createdDate; }
            set { createdDate = value; }
        }

        /// <summary>
        /// Gets or sets invoice amount.
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        #endregion

    }
}
