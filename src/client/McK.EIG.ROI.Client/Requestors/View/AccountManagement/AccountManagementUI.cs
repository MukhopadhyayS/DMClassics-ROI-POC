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
using System.Windows.Forms;

using McK.EIG.Common.FileTransfer.Controller.Upload;
using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Request.View;

using McK.EIG.Common.Utility.Logging;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    public partial class AccountManagementUI : ROIBaseUI, IFooterProvider
    {
        #region Fields
        
        private AccountManagementFooterUI accountManagementFooterUI;

        private const string CreatedDateColumn = "createdDate";
        private const string InvoiceTypeColumn = "invoiceType";
        private const string DescriptionColumn = "description";
        private const string RequestIdColumn = "requestId";
        private const string ChargesColumn = "charges";
        private const string AdjustementsColumn = "adjustments";
        private const string PaymentsColumn = "payments";
        private const string BalanceColumn = "balance";
        private const string UnbillableColumn = "unbillable";

        private ROIProgressBar fileTransferProgress;
        internal static EventHandler ProgressHandler;

        private string requestorName;
        private ComparableCollection <RequestInvoiceDetail> reqInvDetail;

        private RequestorDetails requestorDetails;
        private PaymentUI paymentUI;
        private AdjustmentsUI adjustmentUI;
        private RefundUI refundUI;
        private StatementDialog statementDialog;
        private InvoiceUI invoiceUI;
        private string sortedColumn;
        private ListSortDirection direction;
        private double appliedAmount;
        //CR# 380979
        private double totalUnAppliedPaymentAmount;
        private double totalUnAppliedAdjustmentAmount;
        private double refundAmount;
        private double totalRequestorInvoiceBalance;
        private long invoiceId;
        private RequestInvoiceDetail requestInvoiceDetailForGridSelection;
        #endregion

        #region Constructor

        public AccountManagementUI()
        {
            InitializeComponent();
            accountManagementFooterUI = new AccountManagementFooterUI();
            CreateActionButtons();
            ApplySecurityRights();
            InitGrid();
            EnableUI(true);
            reqInvDetail = new ComparableCollection < RequestInvoiceDetail>();
            invoiceUI = new InvoiceUI();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the Grid.
        /// </summary>
        public virtual void InitGrid()
        {
            DataGridViewTextBoxColumn dgvCreatedDateColumn = grid.AddTextBoxColumn(CreatedDateColumn, "Date", "CreatedDate", 70);
            dgvCreatedDateColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern;                                                           
            grid.AddTextBoxColumn(InvoiceTypeColumn, "Type", "InvoiceType", 80);            
            grid.AddTextBoxColumn(DescriptionColumn, "Description", "Description", 120);
            grid.AddTextBoxColumn(RequestIdColumn, "Request ID", "RequestId", 70);
            DataGridViewTextBoxColumn dgvChargesColumn =grid.AddTextBoxColumn(ChargesColumn, "Charges", "Charges", 80);
            dgvChargesColumn.DefaultCellStyle.Format = "C";            
            dgvChargesColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            DataGridViewTextBoxColumn dgvAdjustementsColumn = grid.AddTextBoxColumn(AdjustementsColumn, "Adjustments", "AdjAmount", 80);
            dgvAdjustementsColumn.DefaultCellStyle.Format = "C";
            dgvAdjustementsColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            DataGridViewTextBoxColumn dgvPaymentsColumn = grid.AddTextBoxColumn(PaymentsColumn, "Payments", "PayAmount", 95);
            dgvPaymentsColumn.DefaultCellStyle.Format = "C";
            dgvPaymentsColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            //Unbillable cloumn
            DataGridViewImageColumn billableImageColumn = grid.AddImageColumn("BillableImage", "Billable ", "BillableIcon", 50);
            billableImageColumn.DefaultCellStyle.NullValue = null;
            billableImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            billableImageColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopCenter;
            DataGridViewTextBoxColumn balance = grid.AddTextBoxColumn(BalanceColumn, "Balance", "Balance", 70);
            balance.DefaultCellStyle.Format = "C";
            balance.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
			balance.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            balance.MinimumWidth = 90;
            grid.AutoSize = true;
            grid.MultiSelect = false;
            grid.ScrollBars = ScrollBars.Both;
            grid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            grid.SelectionChanged +=new EventHandler(grid_SelectionChanged);
            EnableEvents();
        }

        /// <summary>
        /// Occurs once the data is been bound
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            RequestInvoiceDetail requestInvoiceDetails;
            foreach (DataGridViewRow row in grid.Rows)
            {
                requestInvoiceDetails = (RequestInvoiceDetail)row.DataBoundItem;
                if (requestInvoiceDetails.HasMaskedRequestorFacility)
                {
                    foreach (DataGridViewCell rowCell in row.Cells)
                    {
                        rowCell.ToolTipText = rm.GetString("unauthorizedFacilityPermission");
                    }
                    row.DefaultCellStyle.ForeColor = Color.Red;
                    row.DefaultCellStyle.SelectionBackColor = Color.White;
                    row.DefaultCellStyle.SelectionForeColor = Color.Red;
                    row.ReadOnly = true;
                }
            }
        }

        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            ShowProgress((FileTransferEventArgs)e);
        }

        /// <summary>
        /// Method call the progress bar to display.
        /// </summary>
        /// <param name="e"></param>
        public void ShowProgress(FileTransferEventArgs e)
        {
            switch (e.TransferStatus)
            {
                case FileTransferEventArgs.Status.Start:
                    fileTransferProgress.Visible = true;
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.InProgress:
                    fileTransferProgress.ShowProgress(e);
                    break;

                case FileTransferEventArgs.Status.Finish:
                    fileTransferProgress.ShowProgress(e);
                    fileTransferProgress.Visible = false;
                    break;
            }
        }

        /// <summary>
        /// Method initilize the progress bar.
        /// </summary>
        private void InitProgress()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            fileTransferProgress = new ROIProgressBar();
            fileTransferProgress.Location = new Point(((this.Width / 2)) - fileTransferProgress.Width / 2,
                                                     (this.Height / 2) + 60 + fileTransferProgress.Height / 2);
            listPanel.Controls.Add(fileTransferProgress);
            fileTransferProgress.BringToFront();
            fileTransferProgress.MessageText.Text = rm.GetString("ProgressMessage");
            fileTransferProgress.MessageText.BringToFront();
            fileTransferProgress.PercentageLabel.BackColor = Color.FromArgb(2, 0, 0, 0);
            fileTransferProgress.Visible = false;
        }

        /// <summary>
        /// Localize UI Controls.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            ProgressHandler = new EventHandler(Process_NotifyProgress);
            InitProgress();
            accountManagementFooterUI.Localize();
        }

        /// <summary>
        /// It sets the pane which holds the Requestor information UI
        /// </summary>
        /// <param name="pane"></param>
        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            accountManagementFooterUI.SetPane(pane);
            accountManagementFooterUI.SetExecutionContext(Context);
        }

        /// <summary>
        /// Gets localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Subscribe Events
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
        }

        /// <summary>
        /// Unsubscribe Events.
        /// </summary>
        private void DisableEvents()
        {
        }

        public void SetData(object data, RequestorDetails requestor)
        {
            DisableEvents();
            if (data == null)
            {
                EnableButtons(false);
                requestorDetails = requestor;
                return;
            }            
            reqInvDetail = (ComparableCollection <RequestInvoiceDetail>)data;
            if (reqInvDetail.Count <= 0)
            {
                EnableButtons(false);
                return;
            }
            else
            {
                EnableButtons(true);
            }
            requestorDetails = requestor;
            EnableUI(true);
            PopulateData(data);
            EnableEvents();
            SelectFirstRow();
            if (grid.Items.Count == 0)
                ViewButton.Enabled = false;
        }

        /// <summary>
        /// Enable the request list UI. Used for tab order.
        /// </summary>
        /// <param name="enable"></param>
        protected void EnableUI(bool enable)
        {
            this.Enabled = enable;
        }

        public virtual void PopulateData(object data)
        {
            ROIViewUtility.MarkBusy(true);
            if (data == null) return;
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = (ComparableCollection<RequestInvoiceDetail>)data;
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailsList = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail requestInvoiceDetail in requestInvoiceDetailList)
            {
                if (!requestInvoiceDetail.HasBlockedRequestorFacility)
                {
                    requestInvoiceDetailsList.Add(requestInvoiceDetail);
                }
                if (requestInvoiceDetail.HasBlockedRequestorFacility || requestInvoiceDetail.HasMaskedRequestorFacility)
                {
                    appliedAmount += requestInvoiceDetail.AppliedAmount;
                }
            }
            
            if (!string.IsNullOrEmpty(sortedColumn))
            {
                requestInvoiceDetailsList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))[sortedColumn], direction);
                requestInvoiceDetailsList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))[sortedColumn], direction);
            }
            else
            {
                requestInvoiceDetailsList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                requestInvoiceDetailsList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
            }

            grid.SetItems((IFunctionCollection)requestInvoiceDetailsList);
            
            double totalCharge = 0;
            double totalAdjPay = 0;
            double totalAdj = 0;
            double totalPay = 0;
            double totalBalance = 0;
            double unbillableAmount = 0;
            totalUnAppliedPaymentAmount = 0.0;
            totalUnAppliedAdjustmentAmount = 0.0;
            totalRequestorInvoiceBalance = 0.0;
            foreach (RequestInvoiceDetail reqInv in requestInvoiceDetailsList)
            {
                if ("NO".Equals(reqInv.Unbillable))
                {
                    totalCharge += reqInv.Charges;
                }
                else
                {
                    unbillableAmount += reqInv.Charges;
                }

                if (!reqInv.InvoiceType.Equals("Refund"))
                {
                    totalAdj += reqInv.AdjAmount;
                    totalPay += reqInv.PayAmount;
                }
                totalAdjPay = totalAdj + totalPay;
                totalBalance = totalCharge + totalAdjPay;
                if (reqInv.InvoiceType.Equals("Unapplied Payment") || reqInv.InvoiceType.Equals("Unapplied Adjustment"))
                {
                    totalUnAppliedAdjustmentAmount += Math.Abs(reqInv.AdjAmount);
                    totalUnAppliedPaymentAmount += Math.Abs(reqInv.PayAmount);
                }
                if (reqInv.InvoiceType.Equals("Open Invoice"))
                {
                    totalRequestorInvoiceBalance += reqInv.Balance;
                }
                if ((reqInv.InvoiceType.Equals("Open Invoice") || reqInv.InvoiceType.Equals("Closed Invoice") || reqInv.InvoiceType.Equals("Prebill")))
                {
                    RequestBillingInfoCache.RemoveKey(reqInv.RequestId);
                }

            }
            grid.CellFormatting += new DataGridViewCellFormattingEventHandler(grid_CellFormatting);
            totalChargesValueLabel.Text = totalCharge.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            totalAdjPayLabelValue.Text = totalAdjPay.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            UnbillableAmtValueLabel.Text = unbillableAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            
            accountBalanceValueLable.Text = totalBalance.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            RefundAmountCalculation();
            accountManagementFooterUI.RefundButton.Enabled = totalBalance < 0 && refundAmount > 0;

            ViewButton.Enabled = grid.Items.Count > 0;
                  
            ROIViewUtility.MarkBusy(false);
            if (grid.Items.Count > 0)
            {
                if (!requestorDetails.IsAssociated)
                {
                    requestorDetails.IsAssociated = true;
                }
            }
            
        }

        private void grid_SelectionChanged(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in grid.Rows)
            {
                if (ROIImages.SelectedBillableIcon.Equals(row.Cells["BillableImage"].Value))
                {
                    row.Cells["BillableImage"].Value = ROIImages.BillableIcon;
                    break;
                }
            }
            if (grid.SelectedRows.Count > 0)
            {
                if (ROIImages.BillableIcon.Equals(grid.SelectedRows[0].Cells["BillableImage"].Value))
                {
                    grid.SelectedRows[0].Cells["BillableImage"].Value = ROIImages.SelectedBillableIcon;
                }
            }
        }


        /// <summary>
        /// Calculation for refund amount
        /// </summary>
        private void RefundAmountCalculation()
        {
            refundAmount = 0.0;
            double invoiceBalance = (totalRequestorInvoiceBalance - (totalUnAppliedAdjustmentAmount + totalUnAppliedPaymentAmount));
            if (invoiceBalance <= 0)
            {
                refundAmount = ROIViewUtility.RoundOffValue((totalUnAppliedAdjustmentAmount + totalUnAppliedPaymentAmount), 2);
            }
            else
            {
                refundAmount = ROIViewUtility.RoundOffValue((invoiceBalance - (totalUnAppliedAdjustmentAmount + totalUnAppliedPaymentAmount)), 2);
            }
            refundAmount = Math.Abs(refundAmount);
        }

        private void grid_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if ((grid.Columns[e.ColumnIndex].Name.Equals(RequestIdColumn)) || 
                (grid.Columns[e.ColumnIndex].Name.Equals(ChargesColumn))   || 
                (grid.Columns[e.ColumnIndex].Name.Equals(PaymentsColumn))  ||
                (grid.Columns[e.ColumnIndex].Name.Equals(AdjustementsColumn))) 
            {
                if (grid.Columns[e.ColumnIndex].Name.Equals(RequestIdColumn))
                {
                    if (!string.IsNullOrEmpty(e.Value.ToString()))
                    {
                        if ((long)e.Value == 0)
                        {
                            e.Value = "";
                        }
                    }
                }
                //CR379338

                if(grid.Rows[e.RowIndex].Cells[1].Value.ToString()=="Unapplied Adjustment"||
                    grid.Rows[e.RowIndex].Cells[1].Value.ToString()=="Unapplied Payment")
                {
                     if ((grid.Columns[e.ColumnIndex].Name.Equals(ChargesColumn)) ||
                       (grid.Columns[e.ColumnIndex].Name.Equals(PaymentsColumn)) ||
                            (grid.Columns[e.ColumnIndex].Name.Equals(AdjustementsColumn)))
                    {
                        if (!string.IsNullOrEmpty(e.Value.ToString()))
                        {
                            if ((double)e.Value == 0.0)
                            {
                                e.Value = "";
                            }
                        }
                    }
                }
                else
                {
                       //if ((grid.Columns[e.ColumnIndex].Name.Equals(ChargesColumn)) ||
                       if((grid.Columns[e.ColumnIndex].Name.Equals(PaymentsColumn)) ||
                            (grid.Columns[e.ColumnIndex].Name.Equals(AdjustementsColumn)))
                    {
                        if (!string.IsNullOrEmpty(e.Value.ToString()))
                        {
                            if ((double)e.Value == 0.0)
                            {
                                e.Value = "";
                            }
                        }
                    }
                }

                
            }
        }

        /// <summary>
        /// It creates all action events in the UI
        /// </summary>
        private void CreateActionButtons()
        {
            accountManagementFooterUI = new AccountManagementFooterUI();
            accountManagementFooterUI.Dock = DockStyle.Fill;

            accountManagementFooterUI.PaymentsButton.Click += new EventHandler(PaymentsButton_Click);
            accountManagementFooterUI.AdjustmentsButton.Click += new EventHandler(AdjustmentsButton_Click);
            accountManagementFooterUI.StatementsButton.Click += new EventHandler(StatementsButton_Click);
            accountManagementFooterUI.RefundButton.Click += new EventHandler(RefundButton_Click);
        }

        /// <summary>
        /// It enables the buttons
        /// </summary>
        /// <param name="enable"></param>
        private void EnableButtons(bool enable)
        {
            //accountManagementFooterUI.PaymentsButton.Enabled = enable;
            //accountManagementFooterUI.AdjustmentsButton.Enabled = enable;
            accountManagementFooterUI.StatementsButton.Enabled = enable;
            accountManagementFooterUI.RefundButton.Enabled = enable;
            ViewButton.Enabled = enable;
        }

        /// <summary>
        /// User click on refund button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void RefundButton_Click(object sender, EventArgs e)
        {
            ShowRefundDialog(null, false);
        }

        /// <summary>
        /// User clicks on statements button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void StatementsButton_Click(object sender, EventArgs e)
        {
            try
            {
                ShowStatementDialog();
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                OutputFileDialog.CloseSplashScreen();
                OutputPrintDialog.CloseSplashScreen();
                OutputFaxDialog.CloseSplashScreen();
                OutputEmailDialog.CloseSplashScreen();
                OutputDiscDialog.CloseSplashScreen();
                
            }
            
        }

        /// <summary>
        /// User clicks on adjustments button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void AdjustmentsButton_Click(object sender, EventArgs e)
        {
            try
            {
				//CR# 385093
                int index = 0;
                int scrollPosition = 0;
                var selectionItem = new RequestInvoiceDetail();
                if ((grid.Items != null) && (grid.Items.Count > 0))
                {                    
                    index = grid.SelectedRows[0].Index;
                    scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                }
                ShowAdjustmentDialog(true);
                Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
                if (reqInvoices.Count > 0)
                {
                    list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                    list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                    selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
                    SetData(list, requestorDetails);
                }
                else
                {
                    SetData(null, requestorDetails);
                }
				//CR# 385093
                RetainGridSelection(index,scrollPosition, selectionItem);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// User click on payments button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PaymentsButton_Click(object sender, EventArgs e)
        {
            ShowPaymentsDialog(null, true);
        }

        /// <summary>
        /// Shows the payment dialog.
        /// </summary>
        private void ShowPaymentsDialog(object data, bool isPaymentCreation)
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectRequestorHandler = new EventHandler(Process_SelectPayment);
                EventHandler cancelHandler = new EventHandler(Process_CancelPayment);

                paymentUI = new PaymentUI(selectRequestorHandler, cancelHandler, Pane);

                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("payments.titlebar.text"), paymentUI);
                Collection<PaymentMethodDetails> paymentMethods = BillingAdminController.Instance.RetrieveAllPaymentMethods(false);                
                List<RequestInvoiceDetail> list = new List<RequestInvoiceDetail>(reqInvDetail);
                List<RequestInvoiceDetail> requestInvoices = list.FindAll(delegate(RequestInvoiceDetail item) { return item.Balance > 0; });
                List<RequestInvoiceDetail> requestInvoiceDetails = new List<RequestInvoiceDetail>();

                RequestInvoiceDetail invoiceDetail = new RequestInvoiceDetail();
                //CR#377284
                long paymentId = 0;
                if (grid.Items != null)
                {
                    //CR#377425
                    if (grid.Items.Count != 0)
                    {
                        invoiceDetail = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
                        paymentId = invoiceDetail.Id;
                        if (!isPaymentCreation)
                        {
                            if ((grid.SelectedCells[1].FormattedValue.ToString() == "Unapplied Payment"))
                            {
                                foreach (RequestInvoiceDetail req in list)
                                {
                                    //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                                    if ((req.InvoiceType.Equals("Open Invoice")) || (req.InvoiceType.Equals("Closed Invoice")) || (req.InvoiceType.Equals("Prebill")))
                                    {
                                        foreach (RequestorAdjustmentsPaymentsDetail reqAdj in req.ReqAdjPay)
                                        {
                                            if (paymentId == reqAdj.PaymentId)
                                            {
                                                req.AppliedAmount = reqAdj.AppliedAmount;
                                                req.AppliedAmountCopy = reqAdj.AppliedAmount;
                                                break;
                                            }
                                        }
                                        if (req.HasMaskedRequestorFacility || req.HasBlockedRequestorFacility)
                                        {
                                            appliedAmount += req.AppliedAmount;
                                        }
                                        if (req.AppliedAmount > 0 || req.Balance > 0)
                                        {
                                            requestInvoiceDetails.Add(req);
                                        }
                                    }

                                }

                            }
                        }
                        else
                        {
                            foreach (RequestInvoiceDetail req in requestInvoices)
                            {
                                //US16834 - changes to Include requests in the pre-bill status on the payments popup.
                                if ((req.InvoiceType.Equals("Open Invoice")) || (req.InvoiceType.Equals("Prebill")))
                                {
                                    requestInvoiceDetails.Add(req);
                                }
                            }
                        }
                    }
                }
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetail = new ComparableCollection<RequestInvoiceDetail>(requestInvoiceDetails);

                paymentUI.PrePopulate(paymentMethods, requestInvoiceDetail, requestorDetails.Id, isPaymentCreation, requestorDetails);
                paymentUI.SetData(data, paymentId, appliedAmount);
                this.appliedAmount = 0;
				//CR# 385093
                int index = 0;
                int scrollPosition = 0;
                DialogResult result = form.ShowDialog(this);
                if ((result == DialogResult.Cancel) || (result == DialogResult.OK))
                {
                    form.Close();
                }
                //CR#377248
                var selectionItem = new RequestInvoiceDetail();
                if (grid.Items != null)
                {
                    //CR#377425
                    if (grid.Items.Count != 0)
                    {
                        selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
						//CR# 385093
                        index = grid.SelectedRows[0].Index;
                        scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                        sortedColumn = grid.SortedColumn.DataPropertyName;
                    }
                }
                direction = (grid.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                //Refresh the requestor account register grid.
                Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
                //CR#380459
                requestInvoiceDetailList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                requestInvoiceDetailList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                PopulateData(requestInvoiceDetailList);
                reqInvDetail = requestInvoiceDetailList;
                RequestEvents.OnReleaseInfoUIChanged(null, null);

                //CR# 385093
                if ((grid.Items != null) && (grid.Items.Count > 0))
                {
                    accountManagementFooterUI.StatementsButton.Enabled = true;
                    RetainGridSelection(index, scrollPosition, selectionItem);
                }
                else
                {
                    accountManagementFooterUI.StatementsButton.Enabled = false;
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Shows the adjustments dialog.
        /// </summary>
        private void ShowAdjustmentDialog(bool isAdjCreation)
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectAdjustmentHandler = new EventHandler(Process_SelectAdjsustment);
                EventHandler cancelHandler = new EventHandler(Process_CancelAdjsustment);

                adjustmentUI = new AdjustmentsUI(selectAdjustmentHandler, cancelHandler, Pane);

                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("adjustments.titlebar.text"), adjustmentUI);
                form.FormClosing += delegate { adjustmentUI.CleanUp(); };
                if (isAdjCreation)
                {
                    AdjustmentInfoDetail adjInfoDetail = new AdjustmentInfoDetail();
                    //if (requestorDetails != null)
                    //{
                        adjInfoDetail = RequestorController.Instance.RetrieveAdjustmentInfo(requestorDetails.Id);
                        Collection<RequestInvoiceDetail> requestInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);                        
                        adjInfoDetail.RequestorInvoicesList = requestInvoices;

                        if (adjInfoDetail != null)
                        {
                            adjustmentUI.SetData(adjInfoDetail, requestorDetails.Id, requestorDetails);
                        }
                    //}
                }
                else
                {
                    RequestInvoiceDetail invoiceDetail = new RequestInvoiceDetail();
                    invoiceDetail = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
                    long adjustmentId = invoiceDetail.Id;
                    adjustmentUI.SetAdjData(adjustmentId, requestorDetails.Id, invoiceDetail.AdjAmount, false, requestorDetails);
                }
                int scrollPosition = 0;
				//CR# 385093
                if ((grid.Items != null) && (grid.Items.Count > 0))
                {
                    scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                }
                //int scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                int index = 0;
                DialogResult result = form.ShowDialog(this);
                if ((result == DialogResult.Cancel) || (result == DialogResult.OK))
                {
                    form.Close();
                    RequestorCache.RemoveKey(requestorDetails.Id);
                }
                var selectionItem = new RequestInvoiceDetail();
                if (grid.SelectedRows.Count != 0)
                {
                    selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
				    //CR# 385093
                    index = grid.SelectedRows[0].Index;
                    sortedColumn = grid.SortedColumn.DataPropertyName;
                }
                direction = (grid.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                //Refresh the requestor account register grid.
                Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
                //CR#380459
                requestInvoiceDetailList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                requestInvoiceDetailList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                PopulateData(requestInvoiceDetailList);
                reqInvDetail = requestInvoiceDetailList;

                RequestEvents.OnReleaseInfoUIChanged(null, null);

                //CR# 385093
                if (grid.Items != null)
                {
                    if (grid.Items.Count > 0)
                    {
                        accountManagementFooterUI.StatementsButton.Enabled = true;
                        RetainGridSelection(index, scrollPosition, selectionItem);
                    }
                    else
                    {
                        accountManagementFooterUI.StatementsButton.Enabled = false;
                    }
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Shows the statement dialog
        /// </summary>
        private void ShowStatementDialog()
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectStatementHandler = new EventHandler(Process_SelectStatement);
                EventHandler cancelHandler = new EventHandler(Process_CancelStatement);

                statementDialog = new StatementDialog(selectStatementHandler, cancelHandler, Pane);

                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("Statement.titlebar.text"), statementDialog);
                Collection<LetterTemplateDetails> letterTemplateList = ROIAdminController.Instance.RetrieveAllLetterTemplates();

                IList<LetterTemplateDetails> requestorLetterTemplates = ROIViewUtility.RetrieveLetterTemplates(letterTemplateList, LetterType.RequestorStatement.ToString());

                long defaultRequestorLetterId = ROIViewUtility.RetrieveDefaultId(requestorLetterTemplates);
                statementDialog.PrePopulate(requestorLetterTemplates, defaultRequestorLetterId, requestorDetails);
                form.FormClosing += delegate { statementDialog.CleanUp(); };
                DialogResult result = form.ShowDialog(this);
                if (result == DialogResult.Cancel)
                {
                    form.Close();
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Shows the refund dialog.
        /// </summary>
        private void ShowRefundDialog(object data, bool isRefundCreation)
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectRefundHandler = new EventHandler(Process_SelectRefund);
                EventHandler cancelHandler = new EventHandler(Process_CancelRefund);
                refundUI = new RefundUI(selectRefundHandler, cancelHandler, Pane, requestorDetails, refundAmount);
                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("Refund.titlebar.text"), refundUI);
                form.FormClosing += delegate { refundUI.CleanUp(); };
                if (data != null)
                {
                    refundUI.SetData(data);
                }
                DialogResult result = form.ShowDialog(this);
                if (result == DialogResult.Cancel || result == DialogResult.OK)
                {
                    form.Close();
                }
                var selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
				//CR# 385093
                int index = grid.SelectedRows[0].Index;
                int scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                sortedColumn = grid.SortedColumn.DataPropertyName;
                direction = (grid.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                //Refresh the requestor account register grid.
                Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
                //CR#380459
                requestInvoiceDetailList.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                requestInvoiceDetailList.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                PopulateData(requestInvoiceDetailList);
                reqInvDetail = requestInvoiceDetailList;
				//CR# 385093
                RetainGridSelection(index,scrollPosition, selectionItem);

                RequestEvents.OnReleaseInfoUIChanged(null, null);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                OutputFileDialog.CloseSplashScreen();
                OutputPrintDialog.CloseSplashScreen();
                OutputFaxDialog.CloseSplashScreen();
                OutputEmailDialog.CloseSplashScreen();
                OutputDiscDialog.CloseSplashScreen();
               
            }
        }

        /// <summary>
        /// Shows the invoice Dialog
        /// </summary>
        private void ShowInvoiceDialog()
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectAdjustmentHandler = new EventHandler(Process_SelectAdjsustment);
                EventHandler cancelHandler = new EventHandler(Process_CancelAdjsustment);

                invoiceUI = new InvoiceUI(selectAdjustmentHandler, cancelHandler, Pane);

                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("invoice.titlebar.text") + " - " + invoiceId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), invoiceUI);
                form.FormClosing += delegate { invoiceUI.CleanUp(); };

                RequestInvoiceDetail invDetail = new RequestInvoiceDetail();
                invDetail = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;

                Collection<RequestInvoiceDetail> reqInvoices = new Collection<RequestInvoiceDetail>();
                reqInvoices.Add(invDetail);
                Collection<RequestorAdjustmentsPaymentsDetail> reqAdjPays = new Collection<RequestorAdjustmentsPaymentsDetail>();
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in invDetail.ReqAdjPay)
                {
                    reqAdjPays.Add(reqAdjPay);
                }

                //Code for Total Charges

                ComparableCollection<RequestorAdjustmentsPaymentsDetail> list = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>(reqAdjPays);

                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Ascending);
                //list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Descending);
                ComparableCollection<RequestorAdjustmentsPaymentsDetail> requestorAdjustmentsPaymentsDetail = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>();
                RequestorAdjustmentsPaymentsDetail reqTotalAdjPay = new RequestorAdjustmentsPaymentsDetail();
                reqTotalAdjPay.AppliedAmount = invDetail.Charges;
                reqTotalAdjPay.Date = invDetail.CreatedDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture); 
                reqTotalAdjPay.TxnType = "Total Charges";
                requestorAdjustmentsPaymentsDetail.Add(reqTotalAdjPay);
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in list)
                {
                    requestorAdjustmentsPaymentsDetail.Add(reqAdjPay);
                }
                invoiceUI.SetData(requestorAdjustmentsPaymentsDetail, invDetail, requestorDetails.Id, requestorDetails);
                int scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                DialogResult result = form.ShowDialog(this);
                if (result == DialogResult.Cancel)
                {
                    form.Close();
                }
                var selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
				//CR# 385093
                int index = grid.SelectedRows[0].Index;
                direction = (grid.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                sortedColumn = grid.SortedColumn.DataPropertyName;
                Collection<RequestInvoiceDetail> reqInvoiceList = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(reqInvoiceList);
                PopulateData(requestInvoiceDetailList);
                reqInvDetail = requestInvoiceDetailList;
                RequestEvents.OnReleaseInfoUIChanged(null, null);
                //CR# 385093
                RetainGridSelection(index,scrollPosition, selectionItem);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        /// <summary>
        /// Shows the invoice Dialog
        /// </summary>
        private void ShowpreBillDialog()
        {
            try
            {
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

                EventHandler selectAdjustmentHandler = new EventHandler(Process_SelectAdjsustment);
                EventHandler cancelHandler = new EventHandler(Process_CancelAdjsustment);

                invoiceUI = new InvoiceUI(selectAdjustmentHandler, cancelHandler, Pane);

                Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("prebill.titlebar.text") + " - " + invoiceId.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), invoiceUI);
                form.FormClosing += delegate { invoiceUI.CleanUp(); };

                RequestInvoiceDetail invDetail = new RequestInvoiceDetail();
                invDetail = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;

                Collection<RequestInvoiceDetail> reqInvoices = new Collection<RequestInvoiceDetail>();
                reqInvoices.Add(invDetail);
                Collection<RequestorAdjustmentsPaymentsDetail> reqAdjPays = new Collection<RequestorAdjustmentsPaymentsDetail>();
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in invDetail.ReqAdjPay)
                {
                    reqAdjPays.Add(reqAdjPay);
                }

                //Code for Total Charges

                ComparableCollection<RequestorAdjustmentsPaymentsDetail> list = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>(reqAdjPays);

                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Ascending);
                //list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Descending);
                ComparableCollection<RequestorAdjustmentsPaymentsDetail> requestorAdjustmentsPaymentsDetail = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>();
                RequestorAdjustmentsPaymentsDetail reqTotalAdjPay = new RequestorAdjustmentsPaymentsDetail();
                reqTotalAdjPay.AppliedAmount = invDetail.Charges;
                reqTotalAdjPay.Date = invDetail.CreatedDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                reqTotalAdjPay.TxnType = "Total Charges";
                requestorAdjustmentsPaymentsDetail.Add(reqTotalAdjPay);
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in list)
                {
                    requestorAdjustmentsPaymentsDetail.Add(reqAdjPay);
                }
                invoiceUI.SetData(requestorAdjustmentsPaymentsDetail, invDetail, requestorDetails.Id, requestorDetails);
                int scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                DialogResult result = form.ShowDialog(this);
                if (result == DialogResult.Cancel)
                {
                    form.Close();
                }
                var selectionItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
                //CR# 385093
                int index = grid.SelectedRows[0].Index;
                direction = (grid.SortOrder == SortOrder.Ascending) ? ListSortDirection.Ascending : ListSortDirection.Descending;
                sortedColumn = grid.SortedColumn.DataPropertyName;
                Collection<RequestInvoiceDetail> reqInvoiceList = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(reqInvoiceList);
                PopulateData(requestInvoiceDetailList);
                reqInvDetail = requestInvoiceDetailList;
                RequestEvents.OnReleaseInfoUIChanged(null, null);
                //CR# 385093
                RetainGridSelection(index, scrollPosition, selectionItem);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void RetainGridSelection(RequestInvoiceDetail selectionItem)
        {
            try
            {
                if (selectionItem != null 
						&& grid.Items != null 
						&& grid.Items.Contains(selectionItem))
                {
                    grid.SelectItem(selectionItem);
                }
                else
                {
                    SelectFirstRow();
                }
            }
            catch (ROIException) { }
        }
	    //CR# 385093
        private void RetainGridSelection(int index, int scrollIndex, RequestInvoiceDetail selectionItem)
        {
            try
            {
                if (selectionItem != null
                        && grid.Items != null
                        && grid.Items.Contains(selectionItem))
                {
                    grid.SelectRow(index);
                    grid.FirstDisplayedScrollingRowIndex = scrollIndex;
                }
                else
                {
                    SelectFirstRow();
                }
            }
            catch (ROIException) { }
        }

        private void Process_SelectPayment(object sender, EventArgs e)
        {

        }
        private void Process_CancelPayment(object sender, EventArgs e)
        {

        }

        private void Process_SelectAdjsustment(object sender, EventArgs e)
        {

        }
        private void Process_CancelAdjsustment(object sender, EventArgs e)
        {

        }

        private void Process_SelectRefund(object sender, EventArgs e)
        {

        }
        private void Process_CancelRefund(object sender, EventArgs e)
        {

        }

        private void Process_SelectStatement(object sender, EventArgs e)
        {

        }
        private void Process_CancelStatement(object sender, EventArgs e)
        {

        }
        

        /// <summary>
        /// Select first Row in the grid.
        /// </summary>
        private void SelectFirstRow()
        {
            if (grid.Rows.Count > 0)
            {
                grid.Rows[0].Selected = true;
            }
        }
        /// <summary>
        /// Occurs when user click save button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void buttonView_Click(object sender, EventArgs e)
        {
            try
            {

                var selectedItem = (RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem;
				//CR# 385093
                int index = grid.SelectedRows[0].Index;
                RequestInvoiceDetail req = selectedItem as RequestInvoiceDetail;
                if (req != null)
                    invoiceId = req.Id;
                int scrollPosition = grid.FirstDisplayedScrollingRowIndex;
                if (!req.HasMaskedRequestorFacility)
                {
                    ViewData();
                }
                Collection<RequestInvoiceDetail> reqInvoices = RequestorController.Instance.RetrieveRequestorInvoices(requestorDetails.Id);
                ComparableCollection<RequestInvoiceDetail> list = new ComparableCollection<RequestInvoiceDetail>(reqInvoices);
                //CR#380459
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
                list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestInvoiceDetail))["CreatedDate"], ListSortDirection.Ascending);
				//CR# 385093
                if (reqInvoices.Count > 0)
                {
                    SetData(list, requestorDetails);
                    RetainGridSelection(index, scrollPosition, selectedItem);
                }
                else
                {
                    SetData(null, requestorDetails);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
        }

        private void grid_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            DataGridView.HitTestInfo hti = grid.HitTest(e.X, e.Y);
            if (hti.Type != DataGridViewHitTestType.ColumnHeader)
            {
                if (grid.SelectedRows.Count != 0)
                {
                    var selectedItem = grid.SelectedRows[0].DataBoundItem;
                    RequestInvoiceDetail req = (RequestInvoiceDetail)selectedItem;
                    invoiceId = req.Id;
                    if (!req.HasMaskedRequestorFacility)
                    {
                        ViewData();
                    }
                }
            }
        }

        private void ViewData()
        {
            if (grid.SelectedCells[1].FormattedValue.ToString() == "Open Invoice" || grid.SelectedCells[1].FormattedValue.ToString() == "Closed Invoice")
            {
                ShowInvoiceDialog();
            }
            else if (grid.SelectedCells[1].FormattedValue.ToString() == "Unapplied Payment")
            {
                ShowPaymentsDialog(grid.SelectedRows[0].DataBoundItem, false);
            }
            else if (grid.SelectedCells[1].FormattedValue.ToString() == "Refund")
            {
                ShowRefundDialog((RequestInvoiceDetail)grid.SelectedRows[0].DataBoundItem, true);
            }
            else if (grid.SelectedCells[1].FormattedValue.ToString() == "Prebill")
            {
                ShowpreBillDialog();
            }
            else
            {
                ShowAdjustmentDialog(false);
            }
        }


        #endregion

        #region Properties

        /// <summary>
        /// Gets or sets the requestor name.
        /// </summary>
        public string RequestorName
        {
            get { return requestorName; }
            set { requestorName = value; }
        }

        public AccountManagementFooterUI Footer
        {
            get { return accountManagementFooterUI; }
        }


        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return accountManagementFooterUI;
        }

        #endregion       

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            accountManagementFooterUI.AdjustmentsButton.Enabled = IsAllowed(ROISecurityRights.ROIAdjustCharges);
            accountManagementFooterUI.PaymentsButton.Enabled = IsAllowed(ROISecurityRights.ROIPostPayment);
        }

        #endregion
    }
}
