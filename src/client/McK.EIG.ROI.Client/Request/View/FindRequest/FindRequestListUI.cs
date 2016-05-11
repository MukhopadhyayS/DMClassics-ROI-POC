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
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using System.Drawing;
using System.Collections.Generic;

namespace McK.EIG.ROI.Client.Request.View.FindRequest
{
    public partial class FindRequestListUI : RequestsListUI
    {
        #region Fields

        private const string LastUpdatedColumn = "lastUpdated";
        private const string UpdatedByColumn = "updatedBy";
        private const string StatusColumn = "status.findrequest";
        private const string RequestorNameColumn = "requestorName";
        private const string RequestorTypeColumn = "requestorTypeName";
        private const string RequestIdColumn = "id";
        private const string ReceiptDateColumn = "receiptDate";
        private const string BalanceColumn = "balance";
        private const string InUseImageColumn = "inuseImage";
        private const string PatientsColumn = "patients";
        private const string StatusReasonColumn = "stausReasons";
        private const string SubTitleColumn = "subtitle";

        private bool isMaxCountExceeded;

        #endregion

        #region Constructor

        public FindRequestListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initialize the grid
        /// </summary>
        public override void InitGrid()
        {
            DataGridViewImageColumn inuseImageColumn = Grid.AddImageColumn(InUseImageColumn, " ", "InUseImage", 30);            
            inuseImageColumn.DefaultCellStyle.NullValue = null;
            inuseImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            inuseImageColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopCenter;

            DataGridViewImageColumn lockedImageColumn = Grid.AddImageColumn("lockedImage", " ", "LockedImage", 30);
            lockedImageColumn.DefaultCellStyle.NullValue = null;
            lockedImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            lockedImageColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopCenter;

            DataGridViewImageColumn vipImageColumn = Grid.AddImageColumn("vipImage", " ", "VipImage", 30);
            vipImageColumn.DefaultCellStyle.NullValue = null;
            vipImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            vipImageColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopCenter;

            DataGridViewTextBoxColumn dgvRequestIdColumn = Grid.AddTextBoxColumn(RequestIdColumn, "Request ID", "Id", 57);
            dgvRequestIdColumn.Tag = RequestDetails.IdKey;
            dgvRequestIdColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;

            DataGridViewTextBoxColumn dgvReceiptDateColumn = Grid.AddTextBoxColumn(ReceiptDateColumn, "Receipt Date", "ReceiptDate", 100);
            dgvReceiptDateColumn.Tag = RequestDetails.ReceiptDateKey;
            dgvReceiptDateColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;
            dgvReceiptDateColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;

            DataGridViewTextBoxColumn dgvStatusColumn = Grid.AddTextBoxColumn(StatusColumn, "Request Status", "Status", 100);
            dgvStatusColumn.Tag = RequestDetails.RequestStatusKey;
            dgvStatusColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;

            DataGridViewTextBoxColumn dgvRequestorNameColumn = Grid.AddTextBoxColumn(RequestorNameColumn, "Requestor Name", "RequestorName", 100);
            dgvRequestorNameColumn.Tag = RequestDetails.RequestorNameKey;
            dgvRequestorNameColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;

            DataGridViewTextBoxColumn dgvRequestorTypeColumn = Grid.AddTextBoxColumn(RequestorTypeColumn, "Requestor Type", "RequestorTypeName", 100);
            dgvRequestorTypeColumn.Tag = RequestDetails.RequestorTypeKey;
            dgvRequestorTypeColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;

            DataGridViewTextBoxColumn dgvPatientsColumn = Grid.AddTextBoxColumn(PatientsColumn, "Patient(s)", "FirstTwentyPatientNames", 100);
            dgvPatientsColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;

            DataGridViewTextBoxColumn dgvSubTitleColumn = Grid.AddTextBoxColumn(SubTitleColumn, "Subtitle", "AuthRequestSubTitle", 100);
            dgvSubTitleColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;
            dgvSubTitleColumn.Tag = RequestDetails.SubtitleKey;

            DataGridViewTextBoxColumn dgvLastUpdatedColumn = Grid.AddTextBoxColumn(LastUpdatedColumn, "Last Updated", "LastUpdated", 100);
            dgvLastUpdatedColumn.Tag = RequestDetails.LastUpdatedKey;
            dgvLastUpdatedColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;
            dgvLastUpdatedColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;

            DataGridViewTextBoxColumn dgvUpdatedByColumn = Grid.AddTextBoxColumn(UpdatedByColumn, "Updated By", "UpdatedBy", 100);
            dgvUpdatedByColumn.Tag = RequestDetails.UpdatedByKey;
            dgvUpdatedByColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopLeft;
            
            DataGridViewTextBoxColumn dgvBalanceColumn = Grid.AddTextBoxColumn(BalanceColumn, "Balance Due", "BalanceDue", 100);
            dgvBalanceColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopRight;
            dgvBalanceColumn.DefaultCellStyle.Format = "C";            
            dgvBalanceColumn.Tag = RequestDetails.BalanceDueKey;            

            dgvSubTitleColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvSubTitleColumn.MinimumWidth = 100;

            Grid.ScrollBars = ScrollBars.Both;
            Grid.AutoSize = true;
            
            Grid.ColumnHeadersDefaultCellStyle.BackColor = Color.FromArgb(238, 242, 247);
            Grid.EnableHeadersVisualStyles = false;
            Grid.AlternatingRowsDefaultCellStyle.BackColor = Color.FromArgb(238, 242, 247);

            Grid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            Grid.KeyDown += new KeyEventHandler(Grid_KeyDown);
            base.InitGrid();
        }

        /// <summary>
        /// When user selects a row in grid and pressing ENTER key, Request Info screen will be displayed. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Grid_KeyDown(object sender, KeyEventArgs e)
        {
            if ((Keys)e.KeyCode == Keys.Enter)
            {
                ViewEditRequestButton.PerformClick(); 
                e.Handled = true;
            }
        }

        /// <summary>
        /// Apply localization for grid's column header text
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(Grid, rm, ReceiptDateColumn);
            SetLabel(Grid, rm, StatusColumn);
            SetLabel(Grid, rm, RequestorNameColumn);
            SetLabel(Grid, rm, RequestorTypeColumn);
            SetLabel(Grid, rm, PatientsColumn);
            SetLabel(Grid, rm, LastUpdatedColumn);
            SetLabel(Grid, rm, UpdatedByColumn);
            SetLabel(Grid, rm, BalanceColumn);
            SetLabel(Grid, rm, SubTitleColumn);
            Grid.Columns[BalanceColumn].HeaderText = rm.GetString("balanceDue.columnHeader");
            base.Localize();
        }

        /// <summary>
        /// Populates the data
        /// </summary>
        /// <param name="data"></param>
        public override void PopulateData(object data)
        {
            FindRequestResult requestResult = (FindRequestResult)data;
            isMaxCountExceeded = requestResult.MaxCountExceeded;

            ComparableCollection<RequestDetails> list = new ComparableCollection<RequestDetails>(requestResult.RequestSearchResult, new RequestDetailsComparer());
            list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestDetails))["ReceiptDate"], ListSortDirection.Ascending);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestDetails))["ReceiptDate"], ListSortDirection.Ascending);
            //CR#388277 Fix
            Grid.AutoSizeRowsMode = DataGridViewAutoSizeRowsMode.AllCellsExceptHeaders;            
            Grid.SetItems(list);
            Grid.ClearSelection();
        }

        /// <summary>
        /// Update row count
        /// </summary>
        public override void UpdateRowCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            if (isMaxCountExceeded)
            {
                SearchCountLabel.Text = rm.GetString(SearchCountLabel.Name + RequestsListUI.RequestMaxCountExceeded);
            }
            else
            {
                string countText;
                int rowCount = Grid.RowCount;
                if (rowCount == 0)
                {   
                    countText = SearchCountLabel.Name + "." + Convert.ToString(GetType().Name, System.Threading.Thread.CurrentThread.CurrentUICulture) + RequestsListUI.ZeroRequestFound;
                    SearchCountLabel.Text = rm.GetString(countText);
                }
                else if (rowCount == 1)
                {
                    countText = rm.GetString(SearchCountLabel.Name + RequestsListUI.RequestFound);
                    SearchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
                else
                {
                    countText = rm.GetString(SearchCountLabel.Name + RequestsListUI.RequestsFound);
                    SearchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
            }
        }

        /// <summary>
        /// Occurs once the data is been bound
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            RequestDetails request;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            foreach (DataGridViewRow row in Grid.Rows)
            {
                request = (RequestDetails)row.DataBoundItem;
                if (request.IsLocked)
                {   
                    row.Cells[0].ToolTipText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, rm.GetString("requestLockedImg"), request.InUseRecord.UserId);
                }

                if (request.LockedImage != null)
                {
                    row.Cells[1].ToolTipText = rm.GetString("requestPatientLockedImage");
                }

                if (request.VipImage != null)
                {
                    row.Cells[2].ToolTipText = rm.GetString("requestPatientVipImage");
                }
                //CR#365143 - Display masked text in red color for the request having unauthorized facility
                if (request.HasMaskedRequestFacility)
                {
                    foreach (DataGridViewCell rowCell in row.Cells)
                    {
                        rowCell.ToolTipText = rm.GetString("unauthorizedFacilityRequest");
                    }
                    row.DefaultCellStyle.ForeColor = Color.Red;
                    row.DefaultCellStyle.SelectionForeColor = Color.Red;
                }
            }
        }

        #endregion
    }
}
