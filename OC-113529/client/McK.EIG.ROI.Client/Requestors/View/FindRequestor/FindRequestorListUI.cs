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
using System.ComponentModel;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Windows.Forms;

using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Requestors.Controller;
using McK.EIG.ROI.Client.Requestors.Model;
using System.Collections.ObjectModel;

namespace McK.EIG.ROI.Client.Requestors.View.FindRequestor
{
    /// <summary>
    /// Display the RequestorListUI.
    /// </summary>
    public partial class FindRequestorListUI : ROIBaseUI, IRequestorPageContext, IFooterProvider
    {
        #region Fields

        private const string NoSearchPerformed         = ".noSearchPerformed";
        private const string RequestorMaxCountExceeded = ".moreRequestorFound";
        private const string ZeroRequestorFound        = ".norequestorFound";
        private const string RequestorFound            = ".requestorFound";
        private const string RequestorsFound           = ".requestorsFound";

        private const string AllRequestors           = "All Requestor Types";
        private const string RequestorNameColumn     = "requestorName";
        private const string AddressColumn           = "address";
        private const string PhoneColumn             = "phone";
        private const string FaxColumn               = "fax";
        private const string PatientDobColumn        = "dob";
        private const string PatientSsnColumn        = "ssn";
        private const string PatientMrnColumn        = "mrn";
        private const string PatientFacilityColumn   = "facility";
        private const string PatientEpnColumn        = "epn";
        private const string StatusColumn            = "status";

        private bool isMaxCountExceeded;

        private EIGDataGrid.RowSelectionHandler rowSelectionChangeHandler;
        private EventHandler viewEditHandler;
        private MouseEventHandler rowDoubleClickHandler;

        private FindRequestorActionUI findRequestorActionUI;
        private RequestorFooterUI requestorFooterUI;
        
        #endregion

        #region Constructor

        /// <summary>
        /// Initialize FindRequestorList UI.
        /// </summary>
        public FindRequestorListUI()
        {
            InitializeComponent();
            InitGrid();
            viewEditHandler = new EventHandler(DefaultViewEditRequestorHandler);
            CreateActionButton();
            EnableUI(false);
        }
       
        #endregion
        
        #region Methods

        /// <summary>
        /// Initialize the Grid.
        /// </summary>
        private void InitGrid()
        {
            grid.AddTextBoxColumn(RequestorNameColumn, "Requestor Name", "Name", 150);

            grid.AddTextBoxColumn(AddressColumn, "Address", "MainAddress", 200);
            grid.AddTextBoxColumn(PhoneColumn, "Phone", "Phone", 100);
            grid.AddTextBoxColumn(FaxColumn, "Fax", "Fax", 100);
            grid.Columns[AddressColumn].AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            DataGridViewTextBoxColumn dgvDOBColumn = grid.AddTextBoxColumn(PatientDobColumn, "DOB", "PatientDob", 150);
            dgvDOBColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvDOBColumn.DefaultCellStyle.Format = ROIConstants.DateFormat;

            DataGridViewTextBoxColumn dgvSSNColumn = grid.AddTextBoxColumn(PatientSsnColumn, 
                                                                           "SSN", 
                                                                           "MaskedPatientSSN", 
                                                                           150);
            dgvSSNColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            DataGridViewTextBoxColumn dgvMrnColumn = grid.AddTextBoxColumn(PatientMrnColumn,
                                                                           "MRN", "PatientMrn", 150);
            dgvMrnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            DataGridViewTextBoxColumn dgvFacilityColumn = grid.AddTextBoxColumn(PatientFacilityColumn,
                                                                           "Facility", "PatientFacilityCode", 150);
            dgvFacilityColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            DataGridViewTextBoxColumn dgvEpnColumn = grid.AddTextBoxColumn(PatientEpnColumn, "EPN", "PatientEpn", ColumnWidth);
            dgvEpnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            dgvEpnColumn.Visible = UserData.Instance.EpnEnabled;

            DataGridViewTextBoxColumn dgvStatusColumn = grid.AddTextBoxColumn(StatusColumn, "Status", "Status", ColumnWidth);
            
            dgvStatusColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            rowSelectionChangeHandler = new EIGDataGrid.RowSelectionHandler(grid_SelectionChanged);
            rowDoubleClickHandler     = new MouseEventHandler(grid_RowDoubleClick);
            grid.KeyDown += new KeyEventHandler(Grid_KeyDown);
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
                findRequestorActionUI.viewEditButton.PerformClick();
                e.Handled = true;
            }

            return;
        }

        private void CreateActionButton()
        {
            requestorFooterUI = new RequestorFooterUI(this);
            requestorFooterUI.Dock = DockStyle.Fill;
            //buttonPanel.Controls.Add(requestorFooterUI);

            findRequestorActionUI = new FindRequestorActionUI();

            findRequestorActionUI.viewEditButton.Click += new EventHandler(viewEditButton_Click);
            findRequestorActionUI.viewEditButton.Enabled = false;

            requestorFooterUI.PageActions = findRequestorActionUI;
        }


        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, messageLabel);
            SetLabel(rm, searchCountLabel);            

            grid.Columns[RequestorNameColumn].HeaderText   = rm.GetString(RequestorNameColumn + ".columnHeader");
            grid.Columns[AddressColumn].HeaderText         = rm.GetString(AddressColumn + ".columnHeader");
            grid.Columns[PhoneColumn].HeaderText           = rm.GetString(PhoneColumn + ".columnHeader");
            grid.Columns[FaxColumn].HeaderText             = rm.GetString(FaxColumn + ".columnHeader");
            grid.Columns[PatientDobColumn].HeaderText      = rm.GetString(PatientDobColumn + ".columnHeader");
            grid.Columns[PatientSsnColumn].HeaderText      = rm.GetString(PatientSsnColumn + ".columnHeader");
            grid.Columns[PatientMrnColumn].HeaderText      = rm.GetString(PatientMrnColumn + ".columnHeader");
            grid.Columns[PatientFacilityColumn].HeaderText = rm.GetString(PatientFacilityColumn + ".columnHeader");

            grid.Columns[PatientEpnColumn].HeaderText    = rm.GetString(PatientEpnColumn + ".columnHeader");
            grid.Columns[StatusColumn].HeaderText        = rm.GetString(StatusColumn + ".columnHeader");

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());

            findRequestorActionUI.SetExecutionContext(Context);
            findRequestorActionUI.SetPane(Pane);
            findRequestorActionUI.Localize();
            SetTooltip(rm, toolTip, findRequestorActionUI.ViewEditButton);

            requestorFooterUI.SetExecutionContext(Context);
            requestorFooterUI.SetPane(Pane);
            requestorFooterUI.Localize();
            SetTooltip(rm, toolTip, requestorFooterUI.createRequestButton);
        }

        /// <summary>
        /// Apply Localization for UI controls.
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            if (control == searchCountLabel)
            {
                return control.Name + NoSearchPerformed;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Gets the LocalizeKey of UI controls to show tooltip message
        /// </summary>
        /// <param name="control"></param>
        /// <param name="toolTip"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control, ToolTip toolTip)
        {
            if (control == findRequestorActionUI.ViewEditButton)
            {
                return control.Name;
            }
            return control.Name + "." + GetType().Name;
        }

        /// <summary>
        /// Sets the data into view.
        /// </summary>
        /// <param name="data"></param>
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
            FindRequestorResult searchResult = data as FindRequestorResult;
            isMaxCountExceeded = searchResult.MaxCountExceeded;
            PopulateSearchResult(searchResult);
            EnableEvents();
            SelectFirstRow();
            findRequestorActionUI.viewEditButton.Focus();
        }

        /// <summary>
        /// Clear Rows in the grid.
        /// </summary>
        public void ClearList()
        {
            grid.Rows.Clear();
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            searchCountLabel.Text = rm.GetString("searchCountLabel.noSearchPerformed");
            EnableButtons(false);
            EnableUI(false);
        }

        /// <summary>
        /// Bind Data to the grid.
        /// </summary>
        /// <param name="patientResult"></param>
        private void PopulateSearchResult(FindRequestorResult requestorResult)
        {
            grid.Columns[PatientDobColumn].Visible = requestorResult.RequestorTypeId <= 0;
            grid.Columns[PatientSsnColumn].Visible = requestorResult.RequestorTypeId <= 0 ;
            grid.Columns[PatientEpnColumn].Visible = UserData.Instance.EpnEnabled && requestorResult.RequestorTypeId <= 0;
            grid.Columns[PatientMrnColumn].Visible = requestorResult.RequestorTypeId <= 0;
            grid.Columns[PatientFacilityColumn].Visible = requestorResult.RequestorTypeId <= 0;

            ComparableCollection<RequestorDetails> list = new ComparableCollection<RequestorDetails>(requestorResult.RequestorSearchResult);
            list.ApplySort(TypeDescriptor.GetProperties(typeof(RequestorDetails))["FullName"], ListSortDirection.Ascending);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(RequestorDetails))["Name"], ListSortDirection.Ascending);
            grid.SetItems(list);
            grid.ClearSelection(); // clear the initial selection to generate event for ODP population
            UpdateRowCount();
        }

        /// <summary>
        /// Update the Rowcount Label
        /// </summary>
        private void UpdateRowCount()
        {
            int rowCount = grid.Rows.Count;
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            if (isMaxCountExceeded)
            {
                searchCountLabel.Text = rm.GetString(searchCountLabel.Name + RequestorMaxCountExceeded);
            }
            else
            {
                if (rowCount == 0)
                {
                    searchCountLabel.Text = rm.GetString(searchCountLabel.Name + ZeroRequestorFound);
                }
                else if (rowCount == 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + RequestorFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
                else if (rowCount > 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + RequestorsFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
            }
        }


        /// <summary>
        /// Enable the buttons in ODP.
        /// </summary>
        private void EnableButtons(bool enable)
        {
            findRequestorActionUI.viewEditButton.Enabled = enable;
            requestorFooterUI.createRequestButton.Enabled = enable && SelectedRequestor.IsActive;       
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

     
        private void grid_SelectionChanged(DataGridViewRow row)
        {
            EnableButtons(grid.SelectedRows.Count != 0);
        }

        /// <summary>
        /// Event occurs when the user Double click in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowDoubleClick(object sender, MouseEventArgs e)
        {
            if (grid.Rows.Count == 0) return;
            DataGridView.HitTestInfo hti = grid.HitTest(e.X, e.Y);
            if (hti.Type != DataGridViewHitTestType.ColumnHeader)
            {
                ViewEditHandler(sender, e);
            }
        }
        /// <summary>
        /// Selected requestor information will be displayed
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void viewEditButton_Click(object sender, EventArgs e)
        {
            if (grid.Rows.Count == 0) return;
            ViewEditHandler(sender, e);
        }

        private void DefaultViewEditRequestorHandler(object sender, EventArgs e)
        {
            ShowRequestorInfo();
        }

        private void ShowRequestorInfo()
        {
            try
            {
                RequestorDetails selectedRequestor = (RequestorDetails)grid.SelectedRows[0].DataBoundItem;
                selectedRequestor = RequestorController.Instance.RetrieveRequestor(selectedRequestor.Id, true);
                
                //If the selected requestor is inactive then
                //disable the menu item "Create Request with Requestor" in Requestor menu
                RequestorEvents.OnRequestorStatusChanged(Pane, 
                                                         new ApplicationEventArgs(selectedRequestor.IsActive, this));

                ApplicationEventArgs ae = new ApplicationEventArgs(selectedRequestor, this);
                RequestorEvents.OnRequestorSelected(Pane, ae);
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

        /// <summary>
        /// Subscribe Events
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            grid.SelectionHandler += rowSelectionChangeHandler;
            grid.MouseDoubleClick += rowDoubleClickHandler;
        }

        /// <summary>
        /// Unsubscribe Events.
        /// </summary>
        private void DisableEvents()
        {
            grid.SelectionHandler -= rowSelectionChangeHandler;
            grid.MouseDoubleClick -= rowDoubleClickHandler;
        }

        /// <summary>
        /// Deletes the selected requestor from the grid view.
        /// </summary>
        /// <param name="data"></param>
        public void DeleteRow(object data)
        {
            if (grid.Items == null) return;
            grid.DeleteItem(data);
            UpdateRowCount();

            if (grid.SelectedRows.Count == 0)
            {
                EnableButtons(false);
            }
        }

        /// <summary>
        /// Update the requestor's details in ODP list.
        /// </summary>
        /// <param name="data"></param>
        public void UpdatedRow(object data)
        {
            if (grid.Items == null) return;
            if (grid.Items.Contains(data))
            {
                grid.UpdateItem(data);
            }
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.FindRequestor, e));
        }


        /// <summary>
        /// Used it for tad order.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableUI(bool enable)
        {
            this.Enabled = enable;
            requestorFooterUI.Enabled = enable;
        }   


        #endregion

        #region Security Rights

        public void ApplySecurityRights()
        {
            //Collection<string> securityRightIds = new Collection<string>();
            //securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            //securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(ROISecurityRights.ROICreateRequest))
            {
                HideCreateRequestButton = true;
            }
        }

        #endregion

        #region Properties
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


        public RequestorDetails SelectedRequestor
        {
            get
            {
                if (grid.SelectedRows.Count == 0)
                {
                    return null;
                }

                return grid.SelectedRows[0].DataBoundItem as RequestorDetails;
            }
        }

        #endregion

        #region Event wiring properties

        public EventHandler ViewEditHandler
        {
            get { return viewEditHandler; }
            set { viewEditHandler = value; }
        }

        public bool HideCreateRequestButton
        {
            get { return requestorFooterUI.createRequestButton.Visible;   }
            set { requestorFooterUI.createRequestButton.Visible = !value; }
        }

        public Color FooterPanelBackColor
        {
            get { return requestorFooterUI.FooterPanel.BackColor; }
            set { requestorFooterUI.FooterPanel.BackColor = value; }
        }

        public EIGDataGrid.RowSelectionHandler RowSelectionChangeHandler
        {
            get { return rowSelectionChangeHandler; }
            set { rowSelectionChangeHandler = value; }
        }

        #endregion

        #region IRequestorPageContent Members

        public RequestorDetails Requestor
        {
            get { return (RequestorDetails)grid.SelectedRows[0].DataBoundItem; }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return requestorFooterUI;
        }

        #endregion

    }
}
