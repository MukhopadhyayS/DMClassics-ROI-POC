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
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;
using System.Text.RegularExpressions;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Requestors.View.FindRequestor;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class PaymentUI : ROIBaseUI
    {
        #region Fields

        private const string CheckBoxColumn = "checkBoxColumn";
        private const string InvoiceIdColumn = "invoiceId";
        private const string RequestIdColumn = "requestId";
        private const string DateColumn = "date";
        private const string ChargesColumn = "charges";
        private const string PayAdjColumn = "payAdj";
        private const string AppliedColumn = "applied";
        private const string BalanceColumn = "balance";
        private const string ApplyColumn = "apply";

        private long requestorId;
        private long paymentId;
        private bool isPaymentCreation;
        private double UnAppliedAmount;
        private double totalApplyAmount;
        private double totalPaymentAmount;
        private EventHandler requiredFieldValidateHandler;
        private double unAppliedAmountTotal;
        private ComparableCollection<RequestInvoiceDetail> removedInvoiceDetails;
        private double deniedInvoicesAppliedAmount;
        private double refundAmount;
        private RequestorDetails requestorDetails;
        private double totalUnAppliedAmount;
        private double paymentAmount;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public PaymentUI()
        {
            InitializeComponent();
            InitGrid();
            requiredFieldValidateHandler = new EventHandler(RequiredFieldValidation);
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public PaymentUI(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane): this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
            AmountTextBox.Focus();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, amountLabel);
            SetLabel(rm, methodLabel);
            SetLabel(rm, noteLabel);
            SetLabel(rm, dateLabel);
            SetLabel(rm, paymentAmountLabel);
            SetLabel(rm, appliedlabel);
            SetLabel(rm, availablelabel);
            SetLabel(rm, applyPaymentToLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, refundLabel);
            SetLabel(rm, requiredLabel);
        }

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            //US16834 - changes to Include requests in the pre-bill status on the payments popup.
            DataGridViewTextBoxColumn dgvInvoiceIdColumn = paymentGrid.AddTextBoxColumn(InvoiceIdColumn, "Invoice/Pre-bill #", "Id", 80);
            dgvInvoiceIdColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            DataGridViewTextBoxColumn requestIdColumn = paymentGrid.AddTextBoxColumn(RequestIdColumn, "Request ID", "RequestId", 80);
            requestIdColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            DataGridViewTextBoxColumn dgvCreatedDateColumn = paymentGrid.AddTextBoxColumn(DateColumn, "Date", "CreatedDate", 65);
            dgvCreatedDateColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern;
            dgvCreatedDateColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;

            DataGridViewTextBoxColumn dgvPaymentAmountColumn = paymentGrid.AddTextBoxColumn(ChargesColumn, "Charges", "Charges", 80);
            dgvPaymentAmountColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPaymentAmountColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvPaymentPayAdjColumn = paymentGrid.AddTextBoxColumn(PayAdjColumn, "Pay/Adj", "PayAdjTotal", 75);
            dgvPaymentPayAdjColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPaymentPayAdjColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvAppliedColumn = paymentGrid.AddCustomTextBoxImageColumn(AppliedColumn, "Applied", "AppliedAmount", 75);
            dgvAppliedColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvAppliedColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvPaymentBalanceColumn = paymentGrid.AddTextBoxColumn(BalanceColumn, "Balance", "Balance", 80);
            dgvPaymentBalanceColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPaymentBalanceColumn.DefaultCellStyle.Format = "C";

            CustomTextBoxColumn applyColumn = paymentGrid.AddCustomTextBoxColumn(ApplyColumn, "Apply", "ApplyAmount", 70);
            applyColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            applyColumn.CellTemplate.Style.Padding = new Padding(0, 1, 1, 2);
            applyColumn.DefaultCellStyle.Format = "C";            

            paymentGrid.Columns[InvoiceIdColumn].ReadOnly = true;
            paymentGrid.Columns[RequestIdColumn].ReadOnly = true;
            paymentGrid.Columns[DateColumn].ReadOnly = true;
            paymentGrid.Columns[ChargesColumn].ReadOnly = true;
            paymentGrid.Columns[PayAdjColumn].ReadOnly = true;
            paymentGrid.Columns[AppliedColumn].ReadOnly = true;
            paymentGrid.Columns[BalanceColumn].ReadOnly = true;
            paymentGrid.Columns[ApplyColumn].ReadOnly = false;
            paymentGrid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            paymentGrid.CellClick += new DataGridViewCellEventHandler(paymentGrid_CellClick);
        }

        private void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            int columCount = paymentGrid.ColumnCount;
            for (int colCount = 0; colCount < columCount; colCount++)
            {
                paymentGrid.Columns[6].DefaultCellStyle.Font = new System.Drawing.Font("Arial", 9F, FontStyle.Bold);
            }
        }


        private void grid_RowSelected(DataGridViewRow row)
        {
            row.Selected = false;
        }

        /// <summary>
        /// Gets the localized key for UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Gets the localized key for showing tooltip
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Prepopulates the payment methods and invoice grid.
        /// </summary>
        /// <param name="paymentMethods"></param>
        /// <param name="reqInvDetail"></param>
        /// <param name="requestorId"></param>
        /// <param name="isPaymentCreation"></param>
        public void PrePopulate(Collection<PaymentMethodDetails> paymentMethods, ComparableCollection<RequestInvoiceDetail> reqInvDetail, 
                                long requestorId, bool isPaymentCreation, RequestorDetails requestorDetails)
        {
            PopulatePaymentMethods(paymentMethods);
            PopulateOpenInvoices(reqInvDetail);
            paymentGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(grid_RowSelected);
            this.isPaymentCreation = isPaymentCreation;
            if (!isPaymentCreation)
            {
                EnableControls(false);
            }
            EnableEvents();
            AmountTextBox.Focus();
            this.requestorDetails = requestorDetails;
            this.requestorId = requestorId; 
        }

        /// <summary>
        /// Enable UI controls.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableControls(bool enable)
        {
            AmountTextBox.Enabled = enable;
            DateDateTimePicker.Enabled = enable;
            MethodCombobox.Enabled = enable;
            NoteTextBox.Enabled = enable;           
        }

        /// <summary>
        /// Sets the UI data.
        /// </summary>
        /// <param name="data"></param>
        /// <param name="paymentId"></param>
        public void SetData(object data, long paymentId, double appliedAmount)
        {
            if (data == null) return;
            if (data is RequestorAdjustmentsPaymentsDetail)
            {
                RequestorAdjustmentsPaymentsDetail requestorAdjustmentsPaymentsDetail = (RequestorAdjustmentsPaymentsDetail)data;
                double paymentAmount = requestorAdjustmentsPaymentsDetail.Amount;
                AmountTextBox.Text = paymentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (AmountTextBox.Text.IndexOf('(') != -1)
                {
                    AmountTextBox.Text = AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Trim().Length - 2);
                }
                if (requestorAdjustmentsPaymentsDetail.PaymentMethod != null)
                    MethodCombobox.SelectedValue = requestorAdjustmentsPaymentsDetail.PaymentMethod;
                if (requestorAdjustmentsPaymentsDetail.Description != null)
                    NoteTextBox.Text = requestorAdjustmentsPaymentsDetail.Description;
                //CR377295
                if(requestorAdjustmentsPaymentsDetail.ModifiedDate!=null)
                DateDateTimePicker.Value = Convert.ToDateTime(requestorAdjustmentsPaymentsDetail.ModifiedDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
                paymentAmountValueLabel.Text = AmountTextBox.Text;
                availableValuelabel.Text = requestorAdjustmentsPaymentsDetail.UnAppliedAmt.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                UnAppliedAmount = Math.Abs(requestorAdjustmentsPaymentsDetail.UnAppliedAmt);
                appliedValuelabel.Text = (paymentAmount - requestorAdjustmentsPaymentsDetail.UnAppliedAmt - requestorAdjustmentsPaymentsDetail.RefundAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                this.paymentId = requestorAdjustmentsPaymentsDetail.PaymentId;
                refundAmountValuelabel.Text = requestorAdjustmentsPaymentsDetail.RefundAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                this.refundAmount = requestorAdjustmentsPaymentsDetail.RefundAmount;
                this.totalUnAppliedAmount = UnAppliedAmount;
                this.paymentAmount = paymentAmount;
                
            }
            else
            {
                RequestInvoiceDetail requestInvoiceDetail = (RequestInvoiceDetail)data;
                double paymentAmount = Math.Abs(requestInvoiceDetail.Payments);
                AmountTextBox.Text = paymentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                if (AmountTextBox.Text.IndexOf('(') != -1)
                {
                    AmountTextBox.Text = AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Trim().Length - 2);
                }
                MethodCombobox.SelectedValue = requestInvoiceDetail.PaymentMethod;
                NoteTextBox.Text = requestInvoiceDetail.PaymentDescription;
                //CR#377295
                if (requestInvoiceDetail.ModifiedDate != null)
                DateDateTimePicker.Value = Convert.ToDateTime(requestInvoiceDetail.ModifiedDate, System.Threading.Thread.CurrentThread.CurrentUICulture);
                paymentAmountValueLabel.Text = AmountTextBox.Text;
                availableValuelabel.Text = Math.Abs(requestInvoiceDetail.Balance).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                double applyAmount = Math.Abs(requestInvoiceDetail.Payments) - Math.Abs(requestInvoiceDetail.Balance) - requestInvoiceDetail.RefundAmount;
                appliedValuelabel.Text = applyAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                UnAppliedAmount = Math.Abs(requestInvoiceDetail.Payments) - Math.Abs(requestInvoiceDetail.AppliedAmount);
                this.paymentId = paymentId;
                refundAmountValuelabel.Text = requestInvoiceDetail.RefundAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                this.refundAmount = requestInvoiceDetail.RefundAmount;
                this.totalUnAppliedAmount = Math.Abs(requestInvoiceDetail.Balance);
                this.paymentAmount = paymentAmount;
            }
            if (!isPaymentCreation)           
            {
                EnableControls(false);
            }
            this.deniedInvoicesAppliedAmount = appliedAmount;
            restrictedFacilityLabel.Visible = this.deniedInvoicesAppliedAmount > 0.0 ? true : false;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            restrictedFacilityLabel.Text = rm.GetString("RestrictedFacilityLabel");
            if (!this.isPaymentCreation)
            {
                removePaymentButton.Visible = this.totalUnAppliedAmount == this.paymentAmount && this.refundAmount == 0 ? true : false;
            }
            //CR377160
            //if (paymentGrid.Rows.Count > 0)
            //{
            //    saveButton.Enabled = true;
            //}
            //else
            //{
                //saveButton.Enabled = false;
            //}
        }

        /// <summary>
        /// Enable the events
        /// </summary>
        private void EnableEvents()
        {
            AmountTextBox.Leave += new EventHandler(AmountTextBox_Leave);
            paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
            paymentGrid.CellEndEdit += new DataGridViewCellEventHandler(paymentGrid_CellEndEdit);
            AmountTextBox.TextChanged += requiredFieldValidateHandler;
            MethodCombobox.SelectedIndexChanged += requiredFieldValidateHandler;
            NoteTextBox.TextChanged += requiredFieldValidateHandler;

        }

        private void paymentGrid_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {

            double applyAmount = 0.0;
            double appliedAmount = 0.0;
            
            double checkBalanceAmount = double.Parse(paymentGrid.Rows[e.RowIndex].Cells[BalanceColumn].Value.ToString());
            double checkApplyAmount = ROIViewUtility.RoundOffValue((double.Parse(paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value.ToString())), 2);
			removePaymentButton.Visible = false;
            if (checkApplyAmount > checkBalanceAmount)
            {
                paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value = 0;
                ShowInvalidDialog();
                saveButton.Enabled = false;
                paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);

            }

            foreach (DataGridViewRow row in paymentGrid.Rows)
            {
                double invoiceBalance = double.Parse(row.Cells[BalanceColumn].Value.ToString());
                double invoiceApplyAmount = ROIViewUtility.RoundOffValue((double.Parse(row.Cells[ApplyColumn].Value.ToString())), 2);

                applyAmount += invoiceApplyAmount;
                appliedAmount += ROIViewUtility.RoundOffValue((double.Parse(row.Cells[AppliedColumn].Value.ToString())), 2);
                
                saveButton.Enabled = invoiceApplyAmount == 0 || !(invoiceApplyAmount > invoiceBalance);
                if (!saveButton.Enabled)
                {
                    return;
                }
            }

            double balanceAmount;
            String paymentAmountString;
            
            if (AmountTextBox.Text.Contains("$"))
                paymentAmountString = !string.IsNullOrEmpty(AmountTextBox.Text.Trim()) ? AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Trim().Length - 1) : string.Empty;
            else
                paymentAmountString = AmountTextBox.Text;

            if (!double.TryParse(paymentAmountString, out balanceAmount) || balanceAmount < 0 ||
                !Regex.IsMatch(Convert.ToString(balanceAmount, System.Threading.Thread.CurrentThread.CurrentUICulture), RequestTransaction.ValidAmountFormat))
            {
                return;
            }

            double paymentAmount = Convert.ToDouble(paymentAmountString);
            if (paymentAmount < applyAmount)
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                string titleText = rm.GetString("TotalAppliedAmount.Title");
                string okButtonText = rm.GetString("TotalAppliedAmount.OkButton");
                string messageText = rm.GetString("TotalAppliedAmount.Message");

                ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
                paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                double enteredApplyAmount = Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value);
                applyAmount -= enteredApplyAmount;
                paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value = 0;
                paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);

                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                return;
                
            }
            double calculatedAppliedAmount = applyAmount + appliedAmount;
            double unAppliedAmount = paymentAmount - calculatedAppliedAmount - this.refundAmount;
            this.UnAppliedAmount = unAppliedAmount;
            if (!this.isPaymentCreation)
            {
                if (unAppliedAmount < 0)
                {
                    ShowErrorDialog();
                    paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                    this.UnAppliedAmount = this.UnAppliedAmount + ROIViewUtility.RoundOffValue((Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value.ToString())), 2);
                    paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value = 0;
                    saveButton.Enabled = true;
                    paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                    return;
                }
            }
           
            appliedValuelabel.Text = (calculatedAppliedAmount).ToString("C",System.Threading.Thread.CurrentThread.CurrentUICulture);            
            availableValuelabel.Text = unAppliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (calculatedAppliedAmount == 0)
            {
                if (!this.isPaymentCreation)
                {
                    removePaymentButton.Visible = this.totalUnAppliedAmount == this.paymentAmount && this.refundAmount == 0 ? true : false;
                }
            }
            
        }


        /// <summary>
        /// Disable the events
        /// </summary>
        private void DisableEvents()
        {
            AmountTextBox.Leave -= new EventHandler(AmountTextBox_Leave);
            paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);

            AmountTextBox.TextChanged -= requiredFieldValidateHandler;
            MethodCombobox.SelectedIndexChanged -= requiredFieldValidateHandler;
            NoteTextBox.TextChanged -= requiredFieldValidateHandler;
        }

        private void RequiredFieldValidation(object sender, EventArgs e)
        {
            //saveButton.Enabled = ValidateRequiredFields();
        }

        /// <summary>
        /// Populate the payment methods.
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

                MethodCombobox.DisplayMember = "Name";
                MethodCombobox.ValueMember = "Name";
                MethodCombobox.DataSource = paymentMethods;
            }
         }

        /// <summary>
        /// Populate open invoices.
        /// </summary>
        /// <param name="reqInvDetail"></param>
        private void PopulateOpenInvoices(ComparableCollection<RequestInvoiceDetail> reqInvDetail)
        {
            reqInvDetail.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            reqInvDetail.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetails = new ComparableCollection<RequestInvoiceDetail>();
            removedInvoiceDetails = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail requestInvoiceDetail in reqInvDetail)
            {
                //removed the invoices when the user does not have facility permission
                if (!requestInvoiceDetail.HasMaskedRequestorFacility && !requestInvoiceDetail.HasBlockedRequestorFacility)
                {
                    if(requestInvoiceDetail.Balance > 0)
                    { requestInvoiceDetails.Add(requestInvoiceDetail); }
                    
                }
                else
                {
                    removedInvoiceDetails.Add(requestInvoiceDetail);
                }
            }
            paymentGrid.SetItems(requestInvoiceDetails);
            paymentGrid.ClearSelection();                        
        }

        /// <summary>
        /// Occurs when user clicks on the Save button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void saveButton_Click(object sender, EventArgs e)
        {
            try
            {
                if (paymentGrid.Items.Count > 0)
                    paymentGrid.Rows[0].Cells[0].Selected = true;

                if (!paymentGrid.EndEdit())
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                    return;
                }
                errorProvider.Clear();
                if (this.isPaymentCreation)
                {
                    if (string.IsNullOrEmpty(AmountTextBox.Text.Trim()))
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        errorProvider.SetError(AmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
                        ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        return;
                    }

                    if (!ValidatePaymentAmount())
                    {
                        ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        return;
                    }

                    if (MethodCombobox.SelectedIndex <= 0)
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        errorProvider.SetError(MethodCombobox, rm.GetString(ROIErrorCodes.InvalidPaymentMethod));
                        ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        return;
                    }

                    if (string.IsNullOrEmpty(NoteTextBox.Text.Trim()))
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        errorProvider.SetError(NoteTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentNote));
                        ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        return;
                    }

                    if (!ValidateDateField())
                    {
                        ((Form)(this.Parent)).DialogResult = DialogResult.None;
                        return;
                    }
                }
                if (AmountTextBox.Text.Trim() != string.Empty)
                {
                    if (paymentGrid.Items != null)
                    {
                        RequestorInvoiceList requestorInvoiceList = new RequestorInvoiceList();
                        if (AmountTextBox.Text.Contains("$"))
                        {
                            requestorInvoiceList.PaymentAmount = ROIViewUtility.RoundOffValue(Convert.ToDouble(AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);
                        }
                        else
                        {
                            requestorInvoiceList.PaymentAmount = ROIViewUtility.RoundOffValue(Convert.ToDouble(AmountTextBox.Text.Trim(), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);
                        }
                        //CR# 380580
                        double totalAppliedAmount = 0;
                        totalApplyAmount = 0;
                        foreach (RequestInvoiceDetail requestInvoiceDetail in paymentGrid.Items)
                        {
                            double applyAmount = ROIViewUtility.RoundOffValue(requestInvoiceDetail.ApplyAmount,2);
                            double balanceAmount = ROIViewUtility.RoundOffValue(requestInvoiceDetail.Balance,2);
                            if (applyAmount > balanceAmount)
                            {
                                ShowInvalidDialog();
                                requestInvoiceDetail.ApplyAmount = 0;
                                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                                return;
                            }
                            double unAppliedAmount = ROIViewUtility.RoundOffValue(Convert.ToDouble(availableValuelabel.Text.Trim().Substring(1, availableValuelabel.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);

                            totalApplyAmount += applyAmount;
                            totalAppliedAmount += requestInvoiceDetail.AppliedAmount;
                            if (requestorInvoiceList.PaymentAmount < (totalAppliedAmount + totalApplyAmount))
                            {
                                ShowErrorDialog();
                                requestInvoiceDetail.ApplyAmount = 0;
                                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                                return;
                            }
                        }
                        //requestorInvoiceList.PaymentAmount = requestorInvoiceList.PaymentAmount + this.deniedInvoicesAppliedAmount;
                        totalPaymentAmount = requestorInvoiceList.PaymentAmount;
                        requestorInvoiceList.PaymentDate = DateDateTimePicker.Value;
                        requestorInvoiceList.PaymentMode = Convert.ToString(MethodCombobox.SelectedValue, System.Threading.Thread.CurrentThread.CurrentUICulture);
                        requestorInvoiceList.Description = NoteTextBox.Text.Trim();
                        requestorInvoiceList.RequestorId = this.requestorDetails.Id;
                        requestorInvoiceList.PaymentId = this.paymentId;

                        List<RequestorInvoiceDetails> requestorInvoices = new List<RequestorInvoiceDetails>();
                        int count = 0;
                        totalAppliedAmount = 0.0;
                        totalApplyAmount = 0.0;
                        foreach (RequestInvoiceDetail requestInvoiceDetail in paymentGrid.Items)
                        {
                            RequestorInvoiceDetails requestorInvoiceDetails = new RequestorInvoiceDetails();
                            double applyAmount = ROIViewUtility.RoundOffValue((double.Parse(paymentGrid.Rows[count].Cells[ApplyColumn].Value.ToString())), 2);
                            double appliedAmount = ROIViewUtility.RoundOffValue((double.Parse(paymentGrid.Rows[count].Cells[AppliedColumn].Value.ToString())), 2);
                            double appliedCopyAmount = ROIViewUtility.RoundOffValue((requestInvoiceDetail.AppliedAmountCopy), 2);
                            requestorInvoiceDetails.RequestId = requestInvoiceDetail.RequestId;
                            requestorInvoiceDetails.Facility = requestInvoiceDetail.Facility;
                            requestorInvoiceList.RequestorName = this.requestorDetails.Name;
                            requestorInvoiceList.RequestorType = this.requestorDetails.TypeName;
                            count++;
                            if ((applyAmount <= 0) && (appliedAmount <= 0) && (appliedAmount == appliedCopyAmount)) continue;
                            double previousPaymentAmount = Math.Abs(requestInvoiceDetail.Payments);
                            //to calcalucate the total payment amount for the invoice
                            if(requestInvoiceDetail.InvoiceType == "Prebill")
                            { requestorInvoiceDetails.IsPrebillPayment = true; }                            
                            requestorInvoiceDetails.AmountPaid = ROIViewUtility.RoundOffValue((requestInvoiceDetail.Adjustments + previousPaymentAmount + applyAmount - (appliedCopyAmount - appliedAmount)), 2);
                            requestorInvoiceDetails.PaymentTotal = ROIViewUtility.RoundOffValue((previousPaymentAmount + applyAmount - (appliedCopyAmount - appliedAmount)), 2);
                            requestorInvoiceDetails.InvoiceId = requestInvoiceDetail.Id;
                            requestorInvoiceDetails.BaseCharge = requestorInvoiceDetails.BaseCharge;
                            requestorInvoiceDetails.Balance = ROIViewUtility.RoundOffValue((requestInvoiceDetail.Balance - applyAmount), 2);
                            requestorInvoiceDetails.ApplyAmount = ROIViewUtility.RoundOffValue((appliedAmount + applyAmount), 2);
                            requestorInvoiceDetails.CurrentAppliedAmount = ROIViewUtility.RoundOffValue(applyAmount, 2);
                            totalAppliedAmount += requestorInvoiceDetails.ApplyAmount;
                            requestorInvoiceList.RequestorInvoices.Add(requestorInvoiceDetails);

                            totalApplyAmount += applyAmount;

                        }
                        //while saving the invoice details added the invoices which is removed for not having the user facility permission
                        //Reason for sending removed invoices is, in server, they removed the payment mapping table
                        if (removedInvoiceDetails.Count > 0)
                        {
                            foreach (RequestInvoiceDetail removedInvoiceDetail in removedInvoiceDetails)
                            {
                                if (removedInvoiceDetail.ApplyAmount > 0 || removedInvoiceDetail.AppliedAmount > 0)
                                {
                                    RequestorInvoiceDetails requestorInvoiceDetails = new RequestorInvoiceDetails();
                                    requestorInvoiceDetails.AmountPaid = removedInvoiceDetail.Adjustments + removedInvoiceDetail.Payments;
                                    requestorInvoiceDetails.PaymentTotal = removedInvoiceDetail.Payments;
                                    requestorInvoiceDetails.ApplyAmount = removedInvoiceDetail.AppliedAmount;
                                    requestorInvoiceDetails.InvoiceId = removedInvoiceDetail.Id;
                                    //CR# 380582
                                    requestorInvoiceDetails.RequestId = removedInvoiceDetail.RequestId;
                                    requestorInvoiceDetails.Balance = removedInvoiceDetail.Balance;
                                    requestorInvoiceList.RequestorInvoices.Add(requestorInvoiceDetails);
                                }
                            }
                        }
                        if (this.isPaymentCreation)
                        {
                            requestorInvoiceList.UnAppliedAmount = ROIViewUtility.RoundOffValue((requestorInvoiceList.PaymentAmount - totalAppliedAmount), 2);
                            if (totalAppliedAmount > requestorInvoiceList.PaymentAmount)
                            {
                                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                                string titleText = rm.GetString("TotalAppliedAmount.Title");
                                string okButtonText = rm.GetString("TotalAppliedAmount.OkButton");
                                string messageText = rm.GetString("TotalAppliedAmount.Message");

                                ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
                                ((Form)(this.Parent)).DialogResult = DialogResult.None;
                                return;
                            }
                            if (requestorInvoiceList.RequestorName== null)
                            {
                                requestorInvoiceList.RequestorName = requestorDetails.FullName;
                            }
                            RequestorController.Instance.CreateInvoicePayment(requestorInvoiceList);
                        }
                        else
                        {
                            //shows the error dialog if total applied amount exceed than unapplied amount
                            if (this.UnAppliedAmount < 0)
                            {
                                ShowErrorDialog();
                                return;
                            }
                            //requestorInvoiceList.UnAppliedAmount = previousUnAppliedAmount - totalAppliedAmount;
                            requestorInvoiceList.UnAppliedAmount = ROIViewUtility.RoundOffValue((requestorInvoiceList.PaymentAmount - totalAppliedAmount - this.deniedInvoicesAppliedAmount - this.refundAmount), 2);
                            if (requestorInvoiceList.RequestorInvoices.Count > 0)
                            {
                                RequestorController.Instance.UpdateInvoicePayment(requestorInvoiceList);
                            }
                        }
                        unAppliedAmountTotal = requestorInvoiceList.UnAppliedAmount;
                    }
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void ShowErrorDialog()
        {           
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("AppliedAmount.Title");
            string okButtonText = rm.GetString("AppliedAmount.OkButton");
            string messageText = rm.GetString("AppliedAmount.Message");

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
            ((Form)(this.Parent)).DialogResult = DialogResult.None;
        }
        
        /// <summary>
        /// Occurs when user leave the amount textbox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AmountTextBox_Leave(object sender, EventArgs e)
        {   
            string amount = AmountTextBox.Text.Trim();
            if (amount != string.Empty)
            {
                if (!ValidatePaymentAmount()) return;

                double paymentAmount = ROIViewUtility.RoundOffValue((Convert.ToDouble(AmountTextBox.Text.Trim())), 2);
                paymentAmountValueLabel.Text = paymentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                int count = 0;
                double totalAppliedAmount = 0.0;
                SplitInvoiceAmount(paymentAmount);
                paymentGrid.RefreshEdit();
                foreach (RequestInvoiceDetail requestInvoiceDetail in paymentGrid.Items)
                {
                    double invoiceAppliedAmount = double.Parse(paymentGrid.Rows[count].Cells[ApplyColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                    if (invoiceAppliedAmount <= 0) continue;
                    count++;
                    totalAppliedAmount += invoiceAppliedAmount;
                }
                appliedValuelabel.Text = totalAppliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                double unAppliedAmount = paymentAmount - totalAppliedAmount;
                availableValuelabel.Text = unAppliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                AmountTextBox.Text = paymentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                double refund = 0.0;
                refundAmountValuelabel.Text = refund.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            }
        }
        
        //CR377173
        private bool ValidatePaymentAmount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            double balanceAmount = 0.0;
            string amount = AmountTextBox.Text.Trim();
            if (amount.Contains("$"))
            {
                AmountTextBox.Text = Convert.ToString(amount.Substring(1, AmountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (!double.TryParse(AmountTextBox.Text.Trim(), out balanceAmount) || balanceAmount < 0 ||
                     !Regex.IsMatch(string.Format("{0:0.00}", balanceAmount), RequestTransaction.ValidAmountFormat))
            {
                errorProvider.SetError(AmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmountFormat));
                //saveButton.Enabled = false;
                return false;
            }
            if (balanceAmount == 0)
            {
                errorProvider.SetError(AmountTextBox, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
                //saveButton.Enabled = false;
                return false;
            }
            errorProvider.Clear();         
            return true;
        }

        /// <summary>
        /// Split the payment amount to multiple open invoices.
        /// </summary>
        /// <param name="balanceAmount"></param>
        private void SplitInvoiceAmount(double balanceAmount)
        {
            ComparableCollection<RequestInvoiceDetail> invoiceList = new ComparableCollection<RequestInvoiceDetail>(paymentGrid.Items);
            invoiceList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            for (int count = 0; count < invoiceList.Count; count++)
            {
                // the iteration can be stopped if the amount is zero
                if (balanceAmount == 0)
                {
                    invoiceList[count].ApplyAmount = 0;
                }

                if (invoiceList[count].Balance > 0)
                {
                    invoiceList[count].ApplyAmount = (invoiceList[count].Balance <= balanceAmount) ?
                                                    invoiceList[count].Balance : balanceAmount;
                    balanceAmount = (invoiceList[count].Balance <= balanceAmount) ?
                                    balanceAmount - invoiceList[count].Balance : 0;
                }
            }
            paymentGrid.Refresh();
        }

        /// <summary>
        /// Occurs when the grid cell value changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void paymentGrid_CellValueChanged(object sender, DataGridViewCellEventArgs e)
        {
            AmountTextBoxClass amtTextBox = paymentGrid.EditingControl as AmountTextBoxClass;
            amtTextBox.AlertImage.Visible = false;
            paymentGrid_CellEndEdit(sender, e);
        }

        private bool ValidateRequiredFields()
        {
            return !((AmountTextBox.Text.Trim().Length == 0) || (MethodCombobox.SelectedIndex == 0) || (NoteTextBox.Text.Trim().Length == 0));
        }

        private void ShowInvalidDialog()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("AppliedAmount.Title");
            string okButtonText = rm.GetString("AppliedAmount.OkButton");
            string messageText = rm.GetString("BalanceAmount.Message");

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
            ((Form)(this.Parent)).DialogResult = DialogResult.None;
        }

        /// <summary>
        /// Occurs when the value of a cell changes.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void paymentGrid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (!this.isPaymentCreation)
            {
                if (!(e.RowIndex < 0 || e.ColumnIndex != paymentGrid.Columns[paymentGrid.Columns.Count - 3].Index))
                {
                    double checkAppliedAmount = Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells["Applied"].Value);
                    if (checkAppliedAmount == 0.0)
                    {
                        return;
                    }
                    removePaymentButton.Visible = false;
                    double applyAmount = 0.0;
                    double unAppliedAmount = 0.0;
                    String PaymentAmount = string.Empty;

                    bool saveButtonEnabled = true;

                    double appliedAmountValue = Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells["Applied"].Value);
                    double balanceAmount = Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells["Balance"].Value);
                    double payAdjTotal = Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells[PayAdjColumn].Value);

                    paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);

                    paymentGrid.Rows[e.RowIndex].Cells["Applied"].Value = 0;
                    paymentGrid.Rows[e.RowIndex].Cells["Balance"].Value = balanceAmount + appliedAmountValue;
                    paymentGrid.Rows[e.RowIndex].Cells[PayAdjColumn].Value = payAdjTotal - appliedAmountValue;

                    //Calculates the unapplied amount from the grid                   
                    foreach (DataGridViewRow row in paymentGrid.Rows)
                    {
                        applyAmount += double.Parse(row.Cells[ApplyColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                        double checkBalanceAmount = double.Parse(row.Cells[BalanceColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                        double checkApplyAmount = double.Parse(row.Cells[ApplyColumn].Value.ToString(), System.Threading.Thread.CurrentThread.CurrentUICulture);
                        if (checkApplyAmount > checkBalanceAmount)
                        {
                            saveButtonEnabled = false;
                        }
                    }

                    if (!availableValuelabel.Text.Trim().Contains("("))
                    {
                        //Adds the unapplied amount textbox value + unapplied amount value calculated from the grid.                    
                        appliedAmountValue += Convert.ToDouble(availableValuelabel.Text.Trim().Substring(1, availableValuelabel.Text.Trim().Length - 1));
                    }
                    unAppliedAmount += appliedAmountValue;

                    PaymentAmount = !string.IsNullOrEmpty(AmountTextBox.Text.Trim()) ? AmountTextBox.Text.Trim().Substring(1, AmountTextBox.Text.Trim().Length - 1) : string.Empty;

                    double paymentAmount = Convert.ToDouble(PaymentAmount);
                    double appliedAmount = paymentAmount - unAppliedAmount;

                    unAppliedAmount = paymentAmount - appliedAmount;
                    this.UnAppliedAmount = unAppliedAmount;

                    if (unAppliedAmount < 0)
                    {
                        ShowErrorDialog();
                        paymentGrid.CellValueChanged -= new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                        this.UnAppliedAmount = this.UnAppliedAmount + Convert.ToDouble(paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value.ToString());
                        paymentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value = 0;
                        saveButton.Enabled = true;                            ;
                        paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                        return;
                    }
                    appliedValuelabel.Text = (appliedAmount - this.refundAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    availableValuelabel.Text = unAppliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);

                    paymentGrid.CellValueChanged += new DataGridViewCellEventHandler(paymentGrid_CellValueChanged);
                    saveButton.Enabled = saveButtonEnabled;
                }
            }
        }
      
        private void paymentGrid_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            EIGDataGrid grid = (EIGDataGrid)sender;
            if (grid.EditingControl == null) return;
            AmountTextBoxClass amtTextBox = grid.EditingControl as AmountTextBoxClass;
            ToolTip toolTip = new ToolTip();
            amtTextBox.AlertImage.Visible = true;
            toolTip.SetToolTip(amtTextBox.AlertImage, rm.GetString(ROIErrorCodes.InvalidPaymentAmount));
            //CR# 380999
            if (cancelButton.Focused)
            {
                ((Form)this.Parent).Close();
            }
        }

        /// <summary>
        /// Occurs when user enter any text in the amount textbox.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AmountTextBox_KeyPress_1(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        /// <summary>
        /// Ocurrs when user clicks remove payment button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void removePaymentButton_Click(object sender, EventArgs e)
        {
            RequestorController.Instance.DeleteRequestorPayment(paymentId, requestorDetails.Name);
            RequestorCache.RemoveKey(requestorDetails.Id);
            ((Form)(this.Parent)).DialogResult = DialogResult.OK;
        }

        /// <summary>
        /// Method to validate the Payment Date Field
        /// </summary>
        /// <returns></returns>
        private bool ValidateDateField()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            if (DateDateTimePicker.Value > DateTime.Now)
            {
                errorProvider.SetError(DateDateTimePicker, rm.GetString(ROIErrorCodes.InvalidPaymentDate));
                //saveButton.Enabled = false;
                return false;
            }
            return true;
        }

        /// <summary>
        /// Occurs when the user changed the Payment Date
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DateDateTimePicker_ValueChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
            //saveButton.Enabled = ValidateRequiredFields();
        }

        #endregion

        #region Properties

        public double TotalApplyAmount
        {
            get { return totalApplyAmount; }
        }

        public double TotalPaymentAmount
        {
            get { return totalPaymentAmount; }
        }

        public double UnAppliedAmountTotal
        {
            get { return unAppliedAmountTotal; }
        }

        #endregion
    }  
}
