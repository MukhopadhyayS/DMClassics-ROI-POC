using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;

using McK.EIG.ROI.Client.Base.Model;
namespace McK.EIG.ROI.Client.Request.Model
{
    public class RequestCoreChargeDetails
    {
        #region Fields

        private long requestId;
        private string billingLoc;
        private double previouslyReleasedCost;
        private Nullable<DateTime> releaseDate;
        private double releasedCost;
        private int totalPages;
        private double totalRequestCost;
        private double balanceDue;
        private int totalPagesReleased;
        private double salestaxTotalAmount;
        private double salestaxPercentageForBillingLoc;
        private string billingType;
        private BillingCharge billingCharge;
        private ShippingInfo shippingInfo; 
        private SalesTaxChargeDetails salesTax;
        private List<SalesTaxReasons> salesTaxReasonsList;

        private long billingTypeIdForFeeCharge;
        private double originalBalance;
        private double paymentAmount;
        private double creditAdjustmentAmount;
        private double debitAdjustmentAmount;
        private string billingLocCode;
        private string billingLocName;
        private bool applySalesTax;
        private bool isReleased;
        private double invoiceBaseCharge;
        private double invoiceAutoAdjustment;
        private bool isInvoiced;
        private double invoicesBalanceDue;
        private double invoicesSalesTaxAmount;
        private bool unbillable;

        private double unAppliedAmount;
        public long BillingTypeIdForFeeCharge
        {
            get { return billingTypeIdForFeeCharge; }
            set { billingTypeIdForFeeCharge = value; }
        }
        public double OriginalBalance
        { get { return originalBalance; }
            set { originalBalance = value; }
        }
        public double PaymentAmount
        { get { return paymentAmount; }
            set { paymentAmount = value; }
        }
        public double CreditAdjustmentAmount
        {
            get { return creditAdjustmentAmount; }
            set { creditAdjustmentAmount = value; }
        }
        public double DebitAdjustmentAmount
        {
            get { return debitAdjustmentAmount; }
            set { debitAdjustmentAmount = value; }
        }
        public string BillingLocCode
        {
            get { return billingLocCode; }
            set { billingLocCode = value; }
        }
        public string BillingLocName
        {
            get { return billingLocName; }
            set { billingLocName = value; }
        }
        public bool ApplySalesTax
        {
            get { return applySalesTax; }
            set { applySalesTax = value; }
        }
        public bool IsReleased
        {
            get { return isReleased; }
            set { isReleased = value; }
        }
        public double InvoiceAutoAdjustment
        {
            get { return invoiceAutoAdjustment; }
            set { invoiceAutoAdjustment = value; }
        }
        public double InvoiceBaseCharge
        {
            get { return invoiceBaseCharge; }
            set { invoiceBaseCharge = value; }
        }

        public double UnAppliedAmount
        {
            get { return unAppliedAmount; }
            set { unAppliedAmount = value; }
        }

        #endregion

        #region Constructor

        public RequestCoreChargeDetails() { }

        #endregion


        #region Properties

        public long RequestId
        {
            get { return requestId; }
            set { requestId=value; }
        }
        public string BillingLoc
        {
            get { return billingLoc; }
            set { billingLoc = value; }
        }

        public BillingCharge BillingCharge
        {
            get
            {
                if (billingCharge == null)
                {
                    billingCharge = new BillingCharge();
                }
                return billingCharge;
            }
            set { billingCharge = value; }
        }

        public ShippingInfo ShippingInfo
        {
            get
            {
                if (shippingInfo == null)
                {
                    shippingInfo = new ShippingInfo();
                }
                return shippingInfo;
            }
            set { shippingInfo = value; }
        }


        public SalesTaxChargeDetails SalesTax
        {
            get
            {
                if (salesTax == null)
                {
                    salesTax = new SalesTaxChargeDetails();
                }
                return salesTax;
            }
            set { salesTax = value; }
        }
        public double PreviouslyReleasedCost
        {
            get { return previouslyReleasedCost; }
            set { previouslyReleasedCost = value; }
        }
        public Nullable<DateTime> ReleaseDate
        {
            get { return releaseDate; }
            set { releaseDate = value; }
        }

        public double ReleasedCost
        {
            get { return releasedCost; }
            set { releasedCost = value; }
        }
        public int TotalPages
        {
            get { return totalPages; }
            set { totalPages = value; }
        }
        public double TotalRequestCost
        {
            get { return totalRequestCost; }
            set { totalRequestCost = value; }
        }
        public double BalanceDue
        {
            get { return balanceDue; }
            set { balanceDue = value; }
        }
        public int TotalPagesReleased
        {
            get { return totalPagesReleased; }
            set { totalPagesReleased = value; }
        }
        public double SalestaxTotalAmount
        {
            get { return salestaxTotalAmount; }
            set { salestaxTotalAmount = value; }
        }
        public double SalestaxPercentageForBillingLoc
        {
            get { return salestaxPercentageForBillingLoc; }
            set { salestaxPercentageForBillingLoc = value; }
        }
        public string BillingType
        {
            get { return billingType; }
            set { billingType = value; }
        }
        public bool IsInvoiced
        {
            get { return isInvoiced; }
            set { isInvoiced = value; }
        }
        public bool IsUnbillable
        {
            get { return unbillable; }
            set { unbillable = value; }
        }
        public double InvoicesBalanceDue
        {
            get { return invoicesBalanceDue; }
            set { invoicesBalanceDue = value; }
        }

        public double InvoicesSalesTaxAmount
        {
            get { return invoicesSalesTaxAmount; }
            set { invoicesSalesTaxAmount = value; }
        }

        public List<SalesTaxReasons> SalesTaxReasonsList
        {
            get
            {
                if (salesTaxReasonsList == null)
                {
                    salesTaxReasonsList = new List<SalesTaxReasons>();
                }
                return salesTaxReasonsList;
            }
            set
            {
                salesTaxReasonsList = value;
            }
        }
        #endregion
    }
}
