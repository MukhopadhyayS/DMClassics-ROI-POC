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
using System.Collections.ObjectModel;
using System.Collections;
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;
using System.Text.RegularExpressions;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Admin.Model;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{

    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class AdjustmentsUI : ROIBaseUI
    {
        #region Fields

        private const string CheckBoxColumn = "checkBoxColumn";
        private const string InvoiceIdColumn = "invoiceId";
        private const string RequestIdColumn = "requestId";
        private const string DateColumn = "date";
        private const string ChargesColumn = "charges";
        private const string BalanceColumn = "balance";
        private const string ApplyColumn = "apply";
        private const string PayAdjColumn = "Pay/Adj";
        private const string AppliedColumn = "Applied";
        private EIGCheckedColumnHeader dgvColumnHeader;
        private double AppliedInvoiceAmt = 0;
        private double salesTax = 0;
        private double salesTaxPerFacility;
        private double adjAmount;
        private double appliedValue = 0;
        private double availableValue = 0;
        private double TotalInvBalance = 0;
        private double TotalOrigInvBalance = 0;
        private long requestorId;
        private bool isAdjRemoved = false;
        private bool isAdjCreation=false;
        private double totalApplyAmount = 0.0;
        double appliedValueCpy = 0;
        private double unAppliedAdjustmentAmount = 0.0;
        double availableCpy = 0;
        double totalApplyAmtCpy = 0;
        bool isApplyEdit = false;
        bool isEdit=false;
        private long adjustmentId=0;
        bool bAdjustmentAppliedToInvoice = false;
        double totalApplyAmt = 0;
        private double deniedInvoicesAppliedAmount;
        int feeCount = 0;
        private RequestorDetails requestorDetails;

        Collection<RequestInvoiceDetail> reInvList = new Collection<RequestInvoiceDetail>();
        AdjustmentInfoDetail currentAdjInfo = new AdjustmentInfoDetail();
        AccountManagementUI accountManagementUI = new AccountManagementUI();
        private double totalAppliedAmount = 0;
        private double unAppliedAdjAmount;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public AdjustmentsUI()
        {
            InitializeComponent();
            this.adjustmentGrid.CellLeave += new DataGridViewCellEventHandler(adjustmentGrid_CellLeave);
            EnableEvents();
            InitGrid();
            typeComboBox.Focus();
        }

        void adjustmentGrid_CellLeave(object sender, DataGridViewCellEventArgs e)
        {
            adjustmentGrid.EndEdit();
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public AdjustmentsUI(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane)
            : this()
        {
            SetPane(pane);
            SetExecutionContext(pane.Context);
            Localize();
        }

         

        #endregion

        #region Methods

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, requiredLabel);
            SetLabel(rm, reasonLabel);
            SetLabel(rm, reasonLabel);
            SetLabel(rm, adjustmentAmountLabel);
            SetLabel(rm, appliedLabel);
            SetLabel(rm, availableLabel);
            SetLabel(rm, applyAdjustmentToLabel);
            SetLabel(rm, saveButton);
            SetLabel(rm, cancelButton);
            SetLabel(rm, applyButton);
            SetLabel(rm, typeLabel);
            SetLabel(rm, amountLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, reasonComboBox);
            SetTooltip(rm, toolTip, textBox1);
            SetTooltip(rm, toolTip, dateTimePicker1);
            SetTooltip(rm, toolTip, applyButton);
            SetTooltip(rm, toolTip, saveButton);
            SetTooltip(rm, toolTip, cancelButton);

        }

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            dgvColumnHeader = new EIGCheckedColumnHeader();
            adjustmentGrid.AddTextBoxColumn(InvoiceIdColumn, "Invoice/PreBill #", "Id", 80);
			//CR-377441 fix
            adjustmentGrid.AddTextBoxColumn(RequestIdColumn, "Request ID", "RequestId", 80);
            DataGridViewTextBoxColumn dgvCreatedDateColumn = adjustmentGrid.AddTextBoxColumn(DateColumn, "Date", "CreatedDate", 65);
            dgvCreatedDateColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern;

            DataGridViewTextBoxColumn dgvPaymentAmountColumn = adjustmentGrid.AddTextBoxColumn(ChargesColumn, "Charges", "Charges", 80);
            dgvPaymentAmountColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPaymentAmountColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvPayAdjColumn = adjustmentGrid.AddTextBoxColumn(PayAdjColumn, "Pay/Adj", "PayAdjTotal", 75);
            dgvPayAdjColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPayAdjColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvAppliedColumn = adjustmentGrid.AddCustomTextBoxImageColumn(AppliedColumn, "Applied", "AppliedAmount", 75);
            dgvAppliedColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleLeft;
            dgvAppliedColumn.DefaultCellStyle.Format = "C";

            DataGridViewTextBoxColumn dgvPaymentBalanceColumn = adjustmentGrid.AddTextBoxColumn(BalanceColumn, "Balance", "Balance", 80);
            dgvPaymentBalanceColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvPaymentBalanceColumn.DefaultCellStyle.Format = "C";

            CustomTextBoxColumn applyColumn = adjustmentGrid.AddCustomTextBoxColumn(ApplyColumn, "Apply", "ApplyAmt", 70);
			
			//CR-377441 fix
            //applyColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            applyColumn.MinimumWidth = 100;
            applyColumn.CellTemplate.Style.Padding = new Padding(0, 1, 1, 2);
            applyColumn.DefaultCellStyle.Format = "C";
            applyColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            adjustmentGrid.Columns[InvoiceIdColumn].ReadOnly = true;
            adjustmentGrid.Columns[RequestIdColumn].ReadOnly = true;
            adjustmentGrid.Columns[DateColumn].ReadOnly = true;
            adjustmentGrid.Columns[ChargesColumn].ReadOnly = true;
            adjustmentGrid.Columns[PayAdjColumn].ReadOnly = true;
            adjustmentGrid.Columns[AppliedColumn].ReadOnly = true;
            adjustmentGrid.Columns[BalanceColumn].ReadOnly = true;
            adjustmentGrid.Columns[ApplyColumn].ReadOnly = false;


            adjustmentGrid.AutoSize = true;
            adjustmentGrid.ScrollBars = ScrollBars.Both;

            adjustmentGrid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
        }

        private void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            int columCount = adjustmentGrid.ColumnCount;
            for (int colCount = 0; colCount < columCount; colCount++)
            {
                adjustmentGrid.Columns[6].DefaultCellStyle.Font = new System.Drawing.Font("Arial", 9F, FontStyle.Bold);
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
        public void SetAdjData(long adjId, long requestorId, double appliedAmt, bool isEdit, RequestorDetails requestorDetails)
        {
            try
            {
                this.adjustmentId = adjId;
                this.isEdit = isEdit;
                this.requestorId = requestorId;
                this.saveButton.Enabled = false;
                //CR377288
                if (ParseDouble(availableValueLabel.Text.ToString()) > 0)
                this.applyButton.Enabled = true;
                else
                    this.applyButton.Enabled = false;
                this.requestorDetails = requestorDetails;
                
                currentAdjInfo = RequestorController.Instance.RetrieveAdjustmentInfoByAdjustmentId(adjId,requestorId);
                if (currentAdjInfo.RequestorInvoicesList != null)
                {
                    currentAdjInfo.RequestorInvoicesList = PrepareOpenInvoiceList();
                }
                //Calculated the applied amount for the individual invoice for the user don't have the facility permission
                foreach (RequestInvoiceDetail requestInvoiceDetail in currentAdjInfo.RequestorInvoicesList)
                {
                    if (requestInvoiceDetail.HasBlockedRequestorFacility || requestInvoiceDetail.HasMaskedRequestorFacility)
                    {
                        deniedInvoicesAppliedAmount += requestInvoiceDetail.AppliedAmount;
                    }
                    totalAppliedAmount += requestInvoiceDetail.AppliedAmount;
                    requestInvoiceDetail.AppliedAmountCopy = requestInvoiceDetail.AppliedAmount;
                }
                //double appliedValueAmount = currentAdjInfo.ReqAdjustmentDetails.Amount; for CR 377572
                double appliedValueAmount = currentAdjInfo.ReqAdjustmentDetails.Amount - currentAdjInfo.ReqAdjustmentDetails.UnappliedAmount;
                appliedValueLabel.Text = appliedValueAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                availableValueLabel.Text = currentAdjInfo.ReqAdjustmentDetails.UnappliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                amountTextBox.Text = currentAdjInfo.ReqAdjustmentDetails.Amount.ToString("C",System.Threading.Thread.CurrentThread.CurrentUICulture);
                //double totalAdjustmentAmount = currentAdjInfo.ReqAdjustmentDetails.Amount + currentAdjInfo.ReqAdjustmentDetails.UnappliedAmount;
                //for CR 377572
                double totalAdjustmentAmount = currentAdjInfo.ReqAdjustmentDetails.Amount;
                if (totalAdjustmentAmount >= 0.0)
                {
                    adjustmentAmountValueLabel.Text = (totalAdjustmentAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                else
                {
                    adjustmentAmountValueLabel.Text = "0.00";
                  
                }
                reasonComboBox.DropDownStyle = ComboBoxStyle.DropDown;
                if (currentAdjInfo.ReqAdjustmentDetails.Reason != null)
                {
                    reasonComboBox.Text = currentAdjInfo.ReqAdjustmentDetails.Reason.ToString();
                }
                PopulateAdjustmentType();
                typeComboBox.SelectedValue = currentAdjInfo.ReqAdjustmentDetails.AdjustmentType;
                reasonComboBox.Enabled = false;

                textBox1.Text = currentAdjInfo.ReqAdjustmentDetails.Note;
                textBox1.Enabled = false;

                adjAmount = currentAdjInfo.ReqAdjustmentDetails.Amount; //+ currentAdjInfo.ReqAdjustmentDetails.UnappliedAmount; for cr 377572

                ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
                
                foreach (RequestInvoiceDetail reqInv in list)
                    this.TotalInvBalance = this.TotalInvBalance + reqInv.Balance;

                foreach (RequestInvoiceDetail reqInv in list)
                {
                    if (reqInv.Adjustments < 0)
                        reqInv.Adjustments = -(reqInv.Adjustments);

                    reqInv.OrignalBalance = reqInv.Balance;
                    reqInv.OriginalAdjustment = reqInv.Adjustments;
                    reqInv.OriginalTotalAdjPay = reqInv.PayAdjTotal;
                    this.TotalOrigInvBalance += reqInv.OrignalBalance;
                }
                PopulateData(list);
                
                dateTimePicker1.Text = currentAdjInfo.ReqAdjustmentDetails.AdjustmentDate;
                dateTimePicker1.Enabled = false;
                restrictedFacilityLabel.Visible = this.deniedInvoicesAppliedAmount > 0.0 ? true : false;
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                restrictedFacilityLabel.Text = rm.GetString("RestrictedFacilityLabel");
                removeAdjustmentButton.Visible = ParseDouble(availableValueLabel.Text) == ParseDouble(adjustmentAmountValueLabel.Text);
                typeComboBox.Enabled = false;
                amountTextBox.Enabled = false;
                this.unAppliedAdjAmount = currentAdjInfo.ReqAdjustmentDetails.UnappliedAmount;
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            
        }
        public void SetData(object data, long requestorID, RequestorDetails requestorDetails)
        {
            try
            {
                if (data != null)
                {
                    this.saveButton.Enabled = true;
                    this.applyButton.Enabled = false;

                    isAdjCreation=true;
                    currentAdjInfo = (AdjustmentInfoDetail)data;
                    requestorId = requestorID;

                    outerTableLayoutPanel.SuspendLayout();
                    PopulateAdjustmentType();

                    ReasonDetails reasonDetail = new ReasonDetails();
                    reasonDetail.Name = "Please Select";

                    foreach (ReasonDetails reason in currentAdjInfo.ReasonsList)
                    {
                        reasonComboBox.Items.Add(reason);
                    }
                    reasonComboBox.Items.Insert(0, reasonDetail);
                    reasonComboBox.DisplayMember = "Name";
                    reasonComboBox.SelectedIndex = 0;
                    reasonComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
                    if (currentAdjInfo.RequestorInvoicesList != null)
                    {
                        currentAdjInfo.RequestorInvoicesList = PrepareRequestorInvoiceList();
                    }
                    ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
                    foreach (RequestInvoiceDetail reqInv in list)
                        this.TotalInvBalance = this.TotalInvBalance + reqInv.Balance;
                    
                    foreach (RequestInvoiceDetail reqInv in list)
                    {
                        if (reqInv.Adjustments < 0)
                            reqInv.Adjustments = -(reqInv.Adjustments);

                        reqInv.OrignalBalance = reqInv.Balance;
                        reqInv.OriginalAdjustment = reqInv.Adjustments;
                        reqInv.OriginalTotalAdjPay = reqInv.PayAdjTotal;
                    }

                    PopulateData(PrepareUnAdjustList(list));
                    this.requestorDetails = requestorDetails;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
                            
        }

        private Collection<RequestInvoiceDetail> PrepareRequestorInvoiceList()
        {
           Collection<RequestInvoiceDetail> requestorInvoiceList = new Collection<RequestInvoiceDetail>();
           
           foreach (RequestInvoiceDetail requestInvoiceDetail in currentAdjInfo.RequestorInvoicesList)
           {
                if (!(requestInvoiceDetail.InvoiceType.Equals("Refund")))
                {
                    if (requestInvoiceDetail.Balance > 0)
                    {
                        requestorInvoiceList.Add(requestInvoiceDetail);
                    }
                }
           }
           currentAdjInfo.RequestorInvoicesList.Clear();
           return requestorInvoiceList;
        }

        private Collection<RequestInvoiceDetail> PrepareOpenInvoiceList()
        {
            Collection<RequestInvoiceDetail> requestorInvoiceList = new Collection<RequestInvoiceDetail>();

            foreach (RequestInvoiceDetail requestInvoiceDetail in currentAdjInfo.RequestorInvoicesList)
            {
                if (requestInvoiceDetail.Balance > 0 || requestInvoiceDetail.AppliedAmount > 0)
                {
                    requestorInvoiceList.Add(requestInvoiceDetail);
                }
            }
            currentAdjInfo.RequestorInvoicesList.Clear();
            return requestorInvoiceList;
        }

        public ComparableCollection<RequestInvoiceDetail> PrepareUnAdjustList(ComparableCollection<RequestInvoiceDetail> invList)
        {
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailsList = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail requestInvoiceDetail in invList)
            {
                //removed the invoices when the user does not have facility permission
                if (requestInvoiceDetail.Balance > 0)
                {
                    requestInvoiceDetailsList.Add(requestInvoiceDetail);
                }
            }

            return requestInvoiceDetailsList;

        }

        private void PopulateAdjustmentType()
        {
            IList adjustmentType = EnumUtilities.ToList(typeof(AdjustmentType));
            typeComboBox.DataSource = adjustmentType;
            typeComboBox.ValueMember = "key";
            typeComboBox.DisplayMember = "value";
        }

        public virtual void PopulateData(ComparableCollection<RequestInvoiceDetail> requestInvoiceDetails)
        {
            ROIViewUtility.MarkBusy(true);
            if (requestInvoiceDetails == null) return;
            
            requestInvoiceDetails.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            requestInvoiceDetails.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailsList = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail requestInvoiceDetail in requestInvoiceDetails)
            {
                //removed the invoices when the user does not have facility permission
                if (!requestInvoiceDetail.HasMaskedRequestorFacility && !requestInvoiceDetail.HasBlockedRequestorFacility)
                {
                    requestInvoiceDetailsList.Add(requestInvoiceDetail);
                }
            }
            if (adjustmentGrid.Rows.Count > 0)
            {
                adjustmentGrid.Rows.Clear();
            }
            if (requestInvoiceDetailsList.Count == 0)
            {
                applyButton.Enabled = false;
            }
            else
            {
                //CR377288
                if (ParseDouble(availableValueLabel.Text.ToString()) > 0)
                {
                    applyButton.Enabled = true;
                }
                else
                {
                    applyButton.Enabled = false;
                }
            }
            adjustmentGrid.SetItems(requestInvoiceDetailsList);
            adjustmentGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(grid_RowSelected);
            ROIViewUtility.MarkBusy(false);
        }
        public void CleanUp()
        {

        }

        private void EnableEvents()
        {
            amountTextBox.Leave += new EventHandler(amountTextBox_Leave);
        }


        private void DisableEvents()
        {
            amountTextBox.Leave -= new EventHandler(amountTextBox_Leave);
        }

        private void MarkDirty(object sender, EventArgs e)
        {

        }

        private static void HideFooterButtons(RequestorInfoUI infoUI)
        {
            infoUI.HideDeleteRequestorButton = true;
            infoUI.HideCreateRequestButton = true;
        }

        private void ShowErrorDialog(string errorMessage)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("Feetype.Title");
            string okButtonText = rm.GetString("Feetype.OkButton");
            string messageText = rm.GetString(errorMessage);
            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
            ((Form)(this.Parent)).DialogResult = DialogResult.None; 
        }

        private void reasonPanel_Paint(object sender, PaintEventArgs e)
        {


        }

        private void applyButton_Click(object sender, EventArgs e)
        {
            try
            {
                bAdjustmentAppliedToInvoice = true;
                if (!ValidateAdjustmentData())
                {
                    this.saveButton.Enabled = false;
                    return;
                }
                double availableAmount = ParseDouble(availableValueLabel.Text.ToString());
                if (availableAmount <= 0.0)
                {
                    ShowAdjAmountError("Adjmount.Message");
                    //this.saveButton.Enabled = false;
                    return;
                }
                this.TotalInvBalance = 0;
                double totalAppliedAmount = 0;
                foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                {
                    if (!reqInv.HasMaskedRequestorFacility && !reqInv.HasBlockedRequestorFacility)
                    {
                        this.TotalInvBalance = this.TotalInvBalance + reqInv.Balance;
                        totalAppliedAmount = totalAppliedAmount +reqInv.AppliedAmount + reqInv.ApplyAmt;
                    }
                }

                AppliedInvoiceAmt = availableAmount;

                
                reInvList.Clear();

                ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);

                double availableAdjAmt = AppliedInvoiceAmt;
                bool flgAdjAmtOver= false;
                int count = 0;              
                foreach (RequestInvoiceDetail reqInv in list)
                {
                    if (!reqInv.HasBlockedRequestorFacility && !reqInv.HasMaskedRequestorFacility)
                    {
                        if (Convert.ToDouble(adjustmentGrid.Rows[count].Cells[ApplyColumn].Value) > 0)
                        {
                            availableAdjAmt += Convert.ToDouble(adjustmentGrid.Rows[count].Cells[ApplyColumn].Value);
                            reqInv.Balance += Convert.ToDouble(adjustmentGrid.Rows[count].Cells[ApplyColumn].Value);
                            reqInv.PayAdjTotal -= Convert.ToDouble(adjustmentGrid.Rows[count].Cells[ApplyColumn].Value);
                            adjustmentGrid.Rows[count].Cells[ApplyColumn].Value = 0;
                        }
                    }
                    count++;
                }
                count = 0;
                foreach (RequestInvoiceDetail reqInv in list)
                {
                    if (!reqInv.HasBlockedRequestorFacility && !reqInv.HasMaskedRequestorFacility)
                    {
                        if (reqInv.Balance > 0)  //Invoice should not be closed invoice with ZERO balance
                        {

                            if ((reqInv.Balance >= availableAdjAmt) && (availableAdjAmt != 0))
                            {
                                reqInv.Balance = reqInv.Balance - availableAdjAmt;
                                reqInv.Adjustments = reqInv.Adjustments + availableAdjAmt;
                                reqInv.PayAdjTotal = reqInv.PayAdjTotal + availableAdjAmt;
                                reqInv.ApplyAmt = availableAdjAmt + Convert.ToDouble(adjustmentGrid.Rows[count].Cells[ApplyColumn].Value);
                                availableAdjAmt = 0;
                                flgAdjAmtOver = true;
                            }
                            else if ((reqInv.Balance < availableAdjAmt) && (flgAdjAmtOver != true))
                            {
                                reqInv.ApplyAmt = reqInv.ApplyAmt + reqInv.Balance;
                                availableAdjAmt -= reqInv.Balance;
                                reqInv.Adjustments = reqInv.Adjustments + reqInv.Balance;
                                reqInv.PayAdjTotal = reqInv.PayAdjTotal + reqInv.Balance;
                                reqInv.Balance = 0;
                            }
                        }
                        count++;
                    }
                    reInvList.Add(reqInv);             
                }

                this.appliedValue = AppliedInvoiceAmt - availableAdjAmt;
                appliedValueLabel.Text = (this.appliedValue + totalAppliedAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                availableValue = (this.AdjustmentAmount) - (this.appliedValue + totalAppliedAmount);
                availableValueLabel.Text = availableValue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                PopulateData(list);
                this.saveButton.Enabled = true;
                removeAdjustmentButton.Visible = false;

            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }
        public void CalculateSalesTax()
        {
            double totalTax = 0.0;

                ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
                foreach (RequestInvoiceDetail reqInv in list)
                {
                    if (reqInv.Adjustments < 0)
                        reqInv.Adjustments = -(reqInv.Adjustments);
                    reqInv.Adjustments = reqInv.Adjustments - reqInv.ApplyAmt;
                    reqInv.Balance = reqInv.Balance + reqInv.ApplyAmt;
                    reqInv.PayAdjTotal = reqInv.PayAdjTotal - reqInv.ApplyAmt;
                    reqInv.OrignalBalance = reqInv.Balance + reqInv.ApplyAmt;
                    reqInv.OriginalAdjustment = reqInv.Adjustments;
                    reqInv.OriginalTotalAdjPay = reqInv.PayAdjTotal;
                    reqInv.ApplyAmt = 0;
                }

                PopulateData(PrepareUnAdjustList(list));
                appliedValueLabel.Text = 0.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            salesTax = totalTax;

        }
        #endregion


        public void SetAdjAmount()
        {
            adjAmount = 0;
            //int count=0;
            adjAmount = ROIViewUtility.RoundOffValue(Convert.ToDouble(amountTextBox.Text.Trim().Substring(1, 
                                                     amountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture), 2);

            ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
            foreach (RequestInvoiceDetail reqInv in list)
            {
                if (reqInv.Adjustments < 0)
                    reqInv.Adjustments = -(reqInv.Adjustments);
                reqInv.Adjustments = reqInv.Adjustments - reqInv.ApplyAmt;
                reqInv.OrignalBalance = reqInv.Balance + reqInv.ApplyAmt;
                reqInv.Balance = reqInv.Balance + reqInv.ApplyAmt;
                reqInv.PayAdjTotal = reqInv.PayAdjTotal - reqInv.ApplyAmt;
                reqInv.OriginalAdjustment = reqInv.Adjustments;
                reqInv.OriginalTotalAdjPay = reqInv.PayAdjTotal;
                reqInv.ApplyAmt = 0;
            }
            PopulateData(PrepareUnAdjustList(list));

            appliedValueLabel.Text = 0.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            adjustmentAmountValueLabel.Text = (adjAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);

            if ((adjAmount) > this.TotalInvBalance)
                this.AppliedValue = this.TotalInvBalance;
            else
                appliedValueLabel.Text = this.AppliedValue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            
            if (appliedValueLabel.Text == "")
                availableValue = (adjAmount);        
            else
                availableValue = (adjAmount) - this.AppliedValue;

            availableValueLabel.Text = availableValue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            if (currentAdjInfo.RequestorInvoicesList.Count == 0)
                this.applyButton.Enabled = false;
            else
                this.applyButton.Enabled = true;
            this.saveButton.Enabled = true;
        }
 
        
        #region Properties

        public double SalesTaxPerFacility
        {
            get { return salesTaxPerFacility; }

        }
        public double SalesTax
        {
            get { return salesTax; }
        }
        public double AdjustmentAmount
        {
            get { return adjAmount; }
        }

        public double TotalAdjustmentAmount
        {
            get { return totalApplyAmount; }
        }

        public double UnAppliedAdjustmentAmount
        {
            get { return unAppliedAdjustmentAmount; }
        }

        public double AppliedValue
        {
            get { return ParseDouble(appliedValueLabel.Text); }
            set { appliedValue = value; }
        }

        #endregion

        /// <summary>
        /// ******** Move the constanbt strings to RC table during code cleanup ::********
        /// </summary>
        /// <returns></returns>
        private bool ValidateAdjustmentData()
        {
            try
            {
                ResourceManager rm;
                rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                if (reasonComboBox.SelectedIndex == 0)
                {
                    errorProvider.SetError(reasonComboBox, rm.GetString("select reason"));
                    return false;
                }
                if (adjustmentAmountValueLabel.Text == string.Empty)
                {
                    ShowAdjAmountError("Adjmount.Message");
                    return false;
                }
                if (typeComboBox.SelectedIndex == 0)
                {
                    errorProvider.SetError(typeComboBox, rm.GetString("select adjustment type"));
                    return false;
                }
                double adjusmtentAmount = ParseDouble(amountTextBox.Text.ToString());
                if (adjusmtentAmount == 0)
                {
                    errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
                    return false;
                }

            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }


        private void ShowAdjAmountError(string errorMessage)
        {
            ResourceManager rm=Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText=rm.GetString("Adjmount.Title");
            string okButton=rm.GetString("Adjmount.okButton");
            string messageText=rm.GetString(errorMessage);
            ROIViewUtility.ConfirmChanges(titleText,messageText,okButton,string.Empty,ROIDialogIcon.Alert);
            ((Form)(this.Parent)).DialogResult=DialogResult.None;
        }

        private bool ValidateCalendarEntry()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            if (!ROIViewUtility.IsValidDate(dateTimePicker1.Text))
            {
                errorProvider.SetError(dateTimePicker1, rm.GetString(ROIErrorCodes.InvalidDate));
                return false; 
            }
            else if (Convert.ToDateTime(dateTimePicker1.Text, System.Globalization.CultureInfo.InvariantCulture) > DateTime.Now)
            {
                errorProvider.SetError(dateTimePicker1,  rm.GetString(ROIErrorCodes.InvalidDateOfService));
                return false;
            }
            return true;
        }


        /// <summary>
        /// validates the adjustment reason is Entered in the text box.
        /// </summary>
        /// <returns>is valid</returns>
        private bool ValidateAdjustmentReason()
        {
            ResourceManager rm;
            rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            if (reasonComboBox.SelectedIndex == 0)
            {
                errorProvider.SetError(reasonComboBox, rm.GetString("select reason"));
                return false;
            }
            return true;
        }

        private bool ValidateNotesField()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            //CR#381185
            if (textBox1.Text.Trim().Length == 0)
            {
                errorProvider.SetError(textBox1, rm.GetString("Enter Notes"));
                return false;
            }
            return true;
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            try
            {
                
                //Validate if the Adjustment data entered is proper. Also check if there is any unfilled fee type entry.
                if (!ValidateAdjustmentReason() || !ValidateNotesField() || !ValidateCalendarEntry() || 
                    !ValidateAdjustmentAmount() || !ValidateAdjustmentData() || !adjustmentGrid.EndEdit() )
                {
                    ((Form)(this.Parent)).DialogResult = DialogResult.None;
                    return;
                }

                errorProvider.Clear();

                AdjustmentInfoDetail adjInfoSave = new AdjustmentInfoDetail();
                RequestorAdjustmentDetails reqAdjustmentDetails = new RequestorAdjustmentDetails();
                if(!isAdjCreation)
                {
                    this.TotalInvBalance = 0;
                    reqAdjustmentDetails.AdjustmentDate = currentAdjInfo.ReqAdjustmentDetails.AdjustmentDate;
                    reqAdjustmentDetails.Note = currentAdjInfo.ReqAdjustmentDetails.Note;
                    reqAdjustmentDetails.Reason = currentAdjInfo.ReqAdjustmentDetails.Reason;
                    reqAdjustmentDetails.RequestorSeq = requestorId;
                    reqAdjustmentDetails.UnappliedAmount = ParseDouble(availableValueLabel.Text);
                    //added the applied amount for the user don't have the facility permission
                    //reqAdjustmentDetails.Amount = ParseDouble(appliedValueLabel.Text); for CR 377572
                    reqAdjustmentDetails.Amount = ParseDouble(adjustmentAmountValueLabel.Text.ToString());
                }

                else
                {

                    reqAdjustmentDetails.AdjustmentDate = dateTimePicker1.Text;
                    reqAdjustmentDetails.Note = textBox1.Text.ToString();

                    ReasonDetails reasonDetail = new ReasonDetails();
                    reasonDetail = (ReasonDetails)reasonComboBox.SelectedItem;
                    if (reasonDetail != null)
                    {
                        reqAdjustmentDetails.Reason = reasonDetail.Name;
                    }
                    reqAdjustmentDetails.RequestorSeq = requestorId;
                    reqAdjustmentDetails.UnappliedAmount = ParseDouble(availableValueLabel.Text);
                    reqAdjustmentDetails.Amount = ParseDouble(adjustmentAmountValueLabel.Text.ToString());

                }
                reqAdjustmentDetails.AdjId = adjustmentId;
                if (typeComboBox.SelectedValue.ToString().Equals("BILLING_ADJUSTMENT"))
                {
                    reqAdjustmentDetails.AdjustmentType = AdjustmentType.BILLING_ADJUSTMENT;
                }
                else if (typeComboBox.SelectedValue.ToString().Equals("CUSTOMER_GOODWILL_ADJUSTMENT"))
                {
                    reqAdjustmentDetails.AdjustmentType = AdjustmentType.CUSTOMER_GOODWILL_ADJUSTMENT;
                }
                else
                {
                    reqAdjustmentDetails.AdjustmentType = AdjustmentType.BAD_DEBT_ADJUSTMENT;
                }
                //reqAdjustmentDetails.AdjustmentType = typeComboBox.Text;

                unAppliedAdjustmentAmount = reqAdjustmentDetails.UnappliedAmount;
                adjInfoSave.ReqAdjustmentDetails = reqAdjustmentDetails;
                adjInfoSave.ReqAdjustmentDetails.RequestorName = requestorDetails.Name;
                adjInfoSave.ReqAdjustmentDetails.RequestorType = requestorDetails.TypeName;

                //Pass the invoice information back only if it is applied. 
                if (bAdjustmentAppliedToInvoice)
                {
                    Collection<RequestInvoiceDetail> reqInvList = null;
                    if (reInvList.Count > 0)
                    {
                        reqInvList = new Collection<RequestInvoiceDetail>(reInvList);
                    
                        totalApplyAmount = 0;
                        foreach (RequestInvoiceDetail reqInv in reInvList)
                        {
                           reqInv.ApplyAmount = reqInv.ApplyAmt;
                           totalApplyAmount += reqInv.ApplyAmt;
                           this.TotalInvBalance = this.TotalInvBalance + reqInv.Balance;
                           if(reqInv.InvoiceType == "Prebill")
                            { adjInfoSave.ReqAdjustmentDetails.IsPrebillAdjustment = true; }                             
                        }
                    
                    
                        adjInfoSave.RequestorInvoicesList.Clear();
                        foreach (RequestInvoiceDetail reqInv in reqInvList)
                        {
                            if(reqInv.Adjustments<0)
                                reqInv.Adjustments = -(reqInv.Adjustments);
                            if ((reqInv.ApplyAmount <= 0) && (reqInv.AppliedAmount <= 0) && (reqInv.AppliedAmount == reqInv.AppliedAmountCopy)) continue;
                            adjInfoSave.RequestorInvoicesList.Add(reqInv);
                        }
                    }
                }
                RequestorCache.RemoveKey(requestorId);
                RequestorController.Instance.SaveAdjustmentInfo(adjInfoSave);
            }

            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        
        }

        private double ParseDouble(string strAmount)
        {
            double amount = 0;
            try
            {
                if ((strAmount.Length != 0) && (strAmount.Contains("$")))
                {
                    amount = Convert.ToDouble(strAmount.Trim().Substring(1, strAmount.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
                }
                else
                {
                    amount = double.Parse(strAmount);
                    amount = Math.Round(amount, 2);
                }
            }
            catch (Exception)
            {
                amount = 0.0;
            }
            return amount;
        }
     
       

        private void adjustmentGrid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                if (!(e.RowIndex < 0 || e.ColumnIndex != adjustmentGrid.Columns[adjustmentGrid.Columns.Count - 3].Index))
                {
                    double checkAppliedAmount = Convert.ToDouble(adjustmentGrid.Rows[e.RowIndex].Cells["Applied"].Value);
                    if (checkAppliedAmount == 0.0)
                    {
                        return;
                    }
                    removeAdjustmentButton.Visible = false;
                    if (adjustmentGrid.Rows[e.RowIndex].Cells["Applied"].Value.ToString() != "0")
                    {
                        this.TotalInvBalance = 0;
                        isAdjRemoved = true;
                        bAdjustmentAppliedToInvoice = true;

                        adjustmentGrid.CellEndEdit -= new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);

                        reInvList.Clear();
                        this.totalApplyAmtCpy = 0;
                        foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                        {
                            reInvList.Add(reqInv);
                        }

                        foreach (RequestInvoiceDetail reqInv in reInvList)
                        {
                            if (long.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[0].Value.ToString()) == reqInv.Id)
                            {
                                reqInv.Balance = reqInv.OrignalBalance + reqInv.AppliedAmount - reqInv.ApplyAmt;
                                reqInv.OrignalBalance = reqInv.OrignalBalance + reqInv.AppliedAmount;

                                reqInv.Adjustments = reqInv.OriginalAdjustment - reqInv.AppliedAmount + reqInv.ApplyAmt;
                                reqInv.OriginalAdjustment = reqInv.OriginalAdjustment - reqInv.AppliedAmount;

                                reqInv.PayAdjTotal = reqInv.OriginalTotalAdjPay - reqInv.AppliedAmount + reqInv.ApplyAmt;
                                reqInv.OriginalTotalAdjPay = reqInv.OriginalTotalAdjPay - reqInv.AppliedAmount;

                                reqInv.AppliedAmount = 0;

                                break;
                            }
                        }

                        foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                        {
                            this.TotalInvBalance = this.TotalInvBalance + reqInv.Balance;
                            this.totalApplyAmtCpy = this.totalApplyAmtCpy + reqInv.AppliedAmount + reqInv.ApplyAmt;
                        }

                        
                        ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(reInvList);
                        PopulateData(list);
                        //this.totalApplyAmtCpy -= this.deniedInvoicesAppliedAmount;
                        appliedValueCpy = totalApplyAmtCpy;
                        availableCpy = this.AdjustmentAmount - appliedValueCpy;
                        appliedValueLabel.Text = appliedValueCpy.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                        availableValueLabel.Text = (availableCpy).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);

                        this.saveButton.Enabled = true;

                        adjustmentGrid.CellEndEdit += new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);
                    }
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void ShowInvalidDialog(string strMessageText)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string titleText = rm.GetString("AppliedAmount.Title");
            string okButtonText = rm.GetString("AppliedAmount.OkButton");
            string messageText = rm.GetString(strMessageText); 
            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, string.Empty, ROIDialogIcon.Alert);
            ((Form)(this.Parent)).DialogResult = DialogResult.None;
        }

        private bool ValidateApplyAmount(double checkApplyAmount, double checkBalanceAmount, int rowIndex)
        {
            string strErrorMessage = string.Empty;
            if (checkApplyAmount < 0)
            {
                ShowAdjAmountError("lessThanZero.Message");
                return false;
            }   
            if (checkApplyAmount > checkBalanceAmount)
            {
                adjustmentGrid.CellEndEdit -= new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);
                adjustmentGrid.Rows[rowIndex].Cells[ApplyColumn].Value = 0;
                strErrorMessage = "lesserBalance.Message";
                ShowInvalidDialog(strErrorMessage);
                saveButton.Enabled = false;
                adjustmentGrid.CellEndEdit += new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);
                return false;
            }

            double currInvoiceBalance = 0;
            foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
            {
                currInvoiceBalance = currInvoiceBalance + reqInv.Balance + reqInv.PayAdjTotal;
            }

            if (checkApplyAmount > currInvoiceBalance)
            {
                ShowAdjAmountError("greaterThanTotal.Message");
                return false;
            }
            else
                if (isAdjCreation)
                {
                    if (checkApplyAmount > (ParseDouble(adjustmentAmountValueLabel.Text)))
                    {
                        ShowAdjAmountError("greaterThanAvailable.Message");
                        return false;
                    }
                }
                else
                {
                    double available = 0;
                    double applied = 0;

                    //Calculate the total applied amount for all invoices from invoice grid. Consider Applied column and Apply column amount.  
                    foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                    {
                        if (long.Parse(adjustmentGrid.Rows[rowIndex].Cells[InvoiceIdColumn].Value.ToString()) != reqInv.Id)
                            applied += reqInv.ApplyAmt;
                         applied += reqInv.AppliedAmount;
                    }
                  
                    //Available amount = total adjustment amount - Applied amount for all invoices. 
                    available = ParseDouble(adjustmentAmountValueLabel.Text) - applied;
                    available = Math.Round(available, 2); 
                    if (checkApplyAmount > available)
                    {
                        ShowAdjAmountError("greaterThanAvailable.Message");
                        return false;
                    }
                }
            return true; 
        }
        private void adjustmentGrid_CellEndEdit(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                removeAdjustmentButton.Visible = false;
                //double checkBalanceAmount = double.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[PayAdjColumn].Value.ToString()) +
                //                             double.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[BalanceColumn].Value.ToString());
                double checkBalanceAmount = 0;
                if (currentAdjInfo.RequestorInvoicesList.Count > 0)
                {
                    foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                    {
                        if (long.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[0].Value.ToString()) == reqInv.Id)
                        {
                            checkBalanceAmount = reqInv.OrignalBalance;
                        }
                    }
                }
                double checkApplyAmount = double.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value.ToString());
                    if (!(ValidateApplyAmount(checkApplyAmount, checkBalanceAmount, e.RowIndex)))
                    {
                        this.saveButton.Enabled = false;
                        adjustmentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value=0;
                        checkApplyAmount = double.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Value.ToString());
                        //adjustmentGrid.Rows[e.RowIndex].Cells[ApplyColumn].Selected = true;
                        //return;
                    }                

                double totalAppliedAmt = 0;
                this.TotalInvBalance = 0;
                reInvList.Clear();
                
                foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                {
                    reInvList.Add(reqInv);
                    this.TotalInvBalance = this.TotalInvBalance + reqInv.OrignalBalance;
                    totalAppliedAmt = totalAppliedAmt + reqInv.AppliedAmount;
                }
                bAdjustmentAppliedToInvoice = true;

                adjustmentGrid.CellEndEdit -= new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);

                if (reInvList.Count > 0)
                {

                    foreach (RequestInvoiceDetail reqInv in reInvList)
                    {
                        if (long.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[0].Value.ToString()) == reqInv.Id)
                        {
                            reqInv.Balance = reqInv.OrignalBalance - checkApplyAmount;
                            reqInv.Adjustments = reqInv.OriginalAdjustment + checkApplyAmount;
                            reqInv.PayAdjTotal = reqInv.OriginalTotalAdjPay + checkApplyAmount;
                            reqInv.ApplyAmt = checkApplyAmount;
                            break;
                        }
                    }
                }

                this.totalApplyAmtCpy = 0;
                foreach (RequestInvoiceDetail reqInv in currentAdjInfo.RequestorInvoicesList)
                {
                    this.totalApplyAmtCpy = this.totalApplyAmtCpy + reqInv.ApplyAmt;
                }

                if (this.totalApplyAmtCpy + totalAppliedAmt > (this.AdjustmentAmount))
                {
                    double appliedValue = 0;
                    ShowAdjAmountError("exceedsTotal.Message");
                    this.saveButton.Enabled = false;
                    ComparableCollection<RequestInvoiceDetail> list1 = new ComparableCollection<RequestInvoiceDetail>(currentAdjInfo.RequestorInvoicesList);
                    foreach (RequestInvoiceDetail reqInv in list1)
                    {
                        if (long.Parse(adjustmentGrid.Rows[e.RowIndex].Cells[0].Value.ToString()) == reqInv.Id)
                        {
                            if (reqInv.Adjustments < 0)
                                reqInv.Adjustments = -(reqInv.Adjustments);
                            reqInv.Adjustments = reqInv.Adjustments - reqInv.ApplyAmt;
                            reqInv.Balance = reqInv.Balance + reqInv.ApplyAmt;
                            reqInv.PayAdjTotal = reqInv.PayAdjTotal - reqInv.ApplyAmt;                            
                            reqInv.OriginalAdjustment = reqInv.Adjustments;
                            reqInv.OriginalTotalAdjPay = reqInv.PayAdjTotal;
                            reqInv.ApplyAmt = 0;
                        }
                        appliedValue = appliedValue + reqInv.ApplyAmt;
                    }
                    
                    PopulateData(list1);
                    appliedValueLabel.Text = appliedValue.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    availableValueLabel.Text = (ParseDouble(adjustmentAmountValueLabel.Text.ToString()) - appliedValue).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    adjustmentGrid.CellEndEdit += new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);
                    return;
                }
                
                ComparableCollection<RequestInvoiceDetail> invList = new ComparableCollection<RequestInvoiceDetail>(reInvList);
                PopulateData(invList);

                adjustmentGrid.CellEndEdit += new DataGridViewCellEventHandler(adjustmentGrid_CellEndEdit);

                if (this.totalApplyAmtCpy + totalAppliedAmt > (this.AdjustmentAmount))
                {
                    ShowAdjAmountError("exceedsTotal.Message");
                    this.saveButton.Enabled = false;
                }
                else
                {
                    //removed the applied amount for the user don't have the facility permission
                    appliedValueCpy = totalApplyAmtCpy + totalAppliedAmt;
                    availableCpy = (this.AdjustmentAmount) - appliedValueCpy;
                    appliedValueLabel.Text = appliedValueCpy.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    availableValueLabel.Text=(ParseDouble(adjustmentAmountValueLabel.Text.ToString())-
                        ParseDouble(appliedValueLabel.Text.ToString())).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                    this.saveButton.Enabled = true;
                }
                double appliedAmount = ParseDouble(appliedValueLabel.Text.ToString());
                if (appliedAmount == 0)
                {
                    if (!isAdjCreation)
                    {
                        removeAdjustmentButton.Visible = this.adjAmount == this.unAppliedAdjAmount ? true : false;
                    }
                }
                
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void reasonComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
        }

        private void adjustmentGrid_DataError(object sender, DataGridViewDataErrorEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            EIGDataGrid grid = (EIGDataGrid)sender;
            if (grid.EditingControl == null) return;
            AmountTextBoxClass amtTextBox = grid.EditingControl as AmountTextBoxClass;
            ToolTip toolTip = new ToolTip();
            amtTextBox.AlertImage.Visible = true;
            toolTip.SetToolTip(amtTextBox.AlertImage, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
            //CR# 380999
            if (cancelButton.Focused)
            {
                ((Form)this.Parent).Close();
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
        }

        /// <summary>
        /// occurs when user clicks remove adjustment button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void removeAdjustmentButton_Click(object sender, EventArgs e)
        {
            AdjustmentInfoDetail adjInfoSave = new AdjustmentInfoDetail();
            RequestorAdjustmentDetails reqAdjustmentDetails = new RequestorAdjustmentDetails();
            reqAdjustmentDetails.IsRemoveAdjustment = true;
            reqAdjustmentDetails.AdjId = adjustmentId;
            adjInfoSave.ReqAdjustmentDetails = reqAdjustmentDetails;
            adjInfoSave.ReqAdjustmentDetails.RequestorName = requestorDetails.Name;
            adjInfoSave.ReqAdjustmentDetails.RequestorType = requestorDetails.TypeName;
            adjInfoSave.ReqAdjustmentDetails.AdjustmentType = ((AdjustmentType)EnumUtilities.EnumValueOf(typeComboBox.Text, typeof(AdjustmentType)));
            RequestorCache.RemoveKey(requestorDetails.Id);
            RequestorController.Instance.SaveAdjustmentInfo(adjInfoSave);
            ((Form)(this.Parent)).DialogResult = DialogResult.OK;
        }

        private void availableValueLabel_TextChanged(object sender, EventArgs e)
        {
            double unappliedAmt = 0;
            unappliedAmt = ParseDouble(availableValueLabel.Text.ToString());
            if (unappliedAmt <= 0)
                applyButton.Enabled = false;
            else
                applyButton.Enabled = true;
        }

        private void amountTextBox_Leave(object sender, EventArgs e)
        {
            if (amountTextBox.Text.Trim() != string.Empty)
            {
                if (!ValidateAdjustmentAmount()) return;
                double adjustmentAmount = ParseDouble(amountTextBox.Text.ToString());
                applyButton.Enabled = true;
                amountTextBox.Text = adjustmentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                adjustmentAmountValueLabel.Text = adjustmentAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                double appliedAmount = 0.0;
                appliedValueLabel.Text = appliedAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                availableValueLabel.Text = (adjustmentAmount - appliedAmount).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
                SetAdjAmount();
            }
        }

        private void amountTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (!char.IsNumber(e.KeyChar) & (Keys)e.KeyChar != Keys.Back & e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }

        private void typeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
        }

        private void amountTextBox_TextChanged(object sender, EventArgs e)
        {
            errorProvider.Clear();
        }

        private bool ValidateAdjustmentAmount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
            double balanceAmount = 0.0;
            string amount = amountTextBox.Text.Trim();
            if (amount.Contains("$"))
            {
                amountTextBox.Text = Convert.ToString(amount.Substring(1, amountTextBox.Text.Length - 1), System.Threading.Thread.CurrentThread.CurrentUICulture);
            }

            if (!double.TryParse(amountTextBox.Text.Trim(), out balanceAmount) || balanceAmount < 0 ||
                     !Regex.IsMatch(string.Format("{0:0.00}", balanceAmount), RequestTransaction.ValidAmountFormat))
            {
                errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmountFormat));
                return false;
            }
            if (balanceAmount == 0)
            {
                errorProvider.SetError(amountTextBox, rm.GetString(ROIErrorCodes.InvalidAdjustmentAmount));
                return false;
            }
            errorProvider.Clear();
            return true;
        }
    }
}
