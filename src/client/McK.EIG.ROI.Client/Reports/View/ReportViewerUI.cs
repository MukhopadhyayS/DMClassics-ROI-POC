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
using System.Collections.ObjectModel;
using System.Configuration;
using System.Drawing.Printing;
using System.Globalization;
using System.IO;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.Logging;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Reports.Model;
using McK.EIG.ROI.Client.Patient.View;

using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using CrystalDecisions.Windows.Forms;

namespace McK.EIG.ROI.Client.Reports.View
{
    /// <summary>
    /// ReportDetailsUI
    /// </summary>
    public partial class ReportViewerUI : ROIBaseUI, IFooterProvider
    {
        #region Fields

        private Log log = LogFactory.GetLogger(typeof(ReportViewerUI));     

        private string rptPath;
        private const string imageId = "{@PicCol";

        private ReportDocument reportDocument;
        private ReportDetails  reportDetails;
        private ExportDialogUI dialogUI;
        private WebBrowser browser;
        private PrintDialog printDialog;

        private EventHandler filterComboBoxChanged;
        private EventHandler exportHandler;
        private Form reportPrintPreviewForm;
        
        #endregion

        #region Constructor

        public ReportViewerUI()
        {
            InitializeComponent();
            CreateActionButtons();
            reportDocument = new ReportDocument();

            rptPath = Application.ExecutablePath.Substring(0, Application.ExecutablePath.LastIndexOf(Path.DirectorySeparatorChar));
            rptPath = Path.Combine(rptPath, @"Reports");

            filterComboBoxChanged = new EventHandler(filterComboBox_SelectedIndexChanged);
            exportHandler         = new EventHandler(export_Report);
        }

        #endregion

        #region Methods

        /// <summary>
        /// It creates all action events in the UI
        /// </summary>
        private void CreateActionButtons()
        {
            reportFooterUI = new ReportFooterUI();
            reportFooterUI.Dock = DockStyle.Fill;
            reportFooterUI.ExportButton.Click       += new System.EventHandler(this.exportButton_Click);
            reportFooterUI.PrintButton.Click        += new System.EventHandler(this.printButton_Click);
            reportFooterUI.PrintPreviewButton.Click += new System.EventHandler(this.printPreviewButton_Click);
        }

        /// <summary>
        /// Localize UI
        /// </summary>
        public override void Localize()
        {
            reportFooterUI.SetExecutionContext(Context);
            reportFooterUI.Localize();
            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, viewByLabel);
        }

        /// <summary>
        /// Disable events
        /// </summary>
        private void EnableEvents()
        {
            filterComboBox.SelectedIndexChanged += filterComboBoxChanged;
        }

        /// <summary>
        /// Enable events
        /// </summary>
        private void DisableEvents()
        {
            filterComboBox.SelectedIndexChanged -= filterComboBoxChanged;
        }

        /// <summary>
        /// Prepopulate data
        /// </summary>
        internal void PrePopulate()
        {
            DisableEvents();
            reportDetails = ((ReportEditor)this.Pane.ParentPane).ReportDetails;

            if (reportDetails.ReportType == ReportType.ProcessRequestSummary)
            {
                topPanel.Visible = false;
                EnableEvents();
                return;
            }
            
            IList filterTypes = ReportHelper.GetFilters(reportDetails.ReportType);
            filterComboBox.DataSource    = filterTypes;
            filterComboBox.DisplayMember = "value";
            filterComboBox.ValueMember   = "key";
            EnableEvents();
        }

        /// <summary>
        /// Sets data
        /// </summary>
        /// <param name="data"></param>
        internal void SetData(object data)
        {
            ROIViewUtility.MarkBusy(true);
            reportDetails = (ReportDetails)data;
            ReportHelper.CreateDataSource(reportDetails.FilePath);
            log.EnterFunction();
            Collection<ExceptionData> errors = new Collection<ExceptionData>();
            try
            {
                ReportHelper.RenameCsvFile(reportDetails);
            }
            catch (System.Runtime.InteropServices.COMException cause)
            {
                log.FunctionFailure(cause);
                errors.Add(new ExceptionData(ROIErrorCodes.FileInUse));
                ROIViewUtility.Handle(Context, new ROIException(errors));
                return;
            }
            catch (IOException cause)
            {
                log.FunctionFailure(cause);
                errors.Add(new ExceptionData(ROIErrorCodes.FileInUse));
                ROIViewUtility.Handle(Context, new ROIException(errors));
                return;
            }

            LoadReport();
            topPanel.Visible = filterComboBox.Items.Count > 1;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            reportTitleLabel.Text = rm.GetString(reportDetails.ReportType.ToString());
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Loads the report into crystal report viewer.
        /// </summary>
        private void LoadReport()
        {
            Collection<ExceptionData> errors = new Collection<ExceptionData>();
            log.EnterFunction();
            try
            {
                if (reportDetails.ReportType == ReportType.AccountingDisclosure)
                {
                    if (crystalReportViewer.ReportSource == null)
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                        string info = rm.GetString("reportFees.Info");
                        info = info.Replace("\\n", "\n");
                        ROIViewUtility.ConfirmChanges(rm.GetString("reportFees.Title"),
                                                      info,
                                                      rm.GetString("okButton.DialogUI"),
                                                      string.Empty, ROIDialogIcon.Info);
                    }
                }

                string rptFileName = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, ReportHelper.GetReportFileName(reportDetails.ReportType), filterComboBox.SelectedIndex == -1 ? 0 : filterComboBox.SelectedIndex);
                string csvFilePath = Path.Combine(rptPath, rptFileName);
                reportDocument.Load(csvFilePath);
                crystalReportViewer.ToolPanelView = ToolPanelViewType.None;
                crystalReportViewer.ReportSource = reportDocument;
                crystalReportViewer.BorderStyle = BorderStyle.None;
                ReportHelper.HideReportTabs(crystalReportViewer, false);
                ShowReportHeader(string.Empty);
                ApplyMasking();
                if (reportDetails.ReportType == ReportType.AccountingDisclosure)  EnableControls(true);                
                else EnableControls(reportDocument.Rows.Count > 0);
                HeaderUI headerUI = new HeaderUI();
                PatientHeaderUI patientHeaderUI = new PatientHeaderUI();
                headerUI.HeaderExtension = patientHeaderUI;
            }
            catch (System.Runtime.InteropServices.COMException cause)
            {
                log.FunctionFailure(cause);
                errors.Add(new ExceptionData(ROIErrorCodes.FileInUse));
                ROIViewUtility.Handle(Context, new ROIException(errors));
                return;
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
                log.ExitFunction();
            }
        }

        private void EnableControls(bool enable)
        {
            reportFooterUI.ExportButton.Enabled       = enable;
            reportFooterUI.PrintPreviewButton.Enabled = enable;
            reportFooterUI.PrintButton.Enabled        = enable;
        }
    
        /// <summary>
        /// filterComboBox Selected Index Changed event
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void filterComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            ROIViewUtility.MarkBusy(true);
            //if (reportDetails.ReportType == ReportType.TurnaroundTimes || reportDetails.ReportType==ReportType.MUReleaseTurnaroundTimeReport)
            if (reportDetails.ReportType == ReportType.PrebillReport||reportDetails.ReportType == ReportType.MUReleaseTurnaroundTimeReport || reportDetails.ReportType == ReportType.ProductivityReport || reportDetails.ReportType == ReportType.PostedPaymentsReport)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                if (reportDetails.ReportType == ReportType.MUReleaseTurnaroundTimeReport)
                {
                    MUReleaseTurnaroundTimeUI muReleaseUI = new MUReleaseTurnaroundTimeUI();
                    muReleaseUI.SetResultType(filterComboBox.Text);
                }
                if (reportDetails.ReportType == ReportType.ProductivityReport)
                {
                    ProductivityUI ProductivityUI = new ProductivityUI();
                    ProductivityUI.SetResultType(filterComboBox.Text);
                }
                if (reportDetails.ReportType == ReportType.PostedPaymentsReport)
                {
                    PostedPaymentReportUI postedPayUI = new PostedPaymentReportUI();
                    postedPayUI.SetResultType(filterComboBox.Text);
                }
                if (reportDetails.ReportType == ReportType.PrebillReport)
                {
                    PreBillReportUI preBillUI = new PreBillReportUI();
                    preBillUI.SetResultType(filterComboBox.Text);
                }
                Hashtable parameters = reportUI.CollectData();
                //parameters["resultType"] = filterComboBox.Text.ToLower(System.Threading.Thread.CurrentThread.CurrentUICulture);
                reportUI.GenerateReport(parameters);
                ReportHelper.RenameCsvFile(reportDetails); 
            }
            LoadReport();
            ROIViewUtility.MarkBusy(false);
        }
        #region Sorting

        ObjectInfo info;
        /// <summary>
        /// crystalReportViewer ClickPage event, gets the selected header column text and sort it. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void crystalReportViewer_ClickPage(object sender, CrystalDecisions.Windows.Forms.PageMouseEventArgs e)
        {
            Cursor.Current = Cursors.WaitCursor;
            info = e.ObjectInfo;
            SortHeader(info, true, true);
            Cursor.Current = Cursor.Current;
        }

        /// <summary>
        /// Sort the selected column
        /// </summary>
        /// <param name="info"></param>
        private void SortHeader(ObjectInfo info, bool doSort, bool isGlyph)
        {
            Hashtable reportParams = null;
            IReportSorter reportSorter = null;
            //reportDocument.Refresh();
            int selectedReportView = filterComboBox.SelectedIndex == -1 ? 0 : filterComboBox.SelectedIndex;

            reportSorter = ReportHelper.GetSorter(reportDetails.ReportType);

            if (reportSorter == null) return;

            //After converting the CR11.5 to CR13.0 (CRVS2010), the grouping fields are the first field in the sort fields column (GroupSortField)
            //So the following reports sorting is not working. Based on the grouping field available on each reports, we need to 
            //calculate the record sort field ( first sort field column )
            //SortFieldIndex and reportfilename parameter is added in the IReportSorter interface to solve this issue.
            //AccountingOfDisclosure, DocumentsReleasedByMRN, RequestDetailReport,OutstandingInvoiceBalance,RequestwithLoggedStatus, 
            //PostedPaymentSummary
            string reportFileName = reportDocument.FileName.Substring(reportDocument.FileName.LastIndexOf("\\") + 1);

            reportParams = reportSorter.PrepareParams(info, selectedReportView, reportFileName);

            if (reportParams == null) return;
            if (reportParams.Count == 0) return;
            
            int column = reportSorter.SelectedColumnIndex;            
            SortDirection descendingOrder = SortDirection.DescendingOrder;
            SortDirection ascendingOrder = SortDirection.AscendingOrder;

            if (reportDocument.DataDefinition.SortFields[reportSorter.SortFieldIndex].SortDirection == descendingOrder)
            {
                if (doSort)
                {
                    reportDocument.DataDefinition.SortFields[reportSorter.SortFieldIndex].SortDirection = ascendingOrder;
                    reportParams.Add(imageId + column + "}", Path.GetFullPath(Path.Combine(Application.StartupPath, @"Reports\Sort_Up.png")));
                }
                else 
                {
                    reportDocument.DataDefinition.SortFields[reportSorter.SortFieldIndex].SortDirection = descendingOrder;
                    if (isGlyph)
                    {
                        reportParams.Add(imageId + column + "}", Path.GetFullPath(Path.Combine(Application.StartupPath, @"Reports\Sort_Down.png")));
                    }
                }
            }
            else
            {
                if (doSort)
                {
                    reportDocument.DataDefinition.SortFields[reportSorter.SortFieldIndex].SortDirection = descendingOrder;
                    reportParams.Add(imageId + column + "}", Path.GetFullPath(Path.Combine(Application.StartupPath, @"Reports\Sort_Down.png")));
                }
                else
                {
                    reportDocument.DataDefinition.SortFields[reportSorter.SortFieldIndex].SortDirection = ascendingOrder;
                    if (isGlyph)
                    {
                        reportParams.Add(imageId + column + "}", Path.GetFullPath(Path.Combine(Application.StartupPath, @"Reports\Sort_Up.png")));
                    }
                }
            }


            for (int i = 0; i < reportSorter.ColumnSortCount; i++)
            {
                if ((doSort) || (isGlyph))
                {
                    if (i != column)
                    {
                        reportParams.Add(imageId + i + "}", String.Empty);
                    }
                }
                else
                {
                    reportParams.Add(imageId + i + "}", String.Empty);
                }
            }

            ReportHelper.SetFormula(reportParams, reportDocument);            
            crystalReportViewer.ReportSource = reportDocument;
        }

        #endregion

        #region Export

        /// <summary>
        /// Export Button Click event.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void exportButton_Click(object sender, EventArgs e)
        {
            dialogUI = new ExportDialogUI(Pane, exportHandler);
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            Form form = ROIViewUtility.ConvertToForm(null, rm.GetString("exportReport"), dialogUI);
            form.Icon = null;
            form.ShowDialog(this);
        }
        
        /// <summary>
        /// Export the report to the selected path.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void export_Report(object sender, EventArgs e)
        {
            ShowReportHeader(reportTitleLabel.Text);
            ROIViewUtility.MarkBusy(true);
            Export(dialogUI.ExportPath, dialogUI.ExportType);
            ShowReportHeader(string.Empty);
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Creates export options
        /// </summary>
        /// <param name="exportPath"></param>
        /// <param name="exportType"></param>
        private void Export(string exportPath, ExportFormat exportFormat)
        {
            if (Validator.Validate(exportPath, ROIConstants.FilepathValidation) && reportDetails.FilePath.StartsWith(Environment.CurrentDirectory))
            {
                ExportOptions exportOptions = ((ReportDocument)crystalReportViewer.ReportSource).ExportOptions;
                if (exportOptions != null)
                {
                    exportOptions.ExportFormatOptions = null;
                }

                exportOptions.ExportDestinationType = ExportDestinationType.DiskFile;
                exportOptions.ExportFormatType = ReportHelper.GetExportFormatType(exportFormat);
                DiskFileDestinationOptions diskFileDestinationOptions = new DiskFileDestinationOptions();
                diskFileDestinationOptions.DiskFileName = exportPath;
                exportOptions.ExportDestinationOptions = diskFileDestinationOptions;

                try
                {
                    if (reportDetails.ReportType == ReportType.MUReleaseTurnaroundTimeReport)
                    {
                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
                        ShowReportHeader(rm.GetString(reportDetails.ReportType.ToString() + ".Header"));

                    }
                    if (exportOptions.ExportFormatType == ExportFormatType.Excel)
                    {
                        ReportHelper.ConfigureExcel(exportOptions);
                    }

                    if (exportOptions.ExportFormatType == ExportFormatType.HTML40)
                    {
                        ReportHelper.ConfigureHtml(exportOptions, exportPath, reportDetails.ReportType.ToString());
                    }

                    if (exportOptions.ExportFormatType == ExportFormatType.CharacterSeparatedValues)
                    {
                        File.Copy(Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), reportDetails.ReportType + ".csv"), exportPath);

                        ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                        string selfPayEncounter = rm.GetString("Self Pay Encounter");
                        if (reportDetails.ReportType.ToString() == "AccountingDisclosure")
                        {
                            string fileContent = File.ReadAllText(exportPath);
                            fileContent = fileContent.Replace("\",1,\"", selfPayEncounter + "\",1,\"");
                            File.WriteAllText(exportPath, fileContent);
                        }

                        return;
                    }

                    ((ReportDocument)crystalReportViewer.ReportSource).Export();

                    if (exportOptions.ExportFormatType == ExportFormatType.HTML40)
                    {
                        int reportIndex = filterComboBox.SelectedIndex == -1 ? 0 : filterComboBox.SelectedIndex;
                        ReportHelper.UpdateHtmlFile(reportDetails, reportIndex, exportPath);
                    }

                }
                catch (UnauthorizedAccessException accessException)
                {
                    ROIViewUtility.Handle(Context, new ROIException(ROIErrorCodes.AccessDeniedException, accessException));
                }
            }
        }

        #endregion

        #region Print Priview

        /// <summary>
        /// Print Priview button.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printPreviewButton_Click(object sender, EventArgs e)
        {
            if (info != null)
            {
                SortHeader(info, false, false);
            }
            reportFooterUI.PrintPreviewButton.Enabled = false;
            ROIViewUtility.MarkBusy(true);
            if(!(reportDetails.ReportType == ReportType.MUReleaseTurnaroundTimeReport))
                ShowReportHeader(reportTitleLabel.Text);
            int reportIndex = filterComboBox.SelectedIndex == -1 ? 0 : filterComboBox.SelectedIndex;
            Export(Directory.GetParent(reportDetails.FilePath).ToString() + "\\" + reportDetails.ReportType.ToString() + "_" + reportIndex + ".pdf", ExportFormat.Pdf);
            ShowReportHeader(string.Empty);
            ShowPrintPriviewDialog();
            if (info != null)
            {
                SortHeader(info, false, true);
            }
        }

        private void ApplyMasking()
        {
            Hashtable param = new Hashtable();
            param.Add("{@IsVip}", ROIViewUtility.IsAllowed(ROISecurityRights.ROIVipStatus).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            param.Add("{@IsLocked}", ROIViewUtility.IsAllowed(ROISecurityRights.AccessLockedRecords).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            if((reportDetails.ReportType == ReportType.DocumentsReleasedByMRN) || (reportDetails.ReportType == ReportType.AccountingDisclosure))
            {
                param.Add("{@IsEpnEnabled}", UserData.Instance.EpnEnabled.ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            }
            if (reportDetails.ReportType == ReportType.AccountingDisclosure)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@Facility}", reportUI.FacilityCode);
                ResourceManager rm = Context.CultureManager.GetCulture(CultureType.Message.ToString());
                string selfPay = rm.GetString("Self Pay Encounter");
                param.Add("{@SelfPay}", selfPay);
            }
			//CR#377156
            if ((reportDetails.ReportType == ReportType.RequestDetailReport) || (reportDetails.ReportType == ReportType.RequestWithLoggedStatus)
                                            || (reportDetails.ReportType == ReportType.ProcessRequestSummary) || (reportDetails.ReportType == ReportType.TurnaroundTimes)
                                            || (reportDetails.ReportType == ReportType.RequestStatusSummary))
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@Facilities}", reportUI.Facilities);
            }
            if (reportDetails.ReportType == ReportType.TurnaroundTimes)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@FromStatusValueFormula}", reportUI.FromStatusValue);
                param.Add("{@ToStatusValueFormula}", reportUI.ToStatusValue);
                param.Add("{@FromDateRangeValueFormula}", reportUI.FromDateRangeValue);
                param.Add("{@ToDateRangeValueFormula}", reportUI.ToDateRangeValue);
            }
            if (reportDetails.ReportType == ReportType.BillableAndUnBillableReport)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@FromDateRangeValueFormula}", reportUI.FromDateRangeValue);
                param.Add("{@ToDateRangeValueFormula}", reportUI.ToDateRangeValue);
            }
            if (reportDetails.ReportType == ReportType.SalesTaxReport)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@FromDateRangeValueFormula}", reportUI.FromDateRangeValue);
                param.Add("{@ToDateRangeValueFormula}", reportUI.ToDateRangeValue);
            }
            if (reportDetails.ReportType == ReportType.PostedPaymentsReport)
            {
                ReportUI reportUI = ((ReportLSP)(((ReportEditor)this.Pane.ParentPane).ParentPane.ParentPane.SubPanes[0])).SubPanes[0].View as ReportUI;
                param.Add("{@UserNamesFormula}", reportUI.UserNames);                
            }
            ReportHelper.SetFormula(param, reportDocument);
            crystalReportViewer.ReportSource = reportDocument;
        }

        private void ShowReportHeader(string value)
        {
            Hashtable param = new Hashtable();
            param.Add("{@IsVip}", ROIViewUtility.IsAllowed(ROISecurityRights.ROIVipStatus).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            param.Add("{@IsLocked}", ROIViewUtility.IsAllowed(ROISecurityRights.AccessLockedRecords).ToString(System.Threading.Thread.CurrentThread.CurrentUICulture));
            param.Add("{@ReportHeader}", value);
            ReportHelper.SetFormula(param, reportDocument);
        }

        /// <summary>
        /// Disaplys print priview dialog
        /// </summary>
        private void ShowPrintPriviewDialog()
        {
            int reportIndex = filterComboBox.SelectedIndex == -1? 0: filterComboBox.SelectedIndex;
            Uri newUri = new Uri(Path.Combine
                                 (Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), reportDetails.ReportType + "_" + reportIndex + ".pdf#zoom=100")));

            ReportPrintPreview dialogUI = new ReportPrintPreview();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            reportPrintPreviewForm = ROIViewUtility.ConvertToForm(null, "Print Preview", dialogUI);
            reportPrintPreviewForm.Icon = null;
            reportPrintPreviewForm.AutoSize = true;
            reportPrintPreviewForm.Padding = new Padding(10, 10, 10, 10);
            reportPrintPreviewForm.FormBorderStyle = FormBorderStyle.FixedSingle;
            reportPrintPreviewForm.WindowState = FormWindowState.Maximized;
            reportPrintPreviewForm.MinimizeBox = false;
            reportPrintPreviewForm.MaximizeBox = false;
            WebBrowser browser = new WebBrowser();
            browser.Url = newUri;
            browser.Dock = DockStyle.Fill;
            browser.WebBrowserShortcutsEnabled = false;
            browser.IsWebBrowserContextMenuEnabled = false;
            browser.DocumentCompleted += new WebBrowserDocumentCompletedEventHandler(browser_DocumentCompleted);
            reportPrintPreviewForm.Controls.Add(browser);                                  
         }

        /// <summary>
        /// After loading the content into the browser, print priview dialog will open
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void browser_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            reportPrintPreviewForm.ShowDialog();
            ROIViewUtility.MarkBusy(false);
            reportFooterUI.printPreviewButton.Enabled = true;            
        }

        #endregion

        #region Print 

        /// <summary>
        /// Prints the selected report.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void printButton_Click(object sender, EventArgs e)
        {
            ShowReportHeader(reportTitleLabel.Text);
            ROIViewUtility.MarkBusy(true);
            PrintDocument printDoc = new PrintDocument();
            printDoc.DocumentName = reportDocument.FileName;
            printDialog = new PrintDialog();
            printDialog.Document = printDoc;
            printDialog.AllowCurrentPage = true;
            printDialog.AllowSomePages   = true;
            DialogResult result = printDialog.ShowDialog();
            if (result == DialogResult.OK)
            {
                int margin = 500;
                reportDocument.PrintOptions.PrinterName = printDialog.PrinterSettings.PrinterName;
                
                PageMargins pageMargins = reportDocument.PrintOptions.PageMargins;
                pageMargins.leftMargin   = margin;
                pageMargins.topMargin    = margin;
                pageMargins.rightMargin  = margin;
                pageMargins.bottomMargin = margin;
                reportDocument.PrintOptions.ApplyPageMargins(pageMargins);
                reportDocument.PrintToPrinter(printDialog.PrinterSettings.Copies,
                                              false,
                                              printDialog.PrinterSettings.FromPage,
                                              printDialog.PrinterSettings.ToPage);
            }
            ShowReportHeader(string.Empty);
            ROIViewUtility.MarkBusy(false);
        }

        #endregion

        #endregion

        #region Properties

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return reportFooterUI;
        }

        #endregion

        

        #endregion

    }
}
