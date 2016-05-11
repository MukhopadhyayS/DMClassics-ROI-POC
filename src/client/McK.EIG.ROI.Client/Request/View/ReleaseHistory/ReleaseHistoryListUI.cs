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
using System.Resources;
using System.Collections;
using System.Globalization;
using System.ComponentModel;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Collections.ObjectModel;

using McK.EIG.Common.Utility.View;
using McK.EIG.ROI.Client.Base.View;
using McK.EIG.ROI.Client.Base.Model;
using McK.EIG.ROI.Client.Base.View.Common;

using McK.EIG.ROI.Client.Request.Model;

namespace McK.EIG.ROI.Client.Request.View.ReleaseHistory
{
    public partial class ReleaseHistoryListUI : ROIBaseUI
    {
        #region Fields

        private const string ReleaseDateTimeColumn = "releaseDate";
        private const string PatientColumn = "PatientName";
        private const string EncounterColumn = "encounter";
        private const string DocDetailColumn = "docDetail";
        private const string PagesColumn = "pages";
        private const string ShippingAddressColumn = "shippingAddress";
        private const string ShippingMethodColumn = "shippingMethod";
        private const string QueueSecureColumn = "queuePassword";
        private const string RequestSecureColumn = "requestPassword";
        private const string TrackNoColumn = "trackNo";
        private const string UserNameColumn = "userName";
        private const string ReleaseFound = ".releaseFound";
        private const string ReleasesFound = ".releasesFound";
        private const string PageFound = ".pageFound";
        private const string PagesFound = ".pagesFound";
        private const string NameColumn = "name";

        private ReleaseHistoryDetails releaseHistory;
        private ReleasedPatientDetails releasePatient;

        private EventHandler UserNameSelectionChanged;
        private EventHandler FilterPatientSelectionChanged;
        private EventHandler presetDateRangeHandler;

        private string patientsKey;

        #endregion

        #region Constructor
        
        public ReleaseHistoryListUI()
        {
            InitializeComponent();
            InitGrid();
            outerPanel.SetColumnSpan(releaseListpanel, 2);
            //CR337443
            epnLabel.Visible = UserData.Instance.EpnEnabled;
            presetDateRangeHandler = new EventHandler(Process_PresetDateRangedHandler);
            EnableEvents();
        }

        #endregion

        #region Methods

        /// <summary>
        /// Initializes the grid.
        /// </summary>
        private void InitGrid()
        {
            patientList.AutoGenerateColumns = false;
            DataGridViewTextBoxColumn nameColumn = patientList.AddTextBoxColumn(NameColumn, "Patients", "Name", 30);
            patientList.SortEnabled = false;
            nameColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            
            DataGridViewTextBoxColumn  dgvReleaseDateTimeColumn = grid.AddTextBoxColumn(ReleaseDateTimeColumn, "Date | Time", "ReleasedDate", 70);
            dgvReleaseDateTimeColumn.DefaultCellStyle.Format = System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.ShortDatePattern + ' ' +
                                                               System.Threading.Thread.CurrentThread.CurrentUICulture.DateTimeFormat.LongTimePattern.Replace(":ss", "");
            grid.AddTextBoxColumn(PatientColumn, "Patient Name", "PatientName", 100);
            grid.AddTextBoxColumn(EncounterColumn, "Encounter", "Encounter", 80);
            grid.AddTextBoxColumn(DocDetailColumn, "Document - Version Subtitle", "DocumentVersionSubtitle", ColumnWidth);
            grid.AddTextBoxColumn(PagesColumn, "Pages", "Pages", 50);
            grid.AddTextBoxColumn(RequestSecureColumn, "Request Password", "PlainRequestPassword", 100);
            grid.AddTextBoxColumn(QueueSecureColumn, "Queue Password", "PlainQueuePassword", 100);         
            DataGridViewTextBoxColumn addressColumn = grid.AddTextBoxColumn(ShippingAddressColumn, "Shipping Address", "ShippingAddress", 140);
            //addressColumn.AutoSizeMode = DataGridViewAutoSizeColumnMode.Fill;
            addressColumn.DefaultCellStyle.WrapMode  = DataGridViewTriState.False;            
            grid.AddTextBoxColumn(ShippingMethodColumn, "Shipping Method", "ShippingMethod", 60);
                       grid.AddTextBoxColumn(TrackNoColumn, "Tracking Number", "TrackingNumber", 70);
            grid.AddTextBoxColumn(UserNameColumn, "User Name", "UserName", 100);            
        }

        /// <summary>
        /// Localizing the UI.
        /// </summary>
        public override void Localize()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());

            grid.Columns[ReleaseDateTimeColumn].HeaderText = rm.GetString(ReleaseDateTimeColumn + ".columnHeader");
            grid.Columns[PatientColumn].HeaderText = rm.GetString("releasePatient.columnHeader");
            grid.Columns[EncounterColumn].HeaderText = rm.GetString(EncounterColumn + ".columnHeader");
            grid.Columns[DocDetailColumn].HeaderText = rm.GetString(DocDetailColumn + ".columnHeader");
            grid.Columns[PagesColumn].HeaderText = rm.GetString(PagesColumn + ".columnHeader");
            grid.Columns[RequestSecureColumn].HeaderText = rm.GetString(RequestSecureColumn + ".columnHeader");
            grid.Columns[QueueSecureColumn].HeaderText = rm.GetString(QueueSecureColumn + ".columnHeader");
            grid.Columns[ShippingAddressColumn].HeaderText = rm.GetString(ShippingAddressColumn + ".columnHeader");
            grid.Columns[ShippingMethodColumn].HeaderText = rm.GetString(ShippingMethodColumn + ".columnHeader");
            grid.Columns[TrackNoColumn].HeaderText = rm.GetString(TrackNoColumn + ".columnHeader");
            grid.Columns[UserNameColumn].HeaderText = rm.GetString(UserNameColumn + ".columnHeader");
           
            SetLabel(rm, userNameLabel);
            SetLabel(rm, releaseDateLabel);
            SetLabel(rm, filtersGroupBox);
            SetLabel(rm, filterRadioButton);
            SetLabel(rm, allPatientRadioButton);
            SetLabel(rm, patientsGroupBox);
            SetLabel(rm, patientNameLabel);
            SetLabel(rm, genderLabel);
            SetLabel(rm, dobShortLabel);
            SetLabel(rm, ssnLabel);
            SetLabel(rm, epnLabel);
            SetLabel(rm, facilityLabel);
            SetLabel(rm, mrnLabel);

            presetDateRange.Localize();

            rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            grid.Columns[RequestSecureColumn].ToolTipText = rm.GetString(RequestSecureColumn + ".columnHeader");
            grid.Columns[QueueSecureColumn].ToolTipText = rm.GetString(QueueSecureColumn + ".columnHeader");
        }

        /// <summary>
        /// Gets localized key of UI controls
        /// </summary>
        /// <param name="control"></param>
        /// <returns></returns>
        protected override string GetLocalizeKey(Control control)
        {
            return base.GetLocalizeKey(control);
        }

        /// <summary>
        /// Enable the events.
        /// </summary>
        private void EnableEvents()
        {
            DisableEvents();
            UserNameSelectionChanged = new EventHandler(UserName_SelectionChanged);
            FilterPatientSelectionChanged = new EventHandler(FilterPatient);

            filterRadioButton.CheckedChanged += FilterPatientSelectionChanged;
            usernameCombo.SelectedIndexChanged += UserNameSelectionChanged;
            patientList.SelectionChanged += new EventHandler(patientList_SelectionChanged);

            presetDateRange.PresetDateRangeHandler += presetDateRangeHandler;
        }

        /// <summary>
        /// Disable the events.
        /// </summary>
        private void DisableEvents()
        {
            filterRadioButton.CheckedChanged -= FilterPatientSelectionChanged;
            usernameCombo.SelectedIndexChanged -= UserNameSelectionChanged;
            patientList.SelectionChanged -= new EventHandler(patientList_SelectionChanged);

            presetDateRange.PresetDateRangeHandler -= presetDateRangeHandler;
        }
        
        /// <summary>
        /// Pepopulates the release history UI.
        /// </summary>
        /// <param name="data"></param>
        public void PrePopulate(object data)
        {
            DisableEvents();
            ROIViewUtility.MarkBusy(true);

            if (data == null) return;
            releaseHistory = (ReleaseHistoryDetails)data;            
          
            if (releaseHistory.ReleasedPatients != null)
            {
                SetData(releaseHistory.ReleasedPatients.Values);
                PopulateFilterCombos();
                PopulateReleaseHistories();
                grid.ClearSelection();
            }
            
            ROIViewUtility.MarkBusy(false);
            EnableEvents();
        }

        public override void SetPane(IPane pane)
        {
            base.SetPane(pane);
            presetDateRange.SetExecutionContext(Context);
            presetDateRange.SetPane(Pane);
        }

        /// <summary>
        /// Sets the patient data.
        /// </summary>
        /// <param name="data"></param>
        private void SetData(object data)
        {
            List<ReleasedPatientDetails> patientsList = new List<ReleasedPatientDetails>(data as IList<ReleasedPatientDetails>);
            patientsList.Sort(ReleasedPatientDetails.Sorter);

            ComparableCollection<ReleasedPatientDetails> patients = new ComparableCollection<ReleasedPatientDetails>(patientsList);
            patients.SetSortedInfo(TypeDescriptor.GetProperties(typeof(ReleasedPatientDetails))["Name"], ListSortDirection.Ascending);
            patientList.SetItems(patients);

            EnableEvents();
            patientList.Enabled = (patientList.RowCount > 0 && filterRadioButton.Checked);
            grid.Enabled = patientList.RowCount > 0;
            ShowHeaderText();
            patientList.ClearSelection();
        }

        /// <summary>
        /// Populates the filter dropdowns.
        /// </summary>
        private void PopulateFilterCombos()
        {
            foreach (DictionaryEntry key in releaseHistory.UserNames)
            {
                usernameCombo.Items.Add(key);
            }
            usernameCombo.ValueMember = "Key";
            usernameCombo.DisplayMember = "Value";

            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            usernameCombo.Items.Insert(0, rm.GetString("allRadioButton"));
            usernameCombo.SelectedIndex = 0;
        }

        /// <summary>
        /// Populates the release histories.
        /// </summary>
        private void PopulateReleaseHistories()
        {
            ComparableCollection<ReleasedItemDetails> list = new ComparableCollection<ReleasedItemDetails>(releaseHistory.ReleaseItems);
            list.SetSortedInfo(TypeDescriptor.GetProperties(typeof(ReleasedItemDetails))["ReleasedDate"], ListSortDirection.Ascending);
            grid.SetItems((IFunctionCollection)list);
            UpdateHistoryDetailCount();
        }

        /// <summary>
        /// Update the Release and Page count label
        /// </summary>
        private void UpdateHistoryDetailCount()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            string countReleaseText;
            string countPageText;
            int releaseCount = grid.RowCount;
            int pageCount = 0;

            foreach (DataGridViewRow row in grid.Rows)
            {
                pageCount += ((ReleasedItemDetails)row.DataBoundItem).PageCount;
            }

            if (releaseCount == 0)
            {
                countReleaseText = rm.GetString(countReleaseLabel.Name + ReleasesFound);
                countPageText = rm.GetString(countPageLabel.Name + PagesFound);
            }
            else
            {
                if (releaseCount == 1)
                {
                    countReleaseText = rm.GetString(countReleaseLabel.Name + ReleaseFound);
                }
                else
                {
                    countReleaseText = rm.GetString(countReleaseLabel.Name + ReleasesFound);
                }
                if (pageCount == 1)
                { 
                    countPageText = rm.GetString(countPageLabel.Name + PageFound);
                }
                else
                {
                    countPageText = rm.GetString(countPageLabel.Name + PagesFound);
                }
            }
            countReleaseLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countReleaseText, releaseCount);
            countPageLabel.Text = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture, countPageText, pageCount);
        }

        /// <summary>
        /// Occurs when all patient radio button is selected.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void allPatientRadioButton_CheckedChanged(object sender, EventArgs e)
        {
            if (allPatientRadioButton.Checked)
            {
                patientList.ClearSelection();
                PopulatePatientDetails(null);
                grid.ClearSelection();
                patientList.Enabled = false;
                UpdateFilter();
            }
        }

        /// <summary>
        /// Selects the first row.
        /// </summary>
        private void SelectFirstRow()
        {
            if (patientList.Rows.Count > 0)
            {
                patientList.Rows[0].Selected = true;
                releasePatient = (ReleasedPatientDetails)patientList.SelectedRows[0].DataBoundItem;
                PopulatePatientDetails(releasePatient);
            }
        }

        /// <summary>
        /// Displays the patient count in the grid header.
        /// </summary>
        private void ShowHeaderText()
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.UIText.ToString());
            patientList.Columns[NameColumn].HeaderText = string.Format(System.Threading.Thread.CurrentThread.CurrentUICulture,
                                                                       "{0} " + rm.GetString("patients.columnHeader"), new object[] { patientList.RowCount });
        }

        /// <summary>
        /// Sets the information on selected patient
        /// </summary>
        /// <param name="patient"></param>
        private void PopulatePatientDetails(ReleasedPatientDetails patient)
        {
            if (patient != null)
            {
                nameValueLabel.Text = patient.Name;
                dobValueLabel.Text = patient.FormattedDOB;
                genderValueLabel.Text = patient.Gender;
                ssnValueLabel.Text = ROIViewUtility.ApplyMask(patient.SSN);
                mrnValueLabel.Text = patient.MRN;
                facilityValueLabel.Text = patient.FacilityCode;
                epnValueLabel.Text = patient.EPN;
                //CR337443
                epnValueLabel.Visible = UserData.Instance.EpnEnabled;
                vipPatientIcon.Visible = patient.IsVip;
                lockedPatientIcon.Visible = patient.IsLockedPatient || patient.EncounterLocked;
                toolTip.SetToolTip(nameValueLabel, patient.Name);
            }
            else
            {
                nameValueLabel.Text = string.Empty;
                dobValueLabel.Text = string.Empty;
                genderValueLabel.Text = string.Empty;
                ssnValueLabel.Text = string.Empty;
                mrnValueLabel.Text = string.Empty;
                facilityValueLabel.Text = string.Empty;
                epnValueLabel.Text = string.Empty;
                vipPatientIcon.Visible = false;
                lockedPatientIcon.Visible = false;
            }
        }

        /// <summary>
        /// Invoked when username combo selected index changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserName_SelectionChanged(object sender, EventArgs e)
        {
            UpdateFilter();
        }

        /// <summary>
        /// Invoked when filter radio button state changed.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void FilterPatient(object sender, EventArgs e)
        {
            patientList.Enabled = patientList.RowCount > 0;
            SelectFirstRow();
            UpdateFilter();
        }

        /// <summary>
        /// Update filter to histories grid.
        /// </summary>
        private void UpdateFilter()
        {
            if (allPatientRadioButton.Checked && 
                usernameCombo.SelectedIndex == 0 && 
                !presetDateRange.FromDate.HasValue &&
                !presetDateRange.ToDate.HasValue)
            {
                grid.RemoveFilter();
                UpdateHistoryDetailCount();
                return;
            }
            string expression = string.Empty;
            
            if (!string.IsNullOrEmpty(patientsKey))
            {
                grid.RemoveFilter();
                expression = "Key IN ( " + patientsKey + ") AND ";
            }

            if (usernameCombo.SelectedIndex != 0)
            {
                expression += "UserId = '" + ((DictionaryEntry)usernameCombo.SelectedItem).Key + "' AND ";
            }

            if (presetDateRange.FromDate.HasValue && presetDateRange.ToDate.HasValue)
            {
                expression += "ReleasedDate >='" + presetDateRange.FromDate.Value.ToString() +
                              "' AND ReleasedDate <= '" + presetDateRange.ToDate.Value.ToString() + "'";
            }
            else
            {
                if (expression.Length > 0)
                {
                    expression = expression.Substring(0, expression.Length - 4).Trim();
                }
            }

            grid.Filter = expression;
            UpdateHistoryDetailCount();
        }

        private void patientList_SelectionChanged(object sender, EventArgs e)
        {
            patientsKey = string.Empty;
            if (patientList.SelectedRows.Count != 0)
            {
                releasePatient = (ReleasedPatientDetails)patientList.SelectedRows[0].DataBoundItem;
                if (patientList.SelectedRows.Count == 1)
                {
                    patientsKey = "'" + releasePatient.Key + "'";
                    PopulatePatientDetails(releasePatient);
                }
                else
                {
                    PopulatePatientDetails(null);
                    foreach (DataGridViewRow row in patientList.SelectedRows)
                    {
                        patientsKey += "'" + ((ReleasedPatientDetails)row.DataBoundItem).Key + "', ";
                    }
                    patientsKey = patientsKey.TrimEnd(new char[] { ',', ' ' });
                }
                
            }
            if (allPatientRadioButton.Checked) return;

            UpdateFilter();
        }

        private void grid_CellFormatting(object sender, System.Windows.Forms.DataGridViewCellFormattingEventArgs e)
        {
            ResourceManager rm = Context.CultureManager.GetCulture(CultureType.ToolTip.ToString());
            if (e.ColumnIndex == 5)
            {
                grid.Rows[e.RowIndex].Cells[e.ColumnIndex].ToolTipText = rm.GetString(RequestSecureColumn);                               
            }
            if (e.ColumnIndex == 6)
            {
                grid.Rows[e.RowIndex].Cells[e.ColumnIndex].ToolTipText = rm.GetString(QueueSecureColumn);
            }
        }

        /// <summary>
        /// Event invoked when ever there is any change in the value 
        /// of Search criteria. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Process_PresetDateRangedHandler(object sender, EventArgs e)
        {
            UpdateFilter();
        }

        #endregion

        #region Security Rights

        /// <summary>
        /// Apply security rights for UI controls based on the permissions that user has. 
        /// </summary>
        public void ApplySecurityRights()
        {
            Collection<string> securityRightIds = new Collection<string>();
            securityRightIds.Add(ROISecurityRights.ROICreateRequest);
            securityRightIds.Add(ROISecurityRights.ROIModifyRequest);

            if (!IsAllowed(securityRightIds, false))
            {
                this.Enabled = false;
            }
        }

        #endregion

        #region Properties

        /// <summary>
        /// To fill the column width.
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
