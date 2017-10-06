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
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{

    /// <summary>
    /// Class which maintains Sales Tax Summay
    /// </summary>
    public partial class SalesTaxSummaryUI : ROIBaseUI
    {
        #region Fields

        private const string DateColumn = "summarydate";
        private const string SummaryReasonColumn = "summaryreason";
        private const string HasSalesTaxColumn = "salestax";
        private const string ChargeNameColumn = "chargename";
        private const string AmountColumn = "amount";
        private const string TaxAmountColumn = "taxamount";

        private double facilityTaxPercentage;
        private double totalCharge;
        private double totalNonTaxableChargeAmount;
        private double totalTaxAmount;
        private bool isDirty;

        private ComparableCollection<SalesTaxChargeDetails> salesTaxCharges;
        private ComparableCollection<SalesTaxReasons> salesTaxReasons;
        private EventHandler dirtyDataHandler;
        private DataGridViewCellEventHandler cellClickHandler;
        private Hashtable taxState;
        private bool isGridItemChanged;
        private bool applyTaxState;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public SalesTaxSummaryUI()
        {
            InitializeComponent();
            InitGrid();
            InitEvents();
            EnableEvents();
            cellClickHandler = new DataGridViewCellEventHandler(Process_CellClickHandler);
            isEnabled = true;
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the grid
        /// </summary>
        private void InitGrid()
        {
            DataGridViewDisableCheckBoxColumn checkBoxColumn = salesTaxGrid.
                                                               AddDisableCheckBoxColumn(HasSalesTaxColumn, "Select/Deselect Tax", "HasSalesTax", 128);
            checkBoxColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            checkBoxColumn.Resizable = DataGridViewTriState.False;
            DataGridViewTextBoxColumn chargeColumn = salesTaxGrid.AddTextBoxColumn(ChargeNameColumn, "Charge Type", "ChargeName", 180);
            chargeColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            DataGridViewTextBoxColumn amountColumn = salesTaxGrid.AddTextBoxColumn(AmountColumn, "Charge", "Amount", 75);
            amountColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            amountColumn.DefaultCellStyle.Format = "C";
            DataGridViewTextBoxColumn salesTaxColumn = salesTaxGrid.AddTextBoxColumn(TaxAmountColumn, "Sales Tax", "TaxAmount", 70);
            salesTaxColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            salesTaxColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            salesTaxColumn.DefaultCellStyle.Format = "C";

            //Reason Grid
            reasonGrid.AddTextBoxColumn(DateColumn, "Date", "FormattedDate", 70);
            DataGridViewTextBoxColumn reasonColumn = reasonGrid.AddTextBoxColumn(SummaryReasonColumn, "Reason For Tax Change", "Reason", 150);
            reasonColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
        }

        /// <summary>
        /// Occurs when user click on the ApplyTax checkbox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void applyTaxCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            applyTaxState = applyTaxcheckBox.Checked;
            RequestEvents.OnApplyTaxChanged(Pane, null);
            EnableSalesTaxColumn();
        }        

        /// <summary>
        /// Initialize the events
        /// </summary>
        private void InitEvents()
        {            
            dirtyDataHandler = new EventHandler(MarkDirty);
        }

        /// <summary>
        /// Apply Localization for the UI controls.
        /// </summary>        
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());            
            SetLabel(rm, totalLabel);
            SetLabel(rm, taxFacilityLabel);
            SetLabel(rm, taxPercentageLabel);
            SetLabel(rm, salesTaxReasonLabel);
            SetLabel(rm, applyTaxcheckBox);
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            if (reasonGrid.Rows.Count > 0) reasonGrid.Rows.Clear();
            reasonTextBox.Text = string.Empty;
        }

        /// <summary>
        /// Set the previously saved reasons for adding/removing tax in the grid
        /// List each tax elements in the sales tax summary grid
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data, bool requestSalesTax)
        {
            DisableEvents();
            ClearData();

            ReleaseDetails release = (ReleaseDetails)ROIViewUtility.DeepClone(data);

            salesTaxReasons = new ComparableCollection<SalesTaxReasons>(release.TaxReasons, new SalesTaxReasonComparer());
            salesTaxReasons.ApplySort(TypeDescriptor.GetProperties(typeof(SalesTaxReasons))["FormattedDate"], ListSortDirection.Descending);
            salesTaxReasons.SetSortedInfo(TypeDescriptor.GetProperties(typeof(SalesTaxReasons))["FormattedDate"], ListSortDirection.Descending);
            reasonGrid.SetItems((IFunctionCollection)salesTaxReasons);

            salesTaxGrid.SetItems((IFunctionCollection)SalesTaxCharges);
            applyTaxcheckBox.Checked = requestSalesTax;
            applyTaxState = requestSalesTax;

            EnableEvents();
            EnableSalesTaxColumn();
            
        }

        /// <summary>
        /// Gets the reason details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public void GetData(object data)
        {
            ReleaseDetails release = (ReleaseDetails)data;
            release.TaxReasons.Clear();

            if (reasonGrid.Items != null)
            {
                foreach (SalesTaxReasons reason in reasonGrid.Items)
                {
                    release.TaxReasons.Add(reason);
                }
            }
            if (!string.IsNullOrEmpty(reasonTextBox.Text.Trim()))
            {
                SalesTaxReasons reason = new SalesTaxReasons();
                reason.Reason = reasonTextBox.Text;
                reason.CreatedDate = DateTime.Now;
                release.TaxReasons.Add(reason);
                SalesTaxReasonsList.Add(reason);
                reasonTextBox.Text = string.Empty;
            }
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        public void EnableEvents()
        {
            reasonTextBox.TextChanged += dirtyDataHandler;            
            applyTaxcheckBox.CheckedChanged += dirtyDataHandler;
            salesTaxGrid.CellContentClick += cellClickHandler;

            applyTaxcheckBox.CheckedChanged += new EventHandler(applyTaxCheckBox_CheckedChanged);
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        public void DisableEvents()
        {
            reasonTextBox.TextChanged -= dirtyDataHandler;
            applyTaxcheckBox.CheckedChanged -= dirtyDataHandler;
            salesTaxGrid.CellContentClick += cellClickHandler;

            applyTaxcheckBox.CheckedChanged -= new EventHandler(applyTaxCheckBox_CheckedChanged);
        }

        /// <summary>
        /// Mark the data as dirty state
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            IsDirty = true;
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
        }

        /// <summary>
        /// Occurs when user click the cell inside the grid if applytax checkbox is enabled
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_CellClickHandler(object sender, DataGridViewCellEventArgs e)
        {
            if ((ApplyTaxState && e.RowIndex != -1 && e.ColumnIndex == 0) && (EnableSaveButton))
            {
                IsDirty = true;
                ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
            }
        }


        /// <summary>
        /// prepopulate the tax percentage and facility in summary tab
        /// </summary>
        /// <param name="request"></param>
        public void PrePopulate(RequestDetails request)
        {
            facilityTaxPercentage = request.TaxPercentage;            
            percentageValueLabel.Text = (request.DefaultFacility.TaxPercentage / 100).ToString("P", System.Threading.Thread.CurrentThread.CurrentUICulture);
            totalTaxAmount = 0.0;
            EnableSaveButton = true;
        }

        /// <summary>
        /// set the total charge amount in the grid footer
        /// </summary>
        public void SetTotalChargeAmount()
        {
            chargeTotalLabel.Text = totalCharge.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
        }

        /// <summary>
        /// set the total tax amount for all the tax elements
        /// </summary>
        public void SetTotalTaxAmount()
        {
            totalCharge = 0.0;
            totalTaxAmount = 0.0;
            totalNonTaxableChargeAmount = 0.0;

            foreach (DataGridViewRow row in salesTaxGrid.Rows)
            {
                SalesTaxChargeDetails salesTaxChargeDetails = (SalesTaxChargeDetails)row.DataBoundItem;
				// CR#365251 - Total charge is calculated based on both hassalestax property as well as applytax checkbox
                totalCharge += (salesTaxChargeDetails.HasSalesTax && applyTaxcheckBox.Checked) ? Math.Round(salesTaxChargeDetails.Amount,2) : 0.0;
                totalNonTaxableChargeAmount += (!salesTaxChargeDetails.HasSalesTax) ? salesTaxChargeDetails.Amount : 0.0;                
                //Modified for CR#359331 & CR#365251
                totalTaxAmount += (salesTaxChargeDetails.HasSalesTax && applyTaxcheckBox.Checked) ? Math.Round(salesTaxChargeDetails.TaxAmount, 2) : 0.0;

            }

            salelsTaxTotalLabel.Text = totalTaxAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            RequestEvents.OnTotalTaxChanged(null, null);
            EnableSalesTaxColumn();
        }

        public void EnableSalesTaxColumn()
        {
            for (int i = 0; i < salesTaxGrid.RowCount; i++)
            {
                SalesTaxChargeDetails salesTaxChargeDetails = (SalesTaxChargeDetails)salesTaxGrid.Rows[i].DataBoundItem;
                string str = salesTaxChargeDetails.Key;
                string str1 = str.Substring(str.LastIndexOf('.') + 1);

                DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)salesTaxGrid.Rows[i].Cells[HasSalesTaxColumn];

                if (salesTaxChargeDetails.HasBillingTier)
                {
                    salesTaxGrid.Rows[i].Cells[HasSalesTaxColumn].Tag = "False";
                }
                else
                {
                    if (!ApplyTaxState)
                    {
                        salesTaxGrid.Rows[i].Cells[HasSalesTaxColumn].Tag = "False";

                    }
                    else
                    {
                        salesTaxGrid.Rows[i].Cells[HasSalesTaxColumn].Tag = "True";
                    }
                }
            }
            
        }

        /// <summary>
        /// Disable the salestaxsummaryUI after done the release
        /// </summary>
        public void DisableSalesTaxSummaryUI()
        {
           taxSummaryPanel.Enabled = false;
            isEnabled = false;
        }

        /// <summary>
        /// Retrieve sales tax summary items from the salestax grid.
        /// </summary>
        /// <returns></returns>
        public Hashtable RetrieveTaxItemStates()
        {
            taxState = new Hashtable();

            foreach (DataGridViewRow row in salesTaxGrid.Rows)
            {
                SalesTaxChargeDetails salesTaxChargeDetail = new SalesTaxChargeDetails();
                salesTaxChargeDetail = (SalesTaxChargeDetails)row.DataBoundItem;
                if (!(taxState.ContainsKey(salesTaxChargeDetail.Key)))
                    taxState.Add(salesTaxChargeDetail.Key, salesTaxChargeDetail.HasSalesTax);
            }

            salesTaxGrid.Invalidate();
            salesTaxGrid.Refresh();
            return taxState;
        }

        /// <summary>
        /// this method will be executing when click the cell content in the sales tax grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void salesTaxGrid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            if (salesTaxGrid.Columns[e.ColumnIndex] is DataGridViewCheckBoxColumn && e.RowIndex != -1)
            {
                EnableSaveButton = true;
                SalesTaxChargeDetails salesTaxChargeDetails = (SalesTaxChargeDetails)salesTaxGrid.SelectedRows[0].DataBoundItem;
                DataGridViewCheckBoxCell checkBoxCell = (DataGridViewCheckBoxCell)salesTaxGrid.SelectedRows[0].Cells[HasSalesTaxColumn];
                string str = salesTaxChargeDetails.Key;
                string str1 = str.Substring(str.LastIndexOf('.') + 1);

                if (str1.Equals("Doc"))
                {
                    checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                if (ApplyTaxState)
                {
                    checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                else
                {
                    checkBoxCell.Value = false;
                }
                if ((str1.Equals("Doc")) && (checkBoxCell.Tag.Equals("True")))
                {
                    checkBoxCell.Value = !Convert.ToBoolean(checkBoxCell.Value, System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                if (salesTaxChargeDetails.HasBillingTier)
                {
                    EnableSaveButton = false;
                }
                
                //Modified for CR#359331
                salesTaxChargeDetails.TaxAmount = Math.Round((salesTaxChargeDetails.Amount * facilityTaxPercentage / 100), 2);
                salesTaxGrid.Refresh();
                SetTotalTaxAmount();
                SetTotalChargeAmount();
                isGridItemChanged = true;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// Sets true if dirty data available.
        /// </summary>
        public bool IsDirty
        {
            get { return isDirty; }
            set
            {
                isDirty = value;
            }
        }

        /// <summary>
        /// Property used to hold the total tax amount of the request
        /// </summary>
        public double TotalTaxAmount
        {
            get { return totalTaxAmount; }
        }

        /// <summary>
        /// Property used to hold the total charge amount of the request
        /// </summary>
        public double TotalChargeAmount
        {
            get { return totalCharge; }
        }

        /// <summary>
        /// Property used to hold the facility tax percentage.
        /// </summary>
        public double FacilityTaxPercentage
        {
            get { return facilityTaxPercentage; }
            set { facilityTaxPercentage = value; }
        }

        /// <summary>
        /// Property holds the fee charge collection
        /// </summary>
        public ComparableCollection<SalesTaxChargeDetails> SalesTaxCharges
        {
            get
            {
                if (salesTaxCharges == null)
                {
                    salesTaxCharges = new ComparableCollection<SalesTaxChargeDetails>();
                }

                return salesTaxCharges;
            }
        }

        /// <summary>
        /// Property holds the collection of sales tax reasons
        /// </summary>
        public ComparableCollection<SalesTaxReasons> SalesTaxReasonsList
        {
            get
            {
                if (salesTaxReasons == null)
                {
                    salesTaxReasons = new ComparableCollection<SalesTaxReasons>();
                }
                return salesTaxReasons;
            }
        }

        /// <summary>
        /// property holds the state of the cell content click in the grid
        /// </summary>
        public bool IsGridItemChanged
        {
            get { return isGridItemChanged; }
            set { isGridItemChanged = value; }
        }

        /// <summary>
        /// property holds the applytax checkbox value
        /// </summary>
        public bool ApplyTaxState
        {
            get { return applyTaxState; }
            set { applyTaxState = value; }
        }

        /// <summary>
        /// property holds the applytax checkbox control
        /// </summary>
        public CheckBox ApplyTaxCheckBox
        {
            get { return applyTaxcheckBox; }
            set { applyTaxcheckBox = value; }
        }

        public double TotalNonTaxableChargeAmount
        {
            get { return totalNonTaxableChargeAmount; }
            set { totalNonTaxableChargeAmount = value; }
        }

        private bool isEnabled;
        public bool IsEnabled
        {
            get { return isEnabled; }
        }

        public bool enableSaveButton;
        public bool EnableSaveButton
        {
            get { return enableSaveButton; }
            set { enableSaveButton = value; }
        }

        #endregion

    }
}
