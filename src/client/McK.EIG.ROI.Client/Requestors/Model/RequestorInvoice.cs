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
using System.Collections.ObjectModel;
using System.Globalization;


namespace McK.EIG.ROI.Client.Requestors.Model
{


    /// <summary>
    /// This class is used to hold RequestorInvoice Info.
    /// </summary>
    [Serializable]
    public class RequestorInvoiceDetails 
    {

        #region Fields

        private long invoiceId;
        private long paymentId;
        private double applyAmount;
        private double baseCharge;
        private double balance;
        private double amountPaid;
        private double paymentTotal;
        private long requestId;
        private string facility;
        private double currentAppliedAmount;
        private bool isPrebillPayment;

        #endregion

        #region Constructor

        public RequestorInvoiceDetails()
        {

        }

        #endregion        

        #region Properties

        public long InvoiceId
        {
            get { return invoiceId; }
            set { invoiceId = value; }
        }

        public long PaymentId
        {
            get { return paymentId; }
            set { paymentId = value; }
        }


        public double ApplyAmount
        {
            get { return applyAmount; }
            set { applyAmount = value; }
        }

        public double BaseCharge
        {
            get { return baseCharge; }
            set { baseCharge = value; }
        }

        public double Balance
        {
            get { return balance; }
            set { balance = value; }
        }

        public double AmountPaid
        {
            get { return amountPaid; }
            set { amountPaid = value; }
        }

        public double PaymentTotal
        {
            get { return paymentTotal; }
            set { paymentTotal = value; }
        }

        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }   
        }

        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        public double CurrentAppliedAmount
        {
            get { return currentAppliedAmount; }
            set { currentAppliedAmount = value; }
        }

        public bool IsPrebillPayment
        {
            get { return isPrebillPayment; }
            set { isPrebillPayment = value; }
        }

        #endregion
    }
}
