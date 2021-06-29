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

using CrystalDecisions.CrystalReports.Engine;
using CrystalDecisions.Shared;
using CrystalDecisions.Windows.Forms;

using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Collections.ObjectModel;
using System.Data;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Threading;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;
using McK.EIG.Common.Utility.Pagination.View;

using McK.EIG.ROI.Client.Admin.Model;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Request.Controller;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View.FindRequest;

using McK.EIG.ROI.Client.Reports.View;
using McK.EIG.ROI.Client.Reports.Model;


namespace McK.EIG.ROI.Client.Request.View
{
    public partial class RequestsListUI : ROIBaseUI
    {
        #region Fields

        private int pageSize;
        private long frequency;

        public const string ZeroRequestFound = ".norequestFound";
        public const string RequestFound = ".requestFound";
        public const string RequestsFound = ".requestsFound";
        public const string NoSearchPerformed = ".noSearchPerformed";
        public const string RequestMaxCountExceeded = ".moreRequestFound";

        private const string VipLockedPermissionDialogTitle = "VipLockedPermissionDialog.Title";
        private const string VipLockedPermissionDialogMessage = "VipLockedPermissionDialog.Message";
        private const string VipLockedPermissionDialogOkButton = "VipLockedPermissionDialog.OkButton";
        private const string VipLockedPermissionDialogOkButtonToolTip = "VipLockedPermissionDialog.OkButton";
        
        
        private ComparableCollection<RequestDetails> filteredRequests;
        private RequestHistoryPageProducer pageProducer;
        private RequestHistoryPageConsumer pageConsumer;
        
        private EIGDataGrid.RowSelectionHandler rowSelectionChangeHandler;
        private DataGridViewRowStateChangedEventHandler rowStateChanged;
        private EventHandler viewEditHandler;
        private MouseEventHandler rowDoubleClickHandler;

        private SortedList<string, string> filterEncounters;

        private RequestDetails selectedRequest;

        delegate void ConsumePageCallback();

        private string filter;

        private ExportDialogUI dialogUI;
        private CrystalReportViewer viewer;
        
        #endregion

        #region Constructor

        public RequestsListUI()
        {
            InitializeComponent();
            viewEditHandler = new EventHandler(ViewEditRequestHandler);
            InitGrid();
            EnableButtons(false);
            EnableUI(false);
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the Grid.
        /// </summary>
        public virtual void InitGrid()
        {
            grid.MultiSelect = false;

            rowStateChanged = new DataGridViewRowStateChangedEventHandler(grid_RowStateChanged);
            rowSelectionChangeHandler = new EIGDataGrid.RowSelectionHandler(grid_SelectionChanged);
            rowDoubleClickHandler = new MouseEventHandler(grid_RowDoubleClick);
            grid.MouseDoubleClick += new MouseEventHandler(grid_RowDoubleClick);
            //CR#365143 - Add grid selection event when accessing the request from Requestor module
            grid.SelectionHandler += rowSelectionChangeHandler;
        }

        /// <summary>
        /// Occurs when the user select a new row.
        /// </summary>
        /// <param name="row"></param>
        private void grid_SelectionChanged(DataGridViewRow row)
        {
            if (grid.SelectedRows != null)
            {
                EnableButtons(grid.SelectedRows.Count > 0);
            }
        }

        private void grid_RowStateChanged(object sender, DataGridViewRowStateChangedEventArgs e)
        {
            if (grid.SelectedRows != null)
            {
                EnableButtons(grid.SelectedRows.Count > 0);
            }
        }

        /// <summary>
        /// Occurs when the user Double click a row in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowDoubleClick(object sender, MouseEventArgs e)
        {
            
            if (grid.RowCount > 0)
            {
                if (grid.SelectedRows != null)
                {
                    selectedRequest = grid.SelectedRows[0].DataBoundItem as RequestDetails;
                    if (grid.SelectedRows.Count == 1 && !selectedRequest.HasMaskedRequestFacility) //CR#364666 - Check the authorization to view the request
                    {
                        DataGridView.HitTestInfo hti = grid.HitTest(e.X, e.Y);
                        if (hti.Type != DataGridViewHitTestType.ColumnHeader)
                        {
                            viewEditHandler(sender, e);
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Occurs when the user clicks the column header.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_ColumnHeaderMouseClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            if (!pageProducer.IsPaginationInProgress)
            {
                grid.SortEnabled = true;
            }
            else
            {
				//CR#369,554
                DataGridViewColumn selectedColumn = grid.Columns[0];
                pageProducer.SetSortProperties(Convert.ToString(selectedColumn.Tag, System.Threading.Thread.CurrentThread.CurrentUICulture), string.Empty, true);
                filteredRequests.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestDetails))[selectedColumn.DataPropertyName],
                                               ListSortDirection.Descending);                
            }
        }

        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, viewEditRequestButton);
            if (Pane.GetType().Name.Equals("RequestHistoryMCP"))
            {
                exportRequestButton.Visible = false;
            }
            if (Pane.GetType().Name.Equals("FindRequestODP"))
            {
                SetLabel(rm, exportRequestButton);
            }
            SetLabel(rm, searchCountLabel);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            SetTooltip(rm, toolTip, viewEditRequestButton);
        }
        
        protected override string GetLocalizeKey(Control control)
        {
            if (control == searchCountLabel)
            {
                if (Pane.GetType().Name.Equals("FindRequestODP"))
                {
                    return control.Name + NoSearchPerformed;
                }
                else
                {
                    return control.Name + ZeroRequestFound;
                }
            }
            else if (control == viewEditRequestButton || control == exportRequestButton)
            {
                return control.Name + "." + Pane.GetType().Name;
            }
            return base.GetLocalizeKey(control);
        }

        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == viewEditRequestButton)
            {
                return control.Name + "." + Pane.GetType().Name;
            }
            return base.GetLocalizeKey(control, toolTip);
        }

        public void PopulateRequestHistory(FindRequestCriteria findCriteria, SortedList<string, string> encounters, string filter)
        {
            filterEncounters = encounters;
            this.filter = filter;
            filteredRequests = new ComparableCollection<RequestDetails>(new RequestDetailsComparer());
            InvokeConsumer(findCriteria);
        }

        private void InvokeConsumer(FindRequestCriteria findCriteria)
        {
            pageSize = Convert.ToInt32(ConfigurationManager.AppSettings["PageSize"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            frequency = Convert.ToInt32(ConfigurationManager.AppSettings["Frequency"], System.Threading.Thread.CurrentThread.CurrentUICulture);
            
            pageProducer = new RequestHistoryPageProducer(findCriteria);
            DataGridViewColumn selectedColumn = grid.Columns[0];
            pageProducer.SetSortProperties(Convert.ToString(selectedColumn.Tag, System.Threading.Thread.CurrentThread.CurrentUICulture), string.Empty, true);
            filteredRequests.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestDetails))[selectedColumn.DataPropertyName],
                                           ListSortDirection.Descending);

            
            pageConsumer = new RequestHistoryPageConsumer(this, pageProducer, pageSize, frequency);
            grid.SortEnabled = false;
            pageConsumer.ProgressEvent += new EventHandler(pageConsumer_ProgressEvent);
            pageConsumer.Start();
        }

        void pageConsumer_ProgressEvent(object sender, EventArgs e)
        {
            PaginationEventArgs pe = (PaginationEventArgs)e;
            if (!pe.HasMore && InvokeRequired)
            {   
                ConsumePageCallback callback = new ConsumePageCallback(PopulateRequests);
                Invoke(callback);
            }
        }

        public void PopulateRequests()
        {
            EnableUI(true);
            SelectFirstRow();
            viewEditRequestButton.Focus();
        }

        public void AddHistories(Collection<RequestDetails> requests)
        {
            filteredRequests.RemoveFilter();

            //CR#369,554
            List<RequestDetails> requestList = new List<RequestDetails>(filteredRequests);

            if (typeof(McK.EIG.ROI.Client.Patient.View.RequestHistory.RequestHistoryMCP).IsAssignableFrom(Pane.GetType()))
            {
                requests = GroupByFacility(requests);
                //CR#369,554
                requestList.AddRange(requests);

                filteredRequests = new ComparableCollection<RequestDetails>(requestList, new RequestDetailsComparer());              
                FilterByEncounter(filterEncounters, filter);
            }
            else
            {
                //CR#369,554
                requestList.AddRange(requests);

                filteredRequests = new ComparableCollection<RequestDetails>(requestList, new RequestDetailsComparer());               
            }

            //CR#369,554
            DataGridViewColumn selectedColumn = grid.Columns[0];
            pageProducer.SetSortProperties(Convert.ToString(selectedColumn.Tag, System.Threading.Thread.CurrentThread.CurrentUICulture), string.Empty, true);
            filteredRequests.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestDetails))[selectedColumn.DataPropertyName],
                                           ListSortDirection.Descending);

            PopulateData(filteredRequests);
            UpdateRowCount();
        }

        public void FilterByEncounter(SortedList<string, string> encounters, string filter)
        {
            string excludeRequests = string.Empty; 
            filteredRequests.RemoveFilter();
            bool exclude = true;

            foreach (RequestDetails request in filteredRequests.InnerList)
            {
                //if (request.Encounters.Count == 0) continue;                
                if (filter == Filter.Off.ToString()) continue;                
                exclude = true;
                if (encounters != null && encounters.Count > 0)
                {
                    foreach (string encounter in encounters.Keys)
                    {
                        if(request.Encounters.Contains(encounter) && encounters[encounter].Equals(request.Facility))
                        {
                            exclude = false;
                            break;
                        }
                    }
                    if (exclude)
                    {
                        excludeRequests += request.RequestFacility + ",";
                    }
                }
            }

            excludeRequests = excludeRequests.TrimEnd(',');
            excludeRequests = excludeRequests.Replace(",", "','");
            if (excludeRequests.Trim().Length == 0)
            {
                excludeRequests = "0";
            }
            filteredRequests.Filter = "RequestFacility NOT IN('" + excludeRequests + "')";
        }

        protected virtual Collection<RequestDetails> GroupByFacility(Collection<RequestDetails> requests)
        {
            return requests;
        }

        /// <summary>
        /// Subscribe Events
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            grid.SelectionHandler += rowSelectionChangeHandler;
            grid.MouseDoubleClick += rowDoubleClickHandler;
            grid.RowStateChanged += rowStateChanged;
        }

        /// <summary>
        /// Unsubscribe Events.
        /// </summary>
        private void DisableEvents()
        {
            grid.SelectionHandler -= rowSelectionChangeHandler;
            grid.MouseDoubleClick -= rowDoubleClickHandler;
            grid.RowStateChanged -= rowStateChanged;
        }

        /// <summary>
        /// Select first Row in the grid.
        /// </summary>
        private void SelectFirstRow()
        {
            if (grid.Rows.Count > 0)
            {
                grid.Rows[0].Selected = true;
                EnableButtons(true);
                return;
            }
            EnableButtons(false);
        }

        public void SetData(object data)
        {
            DisableEvents();
            if (data == null)
            {
                ClearList();
                EnableButtons(false);
                return;
            }
            EnableUI(true);
            PopulateData(data);
            UpdateRowCount();
            EnableEvents();
            SelectFirstRow();
            viewEditRequestButton.Focus();
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
            ComparableCollection<RequestDetails> requests = (ComparableCollection<RequestDetails>)data;
            grid.SetItems(requests);
            EnableUI(true);
            EnableButtons(grid.Rows.Count > 0);
            viewEditRequestButton.Focus();
        }

        public void ClearList()
        {
            grid.Rows.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            searchCountLabel.Text = rm.GetString(searchCountLabel.Name + NoSearchPerformed);
            EnableUI(false);
        }

        /// <summary>
        /// Update the Rowcount Label
        /// </summary>
        public virtual void UpdateRowCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            string countText;
            int rowCount = grid.RowCount;
            if (rowCount == 0)
            {
                countText = searchCountLabel.Name + ZeroRequestFound;
                searchCountLabel.Text = rm.GetString(countText);
            }
            else if (rowCount == 1)
            {
                countText = rm.GetString(searchCountLabel.Name + RequestFound);
                searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
            }
            else
            {
                countText = rm.GetString(searchCountLabel.Name + RequestsFound);
                searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
            }
        }

        /// <summary>
        /// Enable the buttons.
        /// </summary>
        protected void EnableButtons(bool enable)
        {
            if (grid.SelectedRows != null)
            {
                //CR#365143 - Check the authorization to view the request
                if (enable && grid.SelectedRows.Count > 0)
                {
                    if (grid.SelectedRows[0] != null)
                    {
                        RequestDetails request = grid.SelectedRows[0].DataBoundItem as RequestDetails;
                        if (request != null)
                        {
                            enable = !request.HasMaskedRequestFacility;
                        }
                    }

                }
                viewEditRequestButton.Enabled = enable &&
                                                ((ROIViewUtility.IsAllowed(ROISecurityRights.ROIViewRequest)) ||
                                                  ROIViewUtility.IsAllowed(ROISecurityRights.ROIModifyRequest));
            }
        }

        private void viewEditReqButton_Click(object sender, EventArgs e)
        {
            selectedRequest = grid.SelectedRows[0].DataBoundItem as RequestDetails;
            viewEditHandler(sender, e);
            
        }

        private void ViewEditRequestHandler(object sender, EventArgs e)
        {
            try
            {
                RequestDetails selectedRequest = (RequestDetails)grid.SelectedRows[0].DataBoundItem;

                if (!HasVipLockedPermission(selectedRequest)) return;
                
                selectedRequest = RequestController.Instance.RetrieveRequest(selectedRequest.Id,true);
                
                selectedRequest.InUseRecord = RequestController.Instance.RetrieveInUseRecord(ROIConstants.RequestDomainType, selectedRequest.Id);
                if (selectedRequest.InUseRecord != null)
                {
                    selectedRequest.IsLocked = !(selectedRequest.InUseRecord.ApplicationId == RequestController.ApplicationId + ROIConstants.Delimiter + RequestController.HostAddress &&
                                                 string.Compare(selectedRequest.InUseRecord.UserId, UserData.Instance.UserId, 
                                                                true, System.Threading.Thread.CurrentThread.CurrentUICulture) == 0);
                }
                else
                {
                    RequestController.Instance.CreateInUseRecord(ROIConstants.RequestDomainType, selectedRequest.Id);
                }

                ApplicationEventArgs ae = new ApplicationEventArgs(selectedRequest, this);
                RequestEvents.OnRequestSelected(Pane, ae);
            }
            catch (ROIException cause)
            {
                ROIViewUtility.Handle(Context, cause);
            }
            finally
            {
                ROIViewUtility.MarkBusy(false);
            } 
        }

        private bool HasVipLockedPermission(RequestDetails request)
        {
            if (request.HasVipPatient || request.HasLockedPatient)
            {
                if (!IsAllowed(ROISecurityRights.ROIVipStatus) ||
                   !IsAllowed(ROISecurityRights.AccessLockedRecords))
                {
                    ShowVipLockedAlert();
                    return false;
                }

            }

            return true;
        }

        private void ShowVipLockedAlert()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(VipLockedPermissionDialogMessage);
            string titleText = rm.GetString(VipLockedPermissionDialogTitle);
            string okButtonText = rm.GetString(VipLockedPermissionDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(VipLockedPermissionDialogOkButtonToolTip);

            ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        private void exportRequestButton_Click(object sender, EventArgs e)
        {
            DataSet dataSet = new DataSet();
            DataTable dataTable = new DataTable();
            dataTable.TableName = "RequestTable";

            Type type;
            foreach (DataGridViewColumn dataGridViewColumn in Grid.Columns)
            {
                if ((dataGridViewColumn.DataPropertyName == "ReceiptDate") || (dataGridViewColumn.DataPropertyName == "LastUpdated"))
                {
                    type = typeof(DateTime);
                }
                else if (dataGridViewColumn.DataPropertyName == "Status")
                {
                    type = typeof(String);
                }
                else
                {
                    type = dataGridViewColumn.ValueType;
                    if (dataGridViewColumn.ValueType.Name == "Image")
                    {
                        type = typeof(System.Byte[]);
                    }
                }
                dataTable.Columns.Add(dataGridViewColumn.DataPropertyName, type);
            }
            ImageConverter imageConverter = new ImageConverter();
            foreach (DataGridViewRow gridRow in Grid.Rows)
            {
                if (gridRow.IsNewRow)
                    continue;
                DataRow dataRow = dataTable.NewRow();
                for (int count = 0; count < Grid.Columns.Count; count++)
                    if ((count < 3) && gridRow.Cells[count].Value != null)
                    {
                        dataRow[count] = (byte[])imageConverter.ConvertTo(gridRow.Cells[count].Value, typeof(byte[]));
                    }
                    else
                    {
                        if (count == 5)
                        {
                            dataRow[count] = EnumUtilities.GetDescription((RequestStatus)gridRow.Cells[count].Value);
                        }
                        else
                        {
                            dataRow[count] = (gridRow.Cells[count].Value == null ? DBNull.Value : gridRow.Cells[count].Value);
                        }
                    }
                dataTable.Rows.Add(dataRow);
            }
            dataSet.Tables.Add(dataTable);

            ReportDocument reportDocument = new ReportDocument();
            reportDocument.Load(System.IO.Directory.GetCurrentDirectory() + @"\Reports\RequestSearchResult.rpt");
            reportDocument.SetDataSource(dataSet.Tables[0]);

            viewer = new CrystalReportViewer();
            viewer.ReportSource = reportDocument;

            EventHandler exportHandler;
            exportHandler = new EventHandler(export_Report);

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
            ROIViewUtility.MarkBusy(true);
            Export(dialogUI.ExportPath, dialogUI.ExportType);
            ROIViewUtility.MarkBusy(false);
        }

        /// <summary>
        /// Creates export options
        /// </summary>
        /// <param name="exportPath"></param>
        /// <param name="exportType"></param>
        private void Export(string exportPath, ExportFormat exportFormat)
        {
            ExportOptions exportOptions = ((ReportDocument)viewer.ReportSource).ExportOptions;
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
  
                if (exportOptions.ExportFormatType == ExportFormatType.Excel)
                {
                    ReportHelper.ConfigureExcel(exportOptions);
                }

                if (exportOptions.ExportFormatType == ExportFormatType.HTML40)
                {
                    ReportDetails reportDetails = new ReportDetails();
                    ReportHelper.ConfigureHtml(exportOptions, exportPath, "Request Results");
                }

                //if (exportOptions.ExportFormatType == ExportFormatType.CharacterSeparatedValues)
                //{
                //    File.Copy(Path.Combine(Directory.GetParent(reportDetails.FilePath).ToString(), reportDetails.ReportType + ".csv"), exportPath);
                //    return;
                //}

                ((ReportDocument)viewer.ReportSource).Export();

                //if (exportOptions.ExportFormatType == ExportFormatType.HTML40)
                //{
                //    int reportIndex = filterComboBox.SelectedIndex == -1 ? 0 : filterComboBox.SelectedIndex;
                //    ReportHelper.UpdateHtmlFile(reportDetails, reportIndex, exportPath);
                //}

            }
            catch (UnauthorizedAccessException accessException)
            {
                ROIViewUtility.Handle(Context, new ROIException(ROIErrorCodes.AccessDeniedException, accessException));
            }
        }

        public void AddRow(object data)
        {
             if (grid.Items == null)
            {
                Collection<RequestDetails> requestList = new Collection<RequestDetails>();
                requestList.Add((RequestDetails)data);
                ComparableCollection<RequestDetails> requests = new ComparableCollection<RequestDetails>(requestList);
                grid.SetItems(requests);
                EnableUI(true);
            }
            else
            {
                grid.AddItem(data);
                grid.SelectItem(data);
            }

            UpdateRowCount();
            grid.Enabled = grid.RowCount > 0;
            EnableButtons(grid.RowCount > 0);
        }

        public void DeleteRow(object data)
        {
            if (grid.Items == null) return;
            if (grid.Items.Contains(data))
            {
                grid.DeleteItem(data);
            }

            UpdateRowCount();
            if (grid.SelectedRows.Count == 0)
            {
                EnableButtons(false);
            }

            grid.Refresh();
        }

        public void UpdateRow(object data)
        {
            if (grid.Items == null) return;
            if (grid.Items.Contains(data))
            {
                grid.UpdateItem(data);
            }
        }

        public static string SetSortProperties(string sortedColumn, string domain)
        {
            return domain + ROIConstants.Delimiter + domain + "." + sortedColumn;
        }

        private void grid_CellFormatting(object sender, DataGridViewCellFormattingEventArgs e)
        {
            if (e.ColumnIndex == 8)
            {
                ROIModel roiModel = (ROIModel)grid.Rows[e.RowIndex].DataBoundItem;
                DataGridViewCell cell = grid.Rows[e.RowIndex].Cells[e.ColumnIndex];

                if (roiModel == null)
                {
                    return;
                }
                RequestDetails requestDetails = ((RequestDetails)roiModel);
                //CR#365143 - Display masked text in red color for the request having unauthorized facility
                if (!requestDetails.HasMaskedRequestFacility)
                {
                    cell.ToolTipText = requestDetails.PatientNames;
                }

                //if (requestDetails.Patients.Count > 20)
                //{
                //    cell.ToolTipText = requestDetails.FirstTwentyPatientNames;
                //}
            }
        }

        #endregion

        #region Properties

        public EIGDataGrid Grid
        {
            get { return grid; }
        }

        public Button ViewEditRequestButton
        {
            get { return viewEditRequestButton; }
        }

        public DataGridViewCellMouseEventHandler ColumnHeaderMouseClickHandler
        {
            get { return new DataGridViewCellMouseEventHandler(grid_ColumnHeaderMouseClick); }
        }

        public Label SearchCountLabel
        {
            get { return searchCountLabel; }
        }

        public RequestDetails SelectedRequest
        {

            get { return selectedRequest; }
            set { selectedRequest = value; }
        }

        /// <summary>
        /// To fill the last column width.
        /// </summary>
        public int ColumnWidth
        {
            get
            {
                int sum = 0;
                foreach (DataGridViewColumn column in grid.Columns)
                {
                    sum += column.Width;
                }
                sum = grid.Width - sum;
                sum = grid.Columns[grid.Columns.Count - 1].Width + sum - 3;
                return sum;
            }
        }
        #endregion
    }
}
