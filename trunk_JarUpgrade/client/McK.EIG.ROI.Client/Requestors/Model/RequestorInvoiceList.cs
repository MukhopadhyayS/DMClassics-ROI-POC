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
using System.Linq;
using System.Text;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    /// <summary>
    /// This class is used to hold Requestor Invoices
    /// </summary>
    [Serializable]
    public class RequestorInvoiceList
    {
        #region Fields

        private long requestorId;

        private long paymentId;

        private double paymentAmount;

        private double unAppliedAmount;

        private string paymentMode;

        private string description;

        private Nullable<DateTime> paymentDate;

        private List<RequestorInvoiceDetails> requestorInvoices;

        private string requestorName;

        private string requestorType;

        #endregion

        #region Methods

        public long RequestorId
        {
            get { return requestorId; }
            set { requestorId = value; }
        }

        public long PaymentId
        {
            get { return paymentId; }
            set { paymentId = value; }
        }

        public double PaymentAmount
        {
            get { return paymentAmount; }
            set { paymentAmount = value; }
        }

        public double UnAppliedAmount
        {
            get { return unAppliedAmount; }
            set { unAppliedAmount = value; }
        }

        public string PaymentMode
        {
            get { return paymentMode; }
            set { paymentMode = value; }
        }
        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public Nullable<DateTime> PaymentDate
        {
            get { return paymentDate; }
            set { paymentDate = value; }
        }

        public List<RequestorInvoiceDetails> RequestorInvoices
        {
            get
            {
                if (requestorInvoices == null)
                {
                    requestorInvoices = new List<RequestorInvoiceDetails>();
                }
                return requestorInvoices;
            }
        }

        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        public string RequestorType
        {
            get { return requestorType; }
            set { requestorType = value; }
        }

        #endregion
    }
    
}
