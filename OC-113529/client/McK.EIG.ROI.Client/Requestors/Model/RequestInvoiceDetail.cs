using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Requestors.Model
{
    [Serializable]
    public class RequestInvoiceDetail : ROIModel
    {
        #region Fields
        private long invoiceId;
        private long id;
        private DateTime date;
        //CR#377295
        private string moddate;
        private long requestId;
        private String invoiceType;
        private String description;
        private double charge;
        private double adjustmentAmount;
        private double paymentAmount;
        private double balance;
        private double tempbalance;
        private double tempAdjustment;
        private String invoiceStatus;
        private double unBillableAmount;
        private List<RequestorAdjustmentsPaymentsDetail> reqAdjPay;
        private double applyAmount; //Used for save the apply payment against the invoices.
        private double appliedAmount;
        private double payAdjTotal;
        private double appliedAmountTotal;
        private double appliedAmountCopy;
        private string paymentDescription;
        private string paymentMethod;
        private double payAmount;
        private double adjAmount;
		private double applyamt;
        private double adjPay;
		private double origtotaladjPay;
        private bool hasMaskedRequestorFacility;
        private bool hasBlockedRequestorFacility;
        private Image billableIcon;
        private String unbillable;
        private double refundAmount;
		private string facility;
        
        #endregion Fields

        #region Properties

        public double OriginalTotalAdjPay
        {
            get {return origtotaladjPay; }
            set { origtotaladjPay = value; }
        }


        public double ApplyAmt
        {
            get { return applyamt; }
            set { applyamt = value; }
        }
        public double AdjPay
        {
            get { return -(Adjustments + Payments); }
            set { adjPay = value; }
        }

        public override long Id
        {
            get { return invoiceId; }
            set { invoiceId = value; }
        }

   
        public DateTime CreatedDate
        {
            get { return date; }
            set { date = value; }
        }

        //CR#377295
        public string ModifiedDate
        {
            get { return moddate; }
            set { moddate = value; }
        }

        public double OriginalAdjustment
        {
            get { return tempAdjustment; }
            set { tempAdjustment = value; }
        }

        public long RequestId
        {
            get{return requestId;}
            set{requestId =value;}
        }
        
            
        public String InvoiceType 
        {
            get{return invoiceType;}
            set{invoiceType=value;}
        }

        public String Description
        {
            get{return description;}
            set{description =value;}
        }


        public double Charges
        {
            get { return charge; }
            set { charge = value; }
        }

        public double Adjustments 
        {
            get{return adjustmentAmount;}
            set{adjustmentAmount=value;}
        }


        public double Payments
        {
            get{ return paymentAmount;}
            set{paymentAmount=value;}
        }
        
        public double PayAmount
        {
            get { return payAmount; }
            set { payAmount = value; }
        }

        public double AdjAmount
        {
            get { return adjAmount; }
            set { adjAmount = value; }
        }

        public double Balance
        {
            get{return balance;}
            set{balance=value;}
        }

        public double OrignalBalance
        {
            get { return tempbalance; }
            set { tempbalance = value; }
        }

        public String InvoiceStatus
        {
            get{return invoiceStatus;}
            set{invoiceStatus=value;}
        }

        public double UnBillableAmount
        {
            get { return unBillableAmount; }
            set { unBillableAmount = value; }
        }

        public List<RequestorAdjustmentsPaymentsDetail> ReqAdjPay
        {
            get
            {
                if (reqAdjPay == null)
                {
                    return new List<RequestorAdjustmentsPaymentsDetail>();
                }
                return reqAdjPay;
            }
            set { reqAdjPay = value; }
        }

        public double ApplyAmount
        {
            get { return applyAmount; }
            set { applyAmount = value; }
        }
        
        public double AppliedAmount
        {
            get { return appliedAmount; }
            set { appliedAmount = value; }
        }

        public double AppliedAmountCopy
        {
            get { return appliedAmountCopy; }
            set { appliedAmountCopy = value; }
        }

        public double PayAdjTotal
        {
            get { return payAdjTotal; }
            set { payAdjTotal = value; }
        }


        public double AppliedAmountTotal
        {
            get { return appliedAmountTotal; }
            set { appliedAmountTotal = value; }
        }

        public string PaymentDescription
        {
            get { return paymentDescription; }
            set { paymentDescription = value; }
        }
        public string PaymentMethod
        {
            get { return paymentMethod; }
            set { paymentMethod = value; }
        }

        public bool HasMaskedRequestorFacility
        {
            get { return hasMaskedRequestorFacility; }
            set { hasMaskedRequestorFacility = value; }
        }

        public bool HasBlockedRequestorFacility
        {
            get { return hasBlockedRequestorFacility; }
            set { hasBlockedRequestorFacility = value; }
        }

        public String Unbillable
        {
            get { return unbillable; }
            set { unbillable = value; }
        }

        public Image BillableIcon
        {
            get { return billableIcon; }
            set { billableIcon = value; }
        }

        public double RefundAmount
        {
            get { return refundAmount; }
            set { refundAmount = value; }
        }

        public string Facility
        {
            get { return facility; }
            set { facility = value; }
        }

        #endregion Properties
    }
    
}
