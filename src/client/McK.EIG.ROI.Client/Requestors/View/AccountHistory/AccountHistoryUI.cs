using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;
using System.Globalization;
using McK.EIG.ROI.Client.Requestors.Model;
using System.Collections.ObjectModel;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using Apache.NMS.ActiveMQ;
using McK.EIG.ROI.Client.Web_References.OverDueInvoiceWS;
using McK.EIG.ROI.Client.Request.View.BillingPayment;
using McK.EIG.ROI.Client.Admin.Model;
using System.Resources;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.Utility.View;
using System.IO;

namespace McK.EIG.ROI.Client.Requestors.View.AccountHistory
{
    public partial class AccountHistoryUI : ROIBaseUI, IRequestorPageContext
    {

        #region Fields

        private const string StatusColumn = "status";
        private const string PreBillNumberColumn = "preBillNumber";
        private const string requestIdColumn = "RequestId";
        private const string DateCrtdColumn = "DateCreated";
        private const string AgingColumn = "Aging";
        private const string CreatedByColumn = "CreatedBy";
        private const string TemplateUsedColumn = "TemplateUsed";
        private const string RequestSecureColumn = "RequestPassword";
        private const string QueueSecureColumn = "QueuePassword";
        private const string BalanceColumn = "Balance";
        private const string DialogName = "Account History";

        private RequestorDetails requestor;

        internal static EventHandler ProgressHandler;

        #endregion

        #region Constructor
        public AccountHistoryUI()
        {
            InitializeComponent();
            InitGrid();
           // Localize();
        }

        #endregion Constructor

        #region Methods

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {

            //Invoice
            DataGridViewTextBoxColumn dgvStatusColumn1 = invoiceGrid.AddTextBoxColumn(StatusColumn, "Status", "Status", 50);
            DataGridViewTextBoxColumn dgvPreBillNumberColumn1 = invoiceGrid.AddTextBoxColumn(PreBillNumberColumn, "InvoiceNumber", "Id", 100);
            DataGridViewTextBoxColumn dgvrequestIdColumn1 = invoiceGrid.AddTextBoxColumn(requestIdColumn, "Request ID", "RequestId", 60);
            DataGridViewTextBoxColumn dgvDateCrtdColum1 = invoiceGrid.AddTextBoxColumn(DateCrtdColumn, "DateCreated", "CreatedDate", 85);
            DataGridViewTextBoxColumn dgvAgingColumn1 = invoiceGrid.AddTextBoxColumn(AgingColumn, "Due Date", "InvoiceDueDate", 70);
            DataGridViewTextBoxColumn dgvCreatedByColumn1 = invoiceGrid.AddTextBoxColumn(AgingColumn, "Created By", "CreatorName", 60);
            DataGridViewTextBoxColumn dgvTemplateColumn1 = invoiceGrid.AddTextBoxColumn(TemplateUsedColumn, "Template Used", "Template", 130);
            DataGridViewTextBoxColumn dgvRequestSecureColumn1 = invoiceGrid.AddTextBoxColumn(RequestSecureColumn, "RequestPassword", "PlainRequestPassword", 115);
            DataGridViewTextBoxColumn dgvQueueSecureColumn1 = invoiceGrid.AddTextBoxColumn(QueueSecureColumn, "QueuePassword", "PlainQueuePassword", 105);
            dgvRequestSecureColumn1.AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells;
            dgvQueueSecureColumn1.AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells;
            //CR# 376963 and CR#376925
            DataGridViewTextBoxColumn dgvBalanceColumn1 = invoiceGrid.AddTextBoxColumn(BalanceColumn, "Balance", "InvoiceBalance", 40);
            dgvBalanceColumn1.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopLeft;
            dgvBalanceColumn1.DefaultCellStyle.Format = "C";
            dgvBalanceColumn1.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvBalanceColumn1.MinimumWidth = 100;

            //prebill
            DataGridViewTextBoxColumn dgvStatusColumn = prebillGrid.AddTextBoxColumn(StatusColumn, "Status", "PrebillStatus", 50);
            DataGridViewTextBoxColumn dgvPreBillNumberColumn = prebillGrid.AddTextBoxColumn(PreBillNumberColumn, "PreBillNumber", "Id", 90);
            DataGridViewTextBoxColumn dgvrequestIdColumn = prebillGrid.AddTextBoxColumn(requestIdColumn, "Request ID", "requestId", 60);
            DataGridViewTextBoxColumn dgvDateCrtdColumn = prebillGrid.AddTextBoxColumn(DateCrtdColumn, "DateCreated", "CreatedDate", 80);
            DataGridViewTextBoxColumn dgvAgingColumn = prebillGrid.AddTextBoxColumn(AgingColumn, "Aging (Days)", "Aging", 50);
            DataGridViewTextBoxColumn dgvCreatedByColumn = prebillGrid.AddTextBoxColumn(AgingColumn, "Created By", "CreatorName", 60);
            DataGridViewTextBoxColumn dgvTemplateColumn = prebillGrid.AddTextBoxColumn(TemplateUsedColumn, "Template Used", "Template", 140);
            DataGridViewTextBoxColumn dgvRequestSecureColumn = prebillGrid.AddTextBoxColumn(RequestSecureColumn, "RequestPassword", "PlainRequestSecure", 115); 
            DataGridViewTextBoxColumn dgvQueueSecureColumn = prebillGrid.AddTextBoxColumn(QueueSecureColumn, "QueuePassword", "PlainQueueSecretWord", 105);
            DataGridViewTextBoxColumn dgvBalanceColumn = prebillGrid.AddTextBoxColumn(BalanceColumn, "Balance", "Balance", 40);
            dgvRequestSecureColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells;
            dgvQueueSecureColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells;
            dgvBalanceColumn.DefaultCellStyle.Alignment = DataGridViewContentAlignment.TopLeft;
            dgvBalanceColumn.DefaultCellStyle.Format = "C";
            dgvBalanceColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvBalanceColumn.MinimumWidth = 100;
             //statements

            DataGridViewTextBoxColumn dgvStatementIdColumn = statementGrid.AddTextBoxColumn(StatusColumn, "Statement ID", "Id", 180);
            DataGridViewTextBoxColumn dgvDateCreatedColumn = statementGrid.AddTextBoxColumn(PreBillNumberColumn, "Date Created", "CreatedDate", 200);
            DataGridViewTextBoxColumn dgvCreatedByColumn2 = statementGrid.AddTextBoxColumn(DateCrtdColumn, "Created By", "CreatorName", 190);
            DataGridViewTextBoxColumn dgvTemplateUsedColumn = statementGrid.AddTextBoxColumn(AgingColumn, "Template Used", "Template", 180);
            dgvTemplateUsedColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvTemplateUsedColumn.MinimumWidth = 100;

            invoiceGrid.AutoSize = true;
            invoiceGrid.MultiSelect = false;
            invoiceGrid.ScrollBars = ScrollBars.Both;

            prebillGrid.AutoSize = true;
            prebillGrid.MultiSelect = false;
            prebillGrid.ScrollBars = ScrollBars.Both;

        }
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, labelpreBill);
            SetLabel(rm, labelStatement);
            SetLabel(rm, labelInvoice);
            SetLabel(rm, buttonViewPrebill);
            SetLabel(rm, buttonViewInvoice);
            SetLabel(rm, buttonViewStatements);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, buttonViewPrebill);
            SetTooltip(rm, toolTip, buttonViewInvoice);
            SetTooltip(rm, toolTip, buttonViewStatements);
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

        /// Gets the localize key of the control for showing tooltip with current culture
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            return control.Name + "." + GetType().Name;
        }



        /// <summary>
        /// Event to Handle the Progress bar
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_NotifyProgress(object sender, EventArgs e)
        {
            //ShowProgress((FileTransferEventArgs)e);
        }

        public RequestorDetails Requestor
        {
            get { return requestor; }
        }


        public void SetData(object data, RequestorDetails requestor)
        {
            if (data == null) return;

            ROIViewUtility.MarkBusy(true);

            this.requestor = requestor;
            Collection<RequestorHistoryDetail> reqHistory = new Collection<RequestorHistoryDetail>();
            reqHistory = (Collection<RequestorHistoryDetail>)data;
            ComparableCollection<RequestorHistoryDetail> preBillHistory = new ComparableCollection<RequestorHistoryDetail>();
            ComparableCollection<RequestorHistoryDetail> invoiceHistory = new ComparableCollection<RequestorHistoryDetail>();
            ComparableCollection<RequestorHistoryDetail> statementHistory = new ComparableCollection<RequestorHistoryDetail>();
            foreach (RequestorHistoryDetail history in reqHistory)
            {
                if (history.Type == "Invoice")
                {
                    invoiceHistory.Add(history);
                }
                else if (history.Type == "Statement")
                {
                    statementHistory.Add(history);
                }
                else if (history.Type == "PreBill")
                {
                    preBillHistory.Add(history);
                }
            }
            prebillGrid.SetItems((IFunctionCollection)preBillHistory);
            invoiceGrid.SetItems((IFunctionCollection)invoiceHistory);
            statementGrid.SetItems((IFunctionCollection)statementHistory);

            StringBuilder labelPreBills=new StringBuilder();
            StringBuilder labelInvoices = new StringBuilder();
            StringBuilder labelStatements = new StringBuilder();
            labelPreBills.Append(labelpreBill.Text);
            labelInvoices.Append(labelInvoice.Text);
            labelStatements.Append(labelStatement.Text);
            if (prebillGrid.Items == null)
            {
                labelPreBills.Append("0");
            }
            else
            {
                labelPreBills.Append(prebillGrid.Items.Count.ToString());
            }
            labelpreBill.Text = labelPreBills.ToString();
            if (invoiceGrid.Items == null)
            {
                labelInvoices.Append("0");
            }
            else
            {
                labelInvoices.Append(invoiceGrid.Items.Count.ToString());
            }
            labelInvoice.Text = labelInvoices.ToString();
            if (statementGrid.Items == null)
            {
                labelStatements.Append("0");
            }
            else
            {
                labelStatements.Append(statementGrid.Items.Count.ToString());
            }
            labelStatement.Text = labelStatements.ToString();
            SelectFirstRow();

            ROIViewUtility.MarkBusy(false);

            //CR-377495 fix            
            buttonViewPrebill.Enabled = preBillHistory.Count > 0;   
            buttonViewInvoice.Enabled = invoiceHistory.Count > 0;
            buttonViewStatements.Enabled = statementHistory.Count > 0;

            //CR-388274 Fix
            if (invoiceGrid.SelectedRows.Count != 0)
            {
                RequestorHistoryDetail requestorHisotryDetail = (RequestorHistoryDetail)invoiceGrid.SelectedRows[0].DataBoundItem;
                buttonViewInvoice.Enabled = (!requestorHisotryDetail.IsMigrated);
            }
        }

        /// <summary>
        /// Select first Row in the grid.
        /// </summary>
        private void SelectFirstRow()
        {
            //if (invoiceGrid.Rows.Count > 0)
            //{
            //    invoiceGrid.Rows[0].Selected = true;
            //}
            
            if (invoiceGrid.Rows.Count > 0)
            {
                invoiceGrid.Rows[0].Selected = true;
                RequestorHistoryDetail requestorHisotryDetail = (RequestorHistoryDetail)invoiceGrid.SelectedRows[0].DataBoundItem;
                buttonViewInvoice.Enabled = (!requestorHisotryDetail.IsMigrated);
            }
        }

        #endregion Constructor

        private void buttonViewPrebill_Click(object sender, EventArgs e)
        {
            ViewInvoiceOrPrebill("PreBill");
            
        }

        private void buttonViewInvoice_Click(object sender, EventArgs e)
        {
            ViewInvoiceOrPrebill("Invoice");
        }

        //For Viewing Invoice/ PreBill
        private void ViewInvoiceOrPrebill(string letterType)
        {
            try
            {
                var selectedItem = new object();
                if (letterType == "Invoice")
                {
                    selectedItem = invoiceGrid.SelectedRows[0].DataBoundItem;
                }
                else if (letterType == "PreBill")
                {
                    selectedItem = prebillGrid.SelectedRows[0].DataBoundItem;
                }
                else if (letterType == "Statement")
                {
                    selectedItem = statementGrid.SelectedRows[0].DataBoundItem;
                }
                RequestorHistoryDetail req = (RequestorHistoryDetail)selectedItem;
                DocumentInfo documentInfo;
                if (letterType == "Invoice" || letterType == "PreBill")
                    documentInfo = RequestorController.Instance.ViewRequestorDetails(req.Id, req.RequestId, "DOCX", letterType, LetterType.Invoice.ToString());
                else
                    documentInfo = RequestorController.Instance.ViewRequestorDetails(req.Id, req.RequestId, "DOCX", "RequestorLetter", LetterType.RequestorStatement.ToString());
                ROIViewer viewer;
                if (letterType == "Statement")
                    viewer = new ROIViewer(Pane, LetterType.RequestorStatement.ToString(), DialogName);
                else
                    viewer = new ROIViewer(Pane, LetterType.Invoice.ToString(), DialogName);               
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                Form dialog;
                if (letterType == "Invoice")
                    dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + LetterType.Invoice + ".preview") +
                                                                 " " + req.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), viewer);
                else if (letterType == "PreBill")
                    dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + LetterType.PreBill + ".preview") +
                                                                 " " + req.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), viewer);
                else
                    dialog = ROIViewUtility.ConvertToForm(null, rm.GetString("title." + LetterType.RequestorStatement + ".preview") +
                                                                 " " + req.Id.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture), viewer);
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
                        viewer.PDFPageViewer.Visible = false;
                        viewer.DocumentViewer.Visible = true;
                        viewer.DocumentViewer.Url = new Uri(filePath);
                    }  
                    viewer.ReleaseDialog = false;
                    RequestorRSP rsp = (RequestorRSP)Pane.ParentPane.ParentPane;
                    DialogResult result = dialog.ShowDialog(this);
                    dialog.Close();

                    if (result == DialogResult.OK)
                    {

                        List<attributesMap> tmpAttrs = new List<attributesMap>();
                        OutputRequestDetails outputRequestDetails = new OutputRequestDetails(req.RequestId, 0,
                                                                    string.Empty, null);
                        outputRequestDetails.OutputDestinationDetails = viewer.OutputPropertyDetails.OutputDestinationDetails[0];

                        string outputMethod = outputRequestDetails.OutputDestinationDetails.Type;

                        if (string.Compare(outputRequestDetails.OutputDestinationDetails.Type, DestinationType.File.ToString(), true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0)
                        {
                            outputMethod = OutputMethod.SaveAsFile.ToString();
                        }


                        //add event history
                        CommentDetails details = new CommentDetails();

                        details.RequestId = req.RequestId;
                        if (letterType == "Invoice")
                            details.EventType = EventType.InvoiceSend;
                        else if (letterType == "PreBill")
                            details.EventType = EventType.PreBillSend;
                        else
                            details.EventType = EventType.LetterSend;
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
                        if (letterType != "Statement")
                        {
                            RequestController.Instance.CreateComment(details);
                        }

                        //Audit for Invoice/Prebill
                        AuditEvent auditEvent = new AuditEvent();
                        auditEvent.UserId = UserData.Instance.UserInstanceId;
                        auditEvent.EventStart = DateTime.Now;
                        auditEvent.EventStatus = (long)AuditEvent.EventStatusId.Success;
                        auditEvent.EventId = 1;
                        auditEvent.Comment = documentInfo.Name + "," + documentInfo.Id + " for request " + req.RequestId;
                        auditEvent.ActionCode = BillingPaymentInfoUI.RetrieveActionCode(outputRequestDetails.OutputDestinationDetails.Type);

                        try
                        {
                            Application.DoEvents();
                            ROIController.Instance.CreateAuditEntry(auditEvent);
                        }
                        catch (ROIException cause)
                        {
                        }

                        outputRequestDetails.RequestParts.Add(BuildROIRequestPartDetails(documentInfo));
                        Application.DoEvents();
                        long jobStatus = OutputController.Instance.SubmitOutputRequest(outputRequestDetails, viewer.DestinationType,
                                                                      viewer.OutputPropertyDetails.OutputViewDetails,
                                                                      false);
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
                catch (System.IO.IOException cause)
                {
                    ROIException fileAlreadyOpen = new ROIException(ROIErrorCodes.FileAlreadyOpen);
                    ROIViewUtility.Handle(Context, fileAlreadyOpen);
                }
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            catch (Exception e)
            {

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


        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            //ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.RequestorLetterHistory, e));
        }

        private void buttonViewStatements_Click(object sender, EventArgs e)
        {
            ViewInvoiceOrPrebill("Statement");
        }

        private void invoiceGrid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (invoiceGrid.SelectedRows.Count != 0)
            {
                RequestorHistoryDetail requestorHisotryDetail = (RequestorHistoryDetail)invoiceGrid.SelectedRows[0].DataBoundItem;
                buttonViewInvoice.Enabled = (!requestorHisotryDetail.IsMigrated);
            }
        }

        private void prebillGrid_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            if(e.RowIndex>=0)
            {
                ViewInvoiceOrPrebill("PreBill");
            }

        }

        private void invoiceGrid_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                //CR-388274 Fix
                RequestorHistoryDetail requestorHisotryDetail = (RequestorHistoryDetail)invoiceGrid.SelectedRows[0].DataBoundItem;
                if (!requestorHisotryDetail.IsMigrated)
                    ViewInvoiceOrPrebill("Invoice");
            }
        }

        private void statementGrid_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                ViewInvoiceOrPrebill("Statement");
            }
        }
    }
}
