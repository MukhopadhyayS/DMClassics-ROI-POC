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
using System.ComponentModel;
using System.Drawing;
using System.Resources;
using System.Text;
using System.Windows.Forms;

using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.OverDueInvoice.Model;

namespace McK.EIG.ROI.Client.OverDueInvoice.View.FindOverDueInvoice
{
    /// <summary>
    /// FindInvoiceByFacilityListUI
    /// </summary>
    public partial class FindOverDueInvoiceListUI : OverDueInvoiceListUI
    {
        #region Constructor

        public FindOverDueInvoiceListUI()
        {
            InitializeComponent();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the past due invoice grid.
        /// </summary>
        public override void InitGrid()
        {
            base.InitGrid();
            dgvColumnHeader = new EIGCheckedColumnHeader();
            DataGridViewCheckBoxColumn dgvCheckColumn = pastDueInvoiceGrid.AddCheckBoxColumn(CheckBoxColumn, string.Empty, "InvoiceSelected", 25);
            dgvCheckColumn.HeaderCell = dgvColumnHeader;
            dgvCheckColumn.Resizable = DataGridViewTriState.False;
            dgvCheckColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            DataGridViewTextBoxColumn dgvFacilityColumn = pastDueInvoiceGrid.AddTextBoxColumn(FacilityColumn, "FacilityCode", "BillingLocation", 120);
            DataGridViewTextBoxColumn dgvRequestorTypeColumn = pastDueInvoiceGrid.AddTextBoxColumn(RequestorTypeColumn, "RequestorType", "RequestorType", 120);
            DataGridViewTextBoxColumn dgvRequestorNameColumn = pastDueInvoiceGrid.AddTextBoxColumn(RequestorNameColumn, "RequestorName", "RequestorName", 140);
            DataGridViewTextBoxColumn dgvInvoiceNumberColumn = pastDueInvoiceGrid.AddTextBoxColumn(InvoiceNumberColumn, "InvoiceNumber", "InvoiceNumber", 117);
            dgvInvoiceNumberColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopRight;
            DataGridViewTextBoxColumn dgvRequestIdColumn = pastDueInvoiceGrid.AddTextBoxColumn(RequestIdColumn, "RequestId", "RequestId", 95);
            dgvRequestIdColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopRight;
            DataGridViewTextBoxColumn dgvOverDueAmountColumn = pastDueInvoiceGrid.AddTextBoxColumn(OverDueAmountColumn, "OverDueAmount", "OverDueAmount", 134);
            dgvOverDueAmountColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopRight;
            dgvOverDueAmountColumn.DefaultCellStyle.Format = "C";
            DataGridViewTextBoxColumn dgvDueDaysColumn = pastDueInvoiceGrid.AddTextBoxColumn(OverDueDaysColumn, "# Days Overdue", "OverDueDays", 120);
            dgvDueDaysColumn.CellTemplate.Style.Alignment = DataGridViewContentAlignment.TopRight;
            DataGridViewTextBoxColumn dgvPhoneNumberColumn = pastDueInvoiceGrid.AddTextBoxColumn(PhoneNumberColumn, "PhoneNumber", "PhoneNumber", 100);
            dgvPhoneNumberColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvPhoneNumberColumn.MinimumWidth = 100;
            DataGridViewTextBoxColumn dgvRequestorIdColumn = pastDueInvoiceGrid.AddTextBoxColumn(RequestorIdColumn, "RequestorId", "RequestorId", 0);
            DataGridViewTextBoxColumn dgvInvoiceAgeColumn = pastDueInvoiceGrid.AddTextBoxColumn(InvoiceAgeColumn, "InvoiceAge", "InvoiceAge", 0);

            dgvRequestorIdColumn.Visible = false;
            dgvInvoiceAgeColumn.Visible = false;
            dgvCheckColumn.TrueValue = true;
            dgvCheckColumn.FalseValue = false;
            dgvFacilityColumn.ReadOnly = true;
            dgvRequestorTypeColumn.ReadOnly = true;
            dgvRequestorNameColumn.ReadOnly = true;
            dgvInvoiceNumberColumn.ReadOnly = true;
            dgvRequestIdColumn.ReadOnly = true;
            dgvOverDueAmountColumn.ReadOnly = true;
            dgvDueDaysColumn.ReadOnly = true;
            dgvPhoneNumberColumn.ReadOnly = true;
            pastDueInvoiceGrid.MultiSelect = false;
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            base.Localize();            
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, invoiceMessageLabel);

            pastDueInvoiceGrid.Columns[FacilityColumn].HeaderText      = rm.GetString(FacilityColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[RequestorTypeColumn].HeaderText = rm.GetString(RequestorTypeColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[RequestorNameColumn].HeaderText = rm.GetString(RequestorNameColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[InvoiceNumberColumn].HeaderText = rm.GetString(InvoiceNumberColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[RequestIdColumn].HeaderText     = rm.GetString(RequestIdColumn + ".columnHeader");

            pastDueInvoiceGrid.Columns[OverDueAmountColumn].HeaderText = rm.GetString(OverDueAmountColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[OverDueDaysColumn].HeaderText   = rm.GetString(OverDueDaysColumn + ".columnHeader");
            pastDueInvoiceGrid.Columns[PhoneNumberColumn].HeaderText   = rm.GetString(PhoneNumberColumn + ".columnHeader");
        }

        /// <summary>
        /// Gets the default sort column in past due invoice grid.
        /// </summary>
        protected override string DefaultSort
        {
            get { return "BillingLocation"; }
        }

        #endregion
    }
}
