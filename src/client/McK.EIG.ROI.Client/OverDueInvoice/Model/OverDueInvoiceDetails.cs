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
using McK.EIG.ROI.Client.Base.View;

namespace McK.EIG.ROI.Client.OverDueInvoice.Model
{   
    /// <summary>
    /// PastDueInvoiceDetails
    /// </summary>
    [Serializable]
    public class OverDueInvoiceDetails : ROIModel
    {
        #region Fields
       
        private long id;
        private bool invoiceSelected;
        private long invoiceNumber;
        private long requestId;
        private long overDueDays;
        private long requestorId;
        private long invoiceAge;

        private string billingLocation;
        private double overDueAmount;
        private string requestorType;
        private string requestorName;
        private string phoneNumber;

        #endregion      

        #region Methods

        /// <summary>
        /// This method will normalize all fields.
        /// </summary>
        public void Normalize()
        {
            billingLocation  = string.IsNullOrEmpty(billingLocation) ? string.Empty : billingLocation.Trim();
            overDueAmount = double.IsNaN(overDueAmount) ? 0 : overDueAmount;
            requestorName = string.IsNullOrEmpty(requestorName) ? string.Empty : requestorName.Trim();
            phoneNumber = string.IsNullOrEmpty(phoneNumber) ? string.Empty : phoneNumber.Trim();
        }
  
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets Id.
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Get or sets the Overdue invoice grid's checkbox column
        /// </summary>
        public bool InvoiceSelected
        {
            get { return invoiceSelected; }
            set { invoiceSelected = value; }
        }
        /// <summary>
        /// Gets or sets BillingLoaction.
        /// </summary>
        public string BillingLocation
        {
            get { return billingLocation; }
            set { billingLocation = value; }
        }

        /// <summary>
        /// Gets or sets invoice Id.
        /// </summary>
        public long InvoiceNumber
        {
            get { return invoiceNumber; }
            set { invoiceNumber = value; }
        }

        /// <summary>
        /// Gets or sets request Id.
        /// </summary>
        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        /// <summary>
        /// Gets or sets invoice overdue amount.
        /// </summary>
        public double OverDueAmount
        {
            get { return overDueAmount; }
            set { overDueAmount = value; }
        }

        /// <summary>
        /// Gets or sets invoice overdue days.
        /// </summary>
        public long OverDueDays
        {
            get { return overDueDays; }
            set { overDueDays = value; }
        }

        /// <summary>
        /// Gets or sets requestor Id.
        /// </summary>
        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        /// <summary>
        /// Gets or sets requestor type.
        /// </summary>
        public string RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        /// <summary>
        /// Gets or sets requestor name.
        /// </summary>
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }  

        /// <summary>
        /// Gets or sets requestor phone number.
        /// </summary>
        public string PhoneNumber
        {
            get { return phoneNumber; }
            set { phoneNumber = value; }
        }

        /// <summary>
        /// Gets or sets invoiceAge.
        /// </summary>
        public long InvoiceAge
        {
            get { return invoiceAge; }
            set { invoiceAge = value; }
        }

        

        #endregion
    }
}
