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

using McK.EIG.ROI.Client.Admin.Model;
namespace McK.EIG.ROI.Client.Request.Model
{

    /// <summary>
    /// Model contains document charge details
    /// </summary>
    [Serializable]
    public class DocumentChargeDetails 
    {
        # region Fields

        public const string DocumentChargeKey = "DocumentCharge";
        public const string CopiesKey = "copies";
        public const string PagesKey = "pages";
        public const string AmountKey = "amount";
        public const string IsElectronicKey = "is-electronic";
        public const string BillingTierIdKey = "billingtier-id";
        public const string BillingTierNameKey = "billingtier-name";
        public const string TotalPagesKey = "total-pages";
        public const string RemoveBaseChargeKey = "remove-basecharge";
        public const string ReleaseCountKey = "release-count";
        public const string DocChargeTaxAmountKey = "salestax-amount";
        public const string HasSalesTaxKey = "has-salestax";

        private int copies;

        private int pages;

        private double amount;

        private string billlingTier;

        private long billingTierId;

        private bool isElectronic;

        private long totalPages;

        private bool removeBaseCharge;

        private int releaseCount;

        private double taxAmount;

        private bool hasSalesTax;

        //Adding grid
        private BillingTierDetails billingTierDetail;
        
        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the copies count
        /// </summary>
        public int Copies
        {
            get { return copies; }
            set { copies = value; }
        }

        /// <summary>
        /// Gets or sets the pages count
        /// </summary>
        public int Pages
        {
            get { return pages; }
            set { pages = value; }
        }

        /// <summary>
        /// Gets or sets the amount.
        /// </summary>
        public double Amount
        {
            get { return amount; }
            set { amount = value; }
        }

        /// <summary>
        /// Gets or sets the sort billing tier name.
        /// </summary>
        public string BillingTier
        {
            get { return billlingTier; }
            set { billlingTier = value; }
        }

        /// <summary>
        /// Gets or sets the sort billing tier Id.
        /// </summary>
        public long BillingTierId
        {
            get { return billingTierId; }
            set { billingTierId = value; }
        }

        public bool IsElectronic
        {
            get { return isElectronic; }
            set { isElectronic = value; }
        }

        public long TotalPages
        {
            get { return totalPages; }
            set { totalPages = value; }
        }

        
        public bool RemoveBaseCharge
        {
            get { return removeBaseCharge; }
            set { removeBaseCharge = value; }
        }

        public int ReleaseCount
        {
            get { return releaseCount; }
            set { releaseCount = value; }
        }

        public double TaxAmount
        {
            get { return taxAmount; }
            set { taxAmount = value; }
        }

        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        //Property holds the billingtier details
        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        public BillingTierDetails BillingTierDetail
        {
            get
            {
                if (billingTierDetail == null)
                {
                    billingTierDetail = new BillingTierDetails();
                }
                return billingTierDetail;
            }
            set
            {
                billingTierDetail = value;
            }
        }

        #endregion
    }
}
