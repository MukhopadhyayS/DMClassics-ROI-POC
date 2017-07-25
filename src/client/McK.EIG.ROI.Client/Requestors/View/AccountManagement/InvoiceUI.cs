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
using System.Drawing;
using System.Resources;
using System.Windows.Forms;
using System.Globalization;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Admin.Controller;
using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using McK.EIG.ROI.Client.Requestors.View;
using McK.EIG.ROI.Client.Requestors.View.RequestorInfo;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Web_References.OverDueInvoiceWS;
using System.Collections.Generic;
using System.Text;
using McK.EIG.Common.Audit.Model;
using McK.EIG.ROI.Client.Base.Controller;
using System.IO;
using McK.EIG.ROI.Client.Request.View;

namespace McK.EIG.ROI.Client.Requestors.View.AccountManagement
{
    /// <summary>
    /// Maintains requestor profile
    /// </summary>
    public partial class InvoiceUI : ROIBaseUI
    {
        #region Fields

        private const string paymentIdColumn = "paymentId";
        private const string TypeColumn = "type";
        private const string AmountColumn = "amount";
        private const string DateColumn = "date";
        private const string ButtonColumn = "charges";

        private PaymentUI paymentUI;
        private AdjustmentsUI adjustmentUI;
        private RequestInvoiceDetail reqInv;
        private long requestorId;
        private long invoiceId;
        private double appliedAmount;
        private int paymentId;
        ComparableCollection<RequestorAdjustmentsPaymentsDetail> reqAdjPayList = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>();
        private double deniedInvoicesAppliedAmount;
        internal static EventHandler ProgressHandler;
        private RequestorDetails requestorDetails;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize the controls
        /// </summary>
        public InvoiceUI()
        {
            InitializeComponent();
            InitGrid();
        }

        /// <summary>
        /// Initialize the action handler
        /// </summary>
        /// <param name="selectRequestorHandler"></param>
        /// <param name="cancelHandler"></param>
        public InvoiceUI(EventHandler selectRequestorHandler, EventHandler cancelHandler, IPane pane)
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
            SetLabel(rm, dateCreatedLabel);
            SetLabel(rm, statusLabel);
            SetLabel(rm, totalChargesLabel);
            SetLabel(rm, unbillableLabel);
            SetLabel(rm, totalAdjustmentsLabel);
            SetLabel(rm, totalPaymentLabel);
            SetLabel(rm, okButton);
            SetLabel(rm, viewInvoiceButton);
        }

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            DataGridViewTextBoxColumn paymentId =  invoiceGrid.AddTextBoxColumn(paymentIdColumn, "PaymentId", "PaymentId", 10);
            paymentId.Visible = false;
            invoiceGrid.AddTextBoxColumn(TypeColumn, "", "TxnType", 90);
            DataGridViewTextBoxColumn dgvPaymentAmountColumn = invoiceGrid.AddTextBoxColumn(AmountColumn, "Amount", "AppliedAmount", 130);
            dgvPaymentAmountColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            dgvPaymentAmountColumn.DefaultCellStyle.Format = "C";
            DataGridViewTextBoxColumn dgvDateColumn=invoiceGrid.AddTextBoxColumn(DateColumn, "Date", "Date", 150);
            dgvDateColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopRight;
            dgvDateColumn.ReadOnly = true;
            DataGridViewButtonColumn dgvButtonColumn = new DataGridViewButtonColumn();
            dgvButtonColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            invoiceGrid.Columns.Add(dgvButtonColumn);


            dgvButtonColumn.Text = "Edit";
            dgvButtonColumn.Name = "";
            
            dgvButtonColumn.UseColumnTextForButtonValue = true;
            dgvButtonColumn.FlatStyle = FlatStyle.Popup;

            dgvPaymentAmountColumn.ReadOnly = true;

            // Add a CellClick handler to handle clicks in the button column.
            invoiceGrid.CellClick +=
                new DataGridViewCellEventHandler(invoiceGrid_EditButtonClick);

        }

        private void grid_RowSelected(DataGridViewRow row)
        {
            row.Selected = false;
        }

        void invoiceGrid_EditButtonClick(object sender, DataGridViewCellEventArgs e)
        {
            if (!(e.RowIndex <= 0 || e.ColumnIndex != invoiceGrid.Columns[invoiceGrid.Columns.Count-1].Index))
            {
                if (invoiceGrid.Rows[e.RowIndex].Cells[1].Value.ToString() == "Adjustment")
                {
                    appliedAmount = double.Parse(invoiceGrid.Rows[e.RowIndex].Cells[2].Value.ToString());
                    RequestorAdjustmentsPaymentsDetail requestorAdjustmentsPayments = (RequestorAdjustmentsPaymentsDetail)invoiceGrid.Rows[e.RowIndex].DataBoundItem;
                    paymentId=Convert.ToInt32(requestorAdjustmentsPayments.PaymentId.ToString());
                    ShowAdjustmentDialog(requestorAdjustmentsPayments.PaymentId);              
                    return;
                }
               paymentId = Convert.ToInt32(invoiceGrid.Rows[e.RowIndex].Cells["PaymentId"].Value);
               // if(invoiceGrid.Rows[e.RowIndex].Cells[0].Value=="")
               RequestorAdjustmentsPaymentsDetail requestorAdjustmentsPaymentsDetail =  (RequestorAdjustmentsPaymentsDetail)invoiceGrid.Rows[e.RowIndex].DataBoundItem;
               ShowPaymentsDialog(requestorAdjustmentsPaymentsDetail, paymentId); 
            }
        }
        /// <summary>
        /// Shows the adjustments dialog.
        /// </summary>
        private void ShowAdjustmentDialog(long adjustmentid)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            EventHandler selectAdjustmentHandler = new EventHandler(Process_SelectAdjsustment);
            EventHandler cancelHandler = new EventHandler(Process_CancelAdjsustment);

            adjustmentUI = new AdjustmentsUI(selectAdjustmentHandler, cancelHandler, Pane);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("adjustments.titlebar.text"), adjustmentUI);
            form.FormClosing += delegate { adjustmentUI.CleanUp(); };
            adjustmentUI.SetAdjData(adjustmentid, requestorId, appliedAmount,true, this.requestorDetails);
            DialogResult result = form.ShowDialog(this);
            
            if( (result == DialogResult.OK))
            {
                ComparableCollection<RequestInvoiceDetail> requestInvoiceDetails = new ComparableCollection<RequestInvoiceDetail>();
                requestInvoiceDetails = ShowData((int)adjustmentid);
                Collection<RequestorAdjustmentsPaymentsDetail> reqAdjPays = new Collection<RequestorAdjustmentsPaymentsDetail>();
                RequestInvoiceDetail selectedRequestInvoiceDetail = new RequestInvoiceDetail();
                foreach (RequestInvoiceDetail invoiceDetail in requestInvoiceDetails)
                {
                    if (invoiceDetail.Id == this.invoiceId)
                    {
                        selectedRequestInvoiceDetail = invoiceDetail;
                        break;
                    }
                }
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in selectedRequestInvoiceDetail.ReqAdjPay)
                {
                    reqAdjPays.Add(reqAdjPay);
                }
                ComparableCollection<RequestorAdjustmentsPaymentsDetail> list = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>(reqAdjPays);
                list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Ascending);
                ComparableCollection<RequestorAdjustmentsPaymentsDetail> requestorAdjustmentsPaymentsDetails = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>();
                RequestorAdjustmentsPaymentsDetail reqTotalAdjPay = new RequestorAdjustmentsPaymentsDetail();
                reqTotalAdjPay.AppliedAmount = selectedRequestInvoiceDetail.Charges;
                reqTotalAdjPay.Date = selectedRequestInvoiceDetail.CreatedDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
                reqTotalAdjPay.TxnType = "Total Charges";
                requestorAdjustmentsPaymentsDetails.Add(reqTotalAdjPay);
                foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in list)
                {
                    requestorAdjustmentsPaymentsDetails.Add(reqAdjPay);
                }
                SetData(requestorAdjustmentsPaymentsDetails, selectedRequestInvoiceDetail, this.requestorId, this.requestorDetails);
            }
            else
            if ((result == DialogResult.Cancel))
            {
                form.Close();
            }
        }
        /// <summary>
        /// Shows the payment dialog.
        /// </summary>
        private void ShowPaymentsDialog(RequestorAdjustmentsPaymentsDetail requestorAdjustmentsPaymentsDetail,int paymentId)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            EventHandler selectRequestorHandler = new EventHandler(Process_SelectPayment);
            EventHandler cancelHandler = new EventHandler(Process_CancelPayment);

            paymentUI = new PaymentUI(selectRequestorHandler, cancelHandler, Pane);

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("payments.titlebar.text"), paymentUI);

            Collection<PaymentMethodDetails> paymentMethods = BillingAdminController.Instance.RetrieveAllPaymentMethods(false);
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetails = ShowData(paymentId);
            paymentUI.PrePopulate(paymentMethods, requestInvoiceDetails, 0, false, this.requestorDetails);
            paymentUI.SetData(requestorAdjustmentsPaymentsDetail, 0, this.deniedInvoicesAppliedAmount);
            this.deniedInvoicesAppliedAmount = 0;

            DialogResult result = form.ShowDialog(this);
            form.Close();
            if (result == DialogResult.Cancel)
            {
                form.Close();
            }
            requestInvoiceDetails = ShowData(paymentId);
            Collection<RequestorAdjustmentsPaymentsDetail> reqAdjPays = new Collection<RequestorAdjustmentsPaymentsDetail>();
            RequestInvoiceDetail selectedRequestInvoiceDetail = new RequestInvoiceDetail();
            foreach (RequestInvoiceDetail invoiceDetail in requestInvoiceDetails)
            {
                if (invoiceDetail.Id == this.invoiceId)
                {
                    selectedRequestInvoiceDetail = invoiceDetail;
                    break;
                }
            }            
            foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in selectedRequestInvoiceDetail.ReqAdjPay)
            {
                reqAdjPays.Add(reqAdjPay);
            }
            ComparableCollection<RequestorAdjustmentsPaymentsDetail> list = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>(reqAdjPays);
            list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestorAdjustmentsPaymentsDetail))["Date"], ListSortDirection.Ascending);
            ComparableCollection<RequestorAdjustmentsPaymentsDetail> requestorAdjustmentsPaymentsDetails = new ComparableCollection<RequestorAdjustmentsPaymentsDetail>();
            RequestorAdjustmentsPaymentsDetail reqTotalAdjPay = new RequestorAdjustmentsPaymentsDetail();
            reqTotalAdjPay.AppliedAmount = selectedRequestInvoiceDetail.Charges;
            reqTotalAdjPay.Date = selectedRequestInvoiceDetail.CreatedDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture); 
            reqTotalAdjPay.TxnType = "Total Charges";
            requestorAdjustmentsPaymentsDetails.Add(reqTotalAdjPay);
            foreach (RequestorAdjustmentsPaymentsDetail reqAdjPay in list)
            {
                requestorAdjustmentsPaymentsDetails.Add(reqAdjPay);
            }
            SetData(requestorAdjustmentsPaymentsDetails, selectedRequestInvoiceDetail, this.requestorId, this.requestorDetails);
            this.deniedInvoicesAppliedAmount = 0;
        }

        private ComparableCollection<RequestInvoiceDetail> ShowData(int paymentId)
        {
            Collection<RequestInvoiceDetail> reqInvoicesDetails = RequestorController.Instance.RetrieveRequestorInvoices(this.requestorId);
            ComparableCollection<RequestInvoiceDetail> requestInvoice = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail req in reqInvoicesDetails)
            {
                if ((req.InvoiceType.Equals("Open Invoice")) || (req.InvoiceType.Equals("Closed Invoice")) ||(req.InvoiceType.Equals("Prebill")))
                {
                    requestInvoice.Add(req);
                }
            }
            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetailList = new ComparableCollection<RequestInvoiceDetail>(requestInvoice);

            ComparableCollection<RequestInvoiceDetail> requestInvoiceDetails = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail req in requestInvoiceDetailList)
            {
                foreach (RequestorAdjustmentsPaymentsDetail reqAdj in req.ReqAdjPay)
                {
                    if (reqAdj.PaymentId == paymentId)
                    {
                        req.AppliedAmount = reqAdj.AppliedAmount;
                        req.AppliedAmountCopy = reqAdj.AppliedAmount;
                        if (req.HasBlockedRequestorFacility || req.HasMaskedRequestorFacility)
                        {
                            this.deniedInvoicesAppliedAmount += reqAdj.AppliedAmount;
                        }
                    }
                }
                requestInvoiceDetails.Add(req);
            }
            ComparableCollection<RequestInvoiceDetail> openRequestInvoiceDetails = new ComparableCollection<RequestInvoiceDetail>();
            foreach (RequestInvoiceDetail req in requestInvoiceDetails)
            {
                if (req.Balance > 0 || req.AppliedAmount > 0)
                {
                    openRequestInvoiceDetails.Add(req);
                }
            }
            return openRequestInvoiceDetails;
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

        public void SetData(object data,RequestInvoiceDetail reqInv, long requestorId, RequestorDetails requestorDetails)
        {
            DisableEvents();
            if (data == null)
            {
                //EnableButtons(false);
                return;
            }
            Localize();
            PopulateData(data);
            reqAdjPayList = (ComparableCollection<RequestorAdjustmentsPaymentsDetail>)data;
            dateCreatedLabelValue.Text = reqInv.CreatedDate.ToString(ROIConstants.DateFormat, CultureInfo.InvariantCulture);
            statusLabelValue.Text = reqInv.InvoiceStatus;
            totalChargeLabelValue.Text = reqInv.Charges.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            unbillableLabelValue.Text = reqInv.UnBillableAmount.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            totalAdjustmentsLabelValue.Text = (-reqInv.Adjustments).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            totalPaymentsLabelValue.Text = (-reqInv.Payments).ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            currentBalanceLabelValue.Text = reqInv.Balance.ToString("C", System.Threading.Thread.CurrentThread.CurrentUICulture);
            EnableEvents();
            this.Enabled = true;
            this.reqInv = reqInv;
            this.requestorId = requestorId;
            this.invoiceId = reqInv.Id;
            this.requestorDetails = requestorDetails;

            //SelectFirstRow();
        }

        public void PopulateData(object data)
        {
            ROIViewUtility.MarkBusy(true);
            if (data == null) return;
            invoiceGrid.SelectionHandler = new EIGDataGrid.RowSelectionHandler(grid_RowSelected);
            try
            {
                invoiceGrid.SetItems((IFunctionCollection)data);

            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Select first Row in the grid.
        /// </summary>
        private void SelectFirstRow()
        {
            if (invoiceGrid .Rows.Count > 0)
            {
                invoiceGrid.Rows[0].Selected = true;
            }
        }

        public void CleanUp()
        {

        }

        private void EnableEvents()
        {

        }

        private void DisableEvents()
        {

        }

        private void MarkDirty(object sender, EventArgs e)
        {

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

        

        private void invoiceGrid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            EIGDataGrid eigDataGrid = sender as EIGDataGrid;

            foreach (DataGridViewRow objCurrRow in eigDataGrid.Rows)
            {
                Object obj = objCurrRow.Cells[1].Value;
                if (obj is string && "Total Charges".Equals(obj))
                {
                    ((DataGridViewButtonCell)objCurrRow.Cells[4]).UseColumnTextForButtonValue = false;
                    DataGridViewTextBoxCell txtcell = new DataGridViewTextBoxCell();
                    objCurrRow.Cells[4] = txtcell;
                    break;
                }

            }
        }

        private void invoiceGrid_DataSourceChanged(object sender, EventArgs e)
        {
            invoiceGrid.SortEnabled = false;
            invoiceGrid.ReadOnly = true;
        }
        #endregion

        private void viewInvoiceButton_Click(object sender, EventArgs e)
        {
            DocumentInfo documentInfo;
            documentInfo = RequestorController.Instance.ViewRequestorDetails(this.invoiceId, this.reqInv.RequestId, "DOCX", "Invoice", LetterType.Invoice.ToString());
            ROIViewer viewer = new ROIViewer(Pane, LetterType.Invoice.ToString(), GetType().Name);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            Form dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + LetterType.Invoice + ".preview") +
                                                             " " + this.reqInv.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), viewer);
            string filePath = string.Empty;
            try
            {
                filePath = BillingController.DownloadLetterTemplate(documentInfo.Name, ProgressHandler);
                viewer.PDFDocumentViewer.SerialNumber = "PDFVW4WIN-ENMBG-1CA2A-9Z3DV-RVH0Y-24K1M";
                //CR#391890
                if (Path.GetExtension(filePath) == ".pdf")
                {
                    viewer.PDFPageViewer.Visible = true;
                    viewer.DocumentViewer.Visible = false;
                    viewer.PDFDocumentViewer.Load(filePath);
                }
                else
                {
                    viewer.DocumentViewer.Visible = true;
                    viewer.PDFPageViewer.Visible = false;
                    viewer.DocumentViewer.Url = new Uri(filePath);
                }      
                viewer.ReleaseDialog = false;
                RequestorRSP rsp = (RequestorRSP)Pane.ParentPane.ParentPane;
                DialogResult result = dialog.ShowDialog(this);
                dialog.Close();

                if (result == DialogResult.OK)
                {
                    //Item prebillInvoiceDetails = BillingController.Instance.RetrieveInvoiceDetails(invoiceHistoryDetails.InvoiceNumber);
                    List<attributesMap> tmpAttrs = new List<attributesMap>();
                    OutputRequestDetails outputRequestDetails = new OutputRequestDetails(this.reqInv.RequestId, 0,
                                                                string.Empty, null);
                    outputRequestDetails.OutputDestinationDetails = viewer.OutputPropertyDetails.OutputDestinationDetails[0];
                    string outputMethod = outputRequestDetails.OutputDestinationDetails.Type;

                    if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        outputMethod = OutputMethod.SaveAsFile.ToString();
                    }
                    //add event history
                    CommentDetails details = new CommentDetails();

                    details.RequestId = this.reqInv.RequestId;
                    details.EventType = EventType.InvoiceSend;
                    StringBuilder notes = new StringBuilder();
                    String destinationType = outputRequestDetails.OutputDestinationDetails.Type;
                    String outputName = outputRequestDetails.OutputDestinationDetails.Name;
                    if (string.Compare(destinationType.ToString(), OutputMethod.Fax.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                    {
                        outputName = outputRequestDetails.OutputDestinationDetails.Fax;
                    }
                    string documentName;
                    documentName = documentInfo.Name;


                    if (!string.IsNullOrEmpty(notes.ToString()))
                    {
                        details.EventRemarks = documentName.Substring(0, documentName.LastIndexOf('.')) + ": " +
                                               notes.ToString().TrimEnd(',') + ", output method: " +
                                               destinationType + ", " + outputName;
                    }
                    else
                    {
                        details.EventRemarks = documentName.Substring(0, documentName.LastIndexOf('.')) + ", output method: " +
                                               destinationType + ", " + outputName;
                    }
                    // RequestController.Instance.CreateComment(details);


                    outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(documentInfo));
                    Application.DoEvents();
                    long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails, viewer.DestinationType,
                                                                  viewer.OutputPropertyDetails.OutputViewDetails,
                                                                  false);


                    //Audit for Invoice/Prebill
                    AuditEvent auditEvent = new AuditEvent();
                    auditEvent.UserId = UserData.Instance.UserInstanceId;
                    auditEvent.EventStart = DateTime.Now;
                    auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                    auditEvent.EventId = 1;
                    auditEvent.Comment = documentInfo.Name + "," + documentInfo.Id + " for request " + reqInv.RequestId;
                    auditEvent.ActionCode = BillingPaymentInfoUI.RetrieveActionCode(outputRequestDetails.OutputDestinationDetails.Type);

                    try
                    {
                        Application.DoEvents();
                        ROIController.Instance.CreateAuditEntry(auditEvent);
                    }
                    catch (ROIException cause)
                    {
                    }

                    if (jobStatus == -200)
                    {
                        rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                        string titleText = rm.GetString("letterUnsuccessfulDialog.title");
                        string okButtonText = rm.GetString("okButton.DialogUI");
                        string messageText = rm.GetString("letterUnsuccessfulDialog.MessageText");
                        string okButtonToolTip = "";
                        ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
                    }
                }
            }
            catch (IOException cause)
            {
                ROIException fileAlreadyOpen = new ROIException(ROIErrorCodes.FileAlreadyOpen);
                ROIViewUtility.Handle(Context, fileAlreadyOpen);
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
        /// Build ROI request part details with CoverLetter or Invoice
        /// </summary>
        /// <param name="fileName"></param>
        private static RequestPartDetails BuildROIRequestPartDetails(DocumentInfo documentInfo)
        {
            RequestPartDetails requestPartDetails = new RequestPartDetails();

            requestPartDetails.ContentId = "";
            requestPartDetails.ContentSource = OutputPropertyDetails.ROIContentSource;

            PropertyDetails propertyDetails = new PropertyDetails();
            propertyDetails.FileIds = documentInfo.Type + "." + documentInfo.Id;

            requestPartDetails.PropertyLists.Add(propertyDetails);

            return requestPartDetails;
        }

    }
}
