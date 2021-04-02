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
using System.Collections.ObjectModel;
using System.Collections.Generic;
using System.Globalization;
using System.Text;
using System.Xml;
using System.Xml.XPath;

using McK.EIG.ROI.Client.Base.Model;

namespace McK.EIG.ROI.Client.Request.Model
{

    /// <summary>
    /// Model contains the release details of the request
    /// </summary>
    [Serializable]
    public class ReleaseDetails : ROIModel 
    {
        # region Fields            

        //CR#359276 - Add automatic adjustment transaction for the current invoice
        public const string CreditAdjustmentTotalKey   = "credit-adjustment-total";

        private long id;

        private long requestId;

        private Nullable<DateTime> releaseDate;

        private string billingType;

        private DateTime shippingDate;

        private Collection<DocumentChargeDetails> documentCharges;

        private Collection<FeeChargeDetails> feeCharges;

        private ShippingInfo shippingInfo;

        private Collection<RequestTransaction> requestTransaction;

        private string details;

        private SortedList<string, ReleasedPatientDetails> releasedPatients;

        private bool isReleased;

        private int totalPages;

        private int totalPagesReleased;

        private double balanceDue;

        private double releaseCost;

        private double documentChargeTotal;

        private double feeChargeTotal;

        private double adjustmentTotal;

        private double creditAdjustmentTotal;

        private double debitAdjustmentTotal;

        private double paymentTotal;

        private double adjustmentPaymentTotal;

        private double previouslyReleasedCost;
        
        private double totalRequestCost;      

        private string queueSecure;

        private string requestSecure;

        private bool nonPrintableQueueSelected;

        private double salesTaxTotalAmount;

        private double feeChargeTaxTotal;

        private double customChargeTaxTotal;

        private double docChargeTaxTotal;

        private Collection<SalesTaxReasons> taxReasons;

        private List<long> roiPagesSeqField;

        private List<long> supplementarityAttachmentsSeqField;

        private List<long> supplementarityDocumentsSeqField;

        private List<long> supplementalAttachmentsSeqField;

        private List<long> supplementalDocumentsSeqField;

        private bool isInvoiced;

        private double invoicesBalanceDue;

        private double invoicesSalesTaxAmount;

        private bool unbillable = UserData.Instance.IsChecked;

		private double unAppliedTotal;

        private List<ROIPage> roiPages;

        #endregion

        #region Methods
        
        /// <summary>
        /// Formatted the amount
        /// </summary>
        /// <param name="amount"></param>
        /// <returns></returns>
        public static string FormattedAmount(double amount)
        {
            return amount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
        }

        #endregion

        #region Properties

        /// <summary>
        /// Get or sets the id of the release
        /// </summary>
        public override long Id
        {
            get { return id; }
            set { id = value; }
        }

        /// <summary>
        /// Gets or sets the request id.
        /// </summary>
        public long RequestId
        {
            get { return requestId; }
            set { requestId = value; }
        }

        /// <summary>
        /// Gets or sets the release date.
        /// </summary>
        public Nullable<DateTime> ReleaseDate
        {
            get { return releaseDate; }
            set { releaseDate = value; }
        }       

        /// <summary>
        /// Checks the whether the release is draft or not
        /// </summary>
        public bool IsReleased
        {
            get { return isReleased; }
            set { isReleased = value; }
        }

        /// <summary>
        /// Gets or sets the billing type
        /// </summary>
        public string BillingType
        {
            get { return billingType; }
            set { billingType = value; }
        }

        /// <summary>
        /// Gets or sets the shipping date
        /// </summary>
        public DateTime ShippingDate
        {
            get { return shippingDate; }
            set { shippingDate = value; }
        }

        /// <summary>
        /// Property holds the document charge collection
        /// </summary>
        public Collection<DocumentChargeDetails> DocumentCharges
        {
            get
            {
                if (documentCharges == null)
                {
                    documentCharges = new Collection<DocumentChargeDetails>();
                }

                return documentCharges;
            }
        }

        /// <summary>
        /// Property holds the fee charge collection
        /// </summary>
        public Collection<FeeChargeDetails> FeeCharges
        {
            get
            {
                if (feeCharges == null)
                {
                    feeCharges = new Collection<FeeChargeDetails>();
                }

                return feeCharges;
            }
        }

        /// <summary>
        /// Gets or sets the shipping info
        /// </summary>
        public ShippingInfo ShippingDetails
        {
            get { return shippingInfo; }
            set { shippingInfo = value; }
        }

        /// <summary>
        ///  Property holds the request transactions
        /// </summary>
        public Collection<RequestTransaction> RequestTransactions
        {
            get
            {
                if (requestTransaction == null)
                {
                    requestTransaction = new Collection<RequestTransaction>();
                }

                return requestTransaction;
            }
        }

        /// <summary>
        /// Holds total released pages
        /// </summary>
        public int TotalPages
        {
            get { return totalPages; }
            set { totalPages = value; }
        }

        /// <summary>
        /// Holds total released pages of the request
        /// </summary>
        public int TotalPagesReleased
        {
            get { return totalPagesReleased; }
            set { totalPagesReleased = value; }
        }
       
        /// <summary>
        /// Balance Due
        /// </summary>
        public double BalanceDue
        {
            get { return balanceDue; }
            set { balanceDue = value; }
        }

        /// <summary>
        /// Pending release cost
        /// </summary>
        public double ReleaseCost
        {
            get { return releaseCost; }
            set { releaseCost = value; }
        }

        /// <summary>
        /// Holds details of the release object
        /// </summary>
        public string Details
        {
            get { return details; }
            set { details = value; }
        }

        /// <summary>
        /// Holds fee charge total amount
        /// </summary>
        public double FeeChargeTotal
        {
            get { return feeChargeTotal; }
            set { feeChargeTotal = value; }
        }

        public double FeeChargeTaxTotal
        {
            get { return feeChargeTaxTotal; }
            set { feeChargeTaxTotal = value; }
        }

        public double CustomChargeTaxTotal
        {
            get { return customChargeTaxTotal; }
            set { customChargeTaxTotal = value; }
        }

        public double DocChargeTaxTotal
        {
            get { return docChargeTaxTotal; }
            set { docChargeTaxTotal = value; }
        }

        /// <summary>
        /// Holds the adjustment total
        /// </summary>
        public double AdjustmentTotal
        {
            get { return adjustmentTotal; }
            set { adjustmentTotal = value; }
        }

		//CR#359276 - Add automatic adjustment transaction for the current invoice
        /// <summary>
        /// Holds the Credit adjustment total
        /// </summary>
        public double CreditAdjustmentTotal
        {
            get { return creditAdjustmentTotal; }
            set { creditAdjustmentTotal = value; }
        }

        /// <summary>
        /// CR#367826 - Holds the debit adjustment total
        /// </summary>
        public double DebitAdjustmentTotal
        {
            get { return debitAdjustmentTotal; }
            set { debitAdjustmentTotal = value; }
        }

        /// <summary>
        /// Holds the payment total
        /// </summary>
        public double PaymentTotal
        {
            get { return paymentTotal; }
            set { paymentTotal = value; }
        }

        public double DocumentChargeTotal
        {
            get { return documentChargeTotal; }
            set { documentChargeTotal = value; }
        }

        public double PreviouslyReleasedCost
        {
            get { return previouslyReleasedCost; }
            set { previouslyReleasedCost = value; }
        }

        public double AdjustmentPaymentTotal
        {
            get { return adjustmentPaymentTotal; }
            set { adjustmentPaymentTotal = value; }
        }

        /// <summary>
        /// Gets or sets the total request cost
        /// </summary>
        public double TotalRequestCost
        {
            get { return totalRequestCost; }
            set { totalRequestCost = value; }
        }

        /// <summary>
        /// This property is used to get or sets the patient object model.
        /// </summary>
        public SortedList<string, ReleasedPatientDetails> ReleasedPatients
        {
            get
            {
                if (releasedPatients == null)
                {
                    releasedPatients = new SortedList<string, ReleasedPatientDetails>();
                }
                return releasedPatients;
            }
        }

        /// <summary>
        /// Holds details of the queue password
        /// </summary>
        public string QueueSecretWord
        {
            get { return queueSecure; }
            set { queueSecure = value; }
        }

        /// <summary>
        /// Holds details of the request password
        /// </summary>
        public string RequestSecretWord
        {
            get { return requestSecure; }
            set { requestSecure = value; }
        }

        /// <summary>
        /// Holds details of the Non Printable queue selected or not
        /// </summary>
        public bool NonPrintableQueueSelected
        {
            get { return nonPrintableQueueSelected; }
            set { nonPrintableQueueSelected = value; }
        }

        public double SalesTaxTotalAmount
        {
            get { return salesTaxTotalAmount; }
            set { salesTaxTotalAmount = value; }
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


        public Collection<SalesTaxReasons> TaxReasons
        {
            get 
            {
                if (taxReasons == null)
                {
                    return taxReasons = new Collection<SalesTaxReasons>();
                }
                return taxReasons;
            }
        }

        /// <summary>
        /// Gets or sets the roiPagesSeqField list.
        /// </summary>
        public List<long> RoiPagesSeqField
        {
            get
            {
                if (roiPagesSeqField == null)
                {
                    roiPagesSeqField = new List<long>();
                }
                return roiPagesSeqField;
            }
        }

        /// <summary>
        /// Gets or sets the supplementarityAttachmentsSeqField list.
        /// </summary>
        public List<long> SupplementarityAttachmentsSeqField
        {
            get
            {
                if (supplementarityAttachmentsSeqField == null)
                {
                    supplementarityAttachmentsSeqField = new List<long>();
                }
                return supplementarityAttachmentsSeqField;
            }
        }

        /// <summary>
        /// Gets or sets the supplementarityDocumentsSeqField list.
        /// </summary>
        public List<long> SupplementarityDocumentsSeqField
        {
            get
            {
                if (supplementarityDocumentsSeqField == null)
                {
                    supplementarityDocumentsSeqField = new List<long>();
                }
                return supplementarityDocumentsSeqField;
            }
        }

        /// <summary>
        /// Gets or sets the SupplementalAttachmentsSeqField list.
        /// </summary>
        public List<long> SupplementalAttachmentsSeqField
        {
            get
            {
                if (supplementalAttachmentsSeqField == null)
                {
                    supplementalAttachmentsSeqField = new List<long>();
                }
                return supplementalAttachmentsSeqField;
            }
        }


        /// <summary>
        /// Gets or sets the SupplementalAttachmentsSeqField list.
        /// </summary>
        public List<long> SupplementalDocumentsSeqField
        {
            get
            {
                if (supplementalDocumentsSeqField == null)
                {
                    supplementalDocumentsSeqField = new List<long>();
                }
                return supplementalDocumentsSeqField;
            }
        }

        public double UnAppliedTotal
        {
            get { return unAppliedTotal; }
            set { unAppliedTotal = value; }
        }

        /// <summary>
        /// Get the ROI Page details
        /// </summary>
        public List<ROIPage> ROIPages
        {
            get
            {
                if (roiPages == null)
                {
                    roiPages = new List<ROIPage>();
                }
                return roiPages;
            }
        }

        #endregion
    }
}
