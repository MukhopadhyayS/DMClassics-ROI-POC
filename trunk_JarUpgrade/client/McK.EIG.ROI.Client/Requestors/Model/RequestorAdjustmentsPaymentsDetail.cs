using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using McK.EIG.ROI.Client.Base.Model;
 
namespace McK.EIG.ROI.Client.Requestors.Model 
{
    public class RequestorAdjustmentsPaymentsDetail : ROIModel
    {
        #region Fields
        private long paymentId;
        private string description;
        private string paymentMethod;
        private string date;
        //CR#377295
        private string moddate;
        private double amount;
        private double unAppliedAmt;
        private String txnType;
        private long invoiceId;
        private double appliedAmount;
        private double refundAmount;
        #endregion Fields

        #region Properties

        public long PaymentId
        {
            get { return paymentId; }
            set { paymentId = value; }
        }

        public string Date
        {
            get{return date;}
            set { date = value; }
        }
        public string Description
        {
            get { return description; }
            set { description = value; }
        }
        public string PaymentMethod
        {
            get { return paymentMethod; }
            set { paymentMethod = value; }
        }
        public double Amount
        {
            get{return amount;}
            set { amount = value; }
        }
        public double UnAppliedAmt
        {
            get { return unAppliedAmt; }
            set { unAppliedAmt = value; }
        }
        public String TxnType
        {
            get { return txnType; }
            set { txnType = value; }
        }
        public override long Id
        {
            get{return invoiceId;}
            set { invoiceId = value; }
        }

        //CR#377295
        public string ModifiedDate
        {
            get { return moddate; }
            set { moddate = value; }
        }

        public double AppliedAmount
        {
            get { return appliedAmount; }
            set { appliedAmount = value; }
        }

        public double RefundAmount
        {
            get { return refundAmount; }
            set { refundAmount = value; }
        }
        #endregion Properties
    }
}
