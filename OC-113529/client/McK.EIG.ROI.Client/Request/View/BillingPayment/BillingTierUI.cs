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
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class which handles the document charge(single)
    /// </summary>
    public partial class BillingTierUI : ROIBaseUI
    {
        #region Fields

        private EventHandler updateTierAmountHandler;
        private EventHandler updateBillingTierHandler;

        private BillingTierDetails billingTier;

        private DocumentChargeDetails documentCharge;

        private double taxAmount;

        private double facilityTaxPercentage;

        private bool hasSalesTax;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public BillingTierUI()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Initialize the billing tier
        /// </summary>
        /// <param name="pane"></param>
        /// <param name="billingTier"></param>
        /// <param name="deleteBillingTierHandler"></param>
        /// <param name="updateTierAmountHandler"></param>
        /// <param name="isElectronic"></param>
        public BillingTierUI(IPane pane, BillingTierDetails billingTier, 
                             EventHandler updateTierAmountHandler): this()
        {
            SetPane(pane);
            SetExecutionContext(Pane.Context);

            //Added for sales tax integration
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            facilityTaxPercentage = billingEditor.Request.TaxPercentage;
            
            this.billingTier = billingTier;

            this.updateTierAmountHandler  = updateTierAmountHandler;

            updateBillingTierHandler = new EventHandler(Proces_UpdateBillingTierAmount);
        }        

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {   
        }

        /// <summary>
        /// Gets the localized key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            copiesTextBox.TextChanged += updateBillingTierHandler;
            pagesTextBox.TextChanged += updateBillingTierHandler;
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        private void DisableEvents()
        {
            copiesTextBox.TextChanged -= updateBillingTierHandler;
            pagesTextBox.TextChanged -= updateBillingTierHandler;
        }

        /// <summary>
        /// Updates documentcharge amount based on pages and copies entered by user.
        /// Removed BaseCharge for request Subsequent release or rerelase.
        /// </summary>
        /// <param name="pages"></param>
        /// <param name="copies"></param>
        public void UpdateBillingTierAmount(int pages, int copies)
        {
            if (pages == 0)
            {
                Amount = 0.00;                
                return;
            }

            long totalpages = pages * copies;
            
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            long totalPagesForBillingTier = (!billingEditor.Request.IsReleased) ? totalpages 
                                                                           : documentCharge.TotalPages + totalpages;
            
            if (billingEditor.Release.IsReleased)
            {
                totalPagesForBillingTier = documentCharge.TotalPages;
            }

            long pageRange = (totalPagesForBillingTier - totalpages + 1);
             
            double price = (documentCharge.RemoveBaseCharge) ? 0 : billingTier.BaseCharge;
            int pageTierCount = billingTier.PageTiers.Count;
            if (pageTierCount > 0)
            {
                foreach (PageLevelTierDetails pageTier in billingTier.PageTiers)
                {
                    if (!(pageRange >= pageTier.StartPage && pageRange <= pageTier.EndPage))
                    {
                        continue;
                    }

                    if (totalPagesForBillingTier > pageTier.EndPage)
                    {
                        price += (pageTier.PricePerPage * (pageTier.EndPage + 1 - pageRange));
                        pageRange = pageTier.EndPage + 1;
                    }
                    else
                    {
                        price += (pageTier.PricePerPage * (totalPagesForBillingTier + 1 - pageRange));
                    }

                    if (totalPagesForBillingTier >= pageTier.StartPage && totalPagesForBillingTier <= pageTier.EndPage)
                    {
                        break;
                    }
                }

                PageLevelTierDetails lastPageTier = billingTier.PageTiers[pageTierCount - 1];
                if (totalPagesForBillingTier > lastPageTier.EndPage)
                {
                    price += (billingTier.OtherPageCharge * (totalPagesForBillingTier + 1 - pageRange));
                }
            }

            Amount = price;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            pagesTextBox.Text = string.Empty;
            copiesTextBox.Text = Convert.ToString(1, CultureInfo.CurrentCulture);
            billingTierLabel.Text = string.Empty;

            double defaultAmont = 0.00;
            amountLabel.Text = ReleaseDetails.FormattedAmount(defaultAmont);            
        }

        /// <summary>
        /// Sets the document charge info
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            ClearData();
            
            if (data != null)
            {
                documentCharge = (DocumentChargeDetails)data;
                hasSalesTax = documentCharge.HasSalesTax;
                billingTierLabel.Text = documentCharge.BillingTier;
                toolTip.SetToolTip(billingTierLabel, billingTierLabel.Text);
                copiesTextBox.Text = documentCharge.Copies.ToString(ROIConstants.NumberFormat, CultureInfo.CurrentCulture);

                EnableEvents();
                pagesTextBox.Text = documentCharge.Pages.ToString(ROIConstants.NumberFormat, CultureInfo.CurrentCulture);
            }            
        }

        /// <summary>
        /// Gets the document charge info
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            DocumentChargeDetails documentCharge = (data == null)? new DocumentChargeDetails() : (DocumentChargeDetails)data ;
            
            documentCharge.Amount      = double.Parse(amountLabel.Text, NumberStyles.Currency, CultureInfo.CurrentCulture);
            documentCharge.BillingTier = billingTierLabel.Text;
            documentCharge.Copies =  int.Parse(copiesTextBox.Text, NumberStyles.Number, CultureInfo.CurrentCulture);
            documentCharge.Pages  =  int.Parse(pagesTextBox.Text, NumberStyles.Number, CultureInfo.CurrentCulture);
            documentCharge.BillingTierId = billingTier.Id;
            documentCharge.TaxAmount = Amount * facilityTaxPercentage / 100;
            
            documentCharge.TotalPages       = this.documentCharge.TotalPages;
            documentCharge.RemoveBaseCharge = this.documentCharge.RemoveBaseCharge;
            documentCharge.ReleaseCount     = this.documentCharge.ReleaseCount;
            documentCharge.HasSalesTax = HasSalesTax;
 
            return documentCharge;
        }

        /// <summary>
        /// Recalculate tax based on user select apply tax 
        /// </summary>
        public void CalculateTax(double taxPercentage)
        {
            facilityTaxPercentage = taxPercentage; // If user uncheck apply tax and change the copies
            taxAmount = Amount * taxPercentage / 100;
        }

        /// <summary>
        /// Updates the billing tier amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Proces_UpdateBillingTierAmount(object sender, EventArgs e)
        {
            try
            {
                int copies;
                int pages = 0;

                if (!int.TryParse(copiesTextBox.Text, NumberStyles.Number, CultureInfo.CurrentCulture, out copies) || copies <= 0)
                {  
                    documentCharge.Copies = 1;
                    copiesTextBox.Text = Convert.ToString(documentCharge.Copies, CultureInfo.CurrentCulture);                 
                    throw new ROIException(ROIErrorCodes.InvalidCopy);
                }
                
                documentCharge.Copies = copies;

                if (!int.TryParse(pagesTextBox.Text, NumberStyles.Number, CultureInfo.CurrentCulture, out pages) || pages <= 0)
                {
                    documentCharge.Pages = 1;
                    pagesTextBox.Text = Convert.ToString(documentCharge.Pages, CultureInfo.CurrentCulture);
                    throw new ROIException(ROIErrorCodes.InvalidPage);                   
                }
                            
                documentCharge.Pages = pages;                
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                UpdateBillingTierAmount(documentCharge.Pages, documentCharge.Copies);
                updateTierAmountHandler(this, e);
            }
        }

        #endregion

        #region  Properties

        /// <summary>
        /// Document Charge amount
        /// </summary>
        public double Amount
        {
            get { return documentCharge.Amount; }
            set 
            { 
                documentCharge.Amount = value;
                amountLabel.Text = documentCharge.Amount.ToString("C", CultureInfo.CurrentCulture);

                taxAmount = documentCharge.Amount * facilityTaxPercentage / 100;                
            }
        }

        /// <summary>
        /// Page count
        /// </summary>
        public int PageCount
        {
            get { return documentCharge.Pages; }
        }

        /// <summary>
        /// Copies
        /// </summary>
        public int Copies
        {
            get { return documentCharge.Copies;}
        }

        public double TaxAmount
        {
            get { return taxAmount; }
        }

        public bool HasSalesTax
        {
            get { return hasSalesTax; }
            set { hasSalesTax = value; }
        }

        #endregion       
    }
}
