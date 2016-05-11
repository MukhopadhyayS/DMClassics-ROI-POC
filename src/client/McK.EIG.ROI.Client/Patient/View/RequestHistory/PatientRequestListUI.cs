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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Request.Model;
using McK.EIG.ROI.Client.Request.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Base.View.Common;

namespace McK.EIG.ROI.Client.Patient.View.RequestHistory
{
    public partial class PatientRequestListUI : RequestsListUI
    {
        #region Fields

        private const string LastUpdatedColumn = "lastUpdated";
        private const string FacilityColumn = "facility";
        private const string UpdatedByColumn = "updatedBy";
        private const string StatusColumn = "status";
        private const string RequestorNameColumn = "requestorName";
        private const string RequestorTypeColumn = "requestorTypeName";
        private const string RequestIdColumn = "id";
        private const string ReceiptDateColumn = "receiptDate";
        private const string BalanceColumn = "balance";

        private PatientDetails patient;

        #endregion

        #region Constructor

        public PatientRequestListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the grid
        /// </summary>
        public override void InitGrid()
        {

            DataGridViewTextBoxColumn dgvLastUpdatedDateTimeColumn = Grid.AddTextBoxColumn(LastUpdatedColumn, "Last Updated", "LastUpdated", 110);
            dgvLastUpdatedDateTimeColumn.Tag = RequestDetails.LastUpdatedKey;
            dgvLastUpdatedDateTimeColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;
                                                                   
            Grid.AddTextBoxColumn(FacilityColumn, "Facility", "Facility", 110).Tag = RequestDetails.LastUpdatedKey;
            Grid.AddTextBoxColumn(UpdatedByColumn, "Updated By", "UpdatedBy", 100).Tag = RequestDetails.UpdatedByKey;
            Grid.AddTextBoxColumn(StatusColumn, "Status", "Status", 100).Tag = SetSortProperties(RequestDetails.RequestStatusKey, ROIConstants.RequestDomainType);

            DataGridViewTextBoxColumn dgvRequestorColumn = Grid.AddTextBoxColumn(RequestorNameColumn, "Requestor Name", "RequestorName", 125);
            //dgvRequestorColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvRequestorColumn.Tag = SetSortProperties(RequestDetails.RequestorNameKey, ROIConstants.RequestorDomainType);

            Grid.AddTextBoxColumn(RequestorTypeColumn, "Requestor Type", "RequestorTypeName", 100).Tag = SetSortProperties(RequestDetails.RequestorTypeKey, ROIConstants.RequestorDomainType);
            Grid.AddTextBoxColumn(RequestIdColumn, "Request ID", "Id", 100).Tag = SetSortProperties(RequestDetails.IdKey, ROIConstants.RequestDomainType);
            DataGridViewTextBoxColumn dgvReceiptDateColumn = Grid.AddTextBoxColumn(ReceiptDateColumn, "Receipt Date", "ReceiptDate", 105);
            dgvReceiptDateColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;
            dgvReceiptDateColumn.Tag = SetSortProperties(RequestDetails.ReceiptDateKey, ROIConstants.RequestDomainType);

            DataGridViewTextBoxColumn dgvBalanceColumn = Grid.AddTextBoxColumn(BalanceColumn, "Balance", "BalanceDue", 70);
            dgvBalanceColumn.Tag = SetSortProperties(RequestDetails.BalanceDueKey, ROIConstants.RequestDomainType);
            dgvBalanceColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.MiddleRight;
            dgvBalanceColumn.DefaultCellStyle.Format = "C";
            dgvBalanceColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvBalanceColumn.MinimumWidth = 100;
            Grid.ScrollBars = ScrollBars.Both;

            Grid.ColumnHeaderMouseClick += ColumnHeaderMouseClickHandler;
			//CR#365143 - Display text in red color and set tooltip for the request having unauthorized facility
            Grid.DataBindingComplete += new DataGridViewBindingCompleteEventHandler(grid_DataBindingComplete);
            base.InitGrid();
        }

        /// <summary>
        /// Apply localization for grid's column header text
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(Grid, rm, LastUpdatedColumn);
            SetLabel(Grid, rm, FacilityColumn);
            SetLabel(Grid, rm, UpdatedByColumn);
            SetLabel(Grid, rm, StatusColumn);
            SetLabel(Grid, rm, RequestorNameColumn);
            SetLabel(Grid, rm, RequestorTypeColumn);
            SetLabel(Grid, rm, RequestIdColumn);
            SetLabel(Grid, rm, ReceiptDateColumn);
            SetLabel(Grid, rm, BalanceColumn);

            patient = ((PatientRSP)Pane.ParentPane.ParentPane).HistoryEditor.PatientInfo;

            base.Localize();
        }

        protected override Collection<RequestDetails> GroupByFacility(Collection<RequestDetails> requests)
        {
            bool epnEnabled = UserData.Instance.EpnEnabled;
            ArrayList facilities = new ArrayList();
            Collection<RequestDetails> facilityRequests = new Collection<RequestDetails>();
            RequestDetails facilityRequest = null;
            foreach (RequestDetails request in requests)
            {
                facilityRequests.Add(request);
                bool addRequest = false;
                foreach (RequestPatientDetails patient in request.Patients.Values)
                {
                    if (epnEnabled)
                    {
                        if (patient.EPN.Equals(this.patient.EPN) && !facilities.Contains(request.Id + "." + patient.Id +"." + patient.IsHpf + "." + patient.FacilityCode))
                        {
                            if (addRequest)
                            {
                                facilityRequest = (RequestDetails)ROIViewUtility.DeepClone(request);
                                facilityRequest.Facility = patient.FacilityCode;
                                //newrequests.Add(facilityRequest);
                                facilityRequests.Add(facilityRequest);
                            }
                            else
                            {
                                request.Facility = patient.FacilityCode;
                            }

                            facilities.Add(request.Id + "." + 
                                           patient.Id + "." + 
                                           patient.IsHpf + "." + patient.FacilityCode);

                            addRequest = true;
                        }
                    }
                    else
                    {
                        request.Facility = this.patient.FacilityCode;
                    }
                }
            }

            return requests = facilityRequests;
        }
		
		/// <summary>
        /// CR#365143 - Display masked text in red color and set tooltip for the request having unauthorized facility
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
