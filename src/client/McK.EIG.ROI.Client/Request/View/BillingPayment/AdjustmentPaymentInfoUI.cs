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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Text.RegularExpressions;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.BillingPayment
{
    /// <summary>
    /// Class which maintains the request transactions
    /// </summary>
    public partial class AdjustmentPaymentInfoUI : ROIBaseUI
    {
        #region Fields

        private const string DateColumn = "adjustmentdate";
        private const string AdjustmentPaymentColumn = "adjustmentpayment";
        private const string DescriptionColumn = "descriptionreason";
        private const string AmountColumn = "amount";

        private const string InvoiceIdColumn = "invoiceid";
        private const string CreatedDateColumn = "invoicecreateddate";
        private const string BalanceDueColumn = "invoicebalancedue";
        private const string AmountPaidColumn = "invoiceamountpaid";
        private const string PaymentAmountPaidColumn = "paymentamountpaid";
		//added for CR#359331.
        private const string PaymentTab = "paymentTabPage";
        private const string AdjustmentTab = "adjustmentTabPage";

        private string ZeroString;

        private EventHandler dirtyDataHandler;
        private DataGridViewRowsAddedEventHandler rowAddedHandler;
        private DataGridViewBindingCompleteEventHandler bindingCompleteHandler;

        private double adjustmentTotal;
        private double paymentTotal;
        private Collection<RequestTransaction> newTransactions;

        private static bool isAutomaticTransaction;
        private double initialBalanceDue;
        private long invoiceRequestId;
        private bool isGridAmountChanged;
        private bool isAdjustmentGridAmountChanged;
        private bool hasRadioButtonChanged;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the UI components
        /// </summary>
        public AdjustmentPaymentInfoUI()
        {
            InitializeComponent();
            ApplySecurityRights();
            InitGrid();
            InitInvoiceGrid(invoiceAdjustmentGrid);
            InitInvoiceGrid(invoicePaymentGrid);

            dirtyDataHandler = new EventHandler(MarkDirty);
            rowAddedHandler = new DataGridViewRowsAddedEventHandler(grid_RowsAdded);
            bindingCompleteHandler = new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            invoiceTransactionsGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(grid_RowSelected);
            invoiceAdjustmentGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(adjustmentGrid_RowSelected);
            invoicePaymentGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(adjustmentGrid_RowSelected);
            invoiceTransactionsGrid.Sorted += new EventHandler(grid_Sorted);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the grid
        /// </summary>
        private void InitGrid()
        {
            invoiceTransactionsGrid.AddTextBoxColumn(InvoiceIdColumn, "Invoice", "Id", 70);
            invoiceTransactionsGrid.AddTextBoxColumn(DateColumn, "Date", "FormattedDate", 70);
            invoiceTransactionsGrid.AddTextBoxColumn(AdjustmentPaymentColumn, "Adj/Pay", "Transaction", 75);
			// CR#359230
            DataGridViewTextBoxColumn descColumn = invoiceTransactionsGrid.AddTextBoxColumn(DescriptionColumn, "Description/Reason", "PaymentTypeDescription", 150);
            descColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
			//CR# 366908 - Adjustments/Payments screen is not getting adjusted accordingly
            descColumn.MinimumWidth = 150;

            DataGridViewTextBoxColumn amountColumn = invoiceTransactionsGrid.AddTextBoxColumn(AmountColumn, "Amount", "FormattedAmount", 100);
            amountColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
            amountColumn.DefaultCellStyle.Format = "C";
        }

        /// <summary>
        /// Initialize the invoice grid
        /// </summary>
        private static void InitInvoiceGrid(EIGDataGrid grid)
        {
            grid.AddTextBoxColumn(InvoiceIdColumn, "Invoice", "Id", 70);
            grid.AddTextBoxColumn(CreatedDateColumn, "Date", "CreatedDate", 70);
            DataGridViewColumn balanceDueColumn = grid.AddTextBoxColumn(BalanceDueColumn, "Amount", "FormattedBalanceDue", 75);
            balanceDueColumn.DefaultCellStyle.Format = "C";
            string amountPaidColumn = grid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;
            CustomTextBoxColumn amountPaidColum = grid.AddCustomTextBoxColumn(amountPaidColumn, "Adjustment", "PaidAmount", 150);
            amountPaidColum.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            amountPaidColum.CellTemplate.Style.Padding = new Padding(0, 1, 0, 2);
            amountPaidColum.DefaultCellStyle.Format = "C";
            grid.Columns[InvoiceIdColumn].ReadOnly = true;
            grid.Columns[CreatedDateColumn].ReadOnly = true;
            grid.Columns[BalanceDueColumn].ReadOnly = true;
            grid.ClearSelection();
        }

        /// <summary>
        /// Subscribe the events
        /// </summary>
        private void EnableEvents()
        {
            adjustmentAmountTextBox.TextChanged += dirtyDataHandler;
            reasonComboBox.SelectedIndexChanged += dirtyDataHandler;
            reasonComboBox.TextChanged          += dirtyDataHandler;

            paymentAmountTextBox.TextChanged           += dirtyDataHandler;
            paymentMethodComboBox.SelectedIndexChanged += dirtyDataHandler;
            adjustmentAmountTextBox.Leave += new EventHandler(adjustmentAmountTextBox_Leave);
            paymentAmountTextBox.Leave    += new EventHandler(paymentAmountTextBox_Leave);
        }

        /// <summary>
        /// Unsubscribe the events
        /// </summary>
        public void DisableEvents()
        {
            adjustmentAmountTextBox.TextChanged -= dirtyDataHandler;
            reasonComboBox.SelectedIndexChanged -= dirtyDataHandler;
            reasonComboBox.TextChanged          -= dirtyDataHandler;

            paymentAmountTextBox.TextChanged           -= dirtyDataHandler;
            paymentMethodComboBox.SelectedIndexChanged -= dirtyDataHandler;
            adjustmentAmountTextBox.Leave              -= new EventHandler(adjustmentAmountTextBox_Leave);
            paymentAmountTextBox.Leave                 -= new EventHandler(paymentAmountTextBox_Leave);
        }

        /// <summary>
        /// Split the amount among the existing invoices
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentAmountTextBox_Leave(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            errorProvider.Clear();
            double balanceAmount;
            if (!string.IsNullOrEmpty(adjustmentAmountTextBox.Text.Trim()))
            {
                if (!double.TryParse(adjustmentAmountTextBox.Text.Trim(), out balanceAmount) || balanceAmount < 0 ||
                    !Regex.IsMatch(Convert.ToString(balanceAmount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
                {
                    if (balanceAmount == 0)
                    {
                        ClearAmountPaidColumn(invoiceAdjustmentGrid);
                    }
                    errorProvider.SetError(adjustmentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
                    return;
                }
                isAutomaticTransaction = true;

                if (invoiceAdjustmentGrid.Items != null && invoiceAdjustmentGrid.Items.Count > 0)
                {
                    SplitInvoiceAmount(invoiceAdjustmentGrid, balanceAmount);
                }

                adjustmentAmountTextBox.Text = ROIViewUtility.FormattedAmount(balanceAmount);
            }
            else
            {
                ClearAmountPaidColumn(invoiceAdjustmentGrid);
            }
        }

        /// <summary>
        /// Split the amount among the existing invoices
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void paymentAmountTextBox_Leave(object sender, EventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            errorProvider.Clear();
            double balanceAmount;
            if (!string.IsNullOrEmpty(paymentAmountTextBox.Text.Trim()))
            {
                if (!double.TryParse(paymentAmountTextBox.Text.Trim(), out balanceAmount) || balanceAmount < 0 ||
                    !Regex.IsMatch(Convert.ToString(balanceAmount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
                {
                    if (balanceAmount == 0)
                    {
                        ClearAmountPaidColumn(invoicePaymentGrid);
                    }
                    errorProvider.SetError(paymentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
                    return;
                }

                isAutomaticTransaction = true;
                if (invoicePaymentGrid.Items != null && invoicePaymentGrid.Items.Count > 0)
                {
                    SplitInvoiceAmount(invoicePaymentGrid, balanceAmount);
                }
                paymentAmountTextBox.Text = ROIViewUtility.FormattedAmount(balanceAmount);
            }
            else
            {
                ClearAmountPaidColumn(invoicePaymentGrid);
            }
        }

        /// <summary>
        /// Split the amount entered among the invoices
        /// </summary>
        /// <param name="adjPayGrid"></param>
        /// <param name="balanceAmount"></param>
        private void SplitInvoiceAmount(EIGDataGrid adjPayGrid, double balanceAmount)
        {
            if (adjPayGrid.Name.Equals("invoiceAdjustmentGrid"))
            {
                adjPayGrid.CellValueChanged -= new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
            }
            else
            {
                adjPayGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
            }
            ClearAmountPaidColumn(adjPayGrid);
            int lastValidIndex = -1;//Hold this variable for adding the balance amount to the last row            
            ComparableCollection<InvoiceChargeDetails> invoiceList = new ComparableCollection<InvoiceChargeDetails>(adjPayGrid.Items);
            invoiceList.ApplySort(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Ascending);
            for (int count = 0; count < invoiceList.Count; count++)
            {
                if (invoiceList[count].BalanceDue > 0)
                {
                    invoiceList[count].PaidAmount = (invoiceList[count].BalanceDue <= balanceAmount) ?
                                                    invoiceList[count].BalanceDue : balanceAmount;
                    balanceAmount = (invoiceList[count].BalanceDue <= balanceAmount) ?
                                    balanceAmount - invoiceList[count].BalanceDue : 0;
                    lastValidIndex = count;
                }
                else if (invoiceList[count].BalanceDue == 0)
                {
                    lastValidIndex = count;
                }
            }
            if (balanceAmount > 0)
            {
                if (lastValidIndex == -1) lastValidIndex = invoiceList.Count - 1;
                if (invoiceList[lastValidIndex].BalanceDue >= 0)
                {
                    invoiceList[lastValidIndex].PaidAmount = invoiceList[lastValidIndex].PaidAmount + balanceAmount;
                }
            }
            if (adjPayGrid.Name.Equals("invoiceAdjustmentGrid"))
            {
                adjPayGrid.CellValueChanged += new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
            }
            else
            {
                adjPayGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
            }
            adjPayGrid.Refresh();
            MarkDirty(Pane, null);
        }

        private static bool CheckInvoiceAmounts(EIGDataGrid adjPayGrid)
        {
            foreach (DataGridViewRow row in adjPayGrid.Rows)
            {
                double amount;
                if (double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount) && Math.Round(amount, 2) != 0)
                {
                    return true;
                }
            }
            return false;
        }

        private void grid_RowSelected(DataGridViewRow row)
        {
            invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
            row.Selected = false;
        }

        /// <summary>
        /// Disable the selection of adjustment/payment grid
        /// </summary>
        /// <param name="row"></param>
        private void adjustmentGrid_RowSelected(DataGridViewRow row)
        {
            row.Selected = false;
        }

        /// <summary>
        /// Apply Localization for the UI controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(invoiceTransactionsGrid, rm, DateColumn);
            SetLabel(invoiceTransactionsGrid, rm, AdjustmentPaymentColumn);
            SetLabel(invoiceTransactionsGrid, rm, DescriptionColumn);
            SetLabel(invoiceTransactionsGrid, rm, AmountColumn);

            SetLabel(invoiceAdjustmentGrid, rm, InvoiceIdColumn);
            SetLabel(invoiceAdjustmentGrid, rm, CreatedDateColumn);
            SetLabel(invoiceAdjustmentGrid, rm, BalanceDueColumn);
            SetLabel(invoiceAdjustmentGrid, rm, AmountPaidColumn);

            SetLabel(invoicePaymentGrid, rm, InvoiceIdColumn);
            SetLabel(invoicePaymentGrid, rm, CreatedDateColumn);
            SetLabel(invoicePaymentGrid, rm, BalanceDueColumn);
            SetLabel(invoicePaymentGrid, rm, PaymentAmountPaidColumn);

            SetLabel(rm, adjustmentPaymentGroupBox);
            SetLabel(rm, adjustmentGroupBox);
            SetLabel(rm, adjustmentTabPage);
            SetLabel(rm, paymentTabPage);
            SetLabel(rm, paymentGroupBox);
            SetLabel(rm, debitRadioButton);
            SetLabel(rm, creditRadioButton);
            SetLabel(rm, adjustmentReasonLabel);
            SetLabel(rm, adjustmentAmountLabel);
            SetLabel(rm, noPaymentMethodMessageLabel);
            SetLabel(rm, paymentAmountLabel);
            SetLabel(rm, paymentMethodLabel);
            SetLabel(rm, paymentNoteLabel);
            SetLabel(rm, adjustmentPaymentTotalLabel);

            SetLabel(rm, addAdjustmentButton);
            SetLabel(rm, addPaymentButton);

            paymentAmountDollarLabel.Text = adjustmentAmountDollarLabel.Text = rm.GetString("dollarLabel");
            ZeroString = rm.GetString("zeroString");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, addAdjustmentButton);
            SetTooltip(rm, toolTip, adjustmentAmountTextBox);
            SetTooltip(rm, toolTip, reasonComboBox);
            SetTooltip(rm, toolTip, addPaymentButton);
            SetTooltip(rm, toolTip, paymentAmountTextBox);
            SetTooltip(rm, toolTip, paymentNoteTextBox);
        }

        /// <summary>
        /// Gets localized key of the UI control
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Clear control values
        /// </summary>
        private void ClearData()
        {
            if (invoiceTransactionsGrid.Rows.Count > 0) invoiceTransactionsGrid.Rows.Clear();

            errorProvider.Clear();
            paymentTotal = 0;
            adjustmentTotal = 0;
            adjustmentAmountTextBox.Text = string.Empty;
            paymentAmountTextBox.Text    = string.Empty;
            paymentNoteTextBox.Text      = string.Empty;
            reasonComboBox.SelectedIndex = 0;
            paymentMethodComboBox.SelectedIndex = 0;

            UpdateAdjustmentPaymentTotalLabel();
        }
        //
        /// <summary>
        /// Sets the transaction details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(InvoiceChargeDetailsList invoiceChargesDetails, long requestId)
        {
            DisableEvents();
            ClearData();
            if (invoiceChargesDetails == null)
            {
                invoiceChargesDetails = RequestController.Instance.RetrieveInvoicesAndAdjPay(requestId);
            }
            //RequestBillingInfo reqInfo =  billingUI.GetRequestBillingInfoObj();
            //invoiceChargesDetails.CurrentRequestBalanceDue = reqInfo.BalanceDue;
            if (invoiceChargesDetails != null)
            {
                List<RequestTransaction> requestTransactions = RetrieveRequestTransactions(invoiceChargesDetails.InvoiceCharges);
                invoiceTransactionsGrid.DataBindingComplete += bindingCompleteHandler;
                ComparableCollection<RequestTransaction> list = new ComparableCollection<RequestTransaction>(requestTransactions, new RequestTransactionComparer());
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Descending);
                list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Descending);
                invoiceTransactionsGrid.SetItems((IFunctionCollection)list);
                invoiceTransactionsGrid.DataBindingComplete -= bindingCompleteHandler;

                ComparableCollection<InvoiceChargeDetails> invoiceList = new ComparableCollection<InvoiceChargeDetails>(invoiceChargesDetails.InvoiceCharges);
                // CR#359231
                invoiceList.ApplySort(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Descending);
                invoiceList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Descending);
                invoiceAdjustmentGrid.SetItems((IFunctionCollection)invoiceList);
                invoicePaymentGrid.SetItems((IFunctionCollection)invoiceList);
            }

            invoiceRequestId = requestId;

            addAdjustmentButton.Enabled = false;
            addPaymentButton.Enabled = false;

            invoiceTransactionsGrid.SortEnabled = true;

            EnableEvents();
            invoiceAdjustmentGrid.ClearSelection();
            invoicePaymentGrid.ClearSelection();

            creditRadioButton.Checked = true;
            debitRadioButton.Checked = true;
            adjustmentAmountTextBox.Text = paymentAmountTextBox.Text = string.Empty;
            adjustmentAmountTextBox.Enabled = paymentAmountTextBox.Enabled = true;
            SetTotalAdjustmentPaymentAmount();
            EnableAdjustmentPaymentGrid(false, adjustmentPaymentTabControl.SelectedTab.Name.Equals(PaymentTab) ? invoicePaymentGrid : invoiceAdjustmentGrid);
        }
        


        /// <summary>
        /// Sets the transaction details
        /// </summary>
        /// <param name="data"></param>
        public void SetData(long requestId)
        {
            DisableEvents();
            ClearData();
           

            //InvoiceChargeDetailsList invoiceChargesDetails = BillingController.Instance.RetrieveInvoices(requestId);
            InvoiceChargeDetailsList invoiceChargesDetails = RequestController.Instance.RetrieveInvoicesAndAdjPay(requestId);

            if (invoiceChargesDetails != null)
            {
                List<RequestTransaction> requestTransactions = RetrieveRequestTransactions(invoiceChargesDetails.InvoiceCharges);
                invoiceTransactionsGrid.DataBindingComplete += bindingCompleteHandler;
                ComparableCollection<RequestTransaction> list = new ComparableCollection<RequestTransaction>(requestTransactions, new RequestTransactionComparer());
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Descending);
                list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestTransaction))["FormattedDate"], ListSortDirection.Descending);
                invoiceTransactionsGrid.SetItems((IFunctionCollection)list);
                invoiceTransactionsGrid.DataBindingComplete -= bindingCompleteHandler;

                ComparableCollection<InvoiceChargeDetails> invoiceList = new ComparableCollection<InvoiceChargeDetails>(invoiceChargesDetails.InvoiceCharges);
				// CR#359231
                invoiceList.ApplySort(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Descending);
                invoiceList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Descending);
                invoiceAdjustmentGrid.SetItems((IFunctionCollection)invoiceList);
                invoicePaymentGrid.SetItems((IFunctionCollection)invoiceList);
            }

            invoiceRequestId = requestId;

            addAdjustmentButton.Enabled = false;
            addPaymentButton.Enabled = false;

            invoiceTransactionsGrid.SortEnabled = true;

            EnableEvents();
            invoiceAdjustmentGrid.ClearSelection();
            invoicePaymentGrid.ClearSelection();
            
            creditRadioButton.Checked = true;
            debitRadioButton.Checked = true;
            adjustmentAmountTextBox.Text = paymentAmountTextBox.Text = string.Empty;
            adjustmentAmountTextBox.Enabled = paymentAmountTextBox.Enabled = true;
            SetTotalAdjustmentPaymentAmount();
            EnableAdjustmentPaymentGrid(false, adjustmentPaymentTabControl.SelectedTab.Name.Equals(PaymentTab) ? invoicePaymentGrid : invoiceAdjustmentGrid);
        }

        /// <summary>
        /// Get the request transactions from the list of InvoiceChargeDetails
        /// </summary>
        /// <param name="invoiceChargeDetails"></param>
        /// <returns></returns>
        private static List<RequestTransaction> RetrieveRequestTransactions(List<InvoiceChargeDetails> invoiceChargeDetails)
        {
            List<RequestTransaction> requestTransactions = new List<RequestTransaction>();
            foreach (InvoiceChargeDetails invoiceChargeDetail in invoiceChargeDetails)
            {
                foreach (RequestTransaction requestTransaction in invoiceChargeDetail.RequestTransactions)
                {
                    //28
                    //requestTransaction.Id = invoiceChargeDetail.Id;
                    requestTransactions.Add(requestTransaction);
                }
            }
            return requestTransactions;
        }

        /// <summary>
        /// Gets the transaction details
        /// </summary>
        /// <param name="data"></param>
        /// <returns></returns>
        public object GetData(object data)
        {
            Collection<RequestTransaction> txns = (data == null) ? new Collection<RequestTransaction>()
                                                                 : (Collection<RequestTransaction>)data;

            if (invoiceTransactionsGrid.Items != null)
            {
                foreach (RequestTransaction txn in invoiceTransactionsGrid.Items)
                {
                    txns.Add(txn);
                }
            }

            return txns;
        }

        /// <summary>
        /// Occurs when user click add button to add the transaction based on adjustment
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addAdjustmentButton_Click(object sender, EventArgs e)
        {
            isAdjustmentGridAmountChanged = false;
            double enteredAmount = 0;
            double actualAmount = 0;
            double amount;
            errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            //CR# 368499 Proper error message will be displayed if the entered amount is 0 or greater than zero
            if (!double.TryParse(adjustmentAmountTextBox.Text, out amount) || amount < 0 ||
               !Regex.IsMatch(Convert.ToString(enteredAmount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
            {
                errorProvider.SetError(adjustmentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmountFormat));
                return;
            }
            else if(amount == 0)
            {
                errorProvider.SetError(adjustmentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
                return;
            }

            if (!Validator.Validate(reasonComboBox.Text, ROIConstants.NameValidation))
            {
                errorProvider.SetError(reasonComboBox, rm.GetString(ROIErrorCodes.InvalidName));
                return;
            }

            adjustmentAmountTextBox.Text = ROIViewUtility.FormattedAmount(enteredAmount);

            foreach (DataGridViewRow row in invoiceAdjustmentGrid.Rows)
            {
                enteredAmount = double.Parse(row.Cells[AmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out actualAmount))
                {
                    actualAmount = 0;
                }
                if (enteredAmount > 0)
                {
                    InvoiceChargeDetails invoiceChargeDetails = (InvoiceChargeDetails)row.DataBoundItem;
                    invoiceChargeDetails.BalanceDue = debitRadioButton.Checked ? actualAmount + enteredAmount : actualAmount - enteredAmount;
                    invoiceAdjustmentGrid.Refresh();
                    invoiceTransactionsGrid.RowsAdded += rowAddedHandler;

                    RequestTransaction txn = new RequestTransaction();
                    txn.Id = Int64.Parse(row.Cells[InvoiceIdColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    txn.CreatedDate = DateTime.Now;
                    txn.ReasonName = (reasonComboBox.SelectedItem == null) ? string.Empty :
                                      ((ReasonDetails)reasonComboBox.SelectedItem).Name;
                    txn.Description = (reasonComboBox.SelectedItem == null) ? reasonComboBox.Text :
                                      ((ReasonDetails)reasonComboBox.SelectedItem).DisplayText;
                    txn.TransactionType = TransactionType.Adjustment;
                    txn.IsDebit = debitRadioButton.Checked;
                    txn.Amount = (txn.IsDebit) ? enteredAmount : -enteredAmount;
                    txn.AdjustmentPaymentType = isAutomaticTransaction ? AdjustmentPaymentType.Automatic : AdjustmentPaymentType.Manual;
                    txn.PaymentMode = txn.AdjustmentPaymentType.ToString();
                    txn.IsNewlyAdded = true;


                    NewTransactions.Add(txn);
                    invoiceTransactionsGrid.AddItem(txn);
                    invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
                }
            }
            adjustmentAmountTextBox.Text = string.Empty;
            reasonComboBox.SelectedIndex = 0;
            ClearAmountPaidColumn(invoiceAdjustmentGrid);
            SetTotalAdjustmentPaymentAmount();
            EnableAdjustmentPaymentGrid(false, invoiceAdjustmentGrid);
            adjustmentAmountTextBox.Enabled = true;
            InvoiceTransactionsGrid.Focus();
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(Pane, null);
        }

        /// <summary>
        /// Occurs when user click add button to add the transaction based on payment
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void addPaymentButton_Click(object sender, EventArgs e)
        {
            isGridAmountChanged = false;
            double actualAmount, enteredAmount;
            double amount;
            errorProvider.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());

            //CR# 368499 Proper error message will be displayed if the entered amount is 0 or greater than zero
            if (!double.TryParse(paymentAmountTextBox.Text, out amount) || amount < 0 ||
               !Regex.IsMatch(Convert.ToString(amount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.AmountFormat))
            {
                errorProvider.SetError(paymentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmountFormat));
                return;
            }
            else if(amount == 0)
            {
                errorProvider.SetError(paymentAmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
                return;
            }

            if (!Validator.Validate(paymentNoteTextBox.Text, ROIConstants.NameValidation))
            {
                errorProvider.SetError(paymentNoteTextBox, rm.GetString(ROIErrorCodes.InvalidName));
                return;
            }

            paymentAmountTextBox.Text = ROIViewUtility.FormattedAmount(amount);

            foreach (DataGridViewRow row in invoicePaymentGrid.Rows)
            {
                if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out actualAmount))
                {
                    actualAmount = 0;
                }
                enteredAmount = double.Parse(row.Cells[PaymentAmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (enteredAmount > 0)
                {
                    InvoiceChargeDetails invoiceChargeDetails = (InvoiceChargeDetails)row.DataBoundItem;
                    invoiceChargeDetails.BalanceDue = actualAmount - enteredAmount;
                    invoicePaymentGrid.Refresh();
                    invoiceTransactionsGrid.RowsAdded += rowAddedHandler;

                    RequestTransaction txn = new RequestTransaction();
                    txn.Id = Int64.Parse(row.Cells[InvoiceIdColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    txn.Amount = -enteredAmount;
                    txn.CreatedDate = DateTime.Now;
					// CR#359230
                    txn.Description = paymentMethodComboBox.Text + ROIConstants.Delimiter + paymentNoteTextBox.Text.Trim();
                    txn.TransactionType = TransactionType.Payment;                    
                    txn.IsDebit = false;
                    txn.AdjustmentPaymentType = isAutomaticTransaction ? AdjustmentPaymentType.Automatic : AdjustmentPaymentType.Manual;
                    txn.PaymentMode = txn.AdjustmentPaymentType.ToString();
                    txn.IsNewlyAdded = true;

                    NewTransactions.Add(txn);
                    invoiceTransactionsGrid.AddItem(txn);
                    invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
                }
            }
            paymentAmountTextBox.Text = string.Empty;
            paymentNoteTextBox.Text = string.Empty;
            paymentMethodComboBox.SelectedIndex = 0;
            ClearAmountPaidColumn(invoicePaymentGrid);
            SetTotalAdjustmentPaymentAmount();
            EnableAdjustmentPaymentGrid(false, invoicePaymentGrid);
            paymentAmountTextBox.Enabled = true;
            InvoiceTransactionsGrid.Focus();
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(Pane, null);
        }

        /// <summary>
        /// Occurs once the data is been bound
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
            foreach (DataGridViewRow row in invoiceTransactionsGrid.Rows)
            {
                ApplyCellStyle(row, true);
            }
        }

        /// <summary>
        /// Occurs when new row added
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowsAdded(object sender, DataGridViewRowsAddedEventArgs e)
        {
            ((BillingPaymentInfoUI)Pane.View).MarkDirty(sender, e);
            ApplyCellStyle(invoiceTransactionsGrid.Rows[e.RowIndex], true);
            invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
        }

        private void grid_Sorted(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in invoiceTransactionsGrid.Rows)
            {
                ApplyCellStyle(row, false);
            }
        }

        /// <summary>
        /// Apply cell style based on transaction
        /// </summary>
        /// <param name="row"></param>
        private void ApplyCellStyle(DataGridViewRow row, bool doUpdate)
        {
            RequestTransaction reqTxn = (RequestTransaction)row.DataBoundItem;
            DataGridViewCell cell = row.Cells[AmountColumn];
            if (!reqTxn.IsDebit)
            {
                cell.Style.ForeColor = Color.FromArgb(32, 125, 41);
            }
            if (doUpdate)
            {
                UpdateAdjustmentPaymentTotal(reqTxn);
            }
        }

        /// <summary>
        /// Update AdjustmentPayment total amount
        /// </summary>
        /// <param name="txn"></param>
        private void UpdateAdjustmentPaymentTotal(RequestTransaction txn)
        {
            if (txn.TransactionType == TransactionType.Payment)
            {
                paymentTotal += txn.Amount;
            }
            else
            {
                //adjustmentTotal = (txn.IsDebit) ? adjustmentTotal + txn.Amount : adjustmentTotal - txn.Amount;
				//CR#359276 and CR#359249 - Exclude automatic adjustments from calculation
                if (!txn.Description.Equals(ROIConstants.AutoCreditAdjustmentText) && !txn.Description.Equals(ROIConstants.AutoDebitAdjustmentText))
                {
                    adjustmentTotal += txn.Amount;
                }
            }

            UpdateAdjustmentPaymentTotalLabel();
            //((BillingPaymentInfoUI)Pane.View).MarkDirty(Pane, null);
            RequestEvents.OnReleaseCostChanged(Pane, null);
        }

        /// <summary>
        /// Update AdjustmentPayment total amount
        /// </summary>
        private void UpdateAdjustmentPaymentTotalLabel()
        {
            adjustmentPaymentValuelLabel.Text = ReleaseDetails.FormattedAmount(AdjustmentPaymentTotal);
            adjustmentPaymentValuelLabel.ForeColor = (AdjustmentPaymentTotal < 0) ? Color.FromArgb(32, 125, 41) : Color.Black;
        }

        /// <summary>
        /// Populates adjustment reasons and payment methods
        /// </summary>
        /// <param name="paymentMethods"></param>
        /// <param name="reasons"></param>
        public void PrePopulate(Collection<PaymentMethodDetails> paymentMethods, Collection<ReasonDetails> reasons)
        {
            PopulateAdjustmentReasons(reasons);
            PopulatePaymentMethods(paymentMethods);
        }

        /// <summary>
        /// Populates adjustment reasons in combo box
        /// </summary>
        /// <param name="adjustmentReasons"></param>
        private void PopulateAdjustmentReasons(Collection<ReasonDetails> adjustmentReasons)
        {
            ReasonDetails forSelect = new ReasonDetails();
            forSelect.Id = 0;
            forSelect.Name = "Please Select";
            adjustmentReasons.Insert(0, forSelect);

            reasonComboBox.DisplayMember = "Name";
            reasonComboBox.ValueMember = "Id";
            reasonComboBox.DataSource = adjustmentReasons;
        }

        /// <summary>
        /// Populates payment methods in combo box
        /// </summary>
        /// <param name="paymentMethods"></param>
        private void PopulatePaymentMethods(Collection<PaymentMethodDetails> paymentMethods)
        {
            bool hasPaymentMethods = (paymentMethods.Count > 0);
            if (hasPaymentMethods)
            {
                PaymentMethodDetails forSelect = new PaymentMethodDetails();
                forSelect.Id = 0;
                forSelect.Name = "Please Select";
                paymentMethods.Insert(0, forSelect);

                paymentMethodComboBox.DisplayMember = "Name";
                paymentMethodComboBox.ValueMember = "Id";
                paymentMethodComboBox.DataSource = paymentMethods;
            }
            paymentPanel.Visible = hasPaymentMethods;
            noPaymentPanel.Visible = !hasPaymentMethods;
        }

        private void paymentMethodComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PaymentMethodDetails paymentMethod = (PaymentMethodDetails)paymentMethodComboBox.SelectedItem;
            paymentNoteTextBox.Enabled = paymentMethod.IsDisplay;
            //CR# 368499 entered amount will not be cleared even though if the payment method changes
            //paymentAmountTextBox.Text = string.Empty;
            paymentNoteTextBox.Text = string.Empty;
        }

        /// <summary>
        /// Mark the data as dirty
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MarkDirty(object sender, EventArgs e)
        {
            if (invoiceAdjustmentGrid.Items == null)
                return;

            addPaymentButton.Enabled = (paymentMethodComboBox.SelectedIndex != 0 && paymentAmountTextBox.Text.Trim().Length > 0
                                           && invoicePaymentGrid.Items.Count > 0 && IsValidAmountInGrid());
            addAdjustmentButton.Enabled = (reasonComboBox.SelectedIndex != 0 &&
                                          ((creditRadioButton.Checked||debitRadioButton.Checked) ? adjustmentAmountTextBox.Text.Trim().Length > 0 : true)
                                           && reasonComboBox.Text.Trim().Length > 0 && invoiceAdjustmentGrid.Items.Count > 0
                                           && IsValidAmountInGrid());
        }

        /// <summary>
        /// Check the amount value entered in the adjustment/payment grid
        /// </summary>
        /// <returns></returns>
        private bool IsValidAmountInGrid()
        {
            if (adjustmentPaymentTabControl.SelectedTab.Name.Equals(PaymentTab))
            {
                foreach (DataGridViewRow row in invoicePaymentGrid.Rows)
                {
                    if (double.Parse(row.Cells[PaymentAmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture) > 0)
                    {
                        return true;
                    }
                }
                return false;
            }
            else
            {
                bool isValid = false;
                foreach (DataGridViewRow row in invoiceAdjustmentGrid.Rows)
                {
                    double amountPaid = double.Parse(row.Cells[AmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    //double amount;
                    //if(!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount))
                    //{
                    //    amount = 0;
                    //}                    
                    if (creditRadioButton.Checked && amountPaid > 0)
                    {
                        return true;
                    }
					// CR#359218
                    else if (debitRadioButton.Checked && amountPaid > 0)
                    {
                        if (amountPaid <= GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem))
                        {
                            isValid = true;
                        }
                        else
                        {
                            return false;
                        }
                    }                                      
                }
                return isValid;
            }
        }

        /// <summary>
        /// It will restrict user to enter other characters apart from Numerical, dot and backspace
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void amountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        ///  Occurs when the value entering into the adjustment amount textbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentAmountTextBox_TextChanged(object sender, EventArgs e)
        {
            double balanceAmount;
            if (!isAdjustmentGridAmountChanged && (creditRadioButton.Checked||debitRadioButton.Checked))
            {
                EnableAdjustmentPaymentGrid(adjustmentAmountTextBox.Enabled && !string.IsNullOrEmpty(adjustmentAmountTextBox.Text.Trim()) &&
                                            double.TryParse(adjustmentAmountTextBox.Text.Trim(), out balanceAmount) && Math.Round(balanceAmount, 2) > 0,
                                            invoiceAdjustmentGrid);
            }
        }

        /// <summary>
        /// Occurs when the value entering into the payment amount textbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void paymentAmountTextBox_TextChanged(object sender, EventArgs e)
        {
            double balanceAmount;
            if (!isGridAmountChanged)
            {
                EnableAdjustmentPaymentGrid(paymentAmountTextBox.Enabled && !string.IsNullOrEmpty(paymentAmountTextBox.Text.Trim()) &&
                                            double.TryParse(paymentAmountTextBox.Text.Trim(), out balanceAmount) && balanceAmount > 0,
                                            invoicePaymentGrid);
            }
        }

        /// <summary>
        /// Enable/Diable the grid based on the values entered in the payment amount textbox
        /// </summary>
        /// <param name="enable"></param>
        private void EnableAdjustmentPaymentGrid(bool enable, EIGDataGrid adjPayGrid)
        {
            if (hasRadioButtonChanged)
                return;
            string amountPaidColumn = adjPayGrid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;
            double amount;
            double amountPaid;
            if (adjPayGrid.Name.Equals("invoicePaymentGrid"))
            {
                foreach (DataGridViewRow row in adjPayGrid.Rows)
                {
                    if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount))
                    {
                        amount = 0;
                    }
                    if (Math.Round(amount, 2) >= 0)
                    {
                        row.Cells[amountPaidColumn].ReadOnly = enable;
                    }
                    else
                    {
                        row.Cells[amountPaidColumn].ReadOnly = true;
                    }
                }
            }
            else
            {
                foreach (DataGridViewRow row in adjPayGrid.Rows)
                {                    
                    if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount))
                    {
                        amount = 0;
                    }
                    if (!double.TryParse(row.Cells[amountPaidColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amountPaid))
                    {
                        amountPaid = 0;
                    }
                    if ((debitRadioButton.Checked && GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem) > 0 &&
                        Math.Round(amountPaid, 2) <= GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem)) ||
                        (creditRadioButton.Checked && Math.Round(amount, 2) >= 0))
                    {
                        row.Cells[AmountPaidColumn].ReadOnly = enable;
                    }
                    else
                    {
                        row.Cells[AmountPaidColumn].ReadOnly = true;
                    }
                }
            }
            adjPayGrid.Refresh();
        }

        /// <summary>
        /// Occurs when the amount entered in the cell 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtAmount_TextChanged(object sender, EventArgs e)
        {
            TextBox txtAmount = (TextBox)sender;
            double balanceAmount;
            if (adjustmentPaymentTabControl.SelectedTab.Name.Equals(PaymentTab))
            {
                if (!HasAmountInAdjustmentPaymentGrid(invoicePaymentGrid) && ((string.IsNullOrEmpty(txtAmount.Text.Trim())) ||
                    (!string.IsNullOrEmpty(txtAmount.Text.Trim()) && double.TryParse(txtAmount.Text.Trim(),
                    out balanceAmount) && balanceAmount == 0)))
                {
                    isGridAmountChanged = false;
                    paymentAmountTextBox.Enabled = true;
                    paymentAmountTextBox.Text = ZeroString;
                    EnableAdjustmentPaymentGrid(false, invoicePaymentGrid);
                }
                else if (!HasAmountInAdjustmentPaymentGrid(invoicePaymentGrid) && !string.IsNullOrEmpty(txtAmount.Text.Trim()) &&
                        double.TryParse(txtAmount.Text.Trim(), out balanceAmount) && balanceAmount > 0)
                {
                    isGridAmountChanged = true;
                    paymentAmountTextBox.Enabled = false;
                }
                invoicePaymentGrid.ClearSelection();
            }
        }

        private static bool HasAmountInAdjustmentPaymentGrid(EIGDataGrid adjPayGrid)
        {
            string amountPaidColumn = adjPayGrid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;
            foreach (DataGridViewRow row in adjPayGrid.Rows)
            {
                if ((row.Index != adjPayGrid.CurrentCell.RowIndex) &&
                    double.Parse(row.Cells[amountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture) > 0)
                {
                    return true;
                }
            }
            return false;
        }

        /// <summary>
        /// Occurs when the amount entered in the cell
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoicePaymentGrid_CurrentCellDirtyStateChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
            AmountTextBoxClass control = invoicePaymentGrid.EditingControl as AmountTextBoxClass;
            TextBox txtAmount = control.Controls[1] as TextBox;
            txtAmount.TextChanged += new EventHandler(txtAmount_TextChanged);
            txtAmount_TextChanged(txtAmount, e);
        }

        /// <summary>
        /// Occurs when the amount entered in the cell
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceAdjustmentGrid_CurrentCellDirtyStateChanged(object sender, EventArgs e)
        {
            if (creditRadioButton.Checked)
            {
                errorProvider.Clear();
                AmountTextBoxClass control = invoiceAdjustmentGrid.EditingControl as AmountTextBoxClass;
                TextBox txtAmount = control.Controls[1] as TextBox;
                txtAmount.TextChanged += new EventHandler(txtAdjustmentAmount_TextChanged);
                txtAdjustmentAmount_TextChanged(txtAmount, e);
            }
        }

        /// <summary>
        /// Occurs when the amount entered in the cell 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void txtAdjustmentAmount_TextChanged(object sender, EventArgs e)
        {
            TextBox txtAmount = (TextBox)sender;
            double balanceAmount;
            if (adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab))
            {
                if (!HasAmountInAdjustmentPaymentGrid(invoiceAdjustmentGrid) && ((string.IsNullOrEmpty(txtAmount.Text.Trim())) ||
                    (!string.IsNullOrEmpty(txtAmount.Text.Trim()) && double.TryParse(txtAmount.Text.Trim(),
                    out balanceAmount) && balanceAmount == 0)))
                {
                    isAdjustmentGridAmountChanged = false;
                    adjustmentAmountTextBox.Enabled = true;
                    adjustmentAmountTextBox.Text = ZeroString;
                    EnableAdjustmentPaymentGrid(false, invoiceAdjustmentGrid);
                }
                else if (!HasAmountInAdjustmentPaymentGrid(invoiceAdjustmentGrid) && !string.IsNullOrEmpty(txtAmount.Text.Trim()) &&
                        double.TryParse(txtAmount.Text.Trim(), out balanceAmount) && balanceAmount > 0)
                {
                    isAdjustmentGridAmountChanged = true;
                    adjustmentAmountTextBox.Enabled = false;
                }
                invoiceAdjustmentGrid.ClearSelection();
            }
        }

        /// <summary>
        /// Enable the amount textbox only while selecting the credit radio button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void creditDebitRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            if (invoiceAdjustmentGrid.EditingControl != null)
            {                
                AmountTextBoxClass amtTextBox = invoiceAdjustmentGrid.EditingControl as AmountTextBoxClass;                
                amtTextBox.AlertImage.Visible = false;
                invoiceAdjustmentGrid.CancelEdit();
            }

            //Exception occurs while makes credit and select the debit and perform sorting
            //Reset the grid items to prevent this sorting null reference exception
            invoiceAdjustmentGrid.SetItems(invoiceAdjustmentGrid.Items);

            hasRadioButtonChanged = true;
            adjustmentAmountTextBox.Visible = adjustmentAmountDollarLabel.Visible =
                                              adjustmentAmountLabel.Visible =
                                              creditRadioButton.Checked  ? true : false;
            string amountPaidColumn = invoiceAdjustmentGrid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;

            foreach (DataGridViewRow row in invoiceAdjustmentGrid.Rows)
            {
                row.DefaultCellStyle.SelectionBackColor = invoiceAdjustmentGrid.BackgroundColor;
                row.DefaultCellStyle.SelectionForeColor = invoiceAdjustmentGrid.ForeColor;
                double amount;
                double amountPaid;
                if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount))
                {
                    amount = 0;
                }
                if (!double.TryParse(row.Cells[amountPaidColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amountPaid))
                {
                    amountPaid = 0;
                }
                if ((debitRadioButton.Checked && GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem) == 0) ||
                    (creditRadioButton.Checked && Math.Round(amount, 2) < 0))
                {
                    row.Cells[AmountPaidColumn].ReadOnly = true;
                }
                else
                {
                    row.Cells[AmountPaidColumn].ReadOnly = false;
                }
            }
            invoiceAdjustmentGrid.Refresh();
            //If user enters an credit amount and change the debit radio button then clear all the entries
            //in credit amount textbox as well as in grid entries
            adjustmentAmountTextBox.Text = string.Empty;
            adjustmentAmountTextBox.Enabled = true;
            errorProvider.Clear();
            invoiceAdjustmentGrid.CellValueChanged -= new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
            ClearAmountPaidColumn(invoiceAdjustmentGrid);
            invoiceAdjustmentGrid.CellValueChanged += new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
            hasRadioButtonChanged = false;
            isAdjustmentGridAmountChanged = false;
        }

        /// <summary>
        /// Check whether the user enters valid amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentGrid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex != 3)
                return;
            isAutomaticTransaction = false;
            double totalAmount = 0;
            foreach (DataGridViewRow row in invoiceAdjustmentGrid.Rows)
            {
                double amountPaid = Math.Round(double.Parse(row.Cells[AmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);
                if (amountPaid == 0)
                {
                    invoiceAdjustmentGrid.CellValueChanged -= new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
                    row.Cells[AmountPaidColumn].Value = 0;
                    invoiceAdjustmentGrid.CellValueChanged += new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
                }
                if (!row.Cells[BalanceDueColumn].Value.ToString().Contains("("))
                {
                    invoiceAdjustmentGrid.CellValueChanged -= new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
                    row.Cells[AmountPaidColumn].Value = amountPaid;
                    invoiceAdjustmentGrid.CellValueChanged += new DataGridViewCellEventHandler(adjustmentGrid_CellValueChanged);
                    totalAmount += amountPaid;
                }
            }
            if (creditRadioButton.Checked || debitRadioButton.Checked)
            {
                adjustmentAmountTextBox.TextChanged -= new EventHandler(adjustmentAmountTextBox_TextChanged);
                adjustmentAmountTextBox.Text = ROIViewUtility.FormattedAmount(totalAmount);
                adjustmentAmountTextBox.TextChanged += new EventHandler(adjustmentAmountTextBox_TextChanged);
            }
            MarkDirty(sender, e);
        }

        /// <summary>
        /// Check whether the user enters valid amount
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void paymentGrid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            if (e.ColumnIndex != 3)
                return;
            isAutomaticTransaction = false;
            double totalAmount = 0;
            double amount;
            foreach (DataGridViewRow row in invoicePaymentGrid.Rows)
            {
                double amountPaid = Math.Round(double.Parse(row.Cells[PaymentAmountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);
                if (amountPaid == 0)
                {
                    invoicePaymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                    row.Cells[PaymentAmountPaidColumn].Value = 0;
                    invoicePaymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                }
                if (double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount) && Math.Round(amount, 2) >= 0)
                {
                    totalAmount += amountPaid;
                }
            }
            paymentAmountTextBox.TextChanged -= new EventHandler(paymentAmountTextBox_TextChanged);
            paymentAmountTextBox.Text = ROIViewUtility.FormattedAmount(totalAmount);
            paymentAmountTextBox.TextChanged += new EventHandler(paymentAmountTextBox_TextChanged);
            MarkDirty(sender, e);
        }

        /// <summary>
        /// Update the adjustment/payment amount textbox after data binding
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentPaymentGrid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            double totalAmount = 0;
            EIGDataGrid adjustmentPaymentGrid = ((EIGDataGrid)sender);
            string amountPaidColumn = adjustmentPaymentGrid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;
            double amountPaid;
            foreach (DataGridViewRow row in adjustmentPaymentGrid.Rows)
            {
                double amount;
                amountPaid = double.Parse(row.Cells[amountPaidColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount) && amount >= 0)
                {
                    totalAmount += amountPaid;
                }
            }
            if (totalAmount > 0)
                adjustmentAmountTextBox.Text = paymentAmountTextBox.Text = ROIViewUtility.FormattedAmount(totalAmount);
        }

        /// <summary>
        /// Clear the selection while leaving the adjustment grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentGrid_EnterLeave(object sender, EventArgs e)
        {
            EIGDataGrid adjustmentGrid = ((EIGDataGrid)sender);
            adjustmentGrid.ClearSelection();
            AmountTextBoxClass amtTextBox = adjustmentGrid.EditingControl as AmountTextBoxClass;
            double amount;
            if (amtTextBox != null && double.TryParse(amtTextBox.Controls[1].Text, out amount) && amount >= 0)
            {
                amtTextBox.AlertImage.Visible = false;
            }
        }

        /// <summary>
        /// Set the value for payment grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentPaymentTabControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab))
            {
                if (invoicePaymentGrid.EditingControl != null)
                {
                    AmountTextBoxClass amtTextBox = invoicePaymentGrid.EditingControl as AmountTextBoxClass;
                    amtTextBox.AlertImage.Visible = false;
                    invoicePaymentGrid.CancelEdit();
                }                
                invoiceAdjustmentGrid.SetItems(invoicePaymentGrid.Items);
                ClearAmountPaidColumn(invoiceAdjustmentGrid);                
                foreach (DataGridViewRow row in invoiceAdjustmentGrid.Rows)
                {
                    double amount;                    
                    if ((debitRadioButton.Checked && GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem) == 0) ||                         
                        (creditRadioButton.Checked &&
                        double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount) && Math.Round(amount, 2) < 0))
                    {
                        row.Cells[AmountPaidColumn].ReadOnly = true;
                    }
                    else
                    {
                        row.Cells[AmountPaidColumn].ReadOnly = false;
                    }

                }
                invoiceAdjustmentGrid.Refresh();
            }
            else
            {
                if (invoiceAdjustmentGrid.EditingControl != null)
                {                    
                    AmountTextBoxClass amtTextBox = invoiceAdjustmentGrid.EditingControl as AmountTextBoxClass;
                    amtTextBox.AlertImage.Visible = false;
                    invoiceAdjustmentGrid.CancelEdit();
                }
                invoicePaymentGrid.SetItems(invoiceAdjustmentGrid.Items);
                ClearAmountPaidColumn(invoicePaymentGrid);
                invoicePaymentGrid.Refresh();
            }
            foreach (DataGridViewRow row in invoicePaymentGrid.Rows)
            {
                row.DefaultCellStyle.SelectionBackColor = invoicePaymentGrid.BackgroundColor;
                row.DefaultCellStyle.SelectionForeColor = invoicePaymentGrid.ForeColor;
                double amount;
                row.Cells[PaymentAmountPaidColumn].ReadOnly = double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(),
                                                       NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount) && Math.Round(amount, 2) < 0 ? true : false;
            }
			errorProvider.Clear();
            adjustmentAmountTextBox.Enabled = paymentAmountTextBox.Enabled = true;
            adjustmentAmountTextBox.Text = string.Empty;
            paymentAmountTextBox.Text = string.Empty;
            paymentNoteTextBox.Text = string.Empty;
            reasonComboBox.SelectedIndex = 0;
            paymentMethodComboBox.SelectedIndex = 0;
            invoiceAdjustmentGrid.ClearSelection();
            invoicePaymentGrid.ClearSelection();
            isGridAmountChanged = false;
            isAdjustmentGridAmountChanged = false;
        }

        /// <summary>
        /// Disable/Enable the AmountPaid column while sorting
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void adjustmentPaymentGrid_Sorted(object sender, EventArgs e)
        {
            EIGDataGrid adjPayGrid = (EIGDataGrid)sender;
            string amountPaidColumn = adjPayGrid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;            
            foreach (DataGridViewRow row in adjPayGrid.Rows)
            {
                double amount, amountPaid;
                if (!double.TryParse(row.Cells[BalanceDueColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amount))
                {
                    amount = 0;
                }
                if (!double.TryParse(row.Cells[amountPaidColumn].Value.ToString(), NumberStyles.Currency, System.Threading.Thread.CurrentThread.CurrentUICulture, out amountPaid))
                {
                    amountPaid = 0;
                }
                if ((adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) && debitRadioButton.Checked &&
                    GetMaximumRevertAmount((InvoiceChargeDetails)row.DataBoundItem) == 0) ||
                    ((adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) && creditRadioButton.Checked && Math.Round(amount, 2) < 0)) ||
                    ((adjustmentPaymentTabControl.SelectedTab.Name.Equals(PaymentTab) && amount < 0)) ||
                    isAutomaticTransaction)
                {
                    row.Cells[amountPaidColumn].ReadOnly = true;
                }
                else
                {
                    row.Cells[amountPaidColumn].ReadOnly = false;
                }
            }
            adjPayGrid.Refresh();
            adjPayGrid.ClearSelection();
        }

        /// <summary>
        /// Clear the Amount Paid column
        /// </summary>
        /// <param name="grid"></param>
        private static void ClearAmountPaidColumn(EIGDataGrid grid)
        {
            string amountPaidColumn = grid.Name.Equals("invoiceAdjustmentGrid") ? AmountPaidColumn : PaymentAmountPaidColumn;
            foreach (DataGridViewRow row in grid.Rows)
            {
                row.Cells[amountPaidColumn].Value = "0.00";
            }
        }

        public void DisableAdjustmentPaymentPanel()
        {
            adjustmentGroupBox.Enabled = false;
            paymentGroupBox.Enabled = false;
        }

        /// <summary>
        /// Get the invoice charge details for updating it
        /// </summary>
        /// <returns></returns>
        public InvoiceChargeDetailsList RetrieveInvoiceChargeDetails(double balanceDue)
        {            
            InvoiceChargeDetailsList invoiceChargeDetailsList = new InvoiceChargeDetailsList();
            List<InvoiceChargeDetails> invoiceChargeDetails = new List<InvoiceChargeDetails>();
            List<RequestTransaction> requestTransactions = new List<RequestTransaction>();
            foreach (RequestTransaction requestTransaction in invoiceTransactionsGrid.Items)
            {
                requestTransactions.Add(requestTransaction);
            }
            foreach (InvoiceChargeDetails invoiceChargeDetail in
                adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid.Items : invoicePaymentGrid.Items)
            {
                List<RequestTransaction> requestTransactionList = requestTransactions.FindAll(
                    delegate(RequestTransaction txn) { return txn.Id == invoiceChargeDetail.Id; });
                foreach (RequestTransaction transaction in requestTransactionList)
                {
                    if (transaction.IsNewlyAdded)
                    {
                        invoiceChargeDetail.RequestTransactions = requestTransactionList;
                        break;
                    }
                }                
                invoiceChargeDetails.Add(invoiceChargeDetail);
            }
            invoiceChargeDetailsList.Id = invoiceRequestId;
            invoiceChargeDetailsList.InvoiceCharges = invoiceChargeDetails;
            invoiceChargeDetailsList.CurrentRequestBalanceDue = Math.Round(balanceDue,2);
            invoiceChargeDetailsList.PreviousRequestBalanceDue = Math.Round(InitialBalanceDue,2);
            return invoiceChargeDetailsList;
        }

        /// <summary>
        /// Retrieve the balance due of all invoices
        /// </summary>
        /// <returns></returns>
        public double RetrieveAllInvoiceBalanceDue()
        {
            double invoiceBalanceDue = 0;
            if (invoiceAdjustmentGrid.Items != null)
            {
                foreach (InvoiceChargeDetails invoiceChargeDetail in invoiceAdjustmentGrid.Items)
                {
                    invoiceBalanceDue += invoiceChargeDetail.BalanceDue;
                }
                return invoiceBalanceDue;
            }
            if (invoicePaymentGrid.Items != null)
            {
                invoiceBalanceDue = 0;
                foreach (InvoiceChargeDetails invoiceChargeDetail in invoicePaymentGrid.Items)
                {
                    invoiceBalanceDue += invoiceChargeDetail.BalanceDue;
                }
            }
            return invoiceBalanceDue;
        }

        /// <summary>
        /// Set the error provider when entering invalid amount in the grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void invoiceAdjustmentPaymentGrid_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            EIGDataGrid grid = (EIGDataGrid)sender;
            if (grid.EditingControl == null) return;
            AmountTextBoxClass amtTextBox = grid.EditingControl as AmountTextBoxClass;            
            ToolTip toolTip = new ToolTip();
            amtTextBox.AlertImage.Visible = true;
            if (grid.Name == "invoicePaymentGrid")
            {
                toolTip.SetToolTip(amtTextBox.AlertImage, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
            }
            else
            {
                toolTip.SetToolTip(amtTextBox.AlertImage, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
            }
        }

        /// <summary>
        /// If the current base charge is greater than zero, remove the overpayment amount from previous invoices
		/// If the current base charge is less than zero, remove the over debit amount from previous invoices
        /// </summary>
        /// <param name="baseCharge"></param>
        /// <returns></returns>
        public double AssignOverPaymentInvoiceAmount(double baseCharge)
        {
            EIGDataGrid adjPayGrid = adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid : invoicePaymentGrid;

            if (adjPayGrid.Items == null)
            {
                return 0;
            }
            ComparableCollection<InvoiceChargeDetails> invoiceList = new ComparableCollection<InvoiceChargeDetails>(adjPayGrid.Items);
            invoiceList.ApplySort(TypeDescriptor.GetProperties(typeof(InvoiceChargeDetails))["Id"], ListSortDirection.Ascending);

            if (baseCharge > 0 && CheckInvoiceAmounts(adjPayGrid))
            {
                foreach (InvoiceChargeDetails invoiceChargeDetail in invoiceList)
                {
                    if (invoiceChargeDetail.BalanceDue < 0)
                    {
                        if (-invoiceChargeDetail.BalanceDue >= baseCharge)
                        {
                            invoiceChargeDetail.BalanceDue += baseCharge;
                            MakeAutomaticAdjustment(invoiceChargeDetail, baseCharge, true);
                            EnableAdjustmentPaymentGrid(false, adjPayGrid);
                            adjPayGrid.Invalidate();
                            invoiceTransactionsGrid.Focus();
                            return 0;
                        }
                        else
                        {
                            baseCharge += invoiceChargeDetail.BalanceDue;
                            MakeAutomaticAdjustment(invoiceChargeDetail, -invoiceChargeDetail.BalanceDue, true);
                            invoiceChargeDetail.BalanceDue = 0;
                        }
                    }
                }                
            }
            else if (baseCharge < 0)
            {                
                foreach (InvoiceChargeDetails invoiceChargeDetail in invoiceList)
                {
                    if (invoiceChargeDetail.BalanceDue >= 0)
                    {                        
                        if (invoiceChargeDetail.BalanceDue >= -baseCharge)
                        {
                            invoiceChargeDetail.BalanceDue += baseCharge;
                            MakeAutomaticAdjustment(invoiceChargeDetail, baseCharge, false);
                            EnableAdjustmentPaymentGrid(false, adjPayGrid);
                            adjPayGrid.Invalidate();
                            invoiceTransactionsGrid.Focus();
                            return 0;
                        }
                        else
                        {
                            baseCharge += invoiceChargeDetail.BalanceDue;
                            if (invoiceChargeDetail.BalanceDue > 0)
                            {
                                MakeAutomaticAdjustment(invoiceChargeDetail, -invoiceChargeDetail.BalanceDue, false);
                            }
                            invoiceChargeDetail.BalanceDue = 0;
                        }
                    }
                }
                if (baseCharge != 0 && invoiceList.Count > 0)
                {
                    invoiceList[invoiceList.Count - 1].BalanceDue += baseCharge;
                    MakeAutomaticAdjustment(invoiceList[invoiceList.Count - 1], baseCharge, false);
                }
            }
            EnableAdjustmentPaymentGrid(false, adjPayGrid);
            adjPayGrid.Invalidate();
            invoiceTransactionsGrid.Focus();
            return baseCharge;
        }

        /// <summary>
        /// Perform automatic adjustment to the respective invoices
        /// </summary>
        /// <param name="invoiceChargeDetail"></param>
        /// <param name="baseCharge"></param>
        /// <param name="isDebit"></param>
        private void MakeAutomaticAdjustment(InvoiceChargeDetails invoiceChargeDetail, double baseCharge, bool isDebit)
        {
            invoiceTransactionsGrid.RowsAdded += rowAddedHandler;

            RequestTransaction txn = new RequestTransaction();
            txn.Id = invoiceChargeDetail.Id;
            txn.CreatedDate = DateTime.Now;
            txn.ReasonName = string.Empty;
            txn.Description = isDebit ? ROIConstants.AutoDebitAdjustmentText : ROIConstants.AutoCreditAdjustmentText;
            txn.TransactionType = TransactionType.AutoAdjustment;
            txn.IsDebit = isDebit;
            txn.Amount = Math.Round(baseCharge, 2);
            txn.AdjustmentPaymentType = AdjustmentPaymentType.Automatic;
            txn.IsNewlyAdded = true;
            

            NewTransactions.Add(txn);
            invoiceTransactionsGrid.AddItem(txn);
            invoiceTransactionsGrid.RowsAdded -= rowAddedHandler;
        }        

        /// <summary>
        /// CR#366854 - Ability to revert payments and adjustments when they are incorrectly posted in a request.
        /// </summary>
        public void SetTotalAdjustmentPaymentAmount()
        {
            EIGDataGrid adjPayGrid = adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid : invoicePaymentGrid;
            if (adjPayGrid.Items == null || invoiceTransactionsGrid.Items == null)
            {
                return;
            }            
            List<RequestTransaction> requestTransactions = new List<RequestTransaction>();
            foreach (RequestTransaction requestTransaction in invoiceTransactionsGrid.Items)
            {
                requestTransactions.Add(requestTransaction);
            }
            foreach (InvoiceChargeDetails invoiceChargeDetails in
                adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid.Items : invoicePaymentGrid.Items)
            {
                invoiceChargeDetails.TotalDebitAdjustment = 0;
                invoiceChargeDetails.TotalCreditAdjustment = 0;
                invoiceChargeDetails.TotalPayment = 0;
                List<RequestTransaction> requestTransactionList = requestTransactions.FindAll(
                    delegate(RequestTransaction txn) { return txn.Id == invoiceChargeDetails.Id; });
                foreach (RequestTransaction requestTransaction in requestTransactionList)
                {
                    if ((requestTransaction.TransactionType == TransactionType.Adjustment)
                        && requestTransaction.IsDebit)
                    {
                        invoiceChargeDetails.TotalDebitAdjustment += requestTransaction.Amount;
                    }
                    else if ((requestTransaction.TransactionType == TransactionType.Adjustment)
                            && !requestTransaction.IsDebit)
                    {
                        invoiceChargeDetails.TotalCreditAdjustment += requestTransaction.Amount;
                    }
                    else if (requestTransaction.TransactionType == TransactionType.Payment)
                    {
                        invoiceChargeDetails.TotalPayment += requestTransaction.Amount;
                    }
                }                
            }
        }

        /// <summary>
        /// CR#366854 - Ability to revert payments and adjustments when they are incorrectly posted in a request.
        /// </summary>
        /// <param name="invoiceChargeDetail"></param>
        /// <returns></returns>
        private double 
            GetMaximumRevertAmount(InvoiceChargeDetails invoiceChargeDetail)
        {
            return Math.Round((-invoiceChargeDetail.TotalCreditAdjustment -
                               invoiceChargeDetail.TotalPayment) -
                              invoiceChargeDetail.TotalDebitAdjustment, 2);
        }

        /// <summary>
        /// CR#367826 - Get the sum of all prebill balance due
        /// </summary>
        /// <returns></returns>
        public double RetrievePrebillBalanceDue()
        {
            double prebillAmount = 0;
            EIGDataGrid adjPayGrid = adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid : invoicePaymentGrid;
            if (adjPayGrid.Items != null)
            {
                foreach (InvoiceChargeDetails invoiceChargeDetail in adjPayGrid.Items)
                {
                    if (!invoiceChargeDetail.IsInvoiced)
                    {
                        prebillAmount += invoiceChargeDetail.BalanceDue;
                    }
                }
            }
            return prebillAmount;
        }

        /// <summary>
        /// CR#367826 - Set the automatic adjustment for prebill balance due greater than zero
        /// </summary>
        public void AssignPrebillBalanceDue()
        {
            EIGDataGrid adjPayGrid = adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid : invoicePaymentGrid;
            if (adjPayGrid.Items != null)
            {
                foreach (InvoiceChargeDetails invoiceChargeDetail in adjPayGrid.Items)
                {
                    if (!invoiceChargeDetail.IsInvoiced && invoiceChargeDetail.BalanceDue > 0)
                    {
                        MakeAutomaticAdjustment(invoiceChargeDetail, -invoiceChargeDetail.BalanceDue, false);
                        invoiceChargeDetail.BalanceDue = 0;
                    }
                }
            }
        }

        /// <summary>
        /// CR#367826 - Check whether the prebill has been created for the request
        /// </summary>
        /// <returns></returns>
        public bool HasPrebilledRequest()
        {
             EIGDataGrid adjPayGrid = adjustmentPaymentTabControl.SelectedTab.Name.Equals(AdjustmentTab) ? invoiceAdjustmentGrid : invoicePaymentGrid;
             if (adjPayGrid.Items != null)
             {
                 foreach (InvoiceChargeDetails invoiceChargeDetail in adjPayGrid.Items)
                 {
                     if (!invoiceChargeDetail.IsInvoiced)
                     {
                         return true;
                     }
                 }
             }
             return false;
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            adjustmentGroupBox.Enabled = IsAllowed(ROISecurityRights.ROIAdjustCharges);
            paymentGroupBox.Enabled    = IsAllowed(ROISecurityRights.ROIPostPayment);
        }

        #endregion

        #region Properties

        /// <summary>
        /// To fill the last column width.
        /// </summary>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in invoiceTransactionsGrid.Columns)
                {
                    sum += column.Width;
                }
                sum = invoiceTransactionsGrid.Width - sum;
                sum = invoiceTransactionsGrid.Columns[invoiceTransactionsGrid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }

        /// <summary>
        /// Adjustment total
        /// </summary>
        public double AdjustmentTotal
        {
            get { return adjustmentTotal; }
        }

        /// <summary>
        /// Payment total
        /// </summary>
        public double PaymentTotal
        {
            get { return paymentTotal; }
        }

        /// <summary>
        /// Adjustment - Payment Total
        /// </summary>
        public double AdjustmentPaymentTotal
        {
            get { return paymentTotal + adjustmentTotal; }
        }

        public Collection<RequestTransaction> NewTransactions
        {
            get
            {
                if (newTransactions == null)
                {
                    newTransactions = new Collection<RequestTransaction>();
                }

                return newTransactions;
            }
        }

        public EIGDataGrid InvoiceTransactionsGrid
        {
            get { return invoiceTransactionsGrid; }
        }

        // CR#364029
        public EIGDataGrid InvoiceAdjustmentGrid
        {
            get { return invoiceAdjustmentGrid; }
        }

        public double InitialBalanceDue
        {
            get { return initialBalanceDue; }
            set { initialBalanceDue = value; }
        }

        private bool noError;
        public bool NoError
        {
            get { return noError; }
        }
        #endregion
    }
}
