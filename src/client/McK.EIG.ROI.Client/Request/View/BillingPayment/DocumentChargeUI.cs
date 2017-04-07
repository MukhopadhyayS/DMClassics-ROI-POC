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
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{

    /// <summary>
    /// Class which maintains Document Charges
    /// </summary>
    public partial class DocumentChargeUI : ROIBaseUI
    {
        #region Fields

        private double documentCharge;
        private int totalPages;
        
        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        private double facilityTaxPercentage;
        private const string CopiesColumn = "copies";
        private const string PagesColumn = "pages";
        private const string BillingTierColumn = "billingtier";
        private const string AmountColumn = "amount";

        private ComparableCollection<DocumentChargeDetails> gridDocumentCharges;
        private bool hasDocuments = false;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public DocumentChargeUI()
        {
            InitializeComponent();
            InitGrid();
            isEnabled = true;
        }

         //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Intialize the grid
        /// </summary>
        private void InitGrid()
        {
            NumericTextBoxColumn copiesColumn = documentChargeGrid.AddNumericTextBoxColumn(CopiesColumn, "Copies", "Copies", 70, 3);
            copiesColumn.CellTemplate.Style.Padding = new Padding(0, 1, 0, 2);
            copiesColumn.Resizable = DataGridViewTriState.False;
            NumericTextBoxColumn pagesColumn = documentChargeGrid.AddNumericTextBoxColumn(PagesColumn, "Pages", "Pages", 70, 5);
            pagesColumn.Resizable = DataGridViewTriState.False;            
            DataGridViewTextBoxColumn billingTierColumn = documentChargeGrid.AddTextBoxColumn(BillingTierColumn, "Billing Tier", "BillingTier", 200);
            billingTierColumn.Resizable = DataGridViewTriState.False;
            billingTierColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            DataGridViewTextBoxColumn amountColumn = documentChargeGrid.AddTextBoxColumn(AmountColumn, "Amount", "Amount", 50);
            amountColumn.DefaultCellStyle.Format = "C";
            amountColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            amountColumn.MinimumWidth = 55;
            amountColumn.Resizable = DataGridViewTriState.False;
            amountColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            documentChargeGrid.Columns[PagesColumn].ReadOnly = true;
            documentChargeGrid.Columns[BillingTierColumn].ReadOnly = true;
            documentChargeGrid.Columns[AmountColumn].ReadOnly = true;
            documentChargeGrid.ClearSelection();
            documentChargeGrid.DefaultCellStyle.SelectionBackColor = documentChargeGrid.BackgroundColor;
            documentChargeGrid.DefaultCellStyle.SelectionForeColor = documentChargeGrid.ForeColor;
        }

        #endregion

        #region Methods
        
        /// <summary>
        /// Updates billing tier amount based on copies entered by user
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_UpdateBillingTierAmount(object sender, EventArgs e)
        {
            documentCharge = 0;
            totalPages = 0;

            foreach (DocumentChargeDetails doc in documentChargeGrid.Items)
            {
                documentCharge += doc.Amount;
                totalPages += (doc.Pages * doc.Copies);
            }            

            SetTotalAmountAndPages();   
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
            RequestEvents.OnReleaseCostUpdated(Pane, null);
        }

        /// <summary>
        /// Set the totalpages and totalcopies value
        /// </summary>
        private void SetTotalAmountAndPages()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            totalPagesLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                rm.GetString(totalPagesLabel.Name + "." + GetType().Name),
                                                totalPages.ToString(ROIConstants.NumberFormat, System.Threading.Thread.CurrentThread.CurrentUICulture));
            double totalAmount = documentCharge;
            totalAmountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                 rm.GetString(totalAmountLabel.Name + "." + GetType().Name),
                                                 ReleaseDetails.FormattedAmount(totalAmount));
        }

        /// <summary>
        /// Recalculate tax based on user select apply tax 
        /// </summary>
        public void CalculateTax(double taxPercentage)
        {
            facilityTaxPercentage = taxPercentage;
            foreach (DocumentChargeDetails docCharge in documentChargeGrid.Items)
            {
                docCharge.TaxAmount = docCharge.Amount * facilityTaxPercentage / 100;
            }
            Process_UpdateBillingTierAmount(null, null);
        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(documentChargeGrid, rm, CopiesColumn);
            SetLabel(documentChargeGrid, rm, PagesColumn);
            SetLabel(documentChargeGrid, rm, BillingTierColumn);
            SetLabel(documentChargeGrid, rm, AmountColumn);
            SetLabel(rm, documentChargesGroupBox);            
        }

        /// <summary>
        /// Gets localized key of UI control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the document charge details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            Collection<DocumentChargeDetails> documentCharges = (data == null) ? new Collection<DocumentChargeDetails>()
                                                                               : (Collection<DocumentChargeDetails>)data;
            
            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            facilityTaxPercentage = billingEditor.Request.TaxPercentage;
            //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
            foreach (DocumentChargeDetails docCharge in documentChargeGrid.Items)
            {
                docCharge.TaxAmount = docCharge.Amount * facilityTaxPercentage / 100;
                documentCharges.Add(docCharge);
            }

            return documentCharges;
        }

        private void ClearData()
        {            
            DocumentCharges.Clear();
            totalPages = 0;
            documentCharge = 0.00;
            SetTotalAmountAndPages();
        }

        /// <summary>
        /// Sets document charge details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data, bool hasDocuments)
        {
            ClearData();
            Collection<DocumentChargeDetails> documentCharges = (Collection<DocumentChargeDetails>)data;
            BillingTierDetails billingTier;
            this.hasDocuments = hasDocuments;
            //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
            foreach (DocumentChargeDetails documentCharge in documentCharges)
            {                
                billingTier = BillingAdminController.Instance.GetBillingTier(documentCharge.BillingTierId);
                documentCharge.BillingTierDetail = billingTier;
                UpdateBillingTierAmount(documentCharge, documentCharge.Pages, documentCharge.Copies);
                DocumentCharges.Add(documentCharge);
            }
            
            documentChargeGrid.SetItems((IFunctionCollection)DocumentCharges);
            documentChargeGrid.ClearSelection();
            Process_UpdateBillingTierAmount(null, null);
        }

        public void DisableDocumentCharges()
        {            
            documentChargeGrid.Columns[0].ReadOnly = true;
            isEnabled = false;
        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Occurs when user changes the text in the copies column
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void textBox_TextChanged(object sender, EventArgs e)
        {      
            TextBox tBox = (TextBox)sender;
            DataGridViewRow row = documentChargeGrid.Rows[documentChargeGrid.CurrentCell.RowIndex];
            DocumentChargeDetails docCharge = (DocumentChargeDetails)row.DataBoundItem;
            try
            {
                int copies;

                if (!int.TryParse(tBox.Text.Trim(), NumberStyles.Number, System.Threading.Thread.CurrentThread.CurrentUICulture, out copies) || copies <= 0)
                {
                    docCharge.Copies = 1;
                    tBox.Text = Convert.ToString(docCharge.Copies, System.Threading.Thread.CurrentThread.CurrentUICulture);
                    throw new ROIException(ROIErrorCodes.InvalidCopy);
                }
                docCharge.Copies = copies;
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                UpdateBillingTierAmount(docCharge, docCharge.Pages, docCharge.Copies);
                Process_UpdateBillingTierAmount(sender, (EventArgs)e);
            }

        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Occurs when editing control in the grid is in editing mode
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void documentChargeGrid_EditingControlShowing(object sender, DataGridViewEditingControlShowingEventArgs e)
        {
            TextBox textBox = e.Control.Controls[0] as TextBox;
            textBox.TextChanged -= new EventHandler(textBox_TextChanged);
            textBox.TextChanged += new EventHandler(textBox_TextChanged);

        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Updates documentcharge amount based on pages and copies entered by user.
        /// Removed BaseCharge for request Subsequent release or rerelase.
        /// </summary>
        /// <param name="docCharge"></param>
        /// <param name="pages"></param>
        /// <param name="copies"></param>
        public void UpdateBillingTierAmount(DocumentChargeDetails docCharge, int pages, int copies)
        {
            if (pages == 0)
            {
                docCharge.Amount = 0.00;
                return;
            }

            long totalpages = pages * copies;

            BillingPaymentInfoEditor billingEditor = (BillingPaymentInfoEditor)Pane.ParentPane;
            long totalPagesForBillingTier = (!(billingEditor.Request.ReleaseCount > 0)) ? totalpages
                                                                           : docCharge.TotalPages + totalpages;

            if (billingEditor.Release.IsReleased)
            {
                totalPagesForBillingTier = docCharge.TotalPages;
            }

            long pageRange = (totalPagesForBillingTier - totalpages + 1);

            double price = (docCharge.RemoveBaseCharge) ? 0 : docCharge.BillingTierDetail.BaseCharge;
            int pageTierCount = docCharge.BillingTierDetail.PageTiers.Count;
            if (pageTierCount > 0)
            {
                foreach (PageLevelTierDetails pageTier in docCharge.BillingTierDetail.PageTiers)
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

                PageLevelTierDetails lastPageTier = docCharge.BillingTierDetail.PageTiers[pageTierCount - 1];
                if (totalPagesForBillingTier > lastPageTier.EndPage)
                {
                    price += (docCharge.BillingTierDetail.OtherPageCharge * (totalPagesForBillingTier + 1 - pageRange));
                }
            }
            double docChargeAmount = 0.0;
            docChargeAmount = docCharge.Amount;
            docCharge.Amount = price;

            if ((billingEditor.Request.ReleaseCount > 0) && (!hasDocuments))
            {
                docCharge.Amount = docChargeAmount;
            }
            
            documentChargeGrid.Refresh();
        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Occurs when user hovers the billingtier description column
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void documentChargeGrid_CellToolTipTextNeeded(object sender, DataGridViewCellToolTipTextNeededEventArgs e)
        {
            if (e.RowIndex > -1 && e.ColumnIndex == documentChargeGrid.Columns[BillingTierColumn].Index)
            {
                DataGridViewRow dataGridViewRow = documentChargeGrid.Rows[e.RowIndex];
                e.ToolTipText = dataGridViewRow.Cells[BillingTierColumn].Value.ToString();
            }
        }

        #endregion

        #region Properties

        ///// <summary>
        ///// DocumentCharge - sum of each document charge's amount
        ///// </summary>
        //public double DocumentCharge
        //{
        //    get { return documentCharge + documentTaxCharge; }
        //}

        /// <summary>
        /// DocumentCharge - sum of each document charge's amount
        /// </summary>
        public double DocumentCharge
        {
            get { return documentCharge; }
        }

        /// <summary>
        /// Total pages to be released
        /// </summary>
        public int TotalPages
        {
            get { return totalPages;}
        }

        private bool isEnabled;
        public bool IsEnabled
        {
            get { return isEnabled; }
        }

        //CR# 366908 - DocumentCharges section not getting adjusted while dragging the LSP
        /// <summary>
        /// Property holds the collection of documentcharges
        /// </summary>
        public ComparableCollection<DocumentChargeDetails> DocumentCharges
        {
            get
            {
                if (gridDocumentCharges == null)
                {
                    gridDocumentCharges = new ComparableCollection<DocumentChargeDetails>();
                }
                return gridDocumentCharges;
            }
        }
            
        #endregion

    }
}
