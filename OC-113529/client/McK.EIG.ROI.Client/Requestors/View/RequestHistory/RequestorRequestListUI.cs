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
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;

using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Base.Model;


namespace McK.EIG.ROI.Client.Requestors.View.RequestHistory
{
    public partial class RequestorRequestListUI : RequestsListUI
    {

        #region Fields

        private const string LastUpdatedColumn = "lastUpdated";
        private const string UpdatedByColumn = "updatedBy";
        private const string StatusColumn = "status";
        private const string PatientsColumn = "patientNames";
        private const string RequestIdColumn = "id";
        private const string ReceiptDateColumn = "receiptDate";
        private const string BalanceColumn = "balance";

        #endregion

        #region Constructor

        public RequestorRequestListUI()
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
            DataGridViewTextBoxColumn dgvLastUpdatedDateTimeColumn = Grid.AddTextBoxColumn(LastUpdatedColumn, "Last Updated", "LastUpdated", 110);
            dgvLastUpdatedDateTimeColumn.Tag = RequestDetails.LastUpdatedKey;
            dgvLastUpdatedDateTimeColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;

            Grid.AddTextBoxColumn(UpdatedByColumn, "Updated By", "UpdatedBy", 100).Tag = RequestDetails.UpdatedByKey;
            Grid.AddTextBoxColumn(StatusColumn, "Status", "Status", 100).Tag = SetSortProperties(RequestDetails.RequestStatusKey, 
                                                                                                    ROIConstants.RequestDomainType);
            DataGridViewTextBoxColumn dgvPatientsColumn = Grid.AddTextBoxColumn(PatientsColumn, "Patients", "PatientNames", ColumnWidth);
            dgvPatientsColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvPatientsColumn.Tag = SetSortProperties(RequestPatientDetails.NameKey, ROIConstants.PatientDomainType);

            Grid.AddTextBoxColumn(RequestIdColumn, "Request ID", "Id", 100).Tag = SetSortProperties(RequestDetails.IdKey, 
                                                                                                               ROIConstants.RequestDomainType);
            DataGridViewTextBoxColumn dgvReceiptDateColumn = Grid.AddTextBoxColumn(ReceiptDateColumn, "Receipt Date", "ReceiptDate", 105);
            dgvReceiptDateColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;
            dgvReceiptDateColumn.Tag = SetSortProperties(RequestDetails.ReceiptDateKey, ROIConstants.RequestDomainType);
            
            DataGridViewTextBoxColumn dgvBalanceColumn = Grid.AddTextBoxColumn(BalanceColumn, "Balance", "BalanceDue", 70);
            dgvBalanceColumn.Tag = SetSortProperties(RequestDetails.BalanceDueKey, ROIConstants.RequestDomainType);
            dgvBalanceColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvBalanceColumn.DefaultCellStyle.Format = "C";
            dgvBalanceColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            Grid.ColumnHeaderMouseClick += ColumnHeaderMouseClickHandler;
            //CR#365143 - Display text in red color and set tooltip for the request having unauthorized facility
            Grid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            base.InitGrid();
        }

        /// <summary>
        /// Applu localization for grid's column header text
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(Grid, rm, LastUpdatedColumn);
            SetLabel(Grid, rm, UpdatedByColumn);
            SetLabel(Grid, rm, StatusColumn);
            SetLabel(Grid, rm, PatientsColumn);
            SetLabel(Grid, rm, RequestIdColumn);
            SetLabel(Grid, rm, ReceiptDateColumn);
            SetLabel(Grid, rm, BalanceColumn);
            
            base.Localize();
        }

        /// <summary>
        /// Display masked text in red color and set tooltip for the request having unauthorized facility
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
                //CR#364666 - Display masked text in red color for the request having unauthorized facility
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
