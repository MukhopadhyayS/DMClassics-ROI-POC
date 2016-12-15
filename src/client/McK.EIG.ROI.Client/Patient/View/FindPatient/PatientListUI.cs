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
using System.Text;
using System.Windows.Forms;

using McK.EIG.Common.Audit.Model;
using McK.EIG.Common.Utility.Logging;
using McK.EIG.Common.Utility.View;

using McK.EIG.ROI.Client.Base.Controller;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.View.Common;
using McK.EIG.ROI.Client.Patient.Controller;
using McK.EIG.ROI.Client.Patient.Model;
using McK.EIG.ROI.Client.Patient.View;
using McK.EIG.ROI.Client.Requestors.Model;

namespace McK.EIG.ROI.Client.Patient.View.FindPatient
{
    /// <summary>
    /// Display the PatientListUI.
    /// </summary>
    public partial class PatientListUI : ROIBaseUI, IPatientPageContext, IFooterProvider
    {
        #region Fields

        private const string NoSearchPerformed = ".noSearchPerformed";
        private const string MaxCountExceeded  = ".morePatientFound";
        private const string ZeroResultFound   = ".noPatientFound";
        private const string PatientFound      = ".patientFound";
        private const string PatientsFound     = ".patientsFound";

        private const string VipLockedPermissionDialogTitle = "PatientVipLockedPermissionDialog.Title";
        private const string VipLockedPermissionDialogMessage = "PatientVipLockedPermissionDialog.Message";
        private const string VipLockedPermissionDialogOkButton = "PatientVipLockedPermissionDialog.OkButton";
        private const string VipLockedPermissionDialogOkButtonToolTip = "VipLockedPermissionDialog.OkButton";

        private const string LockedImageColumn = "lockedImage";
        private const string VipImageColumn    = "vipImage";
        private const string PatientNameColumn = "patientName";
        private const string DobColumn         = "dateOfBirth";
        private const string SsnColumn         = "ssn";
        private const string FacilityColumn    = "facility";
        private const string MrnColumn         = "mrn";
        private const string EpnColumn         = "epn";

        private DataGridViewRowStateChangedEventHandler rowStateChanged;
        private EIGDataGrid.RowSelectionHandler rowSelectionChangeHandler;
        private MouseEventHandler rowDoubleClickHandler;
        private EventHandler viewEditHandler;

        private Log log = LogFactory.GetLogger(typeof(PatientListUI));

        private bool isMaxCountExceeded;

        private PatientsFooterUI patientFooterUI;
        private FindPatientActionUI findPatientActionUI;

        #endregion

        #region Constructor

        /// <summary>
        /// Initialize PatientList UI.
        /// </summary>
        public PatientListUI()
        {
            InitializeComponent();
            InitGrid();
            CreateActionButtons();
            EnableUI(false);
        }

       #endregion
        
        #region Methods

        private void CreateActionButtons()
        {
            patientFooterUI = new PatientsFooterUI(this);
            patientFooterUI.Dock = DockStyle.Fill;

            findPatientActionUI = new FindPatientActionUI();
            findPatientActionUI.ViewEditButton.Click += new EventHandler(viewEditButton_Click);
            viewEditHandler = new EventHandler(DefaultViewEditHandler);

            patientFooterUI.PageActions = findPatientActionUI;
            findPatientActionUI.ViewEditButton.Enabled = false;
        }
       

        /// <summary>
        /// Initialize the Grid.
        /// </summary>
        private void InitGrid()
        {
            DataGridViewImageColumn dgvLockedImageColumn = grid.AddImageColumn(LockedImageColumn,"Locked", "LockedImage", 50);
            dgvLockedImageColumn.DefaultCellStyle.NullValue = null;
            dgvLockedImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;
            
            DataGridViewImageColumn dgvVipImageColumn = grid.AddImageColumn(VipImageColumn, "VIP", "VipImage", 35);
            dgvVipImageColumn.DefaultCellStyle.NullValue = null;
            dgvLockedImageColumn.SortMode = DataGridViewColumnSortMode.NotSortable;

            grid.AddTextBoxColumn(PatientNameColumn, "Patient Name", "Name", 150);
            DataGridViewColumn  dgvDateOfBirth = grid.AddTextBoxColumn(DobColumn, "Date of Birth", "DOB", 125);
            dgvDateOfBirth.DefaultCellStyle.Format = ROIConstants.DateFormat;

            grid.AddTextBoxColumn(SsnColumn, "SSN", "MaskedSSN", 125);
            
            grid.AddTextBoxColumn(FacilityColumn, "FacilityCode", "FacilityCode", 125);
            
            DataGridViewTextBoxColumn dgvMrnColumn = grid.AddTextBoxColumn(MrnColumn, "MRN", "MRN", ColumnWidth);            
            dgvMrnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            
            DataGridViewTextBoxColumn dgvEpnColumn = grid.AddTextBoxColumn(EpnColumn, "EPN", "EPn", ColumnWidth);            
            dgvEpnColumn.Visible      = UserData.Instance.EpnEnabled;
            dgvEpnColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;

            rowStateChanged = new DataGridViewRowStateChangedEventHandler(grid_RowStateChanged);
            rowSelectionChangeHandler = new EIGDataGrid.RowSelectionHandler(grid_SelectionChanged);
            rowDoubleClickHandler = new MouseEventHandler(grid_RowDoubleClick);
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
                ViewEditButton.PerformClick();
                e.Handled = true;
            }
        }


        protected override string GetLocalizeKey(Control control)
        {
            if (control == searchCountLabel)
            {
                return control.Name + NoSearchPerformed;
            }
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Apply localization for UI controls
        /// </summary>
        public override void Localize()
        {
            //UI Text
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            SetLabel(rm, messagePatientLabel);
            
            grid.Columns[LockedImageColumn].HeaderText = rm.GetString(LockedImageColumn + ".columnHeader");
            grid.Columns[VipImageColumn].HeaderText    = rm.GetString(VipImageColumn + ".columnHeader");
            grid.Columns[PatientNameColumn].HeaderText = rm.GetString(PatientNameColumn + ".columnHeader");
            grid.Columns[DobColumn].HeaderText         = rm.GetString(DobColumn + ".columnHeader");
            grid.Columns[SsnColumn].HeaderText         = rm.GetString(SsnColumn + ".columnHeader");
            grid.Columns[FacilityColumn].HeaderText    = rm.GetString(FacilityColumn + ".columnHeader");
            grid.Columns[MrnColumn].HeaderText         = rm.GetString(MrnColumn + ".columnHeader");
            grid.Columns[EpnColumn].HeaderText         = rm.GetString(EpnColumn + ".columnHeader");
            
            ResetCountLabelMessage();
            findPatientActionUI.SetExecutionContext(Context);
            findPatientActionUI.SetPane(Pane);
            findPatientActionUI.Localize();

            patientFooterUI.SetExecutionContext(Context);
            patientFooterUI.SetPane(Pane);
            patientFooterUI.Localize();
            
        }

        private void ResetCountLabelMessage()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            SetLabel(rm, searchCountLabel);
        }

        /// <summary>
        /// Sets the data into datagrid's DataSource object
        /// </summary>
        /// <param name="data"></param>
        public void SetData(object data)
        {
            DisableEvents();
            if (data == null)
            {
                ClearList();
                DisableButton();
                ResetCountLabelMessage();
                EnableEvents();
                return;
            }
            EnableUI(true);
            FindPatientResult searchResult = data as FindPatientResult;
            isMaxCountExceeded = searchResult.MaxCountExceeded;           
            PopulateSearchResult(searchResult);
            UpdateRowCount();
            SelectFirstRow();
            EnableEvents();
            findPatientActionUI.ViewEditButton.Focus();
        }

        /// <summary>
        /// Used it for tad order.
        /// </summary>
        /// <param name="enable"></param>
        private void EnableUI(bool enable)
        {
            this.Enabled            = enable;
            patientFooterUI.Enabled = enable;
        }

        /// <summary>
        /// Bind Data to the grid.
        /// </summary>
        /// <param name="patientResult"></param>
        private void PopulateSearchResult(FindPatientResult patientResult)
        {
            ComparableCollection<PatientDetails> list = new ComparableCollection<PatientDetails>(patientResult.PatientSearchResult);
            list.ApplySort(TypeDescriptor.GetProperties(typeof(PatientDetails))["Name"], ListSortDirection.Ascending);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(PatientDetails))["Name"], ListSortDirection.Ascending);
            grid.SetItems(list);
            grid.ClearSelection(); // clear the initial selection to generate event for ODP population
        }
        
        /// <summary>
        /// clear the grid
        /// </summary>
        public void ClearList()
        {
            grid.Rows.Clear();
            isMaxCountExceeded = false;
            UpdateRowCount();
            EnableButton();
            EnableUI(false);
        }

        /// <summary>
        /// Register events.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            grid.SelectionHandler += rowSelectionChangeHandler;
            grid.MouseDoubleClick += rowDoubleClickHandler;
            grid.RowStateChanged  += rowStateChanged;
        }

        /// <summary>
        /// UnRegister events.
        /// </summary>
        private void DisableEvents()
        {
            grid.SelectionHandler -= rowSelectionChangeHandler;
            grid.MouseDoubleClick -= rowDoubleClickHandler;
            grid.RowStateChanged  -= rowStateChanged;
        }

        private void grid_RowStateChanged(object sender, DataGridViewRowStateChangedEventArgs e)
        {
            if (grid.SelectedRows.Count == 0)
            {
                DisableButton();
            }
            else
            {
                EnableButton();
            }
        }

        /// <summary>
        /// Selects the first row in datagridview
        /// </summary>
        private void SelectFirstRow()
        {
            if (grid.Rows.Count > 0)
            {
                grid.Rows[0].Selected = true;
                EnableButton();
                return;
            }
            DisableButton();
            UpdateRowCount();
        }

        /// <summary>
        /// Enable the Buttons in ODP based on row selection condition.
        /// </summary>
        private void EnableButton()
        {
            int count = grid.SelectedRows.Count;
            findPatientActionUI.ViewEditButton.Enabled  = (count == 1);
            patientFooterUI.CreateRequestButton.Enabled = (count > 0);
            patientFooterUI.PatientRequestButton.Enabled = (count > 0);
        }

        /// <summary>
        /// Disable the button in odp.
        /// </summary>
        private void DisableButton()
        {
           findPatientActionUI.ViewEditButton.Enabled   = false;
           patientFooterUI.PatientRequestButton.Enabled = false;
           patientFooterUI.CreateRequestButton.Enabled  = false;
        }

        /// <summary>
        /// Event occurs when the user select the row in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_SelectionChanged(DataGridViewRow row)
        {
            if (grid.SelectedRows.Count == 0)
            {
                DisableButton();
            }
            else
            {
                EnableButton();
            }
        }

        /// <summary>
        /// Event occurs when the user Double click in the grid.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void grid_RowDoubleClick(object sender, MouseEventArgs e)
        {
            if (grid.Rows.Count == 0) return;
            if (grid.SelectedRows.Count == 1)
            {
                DataGridView.HitTestInfo hti = grid.HitTest(e.X, e.Y);
                if (hti.Type != DataGridViewHitTestType.ColumnHeader)
                {
                    ViewEditHandler(sender, e);
                }
            }
        }

        private void viewEditButton_Click(object sender, EventArgs e)
        {
            ViewEditHandler(sender, e);
        }

        private void DefaultViewEditHandler(object sender, EventArgs e)
        {
            ShowPatientInfo();
        }

        /// <summary>
        /// Show the Patient Information
        /// </summary>
        private void ShowPatientInfo()
        {            
            try
            {
                PatientDetails patientInfo = grid.SelectedRows[0].DataBoundItem as PatientDetails;
                ROIViewUtility.MarkBusy(true);
                if (patientInfo.IsHpf)
                {
                    if (!HasVipLockedPermission(patientInfo)) return;
                    patientInfo = PatientController.Instance.RetrieveHpfPatient(patientInfo.MRN, patientInfo.FacilityCode);
                    //CR#375119
                    patientInfo = PatientController.Instance.RetrieveSuppmentarityDocuments(patientInfo);
                    patientInfo = PatientController.Instance.RetrieveSuppmentarityAttachments(patientInfo);
                }
                else
                {
                    patientInfo = PatientController.Instance.RetrieveSupplementalPatient(patientInfo.Id, true);
                    patientInfo = PatientController.Instance.RetrieveSuppmentalDocuments(patientInfo);
                    patientInfo = PatientController.Instance.RetrieveSuppmentalAttachments(patientInfo);
                }
                ApplicationEventArgs ae = new ApplicationEventArgs(patientInfo, this);
                PatientEvents.OnPatientSelected(Pane, ae);

                //Audit for viewing the patient search record.
                string message = string.Format(ROIConstants.ViewPatientRecordRemarks, "\"" + patientInfo.name + "\"");
                createViewPatientRecordEventMessage(message, patientInfo);   
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
        /// Audit for viewing the patient search record.
        /// </summary>
        /// <param name="auditmessage"></param>
        /// <param name="patientDetails"></param>
        private void createViewPatientRecordEventMessage(string auditmessage, PatientDetails patientDetails)
        {

            //create the audit log in audit table
            AuditEvent auditEvent = new AuditEvent();

            auditEvent.UserId = UserData.Instance.UserInstanceId;
            auditEvent.EventStart = System.DateTime.Now;
            auditEvent.Facility = patientDetails.facilityCode;
            auditEvent.ActionCode = ROIConstants.ViewPatientRecordActionCode;
            auditEvent.Comment = auditmessage.ToString();
            auditEvent.Mrn = patientDetails.mrn;

            try
            {
                Application.DoEvents();
                ROIController.Instance.CreateAuditEntry(auditEvent);
            }
            catch (ROIException cause)
            {
                log.FunctionFailure(cause);

            }
        }

        private bool HasVipLockedPermission(PatientDetails patient)
        {
            if (patient.IsVip || patient.EncounterLocked)
            {
                if (!IsAllowed(ROISecurityRights.ROIVipStatus) ||
                    !IsAllowed(ROISecurityRights.AccessLockedRecords))
                {
                    if (!ShowVipLockedAlert())
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        private bool ShowVipLockedAlert()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string messageText = rm.GetString(VipLockedPermissionDialogMessage);
            string titleText = rm.GetString(VipLockedPermissionDialogTitle);
            string okButtonText = rm.GetString(VipLockedPermissionDialogOkButton);

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            string okButtonToolTip = rm.GetString(VipLockedPermissionDialogOkButtonToolTip);

            return ROIViewUtility.ConfirmChanges(titleText, messageText, okButtonText, okButtonToolTip, ROIDialogIcon.Alert);
        }

        /// <summary>
        /// Remove the deleted patient from the search result.
        /// </summary>
        /// <param name="p"></param>
        public void DeleteRow(object data)
        {
            if (grid.Items == null) return;
            grid.DeleteItem(data);
            UpdateRowCount();

            if (grid.SelectedRows.Count == 0)
            {
                DisableButton();
            }
        }

        public void UpdateRow(object data)
        {
            if (grid.Items == null) return;
            if (grid.Items.Contains(data))
            {
                grid.UpdateItem(data);
            }
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
                searchCountLabel.Text = rm.GetString(searchCountLabel.Name + MaxCountExceeded);
            }
            else
            {
                if (rowCount == 0)
                {
                    searchCountLabel.Text = rm.GetString(searchCountLabel.Name + ZeroResultFound);
                }
                else if (rowCount == 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + PatientFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
                else if (rowCount > 1)
                {
                    string countText = rm.GetString(searchCountLabel.Name + PatientsFound);
                    searchCountLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countText, rowCount);
                }
            }
        }

        public Collection<PatientDetails> RetrievePatients()
        {
            List<PatientDetails> selectedPatients = new List<PatientDetails>();
            foreach (DataGridViewRow row in grid.SelectedRows)
            {
                selectedPatients.Add((PatientDetails)row.DataBoundItem);
            }
            selectedPatients.Reverse();

            return new Collection<PatientDetails>(selectedPatients);
        }

        private void Process_CancelCreateRequest(object sender, EventArgs e)
        {
            ROIEvents.OnNavigationChange(sender, new ApplicationEventArgs(ROIConstants.FindPatients, e));
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            if (!IsAllowed(ROISecurityRights.ROICreateRequest))
            {
                HideCreateRequestButton  = true;
                HidePatientRequestButton = true;
            }
        }

        #endregion

        #region Properties

        public PatientDetails SelectedPatientInfo
        {
            get
            {
                if (grid.SelectedRows.Count == 0)
                {
                    return null;
                }
                return grid.SelectedRows[0].DataBoundItem as PatientDetails;
            }
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

        /// <summary>
        /// 
        /// </summary>

        public Button ViewEditButton
        {
            get { return findPatientActionUI.ViewEditButton; }
        }

        /// <summary>
        /// Returns the grid; Used for Reports
        /// </summary>
        public bool IsMultipleSelect
        {
            set { grid.MultiSelect = value; }
            get { return grid.MultiSelect; }
        }

        /// <summary>
        /// Returns label;
        /// </summary>
        public Label GridInfoLabel
        {
            get { return messagePatientLabel; }
        }


        //Returns DataGrdiView. Note:: Used this property in Reports - AOD -Find patient dialog.
        public DataGridView Grid
        {
            get { return grid; }
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
            get { return patientFooterUI.CreateRequestButton.Visible; }
            set { patientFooterUI.CreateRequestButton.Visible = !value; }
        }

        public bool HidePatientRequestButton
        {
            get { return patientFooterUI.PatientRequestButton.Visible; }
            set { patientFooterUI.PatientRequestButton.Visible = !value; }
        }

        public Color FooterPanelBackColor
        {
            get { return patientFooterUI.FooterPanel.BackColor; }
            set { patientFooterUI.FooterPanel.BackColor = value; }
        }

        public EIGDataGrid.RowSelectionHandler RowSelectionChangeHandler
        {
            get { return rowSelectionChangeHandler; }
            set { rowSelectionChangeHandler = value; }
        }

        #endregion

        #region IPatientPageContext Members

        public Collection<PatientDetails> Patients
        {
            get { return RetrievePatients(); }
        }

        public EventHandler CancelCreateRequestHandler
        {
            get { return new EventHandler(Process_CancelCreateRequest); }
        }

        #endregion

        #region IFooterProvider Members

        public Control RetrieveFooterUI()
        {
            return patientFooterUI;
        }

        #endregion      
        
    }
}
